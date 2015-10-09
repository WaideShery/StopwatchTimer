package com.neirx.neirdialogs.interfaces;

/**
 * Created by Waide Shery on 06.10.15.
 *
 */
public interface ListDialog extends RootDialog {
    void setItems(String[] items);
    void setOnItemClickListener(NeirDialogInterface.OnItemClickListener listener, String tag);
}
