package com.company.qcy.bean.pengyouquan;

/**
 * Created by suneee on 2016/11/17.
 */
public class PhotoInfo {

    public String url;
    public int w;
    public int h;

    public PhotoInfo() {
    }

    public PhotoInfo(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }
}
