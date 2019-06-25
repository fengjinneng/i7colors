package com.company.qcy.adapter.chanpindating;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.bean.chanpin.ChanpinTypeBean;

import java.util.List;

public class ChanpinshaixuanAdapter extends BaseQuickAdapter<ChanpinTypeBean,BaseViewHolder> {

    public ChanpinshaixuanAdapter(int layoutResId, @Nullable List<ChanpinTypeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChanpinTypeBean item) {

    }
}
