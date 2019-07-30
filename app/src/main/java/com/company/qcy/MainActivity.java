package com.company.qcy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
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
import com.company.qcy.Utils.JpushUtil;
import com.company.qcy.Utils.NetworkUtil;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.bean.BannerBean;
import com.company.qcy.bean.UpdateBean;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.fragment.home.HomeFragment;
import com.company.qcy.fragment.home.PengyouquanFragment;
import com.company.qcy.fragment.home.XiaoxiFragment;
import com.company.qcy.fragment.home.WodeFragment;
import com.company.qcy.ui.activity.user.LoginActivity;
import com.githang.statusbar.StatusBarCompat;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.mob.pushsdk.MobPush;
import com.mob.pushsdk.MobPushReceiver;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.HashMap;
import java.util.List;
import cn.jpush.android.api.JPushInterface;
import me.leolin.shortcutbadger.ShortcutBadger;

@Route(path = "/main/entrance")
public class MainActivity extends AppCompatActivity implements OnButtonClickListener {

    private BottomNavigationBar mBottomnavigation;

    private MobPushReceiver receiver;
    private ImageView mImageView17;

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        EventBus.getDefault().register(this);

        if (android.os.Build.VERSION.SDK_INT > 19) {
            StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.baise), true);
        }

        HashMap<String, String> map = (HashMap) getIntent().getSerializableExtra("jpush_data");

        if (!StringUtils.isEmpty(SPUtils.getInstance().getString("adv"))
                && SPUtils.getInstance().getString("adv").length() > 5 && ObjectUtils.isEmpty(map)) {
            ActivityUtils.startActivity(MyWelcomeActivity.class);
        }
        SPUtils.getInstance().put("isFirstIn", "1");

        SPUtils.getInstance().put("registrationId", JPushInterface.getRegistrationID(this));


        initView();

        addAdvData();

        if (SPUtils.getInstance().getInt("pengyouquanNews") <= -1) {
            SPUtils.getInstance().put("pengyouquanNews", 0);
        }
        if (SPUtils.getInstance().getInt("notification") <= -1) {
            SPUtils.getInstance().put("notification", 0);
        }

        //JPUSH推送消息過來的
        if (!ObjectUtils.isEmpty(map)) {
            JpushUtil.jumpActivity(map, this);
        }

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
                                    ServerInfo.IMAGE + bannerBeans.get(0).getAd_image(), mImageView17);

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
        EventBus.getDefault().unregister(this);
        MobPush.removePushReceiver(receiver);
    }

    private DownloadManager manager;


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRec(MessageBean msg) {
        switch (msg.getCode()) {

            case MessageBean.JPush.RECIVENOTIFICATION:

//                ShortcutBadger.removeCount(this);
                ShortcutBadger.applyCount(this, SPUtils.getInstance().getInt("notification"));
                if (xitongXiaoxiMessageItem.isHidden()) {
                    xitongXiaoxiMessageItem.show();
                }
                xitongXiaoxiMessageItem.setText(SPUtils.getInstance().getInt("notification") + "");
                break;

            case MessageBean.JPush.DELETELUNCHNUMBER:
                if (!xitongXiaoxiMessageItem.isHidden()) {
                    xitongXiaoxiMessageItem.hide();
                }
                ShortcutBadger.removeCount(this);
                break;

            //点击进去四个消息的列表，首页的就隐藏
            case MessageBean.Code.DIANJIJINQUMESSAGE:
                if (!pengyouquanMessageItem.isHidden()) {
                    pengyouquanMessageItem.hide();
                }
                break;

            //朋友圈有新的消息
            case MessageBean.Code.PENGYOUQUANHAVENEWMESSAGE:
                if (pengyouquanMessageItem.isHidden()) {
                    pengyouquanMessageItem.show();
                }
                pengyouquanMessageItem.setText(SPUtils.getInstance().getInt("pengyouquanNews") + "");

                break;

            case MessageBean.Code.NEEDUPDATEAPP:
                UpdateBean updateBean = (UpdateBean) msg.getObj();
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
    private XiaoxiFragment xiaoxiFragment;
    private PengyouquanFragment pengyouquanFragment;
    private WodeFragment wodeFragment;

    private void initFragment() {

        homeFragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.home_container, homeFragment);
        fragmentTransaction.commit();
    }


    private TextBadgeItem pengyouquanMessageItem;
    private TextBadgeItem xitongXiaoxiMessageItem;

    private int choicedWhitchFragment;
    BottomNavigationItem xiaoxiItem;

    //初始化底部导航栏
    private void initBottomNavigation() {
        pengyouquanMessageItem = new TextBadgeItem();
        xitongXiaoxiMessageItem = new TextBadgeItem();

        mBottomnavigation = findViewById(R.id.bottomnavigation);
        mBottomnavigation.setActiveColor(R.color.chunhongse);
        BottomNavigationItem homeItem = new BottomNavigationItem(R.mipmap.home_checked, "首页");
        BottomNavigationItem pengyouquanItem = new BottomNavigationItem(R.mipmap.toutiao_checked, "印染圈");
        xiaoxiItem = new BottomNavigationItem(R.mipmap.xiaoxi_checked, "消息");
        BottomNavigationItem wodeItem = new BottomNavigationItem(R.mipmap.wode_checked, "我的");

        homeItem.setInactiveIcon(getResources().getDrawable(R.mipmap.home_unchecked));
        pengyouquanItem.setInactiveIcon(getResources().getDrawable(R.mipmap.toutiao_unchecked));
        xiaoxiItem.setInactiveIcon(getResources().getDrawable(R.mipmap.xiaoxi_unchecked));
        wodeItem.setInactiveIcon(getResources().getDrawable(R.mipmap.wode_unchecked));

        pengyouquanMessageItem.setHideOnSelect(false);
        pengyouquanItem.setBadgeItem(pengyouquanMessageItem);
        if (SPUtils.getInstance().getInt("pengyouquanNews") > 0) {
            pengyouquanMessageItem.setText(SPUtils.getInstance().getInt("pengyouquanNews") + "");

        } else {
            pengyouquanMessageItem.hide();
        }

        xitongXiaoxiMessageItem.setHideOnSelect(false);
        xiaoxiItem.setBadgeItem(xitongXiaoxiMessageItem);
        if (SPUtils.getInstance().getInt("notification") > 0) {
            xitongXiaoxiMessageItem.setText(SPUtils.getInstance().getInt("notification") + "");
            ShortcutBadger.applyCount(this, SPUtils.getInstance().getInt("notification"));
        } else {
            xitongXiaoxiMessageItem.hide();
        }
        mBottomnavigation.addItem(homeItem).addItem(pengyouquanItem).addItem(xiaoxiItem).addItem(wodeItem).initialise();

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
                        if (pengyouquanFragment == null) {
                            pengyouquanFragment = new PengyouquanFragment();
                            fragmentTransaction.add(R.id.home_container, pengyouquanFragment);
                        }
                        hideFragment(fragmentTransaction);
                        fragmentTransaction.show(pengyouquanFragment);
                        break;
                    case 2:
                        isNetWork();

                        choicedWhitchFragment = 2;
                        if (xiaoxiFragment == null) {

                            xiaoxiFragment = new XiaoxiFragment();
                            fragmentTransaction.add(R.id.home_container, xiaoxiFragment);
                        }
                        hideFragment(fragmentTransaction);
                        fragmentTransaction.show(xiaoxiFragment);
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

    @Override
    protected void onResume() {
        super.onResume();

        if (SPUtils.getInstance().getInt("notification") < 1) {
            if (!xitongXiaoxiMessageItem.isHidden()) {
                xitongXiaoxiMessageItem.hide();
            }
        }

        if (StringUtils.isEmpty(SPUtils.getInstance().getString("isLogin"))) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            switch (choicedWhitchFragment) {
                case 0:
                    hideFragment(fragmentTransaction);
                    fragmentTransaction.show(homeFragment);
                    mBottomnavigation.selectTab(choicedWhitchFragment);
                    StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.baise), true);
                    break;
                case 1:
                    hideFragment(fragmentTransaction);
                    fragmentTransaction.show(pengyouquanFragment);
                    mBottomnavigation.selectTab(choicedWhitchFragment);
                    StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.baise), true);
                    break;
                case 2:
                    hideFragment(fragmentTransaction);
                    fragmentTransaction.show(xiaoxiFragment);
                    mBottomnavigation.selectTab(choicedWhitchFragment);
                    StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.baise), false);
                    break;
            }
            fragmentTransaction.commit();
        }
    }

    private AlertDialog dialog;
    AlertDialog.Builder builder = null;

    /**
     * 判断网络
     */
    protected void isNetWork() {
        int status = NetworkUtil.getNetworkType(this);

        LogUtils.e("base--status--" + status);
        if (0 == status) {
            showPop();

        } else {
            if (null != dialog && dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }
    /**
     * 显示无网弹框
     *
     * @param
     */
    private void showPop() {
        if (builder == null) {

            builder = new AlertDialog.Builder(this);
            View view = View.inflate(this, R.layout.toast_newwork_setting_layout, null);
            view.setPadding(10, 0, 10, 200);
            builder.setView(view);
            builder.setCancelable(true);//点击返回是否取消
            LinearLayout rlParent = view.findViewById(R.id.rl_parent);//


            rlParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (dialog.isShowing()) {
//                    dialog.dismiss();
                        NetworkUtil.toSetting(MainActivity.this);
                    }
                }
            });
        }

        //取消或确定按钮监听事件处理
        if (dialog == null) {
            dialog = builder.create();
            dialog.setCanceledOnTouchOutside(true);
            dialog.setCanceledOnTouchOutside(true);
            Window window = dialog.getWindow();
            window.setDimAmount(0);//设置昏暗度为0
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setGravity(Gravity.BOTTOM);

        }

        if (!dialog.isShowing()) {
            dialog.show();
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
        if (pengyouquanFragment != null) {
            transaction.hide(pengyouquanFragment);
        }
        if (xiaoxiFragment != null) {
            transaction.hide(xiaoxiFragment);
        }
    }
}
