package com.chops.app.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;

import com.chops.app.R;
import com.chops.app.catmode.Cats;
import com.chops.app.catmode.Cplist;
import com.chops.app.model.UserData;
import com.chops.app.retrofit.APIClient;
import com.chops.app.retrofit.GetResult;
import com.chops.app.utils.GetService;
import com.chops.app.utils.SessionManager;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

import static com.chops.app.retrofit.APIClient.Base_URL;


public class ItemCategoryFragment extends Fragment implements GetResult.MyListener {

    @BindView(R.id.vpPager)
    ViewPager vpPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    SessionManager sessionManager;

    UserData userData;

    List<String> catlistList;
    public static List<Cplist> cplists = new ArrayList<>();
    int pogition = 0;

    public ItemCategoryFragment() {
    }

    public ItemCategoryFragment newInstance(int param1, String param2) {
        ItemCategoryFragment fragment = new ItemCategoryFragment();
        Bundle args = new Bundle();
        args.putInt("position", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(getActivity());
        userData = sessionManager.getUserDetails("");
        catlistList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_category, container, false);
        ButterKnife.bind(this, view);
        if (getArguments() != null) {
            pogition = getArguments().getInt("position");

        }
        Log.e("poprererer", "--" + pogition);
        getCategory();
        return view;
    }



    @Override
    public void callback(JsonObject result, String callNo) {
        GetService.close();
        if (callNo.equalsIgnoreCase("1")) {
            Gson gson = new Gson();
            catlistList = new ArrayList<>();
            cplists = new ArrayList<>();
            try {
                Cats cat = gson.fromJson(result.toString(), Cats.class);

                for (int i = 0; i < cat.getCplist().size(); i++) {
                    catlistList.add(cat.getCplist().get(i).getTitle());
                }

                cplists = cat.getCplist();
                MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getFragmentManager(), catlistList);
                vpPager.setAdapter(myPagerAdapter);
                tabLayout.setupWithViewPager(vpPager);


                for (int i = 0; i < cat.getCplist().size(); i++) {
                    LinearLayout customTab = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);
                    CircleImageView tabImage = customTab.findViewById(R.id.img_icon);
                    TextView tabTitle = customTab.findViewById(R.id.txt_titel);
                    tabTitle.setText(cat.getCplist().get(i).getTitle());
                    Glide.with(getContext()).load(Base_URL + "/" + cat.getCplist().get(i).getImg()).into(tabImage);
                    tabLayout.getTabAt(i).setCustomView(customTab);
                    tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorblack));

                }

                Handler handler1 = new Handler();
                handler1.postDelayed(() -> vpPager.setCurrentItem(pogition), 200);

                myPagerAdapter.notifyAll();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    public class MyPagerAdapter extends FragmentPagerAdapter {

        List<String> catlistList;

        public MyPagerAdapter(FragmentManager fragmentManager, List<String> catlistList) {
            super(fragmentManager);
            this.catlistList = new ArrayList<>();
            this.catlistList = catlistList;
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return catlistList.size();
        }

        @Override
        public Fragment getItem(int position) {
            Log.e("ppp", " " + position);
            return ItemItemFragment.newInstance(position, catlistList.get(position));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return catlistList.get(position);
        }
    }

    private void getCategory() {
        GetService.showPrograss(getActivity());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", userData.getId());

            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getCategory((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.callForLogin(call, "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        for (Fragment fragment : getFragmentManager().getFragments()) {
            try {
                getFragmentManager().beginTransaction().remove(fragment).commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
