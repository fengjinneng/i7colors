package com.company.qcy.ui.activity.user;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.company.qcy.ui.activity.pengyouquan.MyDyeInfoActivity;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;

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



    private Dialog chooseHeadDialog;
    public static final int REQUEST_CODE_CHOOSE_IMG =1;
    public static final int TAKE_PICTURE =2;


    private void showHeadPortraitDialog() {


        chooseHeadDialog = new Dialog(ZhanghaozhongxinActivity.this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(ZhanghaozhongxinActivity.this).inflate(R.layout.dialog_choose_head, null);
        chooseHeadDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        // layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        contentView.setLayoutParams(layoutParams);
        chooseHeadDialog.getWindow().setGravity(Gravity.BOTTOM);
        chooseHeadDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        chooseHeadDialog.setCancelable(true);
        chooseHeadDialog.setCanceledOnTouchOutside(true);
        chooseHeadDialog.show();
        contentView.findViewById(R.id.tv_take_video).setVisibility(View.GONE);
        contentView.findViewById(R.id.tv_choose_video).setVisibility(View.GONE);


        // 从相册选择图片
        contentView.findViewById(R.id.tv_choose_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseHeadDialog.dismiss();  // 选择之后，关闭dialog
                if (Build.VERSION.SDK_INT >= 23) {
                    int checkCallWritePermission = ContextCompat.checkSelfPermission(ZhanghaozhongxinActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (checkCallWritePermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ZhanghaozhongxinActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                        return;
                    } else {
                        // 打开本地相册
                        MatisseImageUtil.chooseOnlyOnePhoto(ZhanghaozhongxinActivity.this, REQUEST_CODE_CHOOSE_IMG);
                    }
                } else {
                    // 打开本地相册
                    MatisseImageUtil.chooseOnlyOnePhoto(ZhanghaozhongxinActivity.this, REQUEST_CODE_CHOOSE_IMG);
                }
            }
        });
        // 拍照
        contentView.findViewById(R.id.tv_take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseHeadDialog.dismiss();
                //判断权限
                if (Build.VERSION.SDK_INT >= 23) {
                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(ZhanghaozhongxinActivity.this, Manifest.permission.CAMERA);
                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ZhanghaozhongxinActivity.this, new String[]{Manifest.permission.CAMERA}, 222);
                        return;
                    } else {
                        int checkCallWritePermission = ContextCompat.checkSelfPermission(ZhanghaozhongxinActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        if (checkCallWritePermission != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(ZhanghaozhongxinActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                            return;
                        } else {
                            takePicture();
                        }
                    }
                } else {
                    takePicture();
                }
            }
        });

        // 取消
        contentView.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseHeadDialog.dismiss();
            }
        });

    }


    private File outFile;  // 为了根据File获取uri

    // 拍照
    public void takePicture() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            try {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File outDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                if (!outDir.exists()) {
                    outDir.mkdirs();
                }
                outFile = new File(outDir, System.currentTimeMillis() + ".jpg");
                String picFileFullName = outFile.getAbsolutePath();
                LogUtils.e("mxg", "picFileFullName = " + picFileFullName);
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, picFileFullName);
                Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, TAKE_PICTURE);
            } catch (Exception e) {
                e.printStackTrace();
                ToastUtils.showShort("请确认是否开启打开相机的权限！");
                // requestPermission();
            }
        } else {
            ToastUtils.showShort("请确认是否接入SD卡");
        }
    }


    public void upLoadImg(File imgFile){
        // 压缩图片
        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.UPLOADHEADPHOTO)
                .tag(this)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("file", imgFile);

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK) {
                    Uri imageUri = Uri.fromFile(outFile);
                    String filePath = MatisseImageUtil.getRealFilePath(this, imageUri);

                    File file = new File(filePath);
                    File file1 = null;
                    try {
                        file1 = new Compressor(this).compressToFile(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    upLoadImg(file1);
                }
                break;

            case REQUEST_CODE_CHOOSE_IMG:
                if (resultCode == RESULT_OK) {
                    List<Uri> uris = Matisse.obtainResult(data);
                    String filePath = MatisseImageUtil.getRealFilePath(this, uris.get(0));

                    File file = new File(filePath);
                    File file1 = null;
                    try {
                        file1 = new Compressor(this).compressToFile(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    upLoadImg(file1);
                }

                break;
        }
    }

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
                            showHeadPortraitDialog();

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
