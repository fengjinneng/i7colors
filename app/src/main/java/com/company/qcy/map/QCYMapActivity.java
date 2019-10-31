package com.company.qcy.map;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.CoordinateConverter;
import com.amap.api.location.DPoint;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.Utils.maputil.MapUtil;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.base.WebActivity;
import com.company.qcy.bean.kaifangshangcheng.CompanyIntroduceBean;
import com.company.qcy.bean.pengyouquan.MyAddress;
import com.company.qcy.ui.activity.kaifangshangcheng.KFSCXiangqingActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.util.ConvertUtils;

public class QCYMapActivity extends BaseActivity implements View.OnClickListener,
        AMapLocationListener,
        AMap.OnMyLocationChangeListener, GPSIterface, GeocodeSearch.OnGeocodeSearchListener {


    private RecyclerView mActivityQcymapRecyclerView;
    private CompanyInfoAdapter adapter;
    private List<MarketMapBean> marketMapBeans;
    private BottomSheetBehavior behavior;
    private MapView mMapView;
    private AMap aMap;

    private ConstraintLayout mActivityQcymapJieguoLayout;

    private LinearLayoutCompat mBottomSheetLayout;
    /**
     * 地区
     */
    private TextView mActivityQcymapDiqu;
    private ConstraintLayout mActivityQcymapDiquLayout;
    /**
     * 企业类型
     */
    private TextView mActivityQcymapQiyeleixing;
    private ConstraintLayout mActivityQcymapQiyeleixingLayout;
    private TextView mActivityQcymapJieguoTotalCount;
    private TextView mActivityQcymapJieguoSearchCount;
    private ImageView mActivityQcymapTuijianqiyeImage;
    /**  */
    private TextView mActivityQcymapTuijianqiyeName;
    private ConstraintLayout mActivityQcymapTuijianqiyeLayout;
    /**
     * 返回
     */
    private ImageView mActivityQcymapBack;
    /**
     * 请输入企业名称
     */
    private EditText mActivityQcymapSearchText;
    /**
     * 浙江省绍兴市
     */
    private TextView mActivityQcymapTuijianqiyeAddress;
    /**
     * 15580804456
     */
    private TextView mActivityQcymapTuijianqiyePhone;
    private ImageView mActivityQcymapTuijianqiyeTuijian;

    private MyAddress shareAddress;
    //是否能够导航,能否弹出导航信息
    private boolean canNavication;

    //朋友圈点进来的分享位置的marker
    private Marker shareMarker;

    /**
     * 一键导航
     */
    private ImageView mActivityQcymapYijiandaohang;
    private TextView mActivityQcymapYijiandaohangTitle;
    private TextView mActivityQcymapYijiandaohangContent;
    private ImageView mActivityQcymapYijiandaohangNavi;
    private ImageView mActivityQcymapYijiandaohangBack;
    private ConstraintLayout mActivityQcymapYijiandaohangLayout;
    private TextView mActivityQcymapTuijianqiyeJuli;
    /**
     * 查看路线
     */
    private TextView mActivityQcymapTuijianqiyeChakanluxian;
    private ImageView mActivityQcymapDeleteSearch;
    /**
     * 加入我们
     */
    private TextView mActivityQcymapJoinUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qcymap);

        shareAddress = getIntent().getParcelableExtra("address");

        mMapView = (MapView) findViewById(R.id.activity_qcymap_mapview);
        mMapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView.getMap();
        }

        initView();


        if (!ObjectUtils.isEmpty(shareAddress)) {
            mActivityQcymapYijiandaohangLayout.setVisibility(View.VISIBLE);
            if (!StringUtils.isEmpty(shareAddress.getLat()) && !StringUtils.isEmpty(shareAddress.getLot())) {
                LatLng latLng = new LatLng(Double.parseDouble(shareAddress.getLat()), Double.parseDouble(shareAddress.getLot()));
                shareMarker = aMap.addMarker(new MarkerOptions().position(latLng).title(shareAddress.getTitle()).snippet(""));
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                canNavication = true;
                LatLog2Address(Double.parseDouble(shareAddress.getLat()), Double.parseDouble(shareAddress.getLot()));
            } else {
                canNavication = false;
                ToastUtils.showShort("该公司没有地址信息！");

            }

            mActivityQcymapYijiandaohang.setVisibility(View.GONE);
            mActivityQcymapYijiandaohangLayout.setBackground(getResources().getDrawable(R.drawable.background_chanpinfenlei_unchecked));

            mActivityQcymapYijiandaohangTitle.setText(shareAddress.getTitle());
            mActivityQcymapYijiandaohangContent.setText(shareAddress.getContent());


        }
        initOther();
    }

    private void initOther() {


        adapter = new CompanyInfoAdapter(R.layout.item_qcymap_company_info, marketMapBeans);


        //给RecyclerView设置适配器
        mActivityQcymapRecyclerView.setAdapter(adapter);

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                addData();
            }
        }, mActivityQcymapRecyclerView);

        dingweiwode();

        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    //已展开
                } else {
                    //已收缩
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });


//        addData();
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                MarketMapBean marketMapBean = (MarketMapBean) marker.getObject();

                LatLng latLng = new LatLng(Double.parseDouble(marketMapBean.getLatitude()), Double.parseDouble(marketMapBean.getLongitude()));

//                if(!ObjectUtils.isEmpty(preMarker)){
//                    addUnCheckedMarker(preLatLng,preCompanyName);
//
//                }
//                preMarker = marker;
//                preLatLng = latLng;
//                preCompanyName = marketMapBean.getCompanyName();


                addCheckedMarker(marker, latLng, marketMapBean.getCompanyName(), marketMapBean);

                tuijianMarket = marketMapBean;
//                    tuijianMarketMarker = marker;
//                    tuijianMarketLatng = latLng;
//                    tuijianCompanyName = marketMapBean.getCompanyName();


                setTuijianMarketInfo(latLng);

                //控制是否显示弹窗
                return true;
            }
        });

        mActivityQcymapSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    clickSearch = true;
                    upLoadCompanyName = mActivityQcymapSearchText.getText().toString();
                    KeyboardUtils.hideSoftInput(QCYMapActivity.this);

                    reLoadData();
                }
                return false;
            }
        });

        mActivityQcymapSearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                clickSearch = false;
                upLoadCompanyName = s.toString();
                if (s.length() > 0) {
                    mActivityQcymapDeleteSearch.setVisibility(View.VISIBLE);
                } else {
                    mActivityQcymapDeleteSearch.setVisibility(View.GONE);
                }
            }
        });

        mActivityQcymapDeleteSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivityQcymapSearchText.setText("");
            }
        });

        dingwei();

//        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//
//            }
//        });

        aMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    //展开后设置收缩
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }

                if (KeyboardUtils.isSoftInputVisible(QCYMapActivity.this)) {
                    KeyboardUtils.hideSoftInput(QCYMapActivity.this);
                    if (clickSearch) {
                    } else {
                        mActivityQcymapSearchText.setText("");
                    }
                }
            }
        });


        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MarketMapBean marketMapBean = (MarketMapBean) adapter.getData().get(position);
                jumpToDianpu(marketMapBean);
            }
        });

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.item_qcymap_company_info_chakanluxian:
                        MarketMapBean bean = (MarketMapBean) adapter.getData().get(position);
                        if (!ObjectUtils.isEmpty(myAddress) && !ObjectUtils.isEmpty(bean)) {
                            naviStartLat = myAddress.getLat();
                            naviStartLng = myAddress.getLot();
                            naviStartName = myAddress.getTitle();
                            naviEndLat = bean.getLatitude();
                            naviEndLng = bean.getLongitude();
                            naviEndName = bean.getCompanyName();
                            showNaviDialog();
                        }
                        break;
                }
            }
        });


//        if (!gps_presenter.gpsIsOpen(this)) {
//
//            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//            dialog.setTitle("提示!");
//            dialog.setMessage("需要打开您的位置信息！");
//            dialog.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    openLocation(QCYMapActivity.this);
//                    dialog.dismiss();
//                }
//            });
//            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            });
//            dialog.show();
//
//        }

        if (!isLocationEnabled()) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("提示!");
            dialog.setMessage("请打开GPS定位开关，,以便获取更精准的推荐！");
            dialog.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    openLocation(QCYMapActivity.this);
                    dialog.dismiss();
                }
            });
            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

    public boolean isLocationEnabled() {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(QCYMapActivity.this.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(QCYMapActivity.this.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }


    }

    private boolean clickSearch;


    private void setTuijianMarketInfo(LatLng latLng) {

        if (!StringUtils.isEmpty(tuijianMarket.getLatitude()) &&
                !StringUtils.isEmpty(tuijianMarket.getLongitude()) && !StringUtils.equals("0.0", tuijianMarket.getLatitude())
                && !StringUtils.equals("0.0", tuijianMarket.getLongitude())) {

            mActivityQcymapTuijianqiyeChakanluxian.setVisibility(View.VISIBLE);

            DPoint endPoint = new DPoint(Double.parseDouble(tuijianMarket.getLatitude()),
                    Double.parseDouble(tuijianMarket.getLongitude()));

            float distance = CoordinateConverter.calculateLineDistance(myPoint,
                    endPoint);
            if (distance > 1000) {

                mActivityQcymapTuijianqiyeJuli.setText("距离:" + Math.round(distance / 1000) + "km");

            } else {
                mActivityQcymapTuijianqiyeJuli.setText("距离:" + Math.round(distance) + "m");
            }
            aMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        } else {
            mActivityQcymapTuijianqiyeChakanluxian.setVisibility(View.GONE);
            mActivityQcymapTuijianqiyeJuli.setText("");
        }

        if (StringUtils.equals("market", tuijianMarket.getFrom())) {
            mActivityQcymapTuijianqiyeTuijian.setVisibility(View.VISIBLE);
        } else {
            mActivityQcymapTuijianqiyeTuijian.setVisibility(View.GONE);
        }

        if (!StringUtils.isEmpty(tuijianMarket.getCompanyName())) {
            mActivityQcymapTuijianqiyeName.setText(tuijianMarket.getCompanyName());
        }

        if (!ObjectUtils.isEmpty(tuijianMarket.getMarket())) {
            if (!StringUtils.isEmpty(tuijianMarket.getMarket().getPhone())) {
                mActivityQcymapTuijianqiyePhone.setText(tuijianMarket.getMarket().getPhone());
            }
            if (!StringUtils.isEmpty(tuijianMarket.getMarket().getLogo())) {

                Glide.with(QCYMapActivity.this).
                        load(ServerInfo.IMAGE + tuijianMarket.getMarket().getLogo()).into(mActivityQcymapTuijianqiyeImage);
            } else {
                mActivityQcymapTuijianqiyeImage.setImageDrawable(getResources().getDrawable(R.drawable.place_500x500));

            }
        } else {
            mActivityQcymapTuijianqiyeImage.setImageDrawable(getResources().getDrawable(R.drawable.place_500x500));

        }
        if (StringUtils.isEmpty(tuijianMarket.getProvince()) || StringUtils.isEmpty(tuijianMarket.getCity())) {

        } else {
            mActivityQcymapTuijianqiyeAddress.setText(tuijianMarket.getProvince() + " " + tuijianMarket.getCity());
        }

//                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, aMap.getMaxZoomLevel() - 9));

    }


    //选择地图
    private Dialog dialog;

    private String naviStartLat;
    private String naviStartLng;
    private String naviStartName;
    private String naviEndLat;
    private String naviEndLng;
    private String naviEndName;

    private void showNaviDialog() {
        if (!ObjectUtils.isEmpty(dialog)) {
            dialog.show();
            return;
        }
        dialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_choice_map, null);
        dialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        // layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        contentView.setLayoutParams(layoutParams);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        int isHaveMap = 0;
        if (!MapUtil.isBaiduMapInstalled()) {
            contentView.findViewById(R.id.baidu).setVisibility(View.GONE);
        } else {
            isHaveMap++;
            contentView.findViewById(R.id.baidu).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        MapUtil.openBaiDuNavi(QCYMapActivity.this,
                                Double.parseDouble(naviStartLat), Double.parseDouble(naviStartLng),
                                naviStartName,
                                Double.parseDouble(naviEndLat),
                                Double.parseDouble(naviEndLng), naviEndName);
                    } catch (Exception e) {

                    }

                }
            });
        }

        if (!MapUtil.isGdMapInstalled()) {
            contentView.findViewById(R.id.gaode).setVisibility(View.GONE);
        } else {
            isHaveMap++;
            contentView.findViewById(R.id.gaode).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        MapUtil.openGaoDeNavi(QCYMapActivity.this,
                                Double.parseDouble(naviStartLat), Double.parseDouble(naviStartLng),
                                naviStartName,
                                Double.parseDouble(naviEndLat),
                                Double.parseDouble(naviEndLng), naviEndName);
                    } catch (Exception e) {

                    }
                }
            });
        }
        if (!MapUtil.isTencentMapInstalled()) {
            contentView.findViewById(R.id.tengxun).setVisibility(View.GONE);
        } else {
            isHaveMap++;
            contentView.findViewById(R.id.tengxun).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        MapUtil.openTencentMap(QCYMapActivity.this,
                                Double.parseDouble(naviStartLat), Double.parseDouble(naviStartLng),
                                naviStartName,
                                Double.parseDouble(naviEndLat),
                                Double.parseDouble(naviEndLng), naviEndName);
                    } catch (Exception e) {

                    }
                }
            });
        }
        if (isHaveMap == 0) {
            ToastUtils.showShort("你没有安装任何地图，请先下载地图");
            dialog.cancel();
            dialog.dismiss();
            dialog = null;
        }
    }


    //我的位置
    private DPoint myPoint;

    private void initView() {
        mActivityQcymapDeleteSearch = (ImageView) findViewById(R.id.activity_qcymap_delete_search);
        mActivityQcymapDeleteSearch.setOnClickListener(this);
        mActivityQcymapTuijianqiyeChakanluxian = (TextView) findViewById(R.id.activity_qcymap_tuijianqiye_chakanluxian);
        mActivityQcymapTuijianqiyeJuli = (TextView) findViewById(R.id.activity_qcymap_tuijianqiye_juli);
        mActivityQcymapTuijianqiyeTuijian = (ImageView) findViewById(R.id.activity_qcymap_tuijianqiye_tuijian);
        mActivityQcymapYijiandaohang = (ImageView) findViewById(R.id.activity_qcymap_yijiandaohang);
        mActivityQcymapYijiandaohang.setOnClickListener(this);
        mActivityQcymapYijiandaohangTitle = (TextView) findViewById(R.id.activity_qcymap_yijiandaohang_title);
        mActivityQcymapYijiandaohangContent = (TextView) findViewById(R.id.activity_qcymap_yijiandaohang_content);
        mActivityQcymapYijiandaohangNavi = (ImageView) findViewById(R.id.activity_qcymap_yijiandaohang_navi);
        mActivityQcymapYijiandaohangNavi.setOnClickListener(this);
        mActivityQcymapYijiandaohangBack = (ImageView) findViewById(R.id.activity_qcymap_yijiandaohang_back);
        mActivityQcymapYijiandaohangBack.setOnClickListener(this);
        mActivityQcymapYijiandaohangLayout = (ConstraintLayout) findViewById(R.id.activity_qcymap_yijiandaohang_layout);
        gps_presenter = new GPSPresenter(this, this);
        mActivityQcymapTuijianqiyeAddress = (TextView) findViewById(R.id.activity_qcymap_tuijianqiye_address);
        mActivityQcymapTuijianqiyePhone = (TextView) findViewById(R.id.activity_qcymap_tuijianqiye_phone);
        mActivityQcymapBack = (ImageView) findViewById(R.id.activity_qcymap_back);
        mActivityQcymapBack.setOnClickListener(this);
        mActivityQcymapSearchText = (EditText) findViewById(R.id.activity_qcymap_searchText);
        mActivityQcymapJieguoSearchCount = (TextView) findViewById(R.id.activity_qcymap_jieguo_search_count);
        mActivityQcymapTuijianqiyeImage = (ImageView) findViewById(R.id.activity_qcymap_tuijianqiye_image);
        mActivityQcymapTuijianqiyeName = (TextView) findViewById(R.id.activity_qcymap_tuijianqiye_name);
        mActivityQcymapTuijianqiyeLayout = (ConstraintLayout) findViewById(R.id.activity_qcymap_tuijianqiye_layout);
        mActivityQcymapTuijianqiyeLayout.setOnClickListener(this);
        mActivityQcymapJieguoTotalCount = (TextView) findViewById(R.id.activity_qcymap_jieguo_total_count);
        mActivityQcymapDiqu = (TextView) findViewById(R.id.activity_qcymap_diqu);
        mActivityQcymapDiquLayout = (ConstraintLayout) findViewById(R.id.activity_qcymap_diqu_layout);
        mActivityQcymapDiquLayout.setOnClickListener(this);
        mActivityQcymapQiyeleixing = (TextView) findViewById(R.id.activity_qcymap_qiyeleixing);
        mActivityQcymapQiyeleixingLayout = (ConstraintLayout) findViewById(R.id.activity_qcymap_qiyeleixing_layout);
        mActivityQcymapQiyeleixingLayout.setOnClickListener(this);
        mBottomSheetLayout = (LinearLayoutCompat) findViewById(R.id.activity_qcymap_bottom_sheet_layout);
        mActivityQcymapJieguoLayout = (ConstraintLayout) findViewById(R.id.activity_qcymap_jieguo_layout);
        mActivityQcymapJieguoLayout.setOnClickListener(this);
        mActivityQcymapRecyclerView = (RecyclerView) findViewById(R.id.activity_qcymap_recyclerView);
        behavior = BottomSheetBehavior.from(mBottomSheetLayout);
        marketMapBeans = new ArrayList<>();
        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityQcymapRecyclerView.setLayoutManager(layoutManager);
        //创建适配器

        aMap.setOnMyLocationChangeListener(this);

        mActivityQcymapTuijianqiyeChakanluxian.setOnClickListener(this);

        mActivityQcymapJoinUs = (TextView) findViewById(R.id.activity_qcymap_join_us);
        mActivityQcymapJoinUs.setOnClickListener(this);
    }


    private void addCheckedMarker(Marker marker, LatLng latLng, String name, MarketMapBean bean) {

        if (!ObjectUtils.isEmpty(marker)) {
            marker.remove();
        }

        MarkerOptions options = new MarkerOptions();
        View inflate = getLayoutInflater().inflate(R.layout.item_marker, null);
        ((ImageView) inflate.findViewById(R.id.item_marker_pop)).
                setImageDrawable(getResources().getDrawable(R.mipmap.marker_checked));
        ((TextView) inflate.findViewById(R.id.item_marker_name)).setText(name);
        ((TextView) inflate.findViewById(R.id.item_marker_name)).setTextColor(getResources().getColor(R.color.baise));
        options.icon(BitmapDescriptorFactory.fromView(inflate));
        options.position(latLng);
        Marker marker1 = aMap.addMarker(options);
        marker1.setObject(bean);

        marker1.setToTop();

        if (!ObjectUtils.isEmpty(preMarker)) {
            if (isFirstAddTuijianDianpu) {
                preMarker.remove();
            } else
                addUnCheckedMarker(preMarker, preLatLng, preCompanyName, preMarketMapBean);
        }
        preMarker = marker1;
        preLatLng = latLng;
        preCompanyName = name;
        preMarketMapBean = bean;

//        tuijianMarket = bean;
//        tuijianMarketMarker = marker1;
//        tuijianMarketLatng = latLng;
//        tuijianCompanyName = name;

    }

    private void addUnCheckedMarker(Marker marker, LatLng latLng, String name, MarketMapBean bean) {
        marker.remove();
        MarkerOptions options = new MarkerOptions();
        View inflate = getLayoutInflater().inflate(R.layout.item_marker, null);
        ((ImageView) inflate.findViewById(R.id.item_marker_pop)).
                setImageDrawable(getResources().getDrawable(R.mipmap.marker_unchecked));
        ((TextView) inflate.findViewById(R.id.item_marker_name)).setText(name);
        ((TextView) inflate.findViewById(R.id.item_marker_name)).setTextColor(getResources().getColor(R.color.chunhongse));
        options.icon(BitmapDescriptorFactory.fromView(inflate));
        options.position(latLng);
        Marker marker1 = aMap.addMarker(options);
        marker1.setIcon(BitmapDescriptorFactory.fromView(inflate));
        marker1.setObject(bean);
    }

    //记录的上一个点击的marker
    private Marker preMarker;
    private LatLng preLatLng;
    private String preCompanyName;
    private MarketMapBean preMarketMapBean;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
//    public AMapLocationListener mLocationListener ;

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    /**
     * 直接跳转至位置信息设置界面
     */
    public static void openLocation(Context context) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        context.startActivity(intent);
    }


    private GPSPresenter gps_presenter;


    private void dingwei() {

        try {

            mLocationClient = new AMapLocationClient(getApplicationContext());

            mLocationClient.setLocationListener(this);
            //初始化AMapLocationClientOption对象
            mLocationOption = new AMapLocationClientOption();

            //获取一次定位结果：
            //该方法默认为false。
            mLocationOption.setOnceLocation(true);
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);

            //给定位客户端对象设置定位参数
            mLocationClient.setLocationOption(mLocationOption);

            //启动定位
            mLocationClient.startLocation();

        } catch (Exception e) {

            ToastUtils.showShort("定位失败");

        }
    }

    private String upLoadQiyeleixing;
    private String upLoadCompanyName;

    private boolean isRefresh = true;
    private int pageNo;

    private int searchCount;

    private void addData() {

        pageNo++;
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.QUERYDYEMAPLIST)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("companyName", upLoadCompanyName)
                .params("area", upLoadArea)
                .params("province", upLoadProvice)
                .params("city", upLoadCity)
                .params("customerClass", upLoadQiyeleixing)
                .params("pageNo", pageNo)
                .params("pageSize", 20);

        DialogStringCallback stringCallback = new DialogStringCallback(this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("QUERYDYEMAPLIST", response.body());
                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {

                            JSONArray marketMap = jsonObject.getJSONArray("data");


                            if (ObjectUtils.isEmpty(marketMap)) {

                                if (isRefresh) {
                                    ToastUtils.showShort("没有查询到结果");
                                    mActivityQcymapTuijianqiyeLayout.setVisibility(View.GONE);
                                    mActivityQcymapJieguoSearchCount.setText(0 + "");
                                    mActivityQcymapJieguoTotalCount.setText("(共0条)");
                                    marketMapBeans.clear();
                                    adapter.notifyDataSetChanged();
                                    aMap.clear();

                                    if (!ObjectUtils.isEmpty(shareMarker)) {
                                        LatLng latLng = new LatLng(Double.parseDouble(shareAddress.getLat()),
                                                Double.parseDouble(shareAddress.getLot()));
                                        shareMarker = aMap.addMarker(new MarkerOptions().position(latLng).title(shareAddress.getTitle()).snippet(""));
                                        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                                        shareMarker.setToTop();
                                    }

                                }
                                adapter.loadMoreEnd();
                                return;
                            }

                            mActivityQcymapTuijianqiyeLayout.setVisibility(View.VISIBLE);

                            List<MarketMapBean> beans =
                                    JSONObject.parseArray(marketMap.toString(), MarketMapBean.class);
                            mActivityQcymapJieguoTotalCount.setText("(共" + jsonObject.getString("totalCount") + "条)");

                            searchCount += beans.size();

                            mActivityQcymapJieguoSearchCount.setText(searchCount + "");


                            marketMapBeans.addAll(beans);

                            addMarker(beans);


                            if (isRefresh) {
                                marketMapBeans.clear();
                                marketMapBeans.addAll(beans);
                                adapter.setNewData(marketMapBeans);
                                adapter.loadMoreComplete();
                                isRefresh = false;
                                adapter.disableLoadMoreIfNotFullPage();

                                return;
                            }
                            adapter.setNewData(marketMapBeans);
                            adapter.loadMoreComplete();

                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(QCYMapActivity.this, request, this);
                            return;
                        }
                        ToastUtils.showShort(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                ToastUtils.showShort(getResources().getString(R.string.NETEXCEPTION));
            }
        };

        request.execute(stringCallback);
    }

    private boolean isFirstAddTuijianDianpu = true;

    private void addMarker(List<MarketMapBean> beans) {

        if (isFirstAddTuijianDianpu) {
            tuijianMarket = beans.get(0);
        }

        for (int i = 0; i < beans.size(); i++) {
            if (StringUtils.isEmpty(beans.get(i).getLatitude()) || StringUtils.isEmpty(beans.get(i).getLongitude())) {
                //只要有一个为空，不操作
            } else {

                LatLng latLng = new LatLng(Double.parseDouble(beans.get(i).getLatitude()), Double.parseDouble(beans.get(i).getLongitude()));

                if (isFirstAddTuijianDianpu && i == 0) {
//                    tuijianMarketMarker = marker;
//                    tuijianMarketLatng = latLng;
//                    tuijianCompanyName = beans.get(i).getCompanyName();
                    //显示排名第一的店铺
                    addCheckedMarker(null, latLng, beans.get(i).getCompanyName(), beans.get(i));

                } else {
                    MarkerOptions options = new MarkerOptions();
//                options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.jingpai_weikaishi));

                    View inflate = getLayoutInflater().inflate(R.layout.item_marker, null);
                    ((TextView) inflate.findViewById(R.id.item_marker_name)).setText(beans.get(i).getCompanyName());

                    options.icon(BitmapDescriptorFactory.fromView(inflate));
                    options.position(latLng).title(beans.get(i).getCompanyName()).snippet(beans.get(i).getCompanyName());
                    Marker marker = aMap.addMarker(options);
                    marker.setObject(beans.get(i));
                }
            }
        }

        if (isFirstAddTuijianDianpu) {

            if (canNavication) {
                aMap.addMarker(new MarkerOptions().position(new LatLng(
                        Double.parseDouble(shareAddress.getLat()), Double.parseDouble(shareAddress.getLot())))
                        .title(shareAddress.getTitle()));
            }


            setTuijianMarketInfo(new LatLng(Double.parseDouble(tuijianMarket.getLatitude()),
                    Double.parseDouble(tuijianMarket.getLongitude())));

            isFirstAddTuijianDianpu = false;
        }


    }

    private PopupWindow popupWindow;
    private RecyclerView provinceRecyclerview;
    private RecyclerView cityRecyclerview;
    private RecyclerView areaRecyclerview;
    private RecyclerView historyRecyclerview;

    private List<CityBean> provinceDatas = new ArrayList<>();
    private List<CityBean.CitiesBean> cityDatas = new ArrayList<>();
    private List<CityBean.CitiesBean.CountiesBean> areaDatas = new ArrayList<>();
    private List<HistoryBean> historyDatas = new ArrayList<>();

    private ProvinceAdapter provinceAdapter;
    private CityAdapter cityAdapter;
    private AreaAdapter areaAdapter;
    private HistoryAdapter historyAdapter;

    ConstraintLayout addressHistoryLayout;

    private void showPop() {

        if (!ObjectUtils.isEmpty(popupWindow)) {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            } else {
//                WindowManager.LayoutParams lp = getWindow().getAttributes();
//                lp.alpha = 0.6f;
//                getWindow().setAttributes(lp);
//                        mChoiceHuatiSlidingTabLayout.setVisibility(View.INVISIBLE);
                popupWindow.showAsDropDown(mActivityQcymapDiquLayout);
//                mChoiceHuatiXiala.setImageDrawable(getResources().getDrawable(R.mipmap.shangjiantou_hongse));
                if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    //展开后设置收缩
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                }
            }
            return;
        }

        View view = LayoutInflater.from(this).inflate(R.layout.popwindow_address, null);

        addressHistoryLayout = (ConstraintLayout) view.findViewById(R.id.popwindow_address_history_layout);


        provinceAdapter = new ProvinceAdapter(R.layout.item_city_info, provinceDatas);
        cityAdapter = new CityAdapter(R.layout.item_city_info, cityDatas);
        areaAdapter = new AreaAdapter(R.layout.item_city_info, areaDatas);
        historyAdapter = new HistoryAdapter(R.layout.item_city_search_history, historyDatas);

        provinceRecyclerview = view.findViewById(R.id.popwindow_address_recyclerview_province);
        cityRecyclerview = view.findViewById(R.id.popwindow_address_recyclerview_city);
        areaRecyclerview = view.findViewById(R.id.popwindow_address_recyclerview_area);
        historyRecyclerview = view.findViewById(R.id.popwindow_address_history_recyclerview);

        LinearLayoutManager proviceLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager cityLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager areaLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        GridLayoutManager historyGridLayoutManager = new GridLayoutManager(this, 3);

        provinceRecyclerview.setLayoutManager(proviceLinearLayoutManager);
        cityRecyclerview.setLayoutManager(cityLinearLayoutManager);
        areaRecyclerview.setLayoutManager(areaLinearLayoutManager);
        historyRecyclerview.setLayoutManager(historyGridLayoutManager);

        provinceRecyclerview.setAdapter(provinceAdapter);
        cityRecyclerview.setAdapter(cityAdapter);
        areaRecyclerview.setAdapter(areaAdapter);
        historyRecyclerview.setAdapter(historyAdapter);


        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, (int) (ScreenUtils.getScreenHeight() + BarUtils.getStatusBarHeight() - getResources().getDimension(R.dimen.x280)));
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);//要先让popupwindow获得焦点，才能正确获取popupwindow的状态
        popupWindow.showAsDropDown(mActivityQcymapDiquLayout);


        if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            //展开后设置收缩
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        }

        addCityInfo();

        initSearchHistory();

        provinceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CityBean cityBean = (CityBean) adapter.getData().get(position);
                for (int i = 0; i < provinceDatas.size(); i++) {

                    provinceDatas.get(i).setCheck(false);

                }
                provinceDatas.get(position).setCheck(true);

                cityDatas.clear();
                cityDatas.addAll(cityBean.getCities());

                for (int i = 0; i < cityDatas.size(); i++) {

                    cityDatas.get(i).setCheck(false);
                }
                cityAdapter.setNewData(cityDatas);

                areaDatas.clear();
                areaAdapter.notifyDataSetChanged();

                provinceAdapter.notifyDataSetChanged();

                if (StringUtils.equals("全国", cityBean.getAreaName())) {

                    upLoadProvice = "";
                    upLoadCity = "";
                    upLoadArea = "";
                    mActivityQcymapDiqu.setText("全国");
                    addSearchHistory(new HistoryBean("", "", ""));
                    reLoadData();
                } else {
                    choiceProvince = cityBean.getAreaName();
                }

            }
        });

        cityAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                CityBean.CitiesBean cityBean = (CityBean.CitiesBean) adapter.getData().get(position);

                for (int i = 0; i < cityDatas.size(); i++) {

                    cityDatas.get(i).setCheck(false);

                }
                cityDatas.get(position).setCheck(true);

                areaDatas.clear();
                areaDatas.addAll(cityBean.getCounties());

                for (int i = 0; i < areaDatas.size(); i++) {

                    areaDatas.get(i).setCheck(false);
                }

                areaAdapter.setNewData(areaDatas);

                cityAdapter.notifyDataSetChanged();

                if (StringUtils.equals("全省", cityBean.getAreaName())) {
                    upLoadProvice = choiceProvince;
                    upLoadCity = "";
                    upLoadArea = "";
                    mActivityQcymapDiqu.setText(choiceProvince);

                    addSearchHistory(new HistoryBean(choiceProvince, "", ""));
                    reLoadData();

                } else {
                    choiceCity = cityBean.getAreaName();
                }

            }
        });

        areaAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CityBean.CitiesBean.CountiesBean countiesBean = (CityBean.CitiesBean.CountiesBean) adapter.getData().get(position);
                for (int i = 0; i < areaDatas.size(); i++) {

                    areaDatas.get(i).setCheck(false);
                }
                areaDatas.get(position).setCheck(true);

                areaAdapter.notifyDataSetChanged();

                upLoadProvice = choiceProvince;
                upLoadCity = choiceCity;

                if (StringUtils.equals("全市", countiesBean.getAreaName())) {
                    mActivityQcymapDiqu.setText(choiceCity);
                    upLoadArea = "";
                    reLoadData();

                } else {
                    mActivityQcymapDiqu.setText(countiesBean.getAreaName());
                    upLoadArea = countiesBean.getAreaName();
                    reLoadData();
                }

                addSearchHistory(new HistoryBean(upLoadProvice, upLoadCity, upLoadArea));
            }
        });

        historyAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                HistoryBean bean = (HistoryBean) adapter.getData().get(position);
                TextView byPosition = (TextView) adapter.getViewByPosition(historyRecyclerview, position, R.id.item_city_search_history_name);

                upLoadProvice = bean.getProvince();
                upLoadCity = bean.getCity();
                upLoadArea = bean.getArea();

                mActivityQcymapDiqu.setText(byPosition.getText());

                reLoadData();
            }
        });

    }

    //选好了的省市信息
    private String choiceProvince;
    private String choiceCity;


    private void initSearchHistory() {

        String s = SPUtils.getInstance("searchCityHistory").getString("history");


        if (StringUtils.isEmpty(s)) {
            addressHistoryLayout.setVisibility(View.GONE);
            return;
        }

        addressHistoryLayout.setVisibility(View.VISIBLE);

        JSONArray array = JSONArray.parseArray(s);

        List<HistoryBean> beans = JSONObject.parseArray(array.toJSONString(), HistoryBean.class);

        for (int i = 0; i < beans.size(); i++) {
            if (StringUtils.isEmpty(beans.get(i).getProvince())) {
                beans.get(i).setProvince("");
            }
            if (StringUtils.isEmpty(beans.get(i).getCity())) {
                beans.get(i).setCity("");
            }
            if (StringUtils.isEmpty(beans.get(i).getArea())) {
                beans.get(i).setArea("");
            }
        }

        historyDatas.addAll(beans);

        historyAdapter.setNewData(historyDatas);


    }


    //搜索的历史记录

    private void addSearchHistory(HistoryBean historyBean) {
        addressHistoryLayout.setVisibility(View.VISIBLE);

        for (int i = 0; i < historyDatas.size(); i++) {
            if (historyBean.equals(historyDatas.get(i))) {
                historyDatas.remove(i);
            }
        }

        if (historyDatas.size() == 6) {
            historyDatas.remove(5);
        }

        historyDatas.add(0, historyBean);

        historyAdapter.setNewData(historyDatas);

        SPUtils.getInstance("searchCityHistory").put("history", historyDatas.toString());


    }


    //解析json获取的完整数据
    ArrayList<CityBean> allCityBeans = new ArrayList<>();

    private void addCityInfo() {

        try {
            String json = ConvertUtils.toString(activity.getAssets().open("city2.json"));
            allCityBeans.addAll(JSON.parseArray(json, CityBean.class));
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (allCityBeans.size() > 0) {
            provinceDatas.addAll(allCityBeans);
            cityDatas.addAll(provinceDatas.get(1).getCities());
            areaDatas.addAll(provinceDatas.get(1).getCities().get(0).getCounties());
            provinceDatas.get(1).setCheck(true);
            provinceDatas.get(1).getCities().get(0).setCheck(true);
            cityAdapter.setNewData(cityDatas);
            areaAdapter.setNewData(areaDatas);
            provinceAdapter.setNewData(provinceDatas);
            choiceProvince = "北京市";
            choiceCity = "北京市";
        }
    }

    MyLocationStyle myLocationStyle;

    private void dingweiwode() {

        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        // 连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        //设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

        myLocationStyle.showMyLocation(false);

        aMap.moveCamera(CameraUpdateFactory.zoomTo(10));
    }


    private MyAddress myAddress;

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息

//                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude())));
//                aMap.moveCamera(CameraUpdateFactory.zoomTo(aMap.getMaxZoomLevel()));
//                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude()), aMap.getMaxZoomLevel() - 5));


                myPoint = new DPoint(amapLocation.getLatitude(), amapLocation.getLongitude());
                adapter.setMyPoint(myPoint);

                myAddress = new MyAddress();
                myAddress.setLat(String.valueOf(amapLocation.getLatitude()));
                myAddress.setLot(String.valueOf(amapLocation.getLongitude()));
                myAddress.setTitle(amapLocation.getAddress());

                if (!canNavication) {
                    mActivityQcymapDiqu.setText(amapLocation.getDistrict());

                    upLoadProvice = amapLocation.getProvince();
                    upLoadCity = amapLocation.getCity();
                    upLoadArea = amapLocation.getDistrict();
                    reLoadData();

                }

            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    //推荐的企业
    private MarketMapBean tuijianMarket;
//    private LatLng tuijianMarketLatng;
//    private Marker tuijianMarketMarker;
//    private String tuijianCompanyName;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.activity_qcymap_jieguo_layout:
                if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {

                    //展开后设置收缩
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                } else {

                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                break;
            case R.id.activity_qcymap_diqu_layout:
                showPop();

                break;
            case R.id.activity_qcymap_qiyeleixing_layout:
//                choiceMarketType(mActivityQcymapQiyeleixing);
                showQiyeLeixingPop();


                break;
            case R.id.activity_qcymap_tuijianqiye_layout:

                jumpToDianpu(tuijianMarket);

                break;
            case R.id.activity_qcymap_back:

                if (!ObjectUtils.isEmpty(popupWindow) && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    return;
                }
                if (!ObjectUtils.isEmpty(typePopupWindow) && typePopupWindow.isShowing()) {
                    typePopupWindow.dismiss();
                    return;
                }

                KeyboardUtils.hideSoftInput(QCYMapActivity.this);
                finish();
                break;
            case R.id.activity_qcymap_yijiandaohang:

                mActivityQcymapYijiandaohang.setVisibility(View.GONE);
                mActivityQcymapYijiandaohangTitle.setVisibility(View.VISIBLE);
                mActivityQcymapYijiandaohangContent.setVisibility(View.VISIBLE);
                mActivityQcymapYijiandaohangNavi.setVisibility(View.VISIBLE);
                mActivityQcymapYijiandaohangBack.setVisibility(View.VISIBLE);
                mActivityQcymapYijiandaohangLayout.setBackground(getResources().getDrawable(R.drawable.background_chanpinfenlei_unchecked));

                break;
            case R.id.activity_qcymap_yijiandaohang_navi:

                if (canNavication && !ObjectUtils.isEmpty(myAddress) && !ObjectUtils.isEmpty(shareAddress)) {
                    naviStartLat = myAddress.getLat();
                    naviStartLng = myAddress.getLot();
                    naviStartName = myAddress.getTitle();
                    naviEndLat = shareAddress.getLat();
                    naviEndLng = shareAddress.getLot();
                    naviEndName = shareAddress.getTitle();
                    showNaviDialog();
                } else {
                    ToastUtils.showShort("该公司有地址信息有误！");
                }

                break;
            case R.id.activity_qcymap_yijiandaohang_back:

                mActivityQcymapYijiandaohang.setVisibility(View.VISIBLE);
                mActivityQcymapYijiandaohangTitle.setVisibility(View.GONE);
                mActivityQcymapYijiandaohangContent.setVisibility(View.GONE);
                mActivityQcymapYijiandaohangNavi.setVisibility(View.GONE);
                mActivityQcymapYijiandaohangBack.setVisibility(View.GONE);
                mActivityQcymapYijiandaohangLayout.setBackground(getResources().getDrawable(R.color.trans));
                break;
            case R.id.activity_qcymap_tuijianqiye_chakanluxian:
                if (!ObjectUtils.isEmpty(tuijianMarket) && !ObjectUtils.isEmpty(myAddress)) {
                    naviStartLat = myAddress.getLat();
                    naviStartLng = myAddress.getLot();
                    naviStartName = myAddress.getTitle();
                    naviEndLat = tuijianMarket.getLatitude();
                    naviEndLng = tuijianMarket.getLongitude();
                    naviEndName = tuijianMarket.getCompanyName();
                    showNaviDialog();
                } else {
                    ToastUtils.showShort("数据有误！");

                }
                break;
            case R.id.activity_qcymap_delete_search:
                break;
            case R.id.activity_qcymap_join_us:
                Intent intent = new Intent(this,WebActivity.class);
                intent.putExtra("webUrl","https://c.eqxiu.com/s/pJjgBfWy");
                ActivityUtils.startActivity(intent);
                break;
        }
    }


    private void jumpToDianpu(MarketMapBean bean) {
        if (!ObjectUtils.isEmpty(bean)) {

            if (StringUtils.equals("market", bean.getFrom())) {
                Intent intent = new Intent(this, KFSCXiangqingActivity.class);
                if (!ObjectUtils.isEmpty(bean.getMarketId())) {
                    intent.putExtra("id", bean.getMarketId());
                    ActivityUtils.startActivity(intent);
                }

            } else {
                try {
                    Intent intent = new Intent(this, NoMarketCompanyActivity.class);

                    CompanyIntroduceBean companyIntroduceBean = new CompanyIntroduceBean();

                    companyIntroduceBean.setContact(bean.getMarket().getContact());
                    companyIntroduceBean.setPhone(bean.getMarket().getPhone());

                    companyIntroduceBean.setAddress(bean.getAddress());

                    companyIntroduceBean.setIntroduce(bean.getMarket().getDescription());

                    companyIntroduceBean.setCompanyName(bean.getCompanyName());

                    companyIntroduceBean.setLat(bean.getLatitude());
                    companyIntroduceBean.setLng(bean.getLongitude());

                    intent.putExtra("companyIntroduceBean", companyIntroduceBean);
                    ActivityUtils.startActivity(intent);

                } catch (Exception e) {
                    ToastUtils.showShort("店铺信息有误!");
                }

            }
        }
    }

    //企业类型
    private PopupWindow typePopupWindow;

    private void showQiyeLeixingPop() {

        if (!ObjectUtils.isEmpty(typePopupWindow)) {
            typePopupWindow.setFocusable(true);//要先让popupwindow获得焦点，才能正确获取popupwindow的状态
            if (typePopupWindow.isShowing()) {
                typePopupWindow.dismiss();
            } else {
//                WindowManager.LayoutParams lp = getWindow().getAttributes();
//                lp.alpha = 0.6f;
//                getWindow().setAttributes(lp);
//                        mChoiceHuatiSlidingTabLayout.setVisibility(View.INVISIBLE);
                typePopupWindow.showAsDropDown(mActivityQcymapDiquLayout);
//                mChoiceHuatiXiala.setImageDrawable(getResources().getDrawable(R.mipmap.shangjiantou_hongse));
                if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    //展开后设置收缩
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                }
            }
            return;
        }

        View view = LayoutInflater.from(this).inflate(R.layout.pop_qcymap_qiyeleixing, null);

        TextView all = (TextView) view.findViewById(R.id.pop_qcymap_qiyeleixing_all);
        TextView ranliao = (TextView) view.findViewById(R.id.pop_qcymap_qiyeleixing_ranliao);
        TextView zhuji = (TextView) view.findViewById(R.id.pop_qcymap_qiyeleixing_zhuji);
        TextView yinran = (TextView) view.findViewById(R.id.pop_qcymap_qiyeleixing_yinran);
        TextView shebei = (TextView) view.findViewById(R.id.pop_qcymap_qiyeleixing_shebei);
        TextView huaxuepin = (TextView) view.findViewById(R.id.pop_qcymap_qiyeleixing_huaxuepin);

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivityQcymapQiyeleixing.setText("全部");
                upLoadQiyeleixing = "";
                setQiyeleixingBold();
                all.setTextColor(getResources().getColor(R.color.chunhongse));
                ranliao.setTextColor(getResources().getColor(R.color.erjibiaoti));
                zhuji.setTextColor(getResources().getColor(R.color.erjibiaoti));
                yinran.setTextColor(getResources().getColor(R.color.erjibiaoti));
                shebei.setTextColor(getResources().getColor(R.color.erjibiaoti));
                huaxuepin.setTextColor(getResources().getColor(R.color.erjibiaoti));
                reLoadData();
            }
        });

        ranliao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivityQcymapQiyeleixing.setText("染料供应商");
                upLoadQiyeleixing = "染料";
                setQiyeleixingBold();
                all.setTextColor(getResources().getColor(R.color.erjibiaoti));
                ranliao.setTextColor(getResources().getColor(R.color.chunhongse));
                zhuji.setTextColor(getResources().getColor(R.color.erjibiaoti));
                yinran.setTextColor(getResources().getColor(R.color.erjibiaoti));
                shebei.setTextColor(getResources().getColor(R.color.erjibiaoti));
                huaxuepin.setTextColor(getResources().getColor(R.color.erjibiaoti));
                reLoadData();
            }
        });

        zhuji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivityQcymapQiyeleixing.setText("助剂供应商");
                upLoadQiyeleixing = "助剂";
                setQiyeleixingBold();
                all.setTextColor(getResources().getColor(R.color.erjibiaoti));
                ranliao.setTextColor(getResources().getColor(R.color.erjibiaoti));
                zhuji.setTextColor(getResources().getColor(R.color.chunhongse));
                yinran.setTextColor(getResources().getColor(R.color.erjibiaoti));
                shebei.setTextColor(getResources().getColor(R.color.erjibiaoti));
                huaxuepin.setTextColor(getResources().getColor(R.color.erjibiaoti));
                reLoadData();
            }
        });
        yinran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivityQcymapQiyeleixing.setText("印染企业");
                upLoadQiyeleixing = "印染";
                setQiyeleixingBold();
                all.setTextColor(getResources().getColor(R.color.erjibiaoti));
                ranliao.setTextColor(getResources().getColor(R.color.erjibiaoti));
                zhuji.setTextColor(getResources().getColor(R.color.erjibiaoti));
                yinran.setTextColor(getResources().getColor(R.color.chunhongse));
                shebei.setTextColor(getResources().getColor(R.color.erjibiaoti));
                huaxuepin.setTextColor(getResources().getColor(R.color.erjibiaoti));
                reLoadData();
            }
        });
        shebei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivityQcymapQiyeleixing.setText("设备仪器企业");
                upLoadQiyeleixing = "设备仪器";
                setQiyeleixingBold();
                all.setTextColor(getResources().getColor(R.color.erjibiaoti));
                ranliao.setTextColor(getResources().getColor(R.color.erjibiaoti));
                zhuji.setTextColor(getResources().getColor(R.color.erjibiaoti));
                yinran.setTextColor(getResources().getColor(R.color.erjibiaoti));
                shebei.setTextColor(getResources().getColor(R.color.chunhongse));
                huaxuepin.setTextColor(getResources().getColor(R.color.erjibiaoti));
                reLoadData();
            }
        });
        huaxuepin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivityQcymapQiyeleixing.setText("化学品企业");
                upLoadQiyeleixing = "化学品";
                setQiyeleixingBold();
                all.setTextColor(getResources().getColor(R.color.erjibiaoti));
                ranliao.setTextColor(getResources().getColor(R.color.erjibiaoti));
                zhuji.setTextColor(getResources().getColor(R.color.erjibiaoti));
                yinran.setTextColor(getResources().getColor(R.color.erjibiaoti));
                shebei.setTextColor(getResources().getColor(R.color.erjibiaoti));
                huaxuepin.setTextColor(getResources().getColor(R.color.chunhongse));
                reLoadData();
            }
        });


        typePopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        typePopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        typePopupWindow.setOutsideTouchable(true);
        typePopupWindow.setTouchable(true);
        typePopupWindow.showAsDropDown(mActivityQcymapDiquLayout);

    }

    private void setQiyeleixingBold() {

        mActivityQcymapQiyeleixing.setTextColor(getResources().getColor(R.color.erjibiaoti));
        TextPaint tp = mActivityQcymapQiyeleixing.getPaint();
        tp.setFakeBoldText(true);

    }

    //查询的省市区信息
    private String upLoadProvice;
    private String upLoadCity;
    private String upLoadArea;

    private void reLoadData() {

//        if(!ObjectUtils.isEmpty(tuijianMarket)&&!ObjectUtils.isEmpty(tuijianMarketMarker)){
//            tuijianMarketMarker.destroy();
//        }

        isRefresh = true;
        searchCount = 0;
        marketMapBeans.clear();
        pageNo = 0;
        isFirstAddTuijianDianpu = true;

        aMap.clear();

        if (!ObjectUtils.isEmpty(popupWindow) && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        if (!ObjectUtils.isEmpty(typePopupWindow) && typePopupWindow.isShowing()) {
            typePopupWindow.dismiss();
        }
        addData();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
        //释放资源
        if (gps_presenter != null) {
            gps_presenter.onDestroy();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    //我的位置变化
    @Override
    public void onMyLocationChange(Location location) {
        myLocationStyle.showMyLocation(true);
//        LatLog2Address(location.getLatitude(), location.getLongitude());


    }


    @Override
    public void gpsSwitchState(boolean gpsOpen) {
        if (gpsOpen) {

            //启动定位
            mLocationClient.startLocation();
        } else {

        }
    }

    GeocodeSearch geocodeSearch;


    private void LatLog2Address(double lat, double lng) {
        geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(this);
        LatLonPoint latLng = new LatLonPoint(lat, lng);
        RegeocodeQuery query = new RegeocodeQuery(latLng, 200, GeocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(query);

    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        RegeocodeAddress address = regeocodeResult.getRegeocodeAddress();
        upLoadProvice = address.getProvince();
        upLoadCity = address.getCity();
        upLoadArea = address.getDistrict();

        reLoadData();
        if (!StringUtils.isEmpty(address.getDistrict())) {
            mActivityQcymapDiqu.setText(address.getDistrict());

        } else {
            if (!StringUtils.isEmpty(address.getCity())) {
                mActivityQcymapDiqu.setText(address.getCity());
            } else {

                if (!StringUtils.isEmpty(address.getProvince())) {
                    mActivityQcymapDiqu.setText(address.getProvince());
                } else {
                    mActivityQcymapDiqu.setText("地址有误");
                }

            }
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
    }

}
