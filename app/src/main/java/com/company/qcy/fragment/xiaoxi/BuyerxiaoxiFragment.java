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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link BuyerxiaoxiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuyerxiaoxiFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private List<MessageBean> datas;
    private NormalMessageAdapter adapter;

    public BuyerxiaoxiFragment() {
    }

    public static BuyerxiaoxiFragment newInstance(String param1, String param2) {
        BuyerxiaoxiFragment fragment = new BuyerxiaoxiFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private SwipeRefreshLayout swipeRefreshLayout;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;

    @Override
    public void onRec(com.company.qcy.bean.eventbus.MessageBean messageBean) {
        super.onRec(messageBean);

        switch (messageBean.getCode()) {
          //登录成功

            case com.company.qcy.bean.eventbus.MessageBean.Code.DELU:
                swipeRefreshLayout.setRefreshing(true);
                refreshListener.onRefresh();
                break;

            case com.company.qcy.bean.eventbus.MessageBean.Code.WXLOGIN:
                swipeRefreshLayout.setRefreshing(true);
                refreshListener.onRefresh();
                break;
            //消息已读
            case com.company.qcy.bean.eventbus.MessageBean.Code.ENQUIRYMESSAGEREAD:
                swipeRefreshLayout.setRefreshing(true);
                refreshListener.onRefresh();
                break;

            case com.company.qcy.bean.eventbus.MessageBean.JPush.NEEDRESETBUYERMESSAGE:
                swipeRefreshLayout.setRefreshing(true);
                refreshListener.onRefresh();
                break;
        }
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
        return inflater.inflate(R.layout.fragment_qiugouxiaoxi, container, false);
    }

    private boolean isReflash;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_qiugouxiaoxi_recyclerview);
        swipeRefreshLayout = view.findViewById(R.id.fragment_qiugouxiaoxi_swipeRefreshLayout);
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

    private int pageNo;

    private void addData() {
        pageNo++;
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.GETBUYERANDSELLERMESSAGEINFORMLIST)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("pageNo", pageNo)
                .params("pageSize", 20)
                .params("type", "buyer")
                .params("token", SPUtils.getInstance().getString("token"));

        StringCallback stringCallback = new StringCallback() {

            @Override
            public void onSuccess(Response<String> response) {
                swipeRefreshLayout.setRefreshing(false);
                LogUtils.v("GETBUYERMESSAGEINFORMLIST", response.body());
                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
//                        String msg = jsonObject.getString("msg");
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
//                        ToastUtils.showShort(msg);

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
    public void onDetach() {
        super.onDetach();
    }

}
