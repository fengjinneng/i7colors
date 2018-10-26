package com.company.qcy.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.company.qcy.R;
import com.company.qcy.adapter.kaifangshangcheng.KaifangshangchengRecyclerviewAdapter;
import com.company.qcy.bean.ChanpindatingBean;

import java.util.ArrayList;
import java.util.List;

public class KaifangshangchengActivity extends AppCompatActivity {

    /**
     * 搜索您想要的商品
     */
    private EditText mKaifangshangchengSearch;
    private RecyclerView mKaifangshangchengRecyclerview;
    private KaifangshangchengRecyclerviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaifangshangcheng);
        initView();
    }

    private void initView() {
        mKaifangshangchengSearch = (EditText) findViewById(R.id.kaifangshangcheng_search);
        mKaifangshangchengRecyclerview = (RecyclerView) findViewById(R.id.kaifangshangcheng_recyclerview);
        List<ChanpindatingBean> datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            datas.add(new ChanpindatingBean("山东索玛德染料有限公司"+i));
        }

        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mKaifangshangchengRecyclerview.setLayoutManager(layoutManager);


        //创建适配器
        adapter = new KaifangshangchengRecyclerviewAdapter(R.layout.item_kaifangshangcheng_recyclerview, datas);

        //给RecyclerView设置适配器
        mKaifangshangchengRecyclerview.setAdapter(adapter);

    }
}
