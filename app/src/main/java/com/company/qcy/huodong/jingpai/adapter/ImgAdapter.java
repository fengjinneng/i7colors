package com.company.qcy.huodong.jingpai.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.huodong.jingpai.bean.JingpaiDetailBean;

import java.util.List;

public class ImgAdapter extends BaseQuickAdapter<JingpaiDetailBean.DetailListBean, BaseViewHolder> {


    public ImgAdapter(int layoutResId, @Nullable List<JingpaiDetailBean.DetailListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, JingpaiDetailBean.DetailListBean item) {

        ImageView imageView = (ImageView) helper.getView(R.id.item_jingpai_img_img);

//        GlideUtils.loadImage(mContext, ServerInfo.IMAGE+item.getDetailPcPic(),imageView);
        Glide.with(mContext).load(ServerInfo.IMAGE+item.getDetailPcPic()).into(imageView);


    }
}
