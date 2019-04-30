package com.company.qcy.bean.pengyouquan;

public class WodehaoyoufensiMessageBean {


    /**
     * id : 7
     * loginUserId : null
     * loginCompanyId : null
     * postUserId : 114810
     * postUserName : lun
     * postUserPhoto : /dye_community/155539731408362QCWU.jpg
     * postUserIsDyeV : 0
     * postUserDyeVName : null
     * receiveUserId : null
     * type : friend
     * isRead : 0
     * isFollow : 1
     * isValid : null
     * createdAt : null
     * createdAtStamp : 1555475010000
     * updatedAt : null
     */

    private Long id;
    private Long postUserId;
    private String postUserName;
    private String postUserPhoto;
    private String postUserIsDyeV;
    private String postUserDyeVName;
    private String type;
    private String isRead;
    private String isFollow;
    private String createdAtStamp;
    private String postUserCompanyName;
    private String postUserIsCompany;
    private String bossLevel;

    public String getBossLevel() {
        return bossLevel;
    }

    public void setBossLevel(String bossLevel) {
        this.bossLevel = bossLevel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostUserId() {
        return postUserId;
    }

    public void setPostUserId(Long postUserId) {
        this.postUserId = postUserId;
    }

    public String getPostUserName() {
        return postUserName;
    }

    public void setPostUserName(String postUserName) {
        this.postUserName = postUserName;
    }

    public String getPostUserPhoto() {
        return postUserPhoto;
    }

    public void setPostUserPhoto(String postUserPhoto) {
        this.postUserPhoto = postUserPhoto;
    }

    public String getPostUserIsDyeV() {
        return postUserIsDyeV;
    }

    public void setPostUserIsDyeV(String postUserIsDyeV) {
        this.postUserIsDyeV = postUserIsDyeV;
    }

    public String getPostUserDyeVName() {
        return postUserDyeVName;
    }

    public void setPostUserDyeVName(String postUserDyeVName) {
        this.postUserDyeVName = postUserDyeVName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(String isFollow) {
        this.isFollow = isFollow;
    }

    public String getCreatedAtStamp() {
        return createdAtStamp;
    }

    public void setCreatedAtStamp(String createdAtStamp) {
        this.createdAtStamp = createdAtStamp;
    }

    public String getPostUserCompanyName() {
        return postUserCompanyName;
    }

    public void setPostUserCompanyName(String postUserCompanyName) {
        this.postUserCompanyName = postUserCompanyName;
    }

    public String getPostUserIsCompany() {
        return postUserIsCompany;
    }

    public void setPostUserIsCompany(String postUserIsCompany) {
        this.postUserIsCompany = postUserIsCompany;
    }
}
