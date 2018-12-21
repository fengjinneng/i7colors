package com.company.qcy.Utils;

import android.view.View;

/**
 * 第一次进入项目时，ViewPager切换时的动画、
 */
public class RotateUpTransformer extends ABaseTransformer {

    private static final float ROT_MOD = -15f;

    @Override
    protected void onTransform(View page, float position) {
        final float width = page.getWidth();
        final float rotation = ROT_MOD * position;

        page.setPivotX(width * 0.5f);
        page.setPivotY(0f);
        page.setTranslationX(0f);
        page.setRotation(rotation);
    }

    @Override
    protected boolean isPagingEnabled() {
        return true;
    }

}
