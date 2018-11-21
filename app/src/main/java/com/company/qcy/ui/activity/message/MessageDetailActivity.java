package com.company.qcy.ui.activity.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.bean.message.MessageBean;
import com.company.qcy.bean.qiugou.QiugouBean;
import com.company.qcy.ui.activity.qiugoudating.QiugoudatingActivity;
import com.company.qcy.ui.activity.qiugoudating.QiugouxiangqingActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

public class MessageDetailActivity extends AppCompatActivity implements View.OnClickListener {


    private Long id;
    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    /**
     * TextView
     */
    private TextView mActivityMessageDetailTitle;
    /**
     * TextView
     */
    private TextView mActivityMessageDetailContent;
    /**
     * TextView
     */
    private TextView mActivityMessageDetailTime;
    /**
     * 查看详情
     */
    private TextView mActivityMessageDetailChakan;


    private MessageBean messageBean;

    private String isfrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        initView();
        id = getIntent().getLongExtra("id", 0);
        isfrom = getIntent().getStringExtra("isfrom");
        addData();
    }

    private void addData() {


        OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.GETENQUIRYINFORMDETAIL)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("id", id)
                .params("token", SPUtils.getInstance().getString("token"))
                .execute(new DialogStringCallback(this) {
                    @Override
                    public void onSuccess(Response<String> response) {

                        try {
                            if (response.code() == 200) {

                                JSONObject jsonObject = JSONObject.parseObject(response.body());

                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    LogUtils.v("GETENQUIRYINFORMDETAIL", data);
                                    messageBean = data.toJavaObject(MessageBean.class);
                                    setData(messageBean);

                                    return;

                                } else
                                    SignAndTokenUtil.checkSignAndToken(MessageDetailActivity.this, jsonObject);

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

    private void setData(MessageBean messageBean) {
        mActivityMessageDetailTitle.setText(messageBean.getProductName());
        mActivityMessageDetailTime.setText(messageBean.getCreatedAt());
        mActivityMessageDetailContent.setText(messageBean.getContent());

    }

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mActivityMessageDetailTitle = (TextView) findViewById(R.id.activity_message_detail_title);
        mActivityMessageDetailContent = (TextView) findViewById(R.id.activity_message_detail_content);
        mActivityMessageDetailTime = (TextView) findViewById(R.id.activity_message_detail_time);
        mToolbarTitle.setText("求购报价详情");

        mActivityMessageDetailChakan = (TextView) findViewById(R.id.activity_message_detail_chakan);
        mActivityMessageDetailChakan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_message_detail_chakan:

                if (!ObjectUtils.isEmpty(messageBean)) {

                    Intent intent = new Intent(this, QiugouxiangqingActivity.class);
                    intent.putExtra("enquiryId", messageBean.getEnquiryId());
                    intent.putExtra("isCharger", "1");//是本人
                    if (StringUtils.equals("qiugou", isfrom)) {
                        intent.putExtra("status", "1");//正在求购中的状态

                    } else if (StringUtils.equals("baojia", isfrom)) {
                        intent.putExtra("status", "3");//买家接受报价的状态
                    }
                    ActivityUtils.startActivity(intent);

                }

                break;
        }
    }
}