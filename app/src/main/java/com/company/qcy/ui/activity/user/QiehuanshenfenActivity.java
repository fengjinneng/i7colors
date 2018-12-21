package com.company.qcy.ui.activity.user;

import android.app.Activity;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.company.qcy.R;

public class QiehuanshenfenActivity extends Activity implements View.OnClickListener {

    private ImageView mActivityQiehuanshenfenImg1;
    /**
     * 买家中心
     */
    private TextView mActivityQiehuanshenfenText1;
    private ConstraintLayout mActivityQiehuanshenfenLayout1;
    private ImageView mActivityQiehuanshenfenImg2;
    /**
     * 卖家中心
     */
    private TextView mActivityQiehuanshenfenText2;
    private ConstraintLayout mActivityQiehuanshenfenLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qiehuanshenfen);
        initView();
    }

    private void initView() {
        mActivityQiehuanshenfenImg1 = (ImageView) findViewById(R.id.activity_qiehuanshenfen_img_1);
        mActivityQiehuanshenfenText1 = (TextView) findViewById(R.id.activity_qiehuanshenfen_text_1);
        mActivityQiehuanshenfenLayout1 = (ConstraintLayout) findViewById(R.id.activity_qiehuanshenfen_layout1);
        mActivityQiehuanshenfenImg2 = (ImageView) findViewById(R.id.activity_qiehuanshenfen_img_2);
        mActivityQiehuanshenfenText2 = (TextView) findViewById(R.id.activity_qiehuanshenfen_text_2);
        mActivityQiehuanshenfenLayout2 = (ConstraintLayout) findViewById(R.id.activity_qiehuanshenfen_layout2);
        mActivityQiehuanshenfenLayout1.setOnClickListener(this);
        mActivityQiehuanshenfenLayout2.setOnClickListener(this);
        if(StringUtils.equals(SPUtils.getInstance().getString("identity"),"1")){
            //买家
            mActivityQiehuanshenfenLayout1.setBackground(getResources().getDrawable(R.drawable.shape_change_shenfen_bg));
            mActivityQiehuanshenfenLayout2.setBackground(getResources().getDrawable(R.drawable.shape_change_shenfen_bg_uncheck));
        }else if(StringUtils.equals(SPUtils.getInstance().getString("identity"),"2")){
            mActivityQiehuanshenfenLayout1.setBackground(getResources().getDrawable(R.drawable.shape_change_shenfen_bg_uncheck));
            mActivityQiehuanshenfenLayout2.setBackground(getResources().getDrawable(R.drawable.shape_change_shenfen_bg));
    }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_qiehuanshenfen_layout1:
                SPUtils.getInstance().put("identity","1");
                finish();
                break;

            case R.id.activity_qiehuanshenfen_layout2:
                SPUtils.getInstance().put("identity","2");
                finish();

                break;
        }
    }
}
