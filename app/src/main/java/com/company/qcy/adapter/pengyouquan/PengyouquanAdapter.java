package com.company.qcy.adapter.pengyouquan;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.Utils.CommentDialog;
import com.company.qcy.Utils.DateUtil;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.Utils.UserUtil;
import com.company.qcy.bean.pengyouquan.PengyouquanBean;
import com.company.qcy.bean.pengyouquan.PhotoInfo;
import com.company.qcy.ui.activity.pengyouquan.ImagePagerActivity;
import com.company.qcy.ui.activity.pengyouquan.PersonInfoActivity;
import com.company.qcy.ui.activity.user.LoginActivity;
import com.company.qcy.widght.pengyouquan.CommentListView;
import com.company.qcy.widght.pengyouquan.ExpandTextView;
import com.company.qcy.widght.pengyouquan.MultiImageView;
import com.company.qcy.widght.pengyouquan.PileLayout;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PengyouquanAdapter extends BaseQuickAdapter<PengyouquanBean, BaseViewHolder> {

    private Handler handler;

    private List<PengyouquanBean> mDatas;

    public PengyouquanAdapter(int layoutResId, @Nullable List<PengyouquanBean> data, Handler handler) {
        super(layoutResId, data);
        this.handler = handler;
        this.mDatas = data;
    }

    public static Bitmap getNetVideoBitmap(String videoUrl) {
        Bitmap bitmap = null;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据url获取缩略图
            retriever.setDataSource(videoUrl, new HashMap());
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }

    @Override
    protected void convert(BaseViewHolder helper, PengyouquanBean item) {

        ConstraintLayout lianjieLayout = (ConstraintLayout) helper.getView(R.id.item_pengyouquan_lianjie_layout);
        ImageView lianjieImage = (ImageView) helper.getView(R.id.item_pengyouquan_lianjie_image);
        TextView lianjieTitle = (TextView) helper.getView(R.id.item_pengyouquan_lianjie_title);
        if (!ObjectUtils.isEmpty(item.getShareBean())) {
            lianjieLayout.setVisibility(View.VISIBLE);
            GlideUtils.loadImage(mContext, ServerInfo.IMAGE + item.getShareBean().getPic(), lianjieImage);
            lianjieTitle.setText(item.getShareBean().getTitle());
            helper.addOnClickListener(R.id.item_pengyouquan_lianjie_layout);
        } else {
            lianjieLayout.setVisibility(View.GONE);
        }
        TextView address = (TextView) helper.getView(R.id.item_pengyouquan_address);
        helper.addOnClickListener(R.id.item_pengyouquan_address);
        TextView huati = (TextView) helper.getView(R.id.item_pengyouquan_huati);
        helper.addOnClickListener(R.id.item_pengyouquan_huati);
        if (StringUtils.isEmpty(item.getLocationTitle())) {
            address.setVisibility(View.GONE);
        } else {
            address.setText(item.getLocationTitle());
            address.setVisibility(View.VISIBLE);
        }

        if (ObjectUtils.isEmpty(item.getTopic())) {
            huati.setVisibility(View.GONE);
        } else {
            huati.setText("#" + item.getTopic().getTopicList().get(0).getTitle() + "#");
            huati.setVisibility(View.VISIBLE);
        }

        helper.addOnClickListener(R.id.item_pengyouquan_detail);
        inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        ImageView headimg = (ImageView) helper.getView(R.id.item_pengyouquan_headimg);
        helper.addOnClickListener(R.id.item_pengyouquan_headimg);
        if (!StringUtils.isEmpty(item.getPostUserPhoto())) {

            if (item.getPostUserPhoto().startsWith("http")) {

                GlideUtils.loadCircleImage(mContext, item.getPostUserPhoto(), headimg);

            } else {
                GlideUtils.loadCircleImage(mContext, ServerInfo.IMAGE + item.getPostUserPhoto(), headimg);

            }
        } else {
            headimg.setImageResource(R.mipmap.morentouxiang);
        }
        TextView time = (TextView) helper.getView(R.id.timeTv);
//        time.setText(TimeUtils.millis2String(Long.parseLong(item.getCreatedAtStamp())).substring(0, 10));
        String friendlytime = DateUtil.getFriendlytime(new Date(Long.parseLong(item.getCreatedAtStamp())));
        time.setText(friendlytime);

        if (StringUtils.equals("1", item.getIsCompany())) {
            helper.getView(R.id.item_pengyouquan_companyname).setVisibility(View.VISIBLE);
            helper.setText(R.id.item_pengyouquan_companyname, item.getCompanyName());
        } else {
            if (StringUtils.equals("1", item.getIsDyeV())) {
                helper.getView(R.id.item_pengyouquan_companyname).setVisibility(View.VISIBLE);
                helper.setText(R.id.item_pengyouquan_companyname, item.getDyeVName());
            } else {
                helper.getView(R.id.item_pengyouquan_companyname).setVisibility(View.GONE);
            }
        }

        TextView renzheng = (TextView) helper.getView(R.id.item_pengyouquan_renzheng);

        if (StringUtils.equals("1", item.getIsCompany())) {
            helper.getView(R.id.item_pengyouquan_fans_bigv).setVisibility(View.VISIBLE);
            renzheng.setText("已认证");
            renzheng.setTextColor(mContext.getResources().getColor(R.color.chunhongse));
            renzheng.setBackground(mContext.getResources().getDrawable(R.drawable.backgroung_pengyouquan_yirenzheng));
            renzheng.setPadding(10, 0, 10, 0);
        } else {
            if (StringUtils.equals("1", item.getIsDyeV())) {
                helper.getView(R.id.item_pengyouquan_fans_bigv).setVisibility(View.VISIBLE);
                renzheng.setText("已认证");
                renzheng.setTextColor(mContext.getResources().getColor(R.color.chunhongse));
                renzheng.setBackground(mContext.getResources().getDrawable(R.drawable.backgroung_pengyouquan_yirenzheng));
                renzheng.setPadding(10, 0, 10, 0);

            } else {
                helper.getView(R.id.item_pengyouquan_fans_bigv).setVisibility(View.GONE);
                renzheng.setText("未认证");
                renzheng.setTextColor(mContext.getResources().getColor(R.color.putongwenben));
                renzheng.setBackground(mContext.getResources().getDrawable(R.drawable.background_pengyouquan_weirenzheng));
                renzheng.setPadding(10, 0, 10, 0);
            }
        }
        helper.setText(R.id.item_pengyouquan_name, item.getPostUser());
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(item.getPostUser() + " .");
        //大佬等级1
        if (StringUtils.equals("1", item.getBossLevel())) {

            Drawable level1 = mContext.getResources().getDrawable(R.mipmap.level_one);
            level1.setBounds(0, 0, level1.getIntrinsicWidth(), level1.getIntrinsicHeight());
            ImageSpan span1 = new ImageSpan(level1, ImageSpan.ALIGN_BASELINE);
            spannableStringBuilder.setSpan(span1,
                    spannableStringBuilder.length() - 1, spannableStringBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            helper.setText(R.id.item_pengyouquan_name, spannableStringBuilder);

        }
        if (StringUtils.equals("2", item.getBossLevel())) {
            Drawable level2 = mContext.getResources().getDrawable(R.mipmap.level_two);
            level2.setBounds(0, 0, level2.getIntrinsicWidth(), level2.getIntrinsicHeight());

            ImageSpan span2 = new ImageSpan(level2, ImageSpan.ALIGN_BASELINE);

            spannableStringBuilder.setSpan(span2,
                    spannableStringBuilder.length() - 1, spannableStringBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            helper.setText(R.id.item_pengyouquan_name, spannableStringBuilder);

        }
        if (StringUtils.equals("3", item.getBossLevel())) {
            Drawable level3 = mContext.getResources().getDrawable(R.mipmap.level_three);
            level3.setBounds(0, 0, level3.getIntrinsicWidth(), level3.getIntrinsicHeight());
            ImageSpan span3 = new ImageSpan(level3, ImageSpan.ALIGN_BASELINE);
            spannableStringBuilder.setSpan(span3,
                    spannableStringBuilder.length() - 1, spannableStringBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            helper.setText(R.id.item_pengyouquan_name, spannableStringBuilder);
        }

        TextView deleteBtn = (TextView) helper.getView(R.id.deleteBtn);
        helper.addOnClickListener(R.id.deleteBtn);
        if (StringUtils.equals("1", item.getIsCharger())) {
            deleteBtn.setVisibility(View.VISIBLE);
        } else {
            deleteBtn.setVisibility(View.GONE);
        }

        helper.setText(R.id.item_pengyouquan_fans_number, item.getDyeFollowCount());

        helper.addOnClickListener(R.id.item_pengyouquan_guanzhu);

        if (UserUtil.isLogin()) {
            if (StringUtils.equals("0", item.getIsCharger())) {
                helper.getView(R.id.item_pengyouquan_guanzhu).setVisibility(View.VISIBLE);
                if (StringUtils.equals("1", item.getIsFollow())) {
                    helper.setBackgroundRes(R.id.item_pengyouquan_guanzhu, R.mipmap.yiguanzhu);
                    helper.setText(R.id.item_pengyouquan_guanzhu, "");
                } else {
                    helper.setBackgroundRes(R.id.item_pengyouquan_guanzhu, R.drawable.background_pengyouquan_weirenzheng);
                    helper.setText(R.id.item_pengyouquan_guanzhu, "+关注");
                }
            } else {
                helper.getView(R.id.item_pengyouquan_guanzhu).setVisibility(View.GONE);
            }
        } else {
            helper.getView(R.id.item_pengyouquan_guanzhu).setVisibility(View.GONE);
        }

        helper.addOnClickListener(R.id.snsBtn);
        ExpandTextView expandTextView = (ExpandTextView) helper.getView(R.id.item_pengyouquan_content);
        CharSequence cs = item.getContent();
        expandTextView.setText(cs);

        MultiImageView multiImageView = (MultiImageView) helper.getView(R.id.item_pengyouquan_multiImageView);

        LinearLayout digCommentBody = (LinearLayout) helper.getView(R.id.digCommentBody);
        if (ObjectUtils.isEmpty(item.getCommentList()) && ObjectUtils.isEmpty(item.getLikeList())) {
            digCommentBody.setVisibility(View.GONE);
        } else {
            digCommentBody.setVisibility(View.VISIBLE);
        }

        ConstraintLayout chakangengduo = (ConstraintLayout) helper.getView(R.id.item_pengyouquan_chakangengduo);
        helper.addOnClickListener(R.id.item_pengyouquan_chakangengduo);
        helper.addOnClickListener(R.id.item_pengyouquan_shipin_layout);
        ImageView shipinPlayImage = helper.getView(R.id.item_pengyouquan_shipin_img);
        ConstraintLayout shipinLayout = (ConstraintLayout) helper.getView(R.id.item_pengyouquan_shipin_layout);
        if (!StringUtils.isEmpty(item.getUrl())) {
            shipinLayout.setVisibility(View.VISIBLE);
            multiImageView.setVisibility(View.GONE);
//          jzvdStd.setUp(ServerInfo.IMAGE+item.getUrl(), "", JzvdStd.SCREEN_WINDOW_LIST);
//          jzvdStd.thumbImageView.setImageBitmap(getNetVideoBitmap(ServerInfo.IMAGE+item.getUrl()));
//          Glide.with(mContext).load("http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png").into(shipinPlayImage);
            GlideUtils.loadImageFitCenter(mContext, ServerInfo.IMAGE + item.getVideoPicUrl(), shipinPlayImage);

        } else {
            shipinLayout.setVisibility(View.GONE);
            multiImageView.setVisibility(View.VISIBLE);
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
            multiImageView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());

                    List<String> photoUrls = new ArrayList<String>();
                    for (PhotoInfo photoInfo : photoInfos) {
                        photoUrls.add(photoInfo.url);
                    }
                    ImagePagerActivity.startImagePagerActivity(mContext, photoUrls, position, imageSize);

                }
            });
        }

        PileLayout pileLayout = (PileLayout) helper.getView(R.id.pileLayout);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        pileLayout.removeAllViews();
        if (!ObjectUtils.isEmpty(item.getLikeList())) {
            ImageView imageView1 = (ImageView) inflater.inflate(R.layout.item_pengyouquan_praise, pileLayout, false);
            imageView1.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.dianzan_red));
            imageView1.setScaleType(ImageView.ScaleType.CENTER);
            pileLayout.addView(imageView1);
            if (item.getLikeList().size() < 20) {
                for (int i = 0; i < item.getLikeList().size(); i++) {
                    int temp = i;
                    ImageView imageView = (ImageView) inflater.inflate(R.layout.item_pengyouquan_praise, pileLayout, false);
                    if (!StringUtils.isEmpty(item.getLikeList().get(i).getLikeUserPhoto())) {
                        if (item.getLikeList().get(i).getLikeUserPhoto().startsWith("http")) {

                            GlideUtils.loadCircleImage(mContext, item.getLikeList().get(i).getLikeUserPhoto(), imageView);

                        } else {
                            GlideUtils.loadCircleImage(mContext, ServerInfo.IMAGE + item.getLikeList().get(i).getLikeUserPhoto(), imageView);

                        }
                    }else {
                        imageView.setImageResource(R.mipmap.morentouxiang);
                    }

                    pileLayout.addView(imageView);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(mContext, PersonInfoActivity.class);
                            intent.putExtra("userId", item.getLikeList().get(temp).getUserId());
                            ActivityUtils.startActivity(intent);
                        }
                    });
                }
            } else {
                for (int i = 0; i < 20; i++) {
                    int temp = i;
                    ImageView imageView = (ImageView) inflater.inflate(R.layout.item_pengyouquan_praise, pileLayout, false);
                    if (!StringUtils.isEmpty(item.getLikeList().get(i).getLikeUserPhoto())) {
                        if (item.getLikeList().get(i).getLikeUserPhoto().startsWith("http")) {

                            GlideUtils.loadCircleImage(mContext, item.getLikeList().get(i).getLikeUserPhoto(), imageView);

                        } else {
                            GlideUtils.loadCircleImage(mContext, ServerInfo.IMAGE + item.getLikeList().get(i).getLikeUserPhoto(), imageView);

                        }
                    }else {
                        imageView.setImageResource(R.mipmap.morentouxiang);
                    }
                    pileLayout.addView(imageView);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(mContext, PersonInfoActivity.class);
                            intent.putExtra("userId", item.getLikeList().get(temp).getUserId());
                            ActivityUtils.startActivity(intent);
                        }
                    });
                }
            }


        }

        CommentListView commentListView = ((CommentListView) helper.getView(R.id.commentList));

        if (ObjectUtils.isEmpty(item.getCommentList())) {
            commentListView.setDatas(new ArrayList<>());
        } else {
            List<PengyouquanBean.CommentListBean> commentListBeans = new ArrayList<>();
            if (item.getCommentList().size() > 10) {
                chakangengduo.setVisibility(View.VISIBLE);
                for (int i = 0; i < 10; i++) {
//                    PengyouquanBean.CommentListBean commentItem = new PengyouquanBean.CommentListBean();
//                    commentItem  = item.getCommentList().get(i);
//                    commentItem.setContent(item.getCommentList().get(i).getContent());
//                    commentItem.setCommentUser(item.getCommentList().get(i).getCommentUser());
//                    commentItem.setByCommentUser(item.getCommentList().get(i).getByCommentUser());
//                    commentItem.setId(item.getId());
//                    commentItem.setIsCharger(item.getCommentList().get(i).getIsCharger());
//                    commentItem.setUserId(item.getCommentList().get(i).getUserId());
//                    commentItem.setCreatedAtStamp(item.getCommentList().get(i).getCreatedAtStamp());
                    commentListBeans.add(item.getCommentList().get(i));
                }
            } else {
                chakangengduo.setVisibility(View.GONE);
                for (int i = 0; i < item.getCommentList().size(); i++) {
//                    PengyouquanBean.CommentListBean commentItem = new PengyouquanBean.CommentListBean();
//                    commentItem.setContent(item.getCommentList().get(i).getContent());
//                    commentItem.setCommentUser(item.getCommentList().get(i).getCommentUser());
//                    commentItem.setByCommentUser(item.getCommentList().get(i).getByCommentUser());
//                    commentItem.setId(item.getId());
//                    commentItem.setIsCharger(item.getCommentList().get(i).getIsCharger());
//                    commentItem.setUserId(item.getCommentList().get(i).getUserId());
//                    commentItem.setCreatedAtStamp(item.getCommentList().get(i).getCreatedAtStamp());
                    commentListBeans.add(item.getCommentList().get(i));
                }
            }


            commentListView.setDatas(commentListBeans);
            commentListView.setOnItemClickListener(new CommentListView.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {

                    if (!UserUtil.isLogin()) {
                        ActivityUtils.startActivity(LoginActivity.class);
                        return;
                    }
                    if (StringUtils.equals("1", item.getCommentList().get(position).getIsCharger())) {
                        CommentDialog dialog = new CommentDialog(mContext, item.getCommentList().get(position),
                                item.getCommentList().get(position).getId(), helper.getAdapterPosition());
                        dialog.show();
                    } else {
                        handler.sendEmptyMessageDelayed(0, 200);
                        showPop(item.getId(), item.getCommentList().get(position).getId(),
                                commentListBeans.get(position).getCommentUser(), item, helper.getAdapterPosition());

                    }
                }
            });
        }
    }


    private PopupWindow popupWindow;
    private EditText inputComment;
    private TextView btn_submit;
    private InputMethodManager inputMethodManager;


    @SuppressLint("WrongConstant")
    public void showPop(Long id, Long parentId, String commentUser, PengyouquanBean bean,
                        int position) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.pengyouquan_huifu_layout, null);

        popupWindow = new PopupWindow(inflate, LinearLayout.LayoutParams.FILL_PARENT, 100, true);

        btn_submit = (TextView) inflate.findViewById(R.id.tv_confirm);
        //popupwindow弹出时的动画		popWindow.setAnimationStyle(R.style.popupWindowAnimation);

        // 使其聚集 ，要想监听菜单里控件的事件就必须要调用此方法

        popupWindow.setFocusable(true);

        // 设置允许在外点击消失

        popupWindow.setOutsideTouchable(false);

        // 设置背景，这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景

        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        //软键盘不会挡着popupwindow

        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        //设置菜单显示的位置

        popupWindow.showAtLocation(inflate, Gravity.BOTTOM, 0, 0);

        //监听菜单的关闭事件

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override

            public void onDismiss() {

            }

        });

        //监听触屏事件

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE)
                    popupWindow.dismiss();
                inputMethodManager.hideSoftInputFromWindow(inputComment.getWindowToken(), 0);
                return false;
            }

        });


        inputComment = inflate.findViewById(R.id.et_discuss);
        inputComment.setFocusable(true);
        inputComment.setFocusableInTouchMode(true);
        inputComment.setHint("回复 : " + commentUser);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!UserUtil.isLogin()) {
                    ActivityUtils.startActivity(LoginActivity.class);
                    return;
                }

                String comment1 = inputComment.getText().toString().trim();
                if (StringUtils.isEmpty(comment1)) {
                    ToastUtils.showShort("不能发表空的评论");
                    return;
                }
                //调用提交评论接口
                saveDiscuss(comment1, id, parentId, bean, position);
                inputMethodManager.hideSoftInputFromWindow(inputComment.getWindowToken(), 0);
                inputComment.setText("");
                popupWindow.dismiss();
            }
        });

    }

    private void saveDiscuss(String comment1, Long id, Long parentId, PengyouquanBean
            pengyouquanBean, int position) {

        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.FABIAOPINGLUN)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("dyeId", id)
                .params("content", comment1)
                .params("parentId", parentId)
                .params("token", SPUtils.getInstance().getString("token"));


        DialogStringCallback stringCallback = new DialogStringCallback((Activity) mContext) {
            @Override
            public void onSuccess(Response<String> response) {
                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");

                        if (StringUtils.equals(jsonObject.getString("code"), mContext.getResources().getString(R.string.success))) {
                            LogUtils.e("FABIAOPINGLUN", jsonObject.getString("data"));
                            JSONObject data = jsonObject.getJSONObject("data");
                            PengyouquanBean.CommentListBean commentListBean = data.toJavaObject(PengyouquanBean.CommentListBean.class);
                            List<PengyouquanBean.CommentListBean> commentList = pengyouquanBean.getCommentList();
                            commentList.add(commentListBean);
                            pengyouquanBean.setCommentList(commentList);
                            notifyItemChanged(position);
                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), mContext.getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(mContext, request, this);
                            return;
                        }
                        ToastUtils.showShort(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                ToastUtils.showShort(mContext.getResources().getString(R.string.NETEXCEPTION));
            }
        };
        request.execute(stringCallback);


    }

}