package com.company.qcy.ui.activity.qiugoudating;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.adapter.other.QiugoudatingRecyclerviewAdapter;
import com.company.qcy.adapter.other.QiugouxiangqingRecyclerviewAdapter;
import com.company.qcy.bean.ChanpindatingBean;

import java.util.ArrayList;
import java.util.List;

public class QiugouxiangqingActivity extends AppCompatActivity {

    /**
     * 防紫外线整理剂
     */
    private TextView mActivityQiugouxiangqingShangpinming;
    /**
     * 防紫外线整理剂
     */
    private TextView mActivityQiugouxiangqingSpm;
    /**
     * 助剂 功能性
     */
    private TextView mActivityQiugouxiangqingFenlei;
    /**
     * 50KG/桶
     */
    private TextView mActivityQiugouxiangqingBaozhuang;
    /**
     * 100
     */
    private TextView mActivityQiugouxiangqingShuliang;
    /**
     * 2018-08-04
     */
    private TextView mActivityQiugouxiangqingJiaohuoshijian;
    /**
     * 银行承兑
     */
    private TextView mActivityQiugouxiangqingFukuanfangshi;
    /**
     * 上海市 上海市
     */
    private TextView mActivityQiugouxiangqingDiqu;
    /**
     * 款到发货
     */
    private TextView mActivityQiugouxiangqingZhangqi;
    private RecyclerView mActivityQiugouxiangqingRecyclerview;

    private QiugouxiangqingRecyclerviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qiugouxiangqing);
        initView();
    }

    private void initView() {
        mActivityQiugouxiangqingShangpinming = (TextView) findViewById(R.id.activity_qiugouxiangqing_shangpinming);
        mActivityQiugouxiangqingSpm = (TextView) findViewById(R.id.activity_qiugouxiangqing_spm);
        mActivityQiugouxiangqingFenlei = (TextView) findViewById(R.id.activity_qiugouxiangqing_fenlei);
        mActivityQiugouxiangqingBaozhuang = (TextView) findViewById(R.id.activity_qiugouxiangqing_baozhuang);
        mActivityQiugouxiangqingShuliang = (TextView) findViewById(R.id.activity_qiugouxiangqing_shuliang);
        mActivityQiugouxiangqingJiaohuoshijian = (TextView) findViewById(R.id.activity_qiugouxiangqing_jiaohuoshijian);
        mActivityQiugouxiangqingFukuanfangshi = (TextView) findViewById(R.id.activity_qiugouxiangqing_fukuanfangshi);
        mActivityQiugouxiangqingDiqu = (TextView) findViewById(R.id.activity_qiugouxiangqing_diqu);
        mActivityQiugouxiangqingZhangqi = (TextView) findViewById(R.id.activity_qiugouxiangqing_zhangqi);
        mActivityQiugouxiangqingRecyclerview = (RecyclerView) findViewById(R.id.activity_qiugouxiangqing_recyclerview);


        List<ChanpindatingBean> datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            datas.add(new ChanpindatingBean("山东索玛德染料有限公司" + i));
        }

        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityQiugouxiangqingRecyclerview.setLayoutManager(layoutManager);

        //创建适配器
        adapter = new QiugouxiangqingRecyclerviewAdapter(R.layout.item_qiugouxiangqing_recyclerview, datas);
        adapter.addHeaderView(LayoutInflater.from(this).inflate(R.layout.qiugouxiangqing_headview, null), 0);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_fengexian));
        mActivityQiugouxiangqingRecyclerview.addItemDecoration(itemDecoration);

        //给RecyclerView设置适配器
        mActivityQiugouxiangqingRecyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });
    }


}
