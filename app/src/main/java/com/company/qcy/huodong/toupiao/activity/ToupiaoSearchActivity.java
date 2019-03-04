package com.company.qcy.huodong.toupiao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.MyLoadMoreView;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.Utils.UserUtil;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.base.SearchTypeActivity;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.huodong.toupiao.adapter.XuanshouAdapter;
import com.company.qcy.huodong.toupiao.bean.XuanshouBean;
import com.company.qcy.ui.activity.user.LoginActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class ToupiaoSearchActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    /**
     * 请输入参选编号或名称
     */
    private EditText search;
    private RecyclerView recyclerView;
    private XuanshouAdapter adapter;
    private List<XuanshouBean> datas;
    private String id;//mainId

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toupiao_search);
        id = getIntent().getStringExtra("id");
        initView();
    }

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        search = (EditText) findViewById(R.id.activity_toupiao_search);
        recyclerView = (RecyclerView) findViewById(R.id.activity_toupiao_search_recyclerview);
        mToolbarTitle.setText("搜索");
        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        datas = new ArrayList<>();
        adapter = new XuanshouAdapter(R.layout.item_toupiao_xuanshou, datas);
        recyclerView.setAdapter(adapter);


        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    pageNo = 0;
                    KeyboardUtils.hideSoftInput(ToupiaoSearchActivity.this);
                    addData();
                }
                return false;
            }
        });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                XuanshouBean xuanshouBean = (XuanshouBean) adapter.getData().get(position);
                Intent intent = new Intent(ToupiaoSearchActivity.this, XuanshouDetailActivity.class);
                intent.putExtra("mainId", id);
                intent.putExtra("id", xuanshouBean.getId() + "");
                ActivityUtils.startActivity(intent);
            }
        });

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(!UserUtil.isLogin()){
                    ActivityUtils.startActivity(LoginActivity.class);
                    return;
                }
                Button toupiao = (Button) adapter.getViewByPosition(recyclerView, position, R.id.item_toupiao_xuanshou_toupiao);
                XuanshouBean xuanshouBean = (XuanshouBean) adapter.getData().get(position);
                toupiao(toupiao, xuanshouBean, position);

            }
        });

        adapter.setLoadMoreView(new MyLoadMoreView());

    }

    private void toupiao(Button button, XuanshouBean xuanshouBean, int positon) {
        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.TOUPIAO)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("token", SPUtils.getInstance().getString("token"))
                .params("mainId", id)
                .params("applicationId", xuanshouBean.getId())
                .params("from", "app_android");

        DialogStringCallback stringCallback = new DialogStringCallback(this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("TOUPIAO", response.body());
                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            button.setText("已投" + (Integer.parseInt(xuanshouBean.getJoinedTicketNum()) + 1) + "票");
                            xuanshouBean.setTicketNum(String.valueOf(Integer.parseInt(xuanshouBean.getTicketNum()) + 1));
                            xuanshouBean.setJoinedTicketNum(String.valueOf(Integer.parseInt(xuanshouBean.getJoinedTicketNum()) + 1));
                            adapter.notifyItemChanged(positon);
                            EventBus.getDefault().post(new MessageBean(MessageBean.Code.TOUPIAOCHENGGONG));
                            return;
                        }

                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(ToupiaoSearchActivity.this, request, this);
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
    public void onReciveMessage(MessageBean msg) {
        super.onReciveMessage(msg);

        switch (msg.getCode()){

            case MessageBean.Code.TOUPIAOCHENGGONG:

                pageNo = 0;
                addData();

                break;
        }
    }

    private int pageNo;

    private void addData() {
        if (StringUtils.isEmpty(search.getText().toString())) {
            ToastUtils.showShort("请输入要搜索的内容");
            return;
        }
        pageNo++;
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.CANSHAIRENYUANLIST)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("pageNo", pageNo)
                .params("pageSize", 20)
                .params("mainId", id)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("name", search.getText().toString())
                .params("number", search.getText().toString());

        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("CANSHAIRENYUANLIST", response.body());

                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONArray data = jsonObject.getJSONArray("data");

                            if (ObjectUtils.isEmpty(data)) {
                                if (pageNo == 1) {
                                    ToastUtils.showShort("没有搜索到相关结果!");
                                }
                                adapter.loadMoreEnd();
                                return;
                            }
                            List<XuanshouBean> youhuizhanxiaoBeans = JSONObject.parseArray(data.toJSONString(), XuanshouBean.class);
                            if (pageNo == 1) {
                                datas.clear();
                            }
                            datas.addAll(youhuizhanxiaoBeans);
                            adapter.setNewData(datas);
                            adapter.loadMoreComplete();
                            adapter.disableLoadMoreIfNotFullPage();
                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(ToupiaoSearchActivity.this, request, this);
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
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                finish();
                break;
        }
    }
}
