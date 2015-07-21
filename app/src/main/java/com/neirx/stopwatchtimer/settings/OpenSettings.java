package com.neirx.stopwatchtimer.settings;


import android.content.Context;
import android.content.SharedPreferences;

public abstract class OpenSettings implements SettingsManagement {
    final protected SharedPreferences mSettings;
    protected String FILE_PREFERENCES = "app_settings";

    protected OpenSettings(Context context) {
        mSettings = context.getSharedPreferences(FILE_PREFERENCES, Context.MODE_PRIVATE);
    }

    protected OpenSettings(Context context, String fileName) {
        mSettings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }


    @Override
    public void setPref(BoolPref key, boolean value) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putBoolean(key.toString(), value);
        editor.apply();
    }

    @Override
    public void setPref(StringPref key, String value) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(key.toString(), value);
        editor.apply();
    }

    @Override
    public void setPref(LongPref key, long value) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putLong(key.toString(), value);
        editor.apply();
    }

    @Override
    public void setPref(IntPref key, int value) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt(key.toString(), value);
        editor.apply();
    }

    @Override
    public void setFloat(FloatPref key, float value) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putFloat(key.toString(), value);
        editor.apply();
    }

    @Override
    public boolean getBoolPref(BoolPref key) {
        return mSettings.getBoolean(key.toString(), false);
    }
    @Override
    public boolean getBoolPref(BoolPref key, boolean defKey) {
        return mSettings.getBoolean(key.toString(), defKey);
    }

    @Override
    public String getStringPref(StringPref key) {
        return mSettings.getString(key.toString(), null);
    }
    @Override
    public String getStringPref(StringPref key, String defKey) {
        return mSettings.getString(key.toString(), defKey);
    }

    @Override
    public long getLongPref(LongPref key) {
        return mSettings.getLong(key.toString(), 0);
    }
    @Override
    public long getLongPref(LongPref key, long defKey) {
        return mSettings.getLong(key.toString(), defKey);
    }

    @Override
    public int getIntPref(IntPref key) {
        return mSettings.getInt(key.toString(), 0);
    }
    @Override
    public int getIntPref(IntPref key, int defKey) {
        return mSettings.getInt(key.toString(), defKey);
    }

    @Override
    public float getFloatPref(FloatPref key) {
        return mSettings.getFloat(key.toString(), 0.0f);
    }
    @Override
    public float getFloatPref(FloatPref key, float defKey) {
        return mSettings.getFloat(key.toString(), defKey);
    }
}
