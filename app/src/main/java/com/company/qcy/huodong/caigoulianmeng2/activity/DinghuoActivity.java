package com.company.qcy.huodong.caigoulianmeng2.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
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
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.InputWindowListener;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.MyConsrantLayout;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.Utils.UserUtil;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.huodong.caigoulianmeng2.adapter.DinghuoAdapter;
import com.company.qcy.huodong.caigoulianmeng2.bean.ProductBean;
import com.company.qcy.huodong.caigoulianmeng2.bean.UploadProductBean;
import com.company.qcy.ui.activity.user.LoginActivity;
import com.lzy.okgo.OkGo;
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

public class DinghuoActivity extends BaseActivity implements View.OnClickListener {

    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private TextView submit;
    private ConstraintLayout mActivityWoyaodinghuoRootlayout;
    private String meetingId;
    private List<ProductBean> datas;
    private DinghuoAdapter adapter;
    private List<String> zhangqiList;
    private String picIn;//预订或说明图片
    private String isType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_dinghuo);
        if(UserUtil.isLogin()){
        }else {
            ActivityUtils.startActivity(LoginActivity.class);
            finish();
        }
        meetingId = getIntent().getStringExtra("meetingId");
        isType = getIntent().getStringExtra("isType");
        initView();
    }

    private void initView() {
        datas = new ArrayList<>();
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.activity_dinghuo_recyclerwview);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_dinghuo_swipeRefreshLayout);
        submit = (TextView) findViewById(R.id.activity_dinghuo_submit);
        submit.setOnClickListener(this);
        mActivityWoyaodinghuoRootlayout = (ConstraintLayout) findViewById(R.id.activity_woyaodinghuo_rootlayout);
        mToolbarTitle.setText("我要采购");
        adapter = new DinghuoAdapter(R.layout.item_caigoulianmeng_woyaodinghuo, datas);
        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                isRefresh = true;
//                addData();
                refreshLayout.setRefreshing(false);
            }
        });

        recyclerView.setItemViewCacheSize(100);

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ProductBean productBean = (ProductBean) adapter.getData().get(position);
                EditText yudingliang = (EditText) adapter.getViewByPosition(recyclerView, position + 1, R.id.item_caigoulianmeng_woyaodinghuo_yudingliang);
                TextView cankaobiaozhun = (TextView) adapter.getViewByPosition(recyclerView, position + 1, R.id.item_caigoulianmeng_woyaodinghuo_cankaobiaozhun);
                CheckBox checkBox = (CheckBox) adapter.getViewByPosition(recyclerView, position + 1, R.id.item_caigoulianmeng_woyaodinghuo_checkBox);

                switch (view.getId()) {
                    case R.id.item_caigoulianmeng_woyaodinghuo_checkBox:

                        if (StringUtils.isEmpty(yudingliang.getText().toString()) || StringUtils.equals("请选择", cankaobiaozhun.getText().toString())) {
                            productBean.setChecked(false);
                            checkBox.setEnabled(false);
                            checkBox.setClickable(false);
                            checkBox.setChecked(false);
                            ToastUtils.showShort("请先填写预定量和选择参考标准！");
                            return;
                        }
                        productBean.setChecked(checkBox.isChecked());
                        productBean.setReservationNum(yudingliang.getText().toString());
                        productBean.setReferenceType(cankaobiaozhun.getText().toString());
                        break;

                    case R.id.item_caigoulianmeng_woyaodinghuo_cankaobiaozhun:

                        List<ProductBean.MeetingTypeListBean> meetingTypeList = productBean.getMeetingTypeList();
                        String[] arr = new String[meetingTypeList.size()];
                        for (int i = 0; i < meetingTypeList.size(); i++) {
                            arr[i] = meetingTypeList.get(i).getReferenceType();
                        }
                        showMutilAlertDialog(productBean, arr, cankaobiaozhun);
                        break;
                }
            }
        });

        addHeadView();
        addFootView();
        addData();


//        mActivityWoyaodinghuoRootlayout.setListener(new InputWindowListener() {
//            @Override
//            public void show() {
//                submit.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void hidden() {
//                submit.setVisibility(View.VISIBLE);
//            }
//        });

        if(!StringUtils.equals("2",isType)){
            submit.setVisibility(View.GONE);
        }
    }


    @Override
    public void onReciveMessage(MessageBean msg) {
        super.onReciveMessage(msg);

        switch (msg.getCode()){
            case MessageBean.Code.CGLMTIANJIASHANGPIN:
                ProductBean productBean = (ProductBean) msg.getObj();
                datas.add(productBean);
                adapter.setNewData(datas);
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
    private ImageView shuoming;

    private void addFootView() {
        View view = LayoutInflater.from(this).inflate(R.layout.woyaodinghuo_foot_layout, null);

        view.findViewById(R.id.woyaodinghuo_foot_layout_add_product).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startActivity(ZidingyiDinghuoActivity.class);
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
        shuoming = (ImageView) view.findViewById(R.id.woyaodinghuo_foot_layout_dinghuoshuoming);

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
                if (!ObjectUtils.isEmpty(zhangqiList)) {
                    choiceString(zhangqiList, zhangqi);
                }
            }
        });

        choiceAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choiceAddress(choiceAddress);
            }
        });

        if(!StringUtils.equals("2",isType)){
            view.findViewById(R.id.woyaodinghuo_foot_layout_constraintLayout).setVisibility(View.GONE);
        }

        adapter.addFooterView(view);
    }


    private String locationProvince;
    private String locationCity;

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


    private List<UploadProductBean> uploadProducts = new ArrayList<>();

    private AlertDialog alertDialog; //多选框

    private void showMutilAlertDialog(ProductBean productBean, String[] arr, TextView cankaobianzhun) {

        boolean[] choices = new boolean[productBean.getMeetingTypeList().size()];
        for (int i = 0; i < productBean.getMeetingTypeList().size(); i++) {
            choices[i] = (((ProductBean.MeetingTypeListBean) productBean.getMeetingTypeList().get(i)).isChecked());
        }


        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("选择参考标准");
        /**
         *第一个参数:弹出框的消息集合，一般为字符串集合
         * 第二个参数：默认被选中的，布尔类数组
         * 第三个参数：勾选事件监听
         */
        alertBuilder.setMultiChoiceItems(arr, choices, new DialogInterface.OnMultiChoiceClickListener() {
            @Override

            public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {

                productBean.getMeetingTypeList().get(i).setChecked(isChecked);

            }
        });

        alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                List<String> list = new ArrayList<>();
                for (int j = 0; j < productBean.getMeetingTypeList().size(); j++) {

                    if (productBean.getMeetingTypeList().get(j).isChecked()) {
                        list.add(productBean.getMeetingTypeList().get(j).getReferenceType());
                    }
                }

                if (list.size() == 0) {
                    cankaobianzhun.setText("请选择");
                } else {
                    String substring = list.toString().substring(1, list.toString().length() - 1);
                    cankaobianzhun.setText(substring);
                }
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

    private boolean isRefresh;

    private void addData() {
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.CAIGOULIANMENGPRODUCTLIST)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("meetingId", meetingId)
                .params("orderStatus", 0);

        DialogStringCallback stringCallback = new DialogStringCallback(this) {
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
                            picIn = data.getString("picIn");
                            GlideUtils.loadImage(DinghuoActivity.this,ServerInfo.IMAGE+picIn,shuoming);
                            JSONArray accountPeriod = data.getJSONArray("accountPeriod");
                            zhangqiList = JSONObject.parseArray(accountPeriod.toJSONString(), String.class);
                            JSONArray productArray = meetingList.getJSONArray("data");
                            List<ProductBean> productBeans = JSONObject.parseArray(productArray.toJSONString(), ProductBean.class);

                            if (isRefresh) {
                                datas.clear();
                                datas.addAll(productBeans);
                                adapter.notifyDataSetChanged();
                                isRefresh = false;
                                return;
                            }

                            datas.addAll(productBeans);
                            adapter.setNewData(productBeans);
                            return;
                        }

                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(DinghuoActivity.this, request, this);
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
            case R.id.activity_dinghuo_submit:

                if(checkInputInfo()){
                    uploadProducts.clear();
                    for (int i = 0; i < datas.size(); i++) {
                        if (datas.get(i).isChecked()) {
                            UploadProductBean bean = new UploadProductBean();
                            bean.setShopName(datas.get(i).getShopName());
                            bean.setPacking(datas.get(i).getPacking());
                            bean.setDiyShop(datas.get(i).getDiyShop());
                            bean.setNumUnit("吨");
                            bean.setReservationNum(datas.get(i).getReservationNum());
                            bean.setOrderStatus("0");
                            bean.setReferenceType(datas.get(i).getReferenceType());
                            bean.setId(datas.get(i).getId());
                            uploadProducts.add(bean);
                        }
                    }
                    if(uploadProducts.size()==0){
                        ToastUtils.showShort("您选择的商品为空!");
                        return;
                    }
                    upLoad(uploadProducts.toString());
                }

                break;
        }
    }

    private void upLoad(String upData) {
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
        params.put("meetingList", upData);
        params.put("token",SPUtils.getInstance().getString("token"));
        params.put("from",getResources().getString(R.string.app_android));

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
                            ToastUtils.showShort("您的订单已成功提交，可在【我的货单】中查看");
                            finish();
                            return;
                        }

                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(DinghuoActivity.this, request, this);
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

    private boolean checkInputInfo() {

        if (StringUtils.isEmpty(companyName.getText().toString())) {
            ToastUtils.showShort("请填写公司名称");
            return false;
        }
        if (StringUtils.isEmpty(lianxiren.getText().toString())) {
            ToastUtils.showShort("请填写联系人");
            return false;
        }
        if (StringUtils.isEmpty(zhiwu.getText().toString())) {
            ToastUtils.showShort("请填写职务");
            return false;
        }
        if (StringUtils.isEmpty(phone.getText().toString())) {
            ToastUtils.showShort("请填写联系电话");
            return false;
        }
        if (!RegexUtils.isMobileSimple(phone.getText().toString())) {
            ToastUtils.showShort("电话号码有误");
            return false;
        }
        if (StringUtils.equals("请选择付款方式", payType.getText().toString())) {
            ToastUtils.showShort("请选择付款方式");
            return false;
        }
        if (StringUtils.equals("请选择账期", zhangqi.getText().toString())) {
            ToastUtils.showShort("请选择账期");
            return false;
        }
        if (StringUtils.equals("请选择地址", choiceAddress.getText().toString())) {
            ToastUtils.showShort("请选择地址");
            return false;
        }
        return true;

    }

    private void addHeadView() {

        View view = LayoutInflater.from(this).inflate(R.layout.woyaodinghuo_headview, null);
        adapter.addHeaderView(view);

    }

}
