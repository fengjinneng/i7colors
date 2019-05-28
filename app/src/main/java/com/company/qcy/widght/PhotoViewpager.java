package com.company.qcy.widght;

import android.support.v4.view.ViewPager;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class PhotoViewpager extends ViewPager {


    public PhotoViewpager(Context context) {
        super(context);
    }

    public PhotoViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return false;
    }

}
