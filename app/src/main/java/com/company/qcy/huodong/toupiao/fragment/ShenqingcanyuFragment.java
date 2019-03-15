package com.company.qcy.huodong.toupiao.fragment;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.AddressPickTask;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.MatisseImageUtil;
import com.company.qcy.Utils.PermisionUtil;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.Utils.SignAndTokenUtil;
import com.company.qcy.Utils.UserUtil;
import com.company.qcy.base.BaseFragment;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.ui.activity.user.LoginActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.zhihu.matisse.Matisse;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShenqingcanyuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShenqingcanyuFragment extends BaseFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;

    /**
     * 请输入全称
     */
    private EditText mFragmentShenqingcanyuName;

    /**
     * 请输入联系人手机号
     */
    private EditText mFragmentShenqingcanyuPhone;

    /**
     * 请选择
     */
    private TextView mFragmentShenqingcanyuAddress;

    /**
     * 点击\n上传
     */
    private TextView mFragmentShenqingcanyuchoiceimgTest;

    /**
     * 请输入描述,最多50字。
     */
    private EditText mFragmentShenqingcanyuDescribe;
    /**
     * 提交
     */
    private Button mFragmentShenqingcanyuSubmit;
    private ImageView mFragmentShenqingcanyuChoiceimgImg;


    public ShenqingcanyuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ShenqingcanyuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShenqingcanyuFragment newInstance(String param1, String param2) {
        ShenqingcanyuFragment fragment = new ShenqingcanyuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shenqingcanyu, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View inflater) {

        mFragmentShenqingcanyuName = inflater.findViewById(R.id.fragment_shenqingcanyu_name);
        mFragmentShenqingcanyuPhone = inflater.findViewById(R.id.fragment_shenqingcanyu_phone);
        mFragmentShenqingcanyuAddress = inflater.findViewById(R.id.fragment_shenqingcanyu_address);
        mFragmentShenqingcanyuAddress.setOnClickListener(this);
        mFragmentShenqingcanyuchoiceimgTest = inflater.findViewById(R.id.fragment_shenqingcanyu_choiceimg_text);
        mFragmentShenqingcanyuchoiceimgTest.setOnClickListener(this);
        mFragmentShenqingcanyuDescribe = inflater.findViewById(R.id.fragment_shenqingcanyu_describe);
        mFragmentShenqingcanyuSubmit = inflater.findViewById(R.id.fragment_shenqingcanyu_submit);
        mFragmentShenqingcanyuSubmit.setOnClickListener(this);
        mFragmentShenqingcanyuChoiceimgImg = inflater.findViewById(R.id.fragment_shenqingcanyu_choiceimg_img);
        mFragmentShenqingcanyuChoiceimgImg.setOnClickListener(this);

        if (StringUtils.equals("2", mParam2)||StringUtils.equals("0", mParam2)) {
            mFragmentShenqingcanyuSubmit.setBackgroundColor(getActivity().getResources().getColor(R.color.fengexian));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.fragment_shenqingcanyu_address:

                choiceAddress(mFragmentShenqingcanyuAddress);

                break;
            case R.id.fragment_shenqingcanyu_choiceimg_text:

                showHeadPortraitDialog();

                break;
            case R.id.fragment_shenqingcanyu_submit:
                if (StringUtils.equals("1", mParam2)) {
                    shenqing();
                }
                if (StringUtils.equals("2", mParam2)) {
                    ToastUtils.showShort("当前活动已结束！");
                }
                if (StringUtils.equals("0", mParam2)) {
                    ToastUtils.showShort("当前活动未开始！");
                }

                break;
            case R.id.fragment_shenqingcanyu_choiceimg_img:
                showHeadPortraitDialog();
                break;
        }
    }


    //选择地址
    private void choiceAddress(final TextView address) {
        AddressPickTask task = new AddressPickTask(getActivity());
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

    private String locationProvince;
    private String locationCity;

    private void shenqing() {

        if(!UserUtil.isLogin()){
            ActivityUtils.startActivity(LoginActivity.class);
            return;
        }

        if (!checkNull()) {
            return;
        }

        PostRequest<String> request = OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.SHENGQINGCANYU)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("token", SPUtils.getInstance().getString("token"))
                .params("mainId", mParam1)
                .params("phone", mFragmentShenqingcanyuPhone.getText().toString())
                .params("name", mFragmentShenqingcanyuName.getText().toString())
                .params("province", locationProvince)
                .params("city", locationCity)
                .params("description", mFragmentShenqingcanyuDescribe.getText().toString())
                .params("from", "app_android")
                .params("file", upLoadFile).params("from",getActivity().getResources().getString(R.string.app_android));


        DialogStringCallback stringCallback = new DialogStringCallback(getActivity()) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtils.v("TOUPIAO", response.body());
                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        String msg = jsonObject.getString("msg");
                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                            mFragmentShenqingcanyuName.setText("");
                            mFragmentShenqingcanyuPhone.setText("");
                            locationProvince = "";
                            locationCity = "";
                            mFragmentShenqingcanyuAddress.setText("请选择");
                            mFragmentShenqingcanyuChoiceimgImg.setVisibility(View.INVISIBLE);
                            mFragmentShenqingcanyuchoiceimgTest.setVisibility(View.VISIBLE);
                            upLoadFile = null;
                            mFragmentShenqingcanyuDescribe.setText("");
                            ToastUtils.showLong("参与成功，您的编号是" + jsonObject.getString("data"));
                            EventBus.getDefault().post(new MessageBean(MessageBean.Code.CANYUTOUPIAOCHENGGONG));
                            return;
                        }

                        if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.qianmingshixiao))) {
                            SignAndTokenUtil.getSign(getActivity(), request, this);
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


    private Dialog chooseHeadDialog;
    public static final int CHOOSE_PICTURE = 3;
    public static final int TAKE_PICTURE = 2;


    private void showHeadPortraitDialog() {

        chooseHeadDialog = new Dialog(getActivity(), R.style.BottomDialog);
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_choose_head, null);
        chooseHeadDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        // layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        contentView.setLayoutParams(layoutParams);
        chooseHeadDialog.getWindow().setGravity(Gravity.BOTTOM);
        chooseHeadDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        chooseHeadDialog.setCancelable(true);
        chooseHeadDialog.setCanceledOnTouchOutside(true);
        chooseHeadDialog.show();
        contentView.findViewById(R.id.tv_take_video).setVisibility(View.GONE);
        contentView.findViewById(R.id.tv_choose_video).setVisibility(View.GONE);

        // 从相册选择图片
        contentView.findViewById(R.id.tv_choose_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseHeadDialog.dismiss();  // 选择之后，关闭dialog

                if (Build.VERSION.SDK_INT >= 23) {
                    int checkCallWritePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (checkCallWritePermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 5);
                        return;
                    } else {
                        // 打开本地相册
                        MatisseImageUtil.chooseOnlyOnePhoto(getActivity(), CHOOSE_PICTURE);
                    }
                } else {
                    // 打开本地相册
                    MatisseImageUtil.chooseOnlyOnePhoto(getActivity(), CHOOSE_PICTURE);
                }
            }
        });
        // 拍照
        contentView.findViewById(R.id.tv_take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseHeadDialog.dismiss();
                //判断权限
                if (Build.VERSION.SDK_INT >= 23) {
                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 6);
                        return;
                    } else {
                        int checkCallWritePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        if (checkCallWritePermission != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 7);
                            return;
                        } else {
                            takePicture();
                        }
                    }
                } else {
                    takePicture();
                }
            }
        });

        // 取消
        contentView.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseHeadDialog.dismiss();
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    Uri imageUri = Uri.fromFile(outFile);
                    String filePath = MatisseImageUtil.getRealFilePath(getContext(), imageUri);

                    File file = new File(filePath);
                    File file1 = null;
                    try {
                        file1 = new Compressor(getContext()).compressToFile(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mFragmentShenqingcanyuchoiceimgTest.setVisibility(View.INVISIBLE);
                    mFragmentShenqingcanyuChoiceimgImg.setVisibility(View.VISIBLE);
                    mFragmentShenqingcanyuChoiceimgImg.setImageURI(imageUri);
                    upLoadFile = file1;
                }
                break;

            case CHOOSE_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    List<Uri> uris = Matisse.obtainResult(data);
                    String filePath = MatisseImageUtil.getRealFilePath(getContext(), uris.get(0));

                    File file = new File(filePath);
                    File file1 = null;
                    try {
                        file1 = new Compressor(getContext()).compressToFile(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mFragmentShenqingcanyuchoiceimgTest.setVisibility(View.INVISIBLE);
                    mFragmentShenqingcanyuChoiceimgImg.setVisibility(View.VISIBLE);
                    mFragmentShenqingcanyuChoiceimgImg.setImageURI(uris.get(0));
                    upLoadFile = file1;
                }

                break;
        }
    }

    File upLoadFile;


    private File outFile;  // 为了根据File获取uri

    // 拍照
    public void takePicture() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            try {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File outDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                if (!outDir.exists()) {
                    outDir.mkdirs();
                }
                outFile = new File(outDir, System.currentTimeMillis() + ".jpg");
                String picFileFullName = outFile.getAbsolutePath();
                LogUtils.e("mxg", "picFileFullName = " + picFileFullName);
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, picFileFullName);
                Uri uri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, TAKE_PICTURE);
            } catch (Exception e) {
                e.printStackTrace();
                ToastUtils.showShort("请确认是否开启打开相机的权限！");
                // requestPermission();
            }
        } else {
            ToastUtils.showShort("请确认是否接入SD卡");
        }
    }


    private boolean checkNull() {
        if (StringUtils.isEmpty(mParam1)) {
            return false;
        }

        if (StringUtils.isEmpty(mFragmentShenqingcanyuName.getText().toString())) {
            ToastUtils.showShort("请填写参与者名称！");
            return false;
        }
        if (!RegexUtils.isMobileSimple(mFragmentShenqingcanyuPhone.getText().toString())) {
            ToastUtils.showShort("手机号格式不正确！");
            return false;
        }
        if (StringUtils.isEmpty(mFragmentShenqingcanyuPhone.getText().toString())) {
            ToastUtils.showShort("请输入联系人手机号！");
            return false;
        }
        if (StringUtils.isEmpty(locationProvince) || StringUtils.isEmpty(locationCity)) {
            ToastUtils.showShort("您还没选择地区!");
            return false;
        }

        if (ObjectUtils.isEmpty(upLoadFile)) {
            ToastUtils.showShort("请选择图片！");
            return false;
        }
        if (StringUtils.isEmpty(mFragmentShenqingcanyuDescribe.getText().toString())) {
            ToastUtils.showShort("请填写描述！");
            return false;
        }
        return true;
    }
}
