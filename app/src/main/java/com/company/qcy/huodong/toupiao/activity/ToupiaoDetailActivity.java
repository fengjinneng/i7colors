package com.company.qcy.huodong.toupiao.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.ShareUtil;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.BaseViewpageAdapter;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.huodong.toupiao.bean.ToupiaoBean;
import com.company.qcy.huodong.toupiao.fragment.DangqianpaimingFragment;
import com.company.qcy.huodong.toupiao.fragment.HuodongguizeFragment;
import com.company.qcy.huodong.toupiao.fragment.ShenqingcanyuFragment;
import com.company.qcy.ui.activity.qiugoudating.QiugouxiangqingActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import java.util.ArrayList;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;

@Route(path = "/vote/detail")
public class ToupiaoDetailActivity extends BaseActivity implements View.OnClickListener {


    private String id;
    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private ImageView mActivityToupiaoDetailImg;
    /**
     * 2019
     */
    private TextView canyuzhe;
    /**
     * 2019
     */
    private TextView toupiaoshu;
    /**
     * 2019
     */
    private TextView fangwenliang;
    private CountdownView countdownView;
    private TabLayout tabLayout;
    private ViewPager viewpager;
    private BaseViewpageAdapter viewpageAdapter;
    private TextView mToolbarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toupiao_detail);

        Uri data = getIntent().getData();
        //不为空说明是外部网页传过来的
        if (!ObjectUtils.isEmpty(data)) {
            id = data.getQueryParameter("id");
        } else {
            id = getIntent().getStringExtra("id");
        }
        initView();

    }

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarText = (TextView) findViewById(R.id.toolbar_text);
        mToolbarText.setOnClickListener(this);
        mToolbarBack.setOnClickListener(this);
        mActivityToupiaoDetailImg = (ImageView) findViewById(R.id.activity_toupiao_detail_img);
        canyuzhe = (TextView) findViewById(R.id.activity_toupiao_detail_canyuzhe);
        toupiaoshu = (TextView) findViewById(R.id.activity_toupiao_detail_toupiaoshu);
        fangwenliang = (TextView) findViewById(R.id.activity_toupiao_detail_fangwenliang);
        countdownView = (CountdownView) findViewById(R.id.activity_toupiao_detail_countdownView);
        tabLayout = (TabLayout) findViewById(R.id.activity_toupiao_detail_tabLayout);
        viewpager = (ViewPager) findViewById(R.id.activity_toupiao_detail_viewpager);
        viewpager.setOffscreenPageLimit(2);
        mToolbarText.setVisibility(View.VISIBLE);
        mToolbarText.setText("分享");
        addData();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        shenqingcanyuFragment.onActivityResult(requestCode, resultCode, data);

    }


    @Override
    public void onReciveMessage(MessageBean msg) {
        super.onReciveMessage(msg);
        switch (msg.getCode()) {
            case MessageBean.Code.TOUPIAOCHENGGONG:
                bean.setJoinNum(String.valueOf(Integer.parseInt(bean.getJoinNum())+1));
                toupiaoshu.setText(bean.getJoinNum());
                break;
            case MessageBean.Code.CANYUTOUPIAOCHENGGONG:
                addData();
                break;
        }
    }

    private ShenqingcanyuFragment shenqingcanyuFragment;

    private void setInfo() {
        if (ObjectUtils.isEmpty(bean)) {
            return;
        }
        toupiaoshu.setText(bean.getJoinNum());
        canyuzhe.setText(bean.getApplicationNum());
        fangwenliang.setText(bean.getClickNum());
        mToolbarTitle.setText(bean.getName());
        GlideUtils.loadImageRct(this, ServerInfo.IMAGE + bean.getBanner(), mActivityToupiaoDetailImg);

        if (StringUtils.equals(bean.getEndCode(), "1")) {
            countdownView.start(Long.parseLong(bean.getEndTimeStamp()) - System.currentTimeMillis());
        }

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(DangqianpaimingFragment.newInstance(bean.getId() + ""));
        shenqingcanyuFragment = ShenqingcanyuFragment.newInstance(bean.getId() + "",bean.getEndCode());
        fragments.add(shenqingcanyuFragment);
        fragments.add(HuodongguizeFragment.newInstance(bean));
        List<String> datas = new ArrayList<>();
        datas.add("当前排名");
        datas.add("申请参与");
        datas.add("活动规则");
        viewpageAdapter = new BaseViewpageAdapter(getSupportFragmentManager(), fragments, datas);
        viewpager.setAdapter(viewpageAdapter);
        tabLayout.setupWithViewPager(viewpager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    private ToupiaoBean bean;

    private void addData() {
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.GETTOUPIAOLDETAILBYID)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("id", id);

        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("GETTOUPIAOLDETAILBYID", response.body());
                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            bean = data.toJavaObject(ToupiaoBean.class);
                            setInfo();
                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(ToupiaoDetailActivity.this, request, this);
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
            case R.id.toolbar_text:
                ShareUtil.shareVoteDetail(ToupiaoDetailActivity.this,"【投票】"+bean.getName(),
                        "正能量 新征程 共同见证 ! 没有绝代风采，不会甜言蜜语,你的一小票，我们的一大步!",bean.getBanner(),bean.getId()+"");
                break;
        }
    }
}
