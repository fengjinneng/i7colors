package com.company.qcy.Utils;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.company.qcy.R;

//区别就在在于加载完成的背景是白色的
public class MyLoadMoreView2 extends LoadMoreView {
    @Override
    public int getLayoutId() {
        return R.layout.load_more2;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }
}
