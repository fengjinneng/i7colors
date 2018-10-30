package com.company.qcy.ui.activity.qiugoudating;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.qiugou.QiugouxiangqingRecyclerviewAdapter;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.bean.qiugou.BaojiaBean;
import com.company.qcy.bean.qiugou.QiugouBean;
import com.company.qcy.ui.activity.user.LoginActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.timqi.collapsibletextview.CollapsibleTextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class QiugouxiangqingActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 防紫外线整理剂
     */
    private TextView mActivityQiugouxiangqingShangpinming;
    /**
     * 防紫外线整理剂
     */
    private TextView mActivityQiugouxiangqingSpm;
    /**
     * 助剂 功能性
     */
    private TextView mActivityQiugouxiangqingFenlei;
    /**
     * 50KG/桶
     */
    private TextView mActivityQiugouxiangqingBaozhuang;
    /**
     * 100
     */
    private TextView mActivityQiugouxiangqingShuliang;
    /**
     * 2018-08-04
     */
    private TextView mActivityQiugouxiangqingJiaohuoshijian;
    /**
     * 银行承兑
     */
    private TextView mActivityQiugouxiangqingFukuanfangshi;
    /**
     * 上海市 上海市
     */
    private TextView mActivityQiugouxiangqingDiqu;
    /**
     * 款到发货
     */
    private TextView mActivityQiugouxiangqingZhangqi;
    private RecyclerView mActivityQiugouxiangqingRecyclerview;

    private QiugouxiangqingRecyclerviewAdapter adapter;
    /**
     * 23
     */
    private TextView mQiugouxiangqingHeadviewFirstTime;
    /**
     * 天
     */
    private TextView mQiugouxiangqingHeadviewFirstTimeDanwei;
    /**
     * 23
     */
    private TextView mQiugouxiangqingHeadviewSecondTime;
    /**
     * 小时
     */
    private TextView mQiugouxiangqingHeadviewSecondTimeDanwei;
    /**
     * 防紫外线整理剂
     */
    private TextView mActivityQiugouxiangqingShangpinmingXiao;
    /**
     * 1991-08-06
     */
    private TextView mActivityQiugouxiangqingFabushijian;

    /**
     * 企业用户
     */
    private TextView mActivityQiugouxiangqingYonghushenfen;
    /**
     * ****公司
     */
    private TextView mActivityQiugouxiangqingCompany;
    private ImageView mActivityQiugouxiangqingFiveStar;
    private ImageView mActivityQiugouxiangqingFourStar;
    private ImageView mActivityQiugouxiangqingThirdStar;
    private ImageView mActivityQiugouxiangqingSecondStar;
    private ImageView mActivityQiugouxiangqingFirstStar;
    /**
     * 暂无信息！
     */
    private TextView mActivityQiugouxiangqingShuoming;

    //qiugou id
    private Long enquiryId;
    /**
     * 已完成
     */
    private TextView mActivityQiugouxiangqingStatus;
    /**
     * 参与报价
     */
    private Button mActivityQiugouxiangqingCanyubaojia;

    private int isWode;//判断是否从我的页面转过来
    /**
     * 关闭求购
     */
    private Button mActivityQiugouxiangqingGuanbiqiugo;
    /**
     * 我的发布
     */
    private TextView mActivityQiugouxiangqingWodefabu;


    private String qiugouStatus;
    private TextView mActivityQiugouxiangqingLishiqiugou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qiugouxiangqing);
        enquiryId = getIntent().getLongExtra("enquiryId", 0);
        isWode = getIntent().getIntExtra("wode", 0);
        isCharger = getIntent().getStringExtra("isCharger");
        qiugouStatus = getIntent().getStringExtra("status");
        initView();
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
        adapter = new QiugouxiangqingRecyclerviewAdapter(R.layout.item_qiugouxiangqing_recyclerview, datas, isWode, isCharger, qiugouStatus);

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

    }

    private Long enquiryOfferId;


    private void cainabaojia() {
        OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.CAINABAOJIA)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("enquiryOfferId", enquiryOfferId)
                .params("token", SPUtils.getInstance().getString("token"))
                .execute(new DialogStringCallback(QiugouxiangqingActivity.this) {
                    @Override
                    public void onSuccess(Response<String> response) {

                        try {
                            LogUtils.v("cainabaojia", response.body());
                            if (response.code() == 200) {
                                JSONObject jsonObject = JSONObject.parseObject(response.body());

                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {

                                    String data = jsonObject.getString("data");
                                    if (StringUtils.equals(data, "true")) {
                                        ToastUtils.showShort("您已经成功接受报价");
                                        EventBus.getDefault().post(new MessageBean(MessageBean.Code.CAINABAOJIACHENGGONG));
                                    } else ToastUtils.showShort("采纳报价失败");
                                    return;

                                }
                                SignAndTokenUtil.checkSignAndToken(QiugouxiangqingActivity.this, jsonObject);

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


    private QiugouBean qiugouBean;

    private void addQiugouxiangqingData() {

        OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.QIUGOUXIANGQING)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("enquiryId", enquiryId)
                .params("token", SPUtils.getInstance().getString("token"))
                .execute(new DialogStringCallback(QiugouxiangqingActivity.this) {
                    @Override
                    public void onSuccess(Response<String> response) {

                        try {
                            LogUtils.v("addQiugouxiangqing", response.body());
                            if (response.code() == 200) {
                                JSONObject jsonObject = JSONObject.parseObject(response.body());

                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {

                                    JSONObject data = jsonObject.getJSONObject("data");

                                    qiugouBean = data.toJavaObject(QiugouBean.class);
                                    reflashQiugouxinxi(qiugouBean);

                                    return;

                                }
                                SignAndTokenUtil.checkSignAndToken(QiugouxiangqingActivity.this, jsonObject);

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

    //是否是发送消息过来需要刷新
    private boolean isReflash;

    @Override
    public void onReciveMessage(MessageBean msg) {
        super.onReciveMessage(msg);

        switch (msg.getCode()) {
            case MessageBean.Code.BAOJIACHENGGONG:
                isReflash = true;
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
        }
    }

    private String isCharger;

    private void reflashQiugouxinxi(QiugouBean qiugouBean) {

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
                mActivityQiugouxiangqingYonghushenfen.setText(qiugouBean.getPublishType());
                mActivityQiugouxiangqingYonghushenfen.setBackground(getResources().getDrawable(R.mipmap.qiyeyonghu));
                mActivityQiugouxiangqingWodefabu.setBackground(getResources().getDrawable(R.drawable.background_wodefabu_qiye));
                mActivityQiugouxiangqingCompany.setText(qiugouBean.getCompanyName());
            } else {
                mActivityQiugouxiangqingWodefabu.setBackground(getResources().getDrawable(R.drawable.background_wodefabu_geren));
                mActivityQiugouxiangqingYonghushenfen.setText(qiugouBean.getPublishType());
                mActivityQiugouxiangqingYonghushenfen.setBackground(getResources().getDrawable(R.mipmap.gerenfabu));
                mActivityQiugouxiangqingCompany.setText(qiugouBean.getCompanyName2());
            }

            mActivityQiugouxiangqingCanyubaojia.setVisibility(View.GONE);
            if (StringUtils.equals(qiugouBean.getStatus(), "1")) {
                mActivityQiugouxiangqingStatus.setVisibility(View.GONE);
                mActivityQiugouxiangqingGuanbiqiugo.setVisibility(View.VISIBLE);
            } else {
                mActivityQiugouxiangqingCanyubaojia.setVisibility(View.GONE);
                mActivityQiugouxiangqingStatus.setVisibility(View.VISIBLE);
            }


        }

        if (StringUtils.equals("0", qiugouBean.getIsCharger())) {
            mActivityQiugouxiangqingWodefabu.setVisibility(View.GONE);
            if (StringUtils.equals(getResources().getString(R.string.qiyefabu), qiugouBean.getPublishType())) {
                mActivityQiugouxiangqingYonghushenfen.setText(qiugouBean.getPublishType());
                mActivityQiugouxiangqingYonghushenfen.setBackground(getResources().getDrawable(R.mipmap.qiyeyonghu));
                mActivityQiugouxiangqingCompany.setText("********公司");
            } else {
                mActivityQiugouxiangqingWodefabu.setBackground(getResources().getDrawable(R.drawable.background_wodefabu_geren));
                mActivityQiugouxiangqingYonghushenfen.setText(qiugouBean.getPublishType());
                mActivityQiugouxiangqingYonghushenfen.setBackground(getResources().getDrawable(R.mipmap.gerenfabu));
                mActivityQiugouxiangqingCompany.setText("********公司");
            }

            if (StringUtils.equals(qiugouBean.getStatus(), "1")) {
                mActivityQiugouxiangqingCanyubaojia.setVisibility(View.VISIBLE);
                mActivityQiugouxiangqingStatus.setVisibility(View.GONE);
            } else {
                mActivityQiugouxiangqingCanyubaojia.setVisibility(View.GONE);
                mActivityQiugouxiangqingStatus.setVisibility(View.VISIBLE);
            }

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

        OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.BAOJIALIEBIAO)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("enquiryId", enquiryId)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("offerId", "")
                .execute(new DialogStringCallback(QiugouxiangqingActivity.this) {
                    @Override
                    public void onSuccess(Response<String> response) {

                        try {
                            LogUtils.v("addBaojialiebiaoData", response.body());
                            if (response.code() == 200) {
                                JSONObject jsonObject = JSONObject.parseObject(response.body());

                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {

                                    JSONArray data = jsonObject.getJSONArray("data");

                                    List<BaojiaBean> qiugoudatingBeans = JSONObject.parseArray(data.toJSONString(), BaojiaBean.class);
                                    if (isReflash) {
                                        datas.clear();
                                    }
                                    adapter.addData(qiugoudatingBeans);
                                    return;

                                }
                                SignAndTokenUtil.checkSignAndToken(QiugouxiangqingActivity.this, jsonObject);

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

    //关闭求购
    private void guanbiqiugou() {

        OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.GUANBIQIUGOU)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("token", SPUtils.getInstance().getString("token"))
                .params("id", enquiryId)
                .execute(new DialogStringCallback(this) {
                    @Override
                    public void onSuccess(Response<String> response) {

                        try {
                            if (response.code() == 200) {

                                JSONObject jsonObject = JSONObject.parseObject(response.body());

                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                    String data = jsonObject.getString("data");
                                    String msg = jsonObject.getString("msg");
                                    LogUtils.v("GUANBIQIUGOU", data);

                                    if (StringUtils.equals("true", data)) {
                                        ToastUtils.showShort("你已经成功关闭求购");

                                        EventBus.getDefault().post(new MessageBean(MessageBean.Code.GUANBIQIUGOU));
                                    } else {
                                        ToastUtils.showShort(msg);
                                    }
                                } else
                                    SignAndTokenUtil.checkSignAndToken(QiugouxiangqingActivity.this, jsonObject);

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
        return view;
    }

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

                    Intent intent = new Intent(QiugouxiangqingActivity.this, CanyubaojiaActivity.class);
                    intent.putExtra("enquiryId", enquiryId);
                    ActivityUtils.startActivity(intent);

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
        }
    }
}
