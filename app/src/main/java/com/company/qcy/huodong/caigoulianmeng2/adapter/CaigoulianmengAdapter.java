package com.company.qcy.huodong.caigoulianmeng2.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.huodong.caigoulianmeng2.bean.CaigoulianmengBean;

import java.util.List;

public class CaigoulianmengAdapter   extends BaseQuickAdapter<CaigoulianmengBean, BaseViewHolder> {

    public CaigoulianmengAdapter(int layoutResId, @Nullable List<CaigoulianmengBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CaigoulianmengBean item) {

        helper.addOnClickListener(R.id.item_caigoulianmeng_liebiao_woyaocaigou)
                .addOnClickListener(R.id.item_caigoulianmeng_liebiao_woyaogonghuo);

        TextView woyaocaigou = (TextView) helper.getView(R.id.item_caigoulianmeng_liebiao_woyaocaigou);
        TextView woyaogonghuo = (TextView) helper.getView(R.id.item_caigoulianmeng_liebiao_woyaogonghuo);


        helper.setText(R.id.item_caigoulianmeng_liebiao_name,item.getMeetingName());
        helper.setText(R.id.item_caigoulianmeng_liebiao_total_dinghuo,item.getAllNum());
        helper.setText(R.id.item_caigoulianmeng_liebiao_total_gonghuo,item.getAllOutputNum());
        helper.setText(R.id.item_caigoulianmeng_liebiao_qiyenumber,item.getPlaceCount());
        helper.setText(R.id.item_caigoulianmeng_liebiao_gongyingshan,item.getSupplyCount());


        TextView status = (TextView) helper.getView(R.id.item_caigoulianmeng_liebiao_status);

        switch (item.getIsType()){
            case "0"://结束
                woyaocaigou.setBackground(mContext.getResources().getDrawable(R.drawable.background_caigoulianmeng_anniu_hui));
                woyaocaigou.setTextColor(mContext.getResources().getColor(R.color.putongwenben));
                woyaogonghuo.setBackground(mContext.getResources().getDrawable(R.drawable.background_caigoulianmeng_anniu_hui));
                woyaogonghuo.setTextColor(mContext.getResources().getColor(R.color.putongwenben));
                status.setTextColor(mContext.getResources().getColor(R.color.baise));
                status.setText("活动已结束！");
                status.setBackgroundResource(R.color.putongwenben);
                break;
            case "1"://未开始
                woyaocaigou.setBackground(mContext.getResources().getDrawable(R.drawable.background_caigoulianmeng_anniu_hui));
                woyaocaigou.setTextColor(mContext.getResources().getColor(R.color.putongwenben));
                woyaogonghuo.setBackground(mContext.getResources().getDrawable(R.drawable.background_caigoulianmeng_anniu_hui));
                woyaogonghuo.setTextColor(mContext.getResources().getColor(R.color.putongwenben));
                status.setTextColor(mContext.getResources().getColor(R.color.putongwenben));
                status.setText("活动未开始("+item.getStartTime().substring(0,10)+"至"+item.getEndTime().substring(0,10)+")");
                status.setBackgroundResource(R.color.baise);
                break;
            case "2"://进行中
                woyaocaigou.setBackground(mContext.getResources().getDrawable(R.drawable.background_caigoulianmeng_anniu_huang));
                woyaocaigou.setTextColor(mContext.getResources().getColor(R.color.startcolor));
                woyaogonghuo.setBackground(mContext.getResources().getDrawable(R.drawable.background_caigoulianmeng_anniu_lan));
                woyaogonghuo.setTextColor(mContext.getResources().getColor(R.color.lanse));
                status.setTextColor(mContext.getResources().getColor(R.color.baise));
                status.setText("进行中("+item.getStartTime().substring(0,10)+"至"+item.getEndTime().substring(0,10)+")");
                status.setBackground(mContext.getResources().getDrawable(R.mipmap.bg));
                break;
        }

    }
}
