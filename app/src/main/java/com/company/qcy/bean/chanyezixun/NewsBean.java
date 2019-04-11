package com.company.qcy.bean.chanyezixun;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsBean implements Parcelable {



    /**
     * id : 3125
     * is_valid : null
     * created_at : null
     * created_by : null
     * updated_at : null
     * updated_by : null
     * offset : null
     * limit : null
     * title : 首页资讯大图
     * content : null
     * content_summary : 摘要
     * img_url :
     * source_url : null
     * author : null
     * news_date : 2018-06-23 00:00:00.0
     * type : null
     * status : null
     * type_name : null
     * start_date : null
     * end_date : null
     */



    private Long id;
    private String title;
    private String content;
    private String content_summary;
    private String img_url;
    private String news_date;
    private boolean showChoice;//是否显示选择

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getContent_summary() {
        return content_summary;
    }

    public void setContent_summary(String content_summary) {
        this.content_summary = content_summary;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getNews_date() {
        return news_date;
    }

    public void setNews_date(String news_date) {
        this.news_date = news_date;
    }

    public boolean isShowChoice() {
        return showChoice;
    }

    public void setShowChoice(boolean showChoice) {
        this.showChoice = showChoice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.content_summary);
        dest.writeString(this.img_url);
        dest.writeString(this.news_date);
        dest.writeByte(this.showChoice ? (byte) 1 : (byte) 0);
    }

    public NewsBean() {
    }

    protected NewsBean(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.title = in.readString();
        this.content = in.readString();
        this.content_summary = in.readString();
        this.img_url = in.readString();
        this.news_date = in.readString();
        this.showChoice = in.readByte() != 0;
    }

    public static final Parcelable.Creator<NewsBean> CREATOR = new Parcelable.Creator<NewsBean>() {
        @Override
        public NewsBean createFromParcel(Parcel source) {
            return new NewsBean(source);
        }

        @Override
        public NewsBean[] newArray(int size) {
            return new NewsBean[size];
        }
    };
}
