package com.company.qcy.Utils.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.ServerInfo;

import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.themes.classic.PlatformPageAdapter;
import cn.sharesdk.onekeyshare.themes.classic.land.PlatformPageAdapterLand;
import cn.sharesdk.onekeyshare.themes.classic.port.PlatformPageAdapterPort;

public class ShareUtil {


    /**
     *     求购分享  /enquiry/enquiryDetail
     *     产品分享   /product/productDetail
     *     朋友群分享  /friendcircle/friendcircleDetail
     *     投票详情   @Route(path = "/vote/detail")
     *     投票选手   @Route(path = "/vote/player/detail")
     */


    //分享投票
    public static void shareVoteDetail(Context context, String title, String content, String imgUrl, String id) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(content);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        if (StringUtils.isEmpty(imgUrl)) {
            oks.setImageUrl("http://static.i7colors.com/i7colors_logo.png");

        } else {
            oks.setImageUrl(ServerInfo.IMAGE + imgUrl);
        }

        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://mobile.i7colors.com/groupBuyMobile/openApp/voteList.html?id=" + id);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl("http://sharesdk.cn");

//      启动分享GUI
        oks.show(context);
    }


    //分享投票选手
    public static void shareVotePlayerDetail(Context context, String title, String content, String imgUrl, String id,String mainId) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(content);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        if (StringUtils.isEmpty(imgUrl)) {
            oks.setImageUrl("http://static.i7colors.com/i7colors_logo.png");

        } else {
            oks.setImageUrl(ServerInfo.IMAGE + imgUrl);
        }

        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://mobile.i7colors.com/groupBuyMobile/openApp/voteMessage.html?id=" + id+"&mainId="+mainId);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl("http://sharesdk.cn");

//      启动分享GUI
        oks.show(context);
    }


    //分享产品
    public static void shareProduct(Context context, String title, String content, String imgUrl, String id) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(content);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        oks.setImageUrl(ServerInfo.IMAGE + imgUrl);

        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://mobile.i7colors.com/groupBuyMobile/openApp/shopDetails.html?id=" + id);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl("http://sharesdk.cn");

//      启动分享GUI
        oks.show(context);
    }


    //分享朋友圈详情
    public static void shareFriendCircle(Context context, String title, String content, String imgUrl, Long id) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(content);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        if (StringUtils.isEmpty(imgUrl)) {
            oks.setImageUrl("http://static.i7colors.com/i7colors_logo.png");

        } else {
            oks.setImageUrl(ServerInfo.IMAGE + imgUrl);

        }

        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://mobile.i7colors.com/groupBuyMobile/openApp/friendCircle.html?id=" + id);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl("http://sharesdk.cn");

//      启动分享GUI
        oks.show(context);
    }


    //分享店铺
    public static void shareShop(Context context) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("我发现了一个很优惠的团购。赶紧过来看看吧！！");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("真的很优惠！！");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        oks.setImageUrl(ServerInfo.IMAGE + "/ad/1538037103910NJAUF1.jpg");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://mobile.i7colors.com/groupBuyMobile/openApp/preferredShop.html?id=2m");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl("http://sharesdk.cn");

//      启动分享GUI
        oks.show(context);
    }


    //分享求购
    public static void shareEnquiry(Context context, String title, String content, Long enquiryId) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(content);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        oks.setImageUrl("http://static.i7colors.com/i7colors_logo.png");

        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://mobile.i7colors.com/groupBuyMobile/openApp/industryChain.html?enquiryId="+enquiryId);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl("http://sharesdk.cn");
//      启动分享GUI
        oks.show(context);
    }

    //分享资讯
    public static void shareNewsDetail(Context context, String title, String content, String imgUrl, String id) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(content);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        if (StringUtils.isEmpty(imgUrl)) {
            oks.setImageUrl("http://static.i7colors.com/i7colors_logo.png");
        } else {
            oks.setImageUrl(ServerInfo.IMAGE + imgUrl);
        }

        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://mobile.i7colors.com/groupBuyMobile/openApp/voteList.html?id=" + id);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl("http://sharesdk.cn");


        Bitmap enableLogo = BitmapFactory.decodeResource(context.getResources(), R.mipmap.zhanneifenxiang);
        String label = "站内分享";
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                ToastUtils.showShort("zhanneifenxiang ");
            }
        };

        oks.setCustomerLogo(enableLogo, label, listener);

//      启动分享GUI
        oks.show(context);
    }

}
