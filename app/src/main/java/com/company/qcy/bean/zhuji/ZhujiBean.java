package com.company.qcy.bean.zhuji;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ZhujiBean {



    private Long id;
    private Long classId;
    private String className;
    private String zhujiName;
    private String endTimeStamp;
    private String status;
//    private Object userId;
//    private Object loginUserId;
//    private Object loginCompanyId;
//    private Object identity;
    private String companyName;
    private String companyName2;
    private String isCharger;
    private String material;
    private String purpose;
    private String requirement;
    private String equipment;
    private String dye;
    private String temperature;
    private String productName;
    private String producer;
    private String description;
    private String phone;
//    private Object isValid;
    private String createdAt;
//    private Object updatedAt;
    private Integer solution_num;
    private String creditLevel;
    private String vipLevel;
    private String publishType;
    private String diyNumStr;
    private String useNumStr;
    private List<SolutionListBean> solutionList;

    public String getUseNumStr() {
        return useNumStr;
    }

    public void setUseNumStr(String useNumStr) {
        this.useNumStr = useNumStr;
    }

    public String getDiyNumStr() {
        return diyNumStr;
    }

    public void setDiyNumStr(String diyNumStr) {
        this.diyNumStr = diyNumStr;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getZhujiName() {
        return zhujiName;
    }

    public void setZhujiName(String zhujiName) {
        this.zhujiName = zhujiName;
    }

    public String getEndTimeStamp() {
        return endTimeStamp;
    }

    public void setEndTimeStamp(String endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName2() {
        return companyName2;
    }

    public void setCompanyName2(String companyName2) {
        this.companyName2 = companyName2;
    }

    public String getIsCharger() {
        return isCharger;
    }

    public void setIsCharger(String isCharger) {
        this.isCharger = isCharger;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getDye() {
        return dye;
    }

    public void setDye(String dye) {
        this.dye = dye;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getSolution_num() {
        return solution_num;
    }

    public void setSolution_num(Integer solution_num) {
        this.solution_num = solution_num;
    }

    public String getCreditLevel() {
        return creditLevel;
    }

    public void setCreditLevel(String creditLevel) {
        this.creditLevel = creditLevel;
    }

    public String getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(String vipLevel) {
        this.vipLevel = vipLevel;
    }

    public String getPublishType() {
        return publishType;
    }

    public void setPublishType(String publishType) {
        this.publishType = publishType;
    }

    public List<SolutionListBean> getSolutionList() {
        return solutionList;
    }

    public void setSolutionList(List<SolutionListBean> solutionList) {
        this.solutionList = solutionList;
    }

    public static class SolutionListBean {



        private Long id;
        private Long zhujiDiyId;
//        private Object userId;
//        private Object loginUserId;
//        private Object loginCompanyId;
//        private Object identity;
        private String companyName;
        private String companyName2;
        private String status;
        private String phone;
        private String productName;
        private String num;
        private String numUnit;
        private String description;
        private String attachUrl;
        private String isValid;
        private String createdAt;
//        private Object updatedAt;
        private ZhujiDiyBean zhujiDiy;
        private List<AttachListBean> attachList;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getZhujiDiyId() {
            return zhujiDiyId;
        }

        public void setZhujiDiyId(Long zhujiDiyId) {
            this.zhujiDiyId = zhujiDiyId;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getCompanyName2() {
            return companyName2;
        }

        public void setCompanyName2(String companyName2) {
            this.companyName2 = companyName2;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getNumUnit() {
            return numUnit;
        }

        public void setNumUnit(String numUnit) {
            this.numUnit = numUnit;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAttachUrl() {
            return attachUrl;
        }

        public void setAttachUrl(String attachUrl) {
            this.attachUrl = attachUrl;
        }

        public String getIsValid() {
            return isValid;
        }

        public void setIsValid(String isValid) {
            this.isValid = isValid;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public ZhujiDiyBean getZhujiDiy() {
            return zhujiDiy;
        }

        public void setZhujiDiy(ZhujiDiyBean zhujiDiy) {
            this.zhujiDiy = zhujiDiy;
        }

        public List<AttachListBean> getAttachList() {
            return attachList;
        }

        public void setAttachList(List<AttachListBean> attachList) {
            this.attachList = attachList;
        }

        public static class ZhujiDiyBean  {



            private Long id;

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }


        }

        public static class AttachListBean  {


            /**
             * id : null
             * zhujiDiySolutionId : 2
             * attachName : 附件1.PDF
             * attachUrl : /groupBuy/123456.pdf
             * isValid : null
             */

//            private Object id;
            private Long zhujiDiySolutionId;
            private String attachName;
            private String attachUrl;
//            private Object isValid;


            public Long getZhujiDiySolutionId() {
                return zhujiDiySolutionId;
            }

            public void setZhujiDiySolutionId(Long zhujiDiySolutionId) {
                this.zhujiDiySolutionId = zhujiDiySolutionId;
            }

            public String getAttachName() {
                return attachName;
            }

            public void setAttachName(String attachName) {
                this.attachName = attachName;
            }

            public String getAttachUrl() {
                return attachUrl;
            }

            public void setAttachUrl(String attachUrl) {
                this.attachUrl = attachUrl;
            }


        }



    }

}
