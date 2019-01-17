package com.company.qcy.huodong.youhuizhanxiao.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class YouhuizhanxiaoBean implements Parcelable {


    /**
     * id : 28
     * name : 活性黑
     * banner : /groupBuy/1541138990796RBLBKK.jpg
     * status : 1
     * productName : 活性黑-dy
     * productPic : /groupBuy/1541138996984G9K893.jpg
     * oldPrice : 15.0
     * newPrice : 10
     * priceUnit : KG
     * totalNum : 1000.0
     * numUnit : 吨
     * listSale : [{"id":22,"salesId":28,"shuXing":"颜色","zhi":"白"},{"id":23,"salesId":28,"shuXing":"包装","zhi":"箱"}]
     * listPrice : [{"id":20,"salesId":28,"salesPrice":27,"salesNum":"低于5吨"},{"id":21,"salesId":28,"salesPrice":26.5,"salesNum":"5~20吨"},{"id":22,"salesId":28,"salesPrice":26,"salesNum":"20吨以上"}]
     */

    private Long id;
    private String name;
    private String banner;
    private String status;
    private String productName;
    private String productPic;
    private String oldPrice;
    private String newPrice;
    private String priceUnit;
    private String totalNum;
    private String numUnit;
    private ArrayList<ListSaleBean> listSale;
    private List<ListPriceBean> listPrice;
    private String detailMobilePic;

    public String getDetailMobilePic() {
        return detailMobilePic;
    }

    public void setDetailMobilePic(String detailMobilePic) {
        this.detailMobilePic = detailMobilePic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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


    public String getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(String newPrice) {
        this.newPrice = newPrice;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }



    public String getNumUnit() {
        return numUnit;
    }

    public void setNumUnit(String numUnit) {
        this.numUnit = numUnit;
    }

    public ArrayList<ListSaleBean> getListSale() {
        return listSale;
    }

    public void setListSale(ArrayList<ListSaleBean> listSale) {
        this.listSale = listSale;
    }

    public List<ListPriceBean> getListPrice() {
        return listPrice;
    }

    public void setListPrice(List<ListPriceBean> listPrice) {
        this.listPrice = listPrice;
    }

    public static class ListSaleBean implements Parcelable {
        /**
         * id : 22
         * salesId : 28
         * shuXing : 颜色
         * zhi : 白
         */

        private Long id;
        private Long salesId;
        private String shuXing;
        private String zhi;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getSalesId() {
            return salesId;
        }

        public void setSalesId(Long salesId) {
            this.salesId = salesId;
        }

        public String getShuXing() {
            return shuXing;
        }

        public void setShuXing(String shuXing) {
            this.shuXing = shuXing;
        }

        public String getZhi() {
            return zhi;
        }

        public void setZhi(String zhi) {
            this.zhi = zhi;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(this.id);
            dest.writeValue(this.salesId);
            dest.writeString(this.shuXing);
            dest.writeString(this.zhi);
        }

        public ListSaleBean() {
        }

        protected ListSaleBean(Parcel in) {
            this.id = (Long) in.readValue(Long.class.getClassLoader());
            this.salesId = (Long) in.readValue(Long.class.getClassLoader());
            this.shuXing = in.readString();
            this.zhi = in.readString();
        }

        public static final Creator<ListSaleBean> CREATOR = new Creator<ListSaleBean>() {
            @Override
            public ListSaleBean createFromParcel(Parcel source) {
                return new ListSaleBean(source);
            }

            @Override
            public ListSaleBean[] newArray(int size) {
                return new ListSaleBean[size];
            }
        };
    }

    public static class ListPriceBean implements Parcelable {
        /**
         * id : 20
         * salesId : 28
         * salesPrice : 27.0
         * salesNum : 低于5吨
         */

        private Long id;
        private Long salesId;
        private String salesPrice;
        private String salesNum;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getSalesId() {
            return salesId;
        }

        public void setSalesId(Long salesId) {
            this.salesId = salesId;
        }

        public String getSalesPrice() {
            return salesPrice;
        }

        public void setSalesPrice(String salesPrice) {
            this.salesPrice = salesPrice;
        }

        public String getSalesNum() {
            return salesNum;
        }

        public void setSalesNum(String salesNum) {
            this.salesNum = salesNum;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(this.id);
            dest.writeValue(this.salesId);
            dest.writeString(this.salesPrice);
            dest.writeString(this.salesNum);
        }

        public ListPriceBean() {
        }

        protected ListPriceBean(Parcel in) {
            this.id = (Long) in.readValue(Long.class.getClassLoader());
            this.salesId = (Long) in.readValue(Long.class.getClassLoader());
            this.salesPrice = in.readString();
            this.salesNum = in.readString();
        }

        public static final Creator<ListPriceBean> CREATOR = new Creator<ListPriceBean>() {
            @Override
            public ListPriceBean createFromParcel(Parcel source) {
                return new ListPriceBean(source);
            }

            @Override
            public ListPriceBean[] newArray(int size) {
                return new ListPriceBean[size];
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
        dest.writeString(this.name);
        dest.writeString(this.banner);
        dest.writeString(this.status);
        dest.writeString(this.productName);
        dest.writeString(this.productPic);
        dest.writeString(this.oldPrice);
        dest.writeString(this.newPrice);
        dest.writeString(this.priceUnit);
        dest.writeString(this.totalNum);
        dest.writeString(this.numUnit);
        dest.writeList(this.listSale);
        dest.writeList(this.listPrice);
        dest.writeString(this.detailMobilePic);
    }

    public YouhuizhanxiaoBean() {
    }

    protected YouhuizhanxiaoBean(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.banner = in.readString();
        this.status = in.readString();
        this.productName = in.readString();
        this.productPic = in.readString();
        this.oldPrice = in.readString();
        this.newPrice = in.readString();
        this.priceUnit = in.readString();
        this.totalNum = in.readString();
        this.numUnit = in.readString();
        this.listSale = new ArrayList<ListSaleBean>();
        in.readList(this.listSale, ListSaleBean.class.getClassLoader());
        this.listPrice = new ArrayList<ListPriceBean>();
        in.readList(this.listPrice, ListPriceBean.class.getClassLoader());
        this.detailMobilePic = in.readString();
    }

    public static final Parcelable.Creator<YouhuizhanxiaoBean> CREATOR = new Parcelable.Creator<YouhuizhanxiaoBean>() {
        @Override
        public YouhuizhanxiaoBean createFromParcel(Parcel source) {
            return new YouhuizhanxiaoBean(source);
        }

        @Override
        public YouhuizhanxiaoBean[] newArray(int size) {
            return new YouhuizhanxiaoBean[size];
        }
    };
}
