package com.company.qcy.huodong.caigoulianmeng.bean;

public class DinghuoBean {


    private String shopName;
    private String packing;
    private String diyShop ;
    private String reservationNum;
    private String orderStatus;
    private String referenceType;
    private String shopId;


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

    public String getReservationNum() {
        return reservationNum;
    }

    public void setReservationNum(String reservationNum) {
        this.reservationNum = reservationNum;
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

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    @Override
    public String toString() {
        return "DinghuoBean{" +
                "shopName='" + shopName + '\'' +
                ", packing='" + packing + '\'' +
                ", diyShop='" + diyShop + '\'' +
                ", reservationNum='" + reservationNum + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", referenceType='" + referenceType + '\'' +
                ", shopId='" + shopId + '\'' +
                '}';
    }
}
