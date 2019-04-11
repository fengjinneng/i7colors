package com.company.qcy.ui.activity.kaifangshangcheng;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogBitmapcallback;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.Utils.SoftKeyBoardUtils;
import com.company.qcy.Utils.yanzhengma.SeparatedEditText;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;

import uk.co.senab.photoview.PhotoView;

public class QiyezizhiActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private ImageView mActivityQiyezizhiVerifycode;
    /**
     * 换一张
     */
    private TextView mActivityQiyezizhiChange;
    private SeparatedEditText mSeparatedEditText;
    /**
     * 确定
     */
//    private Button mActivityQiyezizhiSubmit;
    private PhotoView mActivityQiyezizhiImg;
    private NestedScrollView mActivityQiyezizhiScrollView;
    private String busInformation;//企业资质图片
    /**
     * 查询有误,请联系平台处理
     */
    private TextView mActivityQiyezizhiPhoneText;
    /**
     * 400-920-8599
     */
    private TextView mActivityQiyezizhiPhone;

    private boolean firstIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qiyezizhi);
        busInformation = getIntent().getStringExtra("busInformation");


        initView();
    }

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mActivityQiyezizhiVerifycode = (ImageView) findViewById(R.id.activity_qiyezizhi_verifycode);
        mActivityQiyezizhiChange = (TextView) findViewById(R.id.activity_qiyezizhi_change);
        mActivityQiyezizhiChange.setOnClickListener(this);
        mSeparatedEditText = (SeparatedEditText) findViewById(R.id.separatedEditText);
//        mActivityQiyezizhiSubmit = (Button) findViewById(R.id.activity_qiyezizhi_submit);
//        mActivityQiyezizhiSubmit.setOnClickListener(this);
        mToolbarTitle.setText("网店经营者营业执照信息");
        mActivityQiyezizhiPhoneText = (TextView) findViewById(R.id.activity_qiyezizhi_phone_text);
        mActivityQiyezizhiPhone = (TextView) findViewById(R.id.activity_qiyezizhi_phone);
        mActivityQiyezizhiVerifycode.setOnClickListener(this);

        getCaptcha();

        mActivityQiyezizhiImg = (PhotoView) findViewById(R.id.activity_qiyezizhi_img);
        mActivityQiyezizhiScrollView = (NestedScrollView) findViewById(R.id.activity_qiyezizhi_scrollView);

//        GlideUtils.loadImageRct(this, ServerInfo.IMAGE + busInformation, mActivityQiyezizhiImg);
//        mActivityQiyezizhiImg.setPivotX(mActivityQiyezizhiImg.getWidth() / 2);
//        mActivityQiyezizhiImg.setPivotY(mActivityQiyezizhiImg.getHeight() / 2);//支点在图片中心
//        mActivityQiyezizhiImg.setRotation(90);


        Glide.with(this).load(ServerInfo.IMAGE + busInformation).into(mActivityQiyezizhiImg);
        mSeparatedEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() >= 4) {
                    checkVerifycode();
                    SoftKeyBoardUtils.hideSoftKeyboard(QiyezizhiActivity.this);
                }

            }
        });

    }


    private void getCaptcha() {

        OkGo.<Bitmap>get(ServerInfo.SERVER + InterfaceInfo.CAPTCHA)
                .tag(this)
                .params("deviceNo", DeviceUtils.getAndroidID())
                .execute(new DialogBitmapcallback(this) {

                    @Override
                    public void onSuccess(Response<Bitmap> response) {
                        try {
                            if (response.code() == 200) {
                                mActivityQiyezizhiVerifycode.setImageBitmap(response.body());
                            } else {
                                ToastUtils.showShort("获取图片验证码失败！");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<Bitmap> response) {
                        super.onError(response);
                    }
                });

    }


    public void checkVerifycode() {

        PostRequest<String> stringPostRequest = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.CHECKCAPTCHA)
                .tag(this)
                .params("deviceNo", DeviceUtils.getAndroidID())
                .params("captcha", mSeparatedEditText.getText().toString());


        DialogStringCallback dialogStringCallback = new DialogStringCallback(this) {
            @Override
            public void onSuccess(Response<String> response) {
                try {
                    LogUtils.v("CHECKCAPTCHA", response.body());

                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            mActivityQiyezizhiScrollView.setVisibility(View.GONE);
                            if (StringUtils.isEmpty(busInformation)) {
                                mActivityQiyezizhiImg.setVisibility(View.GONE);
                                mActivityQiyezizhiPhone.setVisibility(View.VISIBLE);
                                mActivityQiyezizhiPhoneText.setVisibility(View.VISIBLE);
                            } else {
                                mActivityQiyezizhiImg.setVisibility(View.VISIBLE);
                                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
                            }
                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(QiyezizhiActivity.this, stringPostRequest, this);
                            return;
                        }
                        ToastUtils.showShort(jsonObject.getString("msg"));
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

        stringPostRequest.execute(dialogStringCallback);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_qiyezizhi_change:
                getCaptcha();
                break;
            case R.id.activity_qiyezizhi_verifycode:
                getCaptcha();
                break;
        }
    }
}
