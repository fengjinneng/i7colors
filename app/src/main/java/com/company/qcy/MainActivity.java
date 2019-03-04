package com.company.qcy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.azhon.appupdate.config.UpdateConfiguration;
import com.azhon.appupdate.listener.OnButtonClickListener;
import com.azhon.appupdate.manager.DownloadManager;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.BannerBean;
import com.company.qcy.bean.UpdateBean;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.fragment.home.HomeFragment;
import com.company.qcy.fragment.home.ToutiaoFragment;
import com.company.qcy.fragment.home.WodeFragment;
import com.company.qcy.fragment.home.XiaoxiFragment;
import com.company.qcy.ui.activity.user.LoginActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.mob.pushsdk.MobPush;
import com.mob.pushsdk.MobPushReceiver;
import java.util.List;


public class MainActivity extends BaseActivity implements OnButtonClickListener {

    private BottomNavigationBar mBottomnavigation;

    private MobPushReceiver receiver;
    private ImageView mImageView17;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!StringUtils.isEmpty(SPUtils.getInstance().getString("adv"))
                &&SPUtils.getInstance().getString("adv").length()>5){
            ActivityUtils.startActivity(MyWelcomeActivity.class);
        }
        SPUtils.getInstance().put("isFirstIn", "1");
        initView();
//        Uri uri = getIntent().getData();
//        ARouter.getInstance().build(uri).navigation();
//        finish();
        addAdvData();

    }


    private void addAdvData() {

        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.INDEXBANNER)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("plate_code", "APP_Start_Pic");


        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("APP_Start_Pic", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
//                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONArray data = jsonObject.getJSONArray("data");
                            if (ObjectUtils.isEmpty(data)) {
                                return;
                            }
                            List<BannerBean> bannerBeans = JSONObject.parseArray(data.toJSONString(), BannerBean.class);
                            SPUtils.getInstance().put("adv", bannerBeans.get(0).getAd_image());
                            SPUtils.getInstance().put("advUrl", bannerBeans.get(0).getAd_url());
                            GlideUtils.loadImageWithStartPage(MainActivity.this,
                                    ServerInfo.IMAGE + bannerBeans.get(0).getAd_image(),mImageView17);

                        }
//                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
//                            SignAndTokenUtil.getSign(MyWelcomeActivity.this,request,this);
//                            return;
//                        }
//                        ToastUtils.showShort(msg);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
//                ToastUtils.showShort(getResources().getString(R.string.NETEXCEPTION));
            }
        };

        request.execute(stringCallback);

    }


    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        MobPush.removePushReceiver(receiver);
    }

    private DownloadManager manager;

    @Override
    public void onReciveMessage(MessageBean msg) {
        super.onReciveMessage(msg);
        switch (msg.getCode()) {
            case MessageBean.Code.NEEDUPDATEAPP:
                UpdateBean updateBean = (UpdateBean) msg.getObj();
                LogUtils.e("onReciveMessage", updateBean);
                /*
                 * 整个库允许配置的内容
                 * 非必选
                 */
                UpdateConfiguration configuration = new UpdateConfiguration()
                        //输出错误日志
                        .setEnableLog(true)
                        //设置自定义的下载
                        //.setHttpManager()
                        //下载完成自动跳动安装页面
                        .setJumpInstallPage(true)
                        //设置对话框背景图片 (图片规范参照demo中的示例图)
                        //.setDialogImage(R.drawable.ic_dialog)
                        //设置按钮的颜色
                        .setDialogButtonColor(getResources().getColor(R.color.chunhongse))
                        //设置按钮的文字颜色
                        .setDialogButtonTextColor(Color.WHITE)
                        //支持断点下载
                        .setBreakpointDownload(true)
                        //设置是否显示通知栏进度
                        .setShowNotification(true)

                        //设置对话框按钮的点击监听
                        .setButtonClickListener(this);
                //设置下载过程的监听
//                        .setOnDownloadListener(this);
                if (StringUtils.equals("1", updateBean.getIsForce())) {
                    //设置强制更新
                    configuration.setForcedUpgrade(true);
                } else if (StringUtils.equals("0", updateBean.getIsForce())) {
                    //不强制更新
                    configuration.setForcedUpgrade(false);
                }

                manager = DownloadManager.getInstance(this);
                manager.setApkName("update.apk")
                        .setApkUrl(updateBean.getUrl())
                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setShowNewerToast(true)
                        .setConfiguration(configuration)
//                .setDownloadPath(Environment.getExternalStorageDirectory() + "/AppUpdate")
                        .setApkVersionCode(updateBean.getVersionNum())
                        .setApkVersionName(updateBean.getVersionCode())
//                        .setApkSize("20.4")
                        .setAuthorities(getPackageName() + ".demo.provider")
                        .setApkDescription(updateBean.getDescription())
                        .download();
                break;
        }
    }

    @Override
    public void onButtonClick(int id) {

    }

    private void initView() {

        initBottomNavigation();
        initFragment();

        mImageView17 = (ImageView) findViewById(R.id.imageView17);
    }

    private HomeFragment homeFragment;
    private ToutiaoFragment toutiaoFragment;
    private XiaoxiFragment xiaoxiFragment;
    private WodeFragment wodeFragment;

    private void initFragment() {

        homeFragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.home_container, homeFragment);
        fragmentTransaction.commit();
    }

    private int choicedWhitchFragment;
    BottomNavigationItem xiaoxiItem;

    //初始化底部导航栏
    private void initBottomNavigation() {
        mBottomnavigation = findViewById(R.id.bottomnavigation);
        mBottomnavigation.setActiveColor(R.color.chunhongse);
        BottomNavigationItem homeItem = new BottomNavigationItem(R.mipmap.home_checked, "首页");
        BottomNavigationItem toutiaoItem = new BottomNavigationItem(R.mipmap.toutiao_checked, "印染圈");
        xiaoxiItem = new BottomNavigationItem(R.mipmap.xiaoxi_checked, "消息");
        BottomNavigationItem wodeItem = new BottomNavigationItem(R.mipmap.wode_checked, "我的");
//        TextBadgeItem item = new TextBadgeItem();
//        item.setHideOnSelect(true);
//        item.setText("11");
//        wodeItem.setBadgeItem(item);

        homeItem.setInactiveIcon(getResources().getDrawable(R.mipmap.home_unchecked));
        toutiaoItem.setInactiveIcon(getResources().getDrawable(R.mipmap.toutiao_unchecked));
        xiaoxiItem.setInactiveIcon(getResources().getDrawable(R.mipmap.xiaoxi_unchecked));
        wodeItem.setInactiveIcon(getResources().getDrawable(R.mipmap.wode_unchecked));
        mBottomnavigation.addItem(homeItem).addItem(toutiaoItem).addItem(xiaoxiItem).addItem(wodeItem).initialise();
        mBottomnavigation.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                switch (position) {
                    case 0:
                        isNetWork();

                        choicedWhitchFragment = 0;
                        if (homeFragment == null) {

                            homeFragment = new HomeFragment();
                            fragmentTransaction.add(R.id.home_container, homeFragment);
                        }
                        hideFragment(fragmentTransaction);
                        fragmentTransaction.show(homeFragment);
                        break;
                    case 1:
                        isNetWork();

                        choicedWhitchFragment = 1;
                        if (xiaoxiFragment == null) {
                            xiaoxiFragment = new XiaoxiFragment();
                            fragmentTransaction.add(R.id.home_container, xiaoxiFragment);
                        }
                        hideFragment(fragmentTransaction);
                        fragmentTransaction.show(xiaoxiFragment);
                        break;
                    case 2:
                        isNetWork();

                        choicedWhitchFragment = 2;
                        if (toutiaoFragment == null) {

                            toutiaoFragment = new ToutiaoFragment();
                            fragmentTransaction.add(R.id.home_container, toutiaoFragment);
                        }
                        hideFragment(fragmentTransaction);
                        fragmentTransaction.show(toutiaoFragment);
                        break;
                    case 3:
                        isNetWork();

                        if (!StringUtils.equals(SPUtils.getInstance().getString("isLogin"), "true")) {

                            startActivity(new Intent(MainActivity.this, LoginActivity.class));

                        }
                        if (wodeFragment == null) {
                            wodeFragment = new WodeFragment();
                            fragmentTransaction.add(R.id.home_container, wodeFragment);
                        }
                        hideFragment(fragmentTransaction);
                        fragmentTransaction.show(wodeFragment);

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

        addNotReadMessage();

    }

    //记录用户首次点击返回键的时间
    private long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - firstTime > 2000) {
                ToastUtils.showShort("再按一次退出程序");
                firstTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void addNotReadMessage() {
//        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.GETNOTREADMESSAGECOUNT)
//                .tag(this)
//
//                .params("sign", SPUtils.getInstance().getString("sign"))
//                .params("token", SPUtils.getInstance().getString("token"));
//
//        StringCallback stringCallback = new StringCallback() {
//            @Override
//            public void onSuccess(Response<String> response) {
//                LogUtils.v("GETNOTREADMESSAGECOUNT", response.body());
//
//                try {
//                    if (response.code() == 200) {
//                        JSONObject jsonObject = JSONObject.parseObject(response.body());
//                        String msg = jsonObject.getString("msg");
//                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
//                            JSONObject data = jsonObject.getJSONObject("data");
//
//                            return;
//
//                        }
//                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
//                            SignAndTokenUtil.getSign(MainActivity.this,request,this);
//                            return;
//                        }
//                        ToastUtils.showShort(msg);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onError(Response<String> response) {
//                super.onError(response);
//                ToastUtils.showShort(getResources().getString(R.string.NETEXCEPTION));
//            }
//        };
//        request.execute(stringCallback);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (StringUtils.isEmpty(SPUtils.getInstance().getString("isLogin"))) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            switch (choicedWhitchFragment) {
                case 0:
                    hideFragment(fragmentTransaction);
                    fragmentTransaction.show(homeFragment);
                    mBottomnavigation.selectTab(choicedWhitchFragment);
                    break;
                case 1:
                    hideFragment(fragmentTransaction);
                    fragmentTransaction.show(xiaoxiFragment);
                    mBottomnavigation.selectTab(choicedWhitchFragment);
                    break;
                case 2:
                    hideFragment(fragmentTransaction);
                    fragmentTransaction.show(toutiaoFragment);
                    mBottomnavigation.selectTab(choicedWhitchFragment);
                    break;
            }
            fragmentTransaction.commit();
        }
    }
    //隐藏所有的fragment

    private void hideFragment(FragmentTransaction transaction) {

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
