package com.company.qcy.ui.activity.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.company.qcy.R;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        initView();
    }

    private void initView() {
        mResetPasswordPwd1 = (EditText) findViewById(R.id.reset_password_pwd1);
        mResetPasswordPwd2 = (EditText) findViewById(R.id.reset_password_pwd2);
        mResetPasswordButton = (Button) findViewById(R.id.reset_password_button);
        mResetPasswordButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.reset_password_button:
                break;
        }
    }
}
