package com.company.qcy.adapter.other;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.bean.ChanpindatingBean;

import java.util.List;

public class ChanyezixunRecyclerviewAdapter
        extends BaseQuickAdapter<ChanpindatingBean,BaseViewHolder> {
    public ChanyezixunRecyclerviewAdapter(int layoutResId, @Nullable List<ChanpindatingBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChanpindatingBean item) {

        helper.setText(R.id.item_chanyexixun_title,item.getName());
    }
}


