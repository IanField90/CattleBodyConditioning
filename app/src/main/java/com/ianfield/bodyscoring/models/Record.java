package com.ianfield.bodyscoring.models;

import android.text.TextUtils;

import java.util.Date;

/**
 * Created by Ian Field on 09/01/2016.
 *
 * Store the scoring numbers for a herd, UK scale and NZ scale
 * 1. Drying off ( 7–8 weeks pre-calving)
 * 2. Pre-calving ( 3 weeks pre-calving)
 * 3. Pre-service
 */
public class Record {

    private enum Setting { UK, NZ }

    String name;

    Date plannedCalvingDate;

    Date scoringDate;

    Setting setting = Setting.UK;

    int ukOne = 0;

    int ukTwo = 0;

    int ukTwoFive = 0;

    int ukThree = 0;

    int ukFour = 0;

    int ukFive = 0;

    int nzLessThanOrEqualTwo = 0;

    int nzTwo = 0;

    int nzTwoFive = 0;

    int nzThree = 0;

    int nzThreeFive = 0;

    int nzFour = 0;

    int nzFourFive = 0;

    int nzFive = 0;

    int nzFiveFive = 0;

    int nzSix = 0;

    int nzSixFive = 0;

    int nzSevenOrMore = 0;


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

    public int getNzTwo() {
        return nzTwo;
    }

    public void setNzTwo(int nzTwo) {
        this.nzTwo = nzTwo;
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
}
