package com.company.qcy.Utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.company.qcy.I7colorsApplication;

/**
 * Created by warren on 3/23/16.
 */
public class ScreenUtil {
    public static int getScreenWidth(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) I7colorsApplication.instance.getSystemService(context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public static int getScreenWidth() {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) I7colorsApplication.instance.getSystemService(I7colorsApplication.instance.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public static int getScreenHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) I7colorsApplication.instance.getSystemService(I7colorsApplication.instance.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }
}
