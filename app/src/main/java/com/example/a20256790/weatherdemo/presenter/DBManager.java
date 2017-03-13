package com.example.a20256790.weatherdemo.presenter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.a20256790.weatherdemo.data.bean.CityInfoEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zoky on 2017/3/12.
 */

public class DBManager {
    private String DB_NAME = "/location.db";
    private Context mContext;

    public DBManager(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 把assets目录下的db文件复制到apk安装目录下
     * @param packName
     * @return
     */
    public SQLiteDatabase initManager(String packName) {
        String dbPath = mContext.getFilesDir()+  DB_NAME;
        if (!new File(dbPath).exists()) {
            try {
                FileOutputStream out = new FileOutputStream(dbPath);
                InputStream in = mContext.getAssets().open("location.db");
                byte[] buffer = new byte[1024];
                int readBytes = 0;
                while ((readBytes = in.read(buffer)) != -1)
                    out.write(buffer, 0, readBytes);
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return SQLiteDatabase.openOrCreateDatabase(dbPath, null);
    }

    /***
     *  按关键字查询数据库，获取包含关键字的城市列表
     * @param sqliteDB
     * @param seachWord
     * @return
     */
    public List<CityInfoEntity> queryCity(SQLiteDatabase sqliteDB, String seachWord) {

        String[] columns = new String[]{"weather_id", "area_name", "city_name", "province_name"};
        String selection = "area_name like ?";
        CityInfoEntity city;
        Cursor cursor = null;
        List<CityInfoEntity> list = new ArrayList<>();
        if (seachWord == null){
            selection = null;
        }else {
            seachWord = "%"+seachWord+"%";
        }
        try {
            String table = "weathers";
            cursor = sqliteDB.query(table, columns, selection, new String[]{seachWord}, null, null, null);
            while (cursor.moveToNext()) {

                String cityName = cursor.getString(cursor.getColumnIndex("city_name"));
                String areaName = cursor.getString(cursor.getColumnIndex("area_name"));
                String provinceName = cursor.getString(cursor.getColumnIndex("province_name"));
                String weatherId = cursor.getString(cursor.getColumnIndex("weather_id"));
                city = new CityInfoEntity(areaName, cityName, provinceName, weatherId);
                list.add(city);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (cursor != null)
                cursor.close();

        }

        return list;
    }
}