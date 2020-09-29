package com.chops.app.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.chops.app.R;
import com.chops.app.model.Address;
import com.chops.app.model.UserData;
import com.chops.app.retrofit.APIClient;
import com.chops.app.retrofit.GetResult;
import com.chops.app.utils.GetService;
import com.chops.app.utils.SessionManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import static com.chops.app.utils.GetService.isRef;



public class UpdateAddressActivity extends AppCompatActivity implements GetResult.MyListener {


    UserData user;
    SessionManager sessionManager;
    Address address;
    @BindView(R.id.ed_type)
    EditText edType;
    @BindView(R.id.ed_address1)
    EditText edAddress1;
    @BindView(R.id.ed_address2)
    EditText edAddress2;
    @BindView(R.id.ed_pincode)
    EditText edPincode;
    @BindView(R.id.ed_city)
    EditText edCity;
    @BindView(R.id.ed_stats)
    EditText edStats;
    @BindView(R.id.btn_singup)
    Button btnSingup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_address);
        ButterKnife.bind(this);
        user = new UserData();
        sessionManager = new SessionManager(UpdateAddressActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Address");
        user = sessionManager.getUserDetails("");
        address = (Address) getIntent().getSerializableExtra("MyClass");
        if (address != null) {
            edType.setText("" + address.getType());
            edAddress1.setText("" + address.getAdd1());
            edAddress2.setText("" + address.getAdd2());
            edPincode.setText("" + address.getPincode());
            edCity.setText("" + address.getCity());
            edStats.setText("" + address.getState());
        }
    }

    @OnClick({R.id.btn_singup})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btn_singup:
                if (isValidation()) {
                    Address();
                }
                break;
            default:
                break;
        }
    }

    public boolean isValidation() {

        if (TextUtils.isEmpty(edType.getText().toString())) {
            edType.setError("Required field");
            return false;
        } else if (TextUtils.isEmpty(edAddress1.getText().toString())) {
            edAddress1.setError("Required field");
            return false;
        } else if (TextUtils.isEmpty(edAddress2.getText().toString())) {
            edAddress2.setError("Required field");
            return false;
        } else if (TextUtils.isEmpty(edPincode.getText().toString())) {
            edPincode.setError("Required field");
            return false;
        } else if (TextUtils.isEmpty(edCity.getText().toString())) {
            edCity.setError("Required field");
            return false;
        } else if (TextUtils.isEmpty(edStats.getText().toString())) {
            edStats.setError("Required field");
            return false;
        }

        return true;
    }

    private void Address() {
        GetService.showPrograss(UpdateAddressActivity.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", user.getId());
            jsonObject.put("type", edType.getText().toString());
            jsonObject.put("add1", edAddress1.getText().toString());
            jsonObject.put("add2", edAddress2.getText().toString());
            jsonObject.put("pincode", edPincode.getText().toString());
            jsonObject.put("city", edCity.getText().toString());
            jsonObject.put("state", edStats.getText().toString());
            if (address != null) {
                jsonObject.put("aid", address.getId());
            } else {
                jsonObject.put("aid", "0");
            }
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().UpdateAddress((JsonObject) jsonParser.parse(jsonObject.toString()));
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
            isRef = true;
            finish();
        }
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
}
