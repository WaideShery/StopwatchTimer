package com.neirx.neirdialogs.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.neirx.neirdialogs.R;
import com.neirx.neirdialogs.adapter.ListChoiceAdapter;


public class ListDialogFragment extends BaseDialogFragment {
    protected ListView lvChoice;
    protected String[] items;

    public void setItems(String[] items) {
        this.items = items;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.holo_list_dialog, null);
        lineBtnFirst = view.findViewById(R.id.viewHorFirst);
        lineBtnSecond = view.findViewById(R.id.viewHorSecond);
        btnNegative = (Button) view.findViewById(R.id.btnNegative);
        btnNeutral = (Button) view.findViewById(R.id.btnNeutral);
        btnPositive = (Button) view.findViewById(R.id.btnPositive);
        layTitle = (LinearLayout) view.findViewById(R.id.layTitle);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        dividerTitle = view.findViewById(R.id.dividerTitle);
        lvChoice = (ListView) view.findViewById(R.id.lvChoice);
        layButtons = view.findViewById(R.id.layButtons);

        checkTitle();
        checkButtons();
        checkList();


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        return builder.create();
    }

    protected void checkList() {
        if(items == null){
            items = new String[]{""};
        }
        BaseAdapter adapter = new ListChoiceAdapter(items);
        lvChoice.setAdapter(adapter);
    }
}
