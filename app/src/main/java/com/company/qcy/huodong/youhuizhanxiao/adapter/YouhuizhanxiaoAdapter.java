package com.company.qcy.huodong.youhuizhanxiao.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.huodong.youhuizhanxiao.bean.YouhuizhanxiaoBean;

import java.util.List;

public class YouhuizhanxiaoAdapter extends BaseQuickAdapter<YouhuizhanxiaoBean, BaseViewHolder> {


    public YouhuizhanxiaoAdapter(int layoutResId, @Nullable List<YouhuizhanxiaoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, YouhuizhanxiaoBean item) {

        helper.setText(R.id.item_youhuizhanxiao_liebiao_productname,item.getProductName());
        helper.setText(R.id.item_youhuizhanxiao_liebiao_totalnum,item.getTotalNum()+item.getNumUnit());

        helper.setText(R.id.item_youhuizhanxiao_liebiao_oldprice,item.getOldPrice());
        helper.setText(R.id.item_youhuizhanxiao_liebiao_newprice,item.getNewPrice());

        helper.setText(R.id.item_youhuizhanxiao_liebiao_oldprice_unit,item.getPriceUnit());
        helper.setText(R.id.item_youhuizhanxiao_liebiao_newprice_unit,item.getPriceUnit());

        ImageView imageView = (ImageView) helper.getView(R.id.item_youhuizhanxiao_liebiao_img);
        GlideUtils.loadImage(mContext, ServerInfo.IMAGE+item.getProductPic(),imageView);
    }
}
