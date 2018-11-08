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

public class SingleTitleLayoutAdapter extends DelegateAdapter.Adapter<SingleTitleLayoutAdapter.SingleTitleLayoutViewHolder> {

    private String title;
    private static Context context;
    private LayoutHelper layoutHelper;
    private RecyclerView.LayoutParams layoutParams;
    private int count = 0;
    private boolean hideMore;

    public void setOnMoreClickListner(OnMoreClickListner onMoreClickListner) {
        this.onMoreClickListner = onMoreClickListner;
    }

    private OnMoreClickListner onMoreClickListner;

    //构造函数(传入每个的数据列表 & 展示的Item数量)
    public SingleTitleLayoutAdapter(Context context, LayoutHelper layoutHelper, int count, String title, boolean hideMore) {
        this(context, layoutHelper, count, new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT), title, hideMore);
    }

    public SingleTitleLayoutAdapter(Context context, LayoutHelper layoutHelper, int count, @NonNull RecyclerView.LayoutParams layoutParams, String title, boolean hideMore) {
        this.context = context;
        this.layoutHelper = layoutHelper;
        this.count = count;
        this.layoutParams = layoutParams;
        this.title = title;
        this.hideMore = hideMore;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public SingleTitleLayoutAdapter.SingleTitleLayoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SingleTitleLayoutViewHolder(LayoutInflater.from(context).inflate(R.layout.vlayout_home_title, parent, false));
    }

    @Override
    public void onBindViewHolder(SingleTitleLayoutAdapter.SingleTitleLayoutViewHolder holder, int position) {
        if (hideMore) {
            holder.more.setVisibility(View.GONE);
        } else {
            holder.more.setVisibility(View.VISIBLE);
        }
        if (StringUtils.equals("开放商城", title)) {
            holder.line.setVisibility(View.GONE);
        }
        holder.tv.setText(title);
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMoreClickListner.onMoreClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return count;
    }

    static class SingleTitleLayoutViewHolder extends RecyclerView.ViewHolder {
        public TextView tv;
        public TextView more;
        public TextView line;

        public SingleTitleLayoutViewHolder(View root) {
            super(root);
            tv = root.findViewById(R.id.vlayout_home_title_one);
            more = root.findViewById(R.id.vlayout_home_title_more);
            line = root.findViewById(R.id.textView131);

        }
    }

    public interface OnMoreClickListner {
        void onMoreClick();
    }

}
