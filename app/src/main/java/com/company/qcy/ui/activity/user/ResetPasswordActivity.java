package com.company.qcy.ui.activity.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.LogUtils;
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
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;

import org.greenrobot.eventbus.EventBus;

public class ResetPasswordActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 请输入密码
     */
    private EditText mResetPasswordPwd1;
    /**
     * 请再次输入密码
     */
    private EditText mResetPasswordPwd2;
    /**
     * 确认重置密码
     */
    private Button mResetPasswordButton;
    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        phone = getIntent().getStringExtra("phone");
        initView();
    }

    private void initView() {
        mResetPasswordPwd1 = (EditText) findViewById(R.id.reset_password_pwd1);
        mResetPasswordPwd2 = (EditText) findViewById(R.id.reset_password_pwd2);
        mResetPasswordButton = (Button) findViewById(R.id.reset_password_button);
        mResetPasswordButton.setOnClickListener(this);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mToolbarTitle.setText("重置密码");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.reset_password_button:
                if (StringUtils.isEmpty(phone)) {
                    return;
                }
                if(StringUtils.isEmpty(mResetPasswordPwd1.getText().toString())||StringUtils.isEmpty(mResetPasswordPwd2.getText().toString())){
                    ToastUtils.showShort("密码不能为空");
                    return;
                }else if(!StringUtils.equals(mResetPasswordPwd1.getText().toString(),mResetPasswordPwd2.getText().toString())){
                    ToastUtils.showShort("两次输入的密码不一致");
                    return;
                }

                PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.RESETPASSWORD)
                        .tag(this)
                        .params("sign", SPUtils.getInstance().getString("sign"))
                        .params("mobile", phone)

                        .params("password", new String(EncryptUtils.encryptAES2Base64(mResetPasswordPwd1.getText().toString().trim().getBytes(),
                                getResources().getString(R.string.passwordAES).getBytes(), "AES/ECB/PKCS5Padding", null)))
                        .params("rePassword", new String(EncryptUtils.encryptAES2Base64(mResetPasswordPwd2.getText().toString().trim().getBytes(),
                                getResources().getString(R.string.passwordAES).getBytes(), "AES/ECB/PKCS5Padding", null)));

                DialogStringCallback stringCallback = new DialogStringCallback(this) {

                    @Override
                    public void onSuccess(Response<String> response) {
                        LogUtils.v("RESETPASSWORD", response.body());
                        try {
                            if (response.code() == 200) {
                                JSONObject jsonObject = JSONObject.parseObject(response.body());
                                String msg = jsonObject.getString("msg");
                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                    ToastUtils.showShort("重置密码成功");
                                    EventBus.getDefault().post(new MessageBean(MessageBean.Code.RESETPASSWORD));
                                    finish();
                                    return;
                                }
                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                                    SignAndTokenUtil.getSign(ResetPasswordActivity.this,request,this);
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
            case R.id.toolbar_back:
                finish();
                break;
        }
    }
}
