package com.company.qcy.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.company.qcy.R;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.forgetpassword_nextbutton:
                startActivity(new Intent(this,ResetPasswordActivity.class));
                break;
        }
    }
}
