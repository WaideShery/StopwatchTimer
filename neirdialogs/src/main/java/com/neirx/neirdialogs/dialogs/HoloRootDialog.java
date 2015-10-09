package com.neirx.neirdialogs.dialogs;


import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neirx.neirdialogs.enums.TextStyle;

/**
 * Created by Waide Shery.
 *
 * Абстрактный класс диалогового окна в стиле Holo.
 * Включает в себя настройку заголовка и фона.
 */
public abstract class HoloRootDialog extends DialogFragment {
    //Главное View диалогового окна
    protected View view;

    //Стандартные ресурсы заголовка
    protected LinearLayout layTitle;
    protected TextView tvTitle;
    protected View dividerTitle;


    /**
     * Фон диалогового окна. Если isBackgroundDialogRes = true, то фоном устанавливается
     * ресурс isBackgroundDialogRes. В противном случае - backgroundDialogColor.
     */
    protected @DrawableRes int backgroundDialogResId;
    protected int backgroundDialogColor;
    protected boolean isBackgroundDialogRes;

    //Фон layout'а заголовка. Если фон не установлен, то его значение -1.
    protected @DrawableRes int layTitleBackgroundResId;
    protected int layTitleBackgroundColor;
    protected enum TitleBgSet {NO, COLOR, RES}
    protected TitleBgSet titleBgSet = TitleBgSet.NO;

    //Параметры текстового поля заголовка
    private String title;
    protected int titleColor;
    protected float titleSize = -1;
    protected Typeface titleTypeface;
    protected TextStyle titleStyle;
    protected int titleGravity = -1;
    protected int tvTitlePaddingStart  = -1, tvTitlePaddingTop = -1, tvTitlePaddingEnd = -1, tvTitlePaddingBottom = -1;
    protected Drawable tvTitleCompoundDrawablesStart, tvTitleCompoundDrawablesTop, tvTitleCompoundDrawablesEnd,
            tvTitleCompoundDrawablesBottom;

    //Параметры и фон разделяющей линии заголовка
    protected boolean isDividerTitleRes = false;
    protected @DrawableRes int dividerTitleResId;
    protected int dividerTitleColor;
    protected float dividerTitleWidth = 0;

    //Идентификатор диалогового окна
    protected String tag;


    /**
     * Абстрактный метод, в котором нужно установить layout с соответствующим диалоговым окном.
     * В методе должны быть определены такие view элементы:
     * layTitle - ViewGroup, который включает заогловок и разделительную линию.
     * tvTitle - TextView с тесктом заголовка.
     * dividerTitle - View, разделительная титульная полоса.
     *
     * @param savedInstanceState Bundle с сохраненным состоянием
     * @return Объект диалогового окна
     */
    @Override
    public abstract Dialog onCreateDialog(Bundle savedInstanceState);

    /**
     * Установка ресурса фоном диалогового окна. Если до этого был установлен цвет фона диалогового
     * окна, то он сбрасывается.
     *
     * @param resId ресурс с фоном
     */
    public void setDialogBackgroundRes(@DrawableRes int resId) {
        backgroundDialogResId = resId;
        isBackgroundDialogRes = true;
    }

    /**
     * Установка цвета фона диалогового окна. Если до этого был установлен ресурс фона диалогового
     * окна, то он сбрасывается.
     *
     * @param color ресурс цвета фона
     */
    public void setDialogBackgroundColor(int color) {
        backgroundDialogColor = color;
        isBackgroundDialogRes = false;
    }

    /**
     * Установка текста заголовка.
     * Если не вызывать этот метод, то диалоговое окно не будет включать заголовок.
     *
     * @param title текст заголовка
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Установка ресурса фона layout'а заголовка. Если до этого был установлен цвет фона
     * layout'а заголовка окна, то он сбрасывается.
     *
     * @param resId ресурс с фоном
     */
    public void setLayTitleBackground(@DrawableRes int resId) {
        layTitleBackgroundResId = resId;
        titleBgSet = TitleBgSet.RES;
    }

    /**
     * Установка цвета фона layout'а заголовка. Если до этого был установлен ресурс фона layout'а
     * заголовка, то он сбрасывается.
     *
     * @param color ресурс цвета фона
     */
    public void setLayTitleBackgroundColor(int color) {
        layTitleBackgroundColor = color;
        titleBgSet = TitleBgSet.COLOR;
    }

    /**
     * Установка цвета текста заголовка
     *
     * @param textColor  цвет текста
     */
    public void setTitleTextColor(int textColor){
        titleColor = textColor;
    }

    /**
     * Установка размера текста заголовка
     *
     * @param textSizeSP размер текста в sp
     */
    public void setTitleTextSize(int textSizeSP){
        titleSize = textSizeSP;
    }

    /**
     * Установка шрифта заголовка
     *
     * @param typeface   шрифт
     * @param textStyle  стил
     */
    public void setTitleTextFont(Typeface typeface, TextStyle textStyle){
        titleTypeface = typeface;
        titleStyle = textStyle;
    }

    /**
     * Установка выравнивания заголовка.
     * @param gravity выравнивание, смотреть {@link android.view.Gravity}
     */
    public void setTitleGravity(int gravity){
        titleGravity = gravity;
    }

    /**
     * Установка отступов в текстовом поле заголовка в dp.
     *
     * @param start  начало
     * @param top    верх
     * @param end    конец
     * @param bottom низ
     */
    public void setTvTitlePaddingDP(int start, int top, int end, int bottom) {
        tvTitlePaddingStart = start;
        tvTitlePaddingTop = top;
        tvTitlePaddingEnd = end;
        tvTitlePaddingBottom = bottom;
    }

    /**
     * Устанавливает изображение в текстовом поле в соответствующем месте. Если не нужно устанавливать
     * изображение, ставится null вместо него.
     *
     * @param start  начало
     * @param top    верх
     * @param end    конец
     * @param bottom низ
     */
    public void setTvTitleCompoundDrawables(@Nullable Drawable start, @Nullable Drawable top,
                                            @Nullable Drawable end, @Nullable Drawable bottom) {
        tvTitleCompoundDrawablesStart = start;
        tvTitleCompoundDrawablesTop = top;
        tvTitleCompoundDrawablesEnd = end;
        tvTitleCompoundDrawablesBottom = bottom;
        if(title == null){
            title = "";
        }
    }

    /**
     * Установка фона титульной разделяющей линии. Если до этого был установлен цвет
     * титульной разделяющей линии, то он сбрасывается
     *
     * @param resId ресурс с фоном
     */
    public void setDividerTitleBackground(@DrawableRes int resId) {
        dividerTitleResId = resId;
        isDividerTitleRes = true;
    }

    /**
     * Установка цвета титульной разделяющей линии. Если до этого был установлен фон
     * титульной разделяющей линии, то он сбрасывается
     *
     * @param color цвет
     */
    public void setDividerTitleColor(int color) {
        dividerTitleColor = color;
        isDividerTitleRes = false;
    }

    /**
     * Установка толщины титульной разделяющей линии.
     *
     * @param dpWidth высота в dp
     */
    public void setDividerTitleWidth(float dpWidth) {
        dividerTitleWidth = dpWidth;
    }

    /**
     * Проверка параметров корневого view диалога.
     * Установка фона диалогового окна.
     */
    protected void checkRootView(){
        if(isBackgroundDialogRes) view.setBackgroundResource(backgroundDialogResId);
        else view.setBackgroundColor(backgroundDialogColor);
    }

    /**
     * Проверка, установлен ли заоголовок у окна.
     * Если не установлен, то соответствующее view скрывается.
     * Установка параметров заголовка.
     */
    protected void checkTitle() {
        if (title == null) {
            layTitle.setVisibility(View.GONE);
            return;
        }
        if(titleBgSet == TitleBgSet.RES) layTitle.setBackgroundResource(layTitleBackgroundResId);
        else if(titleBgSet == TitleBgSet.COLOR) layTitle.setBackgroundColor(layTitleBackgroundColor);

        tvTitle.setText(title);
        tvTitle.setTextColor(titleColor);
        if(titleSize > 0) tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleSize);
        if(titleTypeface != null && titleStyle != null) tvTitle.setTypeface(titleTypeface, titleStyle.getValue());
        else if(titleTypeface != null) tvTitle.setTypeface(titleTypeface);
        else if(titleStyle != null) tvTitle.setTypeface(Typeface.DEFAULT, titleStyle.getValue());

        if(titleGravity > -1) {
            layTitle.setGravity(titleGravity);
        }

        if(tvTitlePaddingStart > -1) {
            if (Build.VERSION.SDK_INT >= 16) {
                tvTitle.setPaddingRelative(tvTitlePaddingStart, tvTitlePaddingTop, tvTitlePaddingEnd, tvTitlePaddingBottom);
            } else {
                tvTitle.setPadding(tvTitlePaddingStart, tvTitlePaddingTop, tvTitlePaddingEnd, tvTitlePaddingBottom);
            }
        }
        if(Build.VERSION.SDK_INT >= 17) {
            tvTitle.setCompoundDrawablesRelative(tvTitleCompoundDrawablesStart, tvTitleCompoundDrawablesTop,
                    tvTitleCompoundDrawablesEnd, tvTitleCompoundDrawablesBottom);
        } else {
            tvTitle.setCompoundDrawables(tvTitleCompoundDrawablesStart, tvTitleCompoundDrawablesTop,
                    tvTitleCompoundDrawablesEnd, tvTitleCompoundDrawablesBottom);
        }

        if (isDividerTitleRes) dividerTitle.setBackgroundResource(dividerTitleResId);
        else dividerTitle.setBackgroundColor(dividerTitleColor);

        if(dividerTitleWidth > 0) {
            int height = (int) getPX(dividerTitleWidth);
            dividerTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height));
        }

    }

    /**
     * Вычисление dp.
     */
    protected float getPX(float value){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
    }
}
