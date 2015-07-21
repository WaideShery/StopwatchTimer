package com.neirx.stopwatchtimer.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neirx.stopwatchtimer.R;


public class QueueFragment extends Fragment {
    public static QueueFragment newInstance() {
        return new QueueFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_queue, container, false);

        return rootView;
    }
}
