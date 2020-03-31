package com.company.qcy.live;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.ServerInfo;

import java.util.List;

public class LiveListAdapter extends BaseQuickAdapter<LiveBean
        , BaseViewHolder> {



    public LiveListAdapter(int layoutResId, @Nullable List<LiveBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LiveBean item) {

        helper.setText(R.id.item_live_title,item.getTitle());
        helper.setText(R.id.item_live_describe,item.getDescription());

        ImageView img = (ImageView) helper.getView(R.id.item_live_img);
        GlideUtils.loadImage(mContext, ServerInfo.IMAGE + item.getBanner(),img);


    }
}
