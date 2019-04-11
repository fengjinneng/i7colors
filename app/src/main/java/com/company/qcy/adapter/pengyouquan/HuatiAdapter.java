package com.company.qcy.adapter.pengyouquan;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.bean.pengyouquan.HuatiBean;

import java.util.List;

public class HuatiAdapter extends BaseQuickAdapter<HuatiBean, BaseViewHolder> {


    public HuatiAdapter(int layoutResId, @Nullable List<HuatiBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HuatiBean item) {

        TextView name = (TextView) helper.getView(R.id.item_popwindow_huati_name);
        name.setText(item.getTitle());

        if (item.isChecked()) {
            name.setTextColor(mContext.getResources().getColor(R.color.hongse));
        } else {
            name.setTextColor(mContext.getResources().getColor(R.color.putongwenben));
        }

    }
}
