package com.company.qcy.huodong.youhuizhanxiao.adapter;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.huodong.youhuizhanxiao.bean.YouhuizhanxiaoRecordBean;

import java.util.List;

public class YouhuizhanxiaojiluAdapter extends BaseQuickAdapter<YouhuizhanxiaoRecordBean, BaseViewHolder> {


    public YouhuizhanxiaojiluAdapter(int layoutResId, @Nullable List<YouhuizhanxiaoRecordBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, YouhuizhanxiaoRecordBean item) {


        helper.setText(R.id.item_youhuizhanxiao_xuhao, item.getNumber());
        if (!StringUtils.isEmpty(item.getProvince())) {

            helper.setText(R.id.item_youhuizhanxiao_companyname, item.getProvince() + "***公司");

        } else helper.setText(R.id.item_youhuizhanxiao_companyname, "***公司");
        helper.setText(R.id.item_youhuizhanxiao_phone, item.getPhone());
        helper.setText(R.id.item_youhuizhanxiao_goumailiang, item.getNum() + item.getNumUnit());

    }
}
