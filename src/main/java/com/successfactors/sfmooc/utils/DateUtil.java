package com.successfactors.sfmooc.utils;

import org.springframework.util.StringUtils;

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

    public static String formatDateToMinutes(String dateStr){
        if(!StringUtils.isEmpty(dateStr) && dateStr.length() >= 21){
            return dateStr.substring(0, dateStr.length() - 5);
        }
        return dateStr;
    }

    public static String formatToDate(String dateStr){
        if(!StringUtils.isEmpty(dateStr) && dateStr.length() >= 21){
            return dateStr.substring(0, 10);
        }
        return dateStr;
    }

    public static String formatDateToSecond(String dateStr){
        if(!StringUtils.isEmpty(dateStr) && dateStr.length() >= 21){
            return dateStr.substring(0, dateStr.length() - 2);
        }
        return dateStr;
    }

    public static void main(String... args){
        String dateStr = formatDate(new Date());
        System.out.println(dateStr);
    }
}
