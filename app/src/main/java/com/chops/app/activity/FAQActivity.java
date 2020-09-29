package com.chops.app.activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.chops.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FAQActivity extends AppCompatActivity {

    @BindView(R.id.img_back)
    LinearLayout imgBack;
    @BindView(R.id.toolbar_top)
    Toolbar toolbarTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        ButterKnife.bind(this);
        setSupportActionBar(toolbarTop);
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }
}
