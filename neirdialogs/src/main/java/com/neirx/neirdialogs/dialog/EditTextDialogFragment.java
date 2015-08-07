package com.neirx.neirdialogs.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.neirx.neirdialogs.R;


public class EditTextDialogFragment extends BaseDialogFragment {
    protected EditText editText;
    protected String text;

    public void setEditText(String text) {
        this.text = text;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.holo_edittext_dialog, null);
        btnNegative = (Button) view.findViewById(R.id.btnNegative);
        btnNeutral = (Button) view.findViewById(R.id.btnNeutral);
        btnPositive = (Button) view.findViewById(R.id.btnPositive);
        layTitle = (LinearLayout) view.findViewById(R.id.layTitle);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        dividerTitle = view.findViewById(R.id.dividerTitle);
        editText = (EditText) view.findViewById(R.id.editText);

        checkTitle();
        checkButtons();
        checkEditText();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        // Create the AlertDialog object and return it
        return builder.create();
    }

    protected void checkEditText() {
        if(text != null){
            editText.setText(text);
        }
    }
}
