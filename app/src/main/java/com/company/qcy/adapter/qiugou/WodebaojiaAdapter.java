package com.company.qcy.adapter.qiugou;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.bean.qiugou.BaojiaBean;

import java.util.List;


public class WodebaojiaAdapter extends BaseQuickAdapter<BaojiaBean, BaseViewHolder> {
    public WodebaojiaAdapter(int layoutResId, @Nullable List<BaojiaBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaojiaBean item) {

        helper.setText(R.id.item_wode_qiugou_name, item.getEnquiryDomain().getProductName())
        .setText(R.id.item_wode_qiugou_time, item.getOfferTimeString())
                .setText(R.id.item_wode_qiugou_number, item.getEnquiryDomain().getNum() + "")
                .setText(R.id.item_wode_qiugou_address, item.getEnquiryDomain().getLocationProvince() + item.getEnquiryDomain().getLocationCity())
                .setText(R.id.item_wode_qiugou_pack, item.getEnquiryDomain().getPack())
                .setText(R.id.item_wode_qiugou_price, item.getPrice())
                .setText(R.id.item_wode_baojia_beizhu, item.getDescription())
                .setText(R.id.item_wode_qiugou_chakan_number, item.getEnquiryDomain().getOfferNum() + "")
                .addOnClickListener(R.id.item_wode_all_qiugouliebiao);

        if (StringUtils.equals(item.getIsIncludeTrans(), "1")) {
            helper.setText(R.id.item_wode_qiugou_postage, "包含运费");
        } else helper.setText(R.id.item_wode_qiugou_postage, "不含运费");

        helper.getView(R.id.item_wode_qiugou_constrainlayout).setVisibility(View.GONE);
        helper.getView(R.id.item_wode_baojia_constrainlayout).setVisibility(View.VISIBLE);

        TextView shenfen = (TextView) helper.getView(R.id.item_wode_baojia_shenfen);
        shenfen.setVisibility(View.VISIBLE);
        if (StringUtils.equals(mContext.getResources().getString(R.string.qiyefabu), item.getEnquiryDomain().getPublishType())) {

            shenfen.setText(item.getEnquiryDomain().getPublishType());
            shenfen.setBackground(mContext.getResources().getDrawable(R.mipmap.qiyeyonghu));
        } else if (StringUtils.equals(mContext.getResources().getString(R.string.gerenfabu), item.getEnquiryDomain().getPublishType())) {
            shenfen.setText(item.getEnquiryDomain().getPublishType());
            shenfen.setBackground(mContext.getResources().getDrawable(R.mipmap.gerenfabu));
        }


        if (StringUtils.equals("0", item.getStatus())) {
            helper.getView(R.id.item_wode_qiugou_status_text).setVisibility(View.GONE);
            helper.getView(R.id.item_wode_qiugou_time_layout).setVisibility(View.VISIBLE);

        }
        if (StringUtils.equals("1", item.getStatus())) {
            helper.getView(R.id.item_wode_qiugou_time_layout).setVisibility(View.GONE);
            helper.getView(R.id.item_wode_qiugou_status_text).setVisibility(View.VISIBLE);
            helper.setText(R.id.item_wode_qiugou_status_text, "已完成");

        }

        if (StringUtils.equals("2", item.getStatus())) {
            helper.getView(R.id.item_wode_qiugou_time_layout).setVisibility(View.GONE);

            helper.getView(R.id.item_wode_qiugou_status_text).setVisibility(View.VISIBLE);
            helper.setText(R.id.item_wode_qiugou_status_text, "已关闭");
        }


        TextView firsttime = (TextView) helper.getView(R.id.item_wode_qiugou_firsttime);
        TextView secondtime = (TextView) helper.getView(R.id.item_wode_qiugou_secondtime);
        TextView firsttimeText = (TextView) helper.getView(R.id.item_wode_qiugou_firsttime_text);
        TextView secondtimeText = (TextView) helper.getView(R.id.item_wode_qiugou_secondtime_text);


        if (StringUtils.isEmpty(item.getEnquiryDomain().getSurplusDay())) {

            if (StringUtils.isEmpty(item.getEnquiryDomain().getSurplusHour())) {


                if (StringUtils.isEmpty(item.getEnquiryDomain().getSurplusMin())) {

                } else {
                    firsttime.setText(item.getEnquiryDomain().getSurplusMin());
                    secondtime.setText(item.getEnquiryDomain().getSurplusSec());
                    firsttimeText.setText("分");
                    secondtimeText.setText("秒");
                }

            } else {

                firsttime.setText(item.getEnquiryDomain().getSurplusHour());
                secondtime.setText(item.getEnquiryDomain().getSurplusMin());
                firsttimeText.setText("小时");
                secondtimeText.setText("分钟");

            }

        } else {

            firsttime.setText(item.getEnquiryDomain().getSurplusDay());
            secondtime.setText(item.getEnquiryDomain().getSurplusHour());


        }


    }
}
