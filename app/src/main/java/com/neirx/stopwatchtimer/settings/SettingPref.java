package com.neirx.stopwatchtimer.settings;


public class SettingPref {
    public enum Bool implements SettingsManagement.BoolPref {
        soundState, isNotTurnOffScreen, isDialClickable, vibrateState, keySoundState, longTimerAlarmState, isCustomTimerSound
    }
    public enum String implements SettingsManagement.StringPref{
        screenOrientation
    }
    public enum Long implements SettingsManagement.LongPref{
    }
    public enum Int implements SettingsManagement.IntPref{
    }
}
