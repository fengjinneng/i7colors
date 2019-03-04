package com.company.qcy.huodong.caigoulianmeng.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class HHHBean {



    /**
     * picOut : /groupBuy/1545875897831ADS2QD.jpg
     * picIn : /groupBuy/15458758919003148XJ.jpg
     * accountPeriodOut : ["货到付款","款到发货","货到30天付款","其他"]
     * meetingList : {"code":"SUCCESS","msg":null,"banner":"/groupBuy/1541138996984G9K893.jpg","data":[{"id":45,"sortNum":null,"shopName":"rewtg ","diyShop":"0","productPic":null,"status":"1","packing":"25KG/袋","meetingTypeList":[{"id":204,"orderId":45,"infoId":null,"referenceType":"参考A"},{"id":205,"orderId":45,"infoId":null,"referenceType":"参考B"},{"id":206,"orderId":45,"infoId":null,"referenceType":"参考C"}],"meetingInfoList":null,"allNum":null,"allOutputNum":null,"companyName":null,"len":null,"price":null,"priceUnit":null,"numUnit":null,"reservationNum":null,"outputNum":null,"effectiveTime":null,"orderStatus":"0","referenceType":null,"phone":null,"meetingUser":null,"name":null,"accountPeriod":null,"payType":null,"provinceName":null,"cityName":null,"createdAt":"2018-11-29 01:30:10","meetingId":49,"banner":null,"meetingName":null,"positioner":null}],"pageNo":null,"pageSize":null,"totalCount":null,"perPageNo":null,"nextPageNo":null,"totalPageSize":null,"isFirstPage":null,"isLastPage":null,"total":null,"rows":[{"id":45,"sortNum":null,"shopName":"rewtg ","diyShop":"0","productPic":null,"status":"1","packing":"25KG/袋","meetingTypeList":[{"id":204,"orderId":45,"infoId":null,"referenceType":"参考A"},{"id":205,"orderId":45,"infoId":null,"referenceType":"参考B"},{"id":206,"orderId":45,"infoId":null,"referenceType":"参考C"}],"meetingInfoList":null,"allNum":null,"allOutputNum":null,"companyName":null,"len":null,"price":null,"priceUnit":null,"numUnit":null,"reservationNum":null,"outputNum":null,"effectiveTime":null,"orderStatus":"0","referenceType":null,"phone":null,"meetingUser":null,"name":null,"accountPeriod":null,"payType":null,"provinceName":null,"cityName":null,"createdAt":"2018-11-29 01:30:10","meetingId":49,"banner":null,"meetingName":null,"positioner":null}]}
     * accountPeriod : ["货到付款","款到发货","其他"]
     */

    private String picOut;
    private String picIn;
    private MeetingListBean meetingList;
    private List<String> accountPeriodOut;
    private List<String> accountPeriod;

    public String getPicOut() {
        return picOut;
    }

    public void setPicOut(String picOut) {
        this.picOut = picOut;
    }

    public String getPicIn() {
        return picIn;
    }

    public void setPicIn(String picIn) {
        this.picIn = picIn;
    }

    public MeetingListBean getMeetingList() {
        return meetingList;
    }

    public void setMeetingList(MeetingListBean meetingList) {
        this.meetingList = meetingList;
    }

    public List<String> getAccountPeriodOut() {
        return accountPeriodOut;
    }

    public void setAccountPeriodOut(List<String> accountPeriodOut) {
        this.accountPeriodOut = accountPeriodOut;
    }

    public List<String> getAccountPeriod() {
        return accountPeriod;
    }

    public void setAccountPeriod(List<String> accountPeriod) {
        this.accountPeriod = accountPeriod;
    }

    public static class MeetingListBean {
        /**
         * code : SUCCESS
         * msg : null
         * banner : /groupBuy/1541138996984G9K893.jpg
         * data : [{"id":45,"sortNum":null,"shopName":"rewtg ","diyShop":"0","productPic":null,"status":"1","packing":"25KG/袋","meetingTypeList":[{"id":204,"orderId":45,"infoId":null,"referenceType":"参考A"},{"id":205,"orderId":45,"infoId":null,"referenceType":"参考B"},{"id":206,"orderId":45,"infoId":null,"referenceType":"参考C"}],"meetingInfoList":null,"allNum":null,"allOutputNum":null,"companyName":null,"len":null,"price":null,"priceUnit":null,"numUnit":null,"reservationNum":null,"outputNum":null,"effectiveTime":null,"orderStatus":"0","referenceType":null,"phone":null,"meetingUser":null,"name":null,"accountPeriod":null,"payType":null,"provinceName":null,"cityName":null,"createdAt":"2018-11-29 01:30:10","meetingId":49,"banner":null,"meetingName":null,"positioner":null}]
         * pageNo : null
         * pageSize : null
         * totalCount : null
         * perPageNo : null
         * nextPageNo : null
         * totalPageSize : null
         * isFirstPage : null
         * isLastPage : null
         * total : null
         * rows : [{"id":45,"sortNum":null,"shopName":"rewtg ","diyShop":"0","productPic":null,"status":"1","packing":"25KG/袋","meetingTypeList":[{"id":204,"orderId":45,"infoId":null,"referenceType":"参考A"},{"id":205,"orderId":45,"infoId":null,"referenceType":"参考B"},{"id":206,"orderId":45,"infoId":null,"referenceType":"参考C"}],"meetingInfoList":null,"allNum":null,"allOutputNum":null,"companyName":null,"len":null,"price":null,"priceUnit":null,"numUnit":null,"reservationNum":null,"outputNum":null,"effectiveTime":null,"orderStatus":"0","referenceType":null,"phone":null,"meetingUser":null,"name":null,"accountPeriod":null,"payType":null,"provinceName":null,"cityName":null,"createdAt":"2018-11-29 01:30:10","meetingId":49,"banner":null,"meetingName":null,"positioner":null}]
         */

        private String code;
        private String msg;
        private String banner;
        private String pageNo;
        private String pageSize;
        private String totalCount;
        private String perPageNo;
        private String nextPageNo;
        private String totalPageSize;
        private String isFirstPage;
        private String isLastPage;
        private String total;
        private List<DataBean> data;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getBanner() {
            return banner;
        }

        public void setBanner(String banner) {
            this.banner = banner;
        }

        public String getPageNo() {
            return pageNo;
        }

        public void setPageNo(String pageNo) {
            this.pageNo = pageNo;
        }

        public String getPageSize() {
            return pageSize;
        }

        public void setPageSize(String pageSize) {
            this.pageSize = pageSize;
        }

        public String getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(String totalCount) {
            this.totalCount = totalCount;
        }

        public String getPerPageNo() {
            return perPageNo;
        }

        public void setPerPageNo(String perPageNo) {
            this.perPageNo = perPageNo;
        }

        public String getNextPageNo() {
            return nextPageNo;
        }

        public void setNextPageNo(String nextPageNo) {
            this.nextPageNo = nextPageNo;
        }

        public String getTotalPageSize() {
            return totalPageSize;
        }

        public void setTotalPageSize(String totalPageSize) {
            this.totalPageSize = totalPageSize;
        }

        public String getIsFirstPage() {
            return isFirstPage;
        }

        public void setIsFirstPage(String isFirstPage) {
            this.isFirstPage = isFirstPage;
        }

        public String getIsLastPage() {
            return isLastPage;
        }

        public void setIsLastPage(String isLastPage) {
            this.isLastPage = isLastPage;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }


        public static class DataBean implements Parcelable {
            /**
             * id : 45
             * sortNum : null
             * shopName : rewtg
             * diyShop : 0
             * productPic : null
             * status : 1
             * packing : 25KG/袋
             * meetingTypeList : [{"id":204,"orderId":45,"infoId":null,"referenceType":"参考A"},{"id":205,"orderId":45,"infoId":null,"referenceType":"参考B"},{"id":206,"orderId":45,"infoId":null,"referenceType":"参考C"}]
             * meetingInfoList : null
             * allNum : null
             * allOutputNum : null
             * companyName : null
             * len : null
             * price : null
             * priceUnit : null
             * numUnit : null
             * reservationNum : null
             * outputNum : null
             * effectiveTime : null
             * orderStatus : 0
             * referenceType : null
             * phone : null
             * meetingUser : null
             * name : null
             * accountPeriod : null
             * payType : null
             * provinceName : null
             * cityName : null
             * createdAt : 2018-11-29 01:30:10
             * meetingId : 49
             * banner : null
             * meetingName : null
             * positioner : null
             */

            private Long id;
            private String sortNum;
            private String shopName;
            private String diyShop;
            private String productPic;
            private String status;
            private String packing;
            private String meetingInfoList;
            private String allNum;
            private String allOutputNum;
            private String companyName;
            private String len;
            private String price;
            private String priceUnit;
            private String numUnit;
            private String reservationNum;
            private String outputNum;
            private String effectiveTime;
            private String orderStatus;
            private String referenceType;
            private String phone;
            private String meetingUser;
            private String name;
            private String accountPeriod;
            private String payType;
            private String provinceName;
            private String cityName;
            private String createdAt;
            private Long meetingId;
            private String banner;
            private String meetingName;
            private String positioner;
            private List<MeetingTypeListBean> meetingTypeList;
            private boolean checked;

            public boolean isChecked() {
                return checked;
            }

            public void setChecked(boolean checked) {
                this.checked = checked;
            }

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public String getSortNum() {
                return sortNum;
            }

            public void setSortNum(String sortNum) {
                this.sortNum = sortNum;
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

            public String getMeetingInfoList() {
                return meetingInfoList;
            }

            public void setMeetingInfoList(String meetingInfoList) {
                this.meetingInfoList = meetingInfoList;
            }

            public String getAllNum() {
                return allNum;
            }

            public void setAllNum(String allNum) {
                this.allNum = allNum;
            }

            public String getAllOutputNum() {
                return allOutputNum;
            }

            public void setAllOutputNum(String allOutputNum) {
                this.allOutputNum = allOutputNum;
            }

            public String getCompanyName() {
                return companyName;
            }

            public void setCompanyName(String companyName) {
                this.companyName = companyName;
            }

            public String getLen() {
                return len;
            }

            public void setLen(String len) {
                this.len = len;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getPriceUnit() {
                return priceUnit;
            }

            public void setPriceUnit(String priceUnit) {
                this.priceUnit = priceUnit;
            }

            public String getNumUnit() {
                return numUnit;
            }

            public void setNumUnit(String numUnit) {
                this.numUnit = numUnit;
            }

            public String getReservationNum() {
                return reservationNum;
            }

            public void setReservationNum(String reservationNum) {
                this.reservationNum = reservationNum;
            }

            public String getOutputNum() {
                return outputNum;
            }

            public void setOutputNum(String outputNum) {
                this.outputNum = outputNum;
            }

            public String getEffectiveTime() {
                return effectiveTime;
            }

            public void setEffectiveTime(String effectiveTime) {
                this.effectiveTime = effectiveTime;
            }

            public String getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(String orderStatus) {
                this.orderStatus = orderStatus;
            }

            public String getReferenceType() {
                return referenceType;
            }

            public void setReferenceType(String referenceType) {
                this.referenceType = referenceType;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getMeetingUser() {
                return meetingUser;
            }

            public void setMeetingUser(String meetingUser) {
                this.meetingUser = meetingUser;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAccountPeriod() {
                return accountPeriod;
            }

            public void setAccountPeriod(String accountPeriod) {
                this.accountPeriod = accountPeriod;
            }

            public String getPayType() {
                return payType;
            }

            public void setPayType(String payType) {
                this.payType = payType;
            }

            public String getProvinceName() {
                return provinceName;
            }

            public void setProvinceName(String provinceName) {
                this.provinceName = provinceName;
            }

            public String getCityName() {
                return cityName;
            }

            public void setCityName(String cityName) {
                this.cityName = cityName;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public Long getMeetingId() {
                return meetingId;
            }

            public void setMeetingId(Long meetingId) {
                this.meetingId = meetingId;
            }

            public String getBanner() {
                return banner;
            }

            public void setBanner(String banner) {
                this.banner = banner;
            }

            public String getMeetingName() {
                return meetingName;
            }

            public void setMeetingName(String meetingName) {
                this.meetingName = meetingName;
            }

            public String getPositioner() {
                return positioner;
            }

            public void setPositioner(String positioner) {
                this.positioner = positioner;
            }

            public List<MeetingTypeListBean> getMeetingTypeList() {
                return meetingTypeList;
            }

            public void setMeetingTypeList(List<MeetingTypeListBean> meetingTypeList) {
                this.meetingTypeList = meetingTypeList;
            }

            public static class MeetingTypeListBean implements Parcelable {
                /**
                 * id : 204
                 * orderId : 45
                 * infoId : null
                 * referenceType : 参考A
                 */

                private Long id;
                private Long orderId;
                private String infoId;
                private boolean checked;
                private String referenceType;

                public boolean getChecked() {
                    return checked;
                }

                public void setChecked(boolean checked) {
                    this.checked = checked;
                }

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

                public String getInfoId() {
                    return infoId;
                }

                public void setInfoId(String infoId) {
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
                    dest.writeString(this.infoId);
                    dest.writeValue(this.checked);
                    dest.writeString(this.referenceType);
                }

                public MeetingTypeListBean() {
                }

                protected MeetingTypeListBean(Parcel in) {
                    this.id = (Long) in.readValue(Long.class.getClassLoader());
                    this.orderId = (Long) in.readValue(Long.class.getClassLoader());
                    this.infoId = in.readString();
                    this.checked = (boolean) in.readValue(Boolean.class.getClassLoader());
                    this.referenceType = in.readString();
                }

                public static final Creator<MeetingTypeListBean> CREATOR = new Creator<MeetingTypeListBean>() {
                    @Override
                    public MeetingTypeListBean createFromParcel(Parcel source) {
                        return new MeetingTypeListBean(source);
                    }

                    @Override
                    public MeetingTypeListBean[] newArray(int size) {
                        return new MeetingTypeListBean[size];
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
                dest.writeString(this.sortNum);
                dest.writeString(this.shopName);
                dest.writeString(this.diyShop);
                dest.writeString(this.productPic);
                dest.writeString(this.status);
                dest.writeString(this.packing);
                dest.writeString(this.meetingInfoList);
                dest.writeString(this.allNum);
                dest.writeString(this.allOutputNum);
                dest.writeString(this.companyName);
                dest.writeString(this.len);
                dest.writeString(this.price);
                dest.writeString(this.priceUnit);
                dest.writeString(this.numUnit);
                dest.writeString(this.reservationNum);
                dest.writeString(this.outputNum);
                dest.writeString(this.effectiveTime);
                dest.writeString(this.orderStatus);
                dest.writeString(this.referenceType);
                dest.writeString(this.phone);
                dest.writeString(this.meetingUser);
                dest.writeString(this.name);
                dest.writeString(this.accountPeriod);
                dest.writeString(this.payType);
                dest.writeString(this.provinceName);
                dest.writeString(this.cityName);
                dest.writeString(this.createdAt);
                dest.writeValue(this.meetingId);
                dest.writeString(this.banner);
                dest.writeString(this.meetingName);
                dest.writeString(this.positioner);
                dest.writeList(this.meetingTypeList);
                dest.writeByte(this.checked ? (byte) 1 : (byte) 0);
            }

            public DataBean() {
            }

            protected DataBean(Parcel in) {
                this.id = (Long) in.readValue(Long.class.getClassLoader());
                this.sortNum = in.readString();
                this.shopName = in.readString();
                this.diyShop = in.readString();
                this.productPic = in.readString();
                this.status = in.readString();
                this.packing = in.readString();
                this.meetingInfoList = in.readString();
                this.allNum = in.readString();
                this.allOutputNum = in.readString();
                this.companyName = in.readString();
                this.len = in.readString();
                this.price = in.readString();
                this.priceUnit = in.readString();
                this.numUnit = in.readString();
                this.reservationNum = in.readString();
                this.outputNum = in.readString();
                this.effectiveTime = in.readString();
                this.orderStatus = in.readString();
                this.referenceType = in.readString();
                this.phone = in.readString();
                this.meetingUser = in.readString();
                this.name = in.readString();
                this.accountPeriod = in.readString();
                this.payType = in.readString();
                this.provinceName = in.readString();
                this.cityName = in.readString();
                this.createdAt = in.readString();
                this.meetingId = (Long) in.readValue(Long.class.getClassLoader());
                this.banner = in.readString();
                this.meetingName = in.readString();
                this.positioner = in.readString();
                this.meetingTypeList = new ArrayList<MeetingTypeListBean>();
                in.readList(this.meetingTypeList, MeetingTypeListBean.class.getClassLoader());
                this.checked = in.readByte() != 0;
            }

            public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
                @Override
                public DataBean createFromParcel(Parcel source) {
                    return new DataBean(source);
                }

                @Override
                public DataBean[] newArray(int size) {
                    return new DataBean[size];
                }
            };
        }
    }
}
