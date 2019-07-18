package com.company.qcy.bean.kaifangshangcheng;

import java.util.List;

public class DianpuxiangqingBean {


    /**
     * id : 1
     * companyName : sxy企业
     * company : {"id":null,"userId":null,"companyId":null,"erpNumber":null,"companyName":"sxy企业","legal":null,"bankName":null,"bankCode":null,"institutionCode":null,"busLicenceCode":null,"taxCode":null,"description":null,"tel":"17329431696","provinceName":"上海市","province":null,"city":null,"cityName":"上海市","area":null,"areaName":"徐汇区","address":"天津市河西区友谊路街道天津宾馆(地铁站)天津市人民政府","post":null,"busLicenceUrl":null,"institutionUrl":null,"taxUrl":null,"signatureUrl":null,"contact":null,"phone":"15601820660","fax":null,"mail":null,"taxRate":null,"isManageCredit":null,"saleModleName":null,"saleModleCode":null,"county":null,"companyIndex":null,"transportLeadTime":null,"customerClass":null,"saleType":null,"departId":null,"salePeopleId":null,"salePeopleName":null,"lastTradeDate":null,"lastTradeAmount":null,"lastPaymentDate":null,"lastPaymentAmount":null,"paymentCond":null,"companyCode":null,"saleArea":null,"status":null,"isValid":null,"createdAt":null,"createdBy":null,"updatedAt":null,"updatedBy":null,"createdAtStart":null,"createdAtEnd":null,"updatedAtStart":null,"updatedAtEnd":null,"synFlag":null,"creditLevel":null,"vipLevel":null,"enquiryTimes":null,"enquiryOfferTimes":null,"lng":"121.400071","lat":"31.176589"}
     * businessList : [{"id":1,"value":"分散染料","topId":null,"createdAt":null,"updatedAt":null},{"id":3,"value":"阳离子染料","topId":null,"createdAt":null,"updatedAt":null},{"id":5,"value":"酸性染料","topId":null,"createdAt":null,"updatedAt":null}]
     * name : null
     * logo : /market/logoImg/1558930980737AEILQS.png
     * banner1 : /market/bannerImg/15589309920182GBV9G.jpg
     * banner2 :
     * banner3 :
     * banner4 :
     * banner5 :
     * creditLevel : 4
     * vipLevel : 1
     * provinceName : 上海市
     * cityName : 上海市
     * description : <p> <strong><em>史晓昱店铺  </em></strong>始于1889年道光年间，拥有200年的历史</p><p><img src="http://static1.i7colors.com/market/productImg/1558939749281V4FRKM.jpg"></p>
     * contact : 朱方庆
     * phone : 15601820660
     * busInformation : null
     * productList : null
     * hotNums : 5725
     * addNum : 6580
     * jobNum : 470
     */

    private Long id;
    private String companyName;
    private CompanyBean company;
    private String name;
    private String logo;
    private String banner1;
    private String banner2;
    private String banner3;
    private String banner4;
    private String banner5;
    private String creditLevel;
    private String vipLevel;
    private String provinceName;
    private String cityName;
    private String description;
    private String contact;
    private String phone;
    private String busInformation;
    private Object productList;
    private String hotNums;
    private String addNum;
    private String jobNum;
    private List<BusinessListBean> businessList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public CompanyBean getCompany() {
        return company;
    }

    public void setCompany(CompanyBean company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBanner1() {
        return banner1;
    }

    public void setBanner1(String banner1) {
        this.banner1 = banner1;
    }

    public String getBanner2() {
        return banner2;
    }

    public void setBanner2(String banner2) {
        this.banner2 = banner2;
    }

    public String getBanner3() {
        return banner3;
    }

    public void setBanner3(String banner3) {
        this.banner3 = banner3;
    }

    public String getBanner4() {
        return banner4;
    }

    public void setBanner4(String banner4) {
        this.banner4 = banner4;
    }

    public String getBanner5() {
        return banner5;
    }

    public void setBanner5(String banner5) {
        this.banner5 = banner5;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBusInformation() {
        return busInformation;
    }

    public void setBusInformation(String busInformation) {
        this.busInformation = busInformation;
    }

    public Object getProductList() {
        return productList;
    }

    public void setProductList(Object productList) {
        this.productList = productList;
    }

    public String getHotNums() {
        return hotNums;
    }

    public void setHotNums(String hotNums) {
        this.hotNums = hotNums;
    }

    public String getAddNum() {
        return addNum;
    }

    public void setAddNum(String addNum) {
        this.addNum = addNum;
    }

    public String getJobNum() {
        return jobNum;
    }

    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }

    public List<BusinessListBean> getBusinessList() {
        return businessList;
    }

    public void setBusinessList(List<BusinessListBean> businessList) {
        this.businessList = businessList;
    }

    public static class CompanyBean {


        private Long id;
        private String companyName;
        private String tel;
        private String provinceName;
        private String cityName;
        private String areaName;
        private String address;
        private String phone;
        private String lng;
        private String lat;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }
    }

    public static class BusinessListBean {
        /**
         * id : 1
         * value : 分散染料
         * topId : null
         * createdAt : null
         * updatedAt : null
         */

        private Long id;
        private String value;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}



