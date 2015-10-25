package com.neirx.stopwatchtimer.custom;

import android.util.Log;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;

import com.neirx.stopwatchtimer.MainActivity;


public class HandRotateAnimation extends RotateAnimation {
    private static final String CLASS_NAME = "<HandRotateAnimation> ";
    private long elapsedAtPause=0;
    private boolean paused=false;
    float interTime;

    public HandRotateAnimation(float fromDegrees, float toDegrees, int pivotXType, float pivotXValue,
                               int pivotYType, float pivotYValue) {
        super(fromDegrees, toDegrees, pivotXType, pivotXValue, pivotYType, pivotYValue);
    }

    @Override
    public boolean getTransformation(long currentTime, Transformation outTransformation) {
        if(!paused) {
            elapsedAtPause=currentTime-getStartTime();
            //Log.d(MainActivity.TAG, CLASS_NAME + "getStartTime() = " + getStartTime());
        }
        if(paused) {
            setStartTime(currentTime - elapsedAtPause);
        }
        //Log.d(MainActivity.TAG, CLASS_NAME+"currentTime = "+currentTime);
        //Log.d(MainActivity.TAG, CLASS_NAME + "elapsedAtPause = " + elapsedAtPause);
        return super.getTransformation(currentTime, outTransformation);
    }

    public void setTransform(float time){
        interTime = time;
    }

    public long getElapsedAtPause() {
        return elapsedAtPause;
    }

    public boolean isPaused(){
        return paused;
    }

    public void pause() {
        paused=true;
    }

    public void resume() {
        paused=false;
    }
}
