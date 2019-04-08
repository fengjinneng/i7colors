package com.company.qcy.bean.pengyouquan;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class PengyouquanBean implements Parcelable {


    @Override
    public String toString() {
        return "PengyouquanBean{" +
                "videoPicUrl='" + videoPicUrl + '\'' +
                ", videoPicWidth=" + videoPicWidth +
                ", videoPicHigh=" + videoPicHigh +
                ", id=" + id +
                ", userId=" + userId +
                ", loginUserId=" + loginUserId +
                ", loginCompanyId=" + loginCompanyId +
                ", postUser='" + postUser + '\'' +
                ", postUserPhoto='" + postUserPhoto + '\'' +
                ", content='" + content + '\'' +
                ", pic1='" + pic1 + '\'' +
                ", pic2='" + pic2 + '\'' +
                ", pic3='" + pic3 + '\'' +
                ", pic4='" + pic4 + '\'' +
                ", pic5='" + pic5 + '\'' +
                ", isCompany='" + isCompany + '\'' +
                ", isDyeV='" + isDyeV + '\'' +
                ", isFollow='" + isFollow + '\'' +
                ", dyeFollowCount='" + dyeFollowCount + '\'' +
                ", isCharger='" + isCharger + '\'' +
                ", companyName='" + companyName + '\'' +
                ", dyeVName='" + dyeVName + '\'' +
                ", bossLevel='" + bossLevel + '\'' +
                ", pic6='" + pic6 + '\'' +
                ", pic7='" + pic7 + '\'' +
                ", pic8='" + pic8 + '\'' +
                ", pic9='" + pic9 + '\'' +
                ", url='" + url + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", createdAtStamp='" + createdAtStamp + '\'' +
                ", isLike='" + isLike + '\'' +
                ", commentList=" + commentList +
                ", likeList=" + likeList +
                '}';
    }

    private String videoPicUrl;
    private Integer videoPicWidth;
    private Integer videoPicHigh;
    private Long id;
    private Long userId;
    private Long loginUserId;
    private Long loginCompanyId;
    private String postUser;
    private String postUserPhoto;
    private String content;
    private String pic1;
    private String pic2;
    private String pic3;
    private String pic4;
    private String pic5;
    private String isCompany;
    private String isDyeV;
    private String isFollow;
    private String dyeFollowCount;
    private String isCharger;
    private String companyName;
    private String dyeVName;
    private String bossLevel;
    private String locationAddress;
    private String longitude;
    private String latitude;
    private TopicBean topic;
    private String locationTitle;
    private ShareBean shareBean;

    public ShareBean getShareBean() {
        return shareBean;
    }

    public void setShareBean(ShareBean shareBean) {
        this.shareBean = shareBean;
    }

    public static class ShareBean implements Parcelable {


        /**
         * id : 4210
         * pic : /news/coverImg/15537345094365CWCU1.jpg
         * title : 染料价格：分散染料价格还会涨吗？
         * content : 染料价格：分散染料价格还会涨吗？
         */

        private Long id;
        private String pic;
        private String title;
        private String content;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(this.id);
            dest.writeString(this.pic);
            dest.writeString(this.title);
            dest.writeString(this.content);
        }

        public ShareBean() {
        }

        protected ShareBean(Parcel in) {
            this.id = (Long) in.readValue(Long.class.getClassLoader());
            this.pic = in.readString();
            this.title = in.readString();
            this.content = in.readString();
        }

        public static final Creator<ShareBean> CREATOR = new Creator<ShareBean>() {
            @Override
            public ShareBean createFromParcel(Parcel source) {
                return new ShareBean(source);
            }

            @Override
            public ShareBean[] newArray(int size) {
                return new ShareBean[size];
            }
        };
    }

    public String getLocationTitle() {
        return locationTitle;
    }

    public void setLocationTitle(String locationTitle) {
        this.locationTitle = locationTitle;
    }

    public TopicBean getTopic() {
        return topic;
    }

    public void setTopic(TopicBean topic) {
        this.topic = topic;
    }

    public static class TopicBean implements Parcelable {

        /**
         * id : 7
         * parentId : null
         * level : null
         * title : 环保降耗
         * banner :
         * description : 环保节能降耗环保节能降耗环保节能降耗环保节能降耗环保节能降耗环保节能降耗环保节能降耗环保节能降耗环保节能降耗环保节能降耗环保节能降耗
         * communityNum : null
         * isValid : null
         * createdAt : null
         * updatedAt : null
         * topicList : [{"id":8,"parentId":7,"level":null,"title":"节能","banner":"","description":"节能节能节能节能节能节能节能节能节能节能节能节能","communityNum":null,"isValid":null,"createdAt":null,"updatedAt":null,"topicList":null,"sortNum":null}]
         * sortNum : null
         */

        private Long id;
        private Long parentId;
        private String title;
        private String banner;
        private String description;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBanner() {
            return banner;
        }

        public void setBanner(String banner) {
            this.banner = banner;
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
             * id : 8
             * parentId : 7
             * level : null
             * title : 节能
             * banner :
             * description : 节能节能节能节能节能节能节能节能节能节能节能节能
             * communityNum : null
             * isValid : null
             * createdAt : null
             * updatedAt : null
             * topicList : null
             * sortNum : null
             */

            private Long id;
            private Long parentId;
            private String title;
            private String banner;
            private String description;

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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getBanner() {
                return banner;
            }

            public void setBanner(String banner) {
                this.banner = banner;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(this.id);
            dest.writeValue(this.parentId);
            dest.writeString(this.title);
            dest.writeString(this.banner);
            dest.writeString(this.description);
            dest.writeList(this.topicList);
        }

        public TopicBean() {
        }

        protected TopicBean(Parcel in) {
            this.id = (Long) in.readValue(Long.class.getClassLoader());
            this.parentId = (Long) in.readValue(Long.class.getClassLoader());
            this.title = in.readString();
            this.banner = in.readString();
            this.description = in.readString();
            this.topicList = new ArrayList<TopicListBean>();
            in.readList(this.topicList, TopicListBean.class.getClassLoader());
        }

        public static final Creator<TopicBean> CREATOR = new Creator<TopicBean>() {
            @Override
            public TopicBean createFromParcel(Parcel source) {
                return new TopicBean(source);
            }

            @Override
            public TopicBean[] newArray(int size) {
                return new TopicBean[size];
            }
        };
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getBossLevel() {
        return bossLevel;
    }

    public void setBossLevel(String bossLevel) {
        this.bossLevel = bossLevel;
    }

    public String getDyeVName() {
        return dyeVName;
    }

    public void setDyeVName(String dyeVName) {
        this.dyeVName = dyeVName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIsCharger() {
        return isCharger;
    }

    public void setIsCharger(String isCharger) {
        this.isCharger = isCharger;
    }

    public String getDyeFollowCount() {
        return dyeFollowCount;
    }

    public void setDyeFollowCount(String dyeFollowCount) {
        this.dyeFollowCount = dyeFollowCount;
    }

    public String getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(String isFollow) {
        this.isFollow = isFollow;
    }

    public String getIsDyeV() {
        return isDyeV;
    }

    public void setIsDyeV(String isDyeV) {
        this.isDyeV = isDyeV;
    }

    public String getIsCompany() {
        return isCompany;
    }

    public void setIsCompany(String isCompany) {
        this.isCompany = isCompany;
    }

    public static Creator<PengyouquanBean> getCREATOR() {
        return CREATOR;
    }

    private String pic6;
    private String pic7;
    private String pic8;
    private String pic9;
    private String url;
    private String createdAt;
    private String createdAtStamp;
    private String isLike;
    private List<CommentListBean> commentList;
    private List<LikeListBean> likeList;

    public String getVideoPicUrl() {
        return videoPicUrl;
    }

    public void setVideoPicUrl(String videoPicUrl) {
        this.videoPicUrl = videoPicUrl;
    }

    public Integer getVideoPicWidth() {
        return videoPicWidth;
    }

    public void setVideoPicWidth(Integer videoPicWidth) {
        this.videoPicWidth = videoPicWidth;
    }

    public Integer getVideoPicHigh() {
        return videoPicHigh;
    }

    public void setVideoPicHigh(Integer videoPicHigh) {
        this.videoPicHigh = videoPicHigh;
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

    public Long getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(Long loginUserId) {
        this.loginUserId = loginUserId;
    }

    public Long getLoginCompanyId() {
        return loginCompanyId;
    }

    public void setLoginCompanyId(Long loginCompanyId) {
        this.loginCompanyId = loginCompanyId;
    }

    public String getPostUser() {
        return postUser;
    }

    public void setPostUser(String postUser) {
        this.postUser = postUser;
    }

    public String getPostUserPhoto() {
        return postUserPhoto;
    }

    public void setPostUserPhoto(String postUserPhoto) {
        this.postUserPhoto = postUserPhoto;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public String getPic3() {
        return pic3;
    }

    public void setPic3(String pic3) {
        this.pic3 = pic3;
    }

    public String getPic4() {
        return pic4;
    }

    public void setPic4(String pic4) {
        this.pic4 = pic4;
    }

    public String getPic5() {
        return pic5;
    }

    public void setPic5(String pic5) {
        this.pic5 = pic5;
    }

    public String getPic6() {
        return pic6;
    }

    public void setPic6(String pic6) {
        this.pic6 = pic6;
    }

    public String getPic7() {
        return pic7;
    }

    public void setPic7(String pic7) {
        this.pic7 = pic7;
    }

    public String getPic8() {
        return pic8;
    }

    public void setPic8(String pic8) {
        this.pic8 = pic8;
    }

    public String getPic9() {
        return pic9;
    }

    public void setPic9(String pic9) {
        this.pic9 = pic9;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAtStamp() {
        return createdAtStamp;
    }

    public void setCreatedAtStamp(String createdAtStamp) {
        this.createdAtStamp = createdAtStamp;
    }

    public String getIsLike() {
        return isLike;
    }

    public void setIsLike(String isLike) {
        this.isLike = isLike;
    }

    public List<CommentListBean> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentListBean> commentList) {
        this.commentList = commentList;
    }

    public List<LikeListBean> getLikeList() {
        return likeList;
    }

    public void setLikeList(List<LikeListBean> likeList) {
        this.likeList = likeList;
    }

    public static class CommentListBean implements Parcelable {

        @Override
        public String toString() {
            return "CommentListBean{" +
                    "id=" + id +
                    ", dyeId=" + dyeId +
                    ", userId=" + userId +
                    ", commentUser='" + commentUser + '\'' +
                    ", byCommentUser='" + byCommentUser + '\'' +
                    ", content='" + content + '\'' +
                    ", createdAtStamp='" + createdAtStamp + '\'' +
                    ", isCharger='" + isCharger + '\'' +
                    ", commentPhoto='" + commentPhoto + '\'' +
                    ", byUserId=" + byUserId +
                    '}';
        }

        private Long id;
        private Long dyeId;
        private Long userId;
        private String commentUser;
        private String byCommentUser;
        private String content;
        private String createdAtStamp;
        private String isCharger;
        private String commentPhoto;
        private Long byUserId;

        public Long getByUserId() {
            return byUserId;
        }

        public void setByUserId(Long byUserId) {
            this.byUserId = byUserId;
        }

        public String getCommentPhoto() {
            return commentPhoto;
        }

        public void setCommentPhoto(String commentPhoto) {
            this.commentPhoto = commentPhoto;
        }

        public static Creator<CommentListBean> getCREATOR() {
            return CREATOR;
        }


        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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

        public String getCommentUser() {
            return commentUser;
        }

        public void setCommentUser(String commentUser) {
            this.commentUser = commentUser;
        }

        public String getByCommentUser() {
            return byCommentUser;
        }

        public void setByCommentUser(String byCommentUser) {
            this.byCommentUser = byCommentUser;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreatedAtStamp() {
            return createdAtStamp;
        }

        public void setCreatedAtStamp(String createdAtStamp) {
            this.createdAtStamp = createdAtStamp;
        }

        public String getIsCharger() {
            return isCharger;
        }

        public void setIsCharger(String isCharger) {
            this.isCharger = isCharger;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(this.id);
            dest.writeValue(this.dyeId);
            dest.writeValue(this.userId);
            dest.writeString(this.commentUser);
            dest.writeString(this.byCommentUser);
            dest.writeString(this.content);
            dest.writeString(this.createdAtStamp);
            dest.writeString(this.isCharger);
        }

        public CommentListBean() {
        }

        protected CommentListBean(Parcel in) {
            this.id = (Long) in.readValue(Long.class.getClassLoader());
            this.dyeId = (Long) in.readValue(Long.class.getClassLoader());
            this.userId = (Long) in.readValue(Long.class.getClassLoader());
            this.commentUser = in.readString();
            this.byCommentUser = in.readString();
            this.content = in.readString();
            this.createdAtStamp = in.readString();
            this.isCharger = in.readString();
        }

        public static final Parcelable.Creator<CommentListBean> CREATOR = new Parcelable.Creator<CommentListBean>() {
            @Override
            public CommentListBean createFromParcel(Parcel source) {
                return new CommentListBean(source);
            }

            @Override
            public CommentListBean[] newArray(int size) {
                return new CommentListBean[size];
            }
        };
    }

    public static class LikeListBean implements Parcelable {

        @Override
        public String toString() {
            return "LikeListBean{" +
                    "id=" + id +
                    ", dyeId=" + dyeId +
                    ", userId=" + userId +
                    ", createdAt='" + createdAt + '\'' +
                    ", likeUser='" + likeUser + '\'' +
                    ", likeUserPhoto='" + likeUserPhoto + '\'' +
                    ", likePhoto='" + likePhoto + '\'' +
                    '}';
        }

        private Long id;
        private Long dyeId;
        private Long userId;
        private String createdAt;
        private String likeUser;
        private String likeUserPhoto;
        private String likePhoto;
        private String createdAtStamp;

        public String getCreatedAtStamp() {
            return createdAtStamp;
        }

        public void setCreatedAtStamp(String createdAtStamp) {
            this.createdAtStamp = createdAtStamp;
        }

        public String getLikePhoto() {
            return likePhoto;
        }

        public void setLikePhoto(String likePhoto) {
            this.likePhoto = likePhoto;
        }

        public static Creator<LikeListBean> getCREATOR() {
            return CREATOR;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getLikeUser() {
            return likeUser;
        }

        public void setLikeUser(String likeUser) {
            this.likeUser = likeUser;
        }

        public String getLikeUserPhoto() {
            return likeUserPhoto;
        }

        public void setLikeUserPhoto(String likeUserPhoto) {
            this.likeUserPhoto = likeUserPhoto;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(this.id);
            dest.writeValue(this.dyeId);
            dest.writeValue(this.userId);
            dest.writeString(this.createdAt);
            dest.writeString(this.likeUser);
            dest.writeString(this.likeUserPhoto);
        }

        public LikeListBean() {
        }

        protected LikeListBean(Parcel in) {
            this.id = (Long) in.readValue(Long.class.getClassLoader());
            this.dyeId = (Long) in.readValue(Long.class.getClassLoader());
            this.userId = (Long) in.readValue(Long.class.getClassLoader());
            this.createdAt = in.readString();
            this.likeUser = in.readString();
            this.likeUserPhoto = in.readString();
        }

        public static final Parcelable.Creator<LikeListBean> CREATOR = new Parcelable.Creator<LikeListBean>() {
            @Override
            public LikeListBean createFromParcel(Parcel source) {
                return new LikeListBean(source);
            }

            @Override
            public LikeListBean[] newArray(int size) {
                return new LikeListBean[size];
            }
        };
    }


    public PengyouquanBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.videoPicUrl);
        dest.writeValue(this.videoPicWidth);
        dest.writeValue(this.videoPicHigh);
        dest.writeValue(this.id);
        dest.writeValue(this.userId);
        dest.writeValue(this.loginUserId);
        dest.writeValue(this.loginCompanyId);
        dest.writeString(this.postUser);
        dest.writeString(this.postUserPhoto);
        dest.writeString(this.content);
        dest.writeString(this.pic1);
        dest.writeString(this.pic2);
        dest.writeString(this.pic3);
        dest.writeString(this.pic4);
        dest.writeString(this.pic5);
        dest.writeString(this.isCompany);
        dest.writeString(this.isDyeV);
        dest.writeString(this.isFollow);
        dest.writeString(this.dyeFollowCount);
        dest.writeString(this.isCharger);
        dest.writeString(this.companyName);
        dest.writeString(this.dyeVName);
        dest.writeString(this.bossLevel);
        dest.writeString(this.locationAddress);
        dest.writeString(this.longitude);
        dest.writeString(this.latitude);
        dest.writeParcelable(this.topic, flags);
        dest.writeString(this.locationTitle);
        dest.writeParcelable(this.shareBean, flags);
        dest.writeString(this.pic6);
        dest.writeString(this.pic7);
        dest.writeString(this.pic8);
        dest.writeString(this.pic9);
        dest.writeString(this.url);
        dest.writeString(this.createdAt);
        dest.writeString(this.createdAtStamp);
        dest.writeString(this.isLike);
        dest.writeTypedList(this.commentList);
        dest.writeTypedList(this.likeList);
    }

    protected PengyouquanBean(Parcel in) {
        this.videoPicUrl = in.readString();
        this.videoPicWidth = (Integer) in.readValue(Integer.class.getClassLoader());
        this.videoPicHigh = (Integer) in.readValue(Integer.class.getClassLoader());
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.userId = (Long) in.readValue(Long.class.getClassLoader());
        this.loginUserId = (Long) in.readValue(Long.class.getClassLoader());
        this.loginCompanyId = (Long) in.readValue(Long.class.getClassLoader());
        this.postUser = in.readString();
        this.postUserPhoto = in.readString();
        this.content = in.readString();
        this.pic1 = in.readString();
        this.pic2 = in.readString();
        this.pic3 = in.readString();
        this.pic4 = in.readString();
        this.pic5 = in.readString();
        this.isCompany = in.readString();
        this.isDyeV = in.readString();
        this.isFollow = in.readString();
        this.dyeFollowCount = in.readString();
        this.isCharger = in.readString();
        this.companyName = in.readString();
        this.dyeVName = in.readString();
        this.bossLevel = in.readString();
        this.locationAddress = in.readString();
        this.longitude = in.readString();
        this.latitude = in.readString();
        this.topic = in.readParcelable(TopicBean.class.getClassLoader());
        this.locationTitle = in.readString();
        this.shareBean = in.readParcelable(ShareBean.class.getClassLoader());
        this.pic6 = in.readString();
        this.pic7 = in.readString();
        this.pic8 = in.readString();
        this.pic9 = in.readString();
        this.url = in.readString();
        this.createdAt = in.readString();
        this.createdAtStamp = in.readString();
        this.isLike = in.readString();
        this.commentList = in.createTypedArrayList(CommentListBean.CREATOR);
        this.likeList = in.createTypedArrayList(LikeListBean.CREATOR);
    }

    public static final Creator<PengyouquanBean> CREATOR = new Creator<PengyouquanBean>() {
        @Override
        public PengyouquanBean createFromParcel(Parcel source) {
            return new PengyouquanBean(source);
        }

        @Override
        public PengyouquanBean[] newArray(int size) {
            return new PengyouquanBean[size];
        }
    };
}
