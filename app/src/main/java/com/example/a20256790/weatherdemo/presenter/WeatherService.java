package com.example.a20256790.weatherdemo.presenter;

import com.example.a20256790.weatherdemo.data.bean.WeatherInfoEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by zoky on 2017/3/6.
 */


public interface WeatherService {
    @Headers("Cache-Control: public, max-age=3600")
    @GET("{weatherId}.json")
    Observable<WeatherInfoEntity> getWeatherInfo(@Path("weatherId") String weatherId );
}
