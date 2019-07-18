package com.company.qcy.fragment.kaifangshangcheng;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.base.BaseFragment;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.bean.kaifangshangcheng.CompanyIntroduceBean;
import com.company.qcy.bean.pengyouquan.MyAddress;
import com.company.qcy.ui.activity.pengyouquan.MapActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link KfscGongsijieshaoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KfscGongsijieshaoFragment extends BaseFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private View view;
    /**
     * 分散染料、活性染料
     */
    private TextView mFragmentKfscGongsijieshaoZhuying;
    /**
     * 张某某
     */
    private TextView mFragmentKfscGongsijieshaoLianxiren;
    /**
     * 13816717719
     */
    private TextView mFragmentKfscGongsijieshaoPhone;
    /**
     * 杭州湾上虞经济技术开发区纬 三路3号
     */
    private TextView mFragmentKfscGongsijieshaoAddress;
    private ImageView mFragmentKfscGongsijieshaoDaohang;

    private WebView webview;
    /**
     * 暂无
     */
    private TextView mFragmentKfscGongsijieshaoZanwujianjie;


    public KfscGongsijieshaoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment KfscGongsijieshaoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KfscGongsijieshaoFragment newInstance(String param1) {
        KfscGongsijieshaoFragment fragment = new KfscGongsijieshaoFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kfsc_gongsijieshao, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    private void initView(View inflate) {

        mFragmentKfscGongsijieshaoZhuying = inflate.findViewById(R.id.fragment_kfsc_gongsijieshao_zhuying);
        mFragmentKfscGongsijieshaoLianxiren = inflate.findViewById(R.id.fragment_kfsc_gongsijieshao_lianxiren);
        mFragmentKfscGongsijieshaoPhone = inflate.findViewById(R.id.fragment_kfsc_gongsijieshao_phone);
        mFragmentKfscGongsijieshaoAddress = inflate.findViewById(R.id.fragment_kfsc_gongsijieshao_address);
        mFragmentKfscGongsijieshaoDaohang = inflate.findViewById(R.id.fragment_kfsc_gongsijieshao_daohang);
        mFragmentKfscGongsijieshaoDaohang.setOnClickListener(this);
        webview = inflate.findViewById(R.id.fragment_kfsc_gongsijieshao_webview);
        mFragmentKfscGongsijieshaoZanwujianjie = inflate.findViewById(R.id.fragment_kfsc_gongsijieshao_zanwujianjie);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    private CompanyIntroduceBean companyIntroduceBean;

    @Override
    public void onRec(MessageBean messageBean) {
        super.onRec(messageBean);
        switch (messageBean.getCode()) {
            case MessageBean.Code.KFSCGONGSIJIESHAO:

                companyIntroduceBean = (CompanyIntroduceBean) messageBean.getObj();

                if (!ObjectUtils.isEmpty(companyIntroduceBean)) {

                    if(StringUtils.isEmpty(companyIntroduceBean.getZhuying())){
                        mFragmentKfscGongsijieshaoZhuying.setText("暂无信息!");
                    }else {
                        mFragmentKfscGongsijieshaoZhuying.setText(companyIntroduceBean.getZhuying());

                    }

                    if(StringUtils.isEmpty(companyIntroduceBean.getContact())){
                        mFragmentKfscGongsijieshaoLianxiren.setText("暂无联系人信息");

                    }else {
                        mFragmentKfscGongsijieshaoLianxiren.setText(companyIntroduceBean.getContact());

                    }

                    if(StringUtils.isEmpty(companyIntroduceBean.getPhone())){
                        mFragmentKfscGongsijieshaoPhone.setText("暂无联系方式");

                    }else {
                        mFragmentKfscGongsijieshaoPhone.setText(companyIntroduceBean.getPhone());

                    }

                    if(StringUtils.isEmpty(companyIntroduceBean.getAddress())){
                        mFragmentKfscGongsijieshaoAddress.setText("暂无地址信息");

                    }else {
                        mFragmentKfscGongsijieshaoAddress.setText(companyIntroduceBean.getAddress());

                    }

                    if(StringUtils.isEmpty(companyIntroduceBean.getIntroduce())){
                        mFragmentKfscGongsijieshaoZanwujianjie.setVisibility(View.VISIBLE);
                        webview.setVisibility(View.GONE);
                    }else {
                        webview.loadData(companyIntroduceBean.getIntroduce(), "text/html;charset=UTF-8", null);
                    }
                }
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.fragment_kfsc_gongsijieshao_daohang:
                if (!ObjectUtils.isEmpty(companyIntroduceBean)) {
                    MyAddress address = new MyAddress();

                    if(StringUtils.isEmpty(companyIntroduceBean.getLat())||
                            StringUtils.isEmpty(companyIntroduceBean.getLng())){
                        ToastUtils.showShort("没有该地址信息");
                        return;
                    }

                    address.setLat(companyIntroduceBean.getLat());
                    address.setLot(companyIntroduceBean.getLng());
                    address.setTitle(companyIntroduceBean.getCompanyName());
                    address.setContent(companyIntroduceBean.getAddress());
                    Intent iAddress = new Intent(getActivity(), MapActivity.class);
                    iAddress.putExtra("address", address);
                    ActivityUtils.startActivity(iAddress);
                }

                break;
        }
    }
}
