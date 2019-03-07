package com.company.qcy.huodong.jingpai.adapter;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.qcy.R;
import com.company.qcy.Utils.MyNicePlayerController;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.huodong.jingpai.bean.JingpaiDetailBean;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import java.util.List;

public class VideoAdapter extends BaseQuickAdapter<JingpaiDetailBean.VideoListBean, BaseViewHolder> {

    public VideoAdapter(int layoutResId, @Nullable List<JingpaiDetailBean.VideoListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, JingpaiDetailBean.VideoListBean item) {

        NiceVideoPlayer niceVideoPlayer = (NiceVideoPlayer) helper.getView(R.id.item_jingpai_video_nicevideoplayer);
        niceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_NATIVE);
        niceVideoPlayer.setUp(ServerInfo.IMAGE+item.getVideoUrl(), null);

        MyNicePlayerController controller = new MyNicePlayerController(mContext);
        controller.setTitle("视频播放");
//        GlideUtils.loadImage(ShipinbofangActivity.this,diyizhen,controller.imageView());
        niceVideoPlayer.setController(controller);
//        niceVideoPlayer.start();
    }
}
