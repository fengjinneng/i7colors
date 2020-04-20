package com.company.qcy.adapter.vlayout;

import android.content.Context;
import android.support.annotation.NonNull;
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
import com.company.qcy.Utils.JumpUtil;
import com.company.qcy.Utils.NetworkImageHolderView;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.base.WebActivity;
import com.company.qcy.bean.BannerBean;
import com.company.qcy.map.QCYMapActivity;
import com.company.qcy.ui.activity.chanpindating.ChanpindatingActivity;
import com.company.qcy.ui.activity.chanyezixun.ChanyezixunActivity;
import com.company.qcy.ui.activity.kaifangshangcheng.KaifangshangchengActivity;
import com.company.qcy.ui.activity.qiugoudating.QiugoudatingActivity;
import com.company.qcy.ui.activity.zhuji.ZhujiListActivity;
import com.company.qcy.ui.activity.zhuji.ZhujiQiyeListActivity;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.ArrayList;
import java.util.List;


public class SingleAdvLayoutAdapter extends DelegateAdapter.Adapter<SingleAdvLayoutAdapter.SingleAdvLayoutViewHolder> {

    // 用于存放数据列表

    private List<BannerBean> bannerDatas;
    private static Context context;
    private LayoutHelper layoutHelper;
    private RecyclerView.LayoutParams layoutParams;
    private int count;

    public ConvenientBanner CBanner;

    static MySingleAdvItemClickListener myItemClickListener;
    // 用于设置Item点击事件

    //构造函数(传入每个的数据列表 & 展示的Item数量)
    public SingleAdvLayoutAdapter(Context context, LayoutHelper layoutHelper, int count, List<BannerBean> bannerDatas) {
        this(context, layoutHelper, count, new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT), bannerDatas);
    }

    public SingleAdvLayoutAdapter(Context context, LayoutHelper layoutHelper,
                                  int count, @NonNull RecyclerView.LayoutParams layoutParams, List<BannerBean> bannerDatas) {
        this.context = context;
        this.layoutHelper = layoutHelper;
        this.count = count;
        this.layoutParams = layoutParams;
        this.bannerDatas = bannerDatas;
    }

    public void startTurning() {
        if (!ObjectUtils.isEmpty(CBanner)) {
            CBanner.startTurning(3000);
        }
    }

    public void closeTurning() {
        if (!ObjectUtils.isEmpty(CBanner)) {
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
    }


    private void setBanner(SingleAdvLayoutViewHolder holder) {

        try {
            CBanner = holder.convenientBanner;

            ArrayList<String> imgs = new ArrayList<>();
            for (int i = 0; i < bannerDatas.size(); i++) {
                imgs.add(ServerInfo.IMAGE+ bannerDatas.get(i).getAd_image());
            }
            holder.convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                @Override
                public NetworkImageHolderView createHolder() {
                    return new NetworkImageHolderView();
                }
            }, imgs);
            holder.convenientBanner.setPageIndicator(new int[]{R.mipmap.banner_unchoiced, R.mipmap.banner_choiced});
            holder.convenientBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
            //设置如果只有一组数据时不能滑动
            holder.convenientBanner.setPointViewVisible(bannerDatas.size() == 1 ? false : true); // 指示器
            holder.convenientBanner.setManualPageable(bannerDatas.size() == 1 ? false : true);//设置false,手动影响（设置了该项无法手动切换）

            holder.convenientBanner.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {

                    JumpUtil.jumpActivty(bannerDatas.get(position), context);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return count;
    }


    static class SingleAdvLayoutViewHolder extends RecyclerView.ViewHolder {
        public ConvenientBanner convenientBanner;

        public SingleAdvLayoutViewHolder(View root) {
            super(root);
            convenientBanner = root.findViewById(R.id.vlayout_home_part1_banner);
        }
    }

    public interface MySingleAdvItemClickListener {
        void onItemClick(View v, int p);
    }
}



