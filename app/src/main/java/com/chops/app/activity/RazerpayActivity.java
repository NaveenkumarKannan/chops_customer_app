package com.chops.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.chops.app.R;
import com.chops.app.database.DatabaseHelper;
import com.chops.app.model.UserData;
import com.chops.app.retrofit.APIClient;
import com.chops.app.retrofit.GetResult;
import com.chops.app.utils.GetService;
import com.chops.app.utils.SessionManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import retrofit2.Call;

import static com.chops.app.utils.SessionManager.RAZORPAYKEY;


public class RazerpayActivity extends AppCompatActivity implements GetResult.MyListener, PaymentResultListener {
    SessionManager sessionManager;
    UserData userData;

    String aid;
    String pid;
    String quantity;
    String total;
    DatabaseHelper helper;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_razorpay);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        helper = new DatabaseHelper(RazerpayActivity.this);
        userData = sessionManager.getUserDetails("");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            aid = extras.getString("aid");
            pid = extras.getString("pid");
            quantity = extras.getString("quantity");
            total = extras.getString("total");
            startPayment(total);

        }


    }


    public void startPayment(String amount) {
        final Checkout co = new Checkout();
        co.setKeyID(sessionManager.getStringData(RAZORPAYKEY));
        final Activity activity = this;


        try {
            JSONObject options = new JSONObject();
            options.put("name", getResources().getString(R.string.app_name));
            options.put("description", "Demoing Charges");
            //You can omit the image option to fetch the image from dashboard
//            options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
            options.put("currency", "INR");
            double totaltemp = Double.parseDouble(amount);
            totaltemp = totaltemp * 100;
            options.put("amount", totaltemp);

            JSONObject preFill = new JSONObject();
            preFill.put("email", userData.getEmail());
            preFill.put("contact", userData.getMobile());

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onPaymentSuccess(String s) {

        Log.e("success", "" + s);
        sendOrder();
    }

    @Override
    public void onPaymentError(int i, String s) {

        Log.e("error", "-->" + i);
        Log.e("error", "-->" + s);
        startActivity(new Intent(RazerpayActivity.this, HomeActivity.class));

        finish();
    }

    private void sendOrder() {
        GetService.showPrograss(RazerpayActivity.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", userData.getId());
            jsonObject.put("aid", aid);
            jsonObject.put("pid", pid);
            jsonObject.put("qty", quantity);
            jsonObject.put("total", total);
            jsonObject.put("type", "razerpay");

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
        startActivity(new Intent(RazerpayActivity.this, CongratulactionActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();
    }
}