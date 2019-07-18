package com.company.qcy.huodong.jingpai.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.company.qcy.R;
import com.company.qcy.huodong.jingpai.bean.JingpaiDetailBean;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JingpaixuzhiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JingpaixuzhiFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "bean";

    // TODO: Rename and change types of parameters
    private JingpaiDetailBean bean;
    private View view;
    /**
     * TextView
     */
    private TextView mFragmentJingpaixuzhiTv;


    public JingpaixuzhiFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static JingpaixuzhiFragment newInstance(JingpaiDetailBean bean) {
        JingpaixuzhiFragment fragment = new JingpaixuzhiFragment();
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
        return inflater.inflate(R.layout.fragment_jingpaixuzhi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View inflate) {

        mFragmentJingpaixuzhiTv = inflate.findViewById(R.id.fragment_jingpaixuzhi_tv);

        setData();

    }

    private void setData() {

        if(ObjectUtils.isEmpty(bean)){
            return;

        }

        if(StringUtils.isEmpty(bean.getFrom())) {

            return;

        }

        LogUtils.e("xzcxzcxzcxzczx",bean.getFrom());

        if(StringUtils.equals("pc",bean.getFrom())){

            if(StringUtils.isEmpty(bean.getRemark())){
                mFragmentJingpaixuzhiTv.append("\t\t\t一:最终商品以现场交付实物数量为准。\n");
                mFragmentJingpaixuzhiTv.append("\t\t\t二:抢购者在出价前应自行认真核实，查验标的信息，自行判断产品的现状是否符合其相关资料或描述。七彩云平台不对商品的数量、质量、种类、规格、实用性等情况作任何承诺，如抢购成功，买卖双方应自行协商交易。\n");
                mFragmentJingpaixuzhiTv.append("\t\t\t三:商品成交后，买卖双方自行协商办理成交商品的移交等一切事宜。\n");
                mFragmentJingpaixuzhiTv.append("\t\t\t四:费用负担情况：商品转让手续由买卖双方自行办理。本次交易过程中产生的税费依照税法等相关法律法规和政策的规定，由双方各自承担。\n");
                mFragmentJingpaixuzhiTv.append("\t\t\t五:至少一人报名且出价不低于最低价，方可成交。\n");
            }else {
                mFragmentJingpaixuzhiTv.append("\t\t\t一:"+bean.getRemark()+"\n");
                mFragmentJingpaixuzhiTv.append("\t\t\t二:最终商品以现场交付实物数量为准。\n");
                mFragmentJingpaixuzhiTv.append("\t\t\t三:抢购者在出价前应自行认真核实，查验标的信息，自行判断产品的现状是否符合其相关资料或描述。七彩云平台不对商品的数量、质量、种类、规格、实用性等情况作任何承诺，如抢购成功，买卖双方应自行协商交易。\n");
                mFragmentJingpaixuzhiTv.append("\t\t\t四:商品成交后，买卖双方自行协商办理成交商品的移交等一切事宜。\n");
                mFragmentJingpaixuzhiTv.append("\t\t\t五:费用负担情况：商品转让手续由买卖双方自行办理。本次交易过程中产生的税费依照税法等相关法律法规和政策的规定，由双方各自承担。\n");
                mFragmentJingpaixuzhiTv.append("\t\t\t六:至少一人报名且出价不低于最低价，方可成交。\n");
            }

        }else {
            if(ObjectUtils.isEmpty(bean.getInstructionsList())){
                mFragmentJingpaixuzhiTv.setText("暂无内容！");
                return;
            }

            for (int i = 0; i < bean.getInstructionsList().size(); i++) {
                mFragmentJingpaixuzhiTv.append("\t\t\t"+bean.getInstructionsList().get(i).getRelatedInstructions()+"\n");
            }
        }

    }

}
