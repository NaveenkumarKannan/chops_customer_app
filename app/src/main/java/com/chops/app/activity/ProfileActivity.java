package com.chops.app.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.chops.app.R;
import com.chops.app.model.User;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class ProfileActivity extends AppCompatActivity implements GetResult.MyListener {

    @BindView(R.id.ed_fname)
    EditText edFname;
    @BindView(R.id.ed_lname)
    EditText edLname;
    @BindView(R.id.ed_email)
    EditText edEmail;
    @BindView(R.id.ed_phone)
    EditText edPhone;
    @BindView(R.id.ed_password)
    EditText edPassword;
    @BindView(R.id.btn_update)
    Button btnUpdate;


    SessionManager sessionManager;
    UserData userData;
    @BindView(R.id.txt_profilename)
    TextView txtProfilename;
    @BindView(R.id.txt_city)
    TextView txtCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(ProfileActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Profile");
        userData = sessionManager.getUserDetails("");
        if (userData != null) {
            txtProfilename.setText("" + userData.getFname() + " " + userData.getLname());
            txtCity.setText("" + userData.getCity());
            edFname.setText("" + userData.getFname());
            edLname.setText("" + userData.getLname());
            edEmail.setText("" + userData.getEmail());
            edPhone.setText("" + userData.getMobile());
            edPassword.setText("" + userData.getPassword());

        }
    }


    @OnClick({R.id.btn_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btn_update:
                if (isValidation()) {
                    UpdateAddress();
                }
                break;
            default:
                break;
        }
    }

    private void UpdateAddress() {
        GetService.showPrograss(ProfileActivity.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", userData.getId());
            jsonObject.put("email", edEmail.getText().toString());
            jsonObject.put("password", edPassword.getText().toString());
            jsonObject.put("mobile", edPhone.getText().toString());
            jsonObject.put("fname", edFname.getText().toString());
            jsonObject.put("lname", edLname.getText().toString());

            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().UpdateProfile((JsonObject) jsonParser.parse(jsonObject.toString()));
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
        if (callNo.equalsIgnoreCase("1")) {
            Gson gson = new Gson();
            User response = gson.fromJson(result.toString(), User.class);
            if (response.getResult().equalsIgnoreCase("true")) {
                sessionManager.setUserDetails("", response.getResultData());
                finish();
            }
            Toast.makeText(ProfileActivity.this, response.getResponseMsg(), Toast.LENGTH_LONG).show();
        }

    }

    public boolean isValidation() {

        if (TextUtils.isEmpty(edEmail.getText().toString())) {
            edEmail.setError("Required field");
            return false;
        } else if (TextUtils.isEmpty(edFname.getText().toString())) {
            edFname.setError("Required field");
            return false;
        } else if (TextUtils.isEmpty(edLname.getText().toString())) {
            edLname.setError("Required field");
            return false;
        } else if (TextUtils.isEmpty(edPhone.getText().toString())) {
            edPhone.setError("Required field");
            return false;
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

//    public void ShowHidePass() {
//
//        if (edPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
//            //Show Password
//            edPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//        } else {
//            //Hide Password
//            edPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
//
//        }
//    }
}
