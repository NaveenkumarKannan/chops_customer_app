
package com.chops.app.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Home {

    @SerializedName("ResponseCode")
    private String mResponseCode;
    @SerializedName("ResponseMsg")
    private String mResponseMsg;
    @SerializedName("Result")
    private String mResult;
    @SerializedName("ResultData")
    private HomeList mHomeList;



    public String getResponseCode() {
        return mResponseCode;
    }

    public void setResponseCode(String responseCode) {
        mResponseCode = responseCode;
    }

    public String getResponseMsg() {
        return mResponseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        mResponseMsg = responseMsg;
    }

    public String getResult() {
        return mResult;
    }

    public void setResult(String result) {
        mResult = result;
    }

    public HomeList getHomeList() {
        return mHomeList;
    }

    public void setHomeList(HomeList homeList) {
        mHomeList = homeList;
    }

}
