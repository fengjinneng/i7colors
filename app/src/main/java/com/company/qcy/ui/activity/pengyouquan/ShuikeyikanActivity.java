package com.company.qcy.ui.activity.pengyouquan;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.qcy.R;

public class ShuikeyikanActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private ConstraintLayout mActivityShuikeyikanGongkai;
    private ConstraintLayout mActivityShuikeyikanQuanbuhaoyou;
    private ConstraintLayout mActivityShuikeyikanXuanzehaoyou;
    private CheckBox mActivityShuikeyikanHaoyouCheckbox;
    /**
     * 英雄の证、张学友、出山、生僻字、周杰伦、冯...英雄の证、张学友、出山、生僻字、周杰伦、冯...英雄の证、张学友、出山、生僻字、周杰伦、冯...
     */
    private TextView mActivityShuikeyikanHaoyouName;
    private ConstraintLayout mActivityShuikeyikanHaoyouLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuikeyikan);
        initView();
    }

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mActivityShuikeyikanGongkai = (ConstraintLayout) findViewById(R.id.activity_shuikeyikan_gongkai);
        mActivityShuikeyikanGongkai.setOnClickListener(this);
        mActivityShuikeyikanQuanbuhaoyou = (ConstraintLayout) findViewById(R.id.activity_shuikeyikan_quanbuhaoyou);
        mActivityShuikeyikanQuanbuhaoyou.setOnClickListener(this);
        mActivityShuikeyikanXuanzehaoyou = (ConstraintLayout) findViewById(R.id.activity_shuikeyikan_xuanzehaoyou);
        mActivityShuikeyikanXuanzehaoyou.setOnClickListener(this);
        mActivityShuikeyikanHaoyouCheckbox = (CheckBox) findViewById(R.id.activity_shuikeyikan_haoyou_checkbox);
        mActivityShuikeyikanHaoyouName = (TextView) findViewById(R.id.activity_shuikeyikan_haoyou_name);
        mActivityShuikeyikanHaoyouLayout = (ConstraintLayout) findViewById(R.id.activity_shuikeyikan_haoyou_layout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_shuikeyikan_gongkai:
                break;
            case R.id.activity_shuikeyikan_quanbuhaoyou:
                break;
            case R.id.activity_shuikeyikan_xuanzehaoyou:
                break;
        }
    }
}
