package com.neirx.stopwatchtimer.utility;

import android.content.Context;

import com.neirx.neirdialogs.HoloDialogFactory;
import com.neirx.stopwatchtimer.R;


/**
 * Created by waide on 20.09.15.
 *
 */
public class CustomDialogFactory extends HoloDialogFactory {
    static CustomDialogFactory instance;

    static public CustomDialogFactory newInstance(Context context){
        if(instance == null){
            instance = new CustomDialogFactory(context);
        }
        return instance;
    }

    protected CustomDialogFactory(Context context) {
        super(context);
        setBackgroundDialogColor(context.getResources().getColor(R.color.app_background));
        setSingleChoiceSelector(R.drawable.dialog_list_selector);
        setSChoiceDividerColor(context.getResources().getColor(R.color.dialog_title_divider));
        setSingleChoiceRightFlag(R.drawable.apptheme_btn_radio_holo_light);
        setSingleChoiceTextColor(context.getResources().getColor(R.color.dialog_list_text));
        setSingleChoiceTextSize(14);
        setButtonSelector(R.drawable.dialog_btn_selector);
        setButtonTextColor(context.getResources().getColor(R.color.dialog_btn_text));
        setDividerButtonHorizontalColor(context.getResources().getColor(R.color.dialog_title_divider));
        setDividerButtonVerticalColor(context.getResources().getColor(R.color.dialog_title_divider));
        setTitleColor(context.getResources().getColor(R.color.dialog_title_divider));
        //setTitleTypeface(Typeface.DEFAULT, TextStyle.BOLD);
        setDividerTitleColor(context.getResources().getColor(R.color.dialog_title_divider));
        //setMessagePadding(10, 15, 10, 15);
    }
}
