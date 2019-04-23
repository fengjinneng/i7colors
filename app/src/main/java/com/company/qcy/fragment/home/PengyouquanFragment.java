package com.company.qcy.fragment.home;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.Utils.UserUtil;
import com.company.qcy.adapter.BaseViewpageAdapter;
import com.company.qcy.base.BaseFragment;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.fragment.pengyouquan.FaxianFragment;
import com.company.qcy.fragment.pengyouquan.GuanzhuFragment;
import com.company.qcy.fragment.pengyouquan.RemenFragment;
import com.company.qcy.ui.activity.pengyouquan.MessageActivity;
import com.company.qcy.ui.activity.pengyouquan.MyNotReadCommunityActivity;
import com.company.qcy.ui.activity.pengyouquan.PubulishPYQActivity;
import com.company.qcy.ui.activity.user.LoginActivity;
import com.flyco.tablayout.SlidingTabLayout;
import com.githang.statusbar.StatusBarCompat;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.webianks.library.PopupBubble;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PengyouquanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PengyouquanFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private PopupBubble mPopupBubble;
    private SlidingTabLayout tabLayout;
    private ImageView mFragmentPengyouquanFabu;
    private ViewPager viewPager;

    public PengyouquanFragment() {
        // Required empty public constructor
    }

    public static PengyouquanFragment newInstance(String param1, String param2) {
        PengyouquanFragment fragment = new PengyouquanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        StatusBarCompat.setStatusBarColor(getActivity(), getActivity().getResources().getColor(R.color.baise), true);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            StatusBarCompat.setStatusBarColor(getActivity(), getActivity().getResources().getColor(R.color.baise), true);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pengyouquan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (SPUtils.getInstance().getInt("pengyouquanNews") != 0) {
            mPopupBubble.setVisibility(View.VISIBLE);
            mPopupBubble.updateText("您有" + SPUtils.getInstance().getInt("pengyouquanNews") + "条新的信息");
            mPopupBubble.show();
        }else {
            mPopupBubble.setVisibility(View.GONE);
        }
    }


    @Override
    public void onRec(MessageBean messageBean) {
        super.onRec(messageBean);
        switch (messageBean.getCode()) {
            case MessageBean.Code.PENGYOUQUANHAVENEWMESSAGE:
                mPopupBubble.setVisibility(View.VISIBLE);
                mPopupBubble.updateText("您有" + SPUtils.getInstance().getInt("pengyouquanNews") + "条新的信息");
                mPopupBubble.show();
                break;
        }
    }

    private void initView(View inflate) {
        mPopupBubble = (PopupBubble) inflate.findViewById(R.id.popup_bubble);
        tabLayout = (SlidingTabLayout) inflate.findViewById(R.id.fragment_pengyouquan_slidingTabLayout);
        mFragmentPengyouquanFabu = (ImageView) inflate.findViewById(R.id.fragment_pengyouquan_fabu);
        viewPager = inflate.findViewById(R.id.fragment_pengyouquan_viewpager);
        mPopupBubble = inflate.findViewById(R.id.popup_bubble);
        mPopupBubble.setPopupBubbleListener(new PopupBubble.PopupBubbleClickListener() {
            @Override
            public void bubbleClicked(Context context) {
                ActivityUtils.startActivity(getActivity(), MessageActivity.class);
            }
        });

        mPopupBubble.hide();
        mPopupBubble.setVisibility(View.GONE);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new FaxianFragment());
        fragments.add(new RemenFragment());
        fragments.add(new GuanzhuFragment());
        String[] arr = new String[3];
        arr[0] = "发现";
        arr[1] = "热门";
        arr[2] = "关注";
        viewPager.setAdapter(new BaseViewpageAdapter(getActivity().getSupportFragmentManager(), fragments));
        tabLayout.setViewPager(viewPager, arr);
        mFragmentPengyouquanFabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!UserUtil.isLogin()) {
                    ActivityUtils.startActivity(LoginActivity.class);
                    return;
                }
                ActivityUtils.startActivity(PubulishPYQActivity.class);
            }
        });
    }
}
