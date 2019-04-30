package com.company.qcy.ui.activity.user;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.MatisseImageUtil;
import com.company.qcy.Utils.MyCommonUtil;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.ui.activity.pengyouquan.ImagePagerActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zhihu.matisse.Matisse;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.List;

import id.zelory.compressor.Compressor;

public class ChoiceHeadImageActivity extends BaseActivity implements View.OnClickListener {


    private String imgUrl;
    private ImageView img;
    /**
     * 更换头像
     */
    private TextView mActivityChoiceHeadImageChange;
    private ImageView mActivityChoiceHeadImageDownload;

    private boolean iszhanghaozhongxin;//是否是帐号中心穿过来的

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_head_image);

        initView();

    }

    private void initView() {
        img = (ImageView) findViewById(R.id.imageView40);
        img.setOnClickListener(this);
        mActivityChoiceHeadImageChange = (TextView) findViewById(R.id.activity_choice_head_image_change);
        mActivityChoiceHeadImageChange.setOnClickListener(this);
        mActivityChoiceHeadImageDownload = (ImageView) findViewById(R.id.activity_choice_head_image_download);
        mActivityChoiceHeadImageDownload.setOnClickListener(this);

        if (StringUtils.equals("mine", getIntent().getStringExtra("from"))) {
            mActivityChoiceHeadImageChange.setVisibility(View.VISIBLE);
        }

        imgUrl = getIntent().getStringExtra("imgUrl");


        if (StringUtils.isEmpty(imgUrl)) {
            img.setImageDrawable(getResources().getDrawable(R.mipmap.morentouxiang));
        } else {
            if (imgUrl.startsWith("http")) {
                Glide.with(this).load(imgUrl).into(img);
            } else {
                Glide.with(this).load(ServerInfo.IMAGE + imgUrl).into(img);
            }
        }

        if (StringUtils.equals("1", getIntent().getStringExtra("iszhanghaozhongxin"))) {
            iszhanghaozhongxin = true;
        } else {
            iszhanghaozhongxin = false;
        }

    }


    private Dialog chooseHeadDialog;
    public static final int REQUEST_CODE_CHOOSE_IMG = 1;
    public static final int TAKE_PICTURE = 2;


    private void showHeadPortraitDialog() {

        chooseHeadDialog = new Dialog(ChoiceHeadImageActivity.this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(ChoiceHeadImageActivity.this).inflate(R.layout.dialog_choose_head, null);
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
                    int checkCallWritePermission = ContextCompat.checkSelfPermission(ChoiceHeadImageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (checkCallWritePermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ChoiceHeadImageActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                        return;
                    } else {
                        // 打开本地相册
                        MatisseImageUtil.chooseOnlyOnePhoto(ChoiceHeadImageActivity.this, REQUEST_CODE_CHOOSE_IMG);
                    }
                } else {
                    // 打开本地相册
                    MatisseImageUtil.chooseOnlyOnePhoto(ChoiceHeadImageActivity.this, REQUEST_CODE_CHOOSE_IMG);
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
                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(ChoiceHeadImageActivity.this, Manifest.permission.CAMERA);
                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ChoiceHeadImageActivity.this, new String[]{Manifest.permission.CAMERA}, 222);
                        return;
                    } else {
                        int checkCallWritePermission = ContextCompat.checkSelfPermission(ChoiceHeadImageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        if (checkCallWritePermission != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(ChoiceHeadImageActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
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
                    Glide.with(ChoiceHeadImageActivity.this).load(imageUri).into(img);
                    File file = new File(filePath);
                    File file1 = null;
                    try {
                        file1 = new Compressor(this).compressToFile(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (iszhanghaozhongxin) {
                        upPersonImg(file1);
                    } else {
                        upPengyouquanHeadImg(file1);
                    }
                }
                break;

            case REQUEST_CODE_CHOOSE_IMG:
                if (resultCode == RESULT_OK) {
                    List<Uri> uris = Matisse.obtainResult(data);
                    String filePath = MatisseImageUtil.getRealFilePath(this, uris.get(0));
                    Glide.with(ChoiceHeadImageActivity.this).load(uris.get(0)).into(img);
                    File file = new File(filePath);
                    File file1 = null;
                    try {
                        file1 = new Compressor(this).compressToFile(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (iszhanghaozhongxin) {
                        upPersonImg(file1);
                    } else {
                        upPengyouquanHeadImg(file1);
                    }
                }

                break;
        }
    }

    private void upPersonImg(File imgFile) {
        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.UPLOADHEADPHOTO)
                .tag(this)
                .isMultipart(true)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("token", SPUtils.getInstance().getString("token"))
                .params("file", imgFile);

        DialogStringCallback stringCallback = new DialogStringCallback(ChoiceHeadImageActivity.this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("UPLOADHEADPHOTO", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            ToastUtils.showShort(msg);
                            if (!StringUtils.isEmpty(jsonObject.getString("data"))) {
                                //修改的是帐号中心的头像
                                SPUtils.getInstance().remove("photo");
                                SPUtils.getInstance().put("photo",jsonObject.getString("data"));
                                EventBus.getDefault().post(new MessageBean(MessageBean.Code.CHANGEPERSONHEADIMG, jsonObject.getString("data")));
                            } else {
                                ToastUtils.showShort(msg);
                            }
                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(ChoiceHeadImageActivity.this, request, this);
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


    private void upPengyouquanHeadImg(File imgFile) {
        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.UPDATEMYDYEINFO)
                .tag(this)
                .isMultipart(true)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("token", SPUtils.getInstance().getString("token"))
                .params("file", imgFile);

        DialogStringCallback stringCallback = new DialogStringCallback(ChoiceHeadImageActivity.this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("UPDATEMYDYEINFO", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            ToastUtils.showShort(msg);
                            if (!StringUtils.isEmpty(jsonObject.getString("data"))) {
                                //修改的是朋友圈的头像
                                EventBus.getDefault().post(new MessageBean(MessageBean.Code.PENGYOUQUANHEADIMGCHANGE, jsonObject.getString("data")));
                            } else {
                                ToastUtils.showShort(msg);
                            }
                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(ChoiceHeadImageActivity.this, request, this);
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
            case R.id.imageView40:
                finish();
                break;
            case R.id.activity_choice_head_image_change:
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
            case R.id.activity_choice_head_image_download:
                MyCommonUtil.saveImageToSysAlbum(ChoiceHeadImageActivity.this, img);
                break;
        }
    }
}
