package com.company.qcy.ui.activity.chanpindating;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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
import com.company.qcy.Utils.MyLoadMoreView;
import com.company.qcy.Utils.PermisionUtil;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.chanpindating.ChanpindatingRecyclerViewAdapter;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.base.SearchTypeActivity;
import com.company.qcy.bean.kaifangshangcheng.ProductBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

import static com.company.qcy.I7colorsApplication.getContext;

public class ChanpindatingActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 搜索您想要的商品
     */
    private TextView mChanpindatingSearch;
    private RecyclerView mChanpindatingRecyclerview;
    private ChanpindatingRecyclerViewAdapter adapter;

    private List<ProductBean> datas;
    private SwipeRefreshLayout refreshLayout;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;
    private ConstraintLayout mChanpindatingJiagepaixu;
    /**
     * 价格从\n低到高
     */
//    private TextView mChanpindatingJiagepaixuText;
//    private ImageView mChanpindatingJiagepaixuImg;
    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chanpindating);
        initView();
        LogUtils.e("asdasdsadasdasdsada",JPushInterface.getRegistrationID(getContext()));
    }

    private void initView() {
        mChanpindatingSearch = (TextView) findViewById(R.id.chanpindating_search);
        mChanpindatingRecyclerview = (RecyclerView) findViewById(R.id.chanpindating_recyclerview);

        datas = new ArrayList<>();

        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mChanpindatingRecyclerview.setLayoutManager(layoutManager);

        //创建适配器
        adapter = new ChanpindatingRecyclerViewAdapter(R.layout.item_chanpindating_recyclerview, datas);
        //给RecyclerView设置适配器
        mChanpindatingRecyclerview.setAdapter(adapter);

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                addData();
            }
        }, mChanpindatingRecyclerview);

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.item_chanpindating_yijianhujiao:
                        ProductBean productBean = (ProductBean) adapter.getData().get(position);
                        if (ObjectUtils.isEmpty(productBean)) {
                            return;
                        }
                        if (StringUtils.isEmpty(productBean.getPhone())) {
                            ToastUtils.showShort("该企业没有留下电话号码哦！");
                            return;
                        }
                        PermisionUtil.callPhone(ChanpindatingActivity.this, productBean.getPhone());
                        break;
                }
            }
        });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Intent intent = new Intent(ChanpindatingActivity.this, ChanpinxiangqingActivity.class);
                ProductBean productBean = (ProductBean) adapter.getData().get(position);
                intent.putExtra("id", productBean.getId());
                ActivityUtils.startActivity(intent);

//             ARouter.getInstance().build("/test/test1").withString("id",productBean.getId()).navigation();


            }
        });
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.chanpindating_SwipeRefreshLayout);


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
        refreshLayout.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_green_light, android.R.color.holo_blue_light);
//        mChanpindatingJiagepaixu = (ConstraintLayout) findViewById(R.id.chanpindating_jiagepaixu);
//        mChanpindatingJiagepaixu.setOnClickListener(this);
//        mChanpindatingJiagepaixuText = (TextView) findViewById(R.id.chanpindating_jiagepaixu_text);
//        mChanpindatingJiagepaixuImg = (ImageView) findViewById(R.id.chanpindating_jiagepaixu_img);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mToolbarTitle.setText("产品大厅");

        mChanpindatingSearch.setOnClickListener(this);

        adapter.setLoadMoreView(new MyLoadMoreView());

    }

    private boolean isReflash;
    private int pageNo;
    private String order = "";//

    private void addData() {
        pageNo++;
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.GETCHANPINLIEBIAO)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("pageNo", pageNo)
                .params("pageSize", 20)
                .params("orderCond", "{\"is_display_price\":\"desc\",\"price\":\"" + order + "\"}")
                .params("aliasName", "");

        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                refreshLayout.setRefreshing(false);
                LogUtils.v("GETCHANPINLIEBIAO", response.body());

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
                            List<ProductBean> productBeans = JSONObject.parseArray(data.toJSONString(), ProductBean.class);
                            if (ObjectUtils.isEmpty(productBeans)) {
                                adapter.loadMoreEnd();
                                return;
                            }
                            if (isReflash) {
                                datas.clear();
                                datas.addAll(productBeans);
                                adapter.setNewData(datas);
                                isReflash = false;
                                adapter.loadMoreComplete();
                                return;
                            }
                            datas.addAll(productBeans);
                            adapter.setNewData(datas);
                            adapter.loadMoreComplete();
                            adapter.disableLoadMoreIfNotFullPage();
                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(ChanpindatingActivity.this,request,this);
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
//            case R.id.chanpindating_jiagepaixu:
//                if (StringUtils.isEmpty(order)) {
//                    order = "asc";
//                }
//                //从小到大
//                if (StringUtils.equals("asc", order)) {
//                    mChanpindatingJiagepaixuText.setText("价格从\n低到高");
//                    mChanpindatingJiagepaixuImg.setImageDrawable(getResources().getDrawable(R.mipmap.congdidaogao));
//                    refreshLayout.setRefreshing(true);
//                    refreshListener.onRefresh();
//                    order = "desc";
//                }
//                //从大到小
//                else if (StringUtils.equals("desc", order)) {
//                    mChanpindatingJiagepaixuText.setText("价格从\n高到低");
//                    mChanpindatingJiagepaixuImg.setImageDrawable(getResources().getDrawable(R.mipmap.conggaodaodi));
//                    refreshLayout.setRefreshing(true);
//                    refreshListener.onRefresh();
//                    order = "asc";
//                }
//                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.chanpindating_search:
                Intent intent = new Intent(ChanpindatingActivity.this, SearchTypeActivity.class);
                intent.putExtra("isFrom", 2);
                intent.putExtra("keyword", "");
                ActivityUtils.startActivity(intent);
                break;
        }
    }
}
