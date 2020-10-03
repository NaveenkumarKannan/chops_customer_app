package com.chops.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

import com.chops.app.activity.HomeActivity;
import com.chops.app.model.Productlist;

import static com.chops.app.activity.HomeActivity.homeActivity;
import static com.chops.app.activity.HomeActivity.lvlMycart;
import static com.chops.app.activity.HomeActivity.txtCount;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "mydatabase.db";
    public static final String TABLE_NAME = "items";

    public static final String ICOL_1 = "PID";
    public static final String ICOL_2 = "cid";
    public static final String ICOL_3 = "discount";
    public static final String ICOL_4 = "gross";
    public static final String ICOL_6 = "image";
    public static final String ICOL_7 = "name";
    public static final String ICOL_8 = "net";
    public static final String ICOL_9 = "pipack";
    public static final String ICOL_10 = "price";
    public static final String ICOL_11 = "sdesc";
    public static final String ICOL_12 = "status";
    public static final String ICOL_13 = "tax";
    public static final String ICOL_14 = "types";
    public static final String ICOL_15 = "Contity";
    public static final String ICOL_16 = "TotalPrice";
    public static final String ICOL_17 = "pieces_type";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, PID TEXT , cid TEXT ,discount TEXT , gross TEXT , image TEXT, name Text, net Text, pipack Text, price Text, sdesc Text, status Text, tax Text, types Text, Contity Text, TotalPrice Text, pieces_type Text )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(Productlist rModel) {
        if (getID(rModel.getId(), String.valueOf(rModel.getCid())) == -1) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(ICOL_1, rModel.getId());
            contentValues.put(ICOL_2, rModel.getCid());
            contentValues.put(ICOL_3, rModel.getDiscount());
            contentValues.put(ICOL_4, rModel.getGross());
            contentValues.put(ICOL_6, rModel.getImage());
            contentValues.put(ICOL_7, rModel.getName());
            contentValues.put(ICOL_8, rModel.getNet());
            contentValues.put(ICOL_9, rModel.getPipack());
            contentValues.put(ICOL_10, rModel.getPrice());
            contentValues.put(ICOL_11, rModel.getSdesc());
            contentValues.put(ICOL_12, rModel.getSdesc());
            contentValues.put(ICOL_14, rModel.getTypes());
            contentValues.put(ICOL_15, rModel.getContity());
            contentValues.put(ICOL_16, rModel.getTotalPrice());
            contentValues.put(ICOL_17, rModel.getPiecesType());
            long result = db.insert(TABLE_NAME, null, contentValues);

            if (result == -1) {
                return false;
            } else {
                Cursor resw = getAllData();
                txtCount.setText("" + resw.getCount());
                if (homeActivity.isView()) {
                    lvlMycart.setVisibility(View.VISIBLE);
                }

                return true;
            }
        } else {

            return updateData(rModel.getId(), String.valueOf(rModel.getCid()), String.valueOf(rModel.getContity()));
        }
    }

    public int getID(String pid, String cost) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query(TABLE_NAME, new String[]{"PID"}, "PID =? AND cid =? ", new String[]{pid, cost}, null, null, null, null);
        if (c.moveToFirst()) //if the row exist then return the id
            return c.getInt(c.getColumnIndex("PID"));
        return -1;
    }

    public int getCard(String pid, String cost) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query(TABLE_NAME, new String[]{"Contity"}, "PID =? AND cid =? ", new String[]{pid, cost}, null, null, null, null);
        if (c.moveToFirst()) { //if the row exist then return the id
            return c.getInt(c.getColumnIndex("Contity"));
        } else {
            return -1;

        }
    }


    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }


    public boolean updateData(String id, String cost, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ICOL_15, status);
        db.update(TABLE_NAME, contentValues, "PID = ? AND cid =?", new String[]{id, cost});
        Cursor res = getAllData();
        txtCount.setText("" + res.getCount());
        if (res.getCount() == 0) {
            lvlMycart.setVisibility(View.GONE);
        } else {
            HomeActivity activity = new HomeActivity();
            if (activity.isView()) {
                lvlMycart.setVisibility(View.VISIBLE);
            }
        }

        return true;

    }

    public void DeleteCard() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME);
        txtCount.setText("0");
        lvlMycart.setVisibility(View.GONE);

    }

    public Integer deleteRData(String id, String cost) {
        SQLiteDatabase db = this.getWritableDatabase();
        Integer a = db.delete(TABLE_NAME, "PID = ? AND cid =?", new String[]{id, cost});
        Cursor res = getAllData();
        txtCount.setText("" + res.getCount());
        if (res.getCount() == 0) {
            lvlMycart.setVisibility(View.GONE);
        } else {
            HomeActivity activity = new HomeActivity();
            if (activity.isView()) {
                lvlMycart.setVisibility(View.VISIBLE);
            }
        }
        return a;
    }
}