package com.company.qcy.adapter.pengyouquan;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.MyCommonUtil;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.bean.pengyouquan.PengyouquanBean;
import com.company.qcy.widght.pengyouquan.CommentListView;
import com.company.qcy.widght.pengyouquan.HuanHangCommentListView;

import java.util.ArrayList;
import java.util.List;

public class PinglunliebiaoAdapter extends BaseQuickAdapter<PengyouquanBean.CommentListBean, BaseViewHolder> {


    public PinglunliebiaoAdapter(int layoutResId, @Nullable List<PengyouquanBean.CommentListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PengyouquanBean.CommentListBean item) {


        helper.setText(R.id.item_pinglun_content, item.getContent());
        helper.addOnClickListener(R.id.item_pinglun_pinglun_layout);


        HuanHangCommentListView commentListView = (HuanHangCommentListView) helper.getView(R.id.item_pinglun_commentListview);

        List<PengyouquanBean.CommentListBean> list = new ArrayList<>();
        list.add(item);

        commentListView.setDatas(list);

//        if (ObjectUtils.isEmpty(item.getByCommentUser())) {
//            helper.getView(R.id.item_pinglun_huifu_text).setVisibility(View.GONE);
//            byCommentUser.setVisibility(View.GONE);
//            commentUser.setTextColor(mContext.getResources().getColor(R.color.erjibiaoti));
//        } else {
//            helper.getView(R.id.item_pinglun_huifu_text).setVisibility(View.VISIBLE);
//            byCommentUser.setVisibility(View.VISIBLE);
//            byCommentUser.setText(item.getByCommentUser());
//            commentUser.setTextColor(mContext.getResources().getColor(R.color.lanse));
//            byCommentUser.setTextColor(mContext.getResources().getColor(R.color.lanse));
//        }
//        commentUser.setText(item.getCommentUser());

        if (StringUtils.equals("1", item.getIsCharger())) {
            helper.getView(R.id.item_pinglun_pinglun_layout).setVisibility(View.INVISIBLE);
        } else {
            helper.getView(R.id.item_pinglun_pinglun_layout).setVisibility(View.VISIBLE);
        }
        if(!StringUtils.isEmpty(item.getCreatedAtStamp())) {
            helper.setText(R.id.item_pinglun_time, TimeUtils.millis2String(Long.parseLong(item.getCreatedAtStamp())));
        }
    }
}
