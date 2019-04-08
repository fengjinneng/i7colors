package com.company.qcy.Utils;

public class InterfaceInfo {


    //首页
    //BANNER
    public static final String INDEXBANNER = "index/getBanner";
    //首页数据
    public static final String INDEXDATA = "index/getAllDataNew";
    //首页搜索
    public static final String INDEXSEARCH = "index/searchData";


    //用户
    //图形验证码
    public static final String CAPTCHA = "captcha";
    //登录
    public static final String LOGIN = "user/toLogin";
    //获取签名
    public static final String SIGN = "user/getRequestSign";
    //获取token
    public static final String TOKEN = "user/getRequestSign";
    //注册短信验证码
    public static final String SENDSMSREGISTER = "user/send_sms_code_register";
    //注册
    public static final String REGISTER = "user/register";
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
    public static final String QIUGOUXIANGQING = "enquiry/getEnquiryDetail";
    //报价列表
    public static final String BAOJIALIEBIAO = "enquiryOffer/getEnquiryOfferList";
    //发布报价
    public static final String FABUBAOJIA = "enquiryOffer/addEnquiryOffer";
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


    //开放商城
    //获取店铺列表
    public static final String DIANPULIEBIAO = "market/getMarketList";
    //获取店铺详情
    public static final String DIANPUXIANGQING = "market/getMarket";
    //根据店铺ID获取商品列表
    public static final String KFSCSHANGPINLIEBIAO = "marketProduct/getMarketProductByPage";


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


    //咨讯大厅
    //咨讯列表
    public static final String INFORMATIONLIST = "information/getInformationList";
    //咨讯详情
    public static final String INFORMATIONDETAIL = "information/getInformationDetail";


    //团购
    //团购列表
    public static final String GROUPBUYLIST = "groupBuyMain/queryGroupBuyMainList";
    //团购详情
    public static final String GROUPBUYDETAIL = "groupBuyMain/getGroupBuyMainById";
    //团购记录
    public static final String GROUPBUYRECORD = "groupBuyMain/queryGroupBuyerList";
    //我要团购
    public static final String WOYAOTUANGOU = "groupBuyMain/addGroupBuyer";


    //消息模块
    //求购报价消息
    public static final String GETENQUIRYINFORMLIST = "user/getMallEnquiryInformList";
    //求购报价消息详情
    public static final String GETENQUIRYINFORMDETAIL = "user/getMallEnquiryInformDetail";
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
    public static final String FABIAOPINGLUN = "dyeCommunity/addDyeComment";
    //删除评论
    public static final String DELETEPINGLUN = "dyeCommunity/cancelDyeComment";
    //点赞
    public static final String DIANZAN = "dyeCommunity/addDyeLike";
    //获取朋友圈记录
    public static final String QUERYMYDYECOMMUNITYLIST = "dyeCommunity/queryDyeCommunityListByUserId";
    //获取粉丝列表
    public static final String QUERYMYFOLLOWLIST = "dyeFollow/queryFollowListByUserId";
    //根据userid获取用户信息
    public static final String GETUSERINFOBYUSERID = "user/getUserInfoDetail";

    //获取我的好友列表
    public static final String GETMYFRIENDSLIST = "dyeFollow/queryMyFriendListSort";


    //根据token获取用户信息
    public static final String GETUSERINFOBYTOKEN = "user/getMyInfoDetail";
    //根据token获取朋友圈记录
    public static final String QUERYMYDYECOMMUNITYLISTBYTOKEN = "dyeCommunity/queryMyDyeCommunityList";
    //根据tokendianzan
    public static final String QUERYMYDYEFOLLOWLISTBYTOKEN = "dyeFollow/queryMyFollowList";


    //根据帖子id获取评论列表（一期）
    public static final String QUERYDYECOMMENTLISTBYID = "dyeCommunity/queryDyeCommentListByDyeId";
    //根据帖子id获取帖子详情
    public static final String QUERYDYECOMMENTDETAIL = "dyeCommunity/queryDyeCommunityDetail";
    //根据用户id添加关注
    public static final String ADDFOLLOWBYUSERID = "dyeFollow/addDyeFollowByUserId";
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


    //优惠展销

    //优惠展销列表
    public static final String YOUHUIZHANXIAOLIST = "sales/querySalesMainList";
    //优惠展销详情
    public static final String YOUHUIZHANXIAODETAIL = "sales/getSalesMainById";
    //优惠展销记录
    public static final String YOUHUIZHANXIAORECORD = "sales/querySalesOrderList";
    //我要购买
    public static final String YOUHUIZHANXIAOBUY = "sales/addSalesOrder";


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
    public static final String CANYUJINGPAI = "auction/addAuctionBuyer";

}
