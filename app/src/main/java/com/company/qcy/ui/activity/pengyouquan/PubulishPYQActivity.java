package com.company.qcy.ui.activity.pengyouquan;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.help.Tip;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.MatisseImageUtil;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.pengyouquan.FabupengyouquanAdapter;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.chanyezixun.NewsBean;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.bean.pengyouquan.ImageBean;
import com.company.qcy.bean.pengyouquan.MyFriendsBean;
import com.company.qcy.ui.activity.chanyezixun.ChanyezixunActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Progress;
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

import id.zelory.compressor.Compressor;

public class PubulishPYQActivity extends BaseActivity implements View.OnClickListener {

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

    private ImageView playRedioButton;
    private ConstraintLayout mActivityPublishPyqAddressLayout;
    private ImageView mActivityPublishPyqAddressLayoutImg;
    /**
     * 你的位置
     */
    private TextView mActivityPublishPyqAddressLayoutTitle;
    private ConstraintLayout mActivityPublishPyqHuatiLayout;
    /**
     * # 话题名称话题名称话题名称 #
     */
    private TextView mActivityPublishPyqHuatiName;
    private ImageView mActivityPublishPyqHuatiImg;
    private ImageView mActivityPublishPyqZixunGuanlianzixunImg;
    /**
     * 关联资讯
     */
    private TextView mActivityPublishPyqZixunGuanlianzixun;
    /**
     * 未关联
     */
    private TextView mActivityPublishPyqZixunGuanlianzixunState;
    private ConstraintLayout mActivityPublishPyqZixunLayout;
    private ImageView mActivityPublishPyqLianjieImg;
    /**
     * 孙瑞哲：科创板是纺织行业“及 时雨”，多细分领域企业有科创板是纺织行业“及 时雨”，多细分领域企业有
     */
    private TextView mActivityPublishPyqLianjieTitle;
    /**
     * 近日，中国纺织工业联合会（下称 “中纺联”）会长孙瑞哲接受中国
     */
    private TextView mActivityPublishPyqLianjieContent;
    /**
     * 删除文章
     */
    private TextView mActivityPublishPyqLianjieDelete;
    private ConstraintLayout mActivityPublishPyqLianjieLayout;
    private ImageView mActivityPublishPyqShuikeyikanImg;
    /**
     * 谁可以看
     */
    private TextView mActivityPublishPyqShuikeyikanText;
    /**
     * 部分关注人可见
     */
    private TextView mActivityPublishPyqShuikeyikanStatus;
    private ConstraintLayout mActivityPublishPyqShuikeyikanLayout;
    /**
     * 昵称、头像等人
     */
    private TextView mActivityPublishPyqTixinghsuikanContent;
    private ConstraintLayout mActivityPublishPyqTixinghsuikanLayout;
    private ImageView mActivityPublishPyqTixinghsuikanImg;
    /**
     * 提醒谁看
     */
    private TextView mActivityPublishPyqTixinghsuikanText;
    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pubulish_pyq);
        from = getIntent().getStringExtra("from");
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

                    LogUtils.e("mActivityPubylishPeyStart", FileUtils.getFileSize(videoUri.getPath()));

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
                    playRedioButton.setOnClickListener(new View.OnClickListener() {
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

            case Choose_VIDEO:
                if (resultCode == RESULT_OK) {
                    recyclerView.setVisibility(View.GONE);
                    videoImg.setVisibility(View.VISIBLE);
                    mActivityPubylishPeyStart.setVisibility(View.VISIBLE);
                    videoUri = Matisse.obtainResult(data).get(0);
                    Bitmap localVideoThumbnail = getLocalVideoThumbnail(MatisseImageUtil.getRealFilePath(PubulishPYQActivity.this, videoUri));
                    videoImg.setImageBitmap(localVideoThumbnail);
                    videoImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(PubulishPYQActivity.this, ShipinbofangActivity.class);
                            intent.putExtra("uri", MatisseImageUtil.getRealFilePath(PubulishPYQActivity.this, videoUri).toString());
                            ActivityUtils.startActivity(intent);
                        }
                    });
                    playRedioButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(PubulishPYQActivity.this, ShipinbofangActivity.class);
                            intent.putExtra("uri", MatisseImageUtil.getRealFilePath(PubulishPYQActivity.this, videoUri).toString());
                            ActivityUtils.startActivity(intent);
                        }
                    });
                    choiceWho = 2;
                }
                break;
        }
    }

    private Uri videoUri;

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

                    AndPermission.with(PubulishPYQActivity.this)
                            .runtime()
                            .permission(Permission.Group.STORAGE, Permission.Group.CAMERA)
                            .onGranted(permissions -> {
                                // Storage permission are allowed.
                                if (choiceNumber >= 1) {
                                    showHeadPortraitDialog(choiceNumber);


                                } else {
                                    ToastUtils.showShort("最多只能选择9张图片");
                                }
                            })
                            .onDenied(permissions -> {
                                // Storage permission are not allowed.
                                ToastUtils.showShort("权限申请失败,您可能无法使用某些功能");
                            })
                            .start();
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
        playRedioButton = findViewById(R.id.activity_pubylish_pey_start);

        mActivityPublishPyqAddressLayout = (ConstraintLayout) findViewById(R.id.activity_publish_pyq_address_layout);
        mActivityPublishPyqAddressLayout.setOnClickListener(this);
        mActivityPublishPyqAddressLayoutImg = (ImageView) findViewById(R.id.activity_publish_pyq_address_layout_img);
        mActivityPublishPyqAddressLayoutTitle = (TextView) findViewById(R.id.activity_publish_pyq_address_layout_title);
        mActivityPublishPyqHuatiLayout = (ConstraintLayout) findViewById(R.id.activity_publish_pyq_huati_layout);
        mActivityPublishPyqHuatiLayout.setOnClickListener(this);
        mActivityPublishPyqHuatiName = (TextView) findViewById(R.id.activity_publish_pyq_huati_name);
        mActivityPublishPyqHuatiImg = (ImageView) findViewById(R.id.activity_publish_pyq_huati_img);
        mActivityPublishPyqZixunGuanlianzixunImg = (ImageView) findViewById(R.id.activity_publish_pyq_zixun_guanlianzixun_img);
        mActivityPublishPyqZixunGuanlianzixun = (TextView) findViewById(R.id.activity_publish_pyq_zixun_guanlianzixun);
        mActivityPublishPyqZixunGuanlianzixunState = (TextView) findViewById(R.id.activity_publish_pyq_zixun_guanlianzixun_state);
        mActivityPublishPyqZixunLayout = (ConstraintLayout) findViewById(R.id.activity_publish_pyq_zixun_layout);
        mActivityPublishPyqZixunLayout.setOnClickListener(this);
        mActivityPublishPyqLianjieImg = (ImageView) findViewById(R.id.activity_publish_pyq_lianjie_img);
        mActivityPublishPyqLianjieTitle = (TextView) findViewById(R.id.activity_publish_pyq_lianjie_title);
        mActivityPublishPyqLianjieContent = (TextView) findViewById(R.id.activity_publish_pyq_lianjie_content);
        mActivityPublishPyqLianjieDelete = (TextView) findViewById(R.id.activity_publish_pyq_lianjie_delete);
        mActivityPublishPyqLianjieDelete.setOnClickListener(this);
        mActivityPublishPyqLianjieLayout = (ConstraintLayout) findViewById(R.id.activity_publish_pyq_lianjie_layout);
        mActivityPublishPyqShuikeyikanImg = (ImageView) findViewById(R.id.activity_publish_pyq_shuikeyikan_img);
        mActivityPublishPyqShuikeyikanText = (TextView) findViewById(R.id.activity_publish_pyq_shuikeyikan_text);
        mActivityPublishPyqShuikeyikanStatus = (TextView) findViewById(R.id.activity_publish_pyq_shuikeyikan_status);
        mActivityPublishPyqShuikeyikanLayout = (ConstraintLayout) findViewById(R.id.activity_publish_pyq_shuikeyikan_layout);
        mActivityPublishPyqShuikeyikanLayout.setOnClickListener(this);
        mActivityPublishPyqTixinghsuikanContent = (TextView) findViewById(R.id.activity_publish_pyq_tixinghsuikan_content);
        mActivityPublishPyqTixinghsuikanLayout = (ConstraintLayout) findViewById(R.id.activity_publish_pyq_tixinghsuikan_layout);
        mActivityPublishPyqTixinghsuikanLayout.setOnClickListener(this);
        mActivityPublishPyqTixinghsuikanImg = (ImageView) findViewById(R.id.activity_publish_pyq_tixinghsuikan_img);
        mActivityPublishPyqTixinghsuikanText = (TextView) findViewById(R.id.activity_publish_pyq_tixinghsuikan_text);

        if(!StringUtils.isEmpty(from)){
            if(StringUtils.equals("chanyezixun",from)){
                NewsBean newsBean = (NewsBean) getIntent().getParcelableExtra("newsBean");
                mActivityPublishPyqLianjieLayout.setVisibility(View.VISIBLE);
                mActivityPublishPyqZixunGuanlianzixun.setTextColor(getResources().getColor(R.color.hongse));
                mActivityPublishPyqZixunGuanlianzixunImg.setImageDrawable(getResources().getDrawable(R.mipmap.fbpyq_zixun_checked));
                mActivityPublishPyqZixunGuanlianzixunState.setTextColor(getResources().getColor(R.color.hongse));
                mActivityPublishPyqZixunGuanlianzixunState.setText("已选中");
                upLoadShareId = String.valueOf(newsBean.getId());
                upLoadShareType = "info";
                GlideUtils.loadImage(this, ServerInfo.IMAGE + newsBean.getImg_url(), mActivityPublishPyqLianjieImg);
                mActivityPublishPyqLianjieTitle.setText(newsBean.getTitle());
                mActivityPublishPyqLianjieContent.setText(newsBean.getContent_summary());
            }
        }

    }

    private Dialog chooseHeadDialog;

    private void showHeadPortraitDialog(int choiceNumber) {


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

        if (choiceNumber <= 8) {
            TextView takeVideo = (TextView) contentView.findViewById(R.id.tv_take_video);
            TextView chooseVideo = (TextView) contentView.findViewById(R.id.tv_choose_video);
            contentView.findViewById(R.id.tv_take_video).setEnabled(false);
            takeVideo.setTextColor(getResources().getColor(R.color.fengexian));
            contentView.findViewById(R.id.tv_choose_video).setEnabled(false);
            chooseVideo.setTextColor(getResources().getColor(R.color.fengexian));
        }


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

        // 从相册选择视频
        contentView.findViewById(R.id.tv_choose_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseHeadDialog.dismiss();  // 选择之后，关闭dialog
                if (Build.VERSION.SDK_INT >= 23) {
                    int checkCallWritePermission = ContextCompat.checkSelfPermission(PubulishPYQActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (checkCallWritePermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(PubulishPYQActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 4);
                        return;
                    } else {
                        // 打开本地视频
                        MatisseImageUtil.chooseVideo(PubulishPYQActivity.this, Choose_VIDEO);
                    }
                } else {
                    // 打开本地视频
                    MatisseImageUtil.chooseVideo(PubulishPYQActivity.this, Choose_VIDEO);
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
    private static final int Choose_VIDEO = 400;

    //拍摄视频
    private void takeVideo() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            try {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 15);//限制录制时间
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
//    MediaMetadataRetriever mmr = new MediaMetadataRetriever();//实例化MediaMetadataRetriever对象
//     mmr.setDataSource(file.getAbsolutePath());
//    String duration = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION);//时长(毫秒)
//    // Log.d("ddd", "duration==" + duration);
//    int int_duration = Integer.parseInt(duration);
//    if (int_duration > 11000) {
//        Toast.makeText(getApplicationContext(), "视频时长已超过10秒，请重新选择", Toast.LENGTH_SHORT).show();
//        return;            }


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
    public void onReciveMessage(MessageBean msg) {
        switch (msg.getCode()) {
            case MessageBean.Code.PENGYOUQUANCHOICEADDRESS:
                PoiItem poiItem = (PoiItem) msg.getObj();
                mActivityPublishPyqAddressLayoutImg.setImageDrawable(getResources().getDrawable(R.mipmap.fbpyq_position_checked));
                mActivityPublishPyqAddressLayoutTitle.setText(poiItem.getTitle());
                mActivityPublishPyqAddressLayoutTitle.setTextColor(getResources().getColor(R.color.hongse));
                upLoadLat = String.valueOf(poiItem.getLatLonPoint().getLatitude());
                upLoadLot = String.valueOf(poiItem.getLatLonPoint().getLongitude());
                upLoadAddressTitle = poiItem.getTitle();
                upLoadAddressContent = poiItem.getCityName() + poiItem.getAdName() + poiItem.getSnippet();
                break;
            case MessageBean.Code.PENGYOUQUANNOCHOICEADDRESS:
                mActivityPublishPyqAddressLayoutImg.setImageDrawable(getResources().getDrawable(R.mipmap.fbpyq_position_unchecked));
                mActivityPublishPyqAddressLayoutTitle.setText("你的位置");
                mActivityPublishPyqAddressLayoutTitle.setTextColor(getResources().getColor(R.color.putongwenben));
                upLoadLat = "";
                upLoadLot = "";
                upLoadAddressTitle = "";
                break;
            case MessageBean.Code.PENGYOUQUANCHOICEADDRESSBYSEARCH:
                Tip tip = (Tip) msg.getObj();
                mActivityPublishPyqAddressLayoutImg.setImageDrawable(getResources().getDrawable(R.mipmap.fbpyq_position_checked));
                mActivityPublishPyqAddressLayoutTitle.setText(tip.getName());
                mActivityPublishPyqAddressLayoutTitle.setTextColor(getResources().getColor(R.color.hongse));
                upLoadLat = String.valueOf(tip.getPoint().getLatitude());
                upLoadLot = String.valueOf(tip.getPoint().getLongitude());
                upLoadAddressTitle = tip.getName();
                upLoadAddressContent = tip.getAddress();
                break;
            case MessageBean.Code.CHOICEERJIHUATI:
                String obj = (String) msg.getObj();
                mActivityPublishPyqHuatiName.setText(msg.getMeaasge());
                upErjiHuatiId = obj;
                mActivityPublishPyqHuatiName.setTextColor(getResources().getColor(R.color.hongse));
                mActivityPublishPyqHuatiImg.setImageDrawable(getResources().getDrawable(R.mipmap.fbpyq_huati_checked));
                break;

            case MessageBean.Code.CHOICEZIXUN:
                mActivityPublishPyqLianjieLayout.setVisibility(View.VISIBLE);
                NewsBean newsBean = (NewsBean) msg.getObj();
                mActivityPublishPyqZixunGuanlianzixun.setTextColor(getResources().getColor(R.color.hongse));
                mActivityPublishPyqZixunGuanlianzixunImg.setImageDrawable(getResources().getDrawable(R.mipmap.fbpyq_zixun_checked));
                mActivityPublishPyqZixunGuanlianzixunState.setTextColor(getResources().getColor(R.color.hongse));
                mActivityPublishPyqZixunGuanlianzixunState.setText("已选中");
                upLoadShareId = String.valueOf(newsBean.getId());
                upLoadShareType = "info";
                GlideUtils.loadImage(this, ServerInfo.IMAGE + newsBean.getImg_url(), mActivityPublishPyqLianjieImg);
                mActivityPublishPyqLianjieTitle.setText(newsBean.getTitle());
                mActivityPublishPyqLianjieContent.setText(newsBean.getContent_summary());
                break;
            case MessageBean.Code.CHOICETIXINGSHUIKAN:
                //提醒谁看
                tixingshuikanDatas.clear();
                tixingshuikanDatas.addAll((ArrayList<MyFriendsBean>) msg.getObj());

                List<String> nameList = new ArrayList<>();
                List<String> idList = new ArrayList<>();
                boolean hasData = false;//是否选择了数据
                for (int i = 0; i < tixingshuikanDatas.size(); i++) {
                    if (tixingshuikanDatas.get(i).isChecked()) {
                        idList.add(String.valueOf(tixingshuikanDatas.get(i).getUserId()));
                        nameList.add(tixingshuikanDatas.get(i).getUserNickName());
                        hasData = true;
                    }
                }

                if (hasData) {
                    mActivityPublishPyqTixinghsuikanContent.setText((nameList.toString()).substring(1, nameList.toString().length() - 1));
                    mActivityPublishPyqTixinghsuikanImg.setImageDrawable(getResources().getDrawable(R.mipmap.fbpyq_tixingshuikan_checked));
                    mActivityPublishPyqTixinghsuikanText.setTextColor(getResources().getColor(R.color.hongse));
                    upNoticeUserId = (idList.toString()).substring(1, idList.toString().length() - 1);
                } else {
                    mActivityPublishPyqTixinghsuikanContent.setText("");
                    mActivityPublishPyqTixinghsuikanImg.setImageDrawable(getResources().getDrawable(R.mipmap.fbpyq_tixingshuikan_unchecked));
                    mActivityPublishPyqTixinghsuikanText.setTextColor(getResources().getColor(R.color.putongwenben));
                    upNoticeUserId = "";
                }

                break;

            case MessageBean.Code.QUEDINGSHUIKAN:
                shukeyikanType = msg.getMeaasge();
                upAppointUserId = "";
                if(StringUtils.equals(shukeyikanType,"1")){
                    mActivityPublishPyqShuikeyikanStatus.setText("公开");
                    return;
                }

                if(StringUtils.equals(shukeyikanType,"2")){
                    mActivityPublishPyqShuikeyikanStatus.setText("全部好友可见");
                    return;
                }
                mActivityPublishPyqShuikeyikanStatus.setText("部分好友可见");
                //谁可以看
                shuikeyikanDatas.clear();
                shuikeyikanDatas.addAll((ArrayList<MyFriendsBean>) msg.getObj());

                List<String> shuikeyikanIdList = new ArrayList<>();
                boolean shuikeyikanHasData = false;//是否选择了数据
                for (int i = 0; i < shuikeyikanDatas.size(); i++) {
                    if (shuikeyikanDatas.get(i).isChecked()) {
                        shuikeyikanIdList.add(String.valueOf(shuikeyikanDatas.get(i).getUserId()));
                        shuikeyikanHasData = true;
                    }
                }
                if (shuikeyikanHasData) {
                    mActivityPublishPyqShuikeyikanImg.setImageDrawable(getResources().getDrawable(R.mipmap.fbpyq_shuikeyikan_checked));
                    mActivityPublishPyqShuikeyikanText.setTextColor(getResources().getColor(R.color.hongse));
                    upAppointUserId = (shuikeyikanIdList.toString()).substring(1, shuikeyikanIdList.toString().length() - 1);
                } else {
                    mActivityPublishPyqShuikeyikanStatus.setText("公开");
                    mActivityPublishPyqShuikeyikanImg.setImageDrawable(getResources().getDrawable(R.mipmap.fbpyq_shuikeyikan_unchecked));
                    mActivityPublishPyqShuikeyikanText.setTextColor(getResources().getColor(R.color.putongwenben));
                    upAppointUserId = "";
                }

                break;
        }
    }

    //提醒谁看
    private ArrayList<MyFriendsBean> tixingshuikanDatas = new ArrayList<>();

    //谁可以看
    private ArrayList<MyFriendsBean> shuikeyikanDatas = new ArrayList<>();

    //上传的二级话题的ID
    private String upErjiHuatiId;

    //上传的位置信息
    private String upLoadLat;
    private String upLoadLot;
    private String upLoadAddressTitle;
    private String upLoadAddressContent;

    //提醒谁看的用户ID
    private String upNoticeUserId;

    //指定谁看的用户ID
    private String upAppointUserId;

    //分享内容类型，info:资讯；enquiry:求购；product：产品；market：店铺；auction：竞拍；groupBuy:团购；meeting：采购联盟；sales;优惠展销;
    private String upLoadShareType;

    //分享内容的id
    private String upLoadShareId;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.activity_pubylish_pey_cancle:
                KeyboardUtils.hideSoftInput(this);
                finish();
                break;
            case R.id.activity_pubylish_pey_publish:
                KeyboardUtils.hideSoftInput(this);
                if (ObjectUtils.isEmpty(updateImgList) && ObjectUtils.isEmpty(videoUri) && StringUtils.isTrimEmpty(mActivityPubylishPeyContent.getText().toString())) {
                    ToastUtils.showShort("不能发空的内容!");
                    return;
                }
                if (!ObjectUtils.isEmpty(videoUri)) {

                    updateVideo(new File(MatisseImageUtil.getRealFilePath(PubulishPYQActivity.this, videoUri)), mActivityPubylishPeyContent.getText().toString());
                    return;
                } else if (!ObjectUtils.isEmpty(updateImgList)) {
                    //上传图片
                    List<File> picFilePaths = new ArrayList<>();
                    for (int i = 0; i < updateImgList.size(); i++) {
                        String picFilePath = MatisseImageUtil.getRealFilePath(this, updateImgList.get(i));
                        File file = new File(picFilePath);
                        File file1 = null;
                        try {
                            file1 = new Compressor(this).compressToFile(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        picFilePaths.add(file1);

                    }
                    updateContent(picFilePaths, mActivityPubylishPeyContent.getText().toString());
                    return;
                } else if (!StringUtils.isTrimEmpty(mActivityPubylishPeyContent.getText().toString())) {
                    updateChunText(mActivityPubylishPeyContent.getText().toString());
                }
                break;
            case R.id.activity_publish_pyq_address_layout:
                ActivityUtils.startActivity(PengyouquanChoiceAddressActivity.class);
                break;
            case R.id.activity_publish_pyq_huati_layout:
                ActivityUtils.startActivity(ChoiceHuatiActivity.class);
                break;
            case R.id.activity_publish_pyq_zixun_layout:
                Intent zixunIntent = new Intent(this, ChanyezixunActivity.class);
                zixunIntent.putExtra("from", "pengyouquan_fabu");
                ActivityUtils.startActivity(zixunIntent);
                break;
            case R.id.activity_publish_pyq_lianjie_delete:

                mActivityPublishPyqLianjieLayout.setVisibility(View.GONE);
                upLoadShareId = null;
                upLoadShareType = null;
                mActivityPublishPyqZixunGuanlianzixun.setTextColor(getResources().getColor(R.color.putongwenben));
                mActivityPublishPyqZixunGuanlianzixunImg.setImageDrawable(getResources().getDrawable(R.mipmap.fbpyq_zixun_unchecked));
                mActivityPublishPyqZixunGuanlianzixunState.setTextColor(getResources().getColor(R.color.putongwenben));
                mActivityPublishPyqZixunGuanlianzixunState.setText("未关联");
                break;
            case R.id.activity_publish_pyq_shuikeyikan_layout:
                Intent shuikeyikanIntent = new Intent(this, ShuikeyikanActivity.class);
                shuikeyikanIntent.putParcelableArrayListExtra("shuikeyikan", shuikeyikanDatas);
                shuikeyikanIntent.putExtra("shukeyikanType",shukeyikanType);
                ActivityUtils.startActivity(shuikeyikanIntent);
                break;
            case R.id.activity_publish_pyq_tixinghsuikan_layout:
                Intent intent = new Intent(this, MyTongunluFriendsActivity.class);
                intent.putParcelableArrayListExtra("tixingshuikan", tixingshuikanDatas);
                intent.putExtra("from", "tixingshuikan");
                ActivityUtils.startActivity(intent);
                break;
        }
    }

    private String shukeyikanType ="1" ;//1为全部  2为全部好友  3为部分好友

    ProgressDialog progressDialog;
    private boolean isWaitResponse;//上传视频完成后等待服务器相应，弹出对话框

    private void updateVideo(File videoPath, String content) {
        progressDialog = new ProgressDialog(this);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("正在上传...");
        progressDialog.setMax((int) FileUtils.getFileLength(videoPath));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setButton(ProgressDialog.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog.dismiss();
                OkGo.getInstance().cancelTag(PubulishPYQActivity.this);
            }
        });
        progressDialog.show();

        HttpParams params = new HttpParams();
        params.put("file", videoPath);
        params.put("type", "video");
        params.put("token", SPUtils.getInstance().getString("token"));
        params.put("sign", SPUtils.getInstance().getString("sign"));
        params.put("content", content);
        params.put("from", getResources().getString(R.string.app_android));

        if (!StringUtils.isEmpty(upErjiHuatiId)) {
            params.put("level2TopicId", upErjiHuatiId);
        }
        if (!StringUtils.isEmpty(upLoadAddressTitle)) {
            params.put("locationTitle", upLoadAddressTitle);
        }
        if (!StringUtils.isEmpty(upLoadAddressContent)) {
            params.put("locationAddress", upLoadAddressContent);
        }

        if (!StringUtils.isEmpty(upLoadShareId)) {
            params.put("shareId", upLoadShareId);
        }
        if (!StringUtils.isEmpty(upLoadShareType)) {
            params.put("shareType", "info");
        }

        if (!StringUtils.isEmpty(upNoticeUserId)) {
            params.put("noticeUserId", upNoticeUserId);
        }

        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.FABUPENGYOUQUAN)
                .tag(this)
                .params(params);


        StringCallback stringCallback = new StringCallback() {

            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.e("FABUPENGYOUQUAN", response.body());
                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            String data = jsonObject.getString("data");
                            if (StringUtils.equals("true", data)) {
                                ToastUtils.showShort(msg);
                                EventBus.getDefault().post(new MessageBean(MessageBean.Code.FABUPENGYOUQUANCHENGGONG));
                                finish();
                            } else ToastUtils.showShort(msg);

                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(PubulishPYQActivity.this, request, this);
                            return;
                        }
                        ToastUtils.showShort(msg);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void uploadProgress(Progress progress) {
                super.uploadProgress(progress);

                if ((progress.currentSize / FileUtils.getFileLength(videoPath)) > 0.9f && isWaitResponse == false) {
                    progressDialog.dismiss();
                    progressDialog.cancel();
                    ProgressDialog dialog = new ProgressDialog(activity);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    dialog.setMessage("正在处理视频，请稍候...");
                    dialog.show();
                    isWaitResponse = true;
                }
                if (!isWaitResponse) {
                    progressDialog.setProgress((int) progress.currentSize);
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

    private void updateChunText(String content) {
        HttpParams params = new HttpParams();
        params.put("type", "photo");
        params.put("token", SPUtils.getInstance().getString("token"));
        params.put("sign", SPUtils.getInstance().getString("sign"));
        params.put("content", content);
        params.put("from", getResources().getString(R.string.app_android));
        if (!StringUtils.isEmpty(upLoadLat)) {
            params.put("latitude", upLoadLat);
        }
        if (!StringUtils.isEmpty(upLoadLot)) {
            params.put("longitude", upLoadLot);
        }
        if (!StringUtils.isEmpty(upLoadAddressTitle)) {
            params.put("locationTitle", upLoadAddressTitle);
        }
        if (!StringUtils.isEmpty(upLoadAddressContent)) {
            params.put("locationAddress", upLoadAddressContent);
        }
        if (!StringUtils.isEmpty(upErjiHuatiId)) {
            params.put("level2TopicId", upErjiHuatiId);
        }

        if (!StringUtils.isEmpty(upLoadShareId)) {
            params.put("shareId", upLoadShareId);
        }

        if (!StringUtils.isEmpty(upLoadShareType)) {
            params.put("shareType", "info");
        }

        if (!StringUtils.isEmpty(upNoticeUserId)) {

            params.put("noticeUserId", upNoticeUserId);
        }

        if(StringUtils.equals("1",shukeyikanType)){

        }else if(StringUtils.equals("2",shukeyikanType)){
            params.put("appointType", "all");
        }else if(StringUtils.equals("3",shukeyikanType)){
            params.put("appointType", "notAll");
            params.put("appointUserId",upAppointUserId);
        }

        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.FABUPENGYOUQUAN)
                .tag(this)
                .params(params)
                .isMultipart(true);

        DialogStringCallback stringCallback = new DialogStringCallback(PubulishPYQActivity.this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.e("FABUPENGYOUQUAN", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");

                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            String data = jsonObject.getString("data");
                            if (StringUtils.equals("true", data)) {
                                ToastUtils.showShort(msg);
                                EventBus.getDefault().post(new MessageBean(MessageBean.Code.FABUPENGYOUQUANCHENGGONG));
                                finish();
                            } else ToastUtils.showShort(msg);

                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(PubulishPYQActivity.this, request, this);
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


    //上传图片
    private void updateContent(List<File> picPath, String content) {

        HttpParams params = new HttpParams();
        params.put("type", "photo");
        params.put("token", SPUtils.getInstance().getString("token"));
        params.put("sign", SPUtils.getInstance().getString("sign"));
        params.put("content", content);
        params.put("from", getResources().getString(R.string.app_android));

        if (!StringUtils.isEmpty(upLoadLat)) {
            params.put("latitude", upLoadLat);
        }
        if (!StringUtils.isEmpty(upLoadLot)) {
            params.put("longitude", upLoadLot);
        }
        if (!StringUtils.isEmpty(upLoadAddressTitle)) {
            params.put("locationTitle", upLoadAddressTitle);
        }
        if (!StringUtils.isEmpty(upLoadAddressContent)) {
            params.put("locationAddress", upLoadAddressContent);
        }
        if (!StringUtils.isEmpty(upErjiHuatiId)) {
            params.put("level2TopicId", upErjiHuatiId);
        }

        if (!StringUtils.isEmpty(upLoadShareId)) {
            params.put("shareId", upLoadShareId);
        }
        if (!StringUtils.isEmpty(upLoadShareType)) {
            params.put("shareType", "info");
        }

        if (!StringUtils.isEmpty(upNoticeUserId)) {
            params.put("noticeUserId", upNoticeUserId);
        }
        // 压缩图片
        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.FABUPENGYOUQUAN)
                .tag(this)
                .params(params)
                .addFileParams("files", picPath).isMultipart(true);

        DialogStringCallback stringCallback = new DialogStringCallback(PubulishPYQActivity.this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.e("FABUPENGYOUQUAN", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            String data = jsonObject.getString("data");
                            if (StringUtils.equals("true", data)) {
                                ToastUtils.showShort(msg);
                                EventBus.getDefault().post(new MessageBean(MessageBean.Code.FABUPENGYOUQUANCHENGGONG));
                                finish();
                            } else ToastUtils.showShort(msg);
                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(PubulishPYQActivity.this, request, this);
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
