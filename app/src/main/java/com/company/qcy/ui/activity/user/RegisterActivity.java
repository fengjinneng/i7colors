package com.company.qcy.ui.activity.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogBitmapcallback;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.base.WebActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    /**
     * 设置
     */
    private TextView mToolbarText;
    private RelativeLayout mToolbarLayout;
    /**
     * 请输入手机号(用于登陆/找回密码)
     */
    private EditText mActivityRegisterPhone;
    /**
     * 输入图片验证码
     */
//    private EditText mActivityRegisterVerifycode;
//    private ImageView mActivityRegisterVerifycodeImg;
    /**
     * 换一张
     */
//    private TextView mActivityRegisterChange;
//    private LinearLayout mActivityRegisterLinnear;
    /**
     * 输入短信验证码
     */
    private EditText mActivityRegisterSms;
    /**
     * 发送验证码
     */
    private TextView mActivityRegisterSendsms;
    private LinearLayout mActivityRegisterLinnear2;
    /**
     * 请输入密码
     */
    private EditText mActivityRegisterPassword1;
    /**
     * 请再次输入密码
     */
    private EditText mActivityRegisterPassword2;
    /**
     * 注册
     */
    private Button mActivityRegisterSubmit;
    /**
     * 《七彩云协议》
     */
    private TextView mActivityRegisterXieyi;
    /**
     * 输入邀请码(非必填)
     */
    private EditText mActivityRegisterYaoqingma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        mActivityRegisterYaoqingma = (EditText) findViewById(R.id.activity_register_yaoqingma);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarText = (TextView) findViewById(R.id.toolbar_text);
        mToolbarLayout = (RelativeLayout) findViewById(R.id.toolbar_layout);
        mActivityRegisterPhone = (EditText) findViewById(R.id.activity_register_phone);
//        mActivityRegisterVerifycode = (EditText) findViewById(R.id.activity_register_verifycode);
//        mActivityRegisterVerifycodeImg = (ImageView) findViewById(R.id.activity_register_verifycode_img);
//        mActivityRegisterChange = (TextView) findViewById(R.id.activity_register_change);
//        mActivityRegisterLinnear = (LinearLayout) findViewById(R.id.activity_register_linnear);
        mActivityRegisterSms = (EditText) findViewById(R.id.activity_register_sms);
        mActivityRegisterSendsms = (TextView) findViewById(R.id.activity_register_sendsms);
        mActivityRegisterLinnear2 = (LinearLayout) findViewById(R.id.activity_register_linnear2);
        mActivityRegisterPassword1 = (EditText) findViewById(R.id.activity_register_password_1);
        mActivityRegisterPassword2 = (EditText) findViewById(R.id.activity_register_password_2);
        mActivityRegisterSubmit = (Button) findViewById(R.id.activity_register_submit);
        mActivityRegisterSubmit.setOnClickListener(this);
        mActivityRegisterXieyi = (TextView) findViewById(R.id.activity_register_xieyi);
        mActivityRegisterSendsms.setOnClickListener(this);
//        mActivityRegisterChange.setOnClickListener(this);
//        getCaptcha();
        mToolbarBack.setOnClickListener(this);
        mToolbarTitle.setText("欢迎注册");
        mActivityRegisterXieyi.setOnClickListener(this);
    }

//    private void getCaptcha() {
//
//        OkGo.<Bitmap>get(ServerInfo.SERVER + InterfaceInfo.CAPTCHA)
//                .tag(this)
//                .params("deviceNo", DeviceUtils.getAndroidID())
//                .execute(new DialogBitmapcallback(this) {
//
//                    @Override
//                    public void onSuccess(Response<Bitmap> response) {
//                        try {
//                            if (response.code() == 200) {
//                                mActivityRegisterVerifycodeImg.setImageBitmap(response.body());
//                            } else {
//                                ToastUtils.showShort("获取图片验证码失败！");
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Response<Bitmap> response) {
//                        super.onError(response);
//                    }
//                });
//
//    }


    private CountDownTimer timer;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.activity_register_submit:

                if (StringUtils.isEmpty(mActivityRegisterPhone.getText().toString()) || !RegexUtils.isMobileSimple(mActivityRegisterPhone.getText().toString())) {
                    ToastUtils.showShort("请填写正确的手机号码");
                    return;

                }  else if (StringUtils.length(mActivityRegisterSms.getText().toString().trim()) != 6) {
                    ToastUtils.showShort("请填写正确的短信验证码");
                    return;
                } else if (StringUtils.isEmpty(mActivityRegisterPassword1.getText().toString())) {
                    ToastUtils.showShort("请填写密码");
                    return;
                } else if (StringUtils.isEmpty(mActivityRegisterPassword2.getText().toString())) {
                    ToastUtils.showShort("请再次填写密码");
                    return;
                } else if (!StringUtils.equals(mActivityRegisterPassword1.getText().toString().trim(), mActivityRegisterPassword2.getText().toString().trim())) {
                    ToastUtils.showShort("两次输入的密码不同");
                    return;
                }

                PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.REGISTER)
                        .tag(this)
                        .params("inviteCode",mActivityRegisterYaoqingma.getText().toString())
                        .params("smsCode", mActivityRegisterSms.getText().toString().trim())
                        .params("sign", SPUtils.getInstance().getString("sign"))
                        .params("phone", mActivityRegisterPhone.getText().toString().trim())
                        .params("from",getResources().getString(R.string.app_android))
                        .params("password", new String(EncryptUtils.encryptAES2Base64(mActivityRegisterPassword1.getText().toString().trim().getBytes(),
                                "LnhtI(bt490B74Je".getBytes(), "AES/ECB/PKCS5Padding", null)));


                DialogStringCallback stringCallback = new DialogStringCallback(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LogUtils.e("REGISTER", response.body());

                        try {
                            if (response.code() == 200) {
                                JSONObject jsonObject = JSONObject.parseObject(response.body());
                                String msg = jsonObject.getString("msg");
                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                    finish();
                                    return;

                                }
                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                                    SignAndTokenUtil.getSign(RegisterActivity.this, request, this);
                                    return;
                                }
                                ToastUtils.showShort(msg);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastUtils.showShort(getResources().getString(R.string.NETEXCEPTION));

                    }
                };

                request.execute(stringCallback);


                break;
            case R.id.activity_register_sendsms:
                if (!RegexUtils.isMobileSimple(mActivityRegisterPhone.getText().toString().trim())) {
                    ToastUtils.showShort("请输入正确的手机号！");
                    return;
                }
//                if (mActivityRegisterVerifycode.getText().toString().trim().length() != 4) {
//                    ToastUtils.showShort("请输入正确的图片验证码！");
//                    return;
//                }

                PostRequest<String> stringPostRequest = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.SENDSMSREGISTER)
                        .tag(this)
                        .params("deviceNo", DeviceUtils.getAndroidID())
                        .params("sign", SPUtils.getInstance().getString("sign"))
                        .params("mobile", mActivityRegisterPhone.getText().toString().trim());


                DialogStringCallback dialogStringCallback = new DialogStringCallback(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            LogUtils.v("SENDSMS", response.body());

                            if (response.code() == 200) {

                                JSONObject jsonObject = JSONObject.parseObject(response.body());
                                String msg = jsonObject.getString("msg");
                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                    String s = jsonObject.getString("data");
                                    if (StringUtils.equals("true", s)) {
                                        ToastUtils.showShort(msg);
                                        timer = new CountDownTimer(59000, 1000) {
                                            @Override
                                            public void onTick(long millisUntilFinished) {
                                                SimpleDateFormat sdf = new SimpleDateFormat("ss");
                                                mActivityRegisterSendsms.setText(sdf.format(new Date(millisUntilFinished)) + "S");
                                                mActivityRegisterSendsms.setEnabled(false);
                                            }

                                            @Override
                                            public void onFinish() {
                                                mActivityRegisterSendsms.setText("重新获取验证码");
                                                mActivityRegisterSendsms.setEnabled(true);
                                            }
                                        }.start();

                                    } else {
                                        ToastUtils.showShort(msg);
                                    }
                                    return;

                                }
                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                                    SignAndTokenUtil.getSign(RegisterActivity.this, stringPostRequest, this);
                                    return;
                                }
                                ToastUtils.showShort(msg);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastUtils.showShort(getResources().getString(R.string.NETEXCEPTION));
                    }
                };

                stringPostRequest.execute(dialogStringCallback);

                break;

//            case R.id.activity_register_change:
//                getCaptcha();
//                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_register_xieyi:
                Intent intent = new Intent(this, WebActivity.class);
                intent.putExtra("webUrl", "http://mobile.i7colors.com/groupBuyMobile/secret.html");
                ActivityUtils.startActivity(intent);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
