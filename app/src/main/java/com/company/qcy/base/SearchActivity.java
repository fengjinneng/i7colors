package com.company.qcy.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ListUtil;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.vlayout.ChanpinLayoutAdapter;
import com.company.qcy.adapter.vlayout.MarketLayoutAdapter;
import com.company.qcy.adapter.vlayout.QiugouLayoutAdapter;
import com.company.qcy.adapter.vlayout.SingleTitleLayoutAdapter;
import com.company.qcy.bean.kaifangshangcheng.DianpuliebiaoBean;
import com.company.qcy.bean.kaifangshangcheng.ProductBean;
import com.company.qcy.bean.qiugou.QiugouBean;
import com.company.qcy.ui.activity.chanpindating.ChanpinxiangqingActivity;
import com.company.qcy.ui.activity.kaifangshangcheng.KFSCXiangqingActivity;
import com.company.qcy.ui.activity.qiugoudating.QiugouxiangqingActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 搜索您想要的商品
     */
    private EditText mHomepageSearch;
    private ImageView back;
    private ImageView mHomepageSearchBack;
    private RecyclerView recyclerview;
    private DelegateAdapter delegateAdapter;
    private VirtualLayoutManager virtualLayoutManager;
    private ConstraintLayout mActivitySearchHistoryLayout;
    /**
     * 删除
     */
    private TextView mActivitySearchHistoryDelete;
    private ImageView mHomepageSearchKeywordDelete;
    private TagFlowLayout mActivitySearchTagFlowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
    }

    private void initView() {
        mActivitySearchHistoryLayout = (ConstraintLayout) findViewById(R.id.activity_search_history_layout);
        mActivitySearchHistoryDelete = (TextView) findViewById(R.id.activity_search_history_delete);
        mActivitySearchHistoryDelete.setOnClickListener(this);
        mHomepageSearchKeywordDelete = (ImageView) findViewById(R.id.homepage_search_keyword_delete);
        mHomepageSearchKeywordDelete.setOnClickListener(this);
        mActivitySearchTagFlowLayout = (TagFlowLayout) findViewById(R.id.activity_search_tagFlowLayout);

        mHomepageSearch = (EditText) findViewById(R.id.homepage_search);
        back = (ImageView) findViewById(R.id.homepage_search_back);
        mHomepageSearch.setOnClickListener(this);
        mHomepageSearchBack = (ImageView) findViewById(R.id.homepage_search_back);
        mHomepageSearchBack.setOnClickListener(this);
        mHomepageSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchData();
                }
                return false;
            }
        });

        mHomepageSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    recyclerview.setVisibility(View.GONE);
                    if (searchHistoryList.size() > 0) {
                        mActivitySearchHistoryLayout.setVisibility(View.VISIBLE);
                    }
                    mHomepageSearchKeywordDelete.setVisibility(View.GONE);
                    initSearchHistory();
                } else {
                    mActivitySearchHistoryLayout.setVisibility(View.GONE);
                    mHomepageSearchKeywordDelete.setVisibility(View.VISIBLE);
                }
            }
        });

        recyclerview = (RecyclerView) findViewById(R.id.homepage_search_recyclerview);
        virtualLayoutManager = new VirtualLayoutManager(this);
        recyclerview.setLayoutManager(virtualLayoutManager);

        delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        recyclerview.setAdapter(delegateAdapter);


        mHomepageSearchKeywordDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHomepageSearch.setText("");
            }
        });


        mActivitySearchTagFlowLayout.setAdapter( tagAdapter = new TagAdapter<String>(searchHistoryList) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.item_search_history,
                        mActivitySearchTagFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });


        mActivitySearchTagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {

                mHomepageSearch.setText(searchHistoryList.get(position));
                mHomepageSearch.setSelection(mHomepageSearch.getText().length());
                searchData();
                return false;
            }
        });
        initSearchHistory();
    }

    private TagAdapter <String> tagAdapter;
    //搜索的历史记录
    private ArrayList<String> searchHistoryList = new ArrayList<>();

    private void searchData() {

        if (StringUtils.isTrimEmpty((mHomepageSearch.getText().toString()))) {
            ToastUtils.showShort("搜索内容不能为空");
            return;
        }

        qiugouDatas.clear();
        marketDatas.clear();
        productDatas.clear();
        delegateAdapter.clear();
        delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        recyclerview.setAdapter(delegateAdapter);
        KeyboardUtils.hideSoftInput(SearchActivity.this);
        addData();

        for (int i = 0; i < searchHistoryList.size(); i++) {
            if (StringUtils.equals(mHomepageSearch.getText().toString(), searchHistoryList.get(i))) {
                searchHistoryList.remove(i);
            }
        }
        searchHistoryList.add(0, mHomepageSearch.getText().toString());
        SPUtils.getInstance("searchHistory").put("all", ListUtil.listToString(searchHistoryList));
    }

    private void initSearchHistory() {

        String str = "";
        str = SPUtils.getInstance("searchHistory").getString("all");
        if (StringUtils.isEmpty(str)) {
            return;
        }
        mActivitySearchHistoryLayout.setVisibility(View.VISIBLE);
        List<String> stringList = ListUtil.stringToList(str);
        searchHistoryList.clear();

        searchHistoryList.addAll(stringList);

        tagAdapter.notifyDataChanged();

    }

    private List<QiugouBean> qiugouDatas = new ArrayList<>();
    private List<DianpuliebiaoBean> marketDatas = new ArrayList<>();
    private List<ProductBean> productDatas = new ArrayList<>();


    private void addData() {

        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.INDEXSEARCH)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("pageNo", 1)
                .params("pageSize", 3)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("keyWord", mHomepageSearch.getText().toString());

        DialogStringCallback stringCallback = new DialogStringCallback(this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.e("INDEXSEARCH", response.body());

                try {
                    if (response.code() == 200) {
                        recyclerview.setVisibility(View.VISIBLE);
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            JSONArray enquiryList = data.getJSONArray("enquiryList");
                            JSONArray marketList = data.getJSONArray("marketList");
                            JSONArray productList = data.getJSONArray("productList");
                            if (ObjectUtils.isEmpty(enquiryList) && ObjectUtils.isEmpty(marketList) && ObjectUtils.isEmpty(productList)) {
                                ToastUtils.showShort(msg);
                                return;
                            }
                            if (!ObjectUtils.isEmpty(enquiryList)) {
                                boolean hideMore = true;
                                if (enquiryList.size() == 3) {
                                    hideMore = false;
                                }
                                SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
                                SingleTitleLayoutAdapter singleTitleLayoutAdapter = new SingleTitleLayoutAdapter(SearchActivity.this, singleLayoutHelper, 1, "求购大厅", hideMore);
                                delegateAdapter.addAdapter(singleTitleLayoutAdapter);
                                singleTitleLayoutAdapter.setOnMoreClickListner(new SingleTitleLayoutAdapter.OnMoreClickListner() {
                                    @Override
                                    public void onMoreClick() {
                                        Intent intent = new Intent(SearchActivity.this, SearchTypeActivity.class);
                                        intent.putExtra("isFrom", 1);
                                        intent.putExtra("keyword", mHomepageSearch.getText().toString());
                                        ActivityUtils.startActivity(intent);
                                    }
                                });

                                qiugouDatas.addAll(JSONObject.parseArray(enquiryList.toJSONString(), QiugouBean.class));
                                LinearLayoutHelper helper = new LinearLayoutHelper();
                                QiugouLayoutAdapter qiugouLayoutAdapter = new QiugouLayoutAdapter(SearchActivity.this, helper, qiugouDatas);
                                delegateAdapter.addAdapter(qiugouLayoutAdapter);
                                qiugouLayoutAdapter.setOnQiugouItemClickListener(new QiugouLayoutAdapter.OnQiugouItemClickListener() {
                                    @Override
                                    public void onQiugouItemClick(QiugouBean item) {
                                        Intent intent = new Intent(SearchActivity.this, QiugouxiangqingActivity.class);
                                        intent.putExtra("enquiryId", item.getId());
                                        intent.putExtra("isCharger", item.getIsCharger());
                                        intent.putExtra("status", item.getStatus());
                                        ActivityUtils.startActivity(intent);
                                    }
                                });

                            }
                            if (!ObjectUtils.isEmpty(marketList)) {
                                boolean hideMore = true;
                                if (marketList.size() == 3) {
                                    hideMore = false;
                                }
                                SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
                                SingleTitleLayoutAdapter singleTitleLayoutAdapter = new SingleTitleLayoutAdapter(SearchActivity.this, singleLayoutHelper, 1, "开放商城", hideMore);
                                delegateAdapter.addAdapter(singleTitleLayoutAdapter);

                                singleTitleLayoutAdapter.setOnMoreClickListner(new SingleTitleLayoutAdapter.OnMoreClickListner() {
                                    @Override
                                    public void onMoreClick() {
                                        Intent intent = new Intent(SearchActivity.this, SearchTypeActivity.class);
                                        intent.putExtra("isFrom", 3);
                                        intent.putExtra("keyword", mHomepageSearch.getText().toString());
                                        ActivityUtils.startActivity(intent);
                                    }
                                });

                                marketDatas.addAll(JSONObject.parseArray(marketList.toJSONString(), DianpuliebiaoBean.class));
                                LinearLayoutHelper helper = new LinearLayoutHelper();
                                MarketLayoutAdapter marketLayoutAdapter = new MarketLayoutAdapter(SearchActivity.this, helper, marketDatas);
                                delegateAdapter.addAdapter(marketLayoutAdapter);
                                marketLayoutAdapter.setOnMarketItemClickListener(new MarketLayoutAdapter.OnMarketItemClickListener() {
                                    @Override
                                    public void onMarketItemClick(DianpuliebiaoBean bean) {
                                        Intent intent = new Intent(SearchActivity.this, KFSCXiangqingActivity.class);
                                        intent.putExtra("id", bean.getId());
                                        ActivityUtils.startActivity(intent);
                                    }
                                });
                            }
                            if (!ObjectUtils.isEmpty(productList)) {
                                boolean hideMore = true;
                                if (productList.size() == 3) {
                                    hideMore = false;
                                }
                                SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
                                SingleTitleLayoutAdapter singleTitleLayoutAdapter = new SingleTitleLayoutAdapter(SearchActivity.this, singleLayoutHelper, 1, "产品大厅", hideMore);
                                delegateAdapter.addAdapter(singleTitleLayoutAdapter);
                                singleTitleLayoutAdapter.setOnMoreClickListner(new SingleTitleLayoutAdapter.OnMoreClickListner() {
                                    @Override
                                    public void onMoreClick() {
                                        Intent intent = new Intent(SearchActivity.this, SearchTypeActivity.class);
                                        intent.putExtra("isFrom", 2);
                                        intent.putExtra("keyword", mHomepageSearch.getText().toString());
                                        ActivityUtils.startActivity(intent);
                                    }
                                });
                                productDatas.addAll(JSONObject.parseArray(productList.toJSONString(), ProductBean.class));
                                LinearLayoutHelper helper = new LinearLayoutHelper();
                                ChanpinLayoutAdapter chanpinLayoutAdapter = new ChanpinLayoutAdapter(SearchActivity.this, helper, productDatas);
                                delegateAdapter.addAdapter(chanpinLayoutAdapter);
                                chanpinLayoutAdapter.setOnChanpinItemClickListener(new ChanpinLayoutAdapter.OnChanpinItemClickListener() {
                                    @Override
                                    public void onChanpinItemClick(ProductBean bean) {
                                        Intent intent = new Intent(SearchActivity.this, ChanpinxiangqingActivity.class);
                                        intent.putExtra("id", bean.getId());
                                        ActivityUtils.startActivity(intent);
                                    }
                                });
                            }
                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(SearchActivity.this, request, this);
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
            case R.id.homepage_search_back:
                KeyboardUtils.hideSoftInput(this);
                finish();
                break;
            case R.id.activity_search_history_delete:
                mActivitySearchHistoryLayout.setVisibility(View.GONE);
//                if (!ObjectUtils.isEmpty(mActivitySearchTagContainerLayout)) {
//                    mActivitySearchTagContainerLayout.removeAllTags();
//                }
                searchHistoryList.clear();
                SPUtils.getInstance("searchHistory").put("all", "");
                break;
            case R.id.homepage_search_keyword_delete:
                break;
        }
    }
}
