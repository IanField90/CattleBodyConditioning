package com.ianfield.bodyscoring.managers;

import android.content.Context;

import com.ianfield.bodyscoring.models.Record;
import com.ianfield.bodyscoring.utils.DatabaseHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;

/**
 * Created by Ian on 14/01/2016.
 */
public class RecordManager {

    public static void createRecord(Context context, Record record) {
        DatabaseHelper helper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        try {
            helper.getRecordDao().create(record);
        } catch (SQLException ignore) { }
    }

}
