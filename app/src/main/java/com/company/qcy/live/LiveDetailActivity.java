package com.company.qcy.live;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.Utils.UserUtil;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.huodong.tuangou.bean.DefaultAddress;
import com.company.qcy.ui.activity.pengyouquan.ShipinbofangActivity;
import com.company.qcy.ui.activity.user.LoginActivity;
import com.hb.dialog.myDialog.MyAlertDialog;
import com.hb.dialog.myDialog.MyAlertInputDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import cn.iwgang.countdownview.CountdownView;

public class LiveDetailActivity extends BaseActivity implements View.OnClickListener {


    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private ImageView mActivityLiveDetailImg;
    /**
     * 99 支付观看
     */
    private Button mActivityLiveDetailButton2;
    /**
     * 智能纺织品及其研发应用智能纺织品及其 研发应用
     */
    private TextView mActivityLiveDetailTitle;
    /**
     * 直播开始时间：2020年3月30日  10：00~11：00
     */
    private TextView mActivityLiveDetailTime;
    private CountdownView mActivityLiveDetailCountdownView;
    /**
     * 路艳华 博士
     */
    private TextView mActivityLiveDetailTeacher;
    /**
     * 短信预约提醒
     */
    private Button mActivityLiveDetailButton1;

    private Long id;

    private LiveBean liveBean;
    private TextView describe;
    private ImageView mActivityLiveDetailImgStatus;
    private ConstraintLayout mActivityLiveDetailCountdownViewLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_detail);
        initView();
    }

    private void initView() {
        mActivityLiveDetailCountdownViewLayout = (ConstraintLayout) findViewById(R.id.activity_live_detail_countdownView_layout);

        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mActivityLiveDetailImg = (ImageView) findViewById(R.id.activity_live_detail_img);
        mActivityLiveDetailButton2 = (Button) findViewById(R.id.activity_live_detail_button2);
        mActivityLiveDetailButton2.setOnClickListener(this);
        mActivityLiveDetailTitle = (TextView) findViewById(R.id.activity_live_detail_title);
        mActivityLiveDetailTime = (TextView) findViewById(R.id.activity_live_detail_time);
        mActivityLiveDetailCountdownView = (CountdownView) findViewById(R.id.activity_live_detail_countdownView);
        mActivityLiveDetailTeacher = (TextView) findViewById(R.id.activity_live_detail_teacher);
        mActivityLiveDetailButton1 = (Button) findViewById(R.id.activity_live_detail_button1);
        mActivityLiveDetailButton1.setOnClickListener(this);
        mActivityLiveDetailImgStatus = (ImageView) findViewById(R.id.activity_live_detail_img_status);

        describe = (TextView) findViewById(R.id.activity_live_detail_describe);

        mToolbarTitle.setText("七彩云公开课");

        id = getIntent().getLongExtra("id", 0);

        addData();
    }

    private void addData() {

        HttpParams httpParams = new HttpParams();

        httpParams.put("sign", SPUtils.getInstance().getString("sign"));
        httpParams.put("token", SPUtils.getInstance().getString("token"));
        httpParams.put("id", id);

        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.CLASSDETAIL)
                .tag(this)
                .params(httpParams);

        DialogStringCallback stringCallback = new DialogStringCallback(this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("CLASSDETAIL", response.body());
                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            liveBean = data.toJavaObject(LiveBean.class);
                            setInfo();
                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(LiveDetailActivity.this, request, this);
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

    private boolean isBuy;
    private boolean isEnd;

    private void setInfo() {

        if (ObjectUtils.isEmpty(liveBean)) {
            return;
        }

//        GlideUtils.loadImageDefault(this, ServerInfo.IMAGE + liveBean.getBanner(), mActivityLiveDetailImg);

        Glide.with(this).load(ServerInfo.IMAGE + liveBean.getBanner()).into(mActivityLiveDetailImg);

        mActivityLiveDetailTitle.setText(liveBean.getTitle());
        mActivityLiveDetailTeacher.setText(liveBean.getTeacher());


        describe.setText(liveBean.getDescription());

        //直播未开始
        if (StringUtils.equals("2", liveBean.getIsEnd())) {

            mActivityLiveDetailTime.setBackground(getResources().getDrawable(R.drawable.background_live_weikaishi));
            mActivityLiveDetailImgStatus.setImageDrawable(getResources().getDrawable(R.mipmap.live_weikaihi));

            mActivityLiveDetailTime.setText("直播开始时间 : " + liveBean.getStartTime());
            mActivityLiveDetailCountdownView.start(Long.parseLong(liveBean.getStartTimeStamp()) - TimeUtils.getNowMills());

            if (StringUtils.equals(liveBean.getLoginUserIsBuy(), "0")) {
                mActivityLiveDetailButton2.setText("￥" + liveBean.getPrice() + " 购买课程");
            } else if (StringUtils.equals(liveBean.getLoginUserIsBuy(), "1")) {
                mActivityLiveDetailButton2.setText("进入直播间");
                isBuy = true;
            }

            if (StringUtils.equals("0", liveBean.getLoginUserIsReserve())) {

            } else if (StringUtils.equals("1", liveBean.getLoginUserIsReserve())) {
                mActivityLiveDetailButton1.setText("取消预约");
            }

            if (liveBean.getPrice() == 0) {
                isBuy = true;
                mActivityLiveDetailButton2.setText("进入直播间");
            }


        } else if (StringUtils.equals("0", liveBean.getIsEnd())) {
            //已结束
            mActivityLiveDetailTime.setTextColor(getResources().getColor(R.color.huikankecheng));
            mActivityLiveDetailTime.setBackground(getResources().getDrawable(R.drawable.background_live_yijieshu));
            mActivityLiveDetailCountdownViewLayout.setVisibility(View.GONE);
            mActivityLiveDetailImgStatus.setImageDrawable(getResources().getDrawable(R.mipmap.live_huikan));

            mActivityLiveDetailTime.setText("直播已结束");

            mActivityLiveDetailButton2.setText("查看回放");

            if(!ObjectUtils.isEmpty(liveBean.getArticleId())){
                if (StringUtils.equals(liveBean.getLoginUserIsBuy(), "0")) {
                    mActivityLiveDetailButton2.setText("￥" + liveBean.getPrice() + " 购买直播录像");
                } else if (StringUtils.equals(liveBean.getLoginUserIsBuy(), "1")) {
                    isBuy = true;
                }
            }

            isEnd = true;
            mActivityLiveDetailButton1.setVisibility(View.GONE);

            if (liveBean.getPrice() == 0) {
                isBuy = true;
            }

        } else if (StringUtils.equals("1", liveBean.getIsEnd())) {
            //进行中

            mActivityLiveDetailTime.setTextColor(getResources().getColor(R.color.chunhongse));
            mActivityLiveDetailTime.setBackground(getResources().getDrawable(R.drawable.background_live_zhibozhong));
            mActivityLiveDetailCountdownViewLayout.setVisibility(View.GONE);

            mActivityLiveDetailImgStatus.setImageDrawable(getResources().getDrawable(R.mipmap.live_zhibozhong));

            mActivityLiveDetailTime.setText("正在直播中");

            if (StringUtils.equals(liveBean.getLoginUserIsBuy(), "0")) {
                mActivityLiveDetailButton2.setText("￥" + liveBean.getPrice() + " 购买课程");
            } else if (StringUtils.equals(liveBean.getLoginUserIsBuy(), "1")) {
                mActivityLiveDetailButton2.setText("进入直播间");
                isBuy = true;
            }

            mActivityLiveDetailButton1.setVisibility(View.GONE);

            if (liveBean.getPrice() == 0) {
                isBuy = true;
                mActivityLiveDetailButton2.setText("进入直播间");
            }
        }
    }

    @Override
    public void onReciveMessage(MessageBean msg) {
        super.onReciveMessage(msg);

        switch (msg.getCode()) {
            case MessageBean.Code.DELU:
                addData();
                break;
            case MessageBean.Code.payLiveOrderSuccess:
                checkStatus();
                break;
            default:
                break;
        }
    }


    private void getDefaultAddress() {

        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.HUODONGMORENDIZHI)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("token", SPUtils.getInstance().getString("token"));

        DialogStringCallback stringCallback = new DialogStringCallback(this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("HUODONGMORENDIZHI", response.body());
                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONObject data = jsonObject.getJSONObject("data");

                            DefaultAddress defaultAddress = null;
                            if (!ObjectUtils.isEmpty(data)) {
                                defaultAddress = data.toJavaObject(DefaultAddress.class);

                            }

                            final MyAlertInputDialog myAlertInputDialog = new MyAlertInputDialog(LiveDetailActivity.this).builder()
                                    .setTitle("输入您的手机号码！")
                                    .setEditText("请输入您的手机号码");

                            EditText contentEditText = myAlertInputDialog.getContentEditText();
                            contentEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                            if (!ObjectUtils.isEmpty(defaultAddress) && !StringUtils.isEmpty(defaultAddress.getPhone())) {
                                contentEditText.setText(defaultAddress.getPhone());
                            }
                            myAlertInputDialog.setPositiveButton("确认", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (StringUtils.isTrimEmpty(myAlertInputDialog.getResult()) || !RegexUtils.isMobileSimple(myAlertInputDialog.getResult())) {
                                        ToastUtils.showShort("请输入正确的手机号码！");
                                        return;
                                    }
                                    phone = myAlertInputDialog.getResult();
                                    myAlertInputDialog.dismiss();
                                    yuyue();

                                }
                            }).setNegativeButton("取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                    showMsg("取消");
                                    myAlertInputDialog.dismiss();
                                }
                            });
                            myAlertInputDialog.show();
                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(LiveDetailActivity.this, request, this);
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


    private void checkStatus() {

        HttpParams httpParams = new HttpParams();

        httpParams.put("token", SPUtils.getInstance().getString("token"));
        httpParams.put("classId", liveBean.getId());
        httpParams.put("type", "school_live");
        httpParams.put("productType", "school_live_class");
        httpParams.put("orderNum", orderNum);

        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.checkPayStatus)
                .tag(this)
                .params(httpParams);

        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("checkPayStatus", response.body());
                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            liveBean.setLoginUserIsBuy("1");
                            mActivityLiveDetailButton2.setText("进入直播间");
                            isBuy = true;
                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(LiveDetailActivity.this, request, this);
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


    private String orderNum;

    private void weChatPay(JSONObject wxPayBean) {
        Runnable payRunnable = new Runnable() {  //这里注意要放在子线程
            @Override
            public void run() {
                try {
                    IWXAPI api = WXAPIFactory.createWXAPI(LiveDetailActivity.this, "wx63410989373f8975", false);
                    //填写自己的APPIDapi.registerApp("wxAPPID");//填写自己的APPID，注册本身
                    PayReq req = new PayReq();//PayReq就是订单信息对象
                    req.appId = "wx63410989373f8975";//你的微信appid
                    req.partnerId = wxPayBean.getString("partnerid");//商户号
                    req.prepayId = wxPayBean.getString("prepayid");//预支付交易会话ID
                    req.nonceStr = wxPayBean.getString("noncestr");//随机字符串
                    req.timeStamp = wxPayBean.getString("timestamp");//时间戳
                    req.packageValue = "Sign=WXPay";
                    req.sign = wxPayBean.getString("sign");//签名

                    orderNum = wxPayBean.getString("orderNum");

                    api.sendReq(req);//将订单信息对象发送给微信服务器，即发送支付请求
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShort("支付信息解析异常!");
                }
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                finish();
                break;

            case R.id.activity_live_detail_button1:

                if (UserUtil.isLogin()) {

                    if (!ObjectUtils.isEmpty(liveBean)) {

                        if (StringUtils.equals("0", liveBean.getLoginUserIsReserve())) {
                            //预约操作
                            getDefaultAddress();

                        } else if (StringUtils.equals("1", liveBean.getLoginUserIsReserve())) {
                            //取消预约
                            MyAlertDialog myAlertDialog = new MyAlertDialog(this).builder()
                                    .setTitle("提示！")
                                    .setMsg("确认取消预约吗！")
                                    .setPositiveButton("取消预约", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            cancleClass();
                                        }
                                    }).setNegativeButton("再想想", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                        }
                                    });
                            myAlertDialog.show();
                        }

                    }

                } else {
                    ActivityUtils.startActivity(LoginActivity.class);
                }

                break;
            case R.id.activity_live_detail_button2:

                if (UserUtil.isLogin()) {
                    if (ObjectUtils.isEmpty(liveBean)) {
                        return;
                    }

                    if (isEnd) {
                        if (ObjectUtils.isEmpty(liveBean.getArticleId())) {
                            ToastUtils.showShort("视频暂无回放，敬请期待！");
                            return;
                        }
                        if (isBuy) {

                            Intent intent = new Intent(this, LiveReplayActivity.class);
                            intent.putExtra("url", ServerInfo.IMAGE + liveBean.getVideoUrl());
                            if (StringUtils.isEmpty(liveBean.getVideoLdUrl())) {
                                intent.putExtra("img", ServerInfo.IMAGE + liveBean.getVideoHdUrl());
                            } else {
                                intent.putExtra("img", ServerInfo.IMAGE + liveBean.getVideoLdUrl());
                            }
                            ActivityUtils.startActivity(intent);
                        } else {
                            payVideo();
                        }
                    } else {
                        if (isBuy) {
                            Intent intent = new Intent(this, LiveWebActivity.class);
                            intent.putExtra("channelId", liveBean.getChannelId());
                            intent.putExtra("classId", liveBean.getId());
                            ActivityUtils.startActivity(intent);
                        } else {
                            pay();
                        }
                    }

                } else {
                    ActivityUtils.startActivity(LoginActivity.class);
                }


                break;

        }
    }

    private void payVideo() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("token", SPUtils.getInstance().getString("token"));
        httpParams.put("sign", SPUtils.getInstance().getString("sign"));
        httpParams.put("articleId", liveBean.getArticleId());
        httpParams.put("videoId", liveBean.getVideoId());
        httpParams.put("type", "system_file");
        httpParams.put("tradeType", "APP");
        httpParams.put("from", getResources().getString(R.string.app_android));
        httpParams.put("price", liveBean.getPrice());
        httpParams.put("productType", "video");

        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.CLASSPAY)
                .tag(this)
                .params(httpParams);

        DialogStringCallback stringCallback = new DialogStringCallback(this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("CLASSPAY", response.body());
                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {

                            JSONObject wxPayBean = JSONObject.parseObject(jsonObject.getString("data"));
                            weChatPay(wxPayBean);
                            return;
                        }

                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(LiveDetailActivity.this, request, this);
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

    private void cancleClass() {


        HttpParams httpParams = new HttpParams();

        httpParams.put("sign", SPUtils.getInstance().getString("sign"));
        httpParams.put("token", SPUtils.getInstance().getString("token"));
        httpParams.put("classId", liveBean.getId());

        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.cancleClass)
                .tag(this)
                .params(httpParams);

        DialogStringCallback stringCallback = new DialogStringCallback(this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("cancleClass", response.body());
                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            ToastUtils.showShort("您已取消预约！");
                            liveBean.setLoginUserIsReserve("0");
                            mActivityLiveDetailButton1.setText("预约课程");
                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(LiveDetailActivity.this, request, this);
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

    private String phone;

    private void yuyue() {


        HttpParams httpParams = new HttpParams();

        httpParams.put("sign", SPUtils.getInstance().getString("sign"));
        httpParams.put("token", SPUtils.getInstance().getString("token"));
        httpParams.put("phone", phone);
        httpParams.put("from", getResources().getString(R.string.app_android));
        httpParams.put("classId", liveBean.getId());
        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.yuyueClass)
                .tag(this)
                .params(httpParams);

        DialogStringCallback stringCallback = new DialogStringCallback(this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("yuyueClass", response.body());
                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            ToastUtils.showShort("您已经预约成功！");
                            liveBean.setLoginUserIsReserve("1");
                            mActivityLiveDetailButton1.setText("取消预约");
                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(LiveDetailActivity.this, request, this);
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

    private void pay() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("token", SPUtils.getInstance().getString("token"));
        httpParams.put("sign", SPUtils.getInstance().getString("sign"));
        httpParams.put("classId", liveBean.getId());
        httpParams.put("type", "school_live");
        httpParams.put("productType", "school_live_class");
        httpParams.put("tradeType", "APP");
        httpParams.put("from", getResources().getString(R.string.app_android));
        httpParams.put("price", liveBean.getPrice());

        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.CLASSPAY)
                .tag(this)
                .params(httpParams);

        DialogStringCallback stringCallback = new DialogStringCallback(this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("CLASSPAY", response.body());
                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {

                            JSONObject wxPayBean = JSONObject.parseObject(jsonObject.getString("data"));
                            weChatPay(wxPayBean);
                            return;
                        }

                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(LiveDetailActivity.this, request, this);
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
