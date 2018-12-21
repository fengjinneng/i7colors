package com.company.qcy;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.ImageCompress;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.bean.BannerBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {

    public static int screenW, screenH;

    private static final int VIEW_NO_1 = 0;
    private static final int VIEW_NO_2 = 1;
    private static final int VIEW_NO_3 = 2;
    private static final int VIEW_NO_4 = 3;
    private static final int VIEW_NO_5 = 4;

    Button submit;
    private ViewPager mPager;
    private MyViewPagerAdapter mPagerAdapter;
    private List<View> list = new ArrayList<>();
    private ImageView mActivityGuideImg;

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        context= this;
        initView();
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        screenW = metric.widthPixels; // 屏幕宽度（像素）
        screenH = metric.heightPixels; // 屏幕高度（像素）

        LayoutInflater inflater = LayoutInflater.from(this);
        View view1 = inflater.inflate(R.layout.guide_fragment_main_1, null);
        View view2 = inflater.inflate(R.layout.guide_fragment_main_1, null);
        View view3 = inflater.inflate(R.layout.guide_fragment_main_1, null);
        View view4 = inflater.inflate(R.layout.guide_fragment_main_1, null);
        View view5 = inflater.inflate(R.layout.guide5, null);

        list.add(view1);
        list.add(view2);
        list.add(view3);
        list.add(view4);
        list.add(view5);

        mPager = (ViewPager) findViewById(R.id.container);
        mPagerAdapter = new MyViewPagerAdapter(list);
        mPager.setAdapter(mPagerAdapter);
//        mPager.setPageTransformer(true, new RotateUpTransformer());
    }


    private void addAdvData() {

        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.INDEXBANNER)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("plate_code", "APP_Start_Pic");


        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("APP_Start_Pic", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
//                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONArray data = jsonObject.getJSONArray("data");
                            if (ObjectUtils.isEmpty(data)) {
                                return;
                            }
                            List<BannerBean> bannerBeans = JSONObject.parseArray(data.toJSONString(), BannerBean.class);
                            SPUtils.getInstance().put("adv",bannerBeans.get(0).getAd_image());
                            GlideUtils.loadImageWithStartPage(GuideActivity.this,
                                    ServerInfo.IMAGE + bannerBeans.get(0).getAd_image(), mActivityGuideImg);

                            return;

                        }
//                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
//                            SignAndTokenUtil.getSign(MyWelcomeActivity.this,request,this);
//                            return;
//                        }
//                        ToastUtils.showShort(msg);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
//                ToastUtils.showShort(getResources().getString(R.string.NETEXCEPTION));
            }
        };

        request.execute(stringCallback);

    }

    private void initView() {
        mActivityGuideImg = (ImageView) findViewById(R.id.activity_guide_img);
    }

    public class MyViewPagerAdapter extends PagerAdapter {

        private List<View> mListViews;

        public MyViewPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = mListViews.get(position);
            BitmapDrawable drawable = (BitmapDrawable) view.getBackground();
            if (drawable != null) {
                drawable.getBitmap().recycle();
            }
            container.removeView(mListViews.get(position));// 删除页卡
        }

        @SuppressWarnings("deprecation")
        @Override
        public Object instantiateItem(ViewGroup container, int position) { // 这个方法用来实例化页卡
            View view = mListViews.get(position);
            container.addView(view, 0);// 添加页卡


            switch (position) {
                case VIEW_NO_1:
                    view.setBackgroundDrawable(
                            ImageCompress
                                    .getInstance()
                                    .getCompressFromId(context, R.drawable.daohang1, screenW, screenH));
                    break;
                case VIEW_NO_2:
                    view.setBackgroundDrawable(
                            ImageCompress
                                    .getInstance()
                                    .getCompressFromId(context, R.drawable.daohang2, screenW, screenH));
                    break;
                case VIEW_NO_3:
                    view.setBackgroundDrawable(
                            ImageCompress
                                    .getInstance()
                                    .getCompressFromId(context, R.drawable.daohang3, screenW, screenH));
                    break;

                case VIEW_NO_4:
                    view.setBackgroundDrawable(
                            ImageCompress
                                    .getInstance()
                                    .getCompressFromId(context, R.drawable.daohang4, screenW, screenH));
                    break;

                case VIEW_NO_5:
                    view.setBackgroundDrawable(
                            ImageCompress
                                    .getInstance()
                                    .getCompressFromId(context, R.drawable.daohang5, screenW, screenH));
                    submit = (Button) view.findViewById(R.id.mBtn);
                    submit.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, MainActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    });
                    break;
                default:

                    break;
            }

            return mListViews.get(position);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (list.size() > 0) {

                for (int i = 0; i < list.size(); i++) {
                    View view = list.get(i);
                    view.setBackground(null);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}