package com.company.qcy.huodong.jingpai.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.huodong.jingpai.adapter.FujianAdapter;
import com.company.qcy.huodong.jingpai.adapter.ImgAdapter;
import com.company.qcy.huodong.jingpai.adapter.JibencanshuAdapter;
import com.company.qcy.huodong.jingpai.adapter.VideoAdapter;
import com.company.qcy.huodong.jingpai.bean.JingpaiDetailBean;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaipinmiaoshuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaipinmiaoshuFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "bean";

    // TODO: Rename and change types of parameters
    private JingpaiDetailBean bean;

    private RecyclerView canshuRecyclerView;
    private JibencanshuAdapter miaoshuAdapter;
    private List<JingpaiDetailBean.AttributeListBean> miaoshuDatas;

    private RecyclerView fujianRecyclerview;
    private FujianAdapter fujianAdapter;
    private List<JingpaiDetailBean.AuctionAttachesBean> fujianDatas;

    private RecyclerView imgRecyclerview;
    private ImgAdapter imgAdapter;
    private List<JingpaiDetailBean.DetailListBean> imgDatas;

    private RecyclerView videoRecyclerview;
    private VideoAdapter videoAdapter;
    private List<JingpaiDetailBean.VideoListBean> videoDatas;


    private TextView jibencanshu;
    private TextView fujianchakan;
    private TextView tupianxiangqing;
    private TextView shipinxiangqing;

    public PaipinmiaoshuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PaipinmiaoshuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PaipinmiaoshuFragment newInstance(JingpaiDetailBean bean) {
        PaipinmiaoshuFragment fragment = new PaipinmiaoshuFragment();
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
        return inflater.inflate(R.layout.fragment_paipinmiaoshu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    private void initView(View inflate) {
        jibencanshu = inflate.findViewById(R.id.fragment_paipinmiaoshu_jibencanshu_text);
        fujianchakan = inflate.findViewById(R.id.fragment_paipinmiaoshu_fujian_text);
        tupianxiangqing = inflate.findViewById(R.id.fragment_paipinmiaoshu_tupian_text);

        miaoshuDatas = new ArrayList<>();
        fujianDatas = new ArrayList<>();
        imgDatas = new ArrayList<>();
        videoDatas = new ArrayList<>();

        canshuRecyclerView = inflate.findViewById(R.id.fragment_paipinmiaoshu_canshu_recyclerview);
        fujianRecyclerview = inflate.findViewById(R.id.fragment_paipinmiaoshu_fujian_recyclerview);
        imgRecyclerview = inflate.findViewById(R.id.fragment_paipinmiaoshu_img_recyclerview);
        videoRecyclerview = inflate.findViewById(R.id.fragment_paipinmiaoshu_video_recyclerview);


        miaoshuAdapter = new JibencanshuAdapter(R.layout.item_jingpai_paipinmiaoshu, miaoshuDatas);
        fujianAdapter = new FujianAdapter(R.layout.item_jingpai_fujian, fujianDatas);
        imgAdapter  =new ImgAdapter(R.layout.item_jingpai_img,imgDatas);
        videoAdapter = new VideoAdapter(R.layout.item_jingpai_video,videoDatas);


        //创建布局管理
        LinearLayoutManager canshuLayoutManager = new LinearLayoutManager(getContext());
        canshuLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        canshuRecyclerView.setLayoutManager(canshuLayoutManager);

        LinearLayoutManager fujianLayoutManager = new LinearLayoutManager(getContext());
        fujianLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        fujianRecyclerview.setLayoutManager(fujianLayoutManager);

        LinearLayoutManager imgLayoutManager = new LinearLayoutManager(getContext());
        imgLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        imgRecyclerview.setLayoutManager(imgLayoutManager);

        LinearLayoutManager videoLayoutManager = new LinearLayoutManager(getContext());
        videoLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        videoRecyclerview.setLayoutManager(videoLayoutManager);


        canshuRecyclerView.setAdapter(miaoshuAdapter);
        fujianRecyclerview.setAdapter(fujianAdapter);
        imgRecyclerview.setAdapter(imgAdapter);
        videoRecyclerview.setAdapter(videoAdapter);

        setData();

        canshuRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        fujianAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                JingpaiDetailBean.AuctionAttachesBean auctionAttachesBean = (JingpaiDetailBean.AuctionAttachesBean) adapter.getData().get(position);
//                Intent intent = new Intent(getActivity(),WebActivity.class);
//                intent.putExtra("webUrl",ServerInfo.IMAGE + auctionAttachesBean.getAttachUrl());
//                ActivityUtils.startActivity(intent);
            }
        });
    }

    private void setData() {

        if (ObjectUtils.isEmpty(bean)) {
            return;
        }

        //属性集合
        if (!ObjectUtils.isEmpty(bean.getAttributeList())) {
            List<JingpaiDetailBean.AttributeListBean> attributeList = bean.getAttributeList();
            miaoshuAdapter.setNewData(attributeList);
        }

        //附件集合
        if (!ObjectUtils.isEmpty(bean.getAuctionAttaches())) {
            List<JingpaiDetailBean.AuctionAttachesBean> auctionAttaches = bean.getAuctionAttaches();
            fujianAdapter.setNewData(auctionAttaches);
        }

        //图片集合
        if (!ObjectUtils.isEmpty(bean.getDetailList())) {
            List<JingpaiDetailBean.DetailListBean> datailList = bean.getDetailList();
            imgAdapter.setNewData(datailList);
        }

        //视频集合
        if (!ObjectUtils.isEmpty(bean.getVideoList())) {
            List<JingpaiDetailBean.VideoListBean> videoList = bean.getVideoList();
            videoAdapter.setNewData(videoList);
        }



//        if (!StringUtils.isEmpty(bean.getDetailPcPic())) {
//
//            Glide.with(this).asBitmap()//强制Glide返回一个Bitmap对象
//                    .load(ServerInfo.IMAGE + bean.getDetailPcPic())
//                    .into(new SimpleTarget<Bitmap>() {
//                        @Override
//                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//
//                            int height = resource.getHeight();
//                            mFragmentPaipinmiaoshuImg.setLayoutParams(new FrameLayout.LayoutParams((FrameLayout.LayoutParams.MATCH_PARENT), height));
//                            GlideUtils.loadImageCenter(getContext(), ServerInfo.IMAGE + bean.getDetailPcPic(), mFragmentPaipinmiaoshuImg);
//                        }
//
//
//                    });
//        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }
}
