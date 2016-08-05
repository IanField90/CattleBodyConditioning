package com.ianfield.bodyscoring.managers;

import com.ianfield.bodyscoring.models.Score;

import io.realm.Realm;

/**
 * Created by Ian Field on 14/01/2016.
 */
public class ScoreManager {

    private static final String TAG = "ScoreManager";

    public static Score createScore(double score) {
        Score scoreRecord = new Score();
        scoreRecord.setScore(score);
        scoreRecord.setCount(0);
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(scoreRecord);
        realm.commitTransaction();
        return scoreRecord;
    }
}
