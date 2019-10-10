package com.company.qcy.bean.message;

public class MessageBean {


    /**
     * id : 2
     * userId : null
     * enquiryId : 43
     * type : null
     * productName : 活性黄
     * content : 您好，有印染企业发布了新的“活性黄”求购信息，您的店铺刚好提供此产品分类的售卖，如您店铺有售卖，请及时给与报价
     * isRead : null
     * status : null
     * isValid : null
     * createdAt : null
     * updatedAt : null
     */

    private Long id;
    private Object userId;
    private Long enquiryId;
    private String type;
    private String workType;
    private String zhujiName;
    private String productName;
    private String content;
    private String isRead;
    private Object status;
    private Object isValid;
    private String createdAt;
    private Object updatedAt;
    private String title;
    private Long zhujiDiyId;
    private Long zhujiDiySolutionId;
    private String directType;

    public String getDirectType() {
        return directType;
    }

    public void setDirectType(String directType) {
        this.directType = directType;
    }

    public Long getZhujiDiyId() {
        return zhujiDiyId;
    }

    public void setZhujiDiyId(Long zhujiDiyId) {
        this.zhujiDiyId = zhujiDiyId;
    }

    public Long getZhujiDiySolutionId() {
        return zhujiDiySolutionId;
    }

    public void setZhujiDiySolutionId(Long zhujiDiySolutionId) {
        this.zhujiDiySolutionId = zhujiDiySolutionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public Long getEnquiryId() {
        return enquiryId;
    }

    public void setEnquiryId(Long enquiryId) {
        this.enquiryId = enquiryId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getZhujiName() {
        return zhujiName;
    }

    public void setZhujiName(String zhujiName) {
        this.zhujiName = zhujiName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Object getIsValid() {
        return isValid;
    }

    public void setIsValid(Object isValid) {
        this.isValid = isValid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
