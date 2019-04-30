package com.company.qcy.fragment.pengyouquan;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.pengyouquan.ErjihuatiAdapter;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.bean.pengyouquan.HuatiBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class ErjihuatiFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private View view;
    private RecyclerView recyclerView;
    private ConstraintLayout mFragmentErjihuatiNoDataLayout;
    private SwipeRefreshLayout mRefreshLayout;


    public ErjihuatiFragment() {
        // Required empty public constructor
    }


    public static ErjihuatiFragment newInstance(String param1) {
        ErjihuatiFragment fragment = new ErjihuatiFragment();
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
        return inflater.inflate(R.layout.fragment_erjihuati, container, false);
    }


    private ErjihuatiAdapter adapter;
    private List<HuatiBean> datas;

    private void initView(View inflate) {
        datas = new ArrayList<>();
        mFragmentErjihuatiNoDataLayout = inflate.findViewById(R.id.fragment_erjihuati_no_data_layout);
        recyclerView = inflate.findViewById(R.id.fragment_erjihuati_recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        adapter = new ErjihuatiAdapter(R.layout.item_huati_erjibiaoti, datas);
        recyclerView.setAdapter(adapter);
        addData();

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HuatiBean bean = (HuatiBean) adapter.getData().get(position);
                EventBus.getDefault().post(new MessageBean(MessageBean.Code.CHOICEERJIHUATI,bean.getTitle(),String.valueOf(bean.getId())));
                getActivity().finish();
            }
        });
        mRefreshLayout = inflate.findViewById(R.id.fragment_erjihuati_swipeRefreshLayout);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                datas.clear();
                addData();
            }
        });
    }

    private void addData() {
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.QUERYHUATI)
                .tag(this)
                .params("parentId", mParam1)
                .params("sign", SPUtils.getInstance().getString("sign"));

        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                mRefreshLayout.setRefreshing(false);
                LogUtils.v("QUERYERJIHUATI", response.body());
                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONArray data = jsonObject.getJSONArray("data");
                            if (ObjectUtils.isEmpty(data)) {
                                mFragmentErjihuatiNoDataLayout.setVisibility(View.VISIBLE);
                                return;
                            }
                            mFragmentErjihuatiNoDataLayout.setVisibility(View.GONE);
                            List<HuatiBean> huatiBeans = JSONObject.parseArray(data.toJSONString(), HuatiBean.class);
                            datas.addAll(huatiBeans);
                            adapter.setNewData(datas);
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
                mRefreshLayout.setRefreshing(false);
                ToastUtils.showShort(getResources().getString(R.string.NETEXCEPTION));
            }
        };
        request.execute(stringCallback);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }
}
