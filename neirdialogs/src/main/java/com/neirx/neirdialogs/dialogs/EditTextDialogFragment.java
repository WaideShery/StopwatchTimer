package com.neirx.neirdialogs.dialogs;


import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neirx.neirdialogs.R;
import com.neirx.neirdialogs.enums.TextStyle;
import com.neirx.neirdialogs.interfaces.NeirDialogInterface;


public class EditTextDialogFragment extends HoloBaseDialog {
    protected EditText editText;
    protected String editTextMessage;
    protected int editTextColor;
    protected float editTextSize;
    protected TextStyle editTextStyle;
    protected Typeface editTextTypeface;
    protected String hintTextMessage;
    protected int hintTextColor;
    protected float hintTextSize;
    protected Typeface hintTextTypeface;
    protected TextStyle hintTextStyle;
    protected int ems;

    /**
     * Установка текста в EditText диалогового окна.
     * Если не вызывать этот метод, то поле EditText будет пустым.
     *
     * @param text текст
     */
    public void setEditText(String text) {
        editTextMessage = text;
    }

    /**
     * Установка цвета текста в EditText.
     *
     * @param color ресурс цвета
     */
    public void setEditTextColor(int color) {
        editTextColor = color;
    }

    /**
     * Установка размера текста в EditText.
     *
     * @param sizeSp размер шрифта
     */
    public void setEditTextSize(float sizeSp) {
        editTextSize = sizeSp;
    }

    /**
     * Установка шрифта и стиля отображения текста в EditText.
     *
     * @param tf    шрифт
     * @param style стиль текста
     */
    public void setEditTextTypeface(Typeface tf, TextStyle style) {
        editTextTypeface = tf;
        editTextStyle = style;
    }

    public void setEms(int ems){
        this.ems = ems;
    }
    /**
     * Установка текста-подсказки в EditText.
     * Если не вызывать этот метод, то поле EditText не будет содержать текста-подсказки.
     *
     * @param text текст
     */
    public void setHintText(String text) {
        hintTextMessage = text;
    }

    /**
     * Установка цвета текста-подсказки в EditText.
     *
     * @param color ресурс цвета
     */
    public void setHintTextColor(int color) {
        hintTextColor = color;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.holo_edittext_dialog, null);
        lineBtnTopHor = view.findViewById(R.id.viewTop);
        lineBtnLeftVer = view.findViewById(R.id.viewLeft);
        lineBtnRightVer = view.findViewById(R.id.viewRight);
        btnNegative = (Button) view.findViewById(R.id.btnNegative);
        btnNeutral = (Button) view.findViewById(R.id.btnNeutral);
        btnPositive = (Button) view.findViewById(R.id.btnPositive);
        layTitle = (LinearLayout) view.findViewById(R.id.layTitle);
        layButtons = (ViewGroup) view.findViewById(R.id.layButtons);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        dividerTitle = view.findViewById(R.id.dividerTitle);

        //Специфичные ресурсы для диалога
        editText = (EditText) view.findViewById(R.id.editText);

        checkRootView();
        checkTitle();
        checkButtons();
        checkEditText();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        // Create the AlertDialog object and return it
        return builder.create();
    }

    /**
     * Метод нажатия на кнопки диалогового окна.
     * Вызывает NeirDialogInterface.OnClickListener.onClick(String tag, int buttonId, Object extraData).
     * В объекте extraData передает String - стоку, содержащуюся в поле EditText.
     */
    @Override
    public void onClick(View view) {
        dismiss();
        if (onClickListener == null) {
            return;
        }
        int id = view.getId();
        if (id == R.id.btnPositive) {
            onClickListener.onClick(tag, NeirDialogInterface.BUTTON_POSITIVE, editText.getText().toString());
        } else if (id == R.id.btnNegative) {
            onClickListener.onClick(tag, NeirDialogInterface.BUTTON_NEGATIVE, editText.getText().toString());
        } else if (id == R.id.btnNeutral) {
            onClickListener.onClick(tag, NeirDialogInterface.BUTTON_NEUTRAL, editText.getText().toString());
        }
    }

    /**
     * Метод проверки, установлен ли текст EditText.
     * Если не установлен, то поле EditText остается пустым.
     * Установка параметров EditText.
     */
    protected void checkEditText() {
        if(editTextMessage != null){
            editText.setText(editTextMessage);
        }
        if(hintTextMessage != null){
            editText.setHint(hintTextMessage);
        }
        editText.setTextColor(editTextColor);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, editTextSize);
        editText.setTypeface(editTextTypeface, editTextStyle.getValue());
        editText.setHintTextColor(hintTextColor);
    }
}
