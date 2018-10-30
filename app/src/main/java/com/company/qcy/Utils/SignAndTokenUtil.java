package com.company.qcy.Utils;

import android.app.Activity;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.MainActivity;
import com.company.qcy.R;
import com.company.qcy.ui.activity.qiugoudating.QiugoudatingActivity;
import com.company.qcy.ui.activity.user.LoginActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

public class SignAndTokenUtil {


    //1 为成功
    //2 为签名失效
    //3 为token过期


    private static boolean isToLogin;
    public static void checkSignAndToken(Activity activity,JSONObject jsonObject){

        if (StringUtils.equals(jsonObject.getString("code"), "INVALID_SIGN")) {
            SignAndTokenUtil.getSign(activity);
            return;
        } else if (StringUtils.equals(jsonObject.getString("code"), "NO_LOGIN")) {
            if(isToLogin ){
                ToastUtils.showShort("您的登录身份已过期，请重新登录");
                ActivityUtils.startActivity(LoginActivity.class);
            }
            isToLogin =true;
            return;
        } else ToastUtils.showShort(jsonObject.getString("msg"));

    }

    public static void getSign(Activity activity) {


        String code = new String(EncryptUtils.encryptAES2Base64(String.valueOf(TimeUtils.getNowMills()).getBytes(),
                "jK)Nig8N40YkntYG".getBytes(), "AES/ECB/PKCS5Padding", null));

        LogUtils.v("getsign", code);

        OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.SIGN)
                .tag(activity)
                .params("code", code)
                .execute(new DialogStringCallback(activity) {

                    @Override
                    public void onSuccess(Response<String> response) {

                        try {
                            if (response.code() == 200) {

                                JSONObject jsonObject = JSONObject.parseObject(response.body());
                                if (StringUtils.equals(jsonObject.getString("code"), "SUCCESS")) {
                                    SPUtils.getInstance().put("sign", jsonObject.getString("data"));
                                    ToastUtils.showShort("您的签名已重新获取，请重新点击登录");
                                } else ToastUtils.showShort(jsonObject.getString("msg"));

                            } else {

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });

    }


//    public static void getToken(Activity activity) {
//
//
//        String code = new String(EncryptUtils.encryptAES2Base64(String.valueOf(TimeUtils.getNowMills()).getBytes(),
//                "LnhtI(bt490B74Je".getBytes(), "AES/ECB/PKCS5Padding", null));
//
//        LogUtils.v("getToken", code);
//
//        OkGo.<String>get(ServerInfo.TESTSERVER + InterfaceInfo.SIGN)
//                .tag(activity)
//                .params("code", code)
//                .execute(new DialogStringCallback(activity) {
//
//                    @Override
//                    public void onSuccess(Response<String> response) {
//
//                        try {
//                            if (response.code() == 200) {
//
//                                JSONObject jsonObject = JSONObject.parseObject(response.body());
//                                if (StringUtils.equals(jsonObject.getString("code"), "SUCCESS")) {
//                                    SPUtils.getInstance().put("sign", jsonObject.getString("data"));
//                                    ToastUtils.showShort("您的签名已重新获取，请重新点击登录");
//                                } else ToastUtils.showShort(jsonObject.getString("msg"));
//
//                            } else {
//
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Response<String> response) {
//                        super.onError(response);
//                    }
//                });
//
//    }
}
