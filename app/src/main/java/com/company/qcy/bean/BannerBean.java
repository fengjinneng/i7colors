package com.company.qcy.bean;


import android.os.Parcel;
import android.os.Parcelable;

public class BannerBean implements Parcelable {



    /**
     * id : null
     * is_valid : null
     * created_at : null
     * created_by : null
     * updated_at : null
     * updated_by : null
     * offset : null
     * limit : null
     * plate_code : APP_Start_Pic
     * ad_name : 01
     * ad_url : https://www.baidu.com
     * is_show : null
     * ad_image : /ad/1564123175875NV5WJ1.png
     * ad_num : 1
     * ad_class : null
     * type : html
     * directType : enquiry
     * directTypeId : 48
     */

    private Long id;
    private String plate_code;
    private String ad_name;
    private String ad_url;
    private String is_show;
    private String ad_image;
    private Integer ad_num;
    private String type;
    private String directType;
    private String directTypeId;
    private String ad_desc;

    public String getAd_desc() {
        return ad_desc;
    }

    public void setAd_desc(String ad_desc) {
        this.ad_desc = ad_desc;
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

    public String getIs_show() {
        return is_show;
    }

    public void setIs_show(String is_show) {
        this.is_show = is_show;
    }

    public String getAd_image() {
        return ad_image;
    }

    public void setAd_image(String ad_image) {
        this.ad_image = ad_image;
    }

    public Integer getAd_num() {
        return ad_num;
    }

    public void setAd_num(Integer ad_num) {
        this.ad_num = ad_num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDirectType() {
        return directType;
    }

    public void setDirectType(String directType) {
        this.directType = directType;
    }

    public String getDirectTypeId() {
        return directTypeId;
    }

    public void setDirectTypeId(String directTypeId) {
        this.directTypeId = directTypeId;
    }

    public BannerBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.plate_code);
        dest.writeString(this.ad_name);
        dest.writeString(this.ad_url);
        dest.writeString(this.is_show);
        dest.writeString(this.ad_image);
        dest.writeValue(this.ad_num);
        dest.writeString(this.type);
        dest.writeString(this.directType);
        dest.writeString(this.directTypeId);
        dest.writeString(this.ad_desc);
    }

    protected BannerBean(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.plate_code = in.readString();
        this.ad_name = in.readString();
        this.ad_url = in.readString();
        this.is_show = in.readString();
        this.ad_image = in.readString();
        this.ad_num = (Integer) in.readValue(Integer.class.getClassLoader());
        this.type = in.readString();
        this.directType = in.readString();
        this.directTypeId = in.readString();
        this.ad_desc = in.readString();
    }

    public static final Creator<BannerBean> CREATOR = new Creator<BannerBean>() {
        @Override
        public BannerBean createFromParcel(Parcel source) {
            return new BannerBean(source);
        }

        @Override
        public BannerBean[] newArray(int size) {
            return new BannerBean[size];
        }
    };
}

