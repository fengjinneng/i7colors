package com.company.qcy.fragment.kaifangshangcheng;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.base.BaseFragment;
import com.company.qcy.bean.eventbus.MessageBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link KfscGongsijieshaoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KfscGongsijieshaoFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private View view;
    private TextView mFragmentKfscGongsijieshaoText;


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

    private TextView textView;

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
        View inflate = inflater.inflate(R.layout.fragment_kfsc_gongsijieshao, container, false);

        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {
        textView = inflate.findViewById(R.id.fragment_kfsc_gongsijieshao_text);
        textView.setText("暂无公司简介");
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }


    @Override
    public void onRec(MessageBean messageBean) {
        super.onRec(messageBean);
        switch (messageBean.getCode()) {
            case MessageBean.Code.KFSCGONGSIJIESHAO:
                textView.setText(messageBean.getMeaasge());
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

}
