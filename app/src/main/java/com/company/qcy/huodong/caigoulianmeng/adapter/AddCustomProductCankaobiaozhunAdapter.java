package com.company.qcy.huodong.caigoulianmeng.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.huodong.caigoulianmeng.bean.HHHBean;

import java.util.List;

public class AddCustomProductCankaobiaozhunAdapter extends BaseQuickAdapter<HHHBean.MeetingListBean.DataBean.MeetingTypeListBean, BaseViewHolder> {


    public AddCustomProductCankaobiaozhunAdapter(int layoutResId, @Nullable List<HHHBean.MeetingListBean.DataBean.MeetingTypeListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HHHBean.MeetingListBean.DataBean.MeetingTypeListBean item) {


        helper.setText(R.id.item_wodehuodan_cankaobianzhun_text,item.getReferenceType());
        helper.addOnClickListener(R.id.item_wodehuodan_cankaobianzhun_reduce);

    }
}
