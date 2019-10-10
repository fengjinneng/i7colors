package com.company.qcy.fragment.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.MyCommonUtil;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.Utils.UserUtil;
import com.company.qcy.base.BaseFragment;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.bean.qiugou.NumberBean;
import com.company.qcy.ui.activity.qiugoudating.DaichulibaojiaActivity;
import com.company.qcy.ui.activity.qiugoudating.DaichuliqiugouActivity;
import com.company.qcy.ui.activity.qiugoudating.WodebaojiaActivity;
import com.company.qcy.ui.activity.qiugoudating.WodeqiugouActivity;
import com.company.qcy.ui.activity.user.LianxikefuActivity;
import com.company.qcy.ui.activity.user.SettingActivity;
import com.company.qcy.ui.activity.user.ZhanghaozhongxinActivity;
import com.company.qcy.ui.activity.zhuji.WodeJiejuefanganListActivity;
import com.company.qcy.ui.activity.zhuji.WodeZhujiListActivity;
import com.githang.statusbar.StatusBarCompat;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.umeng.analytics.MobclickAgent;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class WodeFragment extends BaseFragment implements View.OnClickListener {
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


    /**
     * 设置
     */
    private TextView mToolbarText;

    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private Activity activity;

    /**
     * 买家中心
     */
    private TextView mFragmentWodeBuyerZhongxin;
    /**
     * 卖家中心
     */
    private TextView mFragmentWodeSellerZhongxin;

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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
        StatusBarCompat.setStatusBarColor(getActivity(), getActivity().getResources().getColor(R.color.chunhongse), false);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            StatusBarCompat.setStatusBarColor(getActivity(), getActivity().getResources().getColor(R.color.chunhongse), false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_wode, null, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("我的");
    }

    private void initView(View inflater) {
        mFragmentWodeBuyerZhongxin = (TextView) inflater.findViewById(R.id.fragment_wode_buyer_zhongxin);
        mFragmentWodeSellerZhongxin = (TextView) inflater.findViewById(R.id.fragment_wode_seller_zhongxin);
        mFragmentWodeImage = (ImageView) inflater.findViewById(R.id.fragment_wode_image);
        mFragmentWodeImage.setOnClickListener(this);
        mFragmentWodeName = (TextView) inflater.findViewById(R.id.fragment_wode_name);
        mFragmentWodeShenfen = (TextView) inflater.findViewById(R.id.fragment_wode_shenfen);
        mFragmentWodeLishiqiugou = (TextView) inflater.findViewById(R.id.fragment_wode_lishiqiugou);
        mFragmentWodeLishibaojia = (TextView) inflater.findViewById(R.id.fragment_wode_lishibaojia);


        mToolbarText = inflater.findViewById(R.id.toolbar_text);
        mToolbarText.setVisibility(View.VISIBLE);
        mToolbarText.setOnClickListener(this);
        mToolbarTitle = (TextView) inflater.findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) inflater.findViewById(R.id.toolbar_back);
        mToolbarBack.setVisibility(View.INVISIBLE);
        mToolbarTitle.setText("个人中心");


//        xunpan = new QBadgeView(getContext()).bindTarget(mFragmentWodeXunpanzhongImg)
//                .setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeTextSize(8, true);
//
//        daiquerenbaojia = new QBadgeView(getContext()).bindTarget(mFragmentWodeDaiquerenbaojiaImg)
//                .setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeTextSize(8, true);
//
//        jijiangguoqi = new QBadgeView(getContext()).bindTarget(mFragmentWodeJijiangguoqiImg)
//                .setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeTextSize(8, true);
//
//        maijiajieshou = new QBadgeView(getContext()).bindTarget(mFragmentWodeMaijiajieshouImg)
//                .setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeTextSize(8, true);


        allQiuGou = (TextView) inflater.findViewById(R.id.fragment_wode_buyer_qiugou_all);
        allQiuGou.setOnClickListener(this);
        qiugouXunpanzhongText = (TextView) inflater.findViewById(R.id.fragment_wode_buyer_qiugou_xunpanzhong_text);
        qiugouXunpanzhongImage = (ImageView) inflater.findViewById(R.id.fragment_wode_buyer_qiugou_xunpanzhong_img);
        qiugouXunpanzhongText.setOnClickListener(this);
        qiugouXunpanzhongImage.setOnClickListener(this);

        qiugouDaiquerenbaojiaText = (TextView) inflater.findViewById(R.id.fragment_wode_buyer_qiugou_daiquerenbaojia_text);
        qiugouDaiquerenbaojiaImage = (ImageView) inflater.findViewById(R.id.fragment_wode_buyer_qiugou_daiquerenbaojia_img);
        qiugouDaiquerenbaojiaText.setOnClickListener(this);
        qiugouDaiquerenbaojiaImage.setOnClickListener(this);

        qiugouJijiangguoqiText = (TextView) inflater.findViewById(R.id.fragment_wode_buyer_qiugou_jijiangguoqi_text);
        qiugouJijiangguoqiImage = (ImageView) inflater.findViewById(R.id.fragment_wode_buyer_qiugou_jijiangguoqi_img);
        qiugouJijiangguoqiText.setOnClickListener(this);
        qiugouJijiangguoqiImage.setOnClickListener(this);

        allZhujiDingzhi = (TextView) inflater.findViewById(R.id.fragment_wode_buyer_zhujidingzhi_all);
        allZhujiDingzhi.setOnClickListener(this);

        zhujidingzhiShiyangzhongText = (TextView) inflater.findViewById(R.id.fragment_wode_buyer_zhujidingzhi_shiyangzhong_text);
        zhujidingzhiShiyangzhongImage = (ImageView) inflater.findViewById(R.id.fragment_wode_buyer_zhujidingzhi_shiyangzhong_img);

        zhujidingzhiShiyangzhongText.setOnClickListener(this);
        zhujidingzhiShiyangzhongImage.setOnClickListener(this);

        allBaojia = (TextView) inflater.findViewById(R.id.fragment_wode_seller_baojia_all);
        allBaojia.setOnClickListener(this);

        baojiaMaijiajieshouText = (TextView) inflater.findViewById(R.id.fragment_wode_seller_baojia_maijiayijieshou_text);
        baojiaMaijiajieshouImage = inflater.findViewById(R.id.fragment_wode_seller_baojia_maijiayijieshou_img);
        baojiaMaijiajieshouText.setOnClickListener(this);
        baojiaMaijiajieshouImage.setOnClickListener(this);

        allFangan = (TextView) inflater.findViewById(R.id.fragment_wode_seller_zhujidingzhifangan_all_fangan);
        allFangan.setOnClickListener(this);

        fanganMaijiajieshouText = inflater.findViewById(R.id.fragment_wode_seller_zhujidingzhifangan_maijiajieshou_text);
        fanganMaijiajieshouImage = inflater.findViewById(R.id.fragment_wode_seller_zhujidingzhifangan_maijiajieshou_img);
        fanganMaijiajieshouText.setOnClickListener(this);
        fanganMaijiajieshouImage.setOnClickListener(this);

        buyerLayout = inflater.findViewById(R.id.fragment_wode_buyer);
        sellerLayout = inflater.findViewById(R.id.fragment_wode_seller);

        buyerZhongxin = inflater.findViewById(R.id.fragment_wode_buyer_layout);
        sellerZhongxin = inflater.findViewById(R.id.fragment_wode_seller_layout);
        buyerZhongxin.setOnClickListener(this);
        sellerZhongxin.setOnClickListener(this);

        lianxikefu = inflater.findViewById(R.id.fragment_wode_lianxikefu);
        lianxikefu.setOnClickListener(this);
    }

    private TextView allQiuGou;
    private TextView qiugouXunpanzhongText;
    private ImageView qiugouXunpanzhongImage;

    private TextView qiugouDaiquerenbaojiaText;
    private ImageView qiugouDaiquerenbaojiaImage;

    private TextView qiugouJijiangguoqiText;
    private ImageView qiugouJijiangguoqiImage;

    private TextView allZhujiDingzhi;

    private TextView zhujidingzhiShiyangzhongText;

    private ImageView zhujidingzhiShiyangzhongImage;

    private TextView allBaojia;

    private TextView baojiaMaijiajieshouText;
    private ImageView baojiaMaijiajieshouImage;

    private TextView allFangan;

    private TextView fanganMaijiajieshouText;
    private ImageView fanganMaijiajieshouImage;

    private ConstraintLayout buyerLayout;
    private ConstraintLayout sellerLayout;

    private ConstraintLayout buyerZhongxin;
    private ConstraintLayout sellerZhongxin;
    private ConstraintLayout lianxikefu;



    @Override
    public void onDestroy() {
        super.onDestroy();
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


    @Override
    public void onRec(MessageBean messageBean) {
        super.onRec(messageBean);
        switch (messageBean.getCode()) {
            case MessageBean.Code.DELU:
                tianxiexinxi();
                break;
            case MessageBean.Code.CHANGEPERSONHEADIMG:
                MyCommonUtil.jiazaitouxiang(getActivity(), messageBean.getMeaasge(), mFragmentWodeImage);
                break;
        }
    }

//    private Badge xunpan;
//    private Badge daiquerenbaojia;
//    private Badge jijiangguoqi;
//    private Badge maijiajieshou;

    @Override
    public void onResume() {
        super.onResume();

        MobclickAgent.onPageStart("我的");

        if (UserUtil.isLogin()) {
            tianxiexinxi();

            MyCommonUtil.jiazaitouxiang(getActivity(), SPUtils.getInstance().getString("photo"), mFragmentWodeImage);
            getAllCount();
            //买家
            if (StringUtils.equals(SPUtils.getInstance().getString("identity"), "1")) {
                buyerLayout.setVisibility(View.VISIBLE);
                sellerLayout.setVisibility(View.INVISIBLE);
                mFragmentWodeBuyerZhongxin.setTextSize(18);
                mFragmentWodeBuyerZhongxin.setTextColor(getContext().getResources().getColor(R.color.chunhongse));


                //卖家
            } else if (StringUtils.equals(SPUtils.getInstance().getString("identity"), "2")) {
                sellerLayout.setVisibility(View.VISIBLE);
                buyerLayout.setVisibility(View.INVISIBLE);
                mFragmentWodeSellerZhongxin.setTextSize(18);
                mFragmentWodeSellerZhongxin.setTextColor(getContext().getResources().getColor(R.color.chunhongse));


            }

        }
    }


    private void getAllCount() {
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.GETALLCOUNT)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("token", SPUtils.getInstance().getString("token"));

        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("GETALLCOUNT", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");

                        if (StringUtils.equals(jsonObject.getString("code"), "SUCCESS")) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            NumberBean numberBean = data.toJavaObject(NumberBean.class);

                            setNumberInfo(numberBean);
//                            xunpan.setBadgeNumber(numberBean.getIsEnquiryCount());
//                            daiquerenbaojia.setBadgeNumber(numberBean.getWaitSureCount());
//                            jijiangguoqi.setBadgeNumber(numberBean.getMyExpireCount());
//                            maijiajieshou.setBadgeNumber(numberBean.getMyAcceptOfferCount());
                            mFragmentWodeLishibaojia.setText(numberBean.getOfferTimes() + "");
                            mFragmentWodeLishiqiugou.setText(numberBean.getEnquiryTimes() + "");
                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(getActivity(), request, this);
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

    private void setNumberInfo(NumberBean numberBean) {

        //询盘中
        if (numberBean.getIsEnquiryCount() == 0) {
            qiugouXunpanzhongImage.setImageDrawable(getResources().getDrawable(R.mipmap.wode_qiugouzhong_black));
            qiugouXunpanzhongText.setText("询盘中(0)");
            qiugouXunpanzhongText.setTextColor(getResources().getColor(R.color.putongwenben));
        } else {
            qiugouXunpanzhongImage.setImageDrawable(getResources().getDrawable(R.mipmap.wode_qiugouzhong_red));
            qiugouXunpanzhongText.setText("询盘中"+"("+numberBean.getIsEnquiryCount()+")");
            qiugouXunpanzhongText.setTextColor(getResources().getColor(R.color.chunhongse));
        }

        //待确认报价
        if (numberBean.getWaitSureCount() == 0) {
            qiugouDaiquerenbaojiaImage.setImageDrawable(getResources().getDrawable(R.mipmap.wode_daiqurenbaojia_black));
            qiugouDaiquerenbaojiaText.setText("待确认报价(0)");
            qiugouDaiquerenbaojiaText.setTextColor(getResources().getColor(R.color.putongwenben));
        } else {
            qiugouDaiquerenbaojiaImage.setImageDrawable(getResources().getDrawable(R.mipmap.wode_daiqurenbaojia_red));
            qiugouDaiquerenbaojiaText.setText("待确认报价"+"("+numberBean.getWaitSureCount()+")");
            qiugouDaiquerenbaojiaText.setTextColor(getResources().getColor(R.color.chunhongse));

        }

        //即将过期
        if (numberBean.getMyExpireCount() == 0) {
            qiugouJijiangguoqiImage.setImageDrawable(getResources().getDrawable(R.mipmap.wode_jijiangguoqi_black));
            qiugouJijiangguoqiText.setText("即将过期(0)");
            qiugouJijiangguoqiText.setTextColor(getResources().getColor(R.color.putongwenben));
        } else {
            qiugouJijiangguoqiImage.setImageDrawable(getResources().getDrawable(R.mipmap.wode_jijiangguoqi_red));
            qiugouJijiangguoqiText.setText("即将过期"+"("+numberBean.getMyExpireCount()+")");
            qiugouJijiangguoqiText.setTextColor(getResources().getColor(R.color.chunhongse));
        }

        //买家已接受
        if (numberBean.getMyAcceptOfferCount() == 0) {
            baojiaMaijiajieshouImage.setImageDrawable(getResources().getDrawable(R.mipmap.wode_maijiayijieshou_black));
            baojiaMaijiajieshouText.setText("买家已接受(0)");
            baojiaMaijiajieshouText.setTextColor(getResources().getColor(R.color.putongwenben));
        } else {
            baojiaMaijiajieshouImage.setImageDrawable(getResources().getDrawable(R.mipmap.wode_maijiayijieshou_red));
            baojiaMaijiajieshouText.setText("买家已接受"+"("+numberBean.getMyAcceptOfferCount()+")");
            baojiaMaijiajieshouText.setTextColor(getResources().getColor(R.color.chunhongse));
        }

        //定制中
        if (numberBean.getZhujiDiyingCount() == 0) {
            zhujidingzhiShiyangzhongImage.setImageDrawable(getResources().getDrawable(R.mipmap.wode_shiyangzhong_black));
            zhujidingzhiShiyangzhongText.setText("试样中(0)");
            zhujidingzhiShiyangzhongText.setTextColor(getResources().getColor(R.color.putongwenben));
        } else {
            zhujidingzhiShiyangzhongImage.setImageDrawable(getResources().getDrawable(R.mipmap.wode_shiyangzhong_res));
            zhujidingzhiShiyangzhongText.setText("试样中"+"("+numberBean.getZhujiDiyingCount()+")");
            zhujidingzhiShiyangzhongText.setTextColor(getResources().getColor(R.color.chunhongse));
        }

        //待接收方案
        if (numberBean.getZhujiSolutionAcceptCount() == 0) {
            fanganMaijiajieshouImage.setImageDrawable(getResources().getDrawable(R.mipmap.wode_maijiayijieshou_black));
            fanganMaijiajieshouText.setText("买家已接受(0)");
            fanganMaijiajieshouText.setTextColor(getResources().getColor(R.color.putongwenben));
        } else {
            fanganMaijiajieshouImage.setImageDrawable(getResources().getDrawable(R.mipmap.wode_maijiayijieshou_red));
            fanganMaijiajieshouText.setText("买家已接受"+"("+numberBean.getZhujiSolutionAcceptCount()+")");
            fanganMaijiajieshouText.setTextColor(getResources().getColor(R.color.chunhongse));
        }

    }

    private void tianxiexinxi() {
        if (StringUtils.isEmpty(SPUtils.getInstance().getString("companyName"))) {
            mFragmentWodeName.setText(SPUtils.getInstance().getString("loginName"));
            mFragmentWodeShenfen.setText("个人用户");
        } else {
            mFragmentWodeName.setText(SPUtils.getInstance().getString("loginName"));
            mFragmentWodeShenfen.setText("企业用户");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            //我的头像
            case R.id.fragment_wode_image:
                ActivityUtils.startActivity(ZhanghaozhongxinActivity.class);
                break;
            case R.id.fragment_wode_buyer_qiugou_xunpanzhong_img:
                jumpToDaichuli(1);
                break;
            case R.id.fragment_wode_buyer_qiugou_xunpanzhong_text:
                jumpToDaichuli(1);
                break;
            case R.id.fragment_wode_buyer_qiugou_daiquerenbaojia_img:
                jumpToDaichuli(2);
                break;
            case R.id.fragment_wode_buyer_qiugou_daiquerenbaojia_text:
                jumpToDaichuli(2);
                break;
            case R.id.fragment_wode_buyer_qiugou_jijiangguoqi_img:
                jumpToDaichuli(3);
                break;
            case R.id.fragment_wode_buyer_qiugou_jijiangguoqi_text:
                jumpToDaichuli(3);
                break;
            case R.id.fragment_wode_seller_baojia_maijiayijieshou_img:
                jumpToMaijiajieshou();
                break;
            case R.id.fragment_wode_seller_baojia_maijiayijieshou_text:
                jumpToMaijiajieshou();
                break;
            //我的全部报价
            case R.id.fragment_wode_seller_baojia_all:
                ActivityUtils.startActivity(WodebaojiaActivity.class);
                break;
            //我的求购
            case R.id.fragment_wode_buyer_qiugou_all:
                ActivityUtils.startActivity(WodeqiugouActivity.class);
                break;
            //设置
            case R.id.toolbar_text:
                ActivityUtils.startActivity(SettingActivity.class);
                break;

            case R.id.fragment_wode_lianxikefu:
                ActivityUtils.startActivity(LianxikefuActivity.class);
                break;

            case R.id.fragment_wode_buyer_layout:
                buyerLayout.setVisibility(View.VISIBLE);
                sellerLayout.setVisibility(View.INVISIBLE);
                SPUtils.getInstance().put("identity", "1");
                mFragmentWodeBuyerZhongxin.setTextSize(18);
                mFragmentWodeBuyerZhongxin.setTextColor(getContext().getResources().getColor(R.color.chunhongse));
                mFragmentWodeSellerZhongxin.setTextSize(15);
                mFragmentWodeSellerZhongxin.setTextColor(getContext().getResources().getColor(R.color.putongwenben));

                break;

            case R.id.fragment_wode_seller_layout:
                sellerLayout.setVisibility(View.VISIBLE);
                buyerLayout.setVisibility(View.INVISIBLE);
                SPUtils.getInstance().put("identity", "2");
                mFragmentWodeSellerZhongxin.setTextSize(18);
                mFragmentWodeSellerZhongxin.setTextColor(getContext().getResources().getColor(R.color.chunhongse));
                mFragmentWodeBuyerZhongxin.setTextSize(15);
                mFragmentWodeBuyerZhongxin.setTextColor(getContext().getResources().getColor(R.color.putongwenben));

                break;

            case R.id.fragment_wode_buyer_zhujidingzhi_all:
                ActivityUtils.startActivity(WodeZhujiListActivity.class);

                break;

            case R.id.fragment_wode_seller_zhujidingzhifangan_all_fangan:

                ActivityUtils.startActivity(WodeJiejuefanganListActivity.class);
                break;

            case R.id.fragment_wode_buyer_zhujidingzhi_shiyangzhong_img:

                Intent i1 = new Intent(getActivity(),WodeZhujiListActivity.class);
                i1.putExtra("page",1);
                ActivityUtils.startActivity(i1);

                break;
            case R.id.fragment_wode_buyer_zhujidingzhi_shiyangzhong_text:
                Intent i2 = new Intent(getActivity(),WodeZhujiListActivity.class);
                i2.putExtra("page",1);
                ActivityUtils.startActivity(i2);
                break;

            case R.id.fragment_wode_seller_zhujidingzhifangan_maijiajieshou_text:

                Intent i3 = new Intent(getActivity(),WodeJiejuefanganListActivity.class);
                i3.putExtra("page",2);
                ActivityUtils.startActivity(i3);
                break;
            case R.id.fragment_wode_seller_zhujidingzhifangan_maijiajieshou_img:

                Intent i4 = new Intent(getActivity(),WodeJiejuefanganListActivity.class);
                i4.putExtra("page",2);
                ActivityUtils.startActivity(i4);
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
