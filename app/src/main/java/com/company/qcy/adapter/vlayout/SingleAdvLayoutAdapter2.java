package com.company.qcy.adapter.vlayout;

import android.content.Context;
import android.content.Intent;
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
import com.blankj.utilcode.util.ObjectUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.NetworkImageHolderView;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.ui.activity.chanpindating.ChanpindatingActivity;
import com.company.qcy.ui.activity.chanyezixun.ChanyezixunActivity;
import com.company.qcy.ui.activity.kaifangshangcheng.KaifangshangchengActivity;
import com.company.qcy.ui.activity.qiugoudating.QiugoudatingActivity;
import com.company.qcy.ui.activity.tuangou.TuangouliebiaoActivity;

import java.util.List;


public class SingleAdvLayoutAdapter2 extends DelegateAdapter.Adapter<SingleAdvLayoutAdapter2.SingleAdvLayoutViewHolder> implements View.OnClickListener {

    // 用于存放数据列表

    List<String> datas;
    private static Context context;
    private LayoutHelper layoutHelper;
    private RecyclerView.LayoutParams layoutParams;
    private int count;

    static MySingleAdv2ItemClickListener myItemClickListener;
    // 用于设置Item点击事件

    //构造函数(传入每个的数据列表 & 展示的Item数量)
    public SingleAdvLayoutAdapter2(Context context, LayoutHelper layoutHelper, int count, List<String> datas) {
        this(context, layoutHelper, count, new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT), datas);
    }

    public SingleAdvLayoutAdapter2(Context context, LayoutHelper layoutHelper,
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
        return new SingleAdvLayoutViewHolder(LayoutInflater.from(context).inflate(R.layout.vlayout_home_part2, parent, false));
    }

    @Override
    public void onBindViewHolder(SingleAdvLayoutViewHolder holder, int position) {
        holder.adv.setOnClickListener(this);
        if (ObjectUtils.isEmpty(datas)) {
            return;
        }
        GlideUtils.loadImageRct(context,datas.get(0), holder.adv);
    }

    @Override
    public int getItemCount() {
        return count;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vlayout_home_part2_adv:
                ActivityUtils.startActivity(TuangouliebiaoActivity.class);
                break;

        }
    }

    static class SingleAdvLayoutViewHolder extends RecyclerView.ViewHolder {
        public ImageView adv;

        public SingleAdvLayoutViewHolder(View root) {
            super(root);
            adv = root.findViewById(R.id.vlayout_home_part2_adv);
        }
    }

    public interface MySingleAdv2ItemClickListener {
        void onItemClick(View v, int p);
    }
}



