package com.neirx.stopwatchtimer.settings;


public class SettingPref {
    public enum Bool implements SettingsManagement.BoolPref {
        soundState, isNotTurnOffScreen, isDialClickable, vibrateState, keySoundState, longTimerAlarmState,
        isCustomTimerSound, isStopwatchRun, stopwatchStartOver
    }
    public enum String implements SettingsManagement.StringPref{

    }
    public enum Long implements SettingsManagement.LongPref{
        stopwatchCountTime, stopwatchSaveTime
    }
    public enum Int implements SettingsManagement.IntPref{
        screenOrientation


    }
}
