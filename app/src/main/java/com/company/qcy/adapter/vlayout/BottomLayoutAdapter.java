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
import com.blankj.utilcode.util.StringUtils;
import com.company.qcy.R;

public class BottomLayoutAdapter extends DelegateAdapter.Adapter<BottomLayoutAdapter.SingleTitleLayoutViewHolder> {

    private  Context context;
    private LayoutHelper layoutHelper;
    private RecyclerView.LayoutParams layoutParams;


    //构造函数(传入每个的数据列表 & 展示的Item数量)
    public BottomLayoutAdapter(Context context, LayoutHelper layoutHelper) {
        this(context, layoutHelper, new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public BottomLayoutAdapter(Context context, LayoutHelper layoutHelper, @NonNull RecyclerView.LayoutParams layoutParams) {
        this.context = context;
        this.layoutHelper = layoutHelper;
        this.layoutParams = layoutParams;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public BottomLayoutAdapter.SingleTitleLayoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SingleTitleLayoutViewHolder(LayoutInflater.from(context).inflate(R.layout.home_bottom_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(BottomLayoutAdapter.SingleTitleLayoutViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    static class SingleTitleLayoutViewHolder extends RecyclerView.ViewHolder {

        public SingleTitleLayoutViewHolder(View root) {
            super(root);

        }
    }


}
