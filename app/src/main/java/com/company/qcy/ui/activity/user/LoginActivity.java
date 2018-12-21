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
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
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
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.bean.pengyouquan.User;
import com.company.qcy.bean.user.UserBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;


public class LoginActivity extends BaseActivity implements View.OnClickListener {


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
    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;

    /**
     * 微信登录相关
     */
    private static final String APP_ID = "wx63410989373f8975";
    IWXAPI iwxapi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        // APP_ID 替换为你的应用从官方网站申请到的合法appID
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        iwxapi = WXAPIFactory.createWXAPI(this, APP_ID, true);

        // 将应用的appId注册到微信
        iwxapi.registerApp(APP_ID);
    }


    private void initView() {
        mLoginUsername = (EditText) findViewById(R.id.login_username);
        mLoginPassword = (EditText) findViewById(R.id.login_password);
        mLoginVerifycode = (EditText) findViewById(R.id.login_verifycode);
        mLoginVerifycodeImg = (ImageView) findViewById(R.id.login_verifycode_img);
        mLoginChangeImg = (TextView) findViewById(R.id.login_change_img);
        mLoginChangeImg.setOnClickListener(this);
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
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mToolbarTitle.setText("登录");

        getImageVerifycode();
    }

    private void getImageVerifycode() {

        OkGo.<Bitmap>get(ServerInfo.SERVER + InterfaceInfo.CAPTCHA)
                .tag(this)
                .params("deviceNo", DeviceUtils.getAndroidID())
                .execute(new DialogBitmapcallback(this) {

                    @Override
                    public void onSuccess(Response<Bitmap> response) {
                        try {

                            if (response.code() == 200) {
                                mLoginVerifycodeImg.setImageBitmap(response.body());
                            } else {
                                ToastUtils.showShort("获取图片验证码失败！");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<Bitmap> response) {
                        super.onError(response);
                        ToastUtils.showShort(getResources().getString(R.string.NETEXCEPTION));
                    }
                });

    }


    @Override
    public void onReciveMessage(MessageBean msg) {
        super.onReciveMessage(msg);

        switch (msg.getCode()) {
            case MessageBean.Code.WXLOGIN:
                wxLogin((UserBean) msg.getObj());
                break;

            case MessageBean.Code.RESETPASSWORD:
                mLoginPassword.setText("");
                mLoginVerifycode.setText("");
                getImageVerifycode();
                break;
        }
    }

    private void wxLogin(UserBean user) {
        SPUtils.getInstance().put("companyName", user.getCompanyName());
        SPUtils.getInstance().put("token", user.getToken());
        SPUtils.getInstance().put("loginName", user.getLoginName());
        SPUtils.getInstance().put("photo", user.getPhoto());
        SPUtils.getInstance().put("communityPhoto", user.getCommunityPhoto());
        SPUtils.getInstance().put("isCompany", user.getIsCompany());
        SPUtils.getInstance().put("isLogin", "true");
        SPUtils.getInstance().put("identity", "1");//1为买家 2为卖家
        EventBus.getDefault().post(new MessageBean(MessageBean.Code.DELU));
        ActivityUtils.finishActivity(LoginActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;

            //获取新的图片验证码
            case R.id.login_change_img:
                getImageVerifycode();
                break;

            //登录
            case R.id.login_denglu:
                KeyboardUtils.hideSoftInput(LoginActivity.this);

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

                PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.LOGIN)
                        .tag(this)
                        .params("sign", SPUtils.getInstance().getString("sign"))
                        .params("username", mLoginUsername.getText().toString())
                        .params("aesPass", new String(EncryptUtils.encryptAES2Base64(mLoginPassword.getText().toString().trim().getBytes(),
                                getResources().getString(R.string.passwordAES).getBytes(), "AES/ECB/PKCS5Padding", null)))
                        .params("captcha", mLoginVerifycode.getText().toString())
                        .params("deviceNo", DeviceUtils.getAndroidID());

                DialogStringCallback stringCallback = new DialogStringCallback(this) {

                    @Override
                    public void onSuccess(Response<String> response) {
                        LogUtils.e("LOGIN", response.body());

                        try {
                            if (response.code() == 200) {
                                JSONObject jsonObject = JSONObject.parseObject(response.body());
                                String msg = jsonObject.getString("msg");
                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    UserBean user = data.toJavaObject(UserBean.class);
                                    SPUtils.getInstance().put("companyName", user.getCompanyName());
                                    SPUtils.getInstance().put("token", user.getToken());
                                    SPUtils.getInstance().put("loginName", user.getLoginName());
                                    SPUtils.getInstance().put("photo", user.getPhoto());
                                    SPUtils.getInstance().put("communityPhoto", user.getCommunityPhoto());
                                    SPUtils.getInstance().put("isCompany", user.getIsCompany());
                                    SPUtils.getInstance().put("isLogin", "true");
                                    SPUtils.getInstance().put("identity", "1");//1为买家 2为卖家
                                    EventBus.getDefault().post(new MessageBean(MessageBean.Code.DELU));
                                    ActivityUtils.finishActivity(LoginActivity.this);
                                    return;
                                }
                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                                    SignAndTokenUtil.getSign(LoginActivity.this,request,this);
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

                wechatLogin();

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
            case R.id.toolbar_back:
                KeyboardUtils.hideSoftInput(this);
                finish();
                break;
        }
    }


    // 微信登录
    public void wechatLogin() {
//       Platform wechat= ShareSDK.getPlatform(Wechat.NAME);
//        wechat.setPlatformActionListener(new PlatformActionListener() {
//            @Override
//            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//                LogUtils.e("xczczxcsdasadsa",hashMap);
//            }
//
//            @Override
//            public void onError(Platform platform, int i, Throwable throwable) {
//
//            }
//
//            @Override
//            public void onCancel(Platform platform, int i) {
//
//            }
//        });
//
//        wechat.showUser(null);

        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wx_oauth_authorization_state";
        iwxapi.sendReq(req);

    }

    //是否展示密码
    private boolean xianshimima = true;
}
