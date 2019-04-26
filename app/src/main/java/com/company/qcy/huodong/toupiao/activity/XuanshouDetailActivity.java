package com.company.qcy.huodong.toupiao.activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.GlideUtils;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.share.ShareUtil;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.Utils.UserUtil;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.huodong.toupiao.bean.XuanshouBean;
import com.company.qcy.ui.activity.user.LoginActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

import org.greenrobot.eventbus.EventBus;

@Route(path = "/vote/player/detail")

public class XuanshouDetailActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private ImageView mActivityXuanshouDetailImg;
    private ImageView mActivityXuanshouDetailPaimingImg;
    /**
     * 5
     */
    private TextView mActivityXuanshouDetailPaimingText;
    /**
     * 山东索玛德染料有限公司
     */
    private TextView mActivityXuanshouDetailName;
    /**
     * 1
     */
    private TextView mActivityXuanshouDetailPaiming1;
    /**
     * 1
     */
    private TextView mActivityXuanshouDetailPaiming2;
    /**
     * 我是参与者简要描述显示全部字我是参与者简要描述显示全部字我是参与者简要描述显示全部字我是参与者简要描述显示
     */
    private TextView mActivityXuanshouDetailContent;
    /**
     * 6666
     */
    private TextView mActivityXuanshouDetailTickets;
    /**
     * 015
     */
    private TextView mActivityXuanshouDetailBianhao;
    /**
     * 投票
     */
    private Button mActivityXuanshouDetailToupiao;

    @Autowired
    public String mainId; //投票活动的ID

    @Autowired
    public String id;    //选手的Id
    private TextView mToolbarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuanshou_detail);

        Uri data = getIntent().getData();
        //不为空说明是外部网页传过来的
        if (!ObjectUtils.isEmpty(data)) {
            id = data.getQueryParameter("id");
            mainId = data.getQueryParameter("mainId");
        } else {
            id = getIntent().getStringExtra("id");
            mainId = getIntent().getStringExtra("mainId");
        }

        initView();
    }

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);

        mToolbarText = (TextView) findViewById(R.id.toolbar_text);
        mToolbarText.setOnClickListener(this);
        mActivityXuanshouDetailImg = (ImageView) findViewById(R.id.activity_xuanshou_detail_img);
        mActivityXuanshouDetailPaimingImg = (ImageView) findViewById(R.id.activity_xuanshou_detail_paiming_img);
        mActivityXuanshouDetailPaimingText = (TextView) findViewById(R.id.activity_xuanshou_detail_paiming_text);
        mActivityXuanshouDetailName = (TextView) findViewById(R.id.activity_xuanshou_detail_name);
        mActivityXuanshouDetailPaiming1 = (TextView) findViewById(R.id.activity_xuanshou_detail_paiming_1);
        mActivityXuanshouDetailPaiming2 = (TextView) findViewById(R.id.activity_xuanshou_detail_paiming_2);
        mActivityXuanshouDetailContent = (TextView) findViewById(R.id.activity_xuanshou_detail_content);
        mActivityXuanshouDetailTickets = (TextView) findViewById(R.id.activity_xuanshou_detail_tickets);
        mActivityXuanshouDetailBianhao = (TextView) findViewById(R.id.activity_xuanshou_detail_bianhao);
        mActivityXuanshouDetailToupiao = (Button) findViewById(R.id.activity_xuanshou_detail_toupiao);
        mActivityXuanshouDetailToupiao.setOnClickListener(this);
        mToolbarText.setVisibility(View.VISIBLE);
        mToolbarText.setText("分享");

        addData();

    }

    @Override
    public void onReciveMessage(MessageBean msg) {
        super.onReciveMessage(msg);

        switch (msg.getCode()){
            case MessageBean.Code.TOUPIAOCHENGGONG:
                xuanshouBean.setJoinedTicketNum(String.valueOf(Integer.parseInt(xuanshouBean.getJoinedTicketNum()) + 1));
                xuanshouBean.setTicketNum(String.valueOf(Integer.parseInt(xuanshouBean.getTicketNum()) + 1));
                mActivityXuanshouDetailToupiao.setText("已投" + xuanshouBean.getJoinedTicketNum() + "票");
                mActivityXuanshouDetailTickets.setText(xuanshouBean.getTicketNum());
                break;
        }
    }

    private void addData() {
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.CANSAIRENYUANDETAIL)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("token", SPUtils.getInstance().getString("token"))
                .params("id", id)
                .params("mainId", mainId);

        DialogStringCallback stringCallback = new DialogStringCallback(this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("CANSAIRENYUANDETAIL", response.body());
                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            xuanshouBean = jsonObject.getJSONObject("data").toJavaObject(XuanshouBean.class);
                            setData();
                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(XuanshouDetailActivity.this, request, this);
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

    private XuanshouBean xuanshouBean;

    private void setData() {

        if (ObjectUtils.isEmpty(xuanshouBean)) {
            return;
        }
        mToolbarTitle.setText(xuanshouBean.getName());
        mActivityXuanshouDetailName.setText(xuanshouBean.getName());
        mActivityXuanshouDetailPaiming1.setText(xuanshouBean.getSort());
        mActivityXuanshouDetailPaiming2.setText(xuanshouBean.getSort());
        GlideUtils.loadYuanjiaoPicture(this, ServerInfo.IMAGE + xuanshouBean.getPic(), mActivityXuanshouDetailImg);
        mActivityXuanshouDetailContent.setText("\t\t\t" + xuanshouBean.getDescription());

        mActivityXuanshouDetailTickets.setText(xuanshouBean.getTicketNum());
        mActivityXuanshouDetailBianhao.setText(xuanshouBean.getNumber());

        switch (xuanshouBean.getSort()) {
            case "1":
                mActivityXuanshouDetailPaimingImg.setVisibility(View.VISIBLE);
                mActivityXuanshouDetailPaimingImg.setImageDrawable(getResources().getDrawable(R.mipmap.toupiao_diyiming));
                break;
            case "2":
                mActivityXuanshouDetailPaimingImg.setVisibility(View.VISIBLE);
                mActivityXuanshouDetailPaimingImg.setImageDrawable(getResources().getDrawable(R.mipmap.toupiao_dierming));
                break;
            case "3":
                mActivityXuanshouDetailPaimingImg.setVisibility(View.VISIBLE);
                mActivityXuanshouDetailPaimingImg.setImageDrawable(getResources().getDrawable(R.mipmap.toupiao_disanming));
                break;
            default:
                mActivityXuanshouDetailPaimingText.setVisibility(View.VISIBLE);
                mActivityXuanshouDetailPaimingText.setText(xuanshouBean.getSort());
                break;
        }

        if (StringUtils.equals("0", xuanshouBean.getJoinedTicketNum())) {
            mActivityXuanshouDetailToupiao.setText("投票");
        } else {
            mActivityXuanshouDetailToupiao.setText("已投" + xuanshouBean.getJoinedTicketNum() + "票");
        }

        if (!StringUtils.equals("1", xuanshouBean.getMainStatus())) {
            mActivityXuanshouDetailToupiao.setText("投票已结束!");
            mActivityXuanshouDetailToupiao.setClickable(false);
            mActivityXuanshouDetailToupiao.setBackgroundColor(getResources().getColor(R.color.putongwenben));

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_xuanshou_detail_toupiao:
                if (!UserUtil.isLogin()) {
                    ActivityUtils.startActivity(LoginActivity.class);
                    return;
                }
                toupiao();
                break;
            case R.id.toolbar_text:
                if(ObjectUtils.isEmpty(xuanshouBean)){
                    return;
                }
                ShareUtil.shareVotePlayerDetail(XuanshouDetailActivity.this, "【投票】" + xuanshouBean.getName(),
                        xuanshouBean.getDescription(), xuanshouBean.getPic(), xuanshouBean.getId() + "", xuanshouBean.getMainId() + "");

                break;
        }
    }

    private void toupiao() {
        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.TOUPIAO)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("token", SPUtils.getInstance().getString("token"))
                .params("mainId", mainId)
                .params("applicationId", xuanshouBean.getId())
                .params("from", "app_android");

        DialogStringCallback stringCallback = new DialogStringCallback(this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("TOUPIAO", response.body());
                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            EventBus.getDefault().post(new MessageBean(MessageBean.Code.TOUPIAOCHENGGONG));
                            return;
                        }

                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(XuanshouDetailActivity.this, request, this);
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
}
