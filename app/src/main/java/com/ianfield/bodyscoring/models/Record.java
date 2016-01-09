package com.ianfield.bodyscoring.models;

import android.text.TextUtils;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

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
    private enum Setting { UK, NZ }

    @DatabaseField String name;
    @DatabaseField Date plannedCalvingDate;
    @DatabaseField Date scoringDate;
    @DatabaseField Setting setting = Setting.UK;
    // UK values: 1, 2, 2.5, 3, 4, 5
    @DatabaseField int ukOne = 0;
    @DatabaseField int ukTwo = 0;
    @DatabaseField int ukTwoFive = 0;
    @DatabaseField int ukThree = 0;
    @DatabaseField int ukFour = 0;
    @DatabaseField int ukFive = 0;
    // NZ values: <=2, 2.5, 3, 3.5, 4, 4.5, 5, 5.5, 6, 6.5, >=7
    @DatabaseField int nzLessThanOrEqualTwo = 0;
    @DatabaseField int nzTwoFive = 0;
    @DatabaseField int nzThree = 0;
    @DatabaseField int nzThreeFive = 0;
    @DatabaseField int nzFour = 0;
    @DatabaseField int nzFourFive = 0;
    @DatabaseField int nzFive = 0;
    @DatabaseField int nzFiveFive = 0;
    @DatabaseField int nzSix = 0;
    @DatabaseField int nzSixFive = 0;
    @DatabaseField int nzSevenOrMore = 0;

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

    public int getUkOne() {
        return ukOne;
    }

    public void setUkOne(int ukOne) {
        this.ukOne = ukOne;
    }

    public int getUkTwo() {
        return ukTwo;
    }

    public void setUkTwo(int ukTwo) {
        this.ukTwo = ukTwo;
    }

    public int getUkTwoFive() {
        return ukTwoFive;
    }

    public void setUkTwoFive(int ukTwoFive) {
        this.ukTwoFive = ukTwoFive;
    }

    public int getUkThree() {
        return ukThree;
    }

    public void setUkThree(int ukThree) {
        this.ukThree = ukThree;
    }

    public int getUkFour() {
        return ukFour;
    }

    public void setUkFour(int ukFour) {
        this.ukFour = ukFour;
    }

    public int getUkFive() {
        return ukFive;
    }

    public void setUkFive(int ukFive) {
        this.ukFive = ukFive;
    }

    public int getNzLessThanOrEqualTwo() {
        return nzLessThanOrEqualTwo;
    }

    public void setNzLessThanOrEqualTwo(int nzLessThanOrEqualTwo) {
        this.nzLessThanOrEqualTwo = nzLessThanOrEqualTwo;
    }

    public int getNzTwoFive() {
        return nzTwoFive;
    }

    public void setNzTwoFive(int nzTwoFive) {
        this.nzTwoFive = nzTwoFive;
    }

    public int getNzThree() {
        return nzThree;
    }

    public void setNzThree(int nzThree) {
        this.nzThree = nzThree;
    }

    public int getNzThreeFive() {
        return nzThreeFive;
    }

    public void setNzThreeFive(int nzThreeFive) {
        this.nzThreeFive = nzThreeFive;
    }

    public int getNzFour() {
        return nzFour;
    }

    public void setNzFour(int nzFour) {
        this.nzFour = nzFour;
    }

    public int getNzFourFive() {
        return nzFourFive;
    }

    public void setNzFourFive(int nzFourFive) {
        this.nzFourFive = nzFourFive;
    }

    public int getNzFive() {
        return nzFive;
    }

    public void setNzFive(int nzFive) {
        this.nzFive = nzFive;
    }

    public int getNzFiveFive() {
        return nzFiveFive;
    }

    public void setNzFiveFive(int nzFiveFive) {
        this.nzFiveFive = nzFiveFive;
    }

    public int getNzSix() {
        return nzSix;
    }

    public void setNzSix(int nzSix) {
        this.nzSix = nzSix;
    }

    public int getNzSixFive() {
        return nzSixFive;
    }

    public void setNzSixFive(int nzSixFive) {
        this.nzSixFive = nzSixFive;
    }

    public int getNzSevenOrMore() {
        return nzSevenOrMore;
    }

    public void setNzSevenOrMore(int nzSevenOrMore) {
        this.nzSevenOrMore = nzSevenOrMore;
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

    public String toSummary() {
        switch (setting) {
            case UK:
                return String.format("1: %d, 2: %d, 2.5: %d, 3: %d, 4: %d, 5: %d",
                        ukOne, ukTwo, ukTwoFive, ukThree, ukFour, ukFive
                );
            case NZ:
            return String.format("<=2: %d, 2.5: %d, 3: %d, 3.5: %d, 4: %d, 4.5: %d, 5: %d," +
                        "5.5: %d, 6: %d, 6.5: %d, >=7: %d",
                        nzLessThanOrEqualTwo, nzTwoFive, nzThree, nzThreeFive, nzFour, nzFourFive,
                        nzFive, nzFiveFive, nzSix, nzSixFive, nzSevenOrMore
            );
        }
        return null;
    }
}
