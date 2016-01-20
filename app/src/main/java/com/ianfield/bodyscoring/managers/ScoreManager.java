package com.ianfield.bodyscoring.managers;

import android.content.Context;

import com.ianfield.bodyscoring.models.Record;
import com.ianfield.bodyscoring.models.Score;
import com.ianfield.bodyscoring.utils.DatabaseHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;

/**
 * Created by Ian Field on 14/01/2016.
 */
public class ScoreManager {

    public static Score createScore(Context context, Record record, double score) {
        DatabaseHelper helper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        try {
            Score scoreRecord = new Score();
            scoreRecord.setRecord(record);
            scoreRecord.setScore(score);
            scoreRecord.setCount(0);
            helper.getScoreDao().create(scoreRecord);
            return scoreRecord;
        } catch (SQLException ignore) { }
        return null;
    }
}
