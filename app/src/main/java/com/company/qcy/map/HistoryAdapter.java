package com.company.qcy.map;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;

import java.util.List;

public class HistoryAdapter extends BaseQuickAdapter<HistoryBean, BaseViewHolder> {


    public HistoryAdapter(int layoutResId, @Nullable List<HistoryBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryBean item) {

        helper.addOnClickListener(R.id.item_city_search_history_name);

        TextView name = (TextView) helper.getView(R.id.item_city_search_history_name);


        if (StringUtils.isEmpty(item.getArea())) {

            if (StringUtils.isEmpty(item.getCity())) {

                if (StringUtils.isEmpty(item.getProvince())) {
                    name.setText("全国");
                } else {
                    name.setText(item.getProvince());
                }

            } else {
                name.setText(item.getCity());
            }

        } else {
            name.setText(item.getArea());

        }


//        if(!StringUtils.isEmpty(item.getArea())){
//            //区不为空
//            if(StringUtils.equals("全市",item.getArea())){
//                name.setText(item.getCity());
//            }else {
//                name.setText(item.getArea());
//            }
//            return;
//        }
//
//        if(!StringUtils.isEmpty(item.getCity())){
//            if(StringUtils.equals("全省",item.getCity())){
//                name.setText(item.getProvince());
//            }else {
//                name.setText(item.getCity());
//            }
//            return;
//        }
//
//        if(!StringUtils.isEmpty(item.getProvince())){
//            name.setText(item.getProvince());
//        }else {
//            name.setText("全国");
//        }
    }
}
