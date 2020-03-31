package com.company.qcy.huodong.daixiao;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
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
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.Utils.UserUtil;
import com.company.qcy.Utils.share.ShareUtil;
import com.company.qcy.adapter.BaseViewpageAdapter;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.huodong.daixiao.bean.DaixiaoBean;
import com.company.qcy.huodong.daixiao.fragment.DaixiaoCanshuFragment;
import com.company.qcy.huodong.daixiao.fragment.DaixiaoXuzhiFragment;
import com.company.qcy.ui.activity.user.LoginActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import java.util.ArrayList;
import java.util.List;


@Route(path = "/proxy/proxyMarket")

public class DaixiaoDetailActivity extends BaseActivity implements View.OnClickListener {

    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private ImageView mActivityDaixiaoDetailImg;
    private TextView mActivityDaixiaoDetailRemainNum;
    private TextView mActivityDaixiaoDetailRemainNumDanwie;
    private TextView mActivityDaixiaoDetailPrice;
    private TextView mActivityDaixiaoDetailPriceDanwei;
    private TextView mActivityDaixiaoDetailProductname;
    private TabLayout mTabLayout;
    private ViewPager mViewpager;
    private Long id;
    private BaseViewpageAdapter viewpageAdapter;
    private Button mActivityDaixiaoDetailSuoquyangpin;
    private TextView mActivityDaixiaoDetailSubscribedNum;
    private TextView mActivityDaixiaoDetailSubscribedNumDanwie;
    /**
     * 我要购买
     */
    private Button mActivityDaixiaoDetailBuy;
    private TextView mToolbarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daixiao_detail);
        initView();
    }

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mActivityDaixiaoDetailImg = (ImageView) findViewById(R.id.activity_daixiao_detail_img);
        mActivityDaixiaoDetailRemainNum = (TextView) findViewById(R.id.activity_daixiao_detail_remainNum);
        mActivityDaixiaoDetailRemainNumDanwie = (TextView) findViewById(R.id.activity_daixiao_detail_remainNum_danwie);
        mActivityDaixiaoDetailPrice = (TextView) findViewById(R.id.activity_daixiao_detail_price);
        mActivityDaixiaoDetailPriceDanwei = (TextView) findViewById(R.id.activity_daixiao_detail_price_danwei);
        mActivityDaixiaoDetailProductname = (TextView) findViewById(R.id.activity_daixiao_detail_productname);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);


        mActivityDaixiaoDetailSuoquyangpin = (Button) findViewById(R.id.activity_daixiao_detail_suoquyangpin);
        mActivityDaixiaoDetailSuoquyangpin.setOnClickListener(this);
        mActivityDaixiaoDetailSubscribedNum = (TextView) findViewById(R.id.activity_daixiao_detail_subscribedNum);
        mActivityDaixiaoDetailSubscribedNumDanwie = (TextView) findViewById(R.id.activity_daixiao_detail_subscribedNum_danwie);


        mActivityDaixiaoDetailBuy = (Button) findViewById(R.id.activity_daixiao_detail_buy);
        mActivityDaixiaoDetailBuy.setOnClickListener(this);
        mToolbarText = (TextView) findViewById(R.id.toolbar_text);
        mToolbarText.setOnClickListener(this);
        init();

    }

    private void init() {

        Uri data = getIntent().getData();
        //不为空说明是外部网页传过来的
        if (!ObjectUtils.isEmpty(data)) {
            id = Long.valueOf(data.getQueryParameter("id"));
        } else {
            id = getIntent().getLongExtra("id", 0);
        }
        mToolbarTitle.setText("产品详情");
        mToolbarText.setVisibility(View.VISIBLE);
        mToolbarText.setText("分享");

        addData();

    }

    private boolean receiveMsg;

    @Override
    public void onReciveMessage(MessageBean msg) {
        super.onReciveMessage(msg);

        switch (msg.getCode()) {
            case MessageBean.Code.DAIXIAOBUYSUCESS:
                receiveMsg = true;
                addData();
                break;
        }
    }

    private DaixiaoBean daixiaoBean;

    private void setInfo() {

        if (ObjectUtils.isEmpty(daixiaoBean)) {
            return;
        }

        if (!receiveMsg) {

            List<Fragment> fragments = new ArrayList<>();
            fragments.add(DaixiaoCanshuFragment.newInstance(daixiaoBean));
            fragments.add(DaixiaoXuzhiFragment.newInstance(daixiaoBean));
            List<String> datas = new ArrayList<>();
            datas.add("基本参数");
            datas.add("代销市场须知");
            viewpageAdapter = new BaseViewpageAdapter(getSupportFragmentManager(), fragments, datas);
            mViewpager.setAdapter(viewpageAdapter);
            mTabLayout.setupWithViewPager(mViewpager);
            mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        }

        mActivityDaixiaoDetailRemainNum.setText(daixiaoBean.getRemainNum() + " ");

        if (!ObjectUtils.isEmpty(daixiaoBean.getRemainNum()) && daixiaoBean.getRemainNum() == 0) {
            mActivityDaixiaoDetailBuy.setEnabled(false);
            mActivityDaixiaoDetailBuy.setBackgroundColor(getResources().getColor(R.color.qianhui));
        }

        mActivityDaixiaoDetailRemainNumDanwie.setText(daixiaoBean.getNumUnit());

        mActivityDaixiaoDetailPrice.setText(daixiaoBean.getPrice() + " ");

        mActivityDaixiaoDetailPriceDanwei.setText("元/" + daixiaoBean.getPriceUnit());

        mActivityDaixiaoDetailProductname.setText(daixiaoBean.getProductName());

        mActivityDaixiaoDetailSubscribedNum.setText(daixiaoBean.getSubscribedNum() + " ");

        mActivityDaixiaoDetailSubscribedNumDanwie.setText(daixiaoBean.getNumUnit());

        GlideUtils.loadImage(this, ServerInfo.IMAGE + daixiaoBean.getProductPic(), mActivityDaixiaoDetailImg);

    }

    private void addData() {

        HttpParams httpParams = new HttpParams();

        httpParams.put("sign", SPUtils.getInstance().getString("sign"));
        httpParams.put("id", id);

        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.DAIXIAODETAIL)
                .tag(this)
                .params(httpParams);

        DialogStringCallback stringCallback = new DialogStringCallback(this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("DAIXIAODETAIL", response.body());
                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            daixiaoBean = data.toJavaObject(DaixiaoBean.class);
                            setInfo();
                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(DaixiaoDetailActivity.this, request, this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_daixiao_detail_suoquyangpin:
                if (UserUtil.isLogin()) {
                    Intent intent = new Intent(this, ShenqingyangpinActivity.class);
                    intent.putExtra("daixiaoBean", daixiaoBean);
                    intent.putExtra("buyType", "sample");
                    ActivityUtils.startActivity(intent);
                } else {
                    ActivityUtils.startActivity(LoginActivity.class);
                }
                break;
            case R.id.activity_daixiao_detail_buy:
                if (UserUtil.isLogin()) {
                    Intent intent = new Intent(this, ShenqingyangpinActivity.class);
                    intent.putExtra("daixiaoBean", daixiaoBean);
                    intent.putExtra("buyType", "buy");
                    ActivityUtils.startActivity(intent);
                } else {
                    ActivityUtils.startActivity(LoginActivity.class);
                }
                break;
            case R.id.toolbar_text:
                if(ObjectUtils.isEmpty(daixiaoBean)){return;}
                ShareUtil.shareProxy(this,daixiaoBean.getProductName(),daixiaoBean.getProductPic(),daixiaoBean.getId());
                break;
        }
    }
}
