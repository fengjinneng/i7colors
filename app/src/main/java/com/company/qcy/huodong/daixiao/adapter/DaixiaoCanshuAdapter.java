package com.company.qcy.huodong.daixiao.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.huodong.daixiao.bean.DaixiaoBean;

import java.util.List;

public class DaixiaoCanshuAdapter extends BaseQuickAdapter<DaixiaoBean.DictMapBean
        , BaseViewHolder> {


    public DaixiaoCanshuAdapter(int layoutResId, @Nullable List<DaixiaoBean.DictMapBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DaixiaoBean.DictMapBean item) {


        helper.setText(R.id.item_daixiao_canshu_key,item.getKey());
        helper.setText(R.id.item_daixiao_canshu_value,item.getValue());


    }
}
