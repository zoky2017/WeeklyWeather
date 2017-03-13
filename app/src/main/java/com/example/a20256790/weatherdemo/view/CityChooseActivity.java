package com.example.a20256790.weatherdemo.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a20256790.weatherdemo.R;
import com.example.a20256790.weatherdemo.data.MessageEvent;
import com.example.a20256790.weatherdemo.data.bean.CityInfoEntity;
import com.example.a20256790.weatherdemo.presenter.CityChoosePresenter;
import com.example.a20256790.weatherdemo.presenter.impl.CityChoosePresenterImpl;
import com.example.a20256790.weatherdemo.view.adapter.CityRecycleViewAdatper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CityChooseActivity extends AppCompatActivity implements CityChoose {
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.rv_location_search)
    RecyclerView rvLocationSearch;

    private CityChoosePresenter presenter;
    private String seachWord = null;
    private boolean isFirstShowData = true;
    CityRecycleViewAdatper adapter;
    private List<CityInfoEntity> currentData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_choose);
        ButterKnife.bind(this);
        presenter = new CityChoosePresenterImpl(this);
        presenter.getCityInfo(etSearch.getText().toString());
        initEvent();

    }

    private void initEvent() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                seachWord = s.toString();
                presenter.getCityInfo(seachWord);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public void showSeachData(final List<CityInfoEntity> data) {
        currentData = data;
        if (isFirstShowData){
            adapter = new CityRecycleViewAdatper(this, data);
            adapter.setOnItemClickLitener(new CityRecycleViewAdatper.OnItemClickLitener()
            {

                @Override
                public void onItemClick(View view, int position)
                {
                    String area = currentData.get(position).getArea();
                    Log.d("zoky", "post message:" + area);
                    EventBus.getDefault().post(area);
                    Intent intent = new Intent(CityChooseActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("areaName", area);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    SharedPreferences sp = getContext().getSharedPreferences("weatherArea", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("areaName", area);
                    editor.commit();

                    finish();

                }



            });
            rvLocationSearch.setAdapter(adapter);
            rvLocationSearch.setLayoutManager(new LinearLayoutManager(this));
            isFirstShowData = false;
        }
        adapter.setDatas(currentData);
        adapter.notifyDataSetChanged();


    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(MessageEvent event) {
        finish();
    }
}
