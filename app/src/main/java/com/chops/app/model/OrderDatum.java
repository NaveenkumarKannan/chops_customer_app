
package com.chops.app.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class OrderDatum {

    @SerializedName("listdata")
    private List<Listdatum> mListdata;
    @SerializedName("odate")
    private String mOdate;
    @SerializedName("orderid")
    private String mOrderid;
    @SerializedName("otype")
    private String mOtype;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("total_price")
    private String mTotalPrice;

    public List<Listdatum> getListdata() {
        return mListdata;
    }

    public void setListdata(List<Listdatum> listdata) {
        mListdata = listdata;
    }

    public String getOdate() {
        return mOdate;
    }

    public void setOdate(String odate) {
        mOdate = odate;
    }

    public String getOrderid() {
        return mOrderid;
    }

    public void setOrderid(String orderid) {
        mOrderid = orderid;
    }

    public String getOtype() {
        return mOtype;
    }

    public void setOtype(String otype) {
        mOtype = otype;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getTotalPrice() {
        return mTotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        mTotalPrice = totalPrice;
    }

}
