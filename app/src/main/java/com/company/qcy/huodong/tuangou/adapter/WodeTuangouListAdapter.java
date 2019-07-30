package com.company.qcy.huodong.tuangou.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.huodong.tuangou.bean.TuangouBean;

import java.util.List;

public class WodeTuangouListAdapter extends
        BaseQuickAdapter<TuangouBean, BaseViewHolder> {


    public WodeTuangouListAdapter(int layoutResId, @Nullable List<TuangouBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TuangouBean item) {

        ImageView status = (ImageView) helper.getView(R.id.item_wodetuangou_tuangou_status);

        if (StringUtils.equals("00", item.getEndCode())) {
            //团购未开始
            status.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.tuangou_weikaishi));
        } else if (StringUtils.equals("10", item.getEndCode())) {
            //已开始未领完
            status.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.tuangou_yikaishi));
        } else if (StringUtils.equals("11", item.getEndCode())) {
            //已开始已领完
            if (StringUtils.equals("0", item.getIsConsiderStock())) {
                status.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.tuangou_yikaishi));
            } else {
                status.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.tuangou_yijieshu));
            }
        } else if (StringUtils.equals("20", item.getEndCode())) {
            //已结束未领完
            status.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.tuangou_yijieshu));
        } else if (StringUtils.equals("21", item.getEndCode())) {
            //已结束已领完
            status.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.tuangou_yijieshu));
        }

        if (!StringUtils.isEmpty(item.getProductName())) {
            helper.setText(R.id.item_wodetuangou_list_name, item.getProductName());
        }

        if (!StringUtils.isEmpty(item.getNum())) {
            helper.setText(R.id.item_wodetuangou_list_goumailiang, item.getNum());
        }

        if (!StringUtils.isEmpty(item.getNumUnit())) {
            helper.setText(R.id.item_wodetuangou_list_goumailiang_unit, item.getNumUnit());
        }

        if (!StringUtils.isEmpty(item.getNewPrice())) {
            helper.setText(R.id.item_wodetuangou_list_price, item.getNewPrice());
        }

        if (!StringUtils.isEmpty(item.getPriceUnit())) {
            helper.setText(R.id.item_wodetuangou_list_price_unit, "元/"+item.getPriceUnit());
        }
        ImageView imageView = (ImageView) helper.getView(R.id.item_wodetuangou_list_img);

        if (!StringUtils.isEmpty(item.getProductPic())) {
            Glide.with(mContext).load(ServerInfo.IMAGE + item.getProductPic()).into(imageView);
        }
    }
}
