package com.xinhecuican.qrcodescan.widget.widget;

import ohos.agp.components.AttrSet;
import ohos.agp.components.Component;
import ohos.agp.render.BlendMode;
import ohos.agp.render.Canvas;
import ohos.agp.render.Paint;
import ohos.agp.render.PixelMapHolder;
import ohos.agp.utils.Color;
import ohos.agp.utils.Point;
import ohos.agp.utils.Rect;
import ohos.app.Context;
import ohos.media.image.PixelMap;
import ohos.media.image.common.PixelFormat;
import ohos.media.image.common.Size;

public class ViewMask extends Component implements Component.DrawTask, Component.EstimateSizeListener
{
    private int width = 1080;
    private int height = 1920;

    private int rectWidth = 20;
    private int rectHeight = 100;
    private Color LineColor = Color.BLUE;

    private Paint circlePaint;
    public ViewMask(Context context)
    {
        super(context);
        setEstimateSizeListener(this);
        addDrawTask(this);
    }

    public ViewMask(Context context, AttrSet attrSet) {
        super(context, attrSet);
        initAttrSet(attrSet);
        setEstimateSizeListener(this);
        addDrawTask(this);
    }

    @Override
    public boolean onEstimateSize(int widthEstimateConfig, int heightEstimateConfig)
    {
        int widthSpce = EstimateSpec.getMode(widthEstimateConfig);
        int heightSpce = EstimateSpec.getMode(heightEstimateConfig);

        int widthConfig = 0;
        switch (widthSpce) {
            case EstimateSpec.UNCONSTRAINT:
            case EstimateSpec.PRECISE:
                width = EstimateSpec.getSize(widthEstimateConfig);
                widthConfig = EstimateSpec.getSizeWithMode(width, EstimateSpec.PRECISE);
                break;
            case EstimateSpec.NOT_EXCEED:
                widthConfig = EstimateSpec.getSizeWithMode(width, EstimateSpec.PRECISE);
                break;
            default:
                break;
        }

        int heightConfig = 0;
        switch (heightSpce) {
            case EstimateSpec.UNCONSTRAINT:
            case EstimateSpec.PRECISE:
                height = EstimateSpec.getSize(heightEstimateConfig);
                heightConfig = EstimateSpec.getSizeWithMode(height, EstimateSpec.PRECISE);
                break;
            case EstimateSpec.NOT_EXCEED:
                heightConfig = EstimateSpec.getSizeWithMode(height, EstimateSpec.PRECISE);
                break;
            default:
                break;
        }
        setEstimatedSize(widthConfig, heightConfig);
        return true;
    }

    private void initAttrSet(AttrSet attrSet) {
        if (attrSet == null) return;
        if (attrSet.getAttr("line_color").isPresent()) {
            LineColor = attrSet.getAttr("line_color").get().getColorValue();
        }
        if (attrSet.getAttr("rect_width").isPresent()) {
            rectWidth = attrSet.getAttr("rect_width").get().getDimensionValue();
        }
        if (attrSet.getAttr("rect_height").isPresent()) {
            rectHeight = attrSet.getAttr("rect_height").get().getDimensionValue();
        }
    }

    @Override
    public void onDraw(Component component, Canvas canvas)
    {
        canvas.drawColor(new Color(0x40000000).getValue(), BlendMode.SRC_OVER);


        Paint paint = new Paint();
        paint.setColor(new Color(0x00000000));
        paint.setBlendMode(BlendMode.SRC);

        PixelMap.InitializationOptions initializationOptions = new PixelMap.InitializationOptions();
        initializationOptions.size = new Size(rectWidth, rectHeight);
        initializationOptions.pixelFormat = PixelFormat.ARGB_8888;
        PixelMap background = PixelMap.create(initializationOptions);
        Point rectTopLeft = new Point((component.getWidth() - rectWidth) / 2, (component.getHeight() - rectHeight) / 2 );
        canvas.drawPixelMapHolder(new PixelMapHolder(background), rectTopLeft.getPointX(), rectTopLeft.getPointY(), paint);
        paint = new Paint();
        paint.setColor(LineColor);
        paint.setStrokeWidth(5f);
        paint.setAlpha(0.9f);
        int lineLength = 50;
        canvas.drawLine(rectTopLeft,
                new Point((component.getWidth() - rectWidth) / 2, (component.getHeight() - rectHeight) / 2 + lineLength), paint);
        canvas.drawLine(rectTopLeft,
                new Point((component.getWidth() - rectWidth) / 2 + lineLength, (component.getHeight() - rectHeight) / 2), paint);
        canvas.drawLine((component.getWidth() + rectWidth) / 2, (component.getHeight() - rectHeight) / 2,
                (component.getWidth() + rectWidth) / 2 - lineLength, (component.getHeight() - rectHeight) / 2, paint);
        canvas.drawLine((component.getWidth() + rectWidth) / 2, (component.getHeight() - rectHeight) / 2,
                (component.getWidth() + rectWidth) / 2, (component.getHeight() - rectHeight) / 2 + lineLength, paint);
        canvas.drawLine((component.getWidth() - rectWidth) / 2, (component.getHeight() + rectHeight) / 2,
                (component.getWidth() - rectWidth) / 2  + lineLength, (component.getHeight() + rectHeight) / 2, paint);
        canvas.drawLine((component.getWidth() - rectWidth) / 2, (component.getHeight() + rectHeight) / 2,
                (component.getWidth() - rectWidth) / 2, (component.getHeight() + rectHeight) / 2 - lineLength, paint);
        canvas.drawLine((component.getWidth() + rectWidth) / 2, (component.getHeight() + rectHeight) / 2,
                (component.getWidth() + rectWidth) / 2, (component.getHeight() + rectHeight) / 2 - lineLength, paint);
        canvas.drawLine((component.getWidth() + rectWidth) / 2, (component.getHeight() + rectHeight) / 2,
                (component.getWidth() + rectWidth) / 2 - lineLength, (component.getHeight() + rectHeight) / 2, paint);
    }
}
