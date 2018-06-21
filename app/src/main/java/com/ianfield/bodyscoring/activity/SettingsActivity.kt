package com.ianfield.bodyscoring.activity

import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.support.v7.app.AppCompatActivity

import com.ianfield.bodyscoring.R

/**
 * Created by Ian on 09/01/2016.
 */
class SettingsActivity : AppCompatActivity() {

    lateinit var settingsFragment: SettingsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        settingsFragment = SettingsFragment()
        fragmentManager.beginTransaction()
                .replace(R.id.container, settingsFragment)
                .commit()
    }

    class SettingsFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            addPreferencesFromResource(R.xml.settings)

            val locality = findPreference(getString(R.string.pref_locality))

            val value = locality.sharedPreferences.getString(getString(R.string.pref_locality), getString(R.string.pref_localities_default))
            locality.summary = value

            locality.setOnPreferenceChangeListener { preference, newValue ->
                if (preference.key == getString(R.string.pref_locality)) {
                    preference.summary = newValue as String
                }
                true
            }

        }
    }


}
