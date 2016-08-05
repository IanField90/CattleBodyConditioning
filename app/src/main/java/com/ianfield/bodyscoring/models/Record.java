package com.ianfield.bodyscoring.models;

import android.text.TextUtils;

import com.ianfield.bodyscoring.utils.Setting;

import java.util.Date;
import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ian Field on 09/01/2016.
 *
 * Store the scoring numbers for a herd, UK scale and NZ scale
 * 1. Drying off ( 7–8 weeks pre-calving)
 * 2. Pre-calving ( 3 weeks pre-calving)
 * 3. Pre-service
 */
public class Record extends RealmObject {
    @PrimaryKey
    String id;
    String name;
    Date plannedCalvingDate;
    Date scoringDate;
    String setting;
    RealmList<Score> scores;// = new RealmList<>();

    public Record() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getPlannedCalvingDate() {
        return plannedCalvingDate;
    }

    public void setPlannedCalvingDate(Date plannedCalvingDate) {
        this.plannedCalvingDate = plannedCalvingDate;
    }

    public Date getScoringDate() {
        return scoringDate;
    }

    public void setScoringDate(Date scoringDate) {
        this.scoringDate = scoringDate;
    }


    public @Setting.Country String getSetting() {
        return setting;
    }

    public void setSetting(@Setting.Country String setting) {
        this.setting = setting;
    }

    public boolean isValidRecord() {
        return (plannedCalvingDate != null && scoringDate != null && !TextUtils.isEmpty(name));
    }

    public void setScores(RealmList<Score> scores) {
        this.scores = scores;
    }

    public RealmList<Score> getScores() {
        return scores;
    }

//    public String toSummary() {
//        switch (setting) {
//            case UK:
//                return String.format("1: %d, 2: %d, 2.5: %d, 3: %d, 4: %d, 5: %d",
//                        ukOne, ukTwo, ukTwoFive, ukThree, ukFour, ukFive
//                );
//            case NZ:
//            return String.format("<=2: %d, 2.5: %d, 3: %d, 3.5: %d, 4: %d, 4.5: %d, 5: %d," +
//                        "5.5: %d, 6: %d, 6.5: %d, >=7: %d",
//                        nzLessThanOrEqualTwo, nzTwoFive, nzThree, nzThreeFive, nzFour, nzFourFive,
//                        nzFive, nzFiveFive, nzSix, nzSixFive, nzSevenOrMore
//            );
//        }
//        return null;
//    }
}
