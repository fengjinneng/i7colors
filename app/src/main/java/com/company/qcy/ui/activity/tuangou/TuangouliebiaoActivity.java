package com.company.qcy.ui.activity.tuangou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.NetworkImageHolderView;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.tuangou.TuangouRecyclerviewAdapter;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.BannerBean;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.bean.tuangou.TuangouBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import java.util.ArrayList;
import java.util.List;

public class TuangouliebiaoActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    /**
     * 设置
     */
    private TextView mToolbarText;
    private RecyclerView recyclerView;

    private TuangouRecyclerviewAdapter adapter;
    private List<TuangouBean> datas;
    private SwipeRefreshLayout refreshLayout;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuangouliebiao);
        initView();
    }

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarText = (TextView) findViewById(R.id.toolbar_text);
        recyclerView = (RecyclerView) findViewById(R.id.actiity_tuangouliebiao_recycleriew);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.actiity_tuangouliebiao_swipeRefreshLayout);


        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        datas = new ArrayList<>();
        //创建适配器
        adapter = new TuangouRecyclerviewAdapter(R.layout.item_tuangouliebiao, datas);
        adapter.setEnableLoadMore(false);
        recyclerView.setAdapter(adapter);


        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_fengexian));
        recyclerView.addItemDecoration(itemDecoration);

        refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if(isFirstIn){
                    adapter.setEmptyView(getLayoutInflater().inflate(R.layout.empty_layout,null));
                }

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

                Intent intent = new Intent(TuangouliebiaoActivity.this, TuangouxiangqingActivity.class);
                TuangouBean tuangouBean = (TuangouBean) adapter.getData().get(position);

                intent.putExtra("id", tuangouBean.getId());
                ActivityUtils.startActivity(intent);
            }
        });

        refreshLayout.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_green_light, android.R.color.holo_blue_light);
        mToolbarBack.setOnClickListener(this);
        mToolbarTitle.setText("七彩云团购惠");
        addAdvData();
        if(isFirstIn){
        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.empty_layout,null));
        isFirstIn = true;
        }

    }

    private boolean isFirstIn ;
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


    @Override
    public void onReciveMessage(MessageBean msg) {
        super.onReciveMessage(msg);
        switch (msg.getCode()) {
            case MessageBean.Code.TUANGOUCHENGGONG:
                isReflash = true;
                addData();
                break;
        }
    }


    private void addAdvData() {

        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.INDEXBANNER)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("plate_code", "APP_Group_Buy");


        DialogStringCallback stringCallback = new DialogStringCallback(this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("INDEXBANNER", response.body());

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
                            SignAndTokenUtil.getSign(TuangouliebiaoActivity.this,request,this);
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


    private boolean isReflash;
    private int pageNo;

    private void addData() {
        pageNo++;
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.GROUPBUYLIST)
                .tag(this)

                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("pageNo", pageNo)
                .params("pageSize", 20);


        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                refreshLayout.setRefreshing(false);
                LogUtils.v("GROUPBUYLIST", response.body());

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
                                return;
                            }

                            datas.addAll(qiugouBeans);
                            adapter.setNewData(datas);
                            adapter.loadMoreComplete();
                            adapter.disableLoadMoreIfNotFullPage();
                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(TuangouliebiaoActivity.this,request,this);
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
                adapter.setEmptyView(getLayoutInflater().inflate(R.layout.empty_layout,null));
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
