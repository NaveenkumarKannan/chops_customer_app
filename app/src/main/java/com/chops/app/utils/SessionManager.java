package com.chops.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.chops.app.model.UserData;
import com.google.gson.Gson;

public class SessionManager {
    private final SharedPreferences mPrefs;
    SharedPreferences.Editor mEditor;

    public static boolean ISCART = false;


    public static String USERDATA = "Userdata";
    public static String USERLOGIN = "UserLogin";
    public static String CURRNCY = "Currncy";
    public static String ODERMINIMUM = "orderminimum";
    public static String RAZORPAYKEY = "rkey";

    public static String PRIVACY = "privacy_policy";
    public static String TREMSCODITION = "tremcodition";
    public static String ABOUT_US = "about_us";
    public static String CONTACT_US = "contact_us";
    public SessionManager(Context context) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mPrefs.edit();
    }

    public void setStringData(String key, String val) {
        mEditor.putString(key, val);
        mEditor.commit();
    }

    public String getStringData(String key) {
        return mPrefs.getString(key, "");
    }

    public void setBooleanData(String key, Boolean val) {
        mEditor.putBoolean(key, val);
        mEditor.commit();
    }

    public boolean getBooleanData(String key) {
        return mPrefs.getBoolean(key, false);
    }

    public void setIntData(String key, int val) {
        mEditor.putInt(key, val);
        mEditor.commit();
    }

    public int getIntData(String key) {
        return mPrefs.getInt(key, 0);
    }

    public void setUserDetails(String key, UserData val) {
        mEditor.putString(USERDATA, new Gson().toJson(val));
        mEditor.commit();
    }

    public UserData getUserDetails(String key) {
        return new Gson().fromJson(mPrefs.getString(USERDATA, ""), UserData.class);
    }


    public void logoutUser() {
        mEditor.clear();
        mEditor.commit();
    }


}
