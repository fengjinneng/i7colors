package com.company.qcy.adapter.kaifangshangcheng;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.bean.kaifangshangcheng.DianpuxiangqingBean;
import com.company.qcy.bean.kaifangshangcheng.ProductBean;

import java.util.List;

public class KFSCXiangqingRecyclerviewAdapter extends
        BaseQuickAdapter<ProductBean, BaseViewHolder> {
    public KFSCXiangqingRecyclerviewAdapter(int layoutResId, @Nullable List<ProductBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductBean item) {
    }
}
