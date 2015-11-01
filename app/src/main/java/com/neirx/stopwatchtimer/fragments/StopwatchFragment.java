package com.neirx.stopwatchtimer.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.neirx.stopwatchtimer.MainActivity;
import com.neirx.stopwatchtimer.R;
import com.neirx.stopwatchtimer.settings.AppSettings;
import com.neirx.stopwatchtimer.settings.SettingPref;
import com.neirx.stopwatchtimer.settings.SettingsManagement;
import com.neirx.stopwatchtimer.utility.Stopwatch;

import java.util.Timer;
import java.util.TimerTask;


public class StopwatchFragment extends Fragment implements View.OnClickListener, Stopwatch.OnTickListener {
    private static final String CLASS_NAME = "<StopwatchFragment> ";
    private SettingsManagement settings;
    private ImageView ivSecondHand, ivMinuteHand;
    private ImageView ivDial;
    private TextView tvHours, tvMinutes, tvSeconds, tvMillis;
    private Stopwatch stopwatch;
    private LapsFragment lapsFragment;
    private MainActivity activity;
    private boolean isStopwatchRunning, wasStopwatchStart, isStopwatchClickable, incrStopwatchNum, needStartCount,
            isSoundOn;
    private long totalTime, baseTime, savedTime;
    private int countTimeNum, countStopwatchNum;
    private int secondsTime;
    private int millisTime;
    private int minutesTime;
    private int hoursTime;
    private float secDegree, minDegree;
    private Vibrator vibrator;
    private MediaPlayer startSound, stopSound, addLapSound, resetSound;

    public long getTotalTime(){
        return totalTime;
    }

    public static StopwatchFragment newInstance() {
        return new StopwatchFragment();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("countTimeNum", countTimeNum);
        outState.putInt("countStopwatchNum", countStopwatchNum);
        outState.putLong("baseTime", baseTime);
        outState.putLong("savedTime", savedTime);
        outState.putLong("totalTime", totalTime);
        outState.putFloat("secDegree", secDegree);
        outState.putFloat("minDegree", minDegree);
        outState.putBoolean("isStopwatchRunning", isStopwatchRunning);
        outState.putBoolean("wasStopwatchStart", wasStopwatchStart);
        outState.putBoolean("incrStopwatchNum", incrStopwatchNum);
        Log.d(MainActivity.TAG, CLASS_NAME + "onSaveInstanceState");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(MainActivity.TAG, CLASS_NAME + "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        settings = AppSettings.getInstance(getActivity());
        FrameLayout layoutStopwatch = (FrameLayout) rootView.findViewById(R.id.laySwStopwatch);
        layoutStopwatch.setOnClickListener(this);
        activity = (MainActivity) getActivity();
        vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
        tvHours = (TextView) rootView.findViewById(R.id.tvSwHours);
        tvMinutes = (TextView) rootView.findViewById(R.id.tvSwMinutes);
        tvSeconds = (TextView) rootView.findViewById(R.id.tvSwSeconds);
        tvMillis = (TextView) rootView.findViewById(R.id.tvMillis);
        ivSecondHand = (ImageView) rootView.findViewById(R.id.ivSwSecondHand);
        ivMinuteHand = (ImageView) rootView.findViewById(R.id.ivSwMinuteHand);
        ivDial = (ImageView) rootView.findViewById(R.id.ivDial);
        ViewTreeObserver vto = ivDial.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                int stopwatchHeight = ivDial.getHeight();
                Log.d(MainActivity.TAG, CLASS_NAME + "stopwatchHeight = " + stopwatchHeight);
                int minHeight = (int) (stopwatchHeight / 1.4938);
                int minWidth = (int) (minHeight / 11.85);
                Log.d(MainActivity.TAG, CLASS_NAME + "minWidth = " + minWidth + " minHeight = " + minHeight);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(minWidth, minHeight);
                params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
                ivMinuteHand.setLayoutParams(params);

                ViewTreeObserver obs = ivDial.getViewTreeObserver();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    obs.removeOnGlobalLayoutListener(this);
                } else {
                    obs.removeGlobalOnLayoutListener(this);
                }
            }

        });

        isSoundOn = ((MainActivity) getActivity()).isSoundOn();
        ((MainActivity) getActivity()).setSoundStateListener(new MainActivity.SoundStateListener() {
            @Override
            public void onChangeState(boolean state) {
                isSoundOn = state;
            }
        });
        startSound = MediaPlayer.create(getActivity(), R.raw.sw_start_btn);
        stopSound = MediaPlayer.create(getActivity(), R.raw.sw_stop_btn);
        addLapSound = MediaPlayer.create(getActivity(), R.raw.sw_addlap_btn);
        resetSound = MediaPlayer.create(getActivity(), R.raw.sw_reset_btn);

        stopwatch = new Stopwatch(this);

        if (savedInstanceState == null) {
            countTimeNum = settings.getIntPref(SettingPref.Int.countTimeNum);
            countStopwatchNum = settings.getIntPref(SettingPref.Int.countStopwatchNum, 0);
            baseTime = settings.getLongPref(SettingPref.Long.stopwatchBaseTime, -1);
            savedTime = settings.getLongPref(SettingPref.Long.stopwatchSavedTime, 0);
            wasStopwatchStart = settings.getBoolPref(SettingPref.Bool.wasStopwatchStart, false);
            incrStopwatchNum = settings.getBoolPref(SettingPref.Bool.incrStopwatchNum, true);
            if (savedTime > 0) {
                stopwatch.setSavedTime(savedTime);
            }
            if (baseTime > -1) {
                stopwatch.setBaseTime(baseTime);
                isStopwatchRunning = true;
            } else {
                isStopwatchRunning = false;
            }
            if (isStopwatchRunning) {
                long curTime = System.currentTimeMillis() - baseTime + savedTime;
                secDegree = (curTime * 360f) / 60000f;
                minDegree = (curTime * 360f) / 1800000;
                ivSecondHand.setRotation(secDegree);
                ivMinuteHand.setRotation(minDegree);
                startCount();
            } else {
                totalTime = savedTime;
                secDegree = (totalTime * 360f) / 60000f;
                minDegree = (totalTime * 360f) / 1800000;
                ivSecondHand.setRotation(secDegree);
                ivMinuteHand.setRotation(minDegree);
                parseTotalTime(true);
            }
        } else {
            Log.d(MainActivity.TAG, CLASS_NAME + "savedInstanceState != null");
            isStopwatchRunning = savedInstanceState.getBoolean("isStopwatchRunning", false);
            wasStopwatchStart = savedInstanceState.getBoolean("wasStopwatchStart", false);
            incrStopwatchNum = savedInstanceState.getBoolean("incrStopwatchNum", true);
            secDegree = savedInstanceState.getFloat("secDegree", 0);
            minDegree = savedInstanceState.getFloat("minDegree", 0);
            countTimeNum = savedInstanceState.getInt("countTimeNum", 0);
            countStopwatchNum = savedInstanceState.getInt("countStopwatchNum", 0);
            baseTime = savedInstanceState.getLong("baseTime", -1);
            savedTime = savedInstanceState.getLong("savedTime", 0);
            ivSecondHand.setRotation(secDegree);
            ivMinuteHand.setRotation(minDegree);
            if (savedTime > 0) {
                stopwatch.setSavedTime(savedTime);
            }
            if (isStopwatchRunning && baseTime != -1) {
                stopwatch.setBaseTime(baseTime);
                startCount();
            } else {
                totalTime = savedTime;
                parseTotalTime(true);
            }
        }
        Log.d(MainActivity.TAG, CLASS_NAME + "countStopwatchNum = " + countStopwatchNum);
        needStartCount = false;
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(MainActivity.TAG, CLASS_NAME + "onStart");
        isStopwatchClickable = settings.getBoolPref(SettingPref.Bool.isDialClickable, true);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(MainActivity.TAG, CLASS_NAME + "onResume");
        if (needStartCount && isStopwatchRunning) {
            startCount();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        stopwatch.release();
        needStartCount = true;
        Log.d(MainActivity.TAG, CLASS_NAME + "onStop");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.laySwStopwatch:
                if (isStopwatchClickable) {
                    switchStopwatch();
                }
                break;
        }
    }

    private void startEffect() {
        boolean sound = settings.getBoolPref(SettingPref.Bool.soundState);
        boolean vibrate = settings.getBoolPref(SettingPref.Bool.vibrateState);
        if (vibrate) {
            vibrator.vibrate(25);
        }
    }

    private void stopEffect() {
        boolean sound = settings.getBoolPref(SettingPref.Bool.soundState);
        boolean vibrate = settings.getBoolPref(SettingPref.Bool.vibrateState);
        if (vibrate) {
            vibrator.vibrate(20);
        }
    }

    private void resetEffect() {
        boolean sound = settings.getBoolPref(SettingPref.Bool.soundState);
        boolean vibrate = settings.getBoolPref(SettingPref.Bool.vibrateState);
        if (vibrate) {
            vibrator.vibrate(70);
        }
    }

    private void addLapEffect() {
        boolean sound = settings.getBoolPref(SettingPref.Bool.soundState);
        boolean vibrate = settings.getBoolPref(SettingPref.Bool.vibrateState);
        if (vibrate) {
            vibrator.vibrate(30);
        }
    }

    /**
     * Добавление нового круга в LapsFragment
     */
    public void addNewLap() {
        if (isStopwatchRunning) {
            if (incrStopwatchNum) {
                countStopwatchNum++;
                incrStopwatchNum = false;
                settings.setPref(SettingPref.Bool.incrStopwatchNum, false);
                settings.setPref(SettingPref.Int.countStopwatchNum, countStopwatchNum);
            }
            countTimeNum++;
            addLapEffect();
            settings.setPref(SettingPref.Int.countTimeNum, countTimeNum);
            if (lapsFragment == null) {
                if (activity != null) {
                    lapsFragment = activity.getLapsFragment();
                }
            }
            if (lapsFragment != null) {
                if(isSoundOn) addLapSound.start();
                lapsFragment.addLap(countStopwatchNum, countTimeNum, hoursTime, minutesTime, secondsTime, millisTime);
            }
        }
    }

    public void clearLapsNum() {
        countTimeNum = 0;
        countStopwatchNum = 0;
        incrStopwatchNum = true;
        settings.setPref(SettingPref.Bool.incrStopwatchNum, true);
        settings.setPref(SettingPref.Int.countTimeNum, countTimeNum);
        settings.setPref(SettingPref.Int.countStopwatchNum, countStopwatchNum);
    }

    /**
     * Действия по нажатию кнопки сброса. Проигрывается обратная анимация стрелок до нуля.
     * Обнуляется в настройках значение пройденного время в миллисекундах и значение начала отсчета секундомера.
     */
    public void stopwatchReset() {
        if (!wasStopwatchStart) return;
        wasStopwatchStart = false;
        countTimeNum = 0;
        stopwatch.reset();
        if(isSoundOn) resetSound.start();
        resetTimeView();
        isStopwatchRunning = false;
        totalTime = 0;
        baseTime = -1;
        savedTime = 0;
        incrStopwatchNum = true;
        resetEffect();
        settings.setPref(SettingPref.Bool.incrStopwatchNum, true);
        settings.setPref(SettingPref.Bool.wasStopwatchStart, wasStopwatchStart);
        settings.setPref(SettingPref.Int.countTimeNum, countTimeNum);
        settings.setPref(SettingPref.Int.countStopwatchNum, countStopwatchNum);
        settings.setPref(SettingPref.Long.stopwatchBaseTime, baseTime);
        settings.setPref(SettingPref.Long.stopwatchSavedTime, savedTime);
        activity.clickedStopwatch(isStopwatchRunning);
        resetStopwatchAnimation();
    }

    /**
     * Определение, что нужно сделать по нажатию кнопки старт/стоп:
     * - начать отсчет сначала;
     * - поставить секундомер на паузу;
     * - продолжить отсчет после паузы.
     */
    public void switchStopwatch() {
        if (!isStopwatchRunning) {
            isStopwatchRunning = true;
            startEffect();
            startCount();
            if(isSoundOn) startSound.start();
        } else {
            isStopwatchRunning = false;
            stopEffect();
            pauseCount();
            if(isSoundOn) stopSound.start();
        }
        activity.clickedStopwatch(isStopwatchRunning);
    }

    /**
     * Включается отсчет времени каждую миллисекунду в другом потоке.
     * Сохраняется в настройках текущее системное время в Unix-формате.
     */
    private void startCount() {
        stopwatch.start();
        if (!wasStopwatchStart) wasStopwatchStart = true;
        baseTime = stopwatch.getBaseTime();
        settings.setPref(SettingPref.Bool.wasStopwatchStart, true);
        settings.setPref(SettingPref.Long.stopwatchBaseTime, baseTime);
        Log.d(MainActivity.TAG, CLASS_NAME + "startCount");
    }

    /**
     * Остановка секундомера. Сохраняется в настройках уже пройденное время в миллисекундах.
     * Обнуляется значение начала отсчета секундомера.
     */
    private void pauseCount() {
        stopwatch.stop();
        baseTime = -1;
        savedTime = stopwatch.getSavedTime();
        settings.setPref(SettingPref.Long.stopwatchBaseTime, baseTime);
        settings.setPref(SettingPref.Long.stopwatchSavedTime, savedTime);
        Log.d(MainActivity.TAG, CLASS_NAME + "pauseCount");
    }

    @Override
    public void onTick(long millis) {
        if (!isStopwatchRunning) return;
        totalTime = millis;
        //if(millis % 1000 == 0) Log.d(Statical.TAG, CLASS_NAME+"thread = "+Thread.currentThread().getName());
        secDegree = (millis * 360f) / 60000f;
        minDegree = (millis * 360f) / 1800000f;
        activity.runOnUiThread(new Runnable() {
            public void run() {
                parseTotalTime(false);
                ivSecondHand.setRotation(secDegree);
                ivMinuteHand.setRotation(minDegree);
            }
        });

    }

    private void resetStopwatchAnimation() {
        while (secDegree >= 360) {
            secDegree -= 360;
        }
        while (minDegree >= 360) {
            minDegree -= 360;
        }
        int div = getDiv(secDegree, minDegree);
        float secDiv = (secDegree / div);
        float minDiv = (minDegree / div);
        final float secSubtract = secDiv > 0 ? secDiv : 1;
        final float minSubtract = minDiv > 0 ? minDiv : 1;
        final Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        boolean secReset = false;
                        boolean minReset = false;
                        if (secDegree > 0) {
                            secDegree -= secSubtract;
                        }
                        if (secDegree <= 0) {
                            secDegree = 0;
                            secReset = true;
                        }
                        ivSecondHand.setRotation(secDegree);

                        if (minDegree > 0) {
                            minDegree -= minSubtract;
                        }
                        if (minDegree <= 0) {
                            minDegree = 0;
                            minReset = true;
                        }
                        ivMinuteHand.setRotation(minDegree);
                        if (secReset && minReset) {
                            timer.cancel();
                        }
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(task, 20, 20);
    }

    private int getDiv(float a, float b) {
        float x = a > b ? a : b;
        int y = 6;
        if (x > 90) y = 7;
        if (x > 180) y = 8;
        if (x > 270) y = 9;
        int div = (int) (x / y);
        return div > 0 ? div : 1;
    }

    /**
     * -----------Методы установки значений времени в текстовые поля----------
     */
    private void setMillis() {
        if (millisTime < 10) {
            tvMillis.setText("00" + millisTime);
        } else if (millisTime < 100) {
            tvMillis.setText("0" + millisTime);
        } else {
            tvMillis.setText("" + millisTime);
        }
    }

    private void setSeconds() {
        if (secondsTime < 10) {
            tvSeconds.setText("0" + secondsTime);
        } else {
            tvSeconds.setText("" + secondsTime);
        }
    }

    private void setMinutes() {
        if (minutesTime < 10) {
            tvMinutes.setText("0" + minutesTime);
        } else {
            tvMinutes.setText("" + minutesTime);
        }
    }

    private void setHours() {
        if (hoursTime < 10) {
            tvHours.setText("0" + hoursTime);
        } else {
            tvHours.setText("" + hoursTime);
        }
    }

    private void parseTotalTime(boolean isFirst) {
        int hours = (int) (totalTime / (60 * 60 * 1000));
        if (hours > hoursTime || isFirst) {
            hoursTime = hours;
            setHours();
        }
        int restHours = (int) (totalTime % (60 * 60 * 1000));
        int minutes = restHours / (60 * 1000);
        if (minutes > minutesTime || millisTime >= 59 || isFirst) {
            minutesTime = minutes;
            setMinutes();
        }
        int restMinutes = restHours % (60 * 1000);
        int seconds = restMinutes / 1000;
        if (seconds > secondsTime || secondsTime >= 59 || isFirst) {
            secondsTime = seconds;
            setSeconds();
        }
        millisTime = restMinutes % 1000;
        setMillis();
    }

    /**
     * Обнуление значений секундомера и текстовых полей. Отмена таймера.
     */
    private void resetTimeView() {
        hoursTime = 0;
        minutesTime = 0;
        secondsTime = 0;
        millisTime = 0;
        tvHours.setText("00");
        tvMinutes.setText("00");
        tvSeconds.setText("00");
        tvMillis.setText("000");
        Log.d(MainActivity.TAG, CLASS_NAME + "resetCount");
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

    /*
    @Override
    public void onStart() {
        super.onStart();
        Log.d(MainActivity.TAG, CLASS_NAME + "onStart");
        isStopwatchClickable = settings.getBoolPref(SettingPref.Bool.isDialClickable, true);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(MainActivity.TAG, CLASS_NAME + "onResume");
    }*/

    @Override
    public void onPause() {
        super.onPause();
        Log.d(MainActivity.TAG, CLASS_NAME + "onPause");
    }

    /*@Override
    public void onStop() {
        super.onStop();
        Log.d(MainActivity.TAG, CLASS_NAME + "onStop");
    }*/

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
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(MainActivity.TAG, CLASS_NAME + "onViewStateRestored");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(MainActivity.TAG, CLASS_NAME + "onDetach");
    }
    //-------------------- Методы жизненного цикла(END) --------------------*/

}
