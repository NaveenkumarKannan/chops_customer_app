
package com.chops.app.model;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class UserData {

    @SerializedName("city")
    private String mCity="";
    @SerializedName("email")
    private String mEmail="";
    @SerializedName("fname")
    private String mFname="";
    @SerializedName("id")
    private String mId="";
    @SerializedName("lname")
    private String mLname="";
    @SerializedName("mobile")
    private String mMobile="";
    @SerializedName("password")
    private String mPassword="";
    @SerializedName("status")
    private String mStatus="";

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getFname() {
        return mFname;
    }

    public void setFname(String fname) {
        mFname = fname;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getLname() {
        return mLname;
    }

    public void setLname(String lname) {
        mLname = lname;
    }

    public String getMobile() {
        return mMobile;
    }

    public void setMobile(String mobile) {
        mMobile = mobile;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
