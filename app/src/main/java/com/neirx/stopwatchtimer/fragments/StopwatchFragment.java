package com.neirx.stopwatchtimer.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    SettingsManagement settings;
    ImageView ivSecondHand, ivMinuteHand;
    ImageView ivDial;
    TextView tvHours, tvMinutes, tvSeconds, tvMillis;
    Stopwatch stopwatch;
    LapsFragment lapsFragment;
    MainActivity activity;
    boolean isStopwatchRunning, isStopwatchStart, isStopwatchClickable, wasAddLap;
    long totalTime, baseTime, savedTime;
    int countTimeNum, countStopwatchNum = 1;
    int secondsTime;
    int millisTime;
    int minutesTime;
    int hoursTime;
    float secDegree, minDegree;


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
                int minHeight = (int) (stopwatchHeight / 1.4949);
                int minWidth = (int) (minHeight / 18.96);
                Log.d(MainActivity.TAG, CLASS_NAME + "minWidth = " + minWidth + "minHeight = " + minHeight);
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


        isStopwatchClickable = settings.getBoolPref(SettingPref.Bool.isDialClickable, true);

        stopwatch = new Stopwatch(this);

        if (savedInstanceState == null) {
            countTimeNum = settings.getIntPref(SettingPref.Int.countTimeNum);
            countStopwatchNum = settings.getIntPref(SettingPref.Int.countStopwatchNum, 1);
            baseTime = settings.getLongPref(SettingPref.Long.stopwatchBaseTime, -1);
            savedTime = settings.getLongPref(SettingPref.Long.stopwatchSavedTime, 0);
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
            secDegree = savedInstanceState.getFloat("secDegree", 0);
            minDegree = savedInstanceState.getFloat("minDegree", 0);
            countTimeNum = savedInstanceState.getInt("countTimeNum", 0);
            countStopwatchNum = savedInstanceState.getInt("countStopwatchNum", 1);
            baseTime = savedInstanceState.getLong("baseTime", -1);
            savedTime = savedInstanceState.getLong("savedTime", 0);
            ivSecondHand.setRotation(secDegree);
            ivMinuteHand.setRotation(minDegree);
            if (savedTime > 0) {
                stopwatch.setSavedTime(savedTime);
            }
            if (isStopwatchRunning && baseTime != -1) {
                stopwatch.setBaseTime(baseTime);
                stopwatch.start();
            } else {
                totalTime = savedTime;
                parseTotalTime(true);
            }
        }
        Log.d(MainActivity.TAG, CLASS_NAME + "countStopwatchNum = "+ countStopwatchNum);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.laySwStopwatch:
                if (isStopwatchClickable) {
                    switchStopwatch();
                }
                if (activity != null) {
                    activity.clickedStopwatch();
                }
                break;
        }
    }

    /**
     * Добавление нового круга в LapsFragment
     */
    public void addNewLap() {
        if (isStopwatchRunning) {
            countTimeNum++;
            settings.setPref(SettingPref.Int.countTimeNum, countTimeNum);
            if (lapsFragment == null) {
                if (activity != null) {
                    lapsFragment = activity.getLapsFragment();
                }
            }
            if (lapsFragment != null) {
                lapsFragment.addLap(countStopwatchNum, countTimeNum, hoursTime, minutesTime, secondsTime, millisTime);
                wasAddLap = true;
            }
        }
    }

    public void clearLapsNum() {
        countTimeNum = 0;
        countStopwatchNum = 1;
        settings.setPref(SettingPref.Int.countTimeNum, countTimeNum);
        settings.setPref(SettingPref.Int.countStopwatchNum, countStopwatchNum);
    }
        /**
         * Действия по нажатию кнопки сброса. Проигрывается обратная анимация стрелок до нуля.
         * Обнуляется в настройках значение пройденного время в миллисекундах и значение начала отсчета секундомера.
         */
    public void stopwatchReset() {
        if(!isStopwatchStart) return;
        isStopwatchStart = false;
        countStopwatchNum++;
        countTimeNum = 0;
        stopwatch.reset();
        resetTimeView();
        isStopwatchRunning = false;
        totalTime = 0;
        baseTime = -1;
        savedTime = 0;
        //secDegree = 0;
        settings.setPref(SettingPref.Int.countTimeNum, countTimeNum);
        settings.setPref(SettingPref.Int.countStopwatchNum, countStopwatchNum);
        settings.setPref(SettingPref.Long.stopwatchBaseTime, baseTime);
        settings.setPref(SettingPref.Long.stopwatchSavedTime, savedTime);
        //ivSecondHand.setRotation(0);
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
            startCount();
        } else {
            isStopwatchRunning = false;
            pauseCount();
        }
    }

    /**
     * Включается отсчет времени каждую миллисекунду в другом потоке.
     * Сохраняется в настройках текущее системное время в Unix-формате.
     */
    private void startCount() {
        stopwatch.start();
        isStopwatchStart = true;
        baseTime = stopwatch.getBaseTime();
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
        float secDiv = (secDegree/div);
        float minDiv = (minDegree/div);
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
        if(x > 90) y = 7;
        if(x > 180) y = 8;
        if(x > 270) y = 9;
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

    /*/-------------------- Методы жизненного цикла(BEGIN) --------------------
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
        stopwatch.release();
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
