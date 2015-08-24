package com.neirx.stopwatchtimer;

import com.neirx.stopwatchtimer.settings.SettingPref;
import com.neirx.stopwatchtimer.settings.SettingsManagement;

/**
 * Created by Waide Shery on 24.08.2015.
 *
 */
public class SettingItem {
    private String title;
    private String summary;
    private boolean hasCheckBox;
    private boolean isChecked;
    private SettingsManagement.BoolPref key;

    public void setKey(SettingsManagement.BoolPref key) {
        this.key = key;
    }

    public SettingsManagement.BoolPref getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public boolean hasCheckBox() {
        return hasCheckBox;
    }

    public SettingItem(String title) {
        this.title = title;
        this.summary = null;
        this.hasCheckBox = false;
    }

    public SettingItem(String title, String summary) {
        this.title = title;
        this.summary = summary;
        this.hasCheckBox = false;
    }

    public SettingItem(String title, boolean isChecked) {
        this.title = title;
        this.summary = null;
        this.hasCheckBox = true;
        this.isChecked = isChecked;
    }

    public SettingItem(String title, String summary, boolean isChecked) {
        this.title = title;
        this.summary = summary;
        this.hasCheckBox = true;
        this.isChecked = isChecked;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked){
        isChecked = checked;
    }

}

