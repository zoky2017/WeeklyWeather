package com.example.a20256790.weatherdemo.view;

import android.content.Context;

import com.example.a20256790.weatherdemo.data.bean.CityInfoEntity;

import java.util.List;

/**
 * Created by zoky on 2017/3/12.
 */

public interface CityChoose {
    void showSeachData(List<CityInfoEntity> data);
    Context getContext();

}
