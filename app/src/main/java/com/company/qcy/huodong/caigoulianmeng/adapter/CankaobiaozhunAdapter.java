package com.company.qcy.huodong.caigoulianmeng.adapter;

import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.huodong.caigoulianmeng2.bean.HuodanBean;

import java.util.List;

public class CankaobiaozhunAdapter extends BaseQuickAdapter<HuodanBean.MeetingShopBean.MeetingTypeBean, BaseViewHolder> {


    public CankaobiaozhunAdapter(int layoutResId, @Nullable List<HuodanBean.MeetingShopBean.MeetingTypeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HuodanBean.MeetingShopBean.MeetingTypeBean item) {


        TextView textView = (TextView) helper.getView(R.id.item_wodehuodan_cankaobianzhun_text);
        textView.setText(item.getReferenceType());
        Resources resources = mContext.getResources();
        switch (helper.getAdapterPosition()) {
            case 0:
                textView.setTextColor(resources.getColor(R.color.chunhongse));
                textView.setBackground(resources.getDrawable(R.drawable.biankuang_cankaobianzhun_1));
                break;
            case 1:
                textView.setTextColor(resources.getColor(R.color.lanse));
                textView.setBackground(resources.getDrawable(R.drawable.biankuang_cankaobianzhun_2));
                break;
            case 2:
                textView.setTextColor(resources.getColor(R.color.startcolor));
                textView.setBackground(resources.getDrawable(R.drawable.biankuang_cankaobianzhun_3));
                break;
            case 3:
                textView.setTextColor(resources.getColor(R.color.color_aeaeae));
                textView.setBackground(resources.getDrawable(R.drawable.biankuang_cankaobianzhun_4));
                break;
            case 5:
                textView.setTextColor(resources.getColor(R.color.chunhongse));
                textView.setBackground(resources.getDrawable(R.drawable.biankuang_cankaobianzhun_1));
                break;
            case 6:
                textView.setTextColor(resources.getColor(R.color.lanse));
                textView.setBackground(resources.getDrawable(R.drawable.biankuang_cankaobianzhun_2));
                break;
            case 7:
                textView.setTextColor(resources.getColor(R.color.startcolor));
                textView.setBackground(resources.getDrawable(R.drawable.biankuang_cankaobianzhun_3));
                break;
            case 8:
                textView.setTextColor(resources.getColor(R.color.color_aeaeae));
                textView.setBackground(resources.getDrawable(R.drawable.biankuang_cankaobianzhun_4));
                break;
            case 9:
                textView.setTextColor(resources.getColor(R.color.chunhongse));
                textView.setBackground(resources.getDrawable(R.drawable.biankuang_cankaobianzhun_1));
                break;
            case 10:
                textView.setTextColor(resources.getColor(R.color.lanse));
                textView.setBackground(resources.getDrawable(R.drawable.biankuang_cankaobianzhun_2));
                break;
            case 11:
                textView.setTextColor(resources.getColor(R.color.startcolor));
                textView.setBackground(resources.getDrawable(R.drawable.biankuang_cankaobianzhun_3));
                break;
            case 12:
                textView.setTextColor(resources.getColor(R.color.color_aeaeae));
                textView.setBackground(resources.getDrawable(R.drawable.biankuang_cankaobianzhun_4));
                break;
            default:
                break;
        }

    }
}
