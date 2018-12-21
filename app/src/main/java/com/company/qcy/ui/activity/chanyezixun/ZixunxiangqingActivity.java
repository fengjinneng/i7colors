package com.company.qcy.ui.activity.chanyezixun;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.chanyezixun.NewsBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

public class ZixunxiangqingActivity extends BaseActivity implements View.OnClickListener {


    private Long id;//资讯的ID
    private WebView mWebview;
    /**
     * 七彩云应邀参加孟...
     */
    private TextView mActivityZixunxiangqingPrevTitle;
    private ConstraintLayout mActivityZixunxiangqingPrev;
    /**
     * 关于召开中国印染…
     */
    private TextView mActivityZixunxiangqingNextTitle;
    private ConstraintLayout mActivityZixunxiangqingNext;
    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    /**
     * 设置
     */
    private TextView mToolbarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zixunxiangqing);
        id = getIntent().getLongExtra("id", 0);
        initView();
        addData(id);

        WebSettings webSettings = mWebview.getSettings();
        //支持缩放，默认为true。
//        webSettings.setSupportZoom(true);
        //调整图片至适合webview的大小
//        webSettings.setUseWideViewPort(true);
        // 缩放至屏幕的大小
        webSettings.setLoadWithOverviewMode(true);
        //设置默认编码
//        webSettings.setDefaultTextEncodingName("utf-8");
        //设置自动加载图片
//        webSettings.setLoadsImagesAutomatically(true);
        mWebview.getSettings().setBlockNetworkImage(false);


        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                //view.loadUrl(url);  // webview加载html,a标签超链接禁止跳转
                return true;
            }


        });
    }

    private NewsBean newsBean;
    private NewsBean nextBean;
    private NewsBean prevBean;

    private boolean haveNext = true;
    private boolean havePrev = true;

    private void addData(Long id) {
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.INFORMATIONDETAIL)
                .tag(this)

                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("id", id);


        DialogStringCallback stringCallback = new DialogStringCallback(this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("INFORMATIONDETAIL", response.body());
                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            JSONObject infoDetail = data.getJSONObject("infoDetail");
                            JSONObject next = data.getJSONObject("next");
                            JSONObject prev = data.getJSONObject("prev");

                            newsBean = infoDetail.toJavaObject(NewsBean.class);

                            if (ObjectUtils.isEmpty(next)) {
                                haveNext = false;
                                mActivityZixunxiangqingNextTitle.setText("没有下一篇了");
                            } else {
                                haveNext = true;
                                nextBean = next.toJavaObject(NewsBean.class);
                                if (nextBean.getTitle().length() >= 10) {
                                    mActivityZixunxiangqingNextTitle.setText(new String(nextBean.getTitle()).substring(0, 9) + "...");
                                } else {
                                    mActivityZixunxiangqingNextTitle.setText(nextBean.getTitle());
                                }
                            }

                            if (ObjectUtils.isEmpty(prev)) {
                                havePrev = false;
                                mActivityZixunxiangqingNextTitle.setText("没有上一篇了");
                            } else {
                                havePrev = true;
                                prevBean = prev.toJavaObject(NewsBean.class);
                                if (prevBean.getTitle().length() >= 10) {
                                    mActivityZixunxiangqingPrevTitle.setText(new String(prevBean.getTitle()).substring(0, 9) + "...");

                                } else {
                                    mActivityZixunxiangqingPrevTitle.setText(prevBean.getTitle());

                                }
                            }
                            mToolbarTitle.setText(newsBean.getTitle());


                            mWebview.loadData(newsBean.getContent(), "text/html;charset=UTF-8", null);
                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(ZixunxiangqingActivity.this,request,this);
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


    private void initView() {
        mWebview = (WebView) findViewById(R.id.webview);
        mActivityZixunxiangqingPrevTitle = (TextView) findViewById(R.id.activity_zixunxiangqing_prev_title);
        mActivityZixunxiangqingPrev = (ConstraintLayout) findViewById(R.id.activity_zixunxiangqing_prev);
        mActivityZixunxiangqingNextTitle = (TextView) findViewById(R.id.activity_zixunxiangqing_next_title);
        mActivityZixunxiangqingNext = (ConstraintLayout) findViewById(R.id.activity_zixunxiangqing_next);
        mActivityZixunxiangqingPrev.setOnClickListener(this);
        mActivityZixunxiangqingNext.setOnClickListener(this);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mToolbarTitle.setText("资讯详情");
        mToolbarText = (TextView) findViewById(R.id.toolbar_text);
        mToolbarText.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.activity_zixunxiangqing_next:
                if (!haveNext) {
                    return;
                }
                addData(nextBean.getId());
                break;

            case R.id.activity_zixunxiangqing_prev:
                if (!havePrev) {
                    return;
                }
                addData(prevBean.getId());
                break;
            case R.id.toolbar_back:
                finish();
                break;
        }
    }
}
