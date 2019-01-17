package com.company.qcy.huodong.caigoulianmeng;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class CaigoulianmengAdapter   extends BaseQuickAdapter<CaigoulianmengBean, BaseViewHolder> {

    public CaigoulianmengAdapter(int layoutResId, @Nullable List<CaigoulianmengBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CaigoulianmengBean item) {

    }
}
