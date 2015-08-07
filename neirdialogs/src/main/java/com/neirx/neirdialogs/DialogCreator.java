package com.neirx.neirdialogs;


import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.DrawableRes;
import android.util.Log;

import com.neirx.neirdialogs.dialog.BaseDialogFragment;
import com.neirx.neirdialogs.dialog.ListDialogFragment;
import com.neirx.neirdialogs.dialog.MessageDialogFragment;
import com.neirx.neirdialogs.dialog.SelectDialogFragment;
import com.neirx.neirdialogs.dialog.TextStyle;
import com.neirx.neirdialogs.interfaces.ChoiceItem;

import java.util.List;


public abstract class DialogCreator {
    protected enum DialogStyle {Holo, Material}

    protected int titleColor, dividerTitleColor, dividerTitleHeight, buttonTextColor,
            buttonTextSize, dividerButtonsColor, backgroundDialogColor, messageColor,
            selectItemTextColor;
    protected float titleSize, messageSize, selectItemTextSize;
    protected Typeface titleTypeface, buttonTextTypeface, messageTypeface, selectItemTextTypeface;
    protected TextStyle titleStyle, buttonTextStyle, messageStyle, selectItemTextStyle;
    protected Context context;
    @DrawableRes
    protected int dividerTitleResId, buttonSelectorId, backgroundDialogResId, selectItemBackgroundSelector,
            singleFlagSelector, multiFlagSelector;


    protected DialogCreator(Context context) {
        this.context = context;

        backgroundDialogColor = context.getResources().getColor(R.color.dialog_background);
        backgroundDialogResId = 0;

        titleColor = context.getResources().getColor(R.color.holo_blue);
        titleSize = 16;
        titleTypeface = Typeface.DEFAULT;
        titleStyle = TextStyle.NORMAL;

        dividerTitleColor = context.getResources().getColor(R.color.holo_blue);
        dividerTitleResId = 0;
        dividerTitleHeight = 2;

        buttonTextColor = context.getResources().getColor(R.color.holo_dark_text);
        buttonTextSize = 12;
        buttonTextTypeface = Typeface.DEFAULT;
        buttonTextStyle = TextStyle.NORMAL;
        buttonSelectorId = R.drawable.holo_btn_selector;
        dividerButtonsColor = context.getResources().getColor(R.color.button_border);

        messageColor = context.getResources().getColor(R.color.holo_dark_text);
        messageSize = 14;
        messageTypeface = Typeface.DEFAULT;
        messageStyle = TextStyle.NORMAL;

        selectItemTextColor = context.getResources().getColor(R.color.holo_dark_text);
        selectItemTextSize = 14;
        selectItemTextTypeface = Typeface.DEFAULT;
        selectItemTextStyle = TextStyle.NORMAL;
        selectItemBackgroundSelector = R.drawable.holo_list_item_selector;
        singleFlagSelector = R.drawable.holo_radiobtn_selector;
        multiFlagSelector = R.drawable.holo_checkbox_selector;
    }

    public ListDialogFragment getListDialog() {
        return new ListDialogFragment();
    }

    public SelectDialogFragment getSelectDialog(boolean isMultiChoice, List<ChoiceItem> items) {
        SelectDialogFragment dialog = new SelectDialogFragment();
        setBaseProperties(dialog);
        setSelectItemProperties(dialog);
        dialog.setItems(items);
        dialog.setChoiceMode(isMultiChoice);
        if(isMultiChoice){
            dialog.setFlagSelector(multiFlagSelector);
        } else {
            dialog.setFlagSelector(singleFlagSelector);

        }
        return dialog;
    }

    public void getDatePickerDialog() {

    }

    public void getTimePickerDialog() {

    }

    public MessageDialogFragment getMessageDialog() {
        MessageDialogFragment dialog = new MessageDialogFragment();
        setBaseProperties(dialog);
        dialog.setMessageColor(messageColor);
        dialog.setMessageSize(messageSize);
        dialog.setMessageTypeface(messageTypeface, messageStyle);
        return dialog;
    }

    private void setSelectItemProperties(SelectDialogFragment dialog) {
        dialog.setItemBackgroundSelector(selectItemBackgroundSelector);
        dialog.setItemTextColor(selectItemTextColor);
        dialog.setItemTextSize(selectItemTextSize);
        dialog.setItemTextTypeface(selectItemTextTypeface, selectItemTextStyle);
    }

    private void setBaseProperties(BaseDialogFragment dialog) {
        dialog.setDialogBackgroundColor(backgroundDialogColor);
        dialog.setDialogBackground(backgroundDialogResId);

        dialog.setTitleColor(titleColor);
        dialog.setTitleSize(titleSize);
        dialog.setTitleTypeface(titleTypeface, titleStyle);

        dialog.setDividerTitleColor(dividerTitleColor);
        dialog.setDividerTitleBackground(dividerTitleResId);
        dialog.setDividerTitleHeight(dividerTitleHeight);

        dialog.setButtonsTextColor(buttonTextColor);
        dialog.setButtonsTextSize(buttonTextSize);
        dialog.setButtonsTypeface(buttonTextTypeface, buttonTextStyle);
        dialog.setButtonsBackground(buttonSelectorId);
        dialog.setDividerButtonsColor(dividerButtonsColor);
    }
}
