package com.company.qcy.huodong.daixiao.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.qcy.R;
import com.company.qcy.huodong.daixiao.adapter.DaixiaoCanshuAdapter;
import com.company.qcy.huodong.daixiao.adapter.DaixiaoListAdapter;
import com.company.qcy.huodong.daixiao.bean.DaixiaoBean;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DaixiaoCanshuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DaixiaoCanshuFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private DaixiaoBean mParam1;
    private View view;
    private RecyclerView recyclerView;
    private DaixiaoCanshuAdapter adapter;


    public DaixiaoCanshuFragment() {
        // Required empty public constructor
    }

    public static DaixiaoCanshuFragment newInstance(DaixiaoBean param1) {
        DaixiaoCanshuFragment fragment = new DaixiaoCanshuFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daixiao_canshu, container, false);
    }

    private void initView(View inflate) {


        recyclerView = inflate.findViewById(R.id.fragment_daixiao_canshu_recyclerview);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new DaixiaoCanshuAdapter(R.layout.item_daixiao_canshu, mParam1.getDictMap());

        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

    }
}
