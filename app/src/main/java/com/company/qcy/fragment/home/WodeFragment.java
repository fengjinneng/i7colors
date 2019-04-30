package com.company.qcy.fragment.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.MyCommonUtil;
import com.company.qcy.Utils.MyStatusBarUtil;
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
import com.githang.statusbar.StatusBarCompat;
import com.jaeger.library.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

import static com.blankj.utilcode.util.BarUtils.getStatusBarHeight;

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
     * 我的求购
     */
    private TextView mFragmentWodeWodeqiugou;
    private ConstraintLayout mToolbarLayout;

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

    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private ConstraintLayout mFragmentWodeBuyerLayout;
    private ConstraintLayout mFragmentWodeSellerLayout;
    /**
     * 买家中心
     */
    private TextView mFragmentWodeBuyerZhongxin;
    /**
     * 卖家中心
     */
    private TextView mFragmentWodeSellerZhongxin;
    /**
     * 优惠展销
     */
    private Button mYouhuizhanxiao;
    /**
     * 采购联盟
     */
    private Button mCaigoulianmeng;

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
        StatusBarCompat.setStatusBarColor(getActivity(), getActivity().getResources().getColor(R.color.chunhongse),false);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            StatusBarCompat.setStatusBarColor(getActivity(), getActivity().getResources().getColor(R.color.chunhongse),false);
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
        mFragmentWodeBuyerZhongxin = (TextView) inflater.findViewById(R.id.fragment_wode_buyer_zhongxin);
        mFragmentWodeSellerZhongxin = (TextView) inflater.findViewById(R.id.fragment_wode_seller_zhongxin);
        mFragmentWodeImage = (ImageView) inflater.findViewById(R.id.fragment_wode_image);
        mFragmentWodeImage.setOnClickListener(this);
        mFragmentWodeName = (TextView) inflater.findViewById(R.id.fragment_wode_name);
        mFragmentWodeShenfen = (TextView) inflater.findViewById(R.id.fragment_wode_shenfen);
        mFragmentWodeLishiqiugou = (TextView) inflater.findViewById(R.id.fragment_wode_lishiqiugou);
        mFragmentWodeLishibaojia = (TextView) inflater.findViewById(R.id.fragment_wode_lishibaojia);
        mFragmentWodeAllQiugou = (TextView) inflater.findViewById(R.id.fragment_wode_all_qiugou);
        mFragmentWodeXunpanzhongImg = (ImageView) inflater.findViewById(R.id.fragment_wode_xunpanzhong_img);
        mFragmentWodeXunpanzhongText = (TextView) inflater.findViewById(R.id.fragment_wode_xunpanzhong_text);
        mFragmentWodeDaiquerenbaojiaImg = (ImageView) inflater.findViewById(R.id.fragment_wode_daiquerenbaojia_img);
        mFragmentWodeDaiquerenbaojiaText = (TextView) inflater.findViewById(R.id.fragment_wode_daiquerenbaojia_text);
        mFragmentWodeJijiangguoqiImg = (ImageView) inflater.findViewById(R.id.fragment_wode_jijiangguoqi_img);
        mFragmentWodeJijiangguoqiText = (TextView) inflater.findViewById(R.id.fragment_wode_jijiangguoqi_text);
        mFragmentWodeLixikefu = (ConstraintLayout) inflater.findViewById(R.id.fragment_wode_lixikefu);
        mFragmentWodeBuyerLayout = (ConstraintLayout) inflater.findViewById(R.id.fragment_wode_buyer_layout);
        mFragmentWodeSellerLayout = (ConstraintLayout) inflater.findViewById(R.id.fragment_wode_seller_layout);
        mFragmentWodeBuyerLayout.setOnClickListener(this);
        mFragmentWodeSellerLayout.setOnClickListener(this);
        mFragmentWodeXunpanzhongImg.setOnClickListener(this);
        mFragmentWodeXunpanzhongText.setOnClickListener(this);

        mFragmentWodeDaiquerenbaojiaImg.setOnClickListener(this);
        mFragmentWodeDaiquerenbaojiaText.setOnClickListener(this);

        mFragmentWodeJijiangguoqiImg.setOnClickListener(this);
        mFragmentWodeJijiangguoqiText.setOnClickListener(this);

        mFragmentWodeAllQiugou.setOnClickListener(this);
        mToolbarText = inflater.findViewById(R.id.toolbar_text);
        mToolbarText.setVisibility(View.VISIBLE);
        mToolbarText.setOnClickListener(this);
        mFragmentWodeWodeqiugou = (TextView) inflater.findViewById(R.id.fragment_wode_wodeqiugou);
        mToolbarLayout = (ConstraintLayout) inflater.findViewById(R.id.include8);
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
        mFragmentWodeLixikefu = (ConstraintLayout) inflater.findViewById(R.id.fragment_wode_lixikefu);
        mFragmentWodeLixikefu.setOnClickListener(this);
        mToolbarTitle = (TextView) inflater.findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) inflater.findViewById(R.id.toolbar_back);
        mToolbarBack.setVisibility(View.INVISIBLE);
        mToolbarText = (TextView) inflater.findViewById(R.id.toolbar_text);
        mToolbarText.setOnClickListener(this);
        mToolbarTitle.setText("个人中心");


//        mYouhuizhanxiao = (Button) inflater.findViewById(R.id.youhuizhanxiao);
//        mYouhuizhanxiao.setOnClickListener(this);
//        mCaigoulianmeng = (Button) inflater.findViewById(R.id.caigoulianmeng);
//        mCaigoulianmeng.setOnClickListener(this);
    }

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
                MyCommonUtil.jiazaitouxiang(getActivity(),messageBean.getMeaasge(),mFragmentWodeImage);
                break;
        }
    }

    private Badge xunpan;
    private Badge daiquerenbaojia;
    private Badge jijiangguoqi;
    private Badge maijiajieshou;

    @Override
    public void onResume() {
        super.onResume();

        if (UserUtil.isLogin()) {
            tianxiexinxi();

            MyCommonUtil.jiazaitouxiang(getActivity(),SPUtils.getInstance().getString("photo"),mFragmentWodeImage);
            getAllCount();
            //买家
            if (StringUtils.equals(SPUtils.getInstance().getString("identity"), "1")) {
                mFragmentWodeConstraintlayoutBuyer.setVisibility(View.VISIBLE);
                mFragmentWodeConstraintlayoutSeller.setVisibility(View.INVISIBLE);
                mFragmentWodeBuyerZhongxin.setTextSize(18);
                mFragmentWodeBuyerZhongxin.setTextColor(getContext().getResources().getColor(R.color.chunhongse));
                //卖家
            } else if (StringUtils.equals(SPUtils.getInstance().getString("identity"), "2")) {
                mFragmentWodeConstraintlayoutSeller.setVisibility(View.VISIBLE);
                mFragmentWodeConstraintlayoutBuyer.setVisibility(View.INVISIBLE);
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
                            xunpan.setBadgeNumber(numberBean.getIsEnquiryCount());
                            daiquerenbaojia.setBadgeNumber(numberBean.getWaitSureCount());
                            jijiangguoqi.setBadgeNumber(numberBean.getMyExpireCount());
                            maijiajieshou.setBadgeNumber(numberBean.getMyAcceptOfferCount());
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

            case R.id.fragment_wode_lixikefu:
                ActivityUtils.startActivity(LianxikefuActivity.class);
                break;

            case R.id.fragment_wode_buyer_layout:
//                mFragmentWodeBuyerLayout.setBackgroundColor(getResources().getColor(R.color.fengexian));
//                mFragmentWodeSellerLayout.setBackgroundColor(getResources().getColor(R.color.baise));
                mFragmentWodeConstraintlayoutBuyer.setVisibility(View.VISIBLE);
                mFragmentWodeConstraintlayoutSeller.setVisibility(View.INVISIBLE);
                SPUtils.getInstance().put("identity", "1");
                mFragmentWodeWodeqiugou.setText("我的求购");
                mFragmentWodeBuyerZhongxin.setTextSize(18);
                mFragmentWodeBuyerZhongxin.setTextColor(getContext().getResources().getColor(R.color.chunhongse));

                mFragmentWodeSellerZhongxin.setTextSize(15);
                mFragmentWodeSellerZhongxin.setTextColor(getContext().getResources().getColor(R.color.putongwenben));
                break;

            case R.id.fragment_wode_seller_layout:
//                mFragmentWodeSellerLayout.setBackgroundColor(getResources().getColor(R.color.fengexian));
//                mFragmentWodeSellerLayout.setBackgroundColor(getResources().getColor(R.color.baise));
                mFragmentWodeConstraintlayoutSeller.setVisibility(View.VISIBLE);
                mFragmentWodeConstraintlayoutBuyer.setVisibility(View.INVISIBLE);
                SPUtils.getInstance().put("identity", "2");
                mFragmentWodeWodeqiugou.setText("我的报价");
                mFragmentWodeSellerZhongxin.setTextSize(18);
                mFragmentWodeSellerZhongxin.setTextColor(getContext().getResources().getColor(R.color.chunhongse));

                mFragmentWodeBuyerZhongxin.setTextSize(15);
                mFragmentWodeBuyerZhongxin.setTextColor(getContext().getResources().getColor(R.color.putongwenben));
                break;
//            case R.id.youhuizhanxiao:
//                ActivityUtils.startActivity(YouhuizhanxiaoActivity.class);
//                break;
//            case R.id.caigoulianmeng:
//                ActivityUtils.startActivity(CaigoulianmengActivity.class);
//                break;
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
