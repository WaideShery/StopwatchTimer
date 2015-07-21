package com.neirx.stopwatchtimer;

import android.app.Activity;
import android.os.Bundle;

import com.neirx.stopwatchtimer.fragments.SettingsFragment;


public class PreferencesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        getFragmentManager().beginTransaction()
                .add(R.id.containerPreference, new SettingsFragment()).commit();
    }
}
