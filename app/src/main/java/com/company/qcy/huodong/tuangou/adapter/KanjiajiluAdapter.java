package com.company.qcy.huodong.tuangou.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.huodong.tuangou.bean.KanjiajiluBean;

import java.util.List;

public class KanjiajiluAdapter extends BaseQuickAdapter<KanjiajiluBean
        , BaseViewHolder> {



    public KanjiajiluAdapter(int layoutResId, @Nullable List<KanjiajiluBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, KanjiajiluBean item) {

    }
}
