package com.neirx.stopwatchtimer.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.neirx.stopwatchtimer.settings.AppSettings;
import com.neirx.stopwatchtimer.R;
import com.neirx.stopwatchtimer.settings.SettingPref;
import com.neirx.stopwatchtimer.settings.SettingsManagement;


public class SettingsFragment extends Fragment {
    RelativeLayout layScreenStatus, layScreenOrientation, layDialClickable, layVibrateStatus, laySoundStatus,
            layTimerOff, layCustomSoundTimer, laySelectMelody;
    CheckBox checkScreenStatus, checkDialClickable, checkVibrateStatus, checkKeySoundStatus, checkTimerLongAlarm,
            checkCustomSoundTimer;
    SettingsManagement settings;



    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        //Класс управления настройками приложения
        settings = AppSettings.getInstance(getActivity());

        //Поиск view-элементов
        layScreenStatus = (RelativeLayout) rootView.findViewById(R.id.lay_screenStatus);
        layScreenOrientation = (RelativeLayout) rootView.findViewById(R.id.lay_screenOrientation);
        layDialClickable = (RelativeLayout) rootView.findViewById(R.id.lay_dialClickable);
        layVibrateStatus = (RelativeLayout) rootView.findViewById(R.id.lay_vibrateStatus);
        laySoundStatus = (RelativeLayout) rootView.findViewById(R.id.lay_soundStatus);
        layTimerOff = (RelativeLayout) rootView.findViewById(R.id.lay_timerOff);
        layCustomSoundTimer = (RelativeLayout) rootView.findViewById(R.id.lay_customSoundTimer);
        laySelectMelody = (RelativeLayout) rootView.findViewById(R.id.lay_selectMelody);
        checkScreenStatus = (CheckBox) rootView.findViewById(R.id.check_screenStatus);
        checkDialClickable = (CheckBox) rootView.findViewById(R.id.check_dialClickable);
        checkVibrateStatus = (CheckBox) rootView.findViewById(R.id.check_vibrateStatus);
        checkKeySoundStatus = (CheckBox) rootView.findViewById(R.id.check_keySoundStatus);
        checkTimerLongAlarm = (CheckBox) rootView.findViewById(R.id.check_timerLongAlarm);
        checkCustomSoundTimer = (CheckBox) rootView.findViewById(R.id.check_customSoundTimer);

        //Установка состояний CheckBox-элементов
        checkScreenStatus.setChecked(settings.getBoolPref(SettingPref.Bool.isNotScreenDim));
        checkDialClickable.setChecked(settings.getBoolPref(SettingPref.Bool.isDialClickable));
        checkVibrateStatus.setChecked(settings.getBoolPref(SettingPref.Bool.vibrateState));
        checkKeySoundStatus.setChecked(settings.getBoolPref(SettingPref.Bool.keySoundState));
        checkTimerLongAlarm.setChecked(settings.getBoolPref(SettingPref.Bool.longTimerAlarmState));
        checkCustomSoundTimer.setChecked(settings.getBoolPref(SettingPref.Bool.isCustomTimerSound));

        //Установка слушателей
        setTouchCheckBox(layScreenStatus, checkScreenStatus, booleanAction);
        setTouchLayout(layScreenOrientation, action);
        setTouchCheckBox(layDialClickable, checkDialClickable, booleanAction);
        setTouchCheckBox(layVibrateStatus, checkVibrateStatus, booleanAction);
        setTouchCheckBox(laySoundStatus, checkKeySoundStatus, booleanAction);
        setTouchCheckBox(layTimerOff, checkTimerLongAlarm, booleanAction);
        setTouchCheckBox(layCustomSoundTimer, checkCustomSoundTimer, booleanAction);
        setTouchLayout(laySelectMelody, action);

        return rootView;
    }


    private void setTouchCheckBox(final RelativeLayout layout, final CheckBox checkBox, final ClickBooleanAction action) {
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Handler handler = new Handler();
                Runnable taskRunnable = new Runnable() {
                    @Override
                    public void run() {
                        checkBox.setPressed(true);
                        layout.setBackgroundColor(getResources().getColor(R.color.chb_bg_selectable));
                    }
                };
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        handler.postDelayed(taskRunnable, 2000);
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        checkBox.setPressed(false);
                        checkBox.setChecked(!checkBox.isChecked());
                        action.doAction(v, checkBox.isChecked());
                        layout.setBackgroundColor(getResources().getColor(R.color.app_background));
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_SCROLL:
                    case MotionEvent.ACTION_CANCEL:
                        handler.removeCallbacks(taskRunnable);
                        checkBox.setPressed(false);
                        layout.setBackgroundColor(getResources().getColor(R.color.app_background));
                        return true;
                }
                return false;
            }
        });
    }

    private void setTouchLayout(final RelativeLayout layout, final ClickAction action) {
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        layout.setBackgroundColor(getResources().getColor(R.color.chb_bg_selectable));
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        layout.setBackgroundColor(getResources().getColor(R.color.app_background));
                        action.doAction(v);
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        layout.setBackgroundColor(getResources().getColor(R.color.app_background));
                        return true;
                }
                return false;
            }
        });
    }

    ClickAction action = new ClickAction() {
        @Override
        public void doAction(View v) {
            switch (v.getId()) {
                case R.id.lay_selectMelody:
                    Toast.makeText(getActivity(), "Click on selectMelody", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.lay_screenOrientation:
                    Toast.makeText(getActivity(), "Click on screenOrientation", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    ClickBooleanAction booleanAction = new ClickBooleanAction() {
        @Override
        public void doAction(View v, boolean value) {
            switch (v.getId()) {
                case R.id.lay_screenStatus:
                    settings.setPref(SettingPref.Bool.isNotScreenDim, value);
                    break;
                case R.id.lay_dialClickable:
                    settings.setPref(SettingPref.Bool.isDialClickable, value);
                    break;
                case R.id.lay_vibrateStatus:
                    settings.setPref(SettingPref.Bool.vibrateState, value);
                    break;
                case R.id.lay_soundStatus:
                    settings.setPref(SettingPref.Bool.soundState, value);
                    break;
                case R.id.lay_timerOff:
                    settings.setPref(SettingPref.Bool.longTimerAlarmState, value);
                    break;
                case R.id.lay_customSoundTimer:
                    settings.setPref(SettingPref.Bool.isCustomTimerSound, value);
                    break;
            }
        }
    };


    private interface ClickAction {
        void doAction(View v);
    }

    private interface ClickBooleanAction {
        void doAction(View v, boolean value);
    }
}
