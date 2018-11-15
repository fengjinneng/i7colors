package com.company.qcy.adapter.pengyouquan;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.bean.pengyouquan.DianzantouxiangBean;
import com.company.qcy.bean.pengyouquan.PengyouquanBean;

import java.util.ArrayList;
import java.util.List;

public class PengyouquanAdapter extends BaseQuickAdapter<PengyouquanBean, BaseViewHolder> {


    public PengyouquanAdapter(int layoutResId, @Nullable List<PengyouquanBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PengyouquanBean item) {


        ImageView headimg = (ImageView) helper.getView(R.id.item_pengyouquan_headimg);
        GlideUtils.loadCircleImage(mContext, ServerInfo.IMAGE + item.getPostUserPhoto(), headimg);

        helper.setText(R.id.item_pengyouquan_name, item.getPostUser());
        helper.setText(R.id.item_pengyouquan_content, item.getContent());

        setImage(helper, item);

        RecyclerView touxiangRecyclerview = (RecyclerView) helper.getView(R.id.item_pengyouquan_touxiang_recyclerview);
        RecyclerView messageRecyclerview = (RecyclerView) helper.getView(R.id.item_pengyouquan_message_recyclerview);

        List<DianzantouxiangBean> datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add(new DianzantouxiangBean());
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        touxiangRecyclerview.setLayoutManager(layoutManager);
        DianzantouxiangAdapter adapter = new DianzantouxiangAdapter(R.layout.item_pengyouquan_touxiang, datas);
        touxiangRecyclerview.setAdapter(adapter);





    }

    private void setImage(BaseViewHolder helper, PengyouquanBean item) {
        ImageView image1 = (ImageView) helper.getView(R.id.item_pengyouquan_img1);
        ImageView image2 = (ImageView) helper.getView(R.id.item_pengyouquan_img2);
        ImageView image3 = (ImageView) helper.getView(R.id.item_pengyouquan_img3);
        ImageView image4 = (ImageView) helper.getView(R.id.item_pengyouquan_img4);
        ImageView image5 = (ImageView) helper.getView(R.id.item_pengyouquan_img5);
        ImageView image6 = (ImageView) helper.getView(R.id.item_pengyouquan_img6);
        ImageView image7 = (ImageView) helper.getView(R.id.item_pengyouquan_img7);
        ImageView image8 = (ImageView) helper.getView(R.id.item_pengyouquan_img8);
        ImageView image9 = (ImageView) helper.getView(R.id.item_pengyouquan_img9);

        if (!StringUtils.isEmpty(item.getPic1())) {
            image1.setVisibility(View.VISIBLE);
            GlideUtils.loadImage(mContext, ServerInfo.IMAGE + item.getPic1(), image1);

            if (!StringUtils.isEmpty(item.getPic2())) {
                image2.setVisibility(View.VISIBLE);
                GlideUtils.loadImage(mContext, ServerInfo.IMAGE + item.getPic2(), image2);

                if (!StringUtils.isEmpty(item.getPic3())) {
                    image3.setVisibility(View.VISIBLE);
                    GlideUtils.loadImage(mContext, ServerInfo.IMAGE + item.getPic3(), image3);

                    if (!StringUtils.isEmpty(item.getPic4())) {
                        image4.setVisibility(View.VISIBLE);
                        GlideUtils.loadImage(mContext, ServerInfo.IMAGE + item.getPic4(), image4);
                        if (!StringUtils.isEmpty(item.getPic5())) {
                            image5.setVisibility(View.VISIBLE);
                            GlideUtils.loadImage(mContext, ServerInfo.IMAGE + item.getPic5(), image5);

                            if (!StringUtils.isEmpty(item.getPic6())) {
                                image6.setVisibility(View.VISIBLE);
                                GlideUtils.loadImage(mContext, ServerInfo.IMAGE + item.getPic6(), image6);

                                if (!StringUtils.isEmpty(item.getPic7())) {
                                    image7.setVisibility(View.VISIBLE);
                                    GlideUtils.loadImage(mContext, ServerInfo.IMAGE + item.getPic7(), image7);

                                    if (!StringUtils.isEmpty(item.getPic8())) {
                                        image8.setVisibility(View.VISIBLE);
                                        GlideUtils.loadImage(mContext, ServerInfo.IMAGE + item.getPic8(), image8);

                                        if (!StringUtils.isEmpty(item.getPic9())) {
                                            image9.setVisibility(View.VISIBLE);
                                            GlideUtils.loadImage(mContext, ServerInfo.IMAGE + item.getPic9(), image9);
                                        } else {
                                            image9.setVisibility(View.INVISIBLE);
                                        }

                                    } else {
                                        image8.setVisibility(View.INVISIBLE);
                                        image9.setVisibility(View.INVISIBLE);
                                    }

                                } else {

                                }

                            } else {
                                image6.setVisibility(View.INVISIBLE);
                            }

                        } else {
                            image5.setVisibility(View.INVISIBLE);
                            image6.setVisibility(View.INVISIBLE);
                        }

                    } else {

                    }


                } else {
                    image3.setVisibility(View.INVISIBLE);
                }


            } else {
                image2.setVisibility(View.INVISIBLE);
                image3.setVisibility(View.INVISIBLE);
            }

        }

    }
}
