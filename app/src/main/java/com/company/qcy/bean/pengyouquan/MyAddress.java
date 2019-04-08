package com.company.qcy.bean.pengyouquan;

import android.os.Parcel;
import android.os.Parcelable;

public class MyAddress implements Parcelable {


    private String lat;
    private String lot;
    private String title;
    private String content;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.lat);
        dest.writeString(this.lot);
        dest.writeString(this.title);
        dest.writeString(this.content);
    }

    public MyAddress() {
    }

    protected MyAddress(Parcel in) {
        this.lat = in.readString();
        this.lot = in.readString();
        this.title = in.readString();
        this.content = in.readString();
    }

    public static final Parcelable.Creator<MyAddress> CREATOR = new Parcelable.Creator<MyAddress>() {
        @Override
        public MyAddress createFromParcel(Parcel source) {
            return new MyAddress(source);
        }

        @Override
        public MyAddress[] newArray(int size) {
            return new MyAddress[size];
        }
    };
}
