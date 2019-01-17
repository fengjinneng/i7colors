package com.company.qcy.huodong.youhuizhanxiao.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.huodong.youhuizhanxiao.adapter.YouhuizhanxiaoCanshuAdapter;
import com.company.qcy.huodong.youhuizhanxiao.adapter.YouhuizhanxiaojiluAdapter;
import com.company.qcy.huodong.youhuizhanxiao.bean.YouhuizhanxiaoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link YouhuizhanxiaoCanshuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YouhuizhanxiaoCanshuFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private ArrayList<YouhuizhanxiaoBean.ListSaleBean> param1;


    public YouhuizhanxiaoCanshuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment YouhuizhanxiaoCanshuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static YouhuizhanxiaoCanshuFragment newInstance(ArrayList<YouhuizhanxiaoBean.ListSaleBean> param1) {
        YouhuizhanxiaoCanshuFragment fragment = new YouhuizhanxiaoCanshuFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1,param1);
        fragment.setArguments(args);
        return fragment;
    }



    private RecyclerView recyclerView;
    private YouhuizhanxiaoCanshuAdapter adapter;

    private void initView(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_youhuizhanxiao_canshu_recyclerview);
        adapter = new YouhuizhanxiaoCanshuAdapter(R.layout.item_huodong_canshu, param1);

        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.empty_layout,null));
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            param1 = getArguments().getParcelableArrayList(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_youhuizhanxiao_canshu, container, false);
        initView(inflate);
        return inflate;
    }

}
