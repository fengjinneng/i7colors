package com.company.qcy.ui.activity.pengyouquan;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.MyCommonUtil;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.BaseViewpageAdapter;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.bean.pengyouquan.PYQUserBean;
import com.company.qcy.fragment.pengyouquan.MyFansFragment;
import com.company.qcy.fragment.pengyouquan.MyFriendsFragment;
import com.company.qcy.fragment.pengyouquan.PengyouquanRecordFragment;
import com.githang.statusbar.StatusBarCompat;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class MyPersonInfoActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mActivityPersonInfoImg;
    /**
     * Y.C.Pixel
     */
    private TextView mActivityPersonInfoName;
    /**
     * Noisestorm
     */
    private TextView mActivityPersonInfoNickname;
    /**
     * 点击编辑
     */
    private TextView mActivityPersonInfoEditNickname;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private TabLayout mTabLayout;
    private AppBarLayout mAppbar;
    private ViewPager mViewpager;
    /**
     * 未认证
     */
    private TextView mActivityMyPersonInfoIsrenzheng;
    /**
     * (点击去认证)
     */
    private TextView mActivityMyPersonInfoDianjirenzheng;
    private ImageView mActivityMyPersonInfoDav;
    private TextView xiaoxi;
    private ImageView mActivityMyPersonInfoBack;

    private Badge messageBadge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_person_info);
        initView();
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.chunhongse), false);
    }


    private void initView() {

        xiaoxi = (TextView) findViewById(R.id.activity_my_person_info_xiaoxi);
        mActivityPersonInfoImg = (ImageView) findViewById(R.id.activity_my_person_info_img);
        mActivityPersonInfoName = (TextView) findViewById(R.id.activity_my_person_info_name);
        mActivityPersonInfoNickname = (TextView) findViewById(R.id.activity_my_person_info_nickname);
        mActivityPersonInfoEditNickname = (TextView) findViewById(R.id.activity_my_person_info_edit);
        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mAppbar = (AppBarLayout) findViewById(R.id.appbar);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);
        mActivityPersonInfoEditNickname.setOnClickListener(this);
        mActivityPersonInfoImg.setOnClickListener(this);
        xiaoxi.setOnClickListener(this);
        initToolBarData();
        addData();
        mActivityMyPersonInfoIsrenzheng = (TextView) findViewById(R.id.activity_my_person_info_isrenzheng);
        mActivityMyPersonInfoDianjirenzheng = (TextView) findViewById(R.id.activity_my_person_info_dianjirenzheng);
        mActivityMyPersonInfoDianjirenzheng.setOnClickListener(this);
        mActivityMyPersonInfoDav = (ImageView) findViewById(R.id.activity_my_person_info_dav);
        mActivityMyPersonInfoBack = (ImageView) findViewById(R.id.activity_my_person_info_back);
        mActivityMyPersonInfoBack.setOnClickListener(this);

        messageBadge = new QBadgeView(this).bindTarget(xiaoxi)
                .setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeTextSize(10, true).setExactMode(false);
        messageBadge.setBadgeBackgroundColor(getResources().getColor(R.color.baise));
        messageBadge.setBadgeTextColor(getResources().getColor(R.color.chunhongse));
    }

    private PYQUserBean userBean;


    @Override
    protected void onResume() {
        super.onResume();
        addData();
    }

    private void setData() {
        if (ObjectUtils.isEmpty(userBean)) {
            return;
        }

        mActivityPersonInfoNickname.setText(userBean.getNickName());

        MyCommonUtil.jiazaitouxiang(this,userBean.getCommunityPhoto(),mActivityPersonInfoImg);

        if (StringUtils.equals("1", userBean.getIsCompany())) {
            mActivityPersonInfoName.setText(userBean.getCompanyName());
            mActivityMyPersonInfoIsrenzheng.setText("已认证");
            mActivityMyPersonInfoDav.setVisibility(View.VISIBLE);
        } else {
            if (StringUtils.equals("1", userBean.getIsDyeV())) {
                mActivityPersonInfoName.setText(userBean.getDyeVName());
                mActivityMyPersonInfoIsrenzheng.setText("已认证");
                mActivityMyPersonInfoDav.setVisibility(View.VISIBLE);
            } else if (StringUtils.equals("2", userBean.getIsDyeV())) {
                mActivityPersonInfoName.setText("");
                mActivityMyPersonInfoIsrenzheng.setText("认证审核中...");
                mActivityMyPersonInfoDianjirenzheng.setVisibility(View.GONE);
            } else {
                mActivityPersonInfoName.setText("");
                mActivityMyPersonInfoIsrenzheng.setText("未认证");
                mActivityMyPersonInfoDianjirenzheng.setVisibility(View.VISIBLE);
            }
        }

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(userBean.getNickName() + " .");
        if (StringUtils.equals("1", userBean.getIsCompany()) || StringUtils.equals("1", userBean.getIsDyeV())) {
            //大佬等级1
            if (StringUtils.equals("1", userBean.getBossLevel())) {

                Drawable level1 = getResources().getDrawable(R.mipmap.level_one);
                level1.setBounds(0, 0, level1.getIntrinsicWidth(), level1.getIntrinsicHeight());
                ImageSpan span1 = new ImageSpan(level1, ImageSpan.ALIGN_BASELINE);
                spannableStringBuilder.setSpan(span1,
                        spannableStringBuilder.length() - 1, spannableStringBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                mActivityPersonInfoNickname.setText(spannableStringBuilder);

            }
            if (StringUtils.equals("2", userBean.getBossLevel())) {
                Drawable level2 = getResources().getDrawable(R.mipmap.level_two);
                level2.setBounds(0, 0, level2.getIntrinsicWidth(), level2.getIntrinsicHeight());

                ImageSpan span2 = new ImageSpan(level2, ImageSpan.ALIGN_BASELINE);

                spannableStringBuilder.setSpan(span2,
                        spannableStringBuilder.length() - 1, spannableStringBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                mActivityPersonInfoNickname.setText(spannableStringBuilder);

            }
            if (StringUtils.equals("3", userBean.getBossLevel())) {
                Drawable level3 = getResources().getDrawable(R.mipmap.level_three);
                level3.setBounds(0, 0, level3.getIntrinsicWidth(), level3.getIntrinsicHeight());
                ImageSpan span3 = new ImageSpan(level3, ImageSpan.ALIGN_BASELINE);
                spannableStringBuilder.setSpan(span3,
                        spannableStringBuilder.length() - 1, spannableStringBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                mActivityPersonInfoNickname.setText(spannableStringBuilder);

            }
        }

        messageBadge.setBadgeNumber(Integer.parseInt(userBean.getNotReadMessageCount()));

    }

    @Override
    public void onReciveMessage(MessageBean msg) {
        super.onReciveMessage(msg);

        switch (msg.getCode()) {
            case MessageBean.Code.PENGYOUQUANHEADIMGCHANGE:
                GlideUtils.loadCircleImage(MyPersonInfoActivity.this, ServerInfo.IMAGE + msg.getMeaasge(), mActivityPersonInfoImg);
                break;

            case MessageBean.Code.PENGYOUQUANNICKNAMECHANGE:
                mActivityPersonInfoNickname.setText(msg.getMeaasge());
                break;
        }

    }

    private void addData() {

        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.GETUSERINFOBYTOKEN)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("token", SPUtils.getInstance().getString("token"));

        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("GETUSERINFO", response.body());

                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");

                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            userBean = data.toJavaObject(PYQUserBean.class);
                            setData();
                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(MyPersonInfoActivity.this, request, this);
                            return;
                        }
                        ToastUtils.showShort(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                ToastUtils.showShort(getResources().getString(R.string.NETEXCEPTION));
            }
        };

        request.execute(stringCallback);
    }


    BaseViewpageAdapter baseViewpageAdapter;

    private void initToolBarData() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(PengyouquanRecordFragment.newInstance("mine"));
        fragments.add(new MyFriendsFragment());
        fragments.add(MyFansFragment.newInstance("mine"));
        List<String> datas = new ArrayList<>();
        datas.add("印染圈记录");
        datas.add("好友");
        datas.add("粉丝");
        baseViewpageAdapter = new BaseViewpageAdapter(getSupportFragmentManager(), fragments, datas);
        mViewpager.setAdapter(baseViewpageAdapter);
        mTabLayout.setupWithViewPager(mViewpager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mViewpager.setOffscreenPageLimit(2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_my_person_info_edit:
                if (!ObjectUtils.isEmpty(userBean)) {
                    Intent intent = new Intent(this, MyDyeInfoActivity.class);
                    intent.putExtra("nickName", userBean.getNickName());
                    intent.putExtra("imgUrl", userBean.getCommunityPhoto());
                    ActivityUtils.startActivity(intent);
                }
                break;

            case R.id.activity_my_person_info_img:
                if (!ObjectUtils.isEmpty(userBean)) {
                    Intent i = new Intent(this, MyDyeInfoActivity.class);
                    i.putExtra("nickName", userBean.getNickName());
                    i.putExtra("imgUrl", userBean.getCommunityPhoto());
                    ActivityUtils.startActivity(i);
                }
                break;
            case R.id.activity_my_person_info_dianjirenzheng:
                ActivityUtils.startActivity(DavrenzhengActivity.class);
                break;

            case R.id.activity_my_person_info_xiaoxi:
                ActivityUtils.startActivity(MessageActivity.class);
                break;

            case R.id.activity_my_person_info_back:
                finish();
                break;
        }
    }

}
