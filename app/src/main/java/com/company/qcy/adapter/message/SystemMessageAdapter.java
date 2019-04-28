package com.company.qcy.adapter.message;

import android.app.ActionBar;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.bean.message.SystemMeassageBean;

import java.util.List;

public class SystemMessageAdapter extends BaseQuickAdapter<SystemMeassageBean, BaseViewHolder> {
    public SystemMessageAdapter(int layoutResId, @Nullable List<SystemMeassageBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SystemMeassageBean item) {

        helper.setText(R.id.item_system_message_content,item.getContent());
        helper.setText(R.id.item_system_message_time,item.getCreatedAt());
        helper.setText(R.id.item_system_message_title,item.getTitle());
        ImageView imageView = (ImageView) helper.getView(R.id.item_system_message_img);



        if(!StringUtils.isEmpty(item.getPic())){
            imageView.setVisibility(View.VISIBLE);
            GlideUtils.loadImage(mContext, ServerInfo.IMAGE+item.getPic(),imageView);
        }else {
            imageView.setVisibility(View.GONE);
        }
    }
}
