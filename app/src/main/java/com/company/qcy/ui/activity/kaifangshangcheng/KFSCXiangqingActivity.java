package com.company.qcy.ui.activity.kaifangshangcheng;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.company.qcy.R;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.NetworkImageHolderView;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.kaifangshangcheng.KFSCXiangqingRecyclerviewAdapter;
import com.company.qcy.bean.kaifangshangcheng.DianpuxiangqingBean;
import com.company.qcy.bean.kaifangshangcheng.ProductBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.timqi.collapsibletextview.CollapsibleTextView;

import java.util.ArrayList;
import java.util.List;


public class KFSCXiangqingActivity extends AppCompatActivity implements View.OnClickListener {

    private ConvenientBanner convenientBanner;
    private RecyclerView recyclerView;
    private KFSCXiangqingRecyclerviewAdapter adapter;
    /**
     * 全部商品
     */
    private TextView mQuanbushangpin;
    /**
     * 公司简介
     */
    private TextView mGongsijianjie;
    private LinearLayout mLinnear;
    List<ProductBean> products;

    private Long id;//店铺ID
    private ImageView mActivityKfscxiangqingCompanyLogo;
    /**
     * 山东索玛德染料有限公司
     */
    private TextView mActivityKfscxiangqingCompanyname;
    private ImageView mActivityKfscxiangqingFirststar;
    private ImageView mActivityKfscxiangqingSecondstar;
    private ImageView mActivityKfscxiangqingThirdstar;
    private ImageView mActivityKfscxiangqingFourstar;
    private ImageView mActivityKfscxiangqingFivestar;
    private ImageView mActivityKfscxiangqingYijianhujiao;
    /**
     * 全部商品
     */
    private TextView mActivityKfscxiangqingAllProducts;
    /**
     * 公司简介
     */
    private TextView mActivityKfscxiangqingCompanyIntroduce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kfscxiangqing);
        id = getIntent().getLongExtra("id", 0);
        initView();
    }

    private void initView() {


        initBanner();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_fengexian));
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(layoutManager);

        products = new ArrayList<>();
        adapter = new KFSCXiangqingRecyclerviewAdapter(R.layout.item_kfsc_xiangqing, products);
        recyclerView.setAdapter(adapter);


        mQuanbushangpin = (TextView) findViewById(R.id.activity_kfscxiangqing_all_products);
        mQuanbushangpin.setOnClickListener(this);
        mGongsijianjie = (TextView) findViewById(R.id.activity_kfscxiangqing_company_introduce);
        mGongsijianjie.setOnClickListener(this);
        mLinnear = (LinearLayout) findViewById(R.id.linnear);

        mActivityKfscxiangqingCompanyLogo = (ImageView) findViewById(R.id.activity_kfscxiangqing_company_logo);
        mActivityKfscxiangqingCompanyname = (TextView) findViewById(R.id.activity_kfscxiangqing_companyname);
        mActivityKfscxiangqingFirststar = (ImageView) findViewById(R.id.activity_kfscxiangqing_firststar);
        mActivityKfscxiangqingSecondstar = (ImageView) findViewById(R.id.activity_kfscxiangqing_secondstar);
        mActivityKfscxiangqingThirdstar = (ImageView) findViewById(R.id.activity_kfscxiangqing_thirdstar);
        mActivityKfscxiangqingFourstar = (ImageView) findViewById(R.id.activity_kfscxiangqing_fourstar);
        mActivityKfscxiangqingFivestar = (ImageView) findViewById(R.id.activity_kfscxiangqing_fivestar);
        mActivityKfscxiangqingYijianhujiao = (ImageView) findViewById(R.id.activity_kfscxiangqing_yijianhujiao);
        mActivityKfscxiangqingAllProducts = (TextView) findViewById(R.id.activity_kfscxiangqing_all_products);
        mActivityKfscxiangqingCompanyIntroduce = (TextView) findViewById(R.id.activity_kfscxiangqing_company_introduce);

        addDianpuData();
        addShangpinData();
    }


    private int pageNo;

    private void addShangpinData() {


        products.add(new ProductBean());
        products.add(new ProductBean());
        products.add(new ProductBean());
        products.add(new ProductBean());
        products.add(new ProductBean());
        products.add(new ProductBean());
        products.add(new ProductBean());
        products.add(new ProductBean());
        products.add(new ProductBean());
        products.add(new ProductBean());
        products.add(new ProductBean());
        products.add(new ProductBean());



//        pageNo++;
//        OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.KFSCSHANGPINLIEBIAO)
//                .tag(this)
//                .params("sign", SPUtils.getInstance().getString("sign"))
//                .params("pageNo", pageNo)
//                .params("pageSize", 20)
//                .params("marketId", id)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> response) {
//
//                        try {
//                            if (response.code() == 200) {
//
//                                JSONObject jsonObject = JSONObject.parseObject(response.body());
//
//                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
//                                    JSONArray data = jsonObject.getJSONArray("data");
//                                    LogUtils.v("KFSCSHANGPINLIEBIAO", data);
//                                    List<ProductBean> productBeans = JSONObject.parseArray(data.toJSONString(), ProductBean.class);
//
//
//                                    if (ObjectUtils.isEmpty(productBeans)) {
//                                        adapter.loadMoreEnd();
//                                        return;
//                                    }
//                                    adapter.addData(productBeans);
//                                    adapter.loadMoreComplete();
//                                    adapter.disableLoadMoreIfNotFullPage();
//                                    return;
//
//                                } else
//                                    SignAndTokenUtil.checkSignAndToken(KFSCXiangqingActivity.this, jsonObject);
//
//                            } else {
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Response<String> response) {
//                        super.onError(response);
//                    }
//                });


    }


    private void addDianpuData() {
        OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.DIANPUXIANGQING)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("id", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        try {
                            if (response.code() == 200) {

                                JSONObject jsonObject = JSONObject.parseObject(response.body());

                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    LogUtils.v("DIANPUXIANGQING", data);

                                    DianpuxiangqingBean dianpuBean = data.toJavaObject(DianpuxiangqingBean.class);
                                    Glide.with(KFSCXiangqingActivity.this).load(ServerInfo.IMAGE + dianpuBean.getLogo()).into(mActivityKfscxiangqingCompanyLogo);
                                    mActivityKfscxiangqingCompanyname.setText(dianpuBean.getCompanyName());

                                    return;

                                } else
                                    SignAndTokenUtil.checkSignAndToken(KFSCXiangqingActivity.this, jsonObject);

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

    private void initBanner() {

        List<String> datas = new ArrayList<>();
        datas.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1240469072,2191573380&fm=26&gp=0.jpg");
        datas.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1240469072,2191573380&fm=26&gp=0.jpg");
        datas.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1240469072,2191573380&fm=26&gp=0.jpg");
        datas.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1240469072,2191573380&fm=26&gp=0.jpg");
        datas.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1240469072,2191573380&fm=26&gp=0.jpg");
        convenientBanner = (ConvenientBanner) findViewById(R.id.activity_kfscxiangqing_banner);
        recyclerView = (RecyclerView) findViewById(R.id.activity_kfscxiangqing_recyclerview);

        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, datas);
        convenientBanner.setPageIndicator(new int[]{R.mipmap.banner_unchoiced, R.mipmap.banner_choiced});
        convenientBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        //设置如果只有一组数据时不能滑动
        convenientBanner.setPointViewVisible(datas.size() == 1 ? false : true); // 指示器
        convenientBanner.setManualPageable(datas.size() == 1 ? false : true);//设置false,手动影响（设置了该项无法手动切换）
        convenientBanner.setCanLoop(datas.size() == 1 ? false : true); // 是否循环


        convenientBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.activity_kfscxiangqing_all_products:

                recyclerView.setVisibility(View.VISIBLE);

                break;
            case R.id.activity_kfscxiangqing_company_introduce:
                recyclerView.setVisibility(View.GONE);
                break;
        }
    }
}
