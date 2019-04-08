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
import com.company.qcy.fragment.pengyouquan.FaxianFragment;
import com.company.qcy.fragment.pengyouquan.GuanzhuFragment;
import com.company.qcy.fragment.pengyouquan.RemenFragment;
import com.company.qcy.ui.activity.pengyouquan.MyNotReadCommunityActivity;
import com.company.qcy.ui.activity.pengyouquan.PubulishPYQActivity;
import com.company.qcy.ui.activity.user.LoginActivity;
import com.flyco.tablayout.SlidingTabLayout;
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
public class PengyouquanFragment extends Fragment {
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
        inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        initView(view);
    }


    private MyTask myTask;

    private InputMethodManager inputMethodManager;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            return false;
        }
    });

    private Timer timer;

    @Override
    public void onResume() {
        super.onResume();
        if (UserUtil.isLogin()) {
            timer = new Timer();
            myTask = new MyTask();
            timer.schedule(myTask, 15 * 1000, 15 * 1000);
        }
    }

    public class MyTask extends TimerTask {
        @Override
        public void run() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.QUERYNOTREADCOMMENTCOUNT)
                            .tag(this)
                            .params("sign", SPUtils.getInstance().getString("sign"))
                            .params("token", SPUtils.getInstance().getString("token"));

                    StringCallback stringCallback = new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            LogUtils.e("QUERYNOTREADCOMMENTCOUNT", response.body());

                            try {
                                if (response.code() == 200) {
                                    JSONObject jsonObject = JSONObject.parseObject(response.body());
                                    String msg = jsonObject.getString("msg");
                                    if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {

                                        int data = jsonObject.getInteger("data");
                                        if (data > 0) {
                                            mPopupBubble.setVisibility(View.VISIBLE);
//                                                    mPopupBubble.updateIcon(R.mipmap.dianzan_red);
                                            mPopupBubble.updateText("您有" + data + "条新的信息");
                                            mPopupBubble.show();
                                        }
                                        return;
                                    }
                                    if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                                        SignAndTokenUtil.getSign(getActivity(), request, this);
                                        return;
                                    }
                                    ToastUtils.showShort(msg);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                        }
                    };

                    request.execute(stringCallback);
                }
            });
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (myTask != null) {
            myTask.cancel();
            myTask = null;
        }
        if (myTask != null) {
            myTask.cancel();
            myTask = null;
        }


    }

    private void initView(View inflate) {
        mPopupBubble = (PopupBubble) inflate.findViewById(R.id.popup_bubble);
        tabLayout = (SlidingTabLayout) inflate.findViewById(R.id.fragment_pengyouquan_slidingTabLayout);
        mFragmentPengyouquanFabu = (ImageView) inflate.findViewById(R.id.fragment_pengyouquan_fabu);
        viewPager = inflate.findViewById(R.id.fragment_pengyouquan_viewpager);
        mPopupBubble = inflate.findViewById(R.id.popup_bubble);
        viewPager.setOffscreenPageLimit(2);
        mPopupBubble.setPopupBubbleListener(new PopupBubble.PopupBubbleClickListener() {
            @Override
            public void bubbleClicked(Context context) {
                ActivityUtils.startActivity(getActivity(), MyNotReadCommunityActivity.class);
            }
        });

        mPopupBubble.hide();
        mPopupBubble.setVisibility(View.GONE);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new RemenFragment());
        fragments.add(new FaxianFragment());
        fragments.add(new GuanzhuFragment());
        String[] arr = new String[3];
        arr[0] = "热门";
        arr[1] = "发现";
        arr[2] = "关注";
        viewPager.setAdapter(new BaseViewpageAdapter(getActivity().getSupportFragmentManager(), fragments));
        tabLayout.setViewPager(viewPager, arr);
        tabLayout.setCurrentTab(1);
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
