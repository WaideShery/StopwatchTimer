package com.neirx.neirdialogs.interfaces;

import android.content.DialogInterface;

/**
 * Created by waide on 21.09.15.
 *
 */
public interface NeirDialogInterface {
    int BUTTON_NEGATIVE = -2;
    int BUTTON_NEUTRAL = -3;
    int BUTTON_POSITIVE = -1;

    interface OnClickListener {
        void onClick(String tag, int buttonId, Object extraData);
    }

    interface OnItemClickListener {
        void onItemClick(String tag, int itemId);
    }
}
