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
import com.company.qcy.ui.activity.user.ChoiceHeadImageActivity;
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

import id.zelory.compressor.Compressor;

public class MyDyeInfoActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private ImageView mActivityMyDyeInfoImg;
    private ConstraintLayout mActivityMyDyeInfoImgLayout;
    /**
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
            case MessageBean.Code.PENGYOUQUANHEADIMGCHANGE:
                GlideUtils.loadCircleImage(this, ServerInfo.IMAGE+msg.getMeaasge(),mActivityMyDyeInfoImg);
                break;
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
            case R.id.activity_my_dye_info_img_layout:
                Intent  i = new Intent(this,ChoiceHeadImageActivity.class);
                i.putExtra("imgUrl",imgUrl);
                i.putExtra("from","mine");
                ActivityUtils.startActivity(i);
                break;
            case R.id.activity_my_dye_info_nickname_layout:
                Intent intent = new Intent(this,ChangeNicknameActivity.class);
                intent.putExtra("nickName",nickName);
                ActivityUtils.startActivity(intent);
                break;
        }
    }
}
