package com.company.qcy.bean.pengyouquan;

import android.net.Uri;

import java.net.URI;

public class ImageBean {


    private Uri uri;

    public ImageBean() {
    }

    public ImageBean(Uri uri) {
        this.uri = uri;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
