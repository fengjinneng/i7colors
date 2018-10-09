package com.company.qcy.fragment.home;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.company.qcy.R;
import com.company.qcy.adapter.vlayout.GridStoreLayoutAdapter;
import com.company.qcy.adapter.vlayout.QiugouLayoutAdapter;
import com.company.qcy.adapter.vlayout.SingleAdvLayoutAdapter;
import com.company.qcy.adapter.vlayout.SingleTitleLayoutAdapter;
import com.company.qcy.adapter.vlayout.TouTiaoLayoutAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    private RecyclerView mRecyclerview;
    private SwipeRefreshLayout mSwipeRefreshlayout;
    private VirtualLayoutManager virtualLayoutManager;
    private Context context;
    private DelegateAdapter delegateAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        setBannerData();

        addTitle();

        addToutiao();

        addTitle();

        addQiugou();

        addTitle();

        addStore();


    }

    private void addStore() {
        List<String> datas = new ArrayList<>();
        datas.add("这是一条很重要的新闻");
        datas.add("这是一条很重要的新闻");
        datas.add("这是一条很重要的新闻");
        datas.add("这是一条很重要的新闻");
        datas.add("这是一条很重要的新闻");
        datas.add("这是一条很重要的新闻");
        SingleLayoutHelper helper = new SingleLayoutHelper();
        GridStoreLayoutAdapter adapter = new GridStoreLayoutAdapter(context,datas,helper);
        delegateAdapter.addAdapter(adapter);

    }

    private void addQiugou() {
        List<String> datas = new ArrayList<>();
        datas.add("这是一条很重要的新闻");
        datas.add("这是一条很重要的新闻");
        datas.add("这是一条很重要的新闻");
        datas.add("这是一条很重要的新闻");
        datas.add("这是一条很重要的新闻");
        datas.add("这是一条很重要的新闻");

        LinearLayoutHelper helper = new LinearLayoutHelper();
        QiugouLayoutAdapter adapter = new QiugouLayoutAdapter(context,helper,datas);
        delegateAdapter.addAdapter(adapter);


    }

    private void addToutiao() {
        List<String> datas = new ArrayList<>();
        datas.add("这是一条很重要的新闻");
        datas.add("这是一条很重要的新闻");
        datas.add("这是一条很重要的新闻");

        LinearLayoutHelper helper = new LinearLayoutHelper();
        TouTiaoLayoutAdapter adapter = new TouTiaoLayoutAdapter(context,helper,datas);
        delegateAdapter.addAdapter(adapter);

    }

    private void addTitle() {

        SingleLayoutHelper helper = new SingleLayoutHelper();
        SingleTitleLayoutAdapter adapter = new SingleTitleLayoutAdapter(context,helper,1,"求购信息");
        delegateAdapter.addAdapter(adapter);

    }

    private void setBannerData() {

        List<String> datas = new ArrayList<>();
        datas.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1240469072,2191573380&fm=26&gp=0.jpg");
        datas.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1240469072,2191573380&fm=26&gp=0.jpg");
        datas.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1240469072,2191573380&fm=26&gp=0.jpg");
        datas.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1240469072,2191573380&fm=26&gp=0.jpg");
        datas.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1240469072,2191573380&fm=26&gp=0.jpg");

        SingleLayoutHelper helper = new SingleLayoutHelper();
        SingleAdvLayoutAdapter adapter = new SingleAdvLayoutAdapter(context, helper, 1, datas);
        delegateAdapter.addAdapter(adapter);
    }


    private void initView(View view) {
        mRecyclerview = view.findViewById(R.id.fragment_home_recyclerview);
        mSwipeRefreshlayout = view.findViewById(R.id.fragment_home_swipe_refreshlayout);

        virtualLayoutManager = new VirtualLayoutManager(context);
        mRecyclerview.setLayoutManager(virtualLayoutManager);

        delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        mRecyclerview.setAdapter(delegateAdapter);

    }
}
