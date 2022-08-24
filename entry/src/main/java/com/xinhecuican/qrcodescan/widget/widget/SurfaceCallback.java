package com.xinhecuican.qrcodescan.widget.widget;

import com.xinhecuican.qrcodescan.utils.ScreenInfo;
import com.xinhecuican.qrcodescan.widget.controller.CameraController;
import ohos.agp.graphics.SurfaceOps;
import ohos.app.Context;

public class SurfaceCallback implements SurfaceOps.Callback
{
    private Context context;
    public SurfaceCallback()
    {
        super();
    }

    public SurfaceCallback(Context context)
    {
        this.context = context;
    }
    @Override
    public void surfaceChanged(SurfaceOps surfaceOps, int i, int i1, int i2)
    {

    }

    @Override
    public void surfaceCreated(SurfaceOps surfaceOps)
    {
        if (surfaceOps != null) {
            surfaceOps.setFixedSize(ScreenInfo.screenHeight, ScreenInfo.screenWidth);
        }
        CameraController.getInstance().setSurface(surfaceOps.getSurface());
        CameraController.getInstance().tryStartCamera(context);
    }

    @Override
    public void surfaceDestroyed(SurfaceOps surfaceOps)
    {
        CameraController.getInstance().setSurface(null);
    }
}
