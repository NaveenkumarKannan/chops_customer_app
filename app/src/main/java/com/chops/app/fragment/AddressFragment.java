package com.chops.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.chops.app.R;
import com.chops.app.activity.UpdateAddressActivity;
import com.chops.app.model.Address;
import com.chops.app.model.AddressData;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

import static com.chops.app.utils.GetService.isRef;
import static com.chops.app.utils.GetService.pieces_type;
import static com.chops.app.utils.GetService.skin_type;


public class AddressFragment extends Fragment implements GetResult.MyListener {

    @BindView(R.id.recycle_address)
    RecyclerView recycleAddress;
    @BindView(R.id.btn_addaddress)
    Button btnAddaddress;

    UserData user;
    SessionManager sessionManager;
    String pid;
    String quantity;
    String total;


    public AddressFragment() {
        // Required empty public constructor
    }

    public static AddressFragment newInstance(String param1, String param2) {
        AddressFragment fragment = new AddressFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pid = getArguments().getString("pid");
        quantity = getArguments().getString("quantity");
        total = getArguments().getString("total");
        skin_type = getArguments().getInt("skin_type");
        pieces_type = getArguments().getInt("pieces_type");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_address, container, false);
        ButterKnife.bind(this, view);
        user = new UserData();
        sessionManager = new SessionManager(getActivity());
        user = sessionManager.getUserDetails("");

        LinearLayoutManager recyclerLayoutManager = new LinearLayoutManager(getActivity());
        recycleAddress.setLayoutManager(recyclerLayoutManager);
        getAddress();

        return view;
    }

    @OnClick(R.id.btn_addaddress)
    public void onViewClicked() {
        isRef = false;
        startActivity(new Intent(getActivity(), UpdateAddressActivity.class));

    }


    public class SelectAdrsAdapter extends
            RecyclerView.Adapter<SelectAdrsAdapter.ViewHolder> {


        private List<Address> offersList;


        private int lastSelectedPosition = -1;

        public SelectAdrsAdapter(List<Address> offersListIn
                , Context ctx) {
            offersList = offersListIn;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.selectaddress_item, parent, false);

            ViewHolder viewHolder =
                    new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder,
                                     int position) {
            Address offersModel = offersList.get(position);
            holder.offerSelect.setChecked(lastSelectedPosition == position);
            holder.txtAddressful.setText("" + offersModel.getAdd1() + " " + offersModel.getAdd2() + " " + offersModel.getPincode() + " " + offersModel.getCity() + " " + offersModel.getState());
            holder.txtAddressname.setText("" + offersModel.getType());
        }

        @Override
        public int getItemCount() {
            return offersList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.offer_select)
            RadioButton offerSelect;
            @BindView(R.id.txt_addressname)
            TextView txtAddressname;
            @BindView(R.id.txt_addressful)
            TextView txtAddressful;


            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);

                offerSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lastSelectedPosition = getAdapterPosition();


                        SelectPaymentFragment fragment = new SelectPaymentFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("aid", offersList.get(getAdapterPosition()).getId());
                        bundle.putString("pid", pid);
                        bundle.putString("quantity", quantity);
                        bundle.putString("total", total);
                        bundle.putInt("skin_type", skin_type);
                        bundle.putInt("pieces_type", pieces_type);
                        fragment.setArguments(bundle);
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.home_frame, fragment).addToBackStack(null).commit();
                        notifyDataSetChanged();
                    }
                });
            }
        }
    }

    private void getAddress() {
        GetService.showPrograss(getActivity());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", user.getId());

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
    public void callback(JsonObject result, String callNo) {
        GetService.close();
        if (callNo.equalsIgnoreCase("1")) {
            Gson gson = new Gson();
            try {
                AddressData addressData = gson.fromJson(result.toString(), AddressData.class);
                if (addressData.getResult().equalsIgnoreCase("true")) {
                    SelectAdrsAdapter adapter = new
                            SelectAdrsAdapter(addressData.getResultData(), getActivity());
                    recycleAddress.setAdapter(adapter);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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
}
