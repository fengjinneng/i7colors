package com.company.qcy.fragment.chanyezixun;

import android.app.Activity;
import android.content.Context;
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
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.chanyezixun.ChanyezixunRecyclerviewAdapter;
import com.company.qcy.bean.ChanpindatingBean;
import com.company.qcy.bean.chanyezixun.NewsBean;
import com.company.qcy.bean.qiugou.QiugouBean;
import com.company.qcy.ui.activity.chanyezixun.ZixunxiangqingActivity;
import com.company.qcy.ui.activity.qiugoudating.QiugoudatingActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;


public class RenwufangtanFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private String mParam1;
    private View view;


    public RenwufangtanFragment() {
        // Required empty public constructor
    }

    public static RenwufangtanFragment newInstance(String param1) {
        RenwufangtanFragment fragment = new RenwufangtanFragment();
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

    private RecyclerView recyclerView;
    private Context context;
    private ChanyezixunRecyclerviewAdapter adapter;
    private List<NewsBean> datas;

    private Activity activity;
    private SwipeRefreshLayout refreshLayout;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chanyezixun, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.chanyezixun_recyclerview);
        refreshLayout = view.findViewById(R.id.fragment_chanyezixun_swiperefreshLayout);
        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        datas = new ArrayList<>();
        //创建适配器
        adapter = new ChanyezixunRecyclerviewAdapter(R.layout.item_chanyezixun_fragment, datas);
        adapter.setEnableLoadMore(false);

        //给RecyclerView设置适配器
        recyclerView.setAdapter(adapter);

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                addData();
            }
        }, recyclerView);
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

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {


                Intent intent = new Intent(activity, ZixunxiangqingActivity.class);
                Long id = ((NewsBean) adapter.getData().get(position)).getId();
                intent.putExtra("id",id);
                ActivityUtils.startActivity(intent);

            }
        });

    }

    private boolean isReflash;
    private int pageNo;

    private void addData() {
        pageNo++;
        OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.INFORMATIONLIST)
                .tag(this)

                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("pageNo", pageNo)
                .params("pageSize", 20)
                .params("newsType", 89)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        try {
                            if (response.code() == 200) {

                                JSONObject jsonObject = JSONObject.parseObject(response.body());

                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                    JSONArray data = jsonObject.getJSONArray("data");
                                    LogUtils.v("QIUGOULIEBIAO", data);
                                    if (ObjectUtils.isEmpty(data)) {
                                        if(refreshLayout.isRefreshing()){
                                            refreshLayout.setRefreshing(false);
                                        }
                                        adapter.loadMoreEnd();
                                        return;
                                    }
                                    List<NewsBean> newsBeans = JSONObject.parseArray(data.toJSONString(), NewsBean.class);
                                    if (isReflash) {
                                        datas.clear();
                                        adapter.addData(newsBeans);
                                        isReflash = false;
                                        refreshLayout.setRefreshing(false);
                                        adapter.loadMoreComplete();
                                        return;
                                    }

                                    adapter.addData(newsBeans);
                                    adapter.loadMoreComplete();
                                    adapter.disableLoadMoreIfNotFullPage();
                                    return;

                                } else
                                    SignAndTokenUtil.checkSignAndToken(activity, jsonObject);

                            } else {
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });


    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        activity = ((Activity) context);
    }
}
