package com.company.qcy.ui.activity.pengyouquan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.message.MessageBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class ChangeNicknameActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 保存
     */
    private TextView mActivityChangeNicknameCommit;
    private EditText mActivityChangeNicknameEdit;
    /**
     * 取消
     */
    private TextView mActivityChangeNicknameCancle;
    private String nickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_nickname);
        nickName =  getIntent().getStringExtra("nickName");
        initView();
    }

    private void initView() {
        mActivityChangeNicknameCommit = (TextView) findViewById(R.id.activity_change_nickname_commit);
        mActivityChangeNicknameCommit.setOnClickListener(this);
        mActivityChangeNicknameEdit = (EditText) findViewById(R.id.activity_change_nickname_edit);
        mActivityChangeNicknameCancle = (TextView) findViewById(R.id.activity_change_nickname_cancle);
        mActivityChangeNicknameCancle.setOnClickListener(this);
        mActivityChangeNicknameEdit.setText(nickName);
        mActivityChangeNicknameEdit.setSelection(mActivityChangeNicknameEdit.getText().length());
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.activity_change_nickname_commit:
                if (StringUtils.isTrimEmpty(mActivityChangeNicknameEdit.getText().toString())) {
                    ToastUtils.showShort("不能填写空白名称哦！");
                    return;
                }

                if (StringUtils.equals(nickName, mActivityChangeNicknameEdit.getText().toString())) {
                    ToastUtils.showShort("您没有做任何修改哦！");
                    return;
                }
                update();
                break;
            case R.id.activity_change_nickname_cancle:
                finish();
                break;
        }
    }

    private void update() {
        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.UPDATEMYDYEINFO)
                .tag(this)
                .isMultipart(true)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("token", SPUtils.getInstance().getString("token"))
                .params("nickName", mActivityChangeNicknameEdit.getText().toString());


        StringCallback stringCallback = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("UPDATEMYDYEINFO", response.body());
                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            EventBus.getDefault().post(new com.company.qcy.bean.eventbus.MessageBean
                                    (com.company.qcy.bean.eventbus.MessageBean.Code.PENGYOUQUANNICKNAMECHANGE, mActivityChangeNicknameEdit.getText().toString()));
                            finish();
                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(ChangeNicknameActivity.this, request, this);
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
