package com.company.qcy.map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.kaifangshangcheng.CompanyIntroduceBean;
import com.company.qcy.bean.pengyouquan.MyAddress;
import com.company.qcy.ui.activity.pengyouquan.MapActivity;

public class NoMarketCompanyActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    /**
     * TextView
     */
    private TextView mActivityNoMarketCompanyLianxiren;
    /**
     * TextView
     */
    private TextView mActivityNoMarketCompanyPhone;
    /**
     * TextView
     */
    private TextView mActivityNoMarketCompanyAddress;
    private ImageView mActivityNoMarketCompanyDaohang;
    private WebView mActivityNoMarketCompanyWebview;

    private CompanyIntroduceBean companyIntroduceBean;
    /**
     * 暂无公司介绍!
     */
    private TextView mActivityNoMarketCompanyZanwujianjie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_market_company);

        companyIntroduceBean = getIntent().getParcelableExtra("companyIntroduceBean");

        initView();
    }

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mActivityNoMarketCompanyLianxiren = (TextView) findViewById(R.id.activity_no_market_company_lianxiren);
        mActivityNoMarketCompanyPhone = (TextView) findViewById(R.id.activity_no_market_company_phone);
        mActivityNoMarketCompanyAddress = (TextView) findViewById(R.id.activity_no_market_company_address);
        mActivityNoMarketCompanyDaohang = (ImageView) findViewById(R.id.activity_no_market_company_daohang);
        mActivityNoMarketCompanyDaohang.setOnClickListener(this);
        mActivityNoMarketCompanyWebview = (WebView) findViewById(R.id.activity_no_market_company_webview);
        mActivityNoMarketCompanyZanwujianjie = (TextView) findViewById(R.id.activity_no_market_company_zanwujianjie);



        if (!ObjectUtils.isEmpty(companyIntroduceBean)) {

            mToolbarTitle.setText(companyIntroduceBean.getCompanyName());

            if (!StringUtils.isTrimEmpty(companyIntroduceBean.getContact())) {
                mActivityNoMarketCompanyLianxiren.setText(companyIntroduceBean.getContact());

            } else {
                mActivityNoMarketCompanyLianxiren.setText("暂无联系人信息");
            }

            if (!StringUtils.isTrimEmpty(companyIntroduceBean.getPhone())) {
                mActivityNoMarketCompanyPhone.setText(companyIntroduceBean.getPhone());

            } else {
                mActivityNoMarketCompanyPhone.setText("暂无联系方式");
            }

            if (!StringUtils.isTrimEmpty(companyIntroduceBean.getAddress())) {
                mActivityNoMarketCompanyAddress.setText(companyIntroduceBean.getAddress());

            } else {
                mActivityNoMarketCompanyAddress.setText("暂无地址信息");
            }

            if (!StringUtils.isTrimEmpty(companyIntroduceBean.getIntroduce())) {
                mActivityNoMarketCompanyWebview.loadData(companyIntroduceBean.getIntroduce(), "text/html;charset=UTF-8", null);

            } else {
                mActivityNoMarketCompanyZanwujianjie.setVisibility(View.VISIBLE);
                mActivityNoMarketCompanyWebview.setVisibility(View.GONE);
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_no_market_company_daohang:

                if (!ObjectUtils.isEmpty(companyIntroduceBean)) {

                    if(StringUtils.isEmpty(companyIntroduceBean.getLat())||
                            StringUtils.isEmpty(companyIntroduceBean.getLng())){
                        ToastUtils.showShort("没有该地址信息");
                        return;
                    }

                    MyAddress address = new MyAddress();
                    address.setLat(companyIntroduceBean.getLat());
                    address.setLot(companyIntroduceBean.getLng());
                    address.setTitle(companyIntroduceBean.getCompanyName());
                    address.setContent(companyIntroduceBean.getAddress());
                    Intent iAddress = new Intent(this, MapActivity.class);
                    iAddress.putExtra("address", address);
                    ActivityUtils.startActivity(iAddress);

                }


                break;
        }
    }
}
