package com.company.qcy.bean.kaifangshangcheng;

import java.util.List;

public class DianpuliebiaoBean {



    private Long id;
    private CompanyBean company;
    private String companyName;
    private String name;
    private String logo;
    private String banner1;
    private String banner2;
    private String banner3;
    private String banner4;
    private String banner5;
    private String phone;
    private String creditLevel;
    private String vipLevel;
    private String description;
    private List<BusinessListBean> businessList;
    private List<?> productList;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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


        private Long id;
        private String companyName;
        private String tel;
        private String provinceName;
        private String cityName;
        private String areaName;
        private String phone;

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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

    public static class BusinessListBean {
        /**
         * id : 1
         * value : 分散染料
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
