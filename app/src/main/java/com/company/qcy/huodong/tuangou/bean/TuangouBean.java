package com.company.qcy.huodong.tuangou.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class TuangouBean implements Parcelable {


    /**
     * id : 1
     * name : WNN黑
     * banner : /groupBuy/banner01_640X240.jpg
     * startTime : 2018-08-10 16:00:00
     * startTimeStamp : 1533888000
     * endTime : 2018-08-15 14:00:00
     * endTimeStamp : 1534312800
     * status : 1
     * endCode : 20
     * productName : WNN黑 300%
     * productPic : /groupBuy/1534315286312S8IQS5.png
     * detailPcPic : /groupBuy/detail_pc_pic.png
     * detailMobilePic : /groupBuy/detail_mobile_pic.png
     * notePcPic : /groupBuy/note_pc_pic.png
     * noteMobilePic : /groupBuy/note_mobile_pic.png
     * oldPrice : 45
     * newPrice : 37.5
     * priceUnit : KG
     * totalNum : 20
     * minNum : 1
     * maxNum : 15.5
     * numUnit : 吨
     * isValid : 1
     * createdAt : 2018-08-09 14:41:18
     * createdBy : null
     * updatedAt : 2018-08-09 14:41:18
     * updatedBy : null
     * subscribedNum : 2.32
     * remainNum : 17.68
     * numPercent : 12%
     * description : 活性黑WNN团购，上七彩云平台只要31元！
     * sortNum : 1
     * isConsiderStock : 1
     */

    private Long id;
    private String name;
    private String banner;
    private String startTime;
    private String startTimeStamp;
    private String endTime;
    private String endTimeStamp;
    private String status;
    private String endCode;
    private String productName;
    private String productPic;
    private String detailPcPic;
    private String detailMobilePic;
    private String notePcPic;
    private String noteMobilePic;
    private String oldPrice;
    private String newPrice;
    private String priceUnit;
    private String totalNum;
    private String minNum;
    private String maxNum;
    private String numUnit;
    private String isValid;
    private String createdAt;
    private String updatedAt;
    private String subscribedNum;
    private String remainNum;
    private String numPercent;
    private String description;
    private String sortNum;
    private String isConsiderStock;
    private String num;
    private String isCutPrice;//当前团购是否可以砍价（是否参与砍价），1可以砍价；0不可砍价
    private String hasCutPrice;//当前认购已砍价格
    private String remainCutPrice;//单前认购还可砍的价格
    private Long buyerId;//认购id
    private String loginUserHasBuy;  //1,当前登陆用户已认购。0当前用户没有认购过，或者没有登录



    public String getLoginUserHasBuy() {
        return loginUserHasBuy;
    }

    public void setLoginUserHasBuy(String loginUserHasBuy) {
        this.loginUserHasBuy = loginUserHasBuy;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getIsCutPrice() {
        return isCutPrice;
    }

    public void setIsCutPrice(String isCutPrice) {
        this.isCutPrice = isCutPrice;
    }

    public String getHasCutPrice() {
        return hasCutPrice;
    }

    public void setHasCutPrice(String hasCutPrice) {
        this.hasCutPrice = hasCutPrice;
    }

    public String getRemainCutPrice() {
        return remainCutPrice;
    }

    public void setRemainCutPrice(String remainCutPrice) {
        this.remainCutPrice = remainCutPrice;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(String startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndTimeStamp() {
        return endTimeStamp;
    }

    public void setEndTimeStamp(String endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEndCode() {
        return endCode;
    }

    public void setEndCode(String endCode) {
        this.endCode = endCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    public String getDetailPcPic() {
        return detailPcPic;
    }

    public void setDetailPcPic(String detailPcPic) {
        this.detailPcPic = detailPcPic;
    }

    public String getDetailMobilePic() {
        return detailMobilePic;
    }

    public void setDetailMobilePic(String detailMobilePic) {
        this.detailMobilePic = detailMobilePic;
    }

    public String getNotePcPic() {
        return notePcPic;
    }

    public void setNotePcPic(String notePcPic) {
        this.notePcPic = notePcPic;
    }

    public String getNoteMobilePic() {
        return noteMobilePic;
    }

    public void setNoteMobilePic(String noteMobilePic) {
        this.noteMobilePic = noteMobilePic;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(String newPrice) {
        this.newPrice = newPrice;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public String getMinNum() {
        return minNum;
    }

    public void setMinNum(String minNum) {
        this.minNum = minNum;
    }

    public String getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(String maxNum) {
        this.maxNum = maxNum;
    }

    public String getNumUnit() {
        return numUnit;
    }

    public void setNumUnit(String numUnit) {
        this.numUnit = numUnit;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getSubscribedNum() {
        return subscribedNum;
    }

    public void setSubscribedNum(String subscribedNum) {
        this.subscribedNum = subscribedNum;
    }

    public String getRemainNum() {
        return remainNum;
    }

    public void setRemainNum(String remainNum) {
        this.remainNum = remainNum;
    }

    public String getNumPercent() {
        return numPercent;
    }

    public void setNumPercent(String numPercent) {
        this.numPercent = numPercent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSortNum() {
        return sortNum;
    }

    public void setSortNum(String sortNum) {
        this.sortNum = sortNum;
    }

    public String getIsConsiderStock() {
        return isConsiderStock;
    }

    public void setIsConsiderStock(String isConsiderStock) {
        this.isConsiderStock = isConsiderStock;
    }

    public TuangouBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.banner);
        dest.writeString(this.startTime);
        dest.writeString(this.startTimeStamp);
        dest.writeString(this.endTime);
        dest.writeString(this.endTimeStamp);
        dest.writeString(this.status);
        dest.writeString(this.endCode);
        dest.writeString(this.productName);
        dest.writeString(this.productPic);
        dest.writeString(this.detailPcPic);
        dest.writeString(this.detailMobilePic);
        dest.writeString(this.notePcPic);
        dest.writeString(this.noteMobilePic);
        dest.writeString(this.oldPrice);
        dest.writeString(this.newPrice);
        dest.writeString(this.priceUnit);
        dest.writeString(this.totalNum);
        dest.writeString(this.minNum);
        dest.writeString(this.maxNum);
        dest.writeString(this.numUnit);
        dest.writeString(this.isValid);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeString(this.subscribedNum);
        dest.writeString(this.remainNum);
        dest.writeString(this.numPercent);
        dest.writeString(this.description);
        dest.writeString(this.sortNum);
        dest.writeString(this.isConsiderStock);
        dest.writeString(this.num);
        dest.writeString(this.isCutPrice);
        dest.writeString(this.hasCutPrice);
        dest.writeString(this.remainCutPrice);
        dest.writeValue(this.buyerId);
        dest.writeString(this.loginUserHasBuy);
    }

    protected TuangouBean(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.banner = in.readString();
        this.startTime = in.readString();
        this.startTimeStamp = in.readString();
        this.endTime = in.readString();
        this.endTimeStamp = in.readString();
        this.status = in.readString();
        this.endCode = in.readString();
        this.productName = in.readString();
        this.productPic = in.readString();
        this.detailPcPic = in.readString();
        this.detailMobilePic = in.readString();
        this.notePcPic = in.readString();
        this.noteMobilePic = in.readString();
        this.oldPrice = in.readString();
        this.newPrice = in.readString();
        this.priceUnit = in.readString();
        this.totalNum = in.readString();
        this.minNum = in.readString();
        this.maxNum = in.readString();
        this.numUnit = in.readString();
        this.isValid = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.subscribedNum = in.readString();
        this.remainNum = in.readString();
        this.numPercent = in.readString();
        this.description = in.readString();
        this.sortNum = in.readString();
        this.isConsiderStock = in.readString();
        this.num = in.readString();
        this.isCutPrice = in.readString();
        this.hasCutPrice = in.readString();
        this.remainCutPrice = in.readString();
        this.buyerId = (Long) in.readValue(Long.class.getClassLoader());
        this.loginUserHasBuy = in.readString();
    }

    public static final Creator<TuangouBean> CREATOR = new Creator<TuangouBean>() {
        @Override
        public TuangouBean createFromParcel(Parcel source) {
            return new TuangouBean(source);
        }

        @Override
        public TuangouBean[] newArray(int size) {
            return new TuangouBean[size];
        }
    };
}
