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
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.message.MessageBean;
import com.company.qcy.bean.qiugou.QiugouBean;
import com.company.qcy.ui.activity.qiugoudating.QiugoudatingActivity;
import com.company.qcy.ui.activity.qiugoudating.QiugouxiangqingActivity;
import com.company.qcy.ui.activity.zhuji.WodeFangAnDetailActivity;
import com.company.qcy.ui.activity.zhuji.WodeZhujiDingzhiDetailActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import org.greenrobot.eventbus.EventBus;

public class MessageDetailActivity extends BaseActivity implements View.OnClickListener {


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

    private String workType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        initView();
        id = getIntent().getLongExtra("id", 0);
        workType = getIntent().getStringExtra("workType");
        addData();
    }

    private void addData() {
        //业务类型，workType=enquiry求购消息；workType=zhujiDiy助剂定制消息
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.GETBUYERANDSELLERMESSAGEDETAIL)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("id", id)
                .params("workType", workType)
                .params("token", SPUtils.getInstance().getString("token"));

        DialogStringCallback stringCallback = new DialogStringCallback(this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("GETBUYERANDSELLERMESSAGEDETAIL", response.body());
                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            LogUtils.v("GETBUYERANDSELLERMESSAGEDETAIL", data);
                            messageBean = data.toJavaObject(MessageBean.class);
                            setData(messageBean);
                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(MessageDetailActivity.this, request, this);
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

    private void setData(MessageBean messageBean) {
        if(StringUtils.equals("enquiry",workType)){
            mActivityMessageDetailTitle.setText(messageBean.getProductName());

        }
        if(StringUtils.equals("zhujiDiy",workType)){
            mActivityMessageDetailTitle.setText(messageBean.getZhujiName());

        }
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
        mToolbarTitle.setText("消息详情");

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

                if (ObjectUtils.isEmpty(messageBean) || StringUtils.isEmpty(workType)) {
                    return;
                }

                if (StringUtils.equals("enquiry", workType)) {
                    Intent intent = new Intent(this, QiugouxiangqingActivity.class);
                    intent.putExtra("enquiryId", messageBean.getEnquiryId());
                    ActivityUtils.startActivity(intent);

                }

                if (StringUtils.equals("zhujiDiy", workType)) {
                    if (StringUtils.equals("buyer", messageBean.getType())) {
                        Intent intent = new Intent(this, WodeZhujiDingzhiDetailActivity.class);
                        intent.putExtra("id", messageBean.getZhujiDiyId());
                        ActivityUtils.startActivity(intent);
                    } else {
                        Intent intent = new Intent(this, WodeFangAnDetailActivity.class);
                        intent.putExtra("id", messageBean.getZhujiDiySolutionId());
                        ActivityUtils.startActivity(intent);
                    }
                }


//                if (!ObjectUtils.isEmpty(messageBean)) {
//
//                    Intent intent = new Intent(this, QiugouxiangqingActivity.class);
//                    intent.putExtra("enquiryId", messageBean.getEnquiryId());
//                    intent.putExtra("isCharger", "1");//是本人
//                    if (StringUtils.equals("qiugou", isfrom)) {
//                        intent.putExtra("status", "1");//正在求购中的状态
//
//                    } else if (StringUtils.equals("baojia", isfrom)) {
//                        intent.putExtra("status", "3");//买家接受报价的状态
//                    }
//                    ActivityUtils.startActivity(intent);
//
//                }

                break;
        }
    }
}
