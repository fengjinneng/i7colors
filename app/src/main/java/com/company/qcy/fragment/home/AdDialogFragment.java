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
import com.company.qcy.Utils.JumpUtil;
import com.company.qcy.Utils.ServerInfo;
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
                JumpUtil.jumpActivty(bannerBean,getActivity());
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




}
