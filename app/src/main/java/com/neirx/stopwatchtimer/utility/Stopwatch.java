package com.neirx.stopwatchtimer.utility;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Waide Shery on 24.10.15.
 *
 */
public class Stopwatch {
    long baseTime;
    long savedTime;
    boolean isBaseTimeSet;
    Timer timer;
    OnTickListener onTickListener;

    public interface OnTickListener{
        void onTick(long millis);
    }

    public Stopwatch (OnTickListener listener){
        onTickListener = listener;
    }

    public void setBaseTime(long time){
        baseTime = time;
        isBaseTimeSet = true;
    }

    public void setSavedTime(long time){
        savedTime = time;
    }

    public long getBaseTime(){
        return baseTime;
    }

    public long getSavedTime(){
        return savedTime;
    }

    public void start(){
        if(!isBaseTimeSet) {
            setBaseTime(System.currentTimeMillis());
        }
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                long millis = System.currentTimeMillis() - baseTime;
                onTickListener.onTick(millis+ savedTime);
            }
        };
        timer.scheduleAtFixedRate(task, 1, 1);
    }

    public void stop(){
        if(timer != null){
            timer.cancel();
            savedTime = savedTime + (System.currentTimeMillis() - baseTime);
            isBaseTimeSet = false;
            timer = null;
        }
    }

    public void reset(){
        release();
        isBaseTimeSet = false;
        savedTime = 0;
    }

    public void release(){
        if(timer != null) {
            timer.cancel();
        }
    }
}
