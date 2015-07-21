package com.neirx.stopwatchtimer.fragments;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.neirx.stopwatchtimer.R;

public class PreferenceFragment extends Fragment {
    TextView tvOrientation;
    public static PreferenceFragment newInstance() {
        return new PreferenceFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_preference, container, false);
        tvOrientation = (TextView) rootView.findViewById(R.id.tvOrientation);
        tvOrientation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Важное сообщение!")
                        .setMessage("Покормите кота!")
                        .setCancelable(false)
                        .setNegativeButton("ОК, иду на кухню",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        return rootView;
    }
}
