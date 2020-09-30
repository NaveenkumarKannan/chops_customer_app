package com.chops.app.activity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.chops.app.R;
import com.chops.app.database.DatabaseHelper;
import com.chops.app.model.Productlist;
import com.chops.app.utils.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.chops.app.retrofit.APIClient.Base_URL;
import static com.chops.app.utils.SessionManager.ISCART;


public class ItemDetailsActivity extends AppCompatActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_favrit)
    ImageView imgFavrit;
    @BindView(R.id.img_cart)
    ImageView imgCart;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_desc)
    TextView txtDesc;
    @BindView(R.id.txt_net)
    TextView txtNet;
    @BindView(R.id.txt_gross)
    TextView txtGross;
    @BindView(R.id.txt_pic_titel)
    TextView txtPicTitel;
    @BindView(R.id.txt_pic)
    TextView txtPic;
    @BindView(R.id.img_minus)
    ImageView imgMinus;
    @BindView(R.id.txt_count)
    TextView txtCount;
    @BindView(R.id.txt_tcount)
    TextView txtTcount;
    @BindView(R.id.img_add)
    ImageView imgAdd;
    @BindView(R.id.txt_price)
    TextView txtPrice;


    @BindView(R.id.btn_addtocart)
    Button btnAddtocart;
    Productlist produc;
    @BindView(R.id.imgP)
    ImageView imgP;

    @BindView((R.id.rgSkin))
    RadioGroup rgSkin;
    @BindView((R.id.rgPiece))
    RadioGroup rgPiece;
    @BindView(R.id.rbTrue)
    RadioButton rbTrue;
    @BindView(R.id.rbFalse)
    RadioButton rbFalse;
    @BindView(R.id.rbPieceSmall)
    RadioButton rbPieceSmall;
    @BindView(R.id.rbPieceMedium)
    RadioButton rbPieceMedium;
    @BindView(R.id.rbPieceLarge)
    RadioButton rbPieceLarge;
    int skin = 0;
    int pieces = 0;

    DatabaseHelper helper;
    final int[] count = {0};
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        ButterKnife.bind(this);
        helper = new DatabaseHelper(ItemDetailsActivity.this);
        produc = (Productlist) getIntent().getSerializableExtra("MyClass");
        sessionManager = new SessionManager(this);

        rgSkin.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rbTrue:
                        skin = 1;
                        break;
                    case R.id.rbFalse:
                        skin = 0;
                        break;
                }
            }
        });
        rgPiece.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rbPieceSmall:
                        pieces = 2;
                        break;
                    case R.id.rbPieceMedium:
                        pieces = 1;
                        break;
                    case R.id.rbPieceLarge:
                        pieces = 0;
                        break;
                }
            }
        });
        /*if(produc.getSkin() == 1){
            rbTrue.setChecked(true);
            rbFalse.setChecked(false);
        }else {
            rbTrue.setChecked(false);
            rbFalse.setChecked(true);
        }*/
        Glide.with(ItemDetailsActivity.this).load(Base_URL + "/" + produc.getImage()).into(imgP);
        txtTitle.setText("" + produc.getName());
        txtDesc.setText("" + produc.getSdesc());

        txtGross.setText("" + produc.getGross());
        txtPicTitel.setText("" + produc.getTypes());
        txtPic.setText("" + produc.getPipack());
        txtPrice.setText(sessionManager.getStringData(SessionManager.CURRNCY) + " " + produc.getPrice());
        txtNet.setText("" + produc.getNet());


        int qrt = helper.getCard(produc.getId(), produc.getCid());
        if (qrt != -1) {
            count[0] = qrt;
            txtCount.setText("" + count[0]);
            txtCount.setVisibility(View.VISIBLE);
        } else {
            txtCount.setText("0");
        }
        Cursor resw = helper.getAllData();
        txtTcount.setText("" + resw.getCount());

        double total1 = count[0] * Double.parseDouble(produc.getPrice());

        btnAddtocart.setText("Proceed to Pay " + sessionManager.getStringData(SessionManager.CURRNCY) + " " + total1);
    }

    @OnClick({R.id.img_back, R.id.img_favrit, R.id.img_cart, R.id.img_minus, R.id.img_add, R.id.btn_addtocart,R.id.txt_net})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_favrit:
                break;
            case R.id.img_cart:
                fragment();
                break;
            case R.id.img_minus:
                count[0] = Integer.parseInt(txtCount.getText().toString());
                count[0] = count[0] - 1;
                if (count[0] <= 0) {
                    txtCount.setText("0");
                    helper.deleteRData(produc.getId(), String.valueOf(produc.getCid()));
                } else {
                    txtCount.setVisibility(View.VISIBLE);
                    txtCount.setText("" + count[0]);
                    produc.setContity(count[0]);
                    Log.e("INsert", "--> " + helper.insertData(produc));
                }
                Cursor resw = helper.getAllData();
                txtTcount.setText("" + resw.getCount());
                double total = count[0] * Double.parseDouble(produc.getPrice());

                if (total >= 0) {
                    btnAddtocart.setText("Proceed to Pay " + sessionManager.getStringData(SessionManager.CURRNCY) + total);
                }
                break;
            case R.id.img_add:
                txtCount.setVisibility(View.VISIBLE);
                imgMinus.setVisibility(View.VISIBLE);
                count[0] = Integer.parseInt(txtCount.getText().toString());

                count[0] = count[0] + 1;
                txtCount.setText("" + count[0]);
                produc.setContity(count[0]);
                Log.e("INsert", "--> " + helper.insertData(produc));
                Cursor resws = helper.getAllData();
                txtTcount.setText("" + resws.getCount());
                double total1 = count[0] * Double.parseDouble(produc.getPrice());

                btnAddtocart.setText("Proceed to Pay  " + sessionManager.getStringData(SessionManager.CURRNCY) + total1);
                break;
            case R.id.btn_addtocart:
                fragment();
                break;
            default:
                break;
        }
    }

    public void fragment() {
        ISCART = true;
        finish();

    }
}
