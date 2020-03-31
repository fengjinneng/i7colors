package com.company.qcy.live;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.base.BaseActivity;
import com.lzy.okgo.OkGo;
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
    private WebView mActivityLiveDetailWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_detail);
        initView();
    }

    private void initView() {

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

        mToolbarTitle.setText("直播");

        id = getIntent().getLongExtra("id", 0);

        addData();
        mActivityLiveDetailWebView = (WebView) findViewById(R.id.activity_live_detail_webView);
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

    private void setInfo() {

        if (ObjectUtils.isEmpty(liveBean)) {
            return;
        }

        GlideUtils.loadImageRct(this, ServerInfo.IMAGE + liveBean.getBanner(), mActivityLiveDetailImg);

        mActivityLiveDetailTitle.setText(liveBean.getTitle());
        mActivityLiveDetailTeacher.setText(liveBean.getTeacher());


        mActivityLiveDetailWebView.loadData(liveBean.getDescription(), "text/html;charset=UTF-8", null);

        //直播未开始
        if (StringUtils.equals("2", liveBean.getIsEnd())) {
            mActivityLiveDetailTime.setText("直播开始时间:" + liveBean.getStartTime());
            mActivityLiveDetailCountdownView.start(Long.parseLong(liveBean.getStartTimeStamp()) - TimeUtils.getNowMills());

            if (StringUtils.equals(liveBean.getLoginUserIsBuy(), "0")) {
                mActivityLiveDetailButton2.setText("￥" + liveBean.getPrice() + " 购买课程");
            } else if (StringUtils.equals(liveBean.getLoginUserIsBuy(), "1")) {
                mActivityLiveDetailButton2.setText("已购买，直播未开始！");
                isBuy = true;
            }

        } else if (StringUtils.equals("0", liveBean.getIsEnd())) {
            //已结束
            mActivityLiveDetailTime.setText("直播已结束！");

            if (StringUtils.equals(liveBean.getLoginUserIsBuy(), "0")) {
                mActivityLiveDetailButton2.setText("￥" + liveBean.getPrice() + " 购买课程");
            } else if (StringUtils.equals(liveBean.getLoginUserIsBuy(), "1")) {
                mActivityLiveDetailButton2.setText("查看回放");
                isBuy = true;
            }

        } else if (StringUtils.equals("1", liveBean.getIsEnd())) {
            //进行中
            mActivityLiveDetailTime.setText("正在直播中！");

            if (StringUtils.equals(liveBean.getLoginUserIsBuy(), "0")) {
                mActivityLiveDetailButton2.setText("￥" + liveBean.getPrice() + " 购买课程");
            } else if (StringUtils.equals(liveBean.getLoginUserIsBuy(), "1")) {
                mActivityLiveDetailButton2.setText("观看直播");
                isBuy = true;
            }
        }


    }


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
                    api.sendReq(req);//将订单信息对象发送给微信服务器，即发送支付请求
                }catch (Exception e){
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
                break;
            case R.id.activity_live_detail_button2:
                if (ObjectUtils.isEmpty(liveBean)) {
                    return;
                }
                if (isBuy) {

                } else {
                    pay();
                }
                break;

        }
    }

    private void pay() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("token", SPUtils.getInstance().getString("token"));
        httpParams.put("classId", liveBean.getId());
        httpParams.put("type", "school_live");
        httpParams.put("productType", "school_live_class");
        httpParams.put("tradeType","APP");
        httpParams.put("from",getResources().getString(R.string.app_android));
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

                           JSONObject wxPayBean =  JSONObject.parseObject(jsonObject.getString("data"));
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
