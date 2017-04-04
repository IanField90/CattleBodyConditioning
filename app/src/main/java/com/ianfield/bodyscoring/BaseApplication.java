package com.ianfield.bodyscoring;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.ianfield.bodyscoring.di.AppComponent;
import com.ianfield.bodyscoring.di.DaggerAppComponent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

import io.realm.Realm;
import io.realm.RealmConfiguration;

// import com.crashlytics.android.Crashlytics;
// import io.fabric.sdk.android.Fabric;

public class BaseApplication extends Application {

    private static final String TAG = BaseApplication.class.getSimpleName();

    private AppComponent component;

    public static String getJSONFromBundle(Bundle bundle) {
        if (bundle == null) {
            return "";
        }
        JSONObject json = new JSONObject();
        Set<String> keys = bundle.keySet();
        for (String key : keys) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    json.put(key, JSONObject.wrap(bundle.get(key)));
                }
            } catch (JSONException ignore) {
            }
        }
        return json.toString();
    }

    @CallSuper
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .build();
        Realm.setDefaultConfiguration(realmConfig);

        component = DaggerAppComponent.builder()
                .build();

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
            registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle bundle) {
                    Log.d(TAG, activity.getLocalClassName() + " created. Intent extras: " + getJSONFromBundle(activity.getIntent().getExtras()));
                }

                @Override
                public void onActivityStarted(Activity activity) {
                }

                @Override
                public void onActivityResumed(Activity activity) {
                }

                @Override
                public void onActivityPaused(Activity activity) {
                }

                @Override
                public void onActivityStopped(Activity activity) {
                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                }
            });
        }
    }

    public AppComponent component() {
        return component;
    }
}
