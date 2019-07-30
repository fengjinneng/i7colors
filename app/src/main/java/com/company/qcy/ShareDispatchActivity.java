package com.company.qcy;

import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.azhon.appupdate.config.UpdateConfiguration;
import com.azhon.appupdate.listener.OnButtonClickListener;
import com.azhon.appupdate.manager.DownloadManager;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;

public class ShareDispatchActivity extends AppCompatActivity implements OnButtonClickListener {

    private DownloadManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_temp);

        Uri data = getIntent().getData();
        //不为空说明是外部网页传过来的
        if (!ObjectUtils.isEmpty(data)) {


            String minVersionCode = data.getQueryParameter("minVersionCode");

            if (Integer.parseInt(minVersionCode) <= AppUtils.getAppVersionCode()) {

                //可以
                String target = data.getQueryParameter("target");
                if (StringUtils.equals("main", target)) {
                    ActivityUtils.startActivity(MainActivity.class);
                }
//                else if(){
//
//                }

                finish();

            } else {

                //需要更新

                UpdateConfiguration configuration = new UpdateConfiguration()
                        //输出错误日志
                        .setEnableLog(true)
                        //设置自定义的下载
                        //.setHttpManager()
                        //下载完成自动跳动安装页面
                        .setJumpInstallPage(true)
                        //设置对话框背景图片 (图片规范参照demo中的示例图)
                        //.setDialogImage(R.drawable.ic_dialog)
                        //设置按钮的颜色
                        .setDialogButtonColor(getResources().getColor(R.color.chunhongse))
                        //设置按钮的文字颜色
                        .setDialogButtonTextColor(Color.WHITE)
                        //支持断点下载
                        .setBreakpointDownload(true)
                        //设置是否显示通知栏进度
                        .setShowNotification(true)

                        //设置对话框按钮的点击监听
                        .setButtonClickListener(this);
                //设置下载过程的监听
//                        .setOnDownloadListener(this);
                //设置强制更新
//                    configuration.setForcedUpgrade(true);
                //不强制更新
                configuration.setForcedUpgrade(false);

                manager = DownloadManager.getInstance(this);
                manager.setApkName("update.apk")
                        .setApkUrl("")
                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setShowNewerToast(true)
                        .setConfiguration(configuration)
//                .setDownloadPath(Environment.getExternalStorageDirectory() + "/AppUpdate")
                        .setApkVersionCode(11)
                        .setApkVersionName("")
//                        .setApkSize("20.4")
                        .setAuthorities(getPackageName() + ".demo.provider")
                        .setApkDescription("")
                        .download();
            }

        } else {
            finish();
        }
    }

    @Override
    public void onButtonClick(int id) {

    }
}
