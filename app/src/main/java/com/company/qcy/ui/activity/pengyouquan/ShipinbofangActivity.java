package com.company.qcy.ui.activity.pengyouquan;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.VideoView;

import com.company.qcy.R;

import cn.jzvd.JZMediaManager;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class ShipinbofangActivity extends AppCompatActivity {

    private VideoView videoPlayer;
    private Uri uri;
    private JzvdStd mActivityShipinbofangJzvideo;


    JZMediaManager manager = new JZMediaManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipinbofang);
        String stringExtra = getIntent().getStringExtra("uri");
        uri = Uri.parse(stringExtra);
        initView();


    }

    private void initView() {
//        videoPlayer = (VideoView) findViewById(R.id.activity_shipinbofang_videoview);
//
//        MediaController mediaController = new MediaController(this);
//
//        //设置videoview的控制条
//        //设置播放完成以后监听
//        videoPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//            }
//        });
//        //设置发生错误监听，如果不设置videoview会向用户提示发生错误
//        videoPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
//            @Override
//            public boolean onError(MediaPlayer mp, int what, int extra) {
//                return false;
//            }
//        });        //设置在视频文件在加载完毕以后的回调函数
//        videoPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                videoPlayer.start();
//
//            }
//        });
//        videoPlayer.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return false;
//            }
//        });
//
//
//        videoPlayer.setMediaController(mediaController);
//
//        videoPlayer.setVideoURI(uri);
//        setFull();

        mActivityShipinbofangJzvideo = (JzvdStd) findViewById(R.id.activity_shipinbofang_jzvideo);

        mActivityShipinbofangJzvideo.setUp(uri.toString(), "视频播放", JzvdStd.SCREEN_WINDOW_NORMAL);
        mActivityShipinbofangJzvideo.startVideo();
        mActivityShipinbofangJzvideo.backButton.setVisibility(View.VISIBLE);
        mActivityShipinbofangJzvideo.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mActivityShipinbofangJzvideo.thumbImageView.setImageBitmap(getLocalVideoThumbnail(uri.toString()));
    }

    private void setFull() {
        ConstraintLayout.LayoutParams LayoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);
        videoPlayer.setLayoutParams(LayoutParams);
    }

    private int intPositionWhenPause = -1;


    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }


    /**

     * 获取本地视频的第一帧

     *

     * @param filePath

     * @return

     */

    public static Bitmap getLocalVideoThumbnail(String filePath) {

        Bitmap bitmap = null;

        //MediaMetadataRetriever 是android中定义好的一个类，提供了统一

        //的接口，用于从输入的媒体文件中取得帧和元数据；

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();

        try {

            //根据文件路径获取缩略图

            retriever.setDataSource(filePath);

            //获得第一帧图片

            bitmap = retriever.getFrameAtTime();

        } catch (IllegalArgumentException e) {

            e.printStackTrace();

        } finally {

            retriever.release();

        }

        return bitmap;

    }


}

