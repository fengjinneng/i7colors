package com.company.qcy.live;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.company.qcy.R;
import com.company.qcy.base.BaseActivity;

public class LiveWebActivity extends BaseActivity implements View.OnClickListener {

    private WebView mWebview;

    private String channelId;
    private Long classId;
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private ProgressBar mActivityLiveWebProgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_web);

        channelId = getIntent().getStringExtra("channelId");

        classId = getIntent().getLongExtra("classId", 0);

        initView();

    }

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mWebview = (WebView) findViewById(R.id.activity_live_web_webView);
        mActivityLiveWebProgressbar = (ProgressBar) findViewById(R.id.activity_live_web_progressbar);


        // 这行代码一定加上否则效果不会出现
        mWebview.getSettings().setJavaScriptEnabled(true);

        mWebview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        mWebview.loadUrl("https://mobile.i7colors.com/groupBuyMobile/courseLive/index.html?isLogin=1&token=" +
                SPUtils.getInstance().

                        getString("token") + "&channelId=" + channelId + "&id=" + classId + "&from=" + "app");

        mToolbarBack.setOnClickListener(this);

        WebChromeClient wvcc = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                mToolbarTitle.setText(title);
                mActivityLiveWebProgressbar.setVisibility(View.GONE);
            }
        };

        mWebview.setWebChromeClient(wvcc);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                finish();
                break;
        }
    }
}
