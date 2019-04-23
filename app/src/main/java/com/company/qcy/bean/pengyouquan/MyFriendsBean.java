package com.company.qcy.bean.pengyouquan;


import android.os.Parcel;
import android.os.Parcelable;

public class MyFriendsBean implements Parcelable {

    @Override
    public String toString() {
        return "MyFriendsBean{" +
                "id=" + id +
                ", userId=" + userId +
                ", loginName='" + loginName + '\'' +
                ", userNickName='" + userNickName + '\'' +
                ", userCommunityPhoto='" + userCommunityPhoto + '\'' +
                ", isDyeV='" + isDyeV + '\'' +
                ", dyeVName='" + dyeVName + '\'' +
                ", bossLevel='" + bossLevel + '\'' +
                ", isCompany='" + isCompany + '\'' +
                ", isFollow='" + isFollow + '\'' +
                ", createdAtStamp='" + createdAtStamp + '\'' +
                ", firstSpellStr='" + firstSpellStr + '\'' +
                ", index='" + index + '\'' +
                ", checked=" + checked +
                '}';
    }

    private Long id;
    private Long userId;
    private String loginName;

    private String userNickName;

    private String userCommunityPhoto;
    private String isDyeV;

    private String dyeVName;

    private String bossLevel;

    private String isCompany;

    private String isFollow;

    private String createdAtStamp;

    private String firstSpellStr;

    private String companyName;

    private String index;

    private boolean checked;//判斷是否選中

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserCommunityPhoto() {
        return userCommunityPhoto;
    }

    public void setUserCommunityPhoto(String userCommunityPhoto) {
        this.userCommunityPhoto = userCommunityPhoto;
    }

    public String getIsDyeV() {
        return isDyeV;
    }

    public void setIsDyeV(String isDyeV) {
        this.isDyeV = isDyeV;
    }

    public String getDyeVName() {
        return dyeVName;
    }

    public void setDyeVName(String dyeVName) {
        this.dyeVName = dyeVName;
    }

    public String getBossLevel() {
        return bossLevel;
    }

    public void setBossLevel(String bossLevel) {
        this.bossLevel = bossLevel;
    }

    public String getIsCompany() {
        return isCompany;
    }

    public void setIsCompany(String isCompany) {
        this.isCompany = isCompany;
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

    public String getFirstSpellStr() {
        return firstSpellStr;
    }

    public void setFirstSpellStr(String firstSpellStr) {
        this.firstSpellStr = firstSpellStr;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public MyFriendsBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.userId);
        dest.writeString(this.loginName);
        dest.writeString(this.userNickName);
        dest.writeString(this.userCommunityPhoto);
        dest.writeString(this.isDyeV);
        dest.writeString(this.dyeVName);
        dest.writeString(this.bossLevel);
        dest.writeString(this.isCompany);
        dest.writeString(this.isFollow);
        dest.writeString(this.createdAtStamp);
        dest.writeString(this.firstSpellStr);
        dest.writeString(this.companyName);
        dest.writeString(this.index);
        dest.writeByte(this.checked ? (byte) 1 : (byte) 0);
    }

    protected MyFriendsBean(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.userId = (Long) in.readValue(Long.class.getClassLoader());
        this.loginName = in.readString();
        this.userNickName = in.readString();
        this.userCommunityPhoto = in.readString();
        this.isDyeV = in.readString();
        this.dyeVName = in.readString();
        this.bossLevel = in.readString();
        this.isCompany = in.readString();
        this.isFollow = in.readString();
        this.createdAtStamp = in.readString();
        this.firstSpellStr = in.readString();
        this.companyName = in.readString();
        this.index = in.readString();
        this.checked = in.readByte() != 0;
    }

    public static final Creator<MyFriendsBean> CREATOR = new Creator<MyFriendsBean>() {
        @Override
        public MyFriendsBean createFromParcel(Parcel source) {
            return new MyFriendsBean(source);
        }

        @Override
        public MyFriendsBean[] newArray(int size) {
            return new MyFriendsBean[size];
        }
    };
}
