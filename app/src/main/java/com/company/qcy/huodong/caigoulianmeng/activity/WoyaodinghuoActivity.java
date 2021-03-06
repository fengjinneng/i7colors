package com.company.qcy.huodong.caigoulianmeng.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.Utils.AddressPickTask;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InputWindowListener;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.MyConsrantLayout;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.huodong.caigoulianmeng.adapter.WoyaodinghuoAdapter;
import com.company.qcy.huodong.caigoulianmeng.bean.DinghuoBean;
import com.company.qcy.huodong.caigoulianmeng.bean.HHHBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.SinglePicker;

public class WoyaodinghuoActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private RecyclerView recyclerView;
    /**
     * 提交
     */
    private TextView submit;
    private List<HHHBean.MeetingListBean.DataBean> datas;
    private SwipeRefreshLayout refreshLayout;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;
    private WoyaodinghuoAdapter adapter;
    private String meetingId;
    /**
     * 我要订货
     */
    private TextView mActivityWoyaodinghuoSubmit;
    private MyConsrantLayout mActivityWoyaodinghuoRootlayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woyaodinghuo);
        meetingId = getIntent().getStringExtra("meetingId");
        initView();
    }

    private void initView() {
        mActivityWoyaodinghuoSubmit = (TextView) findViewById(R.id.activity_woyaodinghuo_submit);
        mActivityWoyaodinghuoSubmit.setOnClickListener(this);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.activity_woyaodinghuo_recyclerwview);
        submit = (TextView) findViewById(R.id.activity_woyaodinghuo_submit);
        submit.setOnClickListener(this);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_woyaodinghuo_swipeRefreshLayout);

        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        datas = new ArrayList<>();

        //创建适配器
        adapter = new WoyaodinghuoAdapter(R.layout.item_caigoulianmeng_woyaodinghuo, datas);
        recyclerView.setAdapter(adapter);

        refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉业务

                //禁止掉刷新
                if (isFristIn) {
                    addData();
                    isFristIn = false;
                } else {
                    refreshLayout.setRefreshing(false);
                }
            }
        };
        refreshLayout.setOnRefreshListener(refreshListener);
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                refreshListener.onRefresh();
            }
        });

        mToolbarTitle.setText("我要订货");


        mActivityWoyaodinghuoRootlayout = (MyConsrantLayout) findViewById(R.id.activity_woyaodinghuo_rootlayout);

        mActivityWoyaodinghuoRootlayout.setListener(new InputWindowListener() {
            @Override
            public void show() {
                mActivityWoyaodinghuoSubmit.setVisibility(View.GONE);
            }

            @Override
            public void hidden() {
                mActivityWoyaodinghuoSubmit.setVisibility(View.VISIBLE);
            }
        });

        addHeadView();
        addFootView();

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                TextView cankaobiaozhun = (TextView) adapter.getViewByPosition(recyclerView, position + 1, R.id.item_caigoulianmeng_woyaodinghuo_cankaobiaozhun);
                TextView yudingliang = (TextView) adapter.getViewByPosition(recyclerView, position + 1, R.id.item_caigoulianmeng_woyaodinghuo_yudingliang);
                switch (view.getId()) {
                    case R.id.item_caigoulianmeng_woyaodinghuo_checkBox:
                        CheckBox checkBox = (CheckBox) adapter.getViewByPosition(recyclerView, position + 1, R.id.item_caigoulianmeng_woyaodinghuo_checkBox);

                        HHHBean.MeetingListBean.DataBean dataBean = (HHHBean.MeetingListBean.DataBean) adapter.getData().get(position);

                        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                                LogUtils.e("czxcxzcz",isChecked+"");
                                dataBean.setReservationNum(yudingliang.getText().toString());
                                if (isChecked) {
                                    checkBox.setChecked(true);
                                    upLoadList.add(dataBean);

                                } else {
                                    checkBox.setChecked(false);
                                    upLoadList.remove(dataBean);
                                }
                            }
                        });

                        break;

                    case R.id.item_caigoulianmeng_woyaodinghuo_cankaobiaozhun:
                        TextView cankaobiaozhunView = (TextView) adapter.getViewByPosition(recyclerView, position + 1, R.id.item_caigoulianmeng_woyaodinghuo_cankaobiaozhun);
                        HHHBean.MeetingListBean.DataBean bean = (HHHBean.MeetingListBean.DataBean) adapter.getData().get(position);

                        showMutilAlertDialog(cankaobiaozhunView, bean);


                        break;
                }
            }
        });
    }

    private void addHeadView() {

        View view = LayoutInflater.from(this).inflate(R.layout.woyaodinghuo_headview, null);
        adapter.addHeaderView(view);

    }

    private List<HHHBean.MeetingListBean.DataBean> upLoadList = new ArrayList<>();

    private AlertDialog alertDialog; //多选框

    public void showMutilAlertDialog(TextView cankaobiaozhunView, HHHBean.MeetingListBean.DataBean dataBean) {

        List<HHHBean.MeetingListBean.DataBean.MeetingTypeListBean> meetingTypeList = dataBean.getMeetingTypeList();
        int size = meetingTypeList.size();
        String[] items = new String[size];

        boolean[] choices = new boolean[size];


        for (int i = 0; i < meetingTypeList.size(); i++) {
            items[i] = (((HHHBean.MeetingListBean.DataBean.MeetingTypeListBean) meetingTypeList.get(i))).getReferenceType();
            choices[i] = (((HHHBean.MeetingListBean.DataBean.MeetingTypeListBean) meetingTypeList.get(i))).getChecked();
        }

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("选择参考标准");
        /**
         *第一个参数:弹出框的消息集合，一般为字符串集合
         * 第二个参数：默认被选中的，布尔类数组
         * 第三个参数：勾选事件监听
         */
        alertBuilder.setMultiChoiceItems(items, choices, new DialogInterface.OnMultiChoiceClickListener() {
            @Override

            public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {

                if (isChecked) {
                    meetingTypeList.get(i).setChecked(true);

                } else {
                    meetingTypeList.get(i).setChecked(false);
                }
            }
        });

        alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                StringBuffer sb = new StringBuffer();
                for (int j = 0; j < meetingTypeList.size(); j++) {
                    if (meetingTypeList.get(j).getChecked()) {
                        sb.append(meetingTypeList.get(j).getReferenceType() + ",");
                    }
                }

                boolean z = false;//记录是否有参考标准被选择
                for (int j = 0; j < meetingTypeList.size(); j++) {

                    if (meetingTypeList.get(j).getChecked()) {
                        z = true;
                    }
                }

                if (!z) {
                    cankaobiaozhunView.setText("请选择");
                } else {
                    cankaobiaozhunView.setText(sb);
                }

                alertDialog.dismiss();
            }
        });

        alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                alertDialog.dismiss();
            }
        });

        alertDialog = alertBuilder.create();

        alertDialog.show();

    }

    @Override
    public void onReciveMessage(MessageBean msg) {
        super.onReciveMessage(msg);


        switch (msg.getCode()) {
            case MessageBean.Code.CGLMTIANJIASHANGPIN:
                HHHBean.MeetingListBean.DataBean obj = (HHHBean.MeetingListBean.DataBean) msg.getObj();
//                adapter.addData(obj);
                datas.add(obj);
//                adapter.setNewData(datas);
                break;
        }

    }

    private EditText companyName;
    private EditText lianxiren;
    private EditText zhiwu;
    private EditText phone;
    private TextView payType;
    private TextView zhangqi;
    private TextView choiceAddress;
    private EditText xiangxiAddress;

    private void addFootView() {
        View view = LayoutInflater.from(this).inflate(R.layout.woyaodinghuo_foot_layout, null);

        view.findViewById(R.id.woyaodinghuo_foot_layout_add_product).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startActivity(AddDinghuoCustomProductActivity.class);
            }
        });

        companyName = (EditText) view.findViewById(R.id.woyaodinghuo_foot_layout_companyname);
        lianxiren = (EditText) view.findViewById(R.id.woyaodinghuo_foot_layout_lianxiren);
        zhiwu = (EditText) view.findViewById(R.id.woyaodinghuo_foot_layout_zhiwu);
        phone = (EditText) view.findViewById(R.id.woyaodinghuo_foot_layout_phone);
        payType = (TextView) view.findViewById(R.id.woyaodinghuo_foot_layout_pay_type);
        zhangqi = (TextView) view.findViewById(R.id.woyaodinghuo_foot_layout_zhangqi);
        choiceAddress = (TextView) view.findViewById(R.id.woyaodinghuo_foot_layout_choiceaddress);
        xiangxiAddress = (EditText) view.findViewById(R.id.woyaodinghuo_foot_layout_address);

        payType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> fkfs = new ArrayList<>();
                fkfs.add("现汇");
                fkfs.add("银行承兑");
                choiceString(fkfs, payType);
            }
        });

        zhangqi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ObjectUtils.isEmpty(allZhangqi)) {
                    choiceString(allZhangqi, zhangqi);
                }
            }
        });

        choiceAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choiceAddress(choiceAddress);
            }
        });

        adapter.addFooterView(view);
    }


    //单选
    private void choiceString(final List data, final TextView tv) {
        SinglePicker<String> picker = new SinglePicker<String>(this, data);
//        picker.setTitleText("请选择");
        picker.setCycleDisable(true);
        picker.show();
        picker.setOnItemPickListener(new SinglePicker.OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int i, String s) {
                tv.setText(s);

            }
        });
    }


    //选择地址
    private void choiceAddress(final TextView address) {
        AddressPickTask task = new AddressPickTask(this);
        task.setHideProvince(false);
        task.setHideCounty(true);
        task.setCallback(new AddressPickTask.Callback() {
            @Override
            public void onAddressInitFailed() {
                ToastUtils.showShort("初始化失败！");
            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {
                if (county == null) {
                    address.setText(province.getAreaName() + " " + city.getAreaName());
                    locationProvince = province.getAreaName();
                    locationCity = city.getAreaName();
                } else {
                    address.setText(province.getAreaName() + city.getAreaName() + county.getAreaName());
                }
            }
        });
        task.execute();
    }

    private List<String> allZhangqi = new ArrayList<>();
    private String locationProvince;
    private String locationCity;


    private boolean isReflash;
    private boolean isFristIn = true;

    private void addData() {
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.CAIGOULIANMENGPRODUCTLIST)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("meetingId", meetingId)
                .params("orderStatus", 0);

        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                refreshLayout.setRefreshing(false);
                LogUtils.v("CAIGOULIANMENGPRODUCTLIST", response.body());

                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            JSONObject meetingList = data.getJSONObject("meetingList");
                            JSONArray accountPeriod = data.getJSONArray("accountPeriod");
                            for (int i = 0; i < accountPeriod.size(); i++) {
                                allZhangqi.add(accountPeriod.get(i).toString());
                            }
                            if (StringUtils.equals(meetingList.getString("code"), getResources().getString(R.string.success))) {
                                JSONArray array = meetingList.getJSONArray("data");
                                if (ObjectUtils.isEmpty(data)) {
                                    adapter.loadMoreEnd();
                                    return;
                                }
                                List<HHHBean.MeetingListBean.DataBean> beans = JSONObject.parseArray(array.toJSONString(), HHHBean.MeetingListBean.DataBean.class);
                                if (isReflash) {
                                    datas.clear();
                                    datas.addAll(beans);
                                    adapter.setNewData(datas);
                                    isReflash = false;
                                    adapter.loadMoreComplete();
                                    return;
                                }

                                datas.addAll(beans);
                                adapter.setNewData(datas);
                                adapter.loadMoreComplete();
                                adapter.disableLoadMoreIfNotFullPage();
                                return;
                            }
                        }

                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(WoyaodinghuoActivity.this, request, this);
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
                refreshLayout.setRefreshing(false);
                ToastUtils.showShort(getResources().getString(R.string.NETEXCEPTION));
            }
        };

        request.execute(stringCallback);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_woyaodinghuo_submit:
                if (checkInputInfo()) {

                    upData();

                }
        }

    }

    private void upData() {

        HttpParams params = new HttpParams();
        params.put("sign", SPUtils.getInstance().getString("sign"));
        params.put("companyName", companyName.getText().toString());
        params.put("phone", phone.getText().toString());
        params.put("name", lianxiren.getText().toString());
        params.put("address", xiangxiAddress.getText().toString());
        params.put("provinceName", locationProvince);
        params.put("cityName", locationCity);
        params.put("meetingId", meetingId + "");
        params.put("accountPeriod", zhangqi.getText().toString());
        params.put("payType", payType.getText().toString());
        params.put("positioner", zhiwu.getText().toString());


        if (ObjectUtils.isEmpty(upLoadList)) {
            ToastUtils.showShort("选择的货物为空");
            return;
        }

        JSONArray array = new JSONArray();
        for (int i = 0; i < upLoadList.size(); i++) {
            DinghuoBean bean = new DinghuoBean();
            bean.setShopName(upLoadList.get(i).getShopName());
            bean.setPacking(upLoadList.get(i).getPacking());
            bean.setReservationNum(upLoadList.get(i).getReservationNum());
            bean.setOrderStatus(upLoadList.get(i).getOrderStatus());
            bean.setShopId(upLoadList.get(i).getId() + "");
            bean.setDiyShop(upLoadList.get(i).getDiyShop());

            StringBuffer sb = new StringBuffer();

            for (int j = 0; j < upLoadList.get(i).getMeetingTypeList().size(); j++) {

                if (upLoadList.get(i).getMeetingTypeList().get(j).getChecked()) {

                    if (j == upLoadList.get(i).getMeetingTypeList().size() - 1) {
                        sb.append(upLoadList.get(i).getMeetingTypeList().get(j).getReferenceType());
                    } else
                        sb.append(upLoadList.get(i).getMeetingTypeList().get(j).getReferenceType() + ",");
                }
            }

            boolean b = sb.toString().endsWith(",");
            if (b) {
                String substring = sb.toString().substring(0, sb.toString().length() - 1);
                bean.setReferenceType(substring);

            } else {
                bean.setReferenceType(sb.toString());

            }

            array.add(bean);
        }

        params.put("meetingList", array.toString());

        LogUtils.e("dsadsadsad", params.toString());

        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.CAIGOULIANMENGWOYAODINGHUO)
                .tag(this)
                .params(params);

        DialogStringCallback stringCallback = new DialogStringCallback(this) {
            @Override
            public void onSuccess(Response<String> response) {
                refreshLayout.setRefreshing(false);
                LogUtils.v("CAIGOULIANMENGWOYAODINGHUO", response.body());

                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONObject da = jsonObject.getJSONObject("data");

                            return;
                        }

                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(WoyaodinghuoActivity.this, request, this);
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

private boolean checkInputInfo(){

        if(StringUtils.isEmpty(companyName.getText().toString())){
        ToastUtils.showShort("请填写公司名称");
        return false;
        }
        if(StringUtils.isEmpty(lianxiren.getText().toString())){
        ToastUtils.showShort("请填写联系人");
        return false;
        }
        if(StringUtils.isEmpty(zhiwu.getText().toString())){
        ToastUtils.showShort("请填写职务");
        return false;
        }
        if(StringUtils.isEmpty(phone.getText().toString())){
        ToastUtils.showShort("请填写联系电话");
        return false;
        }
        if(!RegexUtils.isMobileSimple(phone.getText().toString())){
        ToastUtils.showShort("电话号码有误");
        return false;
        }
        if(StringUtils.equals("请选择付款方式",payType.getText().toString())){
        ToastUtils.showShort("请选择付款方式");
        return false;
        }
        if(StringUtils.equals("请选择账期",zhangqi.getText().toString())){
        ToastUtils.showShort("请选择账期");
        return false;
        }
        if(StringUtils.equals("请选择地址",choiceAddress.getText().toString())){
        ToastUtils.showShort("请选择地址");
        return false;
        }
        if(StringUtils.isEmpty(xiangxiAddress.getText().toString())){
        ToastUtils.showShort("请输入详细地址");
        return false;
        }
        return true;

        }

        }
