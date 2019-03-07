package com.company.qcy.huodong.jingpai.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class JingpaiDetailBean implements Parcelable {


    /**
     * id : 13
     * price : 1.00
     * addPrice : 1.00
     * priceUnit : 元
     * numUnit : 吨
     * shopName : 天丝棉/ 织物密度 6 /  幅宽 20 / 纱织 1/  白色  5吨 针织
     * isType : 3
     * province : null
     * city : null
     * address : 上海
     * startTime : 2019-01-07 17:30:00
     * endTime : 2019-01-17 17:30:00
     * maxPrice : 18.00
     * overTime : 1547717400000
     * freight : 自费
     * commission : 1
     * status : 1
     * isValid : null
     * createdAt : 2019-07-07 05:33:31
     * updatedAt : 2020-01-28 03:44:21
     * sortNum : 2
     * productPic : /groupBuy/15468535889975XA96E.png
     * detailPcPic : /groupBuy/1546844845360NUEG84.png
     * count : 16
     * attributeList : [{"id":74,"auctionId":"auction_13","shuXing":"产品名称","zhi":"分散染料 分散红玉s一5BL(R.167#)100%","isValid":null},{"id":75,"auctionId":"auction_13","shuXing":"颜色","zhi":"黑色、白色，蓝色，黑色","isValid":null},{"id":76,"auctionId":"auction_13","shuXing":"长度","zhi":"10，100，1000米","isValid":null}]
     * instructionsList : [{"id":41,"auctionId":"auction_13","relatedInstructions":"颜色"}]
     * buyerList : null
     * belowPrice : 0
     * auctionDetails : null
     * auctionDetails1 : null
     * detailsValue : null
     * detailsValue1 : null
     * manufacturer : ad撒大大大大1
     * dateOfProduction : 2019-01-07
     * auctionAttaches : [{"id":"18","auctionId":"auction_13","attachName":"host配置.txt","attachUrl":"/groupBuy/15486611392061NBT93.txt","attachSize":null,"videoName":null,"videoUrl":null,"detailPcPic":null,"is_type":"2"},{"id":"19","auctionId":"auction_13","attachName":"tableExport.xls","attachUrl":"/groupBuy/1548661148185YEYP53.xls","attachSize":null,"videoName":null,"videoUrl":null,"detailPcPic":null,"is_type":"2"},{"id":"20","auctionId":"auction_13","attachName":"平台问题跟踪2018.pdf","attachUrl":"/groupBuy/1548661459901C1P336.pdf","attachSize":null,"videoName":null,"videoUrl":null,"detailPcPic":null,"is_type":"2"}]
     * videoList : [{"id":"21","auctionId":"auction_13","attachName":null,"attachUrl":null,"attachSize":null,"videoName":"8a8aecd7ce356c75ece34727c4f7477d.mp4","videoUrl":"/groupBuy/1548661129940Y85G2L.mp4","detailPcPic":null,"is_type":"3"}]
     * detailList : null
     */

    private Long id;
    private String price;
    private String addPrice;
    private String priceUnit;
    private String numUnit;
    private String shopName;
    private String isType;
    private String address;
    private String startTime;
    private String endTime;
    private String maxPrice;
    private String overTime;
    private String freight;
    private String commission;
    private String status;
    private String createdAt;
    private String updatedAt;
    private String sortNum;
    private String productPic;
    private String detailPcPic;
    private String count;
    private String belowPrice;
    private String auctionDetails;
    private String auctionDetails1;
    private String detailsValue;
    private String detailsValue1;
    private String manufacturer;
    private String dateOfProduction;
    private List<AttributeListBean> attributeList;
    private List<InstructionsListBean> instructionsList;
    private List<AuctionAttachesBean> auctionAttaches;
    private List<VideoListBean> videoList;
    private List<DetailListBean> detailList;

    public List<DetailListBean> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<DetailListBean> detailList) {
        this.detailList = detailList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddPrice() {
        return addPrice;
    }

    public void setAddPrice(String addPrice) {
        this.addPrice = addPrice;
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

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getIsType() {
        return isType;
    }

    public void setIsType(String isType) {
        this.isType = isType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getOverTime() {
        return overTime;
    }

    public void setOverTime(String overTime) {
        this.overTime = overTime;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getSortNum() {
        return sortNum;
    }

    public void setSortNum(String sortNum) {
        this.sortNum = sortNum;
    }

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    public String getDetailPcPic() {
        return detailPcPic;
    }

    public void setDetailPcPic(String detailPcPic) {
        this.detailPcPic = detailPcPic;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getBelowPrice() {
        return belowPrice;
    }

    public void setBelowPrice(String belowPrice) {
        this.belowPrice = belowPrice;
    }

    public String getAuctionDetails() {
        return auctionDetails;
    }

    public void setAuctionDetails(String auctionDetails) {
        this.auctionDetails = auctionDetails;
    }

    public String getAuctionDetails1() {
        return auctionDetails1;
    }

    public void setAuctionDetails1(String auctionDetails1) {
        this.auctionDetails1 = auctionDetails1;
    }

    public String getDetailsValue() {
        return detailsValue;
    }

    public void setDetailsValue(String detailsValue) {
        this.detailsValue = detailsValue;
    }

    public String getDetailsValue1() {
        return detailsValue1;
    }

    public void setDetailsValue1(String detailsValue1) {
        this.detailsValue1 = detailsValue1;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDateOfProduction() {
        return dateOfProduction;
    }

    public void setDateOfProduction(String dateOfProduction) {
        this.dateOfProduction = dateOfProduction;
    }

    public List<AttributeListBean> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<AttributeListBean> attributeList) {
        this.attributeList = attributeList;
    }

    public List<InstructionsListBean> getInstructionsList() {
        return instructionsList;
    }

    public void setInstructionsList(List<InstructionsListBean> instructionsList) {
        this.instructionsList = instructionsList;
    }

    public List<AuctionAttachesBean> getAuctionAttaches() {
        return auctionAttaches;
    }

    public void setAuctionAttaches(List<AuctionAttachesBean> auctionAttaches) {
        this.auctionAttaches = auctionAttaches;
    }

    public List<VideoListBean> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<VideoListBean> videoList) {
        this.videoList = videoList;
    }

    public static class AttributeListBean implements Parcelable {
        /**
         * id : 74
         * auctionId : auction_13
         * shuXing : 产品名称
         * zhi : 分散染料 分散红玉s一5BL(R.167#)100%
         * isValid : null
         */

        private Long id;
        private String auctionId;
        private String shuXing;
        private String zhi;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getAuctionId() {
            return auctionId;
        }

        public void setAuctionId(String auctionId) {
            this.auctionId = auctionId;
        }

        public String getShuXing() {
            return shuXing;
        }

        public void setShuXing(String shuXing) {
            this.shuXing = shuXing;
        }

        public String getZhi() {
            return zhi;
        }

        public void setZhi(String zhi) {
            this.zhi = zhi;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(this.id);
            dest.writeString(this.auctionId);
            dest.writeString(this.shuXing);
            dest.writeString(this.zhi);
        }

        public AttributeListBean() {
        }

        protected AttributeListBean(Parcel in) {
            this.id = (Long) in.readValue(Long.class.getClassLoader());
            this.auctionId = in.readString();
            this.shuXing = in.readString();
            this.zhi = in.readString();
        }

        public static final Creator<AttributeListBean> CREATOR = new Creator<AttributeListBean>() {
            @Override
            public AttributeListBean createFromParcel(Parcel source) {
                return new AttributeListBean(source);
            }

            @Override
            public AttributeListBean[] newArray(int size) {
                return new AttributeListBean[size];
            }
        };
    }

    public static class InstructionsListBean implements Parcelable {
        /**
         * id : 41
         * auctionId : auction_13
         * relatedInstructions : 颜色
         */

         private Long id;
        private String auctionId;
        private String relatedInstructions;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getAuctionId() {
            return auctionId;
        }

        public void setAuctionId(String auctionId) {
            this.auctionId = auctionId;
        }

        public String getRelatedInstructions() {
            return relatedInstructions;
        }

        public void setRelatedInstructions(String relatedInstructions) {
            this.relatedInstructions = relatedInstructions;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(this.id);
            dest.writeString(this.auctionId);
            dest.writeString(this.relatedInstructions);
        }

        public InstructionsListBean() {
        }

        protected InstructionsListBean(Parcel in) {
            this.id = (Long) in.readValue(Long.class.getClassLoader());
            this.auctionId = in.readString();
            this.relatedInstructions = in.readString();
        }

        public static final Creator<InstructionsListBean> CREATOR = new Creator<InstructionsListBean>() {
            @Override
            public InstructionsListBean createFromParcel(Parcel source) {
                return new InstructionsListBean(source);
            }

            @Override
            public InstructionsListBean[] newArray(int size) {
                return new InstructionsListBean[size];
            }
        };
    }

    public static class AuctionAttachesBean implements Parcelable {
        /**
         * id : 18
         * auctionId : auction_13
         * attachName : host配置.txt
         * attachUrl : /groupBuy/15486611392061NBT93.txt
         * attachSize : null
         * videoName : null
         * videoUrl : null
         * detailPcPic : null
         * is_type : 2
         */


        private String id;
        private String auctionId;
        private String attachName;
        private String attachUrl;
        private String attachSize;
        private String videoName;
        private String videoUrl;
        private String detailPcPic;
        private String is_type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAuctionId() {
            return auctionId;
        }

        public void setAuctionId(String auctionId) {
            this.auctionId = auctionId;
        }

        public String getAttachName() {
            return attachName;
        }

        public void setAttachName(String attachName) {
            this.attachName = attachName;
        }

        public String getAttachUrl() {
            return attachUrl;
        }

        public void setAttachUrl(String attachUrl) {
            this.attachUrl = attachUrl;
        }

        public String getAttachSize() {
            return attachSize;
        }

        public void setAttachSize(String attachSize) {
            this.attachSize = attachSize;
        }

        public String getVideoName() {
            return videoName;
        }

        public void setVideoName(String videoName) {
            this.videoName = videoName;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public String getDetailPcPic() {
            return detailPcPic;
        }

        public void setDetailPcPic(String detailPcPic) {
            this.detailPcPic = detailPcPic;
        }

        public String getIs_type() {
            return is_type;
        }

        public void setIs_type(String is_type) {
            this.is_type = is_type;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.auctionId);
            dest.writeString(this.attachName);
            dest.writeString(this.attachUrl);
            dest.writeString(this.attachSize);
            dest.writeString(this.videoName);
            dest.writeString(this.videoUrl);
            dest.writeString(this.detailPcPic);
            dest.writeString(this.is_type);
        }

        public AuctionAttachesBean() {
        }

        protected AuctionAttachesBean(Parcel in) {
            this.id = in.readString();
            this.auctionId = in.readString();
            this.attachName = in.readString();
            this.attachUrl = in.readString();
            this.attachSize = in.readString();
            this.videoName = in.readString();
            this.videoUrl = in.readString();
            this.detailPcPic = in.readString();
            this.is_type = in.readString();
        }

        public static final Creator<AuctionAttachesBean> CREATOR = new Creator<AuctionAttachesBean>() {
            @Override
            public AuctionAttachesBean createFromParcel(Parcel source) {
                return new AuctionAttachesBean(source);
            }

            @Override
            public AuctionAttachesBean[] newArray(int size) {
                return new AuctionAttachesBean[size];
            }
        };
    }



    public static class DetailListBean implements Parcelable {

        /**
         * id : 56
         * auctionId : auction_1
         * attachName : null
         * attachUrl : null
         * attachSize : null
         * videoName : null
         * videoUrl : null
         * detailPcPic : /groupBuy/15519427140052RQMUX.jpg
         * is_type : 1
         */

        private String id;
        private String auctionId;
        private String attachName;
        private String attachUrl;
        private String attachSize;
        private String videoName;
        private String videoUrl;
        private String detailPcPic;
        private String is_type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAuctionId() {
            return auctionId;
        }

        public void setAuctionId(String auctionId) {
            this.auctionId = auctionId;
        }

        public String getAttachName() {
            return attachName;
        }

        public void setAttachName(String attachName) {
            this.attachName = attachName;
        }

        public String getAttachUrl() {
            return attachUrl;
        }

        public void setAttachUrl(String attachUrl) {
            this.attachUrl = attachUrl;
        }

        public String getAttachSize() {
            return attachSize;
        }

        public void setAttachSize(String attachSize) {
            this.attachSize = attachSize;
        }

        public String getVideoName() {
            return videoName;
        }

        public void setVideoName(String videoName) {
            this.videoName = videoName;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public String getDetailPcPic() {
            return detailPcPic;
        }

        public void setDetailPcPic(String detailPcPic) {
            this.detailPcPic = detailPcPic;
        }

        public String getIs_type() {
            return is_type;
        }

        public void setIs_type(String is_type) {
            this.is_type = is_type;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.auctionId);
            dest.writeString(this.attachName);
            dest.writeString(this.attachUrl);
            dest.writeString(this.attachSize);
            dest.writeString(this.videoName);
            dest.writeString(this.videoUrl);
            dest.writeString(this.detailPcPic);
            dest.writeString(this.is_type);
        }

        public DetailListBean() {
        }

        protected DetailListBean(Parcel in) {
            this.id = in.readString();
            this.auctionId = in.readString();
            this.attachName = in.readString();
            this.attachUrl = in.readString();
            this.attachSize = in.readString();
            this.videoName = in.readString();
            this.videoUrl = in.readString();
            this.detailPcPic = in.readString();
            this.is_type = in.readString();
        }

        public static final Creator<DetailListBean> CREATOR = new Creator<DetailListBean>() {
            @Override
            public DetailListBean createFromParcel(Parcel source) {
                return new DetailListBean(source);
            }

            @Override
            public DetailListBean[] newArray(int size) {
                return new DetailListBean[size];
            }
        };
    }




    public static class VideoListBean implements Parcelable {
        /**
         * id : 21
         * auctionId : auction_13
         * attachName : null
         * attachUrl : null
         * attachSize : null
         * videoName : 8a8aecd7ce356c75ece34727c4f7477d.mp4
         * videoUrl : /groupBuy/1548661129940Y85G2L.mp4
         * detailPcPic : null
         * is_type : 3
         */

        private String Long;
        private String auctionId;
        private String attachName;
        private String attachUrl;
        private String attachSize;
        private String videoName;
        private String videoUrl;
        private String detailPcPic;
        private String is_type;

        public String getLong() {
            return Long;
        }

        public void setLong(String aLong) {
            Long = aLong;
        }

        public String getAuctionId() {
            return auctionId;
        }

        public void setAuctionId(String auctionId) {
            this.auctionId = auctionId;
        }

        public String getAttachName() {
            return attachName;
        }

        public void setAttachName(String attachName) {
            this.attachName = attachName;
        }

        public String getAttachUrl() {
            return attachUrl;
        }

        public void setAttachUrl(String attachUrl) {
            this.attachUrl = attachUrl;
        }

        public String getAttachSize() {
            return attachSize;
        }

        public void setAttachSize(String attachSize) {
            this.attachSize = attachSize;
        }

        public String getVideoName() {
            return videoName;
        }

        public void setVideoName(String videoName) {
            this.videoName = videoName;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public String getDetailPcPic() {
            return detailPcPic;
        }

        public void setDetailPcPic(String detailPcPic) {
            this.detailPcPic = detailPcPic;
        }

        public String getIs_type() {
            return is_type;
        }

        public void setIs_type(String is_type) {
            this.is_type = is_type;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.Long);
            dest.writeString(this.auctionId);
            dest.writeString(this.attachName);
            dest.writeString(this.attachUrl);
            dest.writeString(this.attachSize);
            dest.writeString(this.videoName);
            dest.writeString(this.videoUrl);
            dest.writeString(this.detailPcPic);
            dest.writeString(this.is_type);
        }

        public VideoListBean() {
        }

        protected VideoListBean(Parcel in) {
            this.Long = in.readString();
            this.auctionId = in.readString();
            this.attachName = in.readString();
            this.attachUrl = in.readString();
            this.attachSize = in.readString();
            this.videoName = in.readString();
            this.videoUrl = in.readString();
            this.detailPcPic = in.readString();
            this.is_type = in.readString();
        }

        public static final Creator<VideoListBean> CREATOR = new Creator<VideoListBean>() {
            @Override
            public VideoListBean createFromParcel(Parcel source) {
                return new VideoListBean(source);
            }

            @Override
            public VideoListBean[] newArray(int size) {
                return new VideoListBean[size];
            }
        };
    }

    public JingpaiDetailBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.price);
        dest.writeString(this.addPrice);
        dest.writeString(this.priceUnit);
        dest.writeString(this.numUnit);
        dest.writeString(this.shopName);
        dest.writeString(this.isType);
        dest.writeString(this.address);
        dest.writeString(this.startTime);
        dest.writeString(this.endTime);
        dest.writeString(this.maxPrice);
        dest.writeString(this.overTime);
        dest.writeString(this.freight);
        dest.writeString(this.commission);
        dest.writeString(this.status);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeString(this.sortNum);
        dest.writeString(this.productPic);
        dest.writeString(this.detailPcPic);
        dest.writeString(this.count);
        dest.writeString(this.belowPrice);
        dest.writeString(this.auctionDetails);
        dest.writeString(this.auctionDetails1);
        dest.writeString(this.detailsValue);
        dest.writeString(this.detailsValue1);
        dest.writeString(this.manufacturer);
        dest.writeString(this.dateOfProduction);
        dest.writeTypedList(this.attributeList);
        dest.writeTypedList(this.instructionsList);
        dest.writeTypedList(this.auctionAttaches);
        dest.writeTypedList(this.videoList);
        dest.writeList(this.detailList);
    }

    protected JingpaiDetailBean(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.price = in.readString();
        this.addPrice = in.readString();
        this.priceUnit = in.readString();
        this.numUnit = in.readString();
        this.shopName = in.readString();
        this.isType = in.readString();
        this.address = in.readString();
        this.startTime = in.readString();
        this.endTime = in.readString();
        this.maxPrice = in.readString();
        this.overTime = in.readString();
        this.freight = in.readString();
        this.commission = in.readString();
        this.status = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.sortNum = in.readString();
        this.productPic = in.readString();
        this.detailPcPic = in.readString();
        this.count = in.readString();
        this.belowPrice = in.readString();
        this.auctionDetails = in.readString();
        this.auctionDetails1 = in.readString();
        this.detailsValue = in.readString();
        this.detailsValue1 = in.readString();
        this.manufacturer = in.readString();
        this.dateOfProduction = in.readString();
        this.attributeList = in.createTypedArrayList(AttributeListBean.CREATOR);
        this.instructionsList = in.createTypedArrayList(InstructionsListBean.CREATOR);
        this.auctionAttaches = in.createTypedArrayList(AuctionAttachesBean.CREATOR);
        this.videoList = in.createTypedArrayList(VideoListBean.CREATOR);
        this.detailList = new ArrayList<DetailListBean>();
        in.readList(this.detailList, DetailListBean.class.getClassLoader());
    }

    public static final Creator<JingpaiDetailBean> CREATOR = new Creator<JingpaiDetailBean>() {
        @Override
        public JingpaiDetailBean createFromParcel(Parcel source) {
            return new JingpaiDetailBean(source);
        }

        @Override
        public JingpaiDetailBean[] newArray(int size) {
            return new JingpaiDetailBean[size];
        }
    };
}
