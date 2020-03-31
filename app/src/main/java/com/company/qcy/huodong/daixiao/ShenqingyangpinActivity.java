package com.company.qcy.huodong.daixiao;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
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
import com.company.qcy.huodong.daixiao.bean.DaixiaoBean;
import com.company.qcy.huodong.tuangou.bean.DefaultAddress;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

import org.greenrobot.eventbus.EventBus;

import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;

public class ShenqingyangpinActivity extends BaseActivity implements View.OnClickListener {

    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    /**
     * 请输入联系人
     */
    private EditText mActivitySuoquyangpinLianxiren;
    /**
     * 请输入联系人手机号
     */
    private EditText mActivitySuoquyangpinPhone;
    /**
     * 请输入公司名称
     */
    private EditText mActivitySuoquyangpinCompanyname;
    /**
     * 请输入公司详细地址
     */
    private EditText mActivitySuoquyangpinCompanyAddress;
    /**
     * 提交
     */
    private TextView mActivitySuoquyangpinSubmit;
    /**
     * 取消
     */
    private TextView mActivitySuoquyangpinCancel;
    /**
     * 请选择
     */
    private TextView mActivitySuoquyangpinChoiceAddress;
    private TextView mActivitySuoquyangpinDescribe;
    /**
     * 我的购买量:
     */
    private TextView mActivitySuoquyangpinGoumailiangText;
    /**
     * 填写数字
     */
    private EditText mActivitySuoquyangpinGoumailiang;
    private TextView mActivitySuoquyangpinDanwei;
    private TextView mActivitySuoquyangpinLimit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shenqingyangpin);
        initView();
    }

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mActivitySuoquyangpinLianxiren = (EditText) findViewById(R.id.activity_suoquyangpin_lianxiren);
        mActivitySuoquyangpinPhone = (EditText) findViewById(R.id.activity_suoquyangpin_phone);
        mActivitySuoquyangpinCompanyname = (EditText) findViewById(R.id.activity_suoquyangpin_companyname);
        mActivitySuoquyangpinCompanyAddress = (EditText) findViewById(R.id.activity_suoquyangpin_company_address);
        mActivitySuoquyangpinSubmit = (TextView) findViewById(R.id.activity_suoquyangpin_submit);
        mActivitySuoquyangpinSubmit.setOnClickListener(this);
        mActivitySuoquyangpinCancel = (TextView) findViewById(R.id.activity_suoquyangpin_cancel);
        mActivitySuoquyangpinCancel.setOnClickListener(this);
        mActivitySuoquyangpinChoiceAddress = (TextView) findViewById(R.id.activity_suoquyangpin_choiceAddress);
        mActivitySuoquyangpinChoiceAddress.setOnClickListener(this);


        mActivitySuoquyangpinDescribe = (TextView) findViewById(R.id.activity_suoquyangpin_describe);
        mActivitySuoquyangpinGoumailiangText = (TextView) findViewById(R.id.activity_suoquyangpin_goumailiang_text);
        mActivitySuoquyangpinGoumailiang = (EditText) findViewById(R.id.activity_suoquyangpin_goumailiang);
        mActivitySuoquyangpinDanwei = (TextView) findViewById(R.id.activity_suoquyangpin_danwei);
        mActivitySuoquyangpinLimit = (TextView) findViewById(R.id.activity_suoquyangpin_limit);

        init();

    }

    private DaixiaoBean daixiaoBean;
    private DefaultAddress defaultAddress;
    private String buyType;


    private void init() {

        daixiaoBean = getIntent().getParcelableExtra("daixiaoBean");
        buyType = getIntent().getStringExtra("buyType");

        if (!StringUtils.isEmpty(buyType)) {

            if (StringUtils.equals("buy", buyType)) {
                mToolbarTitle.setText("我要购买");
                mActivitySuoquyangpinDanwei.setText(daixiaoBean.getNumUnit());
                mActivitySuoquyangpinLimit.setText("0" + daixiaoBean.getNumUnit() + " <购买量<= " + daixiaoBean.getRemainNum() + daixiaoBean.getNumUnit());
                mActivitySuoquyangpinDescribe.setText("购买提交后，平台客服人员将在1个工作日内与您电话联系。确认最终交易细节，签订合同！");

            } else if (StringUtils.equals("sample", buyType)) {
                mToolbarTitle.setText("索取样品");
                mActivitySuoquyangpinGoumailiangText.setVisibility(View.GONE);
                mActivitySuoquyangpinGoumailiang.setVisibility(View.GONE);
                mActivitySuoquyangpinDanwei.setVisibility(View.GONE);
                mActivitySuoquyangpinLimit.setVisibility(View.GONE);
                mActivitySuoquyangpinDescribe.setText("索要样品后，平台客服人员将在1个工作日内与您电话联系，安排寄样！");
            }
        }

        mActivitySuoquyangpinGoumailiang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //删除.后面超过两位的数字
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        mActivitySuoquyangpinGoumailiang.setText(s);
                        mActivitySuoquyangpinGoumailiang.setSelection(s.length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s == null) {
                    return;
                }
                if (s.length() == 2) {
                    if (s.toString().startsWith("0") && !s.toString().substring(1).equals(".")) {
                        mActivitySuoquyangpinGoumailiang.setText(s.toString().substring(1));
                        mActivitySuoquyangpinGoumailiang.setSelection(mActivitySuoquyangpinGoumailiang.getText().length());
                    }
                }

                // 以小数点开头，前面自动加上 "0"
                if (s.toString().startsWith(".")) {
                    mActivitySuoquyangpinGoumailiang.setText("0" + s);
                    mActivitySuoquyangpinGoumailiang.setSelection(mActivitySuoquyangpinGoumailiang.getText().length());
                } else {
                    if (s.length() >= 1 && Double.parseDouble(s.toString()) > daixiaoBean.getRemainNum().doubleValue()) {
                        ToastUtils.showShort("购买量不能大于库存量！");
                    }
                }

            }
        });

        getDefaultAddress();
    }


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
                            if (ObjectUtils.isEmpty(data)) {
                                return;
                            }
                            defaultAddress = data.toJavaObject(DefaultAddress.class);
                            setInfo();
                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(ShenqingyangpinActivity.this, request, this);
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

    private void setInfo() {

        if (ObjectUtils.isEmpty(defaultAddress)) {
            return;
        }

        if (!StringUtils.isEmpty(defaultAddress.getContact())) {
            mActivitySuoquyangpinLianxiren.setText(defaultAddress.getContact());
        }


        if (!StringUtils.isEmpty(defaultAddress.getPhone())) {
            mActivitySuoquyangpinPhone.setText(defaultAddress.getPhone());
        }


        if (!StringUtils.isEmpty(defaultAddress.getCompanyName())) {
            mActivitySuoquyangpinCompanyname.setText(defaultAddress.getCompanyName());
        }


        if (!StringUtils.isEmpty(defaultAddress.getProvince()) || !StringUtils.isEmpty(defaultAddress.getCity())) {
            mActivitySuoquyangpinChoiceAddress.setText(defaultAddress.getProvince() + " " + defaultAddress.getCity());
            locationProvince = defaultAddress.getProvince();
            locationCity = defaultAddress.getCity();
        }

        if (!StringUtils.isEmpty(defaultAddress.getAddress())) {
            mActivitySuoquyangpinCompanyAddress.setText(defaultAddress.getAddress());
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
            case R.id.activity_suoquyangpin_cancel:
                finish();
                break;
            case R.id.activity_suoquyangpin_submit:
                if (checkNull()) {
                    updata();
                }
                break;
            case R.id.activity_suoquyangpin_choiceAddress:

                choiceAddress(mActivitySuoquyangpinChoiceAddress);
                break;
        }
    }

    private void updata() {

        HttpParams paras = new HttpParams();
        paras.put("sign", SPUtils.getInstance().getString("sign"));
        paras.put("token", SPUtils.getInstance().getString("token"));
        paras.put("proxyMarketId", daixiaoBean.getId());
        paras.put("proxyMarketUpdateId", daixiaoBean.getProxyMarketUpdateId());

        paras.put("phone", mActivitySuoquyangpinPhone.getText().toString());

        paras.put("contact", mActivitySuoquyangpinLianxiren.getText().toString());

        paras.put("province", locationProvince);

        paras.put("city", locationCity);

        paras.put("companyName", mActivitySuoquyangpinCompanyname.getText().toString());

        paras.put("address", mActivitySuoquyangpinCompanyAddress.getText().toString());

        paras.put("numUnit", daixiaoBean.getNumUnit());

        paras.put("from", getResources().getString(R.string.app_android));

        paras.put("price", daixiaoBean.getPrice());

        paras.put("priceUnit", daixiaoBean.getPriceUnit());

        if (!StringUtils.isEmpty(buyType)) {

            if (StringUtils.equals("buy", buyType)) {
                paras.put("buyType", "buy");
                paras.put("num", mActivitySuoquyangpinGoumailiang.getText().toString());

            } else if (StringUtils.equals("sample", buyType)) {
                paras.put("buyType", "sample");
            }
        }

        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.DAIXIAOSUOYANG)
                .tag(this)
                .params(paras);

        DialogStringCallback stringCallback = new DialogStringCallback(ShenqingyangpinActivity.this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("DAIXIAOSUOYANG", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            ToastUtils.showShort("提交成功!");
                            EventBus.getDefault().post(new MessageBean(MessageBean.Code.DAIXIAOBUYSUCESS));
                            finish();
                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(ShenqingyangpinActivity.this, request, this);
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

    private String locationProvince;
    private String locationCity;

    private boolean checkNull() {

        if (!StringUtils.isEmpty(buyType)) {
            if (StringUtils.equals("buy", buyType)) {
                if (StringUtils.isTrimEmpty(mActivitySuoquyangpinGoumailiang.getText().toString())) {
                    ToastUtils.showShort("请填写购买量！");
                    return false;
                }
                if (Double.parseDouble(mActivitySuoquyangpinGoumailiang.getText().toString()) > daixiaoBean.getRemainNum().doubleValue()) {
                    ToastUtils.showShort("购买量不能大于库存量！");
                    return false;
                }

                if (StringUtils.equals(mActivitySuoquyangpinGoumailiang.getText().toString().trim(), "0.") ||
                        Double.parseDouble(mActivitySuoquyangpinGoumailiang.getText().toString()) == 0) {
                    ToastUtils.showShort("请填写正确的购买量！");
                    return false;
                }

            }
        }

        if (StringUtils.isTrimEmpty(mActivitySuoquyangpinLianxiren.getText().toString())) {
            ToastUtils.showShort("联系人不能为空！");
            return false;
        }

        if (StringUtils.isTrimEmpty(mActivitySuoquyangpinPhone.getText().toString())) {
            ToastUtils.showShort("手机号不能为空！");
            return false;
        }

        if (!RegexUtils.isMobileSimple((mActivitySuoquyangpinPhone.getText().toString()))) {
            ToastUtils.showShort("手机号格式有误！");
            return false;
        }

        if (StringUtils.isTrimEmpty(mActivitySuoquyangpinCompanyname.getText().toString())) {
            ToastUtils.showShort("公司名称不能为空！");
            return false;
        }

        if (StringUtils.isTrimEmpty(locationProvince)) {
            ToastUtils.showShort("请选择地址！");
            return false;
        }

        if (StringUtils.isTrimEmpty(locationCity)) {
            ToastUtils.showShort("请选择地址！");
            return false;
        }

        return true;

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
