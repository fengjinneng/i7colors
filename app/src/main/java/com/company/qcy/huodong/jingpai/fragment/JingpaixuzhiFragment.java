package com.company.qcy.huodong.jingpai.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
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

        if(ObjectUtils.isEmpty(bean.getInstructionsList())){
            mFragmentJingpaixuzhiTv.setText("暂无内容！");
            return;
        }

        for (int i = 0; i < bean.getInstructionsList().size(); i++) {
            mFragmentJingpaixuzhiTv.append("\t\t\t"+bean.getInstructionsList().get(i).getRelatedInstructions()+"\n");
        }
    }

}
