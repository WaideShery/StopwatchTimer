package com.neirx.neirdialogs.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.neirx.neirdialogs.R;


public class MessageDialogFragment extends BaseDialogFragment {
    protected TextView tvMessage;
    protected ScrollView svMessage;
    protected String message;
    protected int messageColor;
    protected float messageSize;
    protected TextStyle messageStyle;
    protected Typeface messageTypeface;

    /**
     * Установка текста сообщения диалогового окна.
     * Если не вызывать этот метод, то область сообщения будет пустой.
     *
     * @param message текст сообщения диалогового окна
     */
    public void setMessage(String message) {
        this.message = message;
    }


    /**
     * Установка цвета сообщения диалогового окна.
     *
     * @param color ресурс цвета
     */
    public void setMessageColor(int color) {
        messageColor = color;
    }

    /**
     * Установка размера шрифта сообщения диалогового окна.
     *
     * @param sizeSp размер шрифта
     */
    public void setMessageSize(float sizeSp) {
        messageSize = sizeSp;
    }

    /**
     * Установка шрифта и стиля отображения сообщения диалогового окна.
     *
     * @param tf    шрифт
     * @param style стиль текста
     */
    public void setMessageTypeface(Typeface tf, TextStyle style) {
        messageTypeface = tf;
        messageStyle = style;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.holo_message_dialog, null);
        lineBtnFirst = view.findViewById(R.id.viewHorFirst);
        lineBtnSecond = view.findViewById(R.id.viewHorSecond);
        btnNegative = (Button) view.findViewById(R.id.btnNegative);
        btnNeutral = (Button) view.findViewById(R.id.btnNeutral);
        btnPositive = (Button) view.findViewById(R.id.btnPositive);
        layTitle = (LinearLayout) view.findViewById(R.id.layTitle);
        layButtons = view.findViewById(R.id.layButtons);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        dividerTitle = view.findViewById(R.id.dividerTitle);
        tvMessage = (TextView) view.findViewById(R.id.tvMessage);
        svMessage = (ScrollView) view.findViewById(R.id.svMessage);

        checkDialogBackground();
        checkTitle();
        checkButtons();
        checkMessage();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        // Create the AlertDialog object and return it
        return builder.create();
    }

    /**
     * Метод проверки, установлен ли текст сообщения диалогового окна.
     * Если не установлен, то область сообщения остается пустой.
     */
    protected void checkMessage() {
        if (message == null) {
            message = "";
        }
        tvMessage.setText(message);
    }
}
