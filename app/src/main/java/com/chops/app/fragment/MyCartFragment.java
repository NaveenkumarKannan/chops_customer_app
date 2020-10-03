package com.chops.app.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.chops.app.R;
import com.chops.app.adepter.MycartAdapter;
import com.chops.app.database.DatabaseHelper;
import com.chops.app.model.Productlist;
import com.chops.app.utils.GetService;
import com.chops.app.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.chops.app.utils.SessionManager.CURRNCY;
import static com.chops.app.utils.SessionManager.ODERMINIMUM;


public class MyCartFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recyview_cart)
    RecyclerView recyviewCart;


    public static TextView txtTotals, txtNoitem;


    @BindView(R.id.btn_procedchackout)
    TextView btnProcedchackout;

    // TODO: Rename and change types of parameters


    DatabaseHelper databaseHelper;
    MycartAdapter adapter1;
    @BindView(R.id.lvl_data)
    LinearLayout lvlData;
    @BindView(R.id.lvl_nodata)
    LinearLayout lvlNodata;

    public MyCartFragment() {
        // Required empty public constructor
    }

    List<Productlist> mycartList = new ArrayList<>();
    SessionManager sessionManager;

    public static MyCartFragment newInstance(String param1, String param2) {
        MyCartFragment fragment = new MyCartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);
        ButterKnife.bind(this, view);
        databaseHelper = new DatabaseHelper(getActivity());
        sessionManager = new SessionManager(getActivity());
        txtTotals = view.findViewById(R.id.totleAmount);


        txtNoitem = view.findViewById(R.id.txt_noitem);

        recyviewCart.setHasFixedSize(true);
        recyviewCart.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        Allcartget();
        return view;
    }

    public void Allcartget() {
        Cursor res = databaseHelper.getAllData();
        mycartList = new ArrayList<>();
        if (res.getCount() != 0) {
            lvlData.setVisibility(View.VISIBLE);
            lvlNodata.setVisibility(View.GONE);
            while (res.moveToNext()) {
                Productlist rModel = new Productlist();
                rModel.setId(res.getString(1));
                rModel.setCid(res.getString(2));
                rModel.setDiscount(res.getString(3));
                rModel.setGross(res.getString(4));
                rModel.setImage(res.getString(5));
                rModel.setName(res.getString(6));
                rModel.setNet(res.getString(7));
                rModel.setPipack(res.getString(8));
                rModel.setPrice(res.getString(9));
                rModel.setSdesc(res.getString(10));
                rModel.setSdesc(res.getString(11));
                rModel.setTypes(res.getString(13));
                rModel.setContity(res.getInt(14));
                rModel.setTotalPrice(res.getInt(15));
                rModel.setPiecesType(res.getString(16));
                mycartList.add(rModel);
            }
            Log.e("size", " : " + mycartList.size());
            total();
            txtNoitem.setText(mycartList.size() + " items Added");
            adapter1 = new MycartAdapter(mycartList, getActivity());
            recyviewCart.setAdapter(adapter1);
        } else {
            lvlData.setVisibility(View.GONE);
            lvlNodata.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.btn_procedchackout)
    public void onViewClicked() {
        String quantity = "";
        String pid = "";
        String pieces_type = "";
        if (mycartList.size() != 0) {
            if (sessionManager.getIntData(ODERMINIMUM) <= GetService.totalTemp) {
                for (int i = 0; i < mycartList.size(); i++) {
                    if (i == 0) {
                        pid = pid + mycartList.get(i).getId();
                        pieces_type = pieces_type + mycartList.get(i).getPiecesType();
                        quantity = quantity + String.valueOf(mycartList.get(i).getContity());
                    } else {
                        pid = pid + "," + mycartList.get(i).getId();
                        pieces_type = pieces_type + "," + mycartList.get(i).getPiecesType();
                        quantity = quantity + "," + String.valueOf(mycartList.get(i).getContity());
                    }
                }

                Log.e("pid", "" + pid);
                Log.e("pieces_type", "" + pieces_type);
                Log.e("quantity", "" + quantity);

                AddressFragment fragment = new AddressFragment();
                Bundle bundle = new Bundle();
                bundle.putString("pid", pid);
                bundle.putString("quantity", quantity);
                bundle.putString("total", "" + GetService.totalTemp);
                bundle.putString("pieces_type", pieces_type);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.home_frame, fragment).addToBackStack(null).commit();
            } else {

                Toast.makeText(getActivity(), "Minimum order value of " + sessionManager.getStringData(CURRNCY) + " " + sessionManager.getIntData(ODERMINIMUM), Toast.LENGTH_SHORT).show();

            }
        }


    }

    public void total() {
        double total = 0;
        for (int i = 0; i < mycartList.size(); i++) {

            Productlist productlist = mycartList.get(i);
            double temp = Double.parseDouble(productlist.getPrice()) * productlist.getContity();
            total = total + temp;

        }

        GetService.totalTemp = total;
        txtTotals.setText(sessionManager.getStringData(CURRNCY) + " " + total);
        txtNoitem.setText(mycartList.size() + " items Added");

    }


}
