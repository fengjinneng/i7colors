package com.company.qcy.fragment.pengyouquan;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.KeyboardUtils;
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
import com.company.qcy.adapter.pengyouquan.DianzanAdapter;
import com.company.qcy.adapter.pengyouquan.PinglunliebiaoAdapter;
import com.company.qcy.base.BaseFragment;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.bean.pengyouquan.PengyouquanBean;
import com.company.qcy.ui.activity.pengyouquan.PersonInfoActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PraiseFragment extends BaseFragment {
    private String mParam1;
    private static final String ARG_PARAM1 = "param1";

    public PraiseFragment() {
        // Required empty public constructor
    }

    public static PraiseFragment newInstance(String param1) {
        PraiseFragment fragment = new PraiseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_praise, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void onRec(MessageBean messageBean) {
        super.onRec(messageBean);
        switch (messageBean.getCode()) {
            case MessageBean.Code.XIANGQINGYEDIANZANCHENGGONG:
                refreshLayout.setRefreshing(true);
                refreshListener.onRefresh();
                break;
        }
    }

    private RecyclerView recyclerView;
    private DianzanAdapter adapter;
    private List<PengyouquanBean.LikeListBean> datas;
    private SwipeRefreshLayout refreshLayout;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;


    private void initView(View view) {
        recyclerView = view.findViewById(R.id.fragment_praise_recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        datas = new ArrayList<>();
        adapter = new DianzanAdapter(R.layout.item_fragment_praise, datas);

//        adapter = new PinglunliebiaoAdapter(R.layout.item_pinglun, datas);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });

        refreshLayout = view.findViewById(R.id.fragment_praise_swipeRefreshLayout);
        refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉业务
                isReflash = true;
                pageNo = 0;
                addData();
            }
        };
        refreshLayout.setOnRefreshListener(refreshListener);
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                refreshListener.onRefresh();
            }
        });

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {

                addData();
            }
        }, recyclerView);

        recyclerView.addItemDecoration(new RecyclerviewDisplayDecoration(getContext()));

        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.empty_layout, null));
        adapter.setLoadMoreView(new MyLoadMoreView());

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                PengyouquanBean.LikeListBean likeListBean = (PengyouquanBean.LikeListBean) adapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.item_fragment_praise_head:
                        Intent intent = new Intent(getActivity(), PersonInfoActivity.class);
                        intent.putExtra("userId", likeListBean.getUserId());
                        ActivityUtils.startActivity(intent);
                        break;
                }
            }
        });

    }

    private int pageNo;
    private boolean isReflash;

    private void addData() {
        pageNo++;
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.QUERYPRAISELIST)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("pageNo", pageNo)
                .params("pageSize", 10)
                .params("dyeId", Long.valueOf(mParam1));

        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                refreshLayout.setRefreshing(false);
                LogUtils.v("QUERYPRAISELIST", response.body());
                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONArray data = jsonObject.getJSONArray("data");
                            List<PengyouquanBean.LikeListBean> likeListBeans = JSONObject.parseArray(data.toJSONString(), PengyouquanBean.LikeListBean.class);
                            if (ObjectUtils.isEmpty(likeListBeans)) {
                                adapter.loadMoreEnd();
                                return;
                            }
                            if (isReflash) {
                                datas.clear();
                                datas.addAll(likeListBeans);
                                adapter.setNewData(datas);
                                isReflash = false;
                                adapter.loadMoreComplete();
                                return;
                            }
                            datas.addAll(likeListBeans);
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
                refreshLayout.setRefreshing(false);
                ToastUtils.showShort(getResources().getString(R.string.NETEXCEPTION));
            }
        };

        request.execute(stringCallback);

    }

}
