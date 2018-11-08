package com.company.qcy.adapter.tuangou;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.bean.tuangou.TuangouRecordBean;

import java.util.List;

public class TuangoujiluAdapter extends BaseQuickAdapter<TuangouRecordBean, BaseViewHolder> {


    public TuangoujiluAdapter(int layoutResId, @Nullable List<TuangouRecordBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TuangouRecordBean item) {


        helper.setText(R.id.item_tugoujiu_number,item.getNumber());
        helper.setText(R.id.item_tugoujiu_companyname,item.getCompanyName());
        helper.setText(R.id.item_tugoujiu_phone,item.getPhone());
        helper.setText(R.id.item_tugoujiu_renlingliang,item.getNum());

    }
}
