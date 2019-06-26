package com.company.qcy.bean.chanpin;

import java.util.List;

public class ChanpinTypeBean {


    private Long id;
    private String typeText;
    private List<PropListBean> propList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeText() {
        return typeText;
    }

    public void setTypeText(String typeText) {
        this.typeText = typeText;
    }

    public List<PropListBean> getPropList() {
        return propList;
    }

    public void setPropList(List<PropListBean> propList) {
        this.propList = propList;
    }

    public static class PropListBean {


        private Long id;
        private String value;
        private boolean checked;
        private String yijibiaoti;

        public String getYijibiaoti() {
            return yijibiaoti;
        }

        public void setYijibiaoti(String yijibiaoti) {
            this.yijibiaoti = yijibiaoti;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
