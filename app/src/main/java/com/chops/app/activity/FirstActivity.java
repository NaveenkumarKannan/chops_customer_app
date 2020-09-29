package com.chops.app.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.chops.app.R;
import com.chops.app.utils.GetService;
import com.chops.app.utils.SessionManager;

import static com.chops.app.utils.SessionManager.USERLOGIN;


public class FirstActivity extends AppCompatActivity {
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        sessionManager = new SessionManager(FirstActivity.this);

        if (!GetService.Isconnection(FirstActivity.this)) {
            new AlertDialog.Builder(FirstActivity.this)
                    .setTitle(getResources().getString(R.string.app_name))
                    .setMessage(getResources().getString(R.string.internet_error))
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();

        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Window w = getWindow();
                w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            }


            new Handler().postDelayed(() -> {
                // This method will be executed once the timer is over
                if (sessionManager.getBooleanData(USERLOGIN)) {
                    Intent i = new Intent(FirstActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(FirstActivity.this, LoginstartActivity.class);
                    startActivity(i);
                    finish();
                }

            }, 3000);
        }

    }
}
