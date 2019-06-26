package com.company.qcy.huodong.jingpai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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
     * 3
     */
    private TextView mActivityJingpaidetailChujiacishu;
    /**
     * 99.00
     */
    private TextView mActivityJingpaidetailQipaijiage;
    /**
     * 元/KG
     */
    private TextView mActivityJingpaidetailQipaijiageUnit;
    /**
     * 999.00
     */
    private TextView mActivityJingpaidetailDangqianjiage;
    /**
     * 元/KG
     */
    private TextView mActivityJingpaidetailDangqianjiageUnit;
    /**
     * E带式数码印花机 / 9成新 / E500-180
     */
    private TextView mActivityJingpaidetailProductname;
    private CountdownView mActivityJingpaidetailCountdownView;
    /**
     * 500
     */
    private TextView mActivityJingpaidetailJiajiafudu;
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
     * 拍卖已流拍，\n无人参拍！
     */
    private TextView mActivityJingpaidetailYiliupai;
    private ConstraintLayout jiageLayout;
    /**
     * 当前价格:
     */
    private TextView mActivityJingpaidetailDangqianjiageText;
    /**
     * 参与抢购
     */
    private Button mActivityJingpaiWoyaojingpai;
    /**
     * 距离结束:
     */
    private TextView mActivityJingpaidetailJulijieshuText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jingpai_detail);
        id = getIntent().getStringExtra("id");
        initView();
    }

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mActivityJingpaidetailJulijieshuText = (TextView) findViewById(R.id.activity_jingpaidetail_julijieshu_text);
        mActivityJingpaidetailImg = (ImageView) findViewById(R.id.activity_jingpaidetail_img);
        mActivityJingpaidetailStatus = (ImageView) findViewById(R.id.activity_jingpaidetail_status);
        mActivityJingpaidetailChujiacishu = (TextView) findViewById(R.id.activity_jingpaidetail_chujiacishu);
        mActivityJingpaidetailQipaijiage = (TextView) findViewById(R.id.activity_jingpaidetail_qipaijiage);
        mActivityJingpaidetailQipaijiageUnit = (TextView) findViewById(R.id.activity_jingpaidetail_qipaijiage_unit);
        mActivityJingpaidetailDangqianjiage = (TextView) findViewById(R.id.activity_jingpaidetail_dangqianjiage);
        mActivityJingpaidetailDangqianjiageUnit = (TextView) findViewById(R.id.activity_jingpaidetail_dangqianjiage_unit);
        mActivityJingpaidetailProductname = (TextView) findViewById(R.id.activity_jingpaidetail_productname);
        mActivityJingpaidetailCountdownView = (CountdownView) findViewById(R.id.activity_jingpaidetail_countdownView);
        mActivityJingpaidetailJiajiafudu = (TextView) findViewById(R.id.activity_jingpaidetail_jiajiafudu);
        mActivityJingpaidetailYunfei = (TextView) findViewById(R.id.activity_jingpaidetail_yunfei);
        mActivityJingpaidetailChangjia = (TextView) findViewById(R.id.activity_jingpaidetail_changjia);
        mActivityJingpaidetailRiqi = (TextView) findViewById(R.id.activity_jingpaidetail_riqi);
        mActivityJingpaidetailAddress = (TextView) findViewById(R.id.activity_jingpaidetail_address);
        mActivityJingpaidetailZidingyi1Key = (TextView) findViewById(R.id.activity_jingpaidetail_zidingyi1_key);
        mActivityJingpaidetailZidingyi2Key = (TextView) findViewById(R.id.activity_jingpaidetail_zidingyi2_key);
        mActivityJingpaidetailZidingyi1Value = (TextView) findViewById(R.id.activity_jingpaidetail_zidingyi1_value);
        mActivityJingpaidetailZidingyi2Value = (TextView) findViewById(R.id.activity_jingpaidetail_zidingyi2_value);
        mActivityJingpaidetailYiliupai = (TextView) findViewById(R.id.activity_jingpaidetail_yiliupai);
        jiageLayout = (ConstraintLayout) findViewById(R.id.constraintLayout11);
        mActivityJingpaidetailDangqianjiageText = (TextView) findViewById(R.id.activity_jingpaidetail_dangqianjiage_text);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);
        mViewpager.setOffscreenPageLimit(2);
        mToolbarTitle.setText("抢购详情");
        mActivityJingpaiWoyaojingpai = (Button) findViewById(R.id.activity_jingpai_woyaojingpai);
        mActivityJingpaiWoyaojingpai.setOnClickListener(this);
        addData();

    }

    private boolean receivMsg ;

    @Override
    public void onReciveMessage(MessageBean msg) {
        super.onReciveMessage(msg);

        switch (msg.getCode()) {
            case MessageBean.Code.TCANYUJINGPAICHENGGONG:

//                bean.setCount(String.valueOf(Integer.parseInt(bean.getCount()) + 1));
//                mActivityJingpaidetailChujiacishu.setText(bean.getCount());
                receivMsg = true;
                addData();
                break;
        }
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


        mActivityJingpaidetailProductname.setText(bean.getShopName());
        mActivityJingpaidetailChujiacishu.setText(bean.getCount());
        mActivityJingpaidetailQipaijiage.setText(bean.getPrice());
        mActivityJingpaidetailQipaijiageUnit.setText(bean.getPriceUnit());
        mActivityJingpaidetailJiajiafudu.setText(bean.getAddPrice());
        mActivityJingpaidetailYunfei.setText(bean.getFreight());
        mActivityJingpaidetailChangjia.setText(bean.getManufacturer());
        mActivityJingpaidetailRiqi.setText(bean.getDateOfProduction());
        mActivityJingpaidetailAddress.setText(bean.getAddress());

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
                mActivityJingpaiWoyaojingpai.setVisibility(View.GONE);
                mActivityJingpaidetailStatus.setImageDrawable(getResources().getDrawable(R.mipmap.wurenqianggou));
                mActivityJingpaidetailYiliupai.setVisibility(View.VISIBLE);
                jiageLayout.setBackground(getResources().getDrawable(R.mipmap.shangpinjiage_huise));
                mActivityJingpaidetailDangqianjiageText.setVisibility(View.GONE);
                mActivityJingpaidetailDangqianjiage.setVisibility(View.GONE);
                mActivityJingpaidetailDangqianjiageUnit.setVisibility(View.GONE);
                break;
            case "1"://未开始
                mActivityJingpaiWoyaojingpai.setVisibility(View.GONE);

                mActivityJingpaidetailStatus.setImageDrawable(getResources().getDrawable(R.mipmap.jingpai_weikaishi));
                jiageLayout.setBackground(getResources().getDrawable(R.mipmap.shangpinjiage_huise));
                mActivityJingpaidetailDangqianjiage.setText(bean.getMaxPrice());
                mActivityJingpaidetailDangqianjiageUnit.setText(bean.getPriceUnit());
                break;
            case "2"://惊醒中
                mActivityJingpaidetailJulijieshuText.setVisibility(View.VISIBLE);
                mActivityJingpaidetailCountdownView.setVisibility(View.VISIBLE);
                mActivityJingpaidetailCountdownView.start(Long.parseLong(bean.getOverTime()) - TimeUtils.getNowMills());
                mActivityJingpaiWoyaojingpai.setVisibility(View.VISIBLE);
                mActivityJingpaidetailStatus.setImageDrawable(getResources().getDrawable(R.mipmap.jingpai_jinxingzhong));
                jiageLayout.setBackground(getResources().getDrawable(R.mipmap.shangpin_jiage));
                mActivityJingpaidetailDangqianjiage.setText(bean.getMaxPrice());
                mActivityJingpaidetailDangqianjiageUnit.setText(bean.getPriceUnit());
                break;
            case "3"://成交
                mActivityJingpaiWoyaojingpai.setVisibility(View.GONE);
                mActivityJingpaidetailStatus.setImageDrawable(getResources().getDrawable(R.mipmap.jingpai_yichengjiao));
                jiageLayout.setBackground(getResources().getDrawable(R.mipmap.shangpinjiage_lanse));
                mActivityJingpaidetailDangqianjiageText.setText("已成交最高价：");
                mActivityJingpaidetailDangqianjiage.setText(bean.getMaxPrice());
                mActivityJingpaidetailDangqianjiageUnit.setText(bean.getPriceUnit());
                break;
        }

        if(!receivMsg){
            setFragment();
        }
    }

    private void setFragment() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(PaipinmiaoshuFragment.newInstance(bean));
        fragments.add(JingpaixuzhiFragment.newInstance(bean));
        fragments.add(ChujiajiluFragment.newInstance(bean.getId() + ""));
        List<String> datas = new ArrayList<>();
        datas.add("商品详情");
        datas.add("抢购须知");
        datas.add("抢购记录");
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
            case R.id.activity_jingpai_woyaojingpai:

                if(UserUtil.isLogin()){
                    Intent intent = new Intent(this, CanyujingpaiActivity.class);
                    intent.putExtra("auctionId", id);
                    intent.putExtra("jiajiafudu", bean.getAddPrice());
                    intent.putExtra("priceUnit", bean.getPriceUnit());
                    intent.putExtra("maxPrice", bean.getMaxPrice());
                    intent.putExtra("count", bean.getCount());
                    ActivityUtils.startActivity(intent);
                }else {
                    ActivityUtils.startActivity(LoginActivity.class);
                }

                break;
        }
    }
}
