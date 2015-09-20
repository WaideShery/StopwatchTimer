package com.neirx.stopwatchtimer.custom;

import com.neirx.neirdialogs.interfaces.ChoiceItem;

/**
 * Created by waide on 20.09.15.
 *
 */
public class OrientationItem implements ChoiceItem {
    private boolean isChecked;
    private String title;

    public OrientationItem(String title){

    }
    public OrientationItem(String title, boolean isChecked){

    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setChecked(boolean state) {
        isChecked = state;
    }
}
