package com.ianfield.bodyscoring.utils

import java.text.SimpleDateFormat
import java.util.Date

/**
 * Created by Ian on 09/01/2016.
 */
object DateUtils {
    fun dateToString(date: Date): String {
        val df = SimpleDateFormat.getDateInstance()
        return df.format(date)
    }
}
