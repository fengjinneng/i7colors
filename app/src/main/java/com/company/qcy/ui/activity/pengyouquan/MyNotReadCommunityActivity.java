package com.company.qcy.ui.activity.pengyouquan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.pengyouquan.MyNotReadCommunityAdapter;
import com.company.qcy.bean.pengyouquan.PengyouquanBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import java.util.ArrayList;
import java.util.List;

public class MyNotReadCommunityActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private MyNotReadCommunityAdapter adapter;
    private List<PengyouquanBean.CommentListBean> datas;
    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_not_read_community);
        initView();
    }

    private void initView() {
        datas = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.activity_my_not_read_community_recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        adapter = new MyNotReadCommunityAdapter(R.layout.item_my_not_read_community, datas);

        recyclerView.setAdapter(adapter);


        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mToolbarTitle.setText("新的消息");
        addData();

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                PengyouquanBean.CommentListBean bean = (PengyouquanBean.CommentListBean) adapter.getData().get(position);

                switch (view.getId()){

                    case R.id.item_my_mot_read_img:
                        Intent i = new Intent(MyNotReadCommunityActivity.this, PersonInfoActivity.class);
                        i.putExtra("userId", bean.getUserId());
                        ActivityUtils.startActivity(i);
                        break;
                }

            }
        });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PengyouquanBean.CommentListBean bean = (PengyouquanBean.CommentListBean) adapter.getData().get(position);
                Intent i = new Intent(MyNotReadCommunityActivity.this, PengyouquanDetailActivity.class);
                i.putExtra("id", bean.getDyeId());
                ActivityUtils.startActivity(i);
            }
        });


    }

    private void addData() {

        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.QUERYNOTREADCOMMENTLIST)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("token", SPUtils.getInstance().getString("token"));

        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("QUERYNOTREADCOMMENTLIST", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            String data = jsonObject.getString("data");
                            if (StringUtils.isEmpty(data)) {
                                ToastUtils.showShort(msg);
                                return;
                            }
                            List<PengyouquanBean.CommentListBean> commentListBeans =
                                    JSONObject.parseArray(data, PengyouquanBean.CommentListBean.class);
                            adapter.addData(commentListBeans);
                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(MyNotReadCommunityActivity.this, request, this);
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
