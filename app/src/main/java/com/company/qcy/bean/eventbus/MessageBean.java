package com.company.qcy.bean.eventbus;

public class MessageBean {


    private int code;
    private String meaasge;
    private int param;
    private Object obj;

    public MessageBean() {
    }

    public MessageBean(int code) {
        this.code = code;
    }

    public MessageBean(int code, String meaasge) {
        this.code = code;
        this.meaasge = meaasge;
    }

    public MessageBean(int code, Object obj) {
        this.code = code;
        this.obj = obj;
    }

    public MessageBean(int code, String meaasge, int param) {
        this.code = code;
        this.meaasge = meaasge;
        this.param = param;
    }

    public MessageBean(int code, int param, Object obj) {
        this.code = code;
        this.param = param;
        this.obj = obj;
    }

    public MessageBean(int code, String meaasge, int param, Object obj) {
        this.code = code;
        this.meaasge = meaasge;
        this.param = param;
        this.obj = obj;
    }

    public MessageBean(int code, String meaasge, Object obj) {
        this.code = code;
        this.meaasge = meaasge;
        this.obj = obj;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMeaasge() {
        return meaasge;
    }

    public void setMeaasge(String meaasge) {
        this.meaasge = meaasge;
    }

    public int getParam() {
        return param;
    }

    public void setParam(int param) {
        this.param = param;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }




    public static class Zhuji{
        //发布助剂定制成功
        public static final int FABUZHUJIDINGZHICHENGGONG=6001;

        //关闭助剂定制成功
        public static final int CLOSEZHUJIDIY=6002;

        //采纳方案成功
        public static final int CAINAFANGAN=6003;

    }

    public static class NetWorkState{
        //网络已连接
        public static final int YILIANJIE=5001;


    }


    public static class JPush{
        //發送的系統消息
        public static final int XITONGXIAOXI=4001;

        //清除lunch数字
        public static final int DELETELUNCHNUMBER=4002;


        //收到系统消息的通知了
        public static final int RECIVENOTIFICATION=4003;

        //需要重制首页的消息数量
        public static final int NEEDRESETMESSAGECOUNT=4004;

        //需要刷新买家消息
        public static final int NEEDRESETBUYERMESSAGE=4005;


        //需要刷新卖家消息
        public static final int NEEDRESETSELLERMESSAGE=4006;


        //需要刷新系统消息
        public static final int NEEDRESETSYSTEMMESSAGE=4007;

    }


    public static class RemenCode{
        //删除评论成功
        public static final int DELETEPINGLUNCHENGGONG=3001;
        //发布朋友圈成功
        public static final int FABUPENGYOUQUANCHENGGONG=3002;
        //朋友圈需要刷新
        public static final int PENGYOUQUANNEEDREFLUSH=3003;
        //详情页点赞成功需要刷新
        public static final int XIANGQINGYEDIANZANCHENGGONG=3004;
    }

    public static class GuanzhuCode{
        //删除评论成功
        public static final int DELETEPINGLUNCHENGGONG=2001;
        //发布朋友圈成功
        public static final int FABUPENGYOUQUANCHENGGONG=2002;
        //朋友圈需要刷新
        public static final int PENGYOUQUANNEEDREFLUSH=2003;
        //详情页点赞成功需要刷新
        public static final int XIANGQINGYEDIANZANCHENGGONG=2004;
    }

    public static class Code {

        //登录成功发送code
        public static final int DELU = 1001;
        //报价成功
        public static final int BAOJIACHENGGONG = 1002;
        //关闭求购成功
        public static final int GUANBIQIUGOU = 1003;
        //发布求购成功
        public static final int FABUQIUGOUCHENGGONG = 1004;

        //采纳报价成功
        public static final int CAINABAOJIACHENGGONG = 1005;

        //开放商城 公司简介发送信息
        public static final int KFSCGONGSIJIESHAO = 1006;

        //团购成功
        public static final int TUANGOUCHENGGONG = 1007;

        //求购报价消息已读
        public static final int ENQUIRYMESSAGEREAD = 1008;

        //关闭ImagePagerActivity
        public static final int CLOSEIMAGEPAGERACTIVITY = 1009;

        //修改个人中心头像成功
        public static final int CHANGEPERSONHEADIMG = 1010;

        //修改密码成功
        public static final int CHANGEPASSWORDCHENGGONG = 1011;

        //删除评论成功
        public static final int DELETEPINGLUNCHENGGONG=1013;

        //发布朋友圈成功
        public static final int FABUPENGYOUQUANCHENGGONG=1014;

        //微信登录成功
        public static final int WXLOGIN=1015;


        //需要更新
        public static final int NEEDUPDATEAPP=1018;

        //关注成功
        public static final int GUANZHUCHENGGONG=1019;

        //朋友圈需要刷新
        public static final int PENGYOUQUANNEEDREFLUSH=1020;

        //评论需要刷新
        public static final int PINGLUNNEEDREFLUSH=1021;

        //详情页点赞成功需要刷新
        public static final int XIANGQINGYEDIANZANCHENGGONG=1022;

        //朋友圈头像修改成功
        public static final int PENGYOUQUANHEADIMGCHANGE=1023;
        //朋友nickname修改成功
        public static final int PENGYOUQUANNICKNAMECHANGE=1024;

        //重置密码成功
        public static final int RESETPASSWORD=1025;

        //启动页停止播放gif
        public static final int GUANGGAOTINGZHI=1026;

        //优惠展销购买成功
        public static final int YOUHUIZHANXIAOGOUMAICHENGGONG = 1027;

        //采购联盟 添加自定义商品成功
        public static final int CGLMTIANJIASHANGPIN = 1028;

        //投票 参与投票成功
        public static final int CANYUTOUPIAOCHENGGONG = 1029;

        //投票 投票成功
        public static final int TOUPIAOCHENGGONG = 1030;

        //竞拍 参与竞拍成功
        public static final int TCANYUJINGPAICHENGGONG = 1031;

        //朋友圈 选择了发布的地址
        public static final int PENGYOUQUANCHOICEADDRESS = 1032;

        //朋友圈 不选择地址
        public static final int PENGYOUQUANNOCHOICEADDRESS = 1033;

        //朋友圈 搜索时选择了发布的地址
        public static final int PENGYOUQUANCHOICEADDRESSBYSEARCH = 1034;

        //朋友圈 选择了二级话题
        public static final int CHOICEERJIHUATI = 1035;

        //朋友圈 选择了资讯
        public static final int CHOICEZIXUN = 1036;

        //朋友圈 选择誰可以看
        public static final int CHOICESHUIKEYIKAN = 1037;

        //朋友圈 提醒谁看
        public static final int CHOICETIXINGSHUIKAN = 1038;

        //朋友圈 确定谁可以看
        public static final int QUEDINGSHUIKAN = 1039;

        //朋友圈 有新的消息
        public static final int PENGYOUQUANHAVENEWMESSAGE = 1040;

        //系统消息 有新的消息
        public static final int XITONGHAVENEWMESSAGE = 1041;

        //系统消息 进去了messageActivity
        public static final int DIANJIJINQUMESSAGE = 1042;

        //团购砍价成功
        public static final int TUANGOUKANJIACHENGGONG = 1043;

        //需要刷新买家消息
        public static final int NEEDREFRESHBUYERMESSAGE = 1044;

        //需要刷新买家消息
        public static final int NEEDREFRESHSELLERMESSAGE = 1045;

        //申请企业认证
        public static final int QIYERENZHENG = 1046;

    }

}

