package com.company.qcy.adapter.pengyouquan;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.bean.pengyouquan.CommentConfig;
import com.company.qcy.bean.pengyouquan.CommentItem;
import com.company.qcy.bean.pengyouquan.PengyouquanBean;
import com.company.qcy.bean.pengyouquan.PhotoInfo;
import com.company.qcy.bean.pengyouquan.User;
import com.company.qcy.fragment.home.XiaoxiFragment;
import com.company.qcy.widght.pengyouquan.CommentListView;
import com.company.qcy.widght.pengyouquan.ExpandTextView;
import com.company.qcy.widght.pengyouquan.MultiImageView;
import com.company.qcy.widght.pengyouquan.PileLayout;

import java.util.ArrayList;
import java.util.List;

public class PengyouquanAdapter extends BaseQuickAdapter<PengyouquanBean, BaseViewHolder> {

    XiaoxiFragment fragment;

    public PengyouquanAdapter(int layoutResId, @Nullable List<PengyouquanBean> data,XiaoxiFragment fragment) {
        super(layoutResId, data);
        this.fragment =fragment;
    }

    @Override
    protected void convert(BaseViewHolder helper, PengyouquanBean item) {


        ImageView headimg = (ImageView) helper.getView(R.id.item_pengyouquan_headimg);
        if (!StringUtils.isEmpty(item.getPostUserPhoto())) {
            GlideUtils.loadCircleImage(mContext, ServerInfo.IMAGE + item.getPostUserPhoto(), headimg);
        }

        helper.setText(R.id.item_pengyouquan_name, item.getPostUser()).addOnClickListener(R.id.snsBtn);
        ExpandTextView expandTextView = (ExpandTextView) helper.getView(R.id.item_pengyouquan_content);
        CharSequence cs = item.getContent();
        expandTextView.setText(cs);

        MultiImageView multiImageView = (MultiImageView) helper.getView(R.id.item_pengyouquan_multiImageView);

        List<PhotoInfo> photoInfos = new ArrayList<>();
        if (!StringUtils.isEmpty(item.getPic1())) {
            photoInfos.add(new PhotoInfo(item.getPic1()));
        }
        if (!StringUtils.isEmpty(item.getPic2())) {
            photoInfos.add(new PhotoInfo(item.getPic2()));
        }
        if (!StringUtils.isEmpty(item.getPic3())) {
            photoInfos.add(new PhotoInfo(item.getPic3()));
        }
        if (!StringUtils.isEmpty(item.getPic4())) {
            photoInfos.add(new PhotoInfo(item.getPic4()));
        }
        if (!StringUtils.isEmpty(item.getPic5())) {
            photoInfos.add(new PhotoInfo(item.getPic5()));
        }
        if (!StringUtils.isEmpty(item.getPic6())) {
            photoInfos.add(new PhotoInfo(item.getPic6()));
        }
        if (!StringUtils.isEmpty(item.getPic7())) {
            photoInfos.add(new PhotoInfo(item.getPic7()));
        }
        if (!StringUtils.isEmpty(item.getPic8())) {
            photoInfos.add(new PhotoInfo(item.getPic8()));
        }
        if (!StringUtils.isEmpty(item.getPic9())) {
            photoInfos.add(new PhotoInfo(item.getPic9()));
        }

        multiImageView.setList(photoInfos);

        PileLayout pileLayout = (PileLayout) helper.getView(R.id.pileLayout);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        pileLayout.removeAllViews();
        if (!ObjectUtils.isEmpty(item.getLikeList())) {
            for (int i = 0; i < item.getLikeList().size(); i++) {
                ImageView imageView = (ImageView) inflater.inflate(R.layout.item_pengyouquan_praise, pileLayout, false);
                GlideUtils.loadCircleImage(mContext, ServerInfo.IMAGE + item.getLikeList().get(i).getLikeUserPhoto(), imageView);
                pileLayout.addView(imageView);
                int finalI = i;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showShort("点击了第" + finalI + "长图片");
                    }
                });
            }

        }

        CommentListView commentListView = ((CommentListView) helper.getView(R.id.commentList));
        List<PengyouquanBean.CommentListBean> datas = new ArrayList<>();


        for (int i = 0; i < item.getCommentList().size(); i++) {
            PengyouquanBean.CommentListBean commentItem = new PengyouquanBean.CommentListBean();
            commentItem.setContent(item.getCommentList().get(i).getContent());
            commentItem.setCommentUser(item.getCommentList().get(i).getCommentUser());
            commentItem.setByCommentUser(item.getCommentList().get(i).getByCommentUser());
            datas.add(commentItem);
        }
        commentListView.setDatas(datas);
        commentListView.setOnItemClickListener(new CommentListView.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                fragment.doThing();

            }
        });

    }

}