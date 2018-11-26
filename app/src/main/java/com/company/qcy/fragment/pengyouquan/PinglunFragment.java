package com.company.qcy.fragment.pengyouquan;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.adapter.pengyouquan.PinglunliebiaoAdapter;
import com.company.qcy.bean.pengyouquan.PengyouquanBean;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PinglunFragment extends Fragment {


    public PinglunFragment() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;
    private PinglunliebiaoAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pinglun, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.fragment_pinglun_recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        initView();

        addData();

    }

    private void initView() {
        List<PengyouquanBean.CommentListBean> datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add(new PengyouquanBean.CommentListBean());
        }
        adapter = new PinglunliebiaoAdapter(R.layout.item_pinglun, datas);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showPopupCommnet();
                KeyboardUtils.showSoftInput(getActivity());
            }
        });
    }

    private void addData() {


    }

    public static void HideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }


    private PopupWindow popupWindow;
    private EditText inputComment;
    private TextView btn_submit;

    private void showPopupCommnet() {
        final View view = LayoutInflater.from(getContext()).inflate(
                R.layout.pengyouquan_huifu_layout, null);
        inputComment = (EditText) view
                .findViewById(R.id.et_discuss);

        btn_submit = (TextView) view.findViewById(R.id.tv_confirm);
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, false);

        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE)
                    popupWindow.dismiss();
                return false;

            }
        });
//
        popupWindow.setFocusable(true);
        // 设置点击窗口外边窗口消失
        popupWindow.setOutsideTouchable(true);
//    popupWindow.setBackgroundDrawable(getResources().getDrawable(
//          R.drawable.popuwindow_white_bg));

        // 设置弹出窗体需要软键盘
        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        // 再设置模式，和Activity的一样，覆盖，调整大小。
        popupWindow
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        ColorDrawable cd = new ColorDrawable(0x000000);
        popupWindow.setBackgroundDrawable(cd);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
//    WindowManager.LayoutParams params = getWindow().getAttributes();
//    params.alpha = 0.4f;
//    getWindow().setAttributes(params);
        // 设置popWindow的显示和消失动画
//    popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        popupWindow.update();
//        popupInputMethodWindow();
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度
            public void onDismiss() {
//          WindowManager.LayoutParams params = getWindow().getAttributes();
//          params.alpha = 1f;
//          getWindow().setAttributes(params);
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // btn_submit.setClickable(false);
                String comment1 = inputComment.getText().toString().trim();
                if (!StringUtils.isEmpty(comment1)) {
                    ToastUtils.showShort(comment1);
                    HideKeyboard(v);
                } else return;
                //调用提交评论接口
//                saveDiscuss(comment1);
                popupWindow.dismiss();
            }
        });
    }


}

