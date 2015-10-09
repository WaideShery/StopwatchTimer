package com.neirx.neirdialogs;


import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.view.Gravity;

import com.neirx.neirdialogs.dialogs.HoloBaseDialog;
import com.neirx.neirdialogs.dialogs.HoloMultiChoiceDialog;
import com.neirx.neirdialogs.dialogs.HoloRootDialog;
import com.neirx.neirdialogs.dialogs.EditTextDialogFragment;
import com.neirx.neirdialogs.dialogs.HoloListDialog;
import com.neirx.neirdialogs.dialogs.HoloMessageDialog;
import com.neirx.neirdialogs.dialogs.HoloSingleChoiceDialog;
import com.neirx.neirdialogs.enums.TextStyle;
import com.neirx.neirdialogs.interfaces.DialogFactory;
import com.neirx.neirdialogs.interfaces.ListDialog;
import com.neirx.neirdialogs.interfaces.MessageDialog;
import com.neirx.neirdialogs.interfaces.MultiChoiceDialog;
import com.neirx.neirdialogs.interfaces.SingleChoiceDialog;


public class HoloDialogFactory implements DialogFactory {

    /**
     * Фон диалогового окна. Если isBackgroundDialogRes = true, то фоном будет ресурс backgroundDialogResId.
     * В противном случае, фоном ставится цвет backgroundDialogColor.
     */
    private
    @DrawableRes
    int backgroundDialogResId;
    private int backgroundDialogColor;
    private boolean isBackgroundDialogRes;

    public void setBackgroundDialogRes(@DrawableRes int resId) {
        this.backgroundDialogResId = resId;
        isBackgroundDialogRes = true;
    }

    public void setBackgroundDialogColor(int color) {
        this.backgroundDialogColor = color;
        isBackgroundDialogRes = false;
    }

    /**
     * Фон заголовка диалогового окна. Первым проверяется ресурс layTitleBackgroundResId, если он не равен -1, то устанавливается фоном.
     * В противном случае, фоном ставиться цвет layTitleBackgroundColor (если больше -1).
     */
    private
    @DrawableRes
    int layTitleBackgroundResId;
    private int layTitleBackgroundColor;
    private enum TitleBgSet {NO, COLOR, RES}
    private TitleBgSet titleBgSet;
    public void setTitleBackgroundRes(@DrawableRes int resId) {
        this.layTitleBackgroundResId = resId;
        titleBgSet = TitleBgSet.RES;
    }
    public void setTitleBackgroundColor(int color) {
        this.layTitleBackgroundColor = color;
        titleBgSet = TitleBgSet.COLOR;
    }

    /**
     * Настройка шрифта заголовка. Размер шрифта в sp.
     */
    private int titleColor;
    private int titleSize;
    private Typeface titleTypeface;
    private TextStyle titleStyle;
    public void setTitleColor(int textColor) {
        this.titleColor = textColor;
    }
    public void setTitleSize(int textSizeSp) {
        this.titleSize = textSizeSp;
    }
    public void setTitleTypeface(Typeface typeface, TextStyle textStyle) {
        this.titleTypeface = typeface;
        this.titleStyle = textStyle;
    }

    /**
     * Выравнивание заголовка.
     */
    private int titleGravity;
    public void setTitleGravity(int textGravity) {
        this.titleGravity = textGravity;
    }

    /**
     * Отступы заголовка в dp.
     */
    private int tvTitlePaddingStart;
    private int tvTitlePaddingTop;
    private int tvTitlePaddingEnd;
    private int tvTitlePaddingBottom;
    public void setTitlePadding(int startPaddingDp, int topPaddingDp, int endPaddingDp, int bottomPaddingDp) {
        this.tvTitlePaddingStart = startPaddingDp;
        this.tvTitlePaddingTop = topPaddingDp;
        this.tvTitlePaddingEnd = endPaddingDp;
        this.tvTitlePaddingBottom = bottomPaddingDp;
    }

    /**
     * Фон разделяющей линии заголовка. Если isDividerTitleRes = true, то фоном устанавливается ресурс dividerTitleResId.
     * В противном случае, фоном ставиться цвет dividerTitleColor.
     */
    private
    @DrawableRes
    int dividerTitleResId;
    private int dividerTitleColor;
    private boolean isDividerTitleRes = false;
    public void setDividerTitleRes(@DrawableRes int resId) {
        this.dividerTitleResId = resId;
        isDividerTitleRes = true;
    }
    public void setDividerTitleColor(int color) {
        this.dividerTitleColor = color;
        isDividerTitleRes = false;
    }


    /**
     * Ширина разделяющей линии заголовка в dp.
     */
    private int dividerTitleWidth;
    public void setDividerTitleWidth(int widthDp) {
        this.dividerTitleWidth = widthDp;
    }

    /**
     * Ресурс селектора кнопок.
     */
    private
    @DrawableRes
    int buttonSelectorId;
    public void setButtonSelector(@DrawableRes int selectorId) {
        this.buttonSelectorId = selectorId;
    }

    /**
     * Настройка шрифта кнопок. Размер шрифта указывается в sp.
     */
    private int buttonTextColor;
    private int buttonTextSize;
    private Typeface buttonTextTypeface;
    private TextStyle buttonTextStyle;
    private boolean isTextBtnAllCaps;
    public void setButtonTextColor(int textColor) {
        this.buttonTextColor = textColor;
    }
    public void setButtonTextSize(int textSizeSp) {
        this.buttonTextSize = textSizeSp;
    }
    public void setButtonTypeface(Typeface typeface, TextStyle textStyle) {
        this.buttonTextTypeface = typeface;
        this.buttonTextStyle = textStyle;
    }
    public void setButtonAllCaps(boolean isTextAllCaps) {
        this.isTextBtnAllCaps = isTextAllCaps;
    }

    /**
     * Выравнивание текста кнопок.
     */
    private int buttonTextGravity;
    public void setButtonGravity(int textGravity) {
        this.buttonTextGravity = textGravity;
    }

    /**
     * Фон горизонтальной линии layout'а кнопок.
     * Если isDividerBtnHorizontalRes = true, то фоном будет ресурс dividerBtnHorizontalResId.
     * В противном случае, фоном ставится цвет dividerBtnHorizontalColor.
     */
    private
    @DrawableRes
    int dividerBtnHorizontalResId;
    private int dividerBtnHorizontalColor;
    private boolean isDividerBtnHorizontalRes;
    public void setDividerButtonHorizontalRes(@DrawableRes int resId) {
        this.dividerBtnHorizontalResId = resId;
        isDividerBtnHorizontalRes = true;
    }
    public void setDividerButtonHorizontalColor(int color) {
        this.dividerBtnHorizontalColor = color;
        isDividerBtnHorizontalRes = false;
    }

    /**
     * Фон вертикальных линий layout'а кнопок.
     * Если isDividerBtnVerticalRes = true, то фоном будет ресурс dividerBtnVerticalResId.
     * В противном случае, фоном ставится цвет dividerBtnVerticalColor.
     */
    private
    @DrawableRes
    int dividerBtnVerticalResId;
    private int dividerBtnVerticalColor;
    private boolean isDividerBtnVerticalRes;
    public void setDividerButtonVerticalRes(@DrawableRes int resId) {
        this.dividerBtnVerticalResId = resId;
        isDividerBtnVerticalRes = true;
    }
    public void setDividerButtonVerticalColor(int color) {
        this.dividerBtnVerticalColor = color;
        isDividerBtnVerticalRes = false;
    }

    /**
     * Ширина горизонтальной и вертикальных линий layout'а кнопок в dp.
     */
    private float dividerBtnHorizontalWidth;
    private float dividerBtnVerticalWidth;
    public void setDividerButtonHorizontalWidth(float widthDp) {
        this.dividerBtnHorizontalWidth = widthDp;
    }
    public void setDividerButtonVerticalWidth(float widthDp) {
        this.dividerBtnVerticalWidth = widthDp;
    }

    /**
     * Настройка шрифта сообщения в MessageDialog. Размер шрифта указывается в sp.
     */
    private int messageTextColor;
    private int messageTextSize;
    private Typeface messageTextTypeface;
    private TextStyle messageTextStyle;
    public void setMessageColor(int textColor) {
        this.messageTextColor = textColor;
    }
    public void setMessageSize(int textSizeSp) {
        this.messageTextSize = textSizeSp;
    }
    public void setMessageTypeface(Typeface typeface, TextStyle textStyle) {
        this.messageTextTypeface = typeface;
        this.messageTextStyle = textStyle;
    }

    /**
     * Выравнивание текста сообщения в MessageDialog.
     */
    private int messageTextGravity;
    public void setMessageGravity(int textGravity) {
        this.messageTextGravity = textGravity;
    }

    /**
     * Отступы сообщения в MessageDialog. Указываются в dp.
     */
    private int tvMessagePaddingStart;
    private int tvMessagePaddingTop;
    private int tvMessagePaddingEnd;
    private int tvMessagePaddingBottom;
    public void setMessagePadding(int startPaddingDp, int topPaddingDp, int endPaddingDp, int bottomPaddingDp) {
        this.tvMessagePaddingStart = startPaddingDp;
        this.tvMessagePaddingTop = topPaddingDp;
        this.tvMessagePaddingEnd = endPaddingDp;
        this.tvMessagePaddingBottom = bottomPaddingDp;
    }

    /**
     * Ресурс селектора фона пунктов в ListDialog.
     */
    private
    @DrawableRes
    int listItemBackgroundSelector;
    public void setListItemSelector(@DrawableRes int backgroundSelector) {
        this.listItemBackgroundSelector = backgroundSelector;
    }

    /**
     * Настройка шрифта пунктов в ListDialog. Размер шрифта указывается в sp.
     */
    private int listItemTextColor;
    private int listItemTextSize;
    private Typeface listItemTextTypeface;
    private TextStyle listItemTextStyle;
    public void setListTextColor(int textColor) {
        this.listItemTextColor = textColor;
    }
    public void setListTextSize(int textSizeSp) {
        this.listItemTextSize = textSizeSp;
    }
    public void setListTypeface(Typeface typeface, TextStyle textStyle) {
        this.listItemTextTypeface = typeface;
        this.listItemTextStyle = textStyle;
    }

    /**
     * Выравнивание текста сообщения в ListDialog.
     */
    private int listItemTextGravity;
    public void setListTextGravity(int textGravity) {
        this.listItemTextGravity = textGravity;
    }

    /**
     * Отступы сообщения в ListDialog. Указываются в dp.
     */
    private int tvListItemPaddingStart;
    private int tvListItemPaddingTop;
    private int tvListItemPaddingEnd;
    private int tvListItemPaddingBottom;
    public void setListItemPadding(int startPaddingDp, int topPaddingDp, int endPaddingDp, int bottomPaddingDp) {
        this.tvListItemPaddingStart = startPaddingDp;
        this.tvListItemPaddingStart = topPaddingDp;
        this.tvListItemPaddingStart = endPaddingDp;
        this.tvListItemPaddingStart = bottomPaddingDp;
    }

    /**
     * Параметры разделяющей линии в ListDialog.
     */
    private @ColorRes int listDividerColorId;
    private float listDividerHeightDp;
    private Drawable listDividerDrawable;
    private boolean isListDividerDrawable;
    public void setListDividerColor(int colorId) {
        this.listDividerColorId = colorId;
        isListDividerDrawable = false;
    }
    public void setListDividerHeight(@ColorRes float heightDp) {
        this.listDividerHeightDp = heightDp;
    }
    public void setListDividerDrawable(Drawable dividerDrawable) {
        this.listDividerDrawable = dividerDrawable;
        isListDividerDrawable = true;
    }

    /**
     * Ресурс селектора фона пунктов в SingleChoiceDialog.
     */
    private
    @DrawableRes
    int sChoiceItemBackgroundSelector;
    public void setSingleChoiceSelector(@DrawableRes int backgroundSelector) {
        this.sChoiceItemBackgroundSelector = backgroundSelector;
    }

    /**
     * Ресурсы флажков переключателя пунктов с левой и правой стороны в SingleChoiceDialog.
     * Можно установить только с одной стороны. Если sChoiceItemLeftFlagSelector не равно -1,
     * то устанавливается с левой стороны. В противном случае - с правой.
     */
    private
    @DrawableRes
    int sChoiceItemLeftFlagSelector;
    private
    @DrawableRes
    int sChoiceItemRightFlagSelector;
    private boolean isSChoiceLeftFlag;

    public void setSingleChoiceLeftFlag(@DrawableRes int flagSelector) {
        this.sChoiceItemLeftFlagSelector = flagSelector;
        isSChoiceLeftFlag = true;
    }

    public void setSingleChoiceRightFlag(@DrawableRes int flagSelector) {
        this.sChoiceItemRightFlagSelector = flagSelector;
        isSChoiceLeftFlag = false;
    }

    /**
     * Настройка шрифта пунктов в SingleChoiceDialog. Размер шрифта указывается в sp.
     */
    private int sChoiceItemTextColor;
    private int sChoiceItemTextSize;
    private Typeface sChoiceItemTextTypeface;
    private TextStyle sChoiceItemTextStyle;
    public void setSingleChoiceTextColor(int textColor) {
        this.sChoiceItemTextColor = textColor;
    }
    public void setSingleChoiceTextSize(int textSizeSp) {
        this.sChoiceItemTextSize = textSizeSp;
    }
    public void setSingleChoiceTypeface(Typeface typeface, TextStyle textStyle) {
        this.sChoiceItemTextTypeface = typeface;
        this.sChoiceItemTextStyle = textStyle;
    }

    /**
     * Выравнивание текста сообщения в SingleChoiceDialog.
     */
    private int sChoiceItemTextGravity;
    public void setSingleChoiceTextGravity(int textGravity) {
        this.sChoiceItemTextGravity = textGravity;
    }

    /**
     * Отступы сообщения в SingleChoiceDialog. Указываются в dp.
     */
    private int tvSChoiceItemPaddingStart;
    private int tvSChoiceItemPaddingTop;
    private int tvSChoiceItemPaddingEnd;
    private int tvSChoiceItemPaddingBottom;
    public void setSingleChoicePadding(int startPaddingDp, int topPaddingDp, int endPaddingDp, int bottomPaddingDp) {
        this.tvSChoiceItemPaddingStart = startPaddingDp;
        this.tvSChoiceItemPaddingTop = topPaddingDp;
        this.tvSChoiceItemPaddingEnd = endPaddingDp;
        this.tvSChoiceItemPaddingBottom = bottomPaddingDp;
    }

    /**
     * Параметры разделяющей линии в SingleChoiceDialog.
     */
    private @ColorRes int sChoiceDividerColorId;
    private float sChoiceDividerHeightDp;
    private Drawable sChoiceDividerDrawable;
    private boolean isSChoiceDividerDrawable;
    public void setSChoiceDividerColor(int colorId) {
        this.sChoiceDividerColorId = colorId;
        isSChoiceDividerDrawable = false;
    }
    public void setSChoiceDividerHeight(float heightDp) {
        this.sChoiceDividerHeightDp = heightDp;
    }
    public void setSChoiceDividerDrawable(Drawable dividerDrawable) {
        this.sChoiceDividerDrawable = dividerDrawable;
        isSChoiceDividerDrawable = true;
    }

    /**
     * Ресурс селектора фона пунктов в MultiChoiceDialog.
     */
    private
    @DrawableRes
    int mChoiceItemBackgroundSelector;
    public void setMultiChoiceSelector(@DrawableRes int backgroundSelector) {
        this.mChoiceItemBackgroundSelector = backgroundSelector;
    }

    /**
     * Ресурсы флажков переключателя пунктов с левой и правой стороны в MultiChoiceDialog.
     * Можно установить только с одной стороны. Если mChoiceItemLeftFlagSelector не равно -1,
     * то устанавливается с левой стороны. В противном случае - с правой.
     */
    private
    @DrawableRes
    int mChoiceItemLeftFlagSelector;
    private
    @DrawableRes
    int mChoiceItemRightFlagSelector;
    private boolean isMChoiceLeftFlag;
    public void setMultiChoiceLeftFlag(@DrawableRes int flagSelector) {
        this.mChoiceItemLeftFlagSelector = flagSelector;
        isMChoiceLeftFlag = true;
    }
    public void setMultiChoiceRightFlag(@DrawableRes int flagSelector) {
        this.mChoiceItemRightFlagSelector = flagSelector;
        isMChoiceLeftFlag = false;
    }

    /**
     * Настройка шрифта пунктов в MultiChoiceDialog. Размер шрифта указывается в sp.
     */
    private int mChoiceItemTextColor;
    private int mChoiceItemTextSize;
    private Typeface mChoiceItemTextTypeface;
    private TextStyle mChoiceItemTextStyle;
    public void setMultiChoiceTextColor(int textColor) {
        this.mChoiceItemTextColor = textColor;
    }
    public void setMultiChoiceTextSize(int textSizeSp) {
        this.mChoiceItemTextSize = textSizeSp;
    }
    public void setMultiChoiceTextTypeface(Typeface textTypeface, TextStyle textStyle) {
        this.mChoiceItemTextTypeface = textTypeface;
        this.mChoiceItemTextStyle = textStyle;
    }

    /**
     * Выравнивание текста сообщения в MultiChoiceDialog.
     */
    private int mChoiceItemTextGravity;

    public void setMultiChoiceTextGravity(int textGravity) {
        this.mChoiceItemTextGravity = textGravity;
    }

    /**
     * Отступы сообщения в MultiChoiceDialog. Указываются в dp.
     */
    private int tvMChoiceItemPaddingStart;
    private int tvMChoiceItemPaddingTop;
    private int tvMChoiceItemPaddingEnd;
    private int tvMChoiceItemPaddingBottom;
    public void setMultiChoicePadding(int startPaddingDp, int topPaddingDp, int endPaddingDp, int bottomPaddingDp) {
        this.tvMChoiceItemPaddingStart = startPaddingDp;
        this.tvMChoiceItemPaddingTop = topPaddingDp;
        this.tvMChoiceItemPaddingEnd = endPaddingDp;
        this.tvMChoiceItemPaddingBottom = bottomPaddingDp;
    }

    /**
     * Параметры разделяющей линии в MultiChoiceDialog.
     */
    private @ColorRes int mChoiceDividerColorId;
    private float mChoiceDividerHeightDp;
    private Drawable mChoiceDividerDrawable;
    private boolean isMChoiceDividerDrawable;
    public void setMChoiceDividerColor(int colorId) {
        this.mChoiceDividerColorId = colorId;
        isMChoiceDividerDrawable = false;
    }
    public void setMChoiceDividerHeight(float heightDp) {
        this.mChoiceDividerHeightDp = heightDp;
    }
    public void setMChoiceDividerDrawable(Drawable dividerDrawable) {
        this.mChoiceDividerDrawable = dividerDrawable;
        isMChoiceDividerDrawable = true;
    }



    private int editTextColor, hintTextColor;
    private int editTextSize;
    private Typeface editTextTypeface;
    private TextStyle editTextStyle;



    protected HoloDialogFactory(Context context) {
        isBackgroundDialogRes = false;
        backgroundDialogColor = context.getResources().getColor(R.color.holo_dialog_background);

        titleBgSet = TitleBgSet.NO;

        titleColor = context.getResources().getColor(R.color.holo_title_text);
        titleSize = 22;
        titleTypeface = Typeface.DEFAULT;
        titleStyle = TextStyle.NORMAL;
        titleGravity = Gravity.START;

        tvTitlePaddingStart = 20;
        tvTitlePaddingTop = 20;
        tvTitlePaddingEnd = 20;
        tvTitlePaddingBottom = 20;

        isDividerTitleRes = false;
        dividerTitleColor = context.getResources().getColor(R.color.holo_title_divider);
        dividerTitleWidth = 2;

        buttonSelectorId = R.drawable.holo_btn_selector;

        buttonTextColor = context.getResources().getColor(R.color.holo_button_text);
        buttonTextSize = 14;
        buttonTextTypeface = Typeface.DEFAULT;
        buttonTextStyle = TextStyle.NORMAL;
        buttonTextGravity = -1;
        isTextBtnAllCaps = false;

        isDividerBtnHorizontalRes = false;
        dividerBtnHorizontalColor = context.getResources().getColor(R.color.holo_button_divider);
        isDividerBtnVerticalRes = false;
        dividerBtnVerticalColor = context.getResources().getColor(R.color.holo_button_divider);

        dividerBtnHorizontalWidth = 0.8f;
        dividerBtnVerticalWidth = 0.8f;

        messageTextColor = context.getResources().getColor(R.color.holo_message_text);
        messageTextSize = 18;
        messageTextTypeface = Typeface.DEFAULT;
        messageTextStyle = TextStyle.NORMAL;

        messageTextGravity = -1;

        tvMessagePaddingStart = 12;
        tvMessagePaddingTop = 12;
        tvMessagePaddingEnd = 12;
        tvMessagePaddingBottom = 12;

        listItemBackgroundSelector = R.drawable.holo_list_item_selector;

        listItemTextColor = context.getResources().getColor(R.color.holo_list_item_text);
        listItemTextSize = 18;
        listItemTextTypeface = Typeface.DEFAULT;
        listItemTextStyle = TextStyle.NORMAL;

        listItemTextGravity = -1;

        tvListItemPaddingStart = 24;
        tvListItemPaddingTop = 24;
        tvListItemPaddingEnd = 24;
        tvListItemPaddingBottom = 24;

        isListDividerDrawable = false;
        listDividerColorId = context.getResources().getColor(R.color.holo_list_divider);
        listDividerHeightDp = 0.8f;

        sChoiceItemBackgroundSelector = R.drawable.holo_list_item_selector;

        isSChoiceLeftFlag = false;
        sChoiceItemRightFlagSelector = R.drawable.holo_radiobtn_selector;

        sChoiceItemTextColor = context.getResources().getColor(R.color.holo_list_item_text);
        sChoiceItemTextSize = 18;
        sChoiceItemTextTypeface = Typeface.DEFAULT;
        sChoiceItemTextStyle = TextStyle.NORMAL;

        sChoiceItemTextGravity = -1;

        tvSChoiceItemPaddingStart = 18;
        tvSChoiceItemPaddingTop = 18;
        tvSChoiceItemPaddingEnd = 18;
        tvSChoiceItemPaddingBottom = 18;

        isSChoiceDividerDrawable = false;
        sChoiceDividerColorId = context.getResources().getColor(R.color.holo_list_divider);
        sChoiceDividerHeightDp = 0.8f;

        mChoiceItemBackgroundSelector = R.drawable.holo_list_item_selector;

        isMChoiceLeftFlag = false;
        mChoiceItemRightFlagSelector = R.drawable.holo_checkbox_selector;

        mChoiceItemTextColor = context.getResources().getColor(R.color.holo_list_item_text);
        mChoiceItemTextSize = 18;
        mChoiceItemTextTypeface = Typeface.DEFAULT;
        mChoiceItemTextStyle = TextStyle.NORMAL;

        mChoiceItemTextGravity = Gravity.CENTER_VERTICAL;

        tvMChoiceItemPaddingStart = 18;
        tvMChoiceItemPaddingTop = 18;
        tvMChoiceItemPaddingEnd = 18;
        tvMChoiceItemPaddingBottom = 18;

        isMChoiceDividerDrawable = false;
        mChoiceDividerColorId = context.getResources().getColor(R.color.holo_list_divider);
        mChoiceDividerHeightDp = 0.8f;


        editTextColor = context.getResources().getColor(R.color.holo_edit_text);
        editTextSize = 16;
        editTextTypeface = Typeface.DEFAULT;
        editTextStyle = TextStyle.NORMAL;
        hintTextColor = context.getResources().getColor(R.color.holo_edit_text_hint);
    }

    /**
     * Создание и установка настроек ListDialog.
     *
     * @return объект типа ListDialog
     */
    public ListDialog createListDialog() {
        HoloListDialog dialog = new HoloListDialog();
        setViewProperties(dialog);
        setTitleProperties(dialog);
        dialog.setItemBackgroundSelector(listItemBackgroundSelector);
        dialog.setItemTextColor(listItemTextColor);
        dialog.setItemTextSize(listItemTextSize);
        dialog.setItemTextTypeface(listItemTextTypeface, listItemTextStyle);
        dialog.setItemTextGravity(listItemTextGravity);
        dialog.setItemTextPaddingDP(tvListItemPaddingStart, tvListItemPaddingTop,
                tvListItemPaddingEnd, tvListItemPaddingBottom);
        if(isListDividerDrawable) dialog.setDividerDrawable(listDividerDrawable);
        else dialog.setDividerColorId(listDividerColorId);
        dialog.setDividerHeightDp(listDividerHeightDp);
        return dialog;
    }


    /**
     * Создание и установка настроек SingleChoiceDialog.
     *
     * @return объект типа SingleChoiceDialog
     */
    public SingleChoiceDialog createSingleChoiceDialog() {
        HoloSingleChoiceDialog dialog = new HoloSingleChoiceDialog();
        setViewProperties(dialog);
        setTitleProperties(dialog);
        setButtonsProperties(dialog);
        dialog.setItemBackgroundSelector(sChoiceItemBackgroundSelector);
        if (isSChoiceLeftFlag) dialog.setLeftFlagSelector(sChoiceItemLeftFlagSelector);
        else dialog.setRightFlagSelector(sChoiceItemRightFlagSelector);
        dialog.setItemTextColor(mChoiceItemTextColor);
        dialog.setItemTextSize(mChoiceItemTextSize);
        dialog.setItemTextTypeface(mChoiceItemTextTypeface, mChoiceItemTextStyle);
        dialog.setItemTextGravity(mChoiceItemTextGravity);
        dialog.setItemTextPaddingDP(tvMChoiceItemPaddingStart, tvMChoiceItemPaddingTop,
                tvMChoiceItemPaddingEnd, tvMChoiceItemPaddingBottom);
        if(isSChoiceDividerDrawable) dialog.setDividerDrawable(sChoiceDividerDrawable);
        else dialog.setDividerColorId(sChoiceDividerColorId);
        dialog.setDividerHeightDp(sChoiceDividerHeightDp);
        return dialog;
    }

    /**
     * Создание и установка настроек MultiChoiceDialog.
     *
     * @return объект типа MultiChoiceDialog
     */
    public MultiChoiceDialog createMultiChoiceDialog() {
        HoloMultiChoiceDialog dialog = new HoloMultiChoiceDialog();
        setViewProperties(dialog);
        setTitleProperties(dialog);
        setButtonsProperties(dialog);
        dialog.setItemBackgroundSelector(mChoiceItemBackgroundSelector);
        if (isMChoiceLeftFlag) dialog.setLeftFlagSelector(mChoiceItemLeftFlagSelector);
        else dialog.setRightFlagSelector(mChoiceItemRightFlagSelector);
        dialog.setItemTextColor(mChoiceItemTextColor);
        dialog.setItemTextSize(mChoiceItemTextSize);
        dialog.setItemTextTypeface(mChoiceItemTextTypeface, mChoiceItemTextStyle);
        dialog.setItemTextGravity(mChoiceItemTextGravity);
        dialog.setItemTextPaddingDP(tvMChoiceItemPaddingStart, tvMChoiceItemPaddingTop,
                tvMChoiceItemPaddingEnd, tvMChoiceItemPaddingBottom);
        if(isMChoiceDividerDrawable) dialog.setDividerDrawable(mChoiceDividerDrawable);
        else dialog.setDividerColorId(mChoiceDividerColorId);
        dialog.setDividerHeightDp(mChoiceDividerHeightDp);
        return dialog;
    }

    /**
     * Создание и установка настроек MessageDialog.
     *
     * @return объект типа MessageDialog
     */
    public MessageDialog createMessageDialog() {
        HoloMessageDialog dialog = new HoloMessageDialog();
        setViewProperties(dialog);
        setTitleProperties(dialog);
        setButtonsProperties(dialog);
        dialog.setMessageColor(messageTextColor);
        dialog.setMessageSize(messageTextSize);
        dialog.setMessageFont(messageTextTypeface, messageTextStyle);
        if (messageTextGravity > -1) {
            dialog.setMessageGravity(messageTextGravity);
        }
        dialog.setMessagePaddingDP(tvMessagePaddingStart, tvMessagePaddingTop, tvMessagePaddingEnd, tvMessagePaddingBottom);
        return dialog;
    }

    public EditTextDialogFragment getEditTextDialog() {
        EditTextDialogFragment dialog = new EditTextDialogFragment();
        setViewProperties(dialog);
        setTitleProperties(dialog);
        setButtonsProperties(dialog);
        dialog.setEditTextSize(editTextSize);
        dialog.setEditTextColor(editTextColor);
        dialog.setEditTextTypeface(editTextTypeface, editTextStyle);
        dialog.setHintTextColor(hintTextColor);
        return dialog;
    }

    /**
     * Установка настроек главного view диалогового окна.
     *
     * @param dialog Объкт диалогового окна.
     */
    private void setViewProperties(HoloRootDialog dialog) {
        Log.d(Statical.TAG, "setBaseProperties");
        if (isBackgroundDialogRes) {
            dialog.setDialogBackgroundRes(backgroundDialogResId);
        } else {
            dialog.setDialogBackgroundColor(backgroundDialogColor);
        }
    }

    /**
     * Установка настроек заголовка диалогового окна.
     *
     * @param dialog Объкт диалогового окна.
     */
    private void setTitleProperties(HoloRootDialog dialog) {
        if (titleBgSet == TitleBgSet.RES) {
            dialog.setDialogBackgroundRes(layTitleBackgroundResId);
        } else if (titleBgSet == TitleBgSet.COLOR) {
            dialog.setDialogBackgroundColor(layTitleBackgroundColor);
        }
        dialog.setTitleTextColor(titleColor);
        dialog.setTitleTextSize(titleSize);
        dialog.setTitleTextFont(titleTypeface, titleStyle);
        if (titleGravity > -1) {
            dialog.setTitleGravity(titleGravity);
        }
        dialog.setTvTitlePaddingDP(tvTitlePaddingStart, tvTitlePaddingTop, tvTitlePaddingEnd, tvTitlePaddingBottom);
        if (isDividerTitleRes) {
            dialog.setDividerTitleBackground(dividerTitleResId);
        } else {
            dialog.setDividerTitleColor(dividerTitleColor);
        }
        dialog.setDividerTitleWidth(dividerTitleWidth);
    }

    /**
     * Установка настроек layout'а кнопок диалогового окна.
     *
     * @param dialog Объкт диалогового окна.
     */
    private void setButtonsProperties(HoloBaseDialog dialog) {
        dialog.setButtonsAllCaps(isTextBtnAllCaps);
        dialog.setButtonsSelectorId(buttonSelectorId);
        dialog.setTextButtonsColor(buttonTextColor);
        dialog.setTextButtonsSize(buttonTextSize);
        dialog.setTextButtonsFont(buttonTextTypeface, buttonTextStyle);
        if (buttonTextGravity > -1) {
            dialog.setTextButtonsGravity(buttonTextGravity);
        }
        if (isDividerBtnHorizontalRes) {
            dialog.setTopDividerBtnResId(dividerBtnHorizontalResId);
        } else {
            dialog.setTopDividerBtnColor(dividerBtnHorizontalColor);
        }
        if (isDividerBtnVerticalRes) {
            dialog.setLeftDividerBtnResId(dividerBtnVerticalResId);
            dialog.setRightDividerBtnResId(dividerBtnVerticalResId);
        } else {
            dialog.setLeftDividerBtnColor(dividerBtnVerticalColor);
            dialog.setRightDividerBtnColor(dividerBtnVerticalColor);
        }
        dialog.setTopDividerBtnWidth(dividerBtnHorizontalWidth);
        dialog.setLeftDividerBtnWidth(dividerBtnVerticalWidth);
        dialog.setRightDividerBtnWidth(dividerBtnVerticalWidth);
    }
}

