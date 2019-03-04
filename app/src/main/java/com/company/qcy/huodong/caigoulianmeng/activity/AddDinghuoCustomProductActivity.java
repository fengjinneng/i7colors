package com.company.qcy.huodong.caigoulianmeng.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.Utils.InputWindowListener;
import com.company.qcy.Utils.MyConsrantLayout;
import com.company.qcy.huodong.caigoulianmeng.adapter.AddCustomProductCankaobiaozhunAdapter;
import com.company.qcy.huodong.caigoulianmeng.bean.HHHBean;

import java.util.ArrayList;
import java.util.List;

public class AddDinghuoCustomProductActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    /**
     * 请输入商品名称
     */
    private EditText mActivityAddCustomProductName;
    /**
     * 请输入预定量
     */
    private EditText mActivityAddCustomProductYudingliang;
    /**
     * 请输入包装形式
     */
    private EditText mActivityAddCustomProductBaozhuangxinshi;
    /**
     * 添加
     */
    private TextView mActivityAddCustomProductSubmit;
    /**
     * 取消
     */
    private TextView mActivityAddCustomProductCancle;
    private RecyclerView recyclerView;

    private AddCustomProductCankaobiaozhunAdapter adapter;
    private List<HHHBean.MeetingListBean.DataBean.MeetingTypeListBean> datas;
    private ConstraintLayout mActivityAddCustomProductSubmitLayout;
    private MyConsrantLayout mActivityAddCustomProductRootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_custom_product);
        initView();
    }

    private InputMethodManager inputMethodManager;

    private void initView() {
        datas = new ArrayList<>();
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        mActivityAddCustomProductSubmitLayout = (ConstraintLayout) findViewById(R.id.activity_add_custom_product_submit_layout);
        mActivityAddCustomProductRootLayout = (MyConsrantLayout) findViewById(R.id.activity_add_custom_product_root_layout);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        adapter = new AddCustomProductCankaobiaozhunAdapter(R.layout.item_addcustom_product_cankaobiaozhun, datas);
        mActivityAddCustomProductName = (EditText) findViewById(R.id.activity_add_custom_product_name);
        mActivityAddCustomProductYudingliang = (EditText) findViewById(R.id.activity_add_custom_product_yudingliang);
        mActivityAddCustomProductBaozhuangxinshi = (EditText) findViewById(R.id.activity_add_custom_product_baozhuangxinshi);
        mActivityAddCustomProductSubmit = (TextView) findViewById(R.id.activity_add_custom_product_submit);
        mActivityAddCustomProductCancle = (TextView) findViewById(R.id.activity_add_custom_product_cancle);
        recyclerView = (RecyclerView) findViewById(R.id.activity_add_custom_product_recyclerview);
        mToolbarTitle.setText("添加自定义商品");
        recyclerView.setAdapter(adapter);

        mActivityAddCustomProductRootLayout.setListener(new InputWindowListener() {
            @Override
            public void show() {

                mActivityAddCustomProductSubmitLayout.setVisibility(View.GONE);

            }

            @Override
            public void hidden() {

                mActivityAddCustomProductSubmitLayout.setVisibility(View.VISIBLE);

            }
        });

        addFootView();

        mActivityAddCustomProductSubmit.setOnClickListener(this);
        mActivityAddCustomProductCancle.setOnClickListener(this);


        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){

                    case R.id.item_wodehuodan_cankaobianzhun_reduce:
                        HHHBean.MeetingListBean.DataBean.MeetingTypeListBean cankaobiaozhun = (HHHBean.MeetingListBean.DataBean.MeetingTypeListBean) adapter.getData().get(position);
                        datas.remove(cankaobiaozhun);
                        adapter.notifyItemRemoved(position);
                        break;

                }
            }
        });
    }

    private void addFootView() {

        View view = LayoutInflater.from(this).inflate(R.layout.add_custom_product_footview, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.add_custom_product_footview_add);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editText = new EditText(AddDinghuoCustomProductActivity.this);
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                AlertDialog alertDialog = new AlertDialog.Builder((AddDinghuoCustomProductActivity.this)).setTitle("请输入参考标准")
                        .setView(editText)
                        .setPositiveButton("确定", null)
                        .setNegativeButton("取消", null)
                        .create();
                alertDialog.show();

                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (StringUtils.isEmpty(editText.getText().toString())) {
                            ToastUtils.showShort("参考标准不能为空");
                            return;
                        }

                        for (int i = 0; i < datas.size(); i++) {
                            if(StringUtils.equals(datas.get(i).getReferenceType(),editText.getText().toString())){
                                ToastUtils.showShort("不能添加重复的参考标准！");
                                if (KeyboardUtils.isSoftInputVisible(AddDinghuoCustomProductActivity.this)) {
                                    inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                                }
                                mActivityAddCustomProductSubmitLayout.setVisibility(View.VISIBLE);
                                alertDialog.dismiss();
                                return;
                            }
                        }
                        HHHBean.MeetingListBean.DataBean.MeetingTypeListBean meetingTypeListBean = new HHHBean.MeetingListBean.DataBean.MeetingTypeListBean();
                        meetingTypeListBean.setReferenceType(editText.getText().toString());
                        datas.add(meetingTypeListBean);
                        adapter.setNewData(datas);
                        if (KeyboardUtils.isSoftInputVisible(AddDinghuoCustomProductActivity.this)) {
                            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                        mActivityAddCustomProductSubmitLayout.setVisibility(View.VISIBLE);
                        alertDialog.dismiss();

                    }
                });

                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (KeyboardUtils.isSoftInputVisible(AddDinghuoCustomProductActivity.this)) {
                            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                        alertDialog.dismiss();

                    }
                });

            }
        });

        adapter.addFooterView(view);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                if (KeyboardUtils.isSoftInputVisible(AddDinghuoCustomProductActivity.this)) {
                    inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }
                finish();
                break;
            case R.id.activity_add_custom_product_submit:

                if(StringUtils.isEmpty(mActivityAddCustomProductName.getText().toString())){
                    ToastUtils.showShort("商品名称不能为空");
                    return;
                }
                if(StringUtils.isEmpty(mActivityAddCustomProductYudingliang.getText().toString())){
                    ToastUtils.showShort("请填写预定量");
                    return;
                }
                if(StringUtils.isEmpty(mActivityAddCustomProductBaozhuangxinshi.getText().toString())){
                    ToastUtils.showShort("请填写包装形式");
                    return;
                }
                HHHBean.MeetingListBean.DataBean zidingyiProduct = new HHHBean.MeetingListBean.DataBean();
                zidingyiProduct.setShopName(mActivityAddCustomProductName.getText().toString());
                zidingyiProduct.setReservationNum(mActivityAddCustomProductYudingliang.getText().toString());
                zidingyiProduct.setPacking(mActivityAddCustomProductBaozhuangxinshi.getText().toString());
                zidingyiProduct.setMeetingTypeList(datas);
                zidingyiProduct.setDiyShop("1");//自定义商品
                zidingyiProduct.setOrderStatus("0");//订货

//                EventBus.getDefault().post(new MessageBean(MessageBean.Code.CGLMTIANJIASHANGPIN,zidingyiProduct));
                finish();
                break;
            case R.id.activity_add_custom_product_cancle:
                if (KeyboardUtils.isSoftInputVisible(AddDinghuoCustomProductActivity.this)) {
                    inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }
                finish();
                break;
        }
    }
}
