package com.company.qcy.ui.activity.pengyouquan;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.BaseViewpageAdapter;
import com.company.qcy.adapter.pengyouquan.HuatiAdapter;
import com.company.qcy.bean.pengyouquan.HuatiBean;
import com.company.qcy.fragment.pengyouquan.ErjihuatiFragment;
import com.flyco.tablayout.SlidingTabLayout;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import java.util.ArrayList;
import java.util.List;

public class ChoiceHuatiActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private SlidingTabLayout mChoiceHuatiSlidingTabLayout;
    private ViewPager mChoiceHuatiViewpager;
    private ImageView mChoiceHuatiXiala;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_huati);
        initView();
    }

    private List<HuatiBean> yijiHuati;

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mChoiceHuatiSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.choice_huati_slidingTabLayout);
        mChoiceHuatiViewpager = (ViewPager) findViewById(R.id.choice_huati_viewpager);
        mToolbarTitle.setText("话题");
        mChoiceHuatiXiala = (ImageView) findViewById(R.id.choice_huati_xiala);
        mChoiceHuatiXiala.setOnClickListener(this);
        addData();

    }

    private void addData() {
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.QUERYHUATI)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"));

        DialogStringCallback stringCallback = new DialogStringCallback(ChoiceHuatiActivity.this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("QUERYHUATI", response.body());
                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONArray data = jsonObject.getJSONArray("data");
                            if (ObjectUtils.isEmpty(data)) {
                                return;
                            }
                            yijiHuati = JSONObject.parseArray(data.toJSONString(), HuatiBean.class);
                            List<Fragment> fragments = new ArrayList<>();
                            for (int i = 0; i < yijiHuati.size(); i++) {
                                fragments.add(ErjihuatiFragment.newInstance(String.valueOf(yijiHuati.get(i).getId())));
                            }
                            String[] arr = new String[yijiHuati.size()];
                            for (int i = 0; i < yijiHuati.size(); i++) {
                                arr[i] = yijiHuati.get(i).getTitle();
                            }
                            mChoiceHuatiViewpager.setAdapter(new BaseViewpageAdapter(getSupportFragmentManager(), fragments));
                            mChoiceHuatiSlidingTabLayout.setViewPager(mChoiceHuatiViewpager, arr);
                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(ChoiceHuatiActivity.this, request, this);
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

    private PopupWindow popupWindow;
    private RecyclerView huatiRecyclerview;
    private HuatiAdapter huatiAdapter;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.choice_huati_xiala:
                if (ObjectUtils.isEmpty(yijiHuati)) {
                    ToastUtils.showShort("数据异常");
                    return;
                }

                if (!ObjectUtils.isEmpty(popupWindow)) {
                    popupWindow.setFocusable(true);//要先让popupwindow获得焦点，才能正确获取popupwindow的状态
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    } else {
                        mChoiceHuatiSlidingTabLayout.setVisibility(View.INVISIBLE);
                        popupWindow.showAsDropDown(mChoiceHuatiSlidingTabLayout);
                    }
                    return;
                }


                mChoiceHuatiSlidingTabLayout.setVisibility(View.INVISIBLE);
                View view = LayoutInflater.from(this).inflate(R.layout.popwindow_huati, null);
                huatiRecyclerview = view.findViewById(R.id.popwindow_huati_recyclerview);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
                huatiRecyclerview.setLayoutManager(gridLayoutManager);

                huatiAdapter = new HuatiAdapter(R.layout.item_popwindow_huati, yijiHuati);
                huatiRecyclerview.setAdapter(huatiAdapter);

                popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.setOutsideTouchable(true);
                popupWindow.setTouchable(true);
                popupWindow.showAsDropDown(mChoiceHuatiSlidingTabLayout);
                huatiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        mChoiceHuatiSlidingTabLayout.setCurrentTab(position);
                        popupWindow.dismiss();
                    }
                });

                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        mChoiceHuatiSlidingTabLayout.setVisibility(View.VISIBLE);
                    }
                });
                break;
        }
    }
}
