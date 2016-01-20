package com.ianfield.bodyscoring.managers;

import android.content.Context;

import com.ianfield.bodyscoring.models.Record;
import com.ianfield.bodyscoring.utils.DatabaseHelper;
import com.ianfield.bodyscoring.utils.ScoreScale;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Ian on 14/01/2016.
 */
public class RecordManager {

    public static Record createRecord(Context context, Record record) {
        DatabaseHelper helper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        try {
            helper.getRecordDao().create(record);

            // TODO possibly perform this in a batch loop for performance
            double[] scoreScale;
            switch (record.getSetting()) {
                case NZ:
                    scoreScale = ScoreScale.NZ_SCORE_SCALE;
                    break;
                case UK:
                    scoreScale = ScoreScale.UK_SCORE_SCALE;
                    break;

                default:
                    scoreScale = ScoreScale.UK_SCORE_SCALE;
            }

            for (double score : scoreScale) {
                ScoreManager.createScore(context, record, score);
            }

            helper.getRecordDao().refresh(record);
            return record;
        } catch (SQLException ignore) { }
        return null;
    }

    public static Record getRecordById(Context context, int id) {
        DatabaseHelper helper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        try {
            return helper.getRecordDao().queryForId(id);
        } catch (SQLException ignore) { }
        return null;
    }

    public static ArrayList<Record> getAllRecords(Context context) {
        DatabaseHelper helper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        try {
            return new ArrayList<>(helper.getRecordDao().queryForAll());
        } catch (SQLException ignore) { }
        return null;
    }

}
