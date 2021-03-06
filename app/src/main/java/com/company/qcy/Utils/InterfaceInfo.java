package com.company.qcy.Utils;

public class InterfaceInfo {


    //首页
    //BANNER
    public static final String INDEXBANNER = "index/getBanner";
    //首页数据
    public static final String INDEXDATA = "index/getAllDataNew2";
    //首页搜索
    public static final String INDEXSEARCH = "index/searchData";

    //用户
    //图形验证码
    public static final String CAPTCHA = "captcha";

    //校验图形验证码
    public static final String CHECKCAPTCHA = "user/checkCaptcha";

    //登录
    public static final String LOGIN = "user/toLogin";
    //获取签名
    public static final String SIGN = "user/getRequestSign";
    //获取token
    public static final String TOKEN = "user/getRequestSign";
    //注册短信验证码
    public static final String SENDSMSREGISTER = "user/send_sms_code_register";
    //注册
    public static final String REGISTER = "user/registerNew";

    //完善注册信息
    public static final String REGISTERINFO = "user/updateUserInfo";

    //重置密码
    public static final String RESETPASSWORD = "user/updatePassword";
    //校验短信验证码
    public static final String CHECKSMSCODE = "user/checkSmsCode";
    //忘记密码短信验证码
    public static final String SENDSMSCHECKPASSWORD = "user/send_sms_code_password";
    //上传用户头像  uploadHeadPhoto
    public static final String UPLOADHEADPHOTO = "user/updateUserHeadPhoto";
    //修改密码
    public static final String CHANGEPASSWORD = "user/updateUserPassword";

    //求购列表
    public static final String QIUGOULIEBIAO = "enquiry/getEnquiryList";
    //发布求购
    public static final String FABUQIUGOU = "enquiry/addEnquiy";
    //求购分类
    public static final String QIUGOUFENLEI = "enquiry/getClassification";
    //求购详情
    public static final String QIUGOUXIANGQING = "enquiry/getEnquiryDetailNew";
    //报价列表
    public static final String BAOJIALIEBIAO = "enquiryOffer/getEnquiryOfferList";
    //发布报价
    public static final String FABUBAOJIA = "enquiryOffer/addEnquiryOfferNew";
    //我的求购列表
    public static final String MYQIUGOU = "enquiry/getMyEnuqiryList";
    //我的报价列表
    public static final String MYBAOJIA = "enquiryOffer/getMyEnquiryOfferList";
    //我的买家数目
    public static final String GETALLCOUNT = "enquiry/getAllCount";
    //我的询盘中的列表
    public static final String XUNPANZHONGLIST = "enquiry/getIsEnquiryList";
    //待确认报价列表
    public static final String DAIQUERENBAOJIALIST = "enquiry/getWaitSureList";
    //即将过期列表
    public static final String JIJIANGGUOQILIST = "enquiry/getMyExpireList";
    //已被接受的列表
    public static final String MAIJIAYIJIESHOULIST = "enquiryOffer/getMyAcceptEnquiryOfferList";
    //接受报价
    public static final String CAINABAOJIA = "enquiryOffer/acceptEnquiryOffer";
    //关闭求购
    public static final String GUANBIQIUGOU = "enquiry/cancelEnquiry";
    //求购搜索
    public static final String SEARCHENQUIRY = "enquiry/getEnquiryListByKeyword";
    //报价消息已读
    public static final String READMYACCEPTOFFER = "enquiryOffer/readMyAcceptOffer";
    //查看直通车信息
    public static final String ZHITONGCHEINFO = "enquiry/queryEnquiryInformation";
    //求购大厅跳转付费广告
    public static final String TOFEEADRECORD = "enquiry/toFeeAdRecord";

    //开放商城
    //获取店铺列表
    public static final String DIANPULIEBIAO = "market/getMarketList";
    //获取店铺详情
    public static final String DIANPUXIANGQING = "market/getMarket";
    //根据店铺ID获取商品列表
    public static final String KFSCSHANGPINLIEBIAO = "marketProduct/getMarketProductByPage";
    //获取店铺分类
    public static final String DIANPUTYPE = "market/selectCompanyType";

    //产品大厅
    public static final String GETCHANPINLIEBIAO = "product/getProductList";
    //获取产品详情
    public static final String GETCHANPINDETAIL = "product/getProductDetail";
    //是否收藏了给商品
    public static final String ISFAVORITEPRODUCT = "product/isFavoriteProduct";
    //取消收藏
    public static final String CANCEFAVORITEPRODUCT = "product/cancelFavoriteProduct";
    //收藏商品
    public static final String ADDFAVORITEPRODUCT = "product/favoriteProduct";
    //获取所有产品的分类
    public static final String GETALLPRODUCTTYPE = "product/getShopAllPropByType";


    //咨讯大厅
    //咨讯列表
    public static final String INFORMATIONLIST = "information/getInformationList";
    //咨讯详情
    public static final String INFORMATIONDETAIL = "information/getInformationDetail";


    //团购
    //团购列表
    public static final String GROUPBUYLIST = "groupBuyMain/queryGroupBuyMainList";
    //团购详情
    public static final String GROUPBUYDETAIL = "groupBuyMain/getGroupBuyMainByIdNew";
    //团购记录
    public static final String GROUPBUYRECORD = "groupBuyMain/queryGroupBuyerList";
    //我要团购
    public static final String WOYAOTUANGOU = "groupBuyMain/addGroupBuyerNew2";

    //我的团购列表
    public static final String WODETUANGOULIST = "groupBuyMain/queryMyGroupBuyMainList";
    //获取为我的某个认购进行砍价的用户列表
    public static final String KANJIAYONGHULIST = "groupBuyMainCutPrice/queryMyGroupBuyCutPriceList";

    //进入砍价页面獲取信息
    public static final String KANJIAINFO = "groupBuyMain/getGroupBuyCutPriceById";
    //砍價
    public static final String KANJIA = "groupBuyMainCutPrice/groupBuyCutPrice";

    //获取获取默认的地址信息
    public static final String HUODONGMORENDIZHI = "defaultAddress/queryDefaultAddress";

    //消息模块
    //买家消息   type=seller卖家消息  type=buyer,获取买家消息
    public static final String GETBUYERANDSELLERMESSAGEINFORMLIST = "vMallInform/getVMallInformList";

    //获取消息详情 workType=enquiry求购消息；workType=zhujiDiy助剂定制消息
    public static final String GETBUYERANDSELLERMESSAGEDETAIL = "vMallInform/getVMallInformDetail";
    //系统消息
    public static final String SYSTEMINFORMLIST = "user/getSystemInformList";
    //未读消息
    public static final String GETNOTREADMESSAGECOUNT = "user/getNotReadCount";

    //朋友圈模块
    //查询朋友圈列表  发现
    public static final String QUERYDYECOMMUNITYLISTFAXIAN = "dyeCommunity/queryMainDyeCommunityList";
    //查询朋友圈列表  热门
    public static final String QUERYDYECOMMUNITYLISTREMEN = "dyeCommunity/queryHotDyeCommunityList";
    //查询朋友圈列表  我的关注
    public static final String QUERYDYECOMMUNITYLISTGUANZHU= "dyeCommunity/queryMyFollowDyeCommunityList";


    //发朋友圈
    public static final String FABUPENGYOUQUAN = "dyeCommunity/addDyeCommunityNew";
    //发表评论
    public static final String FABIAOPINGLUN = "dyeCommunity/addDyeCommentNew";
    //删除评论
    public static final String DELETEPINGLUN = "dyeCommunity/cancelDyeComment";
    //点赞
    public static final String DIANZAN = "dyeCommunity/addDyeLikeNew";


    //删除推送的消息
    //删除评论消息
    public static final String DELETEPINGLUNMESSAGE = "dyeCommunityMessage/delCommentMessageById";
    //删除点赞消息
    public static final String DELETEDIANZANMESSAGE = "dyeCommunityMessage/delLikeMessageById";
    //删除艾特我的消息
    public static final String DELETEAITEMESSAGE = "dyeCommunityMessage/delNoticeMessageById";
    //删除关注消息
    public static final String DELETEGUANZHUMESSAGE = "dyeCommunityMessage/delFollowMessageById";

    //消息全部标为已读
    public static final String BIAOWEIYIDUMESSAGE = "dyeCommunityMessage/batchReadMessage";


    //根据userid获取用户信息
    public static final String GETUSERINFOBYUSERID = "user/getUserInfoDetail";

    //获取我的通讯录好友列表
    public static final String GETMYTONGXUNLUFRIENDSLIST = "dyeFollow/queryMyFriendListSort";
    //获取我的好友列表（用户详情页用）
    public static final String GETMYFRIENDSLIST = "dyeFollow/queryMyFriendList";

    //根据token获取用户信息
    public static final String GETUSERINFOBYTOKEN = "user/getMyInfoDetail";

    //获取朋友圈记录
    public static final String QUERYDYECOMMUNITYLISTBYUSERID = "dyeCommunity/queryDyeCommunityListByUserIdNew";
    //根据token获取朋友圈记录
    public static final String QUERYMYDYECOMMUNITYLISTBYTOKEN = "dyeCommunity/queryMyDyeCommunityList";


    //获取别人的粉丝列表
    public static final String QUERYFOLLOWLISTBYUSERID = "dyeFollow/queryFollowListByUserId";


    //获取我的粉丝列表
    public static final String QUERYMYDYEFOLLOWLISTBYTOKEN = "dyeFollow/queryMyFollowListNew";


    //根据帖子id获取评论列表（一期）
    public static final String QUERYDYECOMMENTLISTBYID = "dyeCommunity/queryDyeCommentListByDyeId";
    //根据帖子id获取帖子详情
    public static final String QUERYDYECOMMENTDETAIL = "dyeCommunity/queryDyeCommunityDetail";
    //根据用户id添加关注
    public static final String ADDFOLLOWBYUSERID = "dyeFollow/addDyeFollowByUserIdNew";
    //根据用户id取消关注
    public static final String CANCLEFOLLOWBYUSERID = "dyeFollow/cancelDyeFollowByUserId";
    //删除帖子
    public static final String CANCLEDYECOMMUNTY = "dyeCommunity/cancelDyeCommunity";
    //修改朋友圈头像和昵称
    public static final String UPDATEMYDYEINFO = "user/updateMyDyeInfo";
    //点赞列表
    public static final String QUERYPRAISELIST = "dyeCommunity/queryDyeLikeListByDyeId";
    //获取我的未读评论个数
    public static final String QUERYNOTREADCOMMENTCOUNT = "dyeCommunity/queryMyNotReadCommentCount";
    //获取我的未读评论列表
    public static final String QUERYNOTREADCOMMENTLIST = "dyeCommunity/queryMyNotReadCommentList";
    //获取印染圈话题
    public static final String QUERYHUATI = "dyeCommunity/queryDyeCommunityTopicList";

    //获取我的评论消息列表（二期）
    public static final String PINGLUNXIAOXILIST = "dyeCommunityMessage/queryMyNotReadCommentMessageList";
    //获取我的点赞消息列表
    public static final String DIANZANXIAOXILIST = "dyeCommunityMessage/queryMyNotReadLikeMessageList";
    //获取@我的消息列表
    public static final String AITEWODEXIAOXILIST = "dyeCommunityMessage/queryMyNotReadNoticeMessageList";
    //获取關注我的消息列表
    public static final String WODEGUANZHUXIAOXILIST = "dyeCommunityMessage/queryMyNotReadFollowMessageList";

    //朋友圈获取我的消息数目统计
    public static final String GETPENGYOUQUANNOTREADMESSAGE = "dyeCommunityMessage/getNoReadCount";


    //微信相关
    //绑定手机号
    public static final String WXBINDPHONE = "wx/bindPhone";
    //发送验证码
    public static final String WXSENDSMS = "wx/send_sms_code_password";
    //微信登录
    public static final String WXLOGIN = "wx/onWxLogin";


    //大V
    //申请打V认真
    public static final String DAVRENZHEN = "user/CertV";
    //申请企业认真
    public static final String QIYERENZHEN = "user/registerCompany";

    //重新申请企业认真
    public static final String RESETQIYERENZHEN = "user/resetCompany";

    //获取企业认证状态
    public static final String QIYERENZHENSTATUS = "user/getCompanyInfoStatus";

    //获取企业认证详情
    public static final String QIYERENZHENSTATUSDETAIL = "user/getCompanyInfoDetail";



    //优惠展销

    //优惠展销列表
    public static final String YOUHUIZHANXIAOLIST = "sales/querySalesMainList";
    //优惠展销详情
    public static final String YOUHUIZHANXIAODETAIL = "sales/getSalesMainById";
    //优惠展销记录
    public static final String YOUHUIZHANXIAORECORD = "sales/querySalesOrderList";
    //我要购买
    public static final String YOUHUIZHANXIAOBUY = "sales/addSalesOrderNew";


    //采购联盟
    //采购联盟列表
    public static final String CAIGOULIANMENGLIST = "meetingName/queryMeetingNameList";
    //获取某个采购的商品列表0 订货  1供货
    public static final String CAIGOULIANMENGPRODUCTLIST = "meetingShop/queryAddMeetingShopList";
    //我的货单  0 订货  1供货
    public static final String CAIGOULIANMENGHUODAN = "meetingShop/queryMeetingUserShopList";
    //我要订货
    public static final String CAIGOULIANMENGWOYAODINGHUO = "meetingShop/addMeetingShop";
    //发送短信
    public static final String CAIGOULIANMENMESSAGE = "meetingShop/registerCode";
    //验证短信
    public static final String YANZHENGCAIGOULIANMENGMESSAGE = "meetingShop/verifycode";

    //投票
    //投票活动列表
    public static final String TOUPIAOLIST = "voteMain/queryVoteMainList";
    //获取投票详情
    public static final String GETTOUPIAOLDETAILBYID = "voteMain/getVoteMainById";
    //参赛人员列表
    public static final String CANSHAIRENYUANLIST = "voteApplication/queryVoteApplicationList";
    //投票
    public static final String TOUPIAO = "voteJoin/addVoteJoin";
    //申请参与
    public static final String SHENGQINGCANYU = "voteApplication/addVoteApplication";
    //获取参赛人员详情
    public static final String CANSAIRENYUANDETAIL = "voteApplication/getVoteApplicationById";


    //竞拍
    //竞拍列表
    public static final String JINGPAILIST = "auction/queryAuctionList";
    //根据id获取竞拍详情
    public static final String JINGPAIDETAIL = "auction/getAuctionById";
    //根据竞拍id获取参与记录
    public static final String GETRECORDBYID = "auction/queryAuctionBuyerList";
    //参与竞拍
    public static final String CANYUJINGPAI = "auction/addAuctionBuyerNew";

    //印染地图
    //条件获取印染地图
    public static final String QUERYDYEMAPLIST = "dyeMap/queryDyeMapList";


    //助剂定制

    //获取助剂定制企业列表
    public static final String ZHUJIQIYELIST = "zhujiDiy/queryZhujiDiySpecialList";

    //根据专场id获取助剂定制列表
    public static final String SPECIALZHUJILIST = "zhujiDiy/queryZhujiDiyListNew";

    //获取助剂定制列表
    public static final String ZHUJILIST = "zhujiDiy/queryZhujiDiyList";

    //获取我的助剂定制列表
    public static final String WODEZHUJILIST = "zhujiDiy/getMyZhujiDiyList";

    //获取助剂定制详情
    public static final String ZHUJIDETAIL = "zhujiDiy/queryZhujiDiyDetail";

    //获取我的助剂定制详情
    public static final String WODEZHUJIDETAIL = "zhujiDiy/getMyZhujiDiyDetail";

    //获取我的助剂定制解决方案列表
    public static final String WODEJIEJUEFANGANLIST = "zhujiDiy/getMyZhujiDiySolutionList";

    //获取我的助剂定制解决方案详情
    public static final String WODEJIEJUEFANGANDETAIL = "zhujiDiy/getMyZhujiDiySolutionDetail";

    //发布助剂定制
    public static final String FABUZHUJIDINGZHI = "zhujiDiy/addZhujiDiyNew";

    //获取助剂分类
    public static final String ZHUJITYPE = "zhujiDiy/getZhujiDiyClassList";

    //获取助剂材质
    public static final String ZHUJICAIZHI = "zhujiDiy/getZhujiDiyMaterialList";

    //发补助及定制解决方案
    public static final String FABUJIEJUEFANGAN = "zhujiDiy/addZhujiDiySolution";

    //关闭我的助剂定制
    public static final String CLOSEZHUJIDIY = "zhujiDiy/updateZhujiDiyStatus";

    //采纳方案
    public static final String CAINAFANGAN = "zhujiDiy/acceptZhujiDiySolution";



    //代销
    //代销列表
    public static final String DAIXIAOLIST = "proxyMarket/queryProxyMarketList";

    //代销列表
    public static final String DAIXIAODETAIL = "proxyMarket/getProxyMarketById";

    //代销索样
    public static final String DAIXIAOSUOYANG = "proxyMarket/addProxyMarketBuy";


    //直播

    //获取课程列表
    public static final String CLASSLIST = "schoolLiveClass/querySchoolLiveClassListForApp";

    //获取课程详情
    public static final String CLASSDETAIL = "schoolLiveClass/querySchoolLiveClassDetailForApp";

    //购买课程
    public static final String CLASSPAY = "wxpay/unifiedOrder";

    //预约课程
    public static final String yuyueClass = "schoolLiveClass/createSchoolLiveClassReserveForApp";

    //取消预约课程
    public static final String cancleClass = "schoolLiveClass/updateSchoolLiveClassReserveForApp";

    //检测支付状态
    public static final String checkPayStatus = "wxpay/checkPayStatus";

}

