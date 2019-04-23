package com.company.qcy.bean.pengyouquan;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class HuatiBean implements Parcelable {

    private Long id;
    private Long parentId;
    private String level;
    private String title;
    private String description;
    private boolean checked;
    private String communityNum;
    private String banner;


    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getCommunityNum() {
        return communityNum;
    }

    public void setCommunityNum(String communityNum) {
        this.communityNum = communityNum;
    }

    public HuatiBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.parentId);
        dest.writeString(this.level);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeByte(this.checked ? (byte) 1 : (byte) 0);
        dest.writeString(this.communityNum);
        dest.writeString(this.banner);
    }

    protected HuatiBean(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.parentId = (Long) in.readValue(Long.class.getClassLoader());
        this.level = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.checked = in.readByte() != 0;
        this.communityNum = in.readString();
        this.banner = in.readString();
    }

    public static final Creator<HuatiBean> CREATOR = new Creator<HuatiBean>() {
        @Override
        public HuatiBean createFromParcel(Parcel source) {
            return new HuatiBean(source);
        }

        @Override
        public HuatiBean[] newArray(int size) {
            return new HuatiBean[size];
        }
    };
}
