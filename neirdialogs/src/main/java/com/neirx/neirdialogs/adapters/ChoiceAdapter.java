package com.neirx.neirdialogs.adapters;

import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;

import com.neirx.neirdialogs.enums.TextStyle;

/**
 * Created by Waide Shery on 08.10.2015.
 *
 */
public abstract class ChoiceAdapter extends BaseAdapter {
    protected int textColor;
    protected float textSize;
    protected int textGravity;
    protected TextStyle textStyle;
    protected Typeface textTypeface;
    @DrawableRes
    protected int leftFlagSelectorId, rightFlagSelectorId,  bgSelector;
    protected int textPaddingStart, textPaddingTop, textPaddingEnd, textPaddingBottom;

    public void setItemParam(int textColor, float textSize, TextStyle textStyle, Typeface textTypeface,
                             int textGravity, int leftFlagSelectorId, int rightFlagSelectorId, int bgSelector){
        this.textColor = textColor;
        this.textSize = textSize;
        this.textStyle = textStyle;
        this.textGravity = textGravity;
        this.textTypeface = textTypeface;
        this.bgSelector = bgSelector;
        this.leftFlagSelectorId = leftFlagSelectorId;
        this.rightFlagSelectorId = rightFlagSelectorId;
        this.bgSelector = bgSelector;
    }

    public void setItemTextPadding(int start, int top, int end, int bottom){
        textPaddingStart = start;
        textPaddingTop = top;
        textPaddingEnd = end;
        textPaddingBottom = bottom;
    }

    protected void setParam(CompoundButton compoundButton){
        compoundButton.setTextColor(textColor);
        if(textSize > 0) compoundButton.setTextSize(textSize);
        if(textGravity > -1) compoundButton.setGravity(textGravity);
        if(textTypeface != null && textStyle != null) compoundButton.setTypeface(textTypeface, textStyle.getValue());
        else if(textTypeface != null) compoundButton.setTypeface(textTypeface);
        else if(textStyle != null) compoundButton.setTypeface(Typeface.DEFAULT, textStyle.getValue());

        if(textPaddingStart > -1) {
            if (Build.VERSION.SDK_INT >= 16) {
                compoundButton.setPaddingRelative(textPaddingStart, textPaddingTop, textPaddingEnd, textPaddingBottom);
            } else {
                compoundButton.setPadding(textPaddingStart, textPaddingTop, textPaddingEnd, textPaddingBottom);
            }
        }

    }
}
