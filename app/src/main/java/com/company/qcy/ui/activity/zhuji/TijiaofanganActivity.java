package com.company.qcy.ui.activity.zhuji;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
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
import com.company.qcy.base.EditTextUtils;
import com.company.qcy.bean.eventbus.MessageBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;

import org.greenrobot.eventbus.EventBus;

public class TijiaofanganActivity extends BaseActivity implements View.OnClickListener {

    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    /**
     * 请输入联系人手机号
     */
    private EditText mActivityTijiaofanganPhone;
    /**
     * 请输入供应商产品全称
     */
    private TextView mActivityTijiaofanganProductname;
    /**
     * 请输入数量
     */
    private EditText mActivityTijiaofanganNum;
    /**
     * 请输入描述内容，50字内
     */
    private EditText mActivityTijiaofanganMiaoshu;
    /**
     * KG
     */
    private TextView mActivityTijiaofanganNumUnit;

    /**
     * 提交方案
     */
    private Button mActivityTijiaofanganSubmit;
    private Long zhujiDiyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tijiaofangan);
        zhujiDiyId = getIntent().getLongExtra("zhujiDiyId", 0);
        initView();
    }

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mActivityTijiaofanganPhone = (EditText) findViewById(R.id.activity_tijiaofangan_phone);
        mActivityTijiaofanganProductname = (TextView) findViewById(R.id.activity_tijiaofangan_productname);
        mActivityTijiaofanganNum = (EditText) findViewById(R.id.activity_tijiaofangan_num);
        mActivityTijiaofanganMiaoshu = (EditText) findViewById(R.id.activity_tijiaofangan_miaoshu);
        mActivityTijiaofanganNumUnit = (TextView) findViewById(R.id.activity_tijiaofangan_num_unit);
        mActivityTijiaofanganSubmit = (Button) findViewById(R.id.activity_tijiaofangan_submit);
        mActivityTijiaofanganSubmit.setOnClickListener(this);
        mToolbarTitle.setText("提交方案");
        EditTextUtils.setEditTextInhibitInputSpace(mActivityTijiaofanganNum);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                KeyboardUtils.hideSoftInput(TijiaofanganActivity.this);
                finish();
                break;
            case R.id.activity_tijiaofangan_submit:
                tijiaofangan();
                break;
        }
    }

    private void tijiaofangan() {

        if (StringUtils.isEmpty(mActivityTijiaofanganPhone.getText().toString())
                || !RegexUtils.isMobileSimple(mActivityTijiaofanganPhone.getText().toString())) {
            ToastUtils.showShort("请填写正确的手机号!");
            return;
        }

        if (StringUtils.isEmpty(mActivityTijiaofanganProductname.getText().toString())) {
            ToastUtils.showShort("请填写产品名称!");
            return;
        }

        HttpParams paras = new HttpParams();
        paras.put("sign", SPUtils.getInstance().getString("sign"));
        paras.put("token", SPUtils.getInstance().getString("token"));
        paras.put("zhujiDiyId", zhujiDiyId);
        paras.put("phone", mActivityTijiaofanganPhone.getText().toString());
        paras.put("productName", mActivityTijiaofanganProductname.getText().toString());

        if (StringUtils.isTrimEmpty(mActivityTijiaofanganNum.getText().toString())) {

            paras.put("num", "");
            paras.put("numUnit", "");

        } else {
            paras.put("num", mActivityTijiaofanganNum.getText().toString());
            paras.put("numUnit", "KG");

        }

        paras.put("description", mActivityTijiaofanganMiaoshu.getText().toString());
        paras.put("from", getResources().getString(R.string.app_android));

        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.FABUJIEJUEFANGAN)
                .tag(this)
                .params(paras);

        DialogStringCallback stringCallback = new DialogStringCallback(TijiaofanganActivity.this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("FABUJIEJUEFANGAN", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");

                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            KeyboardUtils.hideSoftInput(TijiaofanganActivity.this);
                            ToastUtils.showShort(msg);
                            finish();
                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(TijiaofanganActivity.this, request, this);
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

    }
}
