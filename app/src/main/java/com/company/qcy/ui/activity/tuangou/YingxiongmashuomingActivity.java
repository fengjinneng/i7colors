package com.company.qcy.ui.activity.tuangou;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.qcy.R;
import com.company.qcy.base.BaseActivity;

public class YingxiongmashuomingActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yingxiongmashuoming);
        initView();
    }

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mToolbarTitle.setText("英雄码说明");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                finish();
                break;
        }
    }
}
