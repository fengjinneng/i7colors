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
import com.company.qcy.bean.pengyouquan.MyFansBean;

import java.util.List;

public class MyFansAdapter extends BaseQuickAdapter<MyFansBean, BaseViewHolder> {


    public MyFansAdapter(int layoutResId, @Nullable List<MyFansBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyFansBean item) {

        helper.addOnClickListener(R.id.item_myfans_guanzhu);
        ImageView guanzhu = (ImageView) helper.getView(R.id.item_myfans_guanzhu);

        TextView name = (TextView) helper.getView(R.id.item_myfans_name);
        ImageView headImg = (ImageView) helper.getView(R.id.item_myfans_image);

        name.setText(item.getUserNickName());
        MyCommonUtil.jiazaitouxiang(mContext,item.getUserCommunityPhoto(),headImg);

        helper.setText(R.id.item_myfans_time, TimeUtils.millis2String(Long.parseLong(item.getCreatedAtStamp())).substring(0, 10));

        TextView secondname = (TextView) helper.getView(R.id.item_myfans_secondname);

        //判断是否是公司和打V认证
        MyCommonUtil.isDaVOrCompanyAndSetBossLevel(mContext,secondname,item.getIsCompany(),item.getCompanyName(),
                item.getIsDyeV(),item.getDyeVName(),helper.getView(R.id.item_myfans_bigv),item.getUserNickName(),item.getBossLevel(),name);

        if(StringUtils.equals("1",item.getIsFollow())){
            guanzhu.setImageDrawable(mContext.getResources().getDrawable(R.drawable.buzaiguanzhu));
        }else {
            guanzhu.setImageDrawable(mContext.getResources().getDrawable(R.drawable.jiaguanzhu));
        }

        if(StringUtils.equals("1",item.getIsSelf())){
            guanzhu.setVisibility(View.INVISIBLE);
        }else {
            guanzhu.setVisibility(View.VISIBLE);
        }

    }
}
