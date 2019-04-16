package com.company.qcy.adapter.pengyouquan;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.bean.pengyouquan.MyFriendsBean;

import java.util.List;

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

        if (position == 0 || !datas.get(position - 1).getIndex().equals(datas.get(position).getIndex())) {

            zimu.setVisibility(View.VISIBLE);

            zimu.setText(datas.get(position).getIndex());

        } else {

            zimu.setVisibility(View.GONE);

        }
    }

    TextView zimu;

    @Override
    protected void convert(BaseViewHolder helper, MyFriendsBean item) {

        zimu = ((TextView) helper.getView(R.id.item_my_tongxunlu_friends_zimu));

        TextView name = (TextView) helper.getView(R.id.item_my_tongxunlu_friends_name);
        if (StringUtils.isEmpty(item.getUserNickName())) {
            name.setText(item.getLoginName());
        } else {
            name.setText(item.getUserNickName());
        }

        TextView zhiwei = (TextView) helper.getView(R.id.item_my_tongxunlu_friends_zhiwei);
//        zhiwei.setText(item.getCompanyName());
        ImageView img = (ImageView) helper.getView(R.id.item_my_tongxunlu_friends_img);
        GlideUtils.loadImage(mContext, ServerInfo.IMAGE + item.getUserCommunityPhoto(), img);

        CheckBox checkBox = (CheckBox) helper.getView(R.id.item_my_tongxunlu_friends_checkbox);

        if (item.isChecked()) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }
    }
}
