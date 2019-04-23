package com.company.qcy.ui.activity.pengyouquan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
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
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.MyCommonUtil;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.Utils.UserUtil;
import com.company.qcy.adapter.BaseViewpageAdapter;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.bean.pengyouquan.PYQUserBean;
import com.company.qcy.fragment.pengyouquan.MyFansFragment;
import com.company.qcy.fragment.pengyouquan.PengyouquanRecordFragment;
import com.company.qcy.ui.activity.user.ChoiceHeadImageActivity;
import com.company.qcy.ui.activity.user.LoginActivity;
import com.githang.statusbar.StatusBarCompat;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class PersonInfoActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mActivityPersonInfoImg;

    /**
     * Y.C.Pixel
     */
    private TextView mActivityPersonInfoName;
    /**
     * Noisestorm
     */
    private TextView mActivityPersonInfoNickname;

    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private TabLayout mTabLayout;
    private AppBarLayout mAppbar;
    private ViewPager mViewpager;
    private Long userId;
    /**
     * 未认证
     */
    private TextView mActivityPersonInfoIsRehzheng;
    /**
     * 关注
     */
    private TextView mActivityPersonInfoGuanzhu;
    private ImageView mActivityPersonInfoDavImg;

    private String messageId;//判断是否为已读

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.chunhongse), false);
        messageId = getIntent().getStringExtra("messageId");
        userId = getIntent().getLongExtra("userId", 0);
        initView();
    }

    private void initView() {
        mActivityPersonInfoImg = (ImageView) findViewById(R.id.activity_person_info_img);
        mActivityPersonInfoName = (TextView) findViewById(R.id.activity_person_info_name);
        mActivityPersonInfoNickname = (TextView) findViewById(R.id.activity_person_info_nickname);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mAppbar = (AppBarLayout) findViewById(R.id.appbar);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);

        mActivityPersonInfoIsRehzheng = (TextView) findViewById(R.id.activity_person_info_isRehzheng);
        mActivityPersonInfoGuanzhu = (TextView) findViewById(R.id.activity_person_info_guanzhu);
        mActivityPersonInfoGuanzhu.setOnClickListener(this);
        mActivityPersonInfoDavImg = (ImageView) findViewById(R.id.activity_person_info_dav_img);
        mActivityPersonInfoImg.setOnClickListener(this);
        initToolBarData();
        addData();
    }

    private PYQUserBean userBean;

    private void setData() {

        if (ObjectUtils.isEmpty(userBean)) {
            return;
        }

        MyCommonUtil.jiazaitouxiang(this,userBean.getCommunityPhoto(),mActivityPersonInfoImg);

        if (StringUtils.equals("1", userBean.getIsCompany())) {
            mActivityPersonInfoDavImg.setVisibility(View.VISIBLE);
            mActivityPersonInfoIsRehzheng.setText("已认证");
            mActivityPersonInfoName.setText(userBean.getCompanyName());
        } else {
            if (StringUtils.equals("1", userBean.getIsDyeV())) {
                mActivityPersonInfoDavImg.setVisibility(View.VISIBLE);
                mActivityPersonInfoIsRehzheng.setText("已认证");
                mActivityPersonInfoName.setText(userBean.getDyeVName());
            } else {
                mActivityPersonInfoIsRehzheng.setText("未认证");
            }
        }

        mActivityPersonInfoNickname.setText(userBean.getNickName());

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

        if (!StringUtils.equals("1", userBean.getIsCharger())) {
            mActivityPersonInfoGuanzhu.setVisibility(View.VISIBLE);
            if (StringUtils.equals("1", userBean.getIsFollow())) {
                mActivityPersonInfoGuanzhu.setBackground(getResources().getDrawable(R.mipmap.yiguanzhu));
                mActivityPersonInfoGuanzhu.setText("");
            } else {
                mActivityPersonInfoGuanzhu.setText("+关注");
                mActivityPersonInfoGuanzhu.setBackgroundResource(R.drawable.background_pengyouquan_weirenzheng);
            }
        } else {
            mActivityPersonInfoGuanzhu.setVisibility(View.GONE);
        }
    }

    @Override
    public void onReciveMessage(MessageBean msg) {
        super.onReciveMessage(msg);

    }

    private void addData() {
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.GETUSERINFOBYUSERID)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("messageId",messageId)
                .params("userId", userId)
                .params("token", SPUtils.getInstance().getString("token"));

        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("GETUSERINFOBYUSERID", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            userBean = data.toJavaObject(PYQUserBean.class);
                            isGuanzhu = userBean.getIsFollow();
                            setData();
                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(PersonInfoActivity.this, request, this);
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
        fragments.add(PengyouquanRecordFragment.newInstance(String.valueOf(userId)));
        fragments.add(MyFansFragment.newInstance(String.valueOf(userId)));
        List<String> datas = new ArrayList<>();
        datas.add("朋友圈记录");
        datas.add("粉丝");
        baseViewpageAdapter = new BaseViewpageAdapter(getSupportFragmentManager(), fragments, datas);
        mViewpager.setAdapter(baseViewpageAdapter);
        mTabLayout.setupWithViewPager(mViewpager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void cancelFollow(Long userId) {

        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.CANCLEFOLLOWBYUSERID)
                .tag(this)

                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("token", SPUtils.getInstance().getString("token"))
                .params("byUserId", userId);


        DialogStringCallback stringCallback = new DialogStringCallback(PersonInfoActivity.this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.e("CANCLEFOLLOWBYUSERID", response.body());

                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {

                            Boolean data = jsonObject.getBoolean("data");
                            ToastUtils.showShort(msg);
                            if (data) {
                                EventBus.getDefault().post(new MessageBean(MessageBean.Code.GUANZHUCHENGGONG));
                                mActivityPersonInfoGuanzhu.setBackgroundResource(R.drawable.background_pengyouquan_weirenzheng);
                                mActivityPersonInfoGuanzhu.setText("+关注");
                                isGuanzhu = "0";
                            }
                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(PersonInfoActivity.this, request, this);
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

    private void addFollow(Long followUserId) {

        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.ADDFOLLOWBYUSERID)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("token", SPUtils.getInstance().getString("token"))
                .params("byUserId", followUserId);

        DialogStringCallback stringCallback = new DialogStringCallback(PersonInfoActivity.this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.e("ADDFOLLOWBYUSERID", response.body());

                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");

                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {

                            Boolean data = jsonObject.getBoolean("data");
                            ToastUtils.showShort(msg);
                            if (data) {
                                EventBus.getDefault().post(new MessageBean(MessageBean.Code.GUANZHUCHENGGONG));
                                mActivityPersonInfoGuanzhu.setBackgroundResource(R.mipmap.yiguanzhu);
                                mActivityPersonInfoGuanzhu.setText("");
                                isGuanzhu = "1";
                            }
                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(PersonInfoActivity.this, request, this);
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

    private String isGuanzhu = "-1";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.activity_person_info_guanzhu:
                if (!UserUtil.isLogin()) {
                    ActivityUtils.startActivity(LoginActivity.class);
                    return;
                }
                if (StringUtils.equals("1", isGuanzhu)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PersonInfoActivity.this);
                    builder.setMessage("您是否确定取消关注吗？");
                    builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cancelFollow(userId);
                        }
                    });
                    builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                }

                if (StringUtils.equals("0", isGuanzhu)) {
                    addFollow(userId);
                }

                break;
            case R.id.activity_person_info_img:

                Intent i = new Intent(this,ChoiceHeadImageActivity.class);
                i.putExtra("imgUrl",userBean.getCommunityPhoto());
                ActivityUtils.startActivity(i);

                break;
        }
    }
}
