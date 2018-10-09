package com.company.qcy.ui.activity.qiugoudating;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.adapter.other.QiugoudatingRecyclerviewAdapter;
import com.company.qcy.bean.ChanpindatingBean;

import java.util.ArrayList;
import java.util.List;

public class QiugoudatingActivity extends AppCompatActivity {

    /**
     * 搜索您想要的商品
     */
    private EditText mQiugoudatingSearch;
    private RecyclerView mQiugoudatingRecyclerview;
    private QiugoudatingRecyclerviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qiugoudating);
        initView();
    }

    private void initView() {
        mQiugoudatingSearch = (EditText) findViewById(R.id.qiugoudating_search);
        mQiugoudatingRecyclerview = (RecyclerView) findViewById(R.id.qiugoudating_recyclerview);

        List<ChanpindatingBean> datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            datas.add(new ChanpindatingBean("山东索玛德染料有限公司"+i));
        }
        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mQiugoudatingRecyclerview.setLayoutManager(layoutManager);

        //创建适配器
        adapter = new QiugoudatingRecyclerviewAdapter(R.layout.item_qiugoudating_recyclerview, datas);

        //给RecyclerView设置适配器
        mQiugoudatingRecyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(QiugoudatingActivity.this,FabuqiugouActivity.class));
            }
        });

    }
}
