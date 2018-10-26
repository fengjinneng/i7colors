package com.company.qcy.adapter.qiugou;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.bean.qiugou.QiugouBean;

import java.util.List;

public class QiugoudatingRecyclerviewAdapter
        extends BaseQuickAdapter<QiugouBean, BaseViewHolder> {
    public QiugoudatingRecyclerviewAdapter(int layoutResId, @Nullable List<QiugouBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QiugouBean item) {


        helper.setText(R.id.item_qiugoudating_mingcheng, item.getProductName())
                .setText(R.id.item_qiugoudating_canyubaojia, item.getOfferNum() + "")
                .setText(R.id.item_qiugoudating_diqu, item.getLocationProvince() + " " + item.getLocationCity())
                .setText(R.id.item_qiugoudating_danwei, item.getNumUnit())
                .setText(R.id.item_qiugoudating_zhongliang, item.getNum() + "")
                .addOnClickListener(R.id.item_qiugoudating_yijianhujiao);



        ImageView imageView = (ImageView) helper.getView(R.id.item_qiugoudating_zhuangtai);
        TextView yiwancheng = (TextView) helper.getView(R.id.item_qiugoudating_yiwancheng);
        TextView shenfen = (TextView) helper.getView(R.id.item_qiugoudating_shenfen);

        if (StringUtils.equals(item.getStatus(), "1")) {
            imageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.bijiazhong));
            yiwancheng.setVisibility(View.GONE);
        } else {
            imageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.bijiawancheng));
            yiwancheng.setVisibility(View.VISIBLE);
        }

        shenfen.setText(item.getPublishType());
        if (StringUtils.equals(item.getPublishType(), "企业发布")) {
            shenfen.setBackground(mContext.getResources().getDrawable(R.mipmap.qiyeyonghu));
            if (StringUtils.equals("1", item.getIsCharger())) {
                helper.getView(R.id.item_qiugoudating_wodefabu).setVisibility(View.VISIBLE);
                helper.getView(R.id.item_qiugoudating_wodefabu).setBackgroundResource(R.drawable.background_wodefabu_qiye);
            }else {
                helper.getView(R.id.item_qiugoudating_wodefabu).setVisibility(View.GONE);
            }
        } else {
            shenfen.setBackground(mContext.getResources().getDrawable(R.mipmap.gerenfabu));
            if (StringUtils.equals("1", item.getIsCharger())) {
                helper.getView(R.id.item_qiugoudating_wodefabu).setVisibility(View.VISIBLE);
                helper.getView(R.id.item_qiugoudating_wodefabu).setBackgroundResource(R.drawable.background_wodefabu_geren);
            } else {
                helper.getView(R.id.item_qiugoudating_wodefabu).setVisibility(View.GONE);
            }
        }


        if (StringUtils.isEmpty(item.getSurplusDay())) {

            if (StringUtils.isEmpty(item.getSurplusHour())) {


                if (StringUtils.isEmpty(item.getSurplusMin())) {

                } else {
                    helper.setText(R.id.item_qiugoudating_firsttime, item.getSurplusMin());
                    helper.setText(R.id.item_qiugoudating_secondtime, item.getSurplusSec());
                    helper.setText(R.id.item_qiugoudating_firsttime_text, "分");
                    helper.setText(R.id.item_qiugoudating_secondtime_text, "秒");
                }

            } else {

                helper.setText(R.id.item_qiugoudating_firsttime, item.getSurplusHour());
                helper.setText(R.id.item_qiugoudating_secondtime, item.getSurplusMin());
                helper.setText(R.id.item_qiugoudating_firsttime_text, "小时");
                helper.setText(R.id.item_qiugoudating_secondtime_text, "分钟");

            }

        } else {

            helper.setText(R.id.item_qiugoudating_firsttime, item.getSurplusDay());
            helper.setText(R.id.item_qiugoudating_secondtime, item.getSurplusHour());


        }


    }
}