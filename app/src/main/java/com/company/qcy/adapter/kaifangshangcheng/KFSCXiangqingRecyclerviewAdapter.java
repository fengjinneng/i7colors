package com.company.qcy.adapter.kaifangshangcheng;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.bean.kaifangshangcheng.DianpuxiangqingBean;
import com.company.qcy.bean.kaifangshangcheng.ProductBean;

import java.util.List;

public class KFSCXiangqingRecyclerviewAdapter extends
        BaseQuickAdapter<ProductBean, BaseViewHolder> {
    public KFSCXiangqingRecyclerviewAdapter(int layoutResId, @Nullable List<ProductBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductBean item) {

        helper.setText(R.id.item_kfsc_xiangqing_name,item.getProductName());
        if(item.isDisplayPrice()){
            helper.setText(R.id.item_kfsc_xiangqing_price,item.getPrice());
            helper.getView(R.id.item_kfsc_xiangqing_price_fuhao).setVisibility(View.VISIBLE);
            helper.getView(R.id.item_kfsc_xiangqing_price_danwei).setVisibility(View.VISIBLE);
        }else {
            helper.setText(R.id.item_kfsc_xiangqing_price,"议价");
            helper.getView(R.id.item_kfsc_xiangqing_price_fuhao).setVisibility(View.INVISIBLE);
            helper.getView(R.id.item_kfsc_xiangqing_price_danwei).setVisibility(View.INVISIBLE);
        }
        helper.setText(R.id.item_kfsc_xiangqing_baozhuang,item.getPack());

        ImageView imageView = (ImageView) helper.getView(R.id.item_kfsc_xiangqing_img);
        GlideUtils.loadImage(mContext,ServerInfo.IMAGE+item.getPic(),imageView);

    }
}
