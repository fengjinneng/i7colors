package com.company.qcy.bean.user;

import android.os.Parcel;
import android.os.Parcelable;

public class UserBean implements Parcelable {


    /**
     * token : TIL0KapOGdRMgvQpqk4ZctLhpzK07oRy0uGnMrTu
     * userId : 114779
     * companyName : why公司
     * isCompany : true
     * photo : https://wx.qlogo.cn/mmopen/vi_32/ia4U4QF3jQnnILOW2jDb0xeLYkjUQh16Og42P6PK1xukXmdBI0icE2NOKemI36ejGia1z0mVhdA8ic82uyibu20p7hw/0
     * loginName : why
     */

    private String token;
    private Long userId;
    private Long companyId;
    private String companyName;
    private Boolean isCompany;
    private String photo;
    private String loginName;
    private String communityPhoto;
    private Boolean needPhone;

    public Boolean getCompany() {
        return isCompany;
    }

    public void setCompany(Boolean company) {
        isCompany = company;
    }

    public Boolean getNeedPhone() {
        return needPhone;
    }

    public void setNeedPhone(Boolean needPhone) {
        this.needPhone = needPhone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Boolean getIsCompany() {
        return isCompany;
    }

    public void setIsCompany(Boolean isCompany) {
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

    public String getCommunityPhoto() {
        return communityPhoto;
    }

    public void setCommunityPhoto(String communityPhoto) {
        this.communityPhoto = communityPhoto;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.token);
        dest.writeValue(this.userId);
        dest.writeValue(this.companyId);
        dest.writeString(this.companyName);
        dest.writeValue(this.isCompany);
        dest.writeString(this.photo);
        dest.writeString(this.loginName);
        dest.writeString(this.communityPhoto);
        dest.writeValue(this.needPhone);
    }

    public UserBean() {
    }

    protected UserBean(Parcel in) {
        this.token = in.readString();
        this.userId = (Long) in.readValue(Long.class.getClassLoader());
        this.companyId = (Long) in.readValue(Long.class.getClassLoader());
        this.companyName = in.readString();
        this.isCompany = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.photo = in.readString();
        this.loginName = in.readString();
        this.communityPhoto = in.readString();
        this.needPhone = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Parcelable.Creator<UserBean> CREATOR = new Parcelable.Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel source) {
            return new UserBean(source);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };
}
