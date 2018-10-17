package com.company.qcy.adapter.other;


import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.bean.ChanpindatingBean;

import java.util.List;

public class QiugouxiangqingRecyclerviewAdapter
        extends BaseQuickAdapter<ChanpindatingBean, BaseViewHolder> {
    public QiugouxiangqingRecyclerviewAdapter(int layoutResId, @Nullable List<ChanpindatingBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChanpindatingBean item) {
    }
}