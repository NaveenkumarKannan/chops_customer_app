package com.chops.app.model;

import com.google.gson.annotations.SerializedName;

public class MainData {
    @SerializedName("raz_key")
    private String raz_key;

    @SerializedName("currency")
    private String currency;

    @SerializedName("privacy_policy")
    private String privacy_policy;

    @SerializedName("about_us")
    private String about_us;

    @SerializedName("terms")
    private String terms;

    @SerializedName("contact_us")
    private String contact_us;

    @SerializedName("o_min")
    private int oMin;

    public String getRaz_key() {
        return raz_key;
    }

    public void setRaz_key(String raz_key) {
        this.raz_key = raz_key;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPrivacy_policy() {
        return privacy_policy;
    }

    public void setPrivacy_policy(String privacy_policy) {
        this.privacy_policy = privacy_policy;
    }

    public String getAbout_us() {
        return about_us;
    }

    public void setAbout_us(String about_us) {
        this.about_us = about_us;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getContact_us() {
        return contact_us;
    }

    public void setContact_us(String contact_us) {
        this.contact_us = contact_us;
    }

    public int getoMin() {
        return oMin;
    }

    public void setoMin(int oMin) {
        this.oMin = oMin;
    }
}
