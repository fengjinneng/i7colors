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


    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public static Creator<HuatiBean> getCREATOR() {
        return CREATOR;
    }

    public String getCommunityNum() {
        return communityNum;
    }

    public void setCommunityNum(String communityNum) {
        this.communityNum = communityNum;
    }

    private String communityNum;
    private List<TopicListBean> topicList;


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

    public List<TopicListBean> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<TopicListBean> topicList) {
        this.topicList = topicList;
    }

    public static class TopicListBean {




        /**
         * id : 2
         * parentId : 1
         * level : 2
         * title : 助剂
         * banner : null
         * description : 助剂助剂助剂助剂助剂助剂助剂助剂助剂助剂助剂助剂助剂助剂助剂
         * communityNum : null
         * isValid : null
         * createdAt : null
         * updatedAt : null
         * topicList : null
         * sortNum : null
         */

        private Long id;
        private Long parentId;
        private String level;
        private String title;
        private String description;
        private String communityNum;

        public String getCommunityNum() {
            return communityNum;
        }

        public void setCommunityNum(String communityNum) {
            this.communityNum = communityNum;
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
        dest.writeList(this.topicList);
    }

    protected HuatiBean(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.parentId = (Long) in.readValue(Long.class.getClassLoader());
        this.level = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.checked = in.readByte() != 0;
        this.communityNum = in.readString();
        this.topicList = new ArrayList<TopicListBean>();
        in.readList(this.topicList, TopicListBean.class.getClassLoader());
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
