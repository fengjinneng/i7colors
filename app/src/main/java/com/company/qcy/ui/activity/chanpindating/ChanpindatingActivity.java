package com.company.qcy.ui.activity.chanpindating;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
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
import com.company.qcy.adapter.chanpindating.ChanpinerjiTypeAdapter;
import com.company.qcy.adapter.chanpindating.ChanpinshaixuanAdapter;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.base.SearchTypeActivity;
import com.company.qcy.bean.chanpin.ChanpinTypeBean;
import com.company.qcy.bean.chanpin.ProductBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import java.util.ArrayList;
import java.util.List;

public class ChanpindatingActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 搜索您想要的商品
     */
    private ImageView mChanpindatingSearch;
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
    private ConstraintLayout mChanpindatingAllproductLayout;
    /**
     * 筛选
     */
    private TextView mChanpindatingShaixuanText;
    private ImageView mChanpindatingShaixuanImg;
    private ConstraintLayout mChanpindatingShaixuan;
    /**
     * 全部
     */
    private TextView mChanpindatingAllproduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chanpindating);
        initView();

    }

    private void initView() {
        mChanpindatingSearch = (ImageView) findViewById(R.id.chanpindating_search);
        mChanpindatingRecyclerview = (RecyclerView) findViewById(R.id.chanpindating_recyclerview);
        mChanpindatingAllproductLayout = (ConstraintLayout) findViewById(R.id.chanpindating_allproduct_layout);
        mChanpindatingAllproductLayout.setOnClickListener(this);
        mChanpindatingShaixuanText = (TextView) findViewById(R.id.chanpindating_shaixuan_text);
        mChanpindatingShaixuanImg = (ImageView) findViewById(R.id.chanpindating_shaixuan_img);
        mChanpindatingShaixuan = (ConstraintLayout) findViewById(R.id.chanpindating_shaixuan);
        mChanpindatingShaixuan.setOnClickListener(this);
        mChanpindatingAllproduct = (TextView) findViewById(R.id.chanpindating_allproduct);

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
                addData(checkedId);
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
                refreshLayout.setRefreshing(true);
                isReflash = true;
                pageNo = 0;
                addData(checkedId);
            }
        };
        refreshLayout.setOnRefreshListener(refreshListener);
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
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
        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.empty_layout,null));

    }

    private boolean isReflash;
    private int pageNo;
    private String order = "";//

    private void addData(long checkedId) {
        pageNo++;

        HttpParams params = new HttpParams();

        params.put("sign", SPUtils.getInstance().getString("sign"));
        params.put("pageNo", pageNo);
        params.put("pageSize", 20);
        params.put("orderCond", "{\"is_display_price\":\"desc\",\"price\":\"" + order + "\"}");
        params.put("aliasName", "");
        if (checkedId == 0) {
            params.put("classCondJson", "");

        } else {
            params.put("classCondJson", "[" + checkedId + "]");

        }

        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.GETCHANPINLIEBIAO)
                .tag(this)
                .params(params);

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
                                //第一次进来就是空的
                                if (pageNo == 1) {
                                    datas.clear();
                                    adapter.notifyDataSetChanged();
                                }
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
                            SignAndTokenUtil.getSign(ChanpindatingActivity.this, request, this);
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

    private PopupWindow popupWindow;

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
            case R.id.chanpindating_allproduct_layout:
                if(checkedId==0){
                    return;
                }
                getAllProduct();

                break;
            case R.id.chanpindating_shaixuan:

                showPopwindow();

                break;
        }
    }

    private void getAllProduct() {
        checkedId = 0;

        if (!ObjectUtils.isEmpty(checkedLayout)) {
            checkedLayout.setVisibility(View.GONE);
        }

        if (!ObjectUtils.isEmpty(allErjiType)) {
            for (int i = 0; i < allErjiType.size(); i++) {
                allErjiType.get(i).setChecked(false);
            }
            for (int i = 0; i < allErjiAdapter.size(); i++) {
                allErjiAdapter.get(i).notifyDataSetChanged();
            }
        }
        refreshListener.onRefresh();

        mChanpindatingAllproduct.setTextColor(getResources().getColor(R.color.chunhongse));
        mChanpindatingShaixuanText.setText("筛选");
        mChanpindatingShaixuanText.setTextColor(getResources().getColor(R.color.putongwenben));

    }

    private ConstraintLayout checkedLayout;
    private TextView checkedText;
    private TextView yijifenlei;
    private long checkedId;
    private int firstIn;

    private void showPopwindow() {

        if (loadTypeDataComplete) {
            //设置背景半透明
            backgroundAlpha(0.5f);
            popupWindow.showAtLocation(LayoutInflater.from(this).inflate(R.layout.activity_chanpindating, null), Gravity.RIGHT, 0, 100);
            return;
        }

        View popupWindowView = getLayoutInflater().inflate(R.layout.popwindow_chanpinshaixuan, null);

        RecyclerView popRecyclerview = (RecyclerView) popupWindowView.findViewById(R.id.popwindow_chanpinshaixuan_recyclerview);

        List<ChanpinTypeBean> datas = new ArrayList();
        ChanpinshaixuanAdapter shaixuanAdapter = new ChanpinshaixuanAdapter(R.layout.pengyouquan_huifu_layout, datas);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        popRecyclerview.setLayoutManager(linearLayoutManager);
        popRecyclerview.setAdapter(shaixuanAdapter);

        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) popupWindowView.findViewById(R.id.popwindow_chanpinshaixuan_swipeRefreshLayout);

        SwipeRefreshLayout.OnRefreshListener onRefreshListener =  new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(firstIn==0){
                    getTypeData(shaixuanAdapter,swipeRefreshLayout);
                    firstIn = 1;
                }else {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        };
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                onRefreshListener.onRefresh();
            }
        });

        checkedLayout = (ConstraintLayout) popupWindowView.findViewById(R.id.popwindow_chanpinshaixuan_checked_layout);
        checkedText = (TextView) popupWindowView.findViewById(R.id.popwindow_chanpinshaixuan_checked_yixuan);
        yijifenlei = (TextView) popupWindowView.findViewById(R.id.popwindow_chanpinshaixuan_checked_yijifenlei);
        TextView popwindowReset = (TextView) popupWindowView.findViewById(R.id.popwindow_chanpinshaixuan_reset);
        TextView popwindowSubmit = (TextView) popupWindowView.findViewById(R.id.popwindow_chanpinshaixuan_submit);

        popwindowReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkedId==0){
                    return;
                }
                getAllProduct();
            }
        });

        popwindowSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupWindow.dismiss();

            }
        });

        popupWindow = new PopupWindow(popupWindowView, (int) (ScreenUtils.getScreenWidth() * 0.8), LayoutParams.MATCH_PARENT, true);

        ColorDrawable dw = new ColorDrawable(0xffffffff);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.setAnimationStyle(R.style.social_pop_anim);

        popupWindow.showAtLocation(LayoutInflater.from(this).inflate(R.layout.activity_chanpindating, null), Gravity.RIGHT, 0, 100);

        //设置背景半透明
        backgroundAlpha(0.5f);


        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);

            }
        });
    }

    //加载数据是否成功，控制popwindow的显示
    private boolean loadTypeDataComplete;

    private void getTypeData(ChanpinshaixuanAdapter chanpinshaixuanAdapter,SwipeRefreshLayout swipeRefreshLayout) {

        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.GETALLPRODUCTTYPE)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("eid", "");


        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.e("GETALLPRODUCTTYPE", response.body());
                swipeRefreshLayout.setRefreshing(false);
                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            loadTypeDataComplete = true;
                            JSONArray array = jsonObject.getJSONArray("data");
                            if (ObjectUtils.isEmpty(array)) {
                                return;
                            }
                            List<ChanpinTypeBean> chanpinTypeBeans = JSONObject.parseArray(array.toJSONString(), ChanpinTypeBean.class);

                            for (int i = 0; i < chanpinTypeBeans.size(); i++) {
                                List<ChanpinTypeBean.PropListBean> propList = chanpinTypeBeans.get(i).getPropList();

                                ChanpinTypeBean.PropListBean bean = new ChanpinTypeBean.PropListBean();
                                bean.setId(chanpinTypeBeans.get(i).getId());
                                bean.setValue("全部");
                                bean.setYijibiaoti(chanpinTypeBeans.get(i).getTypeText());
                                propList.add(0,bean);

                                for (int j = 0; j < propList.size(); j++) {
                                    propList.get(j).setYijibiaoti(chanpinTypeBeans.get(i).getTypeText());
                                }
                                getHeadView(chanpinshaixuanAdapter, propList, chanpinTypeBeans.get(i).getTypeText());
                            }
                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(ChanpindatingActivity.this, request, this);
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
                swipeRefreshLayout.setRefreshing(false);
                ToastUtils.showShort(getResources().getString(R.string.NETEXCEPTION));
            }
        };

        request.execute(stringCallback);
    }

    private List<ChanpinTypeBean.PropListBean> allErjiType = new ArrayList<>();
    private List<ChanpinerjiTypeAdapter> allErjiAdapter = new ArrayList<>();

    private void getHeadView(ChanpinshaixuanAdapter chanpinshaixuanAdapter, List propList, String s) {
        allErjiType.addAll(propList);
        View inflate = getLayoutInflater().inflate(R.layout.headview_chanpinshaixuan, null);
        TextView textView = inflate.findViewById(R.id.item_chanpinshaixuan_yiji);
        RecyclerView recyclerView = inflate.findViewById(R.id.item_chanpinshaixuan_recyclerview);

        ChanpinerjiTypeAdapter chanpinerjiTypeAdapter = new ChanpinerjiTypeAdapter(R.layout.item_chanpinfenlei_erji, propList);
        allErjiAdapter.add(chanpinerjiTypeAdapter);
//        View view = LayoutInflater.from(getActivity()).inflate(R.layout.popwindow_huati, null);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ChanpindatingActivity.this, 3);

        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(chanpinerjiTypeAdapter);

        chanpinerjiTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ChanpinTypeBean.PropListBean propListBean = (ChanpinTypeBean.PropListBean) adapter.getData().get(position);

                if(checkedId==propListBean.getId()){
                    return;
                }

                for (int i = 0; i < allErjiType.size(); i++) {
                    allErjiType.get(i).setChecked(false);
                }
                propListBean.setChecked(true);
                for (int i = 0; i < allErjiAdapter.size(); i++) {
                    allErjiAdapter.get(i).notifyDataSetChanged();
                }
                if (!ObjectUtils.isEmpty(checkedText) && !ObjectUtils.isEmpty(checkedLayout)) {
                    checkedLayout.setVisibility(View.VISIBLE);
                    checkedText.setText(propListBean.getValue());
                    yijifenlei.setText(propListBean.getYijibiaoti());
                }
                checkedId = propListBean.getId();
                refreshListener.onRefresh();
                mChanpindatingShaixuanText.setText(propListBean.getYijibiaoti() + "·" + propListBean.getValue());
                mChanpindatingShaixuanText.setTextColor(getResources().getColor(R.color.chunhongse));
                mChanpindatingAllproduct.setTextColor(getResources().getColor(R.color.putongwenben));
            }
        });

        textView.setText(s + ":");
        chanpinshaixuanAdapter.addHeaderView(inflate);

    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }
}
