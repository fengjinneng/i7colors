package com.company.qcy.Utils;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;

public class MyStatusBarUtil {


    public static void setTouming(Window window){

        if(Build.VERSION.SDK_INT >= 21) {

            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

            window.setStatusBarColor(Color.TRANSPARENT);

        }

    }


    /**

     * 利用反射获取状态栏高度

     * @return

     */

    public static int getStatusBarHeight(Context context) {

        int result = 0;

        //获取状态栏高度的资源id

        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {

            result = context.getResources().getDimensionPixelSize(resourceId);

        }

        return result;

    }


}
