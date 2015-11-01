package com.neirx.stopwatchtimer.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.neirx.stopwatchtimer.Lap;
import com.neirx.stopwatchtimer.LapAdapter;
import com.neirx.stopwatchtimer.MainActivity;
import com.neirx.stopwatchtimer.R;
import com.neirx.stopwatchtimer.settings.AppSettings;
import com.neirx.stopwatchtimer.settings.SettingPref;
import com.neirx.stopwatchtimer.settings.SettingsManagement;
import com.neirx.stopwatchtimer.utility.DBHelper;

import java.util.List;

public class LapsFragment extends Fragment {
    private static final String CLASS_NAME = "<LapsFragment> ";
    private List<Lap> laps;//коллекция с массивом будильников
    static LapAdapter lapAdapter;//адаптер для создания view
    private DBHelper dbHelper; //создание объкта для работы с базой данных
    private ListView listView;
    private int stopwatchNum = 1;
    private long previousTime;
    private boolean wasClear;
    private SettingsManagement settings;

    public static LapsFragment newInstance() {
        return new LapsFragment();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("previousTime", previousTime);
        outState.putInt("stopwatchNum", stopwatchNum);
        outState.putBoolean("wasClear", wasClear);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_laps, container, false);
        dbHelper = new DBHelper(getActivity());
        laps = dbHelper.getLaps();
        lapAdapter = new LapAdapter(getActivity(), laps);
        settings = AppSettings.getInstance(getActivity());

        if(savedInstanceState != null){
            previousTime = savedInstanceState.getLong("previousTime", 0);
            stopwatchNum = savedInstanceState.getInt("stopwatchNum", 1);
            wasClear = savedInstanceState.getBoolean("wasClear", false);
        } else {
            previousTime = settings.getLongPref(SettingPref.Long.previousLapTime, 0);
        }

        listView = (ListView) rootView.findViewById(R.id.lvLaps);
        listView.setAdapter(lapAdapter);
        return rootView;
    }

    public void clearLaps(){
        dbHelper.clearLaps();
        lapAdapter.clearLapsFromList();
        settings.setPref(SettingPref.Long.previousLapTime, 0);
        wasClear = true;
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null){
            activity.getStopwatchFragment().clearLapsNum();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(MainActivity.TAG, CLASS_NAME + "onResume");
        scrollListViewToBottom();
    }

    public boolean lapsNotEmpty(){
        return lapAdapter.getCount() > 0;
    }

    public void addLap(int stopwatchNum, int timeNum, int hours, int minutes, int seconds, int millis){
        if(this.stopwatchNum != stopwatchNum || wasClear) {
            previousTime=0;
            wasClear = false;
        }

        Log.d(MainActivity.TAG, CLASS_NAME + hours+":"+minutes+":"+seconds+"."+millis);
        Lap lap = new Lap(stopwatchNum, timeNum,formatTime(hours, minutes, seconds, millis),
                formatTimeDifference(((hours*60+minutes)*60+seconds)*1000+millis));
        this.stopwatchNum = stopwatchNum;
        previousTime = ((hours*60+minutes)*60+seconds)*1000+millis;
        settings.setPref(SettingPref.Long.previousLapTime, previousTime);
        dbHelper.addLap(lap);
        lapAdapter.addLapToList(lap);
        scrollListViewToBottom();
    }

    private void scrollListViewToBottom() {
        listView.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                listView.setSelection(listView.getCount() - 1);
            }
        });
    }

    private String formatTimeDifference(long lastTimeInMillis){
        long difference = lastTimeInMillis - previousTime;
        int millisDiffer = (int) (difference % 1000);
        int secondsDiffer = (int) (difference / 1000);
        int minutesDiffer = secondsDiffer / 60;
        secondsDiffer = secondsDiffer % 60;
        int hoursDiffer = minutesDiffer / 60;
        minutesDiffer = minutesDiffer % 60;
        return formatTime(hoursDiffer, minutesDiffer, secondsDiffer, millisDiffer);
    }

    private String formatTime(int hours, int minutes, int seconds, int millis){
        StringBuilder builder = new StringBuilder();
        if(hours < 10){
            builder.append("0").append(hours);
        } else {
            builder.append(hours);
        }
        builder.append(":");
        if(minutes < 10){
            builder.append("0").append(minutes);
        } else {
            builder.append(minutes);
        }
        builder.append(":");
        if(seconds < 10){
            builder.append("0").append(seconds);
        } else {
            builder.append(seconds);
        }
        builder.append(".");
        if(millis < 10){
            builder.append("00").append(millis);
        } else if(millis < 100) {
            builder.append("0").append(millis);
        } else {
            builder.append(millis);
        }

        return builder.toString();

    }
}
