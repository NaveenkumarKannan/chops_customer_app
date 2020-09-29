package com.chops.app.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.chops.app.R;
import com.chops.app.adepter.ItemCategoryAdapter;
import com.chops.app.database.DatabaseHelper;
import com.chops.app.model.Productlist;
import com.chops.app.model.UserData;
import com.chops.app.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.chops.app.activity.HomeActivity.lvlMycart;
import static com.chops.app.utils.SessionManager.ISCART;


public class ItemItemFragment extends Fragment {
    @BindView(R.id.recyview_sub)
    RecyclerView recyviewSub;
    @BindView(R.id.btn_gotocart)
    TextView btnGotocart;

    private int mPosition;
    private String CID;

    DatabaseHelper databaseHelper;
    SessionManager sessionManager;
    UserData userData;

    @BindView(R.id.txt_empty)
    TextView txtEmpty;


    public ItemItemFragment() {
        // Required empty public constructor
    }

    List<Productlist> productlists = new ArrayList<>();
    ItemCategoryAdapter adapter1;

    public static ItemItemFragment newInstance(int param1, String param2) {
        ItemItemFragment fragment = new ItemItemFragment();
        Bundle args = new Bundle();
        args.putInt("position", param1);
        args.putString("cid", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPosition = getArguments().getInt("position");
            CID = getArguments().getString("cid");


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sub_item, container, false);
        ButterKnife.bind(this, view);
        databaseHelper = new DatabaseHelper(getActivity());
        sessionManager = new SessionManager(getActivity());
        userData = sessionManager.getUserDetails("");

        recyviewSub.setHasFixedSize(true);
        recyviewSub.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        Log.e("mPosition", "  " + mPosition);
        Log.e("CID", "  " + CID);
        productlists = ItemCategoryFragment.cplists.get(mPosition).getProductlist();
        adapter1 = new ItemCategoryAdapter(productlists, getActivity());
        recyviewSub.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();
        if (productlists.size() != 0) {
            txtEmpty.setVisibility(View.GONE);
        } else {
            txtEmpty.setVisibility(View.VISIBLE);
        }
        Cursor qrt = databaseHelper.getAllData();
        if (qrt.getCount() == 0) {
            lvlMycart.setVisibility(View.GONE);
        } else {
            lvlMycart.setVisibility(View.VISIBLE);

        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter1 != null)
            adapter1.notifyDataSetChanged();
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

    @OnClick(R.id.btn_gotocart)
    public void onViewClicked() {
        MyCartFragment fragment = new MyCartFragment();
        fragment(fragment);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        lvlMycart.setVisibility(View.GONE);
    }


}
