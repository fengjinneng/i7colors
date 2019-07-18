package com.company.qcy.adapter.kaifangshangcheng;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.bean.kaifangshangcheng.DianpuliebiaoBean;

import java.util.ArrayList;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;

public class KaifangshangchengRecyclerviewAdapter extends
        BaseQuickAdapter<DianpuliebiaoBean, BaseViewHolder> {
    public KaifangshangchengRecyclerviewAdapter(int layoutResId, @Nullable List<DianpuliebiaoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DianpuliebiaoBean item) {
        helper.setText(R.id.item_kaifangshangcheng_company, item.getCompany().getCompanyName());
        TagContainerLayout layout = helper.getView(R.id.kaifangshangcheng_tag_container);
        List<String> tags = new ArrayList<>();
        if (!ObjectUtils.isEmpty(item.getBusinessList())) {
            for (int i = 0; i < item.getBusinessList().size(); i++) {
                tags.add(item.getBusinessList().get(i).getValue());
            }
            layout.setTags(tags);
        }

        ImageView imageView = (ImageView) helper.getView(R.id.item_kaifangshangcheng_img);
//        GlideUtils.loadImage(mContext, ServerInfo.IMAGE + item.getLogo(), imageView);


        Glide.with(mContext).load(ServerInfo.IMAGE + item.getLogo()).into(imageView);


        if (StringUtils.isEmpty(item.getPhone())) {

            if (StringUtils.isEmpty(item.getCompany().getTel())) {
                helper.setText(R.id.item_kaifangshangcheng_phone, "暂无联系方式");
            } else {
                helper.setText(R.id.item_kaifangshangcheng_phone, item.getCompany().getTel());
            }

        } else {
            helper.setText(R.id.item_kaifangshangcheng_phone, item.getPhone());
        }

        if (!StringUtils.isEmpty(item.getCompany().getProvinceName())) {
            helper.setText(R.id.item_kaifangshangcheng_address, item.getCompany().getProvinceName());
            if (!StringUtils.isEmpty(item.getCompany().getCityName())) {
                helper.setText(R.id.item_kaifangshangcheng_address, item.getCompany().getProvinceName() + " " + item.getCompany().getCityName());
            }
        } else {
            helper.setText(R.id.item_kaifangshangcheng_address, "暂无地址");
        }

    }
}


