package com.company.qcy.ui.activity.qiugoudating;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.CalendarUtil;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.bean.eventbus.MessageBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;

import cn.qqtheme.framework.picker.DatePicker;

public class CanyubaojiaActivity extends AppCompatActivity implements View.OnClickListener {


    /**
     * 分散染料 七彩云 分散红FB 200%
     */
    private TextView mActivityCanyubaojiaChanpinming;
    /**
     * 报价方:
     */
    private TextView mActivityCanyubaojiaCompanyText;
    /**
     * 绍兴百丽恒印染有限公司
     */
    private TextView mActivityCanyubaojiaCompany;
    private EditText mActivityCanyubaojiaPhone;
    private EditText mActivityCanyubaojiaPrice;
    /**
     * 请选择
     */
    private TextView mActivityCanyubaojiaTime;
    /**
     * 是
     */
    private RadioButton mActivityCanyubaojiaRadioYes;
    /**
     * 否
     */
    private RadioButton mActivityCanyubaojiaRadioNo;
    private RadioGroup mActivityCanyubaojiaRadioGroup;
    /**
     * 提交报价
     */
    private Button mActivityCanyubaojiaFabubaojia;


    private Long enquiryId;
    /**
     * 填写50字以内的报价说明...
     */
    private EditText mActivityCanyubaojiaDescription;
    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canyubaojia);
        enquiryId = getIntent().getLongExtra("enquiryId", 0);
        initView();
    }

    private void initView() {

        mActivityCanyubaojiaChanpinming = (TextView) findViewById(R.id.activity_canyubaojia_chanpinming);
        mActivityCanyubaojiaCompanyText = (TextView) findViewById(R.id.activity_canyubaojia_company_text);
        mActivityCanyubaojiaCompany = (TextView) findViewById(R.id.activity_canyubaojia_company);
        mActivityCanyubaojiaPhone = (EditText) findViewById(R.id.activity_canyubaojia_phone);
        mActivityCanyubaojiaPrice = (EditText) findViewById(R.id.activity_canyubaojia_price);
        mActivityCanyubaojiaTime = (TextView) findViewById(R.id.activity_canyubaojia_time);
        mActivityCanyubaojiaTime.setOnClickListener(this);
        mActivityCanyubaojiaRadioYes = (RadioButton) findViewById(R.id.activity_canyubaojia_radio_yes);
        mActivityCanyubaojiaRadioNo = (RadioButton) findViewById(R.id.activity_canyubaojia_radio_no);
        mActivityCanyubaojiaRadioGroup = (RadioGroup) findViewById(R.id.activity_canyubaojia_radio_group);


        mActivityCanyubaojiaRadioYes.setOnClickListener(this);
        mActivityCanyubaojiaRadioNo.setOnClickListener(this);

        if (SPUtils.getInstance().getBoolean("isCompany")) {
            mActivityCanyubaojiaCompany.setEnabled(false);
            mActivityCanyubaojiaCompany.setText(SPUtils.getInstance().getString("companyName"));
            mActivityCanyubaojiaCompany.setBackgroundColor(getResources().getColor(R.color.baise));
        } else {
            mActivityCanyubaojiaCompanyText.setText("我的公司名称:");
        }
        mActivityCanyubaojiaFabubaojia = (Button) findViewById(R.id.activity_canyubaojia_fabubaojia);
        mActivityCanyubaojiaFabubaojia.setOnClickListener(this);
        mActivityCanyubaojiaDescription = (EditText) findViewById(R.id.activity_canyubaojia_description);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mToolbarTitle.setText("参与报价");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.activity_canyubaojia_fabubaojia:
                if (checkNull()) {
                    tijiaobaojia();
                }
                break;
            case R.id.activity_canyubaojia_radio_yes:
                isCheckRadioButton = 1;
                break;
            case R.id.activity_canyubaojia_radio_no:
                isCheckRadioButton = 0;
                break;
            case R.id.activity_canyubaojia_time:

                onYearMonthDayPicker(mActivityCanyubaojiaTime);
                break;
            case R.id.toolbar_back:
                finish();
                break;
        }
    }


    //选择年月
    private void onYearMonthDayPicker(final TextView time) {
        final DatePicker picker = new DatePicker(this);
        picker.setCycleDisable(true);
        picker.setTopPadding(15);
        picker.setRangeStart(CalendarUtil.getYear(), CalendarUtil.getMonth(), CalendarUtil.getDay());
        picker.setRangeEnd(2020, 1, 1);
        picker.setLineColor(Color.BLACK);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                mActivityCanyubaojiaTime.setText(year + "-" + month + "-" + day);
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }


    private void tijiaobaojia() {


        HttpParams paras = new HttpParams();
        paras.put("sign", SPUtils.getInstance().getString("sign"));
        paras.put("token", SPUtils.getInstance().getString("token"));
        if (StringUtils.isEmpty(SPUtils.getInstance().getString("companyName"))) {
            paras.put("companyName2", mActivityCanyubaojiaCompany.getText().toString());
        }
        paras.put("enquiryId", enquiryId);
        paras.put("offerTime", TimeUtils.getNowString());
        paras.put("price", mActivityCanyubaojiaPrice.getText().toString());
        paras.put("priceUnit", "KG");
        paras.put("isIncludeTrans", isCheckRadioButton);
        paras.put("phone", mActivityCanyubaojiaPhone.getText().toString());
        paras.put("validTime", mActivityCanyubaojiaTime.getText().toString());
        paras.put("description", mActivityCanyubaojiaDescription.getText().toString());
        OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.FABUBAOJIA)
                .tag(this)
                .params(paras)
                .execute(new DialogStringCallback(CanyubaojiaActivity.this) {
                    @Override
                    public void onSuccess(Response<String> response) {

                        try {
                            LogUtils.v("tijiaobaojia", response.body());
                            if (response.code() == 200) {
                                JSONObject jsonObject = JSONObject.parseObject(response.body());

                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                    ToastUtils.showShort("您已经报价成功");
                                    ActivityUtils.finishActivity(CanyubaojiaActivity.class);
                                    EventBus.getDefault().post(new MessageBean(MessageBean.Code.BAOJIACHENGGONG));
                                    return;
                                }
                                SignAndTokenUtil.checkSignAndToken(CanyubaojiaActivity.this, jsonObject);

                            } else {

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });


    }

    private int isCheckRadioButton = -1;//默认没有选择


    private boolean checkNull() {

        if (StringUtils.isTrimEmpty(mActivityCanyubaojiaCompany.getText().toString())) {
            ToastUtils.showShort("请填写公司名称");
            return false;
        } else if (StringUtils.isTrimEmpty(mActivityCanyubaojiaPhone.getText().toString())) {
            ToastUtils.showShort("请填写联系电话");
            return false;
        } else if (StringUtils.isTrimEmpty(mActivityCanyubaojiaPrice.getText().toString())) {
            ToastUtils.showShort("请填写价格");
            return false;
        } else if (StringUtils.equals(mActivityCanyubaojiaTime.getText().toString(), "请选择")) {
            ToastUtils.showShort("请选择有效时间");
            return false;
        } else if (isCheckRadioButton == -1) {
            ToastUtils.showShort("请选择是否包含运费");
            return false;
        } else if (!RegexUtils.isTel(mActivityCanyubaojiaPhone.getText().toString())
                && !RegexUtils.isMobileSimple(mActivityCanyubaojiaPhone.getText().toString())) {
            ToastUtils.showShort("请填写正确的联系方式");
            return false;
        } else return true;
    }

}