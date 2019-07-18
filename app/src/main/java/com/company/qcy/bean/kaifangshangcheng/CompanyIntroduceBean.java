package com.company.qcy.bean.kaifangshangcheng;

import android.os.Parcel;
import android.os.Parcelable;

import com.amap.api.maps.model.LatLng;

public class CompanyIntroduceBean implements Parcelable {

    private String zhuying;
    private String contact;
    private String phone;
    private String introduce;
    private String address;

    private String lat;
    private String Lng;

    private String companyName;


    public String getZhuying() {
        return zhuying;
    }

    public void setZhuying(String zhuying) {
        this.zhuying = zhuying;
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

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return Lng;
    }

    public void setLng(String lot) {
        Lng = lot;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.zhuying);
        dest.writeString(this.contact);
        dest.writeString(this.phone);
        dest.writeString(this.introduce);
        dest.writeString(this.address);
        dest.writeString(this.lat);
        dest.writeString(this.Lng);
        dest.writeString(this.companyName);
    }

    public CompanyIntroduceBean() {
    }

    protected CompanyIntroduceBean(Parcel in) {
        this.zhuying = in.readString();
        this.contact = in.readString();
        this.phone = in.readString();
        this.introduce = in.readString();
        this.address = in.readString();
        this.lat = in.readString();
        this.Lng = in.readString();
        this.companyName = in.readString();
    }

    public static final Parcelable.Creator<CompanyIntroduceBean> CREATOR = new Parcelable.Creator<CompanyIntroduceBean>() {
        @Override
        public CompanyIntroduceBean createFromParcel(Parcel source) {
            return new CompanyIntroduceBean(source);
        }

        @Override
        public CompanyIntroduceBean[] newArray(int size) {
            return new CompanyIntroduceBean[size];
        }
    };

    @Override
    public String toString() {
        return "CompanyIntroduceBean{" +
                "zhuying='" + zhuying + '\'' +
                ", contact='" + contact + '\'' +
                ", phone='" + phone + '\'' +
                ", introduce='" + introduce + '\'' +
                ", address='" + address + '\'' +
                ", lat='" + lat + '\'' +
                ", Lng='" + Lng + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
