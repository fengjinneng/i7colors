package com.company.qcy.huodong.caigoulianmeng2.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.company.qcy.R;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.huodong.caigoulianmeng.adapter.WodedinghuodanDetailAdapter;
import com.company.qcy.huodong.caigoulianmeng2.bean.HuodanBean;

import java.util.ArrayList;
import java.util.List;

public class WodedinghuodanDetailActivity extends BaseActivity implements View.OnClickListener {


    private HuodanBean bean;
    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    /**
     * 进行中（结束时间：  2019-01-10）
     */
    private TextView status;
    private RecyclerView recyclerView;
    private List<HuodanBean.MeetingShopBean> datas;
    private WodedinghuodanDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wodedinghuodan_detail);

        bean = getIntent().getParcelableExtra("dinghuodanDetail");

        initView();

        setData();
    }

    private void setData() {

        if (ObjectUtils.isEmpty(bean)) {
            return;
        }
        setStatus();
        mToolbarTitle.setText(bean.getMeetingName());
        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        datas = new ArrayList<>();
        if(ObjectUtils.isEmpty(bean.getMeetingShopList())){
            return;
        }
        datas.addAll(bean.getMeetingShopList());
        adapter = new WodedinghuodanDetailAdapter(R.layout.item_wodeyonghuodan, datas);
        recyclerView.setAdapter(adapter);

    }

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        status = (TextView) findViewById(R.id.dinghuodan_detail_status);
        recyclerView = (RecyclerView) findViewById(R.id.dinghuodan_detail_recyclerview);


    }

    private void setStatus() {

        switch (bean.getIsType()) {
            case "0"://结束
                status.setTextColor(getResources().getColor(R.color.putongwenben));
                status.setText("活动已结束！");
                status.setBackgroundResource(R.color.fengexian);
                break;
            case "1"://未开始
                status.setTextColor(getResources().getColor(R.color.putongwenben));
                status.setText("活动未开始(" + bean.getStartTime().substring(0, 10) + "至" + bean.getEndTime().substring(0, 10) + ")");
                status.setBackgroundResource(R.color.fengexian);
                break;
            case "2"://进行中
                status.setTextColor(getResources().getColor(R.color.baise));
                status.setText("进行中(" + bean.getStartTime().substring(0, 10) + "至" + bean.getEndTime().substring(0, 10) + ")");
                status.setBackground(getResources().getDrawable(R.mipmap.bg));
                break;
        }

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
