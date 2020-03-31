package com.company.qcy.huodong.jingpai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.Utils.UserUtil;
import com.company.qcy.adapter.BaseViewpageAdapter;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.huodong.jingpai.bean.JingpaiDetailBean;
import com.company.qcy.huodong.jingpai.fragment.ChujiajiluFragment;
import com.company.qcy.huodong.jingpai.fragment.JingpaixuzhiFragment;
import com.company.qcy.huodong.jingpai.fragment.PaipinmiaoshuFragment;
import com.company.qcy.ui.activity.user.LoginActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import java.util.ArrayList;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;

public class JingpaiDetailActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private ImageView mActivityJingpaidetailImg;
    private ImageView mActivityJingpaidetailStatus;
    /**
     * E带式数码印花机 / 9成新 / E500-180
     */
    private TextView mActivityJingpaidetailProductname;
    private CountdownView mActivityJingpaidetailCountdownView;
    /**
     * 500
     */
    private TextView mActivityJingpaidetailXianjia;
    /**
     * 买家自提
     */
    private TextView mActivityJingpaidetailYunfei;
    /**
     * 安诺其安诺其
     */
    private TextView mActivityJingpaidetailChangjia;
    /**
     * 2018-11-23
     */
    private TextView mActivityJingpaidetailRiqi;
    /**
     * sadsadadadsaaddasdsadsadsadsada
     */
    private TextView mActivityJingpaidetailAddress;
    /**
     * 自定义1:
     */
    private TextView mActivityJingpaidetailZidingyi1Key;
    /**
     * 自定义2:
     */
    private TextView mActivityJingpaidetailZidingyi2Key;
    /**
     * TextView
     */
    private TextView mActivityJingpaidetailZidingyi1Value;
    /**
     * TextView
     */
    private TextView mActivityJingpaidetailZidingyi2Value;
    private TabLayout mTabLayout;
    private ViewPager mViewpager;
    private BaseViewpageAdapter viewpageAdapter;
    private String id;
    /**
     * 距离结束:
     */
    private TextView mActivityJingpaidetailJulijieshuText;
    private TextView mActivityJingpaidetailFaqiren;
    private ConstraintLayout mActivityJingpaidetailFaqirenLayout;
    /**
     * 暂无
     */
    private TextView mActivityJingpaidetailPhone;
    /**
     * 是否含运费:
     */
    private TextView mActivityJingpaidetailIsContainsYunfei;
    /**
     * 总量:
     */
    private TextView mActivityJingpaidetailTotalNumText;
    private TextView mActivityJingpaidetailTotalNum;
    private TextView mActivityJingpaidetailXianjiaUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jingpai_detail);
        id = getIntent().getStringExtra("id");
        initView();
    }

    private void initView() {
        mActivityJingpaidetailIsContainsYunfei = (TextView) findViewById(R.id.activity_jingpaidetail_is_contains_yunfei);
        mActivityJingpaidetailTotalNumText = (TextView) findViewById(R.id.activity_jingpaidetail_totalNum_text);
        mActivityJingpaidetailTotalNum = (TextView) findViewById(R.id.activity_jingpaidetail_totalNum);
        mActivityJingpaidetailFaqiren = (TextView) findViewById(R.id.activity_jingpaidetail_faqiren);
        mActivityJingpaidetailFaqirenLayout = (ConstraintLayout) findViewById(R.id.activity_jingpaidetail_faqiren_layout);
        mActivityJingpaidetailPhone = (TextView) findViewById(R.id.activity_jingpaidetail_phone);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mActivityJingpaidetailJulijieshuText = (TextView) findViewById(R.id.activity_jingpaidetail_julijieshu_text);
        mActivityJingpaidetailImg = (ImageView) findViewById(R.id.activity_jingpaidetail_img);
        mActivityJingpaidetailStatus = (ImageView) findViewById(R.id.activity_jingpaidetail_status);
        mActivityJingpaidetailProductname = (TextView) findViewById(R.id.activity_jingpaidetail_productname);
        mActivityJingpaidetailCountdownView = (CountdownView) findViewById(R.id.activity_jingpaidetail_countdownView);
        mActivityJingpaidetailXianjia = (TextView) findViewById(R.id.activity_jingpaidetail_xianjia);
        mActivityJingpaidetailYunfei = (TextView) findViewById(R.id.activity_jingpaidetail_yunfei);
        mActivityJingpaidetailChangjia = (TextView) findViewById(R.id.activity_jingpaidetail_changjia);
        mActivityJingpaidetailRiqi = (TextView) findViewById(R.id.activity_jingpaidetail_riqi);
        mActivityJingpaidetailAddress = (TextView) findViewById(R.id.activity_jingpaidetail_address);
        mActivityJingpaidetailZidingyi1Key = (TextView) findViewById(R.id.activity_jingpaidetail_zidingyi1_key);
        mActivityJingpaidetailZidingyi2Key = (TextView) findViewById(R.id.activity_jingpaidetail_zidingyi2_key);
        mActivityJingpaidetailZidingyi1Value = (TextView) findViewById(R.id.activity_jingpaidetail_zidingyi1_value);
        mActivityJingpaidetailZidingyi2Value = (TextView) findViewById(R.id.activity_jingpaidetail_zidingyi2_value);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);
        mViewpager.setOffscreenPageLimit(2);
        mToolbarTitle.setText("抢购详情");

        mActivityJingpaidetailXianjiaUnit = (TextView) findViewById(R.id.activity_jingpaidetail_xianjiaUnit);
        addData();

    }


    private JingpaiDetailBean bean;

    private void addData() {
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.JINGPAIDETAIL)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("id", id);

        DialogStringCallback stringCallback = new DialogStringCallback(this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("JINGPAIDETAIL", response.body());
                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            bean = data.toJavaObject(JingpaiDetailBean.class);
                            setInfo();
                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(JingpaiDetailActivity.this, request, this);
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

    private void setInfo() {

        if (ObjectUtils.isEmpty(bean)) {
            return;
        }

        if (StringUtils.isTrimEmpty(bean.getAddress())) {
            if (StringUtils.isEmpty(bean.getSourceOfSupply())) {
                mActivityJingpaidetailAddress.setText("暂无信息");
            } else {
                mActivityJingpaidetailAddress.setText(bean.getSourceOfSupply());
            }
        } else {
            mActivityJingpaidetailAddress.setText(bean.getAddress());
        }

        if (!StringUtils.isEmpty(bean.getFrom()) && StringUtils.equals("pc", bean.getFrom())) {
            mActivityJingpaidetailFaqirenLayout.setVisibility(View.VISIBLE);

            if (StringUtils.isEmpty(bean.getCompanyName())) {
                mActivityJingpaidetailFaqiren.setText("暂无");

            } else {
                mActivityJingpaidetailFaqiren.setText(bean.getCompanyName());

            }
        } else {
            mActivityJingpaidetailFaqirenLayout.setVisibility(View.GONE);
        }

        if (!StringUtils.isEmpty(bean.getPhone()) && StringUtils.equals("pc", bean.getFrom())) {
            mActivityJingpaidetailPhone.setText(bean.getPhone());
        } else {
            mActivityJingpaidetailPhone.setText("暂无");

        }


        if (StringUtils.isEmpty(bean.getNum())) {
            mActivityJingpaidetailTotalNum.setVisibility(View.GONE);
            mActivityJingpaidetailTotalNumText.setVisibility(View.GONE);
        } else {
            mActivityJingpaidetailTotalNum.setText(bean.getNum() + bean.getNumUnit());
        }

        mActivityJingpaidetailProductname.setText(bean.getShopName());
        mActivityJingpaidetailXianjia.setText(bean.getPrice());
        mActivityJingpaidetailXianjiaUnit.setText(bean.getPriceUnit());

        if (StringUtils.isEmpty(bean.getIsFreight())) {
            mActivityJingpaidetailYunfei.setVisibility(View.GONE);
            mActivityJingpaidetailIsContainsYunfei.setVisibility(View.GONE);

        } else {
            if (StringUtils.equals("1", bean.getIsFreight())) {
                mActivityJingpaidetailYunfei.setText("是");
            } else {
                mActivityJingpaidetailYunfei.setText("否");
            }
        }
        mActivityJingpaidetailChangjia.setText(bean.getManufacturer());
        mActivityJingpaidetailRiqi.setText(bean.getDateOfProduction());

        if (!StringUtils.isEmpty(bean.getAuctionDetails())) {
            mActivityJingpaidetailZidingyi1Key.setVisibility(View.VISIBLE);
            mActivityJingpaidetailZidingyi1Value.setVisibility(View.VISIBLE);
            mActivityJingpaidetailZidingyi1Key.setText(bean.getAuctionDetails() + ":");
            mActivityJingpaidetailZidingyi1Value.setText(bean.getDetailsValue());
        }
        if (!StringUtils.isEmpty(bean.getAuctionDetails1())) {
            mActivityJingpaidetailZidingyi2Key.setVisibility(View.VISIBLE);
            mActivityJingpaidetailZidingyi2Value.setVisibility(View.VISIBLE);
            mActivityJingpaidetailZidingyi2Key.setText(bean.getAuctionDetails1() + ":");
            mActivityJingpaidetailZidingyi2Value.setText(bean.getDetailsValue1());
        }

        GlideUtils.loadImage(this, ServerInfo.IMAGE + bean.getProductPic(), mActivityJingpaidetailImg);

        switch (bean.getIsType()) {
            case "0"://流派
                mActivityJingpaidetailStatus.setImageDrawable(getResources().getDrawable(R.mipmap.toupiao_yijieshu));
                break;
            case "1"://未开始
                mActivityJingpaidetailStatus.setImageDrawable(getResources().getDrawable(R.mipmap.jingpai_weikaishi));
                break;
            case "2"://惊醒中
                mActivityJingpaidetailJulijieshuText.setVisibility(View.VISIBLE);
                mActivityJingpaidetailCountdownView.setVisibility(View.VISIBLE);
                mActivityJingpaidetailCountdownView.start(Long.parseLong(bean.getOverTime()) - TimeUtils.getNowMills());
                mActivityJingpaidetailStatus.setImageDrawable(getResources().getDrawable(R.mipmap.jingpai_jinxingzhong));
                break;
            case "3"://成交
                mActivityJingpaidetailStatus.setImageDrawable(getResources().getDrawable(R.mipmap.jingpai_yichengjiao));
                break;
        }

            setFragment();
    }

    private void setFragment() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(PaipinmiaoshuFragment.newInstance(bean));
        fragments.add(JingpaixuzhiFragment.newInstance(bean));
//        fragments.add(ChujiajiluFragment.newInstance(bean.getId() + ""));
        List<String> datas = new ArrayList<>();
        datas.add("商品详情");
        datas.add("抢购须知");
//        datas.add("抢购记录");
        viewpageAdapter = new BaseViewpageAdapter(getSupportFragmentManager(), fragments, datas);
        mViewpager.setAdapter(viewpageAdapter);
        mTabLayout.setupWithViewPager(mViewpager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                finish();
                break;
//            case R.id.activity_jingpai_woyaojingpai:
//
//                if (UserUtil.isLogin()) {
//                    Intent intent = new Intent(this, CanyujingpaiActivity.class);
//                    intent.putExtra("auctionId", id);
//                    intent.putExtra("jiajiafudu", bean.getAddPrice());
//                    intent.putExtra("priceUnit", bean.getPriceUnit());
//                    intent.putExtra("maxPrice", bean.getMaxPrice());
//                    intent.putExtra("count", bean.getCount());
//                    ActivityUtils.startActivity(intent);
//                } else {
//                    ActivityUtils.startActivity(LoginActivity.class);
//                }
//
//                break;
        }
    }
}
