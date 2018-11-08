package com.company.qcy.ui.activity.kaifangshangcheng;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.kaifangshangcheng.KaifangshangchengRecyclerviewAdapter;
import com.company.qcy.base.SearchTypeActivity;
import com.company.qcy.bean.kaifangshangcheng.DianpuliebiaoBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

public class KaifangshangchengActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 搜索您想要的商品
     */
    private RecyclerView recyclerView;
    private KaifangshangchengRecyclerviewAdapter adapter;
    /**
     * 搜索您想要的商品
     */
    private TextView mKaifangshengchengSearch;
    private List<DianpuliebiaoBean> datas;
    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaifangshangcheng);
        initView();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.kaifangshangcheng_recyclerview);
        datas = new ArrayList<>();

        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        //创建适配器
        adapter = new KaifangshangchengRecyclerviewAdapter(R.layout.item_kaifangshangcheng_recyclerview, datas);
        adapter.setEnableLoadMore(false);
        //给RecyclerView设置适配器
        recyclerView.setAdapter(adapter);
        mKaifangshengchengSearch = (TextView) findViewById(R.id.kaifangshengcheng_search);
        addData();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DianpuliebiaoBean dianpuliebiaoBean = (DianpuliebiaoBean) adapter.getData().get(position);
                Intent intent = new Intent(KaifangshangchengActivity.this, KFSCXiangqingActivity.class);
                intent.putExtra("id", dianpuliebiaoBean.getId());
                ActivityUtils.startActivity(intent);
            }
        });
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mToolbarTitle.setText("开放商城");
        mKaifangshengchengSearch.setOnClickListener(this);
    }

    private int pageNo;

    private void addData() {
        pageNo++;
        OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.DIANPULIEBIAO)
                .tag(this)

                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("pageNo", pageNo)
                .params("pageSize", 20)
                .execute(new DialogStringCallback(this) {
                    @Override
                    public void onSuccess(Response<String> response) {

                        try {
                            if (response.code() == 200) {

                                JSONObject jsonObject = JSONObject.parseObject(response.body());

                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                    JSONArray data = jsonObject.getJSONArray("data");
                                    LogUtils.v("QIUGOULIEBIAO", data);
                                    List<DianpuliebiaoBean> dianpuliebiaoBeans = JSONObject.parseArray(data.toJSONString(), DianpuliebiaoBean.class);
//                                    if (isReflash) {
//                                        datas.clear();
//                                        adapter.addData(qiugouBeans);
//                                        isReflash = false;
//                                        refreshLayout.setRefreshing(false);
//                                        adapter.loadMoreComplete();
//
//                                        return;
//                                    }
                                    if (ObjectUtils.isEmpty(dianpuliebiaoBeans)) {
                                        adapter.loadMoreEnd();
                                        return;
                                    }
                                    adapter.addData(dianpuliebiaoBeans);
                                    adapter.loadMoreComplete();
                                    adapter.disableLoadMoreIfNotFullPage();
                                    return;

                                } else
                                    SignAndTokenUtil.checkSignAndToken(KaifangshangchengActivity.this, jsonObject);

                            } else {
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
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.kaifangshengcheng_search:

                Intent intent = new Intent(KaifangshangchengActivity.this, SearchTypeActivity.class);
                intent.putExtra("isFrom", 3);
                intent.putExtra("keyword", "");
                ActivityUtils.startActivity(intent);
                break;
        }
    }
}
