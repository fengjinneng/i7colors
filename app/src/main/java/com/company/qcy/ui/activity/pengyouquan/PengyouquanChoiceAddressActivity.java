package com.company.qcy.ui.activity.pengyouquan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.Utils.RecyclerviewDisplayDecoration;
import com.company.qcy.adapter.pengyouquan.FabuAddressAdapter;
import com.company.qcy.adapter.pengyouquan.ShurutishiAdapter;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.eventbus.MessageBean;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PengyouquanChoiceAddressActivity extends BaseActivity implements AMapLocationListener, View.OnClickListener, Inputtips.InputtipsListener {


    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;

    public PoiSearch poiSearch;
    public PoiSearch.Query query;
    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private RecyclerView recyclerView;
    /**
     * 搜索附近的位置
     */
    private EditText mActivityPengyouquanChoiceAddressSearch;
    private RecyclerView mShurutishiRecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengyouquan_choice_address);
        initView();

        mlocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(120000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.STORAGE, Permission.Group.LOCATION)
                .onGranted(permissions -> {
                    // Storage permission are allowed.
                    mlocationClient.startLocation();
                })
                .onDenied(permissions -> {
                    // Storage permission are not allowed.
                    ToastUtils.showShort("权限申请失败,您可能无法使用某些功能");
                })
                .start();

    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                cityName = amapLocation.getCity();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
//                LogUtils.e("sadsadasd",amapLocation.getStreet());
//                LogUtils.e("sadsadasd",amapLocation.getAoiName());
                query = new PoiSearch.Query(amapLocation.getAoiName(), "", amapLocation.getCityCode());
                query.setPageSize(30);// 设置每页最多返回多少条poiitem
                query.setPageNum(pageNo);//设置查询页码
                poiSearch = new PoiSearch(this, query);
                poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(amapLocation.getLatitude(),
                        amapLocation.getLongitude()), 2000));

                poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
                    @Override
                    public void onPoiSearched(PoiResult poiResult, int i) {
                        if (ObjectUtils.isEmpty(poiResult.getPois())) {
                            addressAdapter.loadMoreEnd();
                            return;
                        }
                        ArrayList<PoiItem> pois = poiResult.getPois();
                        datas.addAll(pois);
                        addressAdapter.setNewData(datas);
                        addressAdapter.loadMoreComplete();
//                        for (int j = 0; j < pois.size(); j++) {
//                            datas.add(pois.get(j));
                        //获取内容
//                            String text = pois.get(j).getSnippet();
//                            String name = pois.get(j).getAdName();
//                            String cityName = pois.get(j).getCityName();
//                            String area = pois.get(j).getBusinessArea();
//                            String provinceName = pois.get(j).getProvinceName();
//                            LogUtils.e("adadasadsa", pois.get(j).getTitle() + provinceName + cityName + name + text);
//                        }
                    }

                    @Override
                    public void onPoiItemSearched(PoiItem poiItem, int i) {

                    }
                });
                poiSearch.searchPOIAsyn();

            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private int pageNo = 1;


    private FabuAddressAdapter addressAdapter;
    private List<PoiItem> datas;

    private ShurutishiAdapter shurutishiAdapter;
    private List<Tip> shurutishiDatas;

    private void initView() {
        datas = new ArrayList<>();
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarTitle.setText("所在位置");
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.activity_pengyouquan_choice_address_recyclerview);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        addressAdapter = new FabuAddressAdapter(R.layout.item_fabu_address, datas);
        recyclerView.setAdapter(addressAdapter);
        recyclerView.addItemDecoration(new RecyclerviewDisplayDecoration(this));

        addHeadView();
        addressAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PoiItem poiItem = (PoiItem) adapter.getData().get(position);
                EventBus.getDefault().post(new MessageBean(MessageBean.Code.PENGYOUQUANCHOICEADDRESS, poiItem));
                finish();
            }
        });

        addressAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                pageNo++;
                query.setPageNum(pageNo);
                poiSearch.searchPOIAsyn();
            }
        }, recyclerView);

        mActivityPengyouquanChoiceAddressSearch = (EditText) findViewById(R.id.activity_pengyouquan_choice_address_search);

        mActivityPengyouquanChoiceAddressSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 1) {
                    recyclerView.setVisibility(View.GONE);
                    mShurutishiRecyclerview.setVisibility(View.VISIBLE);
                    shurutishi(s.toString());
                }else {
                    recyclerView.setVisibility(View.VISIBLE);
                    mShurutishiRecyclerview.setVisibility(View.GONE);
                }
            }
        });
        shurutishiDatas = new ArrayList<>();
        shurutishiAdapter = new ShurutishiAdapter(R.layout.item_fabu_address,shurutishiDatas);
        mShurutishiRecyclerview = (RecyclerView) findViewById(R.id.activity_pengyouquan_choice_address_shurutishi_recyclerview);
        mShurutishiRecyclerview.setAdapter(shurutishiAdapter);
        LinearLayoutManager manager2 = new LinearLayoutManager(this);
        manager2.setOrientation(LinearLayoutManager.VERTICAL);
        mShurutishiRecyclerview.setLayoutManager(manager2);
        mShurutishiRecyclerview.addItemDecoration(new RecyclerviewDisplayDecoration(this));
        shurutishiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Tip tip = (Tip) adapter.getData().get(position);
                EventBus.getDefault().post(new MessageBean(MessageBean.Code.PENGYOUQUANCHOICEADDRESSBYSEARCH, tip));
                finish();

            }
        });

//        shurutishiAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//                shurutishiPageNo++;
//            }
//        }, mShurutishiRecyclerview);
    }

    private int shurutishiPageNo;
    private String cityName;//用于进行城市搜索

    private void shurutishi(String s) {
        shurutishiPageNo = 1;
        //第二个参数传入null或者“”代表在全国进行检索，否则按照传入的city进行检索
        InputtipsQuery inputquery = new InputtipsQuery(s, cityName);
        inputquery.setCityLimit(true);//限制在当前城市

        Inputtips inputTips = new Inputtips(this, inputquery);
        inputTips.setInputtipsListener(this);
        inputTips.requestInputtipsAsyn();
    }


    private void addHeadView() {
        View inflate = getLayoutInflater().inflate(R.layout.item_fabu_address, null);
        TextView textView = (TextView) inflate.findViewById(R.id.item_fabu_address_buxianshiweizhi);
        textView.setVisibility(View.VISIBLE);
        inflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MessageBean(MessageBean.Code.PENGYOUQUANNOCHOICEADDRESS));
                finish();
            }
        });
        addressAdapter.addHeaderView(inflate);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                finish();
                break;
        }
    }

    @Override
    public void onGetInputtips(List<Tip> list, int i) {
        shurutishiDatas.clear();
        shurutishiDatas.addAll(list);
        shurutishiAdapter.setNewData(shurutishiDatas);

    }
}
