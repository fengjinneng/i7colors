package com.company.qcy.bean.qiugou;

public class NumberBean {


    /**
     * waitSureCount : 0
     * enquiryTimes : 16
     * offerTimes : 22
     * myExpireCount : 2
     * isEnquiryCount : 0
     */

    private Integer waitSureCount;
    private Integer enquiryTimes;
    private Integer offerTimes;
    private Integer myExpireCount;
    private Integer isEnquiryCount;
    private Integer myAcceptOfferCount;

    private Integer zhujiSolutionAcceptCount;//买家已接受
    private Integer zhujiDiyingCount;//试样中


    public Integer getMyAcceptOfferCount() {
        return myAcceptOfferCount;
    }

    public void setMyAcceptOfferCount(Integer myAcceptOfferCount) {
        this.myAcceptOfferCount = myAcceptOfferCount;
    }

    public Integer getWaitSureCount() {
        return waitSureCount;
    }

    public void setWaitSureCount(Integer waitSureCount) {
        this.waitSureCount = waitSureCount;
    }

    public Integer getEnquiryTimes() {
        return enquiryTimes;
    }

    public void setEnquiryTimes(Integer enquiryTimes) {
        this.enquiryTimes = enquiryTimes;
    }

    public Integer getOfferTimes() {
        return offerTimes;
    }

    public void setOfferTimes(Integer offerTimes) {
        this.offerTimes = offerTimes;
    }

    public Integer getMyExpireCount() {
        return myExpireCount;
    }

    public void setMyExpireCount(Integer myExpireCount) {
        this.myExpireCount = myExpireCount;
    }

    public Integer getIsEnquiryCount() {
        return isEnquiryCount;
    }

    public void setIsEnquiryCount(Integer isEnquiryCount) {
        this.isEnquiryCount = isEnquiryCount;
    }

    public Integer getZhujiSolutionAcceptCount() {
        return zhujiSolutionAcceptCount;
    }

    public void setZhujiSolutionAcceptCount(Integer zhujiSolutionAcceptCount) {
        this.zhujiSolutionAcceptCount = zhujiSolutionAcceptCount;
    }

    public Integer getZhujiDiyingCount() {
        return zhujiDiyingCount;
    }

    public void setZhujiDiyingCount(Integer zhujiDiyingCount) {
        this.zhujiDiyingCount = zhujiDiyingCount;
    }
}
