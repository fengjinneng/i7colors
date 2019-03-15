package com.company.qcy.huodong.tuangou.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.AddressPickTask;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.huodong.tuangou.bean.TuangouBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;

import org.greenrobot.eventbus.EventBus;

import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;

public class WoyaotuangouActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    /**
     * 设置
     */
    private TextView mToolbarText;
    private RelativeLayout mToolbarLayout;
    private ImageView mImageView4;
    /**
     * 提交成功后系统将自动帮您生成唯一的“团购编号”，可在“参 与团购记录详细”中查看！
     */
    private TextView mTextView82;
    private ConstraintLayout mConstraintLayout12;
    /**
     * 我的认领量:
     */
    private TextView mTextView95;
    /**
     * 联系人:
     */
    private TextView mTextView106;
    /**
     * 联系人方式:
     */
    private TextView mTextView108;
    /**
     * 公司名称:
     */
    private TextView mTextView113;
    /**
     * 公司所属区域:
     */
    private TextView mTextView115;
    /**
     * 公司详细地址:
     */
    private TextView mTextView119;
    private EditText mActivityWoyaotuangouRenlingliang;
    /**
     * 请输入联系人
     */
    private EditText mActivityWoyaotuangouLianxiren;
    /**
     * 请输入联系方式
     */
    private EditText mActivityWoyaotuangouPhone;
    /**
     * 请输入公司名称
     */
    private EditText mActivityWoyaotuangouCompanyname;
    /**
     * 请输入公司详细地址
     */
    private EditText mActivityWoyaotuangouCompanyAddress;
    /**
     * 提交
     */
    private TextView mActivityWoyaotuangouSubmit;
    /**
     * 取消
     */
    private TextView mActivityWoyaotuangouCancel;
    /**
     * 0.5吨\u003C认领量\u003C1吨
     */
    private TextView mActivityWoyaotuangouFanwei;
    /**
     * 请选择
     */
    private TextView mActivityWoyaotuangouChoiceAddress;
    private TuangouBean bean;
    /**
     * 推荐人英雄码
     */
    private EditText mActivityWoyaotuangouYinxiongma;
    /**
     * 查看使用说明
     */
    private TextView mActivityWoyaotuangouYinxiongmaShuoming;
    private RadioGroup mActivityWoyaotuangouYangpinGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woyaotuangou);
        bean = getIntent().getParcelableExtra("bean");
        initView();
    }

    private int xuyaoyangpin = 1;

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarText = (TextView) findViewById(R.id.toolbar_text);
        mToolbarLayout = (RelativeLayout) findViewById(R.id.toolbar_layout);
        mImageView4 = (ImageView) findViewById(R.id.imageView4);
        mTextView82 = (TextView) findViewById(R.id.textView82);
        mConstraintLayout12 = (ConstraintLayout) findViewById(R.id.constraintLayout12);
        mTextView95 = (TextView) findViewById(R.id.textView95);
        mTextView106 = (TextView) findViewById(R.id.textView106);
        mTextView108 = (TextView) findViewById(R.id.textView108);
        mTextView113 = (TextView) findViewById(R.id.textView113);
        mTextView115 = (TextView) findViewById(R.id.textView115);
        mTextView119 = (TextView) findViewById(R.id.textView119);
        mActivityWoyaotuangouRenlingliang = (EditText) findViewById(R.id.activity_woyaotuangou_renlingliang);
        mActivityWoyaotuangouRenlingliang.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                if (text.contains(".")) {
                    int index = text.indexOf(".");
                    if (index + 1 == 6) {
                        text = text.substring(0, index);
                        mActivityWoyaotuangouRenlingliang.setText(text);
                        mActivityWoyaotuangouRenlingliang.setSelection(text.length());
                    } else if (index + 2 < text.length()) {
                        text = text.substring(0, index + 2);
                        mActivityWoyaotuangouRenlingliang.setText(text);
                        mActivityWoyaotuangouRenlingliang.setSelection(text.length());
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s == null) {
                    return;
                }
                if(s.length()==2){
                    if(s.toString().startsWith("0")&&!s.toString().substring(1).equals(".")){
                        mActivityWoyaotuangouRenlingliang.setText(s.toString().substring(1));
                        mActivityWoyaotuangouRenlingliang.setSelection(mActivityWoyaotuangouRenlingliang.getText().length());
                    }
                }

                // 以小数点开头，前面自动加上 "0"
                if (s.toString().startsWith(".")) {
                    mActivityWoyaotuangouRenlingliang.setText("0" + s);
                    mActivityWoyaotuangouRenlingliang.setSelection(mActivityWoyaotuangouRenlingliang.getText().length());
                }

            }
        });
        mActivityWoyaotuangouLianxiren = (EditText) findViewById(R.id.activity_woyaotuangou_lianxiren);
        mActivityWoyaotuangouPhone = (EditText) findViewById(R.id.activity_woyaotuangou_phone);
        mActivityWoyaotuangouCompanyname = (EditText) findViewById(R.id.activity_woyaotuangou_companyname);
        mActivityWoyaotuangouCompanyAddress = (EditText) findViewById(R.id.activity_woyaotuangou_company_address);
        mActivityWoyaotuangouSubmit = (TextView) findViewById(R.id.activity_woyaotuangou_submit);
        mActivityWoyaotuangouCancel = (TextView) findViewById(R.id.activity_woyaotuangou_cancel);
        mActivityWoyaotuangouFanwei = (TextView) findViewById(R.id.activity_woyaotuangou_fanwei);
        mActivityWoyaotuangouSubmit.setOnClickListener(this);
        mActivityWoyaotuangouCancel.setOnClickListener(this);
        mActivityWoyaotuangouChoiceAddress = (TextView) findViewById(R.id.activity_woyaotuangou_choiceAddress);
        mActivityWoyaotuangouChoiceAddress.setOnClickListener(this);
        mActivityWoyaotuangouYinxiongma = (EditText) findViewById(R.id.activity_woyaotuangou_yinxiongma);
        mActivityWoyaotuangouYinxiongmaShuoming = (TextView) findViewById(R.id.activity_woyaotuangou_yinxiongma_shuoming);
        mActivityWoyaotuangouYinxiongmaShuoming.setOnClickListener(this);
        mActivityWoyaotuangouYangpinGroup = (RadioGroup) findViewById(R.id.activity_woyaotuangou_yangpin_group);
        mActivityWoyaotuangouYangpinGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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
        mToolbarBack.setOnClickListener(this);
        mToolbarTitle.setText("我要团购");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.activity_woyaotuangou_submit:
                if (StringUtils.isTrimEmpty(mActivityWoyaotuangouRenlingliang.getText().toString())) {
                    ToastUtils.showShort("请填写认领量");
                    return;
                } else if (Float.parseFloat(mActivityWoyaotuangouRenlingliang.getText().toString()) < Float.parseFloat(bean.getMinNum())) {
                    ToastUtils.showShort("认领量不能小于最少认领量");
                    return;
                } else if (StringUtils.isTrimEmpty(mActivityWoyaotuangouLianxiren.getText().toString())) {
                    ToastUtils.showShort("请填写联系人");
                    return;
                } else if (StringUtils.isTrimEmpty(mActivityWoyaotuangouPhone.getText().toString())) {
                    ToastUtils.showShort("请填写联系方式");
                    return;
                } else if (!RegexUtils.isMobileSimple(mActivityWoyaotuangouPhone.getText().toString().trim())) {
                    ToastUtils.showShort("手机号码填写有误");
                    return;
                } else if (StringUtils.isTrimEmpty(mActivityWoyaotuangouCompanyname.getText().toString())) {
                    ToastUtils.showShort("请填写公司名称");
                    return;
                } else if (StringUtils.equals("请选择", mActivityWoyaotuangouChoiceAddress.getText().toString())) {
                    ToastUtils.showShort("请选择地址");
                    return;
                } else if (StringUtils.isTrimEmpty(mActivityWoyaotuangouCompanyAddress.getText().toString())) {
                    ToastUtils.showShort("请填写公司详细地址");
                    return;
                }
                fabutuangou();

                break;
            case R.id.activity_woyaotuangou_cancel:
                finish();
                break;
            case R.id.activity_woyaotuangou_choiceAddress:
                choiceAddress(mActivityWoyaotuangouChoiceAddress);
                break;
            case R.id.activity_woyaotuangou_yinxiongma_shuoming:
                ActivityUtils.startActivity(YingxiongmashuomingActivity.class);
                break;
            case R.id.toolbar_back:
                finish();
                break;
        }
    }

    //省
    private String locationProvince;

    //市
    private String locationCity;


    private void fabutuangou() {

        HttpParams paras = new HttpParams();
        paras.put("sign", SPUtils.getInstance().getString("sign"));

        paras.put("mainId", bean.getId());

        paras.put("phone", mActivityWoyaotuangouPhone.getText().toString());

        paras.put("contact", mActivityWoyaotuangouLianxiren.getText().toString());

        paras.put("companyName", mActivityWoyaotuangouCompanyname.getText().toString());

        paras.put("num", mActivityWoyaotuangouRenlingliang.getText().toString());

        paras.put("numUnit", bean.getNumUnit());

        paras.put("province", locationProvince);

        paras.put("city", locationCity);

        paras.put("address", locationProvince + locationCity);

        paras.put("isSendSample", xuyaoyangpin);

        paras.put("invitationCode", mActivityWoyaotuangouYinxiongma.getText().toString());
        paras.put("from", "app");

        paras.put("from",getResources().getString(R.string.app_android));

        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.WOYAOTUANGOU)
                .tag(this)
                .params(paras);


        DialogStringCallback stringCallback = new DialogStringCallback(WoyaotuangouActivity.this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("WOYAOTUANGOU", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            ToastUtils.showShort(msg);
                            finish();
                            EventBus.getDefault().post(new MessageBean(MessageBean.Code.TUANGOUCHENGGONG));
                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(WoyaotuangouActivity.this,request,this);
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
