package com.example.new_meditation;

import android.content.Context;

import java.text.DecimalFormat;

public class Utils {
    public static String TAG_HOME = "Home";
    public static String CURRENT_TAG = TAG_HOME;

    public static String getETAString(Context context, long j) {
        if (j < 0) {
            return "";
        }
        int i = (int) (j / 1000);
        long j2 = (long) (i / 3600);
        int i2 = (int) (((long) i) - (3600 * j2));
        long j3 = (long) (i2 / 60);
        int i3 = (int) (((long) i2) - (60 * j3));
        if (j2 > 0) {
            return context.getString(R.string.download_eta_hrs, new Object[]{Long.valueOf(j2), Long.valueOf(j3), Integer.valueOf(i3)});
        } else if (j3 > 0) {
            return context.getString(R.string.download_eta_min, new Object[]{Long.valueOf(j3), Integer.valueOf(i3)});
        } else {
            return context.getString(R.string.download_eta_sec, new Object[]{Integer.valueOf(i3)});
        }
    }

    public static String getDownloadSpeedString(Context context, long j) {
        if (j < 0) {
            return "";
        }
        double d = (double) j;
        Double.isNaN(d);
        double d2 = d / 1000.0d;
        double d3 = d2 / 1000.0d;
        DecimalFormat decimalFormat = new DecimalFormat(".##");
        if (d3 >= 1.0d) {
            return context.getString(R.string.download_speed_mb, new Object[]{decimalFormat.format(d3)});
        } else if (d2 >= 1.0d) {
            return context.getString(R.string.download_speed_kb, new Object[]{decimalFormat.format(d2)});
        } else {
            return context.getString(R.string.download_speed_bytes, new Object[]{Long.valueOf(j)});
        }
    }
}
