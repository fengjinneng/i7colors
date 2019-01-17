package com.company.qcy.fragment.tuangou;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class JibencanshuFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";


    private String mParam1;
    private View view;

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_jibencanshu, container, false);
        initView(inflate);
        return inflate;
    }

    private ImageView imageView;
    private void initView(View inflate) {
        imageView = inflate.findViewById(R.id.fragment_jibencanshu_recyclerview);
        Glide.with(this).asBitmap()//强制Glide返回一个Bitmap对象
                .load(mParam1)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                        int height = resource.getHeight();

                        imageView.setLayoutParams(new FrameLayout.LayoutParams((FrameLayout.LayoutParams.MATCH_PARENT),height));
                        GlideUtils.loadImageCenter(getContext(), mParam1,imageView);
                    }


                });
    }

}
