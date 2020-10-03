package com.chops.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.chops.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CancelActivity extends AppCompatActivity {

    @BindView(R.id.btnclose)
    ImageView btnclose;
    @BindView(R.id.toolbar_top)
    Toolbar toolbarTop;
    @BindView(R.id.btn_back)
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_order);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnclose, R.id.btn_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnclose:
                finish();
                break;
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
