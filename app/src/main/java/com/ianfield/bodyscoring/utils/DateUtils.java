package com.ianfield.bodyscoring.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ian on 09/01/2016.
 */
public class DateUtils {
    public static String dateToString(Date date) {
        DateFormat df = SimpleDateFormat.getDateInstance();
        return df.format(date);
    }
}
