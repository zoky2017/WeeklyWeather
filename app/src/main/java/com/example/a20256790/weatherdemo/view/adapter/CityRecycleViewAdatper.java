package com.example.a20256790.weatherdemo.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a20256790.weatherdemo.R;
import com.example.a20256790.weatherdemo.data.bean.CityInfoEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zoky on 2017/3/12.
 */

public class CityRecycleViewAdatper extends RecyclerView.Adapter<CityRecycleViewAdatper.CityViewHolder> {

    private Context mContext;
    private OnItemClickLitener mOnItemClickLitener;



    private List<CityInfoEntity> datas;
    public CityRecycleViewAdatper(Context context, List<CityInfoEntity> datas) {
        mContext = context;
        this.datas = datas;

    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CityViewHolder holder = new CityViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_search_location, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final CityViewHolder holder, final int position) {
        holder.tvArea.setText(datas.get(position).getArea());
        holder.tvCity.setText(datas.get(position).getCity());
        holder.tvProvince.setText(datas.get(position).getProvince());

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class CityViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_area)
        TextView tvArea;
        @BindView(R.id.tv_city)
        TextView tvCity;
        @BindView(R.id.tv_province)
        TextView tvProvince;
        public CityViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    public void setDatas(List<CityInfoEntity> datas) {
        this.datas = datas;
    }



}
