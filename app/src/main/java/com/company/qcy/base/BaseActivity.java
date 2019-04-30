package com.company.qcy.base;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.StringUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.NetworkUtil;
import com.company.qcy.bean.eventbus.MessageBean;
import com.githang.statusbar.StatusBarCompat;
import com.lzy.okgo.OkGo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class BaseActivity extends AppCompatActivity {

    public Context context;
    public Activity activity;
    private AlertDialog dialog;
    AlertDialog.Builder builder = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //锁定屏幕。禁止旋转屏幕
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        EventBus.getDefault().register(this);
        activity = this;
        context = getApplicationContext();
        if(android.os.Build.VERSION.SDK_INT>19){
            StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.baise), true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReciveMessage(MessageBean msg) {

    }

    /**
     * 判断网络
     */
    protected void isNetWork() {
        int status = NetworkUtil.getNetworkType(activity);

        LogUtils.e("base--status--" + status);
        if (0 == status) {
            showPop();

        } else {
            if (null != dialog && dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    /**
     * 显示无网弹框
     *
     * @param
     */
    private void showPop() {
        if (builder == null) {

            builder = new AlertDialog.Builder(this);
            View view = View.inflate(activity, R.layout.toast_newwork_setting_layout, null);
            view.setPadding(10, 0, 10, 200);
            builder.setView(view);
            builder.setCancelable(true);//点击返回是否取消
            LinearLayout rlParent = view.findViewById(R.id.rl_parent);//


            rlParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (dialog.isShowing()) {
//                    dialog.dismiss();
                        NetworkUtil.toSetting(activity);
                    }
                }
            });
        }

        //取消或确定按钮监听事件处理
        if (dialog == null) {
            dialog = builder.create();
            dialog.setCanceledOnTouchOutside(true);
            dialog.setCanceledOnTouchOutside(true);
            Window window = dialog.getWindow();
            window.setDimAmount(0);//设置昏暗度为0
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setGravity(Gravity.BOTTOM);

        }

        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        OkGo.getInstance().cancelTag(this);

    }


    public static void toSetting(Context context) {
        Intent intent;
        if (android.os.Build.MANUFACTURER.equals("Meizu")) {
            intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        } else if (android.os.Build.VERSION.SDK_INT > 10) {
            intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        } else {
            intent = new Intent();
            ComponentName component = new ComponentName(
                    "com.android.settings",
                    "com.android.settings.WirelessSettings");
            intent.setComponent(component);
            intent.setAction("android.intent.action.VIEW");
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }
}
