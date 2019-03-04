package com.company.qcy.huodong.caigoulianmeng2.bean;

public class UploadProductBean {


    /**
     * shopName : 分散黑
     * packing : 25KG/箱
     * diyShop  : 0
     * numUnit : KG
     * reservationNum : 10000
     * orderStatus : 0
     * referenceType : 龙盛,闰土
     * shopId : 1
     */

    private String shopName;
    private String packing;
    private String diyShop;
    private String numUnit;
    private String reservationNum;
    private String orderStatus;
    private String referenceType;
    private Long id;

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

    public String getNumUnit() {
        return numUnit;
    }

    public void setNumUnit(String numUnit) {
        this.numUnit = numUnit;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{" +
                "shopName='" + shopName + '\'' +
                ", packing='" + packing + '\'' +
                ", diyShop='" + diyShop + '\'' +
                ", numUnit='" + numUnit + '\'' +
                ", reservationNum='" + reservationNum + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", referenceType='" + referenceType + '\'' +
                ", id=" + id +
                '}';
    }
}
