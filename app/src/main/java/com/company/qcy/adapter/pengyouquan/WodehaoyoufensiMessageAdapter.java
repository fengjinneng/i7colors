package com.company.qcy.adapter.pengyouquan;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.bean.pengyouquan.WodehaoyoufensiMessageBean;

import java.util.List;

public class WodehaoyoufensiMessageAdapter extends BaseQuickAdapter<WodehaoyoufensiMessageBean, BaseViewHolder> {

    public WodehaoyoufensiMessageAdapter(int layoutResId, @Nullable List<WodehaoyoufensiMessageBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WodehaoyoufensiMessageBean item) {

//        ((TextView) helper.getView(R.id.item_wodehaoyoufensi_message_name)).setText(item);

    }
}
