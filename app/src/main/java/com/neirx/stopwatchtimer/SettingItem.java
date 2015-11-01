package com.neirx.stopwatchtimer;

import com.neirx.neirdialogs.interfaces.RootDialog;
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
    private RootDialog dialog = null;

    private boolean disable;

    public RootDialog getDialog() {
        return dialog;
    }

    public void setDialog(RootDialog dialog) {
        this.dialog = dialog;
    }

    public void setKey(SettingsManagement.BoolPref key) {
        this.key = key;
    }

    public SettingsManagement.BoolPref getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }

    public void setSummary(String summary){
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }

    public boolean hasCheckBox() {
        return hasCheckBox;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked){
        isChecked = checked;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
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



}

