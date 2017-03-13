package com.example.a20256790.weatherdemo.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zoky on 2017/3/2.
 */
public class DateTimeUtil {

    /**
     * 判断是否是昨天
     * @param day
     * @return
     * @throws ParseException
     */
    public static boolean isYesterday(String day){
        Date date = null;
        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == -1) {
                return true;
            }
        }
        return false;
    }







}
