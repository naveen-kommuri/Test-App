package com.test.testapplication.Utils;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by NKommuri on 8/29/2017.
 */

public class CommonUtil {

    public static long getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTimeInMillis();
    }

    public static String getDate(long modified) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM yy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(modified);
        String format = simpleDateFormat.format(calendar.getTime());
        return format;
    }

    public static String getDate(String modified) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM yy");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Long.parseLong(modified));
            String format = simpleDateFormat.format(calendar.getTime());
            Log.e("Converted Date", format);
            return format;
        } catch (Exception e) {
            return modified;
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
