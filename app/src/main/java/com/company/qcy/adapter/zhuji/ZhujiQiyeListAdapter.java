package com.company.qcy.adapter.zhuji;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.bean.zhuji.ZhujiQiyeBean;

import java.util.List;

public class ZhujiQiyeListAdapter extends BaseQuickAdapter<ZhujiQiyeBean, BaseViewHolder> {


    public ZhujiQiyeListAdapter(int layoutResId, @Nullable List<ZhujiQiyeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ZhujiQiyeBean item) {


        helper.setText(R.id.item_zhuji_qiye_list_name,item.getName());
        helper.setText(R.id.item_zhuji_qiye_list_content,item.getDescription());

        ImageView imageView = (ImageView) helper.getView(R.id.item_zhuji_qiye_list_img);


        GlideUtils.loadImageDefault(mContext,ServerInfo.IMAGE + item.getLogo(),imageView);

    }
}
