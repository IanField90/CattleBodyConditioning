package com.ianfield.bodyscoring;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.ianfield.bodyscoring.utils.DatabaseHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;

/**
 * Created by Ian on 09/01/2016.
 * The engine room of the app for persistence, tracking, crash logging if required etc.
 */
public class BodyConditioningApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        OpenHelperManager.getHelper(this, DatabaseHelper.class);

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }
}
