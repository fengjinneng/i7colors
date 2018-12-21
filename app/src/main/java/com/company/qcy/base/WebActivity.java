package com.company.qcy.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.company.qcy.R;

public class WebActivity extends AppCompatActivity {

    private WebView mWebview;

    private String webUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webUrl = getIntent().getStringExtra("webUrl");
        initView();
    }

    private void initView() {
        mWebview = (WebView) findViewById(R.id.activity_web_webview);
//        WebSettings webSettings = mWebview.getSettings();
        //支持缩放，默认为true。
//        webSettings.setSupportZoom(true);
        //调整图片至适合webview的大小
//        webSettings.setUseWideViewPort(true);
        // 缩放至屏幕的大小
//        webSettings.setLoadWithOverviewMode(true);
        //设置默认编码
//        webSettings.setDefaultTextEncodingName("utf-8");
        //设置自动加载图片
//        mWebview.getSettings().setJavaScriptEnabled(true);
//        webSettings.setLoadsImagesAutomatically(true);
//        mWebview.getSettings().setBlockNetworkImage(false);


//        mWebview.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
//                //view.loadUrl(url);  // webview加载html,a标签超链接禁止跳转
//                return true;
//            }
//
//
//        });
//        mWebview.loadUrl(webUrl);
        mWebview.setWebChromeClient(new WebChromeClient());
        mWebview.setWebViewClient(new WebViewClient());
        mWebview.getSettings().setLoadWithOverviewMode(true);
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.loadUrl(webUrl);
    }
}
