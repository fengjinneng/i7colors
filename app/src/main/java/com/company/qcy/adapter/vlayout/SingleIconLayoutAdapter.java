package com.company.qcy.adapter.vlayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.NetworkImageHolderView;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.base.WebActivity;
import com.company.qcy.bean.BannerBean;
import com.company.qcy.live.LiveListActivity;
import com.company.qcy.map.QCYMapActivity;
import com.company.qcy.ui.activity.chanpindating.ChanpindatingActivity;
import com.company.qcy.ui.activity.chanyezixun.ChanyezixunActivity;
import com.company.qcy.ui.activity.kaifangshangcheng.KaifangshangchengActivity;
import com.company.qcy.ui.activity.qiugoudating.QiugoudatingActivity;
import com.company.qcy.ui.activity.zhuji.ZhujiListActivity;
import com.company.qcy.ui.activity.zhuji.ZhujiQiyeListActivity;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.List;


public class SingleIconLayoutAdapter extends DelegateAdapter.Adapter<SingleIconLayoutAdapter.SingleIconLayoutViewHolder> implements View.OnClickListener {

    // 用于存放数据列表

    private List<BannerBean> iconDatas;
    private static Context context;
    private LayoutHelper layoutHelper;
    private RecyclerView.LayoutParams layoutParams;
    private int count;

    static MySingleAdvItemClickListener myItemClickListener;
    // 用于设置Item点击事件

    //构造函数(传入每个的数据列表 & 展示的Item数量)
    public SingleIconLayoutAdapter(Context context, LayoutHelper layoutHelper, int count, List<BannerBean> iconDatas) {
        this(context, layoutHelper, count, new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT), iconDatas);
    }

    public SingleIconLayoutAdapter(Context context, LayoutHelper layoutHelper,
                                   int count, @NonNull RecyclerView.LayoutParams layoutParams, List<BannerBean> iconDatas) {
        this.context = context;
        this.layoutHelper = layoutHelper;
        this.count = count;
        this.layoutParams = layoutParams;
        this.iconDatas = iconDatas;
    }


    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public SingleIconLayoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SingleIconLayoutViewHolder(LayoutInflater.from(context).inflate(R.layout.vlayout_home_icon, parent, false));
    }

    @Override
    public void onBindViewHolder(SingleIconLayoutViewHolder holder, int position) {

        switch (iconDatas.size()) {
            case 1:
                holder.layout1.setVisibility(View.VISIBLE);
                holder.layout2.setVisibility(View.GONE);
                holder.layout3.setVisibility(View.GONE);
                holder.layout4.setVisibility(View.GONE);
                holder.layout5.setVisibility(View.GONE);
                GlideUtils.loadImageDefault(context, ServerInfo.IMAGE + iconDatas.get(0).getAd_image(), holder.img1);
                holder.textView1.setText(iconDatas.get(0).getAd_desc());
                break;
            case 2:

                holder.layout1.setVisibility(View.VISIBLE);
                holder.layout2.setVisibility(View.VISIBLE);
                holder.layout3.setVisibility(View.GONE);
                holder.layout4.setVisibility(View.GONE);
                holder.layout5.setVisibility(View.GONE);
                GlideUtils.loadImageDefault(context, ServerInfo.IMAGE + iconDatas.get(0).getAd_image(), holder.img1);
                GlideUtils.loadImageDefault(context, ServerInfo.IMAGE + iconDatas.get(1).getAd_image(), holder.img2);
                holder.textView1.setText(iconDatas.get(0).getAd_desc());
                holder.textView2.setText(iconDatas.get(1).getAd_desc());
                break;
            case 3:

                holder.layout1.setVisibility(View.VISIBLE);
                holder.layout2.setVisibility(View.VISIBLE);
                holder.layout3.setVisibility(View.VISIBLE);
                holder.layout4.setVisibility(View.GONE);
                holder.layout5.setVisibility(View.GONE);
                GlideUtils.loadImageDefault(context, ServerInfo.IMAGE + iconDatas.get(0).getAd_image(), holder.img1);
                GlideUtils.loadImageDefault(context, ServerInfo.IMAGE + iconDatas.get(1).getAd_image(), holder.img2);
                GlideUtils.loadImageDefault(context, ServerInfo.IMAGE + iconDatas.get(2).getAd_image(), holder.img3);
                holder.textView1.setText(iconDatas.get(0).getAd_desc());
                holder.textView2.setText(iconDatas.get(1).getAd_desc());
                holder.textView3.setText(iconDatas.get(2).getAd_desc());
                break;
            case 4:

                holder.layout1.setVisibility(View.VISIBLE);
                holder.layout2.setVisibility(View.VISIBLE);
                holder.layout3.setVisibility(View.VISIBLE);
                holder.layout4.setVisibility(View.VISIBLE);
                holder.layout5.setVisibility(View.GONE);
                GlideUtils.loadImageDefault(context, ServerInfo.IMAGE + iconDatas.get(0).getAd_image(), holder.img1);
                GlideUtils.loadImageDefault(context, ServerInfo.IMAGE + iconDatas.get(1).getAd_image(), holder.img2);
                GlideUtils.loadImageDefault(context, ServerInfo.IMAGE + iconDatas.get(2).getAd_image(), holder.img3);
                GlideUtils.loadImageDefault(context, ServerInfo.IMAGE + iconDatas.get(3).getAd_image(), holder.img4);
                holder.textView1.setText(iconDatas.get(0).getAd_desc());
                holder.textView2.setText(iconDatas.get(1).getAd_desc());
                holder.textView3.setText(iconDatas.get(2).getAd_desc());
                holder.textView4.setText(iconDatas.get(3).getAd_desc());
                break;
            case 5:
                holder.layout1.setVisibility(View.VISIBLE);
                holder.layout2.setVisibility(View.VISIBLE);
                holder.layout3.setVisibility(View.VISIBLE);
                holder.layout4.setVisibility(View.VISIBLE);
                holder.layout5.setVisibility(View.VISIBLE);
                GlideUtils.loadImageDefault(context, ServerInfo.IMAGE + iconDatas.get(0).getAd_image(), holder.img1);
                GlideUtils.loadImageDefault(context, ServerInfo.IMAGE + iconDatas.get(1).getAd_image(), holder.img2);
                GlideUtils.loadImageDefault(context, ServerInfo.IMAGE + iconDatas.get(2).getAd_image(), holder.img3);
                GlideUtils.loadImageDefault(context, ServerInfo.IMAGE + iconDatas.get(3).getAd_image(), holder.img4);
                GlideUtils.loadImageDefault(context, ServerInfo.IMAGE + iconDatas.get(4).getAd_image(), holder.img5);
                holder.textView1.setText(iconDatas.get(0).getAd_desc());
                holder.textView2.setText(iconDatas.get(1).getAd_desc());
                holder.textView3.setText(iconDatas.get(2).getAd_desc());
                holder.textView4.setText(iconDatas.get(3).getAd_desc());
                holder.textView5.setText(iconDatas.get(4).getAd_desc());
                break;

        }


        holder.layout1.setOnClickListener(this);
        holder.layout2.setOnClickListener(this);
        holder.layout3.setOnClickListener(this);
        holder.layout4.setOnClickListener(this);
        holder.layout5.setOnClickListener(this);
        holder.map.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return count;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vlayout_home_part1_layout1:
                gotoActivity(0);
                break;
            case R.id.vlayout_home_part1_layout2:
                gotoActivity(1);
                break;
            case R.id.vlayout_home_part1_layout3:
                gotoActivity(2);
                break;
            case R.id.vlayout_home_part1_layout4:
                gotoActivity(3);
                break;
            case R.id.vlayout_home_part1_layout5:
                gotoActivity(4);
                break;
            case R.id.vlayout_home_part1_map:

                //申请权限
                AndPermission.with(context)
                        .runtime()
                        .permission(Permission.Group.STORAGE, Permission.Group.LOCATION)
                        .onGranted(permissions -> {
                            // Storage permission are allowed.
                            context.startActivity(new Intent(context, QCYMapActivity.class));
                        })
                        .onDenied(permissions -> {
                            // Storage permission are not allowed.
                            ToastUtils.showShort("权限申请失败,您可能无法使用某些功能");
                            return;
                        })
                        .start();

                break;
        }
    }

    private void gotoActivity(int position) {
        if (StringUtils.equals("product", iconDatas.get(position).getAd_name())) {
            ActivityUtils.startActivity(ChanpindatingActivity.class);
        } else if (StringUtils.equals("enquiry", iconDatas.get(position).getAd_name())) {
            ActivityUtils.startActivity(QiugoudatingActivity.class);
        } else if (StringUtils.equals("market", iconDatas.get(position).getAd_name())) {
            ActivityUtils.startActivity(KaifangshangchengActivity.class);
        } else if (StringUtils.equals("information", iconDatas.get(position).getAd_name())) {
            ActivityUtils.startActivity(ChanyezixunActivity.class);
        } else if (StringUtils.equals("zhuji_diy", iconDatas.get(position).getAd_name())) {
            ActivityUtils.startActivity(ZhujiListActivity.class);
        }else if (StringUtils.equals("school_live_class", iconDatas.get(position).getAd_name())) {
            ActivityUtils.startActivity(LiveListActivity.class);
        }
    }

    static class SingleIconLayoutViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout layout1, layout2, layout3, layout4, layout5;
        public ImageView img1, img2, img3, img4, img5;
        public TextView textView1, textView2, textView3, textView4, textView5;
        public ImageView map;

        public SingleIconLayoutViewHolder(View root) {
            super(root);
            layout1 = root.findViewById(R.id.vlayout_home_part1_layout1);
            layout2 = root.findViewById(R.id.vlayout_home_part1_layout2);
            layout3 = root.findViewById(R.id.vlayout_home_part1_layout3);
            layout4 = root.findViewById(R.id.vlayout_home_part1_layout4);
            layout5 = root.findViewById(R.id.vlayout_home_part1_layout5);

            img1 = root.findViewById(R.id.vlayout_home_part1_icon1);
            img2 = root.findViewById(R.id.vlayout_home_part1_icon2);
            img3 = root.findViewById(R.id.vlayout_home_part1_icon3);
            img4 = root.findViewById(R.id.vlayout_home_part1_icon4);
            img5 = root.findViewById(R.id.vlayout_home_part1_icon5);

            textView1 = root.findViewById(R.id.vlayout_home_part1_title1);
            textView2 = root.findViewById(R.id.vlayout_home_part1_title2);
            textView3 = root.findViewById(R.id.vlayout_home_part1_title3);
            textView4 = root.findViewById(R.id.vlayout_home_part1_title4);
            textView5 = root.findViewById(R.id.vlayout_home_part1_title5);
            map = root.findViewById(R.id.vlayout_home_part1_map);
        }
    }

    public interface MySingleAdvItemClickListener {
        void onItemClick(View v, int p);
    }
}



