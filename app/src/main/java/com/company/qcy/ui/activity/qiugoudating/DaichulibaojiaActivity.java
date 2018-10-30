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
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.qiugou.WodebaojiaAdapter;
import com.company.qcy.adapter.qiugou.WodeqiugouxiangqingAdapter;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.bean.qiugou.BaojiaBean;
import com.company.qcy.bean.qiugou.QiugouBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

public class DaichulibaojiaActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 求购中
     */
    private TextView mActivityDaichulibaojiaTitle;
    private ImageView mActivityDaichulibaojiaBack;
    /**
     * (待确认报价)
     */
    private TextView mActivityDdaichulibaojiaStatus;
    private RecyclerView recyclerView;
    private WodebaojiaAdapter adapter;
    private List<BaojiaBean> datas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daichulibaojia);
        initView();
    }

    private void initView() {
        mActivityDaichulibaojiaTitle = (TextView) findViewById(R.id.activity_daichulibaojia_title);
        mActivityDaichulibaojiaBack = (ImageView) findViewById(R.id.activity_daichulibaojia_back);
        mActivityDaichulibaojiaBack.setOnClickListener(this);
        mActivityDdaichulibaojiaStatus = (TextView) findViewById(R.id.activity_ddaichulibaojia_status);
        recyclerView = (RecyclerView) findViewById(R.id.activity_daichulibaojia_recyclerview);

        datas =new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_fengexian));
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        //创建适配器
        adapter = new WodebaojiaAdapter(R.layout.item_wode_qiugouliebiao, datas);
        adapter.setEnableLoadMore(false);
        recyclerView.setAdapter(adapter);
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

                Intent i = new Intent(DaichulibaojiaActivity.this, QiugouxiangqingActivity.class);
                BaojiaBean baojialiebiaoBean = (BaojiaBean) adapter.getData().get(position);
                i.putExtra("enquiryId", baojialiebiaoBean.getEnquiryDomain().getId());
                i.putExtra("wode", 1);
                ActivityUtils.startActivity(i);
            }
        });
    }


    @Override
    public void onReciveMessage(MessageBean msg) {
        super.onReciveMessage(msg);
        switch (msg.getCode()){
            case MessageBean.Code.CAINABAOJIACHENGGONG:



                break;
        }
    }

    private int pageNo;

    private void addData() {
        pageNo++;
        OkGo.<String>get(ServerInfo.SERVER+ InterfaceInfo.MAIJIAYIJIESHOULIST)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("token", SPUtils.getInstance().getString("token"))
                .params("pageNo", pageNo)
                .params("pageSize", 20)
                .execute(new DialogStringCallback(this) {
                    @Override
                    public void onSuccess(Response<String> response) {

                        try {
                            LogUtils.v("MAIJIAYIJIESHOULIST", response.body());
                            if (response.code() == 200) {

                                JSONObject jsonObject = JSONObject.parseObject(response.body());

                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                    JSONArray data = jsonObject.getJSONArray("data");

                                    List<BaojiaBean> baojiaBeans = JSONObject.parseArray(data.toJSONString(), BaojiaBean.class);
//                                    if (isReflash) {
//                                        datas.clear();
//                                        adapter.addData(qiugoudatingBeans);
//                                        isReflash = false;
//                                        refreshLayout.setRefreshing(false);
//                                        return;
//                                    }
                                    if (ObjectUtils.isEmpty(baojiaBeans)) {
                                        adapter.loadMoreEnd();
                                        return;
                                    }
                                    adapter.addData(baojiaBeans);
                                    adapter.loadMoreComplete();
                                    adapter.disableLoadMoreIfNotFullPage();
                                    return;

                                } else
                                    SignAndTokenUtil.checkSignAndToken(DaichulibaojiaActivity.this, jsonObject);

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
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.activity_daichulibaojia_back:
                finish();
                break;
        }
    }
}
