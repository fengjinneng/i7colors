package com.company.qcy.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.company.qcy.R;
import com.company.qcy.adapter.other.BaseViewpageAdapter;
import com.company.qcy.fragment.chanyezixun.ChanyezixunFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

public class ChanyezixunActivity extends AppCompatActivity {

    private ViewPager mChanyezixunViewpager;
    private SlidingTabLayout mChanyezixunSlidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chanyezixun);
        initView();
    }

    private void initView() {
        mChanyezixunViewpager = (ViewPager) findViewById(R.id.chanyezixun_viewpager);
        mChanyezixunSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.chanyezixun_slidingTabLayout);


        List<Fragment> datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add(new ChanyezixunFragment());
        }

        String[] arr = new String[10];
        for (int i = 0; i < 10; i++) {
            arr[i] = "title"+i;
        }
        mChanyezixunViewpager.setAdapter(new BaseViewpageAdapter(getSupportFragmentManager(),datas));

        mChanyezixunSlidingTabLayout.setViewPager(mChanyezixunViewpager,arr);
        mChanyezixunSlidingTabLayout.setIndicatorColor(R.color.colorAccent);
        mChanyezixunSlidingTabLayout.setUnderlineColor(R.color.colorPrimaryDark);
        mChanyezixunSlidingTabLayout.setTextSelectColor(R.color.colorPrimary);
        mChanyezixunSlidingTabLayout.setTextUnselectColor(R.color.design_default_color_primary);


    }



}
