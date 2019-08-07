package com.company.qcy.adapter.zhuji;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.bean.zhuji.ZhujiBean;

import java.util.Date;
import java.util.List;

public class ZhujiListAdapter extends BaseQuickAdapter<ZhujiBean, BaseViewHolder> {


    public ZhujiListAdapter(int layoutResId, @Nullable List<ZhujiBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ZhujiBean item) {

        helper.addOnClickListener(R.id.item_zhujidingzhi_list_yijianhujiao);

        if (StringUtils.isEmpty(item.getZhujiName())) {

        } else {
            helper.setText(R.id.item_zhujidingzhi_list_name, item.getZhujiName());
        }

        if (ObjectUtils.isEmpty(item.getSolution_num())) {
            helper.setText(R.id.item_zhujidingzhi_list_number, 0);
        } else {
            helper.setText(R.id.item_zhujidingzhi_list_number, item.getSolution_num()+"");
        }

        if (StringUtils.isEmpty(item.getClassName())) {
            helper.setText(R.id.item_zhujidingzhi_list_type, "暂无分类");
        } else {
            helper.setText(R.id.item_zhujidingzhi_list_type, item.getClassName());
        }

        TextView shenfen = (TextView) helper.getView(R.id.item_zhujidingzhi_list_shenfen);
        if (StringUtils.isEmpty(item.getPublishType())) {
            shenfen.setVisibility(View.GONE);
        } else {
            shenfen.setVisibility(View.VISIBLE);
            shenfen.setText(item.getPublishType());
            if (StringUtils.equals("个人发布", item.getPublishType())) {
                shenfen.setBackground(mContext.getResources().getDrawable(R.mipmap.gerenfabu));
            } else if (StringUtils.equals("企业发布", item.getPublishType())) {
                shenfen.setBackground(mContext.getResources().getDrawable(R.mipmap.qiyeyonghu));
            } else {
                shenfen.setVisibility(View.GONE);
            }
        }

        if (StringUtils.equals("1", item.getIsCharger())) {
            helper.getView(R.id.item_zhujidingzhi_list_wodefabu).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.item_zhujidingzhi_list_wodefabu).setVisibility(View.GONE);
        }

        ImageView statsImg = helper.getView(R.id.item_zhujidingzhi_list_status_img);

        if (StringUtils.isEmpty(item.getStatus())) {
            statsImg.setVisibility(View.GONE);
        } else {
            statsImg.setVisibility(View.VISIBLE);
            if (StringUtils.equals("diying", item.getStatus())) {
                helper.getView(R.id.item_zhujidingzhi_list_yiwancheng).setVisibility(View.GONE);
                statsImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.shiyangzhong));
            } else {
                helper.getView(R.id.item_zhujidingzhi_list_yiwancheng).setVisibility(View.VISIBLE);
                statsImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.bijiawancheng));
            }
        }

        TextView qian = (TextView) helper.getView(R.id.item_zhujidingzhi_list_qian);
        TextView qianUnit = (TextView) helper.getView(R.id.item_zhujidingzhi_list_qian_unit);
        TextView hou = (TextView) helper.getView(R.id.item_zhujidingzhi_list_hou);
        TextView houUnit = (TextView) helper.getView(R.id.item_zhujidingzhi_list_hou_unit);

        if (StringUtils.isEmpty(item.getEndTimeStamp())) {
            qian.setText("");
            qianUnit.setText("");
            hou.setText("");
            houUnit.setText("");
        } else {
            Date date = TimeUtils.millis2Date(TimeUtils.string2Millis(item.getEndTimeStamp())-System.currentTimeMillis());
            if (date.getDay() > 0) {
                qian.setText(date.getDay() + "");
                qianUnit.setText("天");
                hou.setText(date.getHours() + "");
                houUnit.setText("时");
            } else {
                if (date.getHours() > 0) {
                    qian.setText(date.getHours() + "");
                    qianUnit.setText("时");
                    hou.setText(date.getMinutes() + "");
                    houUnit.setText("分");

                } else {
                    if(date.getMinutes()>0){
                        qian.setText(date.getMinutes() + "");
                        qianUnit.setText("分");
                        hou.setText(date.getSeconds() + "");
                        houUnit.setText("秒");
                    }else {
                        qian.setText("0");
                        qianUnit.setText("分");
                        hou.setText(date.getSeconds() + "");
                        houUnit.setText("秒");

                    }
                }
            }
        }
    }
}
