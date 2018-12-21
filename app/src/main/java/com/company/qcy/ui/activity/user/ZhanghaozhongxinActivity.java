package com.company.qcy.ui.activity.user;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.MainActivity;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.GifSizeFilter;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.MatisseImageUtil;
import com.company.qcy.Utils.MyGlideEngine;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.ui.activity.pengyouquan.PubulishPYQActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ZhanghaozhongxinActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 个人用户
     */
    private TextView mActivityZhanghaozhongxinShenfen;
    /**
     * Y.C.Pixel
     */
    private TextView mActivityZhanghaozhongxinName;
    private ImageView mActivityZhanghaozhongxinImg;
    /**
     * Y.C.Pixel
     */
    private TextView mActivityZhanghaozhongxinName2;
    /**
     * 13588888888
     */
    private TextView mActivityZhanghaozhongxinPhone;
    private ConstraintLayout mActivityZhanghaozhongxinChangepawwsord;
    private ImageView mActivityZhanghaozhongxinShenfenImg;
    /**
     * 个人账户
     */
    private TextView mActivityZhanghaozhongxinShenfen2;
    /**
     * 申请成为企业账户
     */
    private TextView mActivityZhanghaozhongxinShenqing;
    /**
     * 我的
     */
    private TextView mActivityZhanghaozhongxinBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhanghaozhongxin);
        initView();
    }

    @Override
    public void onReciveMessage(MessageBean msg) {
        super.onReciveMessage(msg);

        switch (msg.getCode()) {
            case MessageBean.Code.CHANGEPASSWORDCHENGGONG:
                SPUtils.getInstance().clear();
                ActivityUtils.finishAllActivities();
                ActivityUtils.startActivity(MainActivity.class);
                break;
        }
    }

    private void initView() {
        mActivityZhanghaozhongxinShenfen = (TextView) findViewById(R.id.activity_zhanghaozhongxin_shenfen);
        mActivityZhanghaozhongxinName = (TextView) findViewById(R.id.activity_zhanghaozhongxin_name);
        mActivityZhanghaozhongxinImg = (ImageView) findViewById(R.id.activity_zhanghaozhongxin_img);
        mActivityZhanghaozhongxinName2 = (TextView) findViewById(R.id.activity_zhanghaozhongxin_name2);
        mActivityZhanghaozhongxinChangepawwsord = (ConstraintLayout) findViewById(R.id.activity_zhanghaozhongxin_changepawwsord);
        mActivityZhanghaozhongxinShenfenImg = (ImageView) findViewById(R.id.activity_zhanghaozhongxin_shenfen_img);
        mActivityZhanghaozhongxinShenfen2 = (TextView) findViewById(R.id.activity_zhanghaozhongxin_shenfen2);
        mActivityZhanghaozhongxinShenqing = (TextView) findViewById(R.id.activity_zhanghaozhongxin_shenqing);
        mActivityZhanghaozhongxinImg.setOnClickListener(this);
        mActivityZhanghaozhongxinChangepawwsord.setOnClickListener(this);
        mActivityZhanghaozhongxinShenqing.setOnClickListener(this);
        mActivityZhanghaozhongxinBack = (TextView) findViewById(R.id.activity_zhanghaozhongxin_back);
        mActivityZhanghaozhongxinBack.setOnClickListener(this);
        setData();
    }

    private void setData() {
        if (!StringUtils.isEmpty(SPUtils.getInstance().getString("photo"))) {
            GlideUtils.loadCircleImage(this, SPUtils.getInstance().getString("photo"), mActivityZhanghaozhongxinImg);
        }
        mActivityZhanghaozhongxinName.setText(SPUtils.getInstance().getString("loginName"));
        mActivityZhanghaozhongxinName2.setText(SPUtils.getInstance().getString("loginName"));

        if (SPUtils.getInstance().getBoolean("isCompany")) {
            mActivityZhanghaozhongxinShenfen.setText("企业用户");
            mActivityZhanghaozhongxinShenfen.setTextColor(getResources().getColor(R.color.chunhongse));
            mActivityZhanghaozhongxinShenfen.setBackground(getResources().getDrawable(R.drawable.background_qiyezhanghu_yuanxing_anniu));
            mActivityZhanghaozhongxinShenfenImg.setImageDrawable(getResources().getDrawable(R.mipmap.qiyezhanghu));
            mActivityZhanghaozhongxinShenfen2.setText("企业账号");
            mActivityZhanghaozhongxinShenfen2.setTextColor(getResources().getColor(R.color.chunhongse));
            mActivityZhanghaozhongxinShenqing.setVisibility(View.INVISIBLE);
        } else {
            mActivityZhanghaozhongxinShenfen.setText("个人用户");
            mActivityZhanghaozhongxinShenfen.setTextColor(getResources().getColor(R.color.lanse));
            mActivityZhanghaozhongxinShenfen.setBackground(getResources().getDrawable(R.drawable.background_gerenzhanghu_yuanxing_anniu));
            mActivityZhanghaozhongxinShenfenImg.setImageDrawable(getResources().getDrawable(R.mipmap.gerenzhanghu));
            mActivityZhanghaozhongxinShenfen2.setText("个人账号");
            mActivityZhanghaozhongxinShenfen2.setTextColor(getResources().getColor(R.color.lanse));
            mActivityZhanghaozhongxinShenqing.setVisibility(View.VISIBLE);
        }
    }

    List<Uri> mSelected;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            LogUtils.d("Matisse", "mSelected: " + mSelected);
            mActivityZhanghaozhongxinImg.setImageURI(mSelected.get(0));

            try {

                List<File> picFilePaths = new ArrayList<>();
                for (int i = 0; i < mSelected.size(); i++) {
                    String picFilePath = MatisseImageUtil.getRealFilePath(this, mSelected.get(i));
                    Bitmap bmp = MatisseImageUtil.revitionImageSize(picFilePath);
                    picFilePath = MatisseImageUtil.saveBitmap(bmp);
                    picFilePaths.add(new File(picFilePath));

                }
                // 压缩图片
                PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.UPLOADHEADPHOTO)
                        .tag(this)
                        .params("token", SPUtils.getInstance().getString("token"))
                        .params("sign", SPUtils.getInstance().getString("sign"))
                        .params("file", new File(picFilePaths.get(0).getPath()));


                DialogStringCallback stringCallback = new DialogStringCallback(ZhanghaozhongxinActivity.this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LogUtils.e("UPLOADHEADPHOTO", response.body());

                        try {
                            JSONObject jsonObject = JSONObject.parseObject(response.body());
                            String msg = jsonObject.getString("msg");
                            if (response.code() == 200) {

                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                    if (!StringUtils.isEmpty(jsonObject.getString("data"))) {
                                        GlideUtils.loadCircleImage(ZhanghaozhongxinActivity.this, jsonObject.getString("data"), mActivityZhanghaozhongxinImg);
                                        EventBus.getDefault().post(new MessageBean(MessageBean.Code.CHANGEPERSONHEADIMG, jsonObject.getString("data")));
                                        SPUtils.getInstance().put("photo", jsonObject.getString("data"));
                                    }
                                    return;
                                }
                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                                    SignAndTokenUtil.getSign(ZhanghaozhongxinActivity.this, request, this);
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

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int REQUEST_CODE_CHOOSE = 1;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.activity_zhanghaozhongxin_img:
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
            case R.id.activity_zhanghaozhongxin_changepawwsord:
                ActivityUtils.startActivity(ChangePasswordActivity.class);

                break;
            case R.id.activity_zhanghaozhongxin_shenqing:


                break;
            case R.id.activity_zhanghaozhongxin_back:
                finish();
                break;
        }
    }
}
