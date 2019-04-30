package com.company.qcy.bean.pengyouquan;

public class WodedianzanMessageBean {


    /**
     * id : 1
     * loginUserId : null
     * loginCompanyId : null
     * postUserId : 114784
     * postUserName : why公司
     * postUserPhoto : null
     * postUserIsDyeV : 0
     * postUserDyeVName : null
     * receiveUserId : null
     * communityId : 134
     * isRead : 0
     * isValid : null
     * createdAt : null
     * createdAtStamp : 1552889052000
     * updatedAt : null
     */

    private Long id;
    private Long postUserId;
    private String postUserName;
    private String postUserIsDyeV;
    private String isRead;
    private String createdAtStamp;
    private String postUserPhoto;
    private Long communityId;
    private Long commentId;
    private String postUserDyeVName;
    private String bossLevel;

    private String postUserCompanyName;
    private String postUserIsCompany;

    public String getBossLevel() {
        return bossLevel;
    }

    public void setBossLevel(String bossLevel) {
        this.bossLevel = bossLevel;
    }

    public String getPostUserDyeVName() {
        return postUserDyeVName;
    }

    public void setPostUserDyeVName(String postUserDyeVName) {
        this.postUserDyeVName = postUserDyeVName;
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

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getPostUserPhoto() {
        return postUserPhoto;
    }

    public void setPostUserPhoto(String postUserPhoto) {
        this.postUserPhoto = postUserPhoto;
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

    public String getPostUserIsDyeV() {
        return postUserIsDyeV;
    }

    public void setPostUserIsDyeV(String postUserIsDyeV) {
        this.postUserIsDyeV = postUserIsDyeV;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getCreatedAtStamp() {
        return createdAtStamp;
    }

    public void setCreatedAtStamp(String createdAtStamp) {
        this.createdAtStamp = createdAtStamp;
    }
}
