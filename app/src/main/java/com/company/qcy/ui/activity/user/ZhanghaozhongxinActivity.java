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
import com.company.qcy.Utils.MyCommonUtil;
import com.company.qcy.Utils.MyGlideEngine;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.ui.activity.pengyouquan.MyDyeInfoActivity;
import com.company.qcy.ui.activity.pengyouquan.PubulishPYQActivity;
import com.githang.statusbar.StatusBarCompat;
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
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.chunhongse),false);
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
            case MessageBean.Code.CHANGEPERSONHEADIMG:
                MyCommonUtil.jiazaitouxiang(this,msg.getMeaasge(),mActivityZhanghaozhongxinImg);
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.activity_zhanghaozhongxin_img:
                Intent i = new Intent(this,ChoiceHeadImageActivity.class);
                i.putExtra("imgUrl",SPUtils.getInstance().getString("photo"));
                i.putExtra("from","mine");
                i.putExtra("iszhanghaozhongxin","1");
                ActivityUtils.startActivity(i);

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
