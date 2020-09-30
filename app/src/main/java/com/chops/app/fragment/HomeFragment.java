package com.chops.app.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;


import com.chops.app.R;
import com.chops.app.activity.HomeActivity;
import com.chops.app.adepter.BannerAdapter;
import com.chops.app.adepter.CategoryAdapter;
import com.chops.app.adepter.PopularHomeAdapter;
import com.chops.app.adepter.TestimonialAdapter;
import com.chops.app.model.Home;
import com.chops.app.model.UserData;
import com.chops.app.retrofit.APIClient;
import com.chops.app.retrofit.GetResult;
import com.chops.app.utils.AutoScrollViewPager;
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

import static com.chops.app.utils.SessionManager.ABOUT_US;
import static com.chops.app.utils.SessionManager.CONTACT_US;
import static com.chops.app.utils.SessionManager.CURRNCY;
import static com.chops.app.utils.SessionManager.ISCART;
import static com.chops.app.utils.SessionManager.ODERMINIMUM;
import static com.chops.app.utils.SessionManager.PRIVACY;
import static com.chops.app.utils.SessionManager.RAZORPAYKEY;
import static com.chops.app.utils.SessionManager.TREMSCODITION;


public class HomeFragment extends Fragment implements GetResult.MyListener {
    @BindView(R.id.viewpager)
    AutoScrollViewPager viewpager;


    @BindView(R.id.rey_category)
    RecyclerView reyCategory;

    @BindView(R.id.lvl_banner)
    LinearLayout lvlBanner;


    @BindView(R.id.rey_popular)
    RecyclerView reyPopular;
    @BindView(R.id.txt_category)
    TextView txtCategory;
    @BindView(R.id.txt_popular)
    TextView txtPopular;

    SessionManager sessionManager;
    UserData userData;
    @BindView(R.id.testimonation)
    AutoScrollViewPager testimonation;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void setHight() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int width = (display.getWidth());
        double height = (display.getHeight());
        height=height/2.6;
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width, (int)height);
        lvlBanner.setLayoutParams(parms);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        sessionManager = new SessionManager(getActivity());
        userData = new UserData();
        userData = sessionManager.getUserDetails("");

        viewpager.startAutoScroll();
        viewpager.setInterval(4000);
        viewpager.setCycle(true);
        viewpager.setStopScrollWhenTouch(true);


        reyCategory.setHasFixedSize(true);
        reyCategory.setLayoutManager(new GridLayoutManager(getActivity(), 3));


        reyPopular.setHasFixedSize(true);
        reyPopular.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        setHight();
        getDashboard();

        return view;
    }

    @OnClick({R.id.txt_category, R.id.txt_popular})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_category:
                CategoryFragment fragment = new CategoryFragment();
                fragment(fragment);
                break;
            case R.id.txt_popular:
                PopularFragment fragment1 = new PopularFragment();
                fragment(fragment1);
                break;
            default:
                break;
        }
    }

    public void fragment(Fragment fragmentClass) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.home_frame, fragmentClass).addToBackStack(null).commit();
    }

    private void getDashboard() {
        GetService.showPrograss(getActivity());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", userData.getId());
            JsonParser jsonParser = new JsonParser();
            Log.e("HomeFragment", "->> " + userData.getId().toString());
            JsonObject jsonObject1 = (JsonObject) jsonParser.parse(jsonObject.toString());
            Log.e("HomeFragment", "JsonObject ->> " + new Gson().toJson(jsonObject1));
            Call<JsonObject> call = APIClient.getInterface().getHome(jsonObject1);
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.callForLogin(call, "1");
        } catch (JSONException e) {
            Log.e("Error home111", "->> " + e.toString());
            e.printStackTrace();
            GetService.close();
        }
    }

    @Override
    public void callback(JsonObject result, String callNo) {
        GetService.close();
        if (callNo.equalsIgnoreCase("1")) {
            try {
                Gson gson = new Gson();
                Home home = gson.fromJson(result.toString(), Home.class);

                PagerAdapter adapter = new BannerAdapter(getActivity(), home.getHomeList().getBanner());
                viewpager.setAdapter(adapter);

                CategoryAdapter adapter1 = new CategoryAdapter(home.getHomeList().getCatlist(), getActivity());
                reyCategory.setAdapter(adapter1);

                PopularHomeAdapter popularAdapter = new PopularHomeAdapter(home.getHomeList().getProductlist(), getActivity());
                reyPopular.setAdapter(popularAdapter);

                PagerAdapter adapter2 = new TestimonialAdapter(getActivity(), home.getHomeList().getTestimonial());
                testimonation.setAdapter(adapter2);

                HomeActivity.notificationCount(home.getHomeList().getRemainNotification());
                sessionManager.setStringData(CURRNCY, home.getHomeList().getMainData().getCurrency());
                sessionManager.setIntData(ODERMINIMUM, home.getHomeList().getMainData().getoMin());
                sessionManager.setStringData(RAZORPAYKEY, home.getHomeList().getMainData().getRaz_key());
                sessionManager.setStringData(PRIVACY, home.getHomeList().getMainData().getPrivacy_policy());
                sessionManager.setStringData(ABOUT_US, home.getHomeList().getMainData().getAbout_us());
                sessionManager.setStringData(CONTACT_US, home.getHomeList().getMainData().getContact_us());
                sessionManager.setStringData(TREMSCODITION, home.getHomeList().getMainData().getTerms());

            } catch (Exception e) {
                Log.e("Error home", "->> " + e.toString());
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

}
