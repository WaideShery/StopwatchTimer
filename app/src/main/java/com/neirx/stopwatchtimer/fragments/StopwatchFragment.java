package com.neirx.stopwatchtimer.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.neirx.stopwatchtimer.MainActivity;
import com.neirx.stopwatchtimer.R;
import com.neirx.stopwatchtimer.custom.HandRotateAnimation;
import com.neirx.stopwatchtimer.settings.AppSettings;
import com.neirx.stopwatchtimer.settings.SettingPref;
import com.neirx.stopwatchtimer.settings.SettingsManagement;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;


public class StopwatchFragment extends Fragment implements View.OnClickListener {
    private static final String CLASS_NAME = "<StopwatchFragment> ";
    SettingsManagement settings;
    HandRotateAnimation animation;
    Animation reverseAnimation;
    ImageView ivSecondHand;
    TextView tvHours, tvMinutes, tvSeconds, tvMillis;
    boolean isStopwatchRunning;
    boolean isStopwatchPause;
    Timer timer;
    MainActivity activity;
    long totalTime;
    long countTime;
    long saveTime;
    int countTimeNum = 0;
    int secondsTime = 0;
    int millisTime = 0;
    int minutesTime = 0;
    int hoursTime = 0;
    int syncPeriod;
    boolean stopCountdown, isStopwatchClickable;
    LapsFragment lapsFragment;

    public static StopwatchFragment newInstance() {
        return new StopwatchFragment();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("countTimeNum", countTimeNum);
        outState.putLong("countTime", countTime);
        outState.putLong("saveTime", saveTime);
        outState.putLong("totalTime", totalTime);
        outState.putInt("millisTime", millisTime);
        outState.putInt("secondsTime", secondsTime);
        outState.putInt("minutesTime", minutesTime);
        outState.putInt("hoursTime", hoursTime);
        outState.putBoolean("isStopwatchRunning", isStopwatchRunning);
        outState.putBoolean("isStopwatchPause", isStopwatchPause);
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

        isStopwatchClickable = settings.getBoolPref(SettingPref.Bool.isDialClickable, true);

        if (savedInstanceState == null) {
            long differentTime = 0;
            countTime = settings.getLongPref(SettingPref.Long.stopwatchCountTime);
            saveTime = settings.getLongPref(SettingPref.Long.stopwatchSaveTime);
            if (countTime != 0) {
                differentTime = System.currentTimeMillis() - countTime;
                isStopwatchRunning = true;
            } else if (saveTime != 0) {
                isStopwatchPause = true;
            }
            totalTime = saveTime = differentTime + saveTime;
            parseTotalTime();
        } else {
            Log.d(MainActivity.TAG, CLASS_NAME + "savedInstanceState != null");
            countTimeNum = savedInstanceState.getInt("countTimeNum", 0);
            countTime = savedInstanceState.getLong("countTime", 0);
            saveTime = savedInstanceState.getLong("saveTime", 0);
            totalTime = savedInstanceState.getLong("totalTime", 0);
            millisTime = savedInstanceState.getInt("millisTime", 0);
            secondsTime = savedInstanceState.getInt("secondsTime", 0);
            minutesTime = savedInstanceState.getInt("minutesTime", 0);
            hoursTime = savedInstanceState.getInt("hoursTime", 0);
            isStopwatchRunning = savedInstanceState.getBoolean("isStopwatchRunning", false);
            isStopwatchPause = savedInstanceState.getBoolean("isStopwatchPause", false);
            setMillis();
            setSeconds();
            setMinutes();
            setHours();
        }
        animInit((minutesTime*60+secondsTime)*1000+millisTime);
        if (isStopwatchRunning) {
            ivSecondHand.startAnimation(animation);
            stopCountdown = false;
            startCount();
        }


        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.laySwStopwatch:
                if (isStopwatchClickable) {
                    stopwatchStartStop();
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
            if (lapsFragment == null) {
                if (activity != null) {
                    lapsFragment = activity.getLapsFragment();
                }
            }
            if (lapsFragment != null) {
                lapsFragment.addLap(countTimeNum, hoursTime, minutesTime, secondsTime, millisTime);
            }
        }
    }

    /**
     * Действия по нажатию кнопки сброса. Проигрывается обратная анимация стрелок до нуля.
     * Обнуляется в настройках значение пройденного время в миллисекундах и значение начала отсчета секундомера.
     */
    public void stopwatchReset() {
        countTimeNum = 0;
        if (animation != null) {
            if (!animation.isPaused()) {
                animation.pause();
            }
            long curPosTime = animation.getElapsedAtPause();
            float startPos = (float) (0.006 * curPosTime);
            reverseAnimation = new RotateAnimation(startPos, 0f, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            reverseAnimation.setDuration((long) (startPos * 1.5));
            animation.cancel();
            animation = null;
            stopCountdown = true;
            resetCount();
            ivSecondHand.startAnimation(reverseAnimation);
            isStopwatchRunning = false;
            isStopwatchPause = false;
            totalTime = 0;
            saveTime = 0;
            settings.setPref(SettingPref.Long.stopwatchCountTime, 0);
            settings.setPref(SettingPref.Long.stopwatchSaveTime, 0);
        }
    }

    /**
     * Определение, что нужно сделать по нажатию кнопки старт/стоп:
     * - начать отсчет сначала;
     * - поставить секундомер на паузу;
     * - продолжить отсчет после паузы.
     */
    public void stopwatchStartStop() {
        if (!isStopwatchRunning && !isStopwatchPause) {
            stopCountdown = false;
            animInit(0);
            ivSecondHand.startAnimation(animation);
            isStopwatchRunning = true;
            startCount();
        } else if (isStopwatchRunning) {
            animation.pause();
            isStopwatchPause = true;
            isStopwatchRunning = false;
            stopCountdown = true;
            pauseCount();
        } else {
            animation.resume();
            isStopwatchPause = false;
            isStopwatchRunning = true;
            stopCountdown = false;
            startCount();
        }
    }

    /**
     * Подготовка анимации, установка начальных значений.
     */
    private void animInit(long startTime) {
        Log.d(MainActivity.TAG, "anim start time = " + startTime);
        animation = new HandRotateAnimation(0f, 360f, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(60000);
        animation.setStartTime(startTime);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
    }

    /**
     * Включается отсчет времени каждую миллисекунду в другом потоке.
     * Сохраняется в настройках текущее системной время в Unix-формате.
     */
    private void startCount() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        counter();
                    }
                });
            }
        };
        timer = new Timer();
        timer.scheduleAtFixedRate(task, 13, 13);
        countTime = System.currentTimeMillis();
        settings.setPref(SettingPref.Long.stopwatchCountTime, countTime);
        Log.d(MainActivity.TAG, CLASS_NAME + "startCount");
    }

    /**
     * Остановка секундомера. Сохраняется в настройках уже пройденное время в миллисекундах.
     * Обнуляется значение начала отсчета секундомера.
     */
    private void pauseCount() {
        if (timer != null) {
            timer.cancel();
        }
        settings.setPref(SettingPref.Long.stopwatchCountTime, 0);
        totalTime = saveTime = System.currentTimeMillis() - countTime + saveTime;
        settings.setPref(SettingPref.Long.stopwatchSaveTime, saveTime);
        Log.d(MainActivity.TAG, CLASS_NAME + "pauseCount, totalTime = " +saveTime);
    }

    /**
     * Обнуление значений секундомера и текстовых полей. Отмена таймера.
     */
    private void resetCount() {
        if (timer != null) {
            timer.cancel();
        }
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

    private void counter() {
        if(syncPeriod >= 5){
            //totalTime = System.currentTimeMillis() - countTime + saveTime;
            //parseTotalTime();
            syncPeriod = 0;
        }
        if(stopCountdown) return;;
        millisTime += 13;
        if (millisTime < 1000) {
            setMillis();
        } else {
            syncPeriod++;
            secondsTime++;
            millisTime = 0;
            setMillis();
            if (secondsTime < 60) {
                setSeconds();
            } else {
                minutesTime++;
                secondsTime = 0;
                setSeconds();
                if (minutesTime < 60) {
                    setMinutes();
                } else {
                    hoursTime++;
                    minutesTime = 0;
                    setMinutes();
                    if (hoursTime < 100) {
                        setHours();
                    }
                }
            }
        }
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
        Log.d(MainActivity.TAG, CLASS_NAME + "secondsTime = " + secondsTime);
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

    private void parseTotalTime() {
        hoursTime = (int) (totalTime / (60 * 60 * 1000));
        int restHours = (int) (totalTime % (60 * 60 * 1000));
        minutesTime = restHours / (60 * 1000);
        int restMinutes = restHours % (60 * 1000);
        secondsTime = restMinutes / 1000;
        Log.d(MainActivity.TAG, CLASS_NAME + "parseTotalTime() secondsTime = " + secondsTime);
        millisTime = restMinutes % 1000;
        setMillis();
        setSeconds();
        setMinutes();
        setHours();
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
        if(timer != null) timer.cancel();
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
