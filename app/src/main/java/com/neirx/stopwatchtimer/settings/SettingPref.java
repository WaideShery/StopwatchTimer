package com.neirx.stopwatchtimer.settings;


public class SettingPref {
    public enum Bool implements SettingsManagement.BoolPref {
        soundState, isNotScreenDim, isDialClickable, twiceDialClick, vibrateState, keySoundState, longTimerAlarmState,
        isCustomTimerSound, isStopwatchRun, wasStopwatchStart, incrStopwatchNum, isFirst, lapArrowDisplays
    }
    public enum String implements SettingsManagement.StringPref{

    }
    public enum Long implements SettingsManagement.LongPref{
        stopwatchBaseTime, stopwatchSavedTime, previousLapTime, lastLapTime
    }
    public enum Int implements SettingsManagement.IntPref{
        screenOrientation, countTimeNum, countStopwatchNum


    }
}
