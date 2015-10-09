package com.neirx.neirdialogs.interfaces;


/**
 * Created by Waide Shery on 06.10.15.
 *
 */
public interface DialogFactory {
    MessageDialog createMessageDialog();
    ListDialog createListDialog();
    SingleChoiceDialog createSingleChoiceDialog();
    MultiChoiceDialog createMultiChoiceDialog();
}
