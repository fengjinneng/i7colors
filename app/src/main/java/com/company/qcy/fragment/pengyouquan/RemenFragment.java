package com.company.qcy.fragment.pengyouquan;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
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
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.pengyouquan.jiukou.PinglunHouCallBack;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.pengyouquan.dialogfragment.MyDialogFragment;
import com.company.qcy.Utils.MyLoadMoreView;
import com.company.qcy.Utils.RecyclerviewDisplayDecoration;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.share.ShareUtil;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.Utils.UserUtil;
import com.company.qcy.adapter.pengyouquan.PengyouquanAdapter;
import com.company.qcy.base.BaseFragment;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.bean.pengyouquan.ActionItem;
import com.company.qcy.bean.pengyouquan.MyAddress;
import com.company.qcy.bean.pengyouquan.PengyouquanBean;
import com.company.qcy.ui.activity.chanyezixun.ZixunxiangqingActivity;
import com.company.qcy.ui.activity.pengyouquan.ErjihuatiDetailActivity;
import com.company.qcy.ui.activity.pengyouquan.MapActivity;
import com.company.qcy.ui.activity.pengyouquan.MyPersonInfoActivity;
import com.company.qcy.ui.activity.pengyouquan.PengyouquanDetailActivity;
import com.company.qcy.ui.activity.pengyouquan.PersonInfoActivity;
import com.company.qcy.ui.activity.pengyouquan.ShipinbofangActivity;
import com.company.qcy.ui.activity.user.LoginActivity;
import com.company.qcy.widght.pengyouquan.SnsPopupWindow;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

import java.util.ArrayList;
import java.util.List;

public class RemenFragment extends BaseFragment implements PinglunHouCallBack {
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private View view;

    public RemenFragment() {
        // Required empty public constructor
    }

    public static RemenFragment newInstance(String param1) {
        RemenFragment fragment = new RemenFragment();
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
    public void onRec(MessageBean messageBean) {

        switch (messageBean.getCode()) {
            //删除评论成功
            case MessageBean.RemenCode.DELETEPINGLUNCHENGGONG:
                PengyouquanBean.CommentListBean commentListBean = (PengyouquanBean.CommentListBean) messageBean.getObj();
                List<PengyouquanBean.CommentListBean> commentList = adapter.getData().get(messageBean.getParam()).getCommentList();
                commentList.remove(commentListBean);
                adapter.notifyItemChanged(messageBean.getParam());
                break;
            //发布朋友圈成功
            case MessageBean.RemenCode.FABUPENGYOUQUANCHENGGONG:
                swipeRefreshLayout.setRefreshing(true);
                refreshListener.onRefresh();
                break;
            //登陆成功
            case MessageBean.Code.DELU:
                swipeRefreshLayout.setRefreshing(true);
                refreshListener.onRefresh();
                break;
            //关注成功
            case MessageBean.Code.GUANZHUCHENGGONG:
                swipeRefreshLayout.setRefreshing(true);
                refreshListener.onRefresh();
                break;
            //朋友圈需要刷新
            case MessageBean.RemenCode.PENGYOUQUANNEEDREFLUSH:
                swipeRefreshLayout.setRefreshing(true);
                refreshListener.onRefresh();
                break;

            //朋友圈需要刷新
            case MessageBean.RemenCode.XIANGQINGYEDIANZANCHENGGONG:
                swipeRefreshLayout.setRefreshing(true);
                refreshListener.onRefresh();
                break;
            //微信登陆成功
            case MessageBean.Code.WXLOGIN:
                swipeRefreshLayout.setRefreshing(true);
                refreshListener.onRefresh();
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_remen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        initView(view);
    }

    private RecyclerView recyclerView;
    private PengyouquanAdapter adapter;
    private List<PengyouquanBean> datas;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;
    LinearLayoutManager layoutManager;


    private void initView(View view) {
        recyclerView = view.findViewById(R.id.fragment_pengyouquan_recyclerview);
        swipeRefreshLayout = view.findViewById(R.id.fragment_pengyouquan_swipeRefreshLayout);
        //创建布局管理
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        datas = new ArrayList<>();
        //创建适配器
        adapter = new PengyouquanAdapter(R.layout.item_pengyouquan, datas,getFragmentManager());
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
                        if (!UserUtil.isLogin()) {
                            ActivityUtils.startActivity(LoginActivity.class);
                            return;
                        }
                        SnsPopupWindow snsPopupWindow = new SnsPopupWindow(getContext(), bean.getId(), bean.getIsLike(), position);
                        snsPopupWindow.showPopupWindow(view);
                        snsPopupWindow.setmItemClickListener(new PopupItemClickListener());
                        break;
                    case R.id.item_pengyouquan_shipin_layout:
                        Intent intent = new Intent(getActivity(), ShipinbofangActivity.class);
                        intent.putExtra("url", ServerInfo.IMAGE + bean.getUrl());
                        intent.putExtra("diyizhen", ServerInfo.IMAGE + bean.getVideoPicUrl());
                        ActivityUtils.startActivity(intent);

                        break;
                    case R.id.item_pengyouquan_headimg:
                        if (StringUtils.equals("1", bean.getIsCharger())) {
                            Intent my = new Intent(getActivity(), MyPersonInfoActivity.class);
                            ActivityUtils.startActivity(my);
                        } else {
                            Intent other = new Intent(getActivity(), PersonInfoActivity.class);
                            other.putExtra("userId", bean.getUserId());
                            ActivityUtils.startActivity(other);
                        }
                        break;
                    case R.id.item_pengyouquan_name:
                        Intent name = new Intent(getActivity(), PersonInfoActivity.class);
                        name.putExtra("userId", bean.getUserId());
                        ActivityUtils.startActivity(name);
                        break;
                    case R.id.item_pengyouquan_chakangengduo:
                        Intent detailIntent = new Intent(getActivity(), PengyouquanDetailActivity.class);
                        detailIntent.putExtra("id", bean.getId());
                        ActivityUtils.startActivity(detailIntent);
                        break;
                    case R.id.item_pengyouquan_guanzhu:
                        if (StringUtils.equals("1", bean.getIsFollow())) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setMessage("您是否确定取消关注吗？");
                            builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    cancelFollow(bean.getUserId());
                                }
                            });
                            builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            builder.show();
                        } else {

                            addFollow(bean.getUserId());
                        }
                        break;
                    case R.id.deleteBtn:

                        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                        dialog.setMessage("确认删除这条记录吗？");
                        dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                deleteDyeCommunity(bean.getId(), position);
                            }
                        });
                        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        break;
                    case R.id.item_pengyouquan_detail:
                        Intent moreIntent = new Intent(getActivity(), PengyouquanDetailActivity.class);
                        moreIntent.putExtra("id", bean.getId());
                        ActivityUtils.startActivity(moreIntent);

                        break;

                    case R.id.item_pengyouquan_address:
                        MyAddress address = new MyAddress();
                        address.setLat(bean.getLatitude());
                        address.setLot(bean.getLongitude());
                        address.setTitle(bean.getLocationTitle());
                        address.setContent(bean.getLocationAddress());
                        Intent iAddress = new Intent(getActivity(), MapActivity.class);
                        iAddress.putExtra("address", address);
                        ActivityUtils.startActivity(iAddress);
                        break;
                    case R.id.item_pengyouquan_huati:
                        Intent huati = new Intent(getActivity(),ErjihuatiDetailActivity.class);
                        huati.putExtra("level2TopicId",bean.getTopic().getTopicList().get(0).getId()+"");
                        huati.putExtra("name",bean.getTopic().getTopicList().get(0).getTitle());
                        ActivityUtils.startActivity(huati);
                        break;
                    case R.id.item_pengyouquan_lianjie_layout:
                        Intent lianjieIntent = new Intent(getActivity(), ZixunxiangqingActivity.class);
                        Long id = bean.getShareBean().getId();
                        lianjieIntent.putExtra("id", id+"");
                        ActivityUtils.startActivity(lianjieIntent);
                        break;
                }
            }

        });

        recyclerView.addItemDecoration(new RecyclerviewDisplayDecoration(getContext()));
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_green_light, android.R.color.holo_blue_light);

        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.empty_layout, null));
        adapter.setLoadMoreView(new MyLoadMoreView());

//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    Glide.with(getActivity()).resumeRequests();
//                } else {
//                    Glide.with(getActivity()).pauseRequests();
//                }
//            }
//        });

    }

    private void deleteDyeCommunity(Long communityId, int position) {

        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.CANCLEDYECOMMUNTY)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("token", SPUtils.getInstance().getString("token"))
                .params("id", communityId);

        DialogStringCallback stringCallback = new DialogStringCallback(getActivity()) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.e("CANCLEDYECOMMUNTY", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {

                            Boolean data = jsonObject.getBoolean("data");
                            ToastUtils.showShort(msg);
                            if (data) {
                                adapter.getData().remove(position);
                                adapter.notifyItemRemoved(position);
                                if (position != adapter.getData().size()) { // 如果移除的是最后一个，忽略
                                    adapter.notifyItemRangeChanged(position, adapter.getData().size() - position);
                                }
                            }
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

    private void cancelFollow(Long userId) {

        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.CANCLEFOLLOWBYUSERID)
                .tag(this)

                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("token", SPUtils.getInstance().getString("token"))
                .params("byUserId", userId);

        DialogStringCallback stringCallback = new DialogStringCallback(getActivity()) {
            @Override
            public void onSuccess(Response<String> response) {

                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            LogUtils.e("CANCLEFOLLOWBYUSERID", jsonObject.getString("data"));

                            Boolean data = jsonObject.getBoolean("data");
                            ToastUtils.showShort(msg);
                            if (data) {
                                for (int i = 0; i < datas.size(); i++) {
                                    if (StringUtils.equals(String.valueOf(userId), String.valueOf(datas.get(i).getUserId()))) {
                                        datas.get(i).setIsFollow("0");
                                        datas.get(i).setDyeFollowCount(String.valueOf(Integer.parseInt(datas.get(i).getDyeFollowCount()) - 1));
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            }
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



    private void addFollow(Long followUserId) {

        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.ADDFOLLOWBYUSERID)
                .tag(this)

                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("token", SPUtils.getInstance().getString("token"))
                .params("byUserId", followUserId);

        DialogStringCallback stringCallback = new DialogStringCallback(getActivity()) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.e("ADDFOLLOWBYUSERID", response.body());

                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {

                            Boolean data = jsonObject.getBoolean("data");
                            ToastUtils.showShort(msg);
                            if (data) {

                                for (int i = 0; i < datas.size(); i++) {
                                    if (StringUtils.equals(String.valueOf(followUserId), String.valueOf(datas.get(i).getUserId()))) {

                                        datas.get(i).setIsFollow("1");
                                        datas.get(i).setDyeFollowCount(String.valueOf(Integer.parseInt(datas.get(i).getDyeFollowCount()) + 1));
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            }
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


    private InputMethodManager inputMethodManager;


    private void saveDiscuss(String comment1, Long id, int tieziPositon) {

        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.FABIAOPINGLUN)
                .tag(this)

                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("dyeId", id)
                .params("content", comment1)
                .params("parentId", "")
                .params("from",getActivity().getResources().getString(R.string.app_android))
                .params("token", SPUtils.getInstance().getString("token"));

        DialogStringCallback stringCallback = new DialogStringCallback(getActivity()) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.e("FABIAOPINGLUN", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            PengyouquanBean.CommentListBean commentListBean = data.toJavaObject(PengyouquanBean.CommentListBean.class);
                            PengyouquanBean pengyouquanBean = adapter.getData().get(tieziPositon);
                            List<PengyouquanBean.CommentListBean> commentListBeans = adapter.getData().get(tieziPositon).getCommentList();
                            commentListBeans.add(commentListBean);
                            pengyouquanBean.setCommentList(commentListBeans);
                            adapter.notifyItemChanged(tieziPositon);
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

    private boolean isReflash;
    private int pageNo;

    private void addData() {

        pageNo++;
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.QUERYDYECOMMUNITYLISTREMEN)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("pageNo", pageNo)
                .params("pageSize", 10)
                .params("token", SPUtils.getInstance().getString("token"));

        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                swipeRefreshLayout.setRefreshing(false);
                LogUtils.v("QUERYDYECOMMUNITYLISTREMEN", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONArray data = jsonObject.getJSONArray("data");
                            List<PengyouquanBean> pengyouquanBeans = JSONObject.parseArray(data.toJSONString(), PengyouquanBean.class);
                            if (ObjectUtils.isEmpty(pengyouquanBeans)) {
                                adapter.loadMoreEnd();
                                return;
                            }
                            if (isReflash) {
                                datas.clear();
                                datas.addAll(pengyouquanBeans);
                                adapter.setNewData(datas);
                                isReflash = false;
                                adapter.loadMoreComplete();
                                return;
                            }

                            datas.addAll(pengyouquanBeans);
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
                swipeRefreshLayout.setRefreshing(false);
                ToastUtils.showShort(getResources().getString(R.string.NETEXCEPTION));
            }
        };

        request.execute(stringCallback);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }



    @Override
    public void save(String content, Long id, int poisition) {
        saveDiscuss(content,id,poisition);
    }

    private class PopupItemClickListener implements SnsPopupWindow.OnItemClickListener {
        //动态在列表中的位置
        private long mLasttime = 0;

        public PopupItemClickListener() {
        }

        @Override
        public void onItemClick(ActionItem actionitem, int position, Long id, String isLike, int tieziPosition) {
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
                    if (StringUtils.equals("1", isLike)) {
                        ToastUtils.showShort("暂不能取消点赞");
                    } else {
                        dianZan(id, tieziPosition);
                    }


                    break;
                case 1://发布评论


                    FragmentManager fragmentManager = getFragmentManager();
                    MyDialogFragment dialog = new MyDialogFragment();
                    dialog.setPinglunHouCallBack(RemenFragment.this);
                    dialog.setId(id);
                    dialog.setPosition(tieziPosition);
                    dialog.show(fragmentManager,"dialog");

                    break;

                case 2://分享
                    PengyouquanBean bean = adapter.getData().get(tieziPosition);
                    if (ObjectUtils.isEmpty(bean)) {
                        ToastUtils.showShort("分享异常");
                        return;
                    }
                    LogUtils.e("tieziPosition", bean);
                    String title = "";
                    String content = "";
                    if (StringUtils.isEmpty(bean.getContent())) {
                        title = "【朋友圈】";
                    } else {
                        if (bean.getContent().length() < 11) {
                            title = "【朋友圈】" + bean.getContent() + "...";
                        } else {
                            title = "【朋友圈】" + bean.getContent().substring(0, 10) + "...";
                        }
                        if (bean.getContent().length() < 31) {
                            content = "【朋友圈】" + bean.getContent() + "...";
                        } else {
                            content = "【朋友圈】" + bean.getContent().substring(0, 30) + "...";
                        }
                    }
                    if (!StringUtils.isEmpty(bean.getVideoPicUrl())) {
                        ShareUtil.shareFriendCircle(getContext(), title,
                                content, bean.getVideoPicUrl(), bean.getId());
                        return;
                    } else if (!StringUtils.isEmpty(bean.getPic1())) {
                        ShareUtil.shareFriendCircle(getContext(), title,
                                content, bean.getPic1(), bean.getId());
                        return;
                    } else
                        ShareUtil.shareFriendCircle(getContext(), title,
                                content, "", bean.getId());
                    break;
                default:
                    break;
            }
        }
    }

    private void dianZan(Long id, int tieziPosition) {

        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.DIANZAN)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("dyeId", id)
                .params("parentId", 0)
                .params("token", SPUtils.getInstance().getString("token"));


        DialogStringCallback stringCallback = new DialogStringCallback(getActivity()) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.e("DIANZAN", response.body());
                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {

                            JSONObject data = jsonObject.getJSONObject("data");
                            PengyouquanBean.LikeListBean likeListBean = data.toJavaObject(PengyouquanBean.LikeListBean.class);

                            List<PengyouquanBean.LikeListBean> likeList = adapter.getData().get(tieziPosition).getLikeList();
                            likeList.add(likeListBean);
                            PengyouquanBean pengyouquanBean = adapter.getData().get(tieziPosition);
                            pengyouquanBean.setLikeList(likeList);
                            adapter.notifyItemChanged(tieziPosition);
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

}
