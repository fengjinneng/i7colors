package com.company.qcy.ui.activity.qiugoudating;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
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
import com.company.qcy.adapter.qiugou.WodeqiugouxiangqingAdapter;
import com.company.qcy.bean.qiugou.QiugouBean;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

public class WodeqiugouActivity extends AppCompatActivity {

    private CommonTabLayout commonTabLayout;
    private ViewPager mActivityWodeQiugouViewpger;
    private RecyclerView recyclerView;
    private WodeqiugouxiangqingAdapter adapter;
    private List<QiugouBean> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wodeqiugouxiangqing);
        status = getIntent().getStringExtra("status");
        initView();
    }


    private void initView() {
        commonTabLayout = (CommonTabLayout) findViewById(R.id.activity_wode_qiugou_slideingtablayout);

        recyclerView = (RecyclerView) findViewById(R.id.activity_wodeqiugouxiangqing_recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        datas = new ArrayList<>();
        ArrayList<CustomTabEntity> d = new ArrayList<>();
        Myenty myenty = new Myenty("全部求购", R.mipmap.buy, R.mipmap.sell);
        Myenty myenty1 = new Myenty("求购中", R.mipmap.buy, R.mipmap.sell);
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
                pageNo=0;

                switch (position) {
                    case 0:
                        status="";
                        break;
                    case 1:
                        status = "1";//求购中
                        break;
                    case 2:
                        status = "3";//已接受报价
                        break;
                    case 3:
                        status = "2";//已关闭
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
            commonTabLayout.setCurrentTab(Integer.parseInt(status));
        }

        //创建适配器
        adapter = new WodeqiugouxiangqingAdapter(R.layout.item_wode_qiugouliebiao, datas);
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

                Intent intent = new Intent(WodeqiugouActivity.this,QiugouxiangqingActivity.class);
                QiugouBean qiugouliebiaoBean = (QiugouBean) adapter.getData().get(position);
                intent.putExtra("enquiryId",qiugouliebiaoBean.getId());
                intent.putExtra("wode", 1);
                intent.putExtra("status",qiugouliebiaoBean.getStatus());
                ActivityUtils.startActivity(intent);
            }
        });

    }
    private int pageNo ;
    private String status;

    private void addData() {
        pageNo++;
        OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.MYQIUGOU)
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
                            LogUtils.v("MYQIUGOU", response.body());
                            if (response.code() == 200) {

                                JSONObject jsonObject = JSONObject.parseObject(response.body());

                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                    JSONArray data = jsonObject.getJSONArray("data");

                                    List<QiugouBean> QiugouxiangqingBeans = JSONObject.parseArray(data.toJSONString(), QiugouBean.class);
//                                    if (isReflash) {
//                                        datas.clear();
//                                        adapter.addData(qiugoudatingBeans);
//                                        isReflash = false;
//                                        refreshLayout.setRefreshing(false);
//                                        return;
//                                    }
                                    if (ObjectUtils.isEmpty(QiugouxiangqingBeans)) {
                                        adapter.loadMoreEnd();
                                        return;
                                    }
                                    adapter.addData(QiugouxiangqingBeans);
                                    adapter.loadMoreComplete();
                                    adapter.disableLoadMoreIfNotFullPage();
                                    return;

                                } else
                                    SignAndTokenUtil.checkSignAndToken(WodeqiugouActivity.this, jsonObject);

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

