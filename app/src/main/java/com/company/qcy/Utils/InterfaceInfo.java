package com.company.qcy.Utils;

public class InterfaceInfo {


    //首页
    //BANNER
    public static final String INDEXBANNER = "index/getBanner";
    //首页数据
    public static final String INDEXDATA = "index/getAllData";
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
    public static final String SENDSMS = "user/send_sms_code_register";
    //注册
    public static final String REGISTER = "user/send_sms_code_register";


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
}
