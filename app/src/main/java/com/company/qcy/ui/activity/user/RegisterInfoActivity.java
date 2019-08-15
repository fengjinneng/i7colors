package com.company.qcy.ui.activity.user;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.EncryptUtils;
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
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.SinglePicker;

public class RegisterInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    /**
     * 确认
     */
    private Button mActivityRegisterInfoSubmit;
    /**
     * 输入邀请码(非必填)
     */
    private EditText mActivityRegisterInfoYaoqingma;
    /**
     * 输入公司名称
     */
    private EditText mActivityRegisterInfoCompanyname;
    /**
     * 请选择职位
     */
    private TextView mActivityRegisterInfoZhiwei;

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_info);
        token  = getIntent().getStringExtra("token");
        initView();
    }

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mActivityRegisterInfoSubmit = (Button) findViewById(R.id.activity_register_info_submit);
        mActivityRegisterInfoSubmit.setOnClickListener(this);
        mActivityRegisterInfoYaoqingma = (EditText) findViewById(R.id.activity_register_info_yaoqingma);
        mActivityRegisterInfoCompanyname = (EditText) findViewById(R.id.activity_register_info_companyname);
        mActivityRegisterInfoZhiwei = (TextView) findViewById(R.id.activity_register_info_zhiwei);
        mActivityRegisterInfoZhiwei.setOnClickListener(this);

        mToolbarTitle.setText("完善信息");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                KeyboardUtils.hideSoftInput(this);
                finish();
                break;
            case R.id.activity_register_info_submit:

                KeyboardUtils.hideSoftInput(this);

                if (StringUtils.isTrimEmpty(mActivityRegisterInfoCompanyname.getText().toString())) {
                    ToastUtils.showShort("请填写公司名称");
                    return;

                }
                if (StringUtils.equals("请选择职位", mActivityRegisterInfoZhiwei.getText().toString())) {
                    ToastUtils.showShort("请选择职位信息");
                    return;
                }


                PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.REGISTERINFO)
                        .tag(this)
                        .params("sign", SPUtils.getInstance().getString("sign"))
                        .params("token", token)
                        .params("company", mActivityRegisterInfoCompanyname.getText().toString().trim())
                        .params("positionName", mActivityRegisterInfoZhiwei.getText().toString())
                        .params("inviteCode", mActivityRegisterInfoYaoqingma.getText().toString().trim());


                DialogStringCallback stringCallback = new DialogStringCallback(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LogUtils.e("REGISTERINFO", response.body());

                        try {
                            if (response.code() == 200) {
                                JSONObject jsonObject = JSONObject.parseObject(response.body());
                                String msg = jsonObject.getString("msg");
                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {

                                    finish();
                                    return;

                                }
                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                                    SignAndTokenUtil.getSign(RegisterInfoActivity.this, request, this);
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
            case R.id.activity_register_info_zhiwei:

                List<String> zhiwei = new ArrayList<>();
                zhiwei.add("采购");
                zhiwei.add("销售");
                zhiwei.add("技术");
                zhiwei.add("老板");
                zhiwei.add("生产");
                zhiwei.add("其它");
                choiceString(zhiwei, mActivityRegisterInfoZhiwei);
                break;
        }
    }

    //单选
    private void choiceString(final List data, final TextView tv) {
        SinglePicker<String> picker = new SinglePicker<String>(this, data);
        picker.setTitleText("请选择");
        picker.setCycleDisable(true);
        picker.show();
        picker.setOnItemPickListener(new SinglePicker.OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int i, String s) {
                tv.setText(s);
            }
        });
    }

}
