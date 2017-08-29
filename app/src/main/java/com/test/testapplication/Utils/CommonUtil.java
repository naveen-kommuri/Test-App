package com.test.testapplication.Utils;

import android.util.Log;

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

}
