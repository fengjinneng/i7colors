package com.company.qcy.adapter.vlayout;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.company.qcy.R;
import com.company.qcy.Utils.NetworkImageHolderView;
import com.company.qcy.ui.activity.ChanpindatingActivity;
import com.company.qcy.ui.activity.ChanyezixunActivity;
import com.company.qcy.ui.activity.KaifangshangchengActivity;
import com.company.qcy.ui.activity.qiugoudating.QiugoudatingActivity;

import java.util.List;


public class SingleAdvLayoutAdapter extends DelegateAdapter.Adapter<SingleAdvLayoutAdapter.SingleAdvLayoutViewHolder> implements View.OnClickListener {

    // 用于存放数据列表

    List<String> datas;
    private static Context context;
    private LayoutHelper layoutHelper;
    private RecyclerView.LayoutParams layoutParams;
    private int count;

    static MySingleAdvItemClickListener myItemClickListener;
    // 用于设置Item点击事件

    //构造函数(传入每个的数据列表 & 展示的Item数量)
    public SingleAdvLayoutAdapter(Context context, LayoutHelper layoutHelper, int count, List<String> datas) {
        this(context, layoutHelper, count, new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT), datas);
    }

    public SingleAdvLayoutAdapter(Context context, LayoutHelper layoutHelper,
                                  int count, @NonNull RecyclerView.LayoutParams layoutParams, List<String> datas) {
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
        return new SingleAdvLayoutViewHolder(LayoutInflater.from(context).inflate(R.layout.vlayout_home_part1, parent, false));
    }

    @Override
    public void onBindViewHolder(SingleAdvLayoutViewHolder holder, int position) {
        setBanner(holder);
        setAdv(holder);
        holder.chanpindating.setOnClickListener(this);
        holder.qiugoudating.setOnClickListener(this);
        holder.kaifangshangcheng.setOnClickListener(this);
        holder.chanyezixun.setOnClickListener(this);
    }


    private void setBanner(SingleAdvLayoutViewHolder holder){
        holder.convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, datas);
        holder.convenientBanner.setPageIndicator(new int[]{R.mipmap.banner_unchoiced, R.mipmap.banner_choiced});
        holder.convenientBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        //设置如果只有一组数据时不能滑动
        holder.convenientBanner.setPointViewVisible(datas.size() == 1 ? false : true); // 指示器
        holder.convenientBanner.setManualPageable(datas.size() == 1 ? false : true);//设置false,手动影响（设置了该项无法手动切换）
        holder.convenientBanner.setCanLoop(datas.size() == 1 ? false : true); // 是否循环


        holder.convenientBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
            }
        });
    }
    private void setAdv(SingleAdvLayoutViewHolder holder){
        holder.adv.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, datas);
        holder.adv.setPageIndicator(new int[]{R.mipmap.banner_unchoiced, R.mipmap.banner_choiced});
        holder.adv.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        //设置如果只有一组数据时不能滑动
        holder.adv.setPointViewVisible(datas.size() == 1 ? false : true); // 指示器
        holder.adv.setManualPageable(datas.size() == 1 ? false : true);//设置false,手动影响（设置了该项无法手动切换）
        holder.adv.setCanLoop(datas.size() == 1 ? false : true); // 是否循环


        holder.adv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
            }
        });
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
        }
    }

    static class SingleAdvLayoutViewHolder extends RecyclerView.ViewHolder {
        public ConvenientBanner convenientBanner;
        public ConvenientBanner adv;
        public LinearLayout chanpindating,qiugoudating,kaifangshangcheng,chanyezixun;

        public SingleAdvLayoutViewHolder(View root) {
            super(root);
            convenientBanner = root.findViewById(R.id.vlayout_home_part1_banner);
            adv = root.findViewById(R.id.vlayout_home_part1_adv);
            chanpindating = root.findViewById(R.id.vlayout_home_part1_chanpin);
            qiugoudating = root.findViewById(R.id.vlayout_home_part1_qiugou);
            kaifangshangcheng = root.findViewById(R.id.vlayout_home_part1_kaifang);
            chanyezixun = root.findViewById(R.id.vlayout_home_part1_chanye);
        }
    }

    public interface MySingleAdvItemClickListener {
        void onItemClick(View v, int p);
    }
}



