package com.company.qcy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.umeng.commonsdk.statistics.common.DeviceConfig;


public class SplashActivity extends AppCompatActivity {

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        String[] testDeviceInfo = getTestDeviceInfo(this);
//        LogUtils.e("zzxzvcxbfdsfds",testDeviceInfo[0]);
//        LogUtils.e("zzxzvcxbfdsfds",testDeviceInfo[1]);

        if(StringUtils.isEmpty(SPUtils.getInstance().getString("isFirstIn"))){
            ActivityUtils.startActivity(GuideActivity.class);
            finish();
        }else {
            ActivityUtils.startActivity(MainActivity.class);
            finish();
        }
    }


    public static String[] getTestDeviceInfo(Context context){
        String[] deviceInfo = new String[2];
        try {
            if(context != null){
                deviceInfo[0] = DeviceConfig.getDeviceIdForGeneral(context);
                deviceInfo[1] = DeviceConfig.getMac(context);
            }
        } catch (Exception e){
        }
        return deviceInfo;
    }


}
