package com.company.qcy.huodong.youhuizhanxiao.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.company.qcy.base.BaseFragment;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.huodong.youhuizhanxiao.adapter.YouhuizhanxiaojiluAdapter;
import com.company.qcy.huodong.youhuizhanxiao.bean.YouhuizhanxiaoRecordBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class YouhuizhanxiaoJiluFragment extends BaseFragment {

    private String mParam1;
    private static final String ARG_PARAM1 = "param1";
    private View view;
    private RecyclerView recyclerView;

    public YouhuizhanxiaoJiluFragment() {
        // Required empty public constructor
    }

    public static YouhuizhanxiaoJiluFragment newInstance(String param1) {
        YouhuizhanxiaoJiluFragment fragment = new YouhuizhanxiaoJiluFragment();
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
        View inflate = inflater.inflate(R.layout.fragment_jilu, container, false);
        initView(inflate);
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onRec(MessageBean messageBean) {
        super.onRec(messageBean);

        switch (messageBean.getCode()){
            case MessageBean.Code.YOUHUIZHANXIAOGOUMAICHENGGONG:
                datas.clear();
                pageNo=0;
                getRecord();
                break;
        }
    }


    private int pageNo;

    private YouhuizhanxiaojiluAdapter adapter;

    private List<YouhuizhanxiaoRecordBean> datas;

    private void initView(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_jilu_recyclerview);
        datas = new ArrayList<>();
        adapter = new YouhuizhanxiaojiluAdapter(R.layout.item_youhuizhanxiaojilu, datas);

        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
        getRecord();
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getRecord();
            }
        }, recyclerView);
        adapter.addHeaderView(getLayoutInflater().inflate(R.layout.item_youhuizhanxiaojilu_headview,null));
        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.empty_layout,null));
    }


    private void getRecord() {
        pageNo++;
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.YOUHUIZHANXIAORECORD)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("salesId", Long.parseLong(mParam1))
                .params("pageNo", pageNo)
                .params("pageSize", 20);

        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("YOUHUIZHANXIAORECORD", response.body());

                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONArray data = jsonObject.getJSONArray("data");
                            if (ObjectUtils.isEmpty(data)) {
                                adapter.loadMoreEnd();
                                return;
                            }
                            List<YouhuizhanxiaoRecordBean> beans = JSONObject.parseArray(data.toJSONString(), YouhuizhanxiaoRecordBean.class);

                            adapter.addData(beans);
                            adapter.loadMoreComplete();
                            adapter.disableLoadMoreIfNotFullPage();
                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(getActivity(),request,this);
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

}
