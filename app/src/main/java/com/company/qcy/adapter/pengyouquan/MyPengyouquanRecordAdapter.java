package com.company.qcy.adapter.pengyouquan;

import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.Utils.DateUtil;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.bean.pengyouquan.PengyouquanBean;

import java.text.ParseException;
import java.util.List;

public class MyPengyouquanRecordAdapter extends BaseQuickAdapter<PengyouquanBean, BaseViewHolder> {


    public MyPengyouquanRecordAdapter(int layoutResId, @Nullable List<PengyouquanBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PengyouquanBean item) {

        helper.setText(R.id.item_pengyouquan_record_content, item.getContent());
        ImageView img = (ImageView) helper.getView(R.id.item_pengyouquan_record_img);
        TextView time = (TextView) helper.getView(R.id.item_pengyouquan_record_time);
        TextView monthAndDay = (TextView) helper.getView(R.id.item_pengyouquan_record_mouth_and_day);


        time.setText(TimeUtils.millis2String(Long.parseLong(item.getCreatedAtStamp())).substring(0, 4));
        String substring = TimeUtils.millis2String(Long.parseLong(item.getCreatedAtStamp())).substring(5, 10);
        String replace = substring.replace("-", "/");
        monthAndDay.setText(replace);
        monthAndDay.setVisibility(View.VISIBLE);
        try {
            if (DateUtil.IsToday(TimeUtils.millis2String(Long.parseLong(item.getCreatedAtStamp())))) {
                time.setText("今天");
                monthAndDay.setVisibility(View.GONE);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            if (DateUtil.IsYesterday(TimeUtils.millis2String(Long.parseLong(item.getCreatedAtStamp())))) {
                time.setText("昨天");
                monthAndDay.setVisibility(View.GONE);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!StringUtils.isEmpty(item.getUrl())) {
            img.setVisibility(View.VISIBLE);
            helper.getView(R.id.item_pengyouquan_record_play).setVisibility(View.VISIBLE);
            GlideUtils.loadImage(mContext, ServerInfo.IMAGE + item.getVideoPicUrl(), img);
        } else {
            helper.getView(R.id.item_pengyouquan_record_play).setVisibility(View.GONE);
            if (StringUtils.isEmpty(item.getPic1())) {
                img.setVisibility(View.GONE);
            } else {
                img.setVisibility(View.VISIBLE);
                GlideUtils.loadImage(mContext, ServerInfo.IMAGE + item.getPic1(), img);
            }
        }

        ConstraintLayout contentLayout = (ConstraintLayout) helper.getView(R.id.item_pengyouquan_record_content_layout);
        ConstraintLayout lianjieLayout = (ConstraintLayout) helper.getView(R.id.item_pengyouquan_record_lianjie_layout);
        ImageView lianjieImage = (ImageView) helper.getView(R.id.item_pengyouquan_record_lianjie_img);
        TextView lianjieText = (TextView) helper.getView(R.id.item_pengyouquan_record_lianjie_title);

        if(ObjectUtils.isEmpty(item.getShareBean())){
            lianjieLayout.setVisibility(View.GONE);
        }else {
            lianjieLayout.setVisibility(View.VISIBLE);
            GlideUtils.loadImage(mContext,ServerInfo.IMAGE+item.getShareBean().getPic(),lianjieImage);
            lianjieText.setText(item.getShareBean().getTitle());
        }


        if(StringUtils.isEmpty(item.getVideoPicUrl())&&StringUtils.isEmpty(item.getContent())&&StringUtils.isEmpty(item.getPic1())){
            contentLayout.setVisibility(View.GONE);
        }else {
            contentLayout.setVisibility(View.VISIBLE);
        }

    }
}
