package ru.emiren.infosystemdepartment.Util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    public static Date stringToDate(String str){
        String[] dateFormats = {"dd/MM/yyyy", "dd-MM-yyyy", "MM/dd/yyyy"};
        for (String format : dateFormats) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
            dateFormat.setLenient(false);
            try {
                return dateFormat.parse(str);
            } catch (ParseException e) {
                // Continue to the next format
            }
        }

        return new Date();
    }

    public static String getYear(Date date) {
        if (date == null) {
            Calendar cal = Calendar.getInstance();
            return String.valueOf(cal.get(Calendar.YEAR));
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return String.valueOf(cal.get(Calendar.YEAR));
    }

    public static String getMonth(Date date) {
        if (date == null) {
            Calendar cal = Calendar.getInstance();
            return String.valueOf(cal.get(Calendar.MONTH));
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return String.valueOf(cal.get(Calendar.MONTH));
    }

    public static String getDay(Date date) {
        if (date == null) {
            Calendar cal = Calendar.getInstance();
            return String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
    }


    public static String dateToString(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        if (date instanceof Date){
            return dateFormat.format(date);
        }
        return dateFormat.format(new Date());
    }

}
