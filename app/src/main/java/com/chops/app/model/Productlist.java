
package com.chops.app.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Productlist implements Serializable {

    @SerializedName("cid")
    private String mCid;
    @SerializedName("discount")
    private String mDiscount;
    @SerializedName("gross")
    private String mGross;
    @SerializedName("id")
    private String mId;
    @SerializedName("image")
    private String mImage;
    @SerializedName("name")
    private String mName;
    @SerializedName("net")
    private String mNet;
    @SerializedName("pipack")
    private String mPipack;
    @SerializedName("price")
    private String mPrice;
    @SerializedName("sdesc")
    private String mSdesc;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("types")
    private String mTypes;
    @SerializedName("skin")
    private int skin;

    @SerializedName("pieces_type")
    private String piecesType;

    public int getSkin() {
        return skin;
    }

    public void setSkin(int skin) {
        this.skin = skin;
    }

    public String getPiecesType() {
        return piecesType;
    }

    public void setPiecesType(String piecesType) {
        this.piecesType = piecesType;
    }

    private int contity;
    private int totalPrice;

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getContity() {
        return contity;
    }

    public void setContity(int contity) {
        this.contity = contity;
    }

    public String getCid() {
        return mCid;
    }

    public void setCid(String cid) {
        mCid = cid;
    }

    public String getDiscount() {
        return mDiscount;
    }

    public void setDiscount(String discount) {
        mDiscount = discount;
    }

    public String getGross() {
        return mGross;
    }

    public void setGross(String gross) {
        mGross = gross;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getNet() {
        return mNet;
    }

    public void setNet(String net) {
        mNet = net;
    }

    public String getPipack() {
        return mPipack;
    }

    public void setPipack(String pipack) {
        mPipack = pipack;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getSdesc() {
        return mSdesc;
    }

    public void setSdesc(String sdesc) {
        mSdesc = sdesc;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }



    public String getTypes() {
        return mTypes;
    }

    public void setTypes(String types) {
        mTypes = types;
    }

}
