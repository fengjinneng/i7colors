package com.company.qcy.ui.activity.zhuji;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.company.qcy.adapter.zhuji.ZhujiQiyeListAdapter;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.base.RequestBackUtil;
import com.company.qcy.bean.zhuji.ZhujiBean;
import com.company.qcy.bean.zhuji.ZhujiQiyeBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import java.util.ArrayList;
import java.util.List;

public class ZhujiQiyeListActivity extends BaseActivity implements View.OnClickListener {

    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;
    private List<ZhujiQiyeBean> datas;
    private ZhujiQiyeListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuji_qiye_list);
        initView();
    }

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.activity_zhuji_qiye_list_recyclerview);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_zhuji_qiye_list_swipeRefreshLayout);

        mToolbarTitle.setText("助剂定制专场");
        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        datas = new ArrayList<>();

        adapter = new ZhujiQiyeListAdapter(R.layout.item_zhuji_qiye_list,datas);

        refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉业务
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

        refreshLayout.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_green_light, android.R.color.holo_blue_light);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                ZhujiQiyeBean zhujiQiyeBean = (ZhujiQiyeBean) adapter.getData().get(position);
                if(!ObjectUtils.isEmpty(zhujiQiyeBean)){
                    Intent intent = new Intent(ZhujiQiyeListActivity.this,ZhujiListActivity.class);
                    intent.putExtra("specialId",zhujiQiyeBean.getId());
                    intent.putExtra("name",zhujiQiyeBean.getName());
                    intent.putExtra("imgUrl",ServerInfo.IMAGE + zhujiQiyeBean.getMobileBanner());
                    ActivityUtils.startActivity(intent);

                }
            }
        });

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                addData();

            }
        }, recyclerView);

    }

    private int pageNo;

    private void addData() {

        pageNo++;

        HttpParams params = new HttpParams();

        params.put("sign", SPUtils.getInstance().getString("sign"));
        params.put("pageNo", pageNo);
        params.put("pageSize", 20);

        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.ZHUJIQIYELIST)
                .tag(this)
                .params(params);

        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                refreshLayout.setRefreshing(false);
                LogUtils.v("ZHUJIQIYELIST", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");

                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {

                            RequestBackUtil.parseArray(jsonObject.getJSONArray("data"),
                                    datas, pageNo, adapter, ZhujiQiyeBean.class);
                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(ZhujiQiyeListActivity.this, request, this);
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
