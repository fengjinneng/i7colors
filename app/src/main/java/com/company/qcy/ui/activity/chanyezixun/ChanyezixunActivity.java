package com.company.qcy.ui.activity.chanyezixun;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.qcy.R;
import com.company.qcy.adapter.BaseViewpageAdapter;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.fragment.chanyezixun.ChanyezixunFragment;
import com.company.qcy.fragment.chanyezixun.HangyezixunFragment;
import com.company.qcy.fragment.chanyezixun.RencaizhaopinFragment;
import com.company.qcy.fragment.chanyezixun.RenwufangtanFragment;
import com.company.qcy.fragment.chanyezixun.ZhanhuiFragment;
import com.company.qcy.fragment.chanyezixun.ZhengcefaguiFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

public class ChanyezixunActivity extends BaseActivity implements View.OnClickListener {

    private ViewPager mChanyezixunViewpager;
    private SlidingTabLayout mChanyezixunSlidingTabLayout;
    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
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
        datas.add(new ChanyezixunFragment());
        datas.add(new HangyezixunFragment());
        datas.add(new RenwufangtanFragment());
        datas.add(new ZhengcefaguiFragment());
        datas.add(new ZhanhuiFragment());
        datas.add(new RencaizhaopinFragment());

        String[] arr = new String[datas.size()];
        arr[0] = "全部";
        arr[1] = "行业资讯";
        arr[2] = "人物访谈";
        arr[3] = "政策法规";
        arr[4] = "展会/会议";
        arr[5] = "人才招聘";
        mChanyezixunViewpager.setAdapter(new BaseViewpageAdapter(getSupportFragmentManager(), datas));

        mChanyezixunSlidingTabLayout.setViewPager(mChanyezixunViewpager, arr);
//        mChanyezixunSlidingTabLayout.setIndicatorColor(R.color.colorAccent);
//        mChanyezixunSlidingTabLayout.setUnderlineColor(getResources().getColor(R.color.chunhongse));
//        mChanyezixunSlidingTabLayout.setTextSelectColor(R.color.colorPrimary);
//        mChanyezixunSlidingTabLayout.setTextUnselectColor(R.color.design_default_color_primary);


        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mToolbarTitle.setText("产业资讯");
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
