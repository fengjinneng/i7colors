package com.company.qcy.adapter.chanpindating;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.bean.kaifangshangcheng.ProductBean;

import java.util.List;

public class ChanpinCanshuRecyclerviewAdapter extends
        BaseQuickAdapter<ProductBean.PropMapBean, BaseViewHolder> {
    public ChanpinCanshuRecyclerviewAdapter(int layoutResId, @Nullable List<ProductBean.PropMapBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductBean.PropMapBean item) {
        helper.setText(R.id.item_chanpincanshu_first, item.getKey());
        helper.setText(R.id.item_chanpincanshu_second, item.getValue());
    }
}
