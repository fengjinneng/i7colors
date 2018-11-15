package com.company.qcy.ui.activity.user;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.MyGlideEngine;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.base.BaseActivity;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;

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
        }
    }

    private int REQUEST_CODE_CHOOSE = 1;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.activity_zhanghaozhongxin_img:
                Matisse.from(this)
                        .choose(MimeType.ofImage())
                        .countable(false)
                        .maxSelectable(1)
//                        .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new MyGlideEngine())
                        .forResult(REQUEST_CODE_CHOOSE);

                break;
            case R.id.activity_zhanghaozhongxin_changepawwsord:


                break;
            case R.id.activity_zhanghaozhongxin_shenqing:


                break;
            case R.id.activity_zhanghaozhongxin_back:
                finish();
                break;
        }
    }
}
