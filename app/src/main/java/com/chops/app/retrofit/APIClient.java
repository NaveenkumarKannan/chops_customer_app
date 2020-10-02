package com.chops.app.retrofit;


import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    static Retrofit retrofit = null;

    //public static String Base_URL = "http://fishmeat.ml/";
    public static String Base_URL = "http://chopsindia.com/chopss/";
    public static final String Append_URL = "/chopss/api/";

    public static UserService getInterface() {

        Log.e("ApiClient", Base_URL+Append_URL);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(UserService.class);
    }

}
