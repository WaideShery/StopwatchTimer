package com.neirx.neirdialogs.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.neirx.neirdialogs.R;

import java.util.List;

/**
 * Created by Waide Shery on 25.07.2015.
 *
 */
public class SingleChoiceAdapter extends ChoiceAdapter {
    private LayoutInflater lInflater;
    private Context context;
    private List<String> listItems;
    private int checkedItemPos;

    public SingleChoiceAdapter(List<String> listItems, int[] checkedItemsPos, Context context) {
        this.listItems = listItems;
        this.checkedItemPos = checkedItemsPos[0];
        this.context = context;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public int getCheckedItemPos() {
        return checkedItemPos;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.adapter_singlechoice, parent, false);
        }
        RadioButton radioButton = (RadioButton) view.findViewById(R.id.radioButton);
        setParam(radioButton);
        radioButton.setText(listItems.get(position));
        if (leftFlagSelectorId > -1) {
            radioButton.setCompoundDrawablesWithIntrinsicBounds(
                    context.getResources().getDrawable(leftFlagSelectorId), null, null, null);
        } else if (rightFlagSelectorId > -1) {
            radioButton.setCompoundDrawablesWithIntrinsicBounds(
                    null, null, context.getResources().getDrawable(rightFlagSelectorId), null);
        }
        if (bgSelector > -1) radioButton.setBackgroundResource(bgSelector);
        radioButton.setChecked(checkedItemPos == position);

        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedItemPos = position;
                SingleChoiceAdapter.this.notifyDataSetChanged();
            }
        });
        return view;
    }

}
