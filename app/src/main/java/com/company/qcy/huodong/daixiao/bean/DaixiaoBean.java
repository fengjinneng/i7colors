package com.company.qcy.huodong.daixiao.bean;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class DaixiaoBean implements Parcelable {

    private Long id;
    private Long proxyMarketUpdateId;
    private String name;
    private String productName;
    private String productPic;
    private Double price;
    private String priceUnit;
    private Integer num;
    private Integer remainNum;
    private Integer subscribedNum;
    private String pack;
    private String code;
    private String numUnit;
    private List<DictMapBean> dictMap;
    private List<String> noteList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProxyMarketUpdateId() {
        return proxyMarketUpdateId;
    }

    public void setProxyMarketUpdateId(Long proxyMarketUpdateId) {
        this.proxyMarketUpdateId = proxyMarketUpdateId;
    }

    public static Creator<DaixiaoBean> getCREATOR() {
        return CREATOR;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getRemainNum() {
        return remainNum;
    }

    public void setRemainNum(Integer remainNum) {
        this.remainNum = remainNum;
    }

    public Integer getSubscribedNum() {
        return subscribedNum;
    }

    public void setSubscribedNum(Integer subscribedNum) {
        this.subscribedNum = subscribedNum;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNumUnit() {
        return numUnit;
    }

    public void setNumUnit(String numUnit) {
        this.numUnit = numUnit;
    }

    public List<DictMapBean> getDictMap() {
        return dictMap;
    }

    public void setDictMap(List<DictMapBean> dictMap) {
        this.dictMap = dictMap;
    }

    public List<String> getNoteList() {
        return noteList;
    }

    public void setNoteList(List<String> noteList) {
        this.noteList = noteList;
    }

    public static class DictMapBean implements Parcelable {
        /**
         * id : 49
         * proxyMarketId : null
         * key : 名称
         * value : 分散 黑ECT 300%
         * isValid : null
         * createdAt : null
         * createdBy : null
         * updatedAt : null
         * updatedBy : null
         */

        private Integer id;
        private String key;
        private String value;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(this.id);
            dest.writeString(this.key);
            dest.writeString(this.value);
        }

        public DictMapBean() {
        }

        protected DictMapBean(Parcel in) {
            this.id = (Integer) in.readValue(Integer.class.getClassLoader());
            this.key = in.readString();
            this.value = in.readString();
        }

        public static final Creator<DictMapBean> CREATOR = new Creator<DictMapBean>() {
            @Override
            public DictMapBean createFromParcel(Parcel source) {
                return new DictMapBean(source);
            }

            @Override
            public DictMapBean[] newArray(int size) {
                return new DictMapBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.proxyMarketUpdateId);
        dest.writeString(this.name);
        dest.writeString(this.productName);
        dest.writeString(this.productPic);
        dest.writeValue(this.price);
        dest.writeString(this.priceUnit);
        dest.writeValue(this.num);
        dest.writeValue(this.remainNum);
        dest.writeValue(this.subscribedNum);
        dest.writeString(this.pack);
        dest.writeString(this.code);
        dest.writeString(this.numUnit);
        dest.writeList(this.dictMap);
        dest.writeStringList(this.noteList);
    }

    public DaixiaoBean() {
    }

    protected DaixiaoBean(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.proxyMarketUpdateId = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.productName = in.readString();
        this.productPic = in.readString();
        this.price = (Double) in.readValue(Double.class.getClassLoader());
        this.priceUnit = in.readString();
        this.num = (Integer) in.readValue(Integer.class.getClassLoader());
        this.remainNum = (Integer) in.readValue(Integer.class.getClassLoader());
        this.subscribedNum = (Integer) in.readValue(Integer.class.getClassLoader());
        this.pack = in.readString();
        this.code = in.readString();
        this.numUnit = in.readString();
        this.dictMap = new ArrayList<DictMapBean>();
        in.readList(this.dictMap, DictMapBean.class.getClassLoader());
        this.noteList = in.createStringArrayList();
    }

    public static final Parcelable.Creator<DaixiaoBean> CREATOR = new Parcelable.Creator<DaixiaoBean>() {
        @Override
        public DaixiaoBean createFromParcel(Parcel source) {
            return new DaixiaoBean(source);
        }

        @Override
        public DaixiaoBean[] newArray(int size) {
            return new DaixiaoBean[size];
        }
    };
}
