package com.ianfield.bodyscoring.models;

import java.util.Locale;

import io.realm.RealmObject;

/**
 * Created by Ian on 14/01/2016.
 */
public class Score extends RealmObject {

    double score;

    int count = 0;

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String toString() {
        return String.format(Locale.getDefault(), "Score: %.1f, Count: %d", score, count);
    }
}
