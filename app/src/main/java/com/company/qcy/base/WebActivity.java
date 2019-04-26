package com.company.qcy.base;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.qcy.R;

public class WebActivity extends BaseActivity implements View.OnClickListener {

    private WebView mWebview;

    private String webUrl;
    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private ImageView mActivityWebBack;
    private ImageView mActivityWebForward;
    private ImageView mActivityWebShuaxin;

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
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mToolbarTitle.setText("");

//        mWebview.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//
//                if (event.getAction() == KeyEvent.ACTION_DOWN) {
//                    if (keyCode == KeyEvent.KEYCODE_BACK && mWebview.canGoBack()) {
//                        mWebview.goBack();
//                        return true;
//                    }
//                }
//
//                    return false;
//            }
//        });


        mActivityWebBack = (ImageView) findViewById(R.id.activity_web_back);
        mActivityWebBack.setOnClickListener(this);
        mActivityWebForward = (ImageView) findViewById(R.id.activity_web_forward);
        mActivityWebForward.setOnClickListener(this);
        mActivityWebShuaxin = (ImageView) findViewById(R.id.activity_web_shuaxin);
        mActivityWebShuaxin.setOnClickListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (mWebview.canGoBack()) {
                mWebview.goBack(); //goBack()表示返回WebView的上一页面
                return true;
            } else {
                finish();
                return true;
            }

        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                if (mWebview.canGoBack()) {
                    mWebview.goBack(); //goBack()表示返回WebView的上一页面
                } else {
                    finish();
                }
                break;
            case R.id.activity_web_back:

                if (mWebview.canGoBack()) {
                    mWebview.goBack();
                }

                break;
            case R.id.activity_web_forward:
                if (mWebview.canGoForward()) {
                    mWebview.goForward();
                }
                break;
            case R.id.activity_web_shuaxin:
                mWebview.reload();
                break;
        }
    }
}
