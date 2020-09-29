
package com.chops.app.catmode;


import com.chops.app.model.Productlist;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class Cplist {

    @SerializedName("id")
    private String mId;
    @SerializedName("img")
    private String mImg;
    @SerializedName("productlist")
    private List<Productlist> mProductlist;
    @SerializedName("title")
    private String mTitle;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getImg() {
        return mImg;
    }

    public void setImg(String img) {
        mImg = img;
    }

    public List<Productlist> getProductlist() {
        return mProductlist;
    }

    public void setProductlist(List<Productlist> productlist) {
        mProductlist = productlist;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

}
