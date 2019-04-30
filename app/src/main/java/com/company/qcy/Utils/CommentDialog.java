package com.company.qcy.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.bean.pengyouquan.PengyouquanBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;

import org.greenrobot.eventbus.EventBus;

/**
 * @author yiw
 * @ClassName: CommentDialog
 * @Description: 评论长按对话框，保护复制和删除
 * @date 2015-12-28 下午3:36:39
 */
public class CommentDialog extends Dialog implements
        android.view.View.OnClickListener {

    private Context mContext;
    private PengyouquanBean.CommentListBean mCommentItem;
    private Long id;
    private int tieziPosition;

    public CommentDialog(Context context, PengyouquanBean.CommentListBean mCommentItem, Long id, int tieziPosition) {
        super(context, R.style.comment_dialog);
        mContext = context;
        this.mCommentItem = mCommentItem;
        this.id = id;
        this.tieziPosition = tieziPosition;
    }

    public CommentDialog(Context context, Long id) {
        super(context, R.style.comment_dialog);
        mContext = context;
        this.id = id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popwindow_pengyouquan_delete);
        initWindowParams();
        initView();
    }

    private void initWindowParams() {
        Window dialogWindow = getWindow();
        // 获取屏幕宽、高用
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (display.getWidth() * 0.65); // 宽度设置为屏幕的0.65

        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(lp);
    }

    private void initView() {
        TextView copyTv = (TextView) findViewById(R.id.copyTv);
        copyTv.setOnClickListener(this);
        TextView deleteTv = (TextView) findViewById(R.id.deleteTv);
        deleteTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.copyTv:
                if (mCommentItem != null) {
                    ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboard.setText(mCommentItem.getContent());
                }
                dismiss();
                break;
            case R.id.deleteTv:
                if (ObjectUtils.isEmpty(mCommentItem)) {
                    deletePinglunNotReflush();
                } else {
                    deletePinglun();
                }
                dismiss();
                break;
            default:
                break;
        }
    }


    private void deletePinglunNotReflush() {
        OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.DELETEPINGLUN)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("id", id)
                .params("token", SPUtils.getInstance().getString("token"))
                .execute(new DialogStringCallback((Activity) mContext) {
                    @Override
                    public void onSuccess(Response<String> response) {

                        try {
                            if (response.code() == 200) {
                                LogUtils.e("DELETEPINGLUN", response.body());
                                JSONObject jsonObject = JSONObject.parseObject(response.body());
                                String msg = jsonObject.getString("msg");

                                if (StringUtils.equals(jsonObject.getString("code"), mContext.getResources().getString(R.string.success))) {
                                    boolean data = jsonObject.getBoolean("data");
                                    if (data) {
                                        EventBus.getDefault().post(new MessageBean(MessageBean.Code.PENGYOUQUANNEEDREFLUSH));
                                        EventBus.getDefault().post(new MessageBean(MessageBean.Code.PINGLUNNEEDREFLUSH));
                                    } else {
                                        ToastUtils.showShort(msg);
                                    }

                                } else if (StringUtils.equals(jsonObject.getString("code"), mContext.getResources().getString(R.string.qianmingshixiao))) {
//                                    SignAndTokenUtil.getSign((Activity) mContext);
                                } else ToastUtils.showShort(msg);

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

    private void deletePinglun() {
        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.DELETEPINGLUN)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("id", id)
                .params("token", SPUtils.getInstance().getString("token"));

        DialogStringCallback stringCallback = new DialogStringCallback((Activity) mContext) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.e("DELETEPINGLUN", response.body());
                try {
                    if (response.code() == 200) {

                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), mContext.getResources().getString(R.string.success))) {
                            boolean data = jsonObject.getBoolean("data");
                            if (data) {
                                EventBus.getDefault().post(new MessageBean(MessageBean.Code.DELETEPINGLUNCHENGGONG, tieziPosition, mCommentItem));
                            } else {
                                ToastUtils.showShort(msg);
                            }
                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getContext().getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(getContext(),request,this);
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
                ToastUtils.showShort(getContext().getResources().getString(R.string.NETEXCEPTION));
            }
        };


        request.execute(stringCallback);
    }

}
