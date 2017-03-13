package com.example.a20256790.weatherdemo.presenter.impl;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.Xml;


import com.example.a20256790.weatherdemo.data.bean.CountyIdEntity;
import com.example.a20256790.weatherdemo.data.bean.WeatherInfoEntity;
import com.example.a20256790.weatherdemo.presenter.WeatherService;
import com.example.a20256790.weatherdemo.presenter.Weatherpresenter;
import com.example.a20256790.weatherdemo.util.NetWorkUtil;
import com.example.a20256790.weatherdemo.view.WeatherView;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.xmlpull.v1.XmlPullParser;

/**
 * Created by 20256790 on 2017/3/4.
 */

public class WeatherPresenterImpl implements MvpPresenter, Weatherpresenter {
    private final String TAG = "zoky";
    private final String WEATHER_ID_FILENAME = "CityId.xml";
    private final String COUNTY_NODE = "county";
    private final String BASE_URL = "http://res.aider.meizu.com/1.0/weather/";
    private final int ID = 0;
    private final int NAME = 1;
    private final int WEATHER_CODE = 2;
    private List<CountyIdEntity> counties;
    private String areaId;
    WeatherView weatherView;
    private OkHttpClient okHttpClient;
    WeatherService service;
    // 设缓存有效期为两天
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
    // 30秒内直接读缓存
    private static final long CACHE_AGE_SEC = 0;


    public WeatherPresenterImpl(WeatherView mainView) {
        weatherView = mainView;
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .cache(new Cache(mainView.getContext().getExternalCacheDir(), 10 * 1024 * 1024))
                .writeTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(mRewriteCacheControlInterceptor)
                .addInterceptor(LoggingInterceptor)
                .addNetworkInterceptor(mRewriteCacheControlInterceptor);
        okHttpClient = builder.build();
        Retrofit retrofit = new Retrofit.Builder().client(okHttpClient).baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(WeatherService.class);

    }

    @Override
    public void attachView(MvpView view) {

    }

    @Override
    public void detachView(boolean retainInstance) {

    }

    /**
     * 调用网络接口，获取天气数据
     * @param areaId
     * @return
     */
    @Override
    public void getWeatherResult(String areaId) {
        if (areaId == null) {
            Log.d(TAG, "aread is null");
        }

        service.getWeatherInfo(areaId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherInfoEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(WeatherInfoEntity value) {
                        Log.d(TAG, value.getCity());
                        weatherView.showWeatherData(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.d(TAG, "error");
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    /**
     * 获取当前位置的城市名称
     * @param locationManager
     * @return
     */
    @Override
    public String getLocationCity(LocationManager locationManager) {
        String cityName;
        String provider = null;

        List<String> providerList = locationManager.getProviders(true);
        // 传入 true 就表示只有启用的位置提供器才会被返回。
        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        }else {
            // 当没有可用的位置提供器时，弹出Toast提示用户
            Log.d(TAG,"No location provider to use");
            return null;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if (location == null){
            Log.d(TAG,"location is null");
        }else {
            Log.d(TAG, "latiude:" + location.getLatitude());
        }
        if (ActivityCompat.checkSelfPermission(weatherView.getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(weatherView.getContext()
                , Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            Log.d(TAG,"permission reject granted");


        }else {
            Log.d(TAG," permission granted");
            if (location != null) {
                // 显示当前设备的位置信息
                cityName = getCityNameByLocation(location);
                return cityName;
            }
        }

        return "广州";
    }

    private String getCityNameByLocation(Location location) {
        Geocoder geocoder = new Geocoder(weatherView.getContext(), Locale.getDefault());
        String mcityName = "";
        double lat = 0;
        double lng = 0;
        List<Address> addList = null;
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
        } else {

            System.out.println("无法获取地理信息");
        }

        try {

            addList = geocoder.getFromLocation(lat, lng, 1);    //解析经纬度

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (addList != null && addList.size() > 0) {
            for (int i = 0; i < addList.size(); i++) {
                Address add = addList.get(i);
                mcityName += add.getLocality();
            }
        }
        if(mcityName.length()!=0){

            return mcityName.substring(0, (mcityName.length()-1));
        } else {
            return mcityName;
        }
    }

    public interface ParseCallBack{
        void success(String cityId);
        void fail(String msg);
    }

    @Override
    public void refershWeatherData(final String cityName) {
        //判断是否已经读取了xml数据
        if (counties == null){
            parseXml(cityName, new ParseCallBack() {
                @Override
                public void success(String areaId) {
                    getWeatherResult(areaId);
                }

                @Override
                public void fail(String msg) {
                    Log.d(TAG,"parseXml failue");
                }
            });
        }else{
            for (CountyIdEntity countyIdEntityBean : counties) {
                if (cityName.equals(countyIdEntityBean.getName())) {
                    areaId = countyIdEntityBean.getWeatherCode();
                    getWeatherResult(areaId);
                    break;
                }

            }
        }


    }

    //解析xml数据，获取天气Id
    private void parseXml(final String cityName, final ParseCallBack callBack){
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                InputStream is;
                XmlPullParser parser = Xml.newPullParser();
                int eventType;
                CountyIdEntity countyIdEntity;
                try {
                    is = weatherView.getContext().getAssets().open(WEATHER_ID_FILENAME);
                    parser.setInput(is, "UTF-8");
                    eventType = parser.getEventType();

                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        switch (eventType) {
                            case XmlPullParser.START_DOCUMENT:
                                counties = Collections.synchronizedList(new ArrayList<CountyIdEntity>());
                                break;
                            case XmlPullParser.START_TAG:

                                if (parser.getName().equals(COUNTY_NODE)) {
                                    countyIdEntity = new CountyIdEntity();
                                    parser.next();
                                    countyIdEntity.setId(parser.getAttributeValue(ID));
                                    countyIdEntity.setName(parser.getAttributeValue(NAME));
                                    countyIdEntity.setWeatherCode(parser.getAttributeValue(WEATHER_CODE));
                                    counties.add(countyIdEntity);

                                }
                                break;
                            case XmlPullParser.END_TAG:
                                break;
                        }
                        eventType = parser.next();

                    }
                    for (CountyIdEntity countyIdEntityBean : counties) {

                        if (cityName.equals(countyIdEntityBean.getName())) {
                            areaId = countyIdEntityBean.getWeatherCode();
                            Log.d(TAG,"areaId on subscribe"+areaId);
                            callBack.success(areaId);
                            break;
                        }
                    }
                } catch (Exception exce) {
                    exce.printStackTrace();
                }
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(String value) {
                Log.d(TAG,"areaId on Next val"+value);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });

    }

    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            // 在这里统一配置请求头缓存策略以及响应头缓存策略
            if (NetWorkUtil.isNetworkAvailable(weatherView.getContext())) {
                // 在有网的情况下CACHE_AGE_SEC秒内读缓存，大于CACHE_AGE_SEC秒后会重新请求数据
                request = request.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control").header("Cache-Control", "public, max-age=" + CACHE_AGE_SEC).build();
                Response response = chain.proceed(request);
                return response.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control").header("Cache-Control", "public, max-age=" + CACHE_AGE_SEC).build();
            } else {
                // 无网情况下CACHE_STALE_SEC秒内读取缓存，大于CACHE_STALE_SEC秒缓存无效报504
                request = request.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC).build();
                Response response = chain.proceed(request);
                return response.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC).build();
            }

        }
    };

    private Interceptor LoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();
            Log.d(TAG, String.format("Sending request %s on %s%n%s", request.url(),  chain.connection(), request.headers()));
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();
            Log.d(TAG, String.format("Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            return response;
        }
    };

}
