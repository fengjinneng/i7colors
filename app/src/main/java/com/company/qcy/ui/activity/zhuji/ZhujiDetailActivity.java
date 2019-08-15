package com.company.qcy.ui.activity.zhuji;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.MyCommonUtil;
import com.company.qcy.Utils.UserUtil;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.zhuji.ZhujiBean;
import com.company.qcy.ui.activity.user.LoginActivity;

import java.util.Date;

public class ZhujiDetailActivity extends BaseActivity implements View.OnClickListener {


    private ZhujiBean zhujiBean;
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private TextView mActivityZhujiDetailType;
    private TextView mActivityZhujiDetailName;
    private TextView mActivityZhujiDetailCaizhi;
    private TextView mActivityZhujiDetailChengpinyongtu;
    private TextView mActivityZhujiDetailHuanbaoyaoqiu;
    private TextView mActivityZhujiDetailShebei;
    private TextView mActivityZhujiDetailRanliao;
    private TextView mActivityZhujiDetailRansewendu;
    private TextView mActivityZhujiDetailXianyongchanpinshuoming;
    private TextView mActivityZhujiDetailShengchanchangjia;
    private TextView mActivityZhujiDetailXuqiuliang;
    private TextView mActivityZhujiDetailXingnengmiaoshu;
    /**
     * 企业用户
     */
    private TextView mActivityZhujiDetailShenfen;
    /**
     * 我的发布
     */
    private TextView mActivityZhujiDetailWodefabu;
    /**
     * ***************公司
     */
    private TextView mActivityZhujiDetailCompanyname;
    private TextView mActivityZhujiDetailZhujimingcheng;
    private TextView qian;
    private TextView qianUnit;
    private TextView hou;
    private TextView houUnit;
    /**
     * 已完成
     */
    private TextView mActivityZhujiDetailYiwancheng;
    private TextView mActivityZhujiDetailSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuji_detail);
        initView();
        zhujiBean = getIntent().getParcelableExtra("zhujiBean");
        setInfo(zhujiBean);
    }

    //助剂定制是否还在进行中
    private boolean isContinue;

    private void setInfo(ZhujiBean bean) {
        if (ObjectUtils.isEmpty(bean)) {
            return;
        }

        mActivityZhujiDetailZhujimingcheng.setText(StringUtils.isEmpty(bean.getZhujiName()) ? "暂无" : bean.getZhujiName());

        mActivityZhujiDetailType.setText(StringUtils.isEmpty(bean.getClassName()) ? "暂无" : bean.getClassName());

        mActivityZhujiDetailName.setText(StringUtils.isEmpty(bean.getZhujiName()) ? "暂无" : bean.getZhujiName());

        mActivityZhujiDetailCaizhi.setText(StringUtils.isEmpty(bean.getMaterial()) ? "暂无" : bean.getMaterial());

        mActivityZhujiDetailChengpinyongtu.setText(StringUtils.isEmpty(bean.getPurpose()) ? "暂无" : bean.getPurpose());

        mActivityZhujiDetailHuanbaoyaoqiu.setText(StringUtils.isEmpty(bean.getRequirement()) ? "暂无" : bean.getRequirement());

        mActivityZhujiDetailShebei.setText(StringUtils.isEmpty(bean.getEquipment()) ? "暂无" : bean.getEquipment());

        mActivityZhujiDetailRanliao.setText(StringUtils.isEmpty(bean.getDye()) ? "暂无" : bean.getDye());

        mActivityZhujiDetailRansewendu.setText(StringUtils.isEmpty(bean.getTemperature()) ? "暂无" : bean.getTemperature());

        mActivityZhujiDetailXianyongchanpinshuoming.setText(StringUtils.isEmpty(bean.getProductName()) ? "暂无" : bean.getProductName());

        mActivityZhujiDetailShengchanchangjia.setText(StringUtils.isEmpty(bean.getProducer()) ? "暂无" : bean.getProducer());

        mActivityZhujiDetailXuqiuliang.setText(ObjectUtils.isEmpty(bean.getNum()) ? "暂无" : bean.getProducer() + "");

        mActivityZhujiDetailXingnengmiaoshu.setText(StringUtils.isEmpty(bean.getDescription()) ? "暂无" : bean.getDescription());


        if (StringUtils.isEmpty(bean.getPublishType())) {
            mActivityZhujiDetailShenfen.setVisibility(View.GONE);
        } else {
            mActivityZhujiDetailShenfen.setVisibility(View.VISIBLE);
            mActivityZhujiDetailShenfen.setText(bean.getPublishType());
            if (StringUtils.equals("个人发布", bean.getPublishType())) {
                mActivityZhujiDetailShenfen.setBackground(getResources().getDrawable(R.mipmap.gerenfabu));
            } else if (StringUtils.equals("企业发布", bean.getPublishType())) {
                mActivityZhujiDetailShenfen.setBackground(getResources().getDrawable(R.mipmap.qiyeyonghu));
            } else {
                mActivityZhujiDetailShenfen.setVisibility(View.GONE);
            }
        }

        if (StringUtils.equals("1", bean.getIsCharger())) {
            mActivityZhujiDetailWodefabu.setVisibility(View.VISIBLE);
            mActivityZhujiDetailSubmit.setVisibility(View.GONE);
        } else {
            mActivityZhujiDetailWodefabu.setVisibility(View.GONE);
            mActivityZhujiDetailSubmit.setVisibility(View.VISIBLE);
        }

        if (StringUtils.isEmpty(bean.getStatus())) {
        } else {
            if (StringUtils.equals("diying", bean.getStatus())) {
                isContinue = true;
                mActivityZhujiDetailYiwancheng.setVisibility(View.GONE);
                mActivityZhujiDetailSubmit.setText("提交方案");
            } else {
                mActivityZhujiDetailYiwancheng.setVisibility(View.VISIBLE);
                mActivityZhujiDetailSubmit.setText("已完成");
                mActivityZhujiDetailSubmit.setBackgroundColor(getResources().getColor(R.color.fengexian));
            }
        }


        if (StringUtils.isEmpty(bean.getEndTimeStamp())) {
            qian.setText("");
            qianUnit.setText("");
            hou.setText("");
            houUnit.setText("");
        } else {
            MyCommonUtil.setDaojishiDate(bean.getEndTimeStamp(),
                    qian, qianUnit, hou, houUnit);
        }

        if(StringUtils.equals("1",bean.getIsCharger())){
            mActivityZhujiDetailCompanyname.setText(bean.getCompanyName());
        }

    }

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mActivityZhujiDetailType = (TextView) findViewById(R.id.activity_zhuji_detail_type);
        mActivityZhujiDetailName = (TextView) findViewById(R.id.activity_zhuji_detail_name);
        mActivityZhujiDetailCaizhi = (TextView) findViewById(R.id.activity_zhuji_detail_caizhi);
        mActivityZhujiDetailChengpinyongtu = (TextView) findViewById(R.id.activity_zhuji_detail_chengpinyongtu);
        mActivityZhujiDetailHuanbaoyaoqiu = (TextView) findViewById(R.id.activity_zhuji_detail_huanbaoyaoqiu);
        mActivityZhujiDetailShebei = (TextView) findViewById(R.id.activity_zhuji_detail_shebei);
        mActivityZhujiDetailRanliao = (TextView) findViewById(R.id.activity_zhuji_detail_ranliao);
        mActivityZhujiDetailRansewendu = (TextView) findViewById(R.id.activity_zhuji_detail_ransewendu);
        mActivityZhujiDetailXianyongchanpinshuoming = (TextView) findViewById(R.id.activity_zhuji_detail_xianyongchanpinmingcheng);
        mActivityZhujiDetailShengchanchangjia = (TextView) findViewById(R.id.activity_zhuji_detail_shengchanchangjia);
        mActivityZhujiDetailXuqiuliang = (TextView) findViewById(R.id.activity_zhuji_detail_xuqiuliang);
        mActivityZhujiDetailXingnengmiaoshu = (TextView) findViewById(R.id.activity_zhuji_detail_xingnengmiaoshu);
        mToolbarTitle.setText("助剂定制详情");
        mActivityZhujiDetailShenfen = (TextView) findViewById(R.id.activity_zhuji_detail_shenfen);
        mActivityZhujiDetailWodefabu = (TextView) findViewById(R.id.activity_zhuji_detail_wodefabu);
        mActivityZhujiDetailCompanyname = (TextView) findViewById(R.id.activity_zhuji_detail_companyname);
        mActivityZhujiDetailZhujimingcheng = (TextView) findViewById(R.id.activity_zhuji_detail_zhujimingcheng);
        qian = (TextView) findViewById(R.id.activity_zhuji_detail_qian);
        qianUnit = (TextView) findViewById(R.id.activity_zhuji_detail_qian_unit);
        hou = (TextView) findViewById(R.id.activity_zhuji_detail_hou);
        houUnit = (TextView) findViewById(R.id.activity_zhuji_detail_hou_unit);
        mActivityZhujiDetailYiwancheng = (TextView) findViewById(R.id.activity_zhuji_detail_yiwancheng);
        mActivityZhujiDetailSubmit = (TextView) findViewById(R.id.activity_zhuji_detail_submit);
        mActivityZhujiDetailSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_zhuji_detail_submit:
                if (UserUtil.isLogin()) {
                    if(StringUtils.isEmpty(SPUtils.getInstance().getString("companyName"))){
                        ToastUtils.showShort("个人用户不能提交方案!");
                        return;
                    }
                    if (!ObjectUtils.isEmpty(zhujiBean) && isContinue) {
                        Intent intent = new Intent(ZhujiDetailActivity.this, TijiaofanganActivity.class);
                        intent.putExtra("zhujiDiyId", zhujiBean.getId());
                        ActivityUtils.startActivity(intent);
                    }
                } else {
                    ActivityUtils.startActivity(LoginActivity.class);
                }
                break;
        }
    }
}