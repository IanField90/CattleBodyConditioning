package com.ianfield.bodyscoring.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Ian on 14/01/2016.
 */
@DatabaseTable(tableName = "scores")
public class Score {
    @DatabaseField(generatedId = true) int id;

    @DatabaseField(foreign = true, columnName = "record_id") Record record;

    @DatabaseField double score;

    @DatabaseField(defaultValue = "0") int count = 0;

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

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
}
