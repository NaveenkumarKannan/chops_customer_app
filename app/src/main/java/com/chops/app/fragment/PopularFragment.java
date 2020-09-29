package com.chops.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.chops.app.R;
import com.chops.app.adepter.PopularAdapter;
import com.chops.app.model.Popular;
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
import retrofit2.Call;

import static com.chops.app.utils.SessionManager.ISCART;


public class PopularFragment extends Fragment implements GetResult.MyListener {


    @BindView(R.id.rey_category)
    RecyclerView reyCategory;
    SessionManager sessionManager;
    UserData userData;

    public PopularFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);
        sessionManager = new SessionManager(getActivity());
        userData = sessionManager.getUserDetails("");
        reyCategory.setHasFixedSize(true);
        reyCategory.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        getPopular();
        return view;
    }

    private void getPopular() {
        GetService.showPrograss(getActivity());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", userData.getId());

            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getPopular((JsonObject) jsonParser.parse(jsonObject.toString()));
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
            try {
                Gson gson = new Gson();
                Popular popular = gson.fromJson(result.toString(), Popular.class);
                PopularAdapter popularAdapter = new PopularAdapter(popular.getmProductlist(), getActivity());
                reyCategory.setAdapter(popularAdapter);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (ISCART) {
            ISCART = false;
            MyCartFragment fragment = new MyCartFragment();
            fragment(fragment);
        }
    }
    public void fragment(Fragment fragmentClass) {

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.home_frame, fragmentClass).addToBackStack(null).commit();


    }
}
