package com.company.qcy.ui.activity.chanpindating;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.chanpindating.ChanpinCanshuRecyclerviewAdapter;
import com.company.qcy.bean.kaifangshangcheng.ProductBean;
import com.company.qcy.ui.activity.kaifangshangcheng.KFSCXiangqingActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

public class ChanpinxiangqingActivity extends AppCompatActivity implements View.OnClickListener {


    private String productID;//商品ID
    /**
     * 分享
     */
    private TextView mActivityChanpinxiangqingShare;
    private ImageView mActivityChanpinxiangqingImg;
    /**
     * CTC DONGWU Disperse-Cationic Red SD-GRL 100%
     */
    private TextView mActivityChanpinxiangqingNameEnglish;
    private RecyclerView recyclerview;
    private LinearLayout mActivityChanpinxiangqingDianpuLayout;
    private LinearLayout mActivityChanpinxiangqingKefuLayout;
    private ChanpinCanshuRecyclerviewAdapter adapter;
    private List<ProductBean.PropMapBean> datas;
    private ProductBean productBean;
    private ImageView mActivityChanpinxiangqingShoucangImg;
    /**
     * 收藏
     */
    private TextView mActivityChanpinxiangqingShoucangText;
    /**
     * 盐城东吴化工有限公司
     */
    private TextView mActivityChanpinxiangqingChanpinming;
    /**
     * 阳离子染料 苏州东吴 分散阳离子红SD-GRL 100%
     */
    private TextView mActivityChanpinxiangqingCompanyname;
    /**
     * 34.5
     */
    private TextView mActivityChanpinxiangqingPrice;
    private ConstraintLayout mActivityChanpinxiangqingJiageLayout;
    private ConstraintLayout mActivityChanpinxiangqingYijiaLayout;
    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chanpinxiangqing);
        productID = getIntent().getStringExtra("id");
        initView();

    }

    private void initView() {
        datas = new ArrayList<>();
        recyclerview = (RecyclerView) findViewById(R.id.activity_chanpinxiangqing_recyclerview);
        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        mActivityChanpinxiangqingShare = (TextView) findViewById(R.id.activity_chanpinxiangqing_share);
        mActivityChanpinxiangqingShare.setOnClickListener(this);
        mActivityChanpinxiangqingImg = (ImageView) findViewById(R.id.activity_chanpinxiangqing_img);
        mActivityChanpinxiangqingNameEnglish = (TextView) findViewById(R.id.activity_chanpinxiangqing_name_english);
        mActivityChanpinxiangqingDianpuLayout = (LinearLayout) findViewById(R.id.activity_chanpinxiangqing_dianpu_layout);
        mActivityChanpinxiangqingDianpuLayout.setOnClickListener(this);
        mActivityChanpinxiangqingKefuLayout = (LinearLayout) findViewById(R.id.activity_chanpinxiangqing_kefu_layout);
        mActivityChanpinxiangqingKefuLayout.setOnClickListener(this);
        adapter = new ChanpinCanshuRecyclerviewAdapter(R.layout.item_chanpincanshu, datas);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerview.addItemDecoration(itemDecoration);
        recyclerview.setAdapter(adapter);
        addData();
        if (SPUtils.getInstance().getBoolean("isCompany")) {
            isShouCang();
        }
        mActivityChanpinxiangqingShoucangImg = (ImageView) findViewById(R.id.activity_chanpinxiangqing_shoucang_img);
        mActivityChanpinxiangqingShoucangImg.setOnClickListener(this);
        mActivityChanpinxiangqingShoucangText = (TextView) findViewById(R.id.activity_chanpinxiangqing_shoucang_text);
        mActivityChanpinxiangqingShoucangText.setOnClickListener(this);
        mActivityChanpinxiangqingChanpinming = (TextView) findViewById(R.id.activity_chanpinxiangqing_chanpinming);
        mActivityChanpinxiangqingCompanyname = (TextView) findViewById(R.id.activity_chanpinxiangqing_companyname);
        mActivityChanpinxiangqingPrice = (TextView) findViewById(R.id.activity_chanpinxiangqing_price);
        mActivityChanpinxiangqingJiageLayout = (ConstraintLayout) findViewById(R.id.activity_chanpinxiangqing_jiage_layout);
        mActivityChanpinxiangqingYijiaLayout = (ConstraintLayout) findViewById(R.id.activity_chanpinxiangqing_yijia_layout);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mToolbarTitle.setText("产品详情");
    }

    private boolean isShoucang;

    private void isShouCang() {

        OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.ISFAVORITEPRODUCT)
                .tag(this)

                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("id", productID)
                .params("token", SPUtils.getInstance().getString("token"))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        try {
                            if (response.code() == 200) {

                                JSONObject jsonObject = JSONObject.parseObject(response.body());
                                LogUtils.v("ISFAVORITEPRODUCT", jsonObject);
                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                    String data = jsonObject.getString("data");
                                    if (StringUtils.equals("true", data)) {
                                        isShoucang = true;
                                        mActivityChanpinxiangqingShoucangImg.setImageDrawable(getResources().getDrawable(R.mipmap.yishouchang));
                                        mActivityChanpinxiangqingShoucangText.setText("已收藏");
                                    } else {
                                        isShoucang = false;
                                        mActivityChanpinxiangqingShoucangImg.setImageDrawable(getResources().getDrawable(R.mipmap.weishouchang));
                                        mActivityChanpinxiangqingShoucangText.setText("收藏");
                                    }
                                    return;

                                } else
                                    SignAndTokenUtil.checkSignAndToken(ChanpinxiangqingActivity.this, jsonObject);

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

    private void addData() {

        OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.GETCHANPINDETAIL)
                .tag(this)

                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("id", productID)
                .execute(new DialogStringCallback(this) {
                    @Override
                    public void onSuccess(Response<String> response) {

                        try {
                            if (response.code() == 200) {

                                JSONObject jsonObject = JSONObject.parseObject(response.body());

                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    LogUtils.v("GETCHANPINDETAIL", data);
                                    productBean = data.toJavaObject(ProductBean.class);
                                    List<ProductBean.PropMapBean> propMap = productBean.getPropMap();
                                    adapter.addData(propMap);
                                    setData(productBean);
                                    return;

                                } else
                                    SignAndTokenUtil.checkSignAndToken(ChanpinxiangqingActivity.this, jsonObject);

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


    private void shouCang() {

        OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.ADDFAVORITEPRODUCT)
                .tag(this)

                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("id", productID)
                .params("token", SPUtils.getInstance().getString("token"))
                .execute(new DialogStringCallback(this) {
                    @Override
                    public void onSuccess(Response<String> response) {

                        try {
                            if (response.code() == 200) {

                                JSONObject jsonObject = JSONObject.parseObject(response.body());
                                LogUtils.v("ADDFAVORITEPRODUCT", jsonObject);
                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                    String data = jsonObject.getString("data");
                                    if (StringUtils.equals("true", data)) {
                                        isShoucang = true;
                                        mActivityChanpinxiangqingShoucangImg.setImageDrawable(getResources().getDrawable(R.mipmap.yishouchang));
                                        mActivityChanpinxiangqingShoucangText.setText("已收藏");
                                    }
                                    return;

                                } else
                                    SignAndTokenUtil.checkSignAndToken(ChanpinxiangqingActivity.this, jsonObject);

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

    private void quxiaoShouCang() {

        OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.CANCEFAVORITEPRODUCT)
                .tag(this)

                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("id", productID)
                .params("token", SPUtils.getInstance().getString("token"))
                .execute(new DialogStringCallback(this) {
                    @Override
                    public void onSuccess(Response<String> response) {

                        try {
                            if (response.code() == 200) {

                                JSONObject jsonObject = JSONObject.parseObject(response.body());
                                LogUtils.v("CANCEFAVORITEPRODUCT", jsonObject);
                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                    String data = jsonObject.getString("data");
                                    if (StringUtils.equals("true", data)) {
                                        isShoucang = false;
                                        mActivityChanpinxiangqingShoucangImg.setImageDrawable(getResources().getDrawable(R.mipmap.weishouchang));
                                        mActivityChanpinxiangqingShoucangText.setText("收藏");
                                    }
                                    return;

                                } else
                                    SignAndTokenUtil.checkSignAndToken(ChanpinxiangqingActivity.this, jsonObject);

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

    private void setData(ProductBean productBean) {
        if (ObjectUtils.isEmpty(productBean)) {
            return;
        }

        GlideUtils.loadImage(this, ServerInfo.IMAGE + productBean.getPic(), mActivityChanpinxiangqingImg);
        mActivityChanpinxiangqingChanpinming.setText(productBean.getProductName());
        if (productBean.isDisplayPrice()) {
            mActivityChanpinxiangqingJiageLayout.setVisibility(View.VISIBLE);
            mActivityChanpinxiangqingYijiaLayout.setVisibility(View.INVISIBLE);
            mActivityChanpinxiangqingPrice.setText(productBean.getPrice());
        } else {
            mActivityChanpinxiangqingJiageLayout.setVisibility(View.INVISIBLE);
            mActivityChanpinxiangqingYijiaLayout.setVisibility(View.VISIBLE);

        }
        mActivityChanpinxiangqingCompanyname.setText(productBean.getSupplierShotName());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.activity_chanpinxiangqing_share:
                break;
            case R.id.activity_chanpinxiangqing_dianpu_layout:
                Intent intent = new Intent(this, KFSCXiangqingActivity.class);
                if (!ObjectUtils.isEmpty(productBean)) {
                    intent.putExtra("id", productBean.getShopId());
                    ActivityUtils.startActivity(intent);
                }

                break;
            case R.id.activity_chanpinxiangqing_kefu_layout:
                break;
            case R.id.activity_chanpinxiangqing_shoucang_img:

                if (!SPUtils.getInstance().getBoolean("isCompany")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("您还不是企业用户！");
                    builder.setMessage("升级为企业用户可收藏盖产品，是否要升级为企业用户?");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ToastUtils.showShort("升级企业用户");
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ToastUtils.showShort("取消升级");
                        }
                    });
                    builder.show();
                } else {
                    if (isShoucang) {
                        quxiaoShouCang();
                    } else {
                        shouCang();
                    }
                }


                break;
            case R.id.activity_chanpinxiangqing_shoucang_text:
                if (!SPUtils.getInstance().getBoolean("isCompany")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("您还不是企业用户！");
                    builder.setMessage("升级为企业用户可收藏盖产品，是否要升级为企业用户?");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ToastUtils.showShort("升级企业用户");
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ToastUtils.showShort("取消升级");
                        }
                    });
                    builder.show();
                } else {
                    if (isShoucang) {
                        quxiaoShouCang();

                    } else shouCang();
                    {
                        shouCang();
                    }
                }
                break;
            case R.id.toolbar_back:
                finish();
                break;
        }
    }

}
