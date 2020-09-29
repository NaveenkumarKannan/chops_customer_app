package com.chops.app.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.chops.app.R;
import com.chops.app.model.Response;
import com.chops.app.retrofit.APIClient;
import com.chops.app.retrofit.GetResult;
import com.chops.app.utils.GetService;
import com.chops.app.utils.SessionManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class ForgotActivity extends AppCompatActivity implements GetResult.MyListener {

    @BindView(R.id.ed_email)
    EditText edEmail;
    @BindView(R.id.ed_otp)
    EditText edOtp;
    @BindView(R.id.ed_password)
    EditText edPassword;
    @BindView(R.id.ed_conpass)
    EditText edConpass;
    @BindView(R.id.lvl_sendpassword)
    LinearLayout lvlSendpassword;
    @BindView(R.id.btn_singup)
    Button btnSingup;
    SessionManager sessionManager;
    @BindView(R.id.txtmessege)
    TextView txtmessege;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        sessionManager = new SessionManager(ForgotActivity.this);
        if (sessionManager.getBooleanData("forgot")) {
            btnSingup.setText("Submit");
            edEmail.setVisibility(View.GONE);
            lvlSendpassword.setVisibility(View.VISIBLE);
            txtmessege.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.btn_singup)
    public void onViewClicked() {

        if (btnSingup.getText().toString().equalsIgnoreCase("Reset")) {
            if (isEmailValidation()) {
                sendOtpEmail();
            }
        } else {
            if (isValidation()) {
                sendOtp();
            }
        }
    }

    private boolean isEmailValidation() {
        if (!GetService.EmailValidator(edEmail.getText().toString())) {
            edEmail.setError("Enter valid Email");
            return false;
        }
        return true;
    }

    private boolean isValidation() {
        if (TextUtils.isEmpty(edOtp.getText().toString())) {
            edOtp.setError("Enter OTP");
            return false;
        } else if (TextUtils.isEmpty(edPassword.getText().toString())) {
            edPassword.setError("Enter Password");
            return false;
        } else if (TextUtils.isEmpty(edConpass.getText().toString())) {
            edConpass.setError("Enter Confirm Password");
            return false;
        } else if (!edPassword.getText().toString().equals(edConpass.getText().toString())) {
            edPassword.setError("Miss Match Password");
            edConpass.setError("Miss Match Password");
            return false;
        }
        return true;
    }

    private void sendOtpEmail() {
        GetService.showPrograss(ForgotActivity.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", edEmail.getText().toString());
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().sendOtp((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.callForLogin(call, "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sendOtp() {
        GetService.showPrograss(ForgotActivity.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("otp", edOtp.getText().toString());
            jsonObject.put("password", edPassword.getText().toString());
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().ChangePassword((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.callForLogin(call, "2");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void callback(JsonObject result, String callNo) {
        GetService.close();
        Gson gson = new Gson();
        if (callNo.equalsIgnoreCase("1")) {
            Response response = gson.fromJson(result.toString(), Response.class);
            if (response.getResult().equalsIgnoreCase("true")) {
                btnSingup.setText("Submit");
                edEmail.setVisibility(View.GONE);
                lvlSendpassword.setVisibility(View.VISIBLE);
                txtmessege.setVisibility(View.VISIBLE);
                sessionManager.setBooleanData("forgot", true);
            }
            Toast.makeText(ForgotActivity.this, response.getResponseMsg(), Toast.LENGTH_SHORT).show();

        } else if (callNo.equalsIgnoreCase("2")) {
            Response response = gson.fromJson(result.toString(), Response.class);
            if (response.getResult().equalsIgnoreCase("true")) {
                sessionManager.setBooleanData("forgot", false);
                finish();
            }
            Toast.makeText(ForgotActivity.this, response.getResponseMsg(), Toast.LENGTH_SHORT).show();
        }
    }

}

