package com.company.qcy.ui.activity.tuangou;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.AddressPickTask;

import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;

public class WoyaotuangouActivity extends AppCompatActivity implements View.OnClickListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woyaotuangou);
        initView();
    }

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


                break;
            case R.id.activity_woyaotuangou_cancel:
                break;
            case R.id.activity_woyaotuangou_choiceAddress:
                choiceAddress(mActivityWoyaotuangouChoiceAddress);
                break;
        }
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
