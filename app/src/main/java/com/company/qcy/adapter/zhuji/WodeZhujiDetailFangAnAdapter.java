package com.company.qcy.adapter.zhuji;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.bean.zhuji.ZhujiBean;

import java.util.List;

public class WodeZhujiDetailFangAnAdapter extends BaseQuickAdapter<ZhujiBean.SolutionListBean, BaseViewHolder> {


    public WodeZhujiDetailFangAnAdapter(int layoutResId, @Nullable List<ZhujiBean.SolutionListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ZhujiBean.SolutionListBean item) {
        helper.addOnClickListener(R.id.item_zhuji_fangan_caina);

        TextView caina = (TextView) helper.getView(R.id.item_zhuji_fangan_caina);

        if (StringUtils.equals("diying", item.getStatus())) {
            caina.setText("采纳");
            caina.setBackground(mContext.getResources().getDrawable(R.drawable.background_yuanxingbiankuang_red));
        } else if (StringUtils.equals("accept", item.getStatus())) {
            caina.setText("已采纳");
            caina.setBackground(mContext.getResources().getDrawable(R.color.fengexian));
        } else {
            caina.setText("未采纳");
            caina.setBackground(mContext.getResources().getDrawable(R.color.fengexian));
        }

        helper.setText(R.id.item_zhuji_fangan_companyname, StringUtils.isEmpty(item.getCompanyName()) ? "暂无" : item.getCompanyName());
        helper.setText(R.id.item_zhuji_fangan_chanpinming, StringUtils.isEmpty(item.getProductName()) ? "暂无" : item.getProductName());
        helper.setText(R.id.item_zhuji_fangan_gongyimiaoshu, StringUtils.isEmpty(item.getDescription()) ? "暂无" : item.getDescription());
        helper.setText(R.id.item_zhuji_fangan_time, StringUtils.isEmpty(item.getCreatedAt()) ? "暂无" : item.getCreatedAt());

        if (StringUtils.isEmpty(item.getNum()) || StringUtils.isEmpty(item.getNumUnit())) {
            helper.setText(R.id.item_zhuji_fangan_yangpin, "无");
        } else {
            helper.setText(R.id.item_zhuji_fangan_yangpin, item.getNum() + item.getNumUnit());

        }
    }
}
