package com.neirx.stopwatchtimer.fragments;


import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.neirx.stopwatchtimer.R;


public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);


    }
}
