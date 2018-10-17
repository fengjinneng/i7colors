package com.company.qcy.ui.activity.qiugoudating;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.AddressPickTask;
import com.company.qcy.Utils.CalendarUtil;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.SinglePicker;


public class FabuqiugouActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mActivityFabuqiugouCompanyName;
    /**
     * 请输入产品名称
     */
    private EditText mActivityFabuqiugouChanpinmingchen;
    /**
     * 填写重量
     */
    private EditText mActivityFabuqiugouWeight;
    /**
     * 请选择分类
     */
    private TextView mActivityFabuqiugouFenlei;
    /**
     * 产品二级分类
     */
    private TextView mActivityFabuqiugouErjifenlei;
    /**
     * 请填写数量
     */
    private EditText mActivityFabuqiugouQiugoushuliang;
    /**
     * 请选择
     */
    private TextView mActivityFabuqiugouFukuanfangshi;
    /**
     * 请选择
     */
    private TextView mActivityFabuqiugouZhangqi;
    /**
     * 请选择
     */
    private TextView mActivityFabuqiugouAddress;
    /**
     * 请选择单位
     */
    private TextView mActivityFabuqiugouDanwei;
    /**
     * 选择结束日期
     */
    private TextView mActivityFabuqiugouJieshuriqi;
    /**
     * 选择交货日期
     */
    private TextView mActivityFabuqiugouJiaohuoriqi;
    /**
     * 说明在50字以内...
     */
    private EditText mActivityFabuqiugouXiangxishuoming;
    /**
     * 发布求购
     */
    private Button mActivityFabuqiugouFabuqiugou;

    /**
     * 发布求购
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabuqiugou);
        initView();
    }

    private void initView() {

        mActivityFabuqiugouCompanyName = (EditText) findViewById(R.id.activity_fabuqiugou_company_name);
        mActivityFabuqiugouChanpinmingchen = (EditText) findViewById(R.id.activity_fabuqiugou_chanpinmingchen);
        mActivityFabuqiugouWeight = (EditText) findViewById(R.id.activity_fabuqiugou_weight);
        mActivityFabuqiugouFenlei = (TextView) findViewById(R.id.activity_fabuqiugou_fenlei);
        mActivityFabuqiugouErjifenlei = (TextView) findViewById(R.id.activity_fabuqiugou_erjifenlei);
        mActivityFabuqiugouQiugoushuliang = (EditText) findViewById(R.id.activity_fabuqiugou_qiugoushuliang);
        mActivityFabuqiugouFukuanfangshi = (TextView) findViewById(R.id.activity_fabuqiugou_fukuanfangshi);
        mActivityFabuqiugouZhangqi = (TextView) findViewById(R.id.activity_fabuqiugou_zhangqi);
        mActivityFabuqiugouAddress = (TextView) findViewById(R.id.activity_fabuqiugou_address);
        mActivityFabuqiugouDanwei = (TextView) findViewById(R.id.activity_fabuqiugou_danwei);
        mActivityFabuqiugouJieshuriqi = (TextView) findViewById(R.id.activity_fabuqiugou_jieshuriqi);
        mActivityFabuqiugouJiaohuoriqi = (TextView) findViewById(R.id.activity_fabuqiugou_jiaohuoriqi);
        mActivityFabuqiugouXiangxishuoming = (EditText) findViewById(R.id.activity_fabuqiugou_xiangxishuoming);
        mActivityFabuqiugouFabuqiugou = (Button) findViewById(R.id.activity_fabuqiugou_fabuqiugou);

        mActivityFabuqiugouFabuqiugou.setOnClickListener(this);
        mActivityFabuqiugouJiaohuoriqi.setOnClickListener(this);
        mActivityFabuqiugouFenlei.setOnClickListener(this);
        mActivityFabuqiugouErjifenlei.setOnClickListener(this);
        mActivityFabuqiugouFukuanfangshi.setOnClickListener(this);
        mActivityFabuqiugouZhangqi.setOnClickListener(this);
        mActivityFabuqiugouAddress.setOnClickListener(this);
        mActivityFabuqiugouDanwei.setOnClickListener(this);
        mActivityFabuqiugouJieshuriqi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            //发布求购
            case R.id.activity_fabuqiugou_fabuqiugou:


                break;
            //选择交货日期
            case R.id.activity_fabuqiugou_jiaohuoriqi:
                onYearMonthDayPicker();
                break;
            //选择分类
            case R.id.activity_fabuqiugou_fenlei:
                break;
            //选择耳二级分类
            case R.id.activity_fabuqiugou_erjifenlei:
                break;
            //选择付款方式
            case R.id.activity_fabuqiugou_fukuanfangshi:

                List<String> fkfs = new ArrayList<>();
                fkfs.add("现汇");
                fkfs.add("银行承兑");
                String fukuanfangsh = choiceString(fkfs);

                break;
            //账期
            case R.id.activity_fabuqiugou_zhangqi:

                List<String> zq = new ArrayList<>();
                zq.add("货到发款");
                zq.add("货到付款");
                zq.add("货到30天付款");
                zq.add("货到45天付款");zq.add("货到60天付款");

                String zhangqi = choiceString(zq);
                break;
            //选择地址
            case R.id.activity_fabuqiugou_address:
                choiceAddress();
                break;
            //选择单位
            case R.id.activity_fabuqiugou_danwei:
                List<String> dw = new ArrayList<>();
                dw.add("箱");
                dw.add("桶");
                dw.add("袋");
                String danwei = choiceString(dw);
                break;
            //结束日期
            case R.id.activity_fabuqiugou_jieshuriqi:
                onYearMonthDayPicker();
                break;
        }
    }

    private String string;
    private  String choiceString(List data){
        string =null;
        SinglePicker<String> picker = new SinglePicker<String>(this, data);
        picker.setTitleText("请选择");
        picker.setCycleDisable(true);
        picker.show();
        picker.setOnItemPickListener(new SinglePicker.OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int i, String s) {
                string = s;
            }
        });
        return string;
    }



    //选择年月
    private void onYearMonthDayPicker() {
        final DatePicker picker = new DatePicker(this);
        picker.setCycleDisable(true);
        picker.setTopPadding(15);
        picker.setRangeStart(CalendarUtil.getYear(), CalendarUtil.getMonth(), CalendarUtil.getDay());
        picker.setRangeEnd(2020, 1, 1);
        picker.setLineColor(Color.BLACK);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                ToastUtils.showShort(year + "-" + month + "-" + day);
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


    //选择地址
    private void choiceAddress() {
        AddressPickTask task = new AddressPickTask(this);
        task.setHideProvince(false);
        task.setHideCounty(true);

        task.setCallback(new AddressPickTask.Callback() {
            @Override
            public void onAddressInitFailed() {
                ToastUtils.showShort("初始化失败！");
            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {
                if (county == null) {
                    ToastUtils.showShort(province.getAreaName() + city.getAreaName());
                } else {
                    ToastUtils.showShort(province.getAreaName() + city.getAreaName() + county.getAreaName());
                }
            }
        });
        task.execute();
    }
}
