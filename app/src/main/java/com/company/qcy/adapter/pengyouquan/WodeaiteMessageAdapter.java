package com.company.qcy.adapter.pengyouquan;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.MyCommonUtil;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.bean.pengyouquan.WodeaiteMessageBean;

import java.util.List;

public class WodeaiteMessageAdapter extends BaseQuickAdapter<WodeaiteMessageBean, BaseViewHolder> {


    public WodeaiteMessageAdapter(int layoutResId, @Nullable List<WodeaiteMessageBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WodeaiteMessageBean item) {


        TextView name = (TextView) helper.getView(R.id.item_aite_message_name);
        name.setText(item.getPostUserName());

        TextView time = (TextView) helper.getView(R.id.item_aite_message_time);
        if (!ObjectUtils.isEmpty(item.getCreatedAtStamp())) {
            time.setText(TimeUtils.millis2String(Long.parseLong(item.getCreatedAtStamp()))
                    .substring(0, 16) );
        }

        ImageView img = (ImageView) helper.getView(R.id.item_aite_message_img);

        TextView secondname = (TextView) helper.getView(R.id.item_aite_message_secondname);
        ImageView bigV = (ImageView) helper.getView(R.id.item_aite_message_bigV);

        MyCommonUtil.jiazaitouxiang(mContext,item.getPostUserPhoto(),img);

        //判断是否是公司和打V认证
        MyCommonUtil.isDaVOrCompanyAndSetBossLevel(mContext, secondname, item.getPostUserIsCompany(), item.getPostUserCompanyName(),
                item.getPostUserIsDyeV(), item.getPostUserDyeVName(), bigV,item.getPostUserName(),item.getBossLevel(),name);

        TextView weiduxiaoxi = (TextView) helper.getView(R.id.item_aite_message_weiduxiaoxi);
        if(StringUtils.equals("1",item.getIsRead())){
            weiduxiaoxi.setVisibility(View.GONE);
        }else {
            weiduxiaoxi.setVisibility(View.VISIBLE);
        }
    }
}
