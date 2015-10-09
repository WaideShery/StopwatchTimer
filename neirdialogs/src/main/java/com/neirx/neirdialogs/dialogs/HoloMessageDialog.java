package com.neirx.neirdialogs.dialogs;


import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.neirx.neirdialogs.R;
import com.neirx.neirdialogs.enums.TextStyle;
import com.neirx.neirdialogs.interfaces.MessageDialog;
import com.neirx.neirdialogs.interfaces.NeirDialogInterface;


public class HoloMessageDialog extends HoloBaseDialog implements MessageDialog {
    protected TextView tvMessage;
    protected ScrollView svMessage;
    protected String message;
    protected int messageColor = -1;
    protected float messageSize = 0;
    protected TextStyle messageStyle;
    protected Typeface messageTypeface;
    protected int messageGravity = -1;
    protected int messagePaddingStart = -1, messagePaddingTop = -1, messagePaddingEnd = -1, messagePaddingBottom = -1;

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
     * @param textColor  цвет текста
     */
    public void setMessageColor(int textColor){
        messageColor = textColor;
    }

    /**
     * Установка размера сообщения диалогового окна.
     *
     * @param textSizeSP размер текста в sp
     */
    public void setMessageSize(int textSizeSP){
        messageSize = textSizeSP;
    }

    /**
     * Установка шрифта сообщения диалогового окна.
     *
     * @param typeface   шрифт
     * @param textStyle  стиль
     */
    public void setMessageFont(Typeface typeface, TextStyle textStyle){
        messageTypeface = typeface;
        messageStyle = textStyle;
    }

    /**
     * Установка выравнивания сообщения диалогового окна.
     * @param gravity выравнивание, смотреть {@link android.view.Gravity}
     */
    public void setMessageGravity(int gravity){
        messageGravity = gravity;
    }

    /**
     * Установка отступов сообщения диалогового окна в dp.
     *
     * @param start  начало
     * @param top    верх
     * @param end    конец
     * @param bottom низ
     */
    public void setMessagePaddingDP(int start, int top, int end, int bottom) {
        messagePaddingStart = start;
        messagePaddingTop = top;
        messagePaddingEnd = end;
        messagePaddingBottom = bottom;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.holo_message_dialog, null);
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
        tvMessage = (TextView) view.findViewById(R.id.tvMessage);
        svMessage = (ScrollView) view.findViewById(R.id.svMessage);

        checkRootView();
        checkTitle();
        checkButtons();
        checkMessage();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        // Create the AlertDialog object and return it
        return builder.create();
    }


    /**
     * Метод нажатия на кнопки диалогового окна.
     * Вызывает NeirDialogInterface.OnClickListener.onClick(String tag, int buttonId, Object extraData).
     * В объекте extraData передает null.
     */
    @Override
    public void onClick(View view) {
        dismiss();
        if (onClickListener == null) {
            return;
        }
        int id = view.getId();
        if (id == R.id.btnPositive) {
            onClickListener.onClick(tag, NeirDialogInterface.BUTTON_POSITIVE, null);
        } else if (id == R.id.btnNegative) {
            onClickListener.onClick(tag, NeirDialogInterface.BUTTON_NEGATIVE, null);
        } else if (id == R.id.btnNeutral) {
            onClickListener.onClick(tag, NeirDialogInterface.BUTTON_NEUTRAL, null);
        }
    }

    /**
     * Метод проверки, установлен ли текст сообщения диалогового окна.
     * Если не установлен, то область сообщения остается пустой.
     * Установка параметров тесктового поля.
     */
    protected void checkMessage() {
        if (message == null) {
            message = "";
        }
        tvMessage.setText(message);
        tvMessage.setTextColor(messageColor);
        if(messageSize > 0) tvMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, messageSize);

        if(messageTypeface != null && messageStyle != null) tvMessage.setTypeface(messageTypeface, messageStyle.getValue());
        else if(messageTypeface != null) tvMessage.setTypeface(messageTypeface);
        else if(messageStyle != null) tvMessage.setTypeface(Typeface.DEFAULT, messageStyle.getValue());

        if(messageGravity > -1) tvMessage.setGravity(messageGravity);

        if(messagePaddingStart > -1) {
            if (Build.VERSION.SDK_INT >= 16) {
                tvMessage.setPaddingRelative(tvTitlePaddingStart, tvTitlePaddingTop, tvTitlePaddingEnd, tvTitlePaddingBottom);
            } else {
                tvMessage.setPadding(tvTitlePaddingStart, tvTitlePaddingTop, tvTitlePaddingEnd, tvTitlePaddingBottom);
            }
        }
    }
}
