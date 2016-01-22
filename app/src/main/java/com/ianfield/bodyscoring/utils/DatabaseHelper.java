package com.ianfield.bodyscoring.utils;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ianfield.bodyscoring.models.Record;
import com.ianfield.bodyscoring.models.Score;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Ian on 09/01/2016.
 * Database helper class used to manage the creation and upgrading of your database. Along with
 * the access to the Data Access Objects (DAOs)
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "bodyconditioning.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Record, Integer> recordDao = null;
    private Dao<Score, Integer> scoreDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(TAG, "onCreate");
            TableUtils.createTable(connectionSource, Record.class);
            TableUtils.createTable(connectionSource, Score.class);
        } catch (SQLException e) {
            Log.e(TAG, "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
//        try {
//            if (oldVersion < 2) {
//
//            }
//        } catch (SQLException e) {
//            Log.e(DatabaseHelper.class.getName(), "Can't upgrade database from " + oldVersion + " to " + newVersion, e);
//            throw new RuntimeException(e);
//        }
    }

    /**
     * @return The Database Access Object (DAO) for our {@link Record} class.
     */
    public Dao<Record, Integer> getRecordDao() throws SQLException {
        if (recordDao == null) {
            recordDao = getDao(Record.class);
        }
        return recordDao;
    }

    public Dao<Score, Integer> getScoreDao() throws SQLException {
        if (scoreDao == null) {
            scoreDao = getDao(Score.class);
        }
        return scoreDao;
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        recordDao = null;
        scoreDao = null;
    }
}
