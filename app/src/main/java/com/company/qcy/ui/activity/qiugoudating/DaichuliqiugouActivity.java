package com.company.qcy.ui.activity.qiugoudating;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
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
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.qiugou.WodeqiugouxiangqingAdapter;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.bean.qiugou.QiugouBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import java.util.ArrayList;
import java.util.List;

public class DaichuliqiugouActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 求购中
     */
    private TextView mActivityDaichuliqiugouTitle;
    private ImageView mActivityDaichuliqiugouBack;
    /**
     * (待确认报价)
     */
    private TextView mActivityDaichuliqiugouStatus;
    private RecyclerView recyclerview;

    private WodeqiugouxiangqingAdapter adapter;
    private List<QiugouBean> datas;

    //询盘中 1，待确认报价2，即将过期3
    private int mudi;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daichuliqiugou);
        initView();

    }

    private void initView() {
        mActivityDaichuliqiugouTitle = (TextView) findViewById(R.id.activity_daichuliqiugou_title);
        mActivityDaichuliqiugouBack = (ImageView) findViewById(R.id.activity_daichuliqiugou_back);
        mActivityDaichuliqiugouBack.setOnClickListener(this);
        mActivityDaichuliqiugouStatus = (TextView) findViewById(R.id.activity_daichuliqiugou_status);
        recyclerview = (RecyclerView) findViewById(R.id.activity_daichuliqiugou_recyclerview);
        datas = new ArrayList<>();
        mudi = getIntent().getIntExtra("mudi", 0);
        switch (mudi) {
            case 1:
                url = ServerInfo.SERVER + InterfaceInfo.XUNPANZHONGLIST;
                mActivityDaichuliqiugouStatus.setText("(暂无人报价)");
                break;
            case 2:
                url = ServerInfo.SERVER + InterfaceInfo.DAIQUERENBAOJIALIST;
                mActivityDaichuliqiugouStatus.setText("(待确认报价)");
                break;
            case 3:
                url = ServerInfo.SERVER + InterfaceInfo.JIJIANGGUOQILIST;
                mActivityDaichuliqiugouStatus.setText("(即将过期)");
                break;
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_fengexian));
        recyclerview.addItemDecoration(itemDecoration);
        recyclerview.setLayoutManager(layoutManager);
        //创建适配器
        adapter = new WodeqiugouxiangqingAdapter(R.layout.item_wode_qiugouliebiao, datas);
        adapter.setEnableLoadMore(false);
        recyclerview.setAdapter(adapter);
        addData();

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                addData();


            }
        }, recyclerview);

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                Intent intent = new Intent(DaichuliqiugouActivity.this, QiugouxiangqingActivity.class);
                QiugouBean qiugouliebiaoBean = (QiugouBean) adapter.getData().get(position);
                intent.putExtra("enquiryId", qiugouliebiaoBean.getId());
                intent.putExtra("wode", 1);
                intent.putExtra("status", qiugouliebiaoBean.getStatus());
                ActivityUtils.startActivity(intent);
            }
        });
        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.empty_layout,null));
    }


    private int pageNo;

    private void addData() {
        pageNo++;
        if (StringUtils.isEmpty(url)) {
            return;
        }
        GetRequest<String> request = OkGo.<String>get(url)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("token", SPUtils.getInstance().getString("token"))
                .params("pageNo", pageNo)
                .params("pageSize", 20);


        DialogStringCallback stringCallback = new DialogStringCallback(this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("DAICHULIQIUGOU", response.body());

                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONArray data = jsonObject.getJSONArray("data");

                            List<QiugouBean> QiugouxiangqingBeans = JSONObject.parseArray(data.toJSONString(), QiugouBean.class);
//                                    if (isReflash) {
//                                        datas.clear();
//                                        adapter.addData(qiugoudatingBeans);
//                                        isReflash = false;
//                                        refreshLayout.setRefreshing(false);
//                                        return;
//                                    }
                            if (ObjectUtils.isEmpty(QiugouxiangqingBeans)) {
                                adapter.loadMoreEnd();
                                return;
                            }
                            adapter.addData(QiugouxiangqingBeans);
                            adapter.loadMoreComplete();
                            adapter.disableLoadMoreIfNotFullPage();
                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(DaichuliqiugouActivity.this,request,this);
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
            case MessageBean.Code.CAINABAOJIACHENGGONG:
                datas.clear();
                pageNo=0;
                addData();
                break;
            case MessageBean.Code.GUANBIQIUGOU:
                datas.clear();
                pageNo=0;
                addData();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.activity_daichuliqiugou_back:

                finish();
                break;
        }
    }
}
