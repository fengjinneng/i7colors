package com.company.qcy.widght.pengyouquan;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.company.qcy.I7colorsApplication;
import com.company.qcy.R;

/**
 * @author yiw
 * @Description:
 * @date 16/1/2 16:32
 */
public abstract class SpannableClickable extends ClickableSpan implements View.OnClickListener {

    private int DEFAULT_COLOR_ID = R.color.lanse;
    /**
     * text颜色
     */
    private int textColor ;

    public SpannableClickable() {
        this.textColor = I7colorsApplication.getContext().getResources().getColor(DEFAULT_COLOR_ID);
    }

    public SpannableClickable(int textColor){
        this.textColor = textColor;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);

        ds.setColor(textColor);
        ds.setUnderlineText(false);
        ds.clearShadowLayer();
    }
}
