package com.company.qcy.huodong.daixiao.bean;

public class UpLoadSuoyangBean {


    private String sign;

    private String token;

    private Long proxyMarketId;

    private Long proxyMarketUpdateId;

    private String phone;

    private String contact;

    private String province;

    private String city;

    private String companyName;

    private String numUnit;

    private String from;

    private String price;

    private String priceUnit;

    private String buyType;

    private String address;
    private String num;


    public void setSuoyang(String sign, String token, Long proxyMarketId, Long proxyMarketUpdateId, String phone,
                           String contact, String province, String city, String companyName, String numUnit,
                           String from, String price, String priceUnit, String buyType) {

        this.sign = sign;
        this.token = token;
        this.proxyMarketId = proxyMarketId;
        this.proxyMarketUpdateId = proxyMarketUpdateId;
        this.phone = phone;
        this.contact = contact;
        this.province = province;
        this.city = city;
        this.companyName = companyName;
        this.numUnit = numUnit;
        this.from = from;
        this.price = price;
        this.priceUnit = priceUnit;
        this.buyType = buyType;

    }

    private void setBuy(String sign, String token, Long proxyMarketId, Long proxyMarketUpdateId, String phone,
                        String contact, String province, String city, String companyName, String numUnit,
                        String from, String price, String priceUnit, String buyType,String num){
        this.sign = sign;
        this.token = token;
        this.proxyMarketId = proxyMarketId;
        this.proxyMarketUpdateId = proxyMarketUpdateId;
        this.phone = phone;
        this.contact = contact;
        this.province = province;
        this.city = city;
        this.companyName = companyName;
        this.numUnit = numUnit;
        this.from = from;
        this.price = price;
        this.priceUnit = priceUnit;
        this.buyType = buyType;
        this.num = num;

    }



}
