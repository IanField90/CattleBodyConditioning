package com.ianfield.bodyscoring;

/**
 * Created by Ian on 09/01/2016.
 * The engine room of the app for persistence, tracking, crash logging if required etc.
 */
public class BodyConditioningApplication extends BaseApplication {
    private static final String TAG = "BodyConditioningApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        component().inject(this);
    }
}
