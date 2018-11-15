package com.company.qcy.ui.activity.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.DeviceUtils;
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
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 请输入手机号（找回密码）
     */
    private EditText mForgetpasswordPhonenumber;
    /**
     * 请输入验证码
     */
    private EditText mForgetpasswordVerifycode;
    /**
     * 下一步
     */
    private Button mForgetpasswordNextbutton;
    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    /**
     * 输入图片验证码
     */
    private EditText mForgetpasswordCapcha;
    private ImageView mForgetpasswordCapchaImg;
    /**
     * 换一张
     */
    private TextView mForgetpasswordCapchaChange;
    /**
     * 发送验证码
     */
    private TextView mForgetpasswordSendsms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initView();
    }

    private void initView() {
        mForgetpasswordPhonenumber = (EditText) findViewById(R.id.forgetpassword_phonenumber);
        mForgetpasswordVerifycode = (EditText) findViewById(R.id.forgetpassword_verifycode);
        mForgetpasswordNextbutton = (Button) findViewById(R.id.forgetpassword_nextbutton);
        mForgetpasswordNextbutton.setOnClickListener(this);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mToolbarTitle.setText("修改密码");
        mForgetpasswordCapcha = (EditText) findViewById(R.id.forgetpassword_capcha);
        mForgetpasswordCapchaImg = (ImageView) findViewById(R.id.forgetpassword_capcha_img);
        mForgetpasswordCapchaChange = (TextView) findViewById(R.id.forgetpassword_capcha_change);
        mForgetpasswordCapchaChange.setOnClickListener(this);
        mForgetpasswordSendsms = (TextView) findViewById(R.id.forgetpassword_sendsms);
        mForgetpasswordSendsms.setOnClickListener(this);
        getCaptcha();
    }

    private CountDownTimer timer;


    private void getCaptcha() {

        OkGo.<Bitmap>get(ServerInfo.SERVER + InterfaceInfo.CAPTCHA)
                .tag(this)
                .params("deviceNo", DeviceUtils.getAndroidID())
                .execute(new DialogBitmapcallback(this) {

                    @Override
                    public void onSuccess(Response<Bitmap> response) {
                        try {
                            mForgetpasswordCapchaImg.setImageBitmap(response.body());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<Bitmap> response) {
                        super.onError(response);
                    }
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.forgetpassword_nextbutton:
                if (StringUtils.isEmpty(mForgetpasswordPhonenumber.getText().toString()) || !RegexUtils.isMobileSimple(mForgetpasswordPhonenumber.getText().toString())) {
                    ToastUtils.showShort("请填写正确的手机号码");
                    return;

                } else if (StringUtils.length(mForgetpasswordCapcha.getText().toString().trim()) != 4) {
                    ToastUtils.showShort("请填写正确的图片验证码");
                    return;
                }else if(mForgetpasswordVerifycode.getText().toString().length()!=6){
                    ToastUtils.showShort("请填写正确的短信验证码");
                    return;
                }
                OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.CHECKSMSCODE)
                        .tag(this)
                        .params("sign", SPUtils.getInstance().getString("sign"))
                        .params("mobile", mForgetpasswordPhonenumber.getText().toString().trim())
                        .params("smsCode", mForgetpasswordVerifycode.getText().toString().trim())
                        .execute(new DialogStringCallback(this) {

                            @Override
                            public void onSuccess(Response<String> response) {
                                try {
                                    if (response.code() == 200) {
                                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                                        String msg = jsonObject.getString("msg");
                                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                            String s = jsonObject.getString("data");
                                            if (StringUtils.equals("true", s)) {

                                                Intent intent = new Intent(ForgetPasswordActivity.this,ResetPasswordActivity.class);
                                                intent.putExtra("phone",mForgetpasswordPhonenumber.getText().toString());
                                                ActivityUtils.startActivity(intent);
                                                finish();
                                            } else {
                                                ToastUtils.showShort(msg);
                                            }
                                        } else
                                            ToastUtils.showShort(msg);
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
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.forgetpassword_capcha_change:
                getCaptcha();

                break;
            case R.id.forgetpassword_sendsms:
                if (StringUtils.isEmpty(mForgetpasswordPhonenumber.getText().toString()) || !RegexUtils.isMobileSimple(mForgetpasswordPhonenumber.getText().toString())) {
                    ToastUtils.showShort("请填写正确的手机号码");
                    return;

                } else if (StringUtils.length(mForgetpasswordCapcha.getText().toString().trim()) != 4) {
                    ToastUtils.showShort("请填写正确的图片验证码");
                    return;
                }

                OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.SENDSMSCHECKPASSWORD)
                        .tag(this)
                        .params("deviceNo", DeviceUtils.getAndroidID())
                        .params("sign", SPUtils.getInstance().getString("sign"))
                        .params("mobile", mForgetpasswordPhonenumber.getText().toString().trim())
                        .params("captcha", mForgetpasswordCapcha.getText().toString().trim())
                        .execute(new DialogStringCallback(this) {
                            @Override
                            public void onSuccess(Response<String> response) {
                                try {
                                    LogUtils.v("SENDSMS", response.body());

                                    if (response.code() == 200) {

                                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                            String s = jsonObject.getString("data");
                                            if (StringUtils.equals("true", s)) {
                                                ToastUtils.showShort("短信获取成功！");

                                                timer = new CountDownTimer(59000, 1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        SimpleDateFormat sdf = new SimpleDateFormat("ss");
                                                        mForgetpasswordSendsms.setText(sdf.format(new Date(millisUntilFinished)) + "S");
                                                        mForgetpasswordSendsms.setEnabled(false);
                                                    }

                                                    @Override
                                                    public void onFinish() {
                                                        mForgetpasswordSendsms.setText("重新获取验证码");
                                                        mForgetpasswordSendsms.setEnabled(true);
                                                    }
                                                }.start();

                                            } else {
                                                ToastUtils.showShort("短信获取失败！");
                                            }
                                            return;

                                        } else
                                            SignAndTokenUtil.checkSignAndToken(ForgetPasswordActivity.this, jsonObject);

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

                break;
        }
    }
}
