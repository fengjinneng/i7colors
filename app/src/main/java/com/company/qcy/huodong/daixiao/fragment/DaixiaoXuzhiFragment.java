package com.company.qcy.huodong.daixiao.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.company.qcy.R;
import com.company.qcy.huodong.daixiao.bean.DaixiaoBean;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DaixiaoXuzhiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DaixiaoXuzhiFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private DaixiaoBean mParam1;

    private TextView xuzhi;

    public DaixiaoXuzhiFragment() {
        // Required empty public constructor
    }


    public static DaixiaoXuzhiFragment newInstance(DaixiaoBean param1) {
        DaixiaoXuzhiFragment fragment = new DaixiaoXuzhiFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daixiao_xuzhi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    private void initView(View view) {
        xuzhi = view.findViewById(R.id.fragment_daixiao_xuzhi_detail);

        List<String> noteList = mParam1.getNoteList();

        for (int i = 0; i < noteList.size(); i++) {
            xuzhi.append(noteList.get(i)+"\n");
        }

    }
}
