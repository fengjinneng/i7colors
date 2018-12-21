package com.company.qcy.ui.activity.tuangou;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
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
import com.company.qcy.adapter.BaseViewpageAdapter;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.bean.tuangou.TuangouBean;
import com.company.qcy.fragment.tuangou.JibencanshuFragment;
import com.company.qcy.fragment.tuangou.JiluFragment;
import com.company.qcy.fragment.tuangou.TuangouxuzhiFragment;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import java.util.ArrayList;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;

public class TuangouxiangqingActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mActivityTuangouxiangqingImg;
    private ImageView mActivityTuangouxiangqingStatus;
    /**
     * 35
     */
    private TextView mActivityTuangouxiangqingYuanjia;
    /**
     * 39
     */
    private TextView mActivityTuangouxiangqingTuangoujia;
    /**
     * 活性翠兰G266%
     */
    private TextView mActivityTuangouxiangqingProductname;
    /**
     * 38
     */
    private TextView mActivityTuangouxiangqingShengyu;
    /**
     * 22
     */
    private TextView mActivityTuangouxiangqingTotal;
    /**
     * 已认领总量：10吨
     */
    private TextView mActivityTuangouxiangqingYirenling;
    private Long id;

    private SeekBar mActivityTuangouxiangqingSeekBar;
    /**
     * 单个用户采购量\n0.5-1吨
     */
    private TextView mActivityTuangouxiangqingDanyonghucaigou;
    private CountdownView mCountdownView;
    /**
     * 达成10%
     */
    private TextView mActivityTuangouxiangqingDacheng;
    /**
     * 元/吨
     */
    private TextView mActivityTuangouxiangqingYuanjiaDanwie;
    /**
     * 元/吨
     */
    private TextView mActivityTuangouxiangqingTuangoujiaDanwei;
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private TabLayout mTabLayout;
    private AppBarLayout mAppbar;
    private ViewPager mViewpager;
    private BaseViewpageAdapter viewpageAdapter;
    /**
     * 团购剩余时间:
     */
    private TextView mActivityTuangouxiangqingShengyushijianText;
    /**
     * 我要团购
     */
    private Button mActivityTuangouxiangqingWoyaotuangou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuangouxiangqing);
        id = getIntent().getLongExtra("id", 0);
        initView();
    }

    //是否有下方虚拟栏
    private static boolean isNavigationBarAvailable() {
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        boolean hasHomeKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME);
        return (!(hasBackKey && hasHomeKey));
    }
    //获取虚拟按键的高度
    public static int getNavigationBarHeight(Context context) {
        int result = 0;
        if (isNavigationBarAvailable()) {
            Resources res = context.getResources();
            int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }




    private void initView() {
        mActivityTuangouxiangqingImg = (ImageView) findViewById(R.id.activity_tuangouxiangqing_img);
        mActivityTuangouxiangqingStatus = (ImageView) findViewById(R.id.activity_tuangouxiangqing_status);
        mActivityTuangouxiangqingYuanjia = (TextView) findViewById(R.id.activity_tuangouxiangqing_yuanjia);
        mActivityTuangouxiangqingTuangoujia = (TextView) findViewById(R.id.activity_tuangouxiangqing_tuangoujia);
        mActivityTuangouxiangqingProductname = (TextView) findViewById(R.id.activity_tuangouxiangqing_productname);
        mActivityTuangouxiangqingShengyu = (TextView) findViewById(R.id.activity_tuangouxiangqing_shengyu);
        mActivityTuangouxiangqingTotal = (TextView) findViewById(R.id.activity_tuangouxiangqing_total);
        mActivityTuangouxiangqingYirenling = (TextView) findViewById(R.id.activity_tuangouxiangqing_yirenling);

        addData();

        mActivityTuangouxiangqingSeekBar = (SeekBar) findViewById(R.id.activity_tuangouxiangqing_seekBar);
        mActivityTuangouxiangqingDanyonghucaigou = (TextView) findViewById(R.id.activity_tuangouxiangqing_danyonghucaigou);
        mCountdownView = (CountdownView) findViewById(R.id.countdownView);
        mActivityTuangouxiangqingDacheng = (TextView) findViewById(R.id.activity_tuangouxiangqing_dacheng);
        mActivityTuangouxiangqingYuanjiaDanwie = (TextView) findViewById(R.id.activity_tuangouxiangqing_yuanjia_danwie);
        mActivityTuangouxiangqingTuangoujiaDanwei = (TextView) findViewById(R.id.activity_tuangouxiangqing_tuangoujia_danwei);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mAppbar = (AppBarLayout) findViewById(R.id.appbar);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);
        mViewpager.setOffscreenPageLimit(2);

        setTitle("返回");

        mCollapsingToolbar.setTitle("返回");
        mCollapsingToolbar.setExpandedTitleColor(Color.parseColor("#00ffffff"));//设置还没收缩时状态下字体颜色
        mCollapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.chunhongse));//设置收缩后Toolbar上字体的

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mActivityTuangouxiangqingShengyushijianText = (TextView) findViewById(R.id.activity_tuangouxiangqing_shengyushijian_text);
        mActivityTuangouxiangqingWoyaotuangou = (Button) findViewById(R.id.activity_tuangouxiangqing_woyaotuangou);
        mActivityTuangouxiangqingWoyaotuangou.setOnClickListener(this);
        if(isNavigationBarAvailable()){
//            ViewGroup.MarginLayoutParams margin=new ViewGroup.MarginLayoutParams(mActivityTuangouxiangqingWoyaotuangou.getLayoutParams());
//            margin.setMargins(0,0, margin.topMargin, 0);
//            ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(margin);
//            mActivityTuangouxiangqingWoyaotuangou.setLayoutParams(layoutParams);

            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mActivityTuangouxiangqingWoyaotuangou.getLayoutParams();
            //设置各个方向上的间距
            params.setMargins(0,0,0,getNavigationBarHeight(this));
            //改变控件的属性
            mActivityTuangouxiangqingWoyaotuangou.setLayoutParams(params);
        }
    }


    private TuangouBean bean;

    private void setInfo(TuangouBean bean) {

        if (ObjectUtils.isEmpty(bean)) {
            return;
        }
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(JibencanshuFragment.newInstance(ServerInfo.IMAGE + bean.getDetailMobilePic()));
        fragments.add(TuangouxuzhiFragment.newInstance(ServerInfo.IMAGE + bean.getNoteMobilePic()));
        fragments.add(JiluFragment.newInstance(String.valueOf(id)));
        List<String> datas = new ArrayList<>();
        datas.add("基本参数");
        datas.add("团购须知");
        datas.add("团购记录");
        viewpageAdapter = new BaseViewpageAdapter(getSupportFragmentManager(), fragments, datas);
        mViewpager.setAdapter(viewpageAdapter);
        mTabLayout.setupWithViewPager(mViewpager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        mActivityTuangouxiangqingDanyonghucaigou.setText("单个用户采购量:\n" + bean.getMinNum() + "-" + bean.getMaxNum() + bean.getNumUnit());
        mActivityTuangouxiangqingYuanjia.setText(bean.getOldPrice());
        mActivityTuangouxiangqingTuangoujia.setText(bean.getNewPrice());
        mActivityTuangouxiangqingProductname.setText(bean.getProductName());
        mActivityTuangouxiangqingTotal.setText(bean.getTotalNum());
        mActivityTuangouxiangqingShengyu.setText(bean.getRemainNum());
        GlideUtils.loadImage(TuangouxiangqingActivity.this,ServerInfo.IMAGE+bean.getProductPic(),mActivityTuangouxiangqingImg);
        String[] split = bean.getNumPercent().split("%");
        mActivityTuangouxiangqingSeekBar.setProgress(Integer.parseInt(split[0]));
        mActivityTuangouxiangqingDacheng.setText("达成 " + bean.getNumPercent());
        mActivityTuangouxiangqingYirenling.setText("已认领总量:  " + bean.getSubscribedNum() + bean.getNumUnit());
        mActivityTuangouxiangqingYuanjiaDanwie.setText("元/" + bean.getNumUnit());
        mActivityTuangouxiangqingTuangoujiaDanwei.setText("元/" + bean.getNumUnit());


        if (StringUtils.equals("00", bean.getEndCode())) {
            //团购未开始
            mActivityTuangouxiangqingShengyushijianText.setText("距离团购开始还有:");
            mCountdownView.start(Long.parseLong(bean.getStartTimeStamp()) - TimeUtils.getNowMills());
            mActivityTuangouxiangqingStatus.setImageDrawable(getResources().getDrawable(R.mipmap.tuangou_weikaishi));
        } else if (StringUtils.equals("10", bean.getEndCode())) {
            //已开始未领完
            mCountdownView.start(Long.parseLong(bean.getEndTimeStamp()) - TimeUtils.getNowMills());
            mActivityTuangouxiangqingStatus.setImageDrawable(getResources().getDrawable(R.mipmap.tuangou_yikaishi));
            mActivityTuangouxiangqingWoyaotuangou.setVisibility(View.VISIBLE);
        } else if (StringUtils.equals("11", bean.getEndCode())) {
            //已开始已领完
            if (StringUtils.equals("0", bean.getIsConsiderStock())) {
                mCountdownView.start(Long.parseLong(bean.getEndTimeStamp()) - TimeUtils.getNowMills());
                mActivityTuangouxiangqingStatus.setImageDrawable(getResources().getDrawable(R.mipmap.tuangou_yikaishi));
                mActivityTuangouxiangqingWoyaotuangou.setVisibility(View.VISIBLE);
            } else {
                mCountdownView.start(0);
                mActivityTuangouxiangqingStatus.setImageDrawable(getResources().getDrawable(R.mipmap.tuangou_yijieshu));
            }
        } else if (StringUtils.equals("20", bean.getEndCode())) {
            //已结束未领完
            mCountdownView.start(0);
            mActivityTuangouxiangqingStatus.setImageDrawable(getResources().getDrawable(R.mipmap.tuangou_yijieshu));
        } else if (StringUtils.equals("21", bean.getEndCode())) {
            //已结束已领完
            mCountdownView.start(0);
            mActivityTuangouxiangqingStatus.setImageDrawable(getResources().getDrawable(R.mipmap.tuangou_yijieshu));
        }


    }


    @Override
    public void onReciveMessage(MessageBean msg) {
        super.onReciveMessage(msg);
        switch (msg.getCode()) {
            case MessageBean.Code.TUANGOUCHENGGONG:

                break;
        }

    }

    private void addData() {
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.GROUPBUYDETAIL)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("id", id);

        DialogStringCallback stringCallback = new DialogStringCallback(this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("GROUPBUYDETAIL", response.body());
                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            bean = data.toJavaObject(TuangouBean.class);
                            setInfo(bean);
                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(TuangouxiangqingActivity.this,request,this);
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
            case R.id.activity_tuangouxiangqing_woyaotuangou:
                Intent intent = new Intent(this, WoyaotuangouActivity.class);
                intent.putExtra("bean", bean);
                ActivityUtils.startActivity(intent);
                break;
        }
    }
}
