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
import static com.chops.app.utils.GetService.ISORDER;



public class CongratulactionActivity extends AppCompatActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.toolbar_top)
    Toolbar toolbarTop;
    @BindView(R.id.btn_signin)
    Button btnSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulaction);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.img_back, R.id.btn_signin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                startActivity(new Intent(CongratulactionActivity.this, HomeActivity.class));
                finish();
                break;
            case R.id.btn_signin:
                ISORDER=true;
                startActivity(new Intent(CongratulactionActivity.this, MyordersActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CongratulactionActivity.this, HomeActivity.class));
        finish();
    }
}
