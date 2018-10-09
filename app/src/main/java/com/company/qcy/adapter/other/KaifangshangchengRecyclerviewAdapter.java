package com.company.qcy.adapter.other;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.bean.ChanpindatingBean;

import java.util.ArrayList;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;

public class KaifangshangchengRecyclerviewAdapter extends
        BaseQuickAdapter<ChanpindatingBean, BaseViewHolder> {
    public KaifangshangchengRecyclerviewAdapter(int layoutResId,
                                                @Nullable List<ChanpindatingBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChanpindatingBean item) {
        helper.setText(R.id.item_kaifangshangcheng_company, item.getName());
        TagContainerLayout layout = helper.getView(R.id.kaifangshangcheng_tag_container);
        List<String> tags = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            tags.add("染料"+i);
        }
        layout.setTags(tags);
    }
}


