package com.neirx.stopwatchtimer.settings;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

public abstract class EncryptSettings implements SettingsManagement {
    protected String FILE_PREFERENCES = "app_settings";
    protected int encryptNum = 77;
    final protected SharedPreferences mSettings;

    protected EncryptSettings(Context context) {
        mSettings = context.getSharedPreferences(FILE_PREFERENCES, Context.MODE_PRIVATE);
    }

    protected EncryptSettings(Context context, String fileName) {
        mSettings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }


    public abstract SettingsManagement getInstance(Context context);

    private String encryptKey(String keyName) {
        byte[] key = keyName.getBytes();
        byte[] arr = keyName.getBytes();
        byte[] result = new byte[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = (byte) (arr[i] ^ key[encryptNum % key.length]);
        }
        return new String(Base64.encode(result, Base64.NO_WRAP));
    }

    @Override
    public void setPref(BoolPref key, boolean value) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putBoolean(encryptKey(key.toString()), value);
        editor.apply();
    }

    @Override
    public void setPref(StringPref key, String value) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(encryptKey(key.toString()), value);
        editor.apply();
    }

    @Override
    public void setPref(LongPref key, long value) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putLong(encryptKey(key.toString()), value);
        editor.apply();
    }

    @Override
    public void setPref(IntPref key, int value) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt(encryptKey(key.toString()), value);
        editor.apply();
    }

    @Override
    public void setFloat(FloatPref key, float value) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putFloat(encryptKey(key.toString()), value);
        editor.apply();
    }

    @Override
    public boolean getBoolPref(BoolPref key) {
        return mSettings.getBoolean(encryptKey(key.toString()), false);
    }
    @Override
    public boolean getBoolPref(BoolPref key, boolean defKey) {
        return mSettings.getBoolean(encryptKey(key.toString()), defKey);
    }

    @Override
    public String getStringPref(StringPref key) {
        return mSettings.getString(encryptKey(key.toString()), null);
    }
    @Override
    public String getStringPref(StringPref key, String defKey) {
        return mSettings.getString(encryptKey(key.toString()), defKey);
    }

    @Override
    public long getLongPref(LongPref key) {
        return mSettings.getLong(encryptKey(key.toString()), 0);
    }
    @Override
    public long getLongPref(LongPref key, long defKey) {
        return mSettings.getLong(encryptKey(key.toString()), defKey);
    }

    @Override
    public int getIntPref(IntPref key) {
        return mSettings.getInt(encryptKey(key.toString()), 0);
    }
    @Override
    public int getIntPref(IntPref key, int defKey) {
        return mSettings.getInt(encryptKey(key.toString()), defKey);
    }

    @Override
    public float getFloatPref(FloatPref key) {
        return mSettings.getFloat(encryptKey(key.toString()), 0.0f);
    }
    @Override
    public float getFloatPref(FloatPref key, float defKey) {
        return mSettings.getFloat(encryptKey(key.toString()), defKey);
    }
}
