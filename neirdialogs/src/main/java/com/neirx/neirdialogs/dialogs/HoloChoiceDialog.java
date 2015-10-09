package com.neirx.neirdialogs.dialogs;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.widget.ListView;

import com.neirx.neirdialogs.Statical;
import com.neirx.neirdialogs.enums.TextStyle;
import com.neirx.neirdialogs.interfaces.SingleChoiceDialog;

import java.util.List;


public abstract class HoloChoiceDialog extends HoloBaseDialog  implements SingleChoiceDialog {
    protected ListView lvChoice;
    protected List<String> items;
    int[] checkedItemsPos;
    protected int leftFlagSelectorId = -1, rightFlagSelectorId = -1;
    protected int itemTextColor = -1;
    protected float itemTextSize = 0;
    protected TextStyle itemTextStyle;
    protected Typeface itemTextTypeface;
    protected int itemTextGravity = -1;
    protected int itemBackgroundSelectorId = -1;
    protected int itemPaddingStart = -1, itemPaddingTop = -1, itemPaddingEnd = -1, itemPaddingBottom = -1;
    protected @ColorRes
    int dividerColorId;
    protected float dividerHeightDp;
    protected Drawable dividerDrawable;
    protected boolean isDividerDrawable;


    /**
     * Установка ресурса разделяющей линии между пунктами списка.
     *
     * @param drawable ресурс разделяющей линии
     */
    public void setDividerDrawable(Drawable drawable){
        dividerDrawable = drawable;
        isDividerDrawable = true;
    }

    /**
     * Установка цвета разделяющей линии между пунктами списка.
     *
     * @param dividerColorId ресурс цвета
     */
    public void setDividerColorId(int dividerColorId) {
        this.dividerColorId = dividerColorId;
        isDividerDrawable = false;
    }

    /**
     *  Установка ширины разделяющей линии между пунктами списка.
     *
     * @param dividerHeightDp ширина в dp.
     */
    public void setDividerHeightDp(float dividerHeightDp) {
        this.dividerHeightDp = dividerHeightDp;
    }

    /**
     * Установка списка для адаптера ListView.
     *
     * @param items коллекция пунктов списка
     * @param checkedItemsPos позиции выбранных элементов
     */
    public void setItems(List<String> items, int[] checkedItemsPos) {
        this.items = items;
        this.checkedItemsPos = checkedItemsPos;
    }

    /**
     * Установка цвета текста пунктов списка.
     *
     * @param color ресурс цвета
     */
    public void setItemTextColor(int color) {
        itemTextColor = color;
    }

    /**
     * Установка размера шрифта текста пунктов списка.
     *
     * @param sizeSp размер шрифта
     */
    public void setItemTextSize(float sizeSp) {
        itemTextSize = sizeSp;
    }

    /**
     * Установка шрифта и стиля отображения текста пунктов списка.
     *
     * @param tf    шрифт
     * @param style стиль текста
     */
    public void setItemTextTypeface(Typeface tf, TextStyle style) {
        itemTextTypeface = tf;
        itemTextStyle = style;
    }

    /**
     * Установка выравнивания текста пунктов списка.
     *
     * @param gravity выравнивание {@link android.view.Gravity}
     */
    public void setItemTextGravity(int gravity) {
        itemTextGravity = gravity;
    }

    /**
     * Установка отступов пунктов списка в dp.
     *
     * @param start  начало
     * @param top    верх
     * @param end    конец
     * @param bottom низ
     */
    public void setItemTextPaddingDP(int start, int top, int end, int bottom) {
        itemPaddingStart = start;
        itemPaddingTop = top;
        itemPaddingEnd = end;
        itemPaddingBottom = bottom;
    }

    /**
     * Установка селектора фона пунктов списка.
     *
     * @param itemBackgroundSelectorId селектор фона пунктов списка.
     */
    public void setItemBackgroundSelector(@DrawableRes int itemBackgroundSelectorId){
        this.itemBackgroundSelectorId = itemBackgroundSelectorId;
    }

    /**
     *Установка ресурса флажка переключателя с правой стороны.
     *
     * @param flagSelectorId ресурс флажка
     */
    public void setRightFlagSelector(@DrawableRes int flagSelectorId){
        rightFlagSelectorId = flagSelectorId;
        leftFlagSelectorId = -1;
    }

    /**
     *Установка ресурса флажка переключателя с левой стороны.
     *
     * @param flagSelectorId ресурс флажка
     */
    public void setLeftFlagSelector(@DrawableRes int flagSelectorId){
        leftFlagSelectorId = flagSelectorId;
        rightFlagSelectorId = -1;
    }
}
