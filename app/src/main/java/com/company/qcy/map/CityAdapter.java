package com.company.qcy.map;

import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;

import java.util.List;

public class CityAdapter extends BaseQuickAdapter<CityBean.CitiesBean, BaseViewHolder> {

    public CityAdapter(int layoutResId, @Nullable List<CityBean.CitiesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CityBean.CitiesBean item) {
        TextView name = (TextView) helper.getView(R.id.item_city_info_cityname);
        name.setText(item.getAreaName());

        if(item.isCheck()){
            ((ConstraintLayout) helper.getView(R.id.item_city_info_layout)).setBackgroundColor(mContext.getResources().getColor(R.color.searchColor));
            name.setTextColor(mContext.getResources().getColor(R.color.chunhongse));
        }else {
            ((ConstraintLayout) helper.getView(R.id.item_city_info_layout)).setBackgroundColor(mContext.getResources().getColor(R.color.video_bg));
            name.setTextColor(mContext.getResources().getColor(R.color.putongwenben));
        }
    }
}
