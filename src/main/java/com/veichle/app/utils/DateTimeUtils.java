package com.veichle.app.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public interface DateTimeUtils {
    DateFormat DF_DDMMYYYY = new SimpleDateFormat("dd-mm-yyyy");
    DateFormat DF_DDMMYYYY_HH = new SimpleDateFormat("dd-mm-yyyy HH");
    DateFormat DF_DDMMYYYY_HHMM = new SimpleDateFormat("dd-mm-yyyy HH:mm");
    DateFormat DF_DDMMYYYY_HHMMSS = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss");


    public static String convertToString(Date date){
        String strDate = DF_DDMMYYYY_HHMMSS.format(date);
        return  strDate;
    }

    public static Date convertToDate(String dataString) throws ParseException {
        Date date=DF_DDMMYYYY.parse(dataString);
        return date;
    }
}
