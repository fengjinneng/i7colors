package com.company.qcy.fragment.home;

import android.content.Context;
import android.net.Uri;
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
import com.company.qcy.adapter.other.KaifangshangchengRecyclerviewAdapter;
import com.company.qcy.adapter.other.ToutiaoViewpagerRecyclerviewAdapter;
import com.company.qcy.bean.ChanpindatingBean;

import java.util.ArrayList;
import java.util.List;


public class ToutiaoViewpagerFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;


    public ToutiaoViewpagerFragment() {
        // Required empty public constructor
    }

    public static ToutiaoViewpagerFragment newInstance(String param1) {
        ToutiaoViewpagerFragment fragment = new ToutiaoViewpagerFragment();
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
        return inflater.inflate(R.layout.fragment_toutiao_viewpager, container, false);
    }

    private RecyclerView recyclerview;
    private Context context;
    private ToutiaoViewpagerRecyclerviewAdapter adapter;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerview = view.findViewById(R.id.frgment_toutiao_viewpager_recyclerview);
        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);

        List<ChanpindatingBean> datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            datas.add(new ChanpindatingBean("2018-09-14"+i));
        }

        //创建适配器
        adapter = new ToutiaoViewpagerRecyclerviewAdapter(R.layout.item_toutiao_recyclerview, datas);

        //给RecyclerView设置适配器
        recyclerview.setAdapter(adapter);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context =context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
