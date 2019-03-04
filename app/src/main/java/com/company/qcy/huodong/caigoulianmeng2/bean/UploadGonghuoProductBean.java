package com.company.qcy.huodong.caigoulianmeng2.bean;

public class UploadGonghuoProductBean {


    /**
     * shopName : 供货非自定义产品
     * packing : 25KG/箱
     * diyShop  : 0
     * price : 11
     * priceUnit : 元/KG
     * numUnit : KG
     * outputNum : 1000
     * orderStatus : 1
     * referenceType : 龙盛,闰土
     * id : 1
     * effectiveTime : 2019-01-22
     */

    private String shopName;
    private String packing;
    private String diyShop;
    private String price;
    private String priceUnit;
    private String numUnit;
    private String outputNum;
    private String orderStatus;
    private String referenceType;
    private Long id;
    private String effectiveTime;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getPacking() {
        return packing;
    }

    public void setPacking(String packing) {
        this.packing = packing;
    }

    public String getDiyShop() {
        return diyShop;
    }

    public void setDiyShop(String diyShop) {
        this.diyShop = diyShop;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public String getNumUnit() {
        return numUnit;
    }

    public void setNumUnit(String numUnit) {
        this.numUnit = numUnit;
    }

    public String getOutputNum() {
        return outputNum;
    }

    public void setOutputNum(String outputNum) {
        this.outputNum = outputNum;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(String effectiveTime) {
        this.effectiveTime = effectiveTime;
    }


    @Override
    public String toString() {
        return "{" +
                "shopName='" + shopName + '\'' +
                ", packing='" + packing + '\'' +
                ", diyShop='" + diyShop + '\'' +
                ", price='" + price + '\'' +
                ", priceUnit='" + priceUnit + '\'' +
                ", numUnit='" + numUnit + '\'' +
                ", outputNum='" + outputNum + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", referenceType='" + referenceType + '\'' +
                ", id=" + id +
                ", effectiveTime='" + effectiveTime + '\'' +
                '}';
    }
}
