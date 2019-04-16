package com.company.qcy;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.company.qcy.adapter.pengyouquan.HuatiAdapter;
import com.company.qcy.bean.pengyouquan.HuatiBean;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    HuatiAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        recyclerView  = findViewById(R.id.rec);
        List<HuatiBean> da = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            da.add(new HuatiBean());
        }
        adapter = new HuatiAdapter(R.layout.fragment_huati,da);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
