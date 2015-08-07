package com.neirx.stopwatchtimer;


import android.content.Context;

import com.neirx.stopwatchtimer.settings.OpenSettings;
import com.neirx.stopwatchtimer.settings.SettingsManagement;


public class AppSettings extends OpenSettings {
    private final String CLASS_NAME = "<AppSettings> ";
    public static AppSettings instance;

    public enum BoolPref implements SettingsManagement.BoolPref {
        soundState, isNotTurnOffScreen, isDialClickable, vibrateState, keySoundState, longTimerAlarmState, isCustomTimerSound;
    }

    private AppSettings(Context context) {
        super(context);
    }

    public static SettingsManagement getInstance(Context context) {
        if(instance == null){
            instance = new AppSettings(context);
        }
        return instance;
    }
}
