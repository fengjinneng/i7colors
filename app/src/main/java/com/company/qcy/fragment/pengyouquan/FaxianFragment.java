package com.company.qcy.fragment.pengyouquan;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.BaseViewpageAdapter;
import com.company.qcy.adapter.pengyouquan.HuatiAdapter;
import com.company.qcy.bean.pengyouquan.HuatiBean;
import com.flyco.tablayout.SlidingTabLayout;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FaxianFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FaxianFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mFViewpager;
    private ImageView mFragmentFaxianXiala;


    public FaxianFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FaxianFragment newInstance(String param1, String param2) {
        FaxianFragment fragment = new FaxianFragment();
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
        return inflater.inflate(R.layout.fragment_faxian, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View inflate) {


        mSlidingTabLayout = inflate.findViewById(R.id.fragment_faxian_slidingTabLayout);
        mFViewpager = inflate.findViewById(R.id.fragment_faxian_viewpager);
        mFragmentFaxianXiala = inflate.findViewById(R.id.fragment_faxian_xiala);
        mFragmentFaxianXiala.setOnClickListener(this);
        addData();
    }

    private List<HuatiBean> yijiHuati;

    private void addData() {
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.QUERYHUATI)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"));

        DialogStringCallback stringCallback = new DialogStringCallback(getActivity()) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("QUERYHUATI", response.body());
                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONArray data = jsonObject.getJSONArray("data");
                            if (ObjectUtils.isEmpty(data)) {
                                return;
                            }
                            yijiHuati = JSONObject.parseArray(data.toJSONString(), HuatiBean.class);
                            List<Fragment> fragments = new ArrayList<>();
                            for (int i = 0; i < yijiHuati.size(); i++) {
                                fragments.add(FaxianSubFragment.newInstance(String.valueOf(yijiHuati.get(i).getId())));
                            }
                            String[] arr = new String[yijiHuati.size()];
                            for (int i = 0; i < yijiHuati.size(); i++) {
                                arr[i] = yijiHuati.get(i).getTitle();
                            }
                            mFViewpager.setAdapter(new BaseViewpageAdapter(getActivity().getSupportFragmentManager(), fragments));
                            mSlidingTabLayout.setViewPager(mFViewpager, arr);
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
                ToastUtils.showShort(getResources().getString(R.string.NETEXCEPTION));
            }
        };
        request.execute(stringCallback);
    }

    private PopupWindow popupWindow;
    private RecyclerView huatiRecyclerview;
    private HuatiAdapter huatiAdapter;

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fragment_faxian_xiala:
                if (ObjectUtils.isEmpty(yijiHuati)) {
                    ToastUtils.showShort("数据异常");
                    return;
                }

                if (!ObjectUtils.isEmpty(popupWindow)) {
                    popupWindow.setFocusable(true);//要先让popupwindow获得焦点，才能正确获取popupwindow的状态
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    } else {
                        mSlidingTabLayout.setVisibility(View.INVISIBLE);
                        popupWindow.showAsDropDown(mSlidingTabLayout);
                    }
                    return;
                }


                mSlidingTabLayout.setVisibility(View.INVISIBLE);
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.popwindow_huati, null);
                huatiRecyclerview = view.findViewById(R.id.popwindow_huati_recyclerview);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
                huatiRecyclerview.setLayoutManager(gridLayoutManager);

                huatiAdapter = new HuatiAdapter(R.layout.item_popwindow_huati, yijiHuati);
                huatiRecyclerview.setAdapter(huatiAdapter);

                popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.setOutsideTouchable(true);
                popupWindow.setTouchable(true);
                popupWindow.showAsDropDown(mSlidingTabLayout);
                huatiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        mSlidingTabLayout.setCurrentTab(position);
                        popupWindow.dismiss();
                    }
                });

                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        mSlidingTabLayout.setVisibility(View.VISIBLE);
                    }
                });
                break;
        }
    }
}
