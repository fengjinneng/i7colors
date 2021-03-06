package com.company.qcy.adapter.chanpindating;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.bean.chanpin.ProductBean;

import java.util.List;

public class ChanpindatingRecyclerViewAdapter extends
        BaseQuickAdapter<ProductBean, BaseViewHolder> {
    public ChanpindatingRecyclerViewAdapter(int layoutResId,
                                            @Nullable List<ProductBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductBean item) {

        helper.setText(R.id.item_chanpindating_companyName, item.getCompanyName()).addOnClickListener(R.id.item_chanpindating_yijianhujiao);

        ImageView imageView = (ImageView) helper.getView(R.id.item_chanpindating_img);
        GlideUtils.loadImage(mContext,ServerInfo.IMAGE+item.getPic(),imageView);
        helper.setText(R.id.item_chanpindating_productName, item.getProductName());

        //价格为空
        if (item.isDisplayPrice()) {

            helper.getView(R.id.item_chanpindating_yijia_layout).setVisibility(View.GONE);
            helper.getView(R.id.item_chanpindating_jiage_layout).setVisibility(View.VISIBLE);
            helper.setText(R.id.item_chanpindating_price, item.getPrice());

        } else {

            helper.getView(R.id.item_chanpindating_yijia_layout).setVisibility(View.VISIBLE);
            helper.getView(R.id.item_chanpindating_jiage_layout).setVisibility(View.GONE);
        }

    }
}
