package com.company.qcy.ui.activity.chanpindating;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.PermisionUtil;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.ShareUtil;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.chanpindating.ChanpinCanshuRecyclerviewAdapter;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.kaifangshangcheng.ProductBean;
import com.company.qcy.ui.activity.kaifangshangcheng.KFSCXiangqingActivity;
import com.company.qcy.ui.activity.pengyouquan.ChangeNicknameActivity;
import com.company.qcy.ui.activity.user.LianxikefuActivity;
import com.lijiankun24.shadowlayout.ShadowLayout;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/product/productDetail")
public class ChanpinxiangqingActivity extends BaseActivity implements View.OnClickListener {


    //变量名与传参时一致且修饰符为public
    @Autowired
    public String id;//商品ID
    /**
     * 分享
     */
    private TextView mActivityChanpinxiangqingShare;
    private ImageView mActivityChanpinxiangqingImg;
    /**
     * CTC DONGWU Disperse-Cationic Red SD-GRL 100%
     */
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
    private ConstraintLayout yijianhujiao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chanpinxiangqing);
        Uri data = getIntent().getData();
        //不为空说明是外部网页传过来的
        if (!ObjectUtils.isEmpty(data)) {
            id = data.getQueryParameter("id");
        } else id = getIntent().getStringExtra("id");
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
        yijianhujiao = findViewById(R.id.activity_chanpinxiangqing_yijianhujiao);
        yijianhujiao.setOnClickListener(this);
    }

    private boolean isShoucang;

    private void isShouCang() {

        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.ISFAVORITEPRODUCT)
                .tag(this)

                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("id", id)
                .params("token", SPUtils.getInstance().getString("token"));

        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("ISFAVORITEPRODUCT", response.body());
                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
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
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(ChanpinxiangqingActivity.this, request, this);
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
            }
        };

        request.execute(stringCallback);


    }

    private void addData() {

        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.GETCHANPINDETAIL)
                .tag(this)

                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("id", id);

        DialogStringCallback stringCallback = new DialogStringCallback(this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("GETCHANPINDETAIL", response.body());
                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONObject data = jsonObject.getJSONObject("data");

                            productBean = data.toJavaObject(ProductBean.class);
                            List<ProductBean.PropMapBean> propMap = productBean.getPropMap();
                            if (ObjectUtils.isEmpty(propMap)) {
                                adapter.addHeaderView(LayoutInflater.from(ChanpinxiangqingActivity.this).inflate(R.layout.head_chanpin_noinfo, null));
                            } else adapter.addData(propMap);
                            setData();
                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(ChanpinxiangqingActivity.this,request,this);
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


    private void shouCang() {
        ToastUtils.showShort("暂不支持收藏！");

//        OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.ADDFAVORITEPRODUCT)
//                .tag(this)
//                .params("sign", SPUtils.getInstance().getString("sign"))
//                .params("id", id)
//                .params("token", SPUtils.getInstance().getString("token"))
//                .execute(new DialogStringCallback(this) {
//                    @Override
//                    public void onSuccess(Response<String> response) {
//
//                        try {
//                            if (response.code() == 200) {
//
//                                JSONObject jsonObject = JSONObject.parseObject(response.body());
//                                LogUtils.v("ADDFAVORITEPRODUCT", jsonObject);
//                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
//                                    String data = jsonObject.getString("data");
//                                    if (StringUtils.equals("true", data)) {
//                                        isShoucang = true;
//                                        mActivityChanpinxiangqingShoucangImg.setImageDrawable(getResources().getDrawable(R.mipmap.yishouchang));
//                                        mActivityChanpinxiangqingShoucangText.setText("已收藏");
//                                    }
//                                    return;
//
//                                } else
//                                    SignAndTokenUtil.checkSignAndToken(ChanpinxiangqingActivity.this, jsonObject);
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

//    private void quxiaoShouCang() {
//
//        OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.CANCEFAVORITEPRODUCT)
//                .tag(this)
//
//                .params("sign", SPUtils.getInstance().getString("sign"))
//                .params("id", id)
//                .params("token", SPUtils.getInstance().getString("token"))
//                .execute(new DialogStringCallback(this) {
//                    @Override
//                    public void onSuccess(Response<String> response) {
//
//                        try {
//                            if (response.code() == 200) {
//
//                                JSONObject jsonObject = JSONObject.parseObject(response.body());
//                                LogUtils.v("CANCEFAVORITEPRODUCT", jsonObject);
//                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
//                                    String data = jsonObject.getString("data");
//                                    if (StringUtils.equals("true", data)) {
//                                        isShoucang = false;
//                                        mActivityChanpinxiangqingShoucangImg.setImageDrawable(getResources().getDrawable(R.mipmap.weishouchang));
//                                        mActivityChanpinxiangqingShoucangText.setText("收藏");
//                                    }
//                                    return;
//
//                                } else
//                                    SignAndTokenUtil.checkSignAndToken(ChanpinxiangqingActivity.this, jsonObject);
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
//
//
//    }

    private void setData() {
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
        mActivityChanpinxiangqingCompanyname.setText(productBean.getCompanyName());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.activity_chanpinxiangqing_share:
                if (ObjectUtils.isEmpty(productBean)) {
                    ToastUtils.showShort("分享异常");
                    return;
                }
                ShareUtil.shareProduct(ChanpinxiangqingActivity.this, "【产品】" + productBean.getProductName(),
                        productBean.getCompanyName(), productBean.getPic(), productBean.getId());

                break;
            case R.id.activity_chanpinxiangqing_dianpu_layout:
                Intent intent = new Intent(this, KFSCXiangqingActivity.class);
                if (!ObjectUtils.isEmpty(productBean)) {
                    intent.putExtra("id", productBean.getShopId());
                    ActivityUtils.startActivity(intent);
                }

                break;
            case R.id.activity_chanpinxiangqing_kefu_layout:
                ActivityUtils.startActivity(LianxikefuActivity.class);
                break;
            case R.id.activity_chanpinxiangqing_shoucang_img:
                ToastUtils.showShort("暂时不支持收藏!");

//                if (!SPUtils.getInstance().getBoolean("isCompany")) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                    builder.setTitle("您还不是企业用户！");
//                    builder.setMessage("升级为企业用户可收藏盖产品，是否要升级为企业用户?");
//                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            ToastUtils.showShort("升级企业用户");
//                        }
//                    });
//                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            ToastUtils.showShort("取消升级");
//                        }
//                    });
//                    builder.show();
//                } else {
//                    if (isShoucang) {
//                        quxiaoShouCang();
//                    } else {
//                        shouCang();
//                    }
//                }
                break;
            case R.id.activity_chanpinxiangqing_shoucang_text:
//                if (!SPUtils.getInstance().getBoolean("isCompany")) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                    builder.setTitle("您还不是企业用户！");
//                    builder.setMessage("升级为企业用户可收藏盖产品，是否要升级为企业用户?");
//                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            ToastUtils.showShort("升级企业用户");
//                        }
//                    });
//                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            ToastUtils.showShort("取消升级");
//                        }
//                    });
//                    builder.show();
//                } else {
//                    if (isShoucang) {
//                        quxiaoShouCang();
//
//                    } else shouCang();
//                    {
//                        shouCang();
//                    }
//                }
                break;
            case R.id.toolbar_back:
                finish();
                break;

            case R.id.activity_chanpinxiangqing_yijianhujiao:
                if (ObjectUtils.isEmpty(productBean)) {
                    return;
                }
                if (StringUtils.isEmpty(productBean.getPhone())) {
                    ToastUtils.showShort("该企业没有留下电话号码哦！");
                    return;
                }
                PermisionUtil.callPhone(ChanpinxiangqingActivity.this, productBean.getPhone());
                break;
        }
    }

}
