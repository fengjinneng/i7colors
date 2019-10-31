package com.company.qcy.Utils;

import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.company.qcy.base.WebActivity;
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
import com.company.qcy.ui.activity.zhuji.WodeFangAnDetailActivity;
import com.company.qcy.ui.activity.zhuji.WodeZhujiDingzhiDetailActivity;
import com.company.qcy.ui.activity.zhuji.ZhujiDetailActivity;
import com.company.qcy.ui.activity.zhuji.ZhujiListActivity;
import com.company.qcy.ui.activity.zhuji.ZhujiQiyeListActivity;

import java.util.HashMap;

public class JpushUtil {


    public static void jumpActivity(HashMap<String, String> map, Context context) {
        if (StringUtils.equals("html", map.get("workType"))) {
            Intent htmlIntent = new Intent(context, WebActivity.class);
            htmlIntent.putExtra("webUrl", map.get("url"));
//            htmlIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            ActivityUtils.startActivity(htmlIntent);
        } else if (StringUtils.equals("appSystemInform", map.get("workType"))) {
            if (StringUtils.equals("txt", map.get("type"))) {
                return;
            } else if (StringUtils.equals("inner", map.get("type"))) {
//                choicedWhitchFragment = 2;
                if (StringUtils.equals("enquiry", map.get("directType"))) {

                    if (ObjectUtils.isEmpty(map.get("directTypeId"))) {
                        Intent enquiryIntent = new Intent(context, QiugoudatingActivity.class);
//                        enquiryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(enquiryIntent);
                    } else {
                        Intent enquiryIntent = new Intent(context, QiugouxiangqingActivity.class);
                        enquiryIntent.putExtra("enquiryId", Long.parseLong(map.get("directTypeId")));
//                        enquiryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(enquiryIntent);
                    }
                } else if (StringUtils.equals("market", map.get("directType"))) {
                    if (ObjectUtils.isEmpty(map.get("directTypeId"))) {
                        Intent marketIntent = new Intent(context, KaifangshangchengActivity.class);
//                        marketIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(marketIntent);
                    } else {
                        Intent marketIntent = new Intent(context, KFSCXiangqingActivity.class);
                        marketIntent.putExtra("id", Long.parseLong(map.get("directTypeId")));
//                        marketIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(marketIntent);
                    }
                } else if (StringUtils.equals("product", map.get("directType"))) {
                    if (ObjectUtils.isEmpty(map.get("directTypeId"))) {
                        Intent productIntent = new Intent(context, ChanpindatingActivity.class);
//                        productIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(productIntent);

                    } else {
                        Intent productIntent = new Intent(context, ChanpinxiangqingActivity.class);
                        productIntent.putExtra("id", map.get("directTypeId") + "m");
//                        productIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(productIntent);
                    }
                } else if (StringUtils.equals("information", map.get("directType"))) {
                    if (ObjectUtils.isEmpty(map.get("directTypeId"))) {
                        Intent zixunIntent = new Intent(context, ChanyezixunActivity.class);
//                        zixunIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(zixunIntent);
                    } else {
                        Intent zixunIntent = new Intent(context, ZixunxiangqingActivity.class);
                        zixunIntent.putExtra("id", map.get("directTypeId") + "");
//                        zixunIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(zixunIntent);

                    }

                } else if (StringUtils.equals("groupBuy", map.get("directType"))) {
                    if (ObjectUtils.isEmpty(map.get("directTypeId"))) {
                        Intent tuangouIntent = new Intent(context, TuangouliebiaoActivity.class);
//                        tuangouIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(tuangouIntent);

                    } else {
                        Intent tuangouIntent = new Intent(context, TuangouxiangqingActivity.class);
                        tuangouIntent.putExtra("id", Long.parseLong(map.get("directTypeId")));
//                        tuangouIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(tuangouIntent);
                    }
                }

                else if (StringUtils.equals("auction", map.get("directType"))) {
                    if (ObjectUtils.isEmpty(map.get("directTypeId"))) {
                        Intent jingpaiIntent = new Intent(context, JingpaiActivity.class);
//                          tuangouIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(jingpaiIntent);

                    } else {
                        Intent jingpaiIntent = new Intent(context, JingpaiDetailActivity.class);
                        jingpaiIntent.putExtra("id", map.get("directTypeId"));
//                        tuangouIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(jingpaiIntent);
                    }
                }

                else if (StringUtils.equals("zhuji", map.get("directType"))) {
                    if (ObjectUtils.isEmpty(map.get("directTypeId"))) {
                        Intent zhujiIntent = new Intent(context, ZhujiQiyeListActivity.class);
//                        tuangouIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(zhujiIntent);

                    } else {
                        Intent zhujiIntent = new Intent(context, ZhujiDetailActivity.class);
                        zhujiIntent.putExtra("id", Long.parseLong(map.get("directTypeId")));
//                        tuangouIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(zhujiIntent);
                    }
                }

            } else if (StringUtils.equals("html", map.get("type"))) {
                Intent i = new Intent(context, WebActivity.class);
                i.putExtra("webUrl", map.get("url"));
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ActivityUtils.startActivity(i);
            }
        } else if (StringUtils.equals("vMallInform", map.get("workType"))) {

            if (StringUtils.equals("buyer", map.get("type"))) {

                if (StringUtils.equals("zhujiDiy", map.get("directType"))) {

                    if (!ObjectUtils.isEmpty(map.get("zhujiDiyId"))) {
                        Intent zhujiDiyIdIntent = new Intent(context, WodeZhujiDingzhiDetailActivity.class);
                        zhujiDiyIdIntent.putExtra("id", Long.parseLong(map.get("zhujiDiyId")));
                        ActivityUtils.startActivity(zhujiDiyIdIntent);
                    }
                } else if (StringUtils.equals("enquiry", map.get("directType"))) {
                    if (!ObjectUtils.isEmpty(map.get("enquiryId"))) {
                        Intent enquiryIntent = new Intent(context, QiugouxiangqingActivity.class);
                        enquiryIntent.putExtra("enquiryId", Long.parseLong(map.get("enquiryId")));
                        ActivityUtils.startActivity(enquiryIntent);
                    }
                }

            } else if (StringUtils.equals("seller", map.get("type"))) {
                if (StringUtils.equals("zhujiDiy", map.get("directType"))) {
                    if (!ObjectUtils.isEmpty(map.get("zhujiDiySolutionId"))) {
                        Intent zhujiDiyIdIntent = new Intent(context, WodeFangAnDetailActivity.class);
                        zhujiDiyIdIntent.putExtra("id", Long.parseLong(map.get("zhujiDiySolutionId")));
                        ActivityUtils.startActivity(zhujiDiyIdIntent);
                    }
                } else if (StringUtils.equals("enquiry", map.get("directType"))) {
                    if (!ObjectUtils.isEmpty(map.get("enquiryId"))) {
                        Intent enquiryIntent = new Intent(context, QiugouxiangqingActivity.class);
                        enquiryIntent.putExtra("enquiryId", Long.parseLong(map.get("enquiryId")));
                        ActivityUtils.startActivity(enquiryIntent);
                    }
                }

            }
        }
    }

}
