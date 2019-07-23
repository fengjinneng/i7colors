package com.company.qcy.huodong.tuangou.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.company.qcy.R;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.huodong.tuangou.adapter.KanjiajiluAdapter;
import com.company.qcy.huodong.tuangou.bean.KanjiajiluBean;
import com.company.qcy.huodong.tuangou.bean.TuangouBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import java.util.ArrayList;
import java.util.List;

public class ShareDetailActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private KanjiajiluAdapter adapter;
    private List<KanjiajiluBean> datas;
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private TuangouBean tuangouBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_detail);
        tuangouBean = getIntent().getParcelableExtra("tuangouBean");
        initView();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.activity_share_detail_recyclerView);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_share_detail_swipeRefreshLayout);
        datas = new ArrayList<>();
        adapter = new KanjiajiluAdapter(R.layout.item_kanjia_jilu, datas);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(manager);

        recyclerView.setAdapter(adapter);


        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);

        mToolbarTitle.setText("我发起的砍价");

        addHeadView();

        addData();

    }

    private boolean isReflash;
    private int pageNo;

    private void addData() {
        pageNo++;
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.KANJIAYONGHULIST)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("token", SPUtils.getInstance().getString("token"))
                .params("mainId",tuangouBean.getId())//团购id
                .params("buyerId",tuangouBean.getBuyerId())//认购id
                .params("pageNo", pageNo)
                .params("pageSize", 20);


        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                refreshLayout.setRefreshing(false);
                LogUtils.v("KANJIAYONGHULIST", response.body());

                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONArray data = jsonObject.getJSONArray("data");

//                            if (ObjectUtils.isEmpty(data)) {
//                                adapter.loadMoreEnd();
//                                return;
//                            }
//                            List<TuangouBean> qiugouBeans = JSONObject.parseArray(data.toJSONString(), TuangouBean.class);
//                            if (isReflash) {
//                                datas.clear();
//                                datas.addAll(qiugouBeans);
//                                adapter.setNewData(datas);
//                                isReflash = false;
//                                adapter.loadMoreComplete();
//                                return;
//                            }
//
//                            datas.addAll(qiugouBeans);
//                            adapter.setNewData(datas);
//                            adapter.loadMoreComplete();
//                            adapter.disableLoadMoreIfNotFullPage();
//                            return;
//
//                        }
//                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
//                            SignAndTokenUtil.getSign(TuangouliebiaoActivity.this, request, this);
//                            return;
                        }
//                        ToastUtils.showShort(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                adapter.setEmptyView(getLayoutInflater().inflate(R.layout.empty_layout, null));
                refreshLayout.setRefreshing(false);
                ToastUtils.showShort(getResources().getString(R.string.NETEXCEPTION));
            }
        };

        request.execute(stringCallback);

    }

    private void addHeadView() {

        View inflate = getLayoutInflater().inflate(R.layout.headview_kanjia_share, null);

        if (!ObjectUtils.isEmpty(tuangouBean)) {

            ImageView img = inflate.findViewById(R.id.headview_kanjia_share_img);
            TextView name = inflate.findViewById(R.id.headview_kanjia_share_name);
            TextView goumailiang = inflate.findViewById(R.id.headview_kanjia_share_goumailiang);
            TextView goumailiangUnit = inflate.findViewById(R.id.headview_kanjia_share_goumailiang_unit);
            TextView price = inflate.findViewById(R.id.headview_kanjia_share_price);
            TextView priceUnit = inflate.findViewById(R.id.headview_kanjia_share_price_unit);
            TextView kucun = inflate.findViewById(R.id.headview_kanjia_share_kucun);
            TextView yikanjiage = inflate.findViewById(R.id.headview_kanjia_share_yikanjiage);
            SeekBar seekBar = inflate.findViewById(R.id.activity_tuangouxiangqing_seekBar);
            TextView shengyukanjia = inflate.findViewById(R.id.headview_kanjia_share_shengyukanjia);
            TextView fenxiangkanjia = inflate.findViewById(R.id.headview_kanjia_share_fenxiangkanjia);
            TextView kucunUnit = inflate.findViewById(R.id.headview_kanjia_share_kucun_unit);
            TextView yikanjiageUnit = inflate.findViewById(R.id.headview_kanjia_share_yikanjiage_unit);
            TextView shengyukanjiaUnit = inflate.findViewById(R.id.headview_kanjia_share_shengyukanjia_unit);

            if (!StringUtils.isEmpty(tuangouBean.getProductPic())) {
                Glide.with(this).load(ServerInfo.IMAGE + tuangouBean.getProductPic()).into(img);
            }

            yikanjiageUnit.setText("元/" + tuangouBean.getPriceUnit());
            shengyukanjiaUnit.setText("元/" + tuangouBean.getPriceUnit());
            name.setText(tuangouBean.getProductName());
            goumailiang.setText(tuangouBean.getNum());
            goumailiangUnit.setText(tuangouBean.getNumUnit());
            price.setText(tuangouBean.getNewPrice());
            priceUnit.setText("元/"+tuangouBean.getPriceUnit());
            kucun.setText(tuangouBean.getRemainNum());
            kucunUnit.setText(tuangouBean.getNumUnit());
            yikanjiage.setText(tuangouBean.getHasCutPrice());
            shengyukanjia.setText(tuangouBean.getRemainCutPrice());
            fenxiangkanjia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        adapter.addHeaderView(inflate);

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
}
