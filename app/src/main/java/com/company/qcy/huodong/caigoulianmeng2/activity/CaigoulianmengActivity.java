package com.company.qcy.huodong.caigoulianmeng2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.MyLoadMoreView;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.ShurukuangDialog;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.BannerBean;
import com.company.qcy.huodong.caigoulianmeng.activity.WoyaodinghuoActivity;
import com.company.qcy.huodong.caigoulianmeng.activity.WoyaogonghuoActivity;
import com.company.qcy.huodong.caigoulianmeng2.adapter.CaigoulianmengAdapter;
import com.company.qcy.huodong.caigoulianmeng2.bean.CaigoulianmengBean;
import com.company.qcy.ui.activity.user.RegisterActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CaigoulianmengActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private RecyclerView recyclerView;
    private List<CaigoulianmengBean> datas;
    private CaigoulianmengAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;
    /**
     * 查看我的货单
     */
    private TextView mActivityCaigoulianmengChakanhuodan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caigoulianmeng);
        initView();
    }

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.activity_caigoulianmeng_recyclerview);
        mToolbarTitle.setText("采购联盟");
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_caigoulianmeng_swipeRefreshLayout);
        mActivityCaigoulianmengChakanhuodan = (TextView) findViewById(R.id.activity_caigoulianmeng_chakanhuodan);
        mActivityCaigoulianmengChakanhuodan.setOnClickListener(this);

        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        datas = new ArrayList<>();

        //创建适配器
        adapter = new CaigoulianmengAdapter(R.layout.item_caigoulianmeng_liebiao, datas);
        recyclerView.setAdapter(adapter);

        refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉业务
                isReflash = true;
                pageNo = 0;
                addData();
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

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                addData();
            }
        }, recyclerView);
        refreshLayout.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_green_light, android.R.color.holo_blue_light);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {


            }
        });


        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                CaigoulianmengBean caigoulianmengBean = (CaigoulianmengBean) adapter.getData().get(position);
                switch (view.getId()) {

                    case R.id.item_caigoulianmeng_liebiao_woyaocaigou:
                        Intent intent = new Intent(CaigoulianmengActivity.this, DinghuoActivity.class);
                        intent.putExtra("meetingId", caigoulianmengBean.getId() + "");
                        intent.putExtra("isType",caigoulianmengBean.getIsType());//
                        ActivityUtils.startActivity(intent);
                        break;
                    case R.id.item_caigoulianmeng_liebiao_woyaogonghuo:
                        Intent gonghuo = new Intent(CaigoulianmengActivity.this, GonghuoActivity.class);
                        gonghuo.putExtra("meetingId", caigoulianmengBean.getId() + "");
                        gonghuo.putExtra("isType",caigoulianmengBean.getIsType());//
                        ActivityUtils.startActivity(gonghuo);

                        break;
                }
            }
        });
        addAdvData();
        adapter.setLoadMoreView(new MyLoadMoreView());

    }

    private boolean isReflash;
    private int pageNo;

    private List<String> advDatas = new ArrayList<>();

    private void addHeadView() {

        View inflate = LayoutInflater.from(this).inflate(R.layout.head_img_huodong, null);
        ImageView img = inflate.findViewById(R.id.head_img_huodong_img);
        GlideUtils.loadImageRct(context, advDatas.get(0), img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        adapter.addHeaderView(inflate);
    }

    private void addAdvData() {

        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.INDEXBANNER)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("plate_code", "App_Meeting_Info");


        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("App_Meeting_Info", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONArray data = jsonObject.getJSONArray("data");
                            if (ObjectUtils.isEmpty(data)) {
                                return;
                            }
                            List<BannerBean> bannerBeans = JSONObject.parseArray(data.toJSONString(), BannerBean.class);
                            for (int i = 0; i < bannerBeans.size(); i++) {
                                advDatas.add(ServerInfo.IMAGE + bannerBeans.get(i).getAd_image());
                            }
                            addHeadView();

                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(CaigoulianmengActivity.this, request, this);
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

    private void addData() {
        pageNo++;
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.CAIGOULIANMENGLIST)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("pageNo", pageNo)
                .params("pageSize", 20);

        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                refreshLayout.setRefreshing(false);
                LogUtils.v("CAIGOULIANMENGLIST", response.body());

                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONArray data = jsonObject.getJSONArray("data");

                            if (ObjectUtils.isEmpty(data)) {
                                adapter.loadMoreEnd();
                                return;
                            }
                            List<CaigoulianmengBean> caigoulianmengBeans = JSONObject.parseArray(data.toJSONString(), CaigoulianmengBean.class);
                            if (isReflash) {
                                datas.clear();
                                datas.addAll(caigoulianmengBeans);
                                adapter.setNewData(datas);
                                isReflash = false;
                                adapter.loadMoreComplete();
                                return;
                            }

                            datas.addAll(caigoulianmengBeans);
                            adapter.setNewData(datas);
                            adapter.loadMoreComplete();
                            adapter.disableLoadMoreIfNotFullPage();
                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(CaigoulianmengActivity.this, request, this);
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

    private CountDownTimer timer;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_caigoulianmeng_chakanhuodan:

                ShurukuangDialog editDialog = new ShurukuangDialog(CaigoulianmengActivity.this);
                editDialog.setTitle("请输入手机号码查询货单信息");
                editDialog.setYesOnclickListener("确定", new ShurukuangDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick(String phone, String verifycode) {
                        if (StringUtils.isEmpty(phone)) {
                            ToastUtils.showShort("请输入手机号!");
                            return;
                        }
                        if (!RegexUtils.isMobileSimple(phone)) {
                            ToastUtils.showShort("手机号不正确!");
                            return;
                        }
                        if (StringUtils.isEmpty(verifycode)) {
                            ToastUtils.showShort("请输入验证码!");
                            return;
                        }

                        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.YANZHENGCAIGOULIANMENGMESSAGE)
                                .tag(this)
                                .params("sign", SPUtils.getInstance().getString("sign"))
                                .params("phone", phone)
                                .params("code",verifycode);

                        DialogStringCallback stringCallback = new DialogStringCallback(CaigoulianmengActivity.this) {
                            @Override
                            public void onSuccess(Response<String> response) {
                                LogUtils.e("YANZHENGCAIGOULIANMENGMESSAGE", response.body());

                                try {
                                    if (response.code() == 200) {
                                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                                        String msg = jsonObject.getString("msg");
                                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                            editDialog.dismiss();
                                            Intent intent = new Intent(CaigoulianmengActivity.this, HuodanActivity.class);
                                            intent.putExtra("phone",phone);
                                            ActivityUtils.startActivity(intent);
                                            return;

                                        }
                                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                                            SignAndTokenUtil.getSign(CaigoulianmengActivity.this, request, this);
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

                });

                editDialog.setSendOnclickListener(new ShurukuangDialog.onSendOnclickListener() {
                    @Override
                    public void onSendClick(String phone) {
                        if (StringUtils.isEmpty(phone)) {
                            ToastUtils.showShort("请输入手机号!");
                            return;
                        }
                        if (!RegexUtils.isMobileSimple(phone)) {
                            ToastUtils.showShort("手机号不正确!");
                            return;
                        }
                        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.CAIGOULIANMENMESSAGE)
                                .tag(this)
                                .params("sign", SPUtils.getInstance().getString("sign"))
                                .params("phone", phone);


                        DialogStringCallback stringCallback = new DialogStringCallback(CaigoulianmengActivity.this) {
                            @Override
                            public void onSuccess(Response<String> response) {
                                LogUtils.e("CAIGOULIANMENMESSAGE", response.body());

                                try {
                                    if (response.code() == 200) {
                                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                                        String msg = jsonObject.getString("msg");
                                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                            ToastUtils.showShort(msg);
                                            timer = new CountDownTimer(59000, 1000) {
                                                @Override
                                                public void onTick(long millisUntilFinished) {
                                                    SimpleDateFormat sdf = new SimpleDateFormat("ss");
                                                    editDialog.setTime(sdf.format(new Date(millisUntilFinished)) + "S");
                                                }

                                                @Override
                                                public void onFinish() {
                                                    editDialog.setTime("发送验证码");
                                                }
                                            }.start();
                                            return;

                                        }
                                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                                            SignAndTokenUtil.getSign(CaigoulianmengActivity.this, request, this);
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
                });

                editDialog.setNoOnclickListener("取消", new ShurukuangDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        editDialog.dismiss();
                    }
                });
                editDialog.show();

                break;
        }
    }
}
