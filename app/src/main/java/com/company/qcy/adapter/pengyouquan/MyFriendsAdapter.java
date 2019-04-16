package com.company.qcy.adapter.pengyouquan;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.bean.pengyouquan.MyFriendsBean;

import java.util.List;

public class MyFriendsAdapter extends BaseQuickAdapter<MyFriendsBean, BaseViewHolder> {


    public MyFriendsAdapter(int layoutResId, @Nullable List<MyFriendsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyFriendsBean item) {

        TextView name = (TextView) helper.getView(R.id.item_myfriends_name);

        TextView companyName = (TextView) helper.getView(R.id.item_myfriends_company);

        name.setText(item.getUserNickName());
//        companyName.setText(item.getCompanyName());

        helper.setText(R.id.item_myfriends_date, TimeUtils.millis2String(Long.parseLong(item.getCreatedAtStamp())).substring(0, 10));


        ImageView img = (ImageView) helper.getView(R.id.item_myfriends_img);
        GlideUtils.loadCircleImage(mContext,ServerInfo.IMAGE+item.getUserCommunityPhoto(),img);

        if(StringUtils.equals("1",item.getIsDyeV())){
            helper.getView(R.id.item_myfriends_bigv).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.item_myfriends_bigv).setVisibility(View.GONE);
        }

        helper.addOnClickListener(R.id.item_myfriends_guanzhu);

        if(StringUtils.equals("1",item.getIsFollow())){
            helper.setBackgroundRes(R.id.item_myfriends_guanzhu, R.mipmap.yiguanzhu);
            helper.setText(R.id.item_myfriends_guanzhu, "");
        }else {
            helper.setBackgroundRes(R.id.item_myfriends_guanzhu, R.drawable.background_pengyouquan_weirenzheng);
            helper.setText(R.id.item_myfriends_guanzhu, "+关注");
        }


    }
}
