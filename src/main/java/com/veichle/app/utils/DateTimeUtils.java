package com.veichle.app.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public interface DateTimeUtils {

    public static String convertToString(Date date){
        DateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
        String strDate = dateFormat.format(date);
        return  strDate;
    }



    public static Date convertToDate(String sdate) throws ParseException {
        Date date=new SimpleDateFormat("dd/MM/yyyy").parse(sdate);
        return date;
    }
}
