package com.company.qcy.adapter.pengyouquan;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.amap.api.services.help.Tip;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;

import java.util.List;

public class ShurutishiAdapter extends BaseQuickAdapter<Tip, BaseViewHolder> {


    public ShurutishiAdapter(int layoutResId, @Nullable List<Tip> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Tip item) {

        TextView title = (TextView) helper.getView(R.id.item_fabu_address_title);
        TextView content = (TextView) helper.getView(R.id.item_fabu_address_content);
        title.setText(item.getName()+" ("+item.getDistrict()+")");
        content.setText(item.getAddress());

    }
}
