package com.company.qcy.live;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.company.qcy.R;
import com.company.qcy.base.BaseActivity;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class LiveReplayActivity extends BaseActivity implements View.OnClickListener {

    private String url;
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;

    private String img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_replay);
        initView();


        url = getIntent().getStringExtra("url");
        img = getIntent().getStringExtra("img");


        JZVideoPlayerStandard jzVideoPlayerStandard = (JZVideoPlayerStandard) findViewById(R.id.jzvd);
        jzVideoPlayerStandard.setUp(url,
                JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,
                "");
        Glide.with(getApplicationContext()).load(img)
                .into(jzVideoPlayerStandard.thumbImageView);

    }


    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mToolbarTitle.setText("视频回放");
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
