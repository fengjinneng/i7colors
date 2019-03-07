package com.company.qcy.huodong.tuangou.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class TuangouxuzhiFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";


    private String mParam1;
    private View view;
    private ImageView mFragmentTuangouxuzhiImg;

    public TuangouxuzhiFragment() {
        // Required empty public constructor
    }

    public static TuangouxuzhiFragment newInstance(String param1) {
        TuangouxuzhiFragment fragment = new TuangouxuzhiFragment();
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
        View inflate = inflater.inflate(R.layout.fragment_tuangouxuzhi, container, false);
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {

        mFragmentTuangouxuzhiImg = (ImageView) inflate.findViewById(R.id.fragment_tuangouxuzhi_img);
        Glide.with(this).asBitmap()//强制Glide返回一个Bitmap对象
                .load(mParam1)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                        int height = resource.getHeight();

                        mFragmentTuangouxuzhiImg.setLayoutParams(new FrameLayout.LayoutParams((FrameLayout.LayoutParams.MATCH_PARENT),height));
                        GlideUtils.loadImageCenter(getContext(), mParam1,mFragmentTuangouxuzhiImg);
                    }


                });
    }

}
