package com.company.qcy.Utils;

import android.app.Activity;
import android.content.Context;

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
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.ui.activity.qiugoudating.QiugoudatingActivity;
import com.company.qcy.ui.activity.user.LoginActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.greenrobot.eventbus.EventBus;

public class SignAndTokenUtil {


    //1 为成功
    //2 为签名失效
    //3 为token过期

    public static void getSign(Context context, Request request, StringCallback callback) {
        String code = new String(EncryptUtils.encryptAES2Base64(String.valueOf(TimeUtils.getNowMills()).getBytes(),
                "jK)Nig8N40YkntYG".getBytes(), "AES/ECB/PKCS5Padding", null));
        LogUtils.v("getsign", code);
        OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.SIGN)
                .tag(context)
                .params("code", code)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            if (response.code() == 200) {
                                JSONObject jsonObject = JSONObject.parseObject(response.body());
                                if (StringUtils.equals(jsonObject.getString("code"), "SUCCESS")) {
                                    SPUtils.getInstance().put("sign", jsonObject.getString("data"));
                                    request.params("sign",jsonObject.getString("data"));
                                    request.execute(callback);
                                }
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

}
