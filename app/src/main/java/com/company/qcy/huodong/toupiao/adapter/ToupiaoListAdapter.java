package com.company.qcy.huodong.toupiao.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.huodong.toupiao.bean.ToupiaoBean;

import java.util.List;

public class ToupiaoListAdapter  extends BaseQuickAdapter<ToupiaoBean, BaseViewHolder> {



    public ToupiaoListAdapter(int layoutResId, @Nullable List<ToupiaoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ToupiaoBean item) {

        ImageView img = (ImageView) helper.getView(R.id.item_toupiao_list_img);
        ImageView status = (ImageView) helper.getView(R.id.item_toupiao_list_status);

        GlideUtils.loadImageRct(mContext, ServerInfo.IMAGE+item.getBanner(),img);

        helper.setText(R.id.item_toupiao_list_title,item.getName());
        helper.setText(R.id.item_toupiao_list_canyuzhe,item.getApplicationNum());
        helper.setText(R.id.item_toupiao_list_toupiaoshu,item.getJoinNum());
        helper.setText(R.id.item_toupiao_list_fangwenliang,item.getClickNum());

        switch (item.getEndCode()){
            case "0"://未开始
                status.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.tuangou_weikaishi));
                break;
            case "1"://进行中
                status.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.toupiao_jinxingzhong));
                break;
            case "2"://结束
                status.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.toupiao_yijieshu));
                break;
        }

    }
}
