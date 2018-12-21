package com.company.qcy.adapter.pengyouquan;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.bean.pengyouquan.MyFansBean;

import java.util.List;

public class MyFansAdapter extends BaseQuickAdapter<MyFansBean, BaseViewHolder> {


    public MyFansAdapter(int layoutResId, @Nullable List<MyFansBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyFansBean item) {


        TextView name = (TextView) helper.getView(R.id.item_myfans_username);
        ImageView headImg = (ImageView) helper.getView(R.id.item_myfans_image);

        if (StringUtils.isEmpty(item.getUserCommunityPhoto())) {
            headImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.morentouxiang));
        } else {
            GlideUtils.loadCircleImage(mContext, ServerInfo.IMAGE + item.getUserCommunityPhoto(), headImg);
        }

        helper.setText(R.id.item_myfans_time, TimeUtils.millis2String(Long.parseLong(item.getCreatedAtStamp())).substring(0, 10) +
                "\n" + TimeUtils.millis2String(Long.parseLong(item.getCreatedAtStamp())).substring(10, 19));

        name.setText(item.getUserNickName());

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(item.getUserNickName() + " .");


        if (StringUtils.equals("1", item.getIsCompany())) {
            helper.getView(R.id.item_myfans_bigv).setVisibility(View.VISIBLE);
            name.setTextColor(mContext.getResources().getColor(R.color.chunhongse));
        } else {
            if (StringUtils.equals("1", item.getIsDyeV())) {
                helper.getView(R.id.item_myfans_bigv).setVisibility(View.VISIBLE);
                name.setTextColor(mContext.getResources().getColor(R.color.chunhongse));
            } else {
                helper.getView(R.id.item_myfans_bigv).setVisibility(View.GONE);
                name.setTextColor(mContext.getResources().getColor(R.color.putongwenben));
            }
        }
        //大佬等级1
        if (StringUtils.equals("1", item.getBossLevel())) {
            Drawable level1 = mContext.getResources().getDrawable(R.mipmap.level_one);
            level1.setBounds(0, 0, level1.getIntrinsicWidth(), level1.getIntrinsicHeight());
            ImageSpan span1 = new ImageSpan(level1, ImageSpan.ALIGN_BASELINE);
            spannableStringBuilder.setSpan(span1,
                    spannableStringBuilder.length() - 1, spannableStringBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            helper.setText(R.id.item_myfans_username, spannableStringBuilder);

        }
        if (StringUtils.equals("2", item.getBossLevel())) {
            Drawable level2 = mContext.getResources().getDrawable(R.mipmap.level_two);
            level2.setBounds(0, 0, level2.getIntrinsicWidth(), level2.getIntrinsicHeight());

            ImageSpan span2 = new ImageSpan(level2, ImageSpan.ALIGN_BASELINE);

            spannableStringBuilder.setSpan(span2,
                    spannableStringBuilder.length() - 1, spannableStringBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            helper.setText(R.id.item_myfans_username, spannableStringBuilder);
        }
        if (StringUtils.equals("3", item.getBossLevel())) {
            Drawable level3 = mContext.getResources().getDrawable(R.mipmap.level_three);
            level3.setBounds(0, 0, level3.getIntrinsicWidth(), level3.getIntrinsicHeight());
            ImageSpan span3 = new ImageSpan(level3, ImageSpan.ALIGN_BASELINE);
            spannableStringBuilder.setSpan(span3,
                    spannableStringBuilder.length() - 1, spannableStringBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            helper.setText(R.id.item_myfans_username, spannableStringBuilder);
        }

    }
}
