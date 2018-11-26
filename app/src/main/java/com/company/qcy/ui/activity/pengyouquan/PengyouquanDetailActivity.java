package com.company.qcy.ui.activity.pengyouquan;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.adapter.BaseViewpageAdapter;
import com.company.qcy.bean.pengyouquan.PengyouquanBean;
import com.company.qcy.fragment.pengyouquan.PinglunFragment;
import com.company.qcy.widght.pengyouquan.MultiImageView;

import java.util.ArrayList;
import java.util.List;

public class PengyouquanDetailActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private ImageView mActivityPengyouquanDetailImg;
    /**
     * 中国印染行业协会会长 陈志华
     */
    private TextView mActivityPengyouquanDetailName;
    /**
     * 2018-02-15 10:52
     */
    private TextView mActivityPengyouquanDetailTime;
    /**
     * 1、四轮降息周期的背景分析。此前共有四轮降息周期：1996-1999年、2008年、2012年、2014-2015年，总结发现降息周期有以下几个特点：1)背景均是经济明显下行，并且大多伴随外部的冲击。其中仅有14-15年降息中没有来自外部危机的影响，其余的三轮降息分别处于亚洲金融危机、次贷危机和欧债危机的阶段。2)降息和降准往往一起出现，货币政策全面转向宽松；并且大多有积极财政政策的配合，形成宽货币+宽财政的政策组合。3)与全球货币政策周期有一定的同步性，大多处于美联储“非加息”的阶段。但在97年和99年，也曾在美联储短暂加息阶段进行过降息。4)对经济的拉动作用在逐渐减弱。如前三轮降息中，经济均在滞后2-3个季度左右见底回升。而14-15年的降息对经济的拉动作用已经有长达1年的时滞，并且只是结束了GDP下滑的趋势，并未出现经济的明显回升。5)对股市和房地产有明显利好，债市影响则不确定。此前四轮降息对股市的影响大多是利好(96-99、08年、14-15年股市均上涨，12年股市震荡走平)，债市的影响则不确定(08年、12年债市均下跌，14-15年债市上涨)。此外，08年以来的三轮降息中均伴随着房价的明显上涨和地产销售的大幅回升，降息对于地产的拉动作用最为明显。
     */
    private TextView mActivityPengyouquanDetailContent;
    private MultiImageView mActivityPengyouquanDetailMultiImageView;
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private TabLayout mTabLayout;
    private AppBarLayout mAppbar;
    private ViewPager mViewpager;
    private PengyouquanBean pengyouquanBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengyouquan_detail);
        pengyouquanBean =  getIntent().getParcelableExtra("pengyouquanBean");
        initView();
    }

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mToolbarTitle.setText("朋友圈详情");
        mActivityPengyouquanDetailImg = (ImageView) findViewById(R.id.activity_pengyouquan_detail_img);
        mActivityPengyouquanDetailName = (TextView) findViewById(R.id.activity_pengyouquan_detail_name);
        mActivityPengyouquanDetailTime = (TextView) findViewById(R.id.activity_pengyouquan_detail_time);
        mActivityPengyouquanDetailContent = (TextView) findViewById(R.id.activity_pengyouquan_detail_content);
        mActivityPengyouquanDetailMultiImageView = (MultiImageView) findViewById(R.id.activity_pengyouquan_detail_multiImageView);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mAppbar = (AppBarLayout) findViewById(R.id.appbar);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);
        mToolbarTitle.setOnClickListener(this);
        initToolBarData();
        setPengyouquanBeanInfo();

    }

    private void setPengyouquanBeanInfo() {

        if(ObjectUtils.isEmpty(pengyouquanBean)){
            return;
        }
        GlideUtils.loadImage(this, ServerInfo.IMAGE+pengyouquanBean.getUrl(),mActivityPengyouquanDetailImg);
        mActivityPengyouquanDetailName.setText(pengyouquanBean.getPostUser());
        mActivityPengyouquanDetailTime.setText(TimeUtils.millis2String(pengyouquanBean.getCreatedAtStamp()));
        mActivityPengyouquanDetailContent.setText(pengyouquanBean.getContent());

    }

    BaseViewpageAdapter baseViewpageAdapter;
    private void initToolBarData() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new PinglunFragment());
        fragments.add(new PinglunFragment());
        List<String> datas = new ArrayList<>();
        datas.add("评论");
        datas.add("点赞");
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                finish();
                break;
        }
    }
}
