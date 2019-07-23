package com.company.qcy.huodong.tuangou.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.huodong.tuangou.bean.TuangouBean;

import java.util.List;

public class WodeTuangouListAdapter extends
        BaseQuickAdapter<TuangouBean, BaseViewHolder> {


    public WodeTuangouListAdapter(int layoutResId, @Nullable List<TuangouBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TuangouBean item) {

        if (!StringUtils.isEmpty(item.getProductName())) {
            helper.setText(R.id.item_wodetuangou_list_name, item.getProductName());
        }

        if (!StringUtils.isEmpty(item.getNum())) {
            helper.setText(R.id.item_wodetuangou_list_goumailiang, item.getNum());
        }

        if (!StringUtils.isEmpty(item.getNumUnit())) {
            helper.setText(R.id.item_wodetuangou_list_goumailiang_unit, item.getNumUnit());
        }

        if (!StringUtils.isEmpty(item.getNewPrice())) {
            helper.setText(R.id.item_wodetuangou_list_price, item.getNewPrice());
        }

        if (!StringUtils.isEmpty(item.getPriceUnit())) {
            helper.setText(R.id.item_wodetuangou_list_price_unit, "元/"+item.getPriceUnit());
        }
        ImageView imageView = (ImageView) helper.getView(R.id.item_wodetuangou_list_img);

        if (!StringUtils.isEmpty(item.getProductPic())) {
            Glide.with(mContext).load(ServerInfo.IMAGE + item.getProductPic()).into(imageView);
        }
    }
}
