package com.company.qcy.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.company.qcy.R;

public class RecyclerviewDisplayDecoration extends RecyclerView.ItemDecoration {

    private Paint dividerPaint;
    private int deviderHeight;


    public RecyclerviewDisplayDecoration(Context context) {

        //设置画笔
        dividerPaint = new Paint();
        //设置分割线颜色
        dividerPaint.setColor(context.getResources().getColor(R.color.fengexian));
        //设置分割线宽度
        deviderHeight = context.getResources().getDimensionPixelSize(R.dimen.divider_height);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

        //不是第一个的格子都设一个上边和底部的间距  这些间隔大小可以自行修改
        int pos = parent.getChildLayoutPosition(view);  //当前条目的position
        int itemCount = state.getItemCount()-1;           //最后一条的postion
        if(pos==itemCount){   //最后一条
            outRect.bottom = 0;
            outRect.top = 0;
        }else {
            //改变宽度
            outRect.bottom = deviderHeight;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        //得到列表所有的条目
        int childCount = parent.getChildCount();
        //得到条目的宽和高
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < childCount - 1; i++) {
            View view = parent.getChildAt(i);
            //计算每一个条目的顶点和底部 float值
            float top = view.getBottom();
            float bottom = view.getBottom() + deviderHeight;
            //重新绘制
            c.drawRect(left, top, right, bottom, dividerPaint);
        }
    }

}
