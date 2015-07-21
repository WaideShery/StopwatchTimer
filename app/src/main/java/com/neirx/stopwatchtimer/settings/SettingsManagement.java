package com.neirx.stopwatchtimer.settings;


public interface SettingsManagement {
    interface BoolPref {}
    interface StringPref{}
    interface LongPref{}
    interface IntPref{}
    interface FloatPref{}

    void setPref(BoolPref key, boolean value);
    void setPref(StringPref key, String value);
    void setPref(LongPref key, long value);
    void setPref(IntPref key, int value);
    void setFloat(FloatPref key, float value);

    boolean getBoolPref(BoolPref key);
    boolean getBoolPref(BoolPref key, boolean defKey);

    String getStringPref(StringPref key);
    String getStringPref(StringPref key, String defKey);

    long getLongPref(LongPref key);
    long getLongPref(LongPref key, long defKey);

    int getIntPref(IntPref key);
    int getIntPref(IntPref key, int defKey);

    float getFloatPref(FloatPref key);
    float getFloatPref(FloatPref key, float defKey);
}
