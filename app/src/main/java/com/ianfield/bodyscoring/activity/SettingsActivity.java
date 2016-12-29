package com.ianfield.bodyscoring.activity;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;

import com.ianfield.bodyscoring.R;

/**
 * Created by Ian on 09/01/2016.
 */
public class SettingsActivity extends AppCompatActivity {

    SettingsFragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingsFragment = new SettingsFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.container, settingsFragment)
                .commit();
    }

    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.settings);

            Preference locality = findPreference(getString(R.string.pref_locality));

            String value = locality.getSharedPreferences().getString(getString(R.string.pref_locality), getString(R.string.pref_localities_default));
            locality.setSummary(value);

            locality.setOnPreferenceChangeListener((preference, newValue) -> {
                if (preference.getKey().equals(getString(R.string.pref_locality))) {
                    preference.setSummary((String) newValue);
                }
                return true;
            });

        }
    }


}
