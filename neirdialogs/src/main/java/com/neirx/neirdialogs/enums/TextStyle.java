package com.neirx.neirdialogs.enums;

import android.graphics.Typeface;

/**
 * Created by Waide Shery on 28.07.2015.
 *
 */
public enum TextStyle {
    BOLD_ITALIC(Typeface.BOLD_ITALIC), BOLD(Typeface.BOLD), ITALIC(Typeface.ITALIC), NORMAL(Typeface.NORMAL);

    private final int value;
    TextStyle(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
