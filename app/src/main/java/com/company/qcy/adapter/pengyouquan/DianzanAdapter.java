
package com.company.qcy.adapter.pengyouquan;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.MyCommonUtil;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.bean.pengyouquan.DianzantouxiangBean;
import com.company.qcy.bean.pengyouquan.PengyouquanBean;

import java.util.List;

public class DianzanAdapter extends BaseQuickAdapter<PengyouquanBean.LikeListBean, BaseViewHolder> {


    public DianzanAdapter(int layoutResId, @Nullable List<PengyouquanBean.LikeListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PengyouquanBean.LikeListBean item) {


        TextView name = (TextView) helper.getView(R.id.item_fragment_praise_name);
        helper.setText(R.id.item_fragment_praise_time, item.getCreatedAt());

        ImageView imageView = (ImageView) helper.getView(R.id.item_fragment_praise_head);

        helper.addOnClickListener(R.id.item_fragment_praise_head);

        MyCommonUtil.jiazaitouxiang(mContext,item.getLikeUserPhoto(),imageView);

        name.setText(item.getLikeUser());

        if(!StringUtils.isEmpty(item.getCreatedAtStamp())){
            helper.setText(R.id.item_fragment_praise_time, TimeUtils.millis2String(Long.parseLong(item.getCreatedAtStamp())));
        }

    }
}
