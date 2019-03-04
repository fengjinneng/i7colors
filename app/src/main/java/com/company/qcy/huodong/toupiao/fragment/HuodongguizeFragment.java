package com.company.qcy.huodong.toupiao.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.company.qcy.R;
import com.company.qcy.huodong.toupiao.bean.ToupiaoBean;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HuodongguizeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HuodongguizeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "bean";

    // TODO: Rename and change types of parameters
    private ToupiaoBean bean;
    private View view;
    /**
     * 1.投票时间:
     */
    private TextView mFragmentHuodongguize1Text;
    /**
     * asdsadsadsadsadsadasdsadsad
     */
    private TextView mFragmentHuodongguize1Content;
    /**
     * TextView
     */
    private TextView mFragmentHuodongguize2Text;
    /**
     * TextView
     */
    private TextView mFragmentHuodongguize2Content;
    /**
     * TextView
     */
    private TextView mFragmentHuodongguize3Text;
    /**
     * TextView
     */
    private TextView mFragmentHuodongguize3Content;
    /**
     * TextView
     */
    private TextView mFragmentHuodongguize4Text;
    /**
     * TextView
     */
    private TextView mFragmentHuodongguize4Content;
    /**
     * TextView
     */
    private TextView mFragmentHuodongguize5Text;
    /**
     * TextView
     */
    private TextView mFragmentHuodongguize5Content;
    /**
     * TextView
     */
    private TextView mFragmentHuodongguize6Text;
    /**
     * TextView
     */
    private TextView mFragmentHuodongguize6Content;
    /**
     * 活动介绍
     */
    private TextView mFragmentHuodongguizeHuodongjieshaoText;
    /**
     * TextView
     */
    private TextView mFragmentHuodongguizeHuodongjieshaoContent;
    private TextView mFragmentHuodongguize7Text;
    /**
     * TextView
     */
    private TextView mFragmentHuodongguize7Content;
    private TextView mFragmentHuodongguize8Text;
    /**
     * TextView
     */
    private TextView mFragmentHuodongguize8Content;
    private TextView mFragmentHuodongguize9Text;
    /**
     * TextView
     */
    private TextView mFragmentHuodongguize9Content;


    public HuodongguizeFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static HuodongguizeFragment newInstance(ToupiaoBean bean) {
        HuodongguizeFragment fragment = new HuodongguizeFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, bean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bean = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_huodongguize, container, false);
    }

    private void initView(View inflate) {
        mFragmentHuodongguize1Text = inflate.findViewById(R.id.fragment_huodongguize_1_text);
        mFragmentHuodongguize1Content = inflate.findViewById(R.id.fragment_huodongguize_1_content);
        mFragmentHuodongguize2Text = inflate.findViewById(R.id.fragment_huodongguize_2_text);
        mFragmentHuodongguize2Content = inflate.findViewById(R.id.fragment_huodongguize_2_content);
        mFragmentHuodongguize3Text = inflate.findViewById(R.id.fragment_huodongguize_3_text);
        mFragmentHuodongguize3Content = inflate.findViewById(R.id.fragment_huodongguize_3_content);
        mFragmentHuodongguize4Text = inflate.findViewById(R.id.fragment_huodongguize_4_text);
        mFragmentHuodongguize4Content = inflate.findViewById(R.id.fragment_huodongguize_4_content);
        mFragmentHuodongguize5Text = inflate.findViewById(R.id.fragment_huodongguize_5_text);
        mFragmentHuodongguize5Content = inflate.findViewById(R.id.fragment_huodongguize_5_content);
        mFragmentHuodongguize6Text = inflate.findViewById(R.id.fragment_huodongguize_6_text);
        mFragmentHuodongguize6Content = inflate.findViewById(R.id.fragment_huodongguize_6_content);
        mFragmentHuodongguizeHuodongjieshaoText = inflate.findViewById(R.id.fragment_huodongguize_huodongjieshao_text);
        mFragmentHuodongguizeHuodongjieshaoContent = inflate.findViewById(R.id.fragment_huodongguize_huodongjieshao_content);
        mFragmentHuodongguize7Text = inflate.findViewById(R.id.fragment_huodongguize_7_text);
        mFragmentHuodongguize7Content = inflate.findViewById(R.id.fragment_huodongguize_7_content);
        mFragmentHuodongguize8Text = inflate.findViewById(R.id.fragment_huodongguize_8_text);
        mFragmentHuodongguize8Content = inflate.findViewById(R.id.fragment_huodongguize_8_content);
        mFragmentHuodongguize9Text = inflate.findViewById(R.id.fragment_huodongguize_9_text);
        mFragmentHuodongguize9Content = inflate.findViewById(R.id.fragment_huodongguize_9_content);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

        setData();

    }

    private void setData() {
        if (ObjectUtils.isEmpty(bean)) {
            return;
        }

        List<ToupiaoBean.RuleListBean> ruleList = bean.getRuleList();
        List<String> detailList = bean.getDetailList();
        if (!ObjectUtils.isEmpty(ruleList)) {

            switch (ruleList.size()) {
                case 1:
                    show1(ruleList);
                    break;
                case 2:
                    show1(ruleList);
                    show2(ruleList);
                    break;
                case 3:
                    show1(ruleList);
                    show2(ruleList);
                    show3(ruleList);
                    break;
                case 4:
                    show1(ruleList);
                    show2(ruleList);
                    show3(ruleList);
                    show4(ruleList);

                    break;
                case 5:
                    show1(ruleList);
                    show2(ruleList);
                    show3(ruleList);
                    show4(ruleList);
                    show5(ruleList);
                    break;
                case 6:
                    show1(ruleList);
                    show2(ruleList);
                    show3(ruleList);
                    show4(ruleList);
                    show5(ruleList);
                    show6(ruleList);
                    break;
                case 7:
                    show1(ruleList);
                    show2(ruleList);
                    show3(ruleList);
                    show4(ruleList);
                    show5(ruleList);
                    show6(ruleList);
                    show7(ruleList);
                    break;
                case 8:
                    show1(ruleList);
                    show2(ruleList);
                    show3(ruleList);
                    show4(ruleList);
                    show5(ruleList);
                    show6(ruleList);
                    show7(ruleList);
                    show8(ruleList);
                    break;
                case 9:
                    show1(ruleList);
                    show2(ruleList);
                    show3(ruleList);
                    show4(ruleList);
                    show5(ruleList);
                    show6(ruleList);
                    show7(ruleList);
                    show8(ruleList);
                    show9(ruleList);
                    break;
            }
            addHuodongjieshao(ruleList.size(), detailList);

        }

    }

    private void addHuodongjieshao(int rulesize, List<String> detailList) {

        switch (rulesize) {
            case 0:
                mFragmentHuodongguizeHuodongjieshaoText.setText("一、活动介绍");
                break;
            case 1:
                mFragmentHuodongguizeHuodongjieshaoText.setText("二、活动介绍");
                break;
            case 2:
                mFragmentHuodongguizeHuodongjieshaoText.setText("三、活动介绍");
                break;
            case 3:
                mFragmentHuodongguizeHuodongjieshaoText.setText("四、活动介绍");
                break;
            case 4:
                mFragmentHuodongguizeHuodongjieshaoText.setText("五、活动介绍");
                break;
            case 5:
                mFragmentHuodongguizeHuodongjieshaoText.setText("六、活动介绍");
                break;
            case 6:
                mFragmentHuodongguizeHuodongjieshaoText.setText("七、活动介绍");
                break;
            case 7:
                mFragmentHuodongguizeHuodongjieshaoText.setText("八、活动介绍");
                break;
            case 8:
                mFragmentHuodongguizeHuodongjieshaoText.setText("九、活动介绍");
                break;
            case 9:
                mFragmentHuodongguizeHuodongjieshaoText.setText("十、活动介绍");
                break;

        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < detailList.size(); i++) {

            sb.append("       " + (i + 1) + "." + detailList.get(i) + "\n");

        }
        mFragmentHuodongguizeHuodongjieshaoContent.setText(sb.toString());
    }

    private void show1(List<ToupiaoBean.RuleListBean> ruleList) {
        mFragmentHuodongguize1Text.setVisibility(View.VISIBLE);
        mFragmentHuodongguize1Content.setVisibility(View.VISIBLE);
        mFragmentHuodongguize1Text.setText("一、" + ruleList.get(0).getKey());
        mFragmentHuodongguize1Content.setText(ruleList.get(0).getValue());

    }

    private void show2(List<ToupiaoBean.RuleListBean> ruleList) {
        mFragmentHuodongguize2Text.setVisibility(View.VISIBLE);
        mFragmentHuodongguize2Content.setVisibility(View.VISIBLE);
        mFragmentHuodongguize2Text.setText("二、" + ruleList.get(1).getKey());
        mFragmentHuodongguize2Content.setText(ruleList.get(1).getValue());
    }

    private void show3(List<ToupiaoBean.RuleListBean> ruleList) {
        mFragmentHuodongguize3Text.setVisibility(View.VISIBLE);
        mFragmentHuodongguize3Content.setVisibility(View.VISIBLE);
        mFragmentHuodongguize3Text.setText("三、" + ruleList.get(2).getKey());
        mFragmentHuodongguize3Content.setText(ruleList.get(2).getValue());

    }

    private void show4(List<ToupiaoBean.RuleListBean> ruleList) {
        mFragmentHuodongguize4Text.setVisibility(View.VISIBLE);
        mFragmentHuodongguize4Content.setVisibility(View.VISIBLE);
        mFragmentHuodongguize4Text.setText("四、" + ruleList.get(3).getKey());
        mFragmentHuodongguize4Content.setText(ruleList.get(3).getValue());
    }


    private void show5(List<ToupiaoBean.RuleListBean> ruleList) {
        mFragmentHuodongguize5Text.setVisibility(View.VISIBLE);
        mFragmentHuodongguize5Content.setVisibility(View.VISIBLE);
        mFragmentHuodongguize5Text.setText("五、" + ruleList.get(4).getKey());
        mFragmentHuodongguize5Content.setText(ruleList.get(4).getValue());

    }

    private void show6(List<ToupiaoBean.RuleListBean> ruleList) {
        mFragmentHuodongguize6Text.setVisibility(View.VISIBLE);
        mFragmentHuodongguize6Content.setVisibility(View.VISIBLE);
        mFragmentHuodongguize6Text.setText("六、" + ruleList.get(5).getKey());
        mFragmentHuodongguize6Content.setText(ruleList.get(5).getValue());
    }

    private void show7(List<ToupiaoBean.RuleListBean> ruleList) {
        mFragmentHuodongguize7Text.setVisibility(View.VISIBLE);
        mFragmentHuodongguize7Content.setVisibility(View.VISIBLE);
        mFragmentHuodongguize7Text.setText("七、" + ruleList.get(6).getKey());
        mFragmentHuodongguize7Content.setText(ruleList.get(6).getValue());
    }

    private void show8(List<ToupiaoBean.RuleListBean> ruleList) {
        mFragmentHuodongguize8Text.setVisibility(View.VISIBLE);
        mFragmentHuodongguize8Content.setVisibility(View.VISIBLE);
        mFragmentHuodongguize8Text.setText("八、" + ruleList.get(7).getKey());
        mFragmentHuodongguize8Content.setText(ruleList.get(7).getValue());
    }

    private void show9(List<ToupiaoBean.RuleListBean> ruleList) {
        mFragmentHuodongguize9Text.setVisibility(View.VISIBLE);
        mFragmentHuodongguize9Content.setVisibility(View.VISIBLE);
        mFragmentHuodongguize9Text.setText("九、" + ruleList.get(8).getKey());
        mFragmentHuodongguize9Content.setText(ruleList.get(8).getValue());
    }
}
