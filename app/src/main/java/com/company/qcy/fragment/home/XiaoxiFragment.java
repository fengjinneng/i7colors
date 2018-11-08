package com.company.qcy.fragment.home;

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


public class XiaoxiFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private View view;
    private SlidingTabLayout mXiaoxiSlidingTabLayout;
    private ViewPager mXiaoxiViewpager;


    public XiaoxiFragment() {
        // Required empty public constructor
    }

    public static XiaoxiFragment newInstance(String param1) {
        XiaoxiFragment fragment = new XiaoxiFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_xiaoxi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        mXiaoxiSlidingTabLayout = view.findViewById(R.id.xiaoxi_slidingTabLayout);
        mXiaoxiViewpager = view.findViewById(R.id.xiaoxi_viewpager);

        List<Fragment> datas = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            datas.add(new XiaoxiViewpagerFragment());
        }

        String[] arr = new String[2];
        arr[0] = "询报价消息";
        arr[1] = "系统消息";
        mXiaoxiViewpager.setAdapter(new BaseViewpageAdapter(getFragmentManager(), datas));

        mXiaoxiSlidingTabLayout.setViewPager(mXiaoxiViewpager, arr);
        mXiaoxiSlidingTabLayout.setIndicatorColor(R.color.colorAccent);
        mXiaoxiSlidingTabLayout.setUnderlineColor(R.color.colorPrimaryDark);
        mXiaoxiSlidingTabLayout.setTextSelectColor(R.color.colorPrimary);
        mXiaoxiSlidingTabLayout.setTextUnselectColor(R.color.design_default_color_primary);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
