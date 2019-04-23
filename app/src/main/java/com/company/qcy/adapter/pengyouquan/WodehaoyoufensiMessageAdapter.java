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
import com.company.qcy.Utils.MyCommonUtil;
import com.company.qcy.bean.pengyouquan.WodehaoyoufensiMessageBean;

import java.util.List;

public class WodehaoyoufensiMessageAdapter extends BaseQuickAdapter<WodehaoyoufensiMessageBean, BaseViewHolder> {

    public WodehaoyoufensiMessageAdapter(int layoutResId, @Nullable List<WodehaoyoufensiMessageBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WodehaoyoufensiMessageBean item) {


        TextView name = (TextView) helper.getView(R.id.item_wodehaoyoufensi_message_name);
        name.setText(item.getPostUserName());

        TextView content = (TextView) helper.getView(R.id.item_wodehaoyoufensi_message_content);
        TextView add = (TextView) helper.getView(R.id.item_wodehaoyoufensi_message_add);
        TextView time = (TextView) helper.getView(R.id.item_wodehaoyoufensi_message_time);
        helper.addOnClickListener(R.id.item_wodehaoyoufensi_message_img);

        TextView isRead = (TextView) helper.getView(R.id.item_wodehaoyoufensi_message_weiduxiaoxi);

        if (!ObjectUtils.isEmpty(item.getCreatedAtStamp())) {
            time.setText(TimeUtils.millis2String(Long.parseLong(item.getCreatedAtStamp()))
                    .substring(0, 10));
        }

        if (StringUtils.equals("friend", item.getType())) {
            content.setText("我们已经是好友了");
            add.setVisibility(View.GONE);
        } else {
            content.setText("内容：你好，希望加您为好友相互关注。");
            add.setVisibility(View.VISIBLE);

            if (StringUtils.equals("1", item.getIsFollow())) {
                add.setTextColor(mContext.getResources().getColor(R.color.erjibiaoti));
                add.setBackground(mContext.getResources().getDrawable(R.drawable.background_yuanxingbiankuang));
            } else {
                add.setText("添加");
                add.setTextColor(mContext.getResources().getColor(R.color.baise));
                add.setBackground(mContext.getResources().getDrawable(R.drawable.background_yuanxingbiankuang_red));
                helper.addOnClickListener(R.id.item_wodehaoyoufensi_message_add);
            }

            if (StringUtils.equals("1", item.getIsRead())) {
                isRead.setVisibility(View.GONE);
            } else {
                isRead.setVisibility(View.VISIBLE);
            }
        }

        TextView secondName = (TextView) helper.getView(R.id.item_wodehaoyoufensi_message_secondname);
        ImageView bigV = (ImageView) helper.getView(R.id.item_wodehaoyoufensi_message_bigV);

        //判断是否是公司和打V认证
        MyCommonUtil.isDaVOrCompanyAndSetBossLevel(mContext, secondName, item.getPostUserIsCompany(), item.getPostUserCompanyName(),
                item.getPostUserIsDyeV(), item.getPostUserDyeVName(), bigV, item.getPostUserName(), item.getBossLevel(), name);

        ImageView imageView = (ImageView) helper.getView(R.id.item_wodehaoyoufensi_message_img);
        MyCommonUtil.jiazaitouxiang(mContext, item.getPostUserPhoto(), imageView);

        TextView weiduxiaoxi = (TextView) helper.getView(R.id.item_wodehaoyoufensi_message_weiduxiaoxi);
        if (StringUtils.equals("1", item.getIsRead())) {
            weiduxiaoxi.setVisibility(View.GONE);
        } else {
            weiduxiaoxi.setVisibility(View.VISIBLE);
        }
    }
}
