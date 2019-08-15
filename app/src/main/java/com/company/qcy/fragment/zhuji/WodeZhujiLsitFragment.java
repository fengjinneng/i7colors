package com.company.qcy.fragment.zhuji;


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

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.zhuji.ZhujiListAdapter;
import com.company.qcy.base.RequestBackUtil;
import com.company.qcy.bean.zhuji.ZhujiBean;
import com.company.qcy.ui.activity.zhuji.WodeZhujiDingzhiDetailActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WodeZhujiLsitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WodeZhujiLsitFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    //试样中的状态
    private String mParam1;
    private String mParam2;
    private View view;


    public WodeZhujiLsitFragment() {
        // Required empty public constructor
    }

    public static WodeZhujiLsitFragment newInstance(String param1, String param2) {
        WodeZhujiLsitFragment fragment = new WodeZhujiLsitFragment();
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
        return inflater.inflate(R.layout.fragment_wode_zhuji_list, container, false);
    }

    private SwipeRefreshLayout refreshLayout;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;
    private RecyclerView recyclerView;
    private List<ZhujiBean> datas;
    private ZhujiListAdapter adapter;

    private void initView(View inflate) {
        refreshLayout = (SwipeRefreshLayout) inflate.findViewById(R.id.fragment_wode_zhuji_list_swipeRefreshLayout);

        recyclerView = (RecyclerView) inflate.findViewById(R.id.fragment_wode_zhuji_list_recyclerview);
        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        datas = new ArrayList<>();
        adapter = new ZhujiListAdapter(R.layout.item_zhujidingzhi_list, datas);
        adapter.setSelf(true);

        recyclerView.setAdapter(adapter);

        refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉业务
                pageNo = 0;
                addData(mParam1);
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
        refreshLayout.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_green_light, android.R.color.holo_blue_light);

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                addData(mParam1);
            }
        },recyclerView);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ZhujiBean zhujiBean = (ZhujiBean) adapter.getData().get(position);
                Intent intent = new Intent(getActivity(),WodeZhujiDingzhiDetailActivity.class);
                intent.putExtra("id",zhujiBean.getId());
                ActivityUtils.startActivity(intent);
            }
        });
    }

    private int pageNo;

    private void addData(String status) {
        pageNo++;

        HttpParams params = new HttpParams();

        params.put("sign", SPUtils.getInstance().getString("sign"));
        params.put("token", SPUtils.getInstance().getString("token"));
        params.put("pageNo", pageNo);
        params.put("pageSize", 20);
        params.put("status",mParam1);

        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.WODEZHUJILIST)
                .tag(this)
                .params(params);

        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                refreshLayout.setRefreshing(false);
                LogUtils.v("WODEZHUJILIST", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");

                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {

                            RequestBackUtil.parseArray(jsonObject.getJSONArray("data"),
                                    datas, pageNo, adapter, ZhujiBean.class);

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


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }
}
