package com.company.qcy.fragment.home;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.qcy.R;
import com.company.qcy.adapter.qiugou.WodeqiugouxiangqingAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class WodeqiugouxiangqingFragment extends Fragment {


    private View view;
    private RecyclerView mFragmentWodeQiugouRecyclerview;
    private Context context;

    private WodeqiugouxiangqingAdapter adapter;


    public WodeqiugouxiangqingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wodeqiugouxiangqing, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
//        mFragmentWodeQiugouRecyclerview = view.findViewById(R.id.fragment_wode_qiugou_recyclerview);
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mFragmentWodeQiugouRecyclerview.setLayoutManager(layoutManager);
//
//        List<ChanpindatingBean> datas = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//
//            datas.add(new ChanpindatingBean(""));
//        }
//
//        //创建适配器
//        adapter = new WodeqiugouxiangqingAdapter(R.layout.item_wode_qiugouliebiao, datas);
//
//        //给RecyclerView设置适配器
//        mFragmentWodeQiugouRecyclerview.setAdapter(adapter);
//        DividerItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
//
//        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_fengexian));
//        mFragmentWodeQiugouRecyclerview.addItemDecoration(itemDecoration);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context =context;

    }

}
