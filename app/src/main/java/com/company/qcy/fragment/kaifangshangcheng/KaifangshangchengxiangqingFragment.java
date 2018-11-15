package com.company.qcy.fragment.kaifangshangcheng;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
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
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.kaifangshangcheng.KFSCXiangqingRecyclerviewAdapter;
import com.company.qcy.bean.kaifangshangcheng.DianpuxiangqingBean;
import com.company.qcy.bean.kaifangshangcheng.ProductBean;
import com.company.qcy.ui.activity.kaifangshangcheng.KFSCXiangqingActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;


public class KaifangshangchengxiangqingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "ARG_PARAM1";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private RecyclerView recyclerview;
    private KFSCXiangqingRecyclerviewAdapter adapter;

    public KaifangshangchengxiangqingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment KaifangshangchengxiangqingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KaifangshangchengxiangqingFragment newInstance(String param1) {
        KaifangshangchengxiangqingFragment fragment = new KaifangshangchengxiangqingFragment();
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
        View inflate = inflater.inflate(R.layout.fragment_kaifangshangchengxiangqing, container, false);
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {

        recyclerview = (RecyclerView) inflate.findViewById(R.id.fragment_kfsc_xiangqing_recyclerview);

        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        List<ProductBean> datas = new ArrayList<>();
        adapter = new KFSCXiangqingRecyclerviewAdapter(R.layout.item_kfsc_xiangqing, datas);
        recyclerview.setAdapter(adapter);
        addShangpinData();
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                addShangpinData();
            }
        }, recyclerview);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_fengexian));
        recyclerview.addItemDecoration(itemDecoration);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    private int pageNo;

    private void addShangpinData() {
        pageNo++;
        OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.KFSCSHANGPINLIEBIAO)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("pageNo", pageNo)
                .params("pageSize", 10)
                .params("marketId", Long.valueOf(mParam1))
                .execute(new DialogStringCallback(getActivity()) {
                    @Override
                    public void onSuccess(Response<String> response) {

                        try {
                            if (response.code() == 200) {

                                JSONObject jsonObject = JSONObject.parseObject(response.body());

                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                    JSONArray data = jsonObject.getJSONArray("data");
                                    LogUtils.v("addShangpinData", data);
                                    List<ProductBean> productBeans = JSONObject.parseArray(data.toJSONString(), ProductBean.class);

                                    if (ObjectUtils.isEmpty(productBeans)) {
                                        adapter.loadMoreEnd();
                                        return;
                                    }
                                    adapter.addData(productBeans);
                                    adapter.loadMoreComplete();
                                    adapter.disableLoadMoreIfNotFullPage();
                                    return;

                                } else
                                    SignAndTokenUtil.checkSignAndToken(getActivity(), jsonObject);

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
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
