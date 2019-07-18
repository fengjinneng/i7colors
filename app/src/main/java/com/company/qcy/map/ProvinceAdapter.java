package com.company.qcy.map;

import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;

import java.util.List;

public class ProvinceAdapter extends BaseQuickAdapter<CityBean, BaseViewHolder> {


    public ProvinceAdapter(int layoutResId, @Nullable List<CityBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CityBean item) {

        TextView name = (TextView) helper.getView(R.id.item_city_info_cityname);
        TextView shuxian = (TextView) helper.getView(R.id.item_city_info_shuxian);

        name.setText(item.getAreaName());

        if (item.isCheck()) {
            ((ConstraintLayout) helper.getView(R.id.item_city_info_layout)).setBackgroundColor(mContext.getResources().getColor(R.color.video_bg));
            name.setTextColor(mContext.getResources().getColor(R.color.chunhongse));
            shuxian.setVisibility(View.VISIBLE);
        } else {
            ((ConstraintLayout) helper.getView(R.id.item_city_info_layout)).setBackgroundColor(mContext.getResources().getColor(R.color.fengexian));
            shuxian.setVisibility(View.GONE);
            name.setTextColor(mContext.getResources().getColor(R.color.putongwenben));

        }

    }
}
