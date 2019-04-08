package com.company.qcy.ui.activity.pengyouquan;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.maputil.MapUtil;
import com.company.qcy.bean.pengyouquan.MyAddress;

public class MapActivity extends AppCompatActivity implements View.OnClickListener {


    private MyAddress address;
    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private MapView mMapView;
    /**
     * 上海七彩云电子商务有限公司
     */
    private TextView mActivityMapTitle;
    /**
     * 上海市钦州北路1199号88幢4层
     */
    private TextView mActivityMapContent;
    private ImageView mActivityMapNavi;
    AMap aMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        address = getIntent().getParcelableExtra("address");

        mMapView = (MapView) findViewById(R.id.activity_map_mapview);
        mMapView.onCreate(savedInstanceState);

        initView();

    }

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mActivityMapTitle = (TextView) findViewById(R.id.activity_map_title);
        mActivityMapContent = (TextView) findViewById(R.id.activity_map_content);
        mActivityMapNavi = (ImageView) findViewById(R.id.activity_map_navi);
        mActivityMapNavi.setOnClickListener(this);
        mToolbarTitle.setText("查看位置");
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        LatLng latLng = new LatLng(Double.parseDouble(address.getLat()), Double.parseDouble(address.getLot()));
        aMap.moveCamera(CameraUpdateFactory.zoomTo(aMap.getMaxZoomLevel() - 5));
        Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title(address.getTitle()).snippet(""));
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
        mActivityMapTitle.setText(address.getTitle());
        mActivityMapContent.setText(address.getContent());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_map_navi:
                showPop();
                break;
        }
    }


    //选择地图
    private Dialog dialog;

    private void showPop() {
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
                        MapUtil.openBaiDuNavi(MapActivity.this, 0, 0, null,
                                Double.parseDouble(address.getLat()), Double.parseDouble(address.getLot()), address.getTitle());
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
                        MapUtil.openGaoDeNavi(MapActivity.this, 0, 0, null,
                                Double.parseDouble(address.getLat()), Double.parseDouble(address.getLot()), address.getTitle());
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
                        MapUtil.openTencentMap(MapActivity.this, 0, 0, null,
                                Double.parseDouble(address.getLat()), Double.parseDouble(address.getLot()), address.getTitle());
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
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

}
