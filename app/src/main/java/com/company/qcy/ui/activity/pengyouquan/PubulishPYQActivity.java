package com.company.qcy.ui.activity.pengyouquan;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.MatisseImageUtil;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.pengyouquan.FabupengyouquanAdapter;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.bean.pengyouquan.ImageBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.zhihu.matisse.Matisse;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PubulishPYQActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 取消
     */
    private TextView mActivityPubylishPeyCancle;
    /**
     * 发布朋友圈
     */
    private TextView mActivityPubylishPeyPublish;
    /**
     * 这一刻的想法...
     */
    private EditText mActivityPubylishPeyContent;
    private RecyclerView recyclerView;

    private FabupengyouquanAdapter adapter;
    private List<ImageBean> datas;
    private ImageView videoImg;
    private ImageView mActivityPubylishPeyStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pubulish_pyq);
        initView();
    }


    private static final int REQUEST_CODE_CHOOSE_IMG = 1;
    private static final int REQUEST_CODE_TAKE_VIDEO = 3;

    List<Uri> mSelected;

    private boolean isHaveAdd = true;
    List<Uri> updateImgList = new ArrayList<>();

    private int choiceWho;//是选择了视频还是图片 1为图片。 2为视频

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK) {
                    Uri imageUri = Uri.fromFile(outFile);
                    updateImgList.add(imageUri);
                    choiceNumber = choiceNumber - 1;
                    adapter.remove(adapter.getData().size() - 1);
                    adapter.addData(new ImageBean(imageUri));
                    if (choiceNumber != 0) {
                        Uri parse = Uri.parse("+");
                        adapter.addData(new ImageBean(parse));
                        isHaveAdd = true;
                    } else {
                        isHaveAdd = false;
                    }
                    choiceWho = 1;
                }
                break;

            case REQUEST_CODE_CHOOSE_IMG:
                if (resultCode == RESULT_OK) {
                    mSelected = Matisse.obtainResult(data);
                    updateImgList.addAll(mSelected);
                    List<ImageBean> list = new ArrayList<>();
                    for (int i = 0; i < mSelected.size(); i++) {
                        list.add(new ImageBean(mSelected.get(i)));
                    }
                    choiceNumber = choiceNumber - mSelected.size();
                    adapter.remove(adapter.getData().size() - 1);
                    adapter.addData(list);
                    if (choiceNumber != 0) {
                        Uri parse = Uri.parse("+");
                        adapter.addData(new ImageBean(parse));
                        isHaveAdd = true;
                    } else {
                        isHaveAdd = false;
                    }
                    choiceWho = 1;
                }

                break;

            case TAKE_VIDEO:
                if (resultCode == RESULT_OK) {
                    recyclerView.setVisibility(View.GONE);
                    videoImg.setVisibility(View.VISIBLE);
                    mActivityPubylishPeyStart.setVisibility(View.VISIBLE);
                    videoUri = Uri.fromFile(outFile);
                    Bitmap localVideoThumbnail = getLocalVideoThumbnail(videoUri.getPath());
                    videoImg.setImageBitmap(localVideoThumbnail);
                    videoImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(PubulishPYQActivity.this, ShipinbofangActivity.class);
                            intent.putExtra("uri", videoUri.toString());
                            ActivityUtils.startActivity(intent);
                        }
                    });
                    choiceWho = 2;
                }
                break;
        }
    }

    private Uri videoUri ;


    /**
     * 获取本地视频的第一帧
     *
     * @param filePath
     * @return
     */

    public static Bitmap getLocalVideoThumbnail(String filePath) {

        Bitmap bitmap = null;

        //MediaMetadataRetriever 是android中定义好的一个类，提供了统一

        //的接口，用于从输入的媒体文件中取得帧和元数据；

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();

        try {

            //根据文件路径获取缩略图

            retriever.setDataSource(filePath);

            //获得第一帧图片

            bitmap = retriever.getFrameAtTime();

        } catch (IllegalArgumentException e) {

            e.printStackTrace();

        } finally {

            retriever.release();

        }

        return bitmap;

    }


    private int choiceNumber = 9;

    private void initView() {
        mActivityPubylishPeyCancle = (TextView) findViewById(R.id.activity_pubylish_pey_cancle);
        mActivityPubylishPeyCancle.setOnClickListener(this);
        mActivityPubylishPeyPublish = (TextView) findViewById(R.id.activity_pubylish_pey_publish);
        mActivityPubylishPeyPublish.setOnClickListener(this);
        mActivityPubylishPeyContent = (EditText) findViewById(R.id.activity_pubylish_pey_content);
        recyclerView = (RecyclerView) findViewById(R.id.activity_pubylish_pey_recyclerview);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        datas = new ArrayList<>();
        adapter = new FabupengyouquanAdapter(R.layout.item_fabupengyouquan_img, datas);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
        Uri parse = Uri.parse("+");
        adapter.addData(new ImageBean(parse));

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ImageBean imageBean = (ImageBean) adapter.getData().get(position);
                if (StringUtils.equals(imageBean.getUri().toString(), "+")) {
                    if (choiceNumber >= 1) {

                        showHeadPortraitDialog();

//                        Matisse.from(PubulishPYQActivity.this)
//                                .choose(MimeType.ofImage())
//                                .countable(false)
//                                .maxSelectable(choiceNumber)
////                          .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
////                          .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
//                                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
//                                .thumbnailScale(0.85f)
//                                .imageEngine(new MyGlideEngine())
//                                .forResult(REQUEST_CODE_CHOOSE);
                    } else {
                        ToastUtils.showShort("最多只能选择9张图片");
                    }

                } else {

                }
            }
        });


        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.item_fabupengyouquan_delete:
                        updateImgList.remove(position);
                        adapter.remove(position);
                        choiceNumber = choiceNumber + 1;
                        if (!isHaveAdd && choiceNumber == 1) {
                            Uri parse = Uri.parse("+");
                            adapter.addData(new ImageBean(parse));
                        }
                        break;
                }
            }
        });
        videoImg = (ImageView) findViewById(R.id.activity_pubylish_pey_video);
        mActivityPubylishPeyStart = (ImageView) findViewById(R.id.activity_pubylish_pey_start);
        mActivityPubylishPeyStart.setOnClickListener(this);
    }

    private Dialog chooseHeadDialog;

    private void showHeadPortraitDialog() {

        chooseHeadDialog = new Dialog(PubulishPYQActivity.this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(PubulishPYQActivity.this).inflate(R.layout.dialog_choose_head, null);
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
        // 从相册选择图片
        contentView.findViewById(R.id.tv_choose_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseHeadDialog.dismiss();  // 选择之后，关闭dialog
                if (Build.VERSION.SDK_INT >= 23) {
                    int checkCallWritePermission = ContextCompat.checkSelfPermission(PubulishPYQActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (checkCallWritePermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(PubulishPYQActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                        return;
                    } else {
                        // 打开本地相册
                        MatisseImageUtil.choosePhoto(PubulishPYQActivity.this, choiceNumber, REQUEST_CODE_CHOOSE_IMG);
                    }
                } else {
                    // 打开本地相册
                    MatisseImageUtil.choosePhoto(PubulishPYQActivity.this, choiceNumber, REQUEST_CODE_CHOOSE_IMG);
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
                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(PubulishPYQActivity.this, Manifest.permission.CAMERA);
                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(PubulishPYQActivity.this, new String[]{Manifest.permission.CAMERA}, 222);
                        return;
                    } else {
                        int checkCallWritePermission = ContextCompat.checkSelfPermission(PubulishPYQActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        if (checkCallWritePermission != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(PubulishPYQActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
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

        // 拍摄视频
        contentView.findViewById(R.id.tv_take_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseHeadDialog.dismiss();
                //判断权限
                if (Build.VERSION.SDK_INT >= 23) {

                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(PubulishPYQActivity.this, Manifest.permission.CAMERA);
                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(PubulishPYQActivity.this, new String[]{Manifest.permission.CAMERA}, 3);
                        return;
                    } else {
                        int checkCallWritePermission = ContextCompat.checkSelfPermission(PubulishPYQActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        if (checkCallWritePermission != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(PubulishPYQActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 3);
                            return;
                        } else {
                            takeVideo();
                        }
                    }
                } else {
                    takeVideo();
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

    // switch要求常数必须有修饰、
    private static final int TAKE_PICTURE = 100;
    private static final int TAKE_VIDEO = 300;

    //拍摄视频
    private void takeVideo() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            try {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 5);//限制录制时间(10秒=10)
                File outDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                if (!outDir.exists()) {
                    outDir.mkdirs();
                }
                outFile = new File(outDir, System.currentTimeMillis() + ".mp4");
                String videoFileFullName = outFile.getAbsolutePath();
                LogUtils.e("mxg", "videoFileFullName = " + videoFileFullName);
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Video.Media.DATA, videoFileFullName);
                Uri uri = getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, TAKE_VIDEO);
            } catch (Exception e) {
                e.printStackTrace();
                ToastUtils.showShort("请确认是否开启打开相机的权限！");
                // requestPermission();
            }
        } else {
            ToastUtils.showShort("请确认是否接入SD卡");
        }
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
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.activity_pubylish_pey_cancle:
                finish();
                break;
            case R.id.activity_pubylish_pey_publish:


//                if (StringUtils.isEmpty(mActivityPubylishPeyContent.getText().toString().trim()) && updateImgList.size() == 0) {
//                    ToastUtils.showShort("不能发布空的消息");
//                    return;
//                }
                try {

                    List<File> picFilePaths = new ArrayList<>();
                    for (int i = 0; i < updateImgList.size(); i++) {
                        String picFilePath = MatisseImageUtil.getRealFilePath(this, updateImgList.get(i));
                        String fileName = MatisseImageUtil.getFileName(picFilePath);
                        Bitmap bmp = MatisseImageUtil.revitionImageSize(picFilePath, 10);
                        picFilePath = MatisseImageUtil.saveBitmap(bmp);
                        picFilePaths.add(new File(picFilePath));

                    }
                    // 压缩图片
                    OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.FABUPENGYOUQUAN)
                            .tag(this)
                            .params("token", SPUtils.getInstance().getString("token"))
                            .params("sign", SPUtils.getInstance().getString("sign"))
                            .params("type", "video")
                            .params("content", mActivityPubylishPeyContent.getText().toString())
                            .params("file", new File(videoUri.getPath()))
//                            .params("files","")
                            .execute(new DialogStringCallback(PubulishPYQActivity.this) {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    try {
                                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                                        LogUtils.e("FABUPENGYOUQUAN",response.body());
                                        if (response.code() == 200) {

                                            if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                                String data = jsonObject.getString("data");
                                                String msg = jsonObject.getString("msg");
                                                if (StringUtils.equals("true", data)) {
                                                    ToastUtils.showShort("您的发布已成功");
                                                    EventBus.getDefault().post(new MessageBean(MessageBean.Code.FABUPENGYOUQUANCHENGGONG));
                                                    finish();
                                                } else ToastUtils.showShort(msg);

                                                return;

                                            }
                                            SignAndTokenUtil.checkSignAndToken(PubulishPYQActivity.this, jsonObject);


                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }

                            });

                } catch (Exception e) {
                    e.printStackTrace();
                }


                break;
//            case R.id.activity_pubylish_pey_textureView:
//                break;
            case R.id.activity_pubylish_pey_start:
                break;
        }
    }
}
