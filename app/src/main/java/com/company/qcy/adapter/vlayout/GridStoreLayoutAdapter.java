package com.company.qcy.adapter.vlayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.company.qcy.R;
import com.company.qcy.widght.GridViewForScrollView;

import java.util.List;
public class GridStoreLayoutAdapter extends DelegateAdapter.Adapter<GridStoreLayoutAdapter.StoreAdapterviewHolder> {

    private Context context;
    private List<String> datas;
    private LayoutHelper helper;
    private RecyclerView.LayoutParams layoutParams;

    public GridStoreLayoutAdapter(Context context, List<String> datas, LayoutHelper helper) {
        this.context = context;
        this.datas = datas;
        this.helper = helper;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return helper;
    }

    @Override
    public GridStoreLayoutAdapter.StoreAdapterviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StoreAdapterviewHolder(LayoutInflater.from(context).inflate(R.layout.vlayout_home_store,parent,false));
    }

    @Override
    public void onBindViewHolder(GridStoreLayoutAdapter.StoreAdapterviewHolder holder, int position) {
        DisplayMetrics dm = new DisplayMetrics();
        int width = context.getResources().getDisplayMetrics().widthPixels;
        float density = dm.density;
        int itemWidth = (width / 3) + 23;
        int exGoodsWidth = (int) (datas.size() * (itemWidth + 5 * density));

        LinearLayout.LayoutParams exGoodsarams = new LinearLayout.LayoutParams(exGoodsWidth, 400);
        holder.gridView.setLayoutParams(exGoodsarams); // 设置GirdView布局参数,横向布局的关键
        holder.gridView.setColumnWidth(itemWidth); // 设置列表项宽
        holder.gridView.setHorizontalSpacing(5); // 设置列表项水平间距
        holder.gridView.setStretchMode(GridView.NO_STRETCH);
        holder.gridView.setNumColumns(datas.size()); // 设置列数量=列表集合数
        holder.gridView.setAdapter(new ScrollGridViewAdapter(context, datas, LayoutInflater.from(context)));
        holder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    static class StoreAdapterviewHolder extends RecyclerView.ViewHolder {
        public GridViewForScrollView gridView;
        public StoreAdapterviewHolder(View itemView) {
            super(itemView);
            gridView = itemView.findViewById(R.id.vlayout_home_store_gridview);
        }

    }

}
