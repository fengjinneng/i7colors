package com.company.qcy.huodong.caigoulianmeng.adapter;

import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CheckBox;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.huodong.caigoulianmeng.bean.HHHBean;

import java.util.List;

public class WoyaodinghuoAdapter extends BaseQuickAdapter<HHHBean.MeetingListBean.DataBean, BaseViewHolder> {


    public WoyaodinghuoAdapter(int layoutResId, @Nullable List<HHHBean.MeetingListBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HHHBean.MeetingListBean.DataBean item) {

        TextView yudingliang = (TextView) helper.getView(R.id.item_caigoulianmeng_woyaodinghuo_yudingliang);
        TextView cankaobiaozhun = (TextView) helper.getView(R.id.item_caigoulianmeng_woyaodinghuo_cankaobiaozhun);
        CheckBox checkBox = (CheckBox) helper.getView(R.id.item_caigoulianmeng_woyaodinghuo_checkBox);

        helper.setText(R.id.item_caigoulianmeng_woyaodinghuo_title, item.getShopName());

        yudingliang.setText(item.getReservationNum());

        if (StringUtils.equals("1", item.getDiyShop())) {
            //自定义商品
            yudingliang.setText(item.getReservationNum());
            StringBuffer sb = new StringBuffer();

            for (int j = 0; j < item.getMeetingTypeList().size(); j++) {
                if (j == item.getMeetingTypeList().size() - 1) {
                    sb.append(item.getMeetingTypeList().get(j).getReferenceType());
                } else
                    sb.append(item.getMeetingTypeList().get(j).getReferenceType() + ",");
            }
            cankaobiaozhun.setText(sb);
            checkBox.setChecked(true);
        }

        helper.addOnClickListener(R.id.item_caigoulianmeng_woyaodinghuo_checkBox)
                .addOnClickListener(R.id.item_caigoulianmeng_woyaodinghuo_cankaobiaozhun);


        yudingliang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 1) {

                    if (!StringUtils.equals("请选择", cankaobiaozhun.getText().toString())) {
                        checkBox.setChecked(true);
                    }

                }else checkBox.setChecked(false);
            }
        });

        cankaobiaozhun.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!StringUtils.equals("请选择",s)) {

                    if (yudingliang.getText().length()>1) {
                        checkBox.setChecked(true);
                    }

                }else checkBox.setChecked(false);
            }
        });
    }
}
