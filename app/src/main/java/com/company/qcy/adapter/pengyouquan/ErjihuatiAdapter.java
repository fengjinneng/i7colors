package com.company.qcy.adapter.pengyouquan;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.bean.pengyouquan.HuatiBean;

import org.w3c.dom.Text;

import java.util.List;

public class ErjihuatiAdapter extends BaseQuickAdapter<HuatiBean, BaseViewHolder> {


    public ErjihuatiAdapter(int layoutResId, @Nullable List<HuatiBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HuatiBean item) {
        TextView title = (TextView) helper.getView(R.id.item_huati_erjihuati_title);
        title.setText("#"+item.getTitle()+"#");

        TextView content = (TextView) helper.getView(R.id.item_huati_erjihuati_content);
        content.setText(item.getDescription());

        TextView cishu = (TextView) helper.getView(R.id.item_huati_erjihuati_cishu);
        if(StringUtils.isEmpty(item.getCommunityNum())){
            cishu.setText("0");
        }else {
            cishu.setText(item.getCommunityNum());
        }
    }
}
