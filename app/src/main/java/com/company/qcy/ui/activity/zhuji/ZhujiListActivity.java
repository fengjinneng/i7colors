package com.company.qcy.ui.activity.zhuji;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.NetworkUtil;
import com.company.qcy.Utils.PermisionUtil;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.Utils.UserUtil;
import com.company.qcy.adapter.zhuji.ZhujiListAdapter;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.base.RequestBackUtil;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.bean.zhuji.ZhujiBean;
import com.company.qcy.ui.activity.user.LoginActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import java.util.ArrayList;
import java.util.List;

public class ZhujiListActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private ZhujiListAdapter adapter;
    private List<ZhujiBean> datas;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    /**
     * 助剂\n定制
     */
    private Button mActivityZhujiListFabu;

    private Long specialId;
    private String imgUrl;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuji_list);
        specialId = getIntent().getLongExtra("specialId",0);
        imgUrl = getIntent().getStringExtra("imgUrl");
        name = getIntent().getStringExtra("name");
        initView();
    }

    private void initView() {
        mActivityZhujiListFabu = (Button) findViewById(R.id.activity_zhuji_list_fabu);
        mActivityZhujiListFabu.setOnClickListener(this);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.activity_zhuji_list_recyclerView);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_zhuji_list_swipeRefreshLayout);
        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        datas = new ArrayList<>();
        adapter = new ZhujiListAdapter(R.layout.item_zhujidingzhi_list, datas);

        recyclerView.setAdapter(adapter);

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                addData();

            }
        }, recyclerView);

        refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉业务
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

        refreshLayout.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_green_light, android.R.color.holo_blue_light);

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                switch (view.getId()) {
                    case R.id.item_zhujidingzhi_list_yijianhujiao:
                        PermisionUtil.callKefu(ZhujiListActivity.this);
                        break;
                }
            }
        });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ZhujiBean zhujiBean = (ZhujiBean) adapter.getData().get(position);
                if(StringUtils.equals("1",zhujiBean.getIsCharger())){
                    Intent intent = new Intent(ZhujiListActivity.this, WodeZhujiDingzhiDetailActivity.class);
                    intent.putExtra("id", zhujiBean.getId());
                    ActivityUtils.startActivity(intent);
                }else {
                    Intent intent = new Intent(ZhujiListActivity.this, ZhujiDetailActivity.class);
                    intent.putExtra("id", zhujiBean.getId());
                    ActivityUtils.startActivity(intent);
                }

            }
        });

        addHeadView();
        mToolbarTitle.setText("助剂定制");

    }


    private void addHeadView() {
        if(StringUtils.isEmpty(imgUrl)){
            return;
        }

        View inflate = LayoutInflater.from(this).inflate(R.layout.head_img_huodong, null);
        ImageView img = inflate.findViewById(R.id.head_img_huodong_img);
        GlideUtils.loadImageRct(context, imgUrl, img);

        adapter.addHeaderView(inflate);
    }

    @Override
    public void onReciveMessage(MessageBean msg) {
        super.onReciveMessage(msg);

        switch (msg.getCode()) {

            case MessageBean.Zhuji.FABUZHUJIDINGZHICHENGGONG:

                pageNo = 0;
                addData();

                break;

            case MessageBean.Code.DELU:
                pageNo = 0;
                addData();
                break;

            case MessageBean.Code.WXLOGIN:
                pageNo = 0;
                addData();
                break;
        }
    }

    private int pageNo;

    private void addData() {
        pageNo++;

        HttpParams params = new HttpParams();

        params.put("sign", SPUtils.getInstance().getString("sign"));
        params.put("token", SPUtils.getInstance().getString("token"));
        params.put("pageNo", pageNo);
        params.put("specialId",specialId);
        params.put("pageSize", 20);

        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.SPECIALZHUJILIST)
                .tag(this)
                .params(params);

        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                refreshLayout.setRefreshing(false);
                LogUtils.v("SPECIALZHUJILIST", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");

                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {

                            RequestBackUtil.parseArray(jsonObject.getJSONArray("data"),
                                    datas, pageNo, adapter, ZhujiBean.class);
                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(ZhujiListActivity.this, request, this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_zhuji_list_fabu:
                if (!NetworkUtil.isNetworkAvailable(ZhujiListActivity.this)) {
                    ToastUtils.showShort("当前网络不可用!");
                    return;
                }
                if (UserUtil.isLogin()) {
                    Intent intent = new Intent(this,PubulishZhujiDiyActivity.class);
                    if(StringUtils.isEmpty(name)||ObjectUtils.isEmpty(specialId)){
                        ToastUtils.showShort("数据错误！");
                        return;
                    }
                    intent.putExtra("name",name);
                    intent.putExtra("specialId",specialId);
                    ActivityUtils.startActivity(intent);
                } else {
                    ActivityUtils.startActivity(LoginActivity.class);
                }
                break;
        }
    }
}
