package com.example.a20256790.weatherdemo.presenter.impl;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.a20256790.weatherdemo.data.bean.CityInfoEntity;
import com.example.a20256790.weatherdemo.presenter.CityChoosePresenter;
import com.example.a20256790.weatherdemo.presenter.DBManager;
import com.example.a20256790.weatherdemo.view.CityChoose;

import java.util.List;

/**
 * Created by 20256790 on 2017/3/12.
 */

public class CityChoosePresenterImpl implements CityChoosePresenter {
    private final SQLiteDatabase sqLite;
    private CityChoose cityChooseView;
    private DBManager manager;
    private final String TAG = "zoky";

    public CityChoosePresenterImpl(CityChoose chooseView){
        cityChooseView = chooseView;
        manager = new DBManager(cityChooseView.getContext());
        sqLite = manager.initManager(cityChooseView.getContext().getPackageName());
    }


    @Override
    public void getCityInfo(String seachWord) {


        List<CityInfoEntity> citys = manager.queryCity(sqLite, seachWord);
        cityChooseView.showSeachData(citys);
        Log.d(TAG, "get seach city count:"+ citys.size());
    }
}
