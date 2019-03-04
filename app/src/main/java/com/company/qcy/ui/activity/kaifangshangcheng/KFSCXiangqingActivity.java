package com.company.qcy.ui.activity.kaifangshangcheng;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.alibaba.fastjson.JSONObject;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.company.qcy.R;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.NetworkImageHolderView;
import com.company.qcy.Utils.PermisionUtil;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.BaseViewpageAdapter;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.bean.kaifangshangcheng.DianpuxiangqingBean;
import com.company.qcy.fragment.kaifangshangcheng.KaifangshangchengxiangqingFragment;
import com.company.qcy.fragment.kaifangshangcheng.KfscGongsijieshaoFragment;
import com.company.qcy.ui.activity.chanpindating.ChanpinxiangqingActivity;
import com.company.qcy.ui.activity.qiugoudating.QiugoudatingActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


public class KFSCXiangqingActivity extends BaseActivity implements View.OnClickListener, NestedScrollView.OnScrollChangeListener {

    private ConvenientBanner convenientBanner;
    private Long id;//店铺ID
    private ImageView mActivityKfscxiangqingCompanyLogo;
    /**
     * 山东索玛德染料有限公司
     */
    private TextView mActivityKfscxiangqingCompanyname;
    private ImageView mActivityKfscxiangqingFirststar;
    private ImageView mActivityKfscxiangqingSecondstar;
    private ImageView mActivityKfscxiangqingThirdstar;
    private ImageView mActivityKfscxiangqingFourstar;
    private ImageView mActivityKfscxiangqingFivestar;
    private ImageView mActivityKfscxiangqingYijianhujiao;
    /**
     * 暂无公司简介！
     */
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private TabLayout mTabLayout;
    private AppBarLayout mAppbar;
    private ViewPager mViewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kfscxiangqing);
        id = getIntent().getLongExtra("id", 0);
        initView();
    }

    private void initView() {
        convenientBanner = (ConvenientBanner) findViewById(R.id.activity_kfscxiangqing_banner);

        mActivityKfscxiangqingCompanyLogo = (ImageView) findViewById(R.id.activity_kfscxiangqing_company_logo);
        mActivityKfscxiangqingCompanyname = (TextView) findViewById(R.id.activity_kfscxiangqing_companyname);
        mActivityKfscxiangqingFirststar = (ImageView) findViewById(R.id.activity_kfscxiangqing_firststar);
        mActivityKfscxiangqingSecondstar = (ImageView) findViewById(R.id.activity_kfscxiangqing_secondstar);
        mActivityKfscxiangqingThirdstar = (ImageView) findViewById(R.id.activity_kfscxiangqing_thirdstar);
        mActivityKfscxiangqingFourstar = (ImageView) findViewById(R.id.activity_kfscxiangqing_fourstar);
        mActivityKfscxiangqingFivestar = (ImageView) findViewById(R.id.activity_kfscxiangqing_fivestar);
        mActivityKfscxiangqingYijianhujiao = (ImageView) findViewById(R.id.activity_kfscxiangqing_yijianhujiao);
        addDianpuData();
        mActivityKfscxiangqingYijianhujiao.setOnClickListener(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mAppbar = (AppBarLayout) findViewById(R.id.appbar);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);
        initToolBarData();
    }

    BaseViewpageAdapter baseViewpageAdapter;

    private void initToolBarData() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(KaifangshangchengxiangqingFragment.newInstance(String.valueOf(id)));
        fragments.add(KfscGongsijieshaoFragment.newInstance(""));
        List<String> datas = new ArrayList<>();
        datas.add("产品详情");
        datas.add("公司介绍");
        setTitle("返回");
        mCollapsingToolbar.setTitle("返回");
        mCollapsingToolbar.setExpandedTitleColor(Color.parseColor("#00ffffff"));//设置还没收缩时状态下字体颜色
        mCollapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的
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

    private DianpuxiangqingBean dianpuBean;

    private void addDianpuData() {
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.DIANPUXIANGQING)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("id", id);

        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("DIANPUXIANGQING", response.body());

                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            dianpuBean = data.toJavaObject(DianpuxiangqingBean.class);
                            Glide.with(KFSCXiangqingActivity.this).load(ServerInfo.IMAGE + dianpuBean.getLogo()).into(mActivityKfscxiangqingCompanyLogo);
                            mActivityKfscxiangqingCompanyname.setText(dianpuBean.getCompanyName());
                            setStarLevel(dianpuBean.getCreditLevel());
                            List<String> banners = new ArrayList<>();
                            if (!StringUtils.isEmpty(dianpuBean.getBanner1())) {
                                banners.add(ServerInfo.IMAGE + dianpuBean.getBanner1());
                            }
                            if (!StringUtils.isEmpty(dianpuBean.getBanner2())) {
                                banners.add(ServerInfo.IMAGE + dianpuBean.getBanner2());
                            }
                            if (!StringUtils.isEmpty(dianpuBean.getBanner3())) {
                                banners.add(ServerInfo.IMAGE + dianpuBean.getBanner3());
                            }
                            if (!StringUtils.isEmpty(dianpuBean.getBanner4())) {
                                banners.add(ServerInfo.IMAGE + dianpuBean.getBanner4());
                            }
                            if (!StringUtils.isEmpty(dianpuBean.getBanner5())) {
                                banners.add(ServerInfo.IMAGE + dianpuBean.getBanner5());
                            }
                            setTitle(dianpuBean.getCompanyName());
                            mCollapsingToolbar.setTitle(dianpuBean.getCompanyName());
                            setBannerData(banners);
                            EventBus.getDefault().post(new MessageBean(MessageBean.Code.KFSCGONGSIJIESHAO, dianpuBean.getDescription()));

                            return;


                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(KFSCXiangqingActivity.this, request, this);
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

    //设置信用等级
    private void setStarLevel(String level) {
        switch (level) {
            case "1":
                mActivityKfscxiangqingFirststar.setBackgroundResource(R.mipmap.wujiaoxing_shixin);
                mActivityKfscxiangqingSecondstar.setBackgroundResource(R.mipmap.wujiaoxing_kongxin);
                mActivityKfscxiangqingThirdstar.setBackgroundResource(R.mipmap.wujiaoxing_kongxin);
                mActivityKfscxiangqingFourstar.setBackgroundResource(R.mipmap.wujiaoxing_kongxin);
                mActivityKfscxiangqingFivestar.setBackgroundResource(R.mipmap.wujiaoxing_kongxin);
                break;
            case "2":
                mActivityKfscxiangqingFirststar.setBackgroundResource(R.mipmap.wujiaoxing_shixin);
                mActivityKfscxiangqingSecondstar.setBackgroundResource(R.mipmap.wujiaoxing_shixin);
                mActivityKfscxiangqingThirdstar.setBackgroundResource(R.mipmap.wujiaoxing_kongxin);
                mActivityKfscxiangqingFourstar.setBackgroundResource(R.mipmap.wujiaoxing_kongxin);
                mActivityKfscxiangqingFivestar.setBackgroundResource(R.mipmap.wujiaoxing_kongxin);

                break;
            case "3":
                mActivityKfscxiangqingFirststar.setBackgroundResource(R.mipmap.wujiaoxing_shixin);
                mActivityKfscxiangqingSecondstar.setBackgroundResource(R.mipmap.wujiaoxing_shixin);
                mActivityKfscxiangqingThirdstar.setBackgroundResource(R.mipmap.wujiaoxing_shixin);
                mActivityKfscxiangqingFourstar.setBackgroundResource(R.mipmap.wujiaoxing_kongxin);
                mActivityKfscxiangqingFivestar.setBackgroundResource(R.mipmap.wujiaoxing_kongxin);
                break;
            case "4":
                mActivityKfscxiangqingFirststar.setBackgroundResource(R.mipmap.wujiaoxing_shixin);
                mActivityKfscxiangqingSecondstar.setBackgroundResource(R.mipmap.wujiaoxing_shixin);
                mActivityKfscxiangqingThirdstar.setBackgroundResource(R.mipmap.wujiaoxing_shixin);
                mActivityKfscxiangqingFourstar.setBackgroundResource(R.mipmap.wujiaoxing_shixin);
                mActivityKfscxiangqingFivestar.setBackgroundResource(R.mipmap.wujiaoxing_kongxin);
                break;
            case "5":
                mActivityKfscxiangqingFirststar.setBackgroundResource(R.mipmap.wujiaoxing_shixin);
                mActivityKfscxiangqingSecondstar.setBackgroundResource(R.mipmap.wujiaoxing_shixin);
                mActivityKfscxiangqingThirdstar.setBackgroundResource(R.mipmap.wujiaoxing_shixin);
                mActivityKfscxiangqingFourstar.setBackgroundResource(R.mipmap.wujiaoxing_shixin);
                mActivityKfscxiangqingFivestar.setBackgroundResource(R.mipmap.wujiaoxing_shixin);
                break;
        }

    }


    private void setBannerData(List<String> bannerData) {


        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, bannerData);

        convenientBanner.setPageIndicator(new int[]{R.mipmap.banner_unchoiced, R.mipmap.banner_choiced});
        convenientBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        //设置如果只有一组数据时不能滑动
        convenientBanner.setPointViewVisible(bannerData.size() == 1 ? false : true); // 指示器
        convenientBanner.setManualPageable(bannerData.size() == 1 ? false : true);//设置false,手动影响（设置了该项无法手动切换）
        convenientBanner.setCanLoop(bannerData.size() == 1 ? false : true); // 是否循环


        convenientBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.activity_kfscxiangqing_yijianhujiao:
                if (ObjectUtils.isEmpty(dianpuBean)) {
                    return;
                }

                if (StringUtils.isEmpty(dianpuBean.getTel())) {

                    if (StringUtils.isEmpty(dianpuBean.getPhone())) {
                        ToastUtils.showShort("该企业没有留下电话号码哦！");
                    } else {
                        PermisionUtil.callPhone(KFSCXiangqingActivity.this, dianpuBean.getPhone());
                    }

                } else {
                    PermisionUtil.callPhone(KFSCXiangqingActivity.this, dianpuBean.getTel());
                }


                break;
        }
    }

    @Override
    public void onScrollChange(NestedScrollView nestedScrollView, int i, int i1, int i2, int i3) {
//        LogUtils.v("onScrollChange", "scrollY == " + i1);
//        if (i1 < 80) {
//            mActivityKfscxiangqingTitleLayout.setBackgroundColor(getResources().getColor(R.color.trans));
//
//            mActivityKfscxiangqingTitleLine.setVisibility(View.INVISIBLE);
//            mActivityKfscxiangqingTitle.setVisibility(View.INVISIBLE);
//
//        } else {
//            mActivityKfscxiangqingTitle.setVisibility(View.VISIBLE);
//            mActivityKfscxiangqingTitleLine.setVisibility(View.VISIBLE);
//
//            float persent = i1 * 1f / 300;
//            int alpha = (int) (255 * persent);
//            if (alpha > 230) {
//                alpha = 255;  // 不透明
//                int color = Color.argb(alpha, 255, 255, 255);
//                mActivityKfscxiangqingTitleLayout.setBackgroundColor(color);
//            } else {
//                int color = Color.argb(alpha, 255, 255, 255);
//                mActivityKfscxiangqingTitleLayout.setBackgroundColor(color);
//            }
//        }
//        if (convenientBanner.getHeight() + mActivityKfscxiangqingConstrainlayout.getHeight() - 60 > i1) {
//            mActivityKfscxiangqingFenleiLayout.setVisibility(View.INVISIBLE);
//        } else {
//            mActivityKfscxiangqingFenleiLayout.setVisibility(View.VISIBLE);
//        }

    }


}
