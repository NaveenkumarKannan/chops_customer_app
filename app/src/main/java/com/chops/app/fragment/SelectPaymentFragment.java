package com.chops.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


import com.chops.app.R;
import com.chops.app.activity.CodActivity;
import com.chops.app.activity.RazerpayActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SelectPaymentFragment extends Fragment {


    @BindView(R.id.radioButton)
    RadioButton radioButton;
    @BindView(R.id.radioButton2)
    RadioButton radioButton2;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.btn_countinue)
    TextView btnCountinue;

    String aid;
    String pid;
    String quantity;
    String total;


    public SelectPaymentFragment() {
        // Required empty public constructor
    }

    public static SelectPaymentFragment newInstance(String param1, String param2) {
        SelectPaymentFragment fragment = new SelectPaymentFragment();

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
        View view = inflater.inflate(R.layout.fragment_select_payment, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        aid = bundle.getString("aid");
        pid = bundle.getString("pid");
        quantity = bundle.getString("quantity");
        total = bundle.getString("total");

        Log.e("aid",""+aid);
        Log.e("pid",""+pid);
        Log.e("quantity",""+quantity);
        Log.e("total",""+total);

        return view;
    }

    @OnClick(R.id.btn_countinue)
    public void onViewClicked() {
        if (radioButton.isChecked()) {
            Intent intent=new Intent(getActivity(), CodActivity.class);
            intent.putExtra("aid",aid);
            intent.putExtra("pid",pid);
            intent.putExtra("quantity",quantity);
            intent.putExtra("total",total);
            startActivity(intent);
            getActivity().finish();

        } else if (radioButton2.isChecked()) {
            Intent intent=new Intent(getActivity(), RazerpayActivity.class);
            intent.putExtra("aid",aid);
            intent.putExtra("pid",pid);
            intent.putExtra("quantity",quantity);
            intent.putExtra("total",total);
            startActivity(intent);
            getActivity().finish();
        }
    }
}
