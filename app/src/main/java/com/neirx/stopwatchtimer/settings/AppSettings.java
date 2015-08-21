package com.neirx.stopwatchtimer.settings;


import android.content.Context;


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
        return instance;
    }
}
