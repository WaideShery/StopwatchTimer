package com.neirx.stopwatchtimer.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.neirx.stopwatchtimer.MainActivity;
import com.neirx.stopwatchtimer.R;
import com.neirx.stopwatchtimer.settings.AppSettings;
import com.neirx.stopwatchtimer.settings.SettingPref;
import com.neirx.stopwatchtimer.settings.SettingsManagement;


public class BottomMenuFragment extends Fragment implements View.OnClickListener {
    private static final String CLASS_NAME = "<BottomMenuFragment> ";
    Button btnNewLap, btnStopwatchReset, btnStopwatchStartStop;
    boolean isStopwatchRun;
    SettingsManagement settings;


    public static BottomMenuFragment newInstance() {
        return new BottomMenuFragment();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isStopwatchRun", isStopwatchRun);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bottom_menu, container, false);
        settings = AppSettings.getInstance(getActivity());
        //поиск ресурсов кнопок и установка обработчиков нажатий
        btnStopwatchReset = (Button) rootView.findViewById(R.id.btnStopwatchReset);
        btnStopwatchReset.setOnClickListener(this);
        btnNewLap = (Button) rootView.findViewById(R.id.btnNewLap);
        btnNewLap.setOnClickListener(this);
        btnStopwatchStartStop = (Button) rootView.findViewById(R.id.btnStopwatchStartStop);
        btnStopwatchStartStop.setOnClickListener(this);


        //определение, запущен ли в данный момент секундомер
        if(savedInstanceState != null){
            isStopwatchRun = savedInstanceState.getBoolean("isStopwatchRun");
        } else {
            isStopwatchRun = settings.getBoolPref(SettingPref.Bool.isStopwatchRun);
        }

        convertStartStop();
        return rootView;
    }

    /**
     * Метод для переключения состояний кнопок в нижнем меню при нажатии на view Секундомера
     */
    public void clickedStopwatch(boolean isRun){
        isStopwatchRun = isRun;
        convertStartStop();
    }

    /**
     * Переключение значений кнопки Стар/Стоп
     */
    private void convertStartStop(){
        //соханение переменной isStopwatchRun(запущен ли секундомер) в файле настроек
        settings.setPref(SettingPref.Bool.isStopwatchRun, isStopwatchRun);
        //переключение текста кнопки старт/стоп
        if(isStopwatchRun){
            btnStopwatchStartStop.setText(R.string.stop);
        } else {
            btnStopwatchStartStop.setText(R.string.start);
        }
    }

    @Override
    public void onClick(View v) {
        MainActivity activity = (MainActivity) getActivity();
        StopwatchFragment stopwatchFragment = activity.getStopwatchFragment();
        switch (v.getId()){
            case R.id.btnStopwatchReset:
                isStopwatchRun = false;
                convertStartStop();
                stopwatchFragment.stopwatchReset();
                break;
            case R.id.btnNewLap:
                stopwatchFragment.addNewLap();
                break;
            case R.id.btnStopwatchStartStop:
                isStopwatchRun = !isStopwatchRun;
                convertStartStop();
                stopwatchFragment.switchStopwatch();
                break;
        }
    }


    //-------------------- Методы жизненного цикла(BEGIN) --------------------
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(MainActivity.TAG, CLASS_NAME + "onAttach");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(MainActivity.TAG, CLASS_NAME + "onCreate");
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(MainActivity.TAG, CLASS_NAME + "onActivityCreated");
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.d(MainActivity.TAG, CLASS_NAME + "onStart");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(MainActivity.TAG, CLASS_NAME + "onResume");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d(MainActivity.TAG, CLASS_NAME + "onPause");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.d(MainActivity.TAG, CLASS_NAME + "onStop");
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(MainActivity.TAG, CLASS_NAME + "onDestroyView");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(MainActivity.TAG, CLASS_NAME + "onDestroy");
    }
    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(MainActivity.TAG, CLASS_NAME + "onDetach");
    }
    //-------------------- Методы жизненного цикла(END) --------------------*/
}
