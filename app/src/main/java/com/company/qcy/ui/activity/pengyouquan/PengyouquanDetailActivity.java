package com.company.qcy.ui.activity.pengyouquan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.MyCommonUtil;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.share.ShareUtil;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.Utils.UserUtil;
import com.company.qcy.adapter.BaseViewpageAdapter;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.bean.pengyouquan.PengyouquanBean;
import com.company.qcy.bean.pengyouquan.PhotoInfo;
import com.company.qcy.fragment.pengyouquan.PinglunFragment;
import com.company.qcy.fragment.pengyouquan.PraiseFragment;
import com.company.qcy.ui.activity.chanyezixun.ZixunxiangqingActivity;
import com.company.qcy.ui.activity.user.LoginActivity;
import com.company.qcy.widght.pengyouquan.MultiImageView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/friendcircle/friendcircleDetail")
public class PengyouquanDetailActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private ImageView mActivityPengyouquanDetailImg;
    /**
     * 中国印染行业协会会长 陈志华
     */
    private TextView mActivityPengyouquanDetailName;
    /**
     * 2018-02-15 10:52
     */
    private TextView mActivityPengyouquanDetailTime;
    /**
     */
    private TextView mActivityPengyouquanDetailContent;
    private MultiImageView mActivityPengyouquanDetailMultiImageView;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private TabLayout mTabLayout;
    private AppBarLayout mAppbar;
    private ViewPager mViewpager;
    private PengyouquanBean pengyouquanBean;
    private TextView mToolbarText;

    @Autowired
    public Long id;
    private ImageView mActivityPengyouquanDetailBigv;
    private TextView mActivityPengyouquanDetailCompanyname;
    private ImageView mActivityPengyouquanDetailPlay;
    private ConstraintLayout mActivityPengyouquanDetailVideolayout;
    private ImageView mActivityPengyouquanDetailVideoimg;
    private ConstraintLayout mFragmentPinglunPinglun;
    private ConstraintLayout mFragmentPinglunDianzan;
    /**
     * 点赞
     */
    private TextView mFragmentPinglunDianzanText;


    //由于我的消息页面过来，判断消息是否已读
    private String pinglunId;
    private String dianzanId;
    private String aiteId;
    private String from;
    private ImageView mActivityPengyouquanDetailLianjieImg;
    private TextView mActivityPengyouquanDetailLianjieTitle;
    private TextView mActivityPengyouquanDetailLianjieContent;
    private ConstraintLayout mActivityPengyouquanDetailLianjieLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengyouquan_detail);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        Uri data = getIntent().getData();
        //不为空说明是外部网页传过来的
        if (!ObjectUtils.isEmpty(data)) {
            id = Long.parseLong(data.getQueryParameter("id"));
        } else id = getIntent().getLongExtra("id", 0);

        from = getIntent().getStringExtra("from");
        if (!StringUtils.isEmpty(from)) {
            switch (from) {
                case "pinglun":
                    pinglunId = getIntent().getStringExtra("pinlunId");
                    break;
                case "dianzan":
                    dianzanId = getIntent().getStringExtra("dianzanId");
                    break;
                case "aite":
                    aiteId = getIntent().getStringExtra("aiteId");
                    break;

            }
        }
        initView();

    }

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mToolbarTitle.setText("朋友圈详情");
        mActivityPengyouquanDetailImg = (ImageView) findViewById(R.id.activity_pengyouquan_detail_img);
        mActivityPengyouquanDetailName = (TextView) findViewById(R.id.activity_pengyouquan_detail_name);
        mActivityPengyouquanDetailTime = (TextView) findViewById(R.id.activity_pengyouquan_detail_time);
        mActivityPengyouquanDetailContent = (TextView) findViewById(R.id.activity_pengyouquan_detail_content);
        mActivityPengyouquanDetailMultiImageView = (MultiImageView) findViewById(R.id.activity_pengyouquan_detail_multiImageView);
        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mAppbar = (AppBarLayout) findViewById(R.id.appbar);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);
        mToolbarTitle.setOnClickListener(this);
        mToolbarText = (TextView) findViewById(R.id.toolbar_text);
        mToolbarText.setOnClickListener(this);
        mToolbarText.setText("分享");
        mToolbarText.setVisibility(View.VISIBLE);
        initToolBarData(id);
        addData();
        mActivityPengyouquanDetailBigv = (ImageView) findViewById(R.id.activity_pengyouquan_detail_bigv);
        mActivityPengyouquanDetailCompanyname = (TextView) findViewById(R.id.activity_pengyouquan_detail_companyname);
        mActivityPengyouquanDetailPlay = (ImageView) findViewById(R.id.activity_pengyouquan_detail_play);
        mActivityPengyouquanDetailPlay.setOnClickListener(this);
        mActivityPengyouquanDetailVideolayout = (ConstraintLayout) findViewById(R.id.activity_pengyouquan_detail_videolayout);
        mActivityPengyouquanDetailVideoimg = (ImageView) findViewById(R.id.activity_pengyouquan_detail_videoimg);
        mFragmentPinglunPinglun = (ConstraintLayout) findViewById(R.id.fragment_pinglun_pinglun);
        mFragmentPinglunPinglun.setOnClickListener(this);
        mFragmentPinglunDianzan = (ConstraintLayout) findViewById(R.id.fragment_pinglun_dianzan);
        mFragmentPinglunDianzan.setOnClickListener(this);
        mFragmentPinglunDianzanText = (TextView) findViewById(R.id.fragment_pinglun_dianzan_text);
        mActivityPengyouquanDetailLianjieImg = (ImageView) findViewById(R.id.activity_pengyouquan_detail_lianjie_img);
        mActivityPengyouquanDetailLianjieTitle = (TextView) findViewById(R.id.activity_pengyouquan_detail_lianjie_title);
        mActivityPengyouquanDetailLianjieContent = (TextView) findViewById(R.id.activity_pengyouquan_detail_lianjie_content);
        mActivityPengyouquanDetailLianjieLayout = (ConstraintLayout) findViewById(R.id.activity_pengyouquan_detail_lianjie_layout);
        mActivityPengyouquanDetailLianjieLayout.setOnClickListener(this);
        mActivityPengyouquanDetailImg.setOnClickListener(this);
    }

    private void setPengyouquanBeanInfo(PengyouquanBean pengyouquanBean) {

        if (ObjectUtils.isEmpty(pengyouquanBean)) {
            return;
        }
        isLike = pengyouquanBean.getIsLike();
        if (StringUtils.equals("1", isLike)) {
            mFragmentPinglunDianzanText.setText("已点赞");
        } else {
            mFragmentPinglunDianzanText.setText("点赞");
        }

        MyCommonUtil.jiazaitouxiang(this, pengyouquanBean.getPostUserPhoto(), mActivityPengyouquanDetailImg);

        mActivityPengyouquanDetailTime.setText(TimeUtils.millis2String(Long.parseLong(pengyouquanBean.getCreatedAtStamp())));
        mActivityPengyouquanDetailContent.setText(pengyouquanBean.getContent());

        mActivityPengyouquanDetailName.setText(pengyouquanBean.getPostUser());


        if (StringUtils.equals("1", pengyouquanBean.getIsCompany())) {
            mActivityPengyouquanDetailBigv.setVisibility(View.VISIBLE);
            mActivityPengyouquanDetailCompanyname.setText(pengyouquanBean.getCompanyName());
        } else {
            if (StringUtils.equals("1", pengyouquanBean.getIsDyeV())) {
                mActivityPengyouquanDetailBigv.setVisibility(View.VISIBLE);
                mActivityPengyouquanDetailCompanyname.setText(pengyouquanBean.getDyeVName());
            } else {
                mActivityPengyouquanDetailCompanyname.setVisibility(View.GONE);
                mActivityPengyouquanDetailBigv.setVisibility(View.GONE);
            }
        }

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(pengyouquanBean.getPostUser() + " .");


        if (StringUtils.equals("1", pengyouquanBean.getIsCompany()) || StringUtils.equals("1", pengyouquanBean.getIsDyeV())) {
            //大佬等级1
            if (StringUtils.equals("1", pengyouquanBean.getBossLevel())) {

                Drawable level1 = getResources().getDrawable(R.mipmap.level_one);
                level1.setBounds(0, 0, level1.getIntrinsicWidth(), level1.getIntrinsicHeight());

                ImageSpan span1 = new ImageSpan(level1, ImageSpan.ALIGN_BASELINE);

                spannableStringBuilder.setSpan(span1,
                        spannableStringBuilder.length() - 1, spannableStringBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                mActivityPengyouquanDetailName.setText(spannableStringBuilder);

            }
            if (StringUtils.equals("2", pengyouquanBean.getBossLevel())) {
                Drawable level2 = getResources().getDrawable(R.mipmap.level_two);
                level2.setBounds(0, 0, level2.getIntrinsicWidth(), level2.getIntrinsicHeight());

                ImageSpan span2 = new ImageSpan(level2, ImageSpan.ALIGN_BASELINE);

                spannableStringBuilder.setSpan(span2,
                        spannableStringBuilder.length() - 1, spannableStringBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                mActivityPengyouquanDetailName.setText(spannableStringBuilder);

            }
            if (StringUtils.equals("3", pengyouquanBean.getBossLevel())) {
                Drawable level3 = getResources().getDrawable(R.mipmap.level_three);
                level3.setBounds(0, 0, level3.getIntrinsicWidth(), level3.getIntrinsicHeight());
                ImageSpan span3 = new ImageSpan(level3, ImageSpan.ALIGN_BASELINE);
                spannableStringBuilder.setSpan(span3,
                        spannableStringBuilder.length() - 1, spannableStringBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                mActivityPengyouquanDetailName.setText(spannableStringBuilder);

            }
        }

        if (!StringUtils.isEmpty(pengyouquanBean.getUrl())) {
            mActivityPengyouquanDetailVideolayout.setVisibility(View.VISIBLE);
            GlideUtils.loadImage(PengyouquanDetailActivity.this,
                    ServerInfo.IMAGE + pengyouquanBean.getVideoPicUrl(), mActivityPengyouquanDetailVideoimg);
        }

        List<PhotoInfo> photoInfos = new ArrayList<>();
        if (!StringUtils.isEmpty(pengyouquanBean.getPic1())) {
            photoInfos.add(new PhotoInfo(pengyouquanBean.getPic1()));
        }
        if (!StringUtils.isEmpty(pengyouquanBean.getPic2())) {
            photoInfos.add(new PhotoInfo(pengyouquanBean.getPic2()));
        }
        if (!StringUtils.isEmpty(pengyouquanBean.getPic3())) {
            photoInfos.add(new PhotoInfo(pengyouquanBean.getPic3()));
        }
        if (!StringUtils.isEmpty(pengyouquanBean.getPic4())) {
            photoInfos.add(new PhotoInfo(pengyouquanBean.getPic4()));
        }
        if (!StringUtils.isEmpty(pengyouquanBean.getPic5())) {
            photoInfos.add(new PhotoInfo(pengyouquanBean.getPic5()));
        }
        if (!StringUtils.isEmpty(pengyouquanBean.getPic6())) {
            photoInfos.add(new PhotoInfo(pengyouquanBean.getPic6()));
        }
        if (!StringUtils.isEmpty(pengyouquanBean.getPic7())) {
            photoInfos.add(new PhotoInfo(pengyouquanBean.getPic7()));
        }
        if (!StringUtils.isEmpty(pengyouquanBean.getPic8())) {
            photoInfos.add(new PhotoInfo(pengyouquanBean.getPic8()));
        }
        if (!StringUtils.isEmpty(pengyouquanBean.getPic9())) {
            photoInfos.add(new PhotoInfo(pengyouquanBean.getPic9()));
        }

        mActivityPengyouquanDetailMultiImageView.setList(photoInfos);
        mActivityPengyouquanDetailMultiImageView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());

                List<String> photoUrls = new ArrayList<String>();
                for (PhotoInfo photoInfo : photoInfos) {
                    photoUrls.add(photoInfo.url);
                }
                ImagePagerActivity.startImagePagerActivity(PengyouquanDetailActivity.this, photoUrls, position, imageSize);
            }
        });


        if (!ObjectUtils.isEmpty(pengyouquanBean.getShareBean())) {
            mActivityPengyouquanDetailLianjieLayout.setVisibility(View.VISIBLE);
            GlideUtils.loadImage(this, ServerInfo.IMAGE + pengyouquanBean.getShareBean().getPic(), mActivityPengyouquanDetailLianjieImg);
            mActivityPengyouquanDetailLianjieTitle.setText(pengyouquanBean.getShareBean().getTitle());
            mActivityPengyouquanDetailLianjieLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PengyouquanDetailActivity.this, ZixunxiangqingActivity.class);
                    intent.putExtra("id", pengyouquanBean.getShareBean().getId());
                    ActivityUtils.startActivity(intent);
                }
            });
        } else {
            mActivityPengyouquanDetailLianjieLayout.setVisibility(View.GONE);
        }

    }

    private void addData() {

        HttpParams params = new HttpParams();
        params.put("sign", SPUtils.getInstance().getString("sign"));
        params.put("token", SPUtils.getInstance().getString("token"));
        params.put("id", id);
        if (!StringUtils.isEmpty(from)) {
            switch (from) {
                case "pinglun":
                    params.put("messageType", "dyeComment");
                    params.put("messageId", pinglunId);
                    break;
                case "dianzan":
                    params.put("messageType", "dyeLike");
                    params.put("messageId", dianzanId);
                    break;
                case "aite":
                    params.put("messageType", "dyeNotice");
                    params.put("messageId", aiteId);
                    break;
            }
        }
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.QUERYDYECOMMENTDETAIL)
                .tag(this)
                .params(params);

        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.e("QUERYDYECOMMENTDETAIL", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            if (ObjectUtils.isEmpty(data)) {
                                ToastUtils.showShort(msg);
                                return;
                            }
                            pengyouquanBean = data.toJavaObject(PengyouquanBean.class);
                            setPengyouquanBeanInfo(pengyouquanBean);
                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(PengyouquanDetailActivity.this, request, this);
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
                ToastUtils.showShort(getResources().getString(R.string.NETEXCEPTION));
            }
        };

        request.execute(stringCallback);
    }

    BaseViewpageAdapter baseViewpageAdapter;

    private void initToolBarData(Long id) {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(PinglunFragment.newInstance(String.valueOf(id)));
        fragments.add(PraiseFragment.newInstance(String.valueOf(id)));
        List<String> datas = new ArrayList<>();
        datas.add("评论");
        datas.add("点赞");
        baseViewpageAdapter = new BaseViewpageAdapter(getSupportFragmentManager(), fragments, datas);
        mViewpager.setAdapter(baseViewpageAdapter);
        mTabLayout.setupWithViewPager(mViewpager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

    }

    private InputMethodManager inputMethodManager;

    private PopupWindow popupWindow;
    private EditText inputComment;
    private TextView btn_submit;


    @SuppressLint("WrongConstant")
    public void showPop(Long id) {
        View inflate = LayoutInflater.from(PengyouquanDetailActivity.this).inflate(R.layout.pengyouquan_huifu_layout, null);

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
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // btn_submit.setClickable(false);
                String comment1 = inputComment.getText().toString().trim();
                if (StringUtils.isEmpty(comment1)) {
                    ToastUtils.showShort("不能发表空的评论");
                    return;
                }
                //调用提交评论接口
                saveDiscuss(comment1, id);
                inputMethodManager.hideSoftInputFromWindow(inputComment.getWindowToken(), 0);
                inputComment.setText("");
                popupWindow.dismiss();
            }
        });

    }


    private void saveDiscuss(String comment1, Long id) {

        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.FABIAOPINGLUN)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("dyeId", id)
                .params("content", comment1)
                .params("parentId", "")
                .params("from",getResources().getString(R.string.app_android))
                .params("token", SPUtils.getInstance().getString("token"));


        DialogStringCallback stringCallback = new DialogStringCallback(PengyouquanDetailActivity.this) {
            @Override
            public void onSuccess(Response<String> response) {

                LogUtils.e("FABIAOPINGLUN", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            EventBus.getDefault().post(new MessageBean(MessageBean.Code.PINGLUNNEEDREFLUSH));
                            EventBus.getDefault().post(new MessageBean(MessageBean.Code.PENGYOUQUANNEEDREFLUSH));
                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(PengyouquanDetailActivity.this, request, this);
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
            }
        };

        request.execute(stringCallback);

    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            return false;
        }
    });


    private void dianZan(Long id) {
        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.DIANZAN)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("dyeId", id)
                .params("parentId", 0)
                .params("token", SPUtils.getInstance().getString("token"));

        DialogStringCallback stringCallback = new DialogStringCallback(PengyouquanDetailActivity.this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("GETCHANPINLIEBIAO", response.body());
                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");

                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            EventBus.getDefault().post(new MessageBean(MessageBean.Code.XIANGQINGYEDIANZANCHENGGONG));
                            isLike = "1";
                            mFragmentPinglunDianzanText.setText("已点赞");
                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(PengyouquanDetailActivity.this, request, this);
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
                ToastUtils.showShort(getResources().getString(R.string.NETEXCEPTION));
            }
        };

        request.execute(stringCallback);

    }

    private String isLike;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.toolbar_text:
                if (ObjectUtils.isEmpty(pengyouquanBean)) {
                    ToastUtils.showShort("分享异常");
                    return;
                }
                String title = "";
                String content = "";
                if (StringUtils.isEmpty(pengyouquanBean.getContent())) {
                    title = "【朋友圈】";
                } else {
                    if (pengyouquanBean.getContent().length() < 11) {
                        title = "【朋友圈】" + pengyouquanBean.getContent() + "...";
                    } else {
                        title = "【朋友圈】" + pengyouquanBean.getContent().substring(0, 10) + "...";
                    }
                    if (pengyouquanBean.getContent().length() < 31) {
                        content = "【朋友圈】" + pengyouquanBean.getContent() + "...";
                    } else {
                        content = "【朋友圈】" + pengyouquanBean.getContent().substring(0, 30) + "...";
                    }
                }
                if (!StringUtils.isEmpty(pengyouquanBean.getVideoPicUrl())) {
                    ShareUtil.shareFriendCircle(PengyouquanDetailActivity.this, title,
                            content, pengyouquanBean.getVideoPicUrl(), pengyouquanBean.getId());
                    return;
                } else if (!StringUtils.isEmpty(pengyouquanBean.getPic1())) {
                    ShareUtil.shareFriendCircle(PengyouquanDetailActivity.this, title,
                            content, pengyouquanBean.getPic1(), pengyouquanBean.getId());
                    return;
                } else
                    ShareUtil.shareFriendCircle(PengyouquanDetailActivity.this, title,
                            content, "", pengyouquanBean.getId());
                break;
            case R.id.activity_pengyouquan_detail_play:
                if (ObjectUtils.isEmpty(pengyouquanBean)) {
                    return;
                }
                Intent intent = new Intent(PengyouquanDetailActivity.this, ShipinbofangActivity.class);
                intent.putExtra("url", ServerInfo.IMAGE + pengyouquanBean.getUrl());
                ActivityUtils.startActivity(intent);
                break;
            case R.id.fragment_pinglun_pinglun:
                if (!UserUtil.isLogin()) {
                    ActivityUtils.startActivity(LoginActivity.class);
                    return;
                }
                handler.sendEmptyMessageDelayed(0, 200);
                showPop(id);
                break;
            case R.id.fragment_pinglun_dianzan:
                if (!UserUtil.isLogin()) {
                    ActivityUtils.startActivity(LoginActivity.class);
                    return;
                }

                if (StringUtils.equals("1", isLike)) {
                    ToastUtils.showShort("暂不能取消点赞");
                } else {
                    dianZan(id);
                }

                break;
            case R.id.activity_pengyouquan_detail_img:
                Intent i = new Intent(this, PersonInfoActivity.class);
                i.putExtra("userId", pengyouquanBean.getUserId());
                ActivityUtils.startActivity(i);
                break;
        }
    }
}
