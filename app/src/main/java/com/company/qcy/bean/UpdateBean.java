package com.company.qcy.bean;


public class UpdateBean {


    /**
     * description : 1.这是更新后的版本/n2.你一定要更新哦/n3./n4.其实没有第三点
     * id : 1
     * isForce : 0
     * type : android
     * versionCode : 1.0.1
     * versionNum : 1
     */

    private String description;
    private int id;
    private String isForce;
    private String type;
    private String versionCode;
    private int versionNum;
    private String url;

    private String hasUpdate;


    public String getHasUpdate() {
        return hasUpdate;
    }

    public void setHasUpdate(String hasUpdate) {
        this.hasUpdate = hasUpdate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }





    public String getIsForce() {
        return isForce;
    }

    public void setIsForce(String isForce) {
        this.isForce = isForce;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(int versionNum) {
        this.versionNum = versionNum;
    }

    @Override
    public String toString() {
        return "UpdateBean{" +
                "description='" + description + '\'' +
                ", id=" + id +
                ", isForce='" + isForce + '\'' +
                ", type='" + type + '\'' +
                ", versionCode='" + versionCode + '\'' +
                ", versionNum=" + versionNum +
                ", url='" + url + '\'' +
                '}';
    }
}
