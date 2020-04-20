package com.company.qcy.Utils;

import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.company.qcy.base.WebActivity;
import com.company.qcy.bean.BannerBean;
import com.company.qcy.huodong.caigoulianmeng2.activity.CaigoulianmengActivity;
import com.company.qcy.huodong.jingpai.activity.JingpaiActivity;
import com.company.qcy.huodong.jingpai.activity.JingpaiDetailActivity;
import com.company.qcy.huodong.tuangou.activity.TuangouliebiaoActivity;
import com.company.qcy.huodong.tuangou.activity.TuangouxiangqingActivity;
import com.company.qcy.live.LiveDetailActivity;
import com.company.qcy.live.LiveListActivity;
import com.company.qcy.ui.activity.chanpindating.ChanpindatingActivity;
import com.company.qcy.ui.activity.chanpindating.ChanpinxiangqingActivity;
import com.company.qcy.ui.activity.chanyezixun.ChanyezixunActivity;
import com.company.qcy.ui.activity.chanyezixun.ZixunxiangqingActivity;
import com.company.qcy.ui.activity.kaifangshangcheng.KFSCXiangqingActivity;
import com.company.qcy.ui.activity.kaifangshangcheng.KaifangshangchengActivity;
import com.company.qcy.ui.activity.qiugoudating.QiugoudatingActivity;
import com.company.qcy.ui.activity.qiugoudating.QiugouxiangqingActivity;
import com.company.qcy.ui.activity.zhuji.ZhujiDetailActivity;
import com.company.qcy.ui.activity.zhuji.ZhujiQiyeListActivity;

public class JumpUtil {

    public static void jumpActivty(BannerBean bannerBean, Context context) {
        if (StringUtils.isEmpty(bannerBean.getType()) ||
                StringUtils.equals("html", bannerBean
                        .getType())) {
            if (!StringUtils.isEmpty(bannerBean.getAd_url())) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("webUrl", bannerBean
                        .getAd_url());
                ActivityUtils.startActivity(intent);

            }
        } else if (StringUtils.equals("inner", bannerBean
                .getType())) {

            if (!StringUtils.isEmpty(bannerBean
                    .getDirectType())) {

                if (StringUtils.equals("enquiry", bannerBean
                        .getDirectType())) {

                    if (StringUtils.isEmpty(bannerBean
                            .getDirectTypeId())) {
                        Intent enquiryIntent = new Intent(context, QiugoudatingActivity.class);
                        ActivityUtils.startActivity(enquiryIntent);
                    } else {
                        Intent enquiryIntent = new Intent(context, QiugouxiangqingActivity.class);
                        enquiryIntent.putExtra("enquiryId", Long.parseLong(bannerBean
                                .getDirectTypeId()));
                        ActivityUtils.startActivity(enquiryIntent);
                    }
                } else if (StringUtils.equals("market", bannerBean
                        .getDirectType())) {
                    if (StringUtils.isEmpty(bannerBean
                            .getDirectTypeId())) {
                        Intent marketIntent = new Intent(context
                                , KaifangshangchengActivity.class);
//                        marketIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(marketIntent);
                    } else {
                        Intent marketIntent = new Intent(context
                                , KFSCXiangqingActivity.class);
                        marketIntent.putExtra("id", Long.parseLong(bannerBean
                                .getDirectTypeId()));
//                        marketIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(marketIntent);
                    }
                } else if (StringUtils.equals("product", bannerBean
                        .getDirectType())) {
                    if (StringUtils.isEmpty(bannerBean
                            .getDirectTypeId())) {
                        Intent productIntent = new Intent(context
                                , ChanpindatingActivity.class);
//                        productIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(productIntent);

                    } else {
                        Intent productIntent = new Intent(context
                                , ChanpinxiangqingActivity.class);
                        productIntent.putExtra("id", bannerBean
                                .getDirectTypeId() + "m");
//                        productIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(productIntent);
                    }
                } else if (StringUtils.equals("information", bannerBean
                        .getDirectType())) {
                    if (StringUtils.isEmpty(bannerBean
                            .getDirectTypeId())) {
                        Intent zixunIntent = new Intent(context
                                , ChanyezixunActivity.class);
//                        zixunIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(zixunIntent);
                    } else {
                        Intent zixunIntent = new Intent(context
                                , ZixunxiangqingActivity.class);
                        zixunIntent.putExtra("id", bannerBean
                                .getDirectTypeId());
//                        zixunIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(zixunIntent);

                    }

                } else if (StringUtils.equals("groupBuy", bannerBean
                        .getDirectType())) {
                    if (StringUtils.isEmpty(bannerBean
                            .getDirectTypeId())) {
                        Intent tuangouIntent = new Intent(context
                                , TuangouliebiaoActivity.class);
//                        tuangouIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(tuangouIntent);

                    } else {
                        Intent tuangouIntent = new Intent(context
                                , TuangouxiangqingActivity.class);
                        tuangouIntent.putExtra("id", Long.parseLong(bannerBean
                                .getDirectTypeId()));
//                        tuangouIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(tuangouIntent);
                    }
                } else if (StringUtils.equals("auction", bannerBean
                        .getDirectType())) {
                    if (StringUtils.isEmpty(bannerBean
                            .getDirectTypeId())) {
                        Intent jingpaiIntent = new Intent(context
                                , JingpaiActivity.class);
//                          tuangouIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(jingpaiIntent);

                    } else {
                        Intent jingpaiIntent = new Intent(context
                                , JingpaiDetailActivity.class);
                        jingpaiIntent.putExtra("id", bannerBean
                                .getDirectTypeId());
//                        tuangouIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(jingpaiIntent);
                    }
                } else if (StringUtils.equals("zhuji", bannerBean
                        .getDirectType())) {
                    if (StringUtils.isEmpty(bannerBean
                            .getDirectTypeId())) {
                        Intent zhujiIntent = new Intent(context
                                , ZhujiQiyeListActivity.class);
//                        tuangouIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(zhujiIntent);

                    } else {
                        Intent zhujiIntent = new Intent(context
                                , ZhujiDetailActivity.class);
                        zhujiIntent.putExtra("id", Long.parseLong(bannerBean
                                .getDirectTypeId()));
//                        tuangouIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(zhujiIntent);
                    }
                } else if (StringUtils.equals("meeting", bannerBean
                        .getDirectType())) {
                    if (StringUtils.isEmpty(bannerBean
                            .getDirectTypeId())) {
                        Intent zhujiIntent = new Intent(context
                                , CaigoulianmengActivity.class);
//                        tuangouIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(zhujiIntent);

                    } else {
                        Intent zhujiIntent = new Intent(context
                                , CaigoulianmengActivity.class);
//                        tuangouIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(zhujiIntent);
                    }
                } else if (StringUtils.equals("schoolLiveClass", bannerBean
                        .getDirectType())) {
                    if (StringUtils.isEmpty(bannerBean
                            .getDirectTypeId())) {

                        ActivityUtils.startActivity(LiveListActivity.class);
                    } else {
                        Intent zhiboIntent = new Intent(context
                                , LiveDetailActivity.class);
                        zhiboIntent.putExtra("id", Long.parseLong(bannerBean
                                .getDirectTypeId()));
                        ActivityUtils.startActivity(zhiboIntent);

                    }
                }
            }
        }
    }

}
