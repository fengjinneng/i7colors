package com.company.qcy.bean.pengyouquan;

import java.util.List;

public class PengyouquanBean {


    /**
     * id : 1
     * userId : null
     * loginUserId : null
     * loginCompanyId : null
     * postUser : why公司
     * postUserPhoto : null
     * content : 中国纺织工业联合会副会长杨纪朝，虎门镇镇委副书记、镇长邓卫洪，中国印染行业协会会长陈志华，中国美术学院设计与艺术学院院长吴海燕，中山市沙溪镇副镇长肖亦宁，虎门服装服饰产业管委会主任谭志强、副主任王宝敏，广东省服装服饰行业协会副会长黄益群、秦建华，深圳市纺织行业协会会长刘士杰，东莞市毛织服装设计师协会会长陈敬东，东莞市虎门服装服饰行业协会常务副会长兼秘书长朱华泽等领导嘉宾出席。来自全国各地服装印花行业产业链上下游各领域的大咖齐聚虎门，共同打造一场服装印花行业交流与展示的盛宴。
     * pic1 : /dye_community/123456789JKL.jpg
     * pic2 : /dye_community/123456789JKL.jpg
     * pic3 : /dye_community/123456789JKL.jpg
     * pic4 : /dye_community/123456789JKL.jpg
     * pic5 : /dye_community/123456789JKL.jpg
     * pic6 : /dye_community/123456789JKL.jpg
     * pic7 : /dye_community/123456789JKL.jpg
     * pic8 : /dye_community/123456789JKL.jpg
     * pic9 : /dye_community/123456789JKL.jpg
     * isBan : null
     * sortNum : 2
     * isValid : null
     * createdAt : 2018-10-23 14:33:05.0
     * createdAtStamp : 1540276385
     * isLike : 1
     * updatedAt : null
     * commentList : [{"id":1,"dyeId":null,"userId":null,"commentUser":"15580804456","byCommentUser":null,"parentId":null,"content":"阿伦公司来评论啦","isRead":null,"isBan":null,"isValid":null,"createdAt":null,"createdAtStamp":null,"updatedAt":null},{"id":2,"dyeId":null,"userId":null,"commentUser":"封金能","byCommentUser":null,"parentId":null,"content":"阿伦子公司来评论啦","isRead":null,"isBan":null,"isValid":null,"createdAt":null,"createdAtStamp":null,"updatedAt":null},{"id":3,"dyeId":null,"userId":null,"commentUser":"封金能","byCommentUser":"15580804456","parentId":null,"content":"阿伦子公司评论阿伦公司哎","isRead":null,"isBan":null,"isValid":null,"createdAt":null,"createdAtStamp":null,"updatedAt":null}]
     * likeList : [{"id":114842,"dyeId":null,"userId":null,"likeUserPhoto":null,"isValid":null,"createdAt":null,"updatedAt":null}]
     */

    private Long id;
    private Long userId;
    private Long loginUserId;
    private Long loginCompanyId;
    private String postUser;
    private String postUserPhoto;
    private String content;
    private String pic1;
    private String pic2;
    private String pic3;
    private String pic4;
    private String pic5;
    private String pic6;
    private String pic7;
    private String pic8;
    private String pic9;
    private Object isBan;
    private int sortNum;
    private Object isValid;
    private String createdAt;
    private String createdAtStamp;
    private String isLike;
    private Object updatedAt;
    private List<CommentListBean> commentList;
    private List<LikeListBean> likeList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(Long loginUserId) {
        this.loginUserId = loginUserId;
    }

    public Long getLoginCompanyId() {
        return loginCompanyId;
    }

    public void setLoginCompanyId(Long loginCompanyId) {
        this.loginCompanyId = loginCompanyId;
    }

    public String getPostUser() {
        return postUser;
    }

    public void setPostUser(String postUser) {
        this.postUser = postUser;
    }

    public String getPostUserPhoto() {
        return postUserPhoto;
    }

    public void setPostUserPhoto(String postUserPhoto) {
        this.postUserPhoto = postUserPhoto;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public String getPic3() {
        return pic3;
    }

    public void setPic3(String pic3) {
        this.pic3 = pic3;
    }

    public String getPic4() {
        return pic4;
    }

    public void setPic4(String pic4) {
        this.pic4 = pic4;
    }

    public String getPic5() {
        return pic5;
    }

    public void setPic5(String pic5) {
        this.pic5 = pic5;
    }

    public String getPic6() {
        return pic6;
    }

    public void setPic6(String pic6) {
        this.pic6 = pic6;
    }

    public String getPic7() {
        return pic7;
    }

    public void setPic7(String pic7) {
        this.pic7 = pic7;
    }

    public String getPic8() {
        return pic8;
    }

    public void setPic8(String pic8) {
        this.pic8 = pic8;
    }

    public String getPic9() {
        return pic9;
    }

    public void setPic9(String pic9) {
        this.pic9 = pic9;
    }

    public Object getIsBan() {
        return isBan;
    }

    public void setIsBan(Object isBan) {
        this.isBan = isBan;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public Object getIsValid() {
        return isValid;
    }

    public void setIsValid(Object isValid) {
        this.isValid = isValid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAtStamp() {
        return createdAtStamp;
    }

    public void setCreatedAtStamp(String createdAtStamp) {
        this.createdAtStamp = createdAtStamp;
    }

    public String getIsLike() {
        return isLike;
    }

    public void setIsLike(String isLike) {
        this.isLike = isLike;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<CommentListBean> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentListBean> commentList) {
        this.commentList = commentList;
    }

    public List<LikeListBean> getLikeList() {
        return likeList;
    }

    public void setLikeList(List<LikeListBean> likeList) {
        this.likeList = likeList;
    }

    public static class CommentListBean {
        /**
         * id : 1
         * dyeId : null
         * userId : null
         * commentUser : 15580804456
         * byCommentUser : null
         * parentId : null
         * content : 阿伦公司来评论啦
         * isRead : null
         * isBan : null
         * isValid : null
         * createdAt : null
         * createdAtStamp : null
         * updatedAt : null
         */

        private Long id;
        private Long dyeId;
        private Long userId;
        private String commentUser;
        private String byCommentUser;
        private Object parentId;
        private String content;
        private Object isRead;
        private Object isBan;
        private Object isValid;
        private Object createdAt;
        private Object createdAtStamp;
        private Object updatedAt;

        public void setId(Long id) {
            this.id = id;
        }

        public void setDyeId(Long dyeId) {
            this.dyeId = dyeId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public Long getId() {
            return id;
        }

        public Long getDyeId() {
            return dyeId;
        }

        public Long getUserId() {
            return userId;
        }

        public String getCommentUser() {
            return commentUser;
        }

        public void setCommentUser(String commentUser) {
            this.commentUser = commentUser;
        }

        public String getByCommentUser() {
            return byCommentUser;
        }

        public void setByCommentUser(String byCommentUser) {
            this.byCommentUser = byCommentUser;
        }

        public Object getParentId() {
            return parentId;
        }

        public void setParentId(Object parentId) {
            this.parentId = parentId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getIsRead() {
            return isRead;
        }

        public void setIsRead(Object isRead) {
            this.isRead = isRead;
        }

        public Object getIsBan() {
            return isBan;
        }

        public void setIsBan(Object isBan) {
            this.isBan = isBan;
        }

        public Object getIsValid() {
            return isValid;
        }

        public void setIsValid(Object isValid) {
            this.isValid = isValid;
        }

        public Object getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Object createdAt) {
            this.createdAt = createdAt;
        }

        public Object getCreatedAtStamp() {
            return createdAtStamp;
        }

        public void setCreatedAtStamp(Object createdAtStamp) {
            this.createdAtStamp = createdAtStamp;
        }

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updatedAt = updatedAt;
        }
    }

    public static class LikeListBean {
        /**
         * id : 114842
         * dyeId : null
         * userId : null
         * likeUserPhoto : null
         * isValid : null
         * createdAt : null
         * updatedAt : null
         */

        private Long id;
        private Long dyeId;
        private Long userId;
        private Object likeUserPhoto;
        private Object isValid;
        private Object createdAt;
        private Object updatedAt;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getDyeId() {
            return dyeId;
        }

        public void setDyeId(Long dyeId) {
            this.dyeId = dyeId;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public Object getLikeUserPhoto() {
            return likeUserPhoto;
        }

        public void setLikeUserPhoto(Object likeUserPhoto) {
            this.likeUserPhoto = likeUserPhoto;
        }

        public Object getIsValid() {
            return isValid;
        }

        public void setIsValid(Object isValid) {
            this.isValid = isValid;
        }

        public Object getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Object createdAt) {
            this.createdAt = createdAt;
        }

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updatedAt = updatedAt;
        }
    }
}
