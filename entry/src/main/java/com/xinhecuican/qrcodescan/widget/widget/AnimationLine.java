package com.xinhecuican.qrcodescan.widget.widget;

import ohos.agp.components.AttrSet;
import ohos.agp.components.Component;
import ohos.agp.render.Canvas;
import ohos.agp.render.Paint;
import ohos.agp.utils.Color;
import ohos.agp.utils.Point;
import ohos.app.Context;

public class AnimationLine extends Component implements Component.DrawTask, Component.EstimateSizeListener
{
    private int height = 100;
    private int width = 1;

    private int rectWidth = 100;
    private int rectHeight = 100;
    private Color LineColor = Color.BLUE;

    private Point startPos;
    private Point EndPos;

    public AnimationLine(Context context)
    {
        super(context);
        setEstimateSizeListener(this);
        addDrawTask(this);
    }

    public AnimationLine(Context context, int rectWidth, int rectHeight, Color lineColor)
    {
        super(context);
        this.rectHeight = rectHeight;
        this.rectWidth = rectWidth;
        this.LineColor = lineColor;
        setEstimateSizeListener(this);
        addDrawTask(this);
    }

    public AnimationLine(Context context, AttrSet attrSet)
    {
        super(context, attrSet);
        initAttr(attrSet);
        setEstimateSizeListener(this);
        addDrawTask(this);
    }

    private void initAttr(AttrSet attr)
    {
        if (attr == null) return;
        if (attr.getAttr("line_color").isPresent()) {
            LineColor = attr.getAttr("line_color").get().getColorValue();
        }
        if (attr.getAttr("rect_width").isPresent()) {
            rectWidth = attr.getAttr("rect_width").get().getDimensionValue();
        }
        if (attr.getAttr("rect_height").isPresent()) {
            rectHeight = attr.getAttr("rect_height").get().getDimensionValue();
        }
    }

    @Override
    public void onDraw(Component component, Canvas canvas)
    {
        startPos = new Point((component.getWidth() - rectWidth) / 2, component.getHeight() / 2);
        EndPos = new Point((component.getWidth() + rectWidth) / 2, component.getHeight() / 2);
        Paint paint = new Paint();
        paint.setStrokeWidth(5f);
        paint.setAlpha(0.9f);
        paint.setColor(LineColor);
        canvas.drawLine(startPos, EndPos, paint);
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
}
