package com.company.qcy.huodong.youhuizhanxiao.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.huodong.youhuizhanxiao.bean.YouhuizhanxiaoBean;

import java.util.List;

public class YouhuizhanxiaoCanshuAdapter extends BaseQuickAdapter<YouhuizhanxiaoBean.ListSaleBean, BaseViewHolder> {



    public YouhuizhanxiaoCanshuAdapter(int layoutResId, @Nullable List<YouhuizhanxiaoBean.ListSaleBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, YouhuizhanxiaoBean.ListSaleBean item) {

        helper.setText(R.id.item_huodong_canshu_shuxing,item.getShuXing());
        helper.setText(R.id.item_huodong_canshu_zhi,item.getZhi());


    }
}
