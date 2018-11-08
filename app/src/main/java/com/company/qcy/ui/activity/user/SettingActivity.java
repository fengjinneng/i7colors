package com.company.qcy.ui.activity.user;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.MainActivity;
import com.company.qcy.R;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

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
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_setting_about_qcy:
                ToastUtils.showShort("关于七彩云");
                break;
            case R.id.activity_setting_clearcache:
                ToastUtils.showShort("清理缓存");
                break;
        }
    }
}
