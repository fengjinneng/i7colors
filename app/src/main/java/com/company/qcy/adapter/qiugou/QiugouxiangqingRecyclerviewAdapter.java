package com.company.qcy.adapter.qiugou;


import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.bean.qiugou.BaojiaBean;

import java.util.List;

public class QiugouxiangqingRecyclerviewAdapter
        extends BaseQuickAdapter<BaojiaBean, BaseViewHolder> {

    private int wode;
    private String isCharger;
    private String qiugouStatus;

    public QiugouxiangqingRecyclerviewAdapter(int layoutResId, @Nullable List<BaojiaBean> data, int wode, String isCharger, String qiugouStatus) {
        super(layoutResId, data);
        this.wode = wode;
        this.isCharger = isCharger;
        this.qiugouStatus = qiugouStatus;
    }


    @Override
    protected void convert(BaseViewHolder helper, BaojiaBean item) {

        //企业发布的报价
        if (StringUtils.equals("企业发布", item.getPublishType())) {
            helper.setText(R.id.item_qiugouxiangqing_recyclerview_company, item.getCompanyDomain().getCompanyName());
        } else {
            helper.setText(R.id.item_qiugouxiangqing_recyclerview_company, item.getCompanyName2());
        }

        TextView caina = (TextView) helper.getView(R.id.item_qiugouxiangqing_recyclerview_cainabaojia);


        if (!StringUtils.isEmpty(isCharger)) {

            if (StringUtils.equals("1", isCharger)) {


                //求购中
                if (StringUtils.equals("1", qiugouStatus)) {

                    //正在报价中
                    if (StringUtils.equals("0", item.getStatus())) {
                        caina.setVisibility(View.VISIBLE);

                    } else if (StringUtils.equals("1", item.getStatus())) {
                        caina.setVisibility(View.VISIBLE);
                        caina.setText("已采纳");
                        caina.setBackgroundResource(R.drawable.background_huise_button);
                        caina.setTextColor(mContext.getResources().getColor(R.color.baise));
                        caina.setEnabled(false);
                    } else if (StringUtils.equals("2", item.getStatus())) {
                        caina.setVisibility(View.VISIBLE);
                        caina.setText("已过期");
                        caina.setBackgroundResource(R.drawable.background_huise_button);
                        caina.setTextColor(mContext.getResources().getColor(R.color.baise));
                        caina.setEnabled(false);
                    } else {
                        caina.setVisibility(View.GONE);
                    }

                    //求购被采纳
                } else {

                    if (StringUtils.equals("1", item.getStatus())) {
                        caina.setVisibility(View.VISIBLE);
                        caina.setText("已采纳");
                        caina.setBackgroundResource(R.drawable.background_huise_button);
                        caina.setTextColor(mContext.getResources().getColor(R.color.baise));
                        caina.setEnabled(false);
                    } else {
                        caina.setVisibility(View.VISIBLE);
                        caina.setText("未采纳");
                        caina.setBackgroundResource(R.drawable.background_huise_button);
                        caina.setTextColor(mContext.getResources().getColor(R.color.baise));
                        caina.setEnabled(false);
                    }

                }

            } else {
                caina.setVisibility(View.GONE);
            }
        }

        TextView shenfen = (TextView) helper.getView(R.id.item_qiugouxiangqing_recyclerview_shenfen);
        shenfen.setVisibility(View.VISIBLE);
        helper.setText(R.id.item_qiugouxiangqing_recyclerview_shenfen, item.getPublishType());
        if (StringUtils.equals(item.getPublishType(), "企业发布")) {
            shenfen.setBackground(mContext.getResources().getDrawable(R.mipmap.qiyeyonghu));
        } else {
            shenfen.setBackground(mContext.getResources().getDrawable(R.mipmap.gerenfabu));
        }

        if (StringUtils.isTrimEmpty(item.getPrice())) {
            helper.setText(R.id.item_qiugouxiangqing_recyclerview_price, "保密");
        } else helper.setText(R.id.item_qiugouxiangqing_recyclerview_price, item.getPrice());

        helper.setText(R.id.item_qiugouxiangqing_recyclerview_phone, item.getPhone())
                .setText(R.id.item_qiugouxiangqing_recyclerview_time, item.getCreatedAtString())
                .addOnClickListener(R.id.item_qiugouxiangqing_recyclerview_cainabaojia);

        TextView postage = (TextView) helper.getView(R.id.item_qiugouxiangqing_recyclerview_is_postage);
        TextView baojiashuoming = (TextView) helper.getView(R.id.item_qiugouxiangqing_recyclerview_beizhu_text);
        TextView beizhu = (TextView) helper.getView(R.id.item_qiugouxiangqing_recyclerview_beizhu);
        TextView havePostage = (TextView) helper.getView(R.id.item_qiugouxiangqing_recyclerview_postage_text);

        if (wode == 1 || StringUtils.equals("1", isCharger)) {
            //求购中
            if (StringUtils.equals("1", qiugouStatus)) {

                if (StringUtils.equals("0", item.getStatus())) {
                    //报价中
                    caina.setVisibility(View.VISIBLE);


                } else if (StringUtils.equals("1", item.getStatus())) {
                    caina.setVisibility(View.VISIBLE);
                    caina.setText("已采纳");
                    caina.setBackgroundResource(R.drawable.background_huise_button);
                    caina.setTextColor(mContext.getResources().getColor(R.color.baise));
                    caina.setEnabled(false);
                } else if (StringUtils.equals("2", item.getStatus())) {
                    caina.setVisibility(View.VISIBLE);
                    caina.setText("已过期");
                    caina.setBackgroundResource(R.drawable.background_huise_button);
                    caina.setTextColor(mContext.getResources().getColor(R.color.baise));
                    caina.setEnabled(false);
                } else {
                    caina.setVisibility(View.GONE);
                }

                //已接受报价
            } else {

                if (StringUtils.equals("1", item.getStatus())) {
                    caina.setVisibility(View.VISIBLE);
                    caina.setText("已采纳");
                    caina.setBackgroundResource(R.drawable.background_huise_button);
                    caina.setTextColor(mContext.getResources().getColor(R.color.baise));
                    caina.setEnabled(false);
                } else {
                    caina.setVisibility(View.VISIBLE);
                    caina.setText("未采纳");
                    caina.setBackgroundResource(R.drawable.background_huise_button);
                    caina.setTextColor(mContext.getResources().getColor(R.color.baise));
                    caina.setEnabled(false);
                }
            }
            postage.setVisibility(View.VISIBLE);
            helper.setText(R.id.item_qiugouxiangqing_recyclerview_is_postage, item.getIsIncludeTrans());
            baojiashuoming.setVisibility(View.VISIBLE);
            beizhu.setVisibility(View.VISIBLE);
            helper.setText(R.id.item_qiugouxiangqing_recyclerview_beizhu, item.getDescription());
            havePostage.setVisibility(View.VISIBLE);
            if (StringUtils.equals("1", item.getIsIncludeTrans())) {
                helper.setText(R.id.item_qiugouxiangqing_recyclerview_is_postage, "包含运费");
            }
            if (StringUtils.equals("0", item.getIsIncludeTrans())) {
                helper.setText(R.id.item_qiugouxiangqing_recyclerview_is_postage, "不含运费");
            }


        } else {
            postage.setVisibility(View.GONE);
            baojiashuoming.setVisibility(View.INVISIBLE);
            beizhu.setVisibility(View.GONE);
            havePostage.setVisibility(View.GONE);

        }

    }
}