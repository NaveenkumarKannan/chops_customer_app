package com.chops.app.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.chops.app.R;
import com.chops.app.adepter.ItemCategoryAdapter;
import com.chops.app.model.SearchData;
import com.chops.app.retrofit.APIClient;
import com.chops.app.retrofit.GetResult;
import com.chops.app.utils.GetService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class SearchActivity extends AppCompatActivity implements GetResult.MyListener {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.ed_search)
    EditText edSearch;
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.recyview_produc)
    RecyclerView recyviewProduc;

    ItemCategoryAdapter adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        recyviewProduc.setHasFixedSize(true);
        recyviewProduc.setLayoutManager(new GridLayoutManager(SearchActivity.this, 1));

    }

    @OnClick({R.id.img_back, R.id.img_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_search:
                if (!TextUtils.isEmpty(edSearch.getText())) {
                    searchData();
                }
                break;
            default:
                break;
        }
    }

    private void searchData() {
        GetService.showPrograss(SearchActivity.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", edSearch.getText().toString());
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getSearch((JsonObject) jsonParser.parse(jsonObject.toString()));
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
                SearchData searchData = gson.fromJson(result.toString(), SearchData.class);
                adapter1 = new ItemCategoryAdapter(searchData.getResultData(), SearchActivity.this);
                recyviewProduc.setAdapter(adapter1);
                Toast.makeText(SearchActivity.this, searchData.getResponseMsg(), Toast.LENGTH_LONG);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

