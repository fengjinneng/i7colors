package com.company.qcy.adapter.vlayout;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.bean.kaifangshangcheng.ProductBean;

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

        holder.company.setText(item.getSupplierShotName());
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
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                PhoneUtils.call(context.getResources().getString(R.string.PHONE));
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
            company = root.findViewById(R.id.item_chanpindating_company);
            img = root.findViewById(R.id.item_chanpindating_img);
            tagContainerLayout = root.findViewById(R.id.item_chanpindating_tagcontainer);
            price = root.findViewById(R.id.item_chanpindating_price);
            yijia = root.findViewById(R.id.item_chanpindating_yijia_layout);
            jiage = root.findViewById(R.id.item_chanpindating_jiage_layout);
            detail = root.findViewById(R.id.item_chanpindating_detail);
            yijianhujiao = root.findViewById(R.id.item_chanpindating_yijianhujiao);

        }
    }

    public interface OnChanpinItemClickListener {
        void onChanpinItemClick(ProductBean bean);
    }

}