package com.company.qcy.huodong.caigoulianmeng2.bean;

import java.util.List;

public class ProductBean {


    /**
     * id : 45
     * sortNum : null
     * shopName : rewtg
     * diyShop : 0
     * productPic : null
     * status : 1
     * packing : 25KG/袋
     * meetingTypeList : [{"id":204,"orderId":45,"infoId":null,"referenceType":"参考A"},{"id":205,"orderId":45,"infoId":null,"referenceType":"参考B"},{"id":206,"orderId":45,"infoId":null,"referenceType":"参考C"}]
     * meetingInfoList : null
     * allNum : null
     * allOutputNum : null
     * companyName : null
     * len : null
     * price : null
     * priceUnit : null
     * numUnit : null
     * reservationNum : null
     * outputNum : null
     * effectiveTime : null
     * orderStatus : 0
     * referenceType : null
     * phone : null
     * meetingUser : null
     * name : null
     * accountPeriod : null
     * payType : null
     * provinceName : null
     * cityName : null
     * createdAt : 2018-11-29 01:30:10
     * meetingId : 49
     * banner : null
     * meetingName : null
     * positioner : null
     */

    private Long id;
    private String shopName;
    private String diyShop;
    private String status;
    private String packing;
    private String orderStatus;
    private String createdAt;
    private Long meetingId;
    private List<MeetingTypeListBean> meetingTypeList;
    private boolean checked;
    private String reservationNum;
    private String referenceType;
    private String price;
    private String date;
    private String priceUnit;

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public String getReservationNum() {
        return reservationNum;
    }

    public void setReservationNum(String reservationNum) {
        this.reservationNum = reservationNum;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getDiyShop() {
        return diyShop;
    }

    public void setDiyShop(String diyShop) {
        this.diyShop = diyShop;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPacking() {
        return packing;
    }

    public void setPacking(String packing) {
        this.packing = packing;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Long getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Long meetingId) {
        this.meetingId = meetingId;
    }

    public List<MeetingTypeListBean> getMeetingTypeList() {
        return meetingTypeList;
    }

    public void setMeetingTypeList(List<MeetingTypeListBean> meetingTypeList) {
        this.meetingTypeList = meetingTypeList;
    }


    public static class MeetingTypeListBean {
        /**
         * id : 204
         * orderId : 45
         * infoId : null
         * referenceType : 参考A
         */

        private Long id;
        private Long orderId;
        private String referenceType;
        private boolean checked;

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getOrderId() {
            return orderId;
        }

        public void setOrderId(Long orderId) {
            this.orderId = orderId;
        }

        public String getReferenceType() {
            return referenceType;
        }

        public void setReferenceType(String referenceType) {
            this.referenceType = referenceType;
        }

    }
}
