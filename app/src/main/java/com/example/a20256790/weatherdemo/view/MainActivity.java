package com.example.a20256790.weatherdemo.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a20256790.weatherdemo.R;
import com.example.a20256790.weatherdemo.data.MessageEvent;
import com.example.a20256790.weatherdemo.data.bean.WeatherInfoEntity;
import com.example.a20256790.weatherdemo.presenter.Weatherpresenter;
import com.example.a20256790.weatherdemo.presenter.impl.WeatherPresenterImpl;
import com.example.a20256790.weatherdemo.util.ScreenUtil;
import com.example.a20256790.weatherdemo.widget.AirQualityView;
import com.example.a20256790.weatherdemo.widget.WeeklyWeatherView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements WeatherView, NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar tb_toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout ctl_toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout ab_appBar;
    @BindView(R.id.content_main)
    NestedScrollView nsv_contentMain;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.srl_weather)
    SwipeRefreshLayout srlWeather;
    @BindView(R.id.weekForecast)
    WeeklyWeatherView weekForecast;
    @BindView(R.id.tv_cityName)
    TextView tvCityName;
    @BindView(R.id.tv_temp)
    TextView tvTemp;
    @BindView(R.id.tv_cloud_type)
    TextView tvCloudType;
    @BindView(R.id.tv_update_time)
    TextView tvUpdateTime;
    @BindView(R.id.tv_maintemp)
    TextView tvMaintemp;
    @BindView(R.id.tv_aqi)
    TextView tvAqi;
    @BindView(R.id.tv_main_cloud_type)
    TextView tvMainCloudType;
    @BindView(R.id.tv_pm25)
    TextView tvPm25;
    @BindView(R.id.tv_pm10)
    TextView tvPm10;
    @BindView(R.id.aqi_progress_view)
    AirQualityView aqiProgressView;
    @BindView(R.id.rl_weather_content)
    RelativeLayout rlWeatherContent;
    @BindView(R.id.first_show_rl)
    RelativeLayout firstShowRl;
    @BindView(R.id.tv_aqi_time)
    TextView tvAqiTime;


    private Weatherpresenter presenter;
    private final String TAG = "zoky";
    private boolean isExpand = false;
    private final int DURASTION = 400;
    private final int ALPHA_LOW = 0;
    private final int ALPHA_HIGH = 1;


    private AnimatorSet animatorSetDown;
    private AnimatorSet animatorSetUP;
    //下滑时动画集合
    private ObjectAnimator animatorCloudAlphaUp;
    private ObjectAnimator animatoTemprAlphaUp;
    private ObjectAnimator animatorTranslateLeft;
    //上滑时动画集合
    private ObjectAnimator animatorTempAlphaDown;
    private ObjectAnimator animatorTranslateRight;
    private ObjectAnimator animatorCloudAlphaDown;
    private SharedPreferences sharedPreferences;
    private String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        presenter = new WeatherPresenterImpl(this);
        ButterKnife.bind(this);
        setSupportActionBar(tb_toolbar);
        EventBus.getDefault().register(this);

        animatorTranslateLeft = ObjectAnimator.ofFloat(tvCityName, "translationX", -ScreenUtil.getScreenWidth(getContext()) / 5).setDuration(DURASTION);
        animatorTranslateRight = ObjectAnimator.ofFloat(tvCityName, "translationX", 0).setDuration(DURASTION);
        animatorCloudAlphaDown = ObjectAnimator.ofFloat(tvCloudType, "alpha", ALPHA_HIGH, ALPHA_LOW).setDuration(DURASTION);
        animatorCloudAlphaUp = ObjectAnimator.ofFloat(tvCloudType, "alpha", ALPHA_LOW, ALPHA_HIGH).setDuration(DURASTION);
        animatorTempAlphaDown = ObjectAnimator.ofFloat(tvTemp, "alpha", ALPHA_HIGH, ALPHA_LOW).setDuration(DURASTION);
        animatoTemprAlphaUp = ObjectAnimator.ofFloat(tvTemp, "alpha", ALPHA_LOW, ALPHA_HIGH).setDuration(DURASTION);

        sharedPreferences = getSharedPreferences("weatherArea", Context.MODE_PRIVATE);
        cityName = sharedPreferences.getString("areaName", presenter.getLocationCity(manager));
        presenter.refershWeatherData(cityName);
        Log.d(TAG, "cityName:" + cityName);
        initEvent();
    }

    private void initEvent() {
        Intent intent = getIntent();
        if (intent != null) {

            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String area = bundle.getString("areaName");
                presenter.refershWeatherData(area);
            }

        }


        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, tb_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        srlWeather.setSize(SwipeRefreshLayout.LARGE);
        srlWeather.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light);
        srlWeather.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refershWeatherData(cityName);
                srlWeather.setRefreshing(false);
            }
        });
        navView.setNavigationItemSelectedListener(this);

        nsv_contentMain.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            int preY = 0;

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > preY && !isExpand && scrollY > 300) {
                    Log.d(TAG, "展开动画,当前scrollY：" + scrollY);
                    animatorSetDown = new AnimatorSet();
                    animatorSetDown.play(animatorCloudAlphaUp).with(animatoTemprAlphaUp).after(animatorTranslateLeft);
                    animatorSetDown.start();
                    isExpand = true;
                } else if (scrollY < preY && isExpand && scrollY < 300) {
                    Log.d(TAG, "收紧动画,当前scrollY：" + scrollY);
                    animatorSetUP = new AnimatorSet();
                    animatorSetUP.play(animatorTempAlphaDown).with(animatorCloudAlphaDown).before(animatorTranslateRight);
                    animatorSetUP.start();
                    isExpand = false;
                }
                preY = scrollY;
            }
        });

    }

    @NonNull
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_city_choose) {
            Intent intent = new Intent(this, CityChooseActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 设置通知栏数据
     * @param data
     */
    @Override
    public void setTitle(WeatherInfoEntity data) {
        tvCityName.setText(data.getCity());
        tvCloudType.setText(data.getRealtime().getWeather());
        tvTemp.setText(data.getRealtime().getTemp() + " °C");
    }


    @Override
    public void showWeatherData(WeatherInfoEntity data) {
        Log.d(TAG, data.getWeathers().get(0).getTemp_day_c() + "");
        weekForecast.setWeathers(data.getWeathers());
        setTitle(data);
        setMainWeatherInfo(data);
        setAqiInfo(data);
    }

    /**
     * 设置主页面上方数据
     * @param data
     */
    @Override
    public void setMainWeatherInfo(WeatherInfoEntity data) {
        tvMaintemp.setText(data.getRealtime().getTemp());
        tvMainCloudType.setText(data.getRealtime().getWeather());
        tvAqi.setText("空气：" + data.getPm25().getQuality() + " " + data.getPm25().getAqi());
        tvUpdateTime.setText(data.getRealtime().getTime());

    }

    /**
     * 设置空气质量数据
     * @param data
     */
    @Override
    public void setAqiInfo(WeatherInfoEntity data) {
        tvPm10.setText(data.getPm25().getPm10());
        tvPm25.setText(data.getPm25().getPm25());
        tvAqiTime.setText(data.getPm25().getUpDateTime());
        aqiProgressView.setProgressAndLabel(Integer.parseInt(data.getPm25().getAqi()), data.getPm25().getQuality());
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 接受城市选择列表Item点击后传递的事件，更新天气信息，但此方法未被调用，待解决
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        Log.d(TAG, "receive message" + event.message);
        presenter.refershWeatherData(event.message);
        sharedPreferences = getSharedPreferences("weatherArea", Context.MODE_PRIVATE);
        cityName = sharedPreferences.getString("areaName",cityName);
        presenter.refershWeatherData(cityName);
    }


}
