package com.neirx.stopwatchtimer.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.neirx.stopwatchtimer.R;
import com.neirx.stopwatchtimer.custom.CustomAdapter;

import java.util.List;

/**
 * Created by Waide Shery on 07.08.2015.
 */
public class SettingsFragmentTest extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_test_settings, container, false);

        CustomAdapter mAdapter = new CustomAdapter(getActivity());
        for (int i = 1; i < 30; i++) {
            mAdapter.addItem("Row Item #" + i);
            if (i % 4 == 0) {
                mAdapter.addSectionHeaderItem("Section #" + i);
            }
        }

        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        listView.setAdapter(mAdapter);
        return rootView;
    }
}
