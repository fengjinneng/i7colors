package com.company.qcy.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.MatisseImageUtil;
import com.company.qcy.Utils.MyCommonUtil;
import com.company.qcy.Utils.PermisionUtil;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.eventbus.MessageBean;
import com.githang.statusbar.StatusBarCompat;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

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
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.chunhongse), false);
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
                MyCommonUtil.jiazaitouxiang(this, msg.getMeaasge(), mActivityZhanghaozhongxinImg);
                break;

            //盛情了企业认证
            case MessageBean.Code.QIYERENZHENG:
                renzhengStatus = 2;
                mActivityZhanghaozhongxinShenqing.setText("正在申请中...");
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

        if (StringUtils.equals(getResources().getString(R.string.geren), SPUtils.getInstance().getString("userType"))) {
            getQiyeRengzhengStatu();
            mActivityZhanghaozhongxinShenqing.setVisibility(View.VISIBLE);
        } else {
            mActivityZhanghaozhongxinShenqing.setVisibility(View.INVISIBLE);
        }

    }

    private int renzhengStatus;

    //企业信息ID
    private String qiyexinxiId;

    //企业用户ID
    private String companyId;

    //企业认证名字
    private String companyName;

    private void getQiyeRengzhengStatu() {

        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.QIYERENZHENSTATUS)
                .tag(this)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("sign", SPUtils.getInstance().getString("sign"));

        DialogStringCallback stringCallback = new DialogStringCallback(ZhanghaozhongxinActivity.this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.e("QIYERENZHENSTATUS", response.body());

                try {
                    JSONObject jsonObject = JSONObject.parseObject(response.body());
                    String msg = jsonObject.getString("msg");
                    if (response.code() == 200) {

                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            //status=empty,未进行企业认证status= wait_audit,审核中；status=checked;已锁定；status=audit_fail,审核未通过；status=audit审核通过
                            String status = data.getString("status");
                            if (StringUtils.isEmpty(status)) {
                                return;
                            }

                            switch (status) {
                                //未进行企业认证
                                case "empty":
                                    mActivityZhanghaozhongxinShenqing.setText("申请成为企业账户");
                                    renzhengStatus = 1;
                                    break;
                                //审核中
                                case "wait_audit":
                                    mActivityZhanghaozhongxinShenqing.setText("正在审核中...");
                                    renzhengStatus = 2;
                                    break;
                                //已锁定
                                case "checked":
                                    mActivityZhanghaozhongxinShenqing.setText("已锁定,请联系客服");
                                    renzhengStatus = 3;
                                    break;
                                //审核未通过
                                case "audit_fail":
                                    mActivityZhanghaozhongxinShenqing.setText("审核未通过,点击重新申请");
                                    renzhengStatus = 4;
                                    qiyexinxiId = data.getString("id");
                                    companyId = data.getString("companyId");
                                    companyName = data.getString("companyName");
                                    break;
                                //审核通过
                                case "audit":
                                    mActivityZhanghaozhongxinShenqing.setText("审核通过,重新启动APP生效!");
                                    break;
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
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.activity_zhanghaozhongxin_img:
                Intent i = new Intent(this, ChoiceHeadImageActivity.class);
                i.putExtra("imgUrl", SPUtils.getInstance().getString("photo"));
                i.putExtra("from", "mine");
                i.putExtra("iszhanghaozhongxin", "1");
                ActivityUtils.startActivity(i);

                break;
            case R.id.activity_zhanghaozhongxin_changepawwsord:
                ActivityUtils.startActivity(ChangePasswordActivity.class);

                break;
            case R.id.activity_zhanghaozhongxin_shenqing:
                switch (renzhengStatus) {
                    //未进行企业认证
                    case 1:
                        Intent intent1 = new Intent(ZhanghaozhongxinActivity.this, QiyerenzhengActivity.class);
                        intent1.putExtra("status", 1);
                        ActivityUtils.startActivity(intent1);

                        break;

                    //审核中
                    case 2:
                        Intent intent2 = new Intent(ZhanghaozhongxinActivity.this, QiyerenzhengActivity.class);
                        intent2.putExtra("status", 2);
                        ActivityUtils.startActivity(intent2);
                        break;

                    //已锁定
                    case 3:
                        PermisionUtil.callKefu(ZhanghaozhongxinActivity.this);
                        break;

                    //审核未通过
                    case 4:
                        Intent intent4 = new Intent(ZhanghaozhongxinActivity.this, QiyerenzhengActivity.class);
                        intent4.putExtra("status", 4);

                        if(StringUtils.isEmpty(qiyexinxiId)||StringUtils.isEmpty(companyId)||StringUtils.isEmpty(companyName)){
                            ToastUtils.showShort("信息有误，请稍候重试!");
                            return;
                        }

                        intent4.putExtra("qiyexinxiId", qiyexinxiId);
                        intent4.putExtra("companyId", companyId);
                        intent4.putExtra("companyName", companyName);
                        ActivityUtils.startActivity(intent4);
                        break;

                }
                break;
            case R.id.activity_zhanghaozhongxin_back:
                finish();
                break;
        }
    }
}
