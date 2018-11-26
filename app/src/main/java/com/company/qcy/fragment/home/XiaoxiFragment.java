package com.company.qcy.fragment.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.pengyouquan.PengyouquanAdapter;
import com.company.qcy.bean.pengyouquan.ActionItem;
import com.company.qcy.bean.pengyouquan.PengyouquanBean;
import com.company.qcy.ui.activity.pengyouquan.PengyouquanDetailActivity;
import com.company.qcy.ui.activity.pengyouquan.PersonInfoActivity;
import com.company.qcy.ui.activity.pengyouquan.PubulishPYQActivity;
import com.company.qcy.widght.pengyouquan.SnsPopupWindow;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;


public class XiaoxiFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;


    public XiaoxiFragment() {
        // Required empty public constructor
    }

    public static XiaoxiFragment newInstance(String param1) {
        XiaoxiFragment fragment = new XiaoxiFragment();
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
        return inflater.inflate(R.layout.fragment_xiaoxi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private RecyclerView recyclerView;
    private PengyouquanAdapter adapter;
    private List<PengyouquanBean> datas;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;
    private TextView title;
    private ImageView back;
    private TextView fabu;
    LinearLayoutManager layoutManager;

    private void initView(View view) {
        title = view.findViewById(R.id.toolbar_title);
        back = view.findViewById(R.id.toolbar_back);
        fabu = view.findViewById(R.id.toolbar_text);
        title.setText("印染朋友圈");
        back.setVisibility(View.INVISIBLE);
        fabu.setVisibility(View.VISIBLE);
        fabu.setText("我要发布");
        fabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startActivity(PubulishPYQActivity.class);
            }
        });

        recyclerView = view.findViewById(R.id.fragment_pengyouquan_recyclerview);
        swipeRefreshLayout = view.findViewById(R.id.fragment_pengyouquan_swipeRefreshLayout);
        //创建布局管理
         layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        datas = new ArrayList<>();
        //创建适配器
        adapter = new PengyouquanAdapter(R.layout.item_pengyouquan, datas,this);
        //给RecyclerView设置适配器
        recyclerView.setAdapter(adapter);

        refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉业务
                isReflash = true;
                pageNo = 0;
                addData();
            }
        };
        swipeRefreshLayout.setOnRefreshListener(refreshListener);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                refreshListener.onRefresh();
            }
        });

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                addData();
            }
        }, recyclerView);

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                PengyouquanBean bean = (PengyouquanBean) adapter.getData().get(position);

                switch (view.getId()) {
                    case R.id.snsBtn:

                        SnsPopupWindow snsPopupWindow = new SnsPopupWindow(getContext());
                        snsPopupWindow.showPopupWindow(view);
                        snsPopupWindow.setmItemClickListener(new PopupItemClickListener(2, "2"));

                        break;
                    case R.id.item_pengyouquan_shipin_img:
                        ToastUtils.showShort(bean.getUrl());
                        break;
                    case R.id.item_pengyouquan_headimg:
                        ActivityUtils.startActivity(PersonInfoActivity.class);
                        break;
                }
            }
        });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                PengyouquanBean pengyouquanBean = (PengyouquanBean) adapter.getData().get(position);
                Intent intent = new Intent(getActivity(),PengyouquanDetailActivity.class);
                intent.putExtra("pengyouquanBean",pengyouquanBean);
                ActivityUtils.startActivity(intent);

            }
        });
    }
    private boolean isReflash;
    private int pageNo;

    private void addData() {

        pageNo++;
        OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.QUERYDYECOMMUNITYLIST)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("pageNo", pageNo)
                .params("pageSize", 10)
                .params("token", SPUtils.getInstance().getString("token"))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        try {
                            if (response.code() == 200) {

                                JSONObject jsonObject = JSONObject.parseObject(response.body());

                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                    JSONArray data = jsonObject.getJSONArray("data");
                                    LogUtils.v("QUERYDYECOMMUNITYLIST", data);
                                    List<PengyouquanBean> pengyouquanBeans = JSONObject.parseArray(data.toJSONString(), PengyouquanBean.class);
                                    if (isReflash) {
                                        datas.clear();
                                        adapter.addData(pengyouquanBeans);
                                        isReflash = false;
                                        swipeRefreshLayout.setRefreshing(false);
                                        adapter.loadMoreComplete();
                                        return;
                                    }
                                    if (ObjectUtils.isEmpty(pengyouquanBeans)) {
                                        adapter.loadMoreEnd();
                                        return;
                                    }
                                    adapter.addData(pengyouquanBeans);
                                    adapter.loadMoreComplete();
                                    adapter.disableLoadMoreIfNotFullPage();
                                    return;

                                } else
                                    SignAndTokenUtil.checkSignAndToken(getActivity(), jsonObject);

                            } else {
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private class PopupItemClickListener implements SnsPopupWindow.OnItemClickListener {
        private String mFavorId;
        //动态在列表中的位置
        private int mCirclePosition;
        private long mLasttime = 0;

        public PopupItemClickListener(int circlePosition,  String favorId) {
            this.mFavorId = favorId;
            this.mCirclePosition = circlePosition;
        }

        @Override
        public void onItemClick(ActionItem actionitem, int position) {
            switch (position) {
                case 0://点赞、取消点赞
                    if (System.currentTimeMillis() - mLasttime < 700)//防止快速点击操作
                        return;
                    mLasttime = System.currentTimeMillis();
//                    if(presenter != null){
//                        if ("赞".equals(actionitem.mTitle.toString())) {
//                            presenter.addFavort(mCirclePosition);
//                        } else {//取消点赞
//                            presenter.deleteFavort(mCirclePosition, mFavorId);
//                        }
//                    }
                    ToastUtils.showShort("点赞");
                    break;
                case 1://发布评论
//                    if(presenter != null){
//                        CommentConfig config = new CommentConfig();
//                        config.circlePosition = mCirclePosition;
//                        config.commentType = CommentConfig.Type.PUBLIC;
//                        presenter.showEditTextBody(config);
//                    }
                    ToastUtils.showShort("评论");
                    break;
                default:
                    break;
            }
        }
    }

}
