package com.company.qcy.adapter.vlayout;

import android.content.Context;
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
import com.blankj.utilcode.util.ObjectUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.bean.kaifangshangcheng.DianpuliebiaoBean;

import java.util.ArrayList;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;

public class MarketLayoutAdapter extends DelegateAdapter.Adapter<MarketLayoutAdapter.MarketLayoutViewHolder> {


    private List<DianpuliebiaoBean> datas;
    private  Context context;
    private LayoutHelper layoutHelper;
    private RecyclerView.LayoutParams layoutParams;

    private OnMarketItemClickListener onMarketItemClickListener;

    //构造函数(传入每个的数据列表 & 展示的Item数量)
    public MarketLayoutAdapter(Context context, LayoutHelper layoutHelper, List<DianpuliebiaoBean> datas) {
        this(context, layoutHelper, new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT), datas);
    }

    public MarketLayoutAdapter(Context context, LayoutHelper layoutHelper, @NonNull RecyclerView.LayoutParams layoutParams, List<DianpuliebiaoBean> datas) {
        this.context = context;
        this.layoutHelper = layoutHelper;
        this.layoutParams = layoutParams;
        this.datas = datas;
    }


    public void setOnMarketItemClickListener(OnMarketItemClickListener onMarketItemClickListener) {
        this.onMarketItemClickListener = onMarketItemClickListener;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public MarketLayoutAdapter.MarketLayoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MarketLayoutViewHolder(LayoutInflater.from(context).inflate(R.layout.item_kaifangshangcheng_recyclerview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MarketLayoutViewHolder holder, final int i) {

        final DianpuliebiaoBean item = datas.get(i);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMarketItemClickListener.onMarketItemClick(item);
            }
        });

        holder.companyname.setText(item.getCompany().getCompanyName());
        List<String> tags = new ArrayList<>();
        if (!ObjectUtils.isEmpty(item.getBusinessList())) {
            for (int i1 = 0; i1 < item.getBusinessList().size(); i1++) {
                tags.add(item.getBusinessList().get(i1).getValue());
            }
            holder.tagContainerLayout.setTags(tags);
        }


        GlideUtils.loadImage(context,ServerInfo.IMAGE + item.getLogo(),holder.img);

        holder.phone.setText(item.getCompany().getPhone());
        holder.address.setText(item.getCompany().getProvinceName()+" "+item.getCompany().getCityName());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    static class MarketLayoutViewHolder extends RecyclerView.ViewHolder {

        ImageView img;

        TagContainerLayout tagContainerLayout;
        TextView companyname;
        TextView address;
        TextView phone;


        public MarketLayoutViewHolder(View root) {
            super(root);
            img = root.findViewById(R.id.item_kaifangshangcheng_img);

            tagContainerLayout = root.findViewById(R.id.kaifangshangcheng_tag_container);
            companyname = root.findViewById(R.id.item_kaifangshangcheng_company);
            address = root.findViewById(R.id.item_kaifangshangcheng_address);
            phone = root.findViewById(R.id.item_kaifangshangcheng_phone);

        }
    }

    public interface OnMarketItemClickListener {
        void onMarketItemClick(DianpuliebiaoBean bean);
    }

}
