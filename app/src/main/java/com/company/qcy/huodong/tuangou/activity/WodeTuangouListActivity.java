package com.company.qcy.huodong.tuangou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
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
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.huodong.tuangou.adapter.TuangouRecyclerviewAdapter;
import com.company.qcy.huodong.tuangou.adapter.WodeTuangouListAdapter;
import com.company.qcy.huodong.tuangou.bean.TuangouBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import java.util.ArrayList;
import java.util.List;

public class WodeTuangouListActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mActicityWodeTuangouListBack;
    /**
     * ···
     */
    private TextView mActicityWodeTuangouListShare;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    private WodeTuangouListAdapter adapter;

    private List<TuangouBean> datas;

    private SwipeRefreshLayout.OnRefreshListener refreshListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wode_tuangou_list);
        initView();
        initOther();
    }

    private void initView() {
        mActicityWodeTuangouListBack = (ImageView) findViewById(R.id.acticity_wode_tuangou_list_back);
        mActicityWodeTuangouListBack.setOnClickListener(this);
        mActicityWodeTuangouListShare = (TextView) findViewById(R.id.acticity_wode_tuangou_list_share);
        mActicityWodeTuangouListShare.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.acticity_wode_tuangou_list_recyclerView);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.acticity_wode_tuangou_list_swipeRefreshLayout);


    }

    private void initOther() {

        datas = new ArrayList<>();
        adapter = new WodeTuangouListAdapter(R.layout.item_wodetuangou_ist, datas);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

//                if (isFirstIn) {
////                    adapter.setEmptyView(getLayoutInflater().inflate(R.layout.empty_layout, null));
////                }

                //下拉业务
                isReflash = true;
                pageNo = 0;
                addData();
            }
        };
        refreshLayout.setOnRefreshListener(refreshListener);
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                refreshListener.onRefresh();
            }
        });

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                addData();
            }
        }, recyclerView);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TuangouBean tuangouBean = (TuangouBean) adapter.getData().get(position);

                //当前团购是否可以砍价（是否参与砍价），1可以砍价；0不可砍价
                if (StringUtils.equals("1", tuangouBean.getIsCutPrice())) {
                    Intent intent = new Intent(WodeTuangouListActivity.this, ShareDetailActivity.class);
                    intent.putExtra("tuangouBean", tuangouBean);
                    ActivityUtils.startActivity(intent);
                } else {
                    Intent intent = new Intent(WodeTuangouListActivity.this, TuangouxiangqingActivity.class);
                    intent.putExtra("id", tuangouBean.getId());
                    ActivityUtils.startActivity(intent);
                }

            }
        });
        refreshLayout.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_green_light, android.R.color.holo_blue_light);

        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.empty_layout,null));

    }

    private boolean isReflash;
    private int pageNo;

    private void addData() {
        pageNo++;
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.WODETUANGOULIST)
                .tag(this)

                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("token", SPUtils.getInstance().getString("token"))
                .params("pageNo", pageNo)
                .params("pageSize", 20);


        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                refreshLayout.setRefreshing(false);
                LogUtils.v("WODETUANGOULIST", response.body());

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
                            List<TuangouBean> qiugouBeans = JSONObject.parseArray(data.toJSONString(), TuangouBean.class);
                            if (isReflash) {
                                datas.clear();
                                datas.addAll(qiugouBeans);
                                adapter.setNewData(datas);
                                isReflash = false;
                                adapter.loadMoreComplete();
                                adapter.disableLoadMoreIfNotFullPage();

                                return;
                            }

                            datas.addAll(qiugouBeans);
                            adapter.setNewData(datas);
                            adapter.loadMoreComplete();
                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(WodeTuangouListActivity.this, request, this);
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
                adapter.setEmptyView(getLayoutInflater().inflate(R.layout.empty_layout, null));
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
            case R.id.acticity_wode_tuangou_list_back:
                finish();
                break;
            case R.id.acticity_wode_tuangou_list_share:
                ToastUtils.showShort("分享哟！");
                break;
        }
    }
}
