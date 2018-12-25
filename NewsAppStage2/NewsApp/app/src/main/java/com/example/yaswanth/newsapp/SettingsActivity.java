package com.example.yaswanth.newsapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public static class NewsPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            Preference newsOrderBy = findPreference(getString(R.string.settings_order_by_key));
            bindPreferenceSummaryToValue(newsOrderBy);

            Preference newsTopic = findPreference(getString(R.string.settings_topic_key));
            bindPreferenceSummaryToValue(newsTopic);

        }


        @Override
        public boolean onPreferenceChange(Preference preference, Object o) {
            String changedValue = o.toString();
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(changedValue);
                if (prefIndex >= 0) {
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);
                }
            } else {
                preference.setSummary(changedValue);
            }
            return true;
        }

        private void bindPreferenceSummaryToValue(Preference newsTopic) {
            newsTopic.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(newsTopic.getContext());
            String preferenceString = preferences.getString(newsTopic.getKey(), "");
            onPreferenceChange(newsTopic, preferenceString);
        }


    }
}
