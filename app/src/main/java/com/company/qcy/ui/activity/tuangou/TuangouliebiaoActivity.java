package com.company.qcy.ui.activity.tuangou;

import android.content.Intent;
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
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.tuangou.TuangouRecyclerviewAdapter;
import com.company.qcy.bean.tuangou.TuangouBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

public class TuangouliebiaoActivity extends AppCompatActivity implements View.OnClickListener {

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
        mToolbarBack.setOnClickListener(this);
        mToolbarTitle.setText("七彩云团购惠");
    }


    private boolean isReflash;
    private int pageNo;

    private void addData() {
        pageNo++;
        OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.GROUPBUYLIST)
                .tag(this)

                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("pageNo", pageNo)
                .params("pageSize", 20)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        try {
                            if (response.code() == 200) {

                                JSONObject jsonObject = JSONObject.parseObject(response.body());

                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                    JSONArray data = jsonObject.getJSONArray("data");
                                    LogUtils.v("GROUPBUYLIST", data);

                                    if (ObjectUtils.isEmpty(data)) {
                                        adapter.loadMoreEnd();
                                        return;
                                    }
                                    List<TuangouBean> qiugouBeans = JSONObject.parseArray(data.toJSONString(), TuangouBean.class);
                                    if (isReflash) {
                                        datas.clear();
                                        adapter.addData(qiugouBeans);
                                        isReflash = false;
                                        refreshLayout.setRefreshing(false);
                                        adapter.loadMoreComplete();
                                        return;
                                    }

                                    adapter.addData(qiugouBeans);
                                    adapter.loadMoreComplete();
                                    adapter.disableLoadMoreIfNotFullPage();
                                    return;

                                } else
                                    SignAndTokenUtil.checkSignAndToken(TuangouliebiaoActivity.this, jsonObject);

                            } else {
                                refreshLayout.setRefreshing(false);
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
            case R.id.toolbar_back:
                finish();
                break;
        }
    }
}
