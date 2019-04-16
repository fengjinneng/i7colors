package com.company.qcy.ui.activity.pengyouquan;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.MyLoadMoreView;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.pengyouquan.WodeaiteMessageAdapter;
import com.company.qcy.bean.pengyouquan.WodeaiteMessageBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import java.util.ArrayList;
import java.util.List;

public class WodeaiteMessageActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;
    private WodeaiteMessageAdapter adapter;
    private List<WodeaiteMessageBean> datas;
    private int pageNo;
    private boolean isRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wodeaite_message);
        initView();
    }

    private void initView() {
        datas =new ArrayList<>();
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.activity_aite_message_recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_aite_message_swipeRefreshLayout);

        mToolbarTitle.setText("@我的");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WodeaiteMessageAdapter(R.layout.item_aitewode_message,datas);
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                pageNo=0;
                addData();
            }
        };
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                onRefreshListener.onRefresh();
            }
        });

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                addData();
            }
        },recyclerView);
        adapter.setLoadMoreView(new MyLoadMoreView());

    }


    private void addData() {
        pageNo++;
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.AITEWODEXIAOXILIST)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("token", SPUtils.getInstance().getString("token"))
                .params("pageNo",pageNo)
                .params("pageSize",20);

        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("AITEWODEXIAOXILIST", response.body());
                swipeRefreshLayout.setRefreshing(false);
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
                            List<WodeaiteMessageBean> wodeaiteMessageBeans =
                                    JSONObject.parseArray(data.toString(), WodeaiteMessageBean.class);

                            if(isRefresh){
                                datas.clear();
                                datas.addAll(wodeaiteMessageBeans);
                                adapter.setNewData(datas);
                                adapter.loadMoreComplete();
                                isRefresh =false;
                                return;
                            }
                            datas.addAll(wodeaiteMessageBeans);
                            adapter.setNewData(datas);
                            adapter.loadMoreComplete();

                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(WodeaiteMessageActivity.this, request, this);
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
                swipeRefreshLayout.setRefreshing(false);
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
            case R.id.toolbar_back:
                finish();
                break;
        }
    }
}
