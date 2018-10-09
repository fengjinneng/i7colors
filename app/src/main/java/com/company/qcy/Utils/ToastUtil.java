package com.company.qcy.Utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.company.qcy.I7colorsApplication;
/**
 * Created by PiGu on 2016/11/18.
 */
public class ToastUtil {

    /**
     * 为了解决 原生toast 反应慢
     * 否则，在加载更多时，连续提示、
     *
     * @param context
     * @param text
     */
    private static Toast toast;

    public static void showToast(Context context, String text) {
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT);
        }

        toast.setText(text);
        toast.show();
    }

    public static void showToast(Context context, int resId) {
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(), resId, Toast.LENGTH_SHORT);
        }
        toast.setText(resId);
        toast.show();
    }

    //居中Toast
    public static void mCenterToast(Context context, String massage) {
        Toast toast = Toast.makeText(context, massage, Toast.LENGTH_SHORT);
        //第一个参数：设置toast在屏幕中显示的位置。我现在的设置是居中靠顶
        //第二个参数：相对于第一个参数设置toast位置的横向X轴的偏移量，正数向右偏移，负数向左偏移
        //第三个参数：同的第二个参数道理一样
        //如果你设置的偏移量超过了屏幕的范围，toast将在屏幕内靠近超出的那个边界显示
        //屏幕居中显示，X轴和Y轴偏移量都是0
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }    //居中Toast

    public static void mCenterToast(String massage) {
        if (toast == null) {
            toast = Toast.makeText(I7colorsApplication.instance, massage, Toast.LENGTH_SHORT);
        }

        //第一个参数：设置toast在屏幕中显示的位置。我现在的设置是居中靠顶
        //第二个参数：相对于第一个参数设置toast位置的横向X轴的偏移量，正数向右偏移，负数向左偏移
        //第三个参数：同的第二个参数道理一样
        //如果你设置的偏移量超过了屏幕的范围，toast将在屏幕内靠近超出的那个边界显示
        //屏幕居中显示，X轴和Y轴偏移量都是0
        toast.setText(massage);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void mCenterToast(int massage) {
        if (toast == null) {
            toast = Toast.makeText(I7colorsApplication.instance, massage, Toast.LENGTH_SHORT);
        }

        //第一个参数：设置toast在屏幕中显示的位置。我现在的设置是居中靠顶
        //第二个参数：相对于第一个参数设置toast位置的横向X轴的偏移量，正数向右偏移，负数向左偏移
        //第三个参数：同的第二个参数道理一样
        //如果你设置的偏移量超过了屏幕的范围，toast将在屏幕内靠近超出的那个边界显示
        //屏幕居中显示，X轴和Y轴偏移量都是0
        toast.setText(massage);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 感觉showMessage名字不太适合
     * 也没必要居中显示吐司吧？ 不都是底部默认显示吗？ 人家UI或者产品要中间吐司呢，服了你了
     */
    public static void showMessage(Context context, int resId) {
        Toast toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
        toast = getToast(toast);
        toast.show();
    }

    public static void showMessage(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast = getToast(toast);
        toast.show();
    }

    private static Toast getToast(Toast toast) {
        toast.setGravity(Gravity.CENTER, 0, 0);
        return toast;
    }

    public static void showMessage(Context context, String message, View view) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast = getToast(toast);
        if (view != null) {
            toast.setView(view);
            TextView tv = (TextView) view.findViewById(android.R.id.message);
            tv.setText(message);
        }
        toast.show();
    }

}