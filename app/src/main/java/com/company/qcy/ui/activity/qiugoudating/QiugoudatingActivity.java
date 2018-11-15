package com.company.qcy.ui.activity.qiugoudating;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.qiugou.QiugoudatingRecyclerviewAdapter;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.base.SearchTypeActivity;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.bean.qiugou.QiugouBean;
import com.company.qcy.ui.activity.chanpindating.ChanpindatingActivity;
import com.company.qcy.ui.activity.user.LoginActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

public class QiugoudatingActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 搜索您想要的商品
     */
    private TextView mQiugoudatingSearch;
    private RecyclerView mQiugoudatingRecyclerview;
    private QiugoudatingRecyclerviewAdapter adapter;
    /**
     * 发布求购
     */
    private Button mQiugoudatingFabuqiugou;
    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qiugoudating);
        initView();
    }


    private List<QiugouBean> datas;
    private SwipeRefreshLayout refreshLayout;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;

    private void initView() {
        mQiugoudatingSearch = (TextView) findViewById(R.id.qiugoudating_search);
        mQiugoudatingRecyclerview = (RecyclerView) findViewById(R.id.qiugoudating_recyclerview);

        datas = new ArrayList<>();
        refreshLayout = findViewById(R.id.activity_qiugoudating_swipe);
        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mQiugoudatingRecyclerview.setLayoutManager(layoutManager);

        //创建适配器
        adapter = new QiugoudatingRecyclerviewAdapter(R.layout.item_qiugoudating_recyclerview, datas);
        //给RecyclerView设置适配器
        mQiugoudatingRecyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(QiugoudatingActivity.this, FabuqiugouActivity.class));
            }
        });

//        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
//        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_fengexian));
//        mQiugoudatingRecyclerview.addItemDecoration(itemDecoration);

        mQiugoudatingFabuqiugou = (Button) findViewById(R.id.qiugoudating_fabuqiugou);
        mQiugoudatingFabuqiugou.setOnClickListener(this);

        mQiugoudatingSearch.setOnClickListener(this);
//        adapter.setPreLoadNumber(3);
//        adapter.setLoadMoreView(new CustomLoadMoreView());
//        adapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.empty_layout, null));

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                addData();

            }
        }, mQiugoudatingRecyclerview);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(QiugoudatingActivity.this, QiugouxiangqingActivity.class);
                QiugouBean item = (QiugouBean) adapter.getItem(position);
                intent.putExtra("enquiryId", item.getId());
                intent.putExtra("isCharger", item.getIsCharger());
                intent.putExtra("status", item.getStatus());
                ActivityUtils.startActivity(intent);
            }
        });

        refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉业务
                isReflash = true;
                page = 0;
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

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.item_qiugoudating_yijianhujiao:
                        if (ActivityCompat.checkSelfPermission(QiugoudatingActivity.this,
                                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        PhoneUtils.call(getResources().getString(R.string.PHONE));

                        break;
                }
            }
        });
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mToolbarTitle.setText("求购大厅");
    }


    @Override
    public void onReciveMessage(MessageBean msg) {
        super.onReciveMessage(msg);
        switch (msg.getCode()) {
            case MessageBean.Code.BAOJIACHENGGONG:
                refreshLayout.setRefreshing(true);
                refreshListener.onRefresh();
                break;
            case MessageBean.Code.GUANBIQIUGOU:
                refreshLayout.setRefreshing(true);
                refreshListener.onRefresh();
                break;
            case MessageBean.Code.FABUQIUGOUCHENGGONG:
                refreshLayout.setRefreshing(true);
                refreshListener.onRefresh();
                break;
            case MessageBean.Code.CAINABAOJIACHENGGONG:
                refreshLayout.setRefreshing(true);
                refreshListener.onRefresh();
                break;
        }
    }

    private int page = 0;
    private boolean isReflash;

    private void addData() {
        page++;
        OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.QIUGOULIEBIAO)
                .tag(this)

                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("pageNo", page)
                .params("pageSize", 20)
                .params("token", SPUtils.getInstance().getString("token"))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        try {
                            if (response.code() == 200) {

                                JSONObject jsonObject = JSONObject.parseObject(response.body());

                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                    JSONArray data = jsonObject.getJSONArray("data");
                                    LogUtils.v("QIUGOULIEBIAO", data);
                                    List<QiugouBean> qiugouBeans = JSONObject.parseArray(data.toJSONString(), QiugouBean.class);
                                    if (isReflash) {
                                        datas.clear();
                                        adapter.addData(qiugouBeans);
                                        isReflash = false;
                                        refreshLayout.setRefreshing(false);
                                        adapter.loadMoreComplete();
                                        return;
                                    }
                                    if (ObjectUtils.isEmpty(qiugouBeans)) {
                                        adapter.loadMoreEnd();
                                        return;
                                    }
                                    adapter.addData(qiugouBeans);
                                    adapter.loadMoreComplete();
                                    adapter.disableLoadMoreIfNotFullPage();
                                    return;

                                } else
                                    SignAndTokenUtil.checkSignAndToken(QiugoudatingActivity.this, jsonObject);

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
            case R.id.qiugoudating_fabuqiugou:
                if (StringUtils.isEmpty(SPUtils.getInstance().getString("isLogin"))) {
                    ActivityUtils.startActivity(LoginActivity.class);

                } else ActivityUtils.startActivity(FabuqiugouActivity.class);

                break;
            case R.id.qiugoudating_search:

                Intent intent = new Intent(QiugoudatingActivity.this, SearchTypeActivity.class);
                intent.putExtra("isFrom", 1);
                intent.putExtra("keyword", "");
                ActivityUtils.startActivity(intent);

                break;
            case R.id.toolbar_back:
                finish();
                break;
        }
    }
}
