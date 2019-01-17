package com.company.qcy.ui.activity.pengyouquan;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.VideoView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.company.qcy.R;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.MyNicePlayerController;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.base.BaseActivity;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

import java.net.URL;

import cn.jzvd.JZMediaManager;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class ShipinbofangActivity extends BaseActivity {

    private Uri uri;
    private NiceVideoPlayer niceVideoPlayer;
    private String url;

    private String diyizhen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipinbofang);
        String uriExtra = getIntent().getStringExtra("uri");
        diyizhen = getIntent().getStringExtra("diyizhen");
        if (StringUtils.isEmpty(uriExtra)) {
            url = getIntent().getStringExtra("url");
        } else {
            uri = Uri.parse(uriExtra);
        }
        initView();
        LogUtils.v("activity_shipinbofang",url);

    }

    private void initView() {
        niceVideoPlayer = (NiceVideoPlayer) findViewById(R.id.activity_shipinbofang_nicevideo);
        niceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_NATIVE);
        niceVideoPlayer.setUp(url, null);
        if (StringUtils.isEmpty(url)) {
            niceVideoPlayer.setUp(uri.toString(),null);
        } else {
            niceVideoPlayer.setUp(url,null);
        }
        MyNicePlayerController controller = new MyNicePlayerController(this);
        controller.setTitle("视频播放");
        GlideUtils.loadImage(ShipinbofangActivity.this,diyizhen,controller.imageView());
        niceVideoPlayer.setController(controller);
        niceVideoPlayer.start();

    }

    @Override
    protected void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

    @Override

    public void onBackPressed() {

        // 在全屏或者小窗口时按返回键要先退出全屏或小窗口，

        // 所以在 Activity 中 onBackPress 要交给 NiceVideoPlayer 先处理。

        if (NiceVideoPlayerManager.instance().onBackPressd()) return;

        super.onBackPressed();

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

