package com.company.qcy.bean.pengyouquan;

public class WodeaiteMessageBean {


    /**
     * id : 1
     * loginUserId : null
     * loginCompanyId : null
     * postUserId : 114782
     * postUserName : 朱方庆
     * postUserPhoto : /bbs/userHead/1542005390732H3QTNY.jpg
     * postUserIsDyeV : 0
     * postUserDyeVName : null
     * receiveUserId : null
     * communityId : 117
     * isRead : 0
     * isValid : null
     * createdAt : null
     * createdAtStamp : 1552619818000
     * updatedAt : null
     */

    private Long id;
    private Long postUserId;
    private String postUserName;
    private String postUserPhoto;
    private String postUserIsDyeV;
    private String postUserDyeVName;
    private String isRead;
    private String createdAtStamp;

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
