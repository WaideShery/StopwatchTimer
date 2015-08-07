package com.neirx.neirdialogs.helper;

import com.neirx.neirdialogs.interfaces.ChoiceItem;

/**
 * Created by Waide Shery on 25.07.2015.
 */
public class ListItem implements ChoiceItem {
    String title;
    boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public String getTitle() {
        return title;
    }

    public ListItem(String title, boolean isChecked) {
        this.title = title;
        this.isChecked = isChecked;
    }

    public void setChecked(boolean state){
        isChecked = state;
    }
}
