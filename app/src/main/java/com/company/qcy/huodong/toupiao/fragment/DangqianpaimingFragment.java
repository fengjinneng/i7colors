package com.company.qcy.huodong.toupiao.fragment;


import android.app.Dialog;
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
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.MyLoadMoreView;
import com.company.qcy.Utils.MyLoadMoreView2;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.Utils.UserUtil;
import com.company.qcy.base.BaseFragment;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.huodong.toupiao.activity.ToupiaoSearchActivity;
import com.company.qcy.huodong.toupiao.activity.XuanshouDetailActivity;
import com.company.qcy.huodong.toupiao.adapter.XuanshouAdapter;
import com.company.qcy.huodong.toupiao.bean.XuanshouBean;
import com.company.qcy.ui.activity.user.LoginActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DangqianpaimingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DangqianpaimingFragment extends BaseFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    /**
     * 请输入参选编号或名称
     */
    private TextView search;
    private RecyclerView recyclerView;
    private XuanshouAdapter adapter;
    private List<XuanshouBean> datas;
    private SwipeRefreshLayout refreshLayout;
    /**
     * 请输入参选编号或名称
     */
    private TextView mFragmentDangqianpaimingSearch;


    public DangqianpaimingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment DangqianpaimingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DangqianpaimingFragment newInstance(String param1) {
        DangqianpaimingFragment fragment = new DangqianpaimingFragment();
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
        return inflater.inflate(R.layout.fragment_dangqianpaiming, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View inflate) {
        search = inflate.findViewById(R.id.fragment_dangqianpaiming_search);
        recyclerView = inflate.findViewById(R.id.fragment_dangqianpaiming_recyclerview);
        refreshLayout = inflate.findViewById(R.id.fragment_dangqianpaiming_swipeRefreshLayout);
        search.setOnClickListener(this);
        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        datas = new ArrayList<>();

        adapter = new XuanshouAdapter(R.layout.item_toupiao_xuanshou, datas);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isReflash = true;
                pageNo = 0;
                addData();
            }
        });
        recyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {

                addData();
            }
        }, recyclerView);
        adapter.setLoadMoreView(new MyLoadMoreView2());

        addData();

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.item_toupiao_xuanshou_toupiao:

                        if(!UserUtil.isLogin()){
                            ActivityUtils.startActivity(LoginActivity.class);
                            return;
                        }
                        Button toupiao = (Button) adapter.getViewByPosition(recyclerView, position, R.id.item_toupiao_xuanshou_toupiao);
                        XuanshouBean xuanshouBean = (XuanshouBean) adapter.getData().get(position);
                        toupiao(toupiao,xuanshouBean,position);

                        break;
                }
            }
        });


        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                XuanshouBean xuanshouBean = (XuanshouBean) adapter.getData().get(position);
                Intent intent = new Intent(getActivity(), XuanshouDetailActivity.class);
                intent.putExtra("mainId",mParam1);
                intent.putExtra("id",xuanshouBean.getId()+"");
                ActivityUtils.startActivity(intent);
            }
        });
    }

    private void toupiao(Button button,XuanshouBean xuanshouBean,int positon) {
        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.TOUPIAO)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("token", SPUtils.getInstance().getString("token"))
                .params("mainId", mParam1)
                .params("applicationId", xuanshouBean.getId())
                .params("from", "app_android");

        DialogStringCallback stringCallback = new DialogStringCallback(getActivity()) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("TOUPIAO", response.body());
                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            button.setText("已投"+(Integer.parseInt(xuanshouBean.getJoinedTicketNum())+1)+"票");
                            xuanshouBean.setTicketNum(String.valueOf(Integer.parseInt(xuanshouBean.getTicketNum())+1));
                            xuanshouBean.setJoinedTicketNum(String.valueOf(Integer.parseInt(xuanshouBean.getJoinedTicketNum())+1));
                            adapter.notifyItemChanged(positon);
                            EventBus.getDefault().post(new MessageBean(MessageBean.Code.TOUPIAOCHENGGONG));
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
                ToastUtils.showShort(getResources().getString(R.string.NETEXCEPTION));
            }
        };

        request.execute(stringCallback);


    }


    @Override
    public void onRec(MessageBean messageBean) {
        super.onRec(messageBean);

        switch (messageBean.getCode()){
            case MessageBean.Code.CANYUTOUPIAOCHENGGONG:
                isReflash = true;
                pageNo = 0;
                addData();
                break;

            case MessageBean.Code.TOUPIAOCHENGGONG:
                isReflash = true;
                pageNo = 0;
                addData();
                break;

        }
    }

    private int pageNo;
    private boolean isReflash;

    private void addData() {
        pageNo++;
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.CANSHAIRENYUANLIST)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("pageNo", pageNo)
                .params("pageSize", 50)
                .params("mainId", mParam1)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("name", "")
                .params("number", "");

        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                refreshLayout.setRefreshing(false);
                LogUtils.v("CANSHAIRENYUANLIST", response.body());

                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONArray data = jsonObject.getJSONArray("data");

                            if (ObjectUtils.isEmpty(data)) {
                                adapter.loadMoreEnd();
                                return;
                            }
                            List<XuanshouBean> youhuizhanxiaoBeans = JSONObject.parseArray(data.toJSONString(), XuanshouBean.class);
                            if (isReflash) {
                                datas.clear();
                                datas.addAll(youhuizhanxiaoBeans);
                                adapter.setNewData(datas);
                                isReflash = false;
                                adapter.loadMoreComplete();
                                return;
                            }
                            datas.addAll(youhuizhanxiaoBeans);
                            adapter.setNewData(datas);
                            adapter.loadMoreComplete();
//                            adapter.disableLoadMoreIfNotFullPage();
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
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.fragment_dangqianpaiming_search:

                Intent intent = new Intent(getActivity(),ToupiaoSearchActivity.class);
                intent.putExtra("id",mParam1);//mainId
                ActivityUtils.startActivity(intent);

                break;
        }
    }
}
