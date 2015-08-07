package com.neirx.neirdialogs.dialog;

import android.graphics.Typeface;

/**
 * Created by Waide Shery on 28.07.2015.
 */
public enum TextStyle {
    BOLD_ITALIC(Typeface.BOLD_ITALIC), BOLD(Typeface.BOLD), ITALIC(Typeface.ITALIC), NORMAL(Typeface.NORMAL);

    private final int value;
    private TextStyle(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
