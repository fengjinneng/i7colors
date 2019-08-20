package com.company.qcy.huodong.caigoulianmeng2.adapter;

import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.huodong.caigoulianmeng2.bean.ProductBean;

import java.util.List;

public class DinghuoAdapter extends BaseQuickAdapter<ProductBean, BaseViewHolder> {


    public DinghuoAdapter(int layoutResId, @Nullable List<ProductBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductBean item) {

        CheckBox checkBox = (CheckBox) helper.getView(R.id.item_caigoulianmeng_woyaodinghuo_checkBox);
        TextView name = (TextView) helper.getView(R.id.item_caigoulianmeng_woyaodinghuo_title);
        name.setText(item.getShopName());

        EditText yudingliang = (EditText) helper.getView(R.id.item_caigoulianmeng_woyaodinghuo_yudingliang);

        TextView cankaobiaozhun = (TextView) helper.getView(R.id.item_caigoulianmeng_woyaodinghuo_cankaobiaozhun);

        helper.addOnClickListener(R.id.item_caigoulianmeng_woyaodinghuo_checkBox);

        if (StringUtils.equals("1", item.getDiyShop())) {
            //自定义商品
            helper.getView(R.id.item_caigoulianmeng_woyaodinghuo_zidingyishangpin).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.item_caigoulianmeng_woyaodinghuo_zidingyishangpin).setVisibility(View.GONE);
        }

        helper.addOnClickListener(R.id.item_caigoulianmeng_woyaodinghuo_cankaobiaozhun);
        if (StringUtils.equals("1", item.getDiyShop())) {
            item.setChecked(true);
            checkBox.setClickable(true);
            checkBox.setEnabled(true);
            checkBox.setChecked(true);
            item.setReservationNum(item.getReservationNum());
            item.setReferenceType(item.getReferenceType());
            yudingliang.setText(item.getReservationNum());
            cankaobiaozhun.setText(item.getReferenceType());
        }

        yudingliang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //删除.后面超过两位的数字
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        yudingliang.setText(s);
                        yudingliang.setSelection(s.length());
                    }
                }

                //如果.在起始位置,则起始位置自动补0
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    yudingliang.setText(s);
                    yudingliang.setSelection(2);
                }

                //如果起始位置为0并且第二位跟的不是".",则无法后续输入
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        yudingliang.setText(s.subSequence(0, 1));
                        yudingliang.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 1) {
                    if (!StringUtils.equals("请选择", cankaobiaozhun.getText().toString())) {
                        item.setChecked(true);
                        item.setReservationNum(yudingliang.getText().toString());
                        item.setReferenceType(cankaobiaozhun.getText().toString());
                        checkBox.setClickable(true);
                        checkBox.setEnabled(true);
                        checkBox.setChecked(true);
                    }

                } else {
                    item.setChecked(false);
                    checkBox.setChecked(false);
                }
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
                if (!StringUtils.equals("请选择", s)) {

                    if (yudingliang.getText().length() >= 1) {
                        item.setChecked(true);
                        item.setReservationNum(yudingliang.getText().toString());
                        item.setReferenceType(cankaobiaozhun.getText().toString());
                        checkBox.setClickable(true);
                        checkBox.setEnabled(true);
                        checkBox.setChecked(true);
                    }

                } else {
                    item.setChecked(false);
                    checkBox.setChecked(false);
                }
            }
        });
    }
}
