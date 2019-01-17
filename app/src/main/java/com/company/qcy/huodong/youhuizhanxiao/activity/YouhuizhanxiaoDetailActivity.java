package com.company.qcy.huodong.youhuizhanxiao.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.BaseViewpageAdapter;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.huodong.youhuizhanxiao.bean.YouhuizhanxiaoBean;
import com.company.qcy.huodong.youhuizhanxiao.fragment.YouhuizhanxiaoCanshuFragment;
import com.company.qcy.huodong.youhuizhanxiao.fragment.YouhuizhanxiaoJiluFragment;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import java.util.ArrayList;
import java.util.List;

public class YouhuizhanxiaoDetailActivity extends BaseActivity implements View.OnClickListener {


    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private ImageView mActivityTuangouxiangqingImg;
    /**
     * 51
     */
    private TextView mActivityYouhuizhanxiaoTotalnum1;
    /**
     * 已销售总量
     */
    private TextView mActivityTuangouxiangqingDanyonghucaigou;
    /**
     * 吨
     */
    private TextView mActivityYouhuizhanxiaoTotalnum1Unit;
    /**
     * 35
     */
    private TextView mActivityTuangouxiangqingYuanjia;
    /**
     * 元/吨
     */
    private TextView mActivityTuangouxiangqingYuanjiaDanwie;
    /**
     * 39
     */
    private TextView mActivityTuangouxiangqingYouhuijia;
    /**
     * 元/吨
     */
    private TextView mActivityTuangouxiangqingYouhuijiaDanwei;
    /**
     * 活性翠兰G266%
     */
    private TextView mActivityYouhuizhanxiaoDetailProductname;
    /**
     * 51
     */
    private TextView mActivityYouhuizhanxiaoTotalnum2;
    /**
     * 吨
     */
    private TextView mActivityYouhuizhanxiaoTotalnum2Unit;
    private TabLayout mTabLayout;
    private ViewPager mViewpager;
    private BaseViewpageAdapter viewpageAdapter;
    private String id;
    /**
     * 5
     */
    private TextView mActivityYouhuizhanxiaoYijiTotalnum;
    /**
     * 27
     */
    private TextView mActivityYouhuizhanxiaoYijiPrice;
    /**
     * 元/KG
     */
    private TextView mActivityYouhuizhanxiaoYijiPriceUnit;
    private ConstraintLayout mActivityYouhuizhanxiaoYijiLayout;
    /**
     * 5
     */
    private TextView mActivityYouhuizhanxiaoErjiTotalnum;
    /**
     * 27
     */
    private TextView mActivityYouhuizhanxiaoErjiPrice;
    /**
     * 元/KG
     */
    private TextView mActivityYouhuizhanxiaoErjiPriceUnit;
    private ConstraintLayout mActivityYouhuizhanxiaoErjiLayout;
    /**
     * 5
     */
    private TextView mActivityYouhuizhanxiaoSanjiTotalnum;
    /**
     * 27
     */
    private TextView mActivityYouhuizhanxiaoSanjiPrice;
    /**
     * 元/KG
     */
    private TextView mActivityYouhuizhanxiaoSanjiPriceUnit;
    private ConstraintLayout mActivityYouhuizhanxiaoSanjiLayout;
    /**
     * 我要购买
     */
    private Button mActivityYouhuizhanxiaoWoyaogoumai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youhuizhanxiao_detail);
        id = getIntent().getStringExtra("id");
        initView();

    }


    //是否有下方虚拟栏
    private static boolean isNavigationBarAvailable() {
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        boolean hasHomeKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME);
        return (!(hasBackKey && hasHomeKey));
    }

    //获取虚拟按键的高度
    public static int getNavigationBarHeight(Context context) {
        int result = 0;
        if (isNavigationBarAvailable()) {
            Resources res = context.getResources();
            int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mActivityTuangouxiangqingImg = (ImageView) findViewById(R.id.activity_tuangouxiangqing_img);
        mActivityYouhuizhanxiaoTotalnum1 = (TextView) findViewById(R.id.activity_youhuizhanxiao_totalnum1);
        mActivityTuangouxiangqingDanyonghucaigou = (TextView) findViewById(R.id.activity_tuangouxiangqing_danyonghucaigou);
        mActivityYouhuizhanxiaoTotalnum1Unit = (TextView) findViewById(R.id.activity_youhuizhanxiao_totalnum1_unit);
        mActivityTuangouxiangqingYuanjia = (TextView) findViewById(R.id.activity_tuangouxiangqing_yuanjia);
        mActivityTuangouxiangqingYuanjiaDanwie = (TextView) findViewById(R.id.activity_tuangouxiangqing_yuanjia_danwie);
        mActivityTuangouxiangqingYouhuijia = (TextView) findViewById(R.id.activity_youhuizhanxiao_youhuijia);
        mActivityTuangouxiangqingYouhuijiaDanwei = (TextView) findViewById(R.id.activity_youhuizhanxiao_youhuijia_danwei);
        mActivityYouhuizhanxiaoDetailProductname = (TextView) findViewById(R.id.activity_youhuizhanxiao_detail_productname);
        mActivityYouhuizhanxiaoTotalnum2 = (TextView) findViewById(R.id.activity_youhuizhanxiao_totalnum2);
        mActivityYouhuizhanxiaoTotalnum2Unit = (TextView) findViewById(R.id.activity_youhuizhanxiao_totalnum2_unit);
        mTabLayout = (TabLayout) findViewById(R.id.actitity_youhuizhanxiao_detail_tabLayout);
        mViewpager = (ViewPager) findViewById(R.id.actitity_youhuizhanxiao_detail_viewpager);
        mActivityYouhuizhanxiaoYijiTotalnum = (TextView) findViewById(R.id.activity_youhuizhanxiao_yiji_totalnum);
        mActivityYouhuizhanxiaoYijiPrice = (TextView) findViewById(R.id.activity_youhuizhanxiao_yiji_price);
        mActivityYouhuizhanxiaoYijiPriceUnit = (TextView) findViewById(R.id.activity_youhuizhanxiao_yiji_price_unit);
        mActivityYouhuizhanxiaoYijiLayout = (ConstraintLayout) findViewById(R.id.activity_youhuizhanxiao_yiji_layout);
        mActivityYouhuizhanxiaoErjiTotalnum = (TextView) findViewById(R.id.activity_youhuizhanxiao_erji_totalnum);
        mActivityYouhuizhanxiaoErjiPrice = (TextView) findViewById(R.id.activity_youhuizhanxiao_erji_price);
        mActivityYouhuizhanxiaoErjiPriceUnit = (TextView) findViewById(R.id.activity_youhuizhanxiao_erji_price_unit);
        mActivityYouhuizhanxiaoErjiLayout = (ConstraintLayout) findViewById(R.id.activity_youhuizhanxiao_erji_layout);
        mActivityYouhuizhanxiaoSanjiTotalnum = (TextView) findViewById(R.id.activity_youhuizhanxiao_sanji_totalnum);
        mActivityYouhuizhanxiaoSanjiPrice = (TextView) findViewById(R.id.activity_youhuizhanxiao_sanji_price);
        mActivityYouhuizhanxiaoSanjiPriceUnit = (TextView) findViewById(R.id.activity_youhuizhanxiao_sanji_price_unit);
        mActivityYouhuizhanxiaoSanjiLayout = (ConstraintLayout) findViewById(R.id.activity_youhuizhanxiao_sanji_layout);
        mToolbarTitle.setText("展销详情");
        addData();

        mActivityYouhuizhanxiaoWoyaogoumai = (Button) findViewById(R.id.activity_youhuizhanxiao_woyaogoumai);
        mActivityYouhuizhanxiaoWoyaogoumai.setOnClickListener(this);
        if (isNavigationBarAvailable()) {
//            ViewGroup.MarginLayoutParams margin=new ViewGroup.MarginLayoutParams(mActivityTuangouxiangqingWoyaotuangou.getLayoutParams());
//            margin.setMargins(0,0, margin.topMargin, 0);
//            ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(margin);
//            mActivityTuangouxiangqingWoyaotuangou.setLayoutParams(layoutParams);

            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mActivityYouhuizhanxiaoWoyaogoumai.getLayoutParams();
            //设置各个方向上的间距
            params.setMargins(0, 0, 0, getNavigationBarHeight(this));
            //改变控件的属性
            mActivityYouhuizhanxiaoWoyaogoumai.setLayoutParams(params);
        }
    }


    @Override
    public void onReciveMessage(MessageBean msg) {
        super.onReciveMessage(msg);


    }

    private void addData() {
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.YOUHUIZHANXIAODETAIL)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("id", id);

        DialogStringCallback stringCallback = new DialogStringCallback(this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("YOUHUIZHANXIAODETAIL", response.body());
                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            YouhuizhanxiaoBean bean = data.toJavaObject(YouhuizhanxiaoBean.class);
                            setInfo(bean);
                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(YouhuizhanxiaoDetailActivity.this, request, this);
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

    private String numUnit;

    private void setInfo(YouhuizhanxiaoBean bean) {
        if (ObjectUtils.isEmpty(bean)) {
            return;
        }
        numUnit = bean.getNumUnit();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(YouhuizhanxiaoCanshuFragment.newInstance(bean.getListSale()));
        fragments.add(YouhuizhanxiaoJiluFragment.newInstance(String.valueOf(id)));
        List<String> datas = new ArrayList<>();
        datas.add("基本参数");
        datas.add("参与购买记录详情");
        viewpageAdapter = new BaseViewpageAdapter(getSupportFragmentManager(), fragments, datas);
        mViewpager.setAdapter(viewpageAdapter);
        mTabLayout.setupWithViewPager(mViewpager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        GlideUtils.loadImage(context, ServerInfo.IMAGE + bean.getProductPic(), mActivityTuangouxiangqingImg);
        mActivityYouhuizhanxiaoDetailProductname.setText(bean.getProductName());
        mActivityYouhuizhanxiaoTotalnum1.setText(bean.getTotalNum());
        mActivityYouhuizhanxiaoTotalnum2.setText(bean.getTotalNum());
        mActivityYouhuizhanxiaoTotalnum1Unit.setText(bean.getNumUnit());
        mActivityYouhuizhanxiaoTotalnum2Unit.setText(bean.getNumUnit());
        mActivityTuangouxiangqingYuanjia.setText(bean.getOldPrice());

        if(bean.getNewPrice().contains("~")){
            String[] strings = bean.getNewPrice().split("~");
            mActivityTuangouxiangqingYouhuijia.setText(strings[0]+"\n~"+strings[1]);
        }else if(bean.getNewPrice().contains("-")){
            String[] strings = bean.getNewPrice().split("-");
            mActivityTuangouxiangqingYouhuijia.setText(strings[0]+"\n-"+strings[1]);
        }else {
            mActivityTuangouxiangqingYouhuijia.setText(bean.getNewPrice());
        }
        mActivityTuangouxiangqingYuanjiaDanwie.setText("元/" + bean.getPriceUnit());
        mActivityTuangouxiangqingYouhuijiaDanwei.setText("元/" + bean.getPriceUnit());

        if (!ObjectUtils.isEmpty(bean.getListPrice())) {
            switch (bean.getListPrice().size()) {
                case 1:
                    mActivityYouhuizhanxiaoYijiLayout.setVisibility(View.VISIBLE);
                    mActivityYouhuizhanxiaoYijiTotalnum.setText(bean.getListPrice().get(0).getSalesNum());
                    mActivityYouhuizhanxiaoYijiPrice.setText(bean.getListPrice().get(0).getSalesPrice());
                    mActivityYouhuizhanxiaoYijiPriceUnit.setText("元/" + bean.getPriceUnit());
                    break;
                case 2:
                    mActivityYouhuizhanxiaoYijiLayout.setVisibility(View.VISIBLE);
                    mActivityYouhuizhanxiaoYijiTotalnum.setText(bean.getListPrice().get(0).getSalesNum());
                    mActivityYouhuizhanxiaoYijiPrice.setText(bean.getListPrice().get(0).getSalesPrice());
                    mActivityYouhuizhanxiaoYijiPriceUnit.setText("元/" + bean.getPriceUnit());

                    mActivityYouhuizhanxiaoErjiLayout.setVisibility(View.VISIBLE);
                    mActivityYouhuizhanxiaoErjiTotalnum.setText(bean.getListPrice().get(1).getSalesNum());
                    mActivityYouhuizhanxiaoErjiPrice.setText(bean.getListPrice().get(1).getSalesPrice());
                    mActivityYouhuizhanxiaoErjiPriceUnit.setText("元/" + bean.getPriceUnit());
                    break;
                case 3:
                    mActivityYouhuizhanxiaoYijiLayout.setVisibility(View.VISIBLE);
                    mActivityYouhuizhanxiaoYijiTotalnum.setText(bean.getListPrice().get(0).getSalesNum());
                    mActivityYouhuizhanxiaoYijiPrice.setText(bean.getListPrice().get(0).getSalesPrice());
                    mActivityYouhuizhanxiaoYijiPriceUnit.setText("元/" + bean.getPriceUnit());

                    mActivityYouhuizhanxiaoErjiLayout.setVisibility(View.VISIBLE);
                    mActivityYouhuizhanxiaoErjiTotalnum.setText(bean.getListPrice().get(1).getSalesNum());
                    mActivityYouhuizhanxiaoErjiPrice.setText(bean.getListPrice().get(1).getSalesPrice());
                    mActivityYouhuizhanxiaoErjiPriceUnit.setText("元/" + bean.getPriceUnit());

                    mActivityYouhuizhanxiaoSanjiLayout.setVisibility(View.VISIBLE);
                    mActivityYouhuizhanxiaoSanjiTotalnum.setText(bean.getListPrice().get(2).getSalesNum());
                    mActivityYouhuizhanxiaoSanjiPrice.setText(bean.getListPrice().get(2).getSalesPrice());
                    mActivityYouhuizhanxiaoSanjiPriceUnit.setText("元/" + bean.getPriceUnit());
                    break;
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_youhuizhanxiao_woyaogoumai:

                Intent intent = new Intent(this,YouhuizhanxiaoBuyActivity.class);
                intent.putExtra("numUnit",numUnit);
                intent.putExtra("id",id);
                ActivityUtils.startActivity(intent);
                break;
        }
    }
}
