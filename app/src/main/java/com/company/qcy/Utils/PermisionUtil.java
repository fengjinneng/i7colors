package com.company.qcy.Utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

public class PermisionUtil {


    @SuppressLint("MissingPermission")
    public static void callKefu(Context context){
        AndPermission.with(context)
                .runtime()
                .permission(Permission.CALL_PHONE)
                .onGranted(permissions -> {
                    // Storage permission are allowed.
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("提示!");
                    builder.setMessage("您是否确认拨打七彩云电商客服电话?");
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PhoneUtils.call(context.getResources().getString(R.string.PHONE));
                        }
                    });
                    builder.show();
                })
                .onDenied(permissions -> {
                    // Storage permission are not allowed.
                    ToastUtils.showShort("权限申请失败,您可能无法使用某些功能");
                })
                .start();
    }

    @SuppressLint("MissingPermission")
    public static void callPhone(Context context,String phoneNumber){
        AndPermission.with(context)
                .runtime()
                .permission(Permission.CALL_PHONE)
                .onGranted(permissions -> {
                    // Storage permission are allowed.
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("提示!");
                    builder.setMessage("您是否确认拨打 "+phoneNumber+" ?");
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PhoneUtils.call(phoneNumber);
                        }
                    });
                    builder.show();
                })
                .onDenied(permissions -> {
                    // Storage permission are not allowed.
                    ToastUtils.showShort("权限申请失败,您可能无法使用某些功能");
                })
                .start();
    }

}
