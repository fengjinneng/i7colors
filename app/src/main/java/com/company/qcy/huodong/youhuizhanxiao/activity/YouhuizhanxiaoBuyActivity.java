package com.company.qcy.huodong.youhuizhanxiao.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.AddressPickTask;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InputWindowListener;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.MyConsrantLayout;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.eventbus.MessageBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;

import org.greenrobot.eventbus.EventBus;

import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;

public class YouhuizhanxiaoBuyActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    /**
     * 例如:100
     */
    private EditText mActivityYouhuizhanxiaoBuyGoumailiang;
    /**
     * 请输入联系人
     */
    private EditText mActivityYouhuizhanxiaoLianxiren;
    /**
     * 请输入联系方式
     */
    private EditText mActivityYouhuizhanxiaoPhone;
    /**
     * 请输入公司名称
     */
    private EditText mActivityYouhuizhanxiaoCompanyname;
    /**
     * 请输入公司详细地址
     */
    private EditText mActivityYouhuizhanxiaoCompanyAddress;
    /**
     * 提交
     */
    private TextView mActivityYouhuizhanxiaoSubmit;
    /**
     * 取消
     */
    private TextView mActivityYouhuizhanxiaoCancel;
    /**
     * 请选择
     */
    private TextView mActivityYouhuizhanxiaoChoiceAddress;
    /**
     * 是
     */
    private RadioButton mActivityYouhuizhanxiaoYes;
    /**
     * 否
     */
    private RadioButton mActivityYouhuizhanxiaoNo;
    private RadioGroup mActivityYouhuizhanxiaoYangpinGroup;

    private String numUnit;
    /**
     * 吨
     */
    private TextView mActivityYouhuizhanxiaoNumunit;
    private String id;
    private MyConsrantLayout mActivityYouhuizhanxiaoRootLayout;
    private ConstraintLayout mActivityYouhuizhanxiaoButtonLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youhuizhanxiao_buy);

        numUnit = getIntent().getStringExtra("numUnit");
        id = getIntent().getStringExtra("id");

        initView();
    }

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mActivityYouhuizhanxiaoBuyGoumailiang = (EditText) findViewById(R.id.activity_youhuizhanxiao_buy_goumailiang);
        mActivityYouhuizhanxiaoLianxiren = (EditText) findViewById(R.id.activity_youhuizhanxiao_lianxiren);
        mActivityYouhuizhanxiaoPhone = (EditText) findViewById(R.id.activity_youhuizhanxiao_phone);
        mActivityYouhuizhanxiaoCompanyname = (EditText) findViewById(R.id.activity_youhuizhanxiao_companyname);
        mActivityYouhuizhanxiaoCompanyAddress = (EditText) findViewById(R.id.activity_youhuizhanxiao_company_address);
        mActivityYouhuizhanxiaoSubmit = (TextView) findViewById(R.id.activity_youhuizhanxiao_submit);
        mActivityYouhuizhanxiaoCancel = (TextView) findViewById(R.id.activity_youhuizhanxiao_cancel);
        mActivityYouhuizhanxiaoChoiceAddress = (TextView) findViewById(R.id.activity_youhuizhanxiao_choiceAddress);
        mActivityYouhuizhanxiaoYes = (RadioButton) findViewById(R.id.activity_youhuizhanxiao_yes);
        mActivityYouhuizhanxiaoNo = (RadioButton) findViewById(R.id.activity_youhuizhanxiao_no);
        mActivityYouhuizhanxiaoYangpinGroup = (RadioGroup) findViewById(R.id.activity_youhuizhanxiao_yangpin_group);
        mActivityYouhuizhanxiaoNumunit = (TextView) findViewById(R.id.activity_youhuizhanxiao_numunit);
        mActivityYouhuizhanxiaoNumunit.setText(numUnit);
        mToolbarTitle.setText("我要购买");
        mActivityYouhuizhanxiaoSubmit.setOnClickListener(this);
        mActivityYouhuizhanxiaoCancel.setOnClickListener(this);
        mActivityYouhuizhanxiaoButtonLayout = (ConstraintLayout) findViewById(R.id.activity_youhuizhanxiao_button_layout);

        mActivityYouhuizhanxiaoBuyGoumailiang.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s == null) {
                    return;
                }
                if (s.length() == 2) {
                    if (s.toString().startsWith("0") && !s.toString().substring(1).equals(".")) {
                        mActivityYouhuizhanxiaoBuyGoumailiang.setText(s.toString().substring(1));
                        mActivityYouhuizhanxiaoBuyGoumailiang.setSelection(mActivityYouhuizhanxiaoBuyGoumailiang.getText().length());
                    }
                }

                // 以小数点开头，前面自动加上 "0"
                if (s.toString().startsWith(".")) {
                    mActivityYouhuizhanxiaoBuyGoumailiang.setText("0" + s);
                    mActivityYouhuizhanxiaoBuyGoumailiang.setSelection(mActivityYouhuizhanxiaoBuyGoumailiang.getText().length());
                }

            }
        });
        mActivityYouhuizhanxiaoRootLayout = (MyConsrantLayout) findViewById(R.id.activity_youhuizhanxiao_root_layout);
        mActivityYouhuizhanxiaoRootLayout.setListener(new InputWindowListener() {
            @Override
            public void show() {
                mActivityYouhuizhanxiaoButtonLayout.setVisibility(View.GONE);
            }

            @Override
            public void hidden() {
                mActivityYouhuizhanxiaoButtonLayout.setVisibility(View.VISIBLE);
            }
        });
        mActivityYouhuizhanxiaoChoiceAddress.setOnClickListener(this);

        mActivityYouhuizhanxiaoYangpinGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.activity_woyaotuangou_radiobutton1:
                        xuyaoyangpin = 1;
                        break;
                    case R.id.activity_woyaotuangou_radiobutton2:
                        xuyaoyangpin = 0;
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_youhuizhanxiao_submit:
                if (StringUtils.isTrimEmpty(mActivityYouhuizhanxiaoBuyGoumailiang.getText().toString())) {
                    ToastUtils.showShort("请填写购买量");
                    return;
                }
                if (StringUtils.isTrimEmpty(mActivityYouhuizhanxiaoLianxiren.getText().toString())) {
                    ToastUtils.showShort("请填写联系人");
                    return;
                }
                if (StringUtils.isTrimEmpty(mActivityYouhuizhanxiaoPhone.getText().toString())) {
                    ToastUtils.showShort("请填写联系方式");
                    return;
                }
                if (!RegexUtils.isMobileSimple(mActivityYouhuizhanxiaoPhone.getText().toString().trim())) {
                    ToastUtils.showShort("手机号码填写有误");
                    return;
                }
                if (StringUtils.isTrimEmpty(mActivityYouhuizhanxiaoCompanyname.getText().toString())) {
                    ToastUtils.showShort("请填写公司名称");
                    return;
                }
                if (StringUtils.equals("请选择", mActivityYouhuizhanxiaoChoiceAddress.getText().toString())) {
                    ToastUtils.showShort("请选择地址");
                    return;
                }
                if (StringUtils.isTrimEmpty(mActivityYouhuizhanxiaoCompanyAddress.getText().toString())) {
                    ToastUtils.showShort("请填写公司详细地址");
                    return;
                }
                goumai();

                break;
            case R.id.activity_youhuizhanxiao_cancel:
                finish();
                break;
            case R.id.activity_youhuizhanxiao_choiceAddress:
                choiceAddress(mActivityYouhuizhanxiaoChoiceAddress);
                break;
        }
    }

    private int xuyaoyangpin = 1;

    private void goumai() {

        HttpParams paras = new HttpParams();
        paras.put("sign", SPUtils.getInstance().getString("sign"));

        paras.put("salesId", id);

        paras.put("num", mActivityYouhuizhanxiaoBuyGoumailiang.getText().toString());

        paras.put("numUnit", numUnit);

        paras.put("companyName", mActivityYouhuizhanxiaoCompanyname.getText().toString());

        paras.put("contact", mActivityYouhuizhanxiaoLianxiren.getText().toString());

        paras.put("phone", mActivityYouhuizhanxiaoPhone.getText().toString());

        paras.put("province", locationProvince);

        paras.put("city", locationCity);

        paras.put("address", locationProvince + locationCity);

        paras.put("isSendSample", xuyaoyangpin);
        paras.put("from",getResources().getString(R.string.app_android));

        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.YOUHUIZHANXIAOBUY)
                .tag(this)
                .params(paras);


        DialogStringCallback stringCallback = new DialogStringCallback(YouhuizhanxiaoBuyActivity.this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("YOUHUIZHANXIAOBUY", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            ToastUtils.showShort(msg);
                            finish();
                            EventBus.getDefault().post(new MessageBean(MessageBean.Code.YOUHUIZHANXIAOGOUMAICHENGGONG));
                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(YouhuizhanxiaoBuyActivity.this, request, this);
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


    //省
    private String locationProvince;

    //市
    private String locationCity;

    //选择地址
    private void choiceAddress(final TextView address) {
        AddressPickTask task = new AddressPickTask(this);
        task.setHideProvince(false);
        task.setHideCounty(true);

        task.setCallback(new AddressPickTask.Callback() {
            @Override
            public void onAddressInitFailed() {
                ToastUtils.showShort("初始化失败！");
            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {
                if (county == null) {
                    address.setText(province.getAreaName() + " " + city.getAreaName());
                    locationProvince = province.getAreaName();
                    locationCity = city.getAreaName();
                } else {
                    address.setText(province.getAreaName() + city.getAreaName() + county.getAreaName());
                }
            }
        });
        task.execute();
    }
}
