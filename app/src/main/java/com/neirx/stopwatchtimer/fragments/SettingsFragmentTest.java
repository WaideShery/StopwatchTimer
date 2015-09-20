package com.neirx.stopwatchtimer.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.neirx.neirdialogs.dialog.ListDialogFragment;
import com.neirx.neirdialogs.dialog.SelectDialogFragment;
import com.neirx.neirdialogs.interfaces.ChoiceItem;
import com.neirx.stopwatchtimer.CustomDialogCreator;
import com.neirx.stopwatchtimer.R;
import com.neirx.stopwatchtimer.SettingItem;
import com.neirx.stopwatchtimer.custom.OrientationItem;
import com.neirx.stopwatchtimer.custom.SettingsAdapter;
import com.neirx.stopwatchtimer.settings.AppSettings;
import com.neirx.stopwatchtimer.settings.SettingPref;
import com.neirx.stopwatchtimer.settings.SettingsManagement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Waide Shery on 07.08.2015.
 *
 */
public class SettingsFragmentTest extends Fragment {
    SettingsManagement settings;
    String title;
    String summary;
    boolean isChecked;
    SettingsAdapter mAdapter;
    SettingItem settingItem;
    CustomDialogCreator dialogCreator;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_test_settings, container, false);
        //Класс управления настройками приложения
        Activity activity = getActivity();
        settings = AppSettings.getInstance(activity);
        mAdapter = new SettingsAdapter(activity);
        dialogCreator = CustomDialogCreator.newInstance(activity);


        //Заголовок "Экран"
        title = getString(R.string.pref_screen);
        mAdapter.addSectionHeaderItem(new SettingItem(title));

        //Настройка "Не выключать экран"
        title = getString(R.string.pref_screenStatus);
        summary = getString(R.string.pref_screenStatus_summ);
        isChecked = settings.getBoolPref(SettingPref.Bool.isNotTurnOffScreen);
        settingItem = new SettingItem(title, summary, isChecked);
        settingItem.setKey(SettingPref.Bool.isNotTurnOffScreen);
        mAdapter.addItem(settingItem);

        //Настройка "ориентация экрана"
        title = getString(R.string.pref_screenOrientation);
        summary = settings.getStringPref(SettingPref.String.screenOrientation, null);
        if(summary == null){
            summary = getString(R.string.pref_screenOrientation_default);
        }
        settingItem = new SettingItem(title, summary);
        List<ChoiceItem> choiceItems = new ArrayList<>();
        String[] orientations = getResources().getStringArray(R.array.pref_screenOrientation_entries);
        choiceItems.add(new OrientationItem())
        SelectDialogFragment dialogOrientation = dialogCreator.getSelectDialog(false);
        dialogOrientation.setItems(getResources().getStringArray(R.array.pref_screenOrientation_entries));
        settingItem.setDialog(dialogOrientation);
        mAdapter.addItem(settingItem);

        //Заголовок "Управлени"
        title = getString(R.string.pref_control);
        mAdapter.addSectionHeaderItem(new SettingItem(title));

        //Настройка "Нажатие на циферблат"
        title = getString(R.string.pref_dialClickable);
        summary = getString(R.string.pref_dialClickable_summ);
        isChecked = settings.getBoolPref(SettingPref.Bool.isDialClickable);
        settingItem = new SettingItem(title, summary, isChecked);
        settingItem.setKey(SettingPref.Bool.isDialClickable);
        mAdapter.addItem(settingItem);

        //Настройка "Вибрация"
        title = getString(R.string.pref_vibrateStatus);
        summary = getString(R.string.pref_vibrateStatus_summ);
        isChecked = settings.getBoolPref(SettingPref.Bool.vibrateState);
        settingItem = new SettingItem(title, summary, isChecked);
        settingItem.setKey(SettingPref.Bool.vibrateState);
        mAdapter.addItem(settingItem);

        //Заголовок "Звуки"
        title = getString(R.string.pref_control);
        mAdapter.addSectionHeaderItem(new SettingItem(title));

        //Настройка "Звук кнопок"
        title = getString(R.string.pref_keySoundStatus_summ);
        summary = getString(R.string.pref_keySoundStatus_summ);
        isChecked = settings.getBoolPref(SettingPref.Bool.keySoundState);
        settingItem = new SettingItem(title, summary, isChecked);
        settingItem.setKey(SettingPref.Bool.keySoundState);
        mAdapter.addItem(settingItem);

        //Настройка "Долгий сигнал таймера"
        title = getString(R.string.pref_timerLongAlarm);
        summary = getString(R.string.pref_timerLongAlarm_summ);
        isChecked = settings.getBoolPref(SettingPref.Bool.longTimerAlarmState);
        settingItem = new SettingItem(title, summary, isChecked);
        settingItem.setKey(SettingPref.Bool.longTimerAlarmState);
        mAdapter.addItem(settingItem);

        //Настройка "Мелодия таймера"
        title = getString(R.string.pref_customSoundTimer);
        summary = getString(R.string.pref_customSoundTimer_summ);
        isChecked = settings.getBoolPref(SettingPref.Bool.isCustomTimerSound);
        settingItem = new SettingItem(title, summary, isChecked);
        settingItem.setKey(SettingPref.Bool.isCustomTimerSound);
        mAdapter.addItem(settingItem);

        //Настройка "Выбор мелодии"
        title = getString(R.string.pref_selectMelody);
        mAdapter.addItem(new SettingItem(title));

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
            DialogFragment dialog;
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


}
