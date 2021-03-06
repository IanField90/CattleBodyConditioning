package com.ianfield.bodyscoring

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.annotation.CallSuper
import android.util.Log

import com.ianfield.bodyscoring.di.AppComponent
import com.ianfield.bodyscoring.di.DaggerAppComponent

import org.json.JSONException
import org.json.JSONObject

import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmSchema

class BodyConditioningApplication : Application() {

    private var component: AppComponent? = null

    @CallSuper
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val realmConfig = RealmConfiguration.Builder()
                .schemaVersion(1)
                .migration { realm, oldVersion, _ ->
                    val schema: RealmSchema =  realm.schema
                    if (oldVersion == 0L) {
                        schema.get("Record")?.removePrimaryKey()
                    }
                }
                .build()
        Realm.setDefaultConfiguration(realmConfig)

        component = DaggerAppComponent.builder()
                .build()

        if (BuildConfig.DEBUG) {
            registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
                override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
                    Log.d(TAG, activity.localClassName + " created. Intent extras: " + getJSONFromBundle(activity.intent.extras))
                }

                override fun onActivityStarted(activity: Activity) {}

                override fun onActivityResumed(activity: Activity) {}

                override fun onActivityPaused(activity: Activity) {}

                override fun onActivityStopped(activity: Activity) {}

                override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle?) {}

                override fun onActivityDestroyed(activity: Activity) {}
            })
        }

        component?.inject(this)
    }

    internal fun component(): AppComponent? {
        return component
    }

    companion object {

        private val TAG = BodyConditioningApplication::class.java.simpleName

        private fun getJSONFromBundle(bundle: Bundle?): String {
            if (bundle == null) {
                return ""
            }
            val json = JSONObject()
            val keys = bundle.keySet()
            for (key in keys) {
                try {
                    json.put(key, JSONObject.wrap(bundle.get(key)))
                } catch (ignore: JSONException) {
                }

            }
            return json.toString()
        }
    }
}
