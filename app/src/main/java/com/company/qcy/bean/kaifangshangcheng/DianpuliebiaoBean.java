package com.company.qcy.bean.kaifangshangcheng;

import java.util.List;

public class DianpuliebiaoBean {


    /**
     * id : 11
     * companyId : null
     * company : {"id":null,"userId":null,"companyId":null,"erpNumber":null,"companyName":"朱方庆的企业账户","legal":null,"bankName":null,"bankCode":null,"institutionCode":null,"busLicenceCode":null,"taxCode":null,"description":null,"tel":"15601820660","provinceName":"北京市","province":null,"city":null,"cityName":"北京市","area":null,"areaName":"东城区","address":null,"post":null,"busLicenceUrl":null,"institutionUrl":null,"taxUrl":null,"signatureUrl":null,"contact":null,"phone":"15601820660","fax":null,"mail":null,"taxRate":null,"isManageCredit":null,"saleModleName":null,"saleModleCode":null,"county":null,"companyIndex":null,"transportLeadTime":null,"customerClass":null,"saleType":null,"departId":null,"salePeopleId":null,"salePeopleName":null,"lastTradeDate":null,"lastTradeAmount":null,"lastPaymentDate":null,"lastPaymentAmount":null,"paymentCond":null,"saleArea":null,"status":null,"isValid":null,"createdAt":null,"createdBy":null,"updatedAt":null,"updatedBy":null,"createdAtStart":null,"createdAtEnd":null,"updatedAtStart":null,"updatedAtEnd":null,"synFlag":null,"creditLevel":null,"vipLevel":null,"enquiryTimes":null,"enquiryOfferTimes":null}
     * companyName : null
     * name : 朱方庆的店铺
     * employee : null
     * logo : /market/logoImg/15313005225743X38UJ.png
     * banner1 : /archiveMain/logoImg/15151185152881F3N1K.png
     * banner2 : /market/bannerImg/1519886611113GHQ5IY.png
     * banner3 : /market/bannerImg/1522734697443MUIREU.jpg
     * banner4 :
     * banner5 :
     * isValid : null
     * isBan : null
     * isCheck : null
     * isNew : null
     * isHot : null
     * sortNum : 1
     * createdAt : null
     * updatedAt : null
     * updatedBy : null
     * creditLevel : 4
     * vipLevel : 1
     * description : 朱方庆的
     * businessList : [{"id":1,"value":"分散染料","createdAt":null,"updatedAt":null},{"id":2,"value":"活性染料","createdAt":null,"updatedAt":null},{"id":3,"value":"阳离子染料","createdAt":null,"updatedAt":null},{"id":4,"value":"硫化染料","createdAt":null,"updatedAt":null},{"id":5,"value":"酸性染料","createdAt":null,"updatedAt":null},{"id":6,"value":"还原染料","createdAt":null,"updatedAt":null},{"id":7,"value":"助剂","createdAt":null,"updatedAt":null},{"id":8,"value":"数码墨水","createdAt":null,"updatedAt":null},{"id":9,"value":"中间体","createdAt":null,"updatedAt":null},{"id":10,"value":"其他","createdAt":null,"updatedAt":null}]
     * businessIdList : null
     * createAtStart : null
     * createAtEnd : null
     * productList : []
     */

    private Long id;
    private Object companyId;
    private CompanyBean company;
    private String companyName;
    private String name;
    private Object employee;
    private String logo;
    private String banner1;
    private String banner2;
    private String banner3;
    private String banner4;
    private String banner5;
    private Object isValid;
    private Object isBan;
    private Object isCheck;
    private Object isNew;
    private Object isHot;
    private String tel;
    private Integer sortNum;
    private Object createdAt;
    private Object updatedAt;
    private Object updatedBy;
    private String creditLevel;
    private String vipLevel;
    private String description;
    private Object businessIdList;
    private Object createAtStart;
    private Object createAtEnd;
    private List<BusinessListBean> businessList;
    private List<?> productList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Object getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Object companyId) {
        this.companyId = companyId;
    }

    public CompanyBean getCompany() {
        return company;
    }

    public void setCompany(CompanyBean company) {
        this.company = company;
    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getEmployee() {
        return employee;
    }

    public void setEmployee(Object employee) {
        this.employee = employee;
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

    public Object getIsValid() {
        return isValid;
    }

    public void setIsValid(Object isValid) {
        this.isValid = isValid;
    }

    public Object getIsBan() {
        return isBan;
    }

    public void setIsBan(Object isBan) {
        this.isBan = isBan;
    }

    public Object getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Object isCheck) {
        this.isCheck = isCheck;
    }

    public Object getIsNew() {
        return isNew;
    }

    public void setIsNew(Object isNew) {
        this.isNew = isNew;
    }

    public Object getIsHot() {
        return isHot;
    }

    public void setIsHot(Object isHot) {
        this.isHot = isHot;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Object updatedBy) {
        this.updatedBy = updatedBy;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getBusinessIdList() {
        return businessIdList;
    }

    public void setBusinessIdList(Object businessIdList) {
        this.businessIdList = businessIdList;
    }

    public Object getCreateAtStart() {
        return createAtStart;
    }

    public void setCreateAtStart(Object createAtStart) {
        this.createAtStart = createAtStart;
    }

    public Object getCreateAtEnd() {
        return createAtEnd;
    }

    public void setCreateAtEnd(Object createAtEnd) {
        this.createAtEnd = createAtEnd;
    }

    public List<BusinessListBean> getBusinessList() {
        return businessList;
    }

    public void setBusinessList(List<BusinessListBean> businessList) {
        this.businessList = businessList;
    }

    public List<?> getProductList() {
        return productList;
    }

    public void setProductList(List<?> productList) {
        this.productList = productList;
    }

    public static class CompanyBean {
        /**
         * id : null
         * userId : null
         * companyId : null
         * erpNumber : null
         * companyName : 朱方庆的企业账户
         * legal : null
         * bankName : null
         * bankCode : null
         * institutionCode : null
         * busLicenceCode : null
         * taxCode : null
         * description : null
         * tel : 15601820660
         * provinceName : 北京市
         * province : null
         * city : null
         * cityName : 北京市
         * area : null
         * areaName : 东城区
         * address : null
         * post : null
         * busLicenceUrl : null
         * institutionUrl : null
         * taxUrl : null
         * signatureUrl : null
         * contact : null
         * phone : 15601820660
         * fax : null
         * mail : null
         * taxRate : null
         * isManageCredit : null
         * saleModleName : null
         * saleModleCode : null
         * county : null
         * companyIndex : null
         * transportLeadTime : null
         * customerClass : null
         * saleType : null
         * departId : null
         * salePeopleId : null
         * salePeopleName : null
         * lastTradeDate : null
         * lastTradeAmount : null
         * lastPaymentDate : null
         * lastPaymentAmount : null
         * paymentCond : null
         * saleArea : null
         * status : null
         * isValid : null
         * createdAt : null
         * createdBy : null
         * updatedAt : null
         * updatedBy : null
         * createdAtStart : null
         * createdAtEnd : null
         * updatedAtStart : null
         * updatedAtEnd : null
         * synFlag : null
         * creditLevel : null
         * vipLevel : null
         * enquiryTimes : null
         * enquiryOfferTimes : null
         */

        private Object id;
        private Object userId;
        private Object companyId;
        private Object erpNumber;
        private String companyName;
        private Object legal;
        private Object bankName;
        private Object bankCode;
        private Object institutionCode;
        private Object busLicenceCode;
        private Object taxCode;
        private Object description;
        private String tel;
        private String provinceName;
        private Object province;
        private Object city;
        private String cityName;
        private Object area;
        private String areaName;
        private Object address;
        private Object post;
        private Object busLicenceUrl;
        private Object institutionUrl;
        private Object taxUrl;
        private Object signatureUrl;
        private Object contact;
        private String phone;
        private Object fax;
        private Object mail;
        private Object taxRate;
        private Object isManageCredit;
        private Object saleModleName;
        private Object saleModleCode;
        private Object county;
        private Object companyIndex;
        private Object transportLeadTime;
        private Object customerClass;
        private Object saleType;
        private Object departId;
        private Object salePeopleId;
        private Object salePeopleName;
        private Object lastTradeDate;
        private Object lastTradeAmount;
        private Object lastPaymentDate;
        private Object lastPaymentAmount;
        private Object paymentCond;
        private Object saleArea;
        private Object status;
        private Object isValid;
        private Object createdAt;
        private Object createdBy;
        private Object updatedAt;
        private Object updatedBy;
        private Object createdAtStart;
        private Object createdAtEnd;
        private Object updatedAtStart;
        private Object updatedAtEnd;
        private Object synFlag;
        private Object creditLevel;
        private Object vipLevel;
        private Object enquiryTimes;
        private Object enquiryOfferTimes;

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public Object getUserId() {
            return userId;
        }

        public void setUserId(Object userId) {
            this.userId = userId;
        }

        public Object getCompanyId() {
            return companyId;
        }

        public void setCompanyId(Object companyId) {
            this.companyId = companyId;
        }

        public Object getErpNumber() {
            return erpNumber;
        }

        public void setErpNumber(Object erpNumber) {
            this.erpNumber = erpNumber;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public Object getLegal() {
            return legal;
        }

        public void setLegal(Object legal) {
            this.legal = legal;
        }

        public Object getBankName() {
            return bankName;
        }

        public void setBankName(Object bankName) {
            this.bankName = bankName;
        }

        public Object getBankCode() {
            return bankCode;
        }

        public void setBankCode(Object bankCode) {
            this.bankCode = bankCode;
        }

        public Object getInstitutionCode() {
            return institutionCode;
        }

        public void setInstitutionCode(Object institutionCode) {
            this.institutionCode = institutionCode;
        }

        public Object getBusLicenceCode() {
            return busLicenceCode;
        }

        public void setBusLicenceCode(Object busLicenceCode) {
            this.busLicenceCode = busLicenceCode;
        }

        public Object getTaxCode() {
            return taxCode;
        }

        public void setTaxCode(Object taxCode) {
            this.taxCode = taxCode;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
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

        public Object getProvince() {
            return province;
        }

        public void setProvince(Object province) {
            this.province = province;
        }

        public Object getCity() {
            return city;
        }

        public void setCity(Object city) {
            this.city = city;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public Object getArea() {
            return area;
        }

        public void setArea(Object area) {
            this.area = area;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public Object getPost() {
            return post;
        }

        public void setPost(Object post) {
            this.post = post;
        }

        public Object getBusLicenceUrl() {
            return busLicenceUrl;
        }

        public void setBusLicenceUrl(Object busLicenceUrl) {
            this.busLicenceUrl = busLicenceUrl;
        }

        public Object getInstitutionUrl() {
            return institutionUrl;
        }

        public void setInstitutionUrl(Object institutionUrl) {
            this.institutionUrl = institutionUrl;
        }

        public Object getTaxUrl() {
            return taxUrl;
        }

        public void setTaxUrl(Object taxUrl) {
            this.taxUrl = taxUrl;
        }

        public Object getSignatureUrl() {
            return signatureUrl;
        }

        public void setSignatureUrl(Object signatureUrl) {
            this.signatureUrl = signatureUrl;
        }

        public Object getContact() {
            return contact;
        }

        public void setContact(Object contact) {
            this.contact = contact;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Object getFax() {
            return fax;
        }

        public void setFax(Object fax) {
            this.fax = fax;
        }

        public Object getMail() {
            return mail;
        }

        public void setMail(Object mail) {
            this.mail = mail;
        }

        public Object getTaxRate() {
            return taxRate;
        }

        public void setTaxRate(Object taxRate) {
            this.taxRate = taxRate;
        }

        public Object getIsManageCredit() {
            return isManageCredit;
        }

        public void setIsManageCredit(Object isManageCredit) {
            this.isManageCredit = isManageCredit;
        }

        public Object getSaleModleName() {
            return saleModleName;
        }

        public void setSaleModleName(Object saleModleName) {
            this.saleModleName = saleModleName;
        }

        public Object getSaleModleCode() {
            return saleModleCode;
        }

        public void setSaleModleCode(Object saleModleCode) {
            this.saleModleCode = saleModleCode;
        }

        public Object getCounty() {
            return county;
        }

        public void setCounty(Object county) {
            this.county = county;
        }

        public Object getCompanyIndex() {
            return companyIndex;
        }

        public void setCompanyIndex(Object companyIndex) {
            this.companyIndex = companyIndex;
        }

        public Object getTransportLeadTime() {
            return transportLeadTime;
        }

        public void setTransportLeadTime(Object transportLeadTime) {
            this.transportLeadTime = transportLeadTime;
        }

        public Object getCustomerClass() {
            return customerClass;
        }

        public void setCustomerClass(Object customerClass) {
            this.customerClass = customerClass;
        }

        public Object getSaleType() {
            return saleType;
        }

        public void setSaleType(Object saleType) {
            this.saleType = saleType;
        }

        public Object getDepartId() {
            return departId;
        }

        public void setDepartId(Object departId) {
            this.departId = departId;
        }

        public Object getSalePeopleId() {
            return salePeopleId;
        }

        public void setSalePeopleId(Object salePeopleId) {
            this.salePeopleId = salePeopleId;
        }

        public Object getSalePeopleName() {
            return salePeopleName;
        }

        public void setSalePeopleName(Object salePeopleName) {
            this.salePeopleName = salePeopleName;
        }

        public Object getLastTradeDate() {
            return lastTradeDate;
        }

        public void setLastTradeDate(Object lastTradeDate) {
            this.lastTradeDate = lastTradeDate;
        }

        public Object getLastTradeAmount() {
            return lastTradeAmount;
        }

        public void setLastTradeAmount(Object lastTradeAmount) {
            this.lastTradeAmount = lastTradeAmount;
        }

        public Object getLastPaymentDate() {
            return lastPaymentDate;
        }

        public void setLastPaymentDate(Object lastPaymentDate) {
            this.lastPaymentDate = lastPaymentDate;
        }

        public Object getLastPaymentAmount() {
            return lastPaymentAmount;
        }

        public void setLastPaymentAmount(Object lastPaymentAmount) {
            this.lastPaymentAmount = lastPaymentAmount;
        }

        public Object getPaymentCond() {
            return paymentCond;
        }

        public void setPaymentCond(Object paymentCond) {
            this.paymentCond = paymentCond;
        }

        public Object getSaleArea() {
            return saleArea;
        }

        public void setSaleArea(Object saleArea) {
            this.saleArea = saleArea;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public Object getIsValid() {
            return isValid;
        }

        public void setIsValid(Object isValid) {
            this.isValid = isValid;
        }

        public Object getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Object createdAt) {
            this.createdAt = createdAt;
        }

        public Object getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Object createdBy) {
            this.createdBy = createdBy;
        }

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Object getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(Object updatedBy) {
            this.updatedBy = updatedBy;
        }

        public Object getCreatedAtStart() {
            return createdAtStart;
        }

        public void setCreatedAtStart(Object createdAtStart) {
            this.createdAtStart = createdAtStart;
        }

        public Object getCreatedAtEnd() {
            return createdAtEnd;
        }

        public void setCreatedAtEnd(Object createdAtEnd) {
            this.createdAtEnd = createdAtEnd;
        }

        public Object getUpdatedAtStart() {
            return updatedAtStart;
        }

        public void setUpdatedAtStart(Object updatedAtStart) {
            this.updatedAtStart = updatedAtStart;
        }

        public Object getUpdatedAtEnd() {
            return updatedAtEnd;
        }

        public void setUpdatedAtEnd(Object updatedAtEnd) {
            this.updatedAtEnd = updatedAtEnd;
        }

        public Object getSynFlag() {
            return synFlag;
        }

        public void setSynFlag(Object synFlag) {
            this.synFlag = synFlag;
        }

        public Object getCreditLevel() {
            return creditLevel;
        }

        public void setCreditLevel(Object creditLevel) {
            this.creditLevel = creditLevel;
        }

        public Object getVipLevel() {
            return vipLevel;
        }

        public void setVipLevel(Object vipLevel) {
            this.vipLevel = vipLevel;
        }

        public Object getEnquiryTimes() {
            return enquiryTimes;
        }

        public void setEnquiryTimes(Object enquiryTimes) {
            this.enquiryTimes = enquiryTimes;
        }

        public Object getEnquiryOfferTimes() {
            return enquiryOfferTimes;
        }

        public void setEnquiryOfferTimes(Object enquiryOfferTimes) {
            this.enquiryOfferTimes = enquiryOfferTimes;
        }
    }

    public static class BusinessListBean {
        /**
         * id : 1
         * value : 分散染料
         * createdAt : null
         * updatedAt : null
         */

        private int id;
        private String value;
        private Object createdAt;
        private Object updatedAt;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Object getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Object createdAt) {
            this.createdAt = createdAt;
        }

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updatedAt = updatedAt;
        }
    }
}
