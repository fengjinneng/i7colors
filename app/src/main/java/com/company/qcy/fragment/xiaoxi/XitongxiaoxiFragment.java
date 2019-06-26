package com.company.qcy.fragment.xiaoxi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.MyLoadMoreView;
import com.company.qcy.Utils.RecyclerviewDisplayDecoration;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.message.SystemMessageAdapter;
import com.company.qcy.base.BaseFragment;
import com.company.qcy.base.WebActivity;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.bean.message.SystemMeassageBean;
import com.company.qcy.ui.activity.chanpindating.ChanpindatingActivity;
import com.company.qcy.ui.activity.chanpindating.ChanpinxiangqingActivity;
import com.company.qcy.ui.activity.chanyezixun.ChanyezixunActivity;
import com.company.qcy.ui.activity.chanyezixun.ZixunxiangqingActivity;
import com.company.qcy.ui.activity.kaifangshangcheng.KFSCXiangqingActivity;
import com.company.qcy.ui.activity.kaifangshangcheng.KaifangshangchengActivity;
import com.company.qcy.ui.activity.qiugoudating.QiugoudatingActivity;
import com.company.qcy.ui.activity.qiugoudating.QiugouxiangqingActivity;
import com.company.qcy.huodong.tuangou.activity.TuangouliebiaoActivity;
import com.company.qcy.huodong.tuangou.activity.TuangouxiangqingActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link XitongxiaoxiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class XitongxiaoxiFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private SystemMessageAdapter adapter;
    private List<SystemMeassageBean> datas;


    public XitongxiaoxiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment XitongxiaoxiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static XitongxiaoxiFragment newInstance(String param1, String param2) {
        XitongxiaoxiFragment fragment = new XitongxiaoxiFragment();
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
        return inflater.inflate(R.layout.fragment_xitongxiaoxi, container, false);
    }

    private SwipeRefreshLayout swipeRefreshLayout;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;

    @Override
    public void onRec(com.company.qcy.bean.eventbus.MessageBean messageBean) {
        super.onRec(messageBean);
        //签名失效
        switch (messageBean.getCode()) {

        }
    }


    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.fragment_xitongxiaoxi_recyclerview);
        swipeRefreshLayout = view.findViewById(R.id.fragment_xitongxiaoxi_swipeRefreshLayout);
        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        datas = new ArrayList<>();
        adapter = new SystemMessageAdapter(R.layout.item_system_message, datas);
        recyclerView.setAdapter(adapter);
//        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);

        recyclerView.addItemDecoration(new RecyclerviewDisplayDecoration(getContext()));

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                addData();
            }
        }, recyclerView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                SystemMeassageBean systemMeassageBean = (SystemMeassageBean) adapter.getData().get(position);

                if (StringUtils.equals("txt", systemMeassageBean.getType())) {
                    return;
                } else if (StringUtils.equals("inner", systemMeassageBean.getType())) {

                    if (StringUtils.equals("enquiry", systemMeassageBean.getDirectType())) {

                        if (ObjectUtils.isEmpty(systemMeassageBean.getDirectTypeId())) {
                            ActivityUtils.startActivity(QiugoudatingActivity.class);
                        } else {
                            Intent marketIntent = new Intent(getActivity(), QiugouxiangqingActivity.class);
                            marketIntent.putExtra("enquiryId", systemMeassageBean.getDirectTypeId());
                            ActivityUtils.startActivity(marketIntent);
                        }
                    } else if (StringUtils.equals("market", systemMeassageBean.getDirectType())) {
                        if (ObjectUtils.isEmpty(systemMeassageBean.getDirectTypeId())) {
                            ActivityUtils.startActivity(KaifangshangchengActivity.class);

                        } else {
                            Intent marketIntent = new Intent(getActivity(), KFSCXiangqingActivity.class);
                            marketIntent.putExtra("id", systemMeassageBean.getDirectTypeId());
                            ActivityUtils.startActivity(marketIntent);
                        }
                    } else if (StringUtils.equals("product", systemMeassageBean.getDirectType())) {
                        if (ObjectUtils.isEmpty(systemMeassageBean.getDirectTypeId())) {
                            ActivityUtils.startActivity(ChanpindatingActivity.class);

                        } else {
                            Intent productIntent = new Intent(getActivity(), ChanpinxiangqingActivity.class);
                            productIntent.putExtra("id", systemMeassageBean.getDirectTypeId() + "m");
                            ActivityUtils.startActivity(productIntent);
                        }
                    } else if (StringUtils.equals("information", systemMeassageBean.getDirectType())) {
                        if (ObjectUtils.isEmpty(systemMeassageBean.getDirectTypeId())) {
                            ActivityUtils.startActivity(ChanyezixunActivity.class);
                        } else {
                            Intent zixunIntent = new Intent(getActivity(), ZixunxiangqingActivity.class);
                            zixunIntent.putExtra("id", systemMeassageBean.getDirectTypeId()+"");
                            ActivityUtils.startActivity(zixunIntent);

                        }

                    } else if (StringUtils.equals("groupBuy", systemMeassageBean.getDirectType())) {
                        if (ObjectUtils.isEmpty(systemMeassageBean.getDirectTypeId())) {
                            ActivityUtils.startActivity(TuangouliebiaoActivity.class);

                        } else {
                            Intent tuangouIntent = new Intent(getActivity(), TuangouxiangqingActivity.class);
                            tuangouIntent.putExtra("id", systemMeassageBean.getDirectTypeId());
                            ActivityUtils.startActivity(tuangouIntent);
                        }
                    }

                } else if (StringUtils.equals("html", systemMeassageBean.getType())) {
                    Intent intent = new Intent(getActivity(), WebActivity.class);
                    intent.putExtra("webUrl", systemMeassageBean.getUrl());
                    ActivityUtils.startActivity(intent);
                }


            }
        });
        refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉业务
                isReflash = true;
                pageNo = 0;
                addData();
            }
        };
        swipeRefreshLayout.setOnRefreshListener(refreshListener);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                refreshListener.onRefresh();
            }
        });
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_green_light, android.R.color.holo_blue_light);
        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.empty_layout, null));
        adapter.setLoadMoreView(new MyLoadMoreView());
    }

    private boolean isReflash;
    private int pageNo;

    private void addData() {
        pageNo++;
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.SYSTEMINFORMLIST)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("pageNo", pageNo)
                .params("pageSize", 20)
                .params("deviceNo", DeviceUtils.getAndroidID());

        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                swipeRefreshLayout.setRefreshing(false);
                LogUtils.v("SYSTEMINFORMLIST", response.body());

                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONArray data = jsonObject.getJSONArray("data");
                            List<SystemMeassageBean> messageBeans = JSONObject.parseArray(data.toJSONString(), SystemMeassageBean.class);
                            if (ObjectUtils.isEmpty(messageBeans)) {
                                adapter.loadMoreEnd();
                                return;
                            }
                            if (isReflash) {
                                datas.clear();
                                datas.addAll(messageBeans);
                                adapter.setNewData(datas);
                                isReflash = false;
                                adapter.loadMoreComplete();
                                return;
                            }
                            adapter.addData(messageBeans);
                            adapter.setNewData(datas);
                            adapter.loadMoreComplete();
                            adapter.disableLoadMoreIfNotFullPage();
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
                swipeRefreshLayout.setRefreshing(false);
                ToastUtils.showShort(getResources().getString(R.string.NETEXCEPTION));
            }
        };

        request.execute(stringCallback);
    }
}
