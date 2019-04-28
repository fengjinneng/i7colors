package com.company.qcy.fragment.pengyouquan;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.Utils.CommonUtils;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.MyCommonUtil;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.Utils.UserUtil;
import com.company.qcy.adapter.BaseViewpageAdapter;
import com.company.qcy.adapter.pengyouquan.HuatiAdapter;
import com.company.qcy.base.BaseFragment;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.bean.pengyouquan.HuatiBean;
import com.company.qcy.bean.pengyouquan.PYQUserBean;
import com.company.qcy.ui.activity.pengyouquan.MyPersonInfoActivity;
import com.company.qcy.ui.activity.user.LoginActivity;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FaxianFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FaxianFragment extends BaseFragment implements View.OnClickListener {
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
    private ImageView mPengyouquanFaxianHeadimg;
    /**
     * name
     */
    private TextView mPengyouquanFaxianName;
    /**
     * 未认证
     */
    private TextView mPengyouquanFaxianRenzheng;
    private ImageView mPengyouquanFaxianRenzhengImg;
    private ConstraintLayout mPengyouquanFaxianMyinfoLayout;


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
        mPengyouquanFaxianHeadimg = inflate.findViewById(R.id.pengyouquan_faxian_headimg);
        mPengyouquanFaxianName = inflate.findViewById(R.id.pengyouquan_faxian_name);
        mPengyouquanFaxianRenzheng = inflate.findViewById(R.id.pengyouquan_faxian_renzheng);
        mPengyouquanFaxianRenzhengImg = inflate.findViewById(R.id.pengyouquan_faxian_renzheng_img);
        mPengyouquanFaxianMyinfoLayout = inflate.findViewById(R.id.pengyouquan_faxian_myinfo_layout);
        mPengyouquanFaxianMyinfoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserUtil.isLogin()) {
                    ActivityUtils.startActivity(MyPersonInfoActivity.class);
                } else {
                    ActivityUtils.startActivity(LoginActivity.class);
                }
            }
        });
        addData();
        getMyInfo();

        mSlidingTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                for (int i = 0; i < popList.size(); i++) {
                    if (i == position) {
                        popList.get(i).setChecked(true);
                    } else {
                        popList.get(i).setChecked(false);
                    }
                }
                if (!ObjectUtils.isEmpty(huatiAdapter)) {
                    huatiAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

    }

    @Override
    public void onRec(MessageBean messageBean) {
        super.onRec(messageBean);

        switch (messageBean.getCode()) {
            //朋友圈头像修改成功
            case MessageBean.Code.PENGYOUQUANHEADIMGCHANGE:
                GlideUtils.loadCircleImage(getContext(), ServerInfo.IMAGE + messageBean.getMeaasge(), mPengyouquanFaxianHeadimg);
                break;
            //朋友圈nickName修改成功
            case MessageBean.Code.PENGYOUQUANNICKNAMECHANGE:
                mPengyouquanFaxianName.setText(messageBean.getMeaasge());
                break;
            case MessageBean.Code.WXLOGIN:
                getMyInfo();
                break;
            case MessageBean.Code.DELU:
                getMyInfo();
                break;
        }
    }

    public void getMyInfo() {
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.GETUSERINFOBYTOKEN)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("token", SPUtils.getInstance().getString("token"));

        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("GETUSERINFOBYTOKEN", response.body());

                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");

                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONObject data = jsonObject.getJSONObject("data");

                            PYQUserBean userBean = data.toJavaObject(PYQUserBean.class);
                            mPengyouquanFaxianName.setText(userBean.getNickName());

                            MyCommonUtil.jiazaitouxiang(getActivity(),userBean.getCommunityPhoto(),mPengyouquanFaxianHeadimg);

//                            if (StringUtils.isEmpty(userBean.getCommunityPhoto())) {
//                                mPengyouquanFaxianHeadimg.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.morentouxiang));
//                            } else {
//                                GlideUtils.loadCircleImage(getContext(), ServerInfo.IMAGE + userBean.getCommunityPhoto(), mPengyouquanFaxianHeadimg);
//                            }

                            if (StringUtils.equals("1", userBean.getIsCompany())) {
                                mPengyouquanFaxianRenzheng.setText("已认证");
                                mPengyouquanFaxianRenzhengImg.setVisibility(View.VISIBLE);
                            } else {
                                if (StringUtils.equals("1", userBean.getIsDyeV())) {
                                    mPengyouquanFaxianRenzheng.setText("已认证");
                                    mPengyouquanFaxianRenzhengImg.setVisibility(View.VISIBLE);
                                } else if (StringUtils.equals("2", userBean.getIsDyeV())) {
                                    mPengyouquanFaxianRenzheng.setText("认证审核中...");
                                    mPengyouquanFaxianRenzhengImg.setVisibility(View.GONE);
                                } else {
                                    mPengyouquanFaxianRenzheng.setText("未认证");
                                    mPengyouquanFaxianRenzhengImg.setVisibility(View.GONE);
                                }
                            }

                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(getActivity(), request, this);
                            return;
                        }
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


    private List<HuatiBean> yijiHuati;

    private void addData() {
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.QUERYHUATI)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("level", "1");

        StringCallback stringCallback = new StringCallback() {
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

                                List<Fragment> fragments = new ArrayList<>();
                                fragments.add(FaxianSubFragment.newInstance(""));

                                String[] arr = new String[1];
                                arr[0]="全部";
                                HuatiBean huatiBean = new HuatiBean();
                                huatiBean.setTitle("全部");
                                huatiBean.setChecked(true);
                                popList.add(huatiBean);

                                mFViewpager.setAdapter(new BaseViewpageAdapter(getActivity().getSupportFragmentManager(), fragments));
                                mSlidingTabLayout.setViewPager(mFViewpager, arr);

                                return;
                            }
                            yijiHuati = JSONObject.parseArray(data.toJSONString(), HuatiBean.class);
                            List<Fragment> fragments = new ArrayList<>();
                            fragments.add(FaxianSubFragment.newInstance(""));
                            for (int i = 0; i < yijiHuati.size(); i++) {
                                fragments.add(FaxianSubFragment.newInstance(String.valueOf(yijiHuati.get(i).getId())));
                            }
                            String[] arr = new String[yijiHuati.size() + 1];
                            arr[0] = "全部";
                            for (int i = 1; i < yijiHuati.size() + 1; i++) {
                                arr[i] = yijiHuati.get(i - 1).getTitle();
                            }

                            HuatiBean huatiBean = new HuatiBean();
                            huatiBean.setTitle("全部");
                            popList.add(huatiBean);
                            popList.addAll(yijiHuati);

                            popList.get(0).setChecked(true);

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

    private List<HuatiBean> popList = new ArrayList<>();//弹出的pop集合，添加了全部之后添加的

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fragment_faxian_xiala:
                if (ObjectUtils.isEmpty(popList)) {
                    ToastUtils.showShort("数据异常");
                    return;
                }

                if (!ObjectUtils.isEmpty(popupWindow)) {
                    popupWindow.setFocusable(true);//要先让popupwindow获得焦点，才能正确获取popupwindow的状态
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                        mFragmentFaxianXiala.setImageDrawable(getActivity().getResources().getDrawable(R.mipmap.xiajiantu_hongse));
                    } else {
                        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                        lp.alpha = 0.6f;
                        getActivity().getWindow().setAttributes(lp);
//                        mSlidingTabLayout.setVisibility(View.INVISIBLE);
                        popupWindow.showAsDropDown(mSlidingTabLayout);
                        mFragmentFaxianXiala.setImageDrawable(getActivity().getResources().getDrawable(R.mipmap.shangjiantou_hongse));
                    }
                    return;
                }

                mFragmentFaxianXiala.setImageDrawable(getActivity().getResources().getDrawable(R.mipmap.shangjiantou_hongse));
//                mSlidingTabLayout.setVisibility(View.INVISIBLE);
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.popwindow_huati, null);
                huatiRecyclerview = view.findViewById(R.id.popwindow_huati_recyclerview);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
                huatiRecyclerview.setLayoutManager(gridLayoutManager);

                huatiAdapter = new HuatiAdapter(R.layout.item_popwindow_huati, popList);
                huatiRecyclerview.setAdapter(huatiAdapter);

                popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                //产生背景变暗效果
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 0.4f;
                getActivity().getWindow().setAttributes(lp);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.setOutsideTouchable(true);
                popupWindow.setTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.showAsDropDown(mSlidingTabLayout);
                huatiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        for (int i = 0; i < popList.size(); i++) {
                            if (i == position) {
                                popList.get(i).setChecked(true);
                            } else {
                                popList.get(i).setChecked(false);
                            }
                        }
                        adapter.notifyDataSetChanged();
                        mSlidingTabLayout.setCurrentTab(position);
                        popupWindow.dismiss();
                    }
                });

                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
//                        mSlidingTabLayout.setVisibility(View.VISIBLE);
                        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                        lp.alpha = 1f;
                        getActivity().getWindow().setAttributes(lp);
                        mFragmentFaxianXiala.setImageDrawable(getActivity().getResources().getDrawable(R.mipmap.xiajiantu_hongse));
                    }
                });
                break;

        }
    }
}
