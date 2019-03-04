package com.company.qcy.huodong.toupiao.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ToupiaoBean implements Parcelable {


    @Override
    public String toString() {
        return "ToupiaoBean{" +
                "id=" + id +
                ", banner='" + banner + '\'' +
                ", name='" + name + '\'' +
                ", startTime='" + startTime + '\'' +
                ", startTimeStamp='" + startTimeStamp + '\'' +
                ", endTime='" + endTime + '\'' +
                ", endTimeStamp='" + endTimeStamp + '\'' +
                ", ticketNum='" + ticketNum + '\'' +
                ", isRepeat='" + isRepeat + '\'' +
                ", clickNum='" + clickNum + '\'' +
                ", extraClickNum='" + extraClickNum + '\'' +
                ", status='" + status + '\'' +
                ", sortNum='" + sortNum + '\'' +
                ", applicationNum='" + applicationNum + '\'' +
                ", joinNum='" + joinNum + '\'' +
                ", isValid='" + isValid + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", endCode='" + endCode + '\'' +
                ", ruleList=" + ruleList +
                ", detailList=" + detailList +
                '}';
    }

    /**
     * id : 9
     * banner : /groupBuy/1550737689935W1K2PH.png
     * name : 2019年七彩云电商人才选拔
     * startTime : null
     * startTimeStamp : 1550764800000
     * endTime : null
     * endTimeStamp : 1552060800000
     * ticketNum : null
     * isRepeat : null
     * clickNum : 0
     * extraClickNum : null
     * status : null
     * sortNum : null
     * applicationNum : 0
     * joinNum : 0
     * isValid : null
     * createdAt : null
     * updatedAt : null
     * createdBy : null
     * updatedBy : null
     * ruleList : [{"id":null,"mainId":null,"key":"投票时间","value":"2019年2月22日0时0分0秒至2019年3月9日0时0分0秒"},{"id":null,"mainId":null,"key":"投票规则","value":"每人每天只能投1票"}]
     * detailList : []
     */

    private Long id;
    private String banner;
    private String name;
    private String startTime;
    private String startTimeStamp;
    private String endTime;
    private String endTimeStamp;
    private String ticketNum;
    private String isRepeat;
    private String clickNum;
    private String extraClickNum;
    private String status;
    private String sortNum;
    private String applicationNum;
    private String joinNum;
    private String isValid;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;
    private String endCode;

    public String getEndCode() {
        return endCode;
    }

    public void setEndCode(String endCode) {
        this.endCode = endCode;
    }

    private List<RuleListBean> ruleList;
    private List<String> detailList;


    public List<String> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<String> detailList) {
        this.detailList = detailList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(String startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndTimeStamp() {
        return endTimeStamp;
    }

    public void setEndTimeStamp(String endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
    }

    public String getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(String ticketNum) {
        this.ticketNum = ticketNum;
    }

    public String getIsRepeat() {
        return isRepeat;
    }

    public void setIsRepeat(String isRepeat) {
        this.isRepeat = isRepeat;
    }

    public String getClickNum() {
        return clickNum;
    }

    public void setClickNum(String clickNum) {
        this.clickNum = clickNum;
    }

    public String getExtraClickNum() {
        return extraClickNum;
    }

    public void setExtraClickNum(String extraClickNum) {
        this.extraClickNum = extraClickNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSortNum() {
        return sortNum;
    }

    public void setSortNum(String sortNum) {
        this.sortNum = sortNum;
    }

    public String getApplicationNum() {
        return applicationNum;
    }

    public void setApplicationNum(String applicationNum) {
        this.applicationNum = applicationNum;
    }

    public String getJoinNum() {
        return joinNum;
    }

    public void setJoinNum(String joinNum) {
        this.joinNum = joinNum;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public List<RuleListBean> getRuleList() {
        return ruleList;
    }

    public void setRuleList(List<RuleListBean> ruleList) {
        this.ruleList = ruleList;
    }

    public static class RuleListBean implements Parcelable {

        @Override
        public String toString() {
            return "RuleListBean{" +
                    "id=" + id +
                    ", mainId=" + mainId +
                    ", key='" + key + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }

        /**
         * id : null
         * mainId : null
         * key : 投票时间
         * value : 2019年2月22日0时0分0秒至2019年3月9日0时0分0秒
         */

        private Long id;
        private Long mainId;
        private String key;
        private String value;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getMainId() {
            return mainId;
        }

        public void setMainId(Long mainId) {
            this.mainId = mainId;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(this.id);
            dest.writeValue(this.mainId);
            dest.writeString(this.key);
            dest.writeString(this.value);
        }

        public RuleListBean() {
        }

        protected RuleListBean(Parcel in) {
            this.id = (Long) in.readValue(Long.class.getClassLoader());
            this.mainId = (Long) in.readValue(Long.class.getClassLoader());
            this.key = in.readString();
            this.value = in.readString();
        }

        public static final Parcelable.Creator<RuleListBean> CREATOR = new Parcelable.Creator<RuleListBean>() {
            @Override
            public RuleListBean createFromParcel(Parcel source) {
                return new RuleListBean(source);
            }

            @Override
            public RuleListBean[] newArray(int size) {
                return new RuleListBean[size];
            }
        };
    }

    public ToupiaoBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.banner);
        dest.writeString(this.name);
        dest.writeString(this.startTime);
        dest.writeString(this.startTimeStamp);
        dest.writeString(this.endTime);
        dest.writeString(this.endTimeStamp);
        dest.writeString(this.ticketNum);
        dest.writeString(this.isRepeat);
        dest.writeString(this.clickNum);
        dest.writeString(this.extraClickNum);
        dest.writeString(this.status);
        dest.writeString(this.sortNum);
        dest.writeString(this.applicationNum);
        dest.writeString(this.joinNum);
        dest.writeString(this.isValid);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeString(this.createdBy);
        dest.writeString(this.updatedBy);
        dest.writeString(this.endCode);
        dest.writeTypedList(this.ruleList);
        dest.writeStringList(this.detailList);
    }

    protected ToupiaoBean(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.banner = in.readString();
        this.name = in.readString();
        this.startTime = in.readString();
        this.startTimeStamp = in.readString();
        this.endTime = in.readString();
        this.endTimeStamp = in.readString();
        this.ticketNum = in.readString();
        this.isRepeat = in.readString();
        this.clickNum = in.readString();
        this.extraClickNum = in.readString();
        this.status = in.readString();
        this.sortNum = in.readString();
        this.applicationNum = in.readString();
        this.joinNum = in.readString();
        this.isValid = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.createdBy = in.readString();
        this.updatedBy = in.readString();
        this.endCode = in.readString();
        this.ruleList = in.createTypedArrayList(RuleListBean.CREATOR);
        this.detailList = in.createStringArrayList();
    }

    public static final Creator<ToupiaoBean> CREATOR = new Creator<ToupiaoBean>() {
        @Override
        public ToupiaoBean createFromParcel(Parcel source) {
            return new ToupiaoBean(source);
        }

        @Override
        public ToupiaoBean[] newArray(int size) {
            return new ToupiaoBean[size];
        }
    };
}
