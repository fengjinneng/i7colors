package com.company.qcy;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.base.WebActivity;
import com.company.qcy.bean.BannerBean;

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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.activity_welcome_text:
                isClick = true;
                timer.cancel();
                finish();
                break;
        }
    }
}
