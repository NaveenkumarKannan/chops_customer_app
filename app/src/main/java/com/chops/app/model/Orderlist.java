
package com.chops.app.model;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("unused")
public class Orderlist {

    @SerializedName("id")
    private String mId;
    @SerializedName("odate")
    private String mOdate;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("total")
    private String mTotal;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getOdate() {


        try {
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat outputFormat = new SimpleDateFormat("MMM dd yyyy");
            Date date =   inputFormat.parse(mOdate);;
            mOdate= outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return mOdate;
    }

    public void setOdate(String odate) {
        mOdate = odate;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getTotal() {
        return mTotal;
    }

    public void setTotal(String total) {
        mTotal = total;
    }

}
