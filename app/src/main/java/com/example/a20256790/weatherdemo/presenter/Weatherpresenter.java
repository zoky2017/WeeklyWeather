package com.example.a20256790.weatherdemo.presenter;

import android.location.LocationManager;

/**
 * Created by zoky on 2017/3/4.
 */

public interface Weatherpresenter {


    String getLocationCity(LocationManager locationManager);

    void  refershWeatherData(String cityName);

    void getWeatherResult(String weatherId);

}
