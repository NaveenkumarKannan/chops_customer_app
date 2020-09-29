
package com.chops.app.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Listdatum {

    @SerializedName("image")
    private String mImage;
    @SerializedName("price")
    private String mPrice;
    @SerializedName("quantity")
    private String mQuantity;
    @SerializedName("title")
    private String mTitle;

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getQuantity() {
        return mQuantity;
    }

    public void setQuantity(String quantity) {
        mQuantity = quantity;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

}
