package com.neirx.stopwatchtimer.settings;


import android.content.Context;

import java.util.Set;


public class AppSettings extends OpenSettings {
    private final String CLASS_NAME = "<AppSettings> ";
    public static AppSettings instance;

    private AppSettings(Context context) {
        super(context);
    }

    public static SettingsManagement getInstance(Context context) {
        if(instance == null){
            instance = new AppSettings(context);
        }
        if(instance.getBoolPref(SettingPref.Bool.isFirst, true)) instance.setDefault();
        return instance;
    }

    public void setDefault(){
        instance.setPref(SettingPref.Bool.isFirst, false);
        instance.setPref(SettingPref.Bool.isDialClickable, true);
        instance.setPref(SettingPref.Bool.vibrateState, true);
        instance.setPref(SettingPref.Int.screenOrientation, 0);
        instance.setPref(SettingPref.Bool.isNotTurnOffScreen, false);
    }
}
