package com.company.qcy.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.company.qcy.MainActivity;
import com.company.qcy.R;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 退出登录
     */
    private Button mActivitySettingLoginout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        mActivitySettingLoginout = (Button) findViewById(R.id.activity_setting_loginout);
        mActivitySettingLoginout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.activity_setting_loginout:

                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setMessage("确定退出吗？");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SPUtils.getInstance().clear();
                        ActivityUtils.finishAllActivities();
                        ActivityUtils.startActivity(MainActivity.class);
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();




                break;
        }
    }
}
