package com.company.qcy.ui.activity.qiugoudating;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.AddressPickTask;
import com.company.qcy.Utils.CalendarUtil;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.bean.qiugou.QiugoufenleiBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.SinglePicker;

public class FabuqiugouActivity extends BaseActivity implements View.OnClickListener {

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
     * 其它时间
     */
    private CheckBox mActivityFabuqiugouQitashijian;
    /**
     * 选择其它时间手动输入账期
     */
    private EditText mActivityFabuqiugouShoudongzhangqi;
    /**
     * ,总数量为
     */
    private TextView mActivityFabuqiugouZongliangwei;
    /**
     * 10000
     */
    private TextView mActivityFabuqiugouZongliang;
    /**
     * KG
     */
    private TextView mActivityFabuqiugouZongliangkg;
    /**  */
    private TextView mActivityFabuqiugouDanweiText;
    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;

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
        mActivityFabuqiugouQitashijian = findViewById(R.id.activity_fabuqiugou_qitashijian);

        mActivityFabuqiugouFabuqiugou.setOnClickListener(this);
        mActivityFabuqiugouJiaohuoriqi.setOnClickListener(this);
        mActivityFabuqiugouFenlei.setOnClickListener(this);
        mActivityFabuqiugouErjifenlei.setOnClickListener(this);
        mActivityFabuqiugouFukuanfangshi.setOnClickListener(this);
        mActivityFabuqiugouZhangqi.setOnClickListener(this);
        mActivityFabuqiugouAddress.setOnClickListener(this);
        mActivityFabuqiugouDanwei.setOnClickListener(this);
        mActivityFabuqiugouJieshuriqi.setOnClickListener(this);
        mActivityFabuqiugouQitashijian.setOnClickListener(this);
        mActivityFabuqiugouQitashijian = (CheckBox) findViewById(R.id.activity_fabuqiugou_qitashijian);
        mActivityFabuqiugouShoudongzhangqi = (EditText) findViewById(R.id.activity_fabuqiugou_shoudongzhangqi);
        mActivityFabuqiugouZongliangwei = (TextView) findViewById(R.id.activity_fabuqiugou_zongliangwei);
        mActivityFabuqiugouZongliang = (TextView) findViewById(R.id.activity_fabuqiugou_zongliang);
        mActivityFabuqiugouZongliangkg = (TextView) findViewById(R.id.activity_fabuqiugou_zongliangkg);
        mActivityFabuqiugouDanweiText = (TextView) findViewById(R.id.activity_fabuqiugou_danwei_text);
        mActivityFabuqiugouShoudongzhangqi.setEnabled(false);

        mActivityFabuqiugouWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                jisuanzongliang();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mActivityFabuqiugouQiugoushuliang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                jisuanzongliang();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if (StringUtils.isEmpty(SPUtils.getInstance().getString("companyName"))) {

        } else {
            mActivityFabuqiugouCompanyName.setText(SPUtils.getInstance().getString("companyName"));
            mActivityFabuqiugouCompanyName.setEnabled(false);
        }
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mToolbarTitle.setText("发布求购");
    }


    private void jisuanzongliang() {
        if (StringUtils.isTrimEmpty(mActivityFabuqiugouWeight.getText().toString())
                || StringUtils.isTrimEmpty(mActivityFabuqiugouQiugoushuliang.getText().toString())) {
            mActivityFabuqiugouZongliangwei.setText("");
            mActivityFabuqiugouZongliang.setText("");
            mActivityFabuqiugouZongliangkg.setText("");
        } else {
            mActivityFabuqiugouZongliangwei.setText(" 总数量为");
            mActivityFabuqiugouZongliang.setText(String.valueOf(Integer.parseInt(mActivityFabuqiugouWeight.getText().toString())
                    * Integer.parseInt(mActivityFabuqiugouQiugoushuliang.getText().toString())));
            mActivityFabuqiugouZongliangkg.setText("KG");
        }
    }


    //账期是否选择其他时间
    private boolean checkedQitashijan;

    //是否是选择单位
    private boolean checkedDanwei;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            //发布求购
            case R.id.activity_fabuqiugou_fabuqiugou:
                if (checkIsNull()) {
                    fabuqiugou();
                }
                break;
            //选择交货日期
            case R.id.activity_fabuqiugou_jiaohuoriqi:
                if (ischoiceEndtime) {
                    onJiaohuoPicker(mActivityFabuqiugouJiaohuoriqi);
                } else ToastUtils.showShort("请先选择结束时间");
                break;
            //选择分类
            case R.id.activity_fabuqiugou_fenlei:
                choiceFenlei(mActivityFabuqiugouFenlei, 1);
                break;
            //选择耳二级分类
            case R.id.activity_fabuqiugou_erjifenlei:
                if (fenleiID == 0) {
                    ToastUtils.showShort("请先选择一级分类");
                    return;
                }
                choiceFenlei(mActivityFabuqiugouErjifenlei, 2);
                break;
            //选择付款方式
            case R.id.activity_fabuqiugou_fukuanfangshi:

                List<String> fkfs = new ArrayList<>();
                fkfs.add("现汇");
                fkfs.add("银行承兑");
                choiceString(fkfs, mActivityFabuqiugouFukuanfangshi);


                break;
            //账期
            case R.id.activity_fabuqiugou_zhangqi:

                List<String> zq = new ArrayList<>();
                zq.add("款到发货");
                zq.add("货到付款");
                zq.add("货到30天付款");
                zq.add("货到45天付款");
                zq.add("货到60天付款");

                choiceString(zq, mActivityFabuqiugouZhangqi);

                break;
            //选择地址
            case R.id.activity_fabuqiugou_address:
                choiceAddress(mActivityFabuqiugouAddress);
                break;
            //选择单位
            case R.id.activity_fabuqiugou_danwei:
                checkedDanwei = true;
                List<String> dw = new ArrayList<>();
                dw.add("箱");
                dw.add("桶");
                dw.add("袋");
                choiceString(dw, mActivityFabuqiugouDanwei);
                break;
            //结束日期
            case R.id.activity_fabuqiugou_jieshuriqi:
                onEndtimePicker(mActivityFabuqiugouJieshuriqi);
                break;

            //点击checkbox
            case R.id.activity_fabuqiugou_qitashijian:
                if (checkedQitashijan) {
                    mActivityFabuqiugouZhangqi.setEnabled(true);
                    mActivityFabuqiugouShoudongzhangqi.setEnabled(false);
                    mActivityFabuqiugouQitashijian.setChecked(false);
                    checkedQitashijan = false;


                } else {
                    mActivityFabuqiugouShoudongzhangqi.requestFocus();
                    mActivityFabuqiugouZhangqi.setText("请选择");
                    mActivityFabuqiugouZhangqi.setEnabled(false);
                    mActivityFabuqiugouShoudongzhangqi.setEnabled(true);
                    mActivityFabuqiugouQitashijian.setChecked(true);
                    checkedQitashijan = true;
                }

                break;
            case R.id.toolbar_back:
                finish();
                break;
        }
    }


    //请求参数

    //一级分类ID
    private String paramYijiID;

    //二级分类ID
    private String paramErjiID;

    //省
    private String locationProvince;

    //市
    private String locationCity;

    //账期
    private String paramZhangqi;

    private void fabuqiugou() {

        HttpParams paras = new HttpParams();
        paras.put("sign", SPUtils.getInstance().getString("sign"));
        paras.put("token", SPUtils.getInstance().getString("token"));
        paras.put("productName", mActivityFabuqiugouChanpinmingchen.getText().toString());

        if (StringUtils.isEmpty(SPUtils.getInstance().getString("companyName"))) {
            paras.put("companyName2", mActivityFabuqiugouCompanyName.getText().toString());
        }
        paras.put("productCli1", paramYijiID);
        paras.put("productCli2", paramErjiID);
        paras.put("pack", mActivityFabuqiugouDanwei.getText().toString());
        paras.put("num", Integer.parseInt(mActivityFabuqiugouWeight.getText().toString()) *
                Integer.parseInt(mActivityFabuqiugouQiugoushuliang.getText().toString()) + "");
        paras.put("numUnit", "KG");
        paras.put("locationProvince", locationProvince);
        paras.put("locationCity", locationCity);
        if (checkedQitashijan) {
            if (StringUtils.isTrimEmpty(mActivityFabuqiugouShoudongzhangqi.getText().toString())) {
                ToastUtils.showShort("请填写账期");
                return;
            } else paramZhangqi = mActivityFabuqiugouShoudongzhangqi.getText().toString();
        } else paramZhangqi = mActivityFabuqiugouZhangqi.getText().toString();
        paras.put("paymentPeriod", paramZhangqi);
        paras.put("deliveryDate", mActivityFabuqiugouJiaohuoriqi.getText().toString());
        paras.put("endTime", mActivityFabuqiugouJieshuriqi.getText().toString());
        paras.put("paymentType", mActivityFabuqiugouFukuanfangshi.getText().toString());
        paras.put("description", mActivityFabuqiugouXiangxishuoming.getText().toString());
        paras.put("from", getResources().getString(R.string.app_android));

        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.FABUQIUGOU)
                .tag(this)
                .params(paras);

        DialogStringCallback stringCallback = new DialogStringCallback(FabuqiugouActivity.this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("FABUQIUGOU", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");

                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            String data = jsonObject.getString("data");
                            if (StringUtils.equals("true", data)) {
                                EventBus.getDefault().post(new MessageBean(MessageBean.Code.FABUQIUGOUCHENGGONG));
                                ActivityUtils.finishActivity(FabuqiugouActivity.class);
                            } else ToastUtils.showShort(msg);
                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(FabuqiugouActivity.this, request, this);
                            return;
                        }
                        ToastUtils.showShort(msg);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                ToastUtils.showShort(getResources().getString(R.string.NETEXCEPTION));
            }
        };
        request.execute(stringCallback);

    }

    private void choiceFenlei(final TextView tv, final int level) {

        if (level == 1) {
            fenleiID = 0;
        }
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.QIUGOUFENLEI)
                .tag(this)

                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("parentId", fenleiID);


        DialogStringCallback stringCallback = new DialogStringCallback(FabuqiugouActivity.this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("choiceFenlei", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), "SUCCESS")) {
                            JSONArray data = jsonObject.getJSONArray("data");
                            List<QiugoufenleiBean> qiugoufenleiBean = JSONObject.parseArray(data.toJSONString(), QiugoufenleiBean.class);

                            List<String> names = new ArrayList<>();
                            for (int i = 0; i < qiugoufenleiBean.size(); i++) {
                                names.add(qiugoufenleiBean.get(i).getName());
                            }

                            choiceFenleiName(qiugoufenleiBean, names, tv, level);
                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(FabuqiugouActivity.this, request, this);
                            return;
                        }
                        ToastUtils.showShort(msg);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                ToastUtils.showShort(getResources().getString(R.string.NETEXCEPTION));
            }
        };

        request.execute(stringCallback);


    }

    private int fenleiID;

    //单选分类
    private void choiceFenleiName(final List<QiugoufenleiBean> data, List<String> names, final TextView tv, final int level) {//是第几级分类
        SinglePicker<String> picker = new SinglePicker<String>(this, names);
        picker.setTitleText("请选择");
        picker.setCycleDisable(true);
        picker.show();
        picker.setOnItemPickListener(new SinglePicker.OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int i, String s) {
                tv.setText(s);
                if (1 == level) {
                    fenleiID = data.get(i).getId();
                    paramYijiID = fenleiID + "";
                    mActivityFabuqiugouErjifenlei.setText("产品二级分类");
                }
                if (2 == level) {
                    paramErjiID = data.get(i).getId() + "";
                }
            }
        });
    }

    //单选
    private void choiceString(final List data, final TextView tv) {
        SinglePicker<String> picker = new SinglePicker<String>(this, data);
        picker.setTitleText("请选择");
        picker.setCycleDisable(true);
        picker.show();
        picker.setOnItemPickListener(new SinglePicker.OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int i, String s) {
                tv.setText(s);
                if (checkedDanwei) {
                    mActivityFabuqiugouDanweiText.setText(s);
                    checkedDanwei = false;
                }

            }
        });
    }


    private boolean checkIsNull() {
        if (StringUtils.isTrimEmpty(mActivityFabuqiugouCompanyName.getText().toString())) {
            ToastUtils.showShort("公司名称不能为空");
            return false;
        } else if (StringUtils.equals("请选择分类", mActivityFabuqiugouFenlei.getText().toString())) {
            ToastUtils.showShort("您还没有选择分类");
            return false;
        } else if (StringUtils.equals("产品二级分类", mActivityFabuqiugouErjifenlei.getText().toString())) {
            ToastUtils.showShort("您还没有选择二级分类");
            return false;
        } else if (StringUtils.isTrimEmpty(mActivityFabuqiugouChanpinmingchen.getText().toString())) {
            ToastUtils.showShort("产品名称不能为空");
            return false;
        } else if (StringUtils.isTrimEmpty(mActivityFabuqiugouQiugoushuliang.getText().toString())) {
            ToastUtils.showShort("请填写求购数量");
            return false;
        } else if (StringUtils.isTrimEmpty(mActivityFabuqiugouWeight.getText().toString())) {
            ToastUtils.showShort("您还没有填写重量");
            return false;
        } else if (StringUtils.equals("请选择单位", mActivityFabuqiugouDanwei.getText().toString())) {
            ToastUtils.showShort("您还没有选择单位");
            return false;
        } else if (StringUtils.equals("请选择", mActivityFabuqiugouFukuanfangshi.getText().toString())) {
            ToastUtils.showShort("您还没有选择付款方式");
            return false;
        } else if (StringUtils.equals("请选择", mActivityFabuqiugouZhangqi.getText().toString())) {
            ToastUtils.showShort("您还没有选择账期");
            return false;
        } else if (StringUtils.equals("请选择", mActivityFabuqiugouAddress.getText().toString())) {
            ToastUtils.showShort("您还没有选择地址");
            return false;
        } else if (StringUtils.equals("选择结束日期", mActivityFabuqiugouJieshuriqi.getText().toString())) {
            ToastUtils.showShort("您还没有选择结束日期");
            return false;
        } else if (StringUtils.equals("选择交货日期", mActivityFabuqiugouJiaohuoriqi.getText().toString())) {
            ToastUtils.showShort("您还没有选择交货日期");
            return false;
        }
        return true;
    }

    private int endtime_year;
    private int endtime_month;
    private int endtime_day;
    private boolean ischoiceEndtime;

    private void onJiaohuoPicker(TextView time) {
        final DatePicker picker = new DatePicker(this);
        picker.setCycleDisable(true);
        picker.setTopPadding(15);
        picker.setRangeStart(endtime_year, endtime_month, endtime_day);

        picker.setLineColor(Color.BLACK);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                time.setText(year + "-" + month + "-" + day);

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


    private void setBigyue(DatePicker picker) {
        if (31 - CalendarUtil.getDay() < 3) {
            if (CalendarUtil.getMonth() == 12) {
                picker.setRangeStart(CalendarUtil.getYear(), 1, (CalendarUtil.getDay() + 3) - 31);
                picker.setRangeEnd(CalendarUtil.getYear(), 2, (CalendarUtil.getDay() + 3) - 31);
            } else {
                picker.setRangeStart(CalendarUtil.getYear(), CalendarUtil.getMonth() + 1, (CalendarUtil.getDay() + 3) - 31);
                picker.setRangeEnd(CalendarUtil.getYear(), CalendarUtil.getMonth() + 2, (CalendarUtil.getDay() + 3) - 31);
            }
        } else {
            picker.setRangeStart(CalendarUtil.getYear(), CalendarUtil.getMonth(), CalendarUtil.getDay() + 3);
            picker.setRangeEnd(CalendarUtil.getYear(), CalendarUtil.getMonth() + 1, CalendarUtil.getDay() + 3);
        }
    }

    private void setXiaoyue(DatePicker picker) {
        if (30 - CalendarUtil.getDay() < 3) {
            if (CalendarUtil.getMonth() == 12) {
                picker.setRangeStart(CalendarUtil.getYear(), 1, (CalendarUtil.getDay() + 3) - 30);
                picker.setRangeEnd(CalendarUtil.getYear(), 2, (CalendarUtil.getDay() + 3) - 30);
            } else {
                picker.setRangeStart(CalendarUtil.getYear(), CalendarUtil.getMonth() + 1, (CalendarUtil.getDay() + 3) - 30);
                picker.setRangeEnd(CalendarUtil.getYear(), CalendarUtil.getMonth() + 2, (CalendarUtil.getDay() + 3) - 30);
            }
        } else {

            picker.setRangeStart(CalendarUtil.getYear(), CalendarUtil.getMonth(), CalendarUtil.getDay() + 3);
            picker.setRangeEnd(CalendarUtil.getYear(), CalendarUtil.getMonth() + 1, CalendarUtil.getDay() + 3);
        }
    }

    private void onEndtimePicker(TextView time) {
        DatePicker endTimePicker = new DatePicker(this);
        endTimePicker.setCycleDisable(true);
        endTimePicker.setTopPadding(15);

        switch (CalendarUtil.getMonth()) {
            case 1:
                setBigyue(endTimePicker);
                break;
            case 3:
                setBigyue(endTimePicker);
                break;
            case 5:
                setBigyue(endTimePicker);
                break;
            case 7:
                setBigyue(endTimePicker);
                break;
            case 8:
                setBigyue(endTimePicker);
                break;
            case 10:
                setBigyue(endTimePicker);
                break;
            case 12:
                setBigyue(endTimePicker);
                break;
            case 2:
                setXiaoyue(endTimePicker);
                break;
            case 4:
                setXiaoyue(endTimePicker);
                break;
            case 6:
                setXiaoyue(endTimePicker);
                break;
            case 9:
                setXiaoyue(endTimePicker);
                break;
            case 11:
                setXiaoyue(endTimePicker);
                break;
        }
        endTimePicker.setLineColor(Color.BLACK);
        endTimePicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                time.setText(year + "-" + month + "-" + day);
                ischoiceEndtime = true;
                endtime_year = Integer.parseInt(year);
                endtime_month = Integer.parseInt(month);
                endtime_day = Integer.parseInt(day);
                mActivityFabuqiugouJiaohuoriqi.setText("选择交货日期");
            }
        });
        endTimePicker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                endTimePicker.setTitleText(year + "-" + endTimePicker.getSelectedMonth() + "-" + endTimePicker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                endTimePicker.setTitleText(endTimePicker.getSelectedYear() + "-" + month + "-" + endTimePicker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                endTimePicker.setTitleText(endTimePicker.getSelectedYear() + "-" + endTimePicker.getSelectedMonth() + "-" + day);
            }
        });
        endTimePicker.show();
    }


    //选择地址
    private void choiceAddress(final TextView address) {
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
                    address.setText(province.getAreaName() + " " + city.getAreaName());
                    locationProvince = province.getAreaName();
                    locationCity = city.getAreaName();
                } else {
                    address.setText(province.getAreaName() + city.getAreaName() + county.getAreaName());
                }
            }
        });
        task.execute();
    }
}
