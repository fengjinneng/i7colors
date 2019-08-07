package com.company.qcy.ui.activity.zhuji;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.CalendarUtil;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InputWindowListener;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.MyConsrantLayout;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.base.KeyboardChangeListener;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.bean.zhuji.ZhujiTypeBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.SinglePicker;

public class PubulishZhujiDiyActivity extends BaseActivity implements View.OnClickListener {

    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    /**
     * 请选择分类
     */
    private TextView mActicityPubulishZhujiDiyType;
    /**
     * 例如：仿蜡印增调剂
     */
    private EditText mActicityPubulishZhujiDiyName;
    /**
     * 例如：涤纶
     */
    private EditText mActicityPubulishZhujiCaizhi;
    /**
     * 例如：用于工厂工人装面料
     */
    private EditText mActicityPubulishZhujiChengpinyongtu;
    private EditText mActicityPubulishZhujiHuanbaoyaoqiu;
    private EditText mActicityPubulishZhujiShebei;
    private EditText mActicityPubulishZhujiRanliao;
    private EditText mActicityPubulishZhujiRansewendu;
    private EditText mActicityPubulishZhujiXianyongchanpinmingcheng;
    private EditText mActicityPubulishZhujiChangjia;
    private EditText mActicityPubulishZhujiXuqiuliang;
    /**
     * 50字以内......
     */
    private EditText mActicityPubulishZhujiXingnengmiaoshu;
    /**
     * 发布助剂定制
     */
    private TextView mActicityPubulishZhujiFabu;
    /**
     * 选择结束时间
     */
    private TextView mActicityPubulishZhujiJieshushijian;
    /**
     * 请输入公司名称
     */
    private EditText mActicityPubulishZhujiCompanyname;
    /**
     * 公司名称:
     */
    private TextView mActicityPubulishZhujiCompanynameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pubulish_zhuji_diy);
        initView();
    }


    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mActicityPubulishZhujiDiyType = (TextView) findViewById(R.id.acticity_pubulish_zhuji_diy_type);
        mActicityPubulishZhujiDiyType.setOnClickListener(this);
        mActicityPubulishZhujiDiyName = (EditText) findViewById(R.id.acticity_pubulish_zhuji_diy_name);
        mActicityPubulishZhujiCaizhi = (EditText) findViewById(R.id.acticity_pubulish_zhuji_caizhi);
        mActicityPubulishZhujiChengpinyongtu = (EditText) findViewById(R.id.acticity_pubulish_zhuji_chengpinyongtu);
        mActicityPubulishZhujiHuanbaoyaoqiu = (EditText) findViewById(R.id.acticity_pubulish_zhuji_huanbaoyaoqiu);
        mActicityPubulishZhujiShebei = (EditText) findViewById(R.id.acticity_pubulish_zhuji_shebei);
        mActicityPubulishZhujiRanliao = (EditText) findViewById(R.id.acticity_pubulish_zhuji_ranliao);
        mActicityPubulishZhujiRansewendu = (EditText) findViewById(R.id.acticity_pubulish_zhuji_ransewendu);
        mActicityPubulishZhujiXianyongchanpinmingcheng = (EditText) findViewById(R.id.acticity_pubulish_zhuji_xianyongchanpinmingcheng);
        mActicityPubulishZhujiChangjia = (EditText) findViewById(R.id.acticity_pubulish_zhuji_changjia);
        mActicityPubulishZhujiXuqiuliang = (EditText) findViewById(R.id.acticity_pubulish_zhuji_xuqiuliang);
        mActicityPubulishZhujiXingnengmiaoshu = (EditText) findViewById(R.id.acticity_pubulish_zhuji_xingnengmiaoshu);
        mActicityPubulishZhujiFabu = (TextView) findViewById(R.id.acticity_pubulish_zhuji_fabu);
        mActicityPubulishZhujiFabu.setOnClickListener(this);
        mToolbarTitle.setText("发布助剂定制");
        mActicityPubulishZhujiJieshushijian = (TextView) findViewById(R.id.acticity_pubulish_zhuji_jieshushijian);
        mActicityPubulishZhujiJieshushijian.setOnClickListener(this);

        mActicityPubulishZhujiCompanyname = (EditText) findViewById(R.id.acticity_pubulish_zhuji_companyname);
        mActicityPubulishZhujiCompanynameText = (TextView) findViewById(R.id.acticity_pubulish_zhuji_companyname_text);
        if (StringUtils.isEmpty(SPUtils.getInstance().getString("companyName"))) {
            //个人用户
            mActicityPubulishZhujiCompanyname.setVisibility(View.VISIBLE);
            mActicityPubulishZhujiCompanynameText.setVisibility(View.VISIBLE);
        } else {
            mActicityPubulishZhujiCompanyname.setVisibility(View.GONE);
            mActicityPubulishZhujiCompanynameText.setVisibility(View.GONE);

        }

        KeyboardChangeListener keyboardChangeListener = new KeyboardChangeListener(this);

        keyboardChangeListener.setKeyBoardListener(new KeyboardChangeListener.KeyBoardListener() {
            @Override
            public void onKeyboardChange(boolean isShow, int keyboardHeight) {
                if (isShow) {
                    mActicityPubulishZhujiFabu.setVisibility(View.GONE);
                } else {
                    mActicityPubulishZhujiFabu.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                KeyboardUtils.hideSoftInput(PubulishZhujiDiyActivity.this);
                finish();
                break;
            case R.id.acticity_pubulish_zhuji_diy_type:

                if (ObjectUtils.isEmpty(zhujiTypeBeans)) {
                    getZhujiType();
                } else {
                    choiceZhujiType(zhujiTypeBeans, mActicityPubulishZhujiDiyType);
                }
                break;
            case R.id.acticity_pubulish_zhuji_fabu:
                fabu();
                break;
            case R.id.acticity_pubulish_zhuji_jieshushijian:
                onEndtimePicker(mActicityPubulishZhujiJieshushijian);
                break;
        }
    }

    private void setBigyue(DatePicker picker) {
        if (31 - CalendarUtil.getDay() < 3) {
            if (CalendarUtil.getMonth() == 12) {
                picker.setRangeStart(CalendarUtil.getYear(), 1, (CalendarUtil.getDay() + 3) - 31);
            } else {
                picker.setRangeStart(CalendarUtil.getYear(), CalendarUtil.getMonth() + 1, (CalendarUtil.getDay() + 3) - 31);
            }
        } else
            picker.setRangeStart(CalendarUtil.getYear(), CalendarUtil.getMonth(), CalendarUtil.getDay() + 3);
    }

    private void setXiaoyue(DatePicker picker) {
        if (30 - CalendarUtil.getDay() < 3) {
            if (CalendarUtil.getMonth() == 12) {
                picker.setRangeStart(CalendarUtil.getYear(), 1, (CalendarUtil.getDay() + 3) - 30);
            } else {
                picker.setRangeStart(CalendarUtil.getYear(), CalendarUtil.getMonth() + 1, (CalendarUtil.getDay() + 3) - 30);
            }
        } else
            picker.setRangeStart(CalendarUtil.getYear(), CalendarUtil.getMonth(), CalendarUtil.getDay() + 3);
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

//                endtime_year = Integer.parseInt(year);
//                endtime_month = Integer.parseInt(month);
//                endtime_day = Integer.parseInt(day);
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


    private List<ZhujiTypeBean> zhujiTypeBeans;

    private void getZhujiType() {

        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.ZHUJITYPE)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"));
        DialogStringCallback stringCallback = new DialogStringCallback(PubulishZhujiDiyActivity.this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("ZHUJITYPE", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), "SUCCESS")) {
                            JSONArray data = jsonObject.getJSONArray("data");
                            if (ObjectUtils.isEmpty(data)) {
                                return;
                            }
                            zhujiTypeBeans = JSONObject.parseArray(jsonObject.getJSONArray("data").toString(), ZhujiTypeBean.class);
                            choiceZhujiType(zhujiTypeBeans, mActicityPubulishZhujiDiyType);
                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(PubulishZhujiDiyActivity.this, request, this);
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

    //单选
    private void choiceZhujiType(List<ZhujiTypeBean> data, final TextView tv) {

        List<String> nameList = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            nameList.add(data.get(i).getName());
        }

        SinglePicker<String> picker = new SinglePicker<String>(this, nameList);
        picker.setTitleText("请选择");
        picker.setCycleDisable(true);
        picker.show();
        picker.setOnItemPickListener(new SinglePicker.OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int i, String s) {
                tv.setText(s);
                for (int j = 0; j < data.size(); j++) {
                    if (StringUtils.equals(s, data.get(j).getName())) {
                        upLoadTypeId = data.get(j).getId();
                    }
                }
            }
        });
    }

    private Long upLoadTypeId;


    private void fabu() {

        if (StringUtils.isEmpty(SPUtils.getInstance().getString("companyName"))
                && StringUtils.isEmpty(mActicityPubulishZhujiCompanyname.getText().toString())) {
            ToastUtils.showShort("请填写公司名称");
            return;
        }

        if (StringUtils.equals("请选择分类", mActicityPubulishZhujiDiyType.getText().toString()) || upLoadTypeId == 0) {
            ToastUtils.showShort("请选择助剂定制分类");
            return;
        }

        if (StringUtils.isEmpty(mActicityPubulishZhujiDiyName.getText().toString())) {
            ToastUtils.showShort("请填写助剂名称");
            return;

        }
        if (StringUtils.isEmpty(mActicityPubulishZhujiCaizhi.getText().toString())) {
            ToastUtils.showShort("请填写材质");
            return;

        }

        if (StringUtils.isEmpty(mActicityPubulishZhujiChengpinyongtu.getText().toString())) {
            ToastUtils.showShort("请填写成品用途");
            return;

        }

        if (StringUtils.isEmpty(mActicityPubulishZhujiHuanbaoyaoqiu.getText().toString())) {
            ToastUtils.showShort("请填写环保要求");
            return;

        }

        if (StringUtils.isEmpty(mActicityPubulishZhujiXingnengmiaoshu.getText().toString())) {
            ToastUtils.showShort("请填写助剂性能描述");
            return;

        }
        if (StringUtils.equals("选择结束时间", mActicityPubulishZhujiJieshushijian.getText().toString())) {
            ToastUtils.showShort("请选择结束时间");
            return;

        }

        HttpParams paras = new HttpParams();
        paras.put("sign", SPUtils.getInstance().getString("sign"));
        paras.put("token", SPUtils.getInstance().getString("token"));
        paras.put("classId", upLoadTypeId);
        paras.put("zhujiName", mActicityPubulishZhujiDiyName.getText().toString());
        paras.put("companyName2", mActicityPubulishZhujiCompanyname.getText().toString());

        paras.put("endTime", mActicityPubulishZhujiJieshushijian.getText().toString());
        paras.put("description", mActicityPubulishZhujiXingnengmiaoshu.getText().toString());
        paras.put("material", mActicityPubulishZhujiCaizhi.getText().toString());
        paras.put("purpose", mActicityPubulishZhujiChengpinyongtu.getText().toString());
        paras.put("requirement", mActicityPubulishZhujiHuanbaoyaoqiu.getText().toString());

        paras.put("equipment", mActicityPubulishZhujiShebei.getText().toString());

        paras.put("dye", mActicityPubulishZhujiRanliao.getText().toString());
        paras.put("temperature", mActicityPubulishZhujiRansewendu.getText().toString());

        paras.put("productName", mActicityPubulishZhujiXianyongchanpinmingcheng.getText().toString());

        paras.put("num", mActicityPubulishZhujiXuqiuliang.getText().toString());

        paras.put("from", getResources().getString(R.string.app_android));

        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.FABUZHUJIDINGZHI)
                .tag(this)
                .params(paras);

        DialogStringCallback stringCallback = new DialogStringCallback(PubulishZhujiDiyActivity.this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("FABUZHUJIDINGZHI", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");

                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            KeyboardUtils.hideSoftInput(PubulishZhujiDiyActivity.this);
                            finish();
                            EventBus.getDefault().post(new MessageBean(MessageBean.Zhuji.FABUZHUJIDINGZHICHENGGONG));

                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(PubulishZhujiDiyActivity.this, request, this);
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
}
