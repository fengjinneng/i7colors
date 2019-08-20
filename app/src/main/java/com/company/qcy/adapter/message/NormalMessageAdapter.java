package com.company.qcy.adapter.message;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.bean.message.MessageBean;

import java.util.List;

public class NormalMessageAdapter extends BaseQuickAdapter<MessageBean, BaseViewHolder> {


    public NormalMessageAdapter(int layoutResId, @Nullable List<MessageBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean item) {

        if (!StringUtils.isEmpty(item.getWorkType())) {
            if (StringUtils.equals("enquiry", item.getWorkType())) {
                helper.setText(R.id.item_message_type, "求购消息");
            }
            if (StringUtils.equals("zhujiDiy", item.getWorkType())) {

                helper.setText(R.id.item_message_type, "助剂定制");
            }
        }


        if (StringUtils.isEmpty(item.getProductName())) {
            helper.setText(R.id.item_message_title, item.getZhujiName());
        } else {
            helper.setText(R.id.item_message_title, item.getProductName());
        }

        helper.setText(R.id.item_message_content, item.getContent());

        if (StringUtils.equals("0", item.getIsRead())) {
            helper.setText(R.id.item_message_status, "未读");
            helper.setTextColor(R.id.item_message_status, mContext.getResources().getColor(R.color.chunhongse));
        } else {
            helper.setText(R.id.item_message_status, "已读");
            helper.setTextColor(R.id.item_message_status, mContext.getResources().getColor(R.color.putongwenben));
        }
//        helper.setText(R.id.item_message_time,item.getCreatedAt().substring(0,10));
        helper.setText(R.id.item_message_time, item.getCreatedAt());

    }
}
