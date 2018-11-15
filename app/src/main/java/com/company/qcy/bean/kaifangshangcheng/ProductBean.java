package com.company.qcy.bean.kaifangshangcheng;

import java.util.List;
import java.util.Map;

public class ProductBean {


    /**
     * id : 47m
     * pic : /market/productImg/1522376281592L3VTA1.jpg
     * supplierShotName : null
     * productName : ZFQ分散黄
     * tagList : ["染料","分散染料"]
     * displayPrice : true
     * price : 12
     * phone : null
     */

    private String id;
    private String pic;
    private String supplierShotName;
    private String productName;
    private boolean displayPrice;
    private String price;
    private String phone;
    private List<String> tagList;
    private String pack;
    private List<String> detailPicList;
    private List<PropMapBean> propMap;
    private Long shopId;
    private String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public List<PropMapBean> getPropMap() {
        return propMap;
    }

    public void setPropMap(List<PropMapBean> propMap) {
        this.propMap = propMap;
    }

    public List<String> getDetailPicList() {
        return detailPicList;
    }

    public void setDetailPicList(List<String> detailPicList) {
        this.detailPicList = detailPicList;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getSupplierShotName() {
        return supplierShotName;
    }

    public void setSupplierShotName(String supplierShotName) {
        this.supplierShotName = supplierShotName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public boolean isDisplayPrice() {
        return displayPrice;
    }

    public void setDisplayPrice(boolean displayPrice) {
        this.displayPrice = displayPrice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }



    public static class PropMapBean {
        /**
         * key : 产品2的属性1
         * value : 产品2 属性值
         */

        private String key;
        private String value;

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
    }
}
