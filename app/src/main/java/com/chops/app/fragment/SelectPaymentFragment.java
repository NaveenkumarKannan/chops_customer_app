package com.chops.app.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.chops.app.R;
import com.chops.app.Utility;
import com.chops.app.activity.CodActivity;
import com.chops.app.activity.CongratulactionActivity;
import com.chops.app.activity.RazerpayActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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
    @BindView(R.id.tvd_date)
    TextView tvdDate;
    String dDate;
    @BindView(R.id.btn_order_now)
    TextView btnOrderNow;
    @BindView(R.id.btn_schedule)
    TextView btnSchedule;
    @BindView(R.id.radioslot1)
    RadioButton radioslot1;
    @BindView(R.id.radioslot2)
    RadioButton radioslot2;
    @BindView(R.id.radioslot3)
    RadioButton radioslot3;
    @BindView(R.id.radioslot4)
    RadioButton radioslot4;
    @BindView(R.id.radioslot5)
    RadioButton radioslot5;
    @BindView(R.id.radioGroupslot)
    RadioGroup radioGroupslot;
    @BindView(R.id.lvl_order)
    LinearLayout lvlOrder;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog datePickerDialogCustomerDob;

    String aid;
    String pid;
    String quantity;
    String total;
    String pieces_type;


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
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        setDateTimeField();
        aid = bundle.getString("aid");
        pid = bundle.getString("pid");
        quantity = bundle.getString("quantity");
        total = bundle.getString("total");
        pieces_type = bundle.getString("pieces_type");

        Log.e("aid", "" + aid);
        Log.e("pid", "" + pid);
        Log.e("quantity", "" + quantity);
        Log.e("total", "" + total);
        Log.e("pieces_type", "" + pieces_type);

        return view;
    }

    private void setDateTimeField() {


        Calendar newCalendar = Calendar.getInstance();
        datePickerDialogCustomerDob = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tvdDate.setText(dateFormatter.format(newDate.getTime()));
                dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                dDate = dateFormatter.format(newDate.getTime());
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @OnClick(R.id.btn_countinue)
    public void onViewClicked() {
        if (dDate != null) {
            if (radioButton.isChecked()) {
                Intent intent = new Intent(getActivity(), CodActivity.class);
                intent.putExtra("aid", aid);
                intent.putExtra("pid", pid);
                intent.putExtra("quantity", quantity);
                intent.putExtra("total", total);
                intent.putExtra("pieces_type", pieces_type);
                intent.putExtra("dDate", dDate);
                startActivity(intent);
                getActivity().finish();

            } else if (radioButton2.isChecked()) {
                Intent intent = new Intent(getActivity(), RazerpayActivity.class);
                intent.putExtra("aid", aid);
                intent.putExtra("pid", pid);
                intent.putExtra("quantity", quantity);
                intent.putExtra("total", total);
                intent.putExtra("pieces_type", pieces_type);
                intent.putExtra("dDate", dDate);
                startActivity(intent);
                getActivity().finish();
            }
        } else {
            Utility.Companion.makeText(getContext(), "Select Delivery Date");
        }
    }

    @OnClick({R.id.tvd_date,R.id.btn_order_now,R.id.btn_schedule})
    public void onClick(View v) {
        if (v == tvdDate) {
            datePickerDialogCustomerDob.show();
        }
        switch (v.getId()) {
            case R.id.btn_order_now:
                Intent intent = new Intent(getActivity(), CongratulactionActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.btn_schedule:
                lvlOrder.setVisibility(View.VISIBLE);
                break;
        }
    }
}
