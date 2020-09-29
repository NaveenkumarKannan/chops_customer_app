package com.chops.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.chops.app.R;
import com.chops.app.model.Address;
import com.chops.app.model.AddressData;
import com.chops.app.model.Response;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

import static com.chops.app.utils.GetService.isRef;


public class AddressActivity extends AppCompatActivity implements GetResult.MyListener {


    @BindView(R.id.toolbar_top)
    Toolbar toolbarTop;
    @BindView(R.id.recycle_address)
    RecyclerView recycleAddress;
    @BindView(R.id.btn_addaddress)
    Button btnAddaddress;

    UserData userData;
    SessionManager sessionManager;
    private List<Address> addressList = new ArrayList<>();
    AddressAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
        LinearLayoutManager recyclerLayoutManager = new LinearLayoutManager(AddressActivity.this);
        recycleAddress.setLayoutManager(recyclerLayoutManager);
        sessionManager = new SessionManager(AddressActivity.this);
        userData = new UserData();
        userData = sessionManager.getUserDetails("");
        getAddress();
    }

    @OnClick({R.id.img_delete, R.id.img_back, R.id.btn_addaddress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_delete:
                if (lastSelectedPosition != -1) {
                    deleteAddress(addressList.get(lastSelectedPosition).getId());
                } else {
                    Toast.makeText(AddressActivity.this, "please select address", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_addaddress:
                isRef = false;
                startActivity(new Intent(AddressActivity.this, UpdateAddressActivity.class));
                break;
            default:
                break;
        }
    }

    private void getAddress() {
        GetService.showPrograss(AddressActivity.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", userData.getId());

            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getAddress((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.callForLogin(call, "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isRef) {
            isRef = false;
            getAddress();
        }
    }

    private void deleteAddress(String aid) {
        GetService.showPrograss(AddressActivity.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", userData.getId());
            jsonObject.put("aid", aid);

            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().deleteAddress((JsonObject) jsonParser.parse(jsonObject.toString()));
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
        if (callNo.equalsIgnoreCase("1")) {

            Gson gson = new Gson();

            AddressData addressData = gson.fromJson(result.toString(), AddressData.class);
            if (addressData.getResult().equalsIgnoreCase("true")) {
                addressList = addressData.getResultData();
                adapter = new AddressAdapter();
                recycleAddress.setAdapter(adapter);
            }


        } else if (callNo.equalsIgnoreCase("2")) {
            Gson gson = new Gson();
            Response respons = gson.fromJson(result.toString(), Response.class);
            if (respons.getResult().equalsIgnoreCase("true")) {
                addressList.remove(lastSelectedPosition);
                adapter.notifyDataSetChanged();
            }
            Toast.makeText(AddressActivity.this, respons.getResponseMsg(), Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private int lastSelectedPosition = -1;

    public class AddressAdapter extends
            RecyclerView.Adapter<AddressAdapter.ViewHolder> {

        public AddressAdapter() {
        }

        @Override
        public AddressAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.address_item, parent, false);

            AddressAdapter.ViewHolder viewHolder =
                    new AddressAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(AddressAdapter.ViewHolder holder,
                                     int position) {
            Address offersModel = addressList.get(position);
            holder.offerSelect.setChecked(lastSelectedPosition == position);
            holder.txtAddressname.setText("" + offersModel.getType());
            holder.txtAddressful.setText("" + offersModel.getAdd1() + " " + offersModel.getAdd2() + " " + offersModel.getPincode() + " " + offersModel.getCity() + " " + offersModel.getState());

        }

        @Override
        public int getItemCount() {
            return addressList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.offer_select)
            RadioButton offerSelect;
            @BindView(R.id.txt_addressname)
            TextView txtAddressname;
            @BindView(R.id.txt_addressful)
            TextView txtAddressful;
            @BindView(R.id.txt_change)
            TextView txtChange;

            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);


                offerSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lastSelectedPosition = getAdapterPosition();
                        notifyDataSetChanged();

                    }
                });
                txtChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isRef = true;
                        startActivity(new Intent(AddressActivity.this, UpdateAddressActivity.class).putExtra("MyClass", addressList.get(getAdapterPosition())));
                    }
                });
            }
        }
    }
}
