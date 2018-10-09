package com.company.qcy.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.company.qcy.R;

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
        mLoginDenglu = (Button) findViewById(R.id.login_denglu);
        mLoginDenglu.setOnClickListener(this);
        mLoginRegister = (TextView) findViewById(R.id.login_register);
        mLoginForgetpassword = (TextView) findViewById(R.id.login_forgetpassword);
        mLoginRegister.setOnClickListener(this);
        mLoginForgetpassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.login_denglu:
                break;
            case R.id.login_forgetpassword:
                startActivity(new Intent(this,ForgetPasswordActivity.class));
                break;
            case R.id.login_register:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
        }
    }
}
