package com.company.qcy.bean.qiugou;

public class BaojiaBean {


    /**
     * id : 115
     * enquiryId : 229
     * enquiryDomain : {"id":229,"companyName":"reborn","companyName2":"商厦就","status":"3","productCli1":1,"productCli1Name":"助剂","productCli2":27,"productCli2Name":"其它","productName":"兴奋剂","pack":"5KG/桶","num":10,"numUnit":"KG","locationProvince":"北京市","locationCity":"北京市","paymentPeriod":"款到发货","paymentPeriodString":"款到发货","paymentType":"现汇","deliveryDate":1540915200000,"deliveryDateString":"2018-10-31","description":"111111111","createdAt":1539855476000,"createAtString":"2018-10-18 17:37:56","surplusDay":null,"surplusHour":null,"surplusMin":null,"surplusSec":null,"offerNum":2,"creditLevel":"4","vipLevel":"1","publishType":"个人发布","enquiryTimes":7,"enquiryOfferTimes":11,"isCharger":"0"}
     * companyDomain : {"id":926,"companyName":"朱方庆的企业账户","phone":"15601820660","provinceName":"北京市","cityName":"北京市","areaName":"东城区","address":""}
     * offerTime : 1539855552000
     * offerTimeString : 2018-10-18
     * price : 1000
     * priceUnit : 吨
     * isIncludeTrans : 1
     * phone : 13698775644
     * description : 原生企业账户
     * status : 0
     * offerNum : 2
     */

    private Long id;
    private Long enquiryId;
    private EnquiryDomainBean enquiryDomain;
    private CompanyDomainBean companyDomain;
    private Long offerTime;
    private String offerTimeString;
    private String price;
    private String priceUnit;
    private String isIncludeTrans;
    private String phone;
    private String description;
    private String status;
    private Integer offerNum;
    private String createdAtString;
    private String publishType;
    private String companyName2;
    private String isCharger;

    public String getIsCharger() {
        return isCharger;
    }

    public void setIsCharger(String isCharger) {
        this.isCharger = isCharger;
    }

    public String getCompanyName2() {
        return companyName2;
    }

    public void setCompanyName2(String companyName2) {
        this.companyName2 = companyName2;
    }

    public String getPublishType() {
        return publishType;
    }

    public void setPublishType(String publishType) {
        this.publishType = publishType;
    }

    public String getCreatedAtString() {
        return createdAtString;
    }

    public void setCreatedAtString(String createdAtString) {
        this.createdAtString = createdAtString;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEnquiryId() {
        return enquiryId;
    }

    public void setEnquiryId(Long enquiryId) {
        this.enquiryId = enquiryId;
    }

    public EnquiryDomainBean getEnquiryDomain() {
        return enquiryDomain;
    }

    public void setEnquiryDomain(EnquiryDomainBean enquiryDomain) {
        this.enquiryDomain = enquiryDomain;
    }

    public CompanyDomainBean getCompanyDomain() {
        return companyDomain;
    }

    public void setCompanyDomain(CompanyDomainBean companyDomain) {
        this.companyDomain = companyDomain;
    }

    public long getOfferTime() {
        return offerTime;
    }

    public void setOfferTime(long offerTime) {
        this.offerTime = offerTime;
    }

    public String getOfferTimeString() {
        return offerTimeString;
    }

    public void setOfferTimeString(String offerTimeString) {
        this.offerTimeString = offerTimeString;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setOfferTime(Long offerTime) {
        this.offerTime = offerTime;
    }

    public void setOfferNum(Integer offerNum) {
        this.offerNum = offerNum;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public String getIsIncludeTrans() {
        return isIncludeTrans;
    }

    public void setIsIncludeTrans(String isIncludeTrans) {
        this.isIncludeTrans = isIncludeTrans;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getOfferNum() {
        return offerNum;
    }

    public void setOfferNum(int offerNum) {
        this.offerNum = offerNum;
    }

    public static class EnquiryDomainBean {
        /**
         * id : 229
         * companyName : reborn
         * companyName2 : 商厦就
         * status : 3
         * productCli1 : 1
         * productCli1Name : 助剂
         * productCli2 : 27
         * productCli2Name : 其它
         * productName : 兴奋剂
         * pack : 5KG/桶
         * num : 10
         * numUnit : KG
         * locationProvince : 北京市
         * locationCity : 北京市
         * paymentPeriod : 款到发货
         * paymentPeriodString : 款到发货
         * paymentType : 现汇
         * deliveryDate : 1540915200000
         * deliveryDateString : 2018-10-31
         * description : 111111111
         * createdAt : 1539855476000
         * createAtString : 2018-10-18 17:37:56
         * surplusDay : null
         * surplusHour : null
         * surplusMin : null
         * surplusSec : null
         * offerNum : 2
         * creditLevel : 4
         * vipLevel : 1
         * publishType : 个人发布
         * enquiryTimes : 7
         * enquiryOfferTimes : 11
         * isCharger : 0
         */

        private Long id;
        private String companyName;
        private String companyName2;
        private String status;
        private Integer productCli1;
        private String productCli1Name;
        private Integer productCli2;
        private String productCli2Name;
        private String productName;
        private String pack;
        private Integer num;
        private String numUnit;
        private String locationProvince;
        private String locationCity;
        private String paymentPeriod;
        private String paymentPeriodString;
        private String paymentType;
        private Long deliveryDate;
        private String deliveryDateString;
        private String description;
        private Long createdAt;
        private String createAtString;
        private String surplusDay;
        private String surplusHour;
        private String surplusMin;
        private String surplusSec;
        private Integer offerNum;
        private String creditLevel;
        private String vipLevel;
        private String publishType;
        private Integer enquiryTimes;
        private Integer enquiryOfferTimes;
        private String isCharger;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setProductCli1(Integer productCli1) {
            this.productCli1 = productCli1;
        }

        public void setProductCli2(Integer productCli2) {
            this.productCli2 = productCli2;
        }

        public void setNum(Integer num) {
            this.num = num;
        }

        public void setDeliveryDate(Long deliveryDate) {
            this.deliveryDate = deliveryDate;
        }

        public void setCreatedAt(Long createdAt) {
            this.createdAt = createdAt;
        }

        public void setOfferNum(Integer offerNum) {
            this.offerNum = offerNum;
        }

        public void setEnquiryTimes(Integer enquiryTimes) {
            this.enquiryTimes = enquiryTimes;
        }

        public void setEnquiryOfferTimes(Integer enquiryOfferTimes) {
            this.enquiryOfferTimes = enquiryOfferTimes;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getCompanyName2() {
            return companyName2;
        }

        public void setCompanyName2(String companyName2) {
            this.companyName2 = companyName2;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getProductCli1() {
            return productCli1;
        }

        public void setProductCli1(int productCli1) {
            this.productCli1 = productCli1;
        }

        public String getProductCli1Name() {
            return productCli1Name;
        }

        public void setProductCli1Name(String productCli1Name) {
            this.productCli1Name = productCli1Name;
        }

        public int getProductCli2() {
            return productCli2;
        }

        public void setProductCli2(int productCli2) {
            this.productCli2 = productCli2;
        }

        public String getProductCli2Name() {
            return productCli2Name;
        }

        public void setProductCli2Name(String productCli2Name) {
            this.productCli2Name = productCli2Name;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getPack() {
            return pack;
        }

        public void setPack(String pack) {
            this.pack = pack;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getNumUnit() {
            return numUnit;
        }

        public void setNumUnit(String numUnit) {
            this.numUnit = numUnit;
        }

        public String getLocationProvince() {
            return locationProvince;
        }

        public void setLocationProvince(String locationProvince) {
            this.locationProvince = locationProvince;
        }

        public String getLocationCity() {
            return locationCity;
        }

        public void setLocationCity(String locationCity) {
            this.locationCity = locationCity;
        }

        public String getPaymentPeriod() {
            return paymentPeriod;
        }

        public void setPaymentPeriod(String paymentPeriod) {
            this.paymentPeriod = paymentPeriod;
        }

        public String getPaymentPeriodString() {
            return paymentPeriodString;
        }

        public void setPaymentPeriodString(String paymentPeriodString) {
            this.paymentPeriodString = paymentPeriodString;
        }

        public String getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(String paymentType) {
            this.paymentType = paymentType;
        }

        public long getDeliveryDate() {
            return deliveryDate;
        }

        public void setDeliveryDate(long deliveryDate) {
            this.deliveryDate = deliveryDate;
        }

        public String getDeliveryDateString() {
            return deliveryDateString;
        }

        public void setDeliveryDateString(String deliveryDateString) {
            this.deliveryDateString = deliveryDateString;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public long getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(long createdAt) {
            this.createdAt = createdAt;
        }

        public String getCreateAtString() {
            return createAtString;
        }

        public void setCreateAtString(String createAtString) {
            this.createAtString = createAtString;
        }

        public String getSurplusDay() {
            return surplusDay;
        }

        public void setSurplusDay(String surplusDay) {
            this.surplusDay = surplusDay;
        }

        public String getSurplusHour() {
            return surplusHour;
        }

        public void setSurplusHour(String surplusHour) {
            this.surplusHour = surplusHour;
        }

        public String getSurplusMin() {
            return surplusMin;
        }

        public void setSurplusMin(String surplusMin) {
            this.surplusMin = surplusMin;
        }

        public String getSurplusSec() {
            return surplusSec;
        }

        public void setSurplusSec(String surplusSec) {
            this.surplusSec = surplusSec;
        }

        public int getOfferNum() {
            return offerNum;
        }

        public void setOfferNum(int offerNum) {
            this.offerNum = offerNum;
        }

        public String getCreditLevel() {
            return creditLevel;
        }

        public void setCreditLevel(String creditLevel) {
            this.creditLevel = creditLevel;
        }

        public String getVipLevel() {
            return vipLevel;
        }

        public void setVipLevel(String vipLevel) {
            this.vipLevel = vipLevel;
        }

        public String getPublishType() {
            return publishType;
        }

        public void setPublishType(String publishType) {
            this.publishType = publishType;
        }

        public int getEnquiryTimes() {
            return enquiryTimes;
        }

        public void setEnquiryTimes(int enquiryTimes) {
            this.enquiryTimes = enquiryTimes;
        }

        public int getEnquiryOfferTimes() {
            return enquiryOfferTimes;
        }

        public void setEnquiryOfferTimes(int enquiryOfferTimes) {
            this.enquiryOfferTimes = enquiryOfferTimes;
        }

        public String getIsCharger() {
            return isCharger;
        }

        public void setIsCharger(String isCharger) {
            this.isCharger = isCharger;
        }
    }

    public static class CompanyDomainBean {
        /**
         * id : 926
         * companyName : 朱方庆的企业账户
         * phone : 15601820660
         * provinceName : 北京市
         * cityName : 北京市
         * areaName : 东城区
         * address :
         */

        private Integer id;
        private String companyName;
        private String phone;
        private String provinceName;
        private String cityName;
        private String areaName;
        private String address;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
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

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
