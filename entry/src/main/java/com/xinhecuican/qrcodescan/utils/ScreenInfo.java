package com.xinhecuican.qrcodescan.utils;

import ohos.agp.utils.Point;
import ohos.agp.window.service.Display;
import ohos.agp.window.service.DisplayManager;
import ohos.app.Context;

import java.util.Optional;

public class ScreenInfo
{
    public static int screenWidth;
    public static int screenHeight;

    public static void getScreenMetrics(Context context){
        DisplayManager displayManager = DisplayManager.getInstance();
        Optional<Display> optDisplay = displayManager.getDefaultDisplay(context);
        Point point = new Point(0, 0);
        if (optDisplay.isPresent()) {
            Display display = optDisplay.get();
            display.getSize(point);
        }
        screenWidth = (int)point.position[0];
        screenHeight = (int)point.position[1];
    }

}
