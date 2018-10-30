package com.company.qcy.bean.kaifangshangcheng;

import java.util.List;

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
    private Object supplierShotName;
    private String productName;
    private boolean displayPrice;
    private String price;
    private String phone;
    private List<String> tagList;

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

    public Object getSupplierShotName() {
        return supplierShotName;
    }

    public void setSupplierShotName(Object supplierShotName) {
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
}
