package com.ianfield.bodyscoring.managers;

import com.ianfield.bodyscoring.models.Record;
import com.ianfield.bodyscoring.models.Score;
import com.ianfield.bodyscoring.utils.ScoreScale;
import com.ianfield.bodyscoring.utils.Setting;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Ian on 14/01/2016.
 */
public class RecordManager {

    public static Record createRecord(Record record) {
        double[] scoreScale = ScoreScale.UK_SCORE_SCALE;
        switch (record.getSetting()) {
            case Setting.NZ:
                record.setSetting(Setting.NZ);
                scoreScale = ScoreScale.NZ_SCORE_SCALE;
                break;
            case Setting.UK:
                record.setSetting(Setting.UK);
                scoreScale = ScoreScale.UK_SCORE_SCALE;
                break;
        }

        ArrayList<Score> scores = new ArrayList<>();

        for (double score : scoreScale) {
            scores.add(ScoreManager.createScore(score));
        }

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        record = realm.copyToRealm(record);
        for (Score score : scores) {
            realm.copyToRealm(score);
            record.getScores().add(score);
        }
        realm.commitTransaction();

        return record;
    }

    public static Record getRecordById(String id) {
        return Realm.getDefaultInstance()
                .where(Record.class)
                .equalTo("id", id)
                .findFirst();
    }

    public static RealmResults<Record> getAllRecords() {
        return Realm.getDefaultInstance()
                .where(Record.class)
                .findAll();
    }

    public static void deleteRecord(Record record) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        record.deleteFromRealm();
        realm.commitTransaction();
    }
}
