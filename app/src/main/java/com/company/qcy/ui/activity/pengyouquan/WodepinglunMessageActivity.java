package com.company.qcy.ui.activity.pengyouquan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.MyLoadMoreView;
import com.company.qcy.Utils.RecyclerViewNoBugLayoutManager;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.pengyouquan.WodepinglunMessageAdapter;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.pengyouquan.WodePinglunMessageBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

import java.util.ArrayList;
import java.util.List;

public class WodepinglunMessageActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;
    private WodepinglunMessageAdapter adapter;
    private List<WodePinglunMessageBean> datas;
    private int pageNo;
    private boolean isRefresh;
    /**
     * 全标记已读
     */
    private TextView mToolbarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wodepinglun_message);
        initView();
    }

    private void initView() {
        datas = new ArrayList<>();
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.activity_wodepinglun_message_recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_wodepinglun_message_swipeRefreshLayout);
        mToolbarTitle.setText("评论");
        mToolbarText = (TextView) findViewById(R.id.toolbar_text);
        mToolbarText.setOnClickListener(this);
        recyclerView.setLayoutManager(new RecyclerViewNoBugLayoutManager(this));
        adapter = new WodepinglunMessageAdapter(R.layout.item_wodepinglun_message, datas);
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                pageNo = 0;
                addData();
            }
        };
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                onRefreshListener.onRefresh();
            }
        });

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                addData();
            }
        }, recyclerView);
        adapter.setLoadMoreView(new MyLoadMoreView());


        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                WodePinglunMessageBean wodePinglunMessageBean = (WodePinglunMessageBean) adapter.getData().get(position);



                Intent intent = new Intent(WodepinglunMessageActivity.this, PengyouquanDetailActivity.class);
                intent.putExtra("id", wodePinglunMessageBean.getCommunityId());
                if (StringUtils.equals("0", wodePinglunMessageBean.getIsRead())) {
                    intent.putExtra("from", "pinglun");
                    intent.putExtra("pinlunId", wodePinglunMessageBean.getId() + "");
                }
                wodePinglunMessageBean.setIsRead("1");
                adapter.notifyItemChanged(position);
                ActivityUtils.startActivity(intent);



            }
        });

        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                WodePinglunMessageBean wodePinglunMessageBean = (WodePinglunMessageBean) adapter.getData().get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(WodepinglunMessageActivity.this);
                builder.setTitle("提示");
                builder.setMessage("确定要删除这条评论消息吗");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deletePinglunMessage(wodePinglunMessageBean.getId(), position);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();

                return false;
            }
        });


    }


    private void deletePinglunMessage(Long id, int position) {

        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.DELETEPINGLUNMESSAGE)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("token", SPUtils.getInstance().getString("token"))
                .params("id", id);

        DialogStringCallback stringCallback = new DialogStringCallback(this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.e("DELETEPINGLUNMESSAGE", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            Boolean data = jsonObject.getBoolean("data");
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
                            SignAndTokenUtil.getSign(WodepinglunMessageActivity.this, request, this);
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


    private void addData() {
        pageNo++;
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.PINGLUNXIAOXILIST)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("token", SPUtils.getInstance().getString("token"))
                .params("pageNo", pageNo)
                .params("pageSize", 20);

        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("PINGLUNXIAOXILIST", response.body());
                swipeRefreshLayout.setRefreshing(false);
                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONArray data = jsonObject.getJSONArray("data");
                            if (ObjectUtils.isEmpty(data)) {
                                adapter.loadMoreEnd();
                                return;
                            }
                            List<WodePinglunMessageBean> wodePinglunMessageBeans =
                                    JSONObject.parseArray(data.toString(), WodePinglunMessageBean.class);

                            if (isRefresh) {
                                datas.clear();
                                datas.addAll(wodePinglunMessageBeans);
                                adapter.setNewData(datas);
                                adapter.loadMoreComplete();
                                isRefresh = false;
                                return;
                            }
                            datas.addAll(wodePinglunMessageBeans);
                            adapter.setNewData(datas);
                            adapter.loadMoreComplete();

                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(WodepinglunMessageActivity.this, request, this);
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
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.toolbar_text:
                quanBiaoweiyidu("dyeComment");
                break;
        }
    }

    private void quanBiaoweiyidu(String type) {

        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.BIAOWEIYIDUMESSAGE)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("token",SPUtils.getInstance().getString("token"))
                .params("type", type);

        DialogStringCallback stringCallback = new DialogStringCallback(this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.e("BIAOWEIYIDUMESSAGE", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            Boolean data = jsonObject.getBoolean("data");
                            if (data) {
                                for (int i = 0; i < datas.size(); i++) {
                                    datas.get(i).setIsRead("1");
                                }
                                adapter.notifyDataSetChanged();
                            }else {
                                ToastUtils.showShort(msg);
                            }
                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(WodepinglunMessageActivity.this, request, this);
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
