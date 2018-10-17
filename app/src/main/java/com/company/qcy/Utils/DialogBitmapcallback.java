package com.company.qcy.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.view.Window;
import com.lzy.okgo.request.base.Request;
import com.lzy.okgo.callback.BitmapCallback;

public abstract class DialogBitmapcallback extends BitmapCallback {

    private ProgressDialog dialog;


    private void initDialog(Activity activity){
        dialog = new ProgressDialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        ;
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("网络请求中...");

    }

    public DialogBitmapcallback(Activity activity){
        super();
        initDialog(activity);
    }


    @Override
    public void onStart(Request<Bitmap, ? extends Request> request) {
        super.onStart(request);
        if(dialog!=null&&!dialog.isShowing()){
            dialog.show();
        }
    }

    @Override
    public void onFinish() {
        super.onFinish();
        if(dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }
    }

}
