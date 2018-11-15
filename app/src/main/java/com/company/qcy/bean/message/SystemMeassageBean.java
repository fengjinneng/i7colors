package com.company.qcy.bean.message;

public class SystemMeassageBean {


    /**
     * id : 2
     * type : html
     * content : 团购活性黑
     * pic : /market/productImg/1522376281592L3VTA1.jpg
     * url : http://manage.i7colors.com/groupBuyMobile/index.html?id=10
     * directType : null
     * directTypeId : null
     * isValid : null
     * createdAt : 2018-11-06 10:40:01
     * updatedAt : null
     */

    private Long id;
    private String type;
    private String content;
    private String pic;
    private String url;
    private String directType;
    private Long directTypeId;
    private Object isValid;
    private String createdAt;
    private Object updatedAt;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDirectType() {
        return directType;
    }

    public void setDirectType(String directType) {
        this.directType = directType;
    }


    public Long getDirectTypeId() {
        return directTypeId;
    }

    public void setDirectTypeId(Long directTypeId) {
        this.directTypeId = directTypeId;
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
}
