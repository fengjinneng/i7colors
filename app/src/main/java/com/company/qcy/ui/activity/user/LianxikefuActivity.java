package com.company.qcy.ui.activity.user;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.company.qcy.R;
import com.company.qcy.Utils.PermisionUtil;
import com.company.qcy.base.BaseActivity;

public class LianxikefuActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    /**
     * 一键呼叫
     */
    private ImageView mActivityLianxikefuYijianhujiao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lianxikefu);
        initView();
    }

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mActivityLianxikefuYijianhujiao = (ImageView) findViewById(R.id.activity_lianxikefu_yijianhujiao);
        mActivityLianxikefuYijianhujiao.setOnClickListener(this);
        mToolbarTitle.setText("联系客服");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_lianxikefu_yijianhujiao:


                PermisionUtil.callKefu(LianxikefuActivity.this);
                break;
        }
    }
}
