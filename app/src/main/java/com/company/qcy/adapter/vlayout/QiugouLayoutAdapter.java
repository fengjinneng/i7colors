package com.company.qcy.adapter.vlayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.MatisseImageUtil;
import com.company.qcy.Utils.PermisionUtil;
import com.company.qcy.bean.qiugou.QiugouBean;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.List;

public class QiugouLayoutAdapter extends DelegateAdapter.Adapter<QiugouLayoutAdapter.QiugouLayoutViewHolder> {

    private List<QiugouBean> datas;
    private  Context context;
    private LayoutHelper layoutHelper;
    private RecyclerView.LayoutParams layoutParams;

    private OnQiugouItemClickListener onQiugouItemClickListener;

    //构造函数(传入每个的数据列表 & 展示的Item数量)
    public QiugouLayoutAdapter(Context context, LayoutHelper layoutHelper, List<QiugouBean> datas) {
        this(context, layoutHelper, new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT), datas);
    }

    public QiugouLayoutAdapter(Context context, LayoutHelper layoutHelper, @NonNull RecyclerView.LayoutParams layoutParams, List<QiugouBean> datas) {
        this.context = context;
        this.layoutHelper = layoutHelper;
        this.layoutParams = layoutParams;
        this.datas = datas;
    }

    public void setOnQiugouItemClickListener(OnQiugouItemClickListener onQiugouItemClickListener) {
        this.onQiugouItemClickListener = onQiugouItemClickListener;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public QiugouLayoutAdapter.QiugouLayoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new QiugouLayoutViewHolder(LayoutInflater.from(context).inflate(R.layout.item_qiugoudating_recyclerview, parent, false));
    }

    @Override
    public void onBindViewHolder(final QiugouLayoutAdapter.QiugouLayoutViewHolder holder, final int position) {

        final QiugouBean item = datas.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onQiugouItemClickListener != null) {
                    onQiugouItemClickListener.onQiugouItemClick(item);
                }
            }
        });

        holder.productName.setText(item.getProductName());
        holder.canyubaojia.setText(item.getOfferNum() + "");
        holder.address.setText(item.getLocationProvince() + " " + item.getLocationCity());
        holder.danwei.setText(datas.get(position).getNumUnit());
        holder.zhongliang.setText(datas.get(position).getNum() + "");


        if (StringUtils.equals(item.getStatus(), "1")) {
            holder.zhuangtai.setImageDrawable(context.getResources().getDrawable(R.mipmap.bijiazhong));
            holder.yiwancheng.setVisibility(View.GONE);
        } else {
            holder.zhuangtai.setImageDrawable(context.getResources().getDrawable(R.mipmap.bijiawancheng));
            holder.yiwancheng.setVisibility(View.VISIBLE);
        }

        if(StringUtils.equals("1",item.getShowInfo())){
            holder.zhitongcheLayout.setVisibility(View.VISIBLE);
        }else {
            holder.zhitongcheLayout.setVisibility(View.GONE);
        }

        holder.shenfen.setText(item.getPublishType());
        if (StringUtils.equals(item.getPublishType(), "企业发布")) {
            holder.shenfen.setBackground(context.getResources().getDrawable(R.mipmap.qiyeyonghu));
            if (StringUtils.equals("1", item.getIsCharger())) {
                holder.wodefabu.setVisibility(View.VISIBLE);
                holder.wodefabu.setBackgroundResource(R.drawable.background_wodefabu_qiye);
            } else {
                holder.wodefabu.setVisibility(View.GONE);
            }
        } else {
            holder.shenfen.setBackground(context.getResources().getDrawable(R.mipmap.gerenfabu));
            if (StringUtils.equals("1", item.getIsCharger())) {
                holder.wodefabu.setVisibility(View.VISIBLE);
                holder.wodefabu.setBackgroundResource(R.drawable.background_wodefabu_geren);
            } else {
                holder.wodefabu.setVisibility(View.GONE);
            }
        }


        holder.yijianhujiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    PermisionUtil.callKefu(context);
            }
        });

        if (StringUtils.isEmpty(item.getSurplusDay())) {
            if (StringUtils.isEmpty(item.getSurplusHour())) {
                if (StringUtils.isEmpty(item.getSurplusMin())) {
                } else {
                    holder.firstTime.setText(item.getSurplusMin());
                    holder.secondTime.setText(item.getSurplusSec());
                    holder.firstTimeDanwei.setText("分");
                    holder.secondTimeDanwei.setText("秒");
                }
            } else {
                holder.firstTime.setText(item.getSurplusHour());
                holder.secondTime.setText(item.getSurplusMin());
                holder.firstTimeDanwei.setText("小时");
                holder.secondTimeDanwei.setText("分钟");

            }

        } else {
            holder.firstTime.setText(item.getSurplusDay());
            holder.secondTime.setText(item.getSurplusHour());

        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    static class QiugouLayoutViewHolder extends RecyclerView.ViewHolder {

        private TextView firstTime;
        private TextView secondTime;
        private TextView zhongliang;
        private TextView address;
        private TextView canyubaojia;
        private TextView productName;

        private TextView shenfen;
        private ImageView yijianhujiao;
        private ImageView zhuangtai;
        private TextView danwei;
        private TextView yiwancheng;
        private TextView wodefabu;

        private TextView firstTimeDanwei;
        private TextView secondTimeDanwei;

        private ConstraintLayout zhitongcheLayout;

        public QiugouLayoutViewHolder(View root) {
            super(root);
            firstTime = root.findViewById(R.id.item_qiugoudating_firsttime);
            secondTime = root.findViewById(R.id.item_qiugoudating_secondtime);
            address = root.findViewById(R.id.item_qiugoudating_diqu);
            canyubaojia = root.findViewById(R.id.item_qiugoudating_canyubaojia);
            productName = root.findViewById(R.id.item_qiugoudating_mingcheng);

            shenfen = root.findViewById(R.id.item_qiugoudating_shenfen);
            zhongliang = root.findViewById(R.id.item_qiugoudating_zhongliang);

            yijianhujiao = root.findViewById(R.id.item_qiugoudating_yijianhujiao);
            zhuangtai = root.findViewById(R.id.item_qiugoudating_zhuangtai);
            danwei = root.findViewById(R.id.item_qiugoudating_danwei);
            yiwancheng = root.findViewById(R.id.item_qiugoudating_yiwancheng);
            wodefabu = root.findViewById(R.id.item_qiugoudating_wodefabu);
            firstTimeDanwei = root.findViewById(R.id.item_qiugoudating_firsttime_text);
            secondTimeDanwei = root.findViewById(R.id.item_qiugoudating_secondtime_text);
            zhitongcheLayout = root.findViewById(R.id.item_qiugoudating_zhitongche);
        }
    }

    public interface OnQiugouItemClickListener {
        void onQiugouItemClick(QiugouBean bean);
    }

}
