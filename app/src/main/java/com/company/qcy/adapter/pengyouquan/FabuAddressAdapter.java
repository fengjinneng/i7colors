package com.company.qcy.adapter.pengyouquan;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;

import java.util.List;

public class FabuAddressAdapter extends BaseQuickAdapter<PoiItem, BaseViewHolder> {



    public FabuAddressAdapter(int layoutResId, @Nullable List<PoiItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PoiItem item) {

        TextView title = (TextView) helper.getView(R.id.item_fabu_address_title);
        TextView content = (TextView) helper.getView(R.id.item_fabu_address_content);
        title.setText(item.getTitle());
        content.setText(item.getCityName() + item.getAdName() + item.getSnippet());

    }
}
