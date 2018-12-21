package com.company.qcy.fragment.home;


import android.content.Context;
import android.content.Intent;
import android.icu.util.VersionInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.vlayout.BottomLayoutAdapter;
import com.company.qcy.adapter.vlayout.MarketLayoutAdapter;
import com.company.qcy.adapter.vlayout.QiugouLayoutAdapter;
import com.company.qcy.adapter.vlayout.SingleAdvLayoutAdapter;
import com.company.qcy.adapter.vlayout.SingleAdvLayoutAdapter2;
import com.company.qcy.adapter.vlayout.SingleTitleLayoutAdapter;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.base.BaseFragment;
import com.company.qcy.base.SearchActivity;
import com.company.qcy.bean.BannerBean;
import com.company.qcy.bean.UpdateBean;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.bean.kaifangshangcheng.DianpuliebiaoBean;
import com.company.qcy.bean.qiugou.QiugouBean;
import com.company.qcy.ui.activity.kaifangshangcheng.KFSCXiangqingActivity;
import com.company.qcy.ui.activity.kaifangshangcheng.KaifangshangchengActivity;
import com.company.qcy.ui.activity.qiugoudating.QiugoudatingActivity;
import com.company.qcy.ui.activity.qiugoudating.QiugouxiangqingActivity;
import com.company.qcy.ui.activity.user.LoginActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {


    private RecyclerView mRecyclerview;
    private SwipeRefreshLayout mSwipeRefreshlayout;
    private VirtualLayoutManager virtualLayoutManager;
    private Context context;
    private DelegateAdapter delegateAdapter;
    private View view;
    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    /**
     * 搜索产品/店铺/求购信息
     */
    private TextView mFragmentHomeSearch;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setBannerData();
        setAdvData();
        addTitle("热门求购");
        addQiugou();
        addTitle("推荐店铺");
        addMarket();
        addBottom();

    }

    private void addBottom() {
        SingleLayoutHelper helper = new SingleLayoutHelper();
        BottomLayoutAdapter adapter = new BottomLayoutAdapter(context, helper);
        delegateAdapter.addAdapter(adapter);
    }

    private MarketLayoutAdapter marketLayoutAdapter;
    private List<DianpuliebiaoBean> marketDatas = new ArrayList<>();


    private void addMarket() {
        LinearLayoutHelper helper = new LinearLayoutHelper();
        marketLayoutAdapter = new MarketLayoutAdapter(context, helper, marketDatas);
        delegateAdapter.addAdapter(marketLayoutAdapter);

        marketLayoutAdapter.setOnMarketItemClickListener(new MarketLayoutAdapter.OnMarketItemClickListener() {
            @Override
            public void onMarketItemClick(DianpuliebiaoBean bean) {
                Intent intent = new Intent(context, KFSCXiangqingActivity.class);
                intent.putExtra("id", bean.getId());
                ActivityUtils.startActivity(intent);
            }
        });
    }

    @Override
    public void onRec(MessageBean messageBean) {
        switch (messageBean.getCode()) {

        }
    }

    private void addData() {

        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.INDEXDATA)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("pageNo", 1)
                .params("pageSize", 8)
                .params("token", SPUtils.getInstance().getString("token"));

        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                mSwipeRefreshlayout.setRefreshing(false);

                try {
                    LogUtils.v("INDEXDATA", response.body());
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            if (ObjectUtils.isEmpty(data)) {
                                return;
                            }
                            String login_status = data.getString("login_status");
                            if (StringUtils.equals(getResources().getString(R.string.NO_LOGIN), login_status)) {
                                SPUtils.getInstance().clear();
                                ActivityUtils.startActivity(LoginActivity.class);
                            }
                            if (isRefresh) {
                                qiugouDatas.clear();
                                marketDatas.clear();
                            }
                            JSONArray enquiryList = data.getJSONArray("enquiryList");
                            JSONArray marketList = data.getJSONArray("marketList");
                            List<QiugouBean> qiugouBeans = JSONObject.parseArray(enquiryList.toJSONString(), QiugouBean.class);
                            List<DianpuliebiaoBean> marketsBeans = JSONObject.parseArray(marketList.toJSONString(), DianpuliebiaoBean.class);
                            qiugouDatas.addAll(qiugouBeans);
                            qiugouLayoutAdapter.notifyDataSetChanged();
                            marketDatas.addAll(marketsBeans);
                            marketLayoutAdapter.notifyDataSetChanged();

                            JSONObject androidVersion = data.getJSONObject("androidVersion");
                            UpdateBean updateBean = androidVersion.toJavaObject(UpdateBean.class);
                            if (AppUtils.getAppVersionCode() < updateBean.getVersionNum()) {
                                EventBus.getDefault().post(new MessageBean(MessageBean.Code.NEEDUPDATEAPP, updateBean));
                            }
                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(getActivity(), request, this);
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
                mSwipeRefreshlayout.setRefreshing(false);
                ToastUtils.showShort(getResources().getString(R.string.NETEXCEPTION));
            }
        };
        request.execute(stringCallback);
    }

    private List<String> advDatas = new ArrayList<>();
    SingleAdvLayoutAdapter2 advAdapter;

    private void setAdvData() {

        SingleLayoutHelper helper = new SingleLayoutHelper();
        advAdapter = new SingleAdvLayoutAdapter2(context, helper, 1, advDatas);
        delegateAdapter.addAdapter(advAdapter);
    }

    private void addAdvData() {

        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.INDEXBANNER)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("plate_code", "APP_Group_Buy");

        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {

                try {
                    LogUtils.v("INDEXBANNER2", response.body());
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONArray data = jsonObject.getJSONArray("data");
                            if (ObjectUtils.isEmpty(data)) {
                                return;
                            }
                            List<BannerBean> bannerBeans = JSONObject.parseArray(data.toJSONString(), BannerBean.class);
                            if (isRefresh) {
                                advDatas.clear();
                            }
                            for (int i = 0; i < bannerBeans.size(); i++) {
                                advDatas.add(ServerInfo.IMAGE + bannerBeans.get(i).getAd_image());
                            }
                            advAdapter.notifyDataSetChanged();
                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(getActivity(), request, this);
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

    private void addbannerData() {

        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.INDEXBANNER)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("plate_code", "APP_Index_Banner");

        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("INDEXBANNER", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");

                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONArray data = jsonObject.getJSONArray("data");
                            if (ObjectUtils.isEmpty(data)) {
                                return;
                            }
                            List<BannerBean> bannerBeans = JSONObject.parseArray(data.toJSONString(), BannerBean.class);
                            if (isRefresh) {
                                bannerDatas.clear();
                            }

                            for (int i = 0; i < bannerBeans.size(); i++) {
                                bannerDatas.add(ServerInfo.IMAGE + bannerBeans.get(i).getAd_image());
                            }
                            bannerAdapter.notifyDataSetChanged();
                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(getActivity(), request, this);
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


    private List<QiugouBean> qiugouDatas = new ArrayList<>();
    private QiugouLayoutAdapter qiugouLayoutAdapter;

    private void addQiugou() {
        LinearLayoutHelper helper = new LinearLayoutHelper();
        qiugouLayoutAdapter = new QiugouLayoutAdapter(context, helper, qiugouDatas);
        delegateAdapter.addAdapter(qiugouLayoutAdapter);
        qiugouLayoutAdapter.setOnQiugouItemClickListener(new QiugouLayoutAdapter.OnQiugouItemClickListener() {
            @Override
            public void onQiugouItemClick(QiugouBean item) {
                Intent intent = new Intent(context, QiugouxiangqingActivity.class);
                intent.putExtra("enquiryId", item.getId());
                intent.putExtra("isCharger", item.getIsCharger());
                intent.putExtra("status", item.getStatus());
                ActivityUtils.startActivity(intent);
            }
        });

    }

    private void addTitle(String title) {
        SingleLayoutHelper helper = new SingleLayoutHelper();
        SingleTitleLayoutAdapter adapter = new SingleTitleLayoutAdapter(context, helper, 1, title, false);
        delegateAdapter.addAdapter(adapter);
        if (StringUtils.equals("热门求购", title)) {
            adapter.setOnMoreClickListner(new SingleTitleLayoutAdapter.OnMoreClickListner() {
                @Override
                public void onMoreClick() {
                    ActivityUtils.startActivity(QiugoudatingActivity.class);
                }
            });
        } else {
            adapter.setOnMoreClickListner(new SingleTitleLayoutAdapter.OnMoreClickListner() {
                @Override
                public void onMoreClick() {
                    ActivityUtils.startActivity(KaifangshangchengActivity.class);
                }
            });
        }

    }

    //banner的adapter
    private List<String> bannerDatas = new ArrayList<>();
    private SingleAdvLayoutAdapter bannerAdapter;

    private void setBannerData() {
        SingleLayoutHelper helper = new SingleLayoutHelper();
        bannerAdapter = new SingleAdvLayoutAdapter(context, helper, 1, bannerDatas);
        delegateAdapter.addAdapter(bannerAdapter);
    }


    private boolean isRefresh;

    private SwipeRefreshLayout.OnRefreshListener refreshListener;

    private void initView(View view) {
        mRecyclerview = view.findViewById(R.id.fragment_home_recyclerview);
        mSwipeRefreshlayout = view.findViewById(R.id.fragment_home_swipe_refreshlayout);

        virtualLayoutManager = new VirtualLayoutManager(context);
        mRecyclerview.setLayoutManager(virtualLayoutManager);

        delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        mRecyclerview.setAdapter(delegateAdapter);

        mToolbarTitle = (TextView) view.findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) view.findViewById(R.id.toolbar_back);
        mToolbarBack.setVisibility(View.INVISIBLE);
        mToolbarTitle.setText("首页");
        refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉业务
                isRefresh = true;
                addbannerData();
                addAdvData();
                addData();
            }
        };
        mSwipeRefreshlayout.setOnRefreshListener(refreshListener);
        mSwipeRefreshlayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshlayout.setRefreshing(true);
                refreshListener.onRefresh();
            }
        });
        mSwipeRefreshlayout.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_green_light, android.R.color.holo_blue_light);
        mFragmentHomeSearch = (TextView) view.findViewById(R.id.fragment_home_search);
        mFragmentHomeSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.fragment_home_search:
                ActivityUtils.startActivity(SearchActivity.class);
                break;
        }
    }
}
