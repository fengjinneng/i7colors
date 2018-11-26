package com.company.qcy.adapter.pengyouquan;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.bean.pengyouquan.PengyouquanBean;

import java.util.List;

public class PinglunliebiaoAdapter extends BaseQuickAdapter<PengyouquanBean.CommentListBean, BaseViewHolder> {


    public PinglunliebiaoAdapter(int layoutResId, @Nullable List<PengyouquanBean.CommentListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PengyouquanBean.CommentListBean item) {

    }
}
