package com.company.qcy.adapter.zhuji;

import android.support.annotation.Nullable;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.bean.zhuji.FangAnBean;

import java.util.List;

public class WodeFangAnDetailFangAnAdapter extends BaseQuickAdapter<FangAnBean, BaseViewHolder> {


    public WodeFangAnDetailFangAnAdapter(int layoutResId, @Nullable List<FangAnBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FangAnBean item) {

        helper.getView(R.id.item_zhuji_fangan_companyname).setVisibility(View.GONE);
        helper.getView(R.id.item_zhuji_fangan_caina).setVisibility(View.GONE);

        helper.setText(R.id.item_zhuji_fangan_chanpinming,StringUtils.isEmpty(item.getProductName())?"暂无":item.getProductName());
        helper.setText(R.id.item_zhuji_fangan_yangpin,StringUtils.isEmpty(item.getCompanyName())?"暂无":item.getCompanyName());
        helper.setText(R.id.item_zhuji_fangan_gongyimiaoshu,StringUtils.isEmpty(item.getDescription())?"暂无":item.getDescription());
        helper.setText(R.id.item_zhuji_fangan_time,StringUtils.isEmpty(item.getCreatedAt())?"暂无":item.getCreatedAt());

    }
}
