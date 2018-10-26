package com.company.qcy.adapter.qiugou;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.bean.qiugou.QiugouBean;

import java.util.List;

public class WodeqiugouxiangqingAdapter extends BaseQuickAdapter<QiugouBean, BaseViewHolder> {
    public WodeqiugouxiangqingAdapter(int layoutResId, @Nullable List<QiugouBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QiugouBean item) {


        helper.setText(R.id.item_wode_qiugou_name, item.getProductName())
                .setText(R.id.item_wode_qiugou_time, item.getCreateAtString())
                .setText(R.id.item_wode_qiugou_number, item.getNum() + "")
                .setText(R.id.item_wode_qiugou_address, item.getLocationProvince() + item.getLocationCity())
                .setText(R.id.item_wode_qiugou_pack, item.getPack())
                .setText(R.id.item_wode_qiugou_beizhu, item.getDescription())
                .setText(R.id.item_wode_qiugou_chakan_number, item.getOfferNum() + "")
        .addOnClickListener(R.id.item_wode_all_qiugouliebiao);

        helper.getView(R.id.item_wode_baojia_constrainlayout).setVisibility(View.GONE);
        helper.getView(R.id.item_wode_qiugou_constrainlayout).setVisibility(View.VISIBLE);

        if (StringUtils.equals("1", item.getStatus())) {
            helper.getView(R.id.item_wode_qiugou_status_text).setVisibility(View.GONE);
            helper.getView(R.id.item_wode_qiugou_time_layout).setVisibility(View.VISIBLE);

        }
        if (StringUtils.equals("2", item.getStatus())) {
            helper.getView(R.id.item_wode_qiugou_time_layout).setVisibility(View.GONE);
            helper.getView(R.id.item_wode_qiugou_status_text).setVisibility(View.VISIBLE);
            helper.setText(R.id.item_wode_qiugou_status_text, "已关闭");
        }

        if (StringUtils.equals("3", item.getStatus())) {
            helper.getView(R.id.item_wode_qiugou_time_layout).setVisibility(View.GONE);

            helper.getView(R.id.item_wode_qiugou_status_text).setVisibility(View.VISIBLE);
            helper.setText(R.id.item_wode_qiugou_status_text, "已接受报价");
        }

        TextView firsttime = (TextView) helper.getView(R.id.item_wode_qiugou_firsttime);
        TextView secondtime = (TextView) helper.getView(R.id.item_wode_qiugou_secondtime);
        TextView firsttimeText = (TextView) helper.getView(R.id.item_wode_qiugou_firsttime_text);
        TextView secondtimeText = (TextView) helper.getView(R.id.item_wode_qiugou_secondtime_text);


        if (StringUtils.isEmpty(item.getSurplusDay())) {

            if (StringUtils.isEmpty(item.getSurplusHour())) {


                if (StringUtils.isEmpty(item.getSurplusMin())) {

                } else {
                    firsttime.setText(item.getSurplusMin());
                    secondtime.setText(item.getSurplusSec());
                    firsttimeText.setText("分");
                    secondtimeText.setText("秒");
                }

            } else {

                firsttime.setText(item.getSurplusHour());
                secondtime.setText(item.getSurplusMin());
                firsttimeText.setText("小时");
                secondtimeText.setText("分钟");

            }

        } else {

            firsttime.setText(item.getSurplusDay());
            secondtime.setText(item.getSurplusHour());


        }

    }
}
