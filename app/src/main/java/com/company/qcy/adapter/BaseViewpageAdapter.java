package com.company.qcy.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class BaseViewpageAdapter extends FragmentPagerAdapter {
    FragmentManager fragmentManager;
    private List<Fragment> fragments; //创建一个List<Fragment>
    private List<String> titles;

    public BaseViewpageAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragmentManager = fm;
        this.fragments = fragments;
    }

    public BaseViewpageAdapter(FragmentManager fm, List<Fragment> fragments,List<String> titles) {
        super(fm);
        this.fragmentManager = fm;
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
