package com.company.qcy.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.company.qcy.R;
import com.company.qcy.Utils.DialogStringCallback;
import com.company.qcy.Utils.InterfaceInfo;
import com.company.qcy.Utils.ServerInfo;
import com.company.qcy.bean.eventbus.MessageBean;
import com.company.qcy.bean.user.UserBean;
import com.company.qcy.ui.activity.user.BindPhoneActivity;
import com.company.qcy.ui.activity.user.LoginActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import cn.sharesdk.wechat.utils.WechatHandlerActivity;


/**
 * 微信客户端回调activity示例
 */
public class WXEntryActivity extends WechatHandlerActivity implements IWXAPIEventHandler {

    private static final String APP_ID = "wx63410989373f8975";
//    private static final String SECRET = "8a63da0ba72799ddd58edf7b55357094";
    private IWXAPI iwxapi;
//    private String unionid;
//    private String openid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //接收到分享以及登录的intent传递handleIntent方法，处理结果
        iwxapi = WXAPIFactory.createWXAPI(this, APP_ID, false);
        iwxapi.handleIntent(getIntent(), this);

    }


    @Override
    public void onReq(BaseReq baseReq) {
    }


    //发送到微信请求的响应结果
//    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK: //发送成功
                switch (resp.getType()) {
                    case ConstantsAPI.COMMAND_SENDAUTH:
                        //登录回调,处理登录成功的逻辑
                        code = ((SendAuth.Resp) resp).code; //即为所需的code
                        wxLogin(code);
                        break;
                    case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
                        //分享回调,处理分享成功后的逻辑
//                        ToastUtils.showShort("分享成功");
                        finish();
                        break;
                    default:
                        break;
                }

                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL: //发送取消
                switch (resp.getType()) {
                    case ConstantsAPI.COMMAND_SENDAUTH:
                        ToastUtils.showShort( "登录取消了");
                        break;
                    case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
                        ToastUtils.showShort("分享取消了");
                        break;
                }
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED: //发送被拒绝
                ToastUtils.showShort("失败");
                break;
            default://发送返回

                break;
        }

    }


    String code;
    //请求回调结果处理
//    @Override
//    public void onResp(BaseResp baseResp) {
//        //登录回调
//        switch (baseResp.errCode) {
//            case BaseResp.ErrCode.ERR_OK:
//                String code = ((SendAuth.Resp) baseResp).code;
//                wxLogin(code);
////				getAccessToken(code);
//                break;
//            case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝授权
//                finish();
//                break;
//            case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
//                finish();
//                break;
//            default:
//                finish();
//                break;
//        }
//    }


    private void wxLogin(String code) {
        OkGo.<String>post(ServerInfo.SERVER + InterfaceInfo.WXLOGIN)
                .tag(this)
                .params("sign", SPUtils.getInstance().getString("sign"))
                .params("code", code)
                .params("from",getResources().getString(R.string.app_android))
                .params("registrationId",SPUtils.getInstance().getString("registrationId"))
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            if (response.code() == 200) {
                                LogUtils.e("WXLOGIN", response.body());
                                JSONObject jsonObject = JSONObject.parseObject(response.body());
                                if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    UserBean user = data.toJavaObject(UserBean.class);
                                    if(user.getNeedPhone()){
                                        Intent intent = new Intent(WXEntryActivity.this,BindPhoneActivity.class);
                                        SPUtils.getInstance().put("token", user.getToken());
                                        intent.putExtra("user",user);
                                        ActivityUtils.startActivity(intent);
                                        finish();
                                    }else {
                                        EventBus.getDefault().post(new MessageBean(MessageBean.Code.WXLOGIN,user));
                                        finish();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });

    }


//	private void BindPhone(){
//
//		OkGo.<String>get(ServerInfo.SERVER + InterfaceInfo.BINDPHONE)
//				.tag(this)
//				.params("sign", SPUtils.getInstance().getString("sign"))
//				.params("token", SPUtils.getInstance().getString("token"))
////				.params("phone",)
//				.execute(new StringCallback() {
//					@Override
//					public void onSuccess(Response<String> response) {
//
//						try {
//							LogUtils.v("INDEXBANNER2", response.body());
//							if (response.code() == 200) {
//								JSONObject jsonObject = JSONObject.parseObject(response.body());
//
//								if (StringUtils.equals(jsonObject.getString("code"), getResources().getString(R.string.success))) {
//									JSONArray data = jsonObject.getJSONArray("data");
//									if (ObjectUtils.isEmpty(data)) {
//										return;
//									}
//									List<BannerBean> bannerBeans = JSONObject.parseArray(data.toJSONString(), BannerBean.class);
//									if (isRefresh) {
//										advDatas.clear();
//									}
//									for (int i = 0; i < bannerBeans.size(); i++) {
//										advDatas.add(ServerInfo.IMAGE + bannerBeans.get(i).getAd_image());
//									}
//									advAdapter.notifyDataSetChanged();
//								}
//							}
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//					}
//
//					@Override
//					public void onError(Response<String> response) {
//						super.onError(response);
//					}
//				});
//
//
//	}

//	private void getAccessToken(String code) {
//		//获取授权
//		StringBuffer loginUrl = new StringBuffer();
//		loginUrl.append("https://api.weixin.qq.com/sns/oauth2/access_token")
//				.append("?appid=")
//				.append("&secret=")
//				.append(SECRET)
//				.append("&code=")
//				.append(code)
//				.append("&grant_type=authorization_code");
//		OkHttpUtils.ResultCallback<String> resultCallback = new OkHttpUtils.ResultCallback<String>() {
//			@Override
//			public void onSuccess(String response) {
//				String access = null;
//				String openId = null;
//				try {
//					JSONObject jsonObject = new JSONObject(response);
//					access = jsonObject.getString("access_token");
//					openId = jsonObject.getString("openid");
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//				//获取个人信息
//				String getUserInfo = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access + "&openid=" + openId;
//				OkHttpUtils.ResultCallback<String> reCallback = new OkHttpUtils.ResultCallback<String>() {
//					@Override
//					public void onSuccess(String responses) {
//
//						String nickName = null;
//						String sex = null;
//						String city = null;
//						String province = null;
//						String country = null;
//						String headimgurl = null;
//						try {
//							JSONObject jsonObject = new JSONObject(responses);
//
//							openid = jsonObject.getString("openid");
//							nickName = jsonObject.getString("nickname");
//							sex = jsonObject.getString("sex");
//							city = jsonObject.getString("city");
//							province = jsonObject.getString("province");
//							country = jsonObject.getString("country");
//							headimgurl = jsonObject.getString("headimgurl");
//							unionid = jsonObject.getString("unionid");
//							loadNetData(1, openid, nickName, sex, province,
//									city, country, headimgurl, unionid);
//
//						} catch (JSONException e) {
//							e.printStackTrace();
//						}
//
//					}
//
//					@Override
//					public void onFailure(Exception e) {
//						Toast.makeText(WXEntryActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
//						finish();
//					}
//				};
//				OkHttpUtils.get(getUserInfo, reCallback);
//			}
//
//			@Override
//			public void onFailure(Exception e) {
//				Toast.makeText(WXEntryActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
//				finish();
//			}
//		};
//		OkHttpUtils.get(loginUrl.toString(), resultCallback);
//	}

    @Override
    protected void onPause() {
        overridePendingTransition(0, 0);
        super.onPause();
    }
}
