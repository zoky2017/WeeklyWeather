package com.example.a20256790.weatherdemo.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.a20256790.weatherdemo.data.bean.WeatherInfoEntity;
import com.example.a20256790.weatherdemo.util.DateTimeUtil;
import com.example.a20256790.weatherdemo.util.ScreenUtil;
import com.example.a20256790.weatherdemo.util.WeatherIconUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 未来一周天气趋势图
 */
public class WeeklyWeatherView extends View {

    private final String TAG = "weeklyWeatherView";
    private final String TEMP = "°";
    private float height, width;
    private Paint paint = new Paint();
    private Context context;
    private List<WeatherInfoEntity.WeathersBean> weathers = new ArrayList<>();
    //未来一周的最高温度和最低温度差
    private float maxMinDelta;
    private int tempH, tempL;
    private float radius = 0;
    //左右两侧的Padding和
    private float leftRight;

    private static final int TYPE_LINE = 0;
    private static final int TYPE_BESSEL = 1;
    private static boolean isYesterday = false;
    private int type = TYPE_BESSEL;


    public WeeklyWeatherView(Context context) {
        super(context);
        this.context = context;
    }

    public WeeklyWeatherView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public WeeklyWeatherView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = ScreenUtil.getScreenWidth(context);
        height = width - getFitSize(20);
        leftRight = getFitSize(30);
        radius = getFitSize(8);
        setMeasuredDimension((int) width, (int) height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (weathers.size() == 0)
            return;

        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(0);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(ScreenUtil.getSp(context, 13));

        drawWeatherDetail(canvas);

    }

    private void drawWeatherDetail(Canvas canvas) {

        float weekPaddingBottom = getFitSize(200);
        float weekInfoPaddingBottom = getFitSize(40);
        float linePaddingBottom = getFitSize(330);
        float tempPaddingTop = getFitSize(20);
        float tempPaddingBottom = getFitSize(45);

        //获取每个天气所占空间
        float lineHigh = getFitSize(320);

        //单位长度值，宽是以一天为单位，高是以1度为单位
        float widthAvg = (width - leftRight) / weathers.size();
        float heightAvg = lineHigh / maxMinDelta;

        Matrix matrix = new Matrix();
        matrix.postScale(0.25f, 0.25f); //长和宽放大缩小的比例

        Path pathTempHigh = new Path();
        Path pathTempLow = new Path();

        float paddingLeft = 0;
        int i = 2;
        List<PointF> mPointHs = new ArrayList<>();
        List<PointF> mPointLs = new ArrayList<>();
        for (WeatherInfoEntity.WeathersBean weathersBean : weathers) {

            if (DateTimeUtil.isYesterday(weathersBean.getDate()) == true){
                i = 1;
                isYesterday = true;
            }
            paddingLeft = leftRight / 2 + (i - 1 + 0.5f) * widthAvg;
            if (type == TYPE_LINE) {
                if (i == 1) {
                    pathTempHigh.moveTo(paddingLeft, height - (linePaddingBottom + (weathersBean.getTemp_day_c() - tempL) * heightAvg));
                    pathTempLow.moveTo(paddingLeft, height - (linePaddingBottom + (weathersBean.getTemp_night_c() - tempL) * heightAvg));
                } else {
                    pathTempHigh.lineTo(paddingLeft, height - (linePaddingBottom + (weathersBean.getTemp_day_c() - tempL) * heightAvg));
                    pathTempLow.lineTo(paddingLeft, height - (linePaddingBottom + (weathersBean.getTemp_night_c() - tempL) * heightAvg));
                }
                paint.setStyle(Paint.Style.FILL);
                paint.setStrokeWidth(getFitSize(2));
                canvas.drawCircle(paddingLeft, height  , radius, paint);
                canvas.drawCircle(paddingLeft, height - (linePaddingBottom + (weathersBean.getTemp_day_c() - tempL) * heightAvg), radius, paint);
                canvas.drawCircle(paddingLeft, height - (linePaddingBottom + (weathersBean.getTemp_night_c() - tempL) * heightAvg), radius, paint);
            } else {
                //将所有的点添加进PointF集合,若是昨天的数据，放在第一位。
                if (isYesterday){
                    PointF pointFH = new PointF(paddingLeft, height - (linePaddingBottom + (weathersBean.getTemp_day_c() - tempL) * heightAvg));
                    mPointHs.add(0,pointFH);
                    PointF pointFL = new PointF(paddingLeft, height - (linePaddingBottom + (weathersBean.getTemp_night_c() - tempL) * heightAvg));
                    mPointLs.add(0,pointFL);
                    isYesterday = false;
                }else {
                    PointF pointFH = new PointF(paddingLeft, height - (linePaddingBottom + (weathersBean.getTemp_day_c() - tempL) * heightAvg));
                    mPointHs.add(pointFH);
                    PointF pointFL = new PointF(paddingLeft, height - (linePaddingBottom + (weathersBean.getTemp_night_c() - tempL) * heightAvg));
                    mPointLs.add(pointFL);
                }

            }

            paint.setStrokeWidth(0);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawText(weathersBean.getTemp_day_c() + TEMP, paddingLeft, height - (linePaddingBottom + tempPaddingTop + (weathersBean.getTemp_day_c() - tempL) * heightAvg), paint);
            canvas.drawText(weathersBean.getTemp_night_c() + TEMP, paddingLeft, height - (linePaddingBottom - tempPaddingBottom + (weathersBean.getTemp_night_c() - tempL) * heightAvg), paint);

            //星期
            canvas.drawText(weathersBean.getWeek(), paddingLeft, height - (weekPaddingBottom), paint);

            //天气图标
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), WeatherIconUtil.getWeatherIconID(weathersBean.getWeather()));
            Bitmap bitmapDisplay = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

            canvas.drawBitmap(bitmapDisplay,
                    paddingLeft - bitmapDisplay.getWidth() / 2, height - getFitSize(8) - ((weekPaddingBottom - weekInfoPaddingBottom) / 2 + weekInfoPaddingBottom) - bitmapDisplay.getHeight() / 2, paint);
            bitmap.recycle();
            bitmapDisplay.recycle();
            //天气描述
            canvas.drawText(weathersBean.getWeather(), paddingLeft, height - (weekInfoPaddingBottom), paint);
            i++;
        }
        paint.setStrokeWidth(getFitSize(3));
        paint.setStyle(Paint.Style.STROKE);

        if (type == TYPE_LINE) {
            canvas.drawPath(pathTempHigh, paint);
            canvas.drawPath(pathTempLow, paint);
        } else {
            drawBezier(canvas, mPointHs, pathTempHigh);
            drawBezier(canvas, mPointLs, pathTempLow);
        }
    }

    private void drawBezier(Canvas canvas, List<PointF> pointFs, Path path) {
        path.reset();
        for (int i = 0; i < pointFs.size(); i++) {
            PointF pointCur = pointFs.get(i);
            PointF pointPre = null;
            if (i > 0)
                pointPre = pointFs.get(i - 1);
            if (i == 0) {
                path.moveTo(pointCur.x, pointCur.y);
            } else {
                path.quadTo(pointPre.x, pointPre.y, (pointPre.x + pointCur.x) / 2, (pointPre.y + pointCur.y) / 2);
            }
        }
        path.lineTo(pointFs.get(pointFs.size() - 1).x, pointFs.get(pointFs.size() - 1).y);

        canvas.drawPath(path, paint);
    }


    private int getMaxMinDelta() {
        if (weathers.size() > 0) {
            tempH = weathers.get(0).getTemp_day_c();
            tempL = weathers.get(0).getTemp_night_c();
            for (WeatherInfoEntity.WeathersBean weathersBean : weathers) {
                if (weathersBean.getTemp_day_c() > tempH) {
                    tempH = weathersBean.getTemp_day_c();
                }
                if (weathersBean.getTemp_night_c() < tempL) {
                    tempL = weathersBean.getTemp_night_c();
                }
            }
            return tempH - tempL;
        }
        return 0;
    }


    public void setWeathers(List<WeatherInfoEntity.WeathersBean> weathers) {
        this.weathers.clear();
        this.weathers.addAll(weathers);
        maxMinDelta = getMaxMinDelta();
        this.invalidate();

    }

    private float getFitSize(float orgSize) {
        return orgSize * width * 1.0f / 1080;
    }



}
