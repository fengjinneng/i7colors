package com.company.qcy.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.company.qcy.R;

public class WebNoBottomActivity extends BaseActivity implements View.OnClickListener {

    private WebView mWebview;

    private String webUrl;
    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private ProgressBar mActivityWebProgressbar;
    /**
     * 阅读并接受
     */
    private TextView mActivityWebNoBottomTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_no_bottom);
        webUrl = getIntent().getStringExtra("webUrl");
        initView();
    }


    private void initView() {
        mWebview = (WebView) findViewById(R.id.activity_web_webview);

        mWebview.setWebChromeClient(new WebChromeClient());
        mWebview.setWebViewClient(new WebViewClient());
        mWebview.getSettings().setLoadWithOverviewMode(true);
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.loadUrl(webUrl);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mActivityWebProgressbar = (ProgressBar) findViewById(R.id.activity_web_progressbar);
        mActivityWebNoBottomTv = (TextView) findViewById(R.id.activity_web_no_bottom_tv);
        mActivityWebNoBottomTv.setOnClickListener(this);

        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mToolbarTitle.setText(view.getTitle());
                mActivityWebProgressbar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:

                finish();
                break;

            case R.id.activity_web_no_bottom_tv:
                finish();

                break;
        }
    }

}
