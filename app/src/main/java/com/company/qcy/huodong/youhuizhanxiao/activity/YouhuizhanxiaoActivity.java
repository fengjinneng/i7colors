package com.company.qcy.huodong.youhuizhanxiao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.MyLoadMoreView;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.BannerBean;
import com.company.qcy.huodong.youhuizhanxiao.bean.YouhuizhanxiaoBean;
import com.company.qcy.huodong.youhuizhanxiao.adapter.YouhuizhanxiaoAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import java.util.ArrayList;
import java.util.List;

public class YouhuizhanxiaoActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private RecyclerView recyclerView;
    private List<YouhuizhanxiaoBean> datas;
    private YouhuizhanxiaoAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youhuizhanxiao);
        initView();
    }

    private void initView() {
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_youhuizhanxiao_swipeRefreshLayout);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.activity_youhuizhanxiao_recyclerview);
        mToolbarTitle.setText("优惠展销 就等你来!");

        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        datas = new ArrayList<>();

        //创建适配器
        adapter = new YouhuizhanxiaoAdapter(R.layout.item_youhuizhanxiao_liebiao, datas);
        recyclerView.setAdapter(adapter);

        refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
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
        refreshLayout.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_green_light, android.R.color.holo_blue_light);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Intent intent = new Intent(YouhuizhanxiaoActivity.this, YouhuizhanxiaoDetailActivity.class);
                YouhuizhanxiaoBean bean = (YouhuizhanxiaoBean) adapter.getData().get(position);
                intent.putExtra("id", String.valueOf(bean.getId()));
                ActivityUtils.startActivity(intent);
            }
        });

        adapter.setLoadMoreView(new MyLoadMoreView());
        addAdvData();
    }
    private boolean isReflash;
    private int pageNo;

    private List<String> advDatas = new ArrayList<>();


    private void addHeadView() {

        View inflate = LayoutInflater.from(this).inflate(R.layout.head_img_huodong, null);
        ImageView img = inflate.findViewById(R.id.head_img_huodong_img);
        GlideUtils.loadImageRct(context,advDatas.get(0),img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        adapter.addHeaderView(inflate);
    }

    private void addAdvData() {

        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.INDEXBANNER)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("plate_code", "App_Sales_Info");


        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("App_Sales_Info", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONArray data = jsonObject.getJSONArray("data");
                            if (ObjectUtils.isEmpty(data)) {
                                return;
                            }
                            List<BannerBean> bannerBeans = JSONObject.parseArray(data.toJSONString(), BannerBean.class);
                            for (int i = 0; i < bannerBeans.size(); i++) {
                                advDatas.add(ServerInfo.IMAGE + bannerBeans.get(i).getAd_image());
                            }
                            addHeadView();

                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(YouhuizhanxiaoActivity.this,request,this);
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

    private void addData() {
        pageNo++;
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.YOUHUIZHANXIAOLIST)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("pageNo", pageNo)
                .params("pageSize", 20);

        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                refreshLayout.setRefreshing(false);
                LogUtils.v("YOUHUIZHANXIAOLIST", response.body());

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
                            List<YouhuizhanxiaoBean> youhuizhanxiaoBeans = JSONObject.parseArray(data.toJSONString(), YouhuizhanxiaoBean.class);
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
                            adapter.disableLoadMoreIfNotFullPage();
                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(YouhuizhanxiaoActivity.this, request, this);
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
            case R.id.toolbar_back:
                finish();
                break;
        }
    }
}
