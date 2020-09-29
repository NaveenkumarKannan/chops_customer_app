
package com.chops.app.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class HomeList {

    @SerializedName("Banner")
    private List<Banner> mBanner;
    @SerializedName("Catlist")
    private List<Catlist> mCatlist;
    @SerializedName("Productlist")
    private List<Productlist> mProductlist;
    @SerializedName("Testimonial")
    private List<Testimonial> mTestimonial;

    @SerializedName("Remain_notification")
    private int RemainNotification;


    @SerializedName("Main_Data")
    private MainData mainData;

    public MainData getMainData() {
        return mainData;
    }

    public void setMainData(MainData mainData) {
        this.mainData = mainData;
    }



    public int getRemainNotification() {
        return RemainNotification;
    }

    public void setRemainNotification(int remainNotification) {
        RemainNotification = remainNotification;
    }

    public List<Banner> getBanner() {
        return mBanner;
    }

    public void setBanner(List<Banner> banner) {
        mBanner = banner;
    }

    public List<Catlist> getCatlist() {
        return mCatlist;
    }

    public void setCatlist(List<Catlist> catlist) {
        mCatlist = catlist;
    }

    public List<Productlist> getProductlist() {
        return mProductlist;
    }

    public void setProductlist(List<Productlist> productlist) {
        mProductlist = productlist;
    }

    public List<Testimonial> getTestimonial() {
        return mTestimonial;
    }

    public void setTestimonial(List<Testimonial> testimonial) {
        mTestimonial = testimonial;
    }

}
