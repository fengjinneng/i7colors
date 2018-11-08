package com.company.qcy.adapter.tuangou;


import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.bean.ChanpindatingBean;
import com.company.qcy.bean.tuangou.TuangouBean;

import java.util.List;

public class TuangouRecyclerviewAdapter extends
        BaseQuickAdapter<TuangouBean, BaseViewHolder> {
    public TuangouRecyclerviewAdapter(int layoutResId,
                                      @Nullable List<TuangouBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TuangouBean item) {
        helper.setText(R.id.item_tuangouliebiao_productname, item.getProductName());
        helper.setText(R.id.item_tuangouliebiao_total, item.getTotalNum());
        helper.setText(R.id.item_tuangouliebiao_shengyu, item.getRemainNum());
        helper.setText(R.id.item_tuangouliebiao_zuixiaorenling, "最小认领量: " + item.getMinNum() + item.getNumUnit());
        helper.setText(R.id.item_tuangouliebiao_zuidarenling, "最大认领量: " + item.getMaxNum() + item.getNumUnit());
        helper.setText(R.id.item_tuangouliebiao_yirenling, "已认领总量: " + item.getSubscribedNum() + item.getNumUnit());
        helper.setText(R.id.item_tuangouliebiao_baifenbi, "达成: " + item.getNumPercent());
        helper.setText(R.id.item_tuangouliebiao_yuanjia, item.getOldPrice());
        helper.setText(R.id.item_tuangouliebiao_tuangoujia, item.getNewPrice());
        ImageView img = (ImageView) helper.getView(R.id.item_tuangouliebiao_img);
        Glide.with(mContext).load(ServerInfo.IMAGE + item.getProductPic()).into(img);

        ConstraintLayout weikaishi = (ConstraintLayout) helper.getView(R.id.item_tuangouliebiao_status_time_layout);
        TextView timeEnd = (TextView) helper.getView(R.id.item_tuangouliebiao_time_end);
        TextView timeFrom = (TextView) helper.getView(R.id.item_tuangouliebiao_time_from);
        ImageView statusImg = (ImageView) helper.getView(R.id.item_tuangouliebiao_status_img);

        ConstraintLayout layout = (ConstraintLayout) helper.getView(R.id.item_tuangouliebiao_status_layout);
        ImageView smile = (ImageView) helper.getView(R.id.item_tuangouliebiao_status_text_img);

        SeekBar seekBar = (SeekBar) helper.getView(R.id.item_tuangouliebiao_seekbar);
        String[] split = item.getNumPercent().split("%");
        seekBar.setProgress(Integer.parseInt(split[0]));

        helper.setText(R.id.item_tuangouliebiao_total_danwei, item.getNumUnit());
        helper.setText(R.id.item_tuangouliebiao_shengyu_danwei, item.getNumUnit());

        helper.setText(R.id.item_tuangouliebiao_yuanjia_danwei, "元/" + item.getNumUnit());

        helper.setText(R.id.item_tuangouliebiao_tuangoujia_danwei, "元/" + item.getNumUnit());

        ConstraintLayout priceLayout = (ConstraintLayout) helper.getView(R.id.item_tuangouliebiao_price_layout);


        if (StringUtils.equals("00", item.getEndCode())) {
            //团购未开始
            weikaishi.setVisibility(View.VISIBLE);
            layout.setVisibility(View.INVISIBLE);
            timeEnd.setText(new String(item.getEndTime()).substring(0, 10));
            timeFrom.setText(new String(item.getStartTime()).substring(0, 10));
            statusImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.tuangou_weikaishi));
            priceLayout.setBackgroundColor(mContext.getResources().getColor(R.color.shenhui));
        } else if (StringUtils.equals("10", item.getEndCode())) {
            //已开始未领完
            weikaishi.setVisibility(View.INVISIBLE);
            layout.setVisibility(View.VISIBLE);
            helper.setText(R.id.item_tuangouliebiao_status_text, "团购进行中...");
            smile.setVisibility(View.INVISIBLE);
            statusImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.tuangou_yikaishi));
        } else if (StringUtils.equals("11", item.getEndCode())) {
            //已开始已领完
            weikaishi.setVisibility(View.INVISIBLE);
            layout.setVisibility(View.VISIBLE);
            helper.setText(R.id.item_tuangouliebiao_status_text, "团购成功！");
            smile.setVisibility(View.VISIBLE);
            smile.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.tuangou_sucess));
            statusImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.tuangou_yijieshu));
            priceLayout.setBackgroundColor(mContext.getResources().getColor(R.color.qianhui));
        } else if (StringUtils.equals("20", item.getEndCode())) {
            //已结束未领完
            weikaishi.setVisibility(View.INVISIBLE);
            layout.setVisibility(View.VISIBLE);
            helper.setText(R.id.item_tuangouliebiao_status_text, "团购失败！");
            ((TextView) helper.getView(R.id.item_tuangouliebiao_status_text)).setTextColor(mContext.getResources().getColor(R.color.erjibiaoti));
            smile.setVisibility(View.VISIBLE);
            smile.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.tuangou_fail));
            statusImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.tuangou_yijieshu));
            priceLayout.setBackgroundColor(mContext.getResources().getColor(R.color.qianhui));
        } else if (StringUtils.equals("21", item.getEndCode())) {
            //已结束已领完
            weikaishi.setVisibility(View.INVISIBLE);
            layout.setVisibility(View.VISIBLE);
            helper.setText(R.id.item_tuangouliebiao_status_text, "团购成功！");
            smile.setVisibility(View.VISIBLE);
            smile.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.tuangou_sucess));
            statusImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.tuangou_yijieshu));
            priceLayout.setBackgroundColor(mContext.getResources().getColor(R.color.qianhui));
        }

    }
}
