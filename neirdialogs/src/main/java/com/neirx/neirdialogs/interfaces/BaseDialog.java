package com.neirx.neirdialogs.interfaces;

/**
 * Created by Waide Shery on 06.10.15.
 *
 */
public interface BaseDialog extends RootDialog {
    void setPositiveButton(String name);
    void setNegativeButton(String name);
    void setNeutralButton(String name);
    void setOnClickListener(NeirDialogInterface.OnClickListener onClickListener, String tag);
}
