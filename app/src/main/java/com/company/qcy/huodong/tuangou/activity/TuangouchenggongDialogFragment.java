package com.company.qcy.huodong.tuangou.activity;

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
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;

public class TuangouchenggongDialogFragment extends DialogFragment {



    private View mLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mLayout = inflater.inflate(R.layout.bg_tuangouchenggong, container);

        //添加这一行
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);


        getDialog().getWindow().setBackgroundDrawable(
                new BitmapDrawable());

        getDialog().setCancelable(true);
        getDialog().setCanceledOnTouchOutside(false);
//        mLayout.requestFocus();


        //点击阴影消失
//        mLayout.setOnTouchListener(new View.OnTouchListener() {
//            public boolean onTouch(View v, MotionEvent event) {
//
//                dismiss();
//                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//
//                return true;
//            }
//        });

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
                ToastUtils.showShort("分享....");
            }
        });


//        getDialog().setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialog) {
////                KeyboardUtils.hideSoftInput(getActivity());
//                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//
//                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//            }
//        });


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
