package com.company.qcy.adapter.pengyouquan;

import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.bean.pengyouquan.ImageBean;

import java.util.List;

public class FabupengyouquanAdapter extends BaseQuickAdapter<ImageBean, BaseViewHolder> {


    public FabupengyouquanAdapter(int layoutResId, @Nullable List<ImageBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ImageBean item) {
        helper.addOnClickListener(R.id.item_fabupengyouquan_delete);

        ImageView imageView = (ImageView) helper.getView(R.id.item_fabupengyouquan_img);
        ImageView delete = (ImageView) helper.getView(R.id.item_fabupengyouquan_delete);
        imageView.setLayoutParams(new ConstraintLayout.LayoutParams(ScreenUtils.getScreenWidth() / 4, ScreenUtils.getScreenWidth() /4));
        if (StringUtils.equals("+", item.getUri().toString())) {
            imageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.pyq_jiahao));
            delete.setVisibility(View.GONE);
        } else {
            Glide.with(mContext).load(item.getUri()).into(imageView);
            delete.setVisibility(View.VISIBLE);
        }

    }
}
