package com.chops.app.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chops.app.R;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetService {
    public static double totalTemp;
    public static int skin_type;
    public static int pieces_type;
    public static boolean ISORDER=false;
    public static Dialog dialog;
    public static boolean isRef=false;
    public static boolean validatePhoneNumber(String phoneNo) {
        //validate phone numbers of format "1234567890"
        if (phoneNo.matches("\\d{10}")) return true;
            //validating phone number with -, . or spaces
        else if (phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) return true;
            //validating phone number with extension length from 3 to 5
        else if (phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) return true;
            //validating phone number where area code is in braces ()
        else if (phoneNo.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) return true;
            //return false if nothing matches the input
        else return false;

    }

    public static boolean EmailValidator(String email) {
        final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);

        Matcher matcher;


        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static void showPrograss(Context context) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.custome_loder);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        // set the custom dialog components - text, image and button
        ProgressBar progressBar = dialog.findViewById(R.id.img_loader);
        dialog.setCancelable(false);
        dialog.show();
    }

    public static void close() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public static void ToastMessege(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static boolean Isconnection(Context context) {
        ConnectivityManager ConnectionManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() == true) {

            return true;

        } else {
            return false;
        }
    }
}
