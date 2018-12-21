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

public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    /**
     * 请输入原密码
     */
    private EditText mActivityChangePasswordYuanmima;
    /**
     * 请输入新密码
     */
    private EditText mActivityChangePasswordNew1;
    /**
     * 请再次输入密码
     */
    private EditText mActivityChangePasswordNew2;
    /**
     * 确认修改
     */
    private Button mActivityChangePasswordSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initView();
    }

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mActivityChangePasswordYuanmima = (EditText) findViewById(R.id.activity_change_password_yuanmima);
        mActivityChangePasswordNew1 = (EditText) findViewById(R.id.activity_change_password_new1);
        mActivityChangePasswordNew2 = (EditText) findViewById(R.id.activity_change_password_new2);
        mActivityChangePasswordSubmit = (Button) findViewById(R.id.activity_change_password_submit);
        mActivityChangePasswordSubmit.setOnClickListener(this);
        mToolbarTitle.setText("重置密码");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_change_password_submit:

                if (StringUtils.isEmpty(mActivityChangePasswordYuanmima.getText().toString())) {
                    ToastUtils.showShort("请输入原密码");
                    return;
                }
                if (StringUtils.isEmpty(mActivityChangePasswordNew1.getText().toString())) {
                    ToastUtils.showShort("请输入新密码");
                    return;
                }
                if (StringUtils.isEmpty(mActivityChangePasswordNew2.getText().toString())) {
                    ToastUtils.showShort("请确认密码");
                    return;
                }
                if (!StringUtils.equals(mActivityChangePasswordNew1.getText().toString(), mActivityChangePasswordNew2.getText().toString())) {
                    ToastUtils.showShort("两次输入的密码不一致");
                    return;
                }

                PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.CHANGEPASSWORD)
                        .tag(this)
                        .params("sign", SPUtils.getInstance().getString("sign"))
                        .params("token", SPUtils.getInstance().getString("token"))
                        .params("password", new String(EncryptUtils.encryptAES2Base64(mActivityChangePasswordYuanmima.getText().toString().trim().getBytes(),
                                getResources().getString(R.string.passwordAES).getBytes(), "AES/ECB/PKCS5Padding", null)))
                        .params("newPassword", new String(EncryptUtils.encryptAES2Base64(mActivityChangePasswordNew1.getText().toString().trim().getBytes(),
                                getResources().getString(R.string.passwordAES).getBytes(), "AES/ECB/PKCS5Padding", null)))
                        .params("reNewPassword", new String(EncryptUtils.encryptAES2Base64(mActivityChangePasswordNew2.getText().toString().trim().getBytes(),
                                getResources().getString(R.string.passwordAES).getBytes(), "AES/ECB/PKCS5Padding", null)));

                DialogStringCallback stringCallback = new DialogStringCallback(this) {

                    @Override
                    public void onSuccess(Response<String> response) {
                        LogUtils.v("CHANGEPASSWORD", response.body());
                        try {
                            if (response.code() == 200) {
                                JSONObject jsonObject = JSONObject.parseObject(response.body());
                                String msg = jsonObject.getString("msg");
                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                    if (jsonObject.getBoolean("data")) {
                                        EventBus.getDefault().post(new MessageBean(MessageBean.Code.CHANGEPASSWORDCHENGGONG));
                                        finish();
                                    }
                                    ToastUtils.showShort(msg);
                                     return;
                                }
                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                                    SignAndTokenUtil.getSign(ChangePasswordActivity.this,request,this);
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
        }
    }
}
