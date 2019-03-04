package com.company.qcy.huodong.caigoulianmeng2.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.huodong.caigoulianmeng2.bean.ProductBean;

import java.util.List;

public class CankaobiaozhunAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public CankaobiaozhunAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        helper.setText(R.id.item_wodehuodan_cankaobianzhun_text,item);
        helper.addOnClickListener(R.id.item_wodehuodan_cankaobianzhun_reduce);

    }
}
