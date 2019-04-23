package com.company.qcy.ui.activity.pengyouquan;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.company.qcy.R;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.bean.pengyouquan.MyFriendsBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class ShuikeyikanActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private ConstraintLayout mActivityShuikeyikanGongkai;
    private ConstraintLayout mActivityShuikeyikanQuanbuhaoyou;
    private ConstraintLayout mActivityShuikeyikanXuanzehaoyou;
    private CheckBox mActivityShuikeyikanHaoyouCheckbox;
    private TextView mActivityShuikeyikanHaoyouName;
    private ConstraintLayout mActivityShuikeyikanHaoyouLayout;
    private TextView mToolbarText;
    private CheckBox mActivityShuikeyikanGongkaiCheckBox;
    private CheckBox mActivityShuikeyikanQuanbuhaoyouCheckBox;

    private ArrayList<MyFriendsBean> shuikeyikanDatas;
    private String shukeyikanType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuikeyikan);
        shuikeyikanDatas = getIntent().getParcelableArrayListExtra("shuikeyikan");
        shukeyikanType = getIntent().getStringExtra("shukeyikanType");
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
        mActivityShuikeyikanGongkaiCheckBox = (CheckBox) findViewById(R.id.activity_shuikeyikan_gongkai_checkBox);
        mActivityShuikeyikanQuanbuhaoyouCheckBox = (CheckBox) findViewById(R.id.activity_shuikeyikan_quanbuhaoyou_checkBox);
        mToolbarTitle.setText("谁可以看");
        mToolbarText = (TextView) findViewById(R.id.toolbar_text);
        mToolbarText.setOnClickListener(this);
        mToolbarText.setVisibility(View.VISIBLE);
        mToolbarText.setText("完成");
        mActivityShuikeyikanHaoyouLayout.setOnClickListener(this);

        mActivityShuikeyikanGongkaiCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mActivityShuikeyikanHaoyouCheckbox.setChecked(false);
                    mActivityShuikeyikanQuanbuhaoyouCheckBox.setChecked(false);
                }
            }
        });

        mActivityShuikeyikanQuanbuhaoyouCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mActivityShuikeyikanGongkaiCheckBox.setChecked(false);
                    mActivityShuikeyikanHaoyouCheckbox.setChecked(false);
                }
            }
        });

        mActivityShuikeyikanHaoyouCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mActivityShuikeyikanGongkaiCheckBox.setChecked(false);
                    mActivityShuikeyikanQuanbuhaoyouCheckBox.setChecked(false);
                }
            }
        });


        switch (shukeyikanType) {
            case "1":
                mActivityShuikeyikanGongkaiCheckBox.setChecked(true);
                break;
            case "2":
                mActivityShuikeyikanQuanbuhaoyouCheckBox.setChecked(true);
                break;
            case "3":
                mActivityShuikeyikanHaoyouCheckbox.setChecked(true);
                mActivityShuikeyikanHaoyouLayout.setVisibility(View.VISIBLE);
                List<String> shuikeyikanNameList = new ArrayList<>();
                for (int i = 0; i < shuikeyikanDatas.size(); i++) {
                    if (shuikeyikanDatas.get(i).isChecked()) {
                        shuikeyikanNameList.add(String.valueOf(shuikeyikanDatas.get(i).getUserNickName()));
                    }
                }
                mActivityShuikeyikanHaoyouName.setText((shuikeyikanNameList.toString()).substring(1, shuikeyikanNameList.toString().length() - 1));
                break;

        }

    }


    @Override
    public void onReciveMessage(MessageBean msg) {
        super.onReciveMessage(msg);

        switch (msg.getCode()) {
            case MessageBean.Code.CHOICESHUIKEYIKAN:
                shuikeyikanDatas.clear();
                shuikeyikanDatas.addAll((ArrayList<MyFriendsBean>) msg.getObj());
                List<String> nameList = new ArrayList<>();
                boolean hasData = false;//是否选择了数据
                for (int i = 0; i < shuikeyikanDatas.size(); i++) {
                    if (shuikeyikanDatas.get(i).isChecked()) {
                        nameList.add(shuikeyikanDatas.get(i).getUserNickName());
                        hasData = true;
                    }
                }
                if (hasData) {
                    mActivityShuikeyikanHaoyouCheckbox.setChecked(true);
                    mActivityShuikeyikanHaoyouLayout.setVisibility(View.VISIBLE);
                    mActivityShuikeyikanHaoyouName.setText((nameList.toString()).substring(1, nameList.toString().length() - 1));
                } else {
                    mActivityShuikeyikanHaoyouLayout.setVisibility(View.GONE);
                    mActivityShuikeyikanHaoyouName.setText("");
                }
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
            case R.id.activity_shuikeyikan_gongkai:
                mActivityShuikeyikanGongkaiCheckBox.setChecked(true);
                break;
            case R.id.activity_shuikeyikan_quanbuhaoyou:
                mActivityShuikeyikanQuanbuhaoyouCheckBox.setChecked(true);
                break;
            case R.id.activity_shuikeyikan_haoyou_layout:
                mActivityShuikeyikanHaoyouCheckbox.setChecked(true);
                break;
            case R.id.activity_shuikeyikan_xuanzehaoyou:
                Intent i = new Intent(this, MyTongunluFriendsActivity.class);
                i.putExtra("from", "shuikeyikan");
                i.putParcelableArrayListExtra("shuikeyikan", shuikeyikanDatas);
                ActivityUtils.startActivity(i);
                break;
            case R.id.toolbar_text:
                if (mActivityShuikeyikanGongkaiCheckBox.isChecked()) {
                    EventBus.getDefault().post(new MessageBean(MessageBean.Code.QUEDINGSHUIKAN, "1", null));
                } else if (mActivityShuikeyikanQuanbuhaoyouCheckBox.isChecked()) {
                    EventBus.getDefault().post(new MessageBean(MessageBean.Code.QUEDINGSHUIKAN, "2", null));
                } else if (mActivityShuikeyikanHaoyouCheckbox.isChecked()) {
                    EventBus.getDefault().post(new MessageBean(MessageBean.Code.QUEDINGSHUIKAN, "3", shuikeyikanDatas));
                }else {
                    EventBus.getDefault().post(new MessageBean(MessageBean.Code.QUEDINGSHUIKAN, "1", null));
                }

                finish();
                break;
        }
    }
}
