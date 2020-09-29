package com.chops.app.retrofit;

import android.util.Log;


import com.chops.app.utils.GetService;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetResult {
    public static MyListener myListener;

    public void callForLogin(Call<JsonObject> call, final String callno) {

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.e("message", " : " + response.message());
                Log.e("body", " : " + response.body());
                Log.e("callno", " : " + callno);
                myListener.callback(response.body(), callno);
                GetService.close();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                GetService.close();
                myListener.callback(null, callno);
                call.cancel();
                t.printStackTrace();
            }
        });
    }

    public interface MyListener {
        // you can define any parameter as per your requirement
        public void callback(JsonObject result, String callNo);
    }

    public void setMyListener(MyListener Listener) {
        myListener = Listener;
    }
}
