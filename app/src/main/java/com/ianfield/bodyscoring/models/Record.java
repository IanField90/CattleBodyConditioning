package com.ianfield.bodyscoring.models;

import android.text.TextUtils;

import java.util.Date;

/**
 * Created by Ian Field on 09/01/2016.
 */
public class Record {
    String name;

    Date plannedCalvingDate;

    Date scoringDate;

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

    public boolean isValid() {
        return (plannedCalvingDate != null && scoringDate != null && !TextUtils.isEmpty(name));
    }
}
