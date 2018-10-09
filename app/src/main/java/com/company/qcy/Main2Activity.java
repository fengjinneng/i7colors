package com.company.qcy;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;


public class Main2Activity extends AppCompatActivity implements View.OnClickListener {


    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
    }

    private void initView() {

        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.slidingTabLayout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mSlidingTabLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }
}
