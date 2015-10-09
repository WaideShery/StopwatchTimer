package com.neirx.stopwatchtimer;

import android.content.Context;

import com.neirx.neirdialogs.HoloDialogFactory;


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
    }
}
