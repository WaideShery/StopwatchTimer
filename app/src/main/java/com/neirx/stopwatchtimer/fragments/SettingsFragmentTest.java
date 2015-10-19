package com.neirx.stopwatchtimer.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.neirx.neirdialogs.interfaces.NeirDialogInterface;
import com.neirx.neirdialogs.interfaces.RootDialog;
import com.neirx.neirdialogs.interfaces.SingleChoiceDialog;
import com.neirx.stopwatchtimer.CustomDialogFactory;
import com.neirx.stopwatchtimer.PreferencesActivity;
import com.neirx.stopwatchtimer.R;
import com.neirx.stopwatchtimer.SettingItem;
import com.neirx.stopwatchtimer.custom.SettingsAdapter;
import com.neirx.stopwatchtimer.settings.AppSettings;
import com.neirx.stopwatchtimer.settings.SettingPref;
import com.neirx.stopwatchtimer.settings.SettingsManagement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Waide Shery on 07.08.2015.
 *
 */
public class SettingsFragmentTest extends Fragment implements NeirDialogInterface.OnClickListener {
    SettingsManagement settings;
    boolean isChecked;
    SettingsAdapter mAdapter;
    SettingItem settingItem;
    CustomDialogFactory dialogFactory;
    SingleChoiceDialog dialogOrientation;

    List<String> orientationList;

    String titleScreen;
    String titleScreenStatus;
    String titleScreenOrientation;
    String titleControl;
    String titleDialClickable;
    String titleVibrateStatus;
    String titleSound;
    String titleKeySoundStatus;
    String titleTimerLongAlarm;
    String titleCustomSoundTimer;
    String titleSelectMelody;

    String sumScreenStatus;
    String sumScreenOrientation;
    String sumDialClickable;
    String sumVibrateStatus;
    String sumKeySoundStatus;
    String sumTimerLongAlarm;
    String sumCustomSoundTimer;

    String[] screenOrientationArray;



    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_test_settings, container, false);
        //Класс управления настройками приложения
        Activity activity = getActivity();
        settings = AppSettings.getInstance(activity);
        mAdapter = new SettingsAdapter(activity);
        dialogFactory = CustomDialogFactory.newInstance(activity);

        initValues();

        //Заголовок "Экран"
        mAdapter.addSectionHeaderItem(new SettingItem(titleScreen));

        //Настройка "Не выключать экран"
        isChecked = settings.getBoolPref(SettingPref.Bool.isNotTurnOffScreen);
        SettingItem screenStatusItem = new SettingItem(titleScreenStatus, sumScreenStatus, isChecked);
        screenStatusItem.setKey(SettingPref.Bool.isNotTurnOffScreen);
        mAdapter.addItem(screenStatusItem);

        //Настройка "ориентация экрана"
        int chOrientation = settings.getIntPref(SettingPref.Int.screenOrientation, 0);
        orientationList = new ArrayList<>();
        Collections.addAll(orientationList, screenOrientationArray);
        sumScreenOrientation = screenOrientationArray[chOrientation];
        SettingItem screenOrientationItem = new SettingItem(titleScreenOrientation, sumScreenOrientation);
        dialogOrientation = dialogFactory.createSingleChoiceDialog();
        dialogOrientation.setTitle(getString(R.string.pref_screenOrientation));
        dialogOrientation.setItems(orientationList, new int[]{chOrientation});
        dialogOrientation.setPositiveButton(getString(R.string.ok_btn));
        dialogOrientation.setNegativeButton(getString(R.string.cancel_btn));
        dialogOrientation.setOnClickListener(this, getString(R.string.pref_screenOrientation));
        screenOrientationItem.setDialog(dialogOrientation);
        mAdapter.addItem(screenOrientationItem);

        //Заголовок "Управлени"
        mAdapter.addSectionHeaderItem(new SettingItem(titleControl));

        //Настройка "Нажатие на циферблат"
        isChecked = settings.getBoolPref(SettingPref.Bool.isDialClickable);
        settingItem = new SettingItem(titleDialClickable, sumDialClickable, isChecked);
        settingItem.setKey(SettingPref.Bool.isDialClickable);
        mAdapter.addItem(settingItem);

        //Настройка "Вибрация"
        isChecked = settings.getBoolPref(SettingPref.Bool.vibrateState);
        settingItem = new SettingItem(titleVibrateStatus, sumVibrateStatus, isChecked);
        settingItem.setKey(SettingPref.Bool.vibrateState);
        mAdapter.addItem(settingItem);

        //Заголовок "Звуки"
        mAdapter.addSectionHeaderItem(new SettingItem(titleSound));

        //Настройка "Звук кнопок"
        isChecked = settings.getBoolPref(SettingPref.Bool.keySoundState);
        settingItem = new SettingItem(titleKeySoundStatus, sumKeySoundStatus, isChecked);
        settingItem.setKey(SettingPref.Bool.keySoundState);
        mAdapter.addItem(settingItem);

        //Настройка "Долгий сигнал таймера"
        isChecked = settings.getBoolPref(SettingPref.Bool.longTimerAlarmState);
        settingItem = new SettingItem(titleTimerLongAlarm, sumTimerLongAlarm, isChecked);
        settingItem.setKey(SettingPref.Bool.longTimerAlarmState);
        mAdapter.addItem(settingItem);

        /*
        //Настройка "Мелодия таймера"
        isChecked = settings.getBoolPref(SettingPref.Bool.isCustomTimerSound);
        settingItem = new SettingItem(titleCustomSoundTimer, sumCustomSoundTimer, isChecked);
        settingItem.setKey(SettingPref.Bool.isCustomTimerSound);
        mAdapter.addItem(settingItem);

        //Настройка "Выбор мелодии"
        mAdapter.addItem(new SettingItem(titleSelectMelody));
        */

        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        listView.setAdapter(mAdapter);
        listView.setDivider(null);
        listView.setOnItemClickListener(clickListener);
        return rootView;
    }



    AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SettingItem item = (SettingItem) parent.getAdapter().getItem(position);
            RootDialog dialog;
            if(item.hasCheckBox()){
                boolean isChecked = item.isChecked();
                settings.setPref(item.getKey(), !isChecked);
                item.setChecked(!isChecked);
                mAdapter.notifyDataSetChanged();
            } else if((dialog = item.getDialog()) != null){
                FragmentManager manager = getFragmentManager();
                dialog.show(manager, item.getTitle());
            }
        }
    };

    @Override
    public void onClick(String tag, int buttonId, Object extraData) {
        List<SettingItem> itemList = mAdapter.getData();
        if (tag.equals(titleScreenOrientation) && buttonId == NeirDialogInterface.BUTTON_POSITIVE) {
            int checkId = (Integer) extraData;
            settings.setPref(SettingPref.Int.screenOrientation, checkId);
            for(SettingItem item : itemList){
                if(item.getTitle().equals(tag)) {
                    item.setSummary(screenOrientationArray[checkId]);
                    dialogOrientation.setItems(orientationList, new int[]{checkId});
                }
            }
            mAdapter.notifyDataSetChanged();
            ((PreferencesActivity)getActivity()).switchScreenOrientation();
        }
    }


    private void initValues() {
        titleScreen = getString(R.string.pref_screen);
        titleScreenStatus = getString(R.string.pref_screenStatus);
        titleScreenOrientation = getString(R.string.pref_screenOrientation);
        titleControl = getString(R.string.pref_control);
        titleDialClickable = getString(R.string.pref_dialClickable);
        titleVibrateStatus = getString(R.string.pref_vibrateStatus);
        titleSound = getString(R.string.pref_sound);
        titleKeySoundStatus = getString(R.string.pref_keySoundStatus);
        titleTimerLongAlarm = getString(R.string.pref_timerLongAlarm);
        titleCustomSoundTimer = getString(R.string.pref_customSoundTimer);
        titleSelectMelody = getString(R.string.pref_selectMelody);

        sumScreenStatus = getString(R.string.pref_screenStatus_summ);
        sumDialClickable = getString(R.string.pref_dialClickable_summ);
        sumVibrateStatus = getString(R.string.pref_vibrateStatus_summ);
        sumKeySoundStatus = getString(R.string.pref_keySoundStatus_summ);
        sumTimerLongAlarm = getString(R.string.pref_timerLongAlarm_summ);
        sumCustomSoundTimer = getString(R.string.pref_customSoundTimer_summ);

        screenOrientationArray = getResources().getStringArray(R.array.pref_screenOrientation_entries);
    }
}
