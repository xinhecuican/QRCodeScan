package com.xinhecuican.qrcodescan.slice;

import com.xinhecuican.qrcodescan.ResourceTable;
import com.xinhecuican.qrcodescan.utils.ScreenInfo;
import com.xinhecuican.qrcodescan.widget.controller.CameraController;
import com.xinhecuican.qrcodescan.widget.widget.AnimationLine;
import com.xinhecuican.qrcodescan.widget.widget.CameraFrameState;
import com.xinhecuican.qrcodescan.widget.widget.SurfaceCallback;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.animation.Animator;
import ohos.agp.animation.AnimatorScatter;
import ohos.agp.animation.AnimatorValue;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.surfaceprovider.SurfaceProvider;
import ohos.agp.graphics.Surface;

public class MainAbilitySlice extends AbilitySlice {
    private CameraController controller;
    private SurfaceProvider surfaceProvider;
    private CameraFrameState frameState;
    private AnimatorValue animatorValue;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        initUI();
        initAnimation();
        controller = CameraController.getInstance();
        controller.setSurface(surfaceProvider.getSurfaceOps().get().getSurface());
        frameState = new CameraFrameState();
        controller.setFrameStateCallback(frameState);
    }

    @Override
    public void onActive() {
        super.onActive();
        controller.tryStartCamera(this);
    }

    @Override
    public void onInactive(){
        super.onInactive();
        controller.stopCamera();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
//        controller.startCamera(this);
    }

    @Override
    public void onBackground()
    {
//        controller.stopCamera();
    }

    private void initUI()
    {
        setUIContent(ResourceTable.Layout_ability_main);
        getWindow().setTransparent(true);
        DirectionalLayout.LayoutConfig params = new DirectionalLayout.LayoutConfig(
                ComponentContainer.LayoutConfig.MATCH_PARENT, ComponentContainer.LayoutConfig.MATCH_PARENT);
        DirectionalLayout camera_layout = (DirectionalLayout)findComponentById(ResourceTable.Id_camera_layout);
        surfaceProvider = new SurfaceProvider(this);
        surfaceProvider.setLayoutConfig(params);
        surfaceProvider.getSurfaceOps().get().setFixedSize(ScreenInfo.screenHeight, ScreenInfo.screenWidth );
        surfaceProvider.pinToZTop(false);
        surfaceProvider.getSurfaceOps().get().addCallback(new SurfaceCallback(this));

        camera_layout.addComponent(surfaceProvider);
    }

    private void initAnimation()
    {
        AnimationLine line = (AnimationLine)findComponentById(ResourceTable.Id_animation_line);
        animatorValue = new AnimatorValue();
        animatorValue.setDelay(200);
        animatorValue.setDuration(2000);
        animatorValue.setLoopedCount(AnimatorValue.INFINITE);
        animatorValue.setCurveType(Animator.CurveType.CYCLE);
        animatorValue.setValueUpdateListener(new AnimatorValue.ValueUpdateListener()
        {
            @Override
            public void onUpdate(AnimatorValue animatorValue, float v)
            {
                line.setAlpha(v * 0.5f);
            }
        });
        animatorValue.start();
    }
}
