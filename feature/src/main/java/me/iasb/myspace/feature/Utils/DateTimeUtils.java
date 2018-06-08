package me.iasb.myspace.feature.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class DateTimeUtils {

    public static int FORWARD_SLASH = 0;

    public static String DateString(int day, int month, int year, int type) {
        // type is used to format string
        if (type == FORWARD_SLASH) {
            return day + "/" + month + "/" + year;
        }
        return day + "/" + month + "/" + year;
    }

    public static int getDaysDiff(String yourDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        int date = 0;
        Calendar c = Calendar.getInstance();
        try {
            Long fromDate = simpleDateFormat.parse("" + yourDate).getTime();
            Long toDate = simpleDateFormat.parse(simpleDateFormat.format(c.getTime())).getTime();
            date = (int) TimeUnit.DAYS.convert(toDate - fromDate, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            // log exception
        }
        return date;
    }
}
