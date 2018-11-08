package com.company.qcy.fragment.tuangou;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.qcy.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class JiluFragment extends Fragment {

    private String mParam1;
    private static final String ARG_PARAM1 = "param1";

    public JiluFragment() {
        // Required empty public constructor
    }


    public static JiluFragment newInstance(String param1) {
        JiluFragment fragment = new JiluFragment();
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
        return inflater.inflate(R.layout.fragment_jilu, container, false);
    }

}
