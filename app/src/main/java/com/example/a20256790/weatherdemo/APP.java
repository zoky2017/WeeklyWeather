package com.example.a20256790.weatherdemo;

import android.app.Application;

import com.antfortune.freeline.FreelineCore;

/**
 * Created by zoky on 2017/3/3.
 */
public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FreelineCore.init(this);
    }
}
