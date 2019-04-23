package com.company.qcy.adapter.pengyouquan;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.MyCommonUtil;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.bean.pengyouquan.MyFriendsBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TongxunluFriendsAdapter extends BaseQuickAdapter<MyFriendsBean, BaseViewHolder> {


    private List<MyFriendsBean> datas;
    private int layoutId;

    public TongxunluFriendsAdapter(int layoutResId, @Nullable List<MyFriendsBean> data) {
        super(layoutResId, data);
        this.datas = data;
        this.layoutId = layoutResId;

    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        TextView zimu = ((TextView) holder.getView(R.id.item_my_tongxunlu_friends_zimu));
        if (position == 0 || !datas.get(position - 1).getIndex().equals(datas.get(position).getIndex())) {
            zimu.setVisibility(View.VISIBLE);
            zimu.setText(datas.get(position).getIndex());
        } else {
            zimu.setVisibility(View.GONE);
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, MyFriendsBean item) {
        CheckBox checkBox = (CheckBox) helper.getView(R.id.item_my_tongxunlu_friends_checkbox);
        checkBox.setOnCheckedChangeListener(null);//先设置一次CheckBox的选中监听器，传入参数null

        TextView name = (TextView) helper.getView(R.id.item_my_tongxunlu_friends_name);
        TextView secondName = (TextView) helper.getView(R.id.item_my_tongxunlu_friends_secondname);
        ImageView bigV = (ImageView) helper.getView(R.id.item_my_tongxunlu_friends_bigV);
        if (StringUtils.isEmpty(item.getUserNickName())) {
            name.setText(item.getLoginName());
        } else {
            name.setText(item.getUserNickName());
        }
        ImageView img = (ImageView) helper.getView(R.id.item_my_tongxunlu_friends_img);
        MyCommonUtil.jiazaitouxiang(mContext, item.getUserCommunityPhoto(), img);

        if (item.isChecked()) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }

        helper.addOnClickListener(R.id.item_my_tongxunlu_friends_checkbox);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item.setChecked(isChecked);
            }
        });
        //判断是否是公司和打V认证
        MyCommonUtil.isDaVOrCompanyAndSetBossLevel(mContext, secondName, item.getIsCompany(), item.getCompanyName(),
                item.getIsDyeV(), item.getDyeVName(), bigV,item.getUserNickName(),item.getBossLevel(),name);

    }
}
