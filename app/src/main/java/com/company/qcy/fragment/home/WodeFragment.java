package com.company.qcy.fragment.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.bean.qiugou.NumberBean;
import com.company.qcy.ui.activity.SettingActivity;
import com.company.qcy.ui.activity.qiugoudating.DaichulibaojiaActivity;
import com.company.qcy.ui.activity.qiugoudating.DaichuliqiugouActivity;
import com.company.qcy.ui.activity.qiugoudating.WodebaojiaActivity;
import com.company.qcy.ui.activity.qiugoudating.WodeqiugouActivity;
import com.company.qcy.ui.activity.user.QiehuanshenfenActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class WodeFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";

    private String mParam1;
    private View view;
    private ImageView mFragmentWodeImage;
    /**
     * 信息部测试
     */
    private TextView mFragmentWodeName;
    /**
     * 个人用户
     */
    private TextView mFragmentWodeShenfen;
    /**
     * 999
     */
    private TextView mFragmentWodeLishiqiugou;
    /**
     * 999
     */
    private TextView mFragmentWodeLishibaojia;
    private ImageView mFragmentWodeZhongxinImage;
    /**
     * 热门询价/询价管理
     */
    private TextView mFragmentWodeZhongxinwenzi;
    /**
     * 买家中心
     */
    private TextView mFragmentWodeZhongxin;
    /**
     * 查看全部
     */
    private TextView mFragmentWodeAllQiugou;
    private ImageView mFragmentWodeXunpanzhongImg;
    /**
     * 询盘中
     */
    private TextView mFragmentWodeXunpanzhongText;
    private ImageView mFragmentWodeDaiquerenbaojiaImg;
    /**
     * 待确认报价
     */
    private TextView mFragmentWodeDaiquerenbaojiaText;
    private ImageView mFragmentWodeJijiangguoqiImg;
    /**
     * 即将过期
     */
    private TextView mFragmentWodeJijiangguoqiText;
    private ConstraintLayout mFragmentWodeLixikefu;
    /**
     * 设置
     */
    private TextView mToolbarText;
    /**
     * 点击切换
     */
    private TextView mFragmentWodeExchangeShenfen;
    /**
     * 我的求购
     */
    private TextView mFragmentWodeWodeqiugou;
    private RelativeLayout mToolbarLayout;

    private Activity activity;
    private ImageView mFragmentWodeMaijiajieshouImg;
    /**
     * 卖家已接受
     */
    private TextView mFragmentWodeMaijiajieshouText;
    private ConstraintLayout mFragmentWodeConstraintlayoutSeller;
    private ConstraintLayout mFragmentWodeConstraintlayoutBuyer;
    /**
     * 查看全部
     */
    private TextView mFragmentWodeAllQiugouSell;

    private TextView textView90;

    public WodeFragment() {
    }

    public static WodeFragment newInstance(String param1) {
        WodeFragment fragment = new WodeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View inflate = inflater.inflate(R.layout.fragment_wode, null, false);
        initView(inflate);
        return inflate;
    }

    private void initView(View inflater) {

        textView90 = inflater.findViewById(R.id.textView90);
        mFragmentWodeImage = (ImageView) inflater.findViewById(R.id.fragment_wode_image);
        mFragmentWodeImage.setOnClickListener(this);
        mFragmentWodeName = (TextView) inflater.findViewById(R.id.fragment_wode_name);
        mFragmentWodeShenfen = (TextView) inflater.findViewById(R.id.fragment_wode_shenfen);
        mFragmentWodeLishiqiugou = (TextView) inflater.findViewById(R.id.fragment_wode_lishiqiugou);
        mFragmentWodeLishibaojia = (TextView) inflater.findViewById(R.id.fragment_wode_lishibaojia);
        mFragmentWodeZhongxinImage = (ImageView) inflater.findViewById(R.id.fragment_wode_zhongxin_image);
        mFragmentWodeZhongxin = (TextView) inflater.findViewById(R.id.fragment_wode_zhongxin);
        mFragmentWodeAllQiugou = (TextView) inflater.findViewById(R.id.fragment_wode_all_qiugou);
        mFragmentWodeXunpanzhongImg = (ImageView) inflater.findViewById(R.id.fragment_wode_xunpanzhong_img);
        mFragmentWodeXunpanzhongText = (TextView) inflater.findViewById(R.id.fragment_wode_xunpanzhong_text);
        mFragmentWodeDaiquerenbaojiaImg = (ImageView) inflater.findViewById(R.id.fragment_wode_daiquerenbaojia_img);
        mFragmentWodeDaiquerenbaojiaText = (TextView) inflater.findViewById(R.id.fragment_wode_daiquerenbaojia_text);
        mFragmentWodeJijiangguoqiImg = (ImageView) inflater.findViewById(R.id.fragment_wode_jijiangguoqi_img);
        mFragmentWodeJijiangguoqiText = (TextView) inflater.findViewById(R.id.fragment_wode_jijiangguoqi_text);
        mFragmentWodeLixikefu = (ConstraintLayout) inflater.findViewById(R.id.fragment_wode_lixikefu);
        mFragmentWodeXunpanzhongImg.setOnClickListener(this);
        mFragmentWodeXunpanzhongText.setOnClickListener(this);

        mFragmentWodeDaiquerenbaojiaImg.setOnClickListener(this);
        mFragmentWodeDaiquerenbaojiaText.setOnClickListener(this);

        mFragmentWodeJijiangguoqiImg.setOnClickListener(this);
        mFragmentWodeJijiangguoqiText.setOnClickListener(this);

        mFragmentWodeAllQiugou.setOnClickListener(this);
        textView90.setOnClickListener(this);
        mToolbarText = inflater.findViewById(R.id.toolbar_text);
        mToolbarText.setVisibility(View.VISIBLE);
        mToolbarText.setOnClickListener(this);
        mFragmentWodeExchangeShenfen = (TextView) inflater.findViewById(R.id.fragment_wode_exchange_shenfen);
        mFragmentWodeExchangeShenfen.setOnClickListener(this);
        mFragmentWodeWodeqiugou = (TextView) inflater.findViewById(R.id.fragment_wode_wodeqiugou);
        mToolbarLayout = (RelativeLayout) inflater.findViewById(R.id.include8);
        mToolbarLayout.setBackground(getResources().getDrawable(R.drawable.button_jianbianbeijing));
        mFragmentWodeMaijiajieshouImg = (ImageView) inflater.findViewById(R.id.fragment_wode_maijiajieshou_img);
        mFragmentWodeMaijiajieshouText = (TextView) inflater.findViewById(R.id.fragment_wode_maijiajieshou_text);
        mFragmentWodeMaijiajieshouImg.setOnClickListener(this);
        mFragmentWodeMaijiajieshouText.setOnClickListener(this);
        xunpan = new QBadgeView(getContext()).bindTarget(mFragmentWodeXunpanzhongImg)
                .setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeTextSize(8, true);

        daiquerenbaojia = new QBadgeView(getContext()).bindTarget(mFragmentWodeDaiquerenbaojiaImg)
                .setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeTextSize(8, true);

        jijiangguoqi = new QBadgeView(getContext()).bindTarget(mFragmentWodeJijiangguoqiImg)
                .setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeTextSize(8, true);

        maijiajieshou = new QBadgeView(getContext()).bindTarget(mFragmentWodeMaijiajieshouImg)
                .setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeTextSize(8, true);
        mFragmentWodeConstraintlayoutSeller = (ConstraintLayout) inflater.findViewById(R.id.fragment_wode_constraintlayout_seller);
        mFragmentWodeConstraintlayoutBuyer = (ConstraintLayout) inflater.findViewById(R.id.fragment_wode_constraintlayout_buyer);
        mFragmentWodeAllQiugouSell = (TextView) inflater.findViewById(R.id.fragment_wode_all_qiugou_sell);
        mFragmentWodeAllQiugouSell.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)

    public void doEventMessage(MessageBean message) {

        if (message.getCode() == MessageBean.Code.DELU) {

            tianxiexinxi();

        }
    }

    private Badge xunpan;
    private Badge daiquerenbaojia;
    private Badge jijiangguoqi;
    private Badge maijiajieshou;

    @Override
    public void onResume() {
        super.onResume();
        if (StringUtils.equals(SPUtils.getInstance().getString("isLogin"), "true")) {
            tianxiexinxi();

            getAllCount();
            //买家
            if (StringUtils.equals(SPUtils.getInstance().getString("identity"), "1")) {
                mFragmentWodeConstraintlayoutBuyer.setVisibility(View.VISIBLE);
                mFragmentWodeConstraintlayoutSeller.setVisibility(View.INVISIBLE);
                //卖家
            } else if (StringUtils.equals(SPUtils.getInstance().getString("identity"), "2")) {
                mFragmentWodeConstraintlayoutSeller.setVisibility(View.VISIBLE);
                mFragmentWodeConstraintlayoutBuyer.setVisibility(View.INVISIBLE);
            }

        }

    }

    private void getAllCount() {
        OkGo.<String>get(ServerInfo.TESTSERVER + InterfaceInfo.GETALLCOUNT)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("token", SPUtils.getInstance().getString("token"))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            LogUtils.v("GETALLCOUNT", response.body());
                            if (response.code() == 200) {
                                JSONObject jsonObject = JSONObject.parseObject(response.body());
                                if (StringUtils.equals(jsonObject.getString("code"), "SUCCESS")) {
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    NumberBean numberBean = data.toJavaObject(NumberBean.class);
                                    xunpan.setBadgeNumber(numberBean.getIsEnquiryCount());
                                    daiquerenbaojia.setBadgeNumber(numberBean.getWaitSureCount());
                                    jijiangguoqi.setBadgeNumber(numberBean.getMyExpireCount());
                                    maijiajieshou.setBadgeNumber(numberBean.getMyAcceptOfferCount());
                                    mFragmentWodeLishibaojia.setText(numberBean.getOfferTimes()+"");
                                    mFragmentWodeLishiqiugou.setText(numberBean.getEnquiryTimes()+"");
                                    return;

                                } else
                                    SignAndTokenUtil.checkSignAndToken(activity, jsonObject);

                            } else {

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });


    }

    private void tianxiexinxi() {
        if (StringUtils.isEmpty(SPUtils.getInstance().getString("companyName"))) {
            mFragmentWodeName.setText(SPUtils.getInstance().getString("loginName"));
            mFragmentWodeShenfen.setText("个人用户");
        } else {
            mFragmentWodeName.setText(SPUtils.getInstance().getString("companyName"));
            mFragmentWodeShenfen.setText("企业用户");
        }

        if (StringUtils.equals(SPUtils.getInstance().getString("identity"), "1")) {
            mFragmentWodeZhongxin.setText("买家中心");
            mFragmentWodeZhongxinImage.setImageDrawable(getResources().getDrawable(R.mipmap.buy));
            mFragmentWodeWodeqiugou.setText("我的求购");
        } else if (StringUtils.equals(SPUtils.getInstance().getString("identity"), "2")) {
            mFragmentWodeZhongxin.setText("卖家中心");
            mFragmentWodeZhongxinImage.setImageDrawable(getResources().getDrawable(R.mipmap.sell));
            mFragmentWodeWodeqiugou.setText("我的报价");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            //我的头像
            case R.id.fragment_wode_image:
                break;
            case R.id.fragment_wode_xunpanzhong_img:
                jumpToDaichuli(1);
                break;
            case R.id.fragment_wode_xunpanzhong_text:
                jumpToDaichuli(1);
                break;
            case R.id.fragment_wode_daiquerenbaojia_img:
                jumpToDaichuli(2);
                break;
            case R.id.fragment_wode_daiquerenbaojia_text:
                jumpToDaichuli(2);
                break;
            case R.id.fragment_wode_jijiangguoqi_img:
                jumpToDaichuli(3);
                break;
            case R.id.fragment_wode_jijiangguoqi_text:
                jumpToDaichuli(3);
                break;
            case R.id.fragment_wode_maijiajieshou_img:
                jumpToMaijiajieshou();
                break;
            case R.id.fragment_wode_maijiajieshou_text:
                jumpToMaijiajieshou();
                break;
            //我的全部报价
            case R.id.fragment_wode_all_qiugou_sell:
                ActivityUtils.startActivity(WodebaojiaActivity.class);
                break;
            //我的求购
            case R.id.fragment_wode_all_qiugou:
                ActivityUtils.startActivity(WodeqiugouActivity.class);
                break;
            //设置
            case R.id.toolbar_text:
                ActivityUtils.startActivity(SettingActivity.class);
                break;
            case R.id.fragment_wode_exchange_shenfen:
                ActivityUtils.startActivity(QiehuanshenfenActivity.class);
                break;
            case R.id.textView90:
                ActivityUtils.startActivity(QiehuanshenfenActivity.class);
                break;
        }
    }

    private void jumpToMaijiajieshou() {
        Intent intent = new Intent(getContext(), DaichulibaojiaActivity.class);
        ActivityUtils.startActivity(intent);

    }

    private void jumpToDaichuli(int mudi) {
        Intent intent = new Intent(getContext(), DaichuliqiugouActivity.class);
        intent.putExtra("mudi", mudi);
        ActivityUtils.startActivity(intent);
    }
}
