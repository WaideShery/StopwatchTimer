package com.neirx.stopwatchtimer;

import android.content.Context;

import com.neirx.neirdialogs.DialogCreator;

/**
 * Created by waide on 20.09.15.
 *
 */
public class CustomDialogCreator extends DialogCreator {
    static CustomDialogCreator instance;

    static public CustomDialogCreator newInstance(Context context){
        if(instance == null){
            instance = new CustomDialogCreator(context);
        }
        return instance;
    }

    protected CustomDialogCreator(Context context) {
        super(context);
    }
}
