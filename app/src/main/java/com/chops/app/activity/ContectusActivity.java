package com.chops.app.activity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.chops.app.R;
import com.chops.app.utils.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.chops.app.utils.SessionManager.CONTACT_US;


public class ContectusActivity extends AppCompatActivity {
    @BindView(R.id.txt_contac)
    TextView txtContac;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contectus);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtContac.setText(Html.fromHtml(sessionManager.getStringData(CONTACT_US), Html.FROM_HTML_MODE_COMPACT));
        } else {
            txtContac.setText(Html.fromHtml(sessionManager.getStringData(CONTACT_US)));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
