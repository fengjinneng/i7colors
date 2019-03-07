package com.company.qcy.huodong.tuangou.adapter;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.huodong.tuangou.bean.TuangouRecordBean;

import java.util.List;

public class TuangoujiluAdapter extends BaseQuickAdapter<TuangouRecordBean, BaseViewHolder> {


    public TuangoujiluAdapter(int layoutResId, @Nullable List<TuangouRecordBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TuangouRecordBean item) {


        helper.setText(R.id.item_tugoujiu_number, item.getNumber());
        if (!StringUtils.isEmpty(item.getProvince())) {

            helper.setText(R.id.item_tugoujiu_companyname, item.getProvince() + "***公司");

        } else helper.setText(R.id.item_tugoujiu_companyname, "***公司");
        helper.setText(R.id.item_tugoujiu_phone, item.getPhone());
        helper.setText(R.id.item_tugoujiu_renlingliang, item.getNum() + item.getNumUnit());

    }
}
