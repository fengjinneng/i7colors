package com.company.qcy.huodong.caigoulianmeng2.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.qcy.R;
import com.company.qcy.adapter.BaseViewpageAdapter;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.huodong.caigoulianmeng2.adapter.WodegonghuodanAdapter;
import com.company.qcy.huodong.caigoulianmeng2.fragment.WodedinghuodanFragment;
import com.company.qcy.huodong.caigoulianmeng2.fragment.WodegonghuodanFragment;

import java.util.ArrayList;
import java.util.List;

public class HuodanActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private TabLayout mActivityHuodanTablayout;
    private ViewPager mActivityHuodanViewpager;
    private BaseViewpageAdapter viewpageAdapter;
    private String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huodan);
        phone = getIntent().getStringExtra("phone");
        initView();
    }

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mActivityHuodanTablayout = (TabLayout) findViewById(R.id.activity_huodan_tablayout);
        mActivityHuodanViewpager = (ViewPager) findViewById(R.id.activity_huodan_viewpager);

        mToolbarTitle.setText("我的货单");

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(WodedinghuodanFragment.newInstance(phone));
        fragments.add(WodegonghuodanFragment.newInstance(phone));
        List<String> datas = new ArrayList<>();
        datas.add("我的订货单");
        datas.add("我的供货单");
        viewpageAdapter = new BaseViewpageAdapter(getSupportFragmentManager(), fragments, datas);
        mActivityHuodanViewpager.setAdapter(viewpageAdapter);
        mActivityHuodanTablayout.setupWithViewPager(mActivityHuodanViewpager);
        mActivityHuodanTablayout.setTabMode(TabLayout.MODE_FIXED);
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
