package com.company.qcy.ui.activity.pengyouquan;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.RecyclerviewDisplayDecoration;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.adapter.pengyouquan.TongxunluFriendsAdapter;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.bean.pengyouquan.MyFriendsBean;
import com.gjiazhe.wavesidebar.WaveSideBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MyTongunluFriendsActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 标题
     */
    private TextView mToolbarTitle;
    private ImageView mToolbarBack;
    private WaveSideBar mSideBar;
    private RecyclerView recyclerview;
    private TongxunluFriendsAdapter adapter;
    private List<MyFriendsBean> datas;
    private TextView mToolbarText;

    private String from;//谁可以看或者提醒谁看

    private ArrayList<MyFriendsBean> tixingshuikanDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tongxunlu_friends);

        from = getIntent().getStringExtra("from");
        tixingshuikanDatas = getIntent().getParcelableArrayListExtra("tixingshuikan");
        initView();

        LogUtils.e("cxzcxzvwvdwwewqqq",tixingshuikanDatas);

    }

    private void initView() {
        datas = new ArrayList<>();
        mToolbarText = (TextView) findViewById(R.id.toolbar_text);
        mToolbarText.setOnClickListener(this);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarBack = (ImageView) findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(this);
        mSideBar = (WaveSideBar) findViewById(R.id.side_bar);
        mToolbarTitle.setText("我的好友");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mSideBar.setOnSelectIndexItemListener(new WaveSideBar.OnSelectIndexItemListener() {
            @Override
            public void onSelectIndexItem(String index) {
                for (int i = 0; i < datas.size(); i++) {

                    if (datas.get(i).getIndex().equals(index)) {
                        manager.scrollToPositionWithOffset(i, 0);
                        return;
                    }
                }
            }
        });

        recyclerview = (RecyclerView) findViewById(R.id.activity_my_friends_recyclerview);
        adapter = new TongxunluFriendsAdapter(R.layout.item_my_tongxunlu_friend, datas);
        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(manager);
        recyclerview.addItemDecoration(new RecyclerviewDisplayDecoration(this));

        if(ObjectUtils.isEmpty(tixingshuikanDatas)){
            addData();
        }else {
            datas.addAll(tixingshuikanDatas);
            adapter.setNewData(datas);
        }
        mToolbarText.setVisibility(View.VISIBLE);
        mToolbarText.setText("完成");

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                MyFriendsBean myFriendsBean = (MyFriendsBean) adapter.getData().get(position);
                CheckBox checkBox = (CheckBox) adapter.getViewByPosition(recyclerview, position, R.id.item_my_tongxunlu_friends_checkbox);



                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            myFriendsBean.setChecked(true);
                        } else {
                            myFriendsBean.setChecked(false);
                        }
                    }
                });

                if (myFriendsBean.isChecked()) {
                    checkBox.setChecked(false);
                } else {
                    checkBox.setChecked(true);

                }
            }
        });
    }

    private void addData() {
        GetRequest<String> request = OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.GETMYTONGXUNLUFRIENDSLIST)
                .tag(this)

                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("token", SPUtils.getInstance().getString("token"));

        DialogStringCallback stringCallback = new DialogStringCallback(this) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("GETMYFRIENDSLIST", response.body());

                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            JSONObject object = jsonObject.getJSONObject("data");

                            if (ObjectUtils.isEmpty(object)) {
                                adapter.loadMoreEnd();
                                return;
                            }

                            JSONArray other = object.getJSONArray("#");
                            JSONArray a = object.getJSONArray("a");
                            JSONArray b = object.getJSONArray("b");
                            JSONArray c = object.getJSONArray("c");
                            JSONArray d = object.getJSONArray("d");
                            JSONArray e = object.getJSONArray("e");
                            JSONArray f = object.getJSONArray("f");
                            JSONArray g = object.getJSONArray("g");
                            JSONArray h = object.getJSONArray("h");
                            JSONArray i = object.getJSONArray("i");
                            JSONArray j = object.getJSONArray("j");
                            JSONArray k = object.getJSONArray("k");
                            JSONArray l = object.getJSONArray("l");
                            JSONArray m = object.getJSONArray("m");
                            JSONArray n = object.getJSONArray("n");
                            JSONArray o = object.getJSONArray("o");
                            JSONArray p = object.getJSONArray("p");
                            JSONArray q = object.getJSONArray("q");
                            JSONArray r = object.getJSONArray("r");
                            JSONArray s = object.getJSONArray("s");
                            JSONArray t = object.getJSONArray("t");
                            JSONArray u = object.getJSONArray("u");
                            JSONArray v = object.getJSONArray("v");
                            JSONArray w = object.getJSONArray("w");
                            JSONArray x = object.getJSONArray("x");
                            JSONArray y = object.getJSONArray("y");
                            JSONArray z = object.getJSONArray("z");

                            List<MyFriendsBean> others = JSONObject.parseArray(other.toJSONString(), MyFriendsBean.class);
                            List<MyFriendsBean> as = JSONObject.parseArray(a.toJSONString(), MyFriendsBean.class);
                            List<MyFriendsBean> bs = JSONObject.parseArray(b.toJSONString(), MyFriendsBean.class);
                            List<MyFriendsBean> cs = JSONObject.parseArray(c.toJSONString(), MyFriendsBean.class);
                            List<MyFriendsBean> ds = JSONObject.parseArray(d.toJSONString(), MyFriendsBean.class);
                            List<MyFriendsBean> es = JSONObject.parseArray(e.toJSONString(), MyFriendsBean.class);
                            List<MyFriendsBean> fs = JSONObject.parseArray(f.toJSONString(), MyFriendsBean.class);
                            List<MyFriendsBean> gs = JSONObject.parseArray(g.toJSONString(), MyFriendsBean.class);
                            List<MyFriendsBean> hs = JSONObject.parseArray(h.toJSONString(), MyFriendsBean.class);
                            List<MyFriendsBean> is = JSONObject.parseArray(i.toJSONString(), MyFriendsBean.class);
                            List<MyFriendsBean> js = JSONObject.parseArray(j.toJSONString(), MyFriendsBean.class);
                            List<MyFriendsBean> ks = JSONObject.parseArray(k.toJSONString(), MyFriendsBean.class);
                            List<MyFriendsBean> ls = JSONObject.parseArray(l.toJSONString(), MyFriendsBean.class);
                            List<MyFriendsBean> ms = JSONObject.parseArray(m.toJSONString(), MyFriendsBean.class);
                            List<MyFriendsBean> ns = JSONObject.parseArray(n.toJSONString(), MyFriendsBean.class);
                            List<MyFriendsBean> os = JSONObject.parseArray(o.toJSONString(), MyFriendsBean.class);
                            List<MyFriendsBean> ps = JSONObject.parseArray(p.toJSONString(), MyFriendsBean.class);
                            List<MyFriendsBean> qs = JSONObject.parseArray(q.toJSONString(), MyFriendsBean.class);
                            List<MyFriendsBean> rs = JSONObject.parseArray(r.toJSONString(), MyFriendsBean.class);
                            List<MyFriendsBean> ss = JSONObject.parseArray(s.toJSONString(), MyFriendsBean.class);
                            List<MyFriendsBean> ts = JSONObject.parseArray(t.toJSONString(), MyFriendsBean.class);
                            List<MyFriendsBean> us = JSONObject.parseArray(u.toJSONString(), MyFriendsBean.class);
                            List<MyFriendsBean> vs = JSONObject.parseArray(v.toJSONString(), MyFriendsBean.class);
                            List<MyFriendsBean> ws = JSONObject.parseArray(w.toJSONString(), MyFriendsBean.class);
                            List<MyFriendsBean> xs = JSONObject.parseArray(x.toJSONString(), MyFriendsBean.class);
                            List<MyFriendsBean> ys = JSONObject.parseArray(y.toJSONString(), MyFriendsBean.class);
                            List<MyFriendsBean> zs = JSONObject.parseArray(z.toJSONString(), MyFriendsBean.class);

                            for (MyFriendsBean myFriendsBean : others) {
                                myFriendsBean.setIndex("#");
                            }
                            for (MyFriendsBean myFriendsBean : as) {
                                myFriendsBean.setIndex("A");
                            }
                            for (MyFriendsBean myFriendsBean : bs) {
                                myFriendsBean.setIndex("B");
                            }
                            for (MyFriendsBean myFriendsBean : cs) {
                                myFriendsBean.setIndex("C");
                            }
                            for (MyFriendsBean myFriendsBean : ds) {
                                myFriendsBean.setIndex("D");
                            }
                            for (MyFriendsBean myFriendsBean : es) {
                                myFriendsBean.setIndex("E");
                            }
                            for (MyFriendsBean myFriendsBean : fs) {
                                myFriendsBean.setIndex("F");
                            }
                            for (MyFriendsBean myFriendsBean : gs) {
                                myFriendsBean.setIndex("G");
                            }
                            for (MyFriendsBean myFriendsBean : hs) {
                                myFriendsBean.setIndex("H");
                            }
                            for (MyFriendsBean myFriendsBean : is) {
                                myFriendsBean.setIndex("I");
                            }
                            for (MyFriendsBean myFriendsBean : js) {
                                myFriendsBean.setIndex("J");
                            }
                            for (MyFriendsBean myFriendsBean : ks) {
                                myFriendsBean.setIndex("K");
                            }
                            for (MyFriendsBean myFriendsBean : ls) {
                                myFriendsBean.setIndex("L");
                            }
                            for (MyFriendsBean myFriendsBean : ms) {
                                myFriendsBean.setIndex("M");
                            }
                            for (MyFriendsBean myFriendsBean : ns) {
                                myFriendsBean.setIndex("N");
                            }
                            for (MyFriendsBean myFriendsBean : os) {
                                myFriendsBean.setIndex("O");
                            }
                            for (MyFriendsBean myFriendsBean : ps) {
                                myFriendsBean.setIndex("P");
                            }
                            for (MyFriendsBean myFriendsBean : qs) {
                                myFriendsBean.setIndex("Q");
                            }
                            for (MyFriendsBean myFriendsBean : rs) {
                                myFriendsBean.setIndex("R");
                            }
                            for (MyFriendsBean myFriendsBean : ss) {
                                myFriendsBean.setIndex("S");
                            }
                            for (MyFriendsBean myFriendsBean : ts) {
                                myFriendsBean.setIndex("T");
                            }
                            for (MyFriendsBean myFriendsBean : us) {
                                myFriendsBean.setIndex("U");
                            }
                            for (MyFriendsBean myFriendsBean : vs) {
                                myFriendsBean.setIndex("V");
                            }
                            for (MyFriendsBean myFriendsBean : ws) {
                                myFriendsBean.setIndex("W");
                            }
                            for (MyFriendsBean myFriendsBean : xs) {
                                myFriendsBean.setIndex("X");
                            }
                            for (MyFriendsBean myFriendsBean : ys) {
                                myFriendsBean.setIndex("Y");
                            }
                            for (MyFriendsBean myFriendsBean : zs) {
                                myFriendsBean.setIndex("Z");
                            }

                            List<MyFriendsBean> all = new ArrayList<>();
                            all.addAll(others);
                            all.addAll(as);
                            all.addAll(bs);
                            all.addAll(cs);
                            all.addAll(ds);
                            all.addAll(es);
                            all.addAll(fs);
                            all.addAll(gs);
                            all.addAll(hs);
                            all.addAll(is);
                            all.addAll(js);
                            all.addAll(ks);
                            all.addAll(ls);
                            all.addAll(ms);
                            all.addAll(ns);
                            all.addAll(os);
                            all.addAll(ps);
                            all.addAll(qs);
                            all.addAll(rs);
                            all.addAll(ss);
                            all.addAll(ts);
                            all.addAll(us);
                            all.addAll(vs);
                            all.addAll(ws);
                            all.addAll(xs);
                            all.addAll(ys);
                            all.addAll(zs);

                            datas.addAll(all);
                            adapter.setNewData(datas);
                            LogUtils.e("xzcdsvdvdvsvds",datas);

                            return;

                        }
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(MyTongunluFriendsActivity.this, request, this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.toolbar_text:

                if (StringUtils.equals("tixingshuikan", from)) {
                    //提醒谁看
                    EventBus.getDefault().post(new MessageBean(MessageBean.Code.CHOICETIXINGSHUIKAN, datas));

                } else {

                }
                finish();
                break;
        }
    }
}
