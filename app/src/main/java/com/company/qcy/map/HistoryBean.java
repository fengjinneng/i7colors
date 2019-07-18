package com.company.qcy.map;

import java.util.Objects;

public class HistoryBean {


    private String province;
    private String city;
    private String area;

    public HistoryBean() {
    }

    public HistoryBean(String province, String city, String area) {
        this.province = province;
        this.city = city;
        this.area = area;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "{" + "\"province\":\"" + province +"\"" +"," +"\"city\":\"" + city +"\"" +"," + "\"area:\":\"" + area  +"\"" + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoryBean that = (HistoryBean) o;
        return Objects.equals(province, that.province) &&
                Objects.equals(city, that.city) &&
                Objects.equals(area, that.area);
    }

    @Override
    public int hashCode() {

        return Objects.hash(province, city, area);
    }
}
