package com.company.qcy.huodong.jingpai.activity;

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
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
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
import com.company.qcy.Utils.UserUtil;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.huodong.tuangou.activity.WoyaotuangouActivity;
import com.company.qcy.huodong.tuangou.bean.DefaultAddress;
import com.company.qcy.ui.activity.user.LoginActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

import org.greenrobot.eventbus.EventBus;

import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;

public class CanyujingpaiActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    /**
     * 请输入价格
     */
    private EditText mActivityCanyujingpaiPrice;
    /**
     * 请输入联系人
     */
    private EditText mActivityCanyujingpaiLianxiren;
    /**
     * 请输入联系电话
     */
    private EditText mActivityCanyujingpaiPhone;
    /**
     * 请输入公司名称
     */
    private EditText mActivityCanyujingpaiCompanyname;
    /**
     * 请输入公司详细地址
     */
    private EditText mActivityCanyujingpaiCompanyAddress;
    /**
     * 请选择
     */
    private TextView mActivityCanyujingpaiChoiceAddress;
    /**
     * 元
     */
    private TextView mActivityYouhuizhanxiaoNumunit;
    /**
     * 是
     */
    private RadioButton mActivityCanyujingpaiYes;
    /**
     * 否
     */
    private RadioButton mActivityCanyujingpaiNo;
    private RadioGroup mActivityCanyujingpaiYangpinGroup;

    private String auctionId;
    private String jiajiafudu;
    private String priceUnit;
    /**
     * TextView
     */
    private TextView mActivityCanyujingpaiMaxpriceAndJiajiafudu;
    /**
     * 取消
     */
    private TextView mActivityCanyujingpaiCancel;
    /**
     * 提交
     */
    private TextView mActivityCanyujingpaiSubmit;
    private String maxPrice;
    private String count;//加价次数
    private ConstraintLayout mActivityCanyujingpaiButtonLayout;
    private MyConsrantLayout mActivityCanyujingpaiRootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canyujingpai);
        if (UserUtil.isLogin()) {
        } else {
            ActivityUtils.startActivity(LoginActivity.class);
            finish();
        }
        auctionId = getIntent().getStringExtra("auctionId");
        jiajiafudu = getIntent().getStringExtra("jiajiafudu");
        priceUnit = getIntent().getStringExtra("priceUnit");
        maxPrice = getIntent().getStringExtra("maxPrice");
        count = getIntent().getStringExtra("count");
        initView();
    }

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mActivityCanyujingpaiPrice = (EditText) findViewById(R.id.activity_canyujingpai_price);
        mActivityCanyujingpaiLianxiren = (EditText) findViewById(R.id.activity_canyujingpai_lianxiren);
        mActivityCanyujingpaiPhone = (EditText) findViewById(R.id.activity_canyujingpai_phone);
        mActivityCanyujingpaiCompanyname = (EditText) findViewById(R.id.activity_canyujingpai_companyname);
        mActivityCanyujingpaiCompanyAddress = (EditText) findViewById(R.id.activity_canyujingpai_company_address);
        mActivityCanyujingpaiChoiceAddress = (TextView) findViewById(R.id.activity_canyujingpai_choiceAddress);
        mActivityYouhuizhanxiaoNumunit = (TextView) findViewById(R.id.activity_youhuizhanxiao_numunit);
        mActivityCanyujingpaiYes = (RadioButton) findViewById(R.id.activity_canyujingpai_yes);
        mActivityCanyujingpaiNo = (RadioButton) findViewById(R.id.activity_canyujingpai_no);
        mActivityCanyujingpaiYangpinGroup = (RadioGroup) findViewById(R.id.activity_canyujingpai_yangpin_group);
        mActivityCanyujingpaiButtonLayout = (ConstraintLayout) findViewById(R.id.activity_canyujingpai_button_layout);
        mActivityCanyujingpaiRootLayout = (MyConsrantLayout) findViewById(R.id.activity_canyujingpai_root_layout);

        mActivityCanyujingpaiYangpinGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.activity_canyujingpai_yes:
                        xuyaoyangpin = 1;
                        break;
                    case R.id.activity_canyujingpai_no:
                        xuyaoyangpin = 0;
                        break;
                }
            }
        });
        mActivityCanyujingpaiChoiceAddress.setOnClickListener(this);
        mToolbarTitle.setText("参与抢购");
        mActivityCanyujingpaiMaxpriceAndJiajiafudu = (TextView) findViewById(R.id.activity_canyujingpai_maxprice_and_jiajiafudu);
        mActivityCanyujingpaiCancel = (TextView) findViewById(R.id.activity_canyujingpai_cancel);
        mActivityCanyujingpaiCancel.setOnClickListener(this);
        mActivityCanyujingpaiSubmit = (TextView) findViewById(R.id.activity_canyujingpai_submit);
        mActivityCanyujingpaiSubmit.setOnClickListener(this);
        mActivityCanyujingpaiMaxpriceAndJiajiafudu.setText("当前最高价:" + maxPrice + priceUnit + "\n"+"加价幅度:" + jiajiafudu + priceUnit);
        mActivityYouhuizhanxiaoNumunit.setText(priceUnit);

        mActivityCanyujingpaiPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 1) {

                        mActivityCanyujingpaiPrice.setText(s.toString().subSequence(0,
                                s.toString().indexOf(".") + 2));

                        mActivityCanyujingpaiPrice.setSelection(s.toString().trim().length() - 1
                        );
                    }
                }
                //这部分是处理如果用户输入以.开头，在前面加上0
                if (s.toString().trim().substring(0).equals(".")) {

                    mActivityCanyujingpaiPrice.setText("0" + s);
                    mActivityCanyujingpaiPrice.setSelection(2);
                }
                //这里处理用户 多次输入.的处理 比如输入 1..6的形式，是不可以的
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        mActivityCanyujingpaiPrice.setText(s.subSequence(0, 1));
                        mActivityCanyujingpaiPrice.setSelection(1);
                        return;
                    }
                }
            }
        });

        mActivityCanyujingpaiRootLayout.setListener(new InputWindowListener() {
            @Override
            public void show() {
                mActivityCanyujingpaiButtonLayout.setVisibility(View.GONE);
            }

            @Override
            public void hidden() {
                mActivityCanyujingpaiButtonLayout.setVisibility(View.VISIBLE);
            }
        });


        getDefaultAddress();

    }

    private DefaultAddress defaultAddress;

    private void getDefaultAddress() {

        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.HUODONGMORENDIZHI)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("token", SPUtils.getInstance().getString("token"));

        DialogStringCallback stringCallback = new DialogStringCallback(this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("HUODONGMORENDIZHI", response.body());
                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            if(ObjectUtils.isEmpty(data)){return;}
                            defaultAddress = data.toJavaObject(DefaultAddress.class);
                            setInfo(defaultAddress);
                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(CanyujingpaiActivity.this, request, this);
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

    private void setInfo(DefaultAddress defaultAddress) {

        if(ObjectUtils.isEmpty(defaultAddress)){
            return;
        }

        if(!StringUtils.isEmpty(defaultAddress.getContact())){
            mActivityCanyujingpaiLianxiren.setText(defaultAddress.getContact());
        }


        if(!StringUtils.isEmpty(defaultAddress.getPhone())){
            mActivityCanyujingpaiPhone.setText(defaultAddress.getPhone());
        }


        if(!StringUtils.isEmpty(defaultAddress.getCompanyName())){
            mActivityCanyujingpaiCompanyname.setText(defaultAddress.getCompanyName());
        }


        if(!StringUtils.isEmpty(defaultAddress.getProvince())||!StringUtils.isEmpty(defaultAddress.getCity())){
            mActivityCanyujingpaiChoiceAddress.setText(defaultAddress.getProvince()+" "+defaultAddress.getCity());
            locationProvince = defaultAddress.getProvince();
            locationCity = defaultAddress.getCity();
        }

        if(!StringUtils.isEmpty(defaultAddress.getAddress())){
            mActivityCanyujingpaiCompanyAddress.setText(defaultAddress.getAddress());
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
            case R.id.activity_canyujingpai_choiceAddress:
                choiceAddress(mActivityCanyujingpaiChoiceAddress);
                break;
            case R.id.activity_canyujingpai_cancel:
                finish();

                break;
            case R.id.activity_canyujingpai_submit:
                if (StringUtils.isTrimEmpty(mActivityCanyujingpaiPrice.getText().toString())) {
                    ToastUtils.showShort("请填写价格");
                    return;
                }
//                mActivityCanyujingpaiPrice.getText().toString()
                String price = mActivityCanyujingpaiPrice.getText().toString();

                if (Double.parseDouble(price) < Double.parseDouble(maxPrice)) {
                    ToastUtils.showShort("报价不能低于最高价格!");
                    return;
                }

                if (Double.parseDouble(price) == Double.parseDouble(maxPrice)) {
                    if (!StringUtils.equals("0", count)) {
                        ToastUtils.showShort("报价不能等于最高价格!");
                        return;
                    }
                }
                if ((Double.parseDouble(price) - Double.parseDouble(maxPrice)) % Double.parseDouble(jiajiafudu) != 0) {
                    ToastUtils.showShort("加价幅度为" + jiajiafudu + "的整数倍");
                    return;
                }

                if (StringUtils.isTrimEmpty(mActivityCanyujingpaiLianxiren.getText().toString())) {
                    ToastUtils.showShort("请填写联系人");
                    return;
                }
                if (StringUtils.isTrimEmpty(mActivityCanyujingpaiPhone.getText().toString())) {
                    ToastUtils.showShort("请填写联系方式");
                    return;
                }
                if (!RegexUtils.isMobileSimple(mActivityCanyujingpaiPhone.getText().toString().trim())) {
                    ToastUtils.showShort("手机号码填写有误");
                    return;
                }
                if (StringUtils.isTrimEmpty(mActivityCanyujingpaiCompanyname.getText().toString())) {
                    ToastUtils.showShort("请填写公司名称");
                    return;
                }
                if (StringUtils.equals("请选择", mActivityCanyujingpaiChoiceAddress.getText().toString())) {
                    ToastUtils.showShort("请选择地址");
                    return;
                }
                canyujingpai();
                break;
        }
    }


    //省
    private String locationProvince;
    //市
    private String locationCity;
    private int xuyaoyangpin = 1;

    private void canyujingpai() {

        HttpParams paras = new HttpParams();
        paras.put("sign", SPUtils.getInstance().getString("sign"));
        paras.put("token",SPUtils.getInstance().getString("token"));
        paras.put("auctionId", auctionId);
        paras.put("contact", mActivityCanyujingpaiLianxiren.getText().toString());
        paras.put("phone", mActivityCanyujingpaiPhone.getText().toString());
        paras.put("companyName", mActivityCanyujingpaiCompanyname.getText().toString());
        paras.put("price", mActivityCanyujingpaiPrice.getText().toString());
        paras.put("priceUnit", priceUnit);
        paras.put("province", locationProvince);
        paras.put("city", locationCity);
        paras.put("address", mActivityCanyujingpaiCompanyAddress.getText().toString());
        paras.put("isSendSample", xuyaoyangpin);
        paras.put("from", getResources().getString(R.string.app_android));

        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.CANYUJINGPAI)
                .tag(this)
                .params(paras);

        DialogStringCallback stringCallback = new DialogStringCallback(CanyujingpaiActivity.this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("CANYUJINGPAI", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            ToastUtils.showShort(msg);
                            finish();
                            EventBus.getDefault().post(new MessageBean(MessageBean.Code.TCANYUJINGPAICHENGGONG));
                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(CanyujingpaiActivity.this, request, this);
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
