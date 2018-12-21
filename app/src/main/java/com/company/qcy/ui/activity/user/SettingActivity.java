package com.company.qcy.ui.activity.user;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.icu.util.VersionInfo;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.MainActivity;
import com.company.qcy.R;
import com.company.qcy.base.BaseActivity;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 退出登录
     */
    private ConstraintLayout mActivitySettingLoginout;
    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private ConstraintLayout mActivitySettingAboutQcy;
    private ConstraintLayout mActivitySettingClearcache;
    /**
     * 账户中心
     */
    private TextView mActivitySettingZhanghuzhongxin;
    private TextView mActivitySettingAboutVersioninfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        mActivitySettingLoginout = (ConstraintLayout) findViewById(R.id.activity_setting_loginout);
        mActivitySettingLoginout.setOnClickListener(this);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mActivitySettingAboutQcy = (ConstraintLayout) findViewById(R.id.activity_setting_about_qcy);
        mActivitySettingClearcache = (ConstraintLayout) findViewById(R.id.activity_setting_clearcache);
        mActivitySettingAboutQcy.setOnClickListener(this);
        mActivitySettingClearcache.setOnClickListener(this);
        mToolbarTitle.setText("设置");
        mActivitySettingZhanghuzhongxin = (TextView) findViewById(R.id.activity_setting_zhanghuzhongxin);
        mActivitySettingZhanghuzhongxin.setOnClickListener(this);
        mActivitySettingAboutVersioninfo = (TextView) findViewById(R.id.activity_setting_about_versioninfo);
        mActivitySettingAboutVersioninfo.setText("V"+AppUtils.getAppVersionName());
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
                        SPUtils.getInstance().put("isFirstIn",  "1");
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
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_setting_about_qcy:
                ActivityUtils.startActivity(AboutQCYActivity.class);
                break;
            case R.id.activity_setting_clearcache:
                break;
            case R.id.activity_setting_zhanghuzhongxin:
                ActivityUtils.startActivity(ZhanghaozhongxinActivity.class);
                break;
        }
    }
}
