package com.company.qcy;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.base.WebActivity;
import com.company.qcy.bean.BannerBean;
import com.company.qcy.huodong.caigoulianmeng2.activity.CaigoulianmengActivity;
import com.company.qcy.huodong.jingpai.activity.JingpaiActivity;
import com.company.qcy.huodong.jingpai.activity.JingpaiDetailActivity;
import com.company.qcy.huodong.tuangou.activity.TuangouliebiaoActivity;
import com.company.qcy.huodong.tuangou.activity.TuangouxiangqingActivity;
import com.company.qcy.ui.activity.chanpindating.ChanpindatingActivity;
import com.company.qcy.ui.activity.chanpindating.ChanpinxiangqingActivity;
import com.company.qcy.ui.activity.chanyezixun.ChanyezixunActivity;
import com.company.qcy.ui.activity.chanyezixun.ZixunxiangqingActivity;
import com.company.qcy.ui.activity.kaifangshangcheng.KFSCXiangqingActivity;
import com.company.qcy.ui.activity.kaifangshangcheng.KaifangshangchengActivity;
import com.company.qcy.ui.activity.qiugoudating.QiugoudatingActivity;
import com.company.qcy.ui.activity.qiugoudating.QiugouxiangqingActivity;
import com.company.qcy.ui.activity.zhuji.ZhujiDetailActivity;
import com.company.qcy.ui.activity.zhuji.ZhujiListActivity;
import com.company.qcy.ui.activity.zhuji.ZhujiQiyeListActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyWelcomeActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 进入
     */
    private TextView mActivityWelcomeText;

    private ImageView mActivityWelcomeImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
    }

    private boolean isClick = false;

    private CountDownTimer timer;

    private void initView() {
        mActivityWelcomeText = (TextView) findViewById(R.id.activity_welcome_text);
        mActivityWelcomeText.setOnClickListener(this);

        timer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                SimpleDateFormat sdf = new SimpleDateFormat("ss");
                mActivityWelcomeText.setText(sdf.format(new Date(millisUntilFinished)) + "S 跳过");
            }

            @Override
            public void onFinish() {
                if (!isClick) {
                    finish();
                }

            }
        }.start();
        mActivityWelcomeImg = (ImageView) findViewById(R.id.activity_welcome_img);

        GlideUtils.loadImageWithStartPage(this, ServerInfo.IMAGE + SPUtils.getInstance().getString("adv"), mActivityWelcomeImg);
//        addAdvData();
        mActivityWelcomeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!StringUtils.isEmpty(SPUtils.getInstance().getString("adv_directType"))) {

                    if (StringUtils.equals("enquiry", SPUtils.getInstance().getString("adv_directType"))) {

                        if (StringUtils.isEmpty(SPUtils.getInstance().getString("adv_directTypeId"))) {
                            Intent enquiryIntent = new Intent(context, QiugoudatingActivity.class);
//                        enquiryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            ActivityUtils.startActivity(enquiryIntent);
                        } else {
                            Intent enquiryIntent = new Intent(context, QiugouxiangqingActivity.class);
                            enquiryIntent.putExtra("enquiryId", Long.parseLong(SPUtils.getInstance().getString("adv_directTypeId")));
//                        enquiryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            ActivityUtils.startActivity(enquiryIntent);
                        }
                    } else if (StringUtils.equals("market", SPUtils.getInstance().getString("adv_directType"))) {
                        if (StringUtils.isEmpty(SPUtils.getInstance().getString("adv_directTypeId"))) {
                            Intent marketIntent = new Intent(context, KaifangshangchengActivity.class);
//                        marketIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            ActivityUtils.startActivity(marketIntent);
                        } else {
                            Intent marketIntent = new Intent(context, KFSCXiangqingActivity.class);
                            marketIntent.putExtra("id", Long.parseLong(SPUtils.getInstance().getString("adv_directTypeId")));
//                        marketIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            ActivityUtils.startActivity(marketIntent);
                        }
                    } else if (StringUtils.equals("product", SPUtils.getInstance().getString("adv_directType"))) {
                        if (StringUtils.isEmpty(SPUtils.getInstance().getString("adv_directTypeId"))) {
                            Intent productIntent = new Intent(context, ChanpindatingActivity.class);
//                        productIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            ActivityUtils.startActivity(productIntent);

                        } else {
                            Intent productIntent = new Intent(context, ChanpinxiangqingActivity.class);
                            productIntent.putExtra("id", SPUtils.getInstance().getString("adv_directTypeId") + "m");
//                        productIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            ActivityUtils.startActivity(productIntent);
                        }
                    } else if (StringUtils.equals("information", SPUtils.getInstance().getString("adv_directType"))) {
                        if (StringUtils.isEmpty(SPUtils.getInstance().getString("adv_directTypeId"))) {
                            Intent zixunIntent = new Intent(context, ChanyezixunActivity.class);
//                        zixunIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            ActivityUtils.startActivity(zixunIntent);
                        } else {
                            Intent zixunIntent = new Intent(context, ZixunxiangqingActivity.class);
                            zixunIntent.putExtra("id", SPUtils.getInstance().getString("adv_directTypeId"));
//                        zixunIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            ActivityUtils.startActivity(zixunIntent);

                        }

                    } else if (StringUtils.equals("groupBuy", SPUtils.getInstance().getString("adv_directType"))) {
                        if (StringUtils.isEmpty(SPUtils.getInstance().getString("adv_directTypeId"))) {
                            Intent tuangouIntent = new Intent(context, TuangouliebiaoActivity.class);
//                        tuangouIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            ActivityUtils.startActivity(tuangouIntent);

                        } else {
                            Intent tuangouIntent = new Intent(context, TuangouxiangqingActivity.class);
                            tuangouIntent.putExtra("id", Long.parseLong(SPUtils.getInstance().getString("adv_directTypeId")));
//                        tuangouIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            ActivityUtils.startActivity(tuangouIntent);
                        }
                    } else if (StringUtils.equals("auction", SPUtils.getInstance().getString("adv_directType"))) {
                        if (StringUtils.isEmpty(SPUtils.getInstance().getString("adv_directTypeId"))) {
                            Intent jingpaiIntent = new Intent(context, JingpaiActivity.class);
//                          tuangouIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            ActivityUtils.startActivity(jingpaiIntent);

                        } else {
                            Intent jingpaiIntent = new Intent(context, JingpaiDetailActivity.class);
                            jingpaiIntent.putExtra("id", SPUtils.getInstance().getString("adv_directTypeId"));
//                        tuangouIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            ActivityUtils.startActivity(jingpaiIntent);
                        }
                    } else if (StringUtils.equals("zhuji", SPUtils.getInstance().getString("adv_directType"))) {
                        if (StringUtils.isEmpty(SPUtils.getInstance().getString("adv_directTypeId"))) {
                            Intent zhujiIntent = new Intent(context, ZhujiQiyeListActivity.class);
//                        tuangouIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            ActivityUtils.startActivity(zhujiIntent);

                        } else {
                            Intent zhujiIntent = new Intent(context, ZhujiDetailActivity.class);
                            zhujiIntent.putExtra("id", Long.parseLong(SPUtils.getInstance().getString("adv_directTypeId")));
//                        tuangouIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            ActivityUtils.startActivity(zhujiIntent);
                        }
                    } else if (StringUtils.equals("meeting", SPUtils.getInstance().getString("adv_directType"))) {
                        Intent zhujiIntent = new Intent(context, CaigoulianmengActivity.class);
//                        tuangouIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(zhujiIntent);

                    }
                } else if (!StringUtils.isEmpty(SPUtils.getInstance().getString("advUrl")) && SPUtils.getInstance().getString("advUrl").length() > 4) {
                    isClick = true;
                    timer.cancel();
                    Intent intent = new Intent(MyWelcomeActivity.this, WebActivity.class);
                    intent.putExtra("webUrl", SPUtils.getInstance().getString("advUrl"));
                    ActivityUtils.startActivity(intent);
                    finish();
                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.activity_welcome_text:
                isClick = true;
                timer.cancel();
                finish();
                break;
        }
    }
}
