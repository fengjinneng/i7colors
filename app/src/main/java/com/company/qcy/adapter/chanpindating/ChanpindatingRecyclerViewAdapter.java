package com.company.qcy.adapter.chanpindating;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.bean.ChanpindatingBean;

import java.util.ArrayList;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;

public class ChanpindatingRecyclerViewAdapter extends
        BaseQuickAdapter<ChanpindatingBean,BaseViewHolder> {
    public ChanpindatingRecyclerViewAdapter(int layoutResId,
                                            @Nullable List<ChanpindatingBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChanpindatingBean item) {
//        helper.setText(R.id.item_chanpindating_company,item.getName());
//        TagContainerLayout layout = helper.getView(R.id.item_chanpindating_tagcontainer);
//        List<String> tags = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            tags.add("染料"+i);
//        }
//        layout.setTags(tags);
    }
}
