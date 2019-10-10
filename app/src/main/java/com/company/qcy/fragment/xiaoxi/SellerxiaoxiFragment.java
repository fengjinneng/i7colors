package com.company.qcy.fragment.xiaoxi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.MyLoadMoreView;
import com.company.qcy.Utils.RecyclerviewDisplayDecoration;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.message.NormalMessageAdapter;
import com.company.qcy.base.BaseFragment;
import com.company.qcy.bean.message.MessageBean;
import com.company.qcy.ui.activity.message.MessageDetailActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import java.util.ArrayList;
import java.util.List;

public class SellerxiaoxiFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    private RecyclerView recyclerView;
    private NormalMessageAdapter adapter;
    private List<MessageBean> datas;

    public SellerxiaoxiFragment() {
    }

    private SwipeRefreshLayout swipeRefreshLayout;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;

    public static SellerxiaoxiFragment newInstance(String param1, String param2) {
        SellerxiaoxiFragment fragment = new SellerxiaoxiFragment();
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
        View inflate = inflater.inflate(R.layout.fragment_baojiaxiaoxi, container, false);
        initView(inflate);
        return inflate;
    }

    private boolean isReflash;

    private void initView(View inflate) {

        recyclerView = (RecyclerView) inflate.findViewById(R.id.fragment_baojiaxiaoxi_recyclerview);
        swipeRefreshLayout = inflate.findViewById(R.id.fragment_baojiaxiaoxi_swipeRefreshLayout);
        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        datas = new ArrayList<>();
        adapter = new NormalMessageAdapter(R.layout.item_message, datas);
        recyclerView.setAdapter(adapter);
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
                MessageBean messageBean = (MessageBean) adapter.getData().get(position);
                messageBean.setIsRead("1");
                adapter.notifyItemChanged(position);
                Intent intent = new Intent(getContext(), MessageDetailActivity.class);
                intent.putExtra("id", messageBean.getId());
                intent.putExtra("workType", messageBean.getWorkType());
                ActivityUtils.startActivity(intent);
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

        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.empty_layout,null));
        adapter.setLoadMoreView(new MyLoadMoreView());
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onRec(com.company.qcy.bean.eventbus.MessageBean messageBean) {
        super.onRec(messageBean);
        //签名失效
        switch (messageBean.getCode()) {
            //用户登录
            case com.company.qcy.bean.eventbus.MessageBean.Code.DELU:
                swipeRefreshLayout.setRefreshing(true);
                refreshListener.onRefresh();
                break;

            //微信登录
            case com.company.qcy.bean.eventbus.MessageBean.Code.WXLOGIN:
                swipeRefreshLayout.setRefreshing(true);
                refreshListener.onRefresh();
                break;

            case com.company.qcy.bean.eventbus.MessageBean.Code.ENQUIRYMESSAGEREAD:
                swipeRefreshLayout.setRefreshing(true);
                refreshListener.onRefresh();
                break;

            case com.company.qcy.bean.eventbus.MessageBean.JPush.NEEDRESETSELLERMESSAGE:
                swipeRefreshLayout.setRefreshing(true);
                refreshListener.onRefresh();
                break;
        }
    }

    private int pageNo;

    private void addData() {

        pageNo++;
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.GETBUYERANDSELLERMESSAGEINFORMLIST)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("pageNo", pageNo)
                .params("pageSize", 20)
                .params("type", "seller")
                .params("token", SPUtils.getInstance().getString("token"));


        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                swipeRefreshLayout.setRefreshing(false);
                LogUtils.v("GETSELLERMESSAGEINFORMLIST", response.body());
                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONArray data = jsonObject.getJSONArray("data");
                            List<MessageBean> messageBeans = JSONObject.parseArray(data.toJSONString(), MessageBean.class);
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
                                adapter.disableLoadMoreIfNotFullPage();
                                return;
                            }
                            adapter.addData(messageBeans);
                            adapter.setNewData(datas);
                            adapter.loadMoreComplete();
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
