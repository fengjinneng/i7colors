package com.company.qcy.fragment.home;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.base.WebActivity;
import com.company.qcy.bean.BannerBean;
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
import com.company.qcy.ui.activity.zhuji.ZhujiQiyeListActivity;

public class AdDialogFragment extends DialogFragment {


    private BannerBean bannerBean;


    public static AdDialogFragment newInstance(BannerBean banner) {

        Bundle args = new Bundle();

        AdDialogFragment fragment = new AdDialogFragment();
        args.putParcelable("banner",banner);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bannerBean = getArguments().getParcelable("banner");
        }
    }

    private View mLayout;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //添加这一行
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);


        Window window = getDialog().getWindow();

        window.setBackgroundDrawable(
                new BitmapDrawable());



        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);

        mLayout = inflater.inflate(R.layout.ad_dialog, container);

        ImageView img = (ImageView) mLayout.findViewById(R.id.ad_dialog_img);
        ImageView close = (ImageView) mLayout.findViewById(R.id.ad_dialog_close);
        GlideUtils.loadImageDefault(getActivity(),ServerInfo.IMAGE+bannerBean.getAd_image(),img);
//        LogUtils.e("21321wqeqwqewqe",bannerBean);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parse();
                dismiss();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return mLayout;

    }


    private void parse() {

        if (StringUtils.isEmpty(bannerBean.getType()) ||
                StringUtils.equals("html", bannerBean
                        .getType())) {
            Intent intent = new Intent(getActivity(), WebActivity.class);
            intent.putExtra("webUrl", bannerBean
                    .getAd_url());
            ActivityUtils.startActivity(intent);
        } else if (StringUtils.equals("inner", bannerBean
                .getType())) {

            if (!StringUtils.isEmpty(bannerBean
                    .getDirectType())) {

                if (StringUtils.equals("enquiry", bannerBean
                        .getDirectType())) {

                    if (StringUtils.isEmpty(bannerBean
                            .getDirectTypeId())) {
                        Intent enquiryIntent = new Intent(getActivity(), QiugoudatingActivity.class);
                        ActivityUtils.startActivity(enquiryIntent);
                    } else {
                        Intent enquiryIntent = new Intent(getActivity(), QiugouxiangqingActivity.class);
                        enquiryIntent.putExtra("enquiryId", Long.parseLong(bannerBean
                                .getDirectTypeId()));
                        ActivityUtils.startActivity(enquiryIntent);
                    }
                } else if (StringUtils.equals("market", bannerBean
                        .getDirectType())) {
                    if (StringUtils.isEmpty(bannerBean
                            .getDirectTypeId())) {
                        Intent marketIntent = new Intent(getActivity(), KaifangshangchengActivity.class);
//                        marketIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(marketIntent);
                    } else {
                        Intent marketIntent = new Intent(getActivity(), KFSCXiangqingActivity.class);
                        marketIntent.putExtra("id", Long.parseLong(bannerBean
                                .getDirectTypeId()));
//                        marketIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(marketIntent);
                    }
                } else if (StringUtils.equals("product", bannerBean
                        .getDirectType())) {
                    if (StringUtils.isEmpty(bannerBean
                            .getDirectTypeId())) {
                        Intent productIntent = new Intent(getActivity(), ChanpindatingActivity.class);
//                        productIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(productIntent);

                    } else {
                        Intent productIntent = new Intent(getActivity(), ChanpinxiangqingActivity.class);
                        productIntent.putExtra("id", bannerBean
                                .getDirectTypeId() + "m");
//                        productIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(productIntent);
                    }
                } else if (StringUtils.equals("information", bannerBean
                        .getDirectType())) {
                    if (StringUtils.isEmpty(bannerBean
                            .getDirectTypeId())) {
                        Intent zixunIntent = new Intent(getActivity(), ChanyezixunActivity.class);
//                        zixunIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(zixunIntent);
                    } else {
                        Intent zixunIntent = new Intent(getActivity(), ZixunxiangqingActivity.class);
                        zixunIntent.putExtra("id", bannerBean
                                .getDirectTypeId());
//                        zixunIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(zixunIntent);

                    }

                } else if (StringUtils.equals("groupBuy", bannerBean
                        .getDirectType())) {
                    if (StringUtils.isEmpty(bannerBean
                            .getDirectTypeId())) {
                        Intent tuangouIntent = new Intent(getActivity(), TuangouliebiaoActivity.class);
//                        tuangouIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(tuangouIntent);

                    } else {
                        Intent tuangouIntent = new Intent(getActivity(), TuangouxiangqingActivity.class);
                        tuangouIntent.putExtra("id", Long.parseLong(bannerBean
                                .getDirectTypeId()));
//                        tuangouIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(tuangouIntent);
                    }
                } else if (StringUtils.equals("auction", bannerBean
                        .getDirectType())) {
                    if (StringUtils.isEmpty(bannerBean
                            .getDirectTypeId())) {
                        Intent jingpaiIntent = new Intent(getActivity(), JingpaiActivity.class);
//                          tuangouIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(jingpaiIntent);

                    } else {
                        Intent jingpaiIntent = new Intent(getActivity(), JingpaiDetailActivity.class);
                        jingpaiIntent.putExtra("id", bannerBean
                                .getDirectTypeId());
//                        tuangouIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(jingpaiIntent);
                    }
                } else if (StringUtils.equals("zhuji", bannerBean
                        .getDirectType())) {
                    if (StringUtils.isEmpty(bannerBean
                            .getDirectTypeId())) {
                        Intent zhujiIntent = new Intent(getActivity(), ZhujiQiyeListActivity.class);
//                        tuangouIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(zhujiIntent);

                    } else {
                        Intent zhujiIntent = new Intent(getActivity(), ZhujiDetailActivity.class);
                        zhujiIntent.putExtra("id", Long.parseLong(bannerBean
                                .getDirectTypeId()));
//                        tuangouIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ActivityUtils.startActivity(zhujiIntent);
                    }
                }
            }
        }
    }

}
