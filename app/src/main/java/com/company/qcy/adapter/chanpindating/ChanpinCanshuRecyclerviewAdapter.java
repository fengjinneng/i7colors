package com.company.qcy.adapter.chanpindating;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.bean.chanpin.ProductBean;
import com.company.qcy.bean.chanpin.PropBean;

import java.util.List;

public class ChanpinCanshuRecyclerviewAdapter extends
        BaseQuickAdapter<PropBean, BaseViewHolder> {


    public ChanpinCanshuRecyclerviewAdapter(int layoutResId, @Nullable List<PropBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PropBean item) {
        helper.setText(R.id.item_chanpincanshu_first, item.getKey());
        helper.setText(R.id.item_chanpincanshu_second, item.getValue());
    }
}
