package com.company.qcy.huodong.caigoulianmeng2.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.Utils.CaigoulianmengAddCankaobiaozhunDialog;
import com.company.qcy.Utils.CalendarUtil;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.huodong.caigoulianmeng2.adapter.CankaobiaozhunAdapter;
import com.company.qcy.huodong.caigoulianmeng2.bean.ProductBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.DatePicker;

public class ZidingyiGonghuoActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    /**
     * 请输入商品名称
     */
    private EditText mActivityZidingyiGonghuoProductName;
    /**
     * 请输入预定量
     */
    private EditText mActivityZidingyiGonghuoProductYudingliang;
    /**
     * 请输入包装形式
     */
    private EditText mActivityZidingyiGonghuoProductBaozhuangxinshi;
    private RecyclerView recyclerView;
    /**
     * 请输入价格
     */
    private EditText mActivityZidingyiGonghuoProductPrice;
    /**
     * 请选择
     */
    private TextView mActivityZidingyiGonghuoProductDate;
    /**
     * 添加
     */
    private TextView mActivityZidingyiGonghuoProductSubmit;
    /**
     * 取消
     */
    private TextView mActivityZidingyiGonghuoProductCancle;

    private CankaobiaozhunAdapter adapter;
    private List<String> datas;
    private InputMethodManager inputMethodManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_zidingyi_gonghuo);
        initView();
    }

    private void initView() {
        datas = new ArrayList<>();
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mActivityZidingyiGonghuoProductName = (EditText) findViewById(R.id.activity_zidingyi_gonghuo_product_name);
        mActivityZidingyiGonghuoProductYudingliang = (EditText) findViewById(R.id.activity_zidingyi_gonghuo_product_yudingliang);
        mActivityZidingyiGonghuoProductBaozhuangxinshi = (EditText) findViewById(R.id.activity_zidingyi_gonghuo_product_baozhuangxinshi);
        recyclerView = (RecyclerView) findViewById(R.id.activity_zidingyi_gonghuo_product_recyclerview);
        mActivityZidingyiGonghuoProductPrice = (EditText) findViewById(R.id.activity_zidingyi_gonghuo_product_price);
        mActivityZidingyiGonghuoProductDate = (TextView) findViewById(R.id.activity_zidingyi_gonghuo_product_date);
        mActivityZidingyiGonghuoProductDate.setOnClickListener(this);
        mActivityZidingyiGonghuoProductSubmit = (TextView) findViewById(R.id.activity_zidingyi_gonghuo_product_submit);
        mActivityZidingyiGonghuoProductSubmit.setOnClickListener(this);
        mActivityZidingyiGonghuoProductCancle = (TextView) findViewById(R.id.activity_zidingyi_gonghuo_product_cancle);
        mActivityZidingyiGonghuoProductCancle.setOnClickListener(this);

        mToolbarTitle.setText("添加供货自定义商品");
        adapter = new CankaobiaozhunAdapter(R.layout.item_addcustom_product_cankaobiaozhun, datas);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        mToolbarTitle.setText("添加订货自定义商品");

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {

                    case R.id.item_wodehuodan_cankaobianzhun_reduce:
                        datas.remove(position);
                        adapter.notifyItemRemoved(position);
                        break;
                }
            }
        });

        addFootView();

    }

    private void addFootView() {

        View view = LayoutInflater.from(this).inflate(R.layout.add_custom_product_footview, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.add_custom_product_footview_add);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CaigoulianmengAddCankaobiaozhunDialog editDialog = new CaigoulianmengAddCankaobiaozhunDialog(ZidingyiGonghuoActivity.this);
                editDialog.setTitle("请输入参考标准");
                editDialog.setYesOnclickListener("确定", new CaigoulianmengAddCankaobiaozhunDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick(String content) {
                        if (StringUtils.isEmpty(content)) {
                            ToastUtils.showShort("请输入参考标准!");
                            return;
                        }
                        for (int i = 0; i < datas.size(); i++) {
                            if (StringUtils.equals(datas.get(i), content)) {
                                ToastUtils.showShort("不能添加重复的参考标准！");
                                if (KeyboardUtils.isSoftInputVisible(ZidingyiGonghuoActivity.this)) {
                                    inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                                }
                                editDialog.dismiss();
                                return;
                            }
                        }
                        datas.add(content);
                        adapter.notifyDataSetChanged();
                        editDialog.dismiss();
                        //让软键盘隐藏
                        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                });

                editDialog.setNoOnclickListener("取消", new CaigoulianmengAddCankaobiaozhunDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        editDialog.dismiss();
                    }
                });
                editDialog.show();

            }
        });
        adapter.addFooterView(view);
    }

    private ProductBean productBean = new ProductBean();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_zidingyi_gonghuo_product_date:
                onYearMonthDayPicker(mActivityZidingyiGonghuoProductDate);
                break;
            case R.id.activity_zidingyi_gonghuo_product_submit:

                if (StringUtils.isEmpty(mActivityZidingyiGonghuoProductName.getText().toString())) {
                    ToastUtils.showShort("商品名称不能为空");
                    return;
                }
                if (StringUtils.isEmpty(mActivityZidingyiGonghuoProductPrice.getText().toString())) {
                    ToastUtils.showShort("请填写价格");
                    return;
                }
                if (StringUtils.isEmpty(mActivityZidingyiGonghuoProductYudingliang.getText().toString())) {
                    ToastUtils.showShort("请填写预定量");
                    return;
                }
                if (StringUtils.isEmpty(mActivityZidingyiGonghuoProductBaozhuangxinshi.getText().toString())) {
                    ToastUtils.showShort("请填写包装形式");
                    return;
                }
                if (StringUtils.equals(mActivityZidingyiGonghuoProductDate.getText().toString(), "请选择")) {
                    ToastUtils.showShort("请填选择报价有效期");
                    return;
                }

                productBean.setShopName(mActivityZidingyiGonghuoProductName.getText().toString());
                productBean.setPrice(mActivityZidingyiGonghuoProductPrice.getText().toString());
                productBean.setReservationNum(mActivityZidingyiGonghuoProductYudingliang.getText().toString());
                productBean.setPacking(mActivityZidingyiGonghuoProductBaozhuangxinshi.getText().toString());
                productBean.setReferenceType(datas.toString().substring(1, datas.toString().length() - 1));
                productBean.setDate(mActivityZidingyiGonghuoProductDate.getText().toString());
                productBean.setChecked(true);
                productBean.setDiyShop("1");

                List<ProductBean.MeetingTypeListBean> meetingTypeListBeans = new ArrayList<>();
                for (int i = 0; i < datas.size(); i++) {
                    ProductBean.MeetingTypeListBean bean = new ProductBean.MeetingTypeListBean();
                    bean.setReferenceType(datas.get(i));
                    bean.setChecked(true);
                    meetingTypeListBeans.add(bean);
                }
                productBean.setMeetingTypeList(meetingTypeListBeans);
                EventBus.getDefault().post(new MessageBean(MessageBean.Code.CGLMTIANJIASHANGPIN, productBean));
                finish();
                break;
            case R.id.activity_zidingyi_gonghuo_product_cancle:
                finish();
                break;
        }
    }


    //选择年月
    private void onYearMonthDayPicker(final TextView date) {
        final DatePicker picker = new DatePicker(this);
        picker.setCycleDisable(true);
        picker.setTopPadding(15);
        picker.setRangeStart(CalendarUtil.getYear(), CalendarUtil.getMonth(), CalendarUtil.getDay());
        picker.setRangeEnd(2021, 1, 1);
        picker.setLineColor(Color.BLACK);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                date.setText(year + "-" + month + "-" + day);
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }
}
