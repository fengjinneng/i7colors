package com.company.qcy.huodong.jingpai.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.huodong.jingpai.bean.JingpaiDetailBean;

import java.util.List;

public class JibencanshuAdapter extends BaseQuickAdapter<JingpaiDetailBean.AttributeListBean
        , BaseViewHolder> {




    public JibencanshuAdapter(int layoutResId, @Nullable List<JingpaiDetailBean.AttributeListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, JingpaiDetailBean.AttributeListBean item) {

        helper.setText(R.id.item_jingpai_paipinmiaoshu_key,item.getShuXing());
        helper.setText(R.id.item_jingpai_paipinmiaoshu_value,item.getZhi());

    }
}
