package com.company.qcy.adapter.chanyezixun;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.bean.ChanpindatingBean;
import com.company.qcy.bean.chanyezixun.NewsBean;

import java.util.List;

public class ChanyezixunRecyclerviewAdapter
        extends BaseQuickAdapter<NewsBean,BaseViewHolder> {
    public ChanyezixunRecyclerviewAdapter(int layoutResId, @Nullable List<NewsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsBean item) {

        helper.setText(R.id.item_chanyexixun_title,item.getTitle());
        ImageView imageView = (ImageView) helper.getView(R.id.item_chanyexixun_img);
        Glide.with(mContext).load(ServerInfo.IMAGE+item.getImg_url()).into(imageView);
        helper.setText(R.id.item_chanyexixun_content,item.getContent_summary());
        helper.setText(R.id.item_chanyexixun_time, new String(item.getNews_date()).substring(0,10));
    }
}


