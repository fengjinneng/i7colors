package com.company.qcy.huodong.tuangou.fragment;

import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.share.ShareUtil;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.huodong.tuangou.bean.TuangouBean;

import org.greenrobot.eventbus.EventBus;

public class TuangouchenggongDialogFragment extends DialogFragment {




    private TuangouBean tuangouBean;

    public void setTuangouBean(TuangouBean tuangouBean) {
        this.tuangouBean = tuangouBean;
    }

    //已经砍掉的价格
    private String cutPrice;

    public void setCutPrice(String cutPrice) {
        this.cutPrice = cutPrice;
    }

    private int status;//1:团购下单成功  2:砍价成功

    public void setStatus(int status) {
        this.status = status;
    }



    private View mLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        //添加这一行
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);


        getDialog().getWindow().setBackgroundDrawable(
                new BitmapDrawable());

        getDialog().setCancelable(true);
        getDialog().setCanceledOnTouchOutside(false);

        if(1==status){
            mLayout = inflater.inflate(R.layout.bg_tuangouchenggong, container);

            TextView tuangoujiagoumai = (TextView) mLayout.findViewById(R.id.bg_tuangouchenggong_tuangoujiagoumai);
            TextView share = (TextView) mLayout.findViewById(R.id.bg_tuangouchenggong_share);


            tuangoujiagoumai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    ShareUtil.shareKanjia(getActivity(),
                            tuangouBean.getProductName(),tuangouBean.getProductPic(),tuangouBean.getId(),tuangouBean.getBuyerId());
                }
            });
        }

        if(status==2){
            mLayout = inflater.inflate(R.layout.bg_kanjiachenggong, container);

            TextView price = (TextView) mLayout.findViewById(R.id.bg_kanjiachenggong_price);
            TextView priceUnit = (TextView) mLayout.findViewById(R.id.bg_kanjiachenggong_price_unit);

            if(!StringUtils.isEmpty(cutPrice)){
                price.setText(cutPrice);
            }


            TextView submit =  mLayout.findViewById(R.id.bg_kanjiachenggong_submit);

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new MessageBean(MessageBean.Code.TUANGOUKANJIACHENGGONG));
                    dismiss();
                }
            });

        }



        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if(keyCode == KeyEvent.KEYCODE_BACK){
                    dismiss();
                }

                return false;
            }
        });

        return mLayout;

    }

}
