package com.example.a20256790.weatherdemo.data.bean;

import java.util.List;

/**
 * Created by zoky on 2017/3/5.
 * 天气数据主Bean
 */

public class WeatherInfoEntity {

    private String city;
    private int cityid;
    private Pm25Bean pm25;
    private String provinceName;
    private RealtimeBean realtime;
    private WeatherDetailsInfoBean weatherDetailsInfo;
    private List<?> alarms;
    private List<IndexesBean> indexes;
    private List<WeathersBean> weathers;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCityid() {
        return cityid;
    }

    public void setCityid(int cityid) {
        this.cityid = cityid;
    }

    public Pm25Bean getPm25() {
        return pm25;
    }

    public void setPm25(Pm25Bean pm25) {
        this.pm25 = pm25;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public RealtimeBean getRealtime() {
        return realtime;
    }

    public void setRealtime(RealtimeBean realtime) {
        this.realtime = realtime;
    }

    public WeatherDetailsInfoBean getWeatherDetailsInfo() {
        return weatherDetailsInfo;
    }

    public void setWeatherDetailsInfo(WeatherDetailsInfoBean weatherDetailsInfo) {
        this.weatherDetailsInfo = weatherDetailsInfo;
    }

    public List<?> getAlarms() {
        return alarms;
    }

    public void setAlarms(List<?> alarms) {
        this.alarms = alarms;
    }

    public List<IndexesBean> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<IndexesBean> indexes) {
        this.indexes = indexes;
    }

    public List<WeathersBean> getWeathers() {
        return weathers;
    }

    public void setWeathers(List<WeathersBean> weathers) {
        this.weathers = weathers;
    }

    @Override
    public String toString() {
        return "WeatherInfoEntity{" +
                "city='" + city + '\'' +
                ", cityid=" + cityid +
                ", pm25=" + pm25.toString() +
                ", provinceName='" + provinceName + '\'' +
                ", realtime=" + realtime.toString() +
                ", weatherDetailsInfo=" + weatherDetailsInfo.toString() +
                ", alarms=" + alarms.toString() +
                ", indexes=" + indexes.toString() +
                ", weathers=" + weathers.toString() +
                '}';
    }

    public static class Pm25Bean {
        @Override
        public String toString() {
            return "Pm25Bean{" +
                    "aqi='" + aqi + '\'' +
                    ", pm10='" + pm10 + '\'' +
                    ", pm25='" + pm25 + '\'' +
                    ", quality='" + quality + '\'' +
                    ", upDateTime='" + upDateTime + '\'' +
                    '}';
        }

        /**
         * advice :
         * aqi : 55
         * citycount : 1848
         * cityrank : 66
         * co : 0.0
         * color :
         * level : 0
         * no2 : 0
         * o3 : 0
         * pm10 : 35
         * pm25 : 39
         * quality : 良
         * so2 : 0
         * timestamp : 0
         * upDateTime : 2017-03-05 15:00:00
         */

        private String aqi;
        private String pm10;
        private String pm25;
        private String quality;
        private String upDateTime;

        public String getAqi() {
            return aqi;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public String getPm10() {
            return pm10;
        }

        public void setPm10(String pm10) {
            this.pm10 = pm10;
        }

        public String getPm25() {
            return pm25;
        }

        public void setPm25(String pm25) {
            this.pm25 = pm25;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }

        public String getUpDateTime() {
            return upDateTime;
        }

        public void setUpDateTime(String upDateTime) {
            this.upDateTime = upDateTime;
        }
    }

    public static class RealtimeBean {
        @Override
        public String toString() {
            return "RealtimeBean{" +
                    "sD='" + sD + '\'' +
                    ", temp='" + temp + '\'' +
                    ", time='" + time + '\'' +
                    ", wD='" + wD + '\'' +
                    ", wS='" + wS + '\'' +
                    ", weather='" + weather + '\'' +
                    '}';
        }

        /**
         * img : 1
         * sD : 72
         * sendibleTemp : 24
         * temp : 24
         * time : 2017-03-05 15:00:00
         * wD : 南风
         * wS : 2级
         * weather : 多云
         * ziwaixian : N/A
         */

        private String sD;
        private String temp;
        private String time;
        private String wD;
        private String wS;
        private String weather;

        public String getSD() {
            return sD;
        }

        public void setSD(String sD) {
            this.sD = sD;
        }

        public String getTemp() {
            return temp;
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getWD() {
            return wD;
        }

        public void setWD(String wD) {
            this.wD = wD;
        }

        public String getWS() {
            return wS;
        }

        public void setWS(String wS) {
            this.wS = wS;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }
    }

    public static class WeatherDetailsInfoBean {
        /**
         * publishTime : 2017-03-05 16:00:00
         * weather24HoursDetailsInfos : [{"endTime":"2017-03-05 18:00:00","highestTemperature":"25","img":"1","isRainFall":"无降水","lowerestTemperature":"25","precipitation":"0","startTime":"2017-03-05 17:00:00","wd":"","weather":"多云","ws":""},{"endTime":"2017-03-05 19:00:00","highestTemperature":"23","img":"1","isRainFall":"无降水","lowerestTemperature":"23","precipitation":"0","startTime":"2017-03-05 18:00:00","wd":"","weather":"多云","ws":""},{"endTime":"2017-03-05 20:00:00","highestTemperature":"22","img":"1","isRainFall":"无降水","lowerestTemperature":"22","precipitation":"0","startTime":"2017-03-05 19:00:00","wd":"","weather":"多云","ws":""},{"endTime":"2017-03-05 21:00:00","highestTemperature":"22","img":"1","isRainFall":"无降水","lowerestTemperature":"22","precipitation":"0","startTime":"2017-03-05 20:00:00","wd":"","weather":"多云","ws":""},{"endTime":"2017-03-05 22:00:00","highestTemperature":"21","img":"1","isRainFall":"无降水","lowerestTemperature":"21","precipitation":"0","startTime":"2017-03-05 21:00:00","wd":"","weather":"多云","ws":""},{"endTime":"2017-03-05 23:00:00","highestTemperature":"20","img":"1","isRainFall":"无降水","lowerestTemperature":"20","precipitation":"0","startTime":"2017-03-05 22:00:00","wd":"","weather":"多云","ws":""},{"endTime":"2017-03-06 00:00:00","highestTemperature":"20","img":"0","isRainFall":"无降水","lowerestTemperature":"20","precipitation":"0","startTime":"2017-03-05 23:00:00","wd":"","weather":"晴","ws":""},{"endTime":"2017-03-06 01:00:00","highestTemperature":"19","img":"1","isRainFall":"无降水","lowerestTemperature":"19","precipitation":"0","startTime":"2017-03-06 00:00:00","wd":"","weather":"多云","ws":""},{"endTime":"2017-03-06 02:00:00","highestTemperature":"19","img":"1","isRainFall":"无降水","lowerestTemperature":"19","precipitation":"0","startTime":"2017-03-06 01:00:00","wd":"","weather":"多云","ws":""},{"endTime":"2017-03-06 03:00:00","highestTemperature":"18","img":"1","isRainFall":"无降水","lowerestTemperature":"18","precipitation":"0","startTime":"2017-03-06 02:00:00","wd":"","weather":"多云","ws":""},{"endTime":"2017-03-06 04:00:00","highestTemperature":"18","img":"1","isRainFall":"无降水","lowerestTemperature":"18","precipitation":"0","startTime":"2017-03-06 03:00:00","wd":"","weather":"多云","ws":""},{"endTime":"2017-03-06 05:00:00","highestTemperature":"18","img":"1","isRainFall":"无降水","lowerestTemperature":"18","precipitation":"0","startTime":"2017-03-06 04:00:00","wd":"","weather":"多云","ws":""},{"endTime":"2017-03-06 06:00:00","highestTemperature":"18","img":"2","isRainFall":"无降水","lowerestTemperature":"18","precipitation":"0","startTime":"2017-03-06 05:00:00","wd":"","weather":"阴","ws":""},{"endTime":"2017-03-06 07:00:00","highestTemperature":"18","img":"2","isRainFall":"无降水","lowerestTemperature":"18","precipitation":"0","startTime":"2017-03-06 06:00:00","wd":"","weather":"阴","ws":""},{"endTime":"2017-03-06 08:00:00","highestTemperature":"18","img":"2","isRainFall":"无降水","lowerestTemperature":"18","precipitation":"0","startTime":"2017-03-06 07:00:00","wd":"","weather":"阴","ws":""},{"endTime":"2017-03-06 09:00:00","highestTemperature":"18","img":"2","isRainFall":"无降水","lowerestTemperature":"18","precipitation":"0","startTime":"2017-03-06 08:00:00","wd":"","weather":"阴","ws":""},{"endTime":"2017-03-06 10:00:00","highestTemperature":"18","img":"2","isRainFall":"无降水","lowerestTemperature":"18","precipitation":"0","startTime":"2017-03-06 09:00:00","wd":"","weather":"阴","ws":""},{"endTime":"2017-03-06 11:00:00","highestTemperature":"19","img":"2","isRainFall":"无降水","lowerestTemperature":"19","precipitation":"0","startTime":"2017-03-06 10:00:00","wd":"","weather":"阴","ws":""},{"endTime":"2017-03-06 12:00:00","highestTemperature":"20","img":"2","isRainFall":"无降水","lowerestTemperature":"20","precipitation":"0","startTime":"2017-03-06 11:00:00","wd":"","weather":"阴","ws":""},{"endTime":"2017-03-06 13:00:00","highestTemperature":"21","img":"2","isRainFall":"无降水","lowerestTemperature":"21","precipitation":"0","startTime":"2017-03-06 12:00:00","wd":"","weather":"阴","ws":""},{"endTime":"2017-03-06 14:00:00","highestTemperature":"22","img":"2","isRainFall":"无降水","lowerestTemperature":"22","precipitation":"0","startTime":"2017-03-06 13:00:00","wd":"","weather":"阴","ws":""},{"endTime":"2017-03-06 15:00:00","highestTemperature":"22","img":"2","isRainFall":"无降水","lowerestTemperature":"22","precipitation":"0","startTime":"2017-03-06 14:00:00","wd":"","weather":"阴","ws":""},{"endTime":"2017-03-06 16:00:00","highestTemperature":"23","img":"2","isRainFall":"无降水","lowerestTemperature":"23","precipitation":"0","startTime":"2017-03-06 15:00:00","wd":"","weather":"阴","ws":""},{"endTime":"2017-03-06 17:00:00","highestTemperature":"23","img":"1","isRainFall":"无降水","lowerestTemperature":"23","precipitation":"0","startTime":"2017-03-06 16:00:00","wd":"","weather":"多云","ws":""},{"endTime":"2017-03-06 18:00:00","highestTemperature":"22","img":"1","isRainFall":"无降水","lowerestTemperature":"22","precipitation":"0","startTime":"2017-03-06 17:00:00","wd":"","weather":"多云","ws":""}]
         */

        private String publishTime;
        private List<Weather24HoursDetailsInfosBean> weather24HoursDetailsInfos;

        public String getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
        }

        public List<Weather24HoursDetailsInfosBean> getWeather24HoursDetailsInfos() {
            return weather24HoursDetailsInfos;
        }

        public void setWeather24HoursDetailsInfos(List<Weather24HoursDetailsInfosBean> weather24HoursDetailsInfos) {
            this.weather24HoursDetailsInfos = weather24HoursDetailsInfos;
        }

        public static class Weather24HoursDetailsInfosBean {
            @Override
            public String toString() {
                return "Weather24HoursDetailsInfosBean{" +
                        "endTime='" + endTime + '\'' +
                        ", lowerestTemperature='" + lowerestTemperature + '\'' +
                        ", startTime='" + startTime + '\'' +
                        ", weather='" + weather + '\'' +
                        '}';
            }

            /**
             * endTime : 2017-03-05 18:00:00
             * highestTemperature : 25
             * img : 1
             * isRainFall : 无降水
             * lowerestTemperature : 25
             * precipitation : 0
             * startTime : 2017-03-05 17:00:00
             * wd :
             * weather : 多云
             * ws :
             */

            private String endTime;
            private String lowerestTemperature;
            private String startTime;
            private String weather;

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public String getLowerestTemperature() {
                return lowerestTemperature;
            }

            public void setLowerestTemperature(String lowerestTemperature) {
                this.lowerestTemperature = lowerestTemperature;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }
        }
    }

    public static class IndexesBean {
        @Override
        public String toString() {
            return "IndexesBean{" +
                    "abbreviation='" + abbreviation + '\'' +
                    ", content='" + content + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }

        /**
         * abbreviation : zs
         * alias :
         * content : 温度不高，其他各项气象条件适宜，中暑机率极低。
         * level : 无
         * name : 中暑指数
         */

        private String abbreviation;
        private String content;
        private String name;

        public String getAbbreviation() {
            return abbreviation;
        }

        public void setAbbreviation(String abbreviation) {
            this.abbreviation = abbreviation;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class WeathersBean {
        @Override
        public String toString() {
            return "WeathersBean{" +
                    "date='" + date + '\'' +
                    ", img='" + img + '\'' +
                    ", sun_down_time='" + sun_down_time + '\'' +
                    ", sun_rise_time='" + sun_rise_time + '\'' +
                    ", temp_day_c='" + temp_day_c + '\'' +
                    ", temp_night_c='" + temp_night_c + '\'' +
                    ", weather='" + weather + '\'' +
                    ", week='" + week + '\'' +
                    '}';
        }

        /**
         * date : 2017-03-05
         * img : 2
         * sun_down_time : 18:30
         * sun_rise_time : 06:42
         * temp_day_c : 26
         * temp_day_f : 78.8
         * temp_night_c : 18
         * temp_night_f : 64.4
         * wd : 无持续风向
         * weather : 阴
         * week : 星期日
         * ws : 0级
         */

        private String date;
        private String img;
        private String sun_down_time;
        private String sun_rise_time;
        private int temp_day_c;
        private int temp_night_c;
        private String weather;
        private String week;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getSun_down_time() {
            return sun_down_time;
        }

        public void setSun_down_time(String sun_down_time) {
            this.sun_down_time = sun_down_time;
        }

        public String getSun_rise_time() {
            return sun_rise_time;
        }

        public void setSun_rise_time(String sun_rise_time) {
            this.sun_rise_time = sun_rise_time;
        }

        public int getTemp_day_c() {
            return temp_day_c;
        }

        public void setTemp_day_c(int temp_day_c) {
            this.temp_day_c = temp_day_c;
        }

        public int getTemp_night_c() {
            return temp_night_c;
        }

        public void setTemp_night_c(int temp_night_c) {
            this.temp_night_c = temp_night_c;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }
    }
}
