package com.company.qcy.fragment.pengyouquan;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.Utils.CommentDialog;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.MyLoadMoreView;
import com.company.qcy.Utils.RecyclerviewDisplayDecoration;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.Utils.UserUtil;
import com.company.qcy.Utils.pengyouquan.dialogfragment.MyDialogFragment;
import com.company.qcy.Utils.pengyouquan.dialogfragment.PinglunListDialogFragment;
import com.company.qcy.Utils.pengyouquan.jiukou.PinglunListCallBack;
import com.company.qcy.adapter.pengyouquan.PinglunliebiaoAdapter;
import com.company.qcy.base.BaseFragment;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.bean.pengyouquan.PengyouquanBean;
import com.company.qcy.ui.activity.pengyouquan.PersonInfoActivity;
import com.company.qcy.ui.activity.user.LoginActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PinglunFragment extends BaseFragment implements PinglunListCallBack {
    private static final String ARG_PARAM1 = "param1";

    public PinglunFragment() {
        // Required empty public constructor
    }


    InputMethodManager inputMethodManager;
    private String mParam1;
    private RecyclerView recyclerView;
    private PinglunliebiaoAdapter adapter;
    private List<PengyouquanBean.CommentListBean> datas;
    private SwipeRefreshLayout refreshLayout;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;
//    private ConstraintLayout pinglun, dianzan;

    public static PinglunFragment newInstance(String param1) {
        PinglunFragment fragment = new PinglunFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
        inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pinglun, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    @Override
    public void onRec(MessageBean messageBean) {
        super.onRec(messageBean);

        switch (messageBean.getCode()) {
            case MessageBean.Code.PINGLUNNEEDREFLUSH:
                refreshLayout.setRefreshing(true);
                refreshListener.onRefresh();
                break;
        }
    }

    private void initView(View view) {
//        pinglun = view.findViewById(R.id.fragment_pinglun_pinglun);
//        dianzan = view.findViewById(R.id.fragment_pinglun_dianzan);
//        pinglun.setOnClickListener(this);
//        dianzan.setOnClickListener(this);
        recyclerView = view.findViewById(R.id.fragment_pinglun_recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        datas = new ArrayList<>();
        adapter = new PinglunliebiaoAdapter(R.layout.item_pinglun, datas);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                PengyouquanBean.CommentListBean bean = (PengyouquanBean.CommentListBean) adapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.item_pinglun_pinglun_layout:
                        if (!UserUtil.isLogin()) {
                            ActivityUtils.startActivity(LoginActivity.class);
                            return;
                        }
//                        showPop(Long.valueOf(mParam1), bean.getId());
//                        getIdKeyboardUtils.showSoftInput(getActivity());
//                        showSoftKeyBoard();

                        FragmentManager fragmentManager = getFragmentManager();
                        PinglunListDialogFragment dialog = new PinglunListDialogFragment();
                        dialog.setPinglunHouCallBack(PinglunFragment.this);
                        dialog.setId(Long.valueOf(mParam1));
                        dialog.setPosition(bean.getId());
                        dialog.setPosition(position);
                        dialog.show(fragmentManager, "dialog");


                        break;
//                    case R.id.item_pinglun_commentUser:
//                        Intent i = new Intent(getActivity(), PersonInfoActivity.class);
//                        i.putExtra("userId", bean.getUserId());
//                        ActivityUtils.startActivity(i);
//                        break;
//                    case R.id.item_pinglun_bycommentUser:
//                        Intent i1 = new Intent(getActivity(), PersonInfoActivity.class);
//                        i1.putExtra("userId", bean.getByUserId());
//                        ActivityUtils.startActivity(i1);
//                        break;
                }
            }
        });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PengyouquanBean.CommentListBean bean = (PengyouquanBean.CommentListBean) adapter.getData().get(position);
                if (StringUtils.equals("1", bean.getIsCharger())) {
                    CommentDialog dialog = new CommentDialog(getActivity(), bean.getId());
                    dialog.show();
                }
            }
        });

        refreshLayout = view.findViewById(R.id.fragment_pinglun_swipeRefreshLayout);
        refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉业务
                isReflash = true;
                pageNo = 0;
                addData();
            }
        };
        refreshLayout.setOnRefreshListener(refreshListener);
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                refreshListener.onRefresh();
            }
        });

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {

                addData();
            }
        }, recyclerView);

        recyclerView.addItemDecoration(new RecyclerviewDisplayDecoration(getContext()));

        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.empty_layout, null));
        adapter.setLoadMoreView(new MyLoadMoreView());
    }

    private int pageNo;
    private boolean isReflash;

    private void addData() {
        pageNo++;
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.QUERYDYECOMMENTLISTBYID)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("pageNo", pageNo)
                .params("pageSize", 10)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("dyeId", Long.valueOf(mParam1));


        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                refreshLayout.setRefreshing(false);
                LogUtils.v("QUERYDYECOMMENTLISTBYID", response.body());
                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");

                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONArray data = jsonObject.getJSONArray("data");
                            List<PengyouquanBean.CommentListBean> commentListBeans = JSONObject.parseArray(data.toJSONString(), PengyouquanBean.CommentListBean.class);
                            if (ObjectUtils.isEmpty(commentListBeans)) {
                                adapter.loadMoreEnd();
                                return;
                            }
                            if (isReflash) {
                                datas.clear();
                                datas.addAll(commentListBeans);
                                adapter.setNewData(datas);
                                isReflash = false;
                                adapter.loadMoreComplete();
                                return;
                            }
                            datas.addAll(commentListBeans);
                            adapter.setNewData(datas);
                            adapter.loadMoreComplete();
                            adapter.disableLoadMoreIfNotFullPage();
                            return;
                        }

                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(getActivity(), request, this);
                            return;
                        }
                        ToastUtils.showShort(msg);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                refreshLayout.setRefreshing(false);
                ToastUtils.showShort(getResources().getString(R.string.NETEXCEPTION));
            }
        };

        request.execute(stringCallback);


    }

    public static void HideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }


    private void showSoftKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && inputComment != null) {
            inputComment.post(() -> {
                inputComment.requestFocus();
                inputMethodManager.showSoftInput(inputComment, 0);
            });
            new Handler().postDelayed(() -> {
//                changeLayoutNullParams(true);
//                changeEmojiPanelParams(0);
            }, 200);
        }
    }

    private PopupWindow popupWindow;
    private EditText inputComment;
    private TextView btn_submit;


    @SuppressLint("WrongConstant")
    public void showPop(Long id, Long parentId,int tieziPosition) {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.pengyouquan_huifu_layout, null);

        popupWindow = new PopupWindow(inflate, LinearLayout.LayoutParams.FILL_PARENT, 150, true);

        btn_submit = (TextView) inflate.findViewById(R.id.tv_confirm);
        //popupwindow弹出时的动画		popWindow.setAnimationStyle(R.style.popupWindowAnimation);

        // 使其聚集 ，要想监听菜单里控件的事件就必须要调用此方法

        popupWindow.setFocusable(true);

        // 设置允许在外点击消失

        popupWindow.setOutsideTouchable(false);

        // 设置背景，这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景

        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        //软键盘不会挡着popupwindow

        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        //设置菜单显示的位置

        popupWindow.showAtLocation(inflate, Gravity.BOTTOM, 0, 0);

        //监听菜单的关闭事件

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override

            public void onDismiss() {

            }

        });

        //监听触屏事件

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE)
                    popupWindow.dismiss();
                inputMethodManager.hideSoftInputFromWindow(inputComment.getWindowToken(), 0);
                return false;
            }

        });


        inputComment = inflate.findViewById(R.id.et_discuss);
        inputComment.setFocusable(true);
        inputComment.setFocusableInTouchMode(true);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // btn_submit.setClickable(false);
                String comment1 = inputComment.getText().toString().trim();
                if (StringUtils.isEmpty(comment1)) {
                    ToastUtils.showShort("不能发表空的评论");
                    return;
                }
                //调用提交评论接口
                saveDiscuss(comment1, id, parentId,tieziPosition);
                inputMethodManager.hideSoftInputFromWindow(inputComment.getWindowToken(), 0);
                inputComment.setText("");
                popupWindow.dismiss();
            }
        });
    }


    private void saveDiscuss(String comment1, Long id, Long parentId,int tieziPosition) {
        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.FABIAOPINGLUN)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("dyeId", id)
                .params("content", comment1)
                .params("parentId", parentId)
                .params("from", getActivity().getResources().getString(R.string.app_android))
                .params("token", SPUtils.getInstance().getString("token"));

        DialogStringCallback stringCallback = new DialogStringCallback(getActivity()) {
            @Override
            public void onSuccess(Response<String> response) {

                LogUtils.v("FABIAOPINGLUN", response.body());
                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {

                            PengyouquanBean.CommentListBean commentListBean =
                                    jsonObject.getJSONObject("data").toJavaObject(PengyouquanBean.CommentListBean.class);

                            adapter.addData(commentListBean);
                            adapter.notifyItemChanged(tieziPosition);

//                            refreshLayout.setRefreshing(true);
//                            refreshListener.onRefresh();
                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(getActivity(), request, this);
                            return;
                        }
                        ToastUtils.showShort(msg);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                ToastUtils.showShort(getResources().getString(R.string.NETEXCEPTION));
            }
        };

        request.execute(stringCallback);

    }

    @Override
    public void save(String content, Long id, Long parentId,int position) {
        saveDiscuss(content, id, parentId,position);
    }
}

