package com.company.qcy.live;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
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
    protected void convert(BaseViewHolder helper, LiveBean liveBean) {

        helper.setText(R.id.item_live_title,liveBean.getTitle());
        helper.setText(R.id.item_live_describe,liveBean.getDescription());

        ImageView img = (ImageView) helper.getView(R.id.item_live_img);
//        GlideUtils.loadImageDefault(mContext, ServerInfo.IMAGE + liveBean.getBanner(),img);
        Glide.with(mContext).load(ServerInfo.IMAGE + liveBean.getBanner()).into(img);

        helper.setText(R.id.item_live_author,liveBean.getTeacher());

        TextView price = (TextView) helper.getView(R.id.item_live_price);
        TextView status = (TextView) helper.getView(R.id.item_live_status);

        ImageView imgStatus = (ImageView) helper.getView(R.id.item_live_img_status);

        //直播未开始
        if (StringUtils.equals("2", liveBean.getIsEnd())) {

            status.setText("￥ ");
            price.setVisibility(View.VISIBLE);
            price.setText(liveBean.getPrice()+"");

            imgStatus.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.live_weikaihi));

        } else if (StringUtils.equals("0", liveBean.getIsEnd())) {

            price.setVisibility(View.GONE);
            status.setText("直播已结束");

            imgStatus.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.live_huikan));

        } else if (StringUtils.equals("1", liveBean.getIsEnd())) {
            //进行中
            status.setText("￥ ");
            price.setVisibility(View.VISIBLE);
            price.setText(liveBean.getPrice()+"");

            imgStatus.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.live_zhibozhong));
        }

    }
}
