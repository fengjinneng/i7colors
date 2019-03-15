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

    }

}

