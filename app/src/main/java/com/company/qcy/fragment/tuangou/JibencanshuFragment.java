package com.company.qcy.fragment.tuangou;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.company.qcy.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class JibencanshuFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";


    private String mParam1;

    public JibencanshuFragment() {
        // Required empty public constructor
    }


    public static JibencanshuFragment newInstance(String param1) {
        JibencanshuFragment fragment = new JibencanshuFragment();
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

    private ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_jibencanshu, container, false);
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        imageView = view.findViewById(R.id.fragment_jibencanshu_img);
//        Glide.with(this).load(mParam1).into(imageView);
//    }
}
