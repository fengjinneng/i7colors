package com.company.qcy;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.company.qcy.fragment.home.HomeFragment;
import com.company.qcy.fragment.home.ToutiaoFragment;
import com.company.qcy.fragment.home.WodeFragment;
import com.company.qcy.fragment.home.XiaoxiFragment;
import com.company.qcy.ui.activity.user.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationBar mBottomnavigation;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {

        initBottomNavigation();
        initFragment();

    }

    private HomeFragment homeFragment;
    private ToutiaoFragment toutiaoFragment;
    private XiaoxiFragment xiaoxiFragment;
    private WodeFragment wodeFragment;

    private void initFragment() {

        homeFragment = new HomeFragment();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.home_container, homeFragment);
        fragmentTransaction.commit();
    }

    //初始化底部导航栏
    private void initBottomNavigation() {
        mBottomnavigation = findViewById(R.id.bottomnavigation);
        BottomNavigationItem homeItem = new BottomNavigationItem(R.mipmap.home_checked, "首页");
        BottomNavigationItem toutiaoItem = new BottomNavigationItem(R.mipmap.toutiao_checked, "头条");
        BottomNavigationItem xiaoxiItem = new BottomNavigationItem(R.mipmap.xiaoxi_checked, "消息");
        BottomNavigationItem wodeItem = new BottomNavigationItem(R.mipmap.wode_checked, "我的");

        homeItem.setInactiveIcon(getResources().getDrawable(R.mipmap.home_unchecked));
        toutiaoItem.setInactiveIcon(getResources().getDrawable(R.mipmap.toutiao_unchecked));
        xiaoxiItem.setInactiveIcon(getResources().getDrawable(R.mipmap.xiaoxi_unchecked));
        wodeItem.setInactiveIcon(getResources().getDrawable(R.mipmap.wode_unchecked));
        mBottomnavigation.addItem(homeItem).addItem(toutiaoItem).addItem(xiaoxiItem).addItem(wodeItem).initialise();
        mBottomnavigation.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                switch (position) {
                    case 0:
                        if (homeFragment == null) {

                            homeFragment = new HomeFragment();
                            fragmentTransaction.add(R.id.home_container, homeFragment);
                        }
                        hideFragment(fragmentTransaction);
                        fragmentTransaction.show(homeFragment);
                        break;
                    case 1:
                        if (toutiaoFragment == null) {
                            toutiaoFragment = new ToutiaoFragment();
                            fragmentTransaction.add(R.id.home_container, toutiaoFragment);
                        }
                        hideFragment(fragmentTransaction);
                        fragmentTransaction.show(toutiaoFragment);
                        break;
                    case 2:
                        if (xiaoxiFragment == null) {

                            xiaoxiFragment = new XiaoxiFragment();
                            fragmentTransaction.add(R.id.home_container, xiaoxiFragment);
                        }
                        hideFragment(fragmentTransaction);
                        fragmentTransaction.show(xiaoxiFragment);
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                        if (wodeFragment == null) {
//
//                            wodeFragment = new WodeFragment();
//                            fragmentTransaction.add(R.id.home_container, wodeFragment);
//                        }
//                        hideFragment(fragmentTransaction);
//                        fragmentTransaction.show(wodeFragment);
                        break;
                }
                fragmentTransaction.commit();
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });

    }

    //隐藏所有的fragment

    private void hideFragment(android.support.v4.app.FragmentTransaction transaction) {

        if (homeFragment != null) {

            transaction.hide(homeFragment);

        }

        if (wodeFragment != null) {

            transaction.hide(wodeFragment);

        }

        if (xiaoxiFragment != null) {

            transaction.hide(xiaoxiFragment);

        }

        if (toutiaoFragment != null) {

            transaction.hide(toutiaoFragment);

        }
    }
}
