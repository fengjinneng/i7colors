package com.company.qcy.huodong.jingpai.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.huodong.jingpai.bean.ChujiajiluBean;

import org.w3c.dom.Text;

import java.util.List;

public class ChujiajiuAdapter extends BaseQuickAdapter<ChujiajiluBean, BaseViewHolder> {


    public ChujiajiuAdapter(int layoutResId, @Nullable List<ChujiajiluBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChujiajiluBean item) {


        TextView phone = (TextView) helper.getView(R.id.item_jingpai_chujialilu_phone);
        TextView address = (TextView) helper.getView(R.id.item_jingpai_chujialilu_address);
        TextView price = (TextView) helper.getView(R.id.item_jingpai_chujialilu_price);
        TextView time = (TextView) helper.getView(R.id.item_jingpai_chujialilu_time);
        phone.setText(item.getPhone());
        address.setText(item.getCity());
        price.setText(item.getPrice());
        time.setText(item.getCreatedAt());
        if (helper.getAdapterPosition() == 0) {
//            helper.getView(R.id.item_jingpai_chujialilu_zuigaojia).setVisibility(View.VISIBLE);
            phone.setTextColor(mContext.getResources().getColor(R.color.hongse));
            address.setTextColor(mContext.getResources().getColor(R.color.hongse));
            price.setTextColor(mContext.getResources().getColor(R.color.hongse));
            time.setTextColor(mContext.getResources().getColor(R.color.hongse));
            ((TextView) helper.getView(R.id.textView275)).setTextColor(mContext.getResources().getColor(R.color.hongse));
        } else {
//            helper.getView(R.id.item_jingpai_chujialilu_zuigaojia).setVisibility(View.GONE);
            phone.setTextColor(mContext.getResources().getColor(R.color.putongwenben));
            address.setTextColor(mContext.getResources().getColor(R.color.putongwenben));
            price.setTextColor(mContext.getResources().getColor(R.color.putongwenben));
            time.setTextColor(mContext.getResources().getColor(R.color.putongwenben));
            ((TextView) helper.getView(R.id.textView275)).setTextColor(mContext.getResources().getColor(R.color.putongwenben));
        }
    }
}
