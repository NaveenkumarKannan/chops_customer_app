package com.chops.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chops.app.R;
import com.chops.app.Utility;
import com.chops.app.model.User;
import com.chops.app.retrofit.APIClient;
import com.chops.app.retrofit.GetResult;
import com.chops.app.utils.GetService;
import com.chops.app.utils.SessionManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class SignInOtpActivity extends AppCompatActivity implements GetResult.MyListener {

    @BindView(R.id.et_phone)
    TextInputEditText etPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_otp);

        //getSupportActionBar().hide();
        ButterKnife.bind(this);
    }

    public boolean isValidation() {

        if (!GetService.validatePhoneNumber(etPhone.getText().toString())) {
            etPhone.setError("Required Mobile Number");
            return false;
        }

        return true;
    }

    private void login() {
        GetService.showPrograss(SignInOtpActivity.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", etPhone.getText().toString());

            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getSignInPhone((JsonObject) jsonParser.parse(jsonObject.toString()));
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
        if (callNo.equalsIgnoreCase("1") || result.toString().length() != 0) {
            Gson gson = new Gson();

            Utility.Companion.log("Login Response: " + gson.toJson(result));

            User response = gson.fromJson(result.toString(), User.class);
            //GetService.ToastMessege(SignInOtpActivity.this, response.getResponseMsg());
            if (response.getResult().equalsIgnoreCase("true")) {

                Intent intent = new Intent(SignInOtpActivity.this, AuthenticationLogin.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("mobile", etPhone.getText().toString());
                startActivity(intent);
            }else {
                GetService.ToastMessege(SignInOtpActivity.this, response.getResponseMsg());
                Intent intent = new Intent(SignInOtpActivity.this, SignUpActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("mobile", etPhone.getText().toString());
                startActivity(intent);
            }
        }
    }

    @OnClick({R.id.btn_sign_in, R.id.btn_sign_up})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_in:
                if (isValidation()) {
                    login();
                }
                break;
            case R.id.btn_sign_up:
                startActivity(new Intent(SignInOtpActivity.this, SignUpActivity.class));
                break;
            default:
                break;
        }
    }
}
