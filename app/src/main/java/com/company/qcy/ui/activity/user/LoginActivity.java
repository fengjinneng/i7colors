package com.company.qcy.ui.activity.user;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogBitmapcallback;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.bean.user.UserBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    /**
     * 请输入用户名
     */
    private EditText mLoginUsername;
    /**
     * 请输入密码
     */
    private EditText mLoginPassword;
    /**
     * 请输入验证码
     */
    private EditText mLoginVerifycode;
    private ImageView mLoginVerifycodeImg;
    /**
     * 换一张
     */
    private TextView mLoginChangeImg;
    /**
     * 获取验证码
     */
    private TextView mLoginHuoquyanzhengma;
    /**
     * 登录
     */
    private Button mLoginDenglu;
    /**
     * 立即注册
     */
    private TextView mLoginRegister;
    /**
     * 忘记密码？
     */
    private TextView mLoginForgetpassword;
    private ImageView mLoginWeixin;
    private ImageView mActivityLoginDeleteUsername;
    private ImageView mActivityLoginDeletePassword;
    private ImageView mActivityLoginShowPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

    }

    private void initView() {
        mLoginUsername = (EditText) findViewById(R.id.login_username);
        mLoginPassword = (EditText) findViewById(R.id.login_password);
        mLoginVerifycode = (EditText) findViewById(R.id.login_verifycode);
        mLoginVerifycodeImg = (ImageView) findViewById(R.id.login_verifycode_img);
        mLoginChangeImg = (TextView) findViewById(R.id.login_change_img);
        mLoginChangeImg.setOnClickListener(this);
        mLoginHuoquyanzhengma = (TextView) findViewById(R.id.login_huoquyanzhengma);
        mLoginHuoquyanzhengma.setOnClickListener(this);
        mLoginDenglu = (Button) findViewById(R.id.login_denglu);
        mLoginDenglu.setOnClickListener(this);
        mLoginRegister = (TextView) findViewById(R.id.login_register);
        mLoginRegister.setOnClickListener(this);
        mLoginForgetpassword = (TextView) findViewById(R.id.login_forgetpassword);
        mLoginForgetpassword.setOnClickListener(this);
        mLoginWeixin = (ImageView) findViewById(R.id.login_weixin);
        mLoginWeixin.setOnClickListener(this);
        mActivityLoginDeleteUsername = (ImageView) findViewById(R.id.activity_login_delete_username);
        mActivityLoginDeletePassword = (ImageView) findViewById(R.id.activity_login_delete_password);
        mActivityLoginShowPassword = (ImageView) findViewById(R.id.activity_login_show_password);
        mActivityLoginDeleteUsername.setOnClickListener(this);
        mActivityLoginDeletePassword.setOnClickListener(this);
        mActivityLoginShowPassword.setOnClickListener(this);

        mLoginUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 1) {
                    mActivityLoginDeleteUsername.setVisibility(View.VISIBLE);
                } else mActivityLoginDeleteUsername.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mLoginPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 1) {
                    mActivityLoginDeletePassword.setVisibility(View.VISIBLE);
                } else mActivityLoginDeletePassword.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;

            //获取新的图片验证码
            case R.id.login_change_img:

                OkGo.<Bitmap>get(ServerInfo.SERVER + InterfaceInfo.CAPTCHA)
                        .tag(this)
                        .params("deviceNo", DeviceUtils.getAndroidID())
                        .execute(new DialogBitmapcallback(this) {

                            @Override
                            public void onSuccess(Response<Bitmap> response) {
                                try {

                                    mLoginVerifycodeImg.setImageBitmap(response.body());


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Response<Bitmap> response) {
                                super.onError(response);
                            }
                        });

                break;

            //获取验证码
            case R.id.login_huoquyanzhengma:

                OkGo.<Bitmap>get(ServerInfo.SERVER + InterfaceInfo.CAPTCHA)
                        .tag(this)
                        .params("deviceNo", DeviceUtils.getAndroidID())
                        .execute(new DialogBitmapcallback(this) {

                            @Override
                            public void onSuccess(Response<Bitmap> response) {
                                try {

                                    mLoginVerifycodeImg.setImageBitmap(response.body());
                                    mLoginHuoquyanzhengma.setVisibility(View.GONE);
                                    mLoginVerifycodeImg.setVisibility(View.VISIBLE);
                                    mLoginChangeImg.setVisibility(View.VISIBLE);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Response<Bitmap> response) {
                                super.onError(response);
                            }
                        });


                break;

            //登录
            case R.id.login_denglu:

                if (StringUtils.isTrimEmpty(mLoginUsername.getText().toString())) {
                    ToastUtils.showShort("用户名不能为空！");
                    return;
                }
                if (StringUtils.isEmpty(mLoginPassword.getText().toString())) {
                    ToastUtils.showShort("密码不能为空！");
                    return;
                }
                if (StringUtils.isEmpty(mLoginVerifycode.getText().toString())) {
                    ToastUtils.showShort("验证码不能为空！");
                    return;
                }
                if (mLoginVerifycode.getText().toString().length() != 4) {
                    ToastUtils.showShort("验证码长度为4位");
                    return;
                }

                OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.LOGIN)
                        .tag(this)

                        .params("sign", SPUtils.getInstance().getString("sign"))
                        .params("username", mLoginUsername.getText().toString())
                        .params("aesPass", new String(EncryptUtils.encryptAES2Base64(mLoginPassword.getText().toString().trim().getBytes(),
                                "LnhtI(bt490B74Je".getBytes(), "AES/ECB/PKCS5Padding", null)))
                        .params("captcha", mLoginVerifycode.getText().toString())
                        .params("deviceNo", DeviceUtils.getAndroidID())

                        .execute(new DialogStringCallback(this) {

                            @Override
                            public void onSuccess(Response<String> response) {

                                try {
                                    if (response.code() == 200) {

                                        JSONObject jsonObject = JSONObject.parseObject(response.body());

                                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                            JSONObject data = jsonObject.getJSONObject("data");
                                            UserBean user = data.toJavaObject(UserBean.class);
                                            SPUtils.getInstance().put("companyName", user.getCompanyName());
                                            SPUtils.getInstance().put("token", user.getToken());
                                            SPUtils.getInstance().put("userId", user.getUserId());
                                            SPUtils.getInstance().put("loginName", user.getLoginName());
                                            SPUtils.getInstance().put("photo", user.getPhoto());
                                            SPUtils.getInstance().put("isCompany", user.getCompany());
                                            SPUtils.getInstance().put("isLogin", "true");
                                            SPUtils.getInstance().put("identity", "1");//1为买家 2为卖家
                                            EventBus.getDefault().post(new MessageBean(MessageBean.Code.DELU, ""));
                                            ActivityUtils.finishActivity(LoginActivity.this);
                                            return;

                                        } else if (StringUtils.equals(jsonObject.getString("code"), "INVALID_SIGN")) {
                                            SignAndTokenUtil.getSign(LoginActivity.this);
                                            return;
                                        } else if (StringUtils.equals(jsonObject.getString("code"), "token失效")) {
                                            return;
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

                break;

            //注册
            case R.id.login_register:
                ActivityUtils.startActivity(RegisterActivity.class);
                break;

            //忘记密码
            case R.id.login_forgetpassword:
                ActivityUtils.startActivity(ForgetPasswordActivity.class);
                break;

            //微信登录
            case R.id.login_weixin:
                break;
            //删除输入的用户名
            case R.id.activity_login_delete_username:
                mLoginUsername.setText("");
                break;

            //删除输入的密码
            case R.id.activity_login_delete_password:
                mLoginPassword.setText("");
                break;

            //是否展示密码
            case R.id.activity_login_show_password:
                if (xianshimima) {
                    xianshimima = false;
                    mLoginPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                    mActivityLoginShowPassword.setImageDrawable(getResources().getDrawable(R.mipmap.biyan));

                } else {
                    mLoginPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);

                    mActivityLoginShowPassword.setImageDrawable(getResources().getDrawable(R.mipmap.zhengyan));
                    xianshimima = true;
                }
                mLoginPassword.setSelection(mLoginPassword.getText().length());
                break;
        }
    }

    //是否展示密码
    private boolean xianshimima = true;
}
