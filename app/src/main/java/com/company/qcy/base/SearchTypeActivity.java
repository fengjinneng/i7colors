package com.company.qcy.base;

import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.chanpindating.ChanpindatingRecyclerViewAdapter;
import com.company.qcy.adapter.kaifangshangcheng.KaifangshangchengRecyclerviewAdapter;
import com.company.qcy.adapter.qiugou.QiugoudatingRecyclerviewAdapter;
import com.company.qcy.bean.ChanpindatingBean;
import com.company.qcy.bean.kaifangshangcheng.DianpuliebiaoBean;
import com.company.qcy.bean.kaifangshangcheng.ProductBean;
import com.company.qcy.bean.qiugou.QiugouBean;
import com.company.qcy.ui.activity.chanpindating.ChanpindatingActivity;
import com.company.qcy.ui.activity.chanpindating.ChanpinxiangqingActivity;
import com.company.qcy.ui.activity.kaifangshangcheng.KFSCXiangqingActivity;
import com.company.qcy.ui.activity.kaifangshangcheng.KaifangshangchengActivity;
import com.company.qcy.ui.activity.qiugoudating.QiugoudatingActivity;
import com.company.qcy.ui.activity.qiugoudating.QiugouxiangqingActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

//分类搜索页面
public class SearchTypeActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 搜索您想要的商品
     */
    private EditText mActivitySearchTypeSearch;
    private ImageView mActivitySearchTypeBack;
    private RecyclerView recyclerview;
    private QiugoudatingRecyclerviewAdapter qiugouAdapter;
    private ChanpindatingRecyclerViewAdapter chanpinAdapter;
    private KaifangshangchengRecyclerviewAdapter marketAdapter;
    private int isFrom; //1为求购，2为产品，3为开放商城
    private String keyword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_type);
        isFrom = getIntent().getIntExtra("isFrom", 0);
        keyword = getIntent().getStringExtra("keyword");
        initView();
    }

    private void initView() {
        mActivitySearchTypeSearch = (EditText) findViewById(R.id.activity_search_type_search);
        if (isFrom == 1) {
            mActivitySearchTypeSearch.setHint("搜索求购相关信息");
        } else if (isFrom == 2) {
            mActivitySearchTypeSearch.setHint("搜索产品相关信息");
        } else if (isFrom == 3) {
            mActivitySearchTypeSearch.setHint("搜索店铺相关信息");
        }
        mActivitySearchTypeBack = (ImageView) findViewById(R.id.activity_search_type_back);
        recyclerview = (RecyclerView) findViewById(R.id.activity_search_type_recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mActivitySearchTypeBack.setOnClickListener(this);
        recyclerview.setLayoutManager(manager);

        mActivitySearchTypeSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    pageNo = 0;
                    KeyboardUtils.hideSoftInput(SearchTypeActivity.this);
                    addData();
                }
                return false;
            }
        });

        if (StringUtils.isTrimEmpty(keyword)) {

            KeyboardUtils.showSoftInput(this);

        }else {
            KeyboardUtils.hideSoftInput(SearchTypeActivity.this);
            mActivitySearchTypeSearch.setText(keyword);
            mActivitySearchTypeSearch.setSelection(keyword.length());
            addData();
        }
    }

    private int pageNo;

    private HttpParams params;

    private void addData() {
        if (StringUtils.isTrimEmpty((mActivitySearchTypeSearch.getText().toString()))) {
            ToastUtils.showShort("搜索内容不能为空");
            return;
        }
        params = new HttpParams();
        params.put("sign", SPUtils.getInstance().getString("sign"));
        params.put("pageSize", 20);

        if (isFrom == 1) {
            params.put("token", SPUtils.getInstance().getString("token"));
            params.put("productName", mActivitySearchTypeSearch.getText().toString());
            List<QiugouBean> datas = new ArrayList<>();
            qiugouAdapter = new QiugoudatingRecyclerviewAdapter(R.layout.item_qiugoudating_recyclerview, datas);
            recyclerview.setAdapter(qiugouAdapter);
            qiugouAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    getQiugouData();
                }
            }, recyclerview);
            qiugouAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent = new Intent(SearchTypeActivity.this, QiugouxiangqingActivity.class);
                    QiugouBean item = (QiugouBean) adapter.getItem(position);
                    intent.putExtra("enquiryId", item.getId());
                    intent.putExtra("isCharger", item.getIsCharger());
                    intent.putExtra("status", item.getStatus());
                    ActivityUtils.startActivity(intent);
                }
            });
            getQiugouData();
        } else if (isFrom == 2) {
            params.put("orderCond", "{\"is_display_price\":\"desc\",\"price\":\"\"}");
            params.put("aliasName", mActivitySearchTypeSearch.getText().toString());
            List<ProductBean> datas = new ArrayList<>();
            chanpinAdapter = new ChanpindatingRecyclerViewAdapter(R.layout.item_chanpindating_recyclerview, datas);
            recyclerview.setAdapter(chanpinAdapter);
            chanpinAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    getChanpinData();
                }
            }, recyclerview);
            getChanpinData();
            chanpinAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent = new Intent(SearchTypeActivity.this, ChanpinxiangqingActivity.class);

                    ProductBean productBean = (ProductBean) adapter.getData().get(position);

                    intent.putExtra("id", productBean.getId());
                    ActivityUtils.startActivity(intent);
                }
            });

        } else if (isFrom == 3) {
            params.put("name", mActivitySearchTypeSearch.getText().toString());
            List<DianpuliebiaoBean> datas = new ArrayList<>();
            marketAdapter = new KaifangshangchengRecyclerviewAdapter(R.layout.item_kaifangshangcheng_recyclerview, datas);
            recyclerview.setAdapter(marketAdapter);
            marketAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    getMarketData();
                }
            }, recyclerview);
            getMarketData();
            marketAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    DianpuliebiaoBean dianpuliebiaoBean = (DianpuliebiaoBean) adapter.getData().get(position);
                    Intent intent = new Intent(SearchTypeActivity.this, KFSCXiangqingActivity.class);
                    intent.putExtra("id", dianpuliebiaoBean.getId());
                    ActivityUtils.startActivity(intent);
                }
            });
        }

    }

    private void getMarketData() {
        pageNo++;
        params.put("pageNo", pageNo);
        OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.DIANPULIEBIAO)
                .tag(this)
                .params(params)
                .execute(new DialogStringCallback(this) {
                    @Override
                    public void onSuccess(Response<String> response) {

                        try {
                            if (response.code() == 200) {

                                JSONObject jsonObject = JSONObject.parseObject(response.body());

                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                    JSONArray data = jsonObject.getJSONArray("data");
                                    LogUtils.v("DIANPULIEBIAO", data);
                                    List<DianpuliebiaoBean> dianpuliebiaoBeans = JSONObject.parseArray(data.toJSONString(), DianpuliebiaoBean.class);

                                    if (ObjectUtils.isEmpty(dianpuliebiaoBeans)) {
                                        marketAdapter.loadMoreEnd();
                                        return;
                                    }
                                    marketAdapter.addData(dianpuliebiaoBeans);
                                    marketAdapter.loadMoreComplete();
                                    marketAdapter.disableLoadMoreIfNotFullPage();
                                    return;

                                } else
                                    SignAndTokenUtil.checkSignAndToken(SearchTypeActivity.this, jsonObject);

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

    private void getChanpinData() {
        pageNo++;
        params.put("pageNo", pageNo);
        OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.GETCHANPINLIEBIAO)
                .tag(this)
                .params(params)
                .execute(new DialogStringCallback(this) {
                    @Override
                    public void onSuccess(Response<String> response) {

                        try {
                            if (response.code() == 200) {

                                JSONObject jsonObject = JSONObject.parseObject(response.body());

                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                    JSONArray data = jsonObject.getJSONArray("data");
                                    LogUtils.v("GETCHANPINLIEBIAO", data);
                                    List<ProductBean> productBeans = JSONObject.parseArray(data.toJSONString(), ProductBean.class);

                                    if (ObjectUtils.isEmpty(productBeans)) {
                                        chanpinAdapter.loadMoreEnd();
                                        return;
                                    }
                                    chanpinAdapter.addData(productBeans);
                                    chanpinAdapter.loadMoreComplete();
                                    chanpinAdapter.disableLoadMoreIfNotFullPage();
                                    return;

                                } else
                                    SignAndTokenUtil.checkSignAndToken(SearchTypeActivity.this, jsonObject);

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

    private void getQiugouData() {
        pageNo++;
        params.put("pageNo", pageNo);
        OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.SEARCHENQUIRY)
                .tag(this)
                .params(params)
                .execute(new DialogStringCallback(this) {
                    @Override
                    public void onSuccess(Response<String> response) {

                        try {
                            if (response.code() == 200) {

                                JSONObject jsonObject = JSONObject.parseObject(response.body());

                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                    JSONArray data = jsonObject.getJSONArray("data");
                                    LogUtils.v("QIUGOULIEBIAO", data);
                                    List<QiugouBean> qiugouBeans = JSONObject.parseArray(data.toJSONString(), QiugouBean.class);

                                    if (ObjectUtils.isEmpty(qiugouBeans)) {
                                        qiugouAdapter.loadMoreEnd();
                                        return;
                                    }
                                    qiugouAdapter.addData(qiugouBeans);
                                    qiugouAdapter.loadMoreComplete();
                                    qiugouAdapter.disableLoadMoreIfNotFullPage();
                                    return;

                                } else
                                    SignAndTokenUtil.checkSignAndToken(SearchTypeActivity.this, jsonObject);

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
            case R.id.activity_search_type_back:

                finish();

                break;
        }
    }
}
