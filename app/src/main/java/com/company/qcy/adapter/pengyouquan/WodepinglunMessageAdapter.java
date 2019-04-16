package com.company.qcy.adapter.pengyouquan;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.bean.pengyouquan.WodePinglunMessageBean;

import java.util.List;

public class WodepinglunMessageAdapter extends BaseQuickAdapter<WodePinglunMessageBean, BaseViewHolder> {


    public WodepinglunMessageAdapter(int layoutResId, @Nullable List<WodePinglunMessageBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WodePinglunMessageBean item) {

        ((TextView) helper.getView(R.id.item_wodepinglun_message_name)).setText(item.getPostUserName());

        ImageView imageView = (ImageView) helper.getView(R.id.item_wodepinglun_message_img);
        GlideUtils.loadCircleImage(mContext,ServerInfo.IMAGE+item.getPostUserPhoto(),imageView);

        ((TextView) helper.getView(R.id.item_wodepinglun_message_content)).setText(item.getCommentText());
    }
}
