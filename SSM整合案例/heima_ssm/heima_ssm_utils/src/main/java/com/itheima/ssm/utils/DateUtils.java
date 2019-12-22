package com.itheima.ssm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    //日期转字符串
    public static String date2String(Date date, String pattern) {
        //根据转换的字符串格式转换
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String format = sdf.format(date);

        return format;

    }

    //字符串转日期
    public static Date string2Date(String date, String pattern) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        Date parse = sdf.parse(date);

        return parse;

    }
}
