package com.company.qcy.Utils.pengyouquan.dialogfragment;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.pengyouquan.jiukou.PengyouquanAdapterCallBack;
import com.company.qcy.Utils.pengyouquan.jiukou.PinglunHouCallBack;
import com.company.qcy.bean.pengyouquan.PengyouquanBean;


public class PengyouquanAdapterDialogFragment extends DialogFragment {

    private Long id;
    private int position;
    Long parentId;
    String commentUser;
    PengyouquanBean bean;

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public void setCommentUser(String commentUser) {
        this.commentUser = commentUser;
    }

    public void setBean(PengyouquanBean bean) {
        this.bean = bean;
    }

    private PengyouquanAdapterCallBack pengyouquanAdapterCallBack = null;


    public void setPinglunHouCallBack(PengyouquanAdapterCallBack pengyouquanAdapterCallBack) {
        this.pengyouquanAdapterCallBack = pengyouquanAdapterCallBack;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    InputMethodManager inputMethodManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Light_DialogWhenLarge_NoActionBar);
    }


    private void autoFocus() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
    }

    private View mLayout;
    EditText inputComment;
    TextView confirm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mLayout = inflater.inflate(R.layout.pengyouquan_huifu_layout, container);
        inputComment = (EditText) mLayout.findViewById(R.id.et_discuss);
        confirm = (TextView) mLayout.findViewById(R.id.tv_confirm);

        //添加这一行
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);


        getDialog().getWindow().setBackgroundDrawable(
                new BitmapDrawable());

        getDialog().setCancelable(true);
        getDialog().setCanceledOnTouchOutside(true);
//        mLayout.requestFocus();

        inputComment.requestFocus();

        int softInputStateVisible = WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE;

        autoFocus();

        //点击阴影消失
        mLayout.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                dismiss();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

                return true;
            }
        });

        getDialog().setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
//                KeyboardUtils.hideSoftInput(getActivity());
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });


        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dismiss();
                }

                return false;
            }
        });

        inputComment.setHint("回复 : " + commentUser);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (StringUtils.isEmpty(inputComment.getText().toString().trim())) {
                    ToastUtils.showShort("不能发表空的信息");
                    return;
                }
                dismiss();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                pengyouquanAdapterCallBack.save(inputComment.getText().toString(), id, parentId,commentUser,bean,position);

            }
        });

        return mLayout;
    }


}

