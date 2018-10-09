package com.company.qcy.adapter.vlayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.company.qcy.R;

import java.util.List;

public class TouTiaoLayoutAdapter extends DelegateAdapter.Adapter<TouTiaoLayoutAdapter.SingleTitleLayoutViewHolder> {



    private List<String> datas;
    private static Context context;
    private LayoutHelper layoutHelper;
    private RecyclerView.LayoutParams layoutParams;

    //构造函数(传入每个的数据列表 & 展示的Item数量)
    public TouTiaoLayoutAdapter(Context context, LayoutHelper layoutHelper, List<String> datas) {
        this(context, layoutHelper, new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT), datas);
    }

    public TouTiaoLayoutAdapter(Context context, LayoutHelper layoutHelper, @NonNull RecyclerView.LayoutParams layoutParams, List<String> datas) {
        this.context = context;
        this.layoutHelper = layoutHelper;
        this.layoutParams = layoutParams;
        this.datas = datas;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public TouTiaoLayoutAdapter.SingleTitleLayoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SingleTitleLayoutViewHolder(LayoutInflater.from(context).inflate(R.layout.vlayout_home_toutiao,parent,false));
    }

    @Override
    public void onBindViewHolder(TouTiaoLayoutAdapter.SingleTitleLayoutViewHolder holder, int position) {
        holder.number.setText(position+1+"");
        holder.tv.setText(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    static class SingleTitleLayoutViewHolder extends RecyclerView.ViewHolder {
        public TextView tv;
        public TextView number;

        public SingleTitleLayoutViewHolder(View root) {
            super(root);
            tv = root.findViewById(R.id.vlayout_home_toutiao_textview);
            number = root.findViewById(R.id.vlayout_home_toutiao_number);


        }
    }

}
