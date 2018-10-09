package com.company.qcy;

import android.app.Application;

public class I7colorsApplication extends Application {

    public static I7colorsApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance =this;
    }
}
