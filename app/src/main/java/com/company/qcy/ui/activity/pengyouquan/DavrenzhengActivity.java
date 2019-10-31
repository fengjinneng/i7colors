package com.company.qcy.ui.activity.pengyouquan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.MatisseImageUtil;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.ui.activity.user.ZhanghaozhongxinActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zhihu.matisse.Matisse;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DavrenzhengActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    /**
     * 请输入姓名
     */
    private EditText mActivityDavrenzhenName;
    /**
     * 请输入公司名称
     */
    private EditText mActivityDavrenzhenCompany;
    /**
     * 请输入个人职务名称
     */
    private EditText mActivityDavrenzhenJob;
    /**
     * 上传凭证:
     */
    private ImageView mActivityDavrenzhenImg;
    /**
     * 提交
     */
    private Button mActivityDavrenzhenCommit;
    /**
     * 取消
     */
    private Button mActivityDavrenzhenCancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_davrenzheng);
        initView();
    }

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mActivityDavrenzhenName = (EditText) findViewById(R.id.activity_davrenzhen_name);
        mActivityDavrenzhenCompany = (EditText) findViewById(R.id.activity_davrenzhen_company);
        mActivityDavrenzhenJob = (EditText) findViewById(R.id.activity_davrenzhen_job);
        mActivityDavrenzhenImg = (ImageView) findViewById(R.id.activity_davrenzhen_img);
        mActivityDavrenzhenCommit = (Button) findViewById(R.id.activity_davrenzhen_commit);
        mActivityDavrenzhenCommit.setOnClickListener(this);
        mActivityDavrenzhenCancle = (Button) findViewById(R.id.activity_davrenzhen_cancle);
        mActivityDavrenzhenCancle.setOnClickListener(this);
        mActivityDavrenzhenImg.setOnClickListener(this);
        mToolbarTitle.setText("大V认证");
    }


    private int REQUEST_CODE_CHOOSE = 10;
    List<Uri> mSelected;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            LogUtils.d("Matisse", "mSelected: " + mSelected);
            mActivityDavrenzhenImg.setImageURI(mSelected.get(0));
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
            case R.id.activity_davrenzhen_commit:
                if (StringUtils.isTrimEmpty(mActivityDavrenzhenName.getText().toString())) {
                    ToastUtils.showShort("姓名不能为空");
                    return;
                }
                if (StringUtils.isTrimEmpty(mActivityDavrenzhenCompany.getText().toString())) {
                    ToastUtils.showShort("公司名称不能为空");
                    return;
                }
                if (StringUtils.isTrimEmpty(mActivityDavrenzhenJob.getText().toString())) {
                    ToastUtils.showShort("个人职务不能为空");
                    return;
                }

                update();

                break;
            case R.id.activity_davrenzhen_cancle:
                finish();
                break;
            case R.id.activity_davrenzhen_img:
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
        }
    }

    private void update() {
        if (ObjectUtils.isEmpty(mSelected)) {
            ToastUtils.showShort("请上传你您的认证信息");
            return;
        }

        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.DAVRENZHEN)
                .tag(this)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("name", mActivityDavrenzhenName.getText().toString())
                .params("companyName", mActivityDavrenzhenCompany.getText().toString())
                .params("job", mActivityDavrenzhenJob.getText().toString())
                .params("from",getResources().getString(R.string.app_android))
                .params("file", new File(MatisseImageUtil.getRealFilePath(this, mSelected.get(0))));


        DialogStringCallback stringCallback = new DialogStringCallback(DavrenzhengActivity.this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.e("DAVRENZHEN", response.body());

                try {
                    JSONObject jsonObject = JSONObject.parseObject(response.body());
                    String msg = jsonObject.getString("msg");
                    if (response.code() == 200) {

                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            ToastUtils.showShort("已成功提交申请");
                            finish();
                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(DavrenzhengActivity.this, request, this);
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
