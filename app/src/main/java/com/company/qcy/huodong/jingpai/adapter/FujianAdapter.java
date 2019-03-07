package com.company.qcy.huodong.jingpai.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.huodong.jingpai.bean.JingpaiDetailBean;

import java.util.List;

public class FujianAdapter extends BaseQuickAdapter<JingpaiDetailBean.AuctionAttachesBean, BaseViewHolder> {



    public FujianAdapter(int layoutResId, @Nullable List<JingpaiDetailBean.AuctionAttachesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, JingpaiDetailBean.AuctionAttachesBean item) {
        helper.setText(R.id.item_jingpai_fujian_tv,item.getAttachName());
    }
}
