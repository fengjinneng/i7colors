package com.company.qcy.Utils.share;
import android.content.Context;
import com.blankj.utilcode.util.StringUtils;
import com.company.qcy.Utils.ServerInfo;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class ShareUtil {


    /**
     *     求购分享  /enquiry/enquiryDetail
     *     产品分享   /product/productDetail
     *     朋友群分享  /friendcircle/friendcircleDetail
     *     投票详情   @Route(path = "/vote/detail")
     *     投票选手   @Route(path = "/vote/player/detail")
     */

    //生產
    public static String shareUrl ="http://mobile.i7colors.com/groupBuyMobile/openApp/" ;

    //測試
//        public static String shareUrl ="http://manage.i7colors.com/groupBuyMobile/openApp/" ;

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
        oks.setUrl(shareUrl+"voteList.html?id=" + id);
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
        oks.setUrl(shareUrl+"voteMessage.html?id=" + id+"&mainId="+mainId);
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
        oks.setUrl(shareUrl+"shopDetails.html?id=" + id);
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
        oks.setUrl(shareUrl+"friendCircle.html?id=" + id);
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
        oks.setUrl(shareUrl+"preferredShop.html?id=2m");
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
        oks.setUrl(shareUrl+"industryChain.html?enquiryId="+enquiryId);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl("http://sharesdk.cn");
//      启动分享GUI
        oks.show(context);
    }

    //分享砍价
    public static void shareKanjia(Context context,String content,String imgUrl, Long mainId,Long buyerId) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("【团购砍价】");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("亲们帮帮忙,我正在参与七彩云电商 \""+content+"\" 砍价活动");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片

        if (StringUtils.isEmpty(imgUrl)) {
            oks.setImageUrl("http://static.i7colors.com/i7colors_logo.png");

        } else {
            oks.setImageUrl(ServerInfo.IMAGE + imgUrl);
        }

        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(shareUrl+"cut.html?mainId="+mainId+"&buyerId="+buyerId);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl("http://sharesdk.cn");
//      启动分享GUI
        oks.show(context);
    }

    //分享代销
    public static void shareProxy(Context context,String title,String imgUrl, Long id) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("【产品】 "+title);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("代销市场,一大波便宜又丰富的产品正等你拿！！！");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片

        if (StringUtils.isEmpty(imgUrl)) {
            oks.setImageUrl("http://static.i7colors.com/i7colors_logo.png");

        } else {
            oks.setImageUrl(ServerInfo.IMAGE + imgUrl);
        }

        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(shareUrl+"consignment.html?id="+id);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl("http://sharesdk.cn");
//      启动分享GUI
        oks.show(context);
    }

}
