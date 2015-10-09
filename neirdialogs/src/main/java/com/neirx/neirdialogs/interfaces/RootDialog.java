package com.neirx.neirdialogs.interfaces;

import android.app.FragmentManager;

/**
 * Created by Waide Shery on 06.10.15.
 *
 */
public interface RootDialog {
    void setTitle(String title);
    void show(FragmentManager manager, String tag);
}
