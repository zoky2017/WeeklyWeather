package com.example.a20256790.weatherdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zoky on 2016/3/5.
 * 空气质量弧形条
 */
public class AirQualityView extends View {

    public AirQualityView(Context context) {
        super(context);
    }

    public AirQualityView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AirQualityView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = width * 550 / 992;
        setMeasuredDimension((int) width, (int) (height + getFitSize(100)));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(getFitSize(60));
        paint.setStyle(Paint.Style.STROKE);

        //外围虚线
        float center = width / 2;
        float radius = height - getFitSize(150);
        RectF rect = new RectF(center - radius + getFitSize(40), center - radius, center
                + radius - getFitSize(40), center + radius - getFitSize(40));
        paint.setAlpha(60);
        canvas.drawArc(rect, 180, 180, false, paint);

        //进度实线
        paint.setAlpha(255);
        canvas.drawArc(rect, 180, (180 * progress * 1.0f / 500), false, paint);

        //文字描述
        paint.setStrokeWidth((float) 0.8);
        paint.setTextSize(getFitSize(80));
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(progress + "", center, radius + getFitSize(30), paint);
        paint.setTextSize(getFitSize(60));
        canvas.drawText(label, rect.centerX(), radius + getFitSize(200), paint);
    }

    private float getFitSize(float orgSize) {
        return orgSize * width / 992;
    }


    public void setProgressAndLabel(int progress, String label) {
        this.progress = progress;
        this.label = label;
        this.invalidate();
    }

    ///////////////////////////////////////////////////////////
    Paint paint = new Paint();
    private float height, width;
    private int progress = 0;
    private String label = "";

}
