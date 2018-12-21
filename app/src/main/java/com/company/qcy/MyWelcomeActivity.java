package com.company.qcy;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.base.WebActivity;
import com.company.qcy.bean.BannerBean;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.ui.activity.tuangou.TuangouliebiaoActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyWelcomeActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 进入
     */
    private TextView mActivityWelcomeText;

    private ImageView mActivityWelcomeImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
    }

    private  boolean isClick = false;

    private CountDownTimer timer;

    private void initView() {
        mActivityWelcomeText = (TextView) findViewById(R.id.activity_welcome_text);
        mActivityWelcomeText.setOnClickListener(this);

        timer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                SimpleDateFormat sdf = new SimpleDateFormat("ss");
                mActivityWelcomeText.setText("跳过" + sdf.format(new Date(millisUntilFinished)) + "S");
            }

            @Override
            public void onFinish() {
                if (!isClick) {
                    finish();

                }

            }
        }.start();
        mActivityWelcomeImg = (ImageView) findViewById(R.id.activity_welcome_img);

        GlideUtils.loadImageWithStartPage(this,ServerInfo.IMAGE+SPUtils.getInstance().getString("adv"),mActivityWelcomeImg);
//        addAdvData();
        mActivityWelcomeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!StringUtils.isEmpty(SPUtils.getInstance().getString("advUrl"))&&SPUtils.getInstance().getString("advUrl").length()>4){
                    isClick=true;
                    timer.cancel();
                    Intent intent = new Intent(MyWelcomeActivity.this,WebActivity.class);
                    intent.putExtra("webUrl",SPUtils.getInstance().getString("advUrl"));
                    ActivityUtils.startActivity(intent);
                    finish();
                }
            }
        });

    }

    private boolean isClickAdv;
    List<BannerBean> bannerBeans;

//    private void addAdvData() {
//
//        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.INDEXBANNER)
//                .tag(this)
//                .params("sign", SPUtils.getInstance().getString("sign"))
//                .params("plate_code", "APP_Start_Pic");
//
//
//        StringCallback stringCallback = new StringCallback() {
//            @Override
//            public void onSuccess(Response<String> response) {
//                LogUtils.v("APP_Start_Pic", response.body());
//
//                try {
//                    if (response.code() == 200) {
//                        JSONObject jsonObject = JSONObject.parseObject(response.body());
////                        String msg = jsonObject.getString("msg");
//                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
//                            JSONArray data = jsonObject.getJSONArray("data");
//                            if (ObjectUtils.isEmpty(data)) {
//                                return;
//                            }
//                            bannerBeans = JSONObject.parseArray(data.toJSONString(), BannerBean.class);
//                            GlideUtils.loadImageWithStartPage(MyWelcomeActivity.this,
//                                    ServerInfo.IMAGE + bannerBeans.get(0).getAd_image(), mActivityWelcomeImg);
//                            if (bannerBeans.get(0).getAd_url().length() > 4) {
//                                mActivityWelcomeImg.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        isClickAdv = true;
//                                        timer.cancel();
//                                        finish();
//                                        Intent intent1 = new Intent(MyWelcomeActivity.this, MainActivity.class);
//                                        ActivityUtils.startActivity(intent1);
//
//                                        Intent intent = new Intent(MyWelcomeActivity.this, WebActivity.class);
//                                        intent.putExtra("webUrl", bannerBeans.get(0).getAd_url());
//                                        ActivityUtils.startActivity(intent);
//
//                                    }
//                                });
//                            }
//                            return;
//
//                        }
////                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
////                            SignAndTokenUtil.getSign(MyWelcomeActivity.this,request,this);
////                            return;
////                        }
////                        ToastUtils.showShort(msg);
//
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onError(Response<String> response) {
//                super.onError(response);
////                ToastUtils.showShort(getResources().getString(R.string.NETEXCEPTION));
//            }
//        };
//
//        request.execute(stringCallback);
//
//    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.activity_welcome_text:
                isClick = true;
                ActivityUtils.startActivity(MainActivity.class);
                finish();
                break;
        }
    }
}
