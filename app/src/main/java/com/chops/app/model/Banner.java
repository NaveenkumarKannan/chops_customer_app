
package com.chops.app.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Banner {

    @SerializedName("banner_img")
    private String mBannerImg;

    public String getBannerImg() {
        return mBannerImg;
    }

    public void setBannerImg(String bannerImg) {
        mBannerImg = bannerImg;
    }

}
