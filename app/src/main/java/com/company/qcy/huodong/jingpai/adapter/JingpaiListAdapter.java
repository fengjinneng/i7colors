package com.company.qcy.huodong.jingpai.adapter;

import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.huodong.jingpai.bean.JingpaiBean;

import java.util.List;

public class JingpaiListAdapter extends BaseQuickAdapter<JingpaiBean, BaseViewHolder> {



    public JingpaiListAdapter(int layoutResId, @Nullable List<JingpaiBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, JingpaiBean item) {

        helper.setText(R.id.item_jingpai_list_name,item.getShopName());
        helper.setText(R.id.item_jingpai_list_address,item.getAddress());

        helper.setText(R.id.item_jingpai_list_qipaijiage,item.getPrice());
        helper.setText(R.id.item_jingpai_list_dangqianjiage,item.getMaxPrice());

        ImageView img = (ImageView) helper.getView(R.id.item_jingpai_list_img);
        ImageView statusImg = (ImageView) helper.getView(R.id.item_jingpai_list_status);
        TextView statusText = (TextView) helper.getView(R.id.item_jingpai_list_status_text);

        GlideUtils.loadImage(mContext, ServerInfo.IMAGE+item.getProductPic(),img);

        helper.setText(R.id.item_jingpai_list_qipaijiage_unit,item.getPriceUnit());

        helper.setText(R.id.item_jingpai_list_dangqianjiage_unit,item.getPriceUnit());

        Resources resources = mContext.getResources();

        switch (item.getIsType()){
            case "0"://流派
                statusImg.setImageDrawable(resources.getDrawable(R.mipmap.jingpai_yiliupai));
                statusText.setText("竞拍已流拍 ！");
                statusText.setTextColor(resources.getColor(R.color.baise));
                statusText.setBackgroundColor(resources.getColor(R.color.shenhui));
                break;
            case "1"://未开始
                statusImg.setImageDrawable(resources.getDrawable(R.mipmap.jingpai_weikaishi));
                statusText.setText("尚未开始（活动时间："+item.getStartTime()+" 至 "+item.getEndTime()+"）");
                statusText.setTextColor(resources.getColor(R.color.putongwenben));
                statusText.setBackgroundColor(resources.getColor(R.color.baise));
                break;
            case "2"://惊醒中
                statusImg.setImageDrawable(resources.getDrawable(R.mipmap.jingpai_jinxingzhong));
                statusText.setText("进行中（结束时间："+item.getEndTime()+"）");
                statusText.setTextColor(resources.getColor(R.color.baise));
                statusText.setBackgroundColor(resources.getColor(R.color.endcolor));
                break;
            case "3"://成交
                statusImg.setImageDrawable(resources.getDrawable(R.mipmap.jingpai_yichengjiao));
                statusText.setText("竞拍已成交 !");
                statusText.setTextColor(resources.getColor(R.color.baise));
                statusText.setBackgroundColor(resources.getColor(R.color.lanse));
                break;

        }

    }
}