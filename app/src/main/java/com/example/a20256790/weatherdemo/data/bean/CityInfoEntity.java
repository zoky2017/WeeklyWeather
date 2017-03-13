package com.example.a20256790.weatherdemo.data.bean;

/**
 * Created by zoky on 2017/3/12.
 */

public class CityInfoEntity {

    private String city;
    private String province;
    private String area;
    private String weatherId;

    public CityInfoEntity(String area, String city, String province, String weatherId){
        this.city = city;
        this.province = province;
        this.area = area;
        this.weatherId = weatherId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }
}
