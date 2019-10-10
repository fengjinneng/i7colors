package com.company.qcy.adapter.chanpindating;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.ServerInfo;

import java.util.List;

public class ChanpinImageDetailAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public ChanpinImageDetailAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView imageView = (ImageView) helper.getView(R.id.item_chanpindetail_img_img);
//        GlideUtils.loadImage(mContext,ServerInfo.IMAGE+item,imageView);
        Glide.with(mContext).load(ServerInfo.IMAGE+item).into(imageView);
    }
}
