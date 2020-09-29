
package com.chops.app.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Address implements Serializable {

    @SerializedName("type")
    private String mType;
    @SerializedName("add1")
    private String mAdd1;
    @SerializedName("add2")
    private String mAdd2;
    @SerializedName("city")
    private String mCity;
    @SerializedName("id")
    private String mId;
    @SerializedName("pincode")
    private String mPincode;
    @SerializedName("state")
    private String mState;
    @SerializedName("uid")
    private String mUid;


    public String getType() {
        return mType;
    }

    public void setType(String Type) {
        this.mType = Type;
    }

    public String getAdd1() {
        return mAdd1;
    }

    public void setAdd1(String add1) {
        mAdd1 = add1;
    }

    public String getAdd2() {
        return mAdd2;
    }

    public void setAdd2(String add2) {
        mAdd2 = add2;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getPincode() {
        return mPincode;
    }

    public void setPincode(String pincode) {
        mPincode = pincode;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        mState = state;
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        mUid = uid;
    }

}
