package com.company.qcy.huodong.caigoulianmeng2.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.huodong.caigoulianmeng2.bean.HuodanBean;

import java.util.List;

public class WodegonghuodanAdapter extends BaseQuickAdapter<HuodanBean, BaseViewHolder> {



    public WodegonghuodanAdapter(int layoutResId, @Nullable List<HuodanBean> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, HuodanBean item) {

        helper.addOnClickListener(R.id.item_caigoulianmeng_yonghuodan_chakan);

        helper.setText(R.id.item_caigoulianmeng_yonghuodan_name,item.getMeetingName());

        TextView status = (TextView) helper.getView(R.id.item_caigoulianmeng_yonghuodan_status);


        switch (item.getIsType()){
            case "0"://结束
                status.setTextColor(mContext.getResources().getColor(R.color.baise));
                status.setText("活动已结束！");
                status.setBackgroundResource(R.color.putongwenben);
                break;
            case "1"://未开始
                status.setTextColor(mContext.getResources().getColor(R.color.baise));
                status.setText("活动未开始("+item.getStartTime().substring(0,10)+"至"+item.getEndTime().substring(0,10)+")");
                status.setBackgroundResource(R.color.putongwenben);
                break;
            case "2"://进行中
                status.setTextColor(mContext.getResources().getColor(R.color.baise));
                status.setText("进行中("+item.getStartTime().substring(0,10)+"至"+item.getEndTime().substring(0,10)+")");
                status.setBackground(mContext.getResources().getDrawable(R.mipmap.bg));
                break;
        }

    }
}
