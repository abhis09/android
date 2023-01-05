package com.example.new_meditation;

public class Utilities {
    public String milliSecondsToTimer(long j) {
        String str;
        String str2 = "";
        int i = (int) (j / 3600000);
        long j2 = j % 3600000;
        int i2 = ((int) j2) / 60000;
        int i3 = (int) ((j2 % 60000) / 1000);
        if (i > 0) {
            str2 = i + ":";
        }
        if (i3 < 10) {
            str = "0" + i3;
        } else {
            str = "" + i3;
        }
        return str2 + i2 + ":" + str;
    }

    public String milliSecondsToTimerText(long j) {
        String str;
        String str2 = "";
        int i = (int) (j / 3600000);
        long j2 = j % 3600000;
        int i2 = ((int) j2) / 60000;
        int i3 = (int) ((j2 % 60000) / 1000);
        if (i > 0) {
            str2 = i + ":";
        }
        if (i3 < 10) {
            str = "0" + i3;
        } else {
            str = "" + i3;
        }
        return str2 + i2 + "min ," + str + "sec";
    }

    public int getProgressPercentage(long j, long j2) {
        Double.valueOf(0.0d);
        double d = (double) ((long) ((int) (j / 1000)));
        double d2 = (double) ((long) ((int) (j2 / 1000)));
        Double.isNaN(d);
        Double.isNaN(d2);
        return Double.valueOf((d / d2) * 100.0d).intValue();
    }

    public int progressToTimer(int i, int i2) {
        double d = (double) i;
        Double.isNaN(d);
        double d2 = (double) (i2 / 1000);
        Double.isNaN(d2);
        return ((int) ((d / 100.0d) * d2)) * 1000;
    }
}
