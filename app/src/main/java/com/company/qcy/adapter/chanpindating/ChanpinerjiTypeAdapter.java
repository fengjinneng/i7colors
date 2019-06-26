package com.company.qcy.adapter.chanpindating;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.bean.chanpin.ChanpinTypeBean;

import java.util.List;

public class ChanpinerjiTypeAdapter extends BaseQuickAdapter<ChanpinTypeBean.PropListBean, BaseViewHolder> {


    public ChanpinerjiTypeAdapter(int layoutResId, @Nullable List<ChanpinTypeBean.PropListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChanpinTypeBean.PropListBean item) {


        TextView name = (TextView) helper.getView(R.id.item_chanpinfenlei_erji_name);

        name.setText(item.getValue());
        if (item.isChecked()) {
            name.setBackground(mContext.getResources().getDrawable(R.drawable.background_chanpinfenlei_checked));
            name.setTextColor(mContext.getResources().getColor(R.color.baise));
        } else {
            name.setBackground(mContext.getResources().getDrawable(R.drawable.background_chanpinfenlei_unchecked));
            name.setTextColor(mContext.getResources().getColor(R.color.putongwenben));
        }

    }
}
