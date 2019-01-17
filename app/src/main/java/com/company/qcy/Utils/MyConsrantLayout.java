package com.company.qcy.Utils;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;

public class MyConsrantLayout extends ConstraintLayout {

    private InputWindowListener listener;

    public MyConsrantLayout(Context context) {
        super(context);
    }

    public MyConsrantLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyConsrantLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (oldh > h) {
            listener.show();
        } else{
            listener.hidden();
        }
    }

    public void setListener(InputWindowListener listener) {
        this.listener = listener;
    }
}
