package com.company.qcy.ui.activity.qiugoudating;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.NetworkUtil;
import com.company.qcy.Utils.PermisionUtil;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.Utils.UserUtil;
import com.company.qcy.Utils.share.ShareUtil;
import com.company.qcy.adapter.qiugou.QiugouxiangqingRecyclerviewAdapter;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.bean.qiugou.BaojiaBean;
import com.company.qcy.bean.qiugou.QiugouBean;
import com.company.qcy.ui.activity.kaifangshangcheng.KFSCVipActivity;
import com.company.qcy.ui.activity.kaifangshangcheng.QiyezizhiActivity;
import com.company.qcy.ui.activity.pengyouquan.DavrenzhengActivity;
import com.company.qcy.ui.activity.user.LoginActivity;
import com.company.qcy.ui.activity.user.QiyerenzhengActivity;
import com.company.qcy.ui.activity.user.ZhanghaozhongxinActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/enquiry/enquiryDetail")
public class QiugouxiangqingActivity extends BaseActivity implements View.OnClickListener {
    private TextView mActivityQiugouxiangqingShangpinming;
    private TextView mActivityQiugouxiangqingSpm;
    private TextView mActivityQiugouxiangqingFenlei;
    private TextView mActivityQiugouxiangqingBaozhuang;
    private TextView mActivityQiugouxiangqingShuliang;
    private TextView mActivityQiugouxiangqingJiaohuoshijian;
    private TextView mActivityQiugouxiangqingFukuanfangshi;
    private TextView mActivityQiugouxiangqingDiqu;
    private TextView mActivityQiugouxiangqingZhangqi;
    private RecyclerView mActivityQiugouxiangqingRecyclerview;
    private QiugouxiangqingRecyclerviewAdapter adapter;
    private TextView mQiugouxiangqingHeadviewFirstTime;
    private TextView mQiugouxiangqingHeadviewFirstTimeDanwei;
    private TextView mQiugouxiangqingHeadviewSecondTime;
    private TextView mQiugouxiangqingHeadviewSecondTimeDanwei;
    private TextView mActivityQiugouxiangqingShangpinmingXiao;
    private TextView mActivityQiugouxiangqingFabushijian;
    private TextView mActivityQiugouxiangqingYonghushenfen;
    private TextView mActivityQiugouxiangqingCompany;
    private ImageView mActivityQiugouxiangqingFiveStar;
    private ImageView mActivityQiugouxiangqingFourStar;
    private ImageView mActivityQiugouxiangqingThirdStar;
    private ImageView mActivityQiugouxiangqingSecondStar;
    private ImageView mActivityQiugouxiangqingFirstStar;
    private ConstraintLayout zhitongcheLayout;
    /**
     * 暂无信息！
     */
    private TextView mActivityQiugouxiangqingShuoming;

    //qiugou id
    @Autowired
    public Long enquiryId;
    /**
     * 已完成
     */
    private TextView mActivityQiugouxiangqingStatus;
    /**
     * 参与报价
     */
    private Button mActivityQiugouxiangqingCanyubaojia;

    /**
     * 关闭求购
     */
    private Button mActivityQiugouxiangqingGuanbiqiugo;
    /**
     * 我的发布
     */
    private TextView mActivityQiugouxiangqingWodefabu;


    private TextView mActivityQiugouxiangqingLishiqiugou;
    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private Long wodeBaojiaID;
    /**
     * 设置
     */
    private TextView mToolbarText;
    /**
     * 查看联系方式
     */
    private TextView mActivityQiugouxiangqingChakan;
    private ConstraintLayout mActivityQiugouxiangqingConstraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qiugouxiangqing);

        wodeBaojiaID = getIntent().getLongExtra("enquiryOfferId", 0);

        Uri data = getIntent().getData();
        //不为空说明是外部网页传过来的
        if (!ObjectUtils.isEmpty(data)) {
            enquiryId = Long.parseLong(data.getQueryParameter("enquiryId"));
        } else enquiryId = getIntent().getLongExtra("enquiryId", 0);

        initView();

        if (wodeBaojiaID != 0) {
            //卖家消息已读
            haveReadMessage();
        }

    }

    private void haveReadMessage() {

        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.READMYACCEPTOFFER)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("enquiryOfferId", wodeBaojiaID)
                .params("token", SPUtils.getInstance().getString("token"));


        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("READMYACCEPTOFFER", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {

                            EventBus.getDefault().post(new MessageBean(MessageBean.Code.ENQUIRYMESSAGEREAD));

                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(QiugouxiangqingActivity.this, request, this);
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

    private List<BaojiaBean> datas;

    private void initView() {
        mActivityQiugouxiangqingRecyclerview = findViewById(R.id.activity_qiugouxiangqing_recyclerview);

        datas = new ArrayList<>();

        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityQiugouxiangqingRecyclerview.setLayoutManager(layoutManager);

        //创建适配器
        adapter = new QiugouxiangqingRecyclerviewAdapter(R.layout.item_qiugouxiangqing_recyclerview, datas);

        adapter.addHeaderView(getHeadView(), 0);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_fengexian));
        mActivityQiugouxiangqingRecyclerview.addItemDecoration(itemDecoration);

        //给RecyclerView设置适配器
        mActivityQiugouxiangqingRecyclerview.setAdapter(adapter);
        addQiugouxiangqingData();
        mActivityQiugouxiangqingCanyubaojia = (Button) findViewById(R.id.activity_qiugouxiangqing_canyubaojia);
        mActivityQiugouxiangqingCanyubaojia.setOnClickListener(this);
        mActivityQiugouxiangqingGuanbiqiugo = (Button) findViewById(R.id.activity_qiugouxiangqing_guanbiqiugo);
        mActivityQiugouxiangqingGuanbiqiugo.setOnClickListener(this);

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.item_qiugouxiangqing_recyclerview_cainabaojia:

                        BaojiaBean bean = (BaojiaBean) adapter.getData().get(position);
                        enquiryOfferId = bean.getId();
                        AlertDialog.Builder builder = new AlertDialog.Builder(QiugouxiangqingActivity.this);
                        builder.setMessage("您确定采用该报价吗");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                cainabaojia();
                            }
                        });
                        builder.setNegativeButton("取消", null);
                        builder.show();

                        break;
                }
            }
        });

        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mToolbarTitle.setText("求购详情");
        mToolbarText = (TextView) findViewById(R.id.toolbar_text);
        mToolbarText.setOnClickListener(this);
        mToolbarText.setVisibility(View.VISIBLE);
        mToolbarText.setText("分享");
        mActivityQiugouxiangqingChakan = (TextView) findViewById(R.id.activity_qiugouxiangqing_chakan);
        mActivityQiugouxiangqingChakan.setOnClickListener(this);
        mActivityQiugouxiangqingConstraintLayout = (ConstraintLayout) findViewById(R.id.activity_qiugouxiangqing_constraintLayout);
    }

    private Long enquiryOfferId;


    private void cainabaojia() {
        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.CAINABAOJIA)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("enquiryOfferId", enquiryOfferId)
                .params("token", SPUtils.getInstance().getString("token"));

        DialogStringCallback stringCallback = new DialogStringCallback(QiugouxiangqingActivity.this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("CAINABAOJIA", response.body());
                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {

                            String data = jsonObject.getString("data");
                            if (StringUtils.equals(data, "true")) {
                                ToastUtils.showShort(msg);
                                EventBus.getDefault().post(new MessageBean(MessageBean.Code.CAINABAOJIACHENGGONG));
                            } else ToastUtils.showShort(msg);
                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(QiugouxiangqingActivity.this, request, this);
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


    private QiugouBean qiugouBean;

    private void addQiugouxiangqingData() {

        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.QIUGOUXIANGQING)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("enquiryId", enquiryId)
                .params("token", SPUtils.getInstance().getString("token"));

        DialogStringCallback stringCallback = new DialogStringCallback(QiugouxiangqingActivity.this) {
            @Override
            public void onSuccess(Response<String> response) {

                try {
                    LogUtils.v("addQiugouxiangqing", response.body());
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {

                            JSONObject data = jsonObject.getJSONObject("data");

                            qiugouBean = data.toJavaObject(QiugouBean.class);
                            reflashQiugouxinxi();

                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(QiugouxiangqingActivity.this, request, this);
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

    //是否是发送消息过来需要刷新
    private boolean isReflash;

    @Override
    public void onReciveMessage(MessageBean msg) {
        super.onReciveMessage(msg);

        switch (msg.getCode()) {
            case MessageBean.Code.BAOJIACHENGGONG:
                isReflash = true;
                qiugouBean.setLoginUserRemainOfferCount(qiugouBean.getLoginUserRemainOfferCount() - 1);
                addBaojialiebiaoData();
                break;

            case MessageBean.Code.CAINABAOJIACHENGGONG:
                isReflash = true;
                mActivityQiugouxiangqingGuanbiqiugo.setVisibility(View.GONE);
                mActivityQiugouxiangqingCanyubaojia.setVisibility(View.GONE);
                mActivityQiugouxiangqingStatus.setVisibility(View.VISIBLE);
                addBaojialiebiaoData();
                break;
            case MessageBean.Code.GUANBIQIUGOU:
                isReflash = true;
                mActivityQiugouxiangqingGuanbiqiugo.setVisibility(View.GONE);
                mActivityQiugouxiangqingStatus.setVisibility(View.VISIBLE);
                addBaojialiebiaoData();
                break;

            case MessageBean.Code.DELU:
                isReflash = true;
                addQiugouxiangqingData();
                break;

            case MessageBean.Code.WXLOGIN:
                isReflash = true;
                addQiugouxiangqingData();
                break;
        }
    }

    private void reflashQiugouxinxi() {
        if (ObjectUtils.isEmpty(qiugouBean)) {
            return;
        }

        setStarLevel(qiugouBean.getCreditLevel());
        mActivityQiugouxiangqingFabushijian.setText(qiugouBean.getCreateAtString());
        mActivityQiugouxiangqingShangpinmingXiao.setText(qiugouBean.getProductName());
        mActivityQiugouxiangqingShangpinming.setText(qiugouBean.getProductName());
        mActivityQiugouxiangqingDiqu.setText(qiugouBean.getLocationProvince() + " " + qiugouBean.getLocationCity());
        mActivityQiugouxiangqingFukuanfangshi.setText(qiugouBean.getPaymentType());
        mActivityQiugouxiangqingFenlei.setText(qiugouBean.getProductCli1Name() + " " + qiugouBean.getProductCli2Name());
        mActivityQiugouxiangqingBaozhuang.setText(qiugouBean.getPack());
        mActivityQiugouxiangqingShuliang.setText(qiugouBean.getNum() + qiugouBean.getNumUnit());
        mActivityQiugouxiangqingJiaohuoshijian.setText(qiugouBean.getDeliveryDateString());
        mActivityQiugouxiangqingZhangqi.setText(qiugouBean.getPaymentPeriodString());
        mActivityQiugouxiangqingShuoming.setText(qiugouBean.getDescription());
        mActivityQiugouxiangqingLishiqiugou.setText(qiugouBean.getEnquiryTimes() + "");


        //判断是本人吗？是
        if (StringUtils.equals("1", qiugouBean.getIsCharger())) {
            mActivityQiugouxiangqingWodefabu.setVisibility(View.VISIBLE);
            if (StringUtils.equals(getResources().getString(R.string.qiyefabu), qiugouBean.getPublishType())) {
                mActivityQiugouxiangqingWodefabu.setBackground(getResources().getDrawable(R.drawable.background_wodefabu_qiye));
                mActivityQiugouxiangqingCompany.setText(qiugouBean.getCompanyName());
            } else {
                mActivityQiugouxiangqingWodefabu.setBackground(getResources().getDrawable(R.drawable.background_wodefabu_geren));
                mActivityQiugouxiangqingCompany.setText(qiugouBean.getCompanyName2());
            }

            if (StringUtils.equals(qiugouBean.getStatus(), "1")) {
                mActivityQiugouxiangqingConstraintLayout.setVisibility(View.VISIBLE);
                mActivityQiugouxiangqingStatus.setVisibility(View.GONE);
                mActivityQiugouxiangqingGuanbiqiugo.setVisibility(View.VISIBLE);
            } else {
                mActivityQiugouxiangqingConstraintLayout.setVisibility(View.GONE);
                mActivityQiugouxiangqingStatus.setVisibility(View.VISIBLE);
            }

        }

        if (StringUtils.equals(getResources().getString(R.string.qiyefabu), qiugouBean.getPublishType())) {
            mActivityQiugouxiangqingYonghushenfen.setText(qiugouBean.getPublishType());
            mActivityQiugouxiangqingYonghushenfen.setBackground(getResources().getDrawable(R.mipmap.qiyeyonghu));
        } else {
            mActivityQiugouxiangqingYonghushenfen.setText(qiugouBean.getPublishType());
            mActivityQiugouxiangqingYonghushenfen.setBackground(getResources().getDrawable(R.mipmap.gerenfabu));
        }

        if (StringUtils.equals("0", qiugouBean.getIsCharger())) {
            mActivityQiugouxiangqingWodefabu.setVisibility(View.GONE);
            if (StringUtils.equals(getResources().getString(R.string.qiyefabu), qiugouBean.getPublishType())) {
            } else {
                mActivityQiugouxiangqingWodefabu.setBackground(getResources().getDrawable(R.drawable.background_wodefabu_geren));
            }
            mActivityQiugouxiangqingCompany.setText("********公司");

            if (StringUtils.equals(qiugouBean.getStatus(), "1")) {
                mActivityQiugouxiangqingConstraintLayout.setVisibility(View.VISIBLE);
                mActivityQiugouxiangqingCanyubaojia.setVisibility(View.VISIBLE);
                mActivityQiugouxiangqingStatus.setVisibility(View.GONE);
            } else {
                mActivityQiugouxiangqingConstraintLayout.setVisibility(View.GONE);
                mActivityQiugouxiangqingStatus.setVisibility(View.VISIBLE);
            }

        }

        if (StringUtils.equals("1", qiugouBean.getShowInfo())) {
            zhitongcheLayout.setVisibility(View.VISIBLE);
            mActivityQiugouxiangqingChakan.setVisibility(View.VISIBLE);
            mActivityQiugouxiangqingConstraintLayout.setVisibility(View.VISIBLE);
        }


        //设置求购时间
        if (StringUtils.isEmpty(qiugouBean.getSurplusDay())) {
            if (StringUtils.isEmpty(qiugouBean.getSurplusHour())) {
                if (StringUtils.isEmpty(qiugouBean.getSurplusMin())) {
                } else {
                    mQiugouxiangqingHeadviewFirstTime.setText(qiugouBean.getSurplusMin());
                    mQiugouxiangqingHeadviewSecondTime.setText(qiugouBean.getSurplusSec());
                    mQiugouxiangqingHeadviewFirstTimeDanwei.setText("分");
                    mQiugouxiangqingHeadviewSecondTimeDanwei.setText("秒");
                }
            } else {
                mQiugouxiangqingHeadviewFirstTime.setText(qiugouBean.getSurplusHour());
                mQiugouxiangqingHeadviewSecondTime.setText(qiugouBean.getSurplusMin());
                mQiugouxiangqingHeadviewFirstTimeDanwei.setText("小时");
                mQiugouxiangqingHeadviewSecondTimeDanwei.setText("分钟");
            }
        } else {
            mQiugouxiangqingHeadviewFirstTime.setText(qiugouBean.getSurplusDay());
            mQiugouxiangqingHeadviewSecondTime.setText(qiugouBean.getSurplusHour());
        }

        addBaojialiebiaoData();

    }


    //设置信用等级
    private void setStarLevel(String level) {
        switch (level) {
            case "1":
                mActivityQiugouxiangqingFirstStar.setBackgroundResource(R.mipmap.wujiaoxing_shixin);
                mActivityQiugouxiangqingSecondStar.setBackgroundResource(R.mipmap.wujiaoxing_kongxin);
                mActivityQiugouxiangqingThirdStar.setBackgroundResource(R.mipmap.wujiaoxing_kongxin);
                mActivityQiugouxiangqingFourStar.setBackgroundResource(R.mipmap.wujiaoxing_kongxin);
                mActivityQiugouxiangqingFiveStar.setBackgroundResource(R.mipmap.wujiaoxing_kongxin);
                break;
            case "2":
                mActivityQiugouxiangqingFirstStar.setBackgroundResource(R.mipmap.wujiaoxing_shixin);
                mActivityQiugouxiangqingSecondStar.setBackgroundResource(R.mipmap.wujiaoxing_shixin);
                mActivityQiugouxiangqingThirdStar.setBackgroundResource(R.mipmap.wujiaoxing_kongxin);
                mActivityQiugouxiangqingFourStar.setBackgroundResource(R.mipmap.wujiaoxing_kongxin);
                mActivityQiugouxiangqingFiveStar.setBackgroundResource(R.mipmap.wujiaoxing_kongxin);

                break;
            case "3":
                mActivityQiugouxiangqingFirstStar.setBackgroundResource(R.mipmap.wujiaoxing_shixin);
                mActivityQiugouxiangqingSecondStar.setBackgroundResource(R.mipmap.wujiaoxing_shixin);
                mActivityQiugouxiangqingThirdStar.setBackgroundResource(R.mipmap.wujiaoxing_shixin);
                mActivityQiugouxiangqingFourStar.setBackgroundResource(R.mipmap.wujiaoxing_kongxin);
                mActivityQiugouxiangqingFiveStar.setBackgroundResource(R.mipmap.wujiaoxing_kongxin);
                break;
            case "4":
                mActivityQiugouxiangqingFirstStar.setBackgroundResource(R.mipmap.wujiaoxing_shixin);
                mActivityQiugouxiangqingSecondStar.setBackgroundResource(R.mipmap.wujiaoxing_shixin);
                mActivityQiugouxiangqingThirdStar.setBackgroundResource(R.mipmap.wujiaoxing_shixin);
                mActivityQiugouxiangqingFourStar.setBackgroundResource(R.mipmap.wujiaoxing_shixin);
                mActivityQiugouxiangqingFiveStar.setBackgroundResource(R.mipmap.wujiaoxing_kongxin);
                break;
            case "5":
                mActivityQiugouxiangqingFirstStar.setBackgroundResource(R.mipmap.wujiaoxing_shixin);
                mActivityQiugouxiangqingSecondStar.setBackgroundResource(R.mipmap.wujiaoxing_shixin);
                mActivityQiugouxiangqingThirdStar.setBackgroundResource(R.mipmap.wujiaoxing_shixin);
                mActivityQiugouxiangqingFourStar.setBackgroundResource(R.mipmap.wujiaoxing_shixin);
                mActivityQiugouxiangqingFiveStar.setBackgroundResource(R.mipmap.wujiaoxing_shixin);
                break;
        }

    }

    private void addBaojialiebiaoData() {

        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.BAOJIALIEBIAO)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("enquiryId", enquiryId)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("offerId", "");


        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("addBaojialiebiaoData", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");

                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONArray data = jsonObject.getJSONArray("data");

                            List<BaojiaBean> baojiaBeans = JSONObject.parseArray(data.toJSONString(), BaojiaBean.class);
                            if (isReflash) {
                                datas.clear();
                            }
                            adapter.setIsCharger(qiugouBean.getIsCharger());
                            adapter.setQiugouStatus(qiugouBean.getStatus());
                            adapter.addData(baojiaBeans);
                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(QiugouxiangqingActivity.this, request, this);
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

    //关闭求购
    private void guanbiqiugou() {

        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.GUANBIQIUGOU)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("token", SPUtils.getInstance().getString("token"))
                .params("id", enquiryId);

        DialogStringCallback stringCallback = new DialogStringCallback(this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("GUANBIQIUGOU", response.body());

                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");

                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            String data = jsonObject.getString("data");

                            if (StringUtils.equals("true", data)) {
                                ToastUtils.showShort(msg);

                                EventBus.getDefault().post(new MessageBean(MessageBean.Code.GUANBIQIUGOU));
                            } else {
                                ToastUtils.showShort(msg);
                            }
                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(QiugouxiangqingActivity.this, request, this);
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


    public View getHeadView() {

        View view = LayoutInflater.from(this).inflate(R.layout.qiugouxiangqing_headview, null);
        mQiugouxiangqingHeadviewFirstTime = view.findViewById(R.id.qiugouxiangqing_headview_first_time);
        mQiugouxiangqingHeadviewFirstTimeDanwei = view.findViewById(R.id.qiugouxiangqing_headview_first_time_danwei);
        mQiugouxiangqingHeadviewSecondTime = view.findViewById(R.id.qiugouxiangqing_headview_second_time);
        mQiugouxiangqingHeadviewSecondTimeDanwei = view.findViewById(R.id.qiugouxiangqing_headview_second_time_danwei);
        mActivityQiugouxiangqingShangpinmingXiao = view.findViewById(R.id.activity_qiugouxiangqing_shangpinming_xiao);
        mActivityQiugouxiangqingFabushijian = view.findViewById(R.id.activity_qiugouxiangqing_fabushijian);
        mActivityQiugouxiangqingYonghushenfen = view.findViewById(R.id.activity_qiugouxiangqing_yonghushenfen);
        mActivityQiugouxiangqingCompany = view.findViewById(R.id.activity_qiugouxiangqing_company);
        mActivityQiugouxiangqingFiveStar = view.findViewById(R.id.activity_qiugouxiangqing_five_star);
        mActivityQiugouxiangqingFourStar = view.findViewById(R.id.activity_qiugouxiangqing_four_star);
        mActivityQiugouxiangqingThirdStar = view.findViewById(R.id.activity_qiugouxiangqing_third_star);
        mActivityQiugouxiangqingSecondStar = view.findViewById(R.id.activity_qiugouxiangqing_second_star);
        mActivityQiugouxiangqingFirstStar = view.findViewById(R.id.activity_qiugouxiangqing_first_star);
        mActivityQiugouxiangqingShuoming = view.findViewById(R.id.activity_qiugouxiangqing_shuoming);
        mActivityQiugouxiangqingShangpinming = view.findViewById(R.id.activity_qiugouxiangqing_shangpinming);
        mActivityQiugouxiangqingDiqu = view.findViewById(R.id.activity_qiugouxiangqing_diqu);
        mActivityQiugouxiangqingFukuanfangshi = view.findViewById(R.id.activity_qiugouxiangqing_fukuanfangshi);
        mActivityQiugouxiangqingStatus = view.findViewById(R.id.activity_qiugouxiangqing_status);
        mActivityQiugouxiangqingFenlei = view.findViewById(R.id.activity_qiugouxiangqing_fenlei);
        mActivityQiugouxiangqingBaozhuang = view.findViewById(R.id.activity_qiugouxiangqing_baozhuang);
        mActivityQiugouxiangqingShuliang = view.findViewById(R.id.activity_qiugouxiangqing_shuliang);
        mActivityQiugouxiangqingJiaohuoshijian = view.findViewById(R.id.activity_qiugouxiangqing_jiaohuoshijian);
        mActivityQiugouxiangqingZhangqi = view.findViewById(R.id.activity_qiugouxiangqing_zhangqi);
        mActivityQiugouxiangqingWodefabu = (TextView) view.findViewById(R.id.activity_qiugouxiangqing_wodefabu);
        mActivityQiugouxiangqingLishiqiugou = (TextView) view.findViewById(R.id.activity_qiugouxiangqing_lishiqiugou);
        mActivityQiugouxiangqingFiveStar = (ImageView) view.findViewById(R.id.activity_qiugouxiangqing_five_star);
        mActivityQiugouxiangqingFourStar = (ImageView) view.findViewById(R.id.activity_qiugouxiangqing_four_star);
        mActivityQiugouxiangqingThirdStar = (ImageView) view.findViewById(R.id.activity_qiugouxiangqing_third_star);
        mActivityQiugouxiangqingSecondStar = (ImageView) view.findViewById(R.id.activity_qiugouxiangqing_second_star);
        mActivityQiugouxiangqingFirstStar = (ImageView) view.findViewById(R.id.activity_qiugouxiangqing_first_star);
        zhitongcheLayout = view.findViewById(R.id.activity_qiugouxiangqing_zhitongche);
        return view;
    }

    private AlertDialog.Builder baojiaBuilder;
    private AlertDialog baojiaDialog;
    private ImageView baojiaDialogClose;
    private TextView baojiaDialogContent;
    private TextView baojiaDialogCancle;
    private TextView baojiaDialogCommit;
    //需要升级到企业用户或者升级到付费用户
    //1为升级企业，2位升级付费用户
    private int baojiaDialogStatus = 0;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;

            //参与报价
            case R.id.activity_qiugouxiangqing_canyubaojia:


                if (StringUtils.isEmpty(SPUtils.getInstance().getString("isLogin"))) {
                    ActivityUtils.startActivity(LoginActivity.class);
                } else {

                    if (ObjectUtils.isEmpty(baojiaBuilder)) {
                        baojiaBuilder = new AlertDialog.Builder(QiugouxiangqingActivity.this);
                        View inflate = LayoutInflater.from(QiugouxiangqingActivity.this).inflate(R.layout.dialog_tongyong, null);
                        baojiaBuilder.setView(inflate);
                        baojiaDialog = baojiaBuilder.create();

                        baojiaDialogClose = inflate.findViewById(R.id.dialog_tongyong_close);
                        baojiaDialogContent = inflate.findViewById(R.id.dialog_tongyong_content);
                        baojiaDialogCancle = inflate.findViewById(R.id.dialog_tongyong_button1);
                        baojiaDialogCommit = inflate.findViewById(R.id.dialog_tongyong_button2);

                        baojiaDialogClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                baojiaDialog.dismiss();
                            }
                        });

                        baojiaDialogCancle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                baojiaDialog.dismiss();
                            }
                        });

                        baojiaDialogCommit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (baojiaDialogStatus) {
                                    case 1:
                                        ActivityUtils.startActivity(ZhanghaozhongxinActivity.class);
                                        break;
                                    case 2:
                                        ActivityUtils.startActivity(KFSCVipActivity.class);
                                        break;
                                }

                            }
                        });
                    }

                    if (StringUtils.equals(getResources().getString(R.string.geren), SPUtils.getInstance().getString("userType"))) {

                        if (!ObjectUtils.isEmpty(qiugouBean.getLoginUserRemainOfferCount())) {

                            if (qiugouBean.getLoginUserRemainOfferCount() > 0) {
                                Intent intent = new Intent(QiugouxiangqingActivity.this, CanyubaojiaActivity.class);
                                intent.putExtra("enquiryId", enquiryId);
                                intent.putExtra("productName", qiugouBean.getProductName());
                                intent.putExtra("numUnit", qiugouBean.getNumUnit());
                                ActivityUtils.startActivity(intent);

                            } else {
                                baojiaDialogStatus = 1;
                                baojiaDialogContent.setText("您的报价次数不足,请升级为企业用户!");
                                baojiaDialogCancle.setText("取消");
                                baojiaDialogCommit.setText("升级企业用户");
                                if (!baojiaDialog.isShowing()) {
                                    baojiaDialog.show();
                                }
                            }
                        }


                    } else if (StringUtils.equals(getResources().getString(R.string.putongqiye), SPUtils.getInstance().getString("userType"))) {

                        if (!ObjectUtils.isEmpty(qiugouBean.getLoginUserRemainOfferCount())) {

                            if (qiugouBean.getLoginUserRemainOfferCount() > 0) {
                                Intent intent = new Intent(QiugouxiangqingActivity.this, CanyubaojiaActivity.class);
                                intent.putExtra("enquiryId", enquiryId);
                                intent.putExtra("productName", qiugouBean.getProductName());
                                intent.putExtra("numUnit", qiugouBean.getNumUnit());
                                ActivityUtils.startActivity(intent);

                            } else {

                                baojiaDialogStatus = 2;
                                baojiaDialogContent.setText("您的报价次数不足,请升级为付费企业用户!");
                                baojiaDialogCancle.setText("取消");
                                baojiaDialogCommit.setText("升级付费企业用户");
                                if (!baojiaDialog.isShowing()) {
                                    baojiaDialog.show();
                                }
                            }
                        }

                    } else {
                        Intent intent = new Intent(QiugouxiangqingActivity.this, CanyubaojiaActivity.class);
                        intent.putExtra("enquiryId", enquiryId);
                        intent.putExtra("productName", qiugouBean.getProductName());
                        intent.putExtra("numUnit", qiugouBean.getNumUnit());
                        ActivityUtils.startActivity(intent);
                    }

                }
                break;

            //关闭求购
            case R.id.activity_qiugouxiangqing_guanbiqiugo:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示！");
                builder.setMessage("您确定要关闭这个求购吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        guanbiqiugou();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();


                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.toolbar_text:
                if (ObjectUtils.isEmpty(qiugouBean)) {
                    ToastUtils.showShort("分享异常");
                    return;
                }
                ShareUtil.shareEnquiry(QiugouxiangqingActivity.this, "【求购】" + qiugouBean.getProductName(),
                        "地区:" + qiugouBean.getLocationProvince() + " " +
                                qiugouBean.getLocationCity() + "\n" + "求购重量:" + qiugouBean.getNum() + "kg", qiugouBean.getId());
                break;
            case R.id.activity_qiugouxiangqing_chakan:

                if (NetworkUtil.isNetworkAvailable(this)) {

                    if (UserUtil.isLogin()) {
                        checkedPhone();
                    } else {
                        ActivityUtils.startActivity(LoginActivity.class);
                    }
                } else {
                    ToastUtils.showShort("当前网络不可用!");
                }

        }

    }


    AlertDialog.Builder zhitongcheBuilder;
    TextView zhitongcheTitle;
    TextView zhitongcheText;
    TextView zhitongcheHujiao;
    TextView zhitongcheFufei;
    AlertDialog alertDialog;
    //查看联系方式后获得的电话
    private String callPhone;


    //查看联系方式后，付费会员展示确定按钮
    private boolean sure;

    private void checkedPhone() {

        View inflate;
        ImageView close;

        if (ObjectUtils.isEmpty(zhitongcheBuilder)) {
            zhitongcheBuilder = new AlertDialog.Builder(QiugouxiangqingActivity.this);
            inflate = LayoutInflater.from(QiugouxiangqingActivity.this).inflate(R.layout.dialog_tongyong, null);
            zhitongcheBuilder.setView(inflate);
            alertDialog = zhitongcheBuilder.create();

            close = inflate.findViewById(R.id.dialog_tongyong_close);
            zhitongcheTitle = inflate.findViewById(R.id.dialog_tongyong_title);
            zhitongcheText = inflate.findViewById(R.id.dialog_tongyong_content);
            zhitongcheFufei = inflate.findViewById(R.id.dialog_tongyong_button2);
            zhitongcheHujiao = inflate.findViewById(R.id.dialog_tongyong_button1);

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

            zhitongcheFufei.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sure) {
                        alertDialog.dismiss();
                    } else {
                        ActivityUtils.startActivity(KFSCVipActivity.class);
                    }
                }
            });

            zhitongcheHujiao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (StringUtils.isEmpty(callPhone)) {
                        PermisionUtil.callKefu(QiugouxiangqingActivity.this);
                    } else {
                        PermisionUtil.callPhone(QiugouxiangqingActivity.this, callPhone);
                    }
                }
            });
        }

        //已经查看过该求购了
        if (StringUtils.equals("1", qiugouBean.getLoginUserIsShowInfo())) {
            zhitongcheTitle.setText("本次查看信息不扣除剩余次数!");
            requestPhone();

        } else {

            if (StringUtils.equals(getResources().getString(R.string.geren), SPUtils.getInstance().getString("userType")) ||
                    StringUtils.equals(getResources().getString(R.string.putongqiye), SPUtils.getInstance().getString("userType"))) {

                //个人用户和普通认证账户
                zhitongcheText.setText("您还不是付费会员，无法查看。如果您已是付费会员无法查看，请联系客服!");
                zhitongcheHujiao.setText("联系客服");
                zhitongcheFufei.setText("付费会员");
                zhitongcheHujiao.setVisibility(View.VISIBLE);
                zhitongcheFufei.setVisibility(View.VISIBLE);
                alertDialog.show();
            } else if (StringUtils.equals(getResources().getString(R.string.fufeiqiye), SPUtils.getInstance().getString("userType"))) {

                if (!ObjectUtils.isEmpty(qiugouBean.getLoginUserRemainShowInfoCount())) {

                    if (qiugouBean.getLoginUserRemainShowInfoCount() <= 0) {
                        //没有查看机会了
                        zhitongcheText.setText("您本月的查看次数已用完!");
                        zhitongcheHujiao.setVisibility(View.GONE);
                        zhitongcheFufei.setText("确定");
                        zhitongcheFufei.setVisibility(View.VISIBLE);
                        sure =true;
                        alertDialog.show();
                    } else if (qiugouBean.getLoginUserRemainShowInfoCount() > 0) {

                        isSureCheck();

                    }
                }
            }
        }

    }


    //是否确认要查看的弹窗
    private void isSureCheck() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View inflate = LayoutInflater.from(QiugouxiangqingActivity.this).inflate(R.layout.dialog_tongyong, null);
        builder.setView(inflate);
        AlertDialog isSureDialog = builder.create();

        ImageView close = inflate.findViewById(R.id.dialog_tongyong_close);
        TextView content = inflate.findViewById(R.id.dialog_tongyong_content);
        TextView button1 = inflate.findViewById(R.id.dialog_tongyong_button1);
        TextView button2 = inflate.findViewById(R.id.dialog_tongyong_button2);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSureDialog.dismiss();
            }
        });

        content.setText("本次查看将会消耗一次机会,是否确定查看?");

        button1.setText("取消");
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSureDialog.dismiss();
            }
        });

        button2.setText("确定");
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPhone();
                isSureDialog.dismiss();
                alertDialog.show();
            }
        });

        isSureDialog.show();

    }


    private void requestPhone() {
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.ZHITONGCHEINFO)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("token", SPUtils.getInstance().getString("token"))
                .params("enquiryId", enquiryId);

        DialogStringCallback stringCallback = new DialogStringCallback(this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("ZHITONGCHEINFO", response.body());

                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");

                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONObject data = jsonObject.getJSONObject("data");

                            if (!ObjectUtils.isEmpty(data)) {
                                String phone = data.getString("phone");
                                Integer remainCount = data.getInteger("remainCount");
                                if (remainCount == null) {
                                    return;
                                }

                                if (remainCount >= 0) {
                                    callPhone = phone;
                                    zhitongcheText.setText("查看成功，您本月剩余 " + remainCount + " 次查看采购商联系方式的机会！");
                                    //设置成已经查看过了
                                    qiugouBean.setLoginUserIsShowInfo("1");
                                    zhitongcheFufei.setVisibility(View.GONE);
                                    zhitongcheHujiao.setText("一键呼叫");
                                    zhitongcheHujiao.setVisibility(View.VISIBLE);
                                    alertDialog.show();
                                }
                            }

                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(QiugouxiangqingActivity.this, request, this);
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

}
