package com.company.qcy.adapter.pengyouquan;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.bean.pengyouquan.DianzantouxiangBean;

import java.util.List;

public class DianzantouxiangAdapter extends BaseQuickAdapter<DianzantouxiangBean, BaseViewHolder> {


    public DianzantouxiangAdapter(int layoutResId, @Nullable List<DianzantouxiangBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DianzantouxiangBean item) {

    }
}
