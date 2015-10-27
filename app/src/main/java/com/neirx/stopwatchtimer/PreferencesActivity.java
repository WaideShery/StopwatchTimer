package com.neirx.stopwatchtimer;

import android.app.ActionBar;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;

import com.neirx.stopwatchtimer.fragments.SettingsFragment;
import com.neirx.stopwatchtimer.fragments.SettingsFragmentTest;
import com.neirx.stopwatchtimer.settings.AppSettings;
import com.neirx.stopwatchtimer.settings.SettingPref;
import com.neirx.stopwatchtimer.settings.SettingsManagement;


public class PreferencesActivity extends Activity {
    int screenOrientation;
    SettingsManagement settings;
    private static final String CLASS_NAME = "<PreferencesActivity> ";

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(MainActivity.TAG, CLASS_NAME + "onStart");
        switchScreenOrientation();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        ActionBar actionBar = getActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        settings = AppSettings.getInstance(this);

        getFragmentManager().beginTransaction().add(R.id.containerPreference, new SettingsFragmentTest()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return false;
        }
    }


    public void switchScreenOrientation(){
        screenOrientation = settings.getIntPref(SettingPref.Int.screenOrientation, 0);
        switch (screenOrientation){
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
