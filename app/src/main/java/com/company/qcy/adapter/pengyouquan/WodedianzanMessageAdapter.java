package com.company.qcy.adapter.pengyouquan;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.bean.pengyouquan.WodedianzanMessageBean;

import java.util.List;

public class WodedianzanMessageAdapter extends BaseQuickAdapter<WodedianzanMessageBean, BaseViewHolder> {

    public WodedianzanMessageAdapter(int layoutResId, @Nullable List<WodedianzanMessageBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WodedianzanMessageBean item) {

        ((TextView) helper.getView(R.id.item_wodedianzan_message_name)).setText(item.getPostUserName());

        ImageView imageView = (ImageView) helper.getView(R.id.item_wodedianzan_message_img);
        GlideUtils.loadCircleImage(mContext,ServerInfo.IMAGE+item.getPostUserPhoto(),imageView);


    }
}
