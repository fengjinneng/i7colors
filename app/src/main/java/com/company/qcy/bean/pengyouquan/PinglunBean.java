package com.company.qcy.bean.pengyouquan;

public class PinglunBean {


    /**
     * id : 4
     * dyeId : null
     * userId : null
     * commentUser : 封金能
     * byCommentUser : null
     * parentId : null
     * content : 阿伦子公司来评论
     * isRead : null
     * isBan : null
     * isValid : null
     * createdAt : null
     * createdAtStamp : null
     * updatedAt : null
     */

    private Long id;
    private Long dyeId;
    private Long userId;
    private String commentUser;
    private Object byCommentUser;
    private Long parentId;
    private String content;
    private Object isRead;
    private Object isBan;
    private Object isValid;
    private Object createdAt;
    private Object createdAtStamp;
    private Object updatedAt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(String commentUser) {
        this.commentUser = commentUser;
    }

    public Object getByCommentUser() {
        return byCommentUser;
    }

    public void setByCommentUser(Object byCommentUser) {
        this.byCommentUser = byCommentUser;
    }

    public Long getDyeId() {
        return dyeId;
    }

    public void setDyeId(Long dyeId) {
        this.dyeId = dyeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Object getIsRead() {
        return isRead;
    }

    public void setIsRead(Object isRead) {
        this.isRead = isRead;
    }

    public Object getIsBan() {
        return isBan;
    }

    public void setIsBan(Object isBan) {
        this.isBan = isBan;
    }

    public Object getIsValid() {
        return isValid;
    }

    public void setIsValid(Object isValid) {
        this.isValid = isValid;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public Object getCreatedAtStamp() {
        return createdAtStamp;
    }

    public void setCreatedAtStamp(Object createdAtStamp) {
        this.createdAtStamp = createdAtStamp;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }
}
