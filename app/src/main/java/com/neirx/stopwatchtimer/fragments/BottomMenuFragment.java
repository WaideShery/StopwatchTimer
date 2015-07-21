package com.neirx.stopwatchtimer.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.neirx.stopwatchtimer.MainActivity;
import com.neirx.stopwatchtimer.R;


public class BottomMenuFragment extends Fragment implements View.OnClickListener {
    private static final String CLASS_NAME = "<BottomMenuFragment> ";
    Button btnNewLap, btnStopwatchReset, btnStopwatchStartStop;
    boolean isStopwatchRun;


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
        btnStopwatchReset = (Button) rootView.findViewById(R.id.btnStopwatchReset);
        btnStopwatchReset.setOnClickListener(this);
        btnNewLap = (Button) rootView.findViewById(R.id.btnNewLap);
        btnNewLap.setOnClickListener(this);
        btnStopwatchStartStop = (Button) rootView.findViewById(R.id.btnStopwatchStartStop);
        btnStopwatchStartStop.setOnClickListener(this);
        if(savedInstanceState != null){
            isStopwatchRun = savedInstanceState.getBoolean("isStopwatchRun");
        }

        convertStartStop();
        return rootView;
    }

    public void clickedStopwatch(){
        isStopwatchRun = !isStopwatchRun;
        convertStartStop();
    }

    private void convertStartStop(){
        if(isStopwatchRun){
            btnStopwatchStartStop.setText("Stop");
        } else {
            btnStopwatchStartStop.setText("Start");
        }
    }

    @Override
    public void onClick(View v) {
        MainActivity activity = (MainActivity) getActivity();
        StopwatchFragment stopwatchFragment = activity.getStopwatchFragment();
        switch (v.getId()){
            case R.id.btnStopwatchReset:
                isStopwatchRun = !isStopwatchRun;
                convertStartStop();
                stopwatchFragment.stopwatchReset();
                break;
            case R.id.btnNewLap:
                stopwatchFragment.addNewLap();
                break;
            case R.id.btnStopwatchStartStop:
                isStopwatchRun = !isStopwatchRun;
                convertStartStop();
                stopwatchFragment.stopwatchStartStop();
                break;
        }
    }
}
