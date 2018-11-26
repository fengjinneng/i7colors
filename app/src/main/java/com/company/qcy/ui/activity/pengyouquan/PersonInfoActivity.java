package com.company.qcy.ui.activity.pengyouquan;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.qcy.R;
import com.company.qcy.adapter.BaseViewpageAdapter;
import com.company.qcy.fragment.pengyouquan.PinglunFragment;

import java.util.ArrayList;
import java.util.List;

public class PersonInfoActivity extends AppCompatActivity {

    private ImageView mActivityPersonInfoImg;
    /**
     * 更换头像
     */
    private TextView mActivityPersonInfoChangeImg;
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
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private TabLayout mTabLayout;
    private AppBarLayout mAppbar;
    private ViewPager mViewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        initView();
    }

    private void initView() {
        mActivityPersonInfoImg = (ImageView) findViewById(R.id.activity_person_info_img);
        mActivityPersonInfoChangeImg = (TextView) findViewById(R.id.activity_person_info_change_img);
        mActivityPersonInfoName = (TextView) findViewById(R.id.activity_person_info_name);
        mActivityPersonInfoNickname = (TextView) findViewById(R.id.activity_person_info_nickname);
        mActivityPersonInfoEditNickname = (TextView) findViewById(R.id.activity_person_info_edit_nickname);
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
        fragments.add(new PinglunFragment());
        fragments.add(new PinglunFragment());
        List<String> datas = new ArrayList<>();
        datas.add("我的朋友圈记录");
        datas.add("关注我的");
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

}
