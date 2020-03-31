package com.company.qcy.huodong.daixiao.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.huodong.daixiao.bean.DaixiaoBean;

import java.util.List;

public class DaixiaoListAdapter extends BaseQuickAdapter<DaixiaoBean
        , BaseViewHolder> {


    public DaixiaoListAdapter(int layoutResId, @Nullable List<DaixiaoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DaixiaoBean item) {

        helper.setText(R.id.item_daixiao_name,item.getName());

        ImageView img = (ImageView) helper.getView(R.id.item_daixiao_img);
        GlideUtils.loadImage(mContext, ServerInfo.IMAGE + item.getProductPic(),img);

        if(!ObjectUtils.isEmpty(item.getPrice())){
            helper.setText(R.id.item_daixiao_price,item.getPrice()+"");
        }

        helper.setText(R.id.item_daixiao_priceUnit,"元/"+item.getPriceUnit());

        helper.setText(R.id.item_daixiao_pack,item.getPack());

        helper.setText(R.id.item_daixiao_remainNum,item.getRemainNum()+item.getNumUnit());


    }
}
