package com.company.qcy.huodong.caigoulianmeng.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.huodong.caigoulianmeng2.bean.HuodanBean;

import java.util.List;

public class WodedinghuodanDetailAdapter extends BaseQuickAdapter<HuodanBean.MeetingShopBean, BaseViewHolder> {


    public WodedinghuodanDetailAdapter(int layoutResId, @Nullable List<HuodanBean.MeetingShopBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HuodanBean.MeetingShopBean item) {

        helper.setText(R.id.item_wodeyonghuodan_title,item.getShopName());
        helper.setText(R.id.item_wodeyonghuodan_guige,item.getPacking());
        helper.setText(R.id.item_wodeyonghuodan_zhongliang_unit,item.getNumUnit());
        helper.setText(R.id.item_wodeyonghuodan_number,item.getReservationNum());


        RecyclerView layout = (RecyclerView) helper.getView(R.id.item_wodeyonghuodan_layout);

        CankaobiaozhunAdapter adapter = new CankaobiaozhunAdapter(R.layout.item_wodehuodan_cankaobiaozhun,item.getMeetingTypeList());

        layout.setAdapter(adapter);


    }
}
