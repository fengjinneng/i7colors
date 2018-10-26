package com.company.qcy.bean.qiugou;

public class QiugoufenleiBean {



    /**
     * id : 1
     * name : 助剂
     * level : 1
     * parentId : 0
     * isValid : 1
     * createdAt : null
     * updateAt : null
     */

    private Integer id;
    private String name;
    private Integer level;
    private Integer parentId;
    private String isValid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

}
