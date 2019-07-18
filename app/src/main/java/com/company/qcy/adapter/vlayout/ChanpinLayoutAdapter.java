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
import com.company.qcy.Utils.PermisionUtil;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.bean.chanpin.ProductBean;

import java.util.ArrayList;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;

public class ChanpinLayoutAdapter extends DelegateAdapter.Adapter<ChanpinLayoutAdapter.ChanpinLayoutViewHolder> {


    private List<ProductBean> datas;
    private  Context context;
    private LayoutHelper layoutHelper;
    private RecyclerView.LayoutParams layoutParams;

    private OnChanpinItemClickListener onChanpinItemClickListener;

    //构造函数(传入每个的数据列表 & 展示的Item数量)
    public ChanpinLayoutAdapter(Context context, LayoutHelper layoutHelper, List<ProductBean> datas) {
        this(context, layoutHelper, new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT), datas);
    }

    public ChanpinLayoutAdapter(Context context, LayoutHelper layoutHelper, @NonNull RecyclerView.LayoutParams layoutParams, List<ProductBean> datas) {
        this.context = context;
        this.layoutHelper = layoutHelper;
        this.layoutParams = layoutParams;
        this.datas = datas;
    }


    public void setOnChanpinItemClickListener(OnChanpinItemClickListener onChanpinItemClickListener) {
        this.onChanpinItemClickListener = onChanpinItemClickListener;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public ChanpinLayoutAdapter.ChanpinLayoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChanpinLayoutViewHolder(LayoutInflater.from(context).inflate(R.layout.item_chanpindating_recyclerview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChanpinLayoutViewHolder holder, final int i) {

        final ProductBean item = datas.get(i);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChanpinItemClickListener.onChanpinItemClick(item);
            }
        });
        List<String> tags = new ArrayList<>();
        if (!ObjectUtils.isEmpty(item.getTagList())) {
            for (int a = 0; a < item.getTagList().size(); a++) {
                tags.add(item.getTagList().get(a));
            }
            holder.tagContainerLayout.setTags(tags);
        }

        holder.company.setText(item.getCompanyName());
        GlideUtils.loadImage(context, ServerInfo.IMAGE + item.getPic(), holder.img);
        holder.detail.setText(item.getProductName());

        //价格为空
        if (item.isDisplayPrice()) {

            holder.yijia.setVisibility(View.GONE);
            holder.jiage.setVisibility(View.VISIBLE);
            holder.price.setText(item.getPrice());

        } else {
            holder.yijia.setVisibility(View.VISIBLE);
            holder.jiage.setVisibility(View.GONE);
        }

        holder.yijianhujiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermisionUtil.callPhone(context,item.getPhone());
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    static class ChanpinLayoutViewHolder extends RecyclerView.ViewHolder {

        TextView company;
        ImageView img;
        TagContainerLayout tagContainerLayout;
        TextView price;
        ConstraintLayout yijia;
        ConstraintLayout jiage;
        TextView detail;
        ImageView yijianhujiao;


        public ChanpinLayoutViewHolder(View root) {
            super(root);
            company = root.findViewById(R.id.item_chanpindating_companyName);
            img = root.findViewById(R.id.item_chanpindating_img);
            tagContainerLayout = root.findViewById(R.id.item_chanpindating_tagcontainer);
            price = root.findViewById(R.id.item_chanpindating_price);
            yijia = root.findViewById(R.id.item_chanpindating_yijia_layout);
            jiage = root.findViewById(R.id.item_chanpindating_jiage_layout);
            detail = root.findViewById(R.id.item_chanpindating_productName);
            yijianhujiao = root.findViewById(R.id.item_chanpindating_yijianhujiao);

        }
    }

    public interface OnChanpinItemClickListener {
        void onChanpinItemClick(ProductBean bean);
    }

}
