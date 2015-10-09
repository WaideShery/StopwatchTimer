package com.neirx.neirdialogs.dialogs;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.neirx.neirdialogs.Statical;
import com.neirx.neirdialogs.enums.TextStyle;
import com.neirx.neirdialogs.interfaces.NeirDialogInterface;

/**
 * Created by Waide Shery.
 *
 * Абстрактный базовый класс диалогового окна в стиле Holo.
 * Дополняется настройкой кнопок.
 */
public abstract class HoloBaseDialog extends HoloRootDialog implements View.OnClickListener {
    //Стандартные ресурсы кнопок диалога
    protected ViewGroup layButtons;
    protected View lineBtnTopHor;
    protected View lineBtnLeftVer;
    protected View lineBtnRightVer;
    protected Button btnNegative, btnNeutral, btnPositive;

    //Определяет, выводить ли текст кнопок в верхнем регистре.
    protected boolean isButtonsAllCaps;

    //Определяет какие кнопки установлены, от 0 до 7.
    // В формате 000(NEGATIVE_BTN, NEUTRAL_BTN, POSITIVE_BTN).
    protected int buttonCount;

    //Параметры кнопок
    private String negativeButton, positiveButton, neutralButton;
    protected int textColorNegativeBtn = -1, textColorPositiveBtn = -1, textColorNeutralBtn = -1;
    protected float textSizeNegativeBtn = 0, textSizePositiveBtn = 0, textSizeNeutralBtn = 0;
    protected Typeface textTypefaceNegativeBtn, textTypefacePositiveBtn, textTypefaceNeutralBtn;
    protected TextStyle textStyleNegativeBtn, textStylePositiveBtn, textStyleNeutralBtn;
    protected int textGravityNegativeBtn = -1, textGravityPositiveBtn = -1, textGravityNeutralBtn = -1;

    //Селекторы кнопок
    protected
    @DrawableRes
    int negativeBtnSelectorId = -1, positiveBtnSelectorId = -1, neutralBtnSelectorId = -1;

    //Параметры и фон разделяющих линий кнопок
    private int topDividerBtnColor = -1, leftDividerBtnColor = -1, rightDividerBtnColor = -1;
    private @DrawableRes int topDividerBtnResId = -1, leftDividerBtnResId = -1, rightDividerBtnResId = -1;
    private boolean isTopDividerBtnRes, isLeftDividerBtnRes, isRightDividerBtnRes;

    protected float topDividerBtnWidth = 0, leftDividerBtnWidth = 0, rightDividerBtnWidth = 0;

    //Обработчик нажатий на кнопки
    protected NeirDialogInterface.OnClickListener onClickListener;

    /**
     * Абстрактный метод, в котором нужно установить layout с соответствующим диалоговым окном.
     * В методе должны быть определены такие view элементы super-класса:
     * layTitle - ViewGroup, который включает заогловок и разделительную линию.
     * tvTitle - TextView с тесктом заголовка.
     * dividerTitle - View, разделительная титульная полоса.
     *
     * А также, view элементы кнопок:
     * layButtons - ViewGroup, который включает кнопки и разделительные линии.
     * lineBtnTopHor - горизонтальная отделяющая кнопки полоса.
     * lineBtnLeftVer, lineBtnRightVer - вертикальные разделительные полосы между кнопками.
     * btnNegative, btnNeutral, btnPositive - Button, соответствующие view кнопок.
     *
     * @param savedInstanceState Bundle с сохраненным состоянием
     * @return Объект диалогового окна
     */
    @Override
    public abstract Dialog onCreateDialog(Bundle savedInstanceState);

    public void setButtonsAllCaps(boolean isButtonsAllCaps){
        this.isButtonsAllCaps = isButtonsAllCaps;
    }
    /**
     * Установка текста Positive кнопки.
     * Если не вызывать этот метод, то диалоговое окно не будет включать Positive кнопку.
     *
     * @param titleButton текст кнопки
     */
    public void setPositiveButton(String titleButton) {
        this.positiveButton = titleButton;
        defineCount();
    }

    /**
     * Установка текста Neutral кнопки.
     * Если не вызывать этот метод, то диалоговое окно не будет включать Neutral кнопку.
     *
     * @param titleButton текст кнопки
     */
    public void setNeutralButton(String titleButton) {
        this.neutralButton = titleButton;
        defineCount();
    }

    /**
     * Установка текста Negative кнопки.
     * Если не вызывать этот метод, то диалоговое окно не будет включать Negative кнопку.
     *
     * @param titleButton текст кнопки
     */
    public void setNegativeButton(String titleButton) {
        this.negativeButton = titleButton;
        defineCount();
    }

    /**
     * Метод для определения какие кнопки установлены. Установливает buttonCount.
     * Соответствует цифрам в двоичном формате. Ноль - кнопка не установлена, единица - установлена.
     * Первая цыфра - negativeButton, вторая - neutralButton, третья - positiveButton.
     * Примеры:
     * 000 - ниодна кнопка не установлена, buttonCount = 0;
     * 001 - установлена только positiveButton, buttonCount = 1;
     * 110 - установлены negativeButton и neutralButton buttonCount = 6.
     */
    private void defineCount() {
        if (negativeButton == null && neutralButton == null && positiveButton == null) {
            buttonCount = 0;
        }
        if (negativeButton == null && neutralButton == null && positiveButton != null) {
            buttonCount = 1;
        }
        if (negativeButton == null && neutralButton != null && positiveButton == null) {
            buttonCount = 2;
        }
        if (negativeButton == null && neutralButton != null && positiveButton != null) {
            buttonCount = 3;
        }
        if (negativeButton != null && neutralButton == null && positiveButton == null) {
            buttonCount = 4;
        }
        if (negativeButton != null && neutralButton == null && positiveButton != null) {
            buttonCount = 5;
        }
        if (negativeButton != null && neutralButton != null && positiveButton == null) {
            buttonCount = 6;
        }
        if (negativeButton != null && neutralButton != null && positiveButton != null) {
            buttonCount = 7;
        }
    }

    /**
     * Установка цвета текста всех кнопок.
     *
     * @param color    цвет текста
     */
    public void setTextButtonsColor(int color){
        textColorNegativeBtn = color;
        textColorNeutralBtn = color;
        textColorPositiveBtn = color;
    }

    /**
     * Установка размера текста всех кнопок.
     *
     * @param sizeSP   размер текста в sp
     */
    public void setTextButtonsSize(int sizeSP){
        textSizeNegativeBtn = sizeSP;
        textSizePositiveBtn = sizeSP;
        textSizeNeutralBtn = sizeSP;
    }

    /**
     * Установка шрифта всех кнопок.
     *
     * @param typeface шрифт
     * @param style    стиль
     */
    public void setTextButtonsFont(Typeface typeface, TextStyle style){
        textTypefaceNegativeBtn = typeface;
        textTypefacePositiveBtn = typeface;
        textTypefaceNeutralBtn = typeface;

        textStyleNegativeBtn = style;
        textStylePositiveBtn = style;
        textStyleNeutralBtn = style;
    }

    /**
     * Установка выравнивания текста всех кнопок.
     *
     * @param gravity выравнивание {@link android.view.Gravity}
     */
    public void setTextButtonsGravity(int gravity){
        textGravityNegativeBtn = gravity;
        textGravityNeutralBtn = gravity;
        textGravityPositiveBtn = gravity;
    }

    /**
     * Установка цвета текста Negative кнопки.
     *
     * @param color    цвет текста
     */
    public void setTextNegativeBtnColor(int color){
        textColorPositiveBtn = color;
    }

    /**
     * Установка размера текста Negative кнопки.
     *
     * @param sizeSP   размер текста в sp
     */
    public void setTextNegativeBtnSize(int sizeSP){
        textSizePositiveBtn = sizeSP;
    }

    /**
     * Установка шрифта Negative кнопки.
     *
     * @param typeface шрифт
     * @param style    стиль
     */
    public void setTextNegativeBtnFont(Typeface typeface, TextStyle style){
        textTypefacePositiveBtn = typeface;
        textStylePositiveBtn = style;
    }

    /**
     * Установка выравнивания текста Negative кнопки.
     *
     * @param gravity выравнивание {@link android.view.Gravity}
     */
    public void setTextNegativeBtnGravity(int gravity){
        textGravityPositiveBtn = gravity;
    }

    /**
     * Установка цвета текста Neutral кнопки.
     *
     * @param color    цвет текста
     */
    public void setTextNeutralBtnColor(int color){
        textColorPositiveBtn = color;
    }

    /**
     * Установка размера текста Neutral кнопки.
     *
     * @param sizeSP   размер текста в sp
     */
    public void setTextNeutralBtnSize(int sizeSP){
        textSizePositiveBtn = sizeSP;
    }

    /**
     * Установка шрифта Neutral кнопки.
     *
     * @param typeface шрифт
     * @param style    стиль
     */
    public void setTextNeutralBtnFont(Typeface typeface, TextStyle style){
        textTypefacePositiveBtn = typeface;
        textStylePositiveBtn = style;
    }

    /**
     * Установка выравнивания текста Neutral кнопки.
     *
     * @param gravity выравнивание {@link android.view.Gravity}
     */
    public void setTextNeutralBtnGravity(int gravity){
        textGravityPositiveBtn = gravity;
    }

    /**
     * Установка цвета текста Positive кнопки.
     *
     * @param color    цвет текста
     */
    public void setTextPositiveBtnColor(int color){
        textColorPositiveBtn = color;
    }

    /**
     * Установка размера текста Positive кнопки.
     *
     * @param sizeSP   размер текста в sp
     */
    public void setTextPositiveBtnSize(int sizeSP){
        textSizePositiveBtn = sizeSP;
    }

    /**
     * Установка шрифта Positive кнопки.
     *
     * @param typeface шрифт
     * @param style    стиль
     */
    public void setTextPositiveBtnFont(Typeface typeface, TextStyle style){
        textTypefacePositiveBtn = typeface;
        textStylePositiveBtn = style;
    }

    /**
     * Установка выравнивания текста Positive кнопки.
     *
     * @param gravity выравнивание {@link android.view.Gravity}
     */
    public void setTextPositiveBtnGravity(int gravity){
        textGravityPositiveBtn = gravity;
    }

    /**
     * Установка селектор всех кнопок.
     *
     * @param resIdSelector ресурс селектора.
     */
    public void setButtonsSelectorId(@DrawableRes int resIdSelector) {
        negativeBtnSelectorId = resIdSelector;
        positiveBtnSelectorId = resIdSelector;
        neutralBtnSelectorId = resIdSelector;
    }

    /**
     * Установка селектора Negative кнопки.
     *
     * @param resIdSelector ресурс селектора.
     */
    public void setNegativeBtnSelectorId(@DrawableRes int resIdSelector) {
        negativeBtnSelectorId = resIdSelector;
    }

    /**
     * Установка селектора Neutral кнопки.
     *
     * @param resIdSelector ресурс селектора.
     */
    public void setNeutralBtnSelectorId(@DrawableRes int resIdSelector) {
        neutralBtnSelectorId = resIdSelector;
    }

    /**
     * Установка селектора Positive кнопки.
     *
     * @param resIdSelector ресурс селектора.
     */
    public void setPositiveBtnSelectorId(@DrawableRes int resIdSelector) {
        positiveBtnSelectorId = resIdSelector;
    }

    /**
     * Установка цвета всех разделяющих линий кнопок.
     *
     * @param color цвет
     */
    public void setDividersBtnColor(int color) {
        topDividerBtnColor = color;
        leftDividerBtnColor = color;
        rightDividerBtnColor = color;
        isTopDividerBtnRes = false;
        isLeftDividerBtnRes = false;
        isRightDividerBtnRes = false;
    }

    /**
     * Установка цвета верхней горизонтальной разделяющей линии кнопок.
     *
     * @param color цвет
     */
    public void setTopDividerBtnColor(int color) {
        topDividerBtnColor = color;
        isTopDividerBtnRes = false;
    }

    /**
     * Установка цвета левой вертикальной разделяющей линии кнопок.
     *
     * @param color цвет
     */
    public void setLeftDividerBtnColor(int color) {
        Log.d(Statical.TAG, "setLeftDividerBtnColor" );
        leftDividerBtnColor = color;
        isLeftDividerBtnRes = false;
    }

    /**
     * Установка цвета правой вертикальной разделяющей линии кнопок.
     *
     * @param color цвет
     */
    public void setRightDividerBtnColor(int color) {
        rightDividerBtnColor = color;
        isRightDividerBtnRes = false;
    }

    /**
     * Установка ресурса фона всех разделяющих линий кнопок.
     *
     * @param resId ресурс фона
     */
    public void setDividersBtnResId(@DrawableRes int resId) {
        topDividerBtnResId = resId;
        leftDividerBtnResId = resId;
        rightDividerBtnResId = resId;
        isTopDividerBtnRes = true;
        isLeftDividerBtnRes = true;
        isRightDividerBtnRes = true;
    }

    /**
     * Установка ресурса фона верхней горизонтальной разделяющей линии кнопок.
     *
     * @param resId ресурс фона
     */
    public void setTopDividerBtnResId(@DrawableRes int resId) {
        topDividerBtnResId = resId;
        isTopDividerBtnRes = true;
    }

    /**
     * Установка ресурса фона левой вертикальной разделяющей линии кнопок.
     *
     * @param resId ресурс фона
     */
    public void setLeftDividerBtnResId(@DrawableRes int resId) {
        leftDividerBtnResId = resId;
        isLeftDividerBtnRes = true;
    }

    /**
     * Установка ресурса фона правой вертикальной разделяющей линии кнопок.
     *
     * @param resId ресурс фона
     */
    public void setRightDividerBtnResId(@DrawableRes int resId) {
        rightDividerBtnResId = resId;
        isRightDividerBtnRes = true;
    }

    /**
     * Установка ширины всех разделяющих линий кнопок.
     *
     * @param widthDp ширина в dp
     */
    public void setDividersBtnWidth(float widthDp) {
        topDividerBtnWidth = widthDp;
        leftDividerBtnWidth = widthDp;
        rightDividerBtnWidth = widthDp;
    }

    /**
     * Установка ширины верхней горизонтальной разделяющей линии кнопок.
     *
     * @param widthDp ширина в dp
     */
    public void setTopDividerBtnWidth(float widthDp) {
        topDividerBtnWidth = widthDp;
    }

    /**
     * Установка ширины левой вертикальной разделяющей линии кнопок.
     *
     * @param widthDp ширина в dp
     */
    public void setLeftDividerBtnWidth(float widthDp) {
        leftDividerBtnWidth = widthDp;
    }


    /**
     * Установка ширины правой вертикальной разделяющей линии кнопок.
     *
     * @param widthDp ширина в dp
     */
    public void setRightDividerBtnWidth(float widthDp) {
        rightDividerBtnWidth = widthDp;
    }

    /**
     * Установка слушателя нажатия кнопок в диалоговом окне
     *
     * @param listener слушатель
     * @param tag      идентификатор диалогового окна
     */
    public void setOnClickListener(NeirDialogInterface.OnClickListener listener, String tag) {
        onClickListener = listener;
        this.tag = tag;
    }

    /**
     * Метод проверки, установлены ли кнопки у окна.
     * Если не установлены, то соответствующие view скрываются.
     */
    protected void checkButtons() {
        switch (buttonCount){
            case 0:
                layButtons.setVisibility(View.GONE);
                break;
            case 1:
                lineBtnLeftVer.setVisibility(View.GONE);
                lineBtnRightVer.setVisibility(View.GONE);
                btnNegative.setVisibility(View.GONE);
                btnNeutral.setVisibility(View.GONE);
                setPositiveBtnParam();
                setTopLineParam();
                break;
            case 2:
                lineBtnLeftVer.setVisibility(View.GONE);
                lineBtnRightVer.setVisibility(View.GONE);
                btnPositive.setVisibility(View.GONE);
                btnNegative.setVisibility(View.GONE);
                setNeutralBtnParam();
                setTopLineParam();
                break;
            case 3:
                lineBtnLeftVer.setVisibility(View.GONE);
                btnNegative.setVisibility(View.GONE);
                setPositiveBtnParam();
                setNeutralBtnParam();
                setTopLineParam();
                setRightLineParam();
                break;
            case 4:
                lineBtnLeftVer.setVisibility(View.GONE);
                lineBtnRightVer.setVisibility(View.GONE);
                btnPositive.setVisibility(View.GONE);
                btnNeutral.setVisibility(View.GONE);
                setNegativeBtnParam();
                setTopLineParam();
                break;
            case 5:
                lineBtnLeftVer.setVisibility(View.GONE);
                btnNeutral.setVisibility(View.GONE);
                setNegativeBtnParam();
                setPositiveBtnParam();
                setRightLineParam();
                setTopLineParam();
                break;
            case 6:
                lineBtnRightVer.setVisibility(View.GONE);
                btnPositive.setVisibility(View.GONE);
                setNegativeBtnParam();
                setNeutralBtnParam();
                setLeftLineParam();
                setTopLineParam();
                break;
            case 7:
                setLeftLineParam();
                setRightLineParam();
                setTopLineParam();
                setNegativeBtnParam();
                setNeutralBtnParam();
                setPositiveBtnParam();
                break;
        }
    }

    /**
     * Установка параметров верхней горизонтальной линии кнопок.
     */
    protected void setTopLineParam(){
        if(isTopDividerBtnRes)lineBtnTopHor.setBackgroundResource(topDividerBtnResId);
        else lineBtnTopHor.setBackgroundColor(topDividerBtnColor);
        if(topDividerBtnWidth > 0) {
            int height = (int) getPX(topDividerBtnWidth);
            lineBtnTopHor.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height));
        }
    }

    /**
     * Установка параметров левой вертикальной линии кнопок.
     */
    protected void setLeftLineParam(){
        if(isLeftDividerBtnRes)lineBtnLeftVer.setBackgroundResource(leftDividerBtnResId);
        else lineBtnLeftVer.setBackgroundColor(leftDividerBtnColor);
        Log.d(Statical.TAG, "leftDividerBtnColor = " + leftDividerBtnColor);
        if(leftDividerBtnWidth > 0) {
            int width = (int) getPX(leftDividerBtnWidth);
            lineBtnLeftVer.setLayoutParams(new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    /**
     * Установка параметров правой вертикальной линии кнопок.
     */
    protected void setRightLineParam(){
        if(isRightDividerBtnRes)lineBtnRightVer.setBackgroundResource(rightDividerBtnResId);
        else lineBtnRightVer.setBackgroundColor(rightDividerBtnColor);
        Log.d(Statical.TAG, "rightDividerBtnColor = " + rightDividerBtnColor);
        if(rightDividerBtnWidth > 0) {
            int width = (int) getPX(rightDividerBtnWidth);
            lineBtnRightVer.setLayoutParams(new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT));
        }
    }

    private String toLowCase(String text){
        char[] chars = text.toCharArray();
        for (int i=0; i<chars.length; i++){
            if(i==0) continue;
            chars[i] = Character.toLowerCase(chars[i]);
        }
        return new String(chars);
    }

    /**
     * Установка параметрой Negative кнопки.
     */
    protected void setNegativeBtnParam(){
        if(Build.VERSION.SDK_INT >= 14){
            btnNegative.setAllCaps(isButtonsAllCaps);
            btnNegative.setText(negativeButton);
        } else {
            if(isButtonsAllCaps) btnNegative.setText(negativeButton.toUpperCase());
            else btnNegative.setText(toLowCase(negativeButton));
        }
        if(textColorNegativeBtn > -1) btnNegative.setTextColor(textColorNegativeBtn);
        if(textSizeNegativeBtn > 0) btnNegative.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeNegativeBtn);

        if(textTypefaceNegativeBtn != null && textStyleNegativeBtn != null)
            btnNegative.setTypeface(textTypefaceNegativeBtn, textStyleNegativeBtn.getValue());
        else if(textTypefaceNegativeBtn != null) btnNegative.setTypeface(textTypefaceNegativeBtn);
        else if(textStyleNegativeBtn != null) btnNegative.setTypeface(Typeface.DEFAULT, textStyleNegativeBtn.getValue());

        if(textGravityNegativeBtn > -1) btnNegative.setGravity(textGravityNegativeBtn);
        if(negativeBtnSelectorId > -1) btnNegative.setBackgroundResource(negativeBtnSelectorId);
        btnNegative.setOnClickListener(this);
    }

    /**
     * Установка параметрой Neutral кнопки.
     */
    protected void setNeutralBtnParam(){
        if(Build.VERSION.SDK_INT >= 14){
            btnNeutral.setAllCaps(isButtonsAllCaps);
            btnNeutral.setText(neutralButton);
        } else {
            if(isButtonsAllCaps) btnNeutral.setText(neutralButton.toUpperCase());
            else btnNeutral.setText(toLowCase(neutralButton));
        }
        if(textColorNeutralBtn > -1) btnNeutral.setTextColor(textColorNeutralBtn);
        if(textSizeNeutralBtn > 0) btnNeutral.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeNeutralBtn);

        if(textTypefaceNeutralBtn != null && textStyleNeutralBtn != null)
            btnNeutral.setTypeface(textTypefaceNeutralBtn, textStyleNeutralBtn.getValue());
        else if(textTypefaceNeutralBtn != null) btnNeutral.setTypeface(textTypefaceNeutralBtn);
        else if(textStyleNeutralBtn != null) btnNeutral.setTypeface(Typeface.DEFAULT, textStyleNeutralBtn.getValue());

        if(textGravityNeutralBtn > -1) btnNeutral.setGravity(textGravityNeutralBtn);
        if(neutralBtnSelectorId > -1) btnNeutral.setBackgroundResource(neutralBtnSelectorId);
        btnNeutral.setOnClickListener(this);
    }

    /**
     * Установка параметрой Positive кнопки.
     */
    protected void setPositiveBtnParam(){
        if(Build.VERSION.SDK_INT >= 14){
            btnPositive.setAllCaps(isButtonsAllCaps);
            btnPositive.setText(positiveButton);
        } else {
            if(isButtonsAllCaps) btnPositive.setText(positiveButton.toUpperCase());
            else btnPositive.setText(toLowCase(positiveButton));
        }
        if(textColorPositiveBtn > -1) btnPositive.setTextColor(textColorPositiveBtn);
        if(textSizePositiveBtn > 0) btnPositive.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizePositiveBtn);

        if(textTypefacePositiveBtn != null && textStylePositiveBtn != null)
            btnPositive.setTypeface(textTypefacePositiveBtn, textStylePositiveBtn.getValue());
        else if(textTypefacePositiveBtn != null) btnPositive.setTypeface(textTypefacePositiveBtn);
        else if(textStylePositiveBtn != null) btnPositive.setTypeface(Typeface.DEFAULT, textStylePositiveBtn.getValue());

        if(textGravityPositiveBtn > -1) btnPositive.setGravity(textGravityPositiveBtn);
        if(positiveBtnSelectorId > -1) btnPositive.setBackgroundResource(positiveBtnSelectorId);
        btnPositive.setOnClickListener(this);
    }

    /**
     * Абстрактный метод для обработки нажатий на кнопки.
     * Следует использовать метод onClick(String tag, int buttonId, Object extraData)
     * объекта {@link #onClickListener} для возврата нажатой кнопки в buttonId и, если нужно,
     * дополнительных данных в extraData. Для идентификации диалогового окна в параметре tag
     * указывается строка {@link #tag}.
     */
    @Override
    public abstract void onClick(View view);
}
