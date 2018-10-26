package com.company.qcy.bean.user;

public class UserBean {


    /**
     * token : TIL0KapOGdRMgvQpqk4ZctLhpzK07oRy0uGnMrTu
     * userId : 114779
     * companyName : why公司
     * isCompany : true
     * photo : https://wx.qlogo.cn/mmopen/vi_32/ia4U4QF3jQnnILOW2jDb0xeLYkjUQh16Og42P6PK1xukXmdBI0icE2NOKemI36ejGia1z0mVhdA8ic82uyibu20p7hw/0
     * loginName : why
     */

    private String token;
    private Integer userId;
    private String companyName;
    private Boolean isCompany;
    private String photo;
    private String loginName;

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getCompany() {
        return isCompany;
    }

    public void setCompany(Boolean company) {
        isCompany = company;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public boolean isIsCompany() {
        return isCompany;
    }

    public void setIsCompany(boolean isCompany) {
        this.isCompany = isCompany;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

}
