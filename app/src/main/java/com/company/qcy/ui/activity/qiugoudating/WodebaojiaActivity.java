package com.company.qcy.ui.activity.qiugoudating;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.Myenty;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.qiugou.WodebaojiaAdapter;
import com.company.qcy.bean.qiugou.BaojiaBean;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

public class WodebaojiaActivity extends AppCompatActivity {

    private CommonTabLayout commonTabLayout;
    private RecyclerView recyclerView;
    private List<BaojiaBean> datas;
    private WodebaojiaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wodebaojia);
        status = getIntent().getStringExtra("status");
        initView();
    }

    private void initView() {
        commonTabLayout = (CommonTabLayout) findViewById(R.id.activity_wode_baojia_slideingtablayout);
        recyclerView = (RecyclerView) findViewById(R.id.activity_wodebaojia_recyclerview);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        datas = new ArrayList<>();
        ArrayList<CustomTabEntity> d = new ArrayList<>();
        Myenty myenty = new Myenty("全部报价", R.mipmap.buy, R.mipmap.sell);
        Myenty myenty1 = new Myenty("报价中", R.mipmap.buy, R.mipmap.sell);
        Myenty myenty2 = new Myenty("已采纳", R.mipmap.buy, R.mipmap.sell);
        Myenty myenty3 = new Myenty("已关闭", R.mipmap.buy, R.mipmap.sell);
        d.add(myenty);
        d.add(myenty1);
        d.add(myenty2);
        d.add(myenty3);
        commonTabLayout.setTabData(d);
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                pageNo = 0;

                switch (position) {
                    case 0:
                        status = "";
                        break;
                    case 1:
                        status = "0";
                        break;
                    case 2:
                        status = "1";
                        break;
                    case 3:
                        status = "2";
                        break;
                }
                datas.clear();
                adapter.notifyDataSetChanged();
                addData();
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        if(!StringUtils.isEmpty(status)){
            commonTabLayout.setCurrentTab(Integer.parseInt(status)+1);
        }
        //创建适配器
        adapter = new WodebaojiaAdapter(R.layout.item_wode_qiugouliebiao, datas);
        adapter.setEnableLoadMore(false);
        //给RecyclerView设置适配器
        recyclerView.setAdapter(adapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_fengexian));
        recyclerView.addItemDecoration(itemDecoration);
        addData();

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                addData();


            }
        }, recyclerView);

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent i = new Intent(WodebaojiaActivity.this, QiugouxiangqingActivity.class);
                BaojiaBean baojialiebiaoBean = (BaojiaBean) adapter.getData().get(position);
                i.putExtra("enquiryId", baojialiebiaoBean.getEnquiryDomain().getId());
                i.putExtra("wode", 1);
                ActivityUtils.startActivity(i);
            }
        });
    }

    private int pageNo;
    private String status;

    private void addData() {
        pageNo++;
        OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.MYBAOJIA)
                .tag(this)

                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("token", SPUtils.getInstance().getString("token"))
                .params("pageNo", pageNo)
                .params("pageSize", 20)
                .params("status", status)
                .execute(new DialogStringCallback(this) {
                    @Override
                    public void onSuccess(Response<String> response) {

                        try {
                            if (response.code() == 200) {

                                JSONObject jsonObject = JSONObject.parseObject(response.body());

                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                    JSONArray data = jsonObject.getJSONArray("data");
                                    LogUtils.v("MYBAOJIA", data);

                                    List<BaojiaBean> baojialiebiaoBeans = JSONObject.parseArray(data.toJSONString(), BaojiaBean.class);
//                                    if (isReflash) {
//                                        datas.clear();
//                                        adapter.addData(qiugoudatingBeans);
//                                        isReflash = false;
//                                        refreshLayout.setRefreshing(false);
//                                        return;
//                                    }
                                    if (ObjectUtils.isEmpty(baojialiebiaoBeans)) {
                                        adapter.loadMoreEnd();
                                        return;
                                    }
                                    adapter.addData(baojialiebiaoBeans);
                                    adapter.loadMoreComplete();
                                    adapter.disableLoadMoreIfNotFullPage();
                                    return;

                                } else
                                    SignAndTokenUtil.checkSignAndToken(WodebaojiaActivity.this, jsonObject);

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
}
