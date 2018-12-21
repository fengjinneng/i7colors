package com.company.qcy.ui.activity.pengyouquan;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
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
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.MatisseImageUtil;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.bean.pengyouquan.ImageBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zhihu.matisse.Matisse;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyDyeInfoActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private ImageView mActivityMyDyeInfoImg;
    private ConstraintLayout mActivityMyDyeInfoImgLayout;
    /**
     *
     */
    private TextView mActivityMyDyeInfoNickname;
    private ConstraintLayout mActivityMyDyeInfoNicknameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dye_info);
        nickName = getIntent().getStringExtra("nickName");
        imgUrl = getIntent().getStringExtra("imgUrl");
        initView();
    }

    private String nickName;
    private String imgUrl;

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mActivityMyDyeInfoImg = (ImageView) findViewById(R.id.activity_my_dye_info_img);
        mActivityMyDyeInfoImgLayout = (ConstraintLayout) findViewById(R.id.activity_my_dye_info_img_layout);
        mActivityMyDyeInfoImgLayout.setOnClickListener(this);
        mActivityMyDyeInfoNickname = (TextView) findViewById(R.id.activity_my_dye_info_nickname);
        mActivityMyDyeInfoNicknameLayout = (ConstraintLayout) findViewById(R.id.activity_my_dye_info_nickname_layout);
        mActivityMyDyeInfoNicknameLayout.setOnClickListener(this);
        mToolbarTitle.setText("个人信息");
        if(!StringUtils.isEmpty(nickName)){
            mActivityMyDyeInfoNickname.setText(nickName);
        }

        if(StringUtils.isEmpty(imgUrl)){
            mActivityMyDyeInfoImg.setBackground(getResources().getDrawable(R.mipmap.morentouxiang));
        }else {
            GlideUtils.loadCircleImage(this, ServerInfo.IMAGE+imgUrl,mActivityMyDyeInfoImg);
        }
    }


    @Override
    public void onReciveMessage(MessageBean msg) {
        super.onReciveMessage(msg);
        switch (msg.getCode()){
            case MessageBean.Code.PENGYOUQUANNICKNAMECHANGE:
                mActivityMyDyeInfoNickname.setText(msg.getMeaasge());
                break;
        }
    }

    private Dialog chooseHeadDialog;
    public static final int REQUEST_CODE_CHOOSE_IMG =1;
    public static final int TAKE_PICTURE =2;


    private void showHeadPortraitDialog() {


        chooseHeadDialog = new Dialog(MyDyeInfoActivity.this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(MyDyeInfoActivity.this).inflate(R.layout.dialog_choose_head, null);
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
                    int checkCallWritePermission = ContextCompat.checkSelfPermission(MyDyeInfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (checkCallWritePermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MyDyeInfoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                        return;
                    } else {
                        // 打开本地相册
                        MatisseImageUtil.chooseOnlyOnePhoto(MyDyeInfoActivity.this, REQUEST_CODE_CHOOSE_IMG);
                    }
                } else {
                    // 打开本地相册
                    MatisseImageUtil.chooseOnlyOnePhoto(MyDyeInfoActivity.this, REQUEST_CODE_CHOOSE_IMG);
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
                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(MyDyeInfoActivity.this, Manifest.permission.CAMERA);
                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MyDyeInfoActivity.this, new String[]{Manifest.permission.CAMERA}, 222);
                        return;
                    } else {
                        int checkCallWritePermission = ContextCompat.checkSelfPermission(MyDyeInfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        if (checkCallWritePermission != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(MyDyeInfoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK) {
                    Uri imageUri = Uri.fromFile(outFile);
                    String filePath = MatisseImageUtil.getRealFilePath(this, imageUri);

                        Bitmap bmp = null;
                        try {
                            bmp = MatisseImageUtil.revitionImageSize(filePath);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    filePath = MatisseImageUtil.saveBitmap(bmp);

                    upDateImg(new File(filePath));
                }
                break;

            case REQUEST_CODE_CHOOSE_IMG:
                if (resultCode == RESULT_OK) {
                    List<Uri> uris = Matisse.obtainResult(data);
                    String filePath = MatisseImageUtil.getRealFilePath(this, uris.get(0));

                    Bitmap bmp = null;
                    try {
                        bmp = MatisseImageUtil.revitionImageSize(filePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    filePath = MatisseImageUtil.saveBitmap(bmp);

                    upDateImg(new File(filePath));
                }

                break;
        }
    }

    private void upDateImg(File imgFile) {
        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.UPDATEMYDYEINFO)
                .tag(this)
                .isMultipart(true)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("token", SPUtils.getInstance().getString("token"))
                .params("file", imgFile);

        DialogStringCallback stringCallback = new DialogStringCallback(MyDyeInfoActivity.this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("UPDATEMYDYEINFO", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            ToastUtils.showShort(msg);
                            if(!StringUtils.isEmpty(jsonObject.getString("data"))){
                                GlideUtils.loadCircleImage(MyDyeInfoActivity.this,ServerInfo.IMAGE+jsonObject.getString("data"),mActivityMyDyeInfoImg);
                                EventBus.getDefault().post(new MessageBean(MessageBean.Code.PENGYOUQUANHEADIMGCHANGE,jsonObject.getString("data")));
                            }else {
                                ToastUtils.showShort(msg);
                            }
                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(MyDyeInfoActivity.this,request,this);
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
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_my_dye_info_img_layout:

                AndPermission.with(this)
                        .runtime()
                        .permission(Permission.Group.STORAGE,Permission.Group.CAMERA)
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
            case R.id.activity_my_dye_info_nickname_layout:
                Intent intent = new Intent(this,ChangeNicknameActivity.class);
                intent.putExtra("nickName",nickName);
                ActivityUtils.startActivity(intent);
                break;
        }
    }
}
