package com.company.qcy.fragment.home;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.qcy.R;
import com.company.qcy.adapter.BaseViewpageAdapter;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ToutiaoFragment extends Fragment {


    private View view;
    private SlidingTabLayout mToutiaoSlidingTabLayout;
    private ViewPager mToutiaoViewpager;
    Context context;
    Activity activity;

    public ToutiaoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_toutiao, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
            mToutiaoSlidingTabLayout = view.findViewById(R.id.toutiao_slidingTabLayout);
            mToutiaoViewpager = view.findViewById(R.id.toutiao_viewpager);

        List<Fragment> datas = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            datas.add(new ToutiaoViewpagerFragment());
        }

        String[] arr = new String[3];
        arr[0] = "全部";
        arr[1] = "最新头条";
        arr[2] = "最火头条";
        mToutiaoViewpager.setAdapter(new BaseViewpageAdapter(getFragmentManager(),datas));

        mToutiaoSlidingTabLayout.setViewPager(mToutiaoViewpager,arr);
        mToutiaoSlidingTabLayout.setIndicatorColor(R.color.colorAccent);
        mToutiaoSlidingTabLayout.setUnderlineColor(R.color.colorPrimaryDark);
        mToutiaoSlidingTabLayout.setTextSelectColor(R.color.colorPrimary);
        mToutiaoSlidingTabLayout.setTextUnselectColor(R.color.design_default_color_primary);

    }


    public void setActivity(Activity activity){
        this.activity =activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }
}
