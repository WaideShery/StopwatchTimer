package com.neirx.neirdialogs.interfaces;

import java.util.List;

/**
 * Created by Waide Shery on 06.10.15.
 *
 */
public interface MultiChoiceDialog extends BaseDialog {
    void setItems(List<String> items, int... checkedItemsPos);
}
