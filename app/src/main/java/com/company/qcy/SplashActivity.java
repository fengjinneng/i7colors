package com.company.qcy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(StringUtils.isEmpty(SPUtils.getInstance().getString("isFirstIn"))){
            ActivityUtils.startActivity(GuideActivity.class);
            finish();
        }else {
            ActivityUtils.startActivity(MainActivity.class);
            finish();
        }

    }
}
