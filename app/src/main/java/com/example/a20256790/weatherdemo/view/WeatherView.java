package com.example.a20256790.weatherdemo.view;

import android.content.Context;

import com.example.a20256790.weatherdemo.data.bean.WeatherInfoEntity;

/**
 * Created by zoky on 2017/3/4.
 */

public interface WeatherView  {
    void setTitle(WeatherInfoEntity data);
    void showWeatherData(WeatherInfoEntity data);
    void setMainWeatherInfo(WeatherInfoEntity data);
    void setAqiInfo(WeatherInfoEntity data);
    Context getContext();
}
