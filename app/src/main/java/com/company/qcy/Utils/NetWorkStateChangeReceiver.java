package com.company.qcy.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.company.qcy.bean.eventbus.MessageBean;

import org.greenrobot.eventbus.EventBus;

public class NetWorkStateChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (NetworkUtil.isNetworkAvailable(context)) {
            EventBus.getDefault().post(new MessageBean(MessageBean.NetWorkState.YILIANJIE));
        }

    }
}
