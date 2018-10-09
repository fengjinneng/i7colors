package com.company.qcy.Utils;


import android.text.TextUtils;
import android.util.Log;

import com.company.qcy.BuildConfig;

/**
 * 日志
 */
public class LogUtil {

    public static final String TAG = "i7colors";
    public static final String COLON = ":";

    /**
     * 日志总开
     */
//    private static boolean isOn = BuildConfig.DEBUG;
    private static boolean isOn = true;
//    private static boolean isOn = false;

    private static int LOG_MAXLENGTH = 2000;

    /**
     * 设置日志总开
     *
     * @param isLogOn 开关
     */
    public static void setLogOn(boolean isLogOn) {
        isOn = isLogOn;
    }

    public static void v(String tag, String msg) {
        if (isOn) {
            Log.v(TAG, tag + COLON + msg);
        }
    }

    public static void i(String tag, String msg) {
        if (isOn) {
            Log.i(TAG, tag + COLON + msg);
        }
    }

    public static void i(String msg) {
        if (isOn) {
            Log.i(TAG, COLON + msg);
        }
    }


    public static void d(String tag, String msg) {
        if (isOn) {
            Log.d(TAG, tag + COLON + msg);
        }
    }


    public static void w(String tag, String msg) {
        if (isOn) {
            Log.w(TAG, tag + COLON + msg);
        }
    }

    public static void e(String tag, String msg) {
//        if(isOn) {
//            // 临时保存
//            if ("mxg".equals(tag)){
//                Log.e(tag, tag + COLON + msg);
//                return;
//            }
//        }

        if (isOn && !TextUtils.isEmpty(msg)) {

            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    Log.i(TAG + i, msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAXLENGTH;
                } else {
                    Log.i(TAG, msg.substring(start, strLength));
                    break;
                }
            }
        }

    }

    public static void e(String msg) {
        LogUtil.e("jj", "isOn__" + isOn);
        if (isOn && !TextUtils.isEmpty(msg)) {

            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    Log.i(TAG + i, msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAXLENGTH;
                } else {
                    Log.i(TAG, msg.substring(start, strLength));
                    break;
                }
            }
        }
    }


    public static void allE(String tag, String msg) {  //信息太长,分段打印
        if (tag == null || tag.length() == 0
                || msg == null || msg.length() == 0)
            return;

        int segmentSize = 4 * 1024;
        long length = msg.length();
        if (length <= segmentSize) {// 长度小于等于限制直接打印
            Log.e(tag, msg);
        } else {
            while (msg.length() > segmentSize) {// 循环分段打印日志
                String logContent = msg.substring(0, segmentSize);
                msg = msg.replace(logContent, "");
                Log.e(tag, logContent);
            }
            Log.e(tag, msg);// 打印剩余日志
        }
    }
}