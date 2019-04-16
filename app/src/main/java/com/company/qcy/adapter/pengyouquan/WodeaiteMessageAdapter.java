package com.company.qcy.adapter.pengyouquan;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.bean.pengyouquan.WodeaiteMessageBean;

import java.util.List;

public class WodeaiteMessageAdapter extends BaseQuickAdapter<WodeaiteMessageBean, BaseViewHolder> {


    public WodeaiteMessageAdapter(int layoutResId, @Nullable List<WodeaiteMessageBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WodeaiteMessageBean item) {

        ((TextView) helper.getView(R.id.item_aite_message_name)).setText(item.getPostUserName());

    }
}
