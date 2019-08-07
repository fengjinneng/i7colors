package com.company.qcy.ui.activity.zhuji;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.company.qcy.R;
import com.company.qcy.adapter.BaseViewpageAdapter;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.fragment.kaifangshangcheng.KaifangshangchengSubFragment;
import com.company.qcy.fragment.zhuji.WodeZhujiLsitFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

public class WodeZhujiListActivity extends BaseActivity implements View.OnClickListener {

    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wode_zhuji_list);
        initView();
    }

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.activity_wode_zhuji_list_slidingTabLayout);
        viewPager = (ViewPager) findViewById(R.id.activity_wode_zhuji_list_viewpager);
        mToolbarTitle.setText("我的助剂定制");

        setFragments();
    }

    private void setFragments() {

        List<Fragment> fragments = new ArrayList<>();

        fragments.add(WodeZhujiLsitFragment.newInstance("",""));
        fragments.add(WodeZhujiLsitFragment.newInstance("diying",""));
        fragments.add(WodeZhujiLsitFragment.newInstance("accept",""));
        fragments.add(WodeZhujiLsitFragment.newInstance("close",""));
        fragments.add(WodeZhujiLsitFragment.newInstance("time_out",""));


        String[] arr = new String[5];
        arr[0] = "全部";
        arr[1] = "试样中";
        arr[2] = "已采纳";
        arr[3] = "已关闭";
        arr[4] = "已过期";


        viewPager.setAdapter(new BaseViewpageAdapter(getSupportFragmentManager(), fragments));
        slidingTabLayout.setViewPager(viewPager, arr);

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
