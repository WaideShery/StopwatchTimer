package com.neirx.neirdialogs.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.neirx.neirdialogs.R;
import com.neirx.neirdialogs.adapters.MultiChoiceAdapter;
import com.neirx.neirdialogs.interfaces.MultiChoiceDialog;
import com.neirx.neirdialogs.interfaces.NeirDialogInterface;

/**
 * Created by Waide Shery on 07.10.2015.
 *
 */
public class HoloMultiChoiceDialog extends HoloChoiceDialog implements MultiChoiceDialog {
    MultiChoiceAdapter adapter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.holo_choice_dialog, null);
        lineBtnTopHor = view.findViewById(R.id.viewTop);
        lineBtnLeftVer = view.findViewById(R.id.viewLeft);
        lineBtnRightVer = view.findViewById(R.id.viewRight);
        btnNegative = (Button) view.findViewById(R.id.btnNegative);
        btnNeutral = (Button) view.findViewById(R.id.btnNeutral);
        btnPositive = (Button) view.findViewById(R.id.btnPositive);
        layTitle = (LinearLayout) view.findViewById(R.id.layTitle);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        dividerTitle = view.findViewById(R.id.dividerTitle);
        lvChoice = (ListView) view.findViewById(R.id.lvChoice);
        layButtons = (ViewGroup) view.findViewById(R.id.layButtons);

        checkRootView();
        checkTitle();
        checkButtons();
        checkSingleChoice();


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onClick(View view) {
        dismiss();
        if (onClickListener == null) {
            Toast.makeText(getActivity(), "onClickListener == null", Toast.LENGTH_SHORT).show();
            return;
        }
        int id = view.getId();
        int buttonId = 0;
        if (id == R.id.btnPositive) {
            buttonId = NeirDialogInterface.BUTTON_POSITIVE;
        } else if (id == R.id.btnNegative) {
            buttonId = NeirDialogInterface.BUTTON_NEGATIVE;
        } else if (id == R.id.btnNeutral) {
            buttonId = NeirDialogInterface.BUTTON_NEUTRAL;
        }

        Integer[] arrCheckedPos = adapter.getCheckedItemsPos();
        onClickListener.onClick(tag, buttonId, arrCheckedPos);

    }

    protected void checkSingleChoice() {
        adapter = new MultiChoiceAdapter(items, checkedItemsPos, getActivity());
        adapter.setItemParam(itemTextColor, itemTextSize, itemTextStyle, itemTextTypeface,
                itemTextGravity, leftFlagSelectorId, rightFlagSelectorId, itemBackgroundSelectorId);
        adapter.setItemTextPadding(itemPaddingStart, itemPaddingTop, itemPaddingEnd, itemPaddingBottom);

        lvChoice.setAdapter(adapter);

        if(isDividerDrawable && dividerDrawable != null){
            lvChoice.setDivider(dividerDrawable);
        } else {
            Drawable defaultDivider = getResources().getDrawable(R.drawable.list_divider);
            assert defaultDivider != null;
            defaultDivider.setColorFilter(dividerColorId, PorterDuff.Mode.SRC_ATOP);
            lvChoice.setDivider(defaultDivider);
        }
        lvChoice.setDividerHeight((int) getPX(dividerHeightDp));
    }
}
