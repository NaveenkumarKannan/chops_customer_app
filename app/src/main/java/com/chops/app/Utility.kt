package com.chops.app

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Handler
import android.util.Log
import android.widget.Toast

class Utility {

    companion object {
        private var utils: Utility? = null
        var toast: Toast? = null

        fun makeText(context: Context, message: String?) {
            if (toast != null)
                toast!!.cancel()

            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
            toast!!.show()
        }
        fun log(message: String) {
            Log.e("Utility", "log: $message")
        }
        fun log(message: String, TAG: String) {
            Log.e(TAG, "log: $message")
        }
        private var exit = false

        fun onBack(activity: Activity) {
            if (exit) {
                activity.finish()
            } else {
                Toast.makeText(
                    activity, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT
                ).show()
                exit = true
                Handler().postDelayed({ exit = false }, 3 * 1000.toLong())
            }
        }

        fun onBack(activity: Activity, message: String?) {
            showDialogOK(activity, message,
                    DialogInterface.OnClickListener { dialog, which ->
                        when (which) {
                            DialogInterface.BUTTON_POSITIVE -> activity.finish()
                            DialogInterface.BUTTON_NEGATIVE -> {
                            }
                        }
                    })
        }
        fun showDialogOK(context: Context?, message: String?, okListener: DialogInterface.OnClickListener?) {
            AlertDialog.Builder(context)
                    .setTitle("Chops")
                    .setMessage(message)
                    .setPositiveButton("YES", okListener)
                    .setNegativeButton("NO", okListener)
                    .create()
                    .show()
        }
    }
}
