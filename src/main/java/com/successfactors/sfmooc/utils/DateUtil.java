package com.successfactors.sfmooc.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String formatDate(Date date){
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static String formatDateTime(Date date){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static String formatDateToMinutes(Date date){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
    }

    public static void main(String... args){
        String dateStr = formatDate(new Date());
        System.out.println(dateStr);
    }
}
