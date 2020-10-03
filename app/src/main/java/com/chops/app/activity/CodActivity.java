package com.chops.app.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import com.chops.app.R;
import com.chops.app.Utility;
import com.chops.app.database.DatabaseHelper;
import com.chops.app.model.UserData;
import com.chops.app.retrofit.APIClient;
import com.chops.app.retrofit.GetResult;
import com.chops.app.utils.GetService;
import com.chops.app.utils.SessionManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import retrofit2.Call;

public class CodActivity extends AppCompatActivity implements GetResult.MyListener {


    String aid;
    String pid;
    String quantity;
    String total;
    String pieces_type;
    String dDate;

    UserData userData;
    SessionManager sessionManager;
    DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cod);
        ButterKnife.bind(this);
        helper = new DatabaseHelper(CodActivity.this);
        sessionManager = new SessionManager(CodActivity.this);
        userData = new UserData();
        userData = sessionManager.getUserDetails("");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            aid = extras.getString("aid");
            pid = extras.getString("pid");
            quantity = extras.getString("quantity");
            total = extras.getString("total");
            pieces_type  = extras.getString("pieces_type");
            dDate  = extras.getString("dDate");
            sendOrder();
        }

    }


    private void sendOrder() {
        GetService.showPrograss(CodActivity.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", userData.getId());
            jsonObject.put("aid", aid);
            jsonObject.put("pid", pid);
            jsonObject.put("qty", quantity);
            jsonObject.put("total", total);
            jsonObject.put("pieces_type", pieces_type);
            jsonObject.put("ddate", dDate);
            jsonObject.put("type", "cod");

            Utility.Companion.log(new Gson().toJson(jsonObject));
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().sendOrder((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.callForLogin(call, "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void callback(JsonObject result, String callNo) {
        GetService.close();
        helper.DeleteCard();
        startActivity(new Intent(CodActivity.this,CongratulactionActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();

    }
}
