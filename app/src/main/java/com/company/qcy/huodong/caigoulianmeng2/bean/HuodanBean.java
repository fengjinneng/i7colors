package com.company.qcy.huodong.caigoulianmeng2.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class HuodanBean implements Parcelable {


    private Long id;
    private String meetingName;
    private String startTime;
    private String endTime;
    private String isType;
    private String status;
    private String sortNum;
    private String isValid;
    private String createdAt;
    private String updatedAt;
    private String banner;
    private String introducePicIn;
    private String introducePicOut;
    private String introduceMobilePicIn;
    private String introduceMobilePicOut;
    private String accountPeriod;
    private String accountPeriodOut;
    private List<MeetingShopBean> meetingShopList;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getIsType() {
        return isType;
    }

    public void setIsType(String isType) {
        this.isType = isType;
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

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getIntroducePicIn() {
        return introducePicIn;
    }

    public void setIntroducePicIn(String introducePicIn) {
        this.introducePicIn = introducePicIn;
    }

    public String getIntroducePicOut() {
        return introducePicOut;
    }

    public void setIntroducePicOut(String introducePicOut) {
        this.introducePicOut = introducePicOut;
    }

    public String getIntroduceMobilePicIn() {
        return introduceMobilePicIn;
    }

    public void setIntroduceMobilePicIn(String introduceMobilePicIn) {
        this.introduceMobilePicIn = introduceMobilePicIn;
    }

    public String getIntroduceMobilePicOut() {
        return introduceMobilePicOut;
    }

    public void setIntroduceMobilePicOut(String introduceMobilePicOut) {
        this.introduceMobilePicOut = introduceMobilePicOut;
    }

    public String getAccountPeriod() {
        return accountPeriod;
    }

    public void setAccountPeriod(String accountPeriod) {
        this.accountPeriod = accountPeriod;
    }

    public String getAccountPeriodOut() {
        return accountPeriodOut;
    }

    public void setAccountPeriodOut(String accountPeriodOut) {
        this.accountPeriodOut = accountPeriodOut;
    }

    public List<MeetingShopBean> getMeetingShopList() {
        return meetingShopList;
    }

    public void setMeetingShopList(List<MeetingShopBean> meetingShopList) {
        this.meetingShopList = meetingShopList;
    }

    public static class MeetingShopBean implements Parcelable {



        private Long id;
        private String shopName;
        private String diyShop;
        private String productPic;
        private String status;
        private String packing;
        private String numUnit;
        private String orderStatus;
        private String phone;
        private Long meetingId;
        private String reservationNum;
        private String price;
        private String priceUnit;
        private List<MeetingTypeBean> meetingTypeList;
        private String startTime;
        private String endTime;
        private String outputNum;
        private String effectiveTime;

        public String getEffectiveTime() {
            return effectiveTime;
        }

        public void setEffectiveTime(String effectiveTime) {
            this.effectiveTime = effectiveTime;
        }

        public String getOutputNum() {
            return outputNum;
        }

        public void setOutputNum(String outputNum) {
            this.outputNum = outputNum;
        }

        public String getPriceUnit() {
            return priceUnit;
        }

        public void setPriceUnit(String priceUnit) {
            this.priceUnit = priceUnit;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getDiyShop() {
            return diyShop;
        }

        public void setDiyShop(String diyShop) {
            this.diyShop = diyShop;
        }

        public String getProductPic() {
            return productPic;
        }

        public void setProductPic(String productPic) {
            this.productPic = productPic;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPacking() {
            return packing;
        }

        public void setPacking(String packing) {
            this.packing = packing;
        }

        public String getNumUnit() {
            return numUnit;
        }

        public void setNumUnit(String numUnit) {
            this.numUnit = numUnit;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Long getMeetingId() {
            return meetingId;
        }

        public void setMeetingId(Long meetingId) {
            this.meetingId = meetingId;
        }

        public String getReservationNum() {
            return reservationNum;
        }

        public void setReservationNum(String reservationNum) {
            this.reservationNum = reservationNum;
        }

        public List<MeetingTypeBean> getMeetingTypeList() {
            return meetingTypeList;
        }

        public void setMeetingTypeList(List<MeetingTypeBean> meetingTypeList) {
            this.meetingTypeList = meetingTypeList;
        }

        public static class MeetingTypeBean implements Parcelable {


            /**
             * id : 275
             * orderId : null
             * infoId : 13111111206
             * referenceType : 12
             */

            private Long id;
            private Long orderId;
            private Long infoId;
            private String referenceType;


            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public Long getOrderId() {
                return orderId;
            }

            public void setOrderId(Long orderId) {
                this.orderId = orderId;
            }

            public Long getInfoId() {
                return infoId;
            }

            public void setInfoId(Long infoId) {
                this.infoId = infoId;
            }

            public String getReferenceType() {
                return referenceType;
            }

            public void setReferenceType(String referenceType) {
                this.referenceType = referenceType;
            }


            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeValue(this.id);
                dest.writeValue(this.orderId);
                dest.writeValue(this.infoId);
                dest.writeString(this.referenceType);
            }

            public MeetingTypeBean() {
            }

            protected MeetingTypeBean(Parcel in) {
                this.id = (Long) in.readValue(Long.class.getClassLoader());
                this.orderId = (Long) in.readValue(Long.class.getClassLoader());
                this.infoId = (Long) in.readValue(Long.class.getClassLoader());
                this.referenceType = in.readString();
            }

            public static final Parcelable.Creator<MeetingTypeBean> CREATOR = new Parcelable.Creator<MeetingTypeBean>() {
                @Override
                public MeetingTypeBean createFromParcel(Parcel source) {
                    return new MeetingTypeBean(source);
                }

                @Override
                public MeetingTypeBean[] newArray(int size) {
                    return new MeetingTypeBean[size];
                }
            };
        }

        public MeetingShopBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(this.id);
            dest.writeString(this.shopName);
            dest.writeString(this.diyShop);
            dest.writeString(this.productPic);
            dest.writeString(this.status);
            dest.writeString(this.packing);
            dest.writeString(this.numUnit);
            dest.writeString(this.orderStatus);
            dest.writeString(this.phone);
            dest.writeValue(this.meetingId);
            dest.writeString(this.reservationNum);
            dest.writeString(this.price);
            dest.writeString(this.priceUnit);
            dest.writeTypedList(this.meetingTypeList);
            dest.writeString(this.startTime);
            dest.writeString(this.endTime);
            dest.writeString(this.outputNum);
            dest.writeString(this.effectiveTime);
        }

        protected MeetingShopBean(Parcel in) {
            this.id = (Long) in.readValue(Long.class.getClassLoader());
            this.shopName = in.readString();
            this.diyShop = in.readString();
            this.productPic = in.readString();
            this.status = in.readString();
            this.packing = in.readString();
            this.numUnit = in.readString();
            this.orderStatus = in.readString();
            this.phone = in.readString();
            this.meetingId = (Long) in.readValue(Long.class.getClassLoader());
            this.reservationNum = in.readString();
            this.price = in.readString();
            this.priceUnit = in.readString();
            this.meetingTypeList = in.createTypedArrayList(MeetingTypeBean.CREATOR);
            this.startTime = in.readString();
            this.endTime = in.readString();
            this.outputNum = in.readString();
            this.effectiveTime = in.readString();
        }

        public static final Creator<MeetingShopBean> CREATOR = new Creator<MeetingShopBean>() {
            @Override
            public MeetingShopBean createFromParcel(Parcel source) {
                return new MeetingShopBean(source);
            }

            @Override
            public MeetingShopBean[] newArray(int size) {
                return new MeetingShopBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.meetingName);
        dest.writeString(this.startTime);
        dest.writeString(this.endTime);
        dest.writeString(this.isType);
        dest.writeString(this.status);
        dest.writeString(this.sortNum);
        dest.writeString(this.isValid);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeString(this.banner);
        dest.writeString(this.introducePicIn);
        dest.writeString(this.introducePicOut);
        dest.writeString(this.introduceMobilePicIn);
        dest.writeString(this.introduceMobilePicOut);
        dest.writeString(this.accountPeriod);
        dest.writeString(this.accountPeriodOut);
        dest.writeTypedList(this.meetingShopList);
    }

    public HuodanBean() {
    }

    protected HuodanBean(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.meetingName = in.readString();
        this.startTime = in.readString();
        this.endTime = in.readString();
        this.isType = in.readString();
        this.status = in.readString();
        this.sortNum = in.readString();
        this.isValid = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.banner = in.readString();
        this.introducePicIn = in.readString();
        this.introducePicOut = in.readString();
        this.introduceMobilePicIn = in.readString();
        this.introduceMobilePicOut = in.readString();
        this.accountPeriod = in.readString();
        this.accountPeriodOut = in.readString();
        this.meetingShopList = in.createTypedArrayList(MeetingShopBean.CREATOR);
    }

    public static final Parcelable.Creator<HuodanBean> CREATOR = new Parcelable.Creator<HuodanBean>() {
        @Override
        public HuodanBean createFromParcel(Parcel source) {
            return new HuodanBean(source);
        }

        @Override
        public HuodanBean[] newArray(int size) {
            return new HuodanBean[size];
        }
    };
}
