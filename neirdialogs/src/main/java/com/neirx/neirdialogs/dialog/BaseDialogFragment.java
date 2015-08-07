package com.neirx.neirdialogs.dialog;


import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Абстрактный класс диалогового окна в стиле Holo. Включает в себя настройку Заглавия и Кнопок окна,
 * а также цвет фона, размеры, цвета и стили шрифтов.
 */
public abstract class BaseDialogFragment extends DialogFragment {
    protected View view, lineBtnFirst, lineBtnSecond;
    protected ViewGroup layTitle;
    View layButtons;
    protected View dividerTitle;
    protected Button btnNegative, btnNeutral, btnPositive;
    protected TextView tvTitle;
    private String title, negativeButton, positiveButton, neutralButton;
    private View.OnClickListener negativeButtonListener, positiveButtonListener, neutralButtonListener;
    protected int buttonCount;
    protected int titleColor;
    protected float titleSize;
    protected Typeface titleTypeface;
    protected TextStyle titleStyle;
    protected int backgroundDialogResId;
    protected int backgroundDialogColor;
    protected int dividerTitleResId;
    protected int dividerTitleColor;
    protected int dividerTitleHeight;
    protected int buttonTextColor;
    protected float buttonTextSize;
    protected Typeface buttonTextTypeface;
    protected TextStyle buttonTextStyle;
    protected int buttonSelectorId;
    protected int dividerButtonsColor;


    /**
     * Абстрактный метод, в котором нужно установить layout с соответствующим диалоговым окном.
     * В методе должны быть определены такие view элементы:
     * layTitle - ViewGroup, который включает заглавие и разделительную линию.
     * tvTitle - TextView с тесктом заглавия.
     * dividerTitle - View, разделительная титульная полоса.
     * lineBtnFirst, lineBtnSecond - горизонтальные разделительные полосы между ккнопками.
     * btnNegative, btnNeutral, btnPositive - Button, соответствующие view кнопок.
     * layButtons - ViewGroup, который включает кнопки и разделительные линии.
     *
     * @param savedInstanceState Bundle с сохраненнім состоянием
     * @return Объект диалогового окна
     */
    @Override
    public abstract Dialog onCreateDialog(Bundle savedInstanceState);

    /**
     * Установка фона диалогового окна.
     *
     * @param resId ресурс с фоном
     */
    public void setDialogBackground(@DrawableRes int resId) {
        backgroundDialogResId = resId;
    }

    /**
     * Установка фона диалогового окна.
     *
     * @param color ресурс цвета фона
     */
    public void setDialogBackgroundColor(int color) {
        backgroundDialogColor = color;
    }

    /**
     * Установка фона титульной разделяющей линии.
     *
     * @param resId ресурс с фоном
     */
    public void setDividerTitleBackground(@DrawableRes int resId) {
        dividerTitleResId = resId;
    }

    /**
     * Установка цвета титульной разделяющей линии.
     *
     * @param color цвет
     */
    public void setDividerTitleColor(int color) {
        dividerTitleColor = color;
    }

    /**
     * Установка высоты титульной разделяющей линии.
     *
     * @param dpHeight высота в dp
     */
    public void setDividerTitleHeight(int dpHeight) {
        dividerTitleHeight = dpHeight;
    }

    /**
     * Установка текста заглавия.
     * Если не вызывать этот метод, то диалоговое окно не будет включать заглавие.
     *
     * @param title текст заглавия
     */
    public void setTitle(String title) {
        this.title = title;
    }


    /**
     * Установка цвета заглавия.
     *
     * @param color ресурс цвета
     */
    public void setTitleColor(int color) {
        titleColor = color;
    }

    /**
     * Установка размера шрифта заглавия.
     *
     * @param sizeSp размер шрифта
     */
    public void setTitleSize(float sizeSp) {
        titleSize = sizeSp;
    }

    /**
     * Установка шрифта и стиля отображения заглавия.
     *
     * @param tf    шрифт
     * @param style стиль текста
     */
    public void setTitleTypeface(Typeface tf, TextStyle style) {
        titleTypeface = tf;
        titleStyle = style;
    }

    /**
     * Установка цвета текста кнопок.
     *
     * @param color ресурс цвета
     */
    public void setButtonsTextColor(int color) {
        buttonTextColor = color;
    }

    /**
     * Установка размера текста кнопок.
     *
     * @param size размер текста
     */
    public void setButtonsTextSize(float size) {
        buttonTextSize = size;
    }

    /**
     * Установка шрифта и стиля отображения текста кнопок.
     *
     * @param tf    шрифт
     * @param style стиль текста
     */
    public void setButtonsTypeface(Typeface tf, TextStyle style) {
        buttonTextTypeface = tf;
        buttonTextStyle = style;

    }

    public void setDividerButtonsColor(int color) {
        dividerButtonsColor = color;

    }

    /**
     * Установка фона кнопок.
     *
     * @param resIdSelector ресурс фона
     */
    public void setButtonsBackground(@DrawableRes int resIdSelector) {
        buttonSelectorId = resIdSelector;

    }

    /**
     * Установка текста и слушателя Negative кнопки.
     * Если не вызывать этот метод, то диалоговое окно не будет включать Negative кнопку.
     *
     * @param titleButton текст кнопки
     * @param listener    слушатель
     */
    public void setNegativeButton(String titleButton, View.OnClickListener listener) {
        this.negativeButton = titleButton;
        this.negativeButtonListener = listener;
        if (positiveButton != null && neutralButton != null) {
            buttonCount = 3;
        } else if (positiveButton != null || neutralButton != null) {
            buttonCount = 2;
        } else {
            buttonCount = 1;
        }
    }

    /**
     * Установка текста и слушателя Positive кнопки.
     * Если не вызывать этот метод, то диалоговое окно не будет включать Positive кнопку.
     *
     * @param titleButton текст кнопки
     * @param listener    слушатель
     */
    public void setPositiveButton(String titleButton, View.OnClickListener listener) {
        this.positiveButton = titleButton;
        this.positiveButtonListener = listener;
        if (negativeButton != null && neutralButton != null) {
            buttonCount = 3;
        } else if (negativeButton != null || neutralButton != null) {
            buttonCount = 2;
        } else {
            buttonCount = 1;
        }
    }

    /**
     * Установка текста и слушателя Neutral кнопки.
     * Если не вызывать этот метод, то диалоговое окно не будет включать Neutral кнопку.
     *
     * @param titleButton текст кнопки
     * @param listener    слушатель
     */
    public void setNeutralButton(String titleButton, View.OnClickListener listener) {
        this.neutralButton = titleButton;
        this.neutralButtonListener = listener;
        if (negativeButton != null && positiveButton != null) {
            buttonCount = 3;
        } else if (negativeButton != null || positiveButton != null) {
            buttonCount = 2;
        } else {
            buttonCount = 1;
        }
    }

    /**
     * Метод проверки, установлено ли заглавие у окна.
     * Если не установлено, то соответствующее view скрывается.
     */
    protected void checkTitle() {
        if (title == null) {
            layTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setText(title);
            tvTitle.setTextColor(titleColor);
            tvTitle.setTextSize(titleSize);
            tvTitle.setTypeface(titleTypeface, titleStyle.getValue());
            if (dividerTitleResId == 0) {
                dividerTitle.setBackgroundColor(dividerTitleColor);
            } else {
                dividerTitle.setBackgroundResource(dividerTitleResId);
            }
            int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerTitleHeight,
                    getResources().getDisplayMetrics());
            dividerTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));

        }
    }

    /**
     * Метод проверки, какой фон устанавливать для диалогового окна.
     */
    protected void checkDialogBackground() {
        if (backgroundDialogResId == 0) {
            view.setBackgroundColor(backgroundDialogColor);
        } else {
            view.setBackgroundResource(backgroundDialogResId);
        }
    }

    /**
     * Метод проверки, установлены ли кнопки у окна.
     * Если не установлены, то соответствующие view скрываются.
     */
    protected void checkButtons() {
        //layButtons.setBackgroundColor(dividerButtonsColor);
        if (buttonTextColor != 0) {
            btnNegative.setTextColor(buttonTextColor);
            btnNeutral.setTextColor(buttonTextColor);
            btnPositive.setTextColor(buttonTextColor);
        }
        if (buttonTextSize != 0) {
            btnNegative.setTextSize(buttonTextSize);
            btnNeutral.setTextSize(buttonTextSize);
            btnPositive.setTextSize(buttonTextSize);
        }
        if (buttonTextTypeface != null && buttonTextStyle != null) {
            btnNegative.setTypeface(buttonTextTypeface, buttonTextStyle.getValue());
            btnNeutral.setTypeface(buttonTextTypeface, buttonTextStyle.getValue());
            btnPositive.setTypeface(buttonTextTypeface, buttonTextStyle.getValue());
        }
        if (buttonSelectorId != 0) {
            btnNegative.setBackgroundResource(buttonSelectorId);
            btnNeutral.setBackgroundResource(buttonSelectorId);
            btnPositive.setBackgroundResource(buttonSelectorId);
        }
        switch (buttonCount) {
            case 0:
                layButtons.setVisibility(View.GONE);
                break;
            case 1:
                lineBtnFirst.setVisibility(View.GONE);
                lineBtnSecond.setVisibility(View.GONE);
                btnNegative.setVisibility(View.GONE);
                btnPositive.setVisibility(View.GONE);
                if (negativeButton != null) {
                    btnNeutral.setText(negativeButton);
                    btnNeutral.setOnClickListener(negativeButtonListener);
                } else if (positiveButton != null) {
                    btnNeutral.setText(positiveButton);
                    btnNeutral.setOnClickListener(positiveButtonListener);
                } else {
                    btnNeutral.setText(neutralButton);
                    btnNeutral.setOnClickListener(neutralButtonListener);
                }
                break;
            case 2:
                btnNeutral.setVisibility(View.GONE);
                lineBtnSecond.setVisibility(View.GONE);
                if (negativeButton != null) {
                    btnNegative.setText(negativeButton);
                    btnNegative.setOnClickListener(negativeButtonListener);
                    if (positiveButton != null) {
                        btnPositive.setText(positiveButton);
                        btnPositive.setOnClickListener(positiveButtonListener);
                    } else {
                        btnPositive.setText(neutralButton);
                        btnPositive.setOnClickListener(neutralButtonListener);
                    }
                } else {
                    btnNegative.setText(neutralButton);
                    btnNegative.setOnClickListener(neutralButtonListener);
                    btnPositive.setText(positiveButton);
                    btnPositive.setOnClickListener(positiveButtonListener);
                }
                break;
            case 3:
                btnNegative.setText(negativeButton);
                btnNegative.setOnClickListener(negativeButtonListener);
                btnNeutral.setText(neutralButton);
                btnNeutral.setOnClickListener(neutralButtonListener);
                btnPositive.setText(positiveButton);
                btnPositive.setOnClickListener(positiveButtonListener);
                break;
        }
    }

}
