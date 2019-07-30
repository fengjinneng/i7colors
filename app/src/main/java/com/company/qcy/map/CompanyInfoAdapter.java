package com.company.qcy.map;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.CoordinateConverter;
import com.amap.api.location.DPoint;
import com.amap.api.maps.model.LatLng;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.ServerInfo;

import java.util.List;

public class CompanyInfoAdapter extends BaseQuickAdapter<MarketMapBean, BaseViewHolder> {


    private DPoint myPoint;
    private DPoint endPoint;

    public void setMyPoint(DPoint myPoint) {
        this.myPoint = myPoint;
    }

    public CompanyInfoAdapter(int layoutResId, @Nullable List<MarketMapBean> data) {
        super(layoutResId, data);
        endPoint = new DPoint();
    }

    @Override
    protected void convert(BaseViewHolder helper, MarketMapBean item) {


        TextView chakanluxian = (TextView) helper.getView(R.id.item_qcymap_company_info_chakanluxian);
        helper.addOnClickListener(R.id.item_qcymap_company_info_chakanluxian);

        if (!StringUtils.isEmpty(item.getCompanyName())) {
            helper.setText(R.id.item_qcymap_company_info_companyname, item.getCompanyName());
        }

        if (StringUtils.equals("market", item.getFrom())) {
            helper.getView(R.id.item_qcymap_company_info_is_tuijian).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.item_qcymap_company_info_is_tuijian).setVisibility(View.GONE);
        }

        ImageView imageView = (ImageView) helper.getView(R.id.item_qcymap_company_info_img);


        if (!ObjectUtils.isEmpty(item.getMarket())) {
            if (!StringUtils.isEmpty(item.getMarket().getPhone())) {
                helper.setText(R.id.item_qcymap_company_info_phone, item.getMarket().getPhone());
            }
            if (!StringUtils.isEmpty(item.getMarket().getLogo())) {

                Glide.with(mContext).
                        load(ServerInfo.IMAGE + item.getMarket().getLogo()).into(imageView);
            }else {
                imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.place_500x500));
            }

        }

        if (StringUtils.isEmpty(item.getProvince()) || StringUtils.isEmpty(item.getCity())) {

        } else {
            helper.setText(R.id.item_qcymap_company_info_diqu, item.getProvince() + " " + item.getCity());
        }

        if(!StringUtils.isEmpty(item.getLatitude())&&!StringUtils.isEmpty(item.getLongitude())
                &&!StringUtils.equals("0.0",item.getLatitude())
                &&!StringUtils.equals("0.0",item.getLongitude())){
            chakanluxian.setVisibility(View.VISIBLE);
            endPoint.setLatitude(Double.parseDouble(item.getLatitude()));
            endPoint.setLongitude(Double.parseDouble(item.getLongitude()));

            float distance = CoordinateConverter.calculateLineDistance(myPoint,
                    endPoint);

            if(distance>1000){

                helper.setText(R.id.item_qcymap_company_info_distance, "距离:"+Math.round(distance/1000) + "km");

            }else {
                helper.setText(R.id.item_qcymap_company_info_distance, "距离:"+Math.round(distance) + "m");
            }

        }else {
            helper.setText(R.id.item_qcymap_company_info_distance, "");
            chakanluxian.setVisibility(View.GONE);
        }
    }
}
