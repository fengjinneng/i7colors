package com.company.qcy.fragment.chanyezixun;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.qcy.R;
import com.company.qcy.adapter.chanpindating.ChanyezixunRecyclerviewAdapter;
import com.company.qcy.bean.ChanpindatingBean;

import java.util.ArrayList;
import java.util.List;


public class ChanyezixunFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private String mParam1;


    public ChanyezixunFragment() {
        // Required empty public constructor
    }

    public static ChanyezixunFragment newInstance(String param1) {
        ChanyezixunFragment fragment = new ChanyezixunFragment();
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

    RecyclerView recyclerView ;
    Context context;
    ChanyezixunRecyclerviewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chanyezixun, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.chanyezixun_recyclerview);
        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        List<ChanpindatingBean> datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            datas.add(new ChanpindatingBean("山东索玛德染料有限公司"+i));
        }
        //创建适配器
        adapter = new ChanyezixunRecyclerviewAdapter(R.layout.item_chanyezixun_fragment, datas);


        //给RecyclerView设置适配器
        recyclerView.setAdapter(adapter);

    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context =context;
    }
}
