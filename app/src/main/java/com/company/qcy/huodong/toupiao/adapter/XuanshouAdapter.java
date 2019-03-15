package com.company.qcy.huodong.toupiao.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.huodong.toupiao.bean.XuanshouBean;

import java.util.List;

public class XuanshouAdapter extends BaseQuickAdapter<XuanshouBean, BaseViewHolder> {


    public XuanshouAdapter(int layoutResId, @Nullable List<XuanshouBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, XuanshouBean item) {

        helper.setText(R.id.item_toupiao_xuanshou_name, item.getName());
        helper.setText(R.id.item_toupiao_xuanshou_miaoshu, item.getDescription());
        helper.setText(R.id.item_toupiao_xuanshou_tickets, item.getTicketNum());

        ImageView imageView = (ImageView) helper.getView(R.id.item_toupiao_xuanshou_img);
//        GlideUtils.loadImage(mContext, ServerInfo.IMAGE + item.getPic(), imageView);

        GlideUtils.loadYuanjiaoPicture(mContext, ServerInfo.IMAGE + item.getPic(), imageView);
        ImageView paimingImg = (ImageView) helper.getView(R.id.item_toupiao_xuanshou_paiming_img);
        TextView paimingText = (TextView) helper.getView(R.id.item_toupiao_xuanshou_paiming_text);

        paimingImg.setVisibility(View.GONE);
        paimingText.setVisibility(View.GONE);

        switch (item.getSort()) {
            case "1":
                paimingImg.setVisibility(View.VISIBLE);
                paimingImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.toupiao_diyiming));
                break;
            case "2":
                paimingImg.setVisibility(View.VISIBLE);
                paimingImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.toupiao_dierming));
                break;
            case "3":
                paimingImg.setVisibility(View.VISIBLE);
                paimingImg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.toupiao_disanming));
                break;
            default:
                paimingText.setVisibility(View.VISIBLE);
                paimingText.setText(item.getSort());
                break;
        }
        helper.addOnClickListener(R.id.item_toupiao_xuanshou_toupiao);

        Button toupiao = (Button) helper.getView(R.id.item_toupiao_xuanshou_toupiao);

        if(StringUtils.equals("0",item.getJoinedTicketNum())){
            toupiao.setText("投票");
        }else {
            toupiao.setText("已投"+item.getJoinedTicketNum()+"票");
        }

        if(!StringUtils.equals("1",item.getMainStatus())){
            toupiao.setText("投票已结束!");
            toupiao.setClickable(false);
            toupiao.setBackgroundColor(mContext.getResources().getColor(R.color.putongwenben));

        }


    }
}
