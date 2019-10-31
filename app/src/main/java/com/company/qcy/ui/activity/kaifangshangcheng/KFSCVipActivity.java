package com.company.qcy.ui.activity.kaifangshangcheng;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.PermisionUtil;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.ui.activity.qiugoudating.QiugouxiangqingActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

public class KFSCVipActivity extends BaseActivity implements View.OnClickListener {

    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private WebView webView;
    /**
     * 15201937838
     */
    private TextView mActivityKfscvipCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kfscvip);
        initView();
    }

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        webView = (WebView) findViewById(R.id.activity_kfscvip_webview);
        mActivityKfscvipCall = (TextView) findViewById(R.id.activity_kfscvip_call);
        mActivityKfscvipCall.setOnClickListener(this);
        mToolbarTitle.setText("开放商城会员服务");
//        Glide.with(this).load("http://static.i7colors.com/VIP.jpg").into(mActivityKfscvipImg);

        webView.loadUrl("http://static.i7colors.com/VIP.jpg");

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        //设置图片不超出页面的范围
        webView.getSettings().setUseWideViewPort(true);//关键点
        webView.getSettings().setLoadWithOverviewMode(true);

        update();
    }


    //统计求购大厅跳转付费广告
    private void update() {
        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.TOFEEADRECORD)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("from",getResources().getString(R.string.app_android))
                .params("token", SPUtils.getInstance().getString("token"));

        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {

            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
            }
        };

        request.execute(stringCallback);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_kfscvip_call:
                PermisionUtil.callPhone(this, "15201937838");
                break;
        }
    }
}
