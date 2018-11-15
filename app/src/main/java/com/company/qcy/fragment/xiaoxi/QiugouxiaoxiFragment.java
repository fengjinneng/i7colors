package com.company.qcy.fragment.xiaoxi;

import android.content.Intent;
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
import com.company.qcy.adapter.message.NormalMessageAdapter;
import com.company.qcy.bean.message.MessageBean;
import com.company.qcy.ui.activity.message.MessageDetailActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link QiugouxiaoxiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QiugouxiaoxiFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private List<MessageBean> datas;
    private NormalMessageAdapter adapter;

    public QiugouxiaoxiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QiugouxiaoxiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QiugouxiaoxiFragment newInstance(String param1, String param2) {
        QiugouxiaoxiFragment fragment = new QiugouxiaoxiFragment();
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
        return inflater.inflate(R.layout.fragment_qiugouxiaoxi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_qiugouxiaoxi_recyclerview);
        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        datas = new ArrayList<>();
        adapter = new NormalMessageAdapter(R.layout.item_message, datas);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_fengexian));
        recyclerView.addItemDecoration(itemDecoration);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
               addData();
            }
        }, recyclerView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MessageBean messageBean = (MessageBean) adapter.getData().get(position);
                Intent intent = new Intent(getContext(), MessageDetailActivity.class);
                intent.putExtra("id",messageBean.getId());
                intent.putExtra("isfrom","qiugou");
                ActivityUtils.startActivity(intent);
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        pageNo =0;
        addData();
    }


    private int pageNo;

    private void addData() {

        pageNo++;
        OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.GETENQUIRYINFORMLIST)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("pageNo", pageNo)
                .params("pageSize", 20)
                .params("type", "buyer")
                .params("token", SPUtils.getInstance().getString("token"))
                .execute(new DialogStringCallback(getActivity()) {
                    @Override
                    public void onSuccess(Response<String> response) {

                        try {
                            if (response.code() == 200) {

                                JSONObject jsonObject = JSONObject.parseObject(response.body());

                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                    JSONArray data = jsonObject.getJSONArray("data");
                                    LogUtils.v("GETENQUIRYINFORMLIST", data);
                                    if(pageNo==1){
                                        datas.clear();
                                    }
                                    List<MessageBean> messageBeans = JSONObject.parseArray(data.toJSONString(), MessageBean.class);
                                    if (ObjectUtils.isEmpty(messageBeans)) {
                                        adapter.loadMoreEnd();
                                        return;
                                    }
                                    adapter.addData(messageBeans);
                                    adapter.loadMoreComplete();
                                    adapter.disableLoadMoreIfNotFullPage();
                                    return;

                                } else
                                    SignAndTokenUtil.checkSignAndToken(getActivity(), jsonObject);

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

}
