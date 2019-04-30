package com.company.qcy.huodong.caigoulianmeng2.activity;

import android.content.Context;
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
import com.company.qcy.Utils.ShurukuangDialog;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.huodong.caigoulianmeng2.adapter.CankaobiaozhunAdapter;
import com.company.qcy.huodong.caigoulianmeng2.bean.ProductBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class ZidingyiDinghuoActivity extends BaseActivity implements View.OnClickListener {

    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private EditText mActivityZidingyiDinghuoProductName;
    private EditText mActivityZidingyiDinghuoProductYudingliang;
    private EditText mActivityZidingyiDinghuoProductBaozhuangxinshi;
    private RecyclerView recyclerView;
    private TextView mActivityZidingyiDinghuoProductSubmit;
    private TextView mActivityZidingyiDinghuoProductCancle;
    private CankaobiaozhunAdapter adapter;
    private List<String> datas;
    private InputMethodManager inputMethodManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_zidingyi_dinghuo);
        initView();
    }

    private void initView() {
        datas = new ArrayList<>();
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mActivityZidingyiDinghuoProductName = (EditText) findViewById(R.id.activity_zidingyi_dinghuo_product_name);
        mActivityZidingyiDinghuoProductYudingliang = (EditText) findViewById(R.id.activity_zidingyi_dinghuo_product_yudingliang);
        mActivityZidingyiDinghuoProductBaozhuangxinshi = (EditText) findViewById(R.id.activity_zidingyi_dinghuo_product_baozhuangxinshi);
        recyclerView = (RecyclerView) findViewById(R.id.activity_zidingyi_dinghuo_product_recyclerview);
        mActivityZidingyiDinghuoProductSubmit = (TextView) findViewById(R.id.activity_zidingyi_dinghuo_product_submit);
        mActivityZidingyiDinghuoProductCancle = (TextView) findViewById(R.id.activity_zidingyi_dinghuo_product_cancle);

        adapter = new CankaobiaozhunAdapter(R.layout.item_addcustom_product_cankaobiaozhun, datas);
        //创建布局管理
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        mToolbarTitle.setText("添加订货自定义商品");

        addFootView();

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

        mActivityZidingyiDinghuoProductSubmit.setOnClickListener(this);
        mActivityZidingyiDinghuoProductCancle.setOnClickListener(this);
    }

    private List<String> cankaobiazhunList = new ArrayList<>();//参考标准的集合

    private ProductBean productBean = new ProductBean();


    private void addFootView() {
        View view = LayoutInflater.from(this).inflate(R.layout.add_custom_product_footview, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.add_custom_product_footview_add);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CaigoulianmengAddCankaobiaozhunDialog editDialog = new CaigoulianmengAddCankaobiaozhunDialog(ZidingyiDinghuoActivity.this);
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
                                    if (KeyboardUtils.isSoftInputVisible(ZidingyiDinghuoActivity.this)) {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_zidingyi_dinghuo_product_submit:

                if (StringUtils.isEmpty(mActivityZidingyiDinghuoProductName.getText().toString())) {
                    ToastUtils.showShort("商品名称不能为空");
                    return;
                }
                if (StringUtils.isEmpty(mActivityZidingyiDinghuoProductYudingliang.getText().toString())) {
                    ToastUtils.showShort("请填写预定量");
                    return;
                }
                if (StringUtils.isEmpty(mActivityZidingyiDinghuoProductBaozhuangxinshi.getText().toString())) {
                    ToastUtils.showShort("请填写包装形式");
                    return;
                }
                productBean.setShopName(mActivityZidingyiDinghuoProductName.getText().toString());
                productBean.setReservationNum(mActivityZidingyiDinghuoProductYudingliang.getText().toString());
                productBean.setPacking(mActivityZidingyiDinghuoProductBaozhuangxinshi.getText().toString());
                productBean.setReferenceType(datas.toString().substring(1, datas.toString().length() - 1));
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
            case R.id.activity_zidingyi_dinghuo_product_cancle:
                finish();
                break;
        }
    }
}
