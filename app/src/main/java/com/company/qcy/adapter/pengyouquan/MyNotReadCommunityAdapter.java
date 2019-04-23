package com.company.qcy.adapter.pengyouquan;

import android.media.Image;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.bean.pengyouquan.PengyouquanBean;

import java.util.List;

public class MyNotReadCommunityAdapter extends BaseQuickAdapter<PengyouquanBean.CommentListBean, BaseViewHolder> {


    public MyNotReadCommunityAdapter(int layoutResId, @Nullable List<PengyouquanBean.CommentListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PengyouquanBean.CommentListBean item) {

        helper.setText(R.id.item_my_mot_read_name, item.getCommentUser());
        helper.setText(R.id.item_my_mot_read_content, item.getContent());

        ImageView img = (ImageView) helper.getView(R.id.item_my_mot_read_img);

        helper.addOnClickListener(R.id.item_my_mot_read_img);


        if (StringUtils.isEmpty(item.getCommentPhoto())) {
            img.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.morentouxiang));
        } else {
            GlideUtils.loadCircleImage(mContext, ServerInfo.IMAGE + item.getCommentPhoto(), img);
        }

        if(!StringUtils.isEmpty(item.getCreatedAtStamp())){

        helper.setText(R.id.item_my_mot_read_time, TimeUtils.millis2String(Long.parseLong(item.getCreatedAtStamp())));
        }

    }
}
