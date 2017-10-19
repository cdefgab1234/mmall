package com.mmall.until;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * Created by Administrator on 2017/10/12.
 */
public class DateTimeUntil {

    //jidaTime
    //String->Date
    //Date->String
    public static final String STANDRAD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static Date strToDate(String DateTimeStr,String formaStr){
        DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern(formaStr);
        DateTime dateTime = dateTimeFormat.parseDateTime(DateTimeStr);
        return dateTime.toDate();
    }

    public static String dateToStr(Date date,String formaStr){
        if (date==null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formaStr);
    }

    public static Date strToDate(String DateTimeStr){
        DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern(STANDRAD_FORMAT);
        DateTime dateTime = dateTimeFormat.parseDateTime(DateTimeStr);
        return dateTime.toDate();
    }

    public static String dateToStr(Date date){
        if (date==null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STANDRAD_FORMAT);
    }

    /*public static void main(String[] args) {
        System.out.println(dateToStr(new Date()));
        System.out.println(strToDate("2013-11-11 23:24:25"));
    }*/
}
