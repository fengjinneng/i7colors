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
import android.widget.LinearLayout;

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
import com.company.qcy.R;
import com.company.qcy.Utils.NetworkImageHolderView;
import com.company.qcy.base.WebActivity;
import com.company.qcy.map.QCYMapActivity;
import com.company.qcy.ui.activity.chanpindating.ChanpindatingActivity;
import com.company.qcy.ui.activity.chanyezixun.ChanyezixunActivity;
import com.company.qcy.ui.activity.kaifangshangcheng.KaifangshangchengActivity;
import com.company.qcy.ui.activity.qiugoudating.QiugoudatingActivity;
import com.company.qcy.ui.activity.zhuji.ZhujiListActivity;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.List;


public class SingleAdvLayoutAdapter extends DelegateAdapter.Adapter<SingleAdvLayoutAdapter.SingleAdvLayoutViewHolder> implements View.OnClickListener {

    // 用于存放数据列表

    private List<String> bannerDatas;
    private static Context context;
    private LayoutHelper layoutHelper;
    private RecyclerView.LayoutParams layoutParams;
    private int count;

    private List<String> bannerUrlDatas;

    public  ConvenientBanner CBanner;

    static MySingleAdvItemClickListener myItemClickListener;
    // 用于设置Item点击事件

    //构造函数(传入每个的数据列表 & 展示的Item数量)
    public SingleAdvLayoutAdapter(Context context, LayoutHelper layoutHelper, int count, List<String> bannerDatas,List<String> bannerUrlDatas) {
        this(context, layoutHelper, count, new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT), bannerDatas,bannerUrlDatas);
    }

    public SingleAdvLayoutAdapter(Context context, LayoutHelper layoutHelper,
                                  int count, @NonNull RecyclerView.LayoutParams layoutParams, List<String> bannerDatas,
                                  List<String> bannerUrlDatas) {
        this.context = context;
        this.layoutHelper = layoutHelper;
        this.count = count;
        this.layoutParams = layoutParams;
        this.bannerDatas = bannerDatas;
        this.bannerUrlDatas = bannerUrlDatas;
    }

    public  void startTurning(){
        if(!ObjectUtils.isEmpty(CBanner)){
            CBanner.startTurning(3000);
        }
    }

    public  void closeTurning(){
        if(!ObjectUtils.isEmpty(CBanner)){
            CBanner.stopTurning();
        }
    }


    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public SingleAdvLayoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SingleAdvLayoutViewHolder(LayoutInflater.from(context).inflate(R.layout.vlayout_home_part1, parent, false));
    }

    @Override
    public void onBindViewHolder(SingleAdvLayoutViewHolder holder, int position) {
        setBanner(holder);
        holder.chanpindating.setOnClickListener(this);
        holder.qiugoudating.setOnClickListener(this);
        holder.kaifangshangcheng.setOnClickListener(this);
        holder.chanyezixun.setOnClickListener(this);
        holder.zhujidingzhi.setOnClickListener(this);
        holder.map.setOnClickListener(this);
    }


    private void setBanner(SingleAdvLayoutViewHolder holder){

        try {
            CBanner = holder.convenientBanner;
            holder.convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                @Override
                public NetworkImageHolderView createHolder() {
                    return new NetworkImageHolderView();
                }
            }, bannerDatas);
            holder.convenientBanner.setPageIndicator(new int[]{R.mipmap.banner_unchoiced, R.mipmap.banner_choiced});
            holder.convenientBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
            //设置如果只有一组数据时不能滑动
            holder.convenientBanner.setPointViewVisible(bannerDatas.size() == 1 ? false : true); // 指示器
            holder.convenientBanner.setManualPageable(bannerDatas.size() == 1 ? false : true);//设置false,手动影响（设置了该项无法手动切换）

            holder.convenientBanner.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {

                    if(StringUtils.isEmpty(bannerUrlDatas.get(position))){
                        return;
                    }
                    Intent intent = new Intent((Activity) context,WebActivity.class);
                    intent.putExtra("webUrl",bannerUrlDatas.get(position));
                    ActivityUtils.startActivity(intent);
                }
            });
        }catch (Exception e){
            LogUtils.e("csssasawevwvwewvw","这里的问题");
        }

    }

    @Override
    public int getItemCount() {
        return count;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.vlayout_home_part1_chanpin:
                context.startActivity(new Intent(context, ChanpindatingActivity.class));
                break;
            case R.id.vlayout_home_part1_qiugou:
                context.startActivity(new Intent(context, QiugoudatingActivity.class));
                break;
            case R.id.vlayout_home_part1_kaifang:
                context.startActivity(new Intent(context, KaifangshangchengActivity.class));
                break;
            case R.id.vlayout_home_part1_chanye:
                context.startActivity(new Intent(context, ChanyezixunActivity.class));
                break;
            case R.id.vlayout_home_part1_zhuji:
                context.startActivity(new Intent(context, ZhujiListActivity.class));
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

    static class SingleAdvLayoutViewHolder extends RecyclerView.ViewHolder {
        public ConvenientBanner convenientBanner;
        public ConstraintLayout chanpindating,qiugoudating,kaifangshangcheng,chanyezixun,zhujidingzhi;
        public ImageView map;

        public SingleAdvLayoutViewHolder(View root) {
            super(root);
            convenientBanner = root.findViewById(R.id.vlayout_home_part1_banner);
            chanpindating = root.findViewById(R.id.vlayout_home_part1_chanpin);
            qiugoudating = root.findViewById(R.id.vlayout_home_part1_qiugou);
            kaifangshangcheng = root.findViewById(R.id.vlayout_home_part1_kaifang);
            chanyezixun = root.findViewById(R.id.vlayout_home_part1_chanye);
            zhujidingzhi = root.findViewById(R.id.vlayout_home_part1_zhuji);
            map = root.findViewById(R.id.vlayout_home_part1_map);
        }
    }

    public interface MySingleAdvItemClickListener {
        void onItemClick(View v, int p);
    }
}



