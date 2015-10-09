package com.neirx.stopwatchtimer;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.neirx.stopwatchtimer.fragments.SettingsFragment;
import com.neirx.stopwatchtimer.fragments.SettingsFragmentTest;
import com.neirx.stopwatchtimer.settings.AppSettings;
import com.neirx.stopwatchtimer.settings.SettingPref;
import com.neirx.stopwatchtimer.settings.SettingsManagement;


public class PreferencesActivity extends Activity {
    int screenOrientation;
    SettingsManagement settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        settings = AppSettings.getInstance(this);
        screenOrientation = settings.getIntPref(SettingPref.Int.screenOrientation, 0);

        switchScreenOrientation(screenOrientation);

        getFragmentManager().beginTransaction().add(R.id.containerPreference, new SettingsFragmentTest()).commit();
    }

    private void switchScreenOrientation(int orientation){
        switch (orientation){
            case Statical.SCREEN_ORIENTATION_SYSTEM:
                setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                break;
            case Statical.SCREEN_ORIENTATION_PORTRAIT:
                setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case Statical.SCREEN_ORIENTATION_LANDSCAPE:
                setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            case Statical.SCREEN_ORIENTATION_AUTO:
                setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                break;
        }
    }
}
