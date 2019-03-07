package com.company.qcy.adapter.vlayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.bean.BannerBean;
import com.company.qcy.huodong.caigoulianmeng2.activity.CaigoulianmengActivity;
import com.company.qcy.huodong.jingpai.activity.JingpaiActivity;
import com.company.qcy.huodong.toupiao.activity.ToupiaoListActivity;
import com.company.qcy.huodong.youhuizhanxiao.activity.YouhuizhanxiaoActivity;
import com.company.qcy.huodong.tuangou.activity.TuangouliebiaoActivity;

import java.util.List;


public class SingleAdvLayoutAdapter2 extends DelegateAdapter.Adapter<SingleAdvLayoutAdapter2.SingleAdvLayoutViewHolder> implements View.OnClickListener {

    // 用于存放数据列表

    private List<BannerBean> datas;
    private static Context context;
    private LayoutHelper layoutHelper;
    private RecyclerView.LayoutParams layoutParams;
    private int count;

    static MySingleAdv2ItemClickListener myItemClickListener;
    // 用于设置Item点击事件

    //构造函数(传入每个的数据列表 & 展示的Item数量)
    public SingleAdvLayoutAdapter2(Context context, LayoutHelper layoutHelper, int count, List<BannerBean> datas) {
        this(context, layoutHelper, count, new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT), datas);
    }

    public SingleAdvLayoutAdapter2(Context context, LayoutHelper layoutHelper,
                                   int count, @NonNull RecyclerView.LayoutParams layoutParams, List<BannerBean> datas) {
        this.context = context;
        this.layoutHelper = layoutHelper;
        this.count = count;
        this.layoutParams = layoutParams;
        this.datas = datas;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public SingleAdvLayoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SingleAdvLayoutViewHolder(LayoutInflater.from(context).inflate(R.layout.vlayout_home_part2, parent, false));
    }

    @Override
    public void onBindViewHolder(SingleAdvLayoutViewHolder holder, int position) {
        holder.img1.setOnClickListener(this);
        holder.img2.setOnClickListener(this);
        holder.img3.setOnClickListener(this);
        holder.img4.setOnClickListener(this);
        holder.img5.setOnClickListener(this);

        if (ObjectUtils.isEmpty(datas)) {
            return;
        }
        holder.layout.setVisibility(View.VISIBLE);

        switch (datas.size()) {
            case 1:
                holder.img1.setVisibility(View.VISIBLE);
                GlideUtils.loadImageRct(context, ServerInfo.IMAGE + datas.get(0).getAd_image(), holder.img1);
                holder.img2.setVisibility(View.GONE);
                holder.img3.setVisibility(View.GONE);
                holder.img4.setVisibility(View.GONE);
                holder.img5.setVisibility(View.GONE);

                break;
            case 2:
                holder.img1.setVisibility(View.GONE);
                holder.img4.setVisibility(View.GONE);
                holder.img5.setVisibility(View.GONE);
                holder.img2.setVisibility(View.VISIBLE);
                GlideUtils.loadImageRct(context, ServerInfo.IMAGE + datas.get(0).getAd_image(), holder.img2);
                holder.img3.setVisibility(View.VISIBLE);
                GlideUtils.loadImageRct(context, ServerInfo.IMAGE + datas.get(1).getAd_image(), holder.img3);
                break;
            case 3:
                holder.img1.setVisibility(View.VISIBLE);
                GlideUtils.loadImageRct(context, ServerInfo.IMAGE + datas.get(0).getAd_image(), holder.img1);
                holder.img2.setVisibility(View.VISIBLE);
                GlideUtils.loadImageRct(context, ServerInfo.IMAGE + datas.get(1).getAd_image(), holder.img2);
                holder.img3.setVisibility(View.VISIBLE);
                GlideUtils.loadImageRct(context, ServerInfo.IMAGE + datas.get(2).getAd_image(), holder.img3);
                holder.img4.setVisibility(View.GONE);
                holder.img5.setVisibility(View.GONE);
                break;
            case 4:
                holder.img1.setVisibility(View.GONE);
                holder.img2.setVisibility(View.VISIBLE);
                GlideUtils.loadImageRct(context, ServerInfo.IMAGE + datas.get(0).getAd_image(), holder.img2);
                holder.img3.setVisibility(View.VISIBLE);
                GlideUtils.loadImageRct(context, ServerInfo.IMAGE + datas.get(1).getAd_image(), holder.img3);
                holder.img4.setVisibility(View.VISIBLE);
                GlideUtils.loadImageRct(context, ServerInfo.IMAGE + datas.get(2).getAd_image(), holder.img4);
                holder.img5.setVisibility(View.VISIBLE);
                GlideUtils.loadImageRct(context, ServerInfo.IMAGE + datas.get(3).getAd_image(), holder.img5);
                break;
            case 5:
                holder.img1.setVisibility(View.VISIBLE);
                GlideUtils.loadImageRct(context, ServerInfo.IMAGE + datas.get(0).getAd_image(), holder.img1);
                holder.img2.setVisibility(View.VISIBLE);
                GlideUtils.loadImageRct(context, ServerInfo.IMAGE + datas.get(1).getAd_image(), holder.img2);
                holder.img3.setVisibility(View.VISIBLE);
                GlideUtils.loadImageRct(context, ServerInfo.IMAGE + datas.get(2).getAd_image(), holder.img3);
                holder.img4.setVisibility(View.VISIBLE);
                GlideUtils.loadImageRct(context, ServerInfo.IMAGE + datas.get(3).getAd_image(), holder.img4);
                holder.img5.setVisibility(View.VISIBLE);
                GlideUtils.loadImageRct(context, ServerInfo.IMAGE + datas.get(4).getAd_image(), holder.img5);
                break;
            default:
                break;
        }

    }

    @Override
    public int getItemCount() {
        return count;
    }

    @Override
    public void onClick(View v) {

        if (datas.size() == 1) {
            jumpTo(datas.get(0).getAd_name());
        }
        if (datas.size() == 2) {

            switch (v.getId()) {
                case R.id.vlayout_home_part2_img2:
                    jumpTo(datas.get(0).getAd_name());
                    break;
                case R.id.vlayout_home_part2_img3:
                    jumpTo(datas.get(1).getAd_name());
                    break;
            }
        }

        if (datas.size() == 3) {
            switch (v.getId()) {
                case R.id.vlayout_home_part2_img1:
                    jumpTo(datas.get(0).getAd_name());
                    break;
                case R.id.vlayout_home_part2_img2:
                    jumpTo(datas.get(1).getAd_name());
                    break;
                case R.id.vlayout_home_part2_img3:
                    jumpTo(datas.get(2).getAd_name());
                    break;
            }
        }

        if (datas.size() == 4) {
            switch (v.getId()) {
                case R.id.vlayout_home_part2_img2:
                    jumpTo(datas.get(0).getAd_name());
                    break;
                case R.id.vlayout_home_part2_img3:
                    jumpTo(datas.get(1).getAd_name());
                    break;
                case R.id.vlayout_home_part2_img4:
                    jumpTo(datas.get(2).getAd_name());
                    break;
                case R.id.vlayout_home_part2_img5:
                    jumpTo(datas.get(3).getAd_name());
                    break;
            }
        }
        if (datas.size() == 5) {
            switch (v.getId()) {
                case R.id.vlayout_home_part2_img1:
                    jumpTo(datas.get(0).getAd_name());
                    break;
                case R.id.vlayout_home_part2_img2:
                    jumpTo(datas.get(1).getAd_name());
                    break;
                case R.id.vlayout_home_part2_img3:
                    jumpTo(datas.get(2).getAd_name());
                    break;
                case R.id.vlayout_home_part2_img4:
                    jumpTo(datas.get(3).getAd_name());
                    break;
                case R.id.vlayout_home_part2_img5:
                    jumpTo(datas.get(4).getAd_name());
                    break;

            }
        }
    }

    private void jumpTo(String adName) {

        switch (adName) {
            //团购
            case "group_buy":
                ActivityUtils.startActivity(TuangouliebiaoActivity.class);
                break;
            //优惠展销
            case "sales":
                ActivityUtils.startActivity(YouhuizhanxiaoActivity.class);
                break;
            //采购联盟
            case "meeting":
                ActivityUtils.startActivity(CaigoulianmengActivity.class);
                break;
            //投票
            case "vote":
                ActivityUtils.startActivity(ToupiaoListActivity.class);
                break;
            //抽奖
            case "draw":
                break;
            //竞拍
            case "auction":
                ActivityUtils.startActivity(JingpaiActivity.class);
                break;
        }

    }

    static class SingleAdvLayoutViewHolder extends RecyclerView.ViewHolder {
        public ImageView img1, img2, img3, img4, img5;
        public RelativeLayout layout;

        public SingleAdvLayoutViewHolder(View root) {
            super(root);
            img1 = root.findViewById(R.id.vlayout_home_part2_img1);
            img2 = root.findViewById(R.id.vlayout_home_part2_img2);
            img3 = root.findViewById(R.id.vlayout_home_part2_img3);
            img4 = root.findViewById(R.id.vlayout_home_part2_img4);
            img5 = root.findViewById(R.id.vlayout_home_part2_img5);
            layout = root.findViewById(R.id.vlayout_home_part2_title);
        }
    }

    public interface MySingleAdv2ItemClickListener {
        void onItemClick(View v, int p);
    }
}



