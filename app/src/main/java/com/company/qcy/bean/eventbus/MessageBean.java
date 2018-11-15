package com.company.qcy.bean.eventbus;

public class MessageBean {


    private int code;
    private String meaasge;

    public MessageBean() {
    }


    public MessageBean(int code) {
        this.code = code;
    }

    public MessageBean(int code, String meaasge) {
        this.code = code;
        this.meaasge = meaasge;
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


    }


}

