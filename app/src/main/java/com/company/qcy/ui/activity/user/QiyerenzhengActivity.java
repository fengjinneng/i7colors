package com.company.qcy.ui.activity.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.MatisseImageUtil;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.ui.activity.pengyouquan.DavrenzhengActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zhihu.matisse.Matisse;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

public class QiyerenzhengActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private ImageView mActivityQiyerenzhengImg;
    /**
     * 提交申请
     */
    private Button mActivityQiyerenzhengCommit;
    /**
     * 请输入企业名称
     */
    private EditText mActivityQiyerenzhengName;
    /**
     * 用于企业号登录密码
     */
    private EditText mActivityQiyerenzhengPwd1;
    /**
     * 请再次输入密码
     */
    private EditText mActivityQiyerenzhengPwd2;


    //未进行企业认证1
    //审核中2
    //已锁定3
    //审核未通过4
    private int status;
    private String qiyexinxiId;
    private String companyId;
    private String companyName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qiyerenzheng);
        status = getIntent().getIntExtra("status", 0);
        qiyexinxiId = getIntent().getStringExtra("qiyexinxiId");
        companyId = getIntent().getStringExtra("companyId");
        companyName = getIntent().getStringExtra("companyName");
        initView();
    }

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mActivityQiyerenzhengImg = (ImageView) findViewById(R.id.activity_qiyerenzheng_img);
        mActivityQiyerenzhengImg.setOnClickListener(this);
        mActivityQiyerenzhengCommit = (Button) findViewById(R.id.activity_qiyerenzheng_commit);
        mActivityQiyerenzhengCommit.setOnClickListener(this);
        mActivityQiyerenzhengName = (EditText) findViewById(R.id.activity_qiyerenzheng_name);
        mActivityQiyerenzhengPwd1 = (EditText) findViewById(R.id.activity_qiyerenzheng_pwd1);
        mActivityQiyerenzhengPwd2 = (EditText) findViewById(R.id.activity_qiyerenzheng_pwd2);

        mToolbarTitle.setText("企业认证");

        //未进行企业认证1
        //审核中2
        //已锁定3
        //审核未通过4

        switch (status) {
            case 1:
                mActivityQiyerenzhengCommit.setVisibility(View.VISIBLE);
                break;
            case 2:
                GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.QIYERENZHENSTATUSDETAIL)
                        .tag(this)
                        .params("token", SPUtils.getInstance().getString("token"))
                        .params("sign", SPUtils.getInstance().getString("sign"));

                DialogStringCallback stringCallback = new DialogStringCallback(QiyerenzhengActivity.this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LogUtils.e("QIYERENZHENSTATUSDETAIL", response.body());

                        try {
                            JSONObject jsonObject = JSONObject.parseObject(response.body());
                            String msg = jsonObject.getString("msg");
                            if (response.code() == 200) {

                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    //status=empty,未进行企业认证status= wait_audit,审核中；status=checked;已锁定；status=audit_fail,审核未通过；status=audit审核通过
                                    if (!ObjectUtils.isEmpty(data)) {

                                        if (!StringUtils.isEmpty(data.getString("companyName"))) {
                                            mActivityQiyerenzhengName.setText(data.getString("companyName"));
                                            mActivityQiyerenzhengName.setEnabled(false);
                                        }

                                        mActivityQiyerenzhengPwd1.setText("000000");
                                        mActivityQiyerenzhengPwd1.setEnabled(false);
                                        mActivityQiyerenzhengPwd2.setText("000000");
                                        mActivityQiyerenzhengPwd2.setEnabled(false);

                                        if (!StringUtils.isEmpty(data.getString("busLicenceUrl"))) {
                                            GlideUtils.loadImageDefault(QiyerenzhengActivity.this,
                                                    ServerInfo.IMAGE + data.getString("busLicenceUrl"), mActivityQiyerenzhengImg);
                                            mActivityQiyerenzhengImg.setEnabled(false);
                                        }

                                        mActivityQiyerenzhengCommit.setVisibility(View.VISIBLE);
                                        mActivityQiyerenzhengCommit.setText("正在申请中,请您耐心等待！");
                                        mActivityQiyerenzhengCommit.setBackgroundColor(getResources().getColor(R.color.fengexian));
                                        mActivityQiyerenzhengCommit.setEnabled(false);
                                    }

                                    return;
                                }
                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                                    SignAndTokenUtil.getSign(QiyerenzhengActivity.this, request, this);
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
            case 3:
                break;
            case 4:

                mActivityQiyerenzhengCommit.setVisibility(View.VISIBLE);
                mActivityQiyerenzhengName.setText(companyName);
                mActivityQiyerenzhengName.setEnabled(false);


                break;
        }
    }

    private int REQUEST_CODE_CHOOSE = 11;
    List<Uri> mSelected;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            LogUtils.d("Matisse", "mSelected: " + mSelected);
            mActivityQiyerenzhengImg.setImageURI(mSelected.get(0));
        }
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
            case R.id.activity_qiyerenzheng_img:

                AndPermission.with(this)
                        .runtime()
                        .permission(Permission.Group.STORAGE, Permission.Group.CAMERA)
                        .onGranted(permissions -> {
                            // Storage permission are allowed.
                            MatisseImageUtil.chooseOnlyOnePhoto(this, REQUEST_CODE_CHOOSE);

                        })
                        .onDenied(permissions -> {
                            // Storage permission are not allowed.
                            ToastUtils.showShort("权限申请失败,您可能无法使用某些功能");
                        })
                        .start();

                break;
            case R.id.activity_qiyerenzheng_commit:
                if (checkNull()) {

                    if(status==1){
                        renzheng();
                    }

                    if(status==4){
                        resetRenzheng();
                    }

                }

                break;
        }
    }

    private void resetRenzheng() {

        PostRequest<String> reset = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.RESETQIYERENZHEN)
                .tag(this)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("companyName", mActivityQiyerenzhengName.getText().toString())
                .params("id",qiyexinxiId)
                .params("companyId",companyId)
                .params("password", new String(EncryptUtils.encryptAES2Base64(mActivityQiyerenzhengPwd1.getText().toString().trim().getBytes(),
                        "LnhtI(bt490B74Je".getBytes(), "AES/ECB/PKCS5Padding", null)))
                .params("from", getResources().getString(R.string.app_android))
                .params("file", new File(MatisseImageUtil.getRealFilePath(this, mSelected.get(0))));


        DialogStringCallback callback = new DialogStringCallback(QiyerenzhengActivity.this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.e("RESETQIYERENZHEN", response.body());

                try {
                    JSONObject jsonObject = JSONObject.parseObject(response.body());
                    String msg = jsonObject.getString("msg");
                    if (response.code() == 200) {

                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            EventBus.getDefault().post(new MessageBean(MessageBean.Code.QIYERENZHENG));
                            ToastUtils.showShort("已成功提交申请");
                            finish();
                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(QiyerenzhengActivity.this, reset, this);
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

        reset.execute(callback);

    }

    private void renzheng() {

        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.QIYERENZHEN)
                .tag(this)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("companyName", mActivityQiyerenzhengName.getText().toString())
                .params("password", new String(EncryptUtils.encryptAES2Base64(mActivityQiyerenzhengPwd1.getText().toString().trim().getBytes(),
                        "LnhtI(bt490B74Je".getBytes(), "AES/ECB/PKCS5Padding", null)))
                .params("from", getResources().getString(R.string.app_android))
                .params("file", new File(MatisseImageUtil.getRealFilePath(this, mSelected.get(0))));


        DialogStringCallback stringCallback = new DialogStringCallback(QiyerenzhengActivity.this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.e("QIYERENZHEN", response.body());

                try {
                    JSONObject jsonObject = JSONObject.parseObject(response.body());
                    String msg = jsonObject.getString("msg");
                    if (response.code() == 200) {

                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            EventBus.getDefault().post(new MessageBean(MessageBean.Code.QIYERENZHENG));
                            ToastUtils.showShort("已成功提交申请");
                            finish();
                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(QiyerenzhengActivity.this, request, this);
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

    private boolean checkNull() {

        if (StringUtils.isTrimEmpty(mActivityQiyerenzhengName.getText().toString())) {
            ToastUtils.showShort("请填写企业名称");
            return false;
        }

        if (StringUtils.isTrimEmpty(mActivityQiyerenzhengPwd1.getText().toString())) {
            ToastUtils.showShort("请填写密码信息");
            return false;
        }

        if (StringUtils.isTrimEmpty(mActivityQiyerenzhengPwd2.getText().toString())) {
            ToastUtils.showShort("请再次输入密码");
            return false;
        }

        if (mActivityQiyerenzhengPwd1.getText().toString().length() < 6 || mActivityQiyerenzhengPwd2.getText().toString().length() < 6) {
            ToastUtils.showShort("密码长度不能小于6位");
            return false;
        }

        if (!StringUtils.equals(mActivityQiyerenzhengPwd1.getText().toString(), mActivityQiyerenzhengPwd2.getText().toString())) {
            ToastUtils.showShort("两次输入的密码不一致");
            return false;
        }

        if (ObjectUtils.isEmpty(mSelected)) {
            ToastUtils.showShort("请上传你您的认证信息");
            return false;
        }

        return true;

    }
}
