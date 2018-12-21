package com.company.qcy.ui.activity.qiugoudating;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
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
import com.company.qcy.Myenty;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.qiugou.WodebaojiaAdapter;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.qiugou.BaojiaBean;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import java.util.ArrayList;
import java.util.List;

public class WodebaojiaActivity extends BaseActivity implements View.OnClickListener {

    private CommonTabLayout commonTabLayout;
    private RecyclerView recyclerView;
    private List<BaojiaBean> datas;
    private WodebaojiaAdapter adapter;
    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;

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

        if (!StringUtils.isEmpty(status)) {
            commonTabLayout.setCurrentTab(Integer.parseInt(status) + 1);
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
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mToolbarTitle.setText("我的报价");
        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.empty_layout,null));
    }

    private int pageNo;
    private String status;

    private void addData() {
        pageNo++;
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.MYBAOJIA)
                .tag(this)

                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("token", SPUtils.getInstance().getString("token"))
                .params("pageNo", pageNo)
                .params("pageSize", 20)
                .params("status", status);

        DialogStringCallback stringCallback = new DialogStringCallback(this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("MYBAOJIA", response.body());

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

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(WodebaojiaActivity.this,request,this);
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
