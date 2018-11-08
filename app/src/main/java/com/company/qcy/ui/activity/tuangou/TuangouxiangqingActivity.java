package com.company.qcy.ui.activity.tuangou;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.tuangou.TuangoujiluAdapter;
import com.company.qcy.bean.tuangou.TuangouBean;
import com.company.qcy.bean.tuangou.TuangouRecordBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;

public class TuangouxiangqingActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mActivityTuangouxiangqingImg;
    private ImageView mActivityTuangouxiangqingStatus;
    /**
     * 35
     */
    private TextView mActivityTuangouxiangqingYuanjia;
    /**
     * 39
     */
    private TextView mActivityTuangouxiangqingTuangoujia;
    /**
     * 活性翠兰G266%
     */
    private TextView mActivityTuangouxiangqingProductname;
    /**
     * 38
     */
    private TextView mActivityTuangouxiangqingShengyu;
    /**
     * 22
     */
    private TextView mActivityTuangouxiangqingTotal;
    /**
     * 已认领总量：10吨
     */
    private TextView mActivityTuangouxiangqingYirenling;
    private Long id;
    private ConstraintLayout mActivityTuangouxiangqingContainer;
    private ImageView mActivityTuangouxiangqingContainerImg;
    /**
     * 基本参数
     */
    private TextView mActivityTuangouxiangqingJibencanshu;
    /**
     * 团购须知
     */
    private TextView mActivityTuangouxiangqingTuangouxuzhi;
    /**
     * 参与记录详情
     */
    private TextView mActivityTuangouxiangqingCanyujilu;
    private RecyclerView recyclerview;
    private TuangoujiluAdapter adapter;

    private List<TuangouRecordBean> datas;
    private SeekBar mActivityTuangouxiangqingSeekBar;
    /**
     * 单个用户采购量\n0.5-1吨
     */
    private TextView mActivityTuangouxiangqingDanyonghucaigou;
    private CountdownView mCountdownView;
    /**
     * 达成10%
     */
    private TextView mActivityTuangouxiangqingDacheng;
    /**
     * 元/吨
     */
    private TextView mActivityTuangouxiangqingYuanjiaDanwie;
    /**
     * 元/吨
     */
    private TextView mActivityTuangouxiangqingTuangoujiaDanwei;
    private TextView mActivityTuangouxiangqingJibencanshuLine;
    private TextView mActivityTuangouxiangqingTuangouxuzhiLine;
    private TextView mActivityTuangouxiangqingCanyujiluLine;
    private ConstraintLayout mActivityTuangouxiangqingWoyaotuangou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuangouxiangqing);
        id = getIntent().getLongExtra("id", 0);
        initView();
    }

    private void initView() {
        mActivityTuangouxiangqingImg = (ImageView) findViewById(R.id.activity_tuangouxiangqing_img);
        mActivityTuangouxiangqingStatus = (ImageView) findViewById(R.id.activity_tuangouxiangqing_status);
        mActivityTuangouxiangqingYuanjia = (TextView) findViewById(R.id.activity_tuangouxiangqing_yuanjia);
        mActivityTuangouxiangqingTuangoujia = (TextView) findViewById(R.id.activity_tuangouxiangqing_tuangoujia);
        mActivityTuangouxiangqingProductname = (TextView) findViewById(R.id.activity_tuangouxiangqing_productname);
        mActivityTuangouxiangqingShengyu = (TextView) findViewById(R.id.activity_tuangouxiangqing_shengyu);
        mActivityTuangouxiangqingTotal = (TextView) findViewById(R.id.activity_tuangouxiangqing_total);
        mActivityTuangouxiangqingYirenling = (TextView) findViewById(R.id.activity_tuangouxiangqing_yirenling);
        mActivityTuangouxiangqingContainer = (ConstraintLayout) findViewById(R.id.activity_tuangouxiangqing_container);
        mActivityTuangouxiangqingContainerImg = (ImageView) findViewById(R.id.activity_tuangouxiangqing_container_img);

        addData();
        datas = new ArrayList<>();

        mActivityTuangouxiangqingJibencanshu = (TextView) findViewById(R.id.activity_tuangouxiangqing_jibencanshu);
        mActivityTuangouxiangqingTuangouxuzhi = (TextView) findViewById(R.id.activity_tuangouxiangqing_tuangouxuzhi);
        mActivityTuangouxiangqingCanyujilu = (TextView) findViewById(R.id.activity_tuangouxiangqing_canyujilu);
        mActivityTuangouxiangqingJibencanshu.setOnClickListener(this);
        mActivityTuangouxiangqingTuangouxuzhi.setOnClickListener(this);
        mActivityTuangouxiangqingCanyujilu.setOnClickListener(this);
        recyclerview = (RecyclerView) findViewById(R.id.activity_tuangouxiangqing_container_recyclerview);

        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        adapter = new TuangoujiluAdapter(R.layout.item_tugoujilu, datas);
        recyclerview.setAdapter(adapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerview.addItemDecoration(itemDecoration);

        mActivityTuangouxiangqingSeekBar = (SeekBar) findViewById(R.id.activity_tuangouxiangqing_seekBar);
        mActivityTuangouxiangqingDanyonghucaigou = (TextView) findViewById(R.id.activity_tuangouxiangqing_danyonghucaigou);
        mCountdownView = (CountdownView) findViewById(R.id.countdownView);
        mActivityTuangouxiangqingDacheng = (TextView) findViewById(R.id.activity_tuangouxiangqing_dacheng);
        mActivityTuangouxiangqingYuanjiaDanwie = (TextView) findViewById(R.id.activity_tuangouxiangqing_yuanjia_danwie);
        mActivityTuangouxiangqingTuangoujiaDanwei = (TextView) findViewById(R.id.activity_tuangouxiangqing_tuangoujia_danwei);
        mActivityTuangouxiangqingJibencanshuLine = (TextView) findViewById(R.id.activity_tuangouxiangqing_jibencanshu_line);
        mActivityTuangouxiangqingTuangouxuzhiLine = (TextView) findViewById(R.id.activity_tuangouxiangqing_tuangouxuzhi_line);
        mActivityTuangouxiangqingCanyujiluLine = (TextView) findViewById(R.id.activity_tuangouxiangqing_canyujilu_line);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                addData();
            }
        }, recyclerview);
        mActivityTuangouxiangqingWoyaotuangou = (ConstraintLayout) findViewById(R.id.activity_tuangouxiangqing_woyaotuangou);
        mActivityTuangouxiangqingWoyaotuangou.setOnClickListener(this);
    }

    private TuangouBean bean;

    private void setInfo(TuangouBean bean) {

        if (ObjectUtils.isEmpty(bean)) {
            return;
        }

        Glide.with(this).load(ServerInfo.IMAGE + bean.getDetailMobilePic()).into(mActivityTuangouxiangqingContainerImg);
        mActivityTuangouxiangqingDanyonghucaigou.setText("单个用户采购量:\n" + bean.getMinNum() + "-" + bean.getMaxNum() + bean.getNumUnit());
        mActivityTuangouxiangqingYuanjia.setText(bean.getOldPrice());
        mActivityTuangouxiangqingTuangoujia.setText(bean.getNewPrice());
        mActivityTuangouxiangqingProductname.setText(bean.getProductName());
        mActivityTuangouxiangqingTotal.setText(bean.getTotalNum());
        mActivityTuangouxiangqingShengyu.setText(bean.getRemainNum());
        mCountdownView.start(Long.parseLong(bean.getEndTimeStamp()) - Long.parseLong(bean.getStartTimeStamp()));
        String[] split = bean.getNumPercent().split("%");
        mActivityTuangouxiangqingSeekBar.setProgress(Integer.parseInt(split[0]));
        mActivityTuangouxiangqingDacheng.setText("达成 " + bean.getNumPercent());
        mActivityTuangouxiangqingYirenling.setText("已认领总量:  " + bean.getSubscribedNum() + bean.getNumUnit());
        mActivityTuangouxiangqingYuanjiaDanwie.setText("元/" + bean.getNumUnit());
        mActivityTuangouxiangqingTuangoujiaDanwei.setText("元/" + bean.getNumUnit());
        Glide.with(this).load(ServerInfo.IMAGE + bean.getProductPic()).into(mActivityTuangouxiangqingImg);


    }

    private int pageNo;

    private void getTuangouRecord() {
        pageNo++;
        OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.GROUPBUYRECORD)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("mainId", id)
                .params("pageNo", pageNo)
                .params("pageSize", 30)
                .execute(new DialogStringCallback(this) {
                    @Override
                    public void onSuccess(Response<String> response) {

                        try {
                            if (response.code() == 200) {

                                JSONObject jsonObject = JSONObject.parseObject(response.body());

                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                    JSONArray data = jsonObject.getJSONArray("data");
                                    LogUtils.v("GROUPBUYRECORD", data);
                                    if (ObjectUtils.isEmpty(data)) {
                                        adapter.loadMoreEnd();
                                        return;
                                    }
                                    List<TuangouRecordBean> tuangouRecordBeans = JSONObject.parseArray(data.toJSONString(), TuangouRecordBean.class);

                                    adapter.addData(tuangouRecordBeans);
                                    adapter.loadMoreComplete();
                                    adapter.disableLoadMoreIfNotFullPage();
                                    return;

                                } else
                                    SignAndTokenUtil.checkSignAndToken(TuangouxiangqingActivity.this, jsonObject);
                            } else {
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });


    }

    private void addData() {
        OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.GROUPBUYDETAIL)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("id", id)
                .execute(new DialogStringCallback(this) {
                    @Override
                    public void onSuccess(Response<String> response) {

                        try {
                            if (response.code() == 200) {

                                JSONObject jsonObject = JSONObject.parseObject(response.body());

                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    bean = data.toJavaObject(TuangouBean.class);
                                    setInfo(bean);
                                    return;

                                } else
                                    SignAndTokenUtil.checkSignAndToken(TuangouxiangqingActivity.this, jsonObject);

                            } else {
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.activity_tuangouxiangqing_jibencanshu:
                recyclerview.setVisibility(View.GONE);
                mActivityTuangouxiangqingContainerImg.setVisibility(View.VISIBLE);
                Glide.with(this).load(ServerInfo.IMAGE + bean.getDetailMobilePic()).into(mActivityTuangouxiangqingContainerImg);
                mActivityTuangouxiangqingJibencanshu.setTextColor(getResources().getColor(R.color.chunhongse));
                mActivityTuangouxiangqingTuangouxuzhi.setTextColor(getResources().getColor(R.color.erjibiaoti));
                mActivityTuangouxiangqingCanyujilu.setTextColor(getResources().getColor(R.color.erjibiaoti));

                mActivityTuangouxiangqingJibencanshuLine.setVisibility(View.VISIBLE);
                mActivityTuangouxiangqingTuangouxuzhiLine.setVisibility(View.INVISIBLE);
                mActivityTuangouxiangqingCanyujiluLine.setVisibility(View.INVISIBLE);
                break;
            case R.id.activity_tuangouxiangqing_tuangouxuzhi:
                recyclerview.setVisibility(View.GONE);
                mActivityTuangouxiangqingContainerImg.setVisibility(View.VISIBLE);
                Glide.with(this).load(ServerInfo.IMAGE + bean.getNoteMobilePic()).into(mActivityTuangouxiangqingContainerImg);
                mActivityTuangouxiangqingTuangouxuzhi.setTextColor(getResources().getColor(R.color.chunhongse));
                mActivityTuangouxiangqingJibencanshu.setTextColor(getResources().getColor(R.color.erjibiaoti));
                mActivityTuangouxiangqingCanyujilu.setTextColor(getResources().getColor(R.color.erjibiaoti));

                mActivityTuangouxiangqingJibencanshuLine.setVisibility(View.INVISIBLE);
                mActivityTuangouxiangqingTuangouxuzhiLine.setVisibility(View.VISIBLE);
                mActivityTuangouxiangqingCanyujiluLine.setVisibility(View.INVISIBLE);

                break;
            case R.id.activity_tuangouxiangqing_canyujilu:
                recyclerview.setVisibility(View.VISIBLE);
                mActivityTuangouxiangqingContainerImg.setVisibility(View.GONE);
                mActivityTuangouxiangqingJibencanshu.setTextColor(getResources().getColor(R.color.erjibiaoti));
                mActivityTuangouxiangqingTuangouxuzhi.setTextColor(getResources().getColor(R.color.erjibiaoti));
                mActivityTuangouxiangqingCanyujilu.setTextColor(getResources().getColor(R.color.chunhongse));

                mActivityTuangouxiangqingJibencanshuLine.setVisibility(View.INVISIBLE);
                mActivityTuangouxiangqingTuangouxuzhiLine.setVisibility(View.INVISIBLE);
                mActivityTuangouxiangqingCanyujiluLine.setVisibility(View.VISIBLE);
                getTuangouRecord();

                break;

            case R.id.activity_tuangouxiangqing_woyaotuangou:

                ActivityUtils.startActivity(WoyaotuangouActivity.class);

                break;
        }
    }
}
