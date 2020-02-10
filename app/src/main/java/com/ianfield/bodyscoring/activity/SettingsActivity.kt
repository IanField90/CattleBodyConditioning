package com.ianfield.bodyscoring.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
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
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, settingsFragment)
                .commit()
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            addPreferencesFromResource(R.xml.settings)

            val locality: Preference? = findPreference(getString(R.string.pref_locality))
            val value = locality?.sharedPreferences?.getString(getString(R.string.pref_locality), getString(R.string.pref_localities_default))
            locality?.summary = value
            locality?.setOnPreferenceChangeListener { preference, newValue ->
                if (preference.key == getString(R.string.pref_locality)) {
                    preference.summary = newValue as String
                }
                true
            }

        }
    }


}
