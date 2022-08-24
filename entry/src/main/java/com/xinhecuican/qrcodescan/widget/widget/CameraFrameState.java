package com.xinhecuican.qrcodescan.widget.widget;

import ohos.media.camera.device.Camera;
import ohos.media.camera.device.FrameConfig;
import ohos.media.camera.device.FrameResult;
import ohos.media.camera.device.FrameStateCallback;

public class CameraFrameState extends FrameStateCallback {
    @Override
    public void onFrameFinished(Camera camera, FrameConfig frameConfig, FrameResult frameResult) {
        super.onFrameFinished(camera, frameConfig, frameResult);
    }
}
