package com.company.qcy.ui.activity.pengyouquan;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class MessageActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private ConstraintLayout mActivityMessagePinglunLayout;
    private ConstraintLayout mActivityMessageDianzanLayout;
    private ConstraintLayout mActivityMessageWodeLayout;
    private ConstraintLayout mActivityMessageHaoyouLayout;
    private ConstraintLayout mActivityMessagePinglunYoujiantouLayout;
    private ConstraintLayout mActivityMessageDianzanYoujiantouLayout;
    private ConstraintLayout mActivityMessageWodeYoujiantouLayout;
    private ConstraintLayout mActivityMessageHaoyouYoujiantouLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initView();
    }

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mActivityMessagePinglunLayout = (ConstraintLayout) findViewById(R.id.activity_message_pinglun_layout);
        mActivityMessagePinglunLayout.setOnClickListener(this);
        mActivityMessageDianzanLayout = (ConstraintLayout) findViewById(R.id.activity_message_dianzan_layout);
        mActivityMessageDianzanLayout.setOnClickListener(this);
        mActivityMessageWodeLayout = (ConstraintLayout) findViewById(R.id.activity_message_wode_layout);
        mActivityMessageWodeLayout.setOnClickListener(this);
        mActivityMessageHaoyouLayout = (ConstraintLayout) findViewById(R.id.activity_message_haoyou_layout);
        mActivityMessageHaoyouLayout.setOnClickListener(this);

        mToolbarTitle.setText("我的消息");

        mActivityMessagePinglunYoujiantouLayout = (ConstraintLayout) findViewById(R.id.activity_message_pinglun_youjiantou_layout);
        mActivityMessageDianzanYoujiantouLayout = (ConstraintLayout) findViewById(R.id.activity_message_dianzan_youjiantou_layout);
        mActivityMessageWodeYoujiantouLayout = (ConstraintLayout) findViewById(R.id.activity_message_wode_youjiantou_layout);
        mActivityMessageHaoyouYoujiantouLayout = (ConstraintLayout) findViewById(R.id.activity_message_haoyou_youjiantou_layout);


        pinglunBadge = new QBadgeView(this).bindTarget(mActivityMessagePinglunYoujiantouLayout)
                .setBadgeGravity(Gravity.START | Gravity.CENTER).setBadgeTextSize(10, true).setExactMode(false);

        dianzanBadge = new QBadgeView(this).bindTarget(mActivityMessageDianzanYoujiantouLayout)
                .setBadgeGravity(Gravity.START | Gravity.CENTER).setBadgeTextSize(10, true).setExactMode(false);

        aitewodeBadge = new QBadgeView(this).bindTarget(mActivityMessageWodeYoujiantouLayout)
                .setBadgeGravity(Gravity.START | Gravity.CENTER).setBadgeTextSize(10, true).setExactMode(false);

        haoyouBadge = new QBadgeView(this).bindTarget(mActivityMessageHaoyouYoujiantouLayout)
                .setBadgeGravity(Gravity.START | Gravity.CENTER).setBadgeTextSize(10, true).setExactMode(false);

        getNotReadMessage();
    }

    private void getNotReadMessage() {


            GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.GETPENGYOUQUANNOTREADMESSAGE)
                    .tag(this)
                    .params("sign", SPUtils.getInstance().getString("sign"))
                    .params("token", SPUtils.getInstance().getString("token"));

            StringCallback stringCallback = new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    LogUtils.v("GETPENGYOUQUANNOTREADMESSAGE", response.body());

                    try {
                        if (response.code() == 200) {

                            JSONObject jsonObject = JSONObject.parseObject(response.body());
                            String msg = jsonObject.getString("msg");

                            if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                JSONObject data = jsonObject.getJSONObject("data");
                                String like_message_not_read_count = data.getString("like_message_not_read_count");
                                String comment_message_not_read_count = data.getString("comment_message_not_read_count");
                                String follow_message_not_read_count = data.getString("follow_message_not_read_count");
                                String notice_message_not_read_count = data.getString("notice_message_not_read_count");

                                if(!StringUtils.isEmpty(like_message_not_read_count)){
                                    //点赞
                                    dianzanBadge.setBadgeNumber(Integer.parseInt(like_message_not_read_count));
                                }
                                if(!StringUtils.isEmpty(comment_message_not_read_count)){
                                    //评论
                                    pinglunBadge.setBadgeNumber(Integer.parseInt(comment_message_not_read_count));
                                }
                                if(!StringUtils.isEmpty(follow_message_not_read_count)){
                                    //好友消息
                                    haoyouBadge.setBadgeNumber(Integer.parseInt(follow_message_not_read_count));
                                }
                                if(!StringUtils.isEmpty(notice_message_not_read_count)){
                                    //艾特我的
                                    aitewodeBadge.setBadgeNumber(Integer.parseInt(notice_message_not_read_count));
                                }

                                return;
                            }
                            if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                                SignAndTokenUtil.getSign(MessageActivity.this, request, this);
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

    private Badge pinglunBadge;
    private Badge dianzanBadge;
    private Badge aitewodeBadge;
    private Badge haoyouBadge;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_message_pinglun_layout:
                ActivityUtils.startActivity(WodepinglunMessageActivity.class);
                break;
            case R.id.activity_message_dianzan_layout:
                ActivityUtils.startActivity(WodedianzanMessageActivity.class);
                break;
            case R.id.activity_message_wode_layout:
                ActivityUtils.startActivity(WodeaiteMessageActivity.class);
                break;
            case R.id.activity_message_haoyou_layout:
                ActivityUtils.startActivity(WodehaoyouMessageActivity.class);
                break;
        }
    }
}
