package com.xinhecuican.qrcodescan.widget.controller;

import com.xinhecuican.qrcodescan.utils.LogLevel;
import com.xinhecuican.qrcodescan.utils.ScreenInfo;
import com.xinhecuican.qrcodescan.widget.widget.ImageArrivalListener;
import ohos.agp.graphics.Surface;
import ohos.app.Context;
import ohos.bundle.IBundleManager;
import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;
import ohos.hiviewdfx.HiLog;
import ohos.media.camera.CameraKit;
import ohos.media.camera.device.*;
import ohos.media.camera.params.Metadata;
import ohos.media.image.ImageReceiver;
import ohos.media.image.common.ImageFormat;

import static ohos.media.camera.device.Camera.FrameConfigType.FRAME_CONFIG_PREVIEW;
import static ohos.media.camera.device.CameraInfo.FacingType.CAMERA_FACING_BACK;
import static ohos.media.camera.device.CameraInfo.FacingType.CAMERA_FACING_FRONT;

public class CameraController extends CameraStateCallback{

    protected Camera camera;
    private Surface surface;
    private FrameStateCallback frameStateCallback;
    private CameraConfig.Builder cameraConfig;
    private FrameConfig.Builder frameConfig;
    private ImageReceiver imageReceiver;
    private ImageArrivalListener imageArrivalListener;

    private EventHandler cameraCBHandler;
    private EventHandler cameraHandler;


    private boolean is_create;
    private boolean is_configure;
    private boolean is_surface_init;

    private static CameraController _instance=null;

    private CameraController() {
        is_create = false;
        is_configure = false;
        is_surface_init = false;
        imageArrivalListener = new ImageArrivalListener();
        cameraHandler = new EventHandler(EventRunner.create("Camera"));
        cameraCBHandler = new EventHandler(EventRunner.create("CameraCB"));
    }

    @Override
    public void onCreated(Camera camera) {
        if(is_create)
            return;
        is_create = true;
        super.onCreated(camera);
        this.camera = camera;
        CameraConfig.Builder builder = camera.getCameraConfigBuilder();
        if (builder == null) {
            LogLevel.error("create camera error");
        }
        assert builder != null;
        builder.addSurface(surface);
        imageReceiver = ImageReceiver.create(ScreenInfo.screenWidth, ScreenInfo.screenHeight, ImageFormat.YUV420_888, 5);
        imageReceiver.setImageArrivalListener(imageArrivalListener);
        builder.addSurface(imageReceiver.getRecevingSurface());
        builder.setFrameStateCallback(frameStateCallback, cameraHandler);
        try {
            camera.configure(builder.build());
        } catch (IllegalArgumentException | IllegalStateException e) {
            LogLevel.error(e.toString());
        }
    }

    @Override
    public void onConfigured(Camera camera) {
        super.onConfigured(camera);
        configure();
        is_configure = true;
        startCaputre();
    }

    @Override
    public void onCreateFailed(String cameraId, int errorCode) {
        super.onCreateFailed(cameraId, errorCode);
        is_create = false;
    }

    @Override
    public void onReleased(Camera camera)
    {
        super.onReleased(camera);
        is_create = false;
    }

    @Override
    public void onConfigureFailed(Camera camera, int errorCode) {
        super.onConfigureFailed(camera, errorCode);
        is_configure = false;
    }

    public static CameraController getInstance()
    {
        if(_instance == null)
        {
            _instance = new CameraController();
        }
        return _instance;
    }

    public void setSurface(Surface surface)
    {
        this.surface = surface;
        is_surface_init = surface != null;
    }
    public void setFrameStateCallback(FrameStateCallback callback){this.frameStateCallback = callback;}

    public boolean tryStartCamera(Context context)
    {
        if(is_create || !is_surface_init || context.verifySelfPermission("ohos.permission.CAMERA") != IBundleManager.PERMISSION_GRANTED)
            return false;
        CameraKit camerakit;
        if((camerakit = CameraKit.getInstance(context)) != null)
        {
            try {
                String[] cameraIds = camerakit.getCameraIds();
                if (cameraIds.length <= 1)
                    return false;
                for(String id : cameraIds)
                {
                    if(camerakit.getCameraInfo(id).getFacingType() == CAMERA_FACING_BACK)
                    {
                        camerakit.createCamera(id, this, cameraCBHandler);
                        break;
                    }
                }

            }catch (IllegalStateException e) {
                HiLog.error(LogLevel.ERROR, e.toString());
            }
        }
        return false;
    }

    public void stopCamera()
    {
        if(is_create)
        {
            if(camera != null)
            {
                camera.release();
            }
            is_create = false;
            is_configure = false;
            is_surface_init = false;
            if(imageReceiver != null)
            {
                imageReceiver.release();
            }
        }
    }

    public boolean startCaputre()
    {
        if(is_configure)
        {
            try {
                int triggerId = camera.triggerLoopingCapture(frameConfig.build());
            } catch (IllegalArgumentException | IllegalStateException e) {
                LogLevel.error(e.toString());
            }
            return true;
        }
        return false;
    }

    public void stopCapture()
    {
        if(is_create)
        {
            camera.stopLoopingCapture();
        }
    }

    private void configure()
    {
        frameConfig = camera.getFrameConfigBuilder(FRAME_CONFIG_PREVIEW);
        frameConfig.addSurface(surface);
    }

    private void processImage()
    {

    }
}
