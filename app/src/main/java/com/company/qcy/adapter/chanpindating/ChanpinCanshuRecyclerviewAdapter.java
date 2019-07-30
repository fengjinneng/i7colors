package com.company.qcy.adapter.chanpindating;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.bean.chanpin.ProductBean;
import com.company.qcy.bean.chanpin.PropBean;

import java.util.List;

public class ChanpinCanshuRecyclerviewAdapter extends
        BaseQuickAdapter<ProductBean.PropMap, BaseViewHolder> {


    public ChanpinCanshuRecyclerviewAdapter(int layoutResId, @Nullable List<ProductBean.PropMap> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductBean.PropMap item) {
        helper.setText(R.id.item_chanpincanshu_first, item.getKey());
        helper.setText(R.id.item_chanpincanshu_second, item.getValue());
    }
}
