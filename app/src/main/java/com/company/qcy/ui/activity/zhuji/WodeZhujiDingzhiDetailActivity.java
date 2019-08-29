package com.company.qcy.ui.activity.zhuji;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.MyCommonUtil;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.zhuji.WodeZhujiDetailFangAnAdapter;
import com.company.qcy.base.BaseActivity;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.bean.zhuji.ZhujiBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WodeZhujiDingzhiDetailActivity extends BaseActivity implements View.OnClickListener {

    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    /**
     * 提交方案
     */
    private TextView mActivityWodeZhujiDetailSubmit;
    private RecyclerView recyclerView;
    private List<ZhujiBean.SolutionListBean> datas;
    private WodeZhujiDetailFangAnAdapter adapter;
    private Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wode_zhuji_detail);
        id = getIntent().getLongExtra("id", 0);
        initView();
    }

    private void initView() {
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mActivityWodeZhujiDetailSubmit = (TextView) findViewById(R.id.activity_wode_zhuji_detail_submit);
        mActivityWodeZhujiDetailSubmit.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.activity_wode_zhuji_detail_recyclerview);
        mToolbarTitle.setText("我的助剂定制详情");

        datas = new ArrayList<>();
        adapter = new WodeZhujiDetailFangAnAdapter(R.layout.item_zhuji_fangan, datas);

        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        addData();


        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.item_zhuji_fangan_caina:
                        ZhujiBean.SolutionListBean item = (ZhujiBean.SolutionListBean) adapter.getData().get(position);

                        if (StringUtils.equals("diying", item.getStatus())) {

                            AlertDialog.Builder dialog = new AlertDialog.Builder(WodeZhujiDingzhiDetailActivity.this);
                            dialog.setTitle("提示!");
                            dialog.setMessage("您是否确认采纳此方案！");
                            dialog.setPositiveButton("采纳", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    caiNa(item.getId());
                                    dialog.dismiss();
                                }
                            });
                            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }

                        break;
                }
            }
        });
    }

    private void caiNa(Long id) {

        HttpParams params = new HttpParams();

        params.put("sign", SPUtils.getInstance().getString("sign"));
        params.put("token", SPUtils.getInstance().getString("token"));
        params.put("id", id);

        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.CAINAFANGAN)
                .tag(this)
                .params(params);

        DialogStringCallback stringCallback = new DialogStringCallback(this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("CAINAFANGAN", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");

                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {

                            EventBus.getDefault().post(new MessageBean(MessageBean.Zhuji.CAINAFANGAN, String.valueOf(id)));

                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(WodeZhujiDingzhiDetailActivity.this, request, this);
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


    private void addData() {

        HttpParams params = new HttpParams();

        params.put("sign", SPUtils.getInstance().getString("sign"));
        params.put("token", SPUtils.getInstance().getString("token"));
        params.put("id", id);

        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.WODEZHUJIDETAIL)
                .tag(this)
                .params(params);

        DialogStringCallback stringCallback = new DialogStringCallback(this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("WODEZHUJIDETAIL", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");

                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {

                            JSONObject data = jsonObject.getJSONObject("data");
                            if (ObjectUtils.isEmpty(data)) {
                                return;
                            }
                            ZhujiBean zhujiBean = data.toJavaObject(ZhujiBean.class);
                            if (!ObjectUtils.isEmpty(zhujiBean)) {
                                setData(zhujiBean);
                            }

                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(WodeZhujiDingzhiDetailActivity.this, request, this);
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

    private void setData(ZhujiBean zhujiBean) {

        addHeadView(zhujiBean);
        List<ZhujiBean.SolutionListBean> solutionList = zhujiBean.getSolutionList();
        datas.addAll(solutionList);
        adapter.setNewData(solutionList);

    }

    @Override
    public void onReciveMessage(MessageBean msg) {
        super.onReciveMessage(msg);

        switch (msg.getCode()) {
            case MessageBean.Zhuji.CLOSEZHUJIDIY:
                mActivityWodeZhujiDetailSubmit.setVisibility(View.GONE);
                if (!ObjectUtils.isEmpty(yiwancheng)) {
                    yiwancheng.setVisibility(View.VISIBLE);
                }
                break;
            case MessageBean.Zhuji.CAINAFANGAN:
                mActivityWodeZhujiDetailSubmit.setVisibility(View.GONE);
                long l = Long.parseLong(msg.getMeaasge());
                for (int i = 0; i < datas.size(); i++) {
                    if (datas.get(i).getId() == l) {
                        datas.get(i).setStatus("accept");
                    } else {
                        datas.get(i).setStatus("not_accept");

                    }
                }
                adapter.notifyDataSetChanged();
                break;


        }
    }

    private TextView yiwancheng;

    private void addHeadView(ZhujiBean bean) {

        View view = getLayoutInflater().inflate(R.layout.headview_wode_zhuji_detail, null);
        TextView zhujimingcheng = view.findViewById(R.id.activity_zhuji_detail_zhujimingcheng);
        TextView type = view.findViewById(R.id.activity_zhuji_detail_type);
        TextView name = view.findViewById(R.id.activity_zhuji_detail_name);
        TextView caizhi = view.findViewById(R.id.activity_zhuji_detail_caizhi);
        TextView chengpinyongtu = view.findViewById(R.id.activity_zhuji_detail_chengpinyongtu);
        TextView huanbaoyaoqiu = view.findViewById(R.id.activity_zhuji_detail_huanbaoyaoqiu);
        TextView shebei = view.findViewById(R.id.activity_zhuji_detail_shebei);
        TextView ranliao = view.findViewById(R.id.activity_zhuji_detail_ranliao);
        TextView ransewendu = view.findViewById(R.id.activity_zhuji_detail_ransewendu);
        TextView xianyongchanpinshuoming = view.findViewById(R.id.activity_zhuji_detail_xianyongchanpinmingcheng);
        TextView shengchanchangjia = view.findViewById(R.id.activity_zhuji_detail_shengchanchangjia);
        TextView meiyueyongliang = view.findViewById(R.id.activity_zhuji_detail_meiyueyongliang);
        TextView xingnengmiaoshu = view.findViewById(R.id.activity_zhuji_detail_xingnengmiaoshu);
        TextView qian = view.findViewById(R.id.activity_zhuji_detail_qian);
        TextView qianUnit = view.findViewById(R.id.activity_zhuji_detail_qian_unit);
        TextView hou = view.findViewById(R.id.activity_zhuji_detail_hou);
        TextView houUnit = view.findViewById(R.id.activity_zhuji_detail_hou_unit);
        TextView xuqiuliang  = view.findViewById(R.id.activity_zhuji_detail_xuqiuliang);
        TextView noData = view.findViewById(R.id.headview_wode_zhuji_detail_no_solution);
        yiwancheng = view.findViewById(R.id.activity_zhuji_detail_yiwancheng);
        if (ObjectUtils.isEmpty(bean.getSolutionList())) {
            noData.setVisibility(View.VISIBLE);
        }

        zhujimingcheng.setText(StringUtils.isEmpty(bean.getZhujiName()) ? "暂无" : bean.getZhujiName());
        type.setText(StringUtils.isEmpty(bean.getClassName()) ? "暂无" : bean.getClassName());
        name.setText(StringUtils.isEmpty(bean.getZhujiName()) ? "暂无" : bean.getZhujiName());
        caizhi.setText(StringUtils.isEmpty(bean.getMaterial()) ? "暂无" : bean.getMaterial());
        chengpinyongtu.setText(StringUtils.isEmpty(bean.getPurpose()) ? "暂无" : bean.getPurpose());
        huanbaoyaoqiu.setText(StringUtils.isEmpty(bean.getRequirement()) ? "暂无" : bean.getRequirement());
        shebei.setText(StringUtils.isEmpty(bean.getEquipment()) ? "暂无" : bean.getEquipment());
        ranliao.setText(StringUtils.isEmpty(bean.getDye()) ? "暂无" : bean.getDye());
        ransewendu.setText(StringUtils.isEmpty(bean.getTemperature()) ? "暂无" : bean.getTemperature());
        xianyongchanpinshuoming.setText(StringUtils.isEmpty(bean.getProductName()) ? "暂无" : bean.getProductName());
        shengchanchangjia.setText(StringUtils.isEmpty(bean.getProducer()) ? "暂无" : bean.getProducer());
        meiyueyongliang.setText(ObjectUtils.isEmpty(bean.getUseNumStr()) ? "暂无" : bean.getUseNumStr());
        xingnengmiaoshu.setText(StringUtils.isEmpty(bean.getDescription()) ? "暂无" : bean.getDescription());
        xuqiuliang.setText(StringUtils.isEmpty(bean.getDiyNumStr()) ? "暂无" : bean.getDiyNumStr());
        TextView text = view.findViewById(R.id.textView364);

        text.setText("收到的解决方案");

        if (StringUtils.isEmpty(bean.getStatus())) {
        } else {
            if (StringUtils.equals("diying", bean.getStatus())) {
                mActivityWodeZhujiDetailSubmit.setVisibility(View.VISIBLE);
                yiwancheng.setVisibility(View.GONE);
            } else {
                yiwancheng.setVisibility(View.VISIBLE);
            }
        }


        if (StringUtils.isEmpty(bean.getEndTimeStamp())) {
            qian.setText("");
            qianUnit.setText("");
            hou.setText("");
            houUnit.setText("");
        } else {
            MyCommonUtil.setDaojishiDate(bean.getEndTimeStamp(),
                    qian, qianUnit, hou, houUnit);
        }


        adapter.addHeaderView(view);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.activity_wode_zhuji_detail_submit:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("提示!");
                dialog.setMessage("您是否确认关闭此助剂定制！");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        closeZhujiDiy();
                        dialog.dismiss();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
        }
    }


    private void closeZhujiDiy() {

        HttpParams params = new HttpParams();

        params.put("sign", SPUtils.getInstance().getString("sign"));
        params.put("token", SPUtils.getInstance().getString("token"));
        params.put("status", "close");
        params.put("id", id);

        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.CLOSEZHUJIDIY)
                .tag(this)
                .params(params);

        DialogStringCallback stringCallback = new DialogStringCallback(this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("CLOSEZHUJIDIY", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");

                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {

                            EventBus.getDefault().post(new MessageBean(MessageBean.Zhuji.CLOSEZHUJIDIY));

                            return;
                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(WodeZhujiDingzhiDetailActivity.this, request, this);
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
