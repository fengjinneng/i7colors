package com.company.qcy.ui.activity.kaifangshangcheng;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.BaseViewpageAdapter;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.base.SearchTypeActivity;
import com.company.qcy.bean.kaifangshangcheng.DianpuTypeBean;
import com.company.qcy.bean.kaifangshangcheng.ProductBean;
import com.company.qcy.fragment.kaifangshangcheng.KaifangshangchengSubFragment;
import com.company.qcy.fragment.pengyouquan.ErjihuatiFragment;
import com.company.qcy.ui.activity.chanpindating.ChanpindatingActivity;
import com.flyco.tablayout.SlidingTabLayout;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import java.util.ArrayList;
import java.util.List;

public class KaifangshangchengActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 搜索您想要的商品
     */
    private ImageView mKaifangshengchengSearch;
    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private SlidingTabLayout mKaifangshengchengSlidingTabLayout;
    private ViewPager mKaifangshengchengViewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaifangshangcheng);
        initView();
    }

    private void initView() {
        mKaifangshengchengSearch = (ImageView) findViewById(R.id.toolbar_img);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mToolbarTitle.setText("开放商城");
        mKaifangshengchengSearch.setOnClickListener(this);
        mKaifangshengchengSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.kaifangshengcheng_slidingTabLayout);
        mKaifangshengchengViewpager = (ViewPager) findViewById(R.id.kaifangshengcheng_viewpager);
        addDianpuType();

    }

    private void addDianpuType() {
        HttpParams params = new HttpParams();
        params.put("sign", SPUtils.getInstance().getString("sign"));

        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.DIANPUTYPE)
                .tag(this)
                .params(params);

        DialogStringCallback stringCallback = new DialogStringCallback(this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("DIANPUTYPE", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");

                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONArray data = jsonObject.getJSONArray("data");
                            DianpuTypeBean allBean = new DianpuTypeBean();
                            allBean.setValue("全部");
                            if (ObjectUtils.isEmpty(data)) {
                                return;
                            }
                            List<DianpuTypeBean> dianpuTypeBeans = JSONObject.parseArray(data.toJSONString(), DianpuTypeBean.class);
                            dianpuTypeBeans.add(0,allBean);

                            setTypeData(dianpuTypeBeans);
                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(KaifangshangchengActivity.this, request, this);
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

    private void setTypeData(List<DianpuTypeBean> dianpuTypeBeans) {


        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < dianpuTypeBeans.size(); i++) {
            String value = dianpuTypeBeans.get(i).getValue();
            if(StringUtils.equals("全部",value)){
                value = "";
            }
            fragments.add(KaifangshangchengSubFragment.newInstance(value));
        }
        String[] arr = new String[dianpuTypeBeans.size()];

        for (int i = 0; i < dianpuTypeBeans.size(); i++) {
            arr[i] = dianpuTypeBeans.get(i).getValue();
        }

        mKaifangshengchengViewpager.setAdapter(new BaseViewpageAdapter(getSupportFragmentManager(), fragments));
        mKaifangshengchengSlidingTabLayout.setViewPager(mKaifangshengchengViewpager, arr);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.toolbar_img:

                Intent intent = new Intent(KaifangshangchengActivity.this, SearchTypeActivity.class);
                intent.putExtra("isFrom", 3);
                intent.putExtra("keyword", "");
                ActivityUtils.startActivity(intent);
                break;
        }
    }
}
