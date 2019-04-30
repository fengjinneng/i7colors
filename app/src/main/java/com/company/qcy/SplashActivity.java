package com.company.qcy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.eventbus.MessageBean;

import me.leolin.shortcutbadger.ShortcutBadger;

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
