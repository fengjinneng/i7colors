package com.company.qcy.fragment.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.company.qcy.R;
import com.company.qcy.base.BaseFragment;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.fragment.xiaoxi.SellerxiaoxiFragment;
import com.company.qcy.fragment.xiaoxi.BuyerxiaoxiFragment;
import com.company.qcy.fragment.xiaoxi.XitongxiaoxiFragment;
import com.githang.statusbar.StatusBarCompat;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * A simple {@link Fragment} subclass.
 */
public class XiaoxiFragment extends BaseFragment implements View.OnClickListener {

    Context context;
    Activity activity;
    private FrameLayout container;
    private ImageView mFragmentToutiaoBuyerImg;
    /**
     * 买家消息
     */
    private TextView mFragmentToutiaoBuyerText;
    private ConstraintLayout mFragmentToutiaoBuyer;
    private ImageView mFragmentToutiaoSellerImg;
    /**
     * 卖家消息
     */
    private TextView mFragmentToutiaoSellerText;
    private ConstraintLayout mFragmentToutiaoSeller;
    private ImageView mFragmentToutiaoXitongxiaoxiImg;
    /**
     * 系统消息
     */
    private TextView mFragmentToutiaoXitongxiaoxiText;
    private ConstraintLayout mFragmentToutiaoXitongxiaoxi;

    public XiaoxiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_toutiao, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(getActivity(), getActivity().getResources().getColor(R.color.baise), false);

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            StatusBarCompat.setStatusBarColor(getActivity(), getActivity().getResources().getColor(R.color.baise), false);
        }
    }


    private BuyerxiaoxiFragment buyerxiFragment;
    private SellerxiaoxiFragment sellerxiaoxiFragment;
    private XitongxiaoxiFragment xitongxiaoxiFragment;

    private Badge messageBadge;


    @Override
    public void onRec(MessageBean messageBean) {
        super.onRec(messageBean);

        switch (messageBean.getCode()) {
            case MessageBean.JPush.DELETELUNCHNUMBER:
                messageBadge.setBadgeNumber(0);
                break;

            case MessageBean.JPush.RECIVENOTIFICATION:
                messageBadge.setBadgeNumber(SPUtils.getInstance().getInt("notification"));
                break;

        }
    }


    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("消息");
        if(SPUtils.getInstance().getInt("notification")>0&&!ObjectUtils.isEmpty(messageBadge)){
            messageBadge.setBadgeNumber(SPUtils.getInstance().getInt("notification"));
        }
    }

    private void initView(View inflate) {


        mFragmentToutiaoBuyerImg = (ImageView) inflate.findViewById(R.id.fragment_toutiao_buyer_img);
        mFragmentToutiaoBuyerText = (TextView) inflate.findViewById(R.id.fragment_toutiao_buyer_text);
        mFragmentToutiaoBuyer = (ConstraintLayout) inflate.findViewById(R.id.fragment_toutiao_buyer);
        mFragmentToutiaoBuyer.setOnClickListener(this);
        mFragmentToutiaoSellerImg = (ImageView) inflate.findViewById(R.id.fragment_toutiao_seller_img);
        mFragmentToutiaoSellerText = (TextView) inflate.findViewById(R.id.fragment_toutiao_seller_text);
        mFragmentToutiaoSeller = (ConstraintLayout) inflate.findViewById(R.id.fragment_toutiao_seller);
        mFragmentToutiaoSeller.setOnClickListener(this);
        mFragmentToutiaoXitongxiaoxiImg = (ImageView) inflate.findViewById(R.id.fragment_toutiao_xitongxiaoxi_img);
        mFragmentToutiaoXitongxiaoxiText = (TextView) inflate.findViewById(R.id.fragment_toutiao_xitongxiaoxi_text);
        mFragmentToutiaoXitongxiaoxi = (ConstraintLayout) inflate.findViewById(R.id.fragment_toutiao_xitongxiaoxi);
        mFragmentToutiaoXitongxiaoxi.setOnClickListener(this);
        container = inflate.findViewById(R.id.fragment_toutiao_container);

        buyerxiFragment = new BuyerxiaoxiFragment();

        mFragmentToutiaoBuyerImg.setImageDrawable(getResources().getDrawable(R.mipmap.buyer_img_red));
        mFragmentToutiaoBuyerText.setTextColor(getResources().getColor(R.color.chunhongse));
        mFragmentToutiaoBuyer.setBackgroundColor(getResources().getColor(R.color.baise));

        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_toutiao_container, buyerxiFragment);
        fragmentTransaction.commit();

        messageBadge = new QBadgeView(getContext()).bindTarget(mFragmentToutiaoXitongxiaoxi)
                .setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeTextSize(10, true).setExactMode(false);
        messageBadge.setBadgeBackgroundColor(getResources().getColor(R.color.chunhongse));
        messageBadge.setBadgeTextColor(getResources().getColor(R.color.baise));
    }


    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("消息");
    }

    @Override
    public void onClick(View v) {
        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (v.getId()) {
            case R.id.fragment_toutiao_buyer:
                if (buyerxiFragment == null) {
                    buyerxiFragment = new BuyerxiaoxiFragment();
                    fragmentTransaction.add(R.id.fragment_toutiao_container, buyerxiFragment);
                }
                hideFragment(fragmentTransaction);
                fragmentTransaction.show(buyerxiFragment);

                mFragmentToutiaoBuyerImg.setImageDrawable(getResources().getDrawable(R.mipmap.buyer_img_red));
                mFragmentToutiaoBuyerText.setTextColor(getResources().getColor(R.color.chunhongse));
                mFragmentToutiaoBuyer.setBackgroundColor(getResources().getColor(R.color.baise));

                mFragmentToutiaoSellerImg.setImageDrawable(getResources().getDrawable(R.mipmap.sell));
                mFragmentToutiaoSellerText.setTextColor(getResources().getColor(R.color.erjibiaoti));
                mFragmentToutiaoSeller.setBackgroundColor(getResources().getColor(R.color.bg_no_photo));

                mFragmentToutiaoXitongxiaoxiImg.setImageDrawable(getResources().getDrawable(R.mipmap.xitongxiaoxi));
                mFragmentToutiaoXitongxiaoxiText.setTextColor(getResources().getColor(R.color.erjibiaoti));
                mFragmentToutiaoXitongxiaoxi.setBackgroundColor(getResources().getColor(R.color.bg_no_photo));

                break;
            case R.id.fragment_toutiao_seller:
                if (sellerxiaoxiFragment == null) {
                    sellerxiaoxiFragment = new SellerxiaoxiFragment();
                    fragmentTransaction.add(R.id.fragment_toutiao_container, sellerxiaoxiFragment);
                }
                hideFragment(fragmentTransaction);
                fragmentTransaction.show(sellerxiaoxiFragment);
                mFragmentToutiaoSellerImg.setImageDrawable(getResources().getDrawable(R.mipmap.seller_img_red));
                mFragmentToutiaoSellerText.setTextColor(getResources().getColor(R.color.chunhongse));
                mFragmentToutiaoSeller.setBackgroundColor(getResources().getColor(R.color.baise));

                mFragmentToutiaoBuyerImg.setImageDrawable(getResources().getDrawable(R.mipmap.buy));
                mFragmentToutiaoBuyerText.setTextColor(getResources().getColor(R.color.erjibiaoti));
                mFragmentToutiaoBuyer.setBackgroundColor(getResources().getColor(R.color.bg_no_photo));

                mFragmentToutiaoXitongxiaoxiImg.setImageDrawable(getResources().getDrawable(R.mipmap.xitongxiaoxi));
                mFragmentToutiaoXitongxiaoxiText.setTextColor(getResources().getColor(R.color.erjibiaoti));
                mFragmentToutiaoXitongxiaoxi.setBackgroundColor(getResources().getColor(R.color.bg_no_photo));

                break;
            case R.id.fragment_toutiao_xitongxiaoxi:

                if(!ObjectUtils.isEmpty(messageBadge)){
//                    if (SPUtils.getInstance().getInt("notification") > 0 ) {
//                        messageBadge.setBadgeNumber(SPUtils.getInstance().getInt("notification"));
//                    } else messageBadge.setBadgeNumber(0);
                    messageBadge.setBadgeNumber(0);
                    SPUtils.getInstance().put("notification",0);
                    EventBus.getDefault().post(new MessageBean(MessageBean.JPush.DELETELUNCHNUMBER));
                }

                if (xitongxiaoxiFragment == null) {
                    xitongxiaoxiFragment = new XitongxiaoxiFragment();
                    fragmentTransaction.add(R.id.fragment_toutiao_container, xitongxiaoxiFragment);
                }
                hideFragment(fragmentTransaction);
                fragmentTransaction.show(xitongxiaoxiFragment);

                mFragmentToutiaoXitongxiaoxiImg.setImageDrawable(getResources().getDrawable(R.mipmap.xitongxiaoxi_img_red));
                mFragmentToutiaoXitongxiaoxiText.setTextColor(getResources().getColor(R.color.chunhongse));
                mFragmentToutiaoXitongxiaoxi.setBackgroundColor(getResources().getColor(R.color.baise));

                mFragmentToutiaoSellerImg.setImageDrawable(getResources().getDrawable(R.mipmap.sell));
                mFragmentToutiaoSellerText.setTextColor(getResources().getColor(R.color.erjibiaoti));
                mFragmentToutiaoSeller.setBackgroundColor(getResources().getColor(R.color.bg_no_photo));

                mFragmentToutiaoBuyerImg.setImageDrawable(getResources().getDrawable(R.mipmap.buy));
                mFragmentToutiaoBuyerText.setTextColor(getResources().getColor(R.color.erjibiaoti));
                mFragmentToutiaoBuyer.setBackgroundColor(getResources().getColor(R.color.bg_no_photo));
                break;
        }
        fragmentTransaction.commit();

    }

    private void hideFragment(android.support.v4.app.FragmentTransaction transaction) {

        if (buyerxiFragment != null) {

            transaction.hide(buyerxiFragment);

        }

        if (sellerxiaoxiFragment != null) {

            transaction.hide(sellerxiaoxiFragment);

        }

        if (xitongxiaoxiFragment != null) {

            transaction.hide(xitongxiaoxiFragment);

        }

    }
}
