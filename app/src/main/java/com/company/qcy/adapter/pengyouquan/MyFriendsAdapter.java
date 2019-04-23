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
import com.company.qcy.Utils.MyCommonUtil;
import com.company.qcy.bean.pengyouquan.MyFriendsBean;

import java.util.List;

public class MyFriendsAdapter extends BaseQuickAdapter<MyFriendsBean, BaseViewHolder> {


    public MyFriendsAdapter(int layoutResId, @Nullable List<MyFriendsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyFriendsBean item) {

        TextView name = (TextView) helper.getView(R.id.item_myfriends_name);
        name.setText(item.getUserNickName());
        TextView secondName = (TextView) helper.getView(R.id.item_myfriends_secondname);

        helper.setText(R.id.item_myfriends_date, TimeUtils.millis2String(Long.parseLong(item.getCreatedAtStamp())).substring(0, 10));

        ImageView headImg = (ImageView) helper.getView(R.id.item_myfriends_img);

        MyCommonUtil.jiazaitouxiang(mContext,item.getUserCommunityPhoto(),headImg);

        //判断是否是公司和打V认证
        MyCommonUtil.isDaVOrCompanyAndSetBossLevel(mContext,secondName,item.getIsCompany(),item.getCompanyName(),
                item.getIsDyeV(),item.getDyeVName(),helper.getView(R.id.item_myfriends_bigv),item.getUserNickName(),item.getBossLevel(),name);

        helper.addOnClickListener(R.id.item_myfriends_guanzhu);

    }
}
