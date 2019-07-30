package com.company.qcy.huodong.tuangou.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
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
import com.bumptech.glide.Glide;
import com.company.qcy.R;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.Utils.UserUtil;
import com.company.qcy.Utils.share.ShareUtil;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.huodong.tuangou.bean.TuangouBean;
import com.company.qcy.huodong.tuangou.fragment.TuangouchenggongDialogFragment;
import com.company.qcy.ui.activity.user.LoginActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

import cn.iwgang.countdownview.CountdownView;


@Route(path = "/cut/cutPrice")
public class KanjiaActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mKanjiaImg;
    private TextView mKanjiaName;
    private TextView mKanjiaPrice;
    private TextView mKanjiaPriceUnit;
    private TextView mKanjiaGoumailiang;
    private TextView mKanjiaGoumailiangUnit;
    private TextView mKanjiaKucun;
    private TextView mKanjiaKucunUnit;
    private CountdownView countdownView;
    private TextView mKanjiaYikanjiage;
    private TextView mKanjiaYikanjiageUnit;
    private ProgressBar mKanjiaProgressBar;
    private TextView mKanjiaShengyukanjiaUnit;
    private TextView mKanjiaShengyukanjia;
    /**
     * 砍价
     */
    private TextView mKanjia;

    @Autowired
    public String mainId;

    @Autowired
    public String buyerId;
    /**
     * 我也要参与
     */
    private TextView mKanjiaWoyaocanyu;
    /**
     * 好友帮砍价
     */
    private TextView mActicityKanjiaTitle;
    private ImageView mActicityKanjiaBack;
    /**
     * ···
     */
    private TextView mActicityKanjiaShare;
    private ImageView mKanjiaTuangouStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kanjia);

        Uri data = getIntent().getData();
        //不为空说明是外部网页传过来的
        if (!ObjectUtils.isEmpty(data)) {
            mainId = data.getQueryParameter("mainId");
            buyerId = data.getQueryParameter("buyerId");
        } else {
            mainId = getIntent().getStringExtra("mainId");
            buyerId = getIntent().getStringExtra("buyerId");
        }
        initView();
    }

    private void initView() {
        mKanjiaImg = (ImageView) findViewById(R.id.kanjia_img);
        mKanjiaName = (TextView) findViewById(R.id.kanjia_name);
        mKanjiaPrice = (TextView) findViewById(R.id.kanjia_price);
        mKanjiaPriceUnit = (TextView) findViewById(R.id.kanjia_price_unit);
        mKanjiaGoumailiang = (TextView) findViewById(R.id.kanjia_goumailiang);
        mKanjiaGoumailiangUnit = (TextView) findViewById(R.id.kanjia_goumailiang_unit);
        mKanjiaKucun = (TextView) findViewById(R.id.kanjia_kucun);
        mKanjiaKucunUnit = (TextView) findViewById(R.id.kanjia_kucun_unit);
        countdownView = (CountdownView) findViewById(R.id.kanjia_countdownView);
        mKanjiaYikanjiage = (TextView) findViewById(R.id.kanjia_yikanjiage);
        mKanjiaYikanjiageUnit = (TextView) findViewById(R.id.kanjia_yikanjiage_unit);
        mKanjiaProgressBar = (ProgressBar) findViewById(R.id.kanjia_progressBar);
        mKanjiaShengyukanjiaUnit = (TextView) findViewById(R.id.kanjia_shengyukanjia_unit);
        mKanjiaShengyukanjia = (TextView) findViewById(R.id.kanjia_shengyukanjia);
        mKanjia = (TextView) findViewById(R.id.kanjia_kanjia);
        mKanjia.setOnClickListener(this);
        mKanjiaWoyaocanyu = (TextView) findViewById(R.id.kanjia_woyaocanyu);
        mKanjiaWoyaocanyu.setOnClickListener(this);
        mKanjiaTuangouStatus = (ImageView) findViewById(R.id.kanjia_tuangou_status);

        mActicityKanjiaTitle = (TextView) findViewById(R.id.acticity_kanjia_title);
        mActicityKanjiaBack = (ImageView) findViewById(R.id.acticity_kanjia_back);
        mActicityKanjiaBack.setOnClickListener(this);
        mActicityKanjiaShare = (TextView) findViewById(R.id.acticity_kanjia_share);
        mActicityKanjiaShare.setOnClickListener(this);

        addData();

    }

    @Override
    public void onReciveMessage(MessageBean msg) {
        super.onReciveMessage(msg);

        switch (msg.getCode()) {
            case MessageBean.Code.DELU:

                addData();

                break;

            case MessageBean.Code.WXLOGIN:

                addData();

                break;

            case MessageBean.Code.TUANGOUKANJIACHENGGONG:

                addData();

                break;
        }

    }


    private void share() {
        ShareUtil.shareKanjia(KanjiaActivity.this,
                tuangouBean.getProductName(), tuangouBean.getProductPic(), Long.parseLong(mainId), Long.parseLong(buyerId));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;

            case R.id.kanjia_kanjia:
                if (ObjectUtils.isEmpty(tuangouBean)) {
                    return;
                }

                if (UserUtil.isLogin()) {
                    if (!StringUtils.isEmpty(tuangouBean.getLoginUserHasCut())) {

                        if (StringUtils.equals("1", tuangouBean.getLoginUserHasCut())) {
                            share();
                        } else {

                            if (StringUtils.equals("1", tuangouBean.getStopCutPrice())) {
                                //没有停止砍价

                                if (StringUtils.equals("10", tuangouBean.getEndCode())) {
                                    if (Float.parseFloat(tuangouBean.getRemainCutPrice()) > 0) {
                                        kanJia();

                                    } else {

                                        share();
                                    }
                                } else if (StringUtils.equals("11", tuangouBean.getEndCode())) {

                                    if (StringUtils.equals("0", tuangouBean.getIsConsiderStock())) {
                                        if (Float.parseFloat(tuangouBean.getRemainCutPrice()) > 0) {
                                            kanJia();

                                        } else {

                                            share();
                                        }
                                    } else {
                                        share();
                                    }
                                } else {
                                    share();
                                }

                            } else if (StringUtils.equals("0", tuangouBean.getStopCutPrice())) {
                                share();
                            }
                        }
                    }
                } else {

                    if (StringUtils.equals("20", tuangouBean.getEndCode()) ||
                            StringUtils.equals("21", tuangouBean.getEndCode())) {
                        //已经结束
                        share();

                    } else if (StringUtils.equals("11", tuangouBean.getEndCode())) {

                        if (StringUtils.equals("0", tuangouBean.getIsConsiderStock())) {
                            //不考虑库存
                            ActivityUtils.startActivity(LoginActivity.class);
                        } else {
                            share();

                        }
                    } else {
                        ActivityUtils.startActivity(LoginActivity.class);
                    }

                }

                break;
            case R.id.kanjia_woyaocanyu:
                if (ObjectUtils.isEmpty(tuangouBean)) {
                    return;
                }

                Intent tuangouIntent = new Intent(KanjiaActivity.this, TuangouxiangqingActivity.class);
                tuangouIntent.putExtra("id", tuangouBean.getId());
                ActivityUtils.startActivity(tuangouIntent);

                break;
            case R.id.acticity_kanjia_back:
                finish();
                break;
            case R.id.acticity_kanjia_share:
                if (ObjectUtils.isEmpty(tuangouBean)) {
                    return;
                }

                try {
                    ShareUtil.shareKanjia(KanjiaActivity.this,
                            tuangouBean.getProductName(), tuangouBean.getProductPic(), Long.parseLong(mainId), Long.parseLong(buyerId));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    private TuangouBean tuangouBean;

    private void addData() {
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.KANJIAINFO)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("token", SPUtils.getInstance().getString("token"))
                .params("mainId", mainId)//团购id
                .params("buyerId", buyerId);//认购id


        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("KANJIAINFO", response.body());

                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            tuangouBean = data.toJavaObject(TuangouBean.class);
                            setInfo();
                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(KanjiaActivity.this, request, this);
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

    private void setInfo() {

        if (ObjectUtils.isEmpty(tuangouBean)) {
            return;
        }

        if (!StringUtils.isEmpty(tuangouBean.getProductPic())) {
            Glide.with(this).load(ServerInfo.IMAGE + tuangouBean.getProductPic()).into(mKanjiaImg);
        }

        mKanjiaYikanjiage.setText("元/" + tuangouBean.getPriceUnit());
        mKanjiaYikanjiageUnit.setText("元/" + tuangouBean.getPriceUnit());
        mKanjiaName.setText(tuangouBean.getProductName());
        mKanjiaGoumailiang.setText(tuangouBean.getNum());
        mKanjiaGoumailiangUnit.setText(tuangouBean.getNumUnit());
        mKanjiaPrice.setText(tuangouBean.getRealPrice());
        mKanjiaPriceUnit.setText("元/" + tuangouBean.getPriceUnit());
        mKanjiaKucun.setText(tuangouBean.getRemainNum());
        mKanjiaKucunUnit.setText(tuangouBean.getNumUnit());
        mKanjiaYikanjiage.setText(tuangouBean.getHasCutPrice());
        mKanjiaShengyukanjia.setText(tuangouBean.getRemainCutPrice());

        if (UserUtil.isLogin()) {
            if (StringUtils.equals("1", tuangouBean.getLoginUserHasCut())) {

                mKanjia.setText("已砍价，帮好友分享！");
            } else {
                if (StringUtils.equals("1", tuangouBean.getStopCutPrice())) {
                    //没有停止砍价

                    if (StringUtils.equals("10", tuangouBean.getEndCode())) {
                        if (Float.parseFloat(tuangouBean.getRemainCutPrice()) > 0) {
                            mKanjia.setText("点我,帮好友砍价!");

                        } else {

                            mKanjia.setText("帮好友分享！");
                        }
                    } else if (StringUtils.equals("11", tuangouBean.getEndCode())) {

                        if (StringUtils.equals("0", tuangouBean.getIsConsiderStock())) {
                            if (Float.parseFloat(tuangouBean.getRemainCutPrice()) > 0) {
                                mKanjia.setText("点我,帮好友砍价!");

                            } else {

                                mKanjia.setText("帮好友分享！");
                            }
                        } else {
                            mKanjia.setText("帮好友分享！");
                        }
                    } else {
                        mKanjia.setText("帮好友分享！");
                    }

                } else if (StringUtils.equals("0", tuangouBean.getStopCutPrice())) {
                    mKanjia.setText("帮好友分享！");
                }


            }
        } else {

            if (StringUtils.equals("20", tuangouBean.getEndCode()) ||
                    StringUtils.equals("21", tuangouBean.getEndCode())) {
                //已经结束
                mKanjia.setText("团购已经结束,帮好友分享!");

            } else if (StringUtils.equals("11", tuangouBean.getEndCode())) {

                if (StringUtils.equals("0", tuangouBean.getIsConsiderStock())) {
                    //不考虑库存
                    mKanjia.setText("点我登录,帮好友砍价!");
                } else {
                    mKanjia.setText("团购已经结束,帮好友分享!");

                }
            } else {
                mKanjia.setText("点我登录,帮好友砍价!");
            }
        }


        String[] split = tuangouBean.getCutPricePercent().split("%");
        mKanjiaProgressBar.setProgress(Integer.parseInt(split[0]));


        if (StringUtils.equals("00", tuangouBean.getEndCode())) {
            //团购未开始
            countdownView.start(Long.parseLong(tuangouBean.getStartTimeStamp()) - TimeUtils.getNowMills());
            mKanjiaTuangouStatus.setImageDrawable(getResources().getDrawable(R.mipmap.tuangou_weikaishi));
        } else if (StringUtils.equals("10", tuangouBean.getEndCode())) {
            //已开始未领完
            countdownView.start(Long.parseLong(tuangouBean.getEndTimeStamp()) - TimeUtils.getNowMills());
            mKanjiaTuangouStatus.setImageDrawable(getResources().getDrawable(R.mipmap.tuangou_yikaishi));
            mKanjiaWoyaocanyu.setVisibility(View.VISIBLE);
        } else if (StringUtils.equals("11", tuangouBean.getEndCode())) {
            //已开始已领完
            if (StringUtils.equals("0", tuangouBean.getIsConsiderStock())) {
                countdownView.start(Long.parseLong(tuangouBean.getEndTimeStamp()) - TimeUtils.getNowMills());
                mKanjiaTuangouStatus.setImageDrawable(getResources().getDrawable(R.mipmap.tuangou_yikaishi));
                mKanjiaWoyaocanyu.setVisibility(View.VISIBLE);
            } else {
                countdownView.start(0);
                mKanjiaTuangouStatus.setImageDrawable(getResources().getDrawable(R.mipmap.tuangou_yijieshu));
            }
        } else if (StringUtils.equals("20", tuangouBean.getEndCode())) {
            //已结束未领完
            countdownView.start(0);
            mKanjiaTuangouStatus.setImageDrawable(getResources().getDrawable(R.mipmap.tuangou_yijieshu));
        } else if (StringUtils.equals("21", tuangouBean.getEndCode())) {
            //已结束已领完
            countdownView.start(0);
            mKanjiaTuangouStatus.setImageDrawable(getResources().getDrawable(R.mipmap.tuangou_yijieshu));
        }
    }

    private void kanJia() {
        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.KANJIA)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("token", SPUtils.getInstance().getString("token"))
                .params("mainId", mainId)//团购id
                .params("from", getResources().getString(R.string.app_android))
                .params("buyerId", buyerId);//认购id


        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("KANJIA", response.body());

                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {

                            FragmentManager fragmentManager = getSupportFragmentManager();
                            TuangouchenggongDialogFragment myDialogFragment = new TuangouchenggongDialogFragment();
                            myDialogFragment.setStatus(2);
                            myDialogFragment.setCutPrice(jsonObject.getString("data"));

                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.add(myDialogFragment, "dialog");
                            fragmentTransaction.commitAllowingStateLoss();

                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(KanjiaActivity.this, request, this);
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

}
