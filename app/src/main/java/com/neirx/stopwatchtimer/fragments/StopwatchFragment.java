package com.neirx.stopwatchtimer.fragments;

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

import java.util.Timer;
import java.util.TimerTask;


public class StopwatchFragment extends Fragment implements View.OnClickListener {
    private static final String CLASS_NAME = "<StopwatchFragment> ";
    HandRotateAnimation animation;
    Animation reverseAnimation;
    ImageView ivSecondHand;
    TextView tvHours, tvMinutes, tvSeconds, tvMillis;
    boolean isStopwatchStart = false;
    boolean isStopwatchPause = false;
    Timer timer;
    MainActivity activity;
    int countTimeNum = 0;
    int secondTime = 0;
    int millisTime = 0;
    int minutesTime = 0;
    int hoursTime = 0;
    boolean reseted, isStopwatchClickable;
    LapsFragment lapsFragment;

    public static StopwatchFragment newInstance() {
        return new StopwatchFragment();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("countTimeNum", countTimeNum);
        outState.putInt("secondTime", secondTime);
        outState.putInt("millisTime", millisTime);
        outState.putInt("minutesTime", minutesTime);
        outState.putInt("hoursTime", hoursTime);
        outState.putBoolean("isStopwatchStart", isStopwatchStart);
        outState.putBoolean("isStopwatchPause", isStopwatchPause);
        outState.putBoolean("reseted", reseted);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(MainActivity.TAG, CLASS_NAME + "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        FrameLayout layoutStopwatch = (FrameLayout) rootView.findViewById(R.id.laySwStopwatch);
        layoutStopwatch.setOnClickListener(this);
        activity = (MainActivity) getActivity();
        tvHours = (TextView) rootView.findViewById(R.id.tvSwHours);
        tvMinutes = (TextView) rootView.findViewById(R.id.tvSwMinutes);
        tvSeconds = (TextView) rootView.findViewById(R.id.tvSwSeconds);
        tvMillis = (TextView) rootView.findViewById(R.id.tvMillis);
        ivSecondHand = (ImageView) rootView.findViewById(R.id.ivSwSecondHand);
        if (savedInstanceState != null) {
            Log.d(MainActivity.TAG, CLASS_NAME + "savedInstanceState != null");
            countTimeNum = savedInstanceState.getInt("countTimeNum");
            secondTime = savedInstanceState.getInt("secondTime");
            millisTime = savedInstanceState.getInt("millisTime");
            minutesTime = savedInstanceState.getInt("minutesTime");
            hoursTime = savedInstanceState.getInt("hoursTime");
            isStopwatchStart = savedInstanceState.getBoolean("isStopwatchStart");
            isStopwatchPause = savedInstanceState.getBoolean("isStopwatchPause");
            reseted = savedInstanceState.getBoolean("reseted");
            setMillis();
            setSeconds();
            setMinutes();
            setHours();
            if(isStopwatchStart && !isStopwatchPause) {
                animInit(secondTime*minutesTime);
                ivSecondHand.startAnimation(animation);
                startCount();
            }
        }
        isStopwatchClickable = true;

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

    public void addNewLap() {
        if (isStopwatchStart) {
            countTimeNum++;
            if (lapsFragment == null) {
                if (activity != null) {
                    lapsFragment = activity.getLapsFragment();
                }
            }
            if (lapsFragment != null) {
                lapsFragment.addLap(countTimeNum, hoursTime, minutesTime, secondTime, millisTime);
            }
        }
    }

    public void stopwatchReset() {
        reseted = true;
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
            resetCount();
            ivSecondHand.startAnimation(reverseAnimation);
            isStopwatchStart = false;
            isStopwatchPause = false;
        }
    }

    public void stopwatchStartStop() {
        if (!isStopwatchStart) {
            reseted = false;
            animInit(0);
            ivSecondHand.startAnimation(animation);
            isStopwatchStart = true;
            startCount();
        } else if (!isStopwatchPause) {
            animation.pause();
            isStopwatchPause = true;
            pauseCount();
        } else {
            animation.resume();
            isStopwatchPause = false;
            startCount();
        }
    }

    private void animInit(long startTime){
        animation = new HandRotateAnimation(0f, 360f, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(60000);
        animation.setStartTime(startTime);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatMode(Animation.RESTART);
    }

    private void startCount() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (activity != null) {
                    activity.runOnUiThread(setTimeText);
                }
            }
        };
        timer = new Timer();
        timer.scheduleAtFixedRate(task, 1, 1);
        Log.d(MainActivity.TAG, CLASS_NAME + "startCount");
    }


    private void pauseCount() {
        if (timer != null) {
            timer.cancel();
        }
        Log.d(MainActivity.TAG, CLASS_NAME + "pauseCount");
    }

    private void resetCount() {
        if (timer != null) {
            timer.cancel();
        }
        hoursTime = 0;
        minutesTime = 0;
        secondTime = 0;
        millisTime = 0;
        tvHours.setText("00");
        tvMinutes.setText("00");
        tvSeconds.setText("00");
        tvMillis.setText("000");
        Log.d(MainActivity.TAG, CLASS_NAME + "resetCount");
    }


    Runnable setTimeText = new Runnable() {
        public void run() {
            counter();
        }
    };


    private void counter() {
        if (!reseted) {
            millisTime++;
            if (millisTime < 1000) {
                setMillis();
            } else {
                secondTime++;
                millisTime = 0;
                setMillis();
                if (secondTime < 60) {
                    setSeconds();
                } else {
                    minutesTime++;
                    secondTime = 0;
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
    }

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
        Log.d(MainActivity.TAG, CLASS_NAME + "secondTime = " +secondTime);
        if (secondTime < 10) {
            Log.d(MainActivity.TAG, CLASS_NAME + "secondTime < 10");
            tvSeconds.setText("0" + secondTime);
        } else {
            Log.d(MainActivity.TAG, CLASS_NAME + "secondTime >= 10");
            tvSeconds.setText("" + secondTime);
        }
    }

    private void setMinutes(){
        if (minutesTime < 10) {
            tvMinutes.setText("0" + minutesTime);
        } else {
            tvMinutes.setText("" + minutesTime);
        }
    }

    private void setHours(){
        if (hoursTime < 10) {
            tvHours.setText("0" + hoursTime);
        } else {
            tvHours.setText("" + hoursTime);
        }
    }

    /*/-------------------- ћетоды жизненного цикла(BEGIN) --------------------
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
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(MainActivity.TAG, CLASS_NAME + "onViewStateRestored");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(MainActivity.TAG, CLASS_NAME + "onDetach");
    }
    //-------------------- ћетоды жизненного цикла(END) --------------------*/

}
