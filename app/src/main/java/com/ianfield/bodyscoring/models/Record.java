package com.ianfield.bodyscoring.models;

import android.text.TextUtils;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ian Field on 09/01/2016.
 *
 * Store the scoring numbers for a herd, UK scale and NZ scale
 * 1. Drying off ( 7–8 weeks pre-calving)
 * 2. Pre-calving ( 3 weeks pre-calving)
 * 3. Pre-service
 */
@DatabaseTable(tableName = "records")
public class Record {
    public enum Setting { UK, NZ }

    @DatabaseField(generatedId = true) int id;

    @DatabaseField String name;
    @DatabaseField Date plannedCalvingDate;
    @DatabaseField Date scoringDate;
    @DatabaseField Setting setting = Setting.UK;

    @ForeignCollectionField(eager = true) private ForeignCollection<Score> scores;

    public int getId() {
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


    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    public boolean isValid() {
        return (plannedCalvingDate != null && scoringDate != null && !TextUtils.isEmpty(name));
    }

    public ArrayList<Score> getScores() {
        return new ArrayList<>(this.scores);
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
