package com.company.qcy.bean;

public class BannerBean {


    /**
     * id : null
     * is_valid : null
     * created_at : null
     * created_by : null
     * updated_at : null
     * updated_by : null
     * offset : null
     * limit : null
     * plate_code : XCX_Index_Banner
     * ad_name : 小程序首页banner1
     * ad_url : null
     * ad_image : /ad/1521013857170CEH9XK.png
     * ad_num : 1
     * ad_class : null
     */

    private Long id;
    private String plate_code;
    private String ad_name;
    private String ad_url;
    private String ad_image;
    private String ad_num;

    public String getAd_num() {
        return ad_num;
    }

    public void setAd_num(String ad_num) {
        this.ad_num = ad_num;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getPlate_code() {
        return plate_code;
    }

    public void setPlate_code(String plate_code) {
        this.plate_code = plate_code;
    }

    public String getAd_name() {
        return ad_name;
    }

    public void setAd_name(String ad_name) {
        this.ad_name = ad_name;
    }

    public String getAd_url() {
        return ad_url;
    }

    public void setAd_url(String ad_url) {
        this.ad_url = ad_url;
    }

    public String getAd_image() {
        return ad_image;
    }

    public void setAd_image(String ad_image) {
        this.ad_image = ad_image;
    }

}
