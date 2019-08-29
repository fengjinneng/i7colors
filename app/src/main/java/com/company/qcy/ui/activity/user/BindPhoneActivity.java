package com.company.qcy.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.bean.user.UserBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.qqtheme.framework.picker.SinglePicker;

public class BindPhoneActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 请填写手机号码
     */
    private EditText mActivityBindPhonePhone;
    /**
     * 输入验证码
     */
    private EditText mActivityBindPhoneVerifycode;
    /**
     * 发送验证码
     */
    private TextView mActivityBindPhoneSendsms;
    /**
     * 取消
     */
    private TextView mActivityBindPhoneCancle;
    /**
     * 确认
     */
    private Button mActivityBindPhoneCommit;


    private UserBean userBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_phone);
        userBean = getIntent().getParcelableExtra("user");
        initView();
    }

    private void initView() {
        mActivityBindPhonePhone = (EditText) findViewById(R.id.activity_bind_phone_phone);
        mActivityBindPhoneVerifycode = (EditText) findViewById(R.id.activity_bind_phone_verifycode);
        mActivityBindPhoneSendsms = (TextView) findViewById(R.id.activity_bind_phone_sendsms);
        mActivityBindPhoneSendsms.setOnClickListener(this);
        mActivityBindPhoneCancle = (TextView) findViewById(R.id.activity_bind_phone_cancle);
        mActivityBindPhoneCancle.setOnClickListener(this);
        mActivityBindPhoneCommit = (Button) findViewById(R.id.activity_bind_phone_commit);
        mActivityBindPhoneCommit.setOnClickListener(this);
    }


    private CountDownTimer timer;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.activity_bind_phone_sendsms:
                if (!RegexUtils.isMobileSimple(mActivityBindPhonePhone.getText().toString())) {
                    ToastUtils.showShort("请填写正确的手机号码");
                    return;
                }
                PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.WXSENDSMS)
                        .tag(this)
                        .params("sign", SPUtils.getInstance().getString("sign"))
                        .params("mobile", mActivityBindPhonePhone.getText().toString());

                DialogStringCallback stringCallback = new DialogStringCallback(this) {

                    @Override
                    public void onSuccess(Response<String> response) {
                        LogUtils.e("WXSENDSMS", response.body());

                        try {
                            JSONObject jsonObject = JSONObject.parseObject(response.body());
                            String msg = jsonObject.getString("msg");
                            if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                ToastUtils.showShort(msg);
                                timer = new CountDownTimer(59000, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        SimpleDateFormat sdf = new SimpleDateFormat("ss");
                                        mActivityBindPhoneSendsms.setText(sdf.format(new Date(millisUntilFinished)) + "S");
                                        mActivityBindPhoneSendsms.setEnabled(false);
                                    }

                                    @Override
                                    public void onFinish() {
                                        mActivityBindPhoneSendsms.setText("重新获取验证码");
                                        mActivityBindPhoneSendsms.setEnabled(true);
                                    }
                                }.start();
                                return;
                            }
                            if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                                SignAndTokenUtil.getSign(BindPhoneActivity.this, request, this);
                                return;
                            }
                            ToastUtils.showShort(msg);

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
            case R.id.activity_bind_phone_cancle:
                KeyboardUtils.hideSoftInput(this);
                finish();
                break;
            case R.id.activity_bind_phone_commit:
                KeyboardUtils.hideSoftInput(this);
                if (!RegexUtils.isMobileSimple(mActivityBindPhonePhone.getText().toString())) {
                    ToastUtils.showShort("请填写正确的手机号码");
                    return;
                }

                if (mActivityBindPhoneVerifycode.getText().toString().length() != 6) {
                    ToastUtils.showShort("请填写正确的验证码");
                    return;
                }
                PostRequest<String> postRequest = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.WXBINDPHONE)
                        .tag(this)
                        .params("sign", SPUtils.getInstance().getString("sign"))
                        .params("phone", mActivityBindPhonePhone.getText().toString())
                        .params("token", SPUtils.getInstance().getString("token"))
                        .params("from", getResources().getString(R.string.app_android))
                        .params("registrationId", SPUtils.getInstance().getString("registrationId"))
                        .params("smsCode", mActivityBindPhoneVerifycode.getText().toString());

                DialogStringCallback dialogStringCallback = new DialogStringCallback(this) {

                    @Override
                    public void onSuccess(Response<String> response) {
                        LogUtils.e("WXBINDPHONE", response.body());

                        try {
                            JSONObject jsonObject = JSONObject.parseObject(response.body());
                            String msg = jsonObject.getString("msg");
                            if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                JSONObject data = jsonObject.getJSONObject("data");
                                UserBean user = data.toJavaObject(UserBean.class);
                                EventBus.getDefault().post(new MessageBean(MessageBean.Code.WXLOGIN, user));
                                finish();

                                Intent i = new Intent(BindPhoneActivity.this,RegisterInfoActivity.class);
                                i.putExtra("token",user.getToken());
                                ActivityUtils.startActivity(i);
                                return;
                            }
                            if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                                SignAndTokenUtil.getSign(BindPhoneActivity.this, postRequest, this);
                                return;
                            }
                            ToastUtils.showShort(msg);

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

                postRequest.execute(dialogStringCallback);
                break;

        }
    }

}
