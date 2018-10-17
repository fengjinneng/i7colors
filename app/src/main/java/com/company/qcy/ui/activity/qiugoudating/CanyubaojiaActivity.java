package com.company.qcy.ui.activity.qiugoudating;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.company.qcy.R;

public class CanyubaojiaActivity extends AppCompatActivity {

    /**
     * 分散染料 七彩云 分散红FB 200%
     */
    private TextView mActivityCanyubaojiaChanpinming;
    /**
     * 绍兴百丽恒印染有限公司
     */
    private TextView mActivityCanyubaojiaCompany;
    private EditText mActivityCanyubaojiaPhone;
    private EditText mActivityCanyubaojiaPrice;
    /**
     * 点击选取时间
     */
    private TextView mActivityCanyubaojiaTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canyubaojia);
        initView();
    }

    private void initView() {
        mActivityCanyubaojiaChanpinming = (TextView) findViewById(R.id.activity_canyubaojia_chanpinming);
        mActivityCanyubaojiaCompany = (TextView) findViewById(R.id.activity_canyubaojia_company);
        mActivityCanyubaojiaPhone = (EditText) findViewById(R.id.activity_canyubaojia_phone);
        mActivityCanyubaojiaPrice = (EditText) findViewById(R.id.activity_canyubaojia_price);
        mActivityCanyubaojiaTime = (TextView) findViewById(R.id.activity_canyubaojia_time);
    }
}
